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
 * 
 * @author Gordon Fraser
 */
package org.evosuite.assertion;

import java.lang.reflect.Modifier;
import java.util.regex.Pattern;

import org.evosuite.testcase.CodeUnderTestException;
import org.evosuite.testcase.PrimitiveStatement;
import org.evosuite.testcase.Scope;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.VariableReference;

public class PrimitiveTraceObserver extends AssertionTraceObserver<PrimitiveTraceEntry> {

	private static Pattern addressPattern = Pattern.compile(".*[\\w+\\.]+@[abcdef\\d]+.*", Pattern.MULTILINE);

	/** {@inheritDoc} */
	@Override
	public synchronized void afterStatement(StatementInterface statement, Scope scope,
	        Throwable exception) {
		visitReturnValue(statement, scope);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.AssertionTraceObserver#visit(org.evosuite.testcase.StatementInterface, org.evosuite.testcase.Scope, org.evosuite.testcase.VariableReference)
	 */
	/** {@inheritDoc} */
	@Override
	protected void visit(StatementInterface statement, Scope scope, VariableReference var) {
		logger.debug("Checking primitive " + var);
		try {
			// Need only legal values
			if (var == null)
				return;

			// We don't need assertions on constant values
			if (statement instanceof PrimitiveStatement<?>)
				return;

			Object object = var.getObject(scope);

			// We don't need to compare to null
			if (object == null)
				return;

			// We can't check private member enums
			if (object.getClass().isEnum()
			        && !Modifier.isPublic(object.getClass().getModifiers()))
				return;

			if (object.getClass().isPrimitive() || object.getClass().isEnum()
			        || isWrapperType(object.getClass()) || object instanceof String) {
				if(object instanceof String) {
					// Check if there is a reference that would make the test fail
					if(addressPattern.matcher((String)object).find()) {
						return;
					}
					// String literals may not be longer than 32767
					if(((String)object).length() >= 32767) {
						return;
					}
				}
				logger.debug("Observed value " + object + " for statement "
				        + statement.getCode());
				trace.addEntry(statement.getPosition(), var, new PrimitiveTraceEntry(var,
				        object));

			}
		} catch (CodeUnderTestException e) {
			logger.debug("", e);			
		}
	}
}
