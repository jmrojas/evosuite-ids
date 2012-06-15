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
package de.unisb.cs.st.evosuite.symbolic.expr;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.symbolic.ConstraintTooLongException;

public class IntegerToRealCast extends RealExpression implements Cast<Long> {
	private static final long serialVersionUID = -3070453617714122236L;

	protected Double concreteValue;

	protected Expression<Long> expr;

	public IntegerToRealCast(Expression<Long> _expr, Double _concValue) {
		this.expr = _expr;
		this.concreteValue = _concValue;
		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH)
			throw new ConstraintTooLongException();
	}

	@Override
	public Double getConcreteValue() {
		return concreteValue;
	}

	@Override
	public Expression<Long> getConcreteObject() {
		return expr;
	}

	@Override
	public String toString() {
		return "((REAL)" + expr + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof IntegerToRealCast) {
			IntegerToRealCast other = (IntegerToRealCast) obj;
			return this.expr.equals(other.expr);
			//					 && this.getSize() == other.getSize();
		}

		return false;
	}

	protected int size = 0;
	@Override
	public int getSize() {
		if (size == 0) {
			size = 1 + expr.getSize();
		}
		return size;
	}

	@Override
	public Double execute() {
		return ((Number) expr.execute()).doubleValue();
	}
}
