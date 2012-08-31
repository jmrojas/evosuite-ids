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
package org.evosuite.symbolic.expr;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntToStringCast extends StringExpression implements Cast<Long> {

	private static final long serialVersionUID = 2414222998301630838L;

	protected static Logger log = LoggerFactory.getLogger(IntToStringCast.class);

	protected final Expression<Long> intVar;

	/**
	 * <p>
	 * Constructor for IntToStringCast.
	 * </p>
	 * 
	 * @param expr
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 */
	@Deprecated
	public IntToStringCast(Expression<Long> expr) {
		this(expr, Long.toString((Long) expr.getConcreteValue()));
	}

	private final String concreteValue;

	public IntToStringCast(Expression<Long> expr, String concV) {
		this.intVar = expr;
		this.containsSymbolicVariable = this.intVar.containsSymbolicVariable();
		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH)
			throw new ConstraintTooLongException();
		this.concreteValue = concV;
	}

	/** {@inheritDoc} */
	@Override
	public String execute() {
		return Long.toString((Long) intVar.execute());
	}

	/** {@inheritDoc} */
	@Override
	public String getConcreteValue() {
		return concreteValue;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return intVar.toString();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof IntToStringCast) {
			IntToStringCast other = (IntToStringCast) obj;
			return this.intVar.equals(other.intVar);
		}

		return false;
	}

	protected int size = 0;

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		if (size == 0) {
			size = 1 + intVar.getSize();
		}
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public Expression<Long> getArgument() {
		return intVar;
	}
}
