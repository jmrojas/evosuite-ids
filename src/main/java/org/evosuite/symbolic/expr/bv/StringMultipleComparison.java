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
import org.evosuite.symbolic.expr.AbstractExpression;
import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.MultipleExpression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * StringMultipleComparison class.
 * </p>
 * 
 * @author krusev
 */
public final class StringMultipleComparison extends AbstractExpression<Long>
		implements IntegerValue, MultipleExpression<String> {

	private static final long serialVersionUID = -3844726361666119758L;

	protected static Logger log = LoggerFactory
			.getLogger(StringMultipleComparison.class);

	private final Operator op;

	private final Expression<String> left;

	private final Expression<?> right;

	private final ArrayList<Expression<?>> other_v;

	/**
	 * <p>
	 * Constructor for StringMultipleComparison.
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
	 *            a {@link java.lang.Long} object.
	 */
	public StringMultipleComparison(Expression<String> _left, Operator _op,
			Expression<?> _right, ArrayList<Expression<?>> _other, Long con) {
		super(con, 1 + _left.getSize() + _right.getSize() + countSizes(_other),
				_left.containsSymbolicVariable()
						|| _right.containsSymbolicVariable()
						|| containsSymbolicVariable(_other));
		this.op = _op;
		this.left = _left;
		this.right = _right;
		this.other_v = _other;

		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH)
			throw new ConstraintTooLongException();
	}

	private static int countSizes(ArrayList<Expression<?>> list) {
		int retVal = 0;
		for (Expression<?> e : list) {
			retVal += e.getSize();
		}
		return retVal;
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
		if (obj instanceof StringMultipleComparison) {
			StringMultipleComparison other = (StringMultipleComparison) obj;
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
		String first = left.execute();
		String second = (String) right.execute();

		switch (op) {
		case STARTSWITH:
			long start = (Long) other_v.get(0).execute();

			return first.startsWith(second, (int) start) ? 1L : 0L;

		case REGIONMATCHES:
			long frstStart = (Long) other_v.get(0).execute();
			long secStart = (Long) other_v.get(1).execute();
			long length = (Long) other_v.get(2).execute();
			long ignoreCase = (Long) other_v.get(3).execute();

			return first.regionMatches(ignoreCase != 0, (int) frstStart,
					second, (int) secStart, (int) length) ? 1L : 0L;
		default:
			log.warn("StringMultipleComparison: unimplemented operator!");
			return null;
		}
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
