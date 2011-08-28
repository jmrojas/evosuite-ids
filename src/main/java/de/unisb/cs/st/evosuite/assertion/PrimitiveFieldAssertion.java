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

import org.apache.commons.lang.StringEscapeUtils;

import de.unisb.cs.st.evosuite.testcase.CodeUnderTestException;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.TestCase;

public class PrimitiveFieldAssertion extends Assertion {

	public Field field;

	@Override
	public String getCode() {
		if (value == null) {
			return "assertNull(" + source.getName() + "." + field.getName() + ");";
		} else if (value.getClass().equals(Long.class)) {
			String val = value.toString();
			return "assertEquals(" + source.getName() + "." + field.getName() + ", "
			+ val + "L);";
		} else if (value.getClass().equals(Float.class)) {
			String val = value.toString();
			return "assertEquals(" + source.getName() + "." + field.getName() + ", "
			+ val + "F);";
		} else if (value.getClass().equals(Character.class)) {
			String val = StringEscapeUtils.escapeJava(((Character) value).toString());
			return "assertEquals(" + source.getName() + "." + field.getName() + ", '"
			+ val + "');";
		} else if (value.getClass().equals(String.class)) {
			return "assertEquals(" + source.getName() + "." + field.getName() + ", \""
			+ StringEscapeUtils.escapeJava((String) value) + "\");";
		} else
			return "assertEquals(" + source.getName() + "." + field.getName() + ", "
			+ value + ");";
	}

	@Override
	public Assertion clone(TestCase newTestCase) {
		PrimitiveFieldAssertion s = new PrimitiveFieldAssertion();
		s.source = newTestCase.getStatement(source.getStPosition()).getReturnValue();
		s.value = value;
		s.field = field;
		return s;
	}

	@Override
	public boolean evaluate(Scope scope) {
		try{
			Object obj = source.getObject(scope);
			if (obj != null) {
				try {
					Object val = field.get(obj);
					if (val != null)
						return val.equals(value);
					else
						return value == null;
				} catch (Exception e) {
					return true;
				}
			} else
				return true;
		}catch(CodeUnderTestException e){
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((field == null) ? 0 : field.hashCode());
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
		PrimitiveFieldAssertion other = (PrimitiveFieldAssertion) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		return true;
	}
}
