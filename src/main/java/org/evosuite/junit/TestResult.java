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

import org.evosuite.testcase.ExecutionTrace;

/**
 * <p>
 * TestResult class.
 * </p>
 * 
 * @author José Campos
 */
public class TestResult
{
	private String name;
	private boolean successful;
	private long runtime;
	private String trace;
	private ExecutionTrace executionTrace;

	public TestResult() {
		this.successful = true;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String n) {
		this.name = n;
	}

	public boolean wasSuccessful() {
		return this.successful;
	}
	public void setSuccessful(boolean s) {
		this.successful = s;
	}

	public long getRuntime() {
		return this.runtime;
	}
	public void setRuntime(long r) {
		this.runtime = r;
	}

	public String getTrace() {
		return this.trace;
	}
	public void setTrace(String t) {
		this.trace = t;
	}

	public ExecutionTrace getExecutionTrace() {
		return this.executionTrace;
	}
	public void setExecutionTrace(ExecutionTrace eT) {
		this.executionTrace = eT;
	}
}
