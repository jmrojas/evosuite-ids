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
package de.unisb.cs.st.evosuite.junit;

import de.unisb.cs.st.evosuite.testcase.ExecutionTrace;

public class TestRun {
	private final ExecutionTrace executionTrace;
	private final Throwable failure;

	public TestRun(ExecutionTrace executionTrace, Throwable failure) {
		this.executionTrace = executionTrace;
		this.failure = failure;
	}

	public ExecutionTrace getExecutionTrace() {
		return executionTrace;
	}

	public Throwable getFailure() {
		return failure;
	}
}