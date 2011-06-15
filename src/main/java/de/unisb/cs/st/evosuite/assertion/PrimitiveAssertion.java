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

import org.apache.commons.lang.StringEscapeUtils;

import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.TestCase;

public class PrimitiveAssertion extends Assertion {

	@Override
	public String getCode() {
		if (value == null) {
			return "assertNull(" + source.getName() + ");";
		} else if (value.getClass().equals(Long.class)) {
			String val = value.toString();
			return "assertEquals(" + source.getName() + ", " + val + "L);";
		} else if (value.getClass().equals(Float.class)) {
			String val = value.toString();
			return "assertEquals(" + source.getName() + ", " + val + "F);";
		} else if (value.getClass().equals(Character.class)) {
			String val = StringEscapeUtils.escapeJava(((Character) value)
			        .toString());
			return "assertEquals(" + source.getName() + ", '" + val + "');";
		} else if (value.getClass().equals(String.class)) {
			return "assertEquals(" + source.getName() + ", \""
			        + StringEscapeUtils.escapeJava((String) value) + "\");";
		} else
			return "assertEquals(" + source.getName() + ", " + value + ");";
	}

	@Override
	public Assertion clone(TestCase newTestCase) {
		PrimitiveAssertion s = new PrimitiveAssertion();
		s.source = newTestCase.getStatement(source.getStPosition()).getReturnValue();
		s.value = value;
		return s;
	}

	@Override
	public boolean evaluate(Scope scope) {
		if (value != null)
			return value.equals(scope.get(source));
		else
			return scope.get(source) == null;
	}

}
