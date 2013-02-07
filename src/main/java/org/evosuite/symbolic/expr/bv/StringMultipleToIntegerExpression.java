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
package org.evosuite.symbolic.expr.bv;

import gnu.trove.set.hash.THashSet;

import java.util.ArrayList;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.evosuite.symbolic.DSEStats;
import org.evosuite.symbolic.expr.AbstractExpression;
import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.MultipleExpression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * StringMultipleExpression class.
 * </p>
 * 
 * @author krusev
 */
public final class StringMultipleToIntegerExpression extends
		AbstractExpression<Long> implements IntegerValue,
		MultipleExpression<String> {

	private static final long serialVersionUID = 7172041118401792672L;

	private final ArrayList<Expression<?>> other_v;

	private final Operator op;

	private final Expression<String> left;

	private final Expression<?> right;

	protected static Logger log = LoggerFactory
			.getLogger(StringMultipleToIntegerExpression.class);

	/**
	 * <p>
	 * Constructor for StringMultipleExpression.
	 * </p>
	 * 
	 * @param _left
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 * @param _op
	 *            a {@link org.evosuite.symbolic.expr.Operator} object.
	 * @param _right
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 * @param _other
	 *            a {@link java.util.ArrayList} object.
	 * @param con
	 *            a {@link java.lang.String} object.
	 */
	public StringMultipleToIntegerExpression(Expression<String> _left,
			Operator _op, Expression<?> _right,
			ArrayList<Expression<?>> _other, Long con) {
		super(con, 1 + _left.getSize() + _right.getSize() + countSizes(_other),
				_left.containsSymbolicVariable()
						|| _right.containsSymbolicVariable()
						|| containsSymbolicVariable(_other));

		this.op = _op;
		this.left = _left;
		this.right = _right;
		this.other_v = _other;

		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH) {
			DSEStats.reportConstraintTooLong(getSize());
			throw new ConstraintTooLongException(getSize());
		}
	}

	/**
	 * <p>
	 * getOther
	 * </p>
	 * 
	 * @return the other
	 */
	public ArrayList<Expression<?>> getOther() {
		return other_v;
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
		String str_other_v = "";
		for (int i = 0; i < this.other_v.size(); i++) {
			str_other_v += " " + this.other_v.get(i).toString();
		}

		return "(" + left + op.toString() + (right == null ? "" : right)
				+ str_other_v + ")";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof StringMultipleToIntegerExpression) {
			StringMultipleToIntegerExpression other = (StringMultipleToIntegerExpression) obj;

			return this.op.equals(other.op) && this.left.equals(other.left)
					&& this.right.equals(other.right)
					&& this.other_v.equals(other.other_v);
		}

		return false;
	}
	
	@Override
	public int hashCode() {
		return this.op.hashCode() + this.left.hashCode()
				+ this.right.hashCode() + this.other_v.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public Long execute() {
		String first = (String) left.execute();
		long secLong, thrdLong;
		String secStr;

		switch (op) {

		// returns int
		case INDEXOFCI:
			secLong = (Long) right.execute();
			thrdLong = (Long) other_v.get(0).execute();
			return (long) first.indexOf((int) secLong, (int) thrdLong);
		case INDEXOFSI:
			secStr = (String) right.execute();
			thrdLong = (Long) other_v.get(0).execute();
			return (long) first.indexOf(secStr, (int) thrdLong);
		case LASTINDEXOFCI:
			secLong = (Long) right.execute();
			thrdLong = (Long) other_v.get(0).execute();
			return (long) first.lastIndexOf((int) secLong, (int) thrdLong);
		case LASTINDEXOFSI:
			secStr = (String) right.execute();
			thrdLong = (Long) other_v.get(0).execute();
			return (long) first.lastIndexOf(secStr, (int) thrdLong);

		default:
			log.warn("StringMultipleToIntegerExpression: unimplemented operator: "
					+ op);
			return null;
		}
	}

	private static boolean containsSymbolicVariable(
			ArrayList<Expression<?>> list) {

		for (Expression<?> e : list) {
			if (e.containsSymbolicVariable()) {
				return true;
			}
		}

		return false;
	}

	private static int countSizes(ArrayList<Expression<?>> list) {
		int retVal = 0;
		for (Expression<?> e : list) {
			retVal += e.getSize();
		}
		return retVal;
	}
	
	@Override
	public Set<Variable<?>> getVariables() {
		Set<Variable<?>> variables = new THashSet<Variable<?>>();
		variables.addAll(this.left.getVariables());
		variables.addAll(this.right.getVariables());
		for (Expression<?> other_e : this.other_v) {
			variables.addAll(other_e.getVariables());
		}
		return variables;
	}


}
