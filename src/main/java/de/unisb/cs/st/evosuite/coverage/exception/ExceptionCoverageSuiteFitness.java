/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unisb.cs.st.evosuite.coverage.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.testcase.ConstructorStatement;
import de.unisb.cs.st.evosuite.testcase.ExecutableChromosome;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.MethodStatement;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testsuite.AbstractTestSuiteChromosome;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteFitnessFunction;

public class ExceptionCoverageSuiteFitness extends TestSuiteFitnessFunction {

	private static final long serialVersionUID = 1565793073526627496L;

	private static Logger logger = LoggerFactory.getLogger(ExceptionCoverageSuiteFitness.class);

	protected TestSuiteFitnessFunction baseFF;

	public ExceptionCoverageSuiteFitness() {
		baseFF = new BranchCoverageSuiteFitness();
	}

	@Override
	public double getFitness(Chromosome individual) {
		logger.trace("Calculating exception fitness");

		/*
		 * We first calculate fitness based on coverage. this not only 
		 * has side-effect of changing "fitness" in individual, but also "coverage".
		 * but because "coverage" is only used for stats, no need to update it here, as
		 * anyway it d be bit difficult to define
		 */
		double coverageFitness = baseFF.getFitness(individual);

		/*
		 * for each method in the SUT, we keep track of which kind of exceptions were thrown.
		 * we distinguish between "implicit" and "explicit" 
		 */
		Map<String, Set<Class<?>>> implicitTypesOfExceptions = new HashMap<String, Set<Class<?>>>();
		Map<String, Set<Class<?>>> explicitTypesOfExceptions = new HashMap<String, Set<Class<?>>>();

		AbstractTestSuiteChromosome<ExecutableChromosome> suite = (AbstractTestSuiteChromosome<ExecutableChromosome>) individual;
		List<ExecutionResult> results = runTestSuite(suite);
		Map<TestCase, Map<Integer, Boolean>> isExceptionExplicit = new HashMap<TestCase, Map<Integer, Boolean>>();

		// for each test case
		for (ExecutionResult result : results) {
			isExceptionExplicit.put(result.test, result.explicitExceptions);

			//iterate on the indexes of the statements that resulted in an exception
			for (Integer i : result.getPositionsWhereExceptionsWereThrown()) {
				if (i >= result.test.size()) {
					// Timeouts are put after the last statement if the process was forcefully killed
					continue;
				}
				//not interested in security exceptions when Sandbox is active
				Throwable t = result.getExceptionThrownAtPosition(i);
				if (t instanceof SecurityException && Properties.SANDBOX)
					continue;
				// If the exception was thrown in the test directly, it is also not interesting
				if (t.getStackTrace().length > 0
				        && t.getStackTrace()[0].getClassName().startsWith("de.unisb.cs.st.evosuite.testcase")) {
					continue;
				}

				String methodName = "";
				boolean sutException = false;				
				if (result.test.getStatement(i) instanceof MethodStatement) {
					MethodStatement ms = (MethodStatement) result.test.getStatement(i);
					Method method = ms.getMethod();
					methodName = method.getName() + Type.getMethodDescriptor(method);
					if (method.getDeclaringClass().equals(Properties.getTargetClass()))
						sutException = true;
				} else if (result.test.getStatement(i) instanceof ConstructorStatement) {
					ConstructorStatement cs = (ConstructorStatement) result.test.getStatement(i);
					Constructor<?> constructor = cs.getConstructor();
					methodName = "<init>" + Type.getConstructorDescriptor(constructor);
					if (constructor.getDeclaringClass().equals(Properties.getTargetClass()))
						sutException = true;
				}

				boolean notDeclared = !result.test.getStatement(i).getDeclaredExceptions().contains(t.getClass());
				
				/*
				 * We only consider exceptions that were thrown directly in the SUT (not called libraries)
				 * and that are not declared in the signature of the method.
				 */
				
				if (notDeclared && sutException) {
					/*
					 * we need to distinguish whether it is explicit (ie "throw" in the code, eg for validating
					 * input for pre-condition) or implicit ("likely" a real fault).
					 */

					boolean isExplicit = isExceptionExplicit.get(result.test).containsKey(i)
					        && isExceptionExplicit.get(result.test).get(i);
					
					if (isExplicit) {
						if (!explicitTypesOfExceptions.containsKey(methodName))
							explicitTypesOfExceptions.put(methodName,
							                              new HashSet<Class<?>>());
						explicitTypesOfExceptions.get(methodName).add(t.getClass());
					} else {
						if (!implicitTypesOfExceptions.containsKey(methodName))
							implicitTypesOfExceptions.put(methodName,
							                              new HashSet<Class<?>>());
						implicitTypesOfExceptions.get(methodName).add(t.getClass());
					}
				}

			}
		}

		int nExc = getNumExceptions(implicitTypesOfExceptions)
		        + getNumExceptions(explicitTypesOfExceptions);

		double exceptionFitness = 1d / (1d + nExc);

		individual.setFitness(coverageFitness + exceptionFitness);
		return coverageFitness + exceptionFitness;
	}

	private static int getNumExceptions(Map<String, Set<Class<?>>> exceptions) {
		int total = 0;
		for (Set<Class<?>> exceptionSet : exceptions.values()) {
			total += exceptionSet.size();
		}
		return total;
	}
}
