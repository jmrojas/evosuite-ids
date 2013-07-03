/**
 * Copyright (C) 2011,2012,2013 Gordon Fraser, Andrea Arcuri, José Campos and EvoSuite
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
package org.evosuite.junit;

import org.evosuite.testcase.ExecutionTracer;
import org.evosuite.utils.LoggingUtils;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * <p>
 * JUnitRunListener class.
 * </p>
 * 
 * @author José Campos
 */
public class JUnitRunListener extends RunListener
{
	public static boolean debug = false;

	private JUnitRunner junitRunner = null;
	private TestResult	testResult = null;

	private long start;

	public JUnitRunListener(JUnitRunner jR) {
		this.junitRunner = jR;
	}

	/**
	 * Called before any tests have been run. 
	 */
	@Override
	public void testRunStarted(Description description)	{
		if (debug)
			LoggingUtils.getEvoLogger().info("Number of testcases to execute : " + description.testCount());
	}

	/**
	 *  Called when all tests have finished
	 */
	@Override
	public void testRunFinished(Result result) {
		if (debug)
			LoggingUtils.getEvoLogger().info("Number of testcases executed : " + result.getRunCount());
	}

	/**
	 *  Called when an atomic test is about to be started
	 */
	@Override
	public void testStarted(Description description)
	{
		if (debug)
			LoggingUtils.getEvoLogger().info("* Started : " +
									"ClasseName: " + description.getClassName() + ", MethodName: " + description.getMethodName());

		this.start = System.nanoTime();

		this.testResult = new TestResult();
		this.testResult.setName(description.getClassName() + "#" + description.getMethodName());

		ExecutionTracer.enable();
		ExecutionTracer.enableTraceCalls();
		ExecutionTracer.setCheckCallerThread(false);
	}

	/**
	 *  Called when an atomic test has finished, whether the test succeeds or fails 
	 */
	@Override
	public void testFinished(Description description)
	{
		if (debug)
			LoggingUtils.getEvoLogger().info("* Finished : " +
									"ClasseName: " + description.getClassName() + ", MethodName: " + description.getMethodName());

		ExecutionTracer.disable();

		this.testResult.setRuntime(System.nanoTime() - this.start);
		this.testResult.setExecutionTrace(ExecutionTracer.getExecutionTracer().getTrace());
		ExecutionTracer.getExecutionTracer().clear();

		this.junitRunner.finish(this.testResult);
	}

	/**
	 * Called when an atomic test fails
	 */
	@Override
	public void testFailure(Failure failure)
	{
		if (debug) {
			LoggingUtils.getEvoLogger().info("Message : ..." + failure.getMessage() + "...");
			LoggingUtils.getEvoLogger().info("Trace : " + failure.getTrace());
			LoggingUtils.getEvoLogger().info("Description : " + failure.getDescription());
			LoggingUtils.getEvoLogger().info("TestHeader : " + failure.getTestHeader());
			LoggingUtils.getEvoLogger().info("ToString : " + failure.toString());
		}

		this.testResult.setSuccessful(false);
		this.testResult.setTrace(failure.getTrace());
	}

	/**
	 *  Called when a test will not be run, generally because a test method is annotated with Ignore 
	 */
	@Override
	public void testIgnored(Description description) throws java.lang.Exception  {
		if (debug)
			LoggingUtils.getEvoLogger().info("Execution of test case ignored : "+ description.getMethodName());
	}
}
