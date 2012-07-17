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
package org.evosuite.coverage;

import java.util.List;

import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestCaseExecutor;
import org.evosuite.testcase.TestChromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>Abstract TestCoverageGoal class.</p>
 *
 * @author Gordon Fraser
 */
public abstract class TestCoverageGoal {

	/** Constant <code>logger</code> */
	protected static Logger logger = LoggerFactory.getLogger(TestCoverageGoal.class);

	/** Constant <code>executor</code> */
	protected static TestCaseExecutor executor = TestCaseExecutor.getInstance();

	/**
	 * Return true if this coverage goal is covered by the given test
	 *
	 * @param test a {@link org.evosuite.testcase.TestChromosome} object.
	 * @return a boolean.
	 */
	public abstract boolean isCovered(TestChromosome test);

	/**
	 * Determine if there is an existing test case covering this goal
	 *
	 * @param tests a {@link java.util.List} object.
	 * @return a boolean.
	 */
	public boolean isCovered(List<TestChromosome> tests) {
		for (TestChromosome test : tests) {
			if (isCovered(test))
				return true;
		}
		return false;
	}

	/**
	 * <p>hasTimeout</p>
	 *
	 * @param result a {@link org.evosuite.testcase.ExecutionResult} object.
	 * @return a boolean.
	 */
	public static boolean hasTimeout(ExecutionResult result) {

		if (result == null) {
			logger.warn("Result is null!");
			return false;
		} else if (result.test == null) {
			logger.warn("Test is null!");
			return false;
		}
		int size = result.test.size();
		if (result.isThereAnExceptionAtPosition(size)) {
			if (result.getExceptionThrownAtPosition(size) instanceof TestCaseExecutor.TimeoutExceeded) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Execute a test case
	 *
	 * @param test
	 *            The test case to execute
	 * @return Result of the execution
	 */
	protected ExecutionResult runTest(TestChromosome test) {

		if (!test.isChanged() && test.getLastExecutionResult() != null)
			return test.getLastExecutionResult();

		ExecutionResult result = new ExecutionResult(test.getTestCase(), null);

		try {
			result = executor.execute(test.getTestCase());
		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			e.printStackTrace();
			logger.error("TG: Exception caught: ", e);
			System.exit(1);
		}

		return result;
	}
}
