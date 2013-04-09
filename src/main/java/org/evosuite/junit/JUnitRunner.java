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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.evosuite.utils.LoggingUtils;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.manipulation.Filter;

/**
 * <p>
 * JUnitRunner class.
 * </p>
 * 
 * @author José Campos
 */
public class JUnitRunner
{
	public static boolean debug = true;

	private static HashSet<String> testNames = new HashSet<String>();

	private List<TestResult> testResults;

	public JUnitRunner() {
		this.testResults = new ArrayList<TestResult>();
	}

	public void run(Class<?> junitClass)
	{
		if (debug)
			LoggingUtils.getEvoLogger().info("Run JUnit Test: " + junitClass.getCanonicalName());

		Request request = Request.aClass(junitClass);
		request = request.filterWith(new Filter()
		{	
			@Override
			public boolean shouldRun(Description desc)
			{
				if (desc.getMethodName() == null) {
					for (String n : testNames) {
						if (n.contains(desc.getClassName()))
							return false;
					}

					testNames.add(desc.getClassName());
					return true;
				}
				else {
					if (!testNames.contains(desc.getClassName() + "#" + desc.getMethodName())) {
						testNames.add(desc.getClassName() + "#" + desc.getMethodName());
						return true;
					}
					else
						return false;
				}
			}

			@Override
			public String describe() {
				return null;
			}
		});

		JUnitCore junit = new JUnitCore();
		junit.addListener(new JUnitRunListener(this));
		Result result = junit.run(request);

		if (debug)
			LoggingUtils.getEvoLogger().info("TestResult: " + (result.wasSuccessful() ? 0 : 1));
	}

	public void finish(TestResult testResult) {
		this.testResults.add(testResult);
	}

	public List<TestResult> getTestResults() {
		return this.testResults;
	}
}
