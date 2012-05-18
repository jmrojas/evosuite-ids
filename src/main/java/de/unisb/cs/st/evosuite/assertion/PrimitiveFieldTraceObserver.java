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

package de.unisb.cs.st.evosuite.assertion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import de.unisb.cs.st.evosuite.testcase.CodeUnderTestException;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.VariableReference;

public class PrimitiveFieldTraceObserver extends
        AssertionTraceObserver<PrimitiveFieldTraceEntry> {

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.AssertionTraceObserver#visit(de.unisb.cs.st.evosuite.testcase.StatementInterface, de.unisb.cs.st.evosuite.testcase.Scope, de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	protected void visit(StatementInterface statement, Scope scope, VariableReference var) {
		logger.debug("Checking fields of " + var);
		try {
			if (var == null)
				return;

			Object object = var.getObject(scope);
			int position = statement.getPosition();

			if (object != null && !object.getClass().isPrimitive()
			        && !object.getClass().isEnum() && !isWrapperType(object.getClass())) {

				PrimitiveFieldTraceEntry entry = new PrimitiveFieldTraceEntry(var);

				for (Field field : var.getVariableClass().getFields()) {
					// TODO Check for wrapper types
					if (Modifier.isPublic(field.getModifiers())
					        && !field.getType().equals(void.class)
					        && field.getType().isPrimitive()) {
						try {
							logger.debug("Keeping field " + field + " with value "
							        + field.get(object));
							entry.addValue(field, field.get(object));
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						}
					}
				}
				trace.addEntry(position, var, entry);
			}
		} catch (CodeUnderTestException e) {
			logger.error("",e);
			//throw new UnsupportedOperationException();
		}
	}

}
