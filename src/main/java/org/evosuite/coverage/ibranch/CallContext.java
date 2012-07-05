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
/**
 * 
 */
package org.evosuite.coverage.ibranch;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>CallContext class.</p>
 *
 * @author Gordon Fraser
 */
public class CallContext {

	private static class Call {

		private final String className;
		private final String methodName;

		public Call(String classname, String methodName) {
			this.className = classname;
			this.methodName = methodName;
		}

		/**
		 * @return the className
		 */
		public String getClassName() {
			return className;
		}

		/**
		 * @return the methodName
		 */
		public String getMethodName() {
			return methodName;
		}
	}

	private final List<Call> context = new ArrayList<Call>();

	/**
	 * <p>Constructor for CallContext.</p>
	 *
	 * @param stackTrace an array of {@link java.lang.StackTraceElement} objects.
	 */
	public CallContext(StackTraceElement[] stackTrace) {
		for (StackTraceElement element : stackTrace) {
			if (!element.getClassName().startsWith("org.evosuite"))
				context.add(new Call(element.getClassName(), element.getMethodName()));
		}
	}

	/**
	 * Determine if the concrete stack trace matches this call context
	 *
	 * @param stackTrace an array of {@link java.lang.StackTraceElement} objects.
	 * @return a boolean.
	 */
	public boolean matches(StackTraceElement[] stackTrace) {
		// TODO: Implement
		return false;
	}

	/**
	 * <p>getRootClassName</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getRootClassName() {
		return context.get(0).getClassName();
	}

	/**
	 * <p>getRootMethodName</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getRootMethodName() {
		return context.get(0).getMethodName();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		String result = "";

		return result;
	}
}
