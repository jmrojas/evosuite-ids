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
package org.evosuite.symbolic.expr.bv;

import gnu.trove.set.hash.THashSet;

import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.evosuite.symbolic.DSEStats;
import org.evosuite.symbolic.expr.AbstractExpression;
import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.UnaryExpression;
import org.evosuite.symbolic.expr.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RealUnaryToIntegerExpression extends AbstractExpression<Long>
		implements IntegerValue, UnaryExpression<Double> {

	private static final long serialVersionUID = 9086637495150131445L;

	protected static Logger log = LoggerFactory
			.getLogger(RealUnaryToIntegerExpression.class);

	private final Operator op;

	private final Expression<Double> expr;

	/**
	 * <p>
	 * Constructor for RealUnaryExpression.
	 * </p>
	 * 
	 * @param e
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 * @param op2
	 *            a {@link org.evosuite.symbolic.expr.Operator} object.
	 * @param con
	 *            a {@link java.lang.Double} object.
	 */
	public RealUnaryToIntegerExpression(Expression<Double> e, Operator op2, Long con) {
		super(con, 1 + e.getSize(), e.containsSymbolicVariable());
		this.expr = e;
		this.op = op2;

		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH) {
			DSEStats.reportConstraintTooLong(getSize());
			throw new ConstraintTooLongException(getSize());
		}
	}

	/** {@inheritDoc} */
	@Override
	public Expression<Double> getOperand() {
		return expr;
	}

	/** {@inheritDoc} */
	@Override
	public Operator getOperator() {
		return op;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return op.toString() + "(" + expr + ")";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RealUnaryToIntegerExpression) {
			RealUnaryToIntegerExpression v = (RealUnaryToIntegerExpression) obj;
			return this.op.equals(v.op) && this.getSize() == v.getSize()
					&& this.expr.equals(v.expr);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Long execute() {
		double leftVal = (Double) expr.execute();

		switch (op) {

		case ROUND:
			return Math.round(leftVal);
		case GETEXPONENT:
			return (long) Math.getExponent(leftVal);

		default:
			log.warn("IntegerUnaryExpression: unimplemented operator: " + op);
			return null;
		}
	}

	@Override
	public Set<Variable<?>> getVariables() {
		Set<Variable<?>> variables = new THashSet<Variable<?>>();
		variables.addAll(this.expr.getVariables());
		return variables;
	}

}
