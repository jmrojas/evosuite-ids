/*
 * Copyright (C) 2010 Saarland University
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
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.testsuite;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.FitnessFunction;
import de.unisb.cs.st.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import de.unisb.cs.st.evosuite.testcase.ExecutableChromosome;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.ExecutionTracer;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;

/**
 * @author Gordon Fraser
 * 
 */
public abstract class TestSuiteFitnessFunction extends FitnessFunction {

	private static final long serialVersionUID = 7243635497292960457L;

	protected static Logger logger = Logger.getLogger(TestSuiteFitnessFunction.class);

	protected static TestCaseExecutor executor = TestCaseExecutor.getInstance();

	/**
	 * Execute a test case
	 * 
	 * @param test
	 *            The test case to execute
	 * @param mutant
	 *            The mutation to active (null = no mutation)
	 * 
	 * @return Result of the execution
	 */
	public ExecutionResult runTest(TestCase test) {
		ExecutionResult result = new ExecutionResult(test, null);

		try {
			result = executor.execute(test);
			/*
						result.exceptions = executor.run(test);
						executor.setLogging(true);
						result.trace = ExecutionTracer.getExecutionTracer().getTrace();
			*/
			int num = test.size();
			MaxStatementsStoppingCondition.statementsExecuted(num);
		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			try {
				Thread.sleep(1000);
				result.setTrace(ExecutionTracer.getExecutionTracer().getTrace());
			} catch (Exception e1) {
				e.printStackTrace();
				// TODO: Do some error recovery?
				System.exit(1);
			}

		}

		// System.out.println("TG: Killed "+result.getNumKilled()+" out of "+mutants.size());
		return result;
	}

	
	protected static boolean hasTimeout(ExecutionResult result) {

		if (result == null) {
			return false;
		} else if (result.test == null) {
			return false;
		}
		int size = result.test.size();
		if (result.exceptions.containsKey(size)) {
			if (result.exceptions.get(size) instanceof TestCaseExecutor.TimeoutExceeded) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void updateIndividual(Chromosome individual, double fitness) {
		individual.setFitness(fitness);
	}

	protected List<ExecutionResult> runTestSuite(AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite) {
		CurrentChromosomeTracker.getInstance().modification(suite);
		List<ExecutionResult> results = new ArrayList<ExecutionResult>();

		for (ExecutableChromosome chromosome : suite.getTestChromosomes()) {
			// Only execute test if it hasn't been changed
			if (chromosome.isChanged() || chromosome.getLastExecutionResult() == null) {
				ExecutionResult result = chromosome.executeForFitnessFunction(this);
				
				if (result != null) {
					results.add(result);
				
					chromosome.setLastExecutionResult(result); // .clone();
					chromosome.setChanged(false);
				}
			} else {
				results.add(chromosome.getLastExecutionResult());
			}
		}

		return results;
	}
}
