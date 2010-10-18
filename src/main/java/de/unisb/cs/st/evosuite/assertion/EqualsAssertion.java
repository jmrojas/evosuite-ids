/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.unisb.cs.st.evosuite.assertion;

import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.VariableReference;

public class EqualsAssertion extends Assertion {

	public VariableReference dest;
	
	@Override
	public Assertion clone() {
		EqualsAssertion s = new EqualsAssertion();
		s.source = source.clone();
		s.dest   = dest.clone();
		s.value  = value;
		return s;
	}

	@Override
	public String getCode() {
		if(((Boolean)value).booleanValue())
			return "assertTrue("+source.getName()+".equals("+dest.getName()+"))";
		else
			return "assertFalse("+source.getName()+".equals("+dest.getName()+"))";
	}

	@Override
	public boolean evaluate(Scope scope) {
		return scope.get(source).equals(scope.get(dest));
	}

}
