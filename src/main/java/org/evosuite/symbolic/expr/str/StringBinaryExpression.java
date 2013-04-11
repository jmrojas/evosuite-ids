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
package org.evosuite.symbolic.expr.str;

import gnu.trove.set.hash.THashSet;

import java.util.HashSet;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.evosuite.symbolic.DSEStats;
import org.evosuite.symbolic.expr.AbstractExpression;
import org.evosuite.symbolic.expr.BinaryExpression;
import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * StringBinaryExpression class.
 * </p>
 * 
 * @author krusev
 */
public final class StringBinaryExpression extends AbstractExpression<String> implements
        StringValue, BinaryExpression<String> {

	private static final long serialVersionUID = -986689442489666986L;

	protected static final Logger log = LoggerFactory.getLogger(StringBinaryExpression.class);

	private final Expression<String> left;
	private final Operator op;
	private final Expression<?> right;

	/**
	 * <p>
	 * Constructor for StringBinaryExpression.
	 * </p>
	 * 
	 * @param left2
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 * @param op2
	 *            a {@link org.evosuite.symbolic.expr.Operator} object.
	 * @param right2
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 * @param con
	 *            a {@link java.lang.String} object.
	 */
	public StringBinaryExpression(Expression<String> left2, Operator op2,
	        Expression<?> right2, String con) {
		super(con, 1 + left2.getSize() + right2.getSize(),
		        left2.containsSymbolicVariable() || right2.containsSymbolicVariable());
		this.left = left2;
		this.op = op2;
		this.right = right2;

		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH) {
			DSEStats.reportConstraintTooLong(getSize());
			throw new ConstraintTooLongException(getSize());
		}
	}

	/** {@inheritDoc} */
	@Override
	public Operator getOperator() {
		return op;
	}

	/** {@inheritDoc} */
	@Override
	public Expression<String> getLeftOperand() {
		return left;
	}

	/** {@inheritDoc} */
	@Override
	public Expression<?> getRightOperand() {
		return right;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "(" + left + op.toString() + right + ")";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof StringBinaryExpression) {
			StringBinaryExpression other = (StringBinaryExpression) obj;
			return this.op.equals(other.op) && this.left.equals(other.left)
			        && this.right.equals(other.right);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.left.hashCode() + this.op.hashCode() + this.right.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String execute() {
		String first = left.execute();
		Object second = right.execute();

		switch (op) {

		// returns String
		case CONCAT: {
			String string = (String) second;
			return first.concat(string);
		}
		case APPEND_BOOLEAN: {
			Long sndLong = (Long) second;
			boolean booleabValue = sndLong == 0 ? false : true;
			return first + booleabValue;
		}
		case APPEND_CHAR: {
			Long sndLong = (Long) second;
			char charValue = (char) sndLong.longValue();
			return first + charValue;
		}
		case APPEND_INTEGER: {
			Long sndLong = (Long) second;
			return first + sndLong;
		}
		case APPEND_REAL: {
			Double sndLong = (Double) second;
			return first + sndLong;
		}
		case APPEND_STRING: {
			String string = (String) second;
			return first + (string);
		}

		default:
			log.warn("StringBinaryExpression: unimplemented operator! Operator"
			        + op.toString());
			return null;
		}

	}

	@Override
	public Set<Variable<?>> getVariables() {
		Set<Variable<?>> variables = new THashSet<Variable<?>>();
		variables.addAll(this.left.getVariables());
		variables.addAll(this.right.getVariables());
		return variables;
	}

	@Override
	public Set<Object> getConstants() {
		Set<Object> result = new HashSet<Object>();
		result.addAll(this.left.getConstants());
		result.addAll(this.right.getConstants());
		return result;
	}
}
