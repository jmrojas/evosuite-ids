/**
 * Copyright (C) 2012 Gordon Fraser, Andrea Arcuri
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
package de.unisb.cs.st.evosuite.assertion;

import de.unisb.cs.st.evosuite.testcase.CodeUnderTestException;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.utils.NumberFormatter;

public class InspectorAssertion extends Assertion {

	private static final long serialVersionUID = -4080051661226820222L;

	// VariableReference value;
	protected Inspector inspector;

	public Inspector getInspector() {
		return inspector;
	}

	@Override
	public Assertion copy(TestCase newTestCase, int offset) {
		InspectorAssertion s = new InspectorAssertion();
		s.source = newTestCase.getStatement(source.getStPosition() + offset).getReturnValue();
		s.inspector = inspector;
		s.value = value;
		return s;

	}

	@Override
	public String getCode() {
		if (value == null) {
			return "assertNull(" + source.getName() + "." + inspector.getMethodCall()
			        + "());";
		} else if (value.getClass().equals(Long.class)) {
			return "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + source.getName() + "." + inspector.getMethodCall() + "());";
		} else if (value.getClass().equals(Float.class)) {
			return "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + source.getName() + "." + inspector.getMethodCall() + "(), 0.01F);";
		} else if (value.getClass().equals(Double.class)) {
			return "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + source.getName() + "." + inspector.getMethodCall() + "(), 0.01D);";
		} else if (value.getClass().equals(Character.class)) {
			return "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + source.getName() + "." + inspector.getMethodCall() + "());";
		} else if (value.getClass().equals(String.class)) {
			return "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + source.getName() + "." + inspector.getMethodCall() + "());";
		} else if (value.getClass().isEnum()) {
			return "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + source.getName() + "." + inspector.getMethodCall() + "());";

		} else
			return "assertEquals(" + value + ", " + source.getName() + "."
			        + inspector.getMethodCall() + "());";
	}

	@Override
	public boolean evaluate(Scope scope) {
		try {
			if (source.getObject(scope) == null)
				return true; // TODO - true or false?
			else {
				try {
					Object val = inspector.getValue(source.getObject(scope));
					if (val == null)
						return val == value;
					else
						return val.equals(value);
				} catch (Exception e) {
					logger.error("* Exception during call to inspector",e);
					return true;
				}
			}
		} catch (CodeUnderTestException e) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inspector == null) ? 0 : inspector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InspectorAssertion other = (InspectorAssertion) obj;
		if (inspector == null) {
			if (other.inspector != null)
				return false;
		} else if (!inspector.equals(other.inspector))
			return false;

		return true;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.Assertion#isValid()
	 */
	@Override
	public boolean isValid() {
		return source != null;
	}
}
