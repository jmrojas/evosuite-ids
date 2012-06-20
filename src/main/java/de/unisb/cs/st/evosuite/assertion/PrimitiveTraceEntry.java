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
package de.unisb.cs.st.evosuite.assertion;

import java.util.HashSet;
import java.util.Set;

import de.unisb.cs.st.evosuite.testcase.VariableReference;

/**
 * @author Gordon Fraser
 * 
 */
public class PrimitiveTraceEntry implements OutputTraceEntry {

	protected VariableReference var;

	protected Object value;

	public PrimitiveTraceEntry(VariableReference var, Object value) {
		this.var = var;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.OutputTraceEntry#differs(de.unisb.cs.st.evosuite.assertion.OutputTraceEntry)
	 */
	@Override
	public boolean differs(OutputTraceEntry other) {
		if (other instanceof PrimitiveTraceEntry) {
			PrimitiveTraceEntry otherEntry = (PrimitiveTraceEntry) other;
			if (!value.equals(otherEntry.value))
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.OutputTraceEntry#getAssertion(de.unisb.cs.st.evosuite.assertion.OutputTraceEntry)
	 */
	@Override
	public Set<Assertion> getAssertions(OutputTraceEntry other) {
		Set<Assertion> assertions = new HashSet<Assertion>();
		if (other instanceof PrimitiveTraceEntry) {
			PrimitiveTraceEntry otherEntry = (PrimitiveTraceEntry) other;
			if (otherEntry != null && otherEntry.value != null && value != null
			        && var.equals(otherEntry.var))
				if (!value.equals(otherEntry.value)) {
					PrimitiveAssertion assertion = new PrimitiveAssertion();
					assertion.value = value;
					assertion.source = var;
					assertions.add(assertion);
					assert (assertion.isValid());
				}
		}
		return assertions;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.OutputTraceEntry#getAssertion()
	 */
	@Override
	public Set<Assertion> getAssertions() {
		Set<Assertion> assertions = new HashSet<Assertion>();
		PrimitiveAssertion assertion = new PrimitiveAssertion();
		assertion.source = var;
		assertion.value = value;
		assertions.add(assertion);
		assert (assertion.isValid());

		return assertions;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.OutputTraceEntry#isDetectedBy(de.unisb.cs.st.evosuite.assertion.Assertion)
	 */
	@Override
	public boolean isDetectedBy(Assertion assertion) {
		if (assertion instanceof PrimitiveAssertion) {
			PrimitiveAssertion ass = (PrimitiveAssertion) assertion;
			if (var.equals(ass.source))
				return !value.equals(ass.value);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.assertion.OutputTraceEntry#cloneEntry()
	 */
	@Override
	public OutputTraceEntry cloneEntry() {
		return new PrimitiveTraceEntry(var, value);
	}

}
