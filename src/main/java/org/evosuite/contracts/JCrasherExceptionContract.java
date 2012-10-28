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
package org.evosuite.contracts;

import java.util.Arrays;
import java.util.List;

import org.evosuite.testcase.CodeUnderTestException;
import org.evosuite.testcase.ConstructorStatement;
import org.evosuite.testcase.MethodStatement;
import org.evosuite.testcase.Scope;
import org.evosuite.testcase.StatementInterface;


/**
 * <p>JCrasherExceptionContract class.</p>
 *
 * @author fraser
 */
public class JCrasherExceptionContract extends Contract {

	// ArrayIndexOutOfBoundsException, NegativeArraySizeException, ArrayStoreException, ClassCastException, and ArithmeticException

	private final static Class<?>[] uncheckedBugExceptions = {
	        ArrayIndexOutOfBoundsException.class, NegativeArraySizeException.class,
	        ArrayStoreException.class, ClassCastException.class,
	        ArithmeticException.class };

	private final static List<Class<?>> uncheckedExceptions = Arrays.asList(uncheckedBugExceptions);

	/* (non-Javadoc)
	 * @see org.evosuite.contracts.Contract#check(org.evosuite.testcase.StatementInterface, org.evosuite.testcase.Scope, java.lang.Throwable)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean check(StatementInterface statement, Scope scope, Throwable exception) {
		if (!isTargetStatement(statement))
			return true;

		if (exception != null) {
			if (exception instanceof CodeUnderTestException)
				return true;
			if (exception instanceof RuntimeException) {
				if (uncheckedExceptions.contains(exception.getClass()))
					return false;
				else {
					StackTraceElement element = exception.getStackTrace()[0];

					String methodName = "";
					if (statement instanceof ConstructorStatement)
						methodName = "<init>";
					else if (statement instanceof MethodStatement)
						methodName = ((MethodStatement) statement).getMethod().getName();
					else
						return true;

					// If the exception was thrown in the called method we assume it is a bug in the test, not the class		      
					if (element.getMethodName().equals(methodName)) {
						return true;
					}
					// If the exception was thrown in the test directly, it is also not interesting
					if (element.getClassName().startsWith("org.evosuite.testcase")) {
						return true;
					}
					return false;
				}
			}
		}

		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Undeclared exception (JCrasher style)";
	}

}