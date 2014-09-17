/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.coverage.output;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.MethodNameMatcher;
import org.evosuite.graphs.cfg.BytecodeInstructionPool;
import org.evosuite.testsuite.AbstractFitnessFactory;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jose Miguel Rojas
 *
 */
public class OutputCoverageFactory extends AbstractFitnessFactory<OutputCoverageTestFitness>  {

	private static final Logger logger = LoggerFactory.getLogger(OutputCoverageFactory.class);
	
	public static final String CHAR_ALPHA_SUFFIX = "alpha";
	public static final String CHAR_DIGIT_SUFFIX = "digit";
	public static final String CHAR_OTHER_SUFFIX = "other";
	public static final String BOOL_TRUE_SUFFIX = "true";
	public static final String BOOL_FALSE_SUFFIX = "false";
	public static final String NUM_POSITIVE_SUFFIX = "positive";
	public static final String NUM_ZERO_SUFFIX = "zero";
	public static final String NUM_NEGATIVE_SUFFIX = "negative";
	public static final String DEFAULT_SUFFIX = "default";
	public static final String REF_NULL_SUFFIX = "null";
	public static final String REF_NONNULL_SUFFIX = "nonnull";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.coverage.TestCoverageFactory#getCoverageGoals()
	 */
	/** {@inheritDoc} */
	@Override
	public List<OutputCoverageTestFitness> getCoverageGoals() {
		List<OutputCoverageTestFitness> goals = new ArrayList<OutputCoverageTestFitness>();

		long start = System.currentTimeMillis();
		String targetClass = Properties.TARGET_CLASS;

		final MethodNameMatcher matcher = new MethodNameMatcher();
		for (String className : BytecodeInstructionPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT()).knownClasses()) {
			if (!(targetClass.equals("") || className.endsWith(targetClass)))
				continue ;
			for (String methodName : BytecodeInstructionPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT()).knownMethods(className)) {
				if (!matcher.methodMatches(methodName))
					continue ;
				logger.info("Adding goals for method " + className + "." + methodName);
				Type returnType = Type.getReturnType(methodName);
				switch (returnType.getSort()) {
				case Type.BOOLEAN:
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), BOOL_TRUE_SUFFIX)));
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), BOOL_FALSE_SUFFIX)));
					break;
				case Type.CHAR:
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), OutputCoverageFactory.CHAR_ALPHA_SUFFIX)));
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), OutputCoverageFactory.CHAR_DIGIT_SUFFIX)));
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), OutputCoverageFactory.CHAR_OTHER_SUFFIX)));
					break;
				case Type.BYTE:
				case Type.SHORT:
				case Type.INT:
				case Type.FLOAT:
				case Type.LONG:
				case Type.DOUBLE:
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), NUM_NEGATIVE_SUFFIX)));
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), NUM_ZERO_SUFFIX)));
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), NUM_POSITIVE_SUFFIX)));		
					break;
				case Type.ARRAY:
				case Type.OBJECT:
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), REF_NULL_SUFFIX)));
					goals.add(new OutputCoverageTestFitness(new OutputCoverageGoal(className, methodName, returnType.toString(), REF_NONNULL_SUFFIX)));
					break;
				default:
					// Ignore
					// TODO: what to do with the sort for METHOD?
					break;
				}
			}
		}		
		goalComputationTime = System.currentTimeMillis() - start;
		return goals;
	}
	
	public static String goalString(String className, String methodName, String suffix) {
		return new String(className + "." + methodName + ":" + suffix);
	}
}
