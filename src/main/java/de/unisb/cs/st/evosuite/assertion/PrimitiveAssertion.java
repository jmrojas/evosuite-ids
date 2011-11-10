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

import de.unisb.cs.st.evosuite.testcase.CodeUnderTestException;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.utils.NumberFormatter;

public class PrimitiveAssertion extends Assertion {

	@Override
	public String getCode() {

		if (value == null) {
			return "assertNull(" + source.getName() + ");";
		} else if (source.getVariableClass().equals(float.class)) {
			return "assertEquals(" + source.getName() + ", "
			        + NumberFormatter.getNumberString(value) + ", 0.01F);";
		} else if (source.getVariableClass().equals(double.class)) {
			return "assertEquals(" + source.getName() + ", "
			        + NumberFormatter.getNumberString(value) + ", 0.01D);";
		} else if (value.getClass().isEnum()) {
			return "assertEquals(" + source.getName() + ", "
			        + this.source.getSimpleClassName() + "." + value + ");";
		} else if (source.isWrapperType()) {
			if (source.getVariableClass().equals(Float.class)) {
				return "assertEquals((float)" + source.getName() + ", "
				        + NumberFormatter.getNumberString(value) + ", 0.01F);";
			} else if (source.getVariableClass().equals(Double.class)) {
				return "assertEquals((double)" + source.getName() + ", "
				        + NumberFormatter.getNumberString(value) + ", 0.01D);";
			} else if (value.getClass().isEnum()) {
				return "assertEquals(" + source.getName() + ", "
				        + this.source.getSimpleClassName() + "." + value + ");";
			} else
				return "assertEquals((" + NumberFormatter.getBoxedClassName(value) + ")"
				        + source.getName() + ", " + value + ");";
		} else
			return "assertEquals(" + source.getName() + ", "
			        + NumberFormatter.getNumberString(value) + ");";

	}

	@Override
	public Assertion copy(TestCase newTestCase, int offset) {
		PrimitiveAssertion s = new PrimitiveAssertion();
		s.source = source.copy(newTestCase, offset);
		s.value = value;
		return s;
	}

	@Override
	public boolean evaluate(Scope scope) {
		try {
			if (value != null)
				return value.equals(source.getObject(scope));
			else
				return source.getObject(scope) == null;
		} catch (CodeUnderTestException e) {
			throw new UnsupportedOperationException();
		}
	}

}
