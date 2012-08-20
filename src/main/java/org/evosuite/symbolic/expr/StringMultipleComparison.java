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
package org.evosuite.symbolic.expr;

import java.util.ArrayList;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.evosuite.symbolic.search.DistanceEstimator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * StringMultipleComparison class.
 * </p>
 * 
 * @author krusev
 */
public class StringMultipleComparison extends StringComparison implements
        BinaryExpression<String> {

	private static final long serialVersionUID = -3844726361666119758L;

	protected static Logger log = LoggerFactory.getLogger(StringMultipleComparison.class);

	protected ArrayList<Expression<?>> other_v;

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
		super(_left, _op, _right, con);
		this.other_v = _other;

		if (_left.containsSymbolicVariable()) {
			this.containsSymbolicVariable = true;
		} else if (_right.containsSymbolicVariable()) {
			this.containsSymbolicVariable = true;
		} else {
			for (Expression<?> e : _other) {
				if (e.containsSymbolicVariable()) {
					this.containsSymbolicVariable = true;
					break;
				}
			}
		}

		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH)
			throw new ConstraintTooLongException();
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
	public Long getConcreteValue() {
		return conVal;
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

		return "(" + left + op.toString() + (right == null ? "" : right) + str_other_v
		        + ")";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof StringMultipleComparison) {
			StringMultipleComparison other = (StringMultipleComparison) obj;

			boolean other_v_eq = true;

			if (other.other_v.size() == this.other_v.size()) {
				for (int i = 0; i < other.other_v.size(); i++) {
					if (!(other.other_v.get(i).equals(this.other_v.get(i)))) {
						other_v_eq = false;
					}
				}
			} else {
				other_v_eq = false;
			}

			return this.op.equals(other.op) && this.getSize() == other.getSize()
			        && this.left.equals(other.left) && this.right.equals(other.right)
			        && other_v_eq;
		}

		return false;
	}

	protected int size = 0;

	// @Override
	// public int getSize() {
	// if (size == 0) {
	// size = 1 + left.getSize() + right.getSize();
	// }
	// return size;
	// }

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		if (size == 0 && other_v != null) {
			int other_size = 0;
			for (int i = 0; i < other_v.size(); i++) {
				other_size += other_v.get(i).getSize();
			}
			size = 1 + left.getSize() + right.getSize() + other_size;
		}
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public Long execute() {
		try {
			String first = (String) left.execute();
			String second = (String) right.execute();

			switch (op) {
			case STARTSWITH:
				long start = (Long) other_v.get(0).execute();

				return (long) DistanceEstimator.StrStartsWith(first, second, (int) start);
			case REGIONMATCHES:
				long frstStart = (Long) other_v.get(0).execute();
				long secStart = (Long) other_v.get(1).execute();
				long length = (Long) other_v.get(2).execute();
				long ignoreCase = (Long) other_v.get(3).execute();

				return (long) DistanceEstimator.StrRegionMatches(first, (int) frstStart,
				                                                 second, (int) secStart,
				                                                 (int) length,
				                                                 ignoreCase != 0);
			default:
				log.warn("StringMultipleComparison: unimplemented operator!");
				return null;
			}
		} catch (Exception e) {
			return Long.MAX_VALUE;
		}
	}

}
