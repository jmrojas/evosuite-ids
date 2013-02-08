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
import org.evosuite.symbolic.DSEStats;
import org.evosuite.symbolic.expr.bv.IntegerConstant;
import org.evosuite.symbolic.expr.bv.IntegerUnaryExpression;
import org.evosuite.symbolic.expr.bv.StringBinaryToIntegerExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IntegerConstraint extends Constraint<Long> {

	static Logger log = LoggerFactory.getLogger(IntegerConstraint.class);

	private static final long serialVersionUID = 5345957507046422507L;

	/**
	 * <p>
	 * Constructor for IntegerConstraint.
	 * </p>
	 * 
	 * @param left
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 * @param cmp
	 *            a {@link org.evosuite.symbolic.expr.Comparator} object.
	 * @param right
	 *            a {@link org.evosuite.symbolic.expr.Expression} object.
	 */
	public IntegerConstraint(Expression<Long> left, Comparator cmp,
			Expression<Long> right) {
		super();
		this.left = left;
		this.cmp = cmp;
		this.right = right;
		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH) {
			DSEStats.reportConstraintTooLong(getSize());
			throw new ConstraintTooLongException(getSize());
		}
	}

	private final Expression<Long> left;
	private final Comparator cmp;
	private final Expression<Long> right;

	/** {@inheritDoc} */
	@Override
	public Comparator getComparator() {
		return cmp;
	}

	/** {@inheritDoc} */
	@Override
	public Expression<?> getLeftOperand() {
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
		return left + cmp.toString() + right;
	}

	@Override
	public Constraint<Long> negate() {
		return new IntegerConstraint(this.left, this.cmp.not(), this.right);
	}

	public long getIntegerDist() {

		long left = (Long) this.getLeftOperand().execute();
		long right = (Long) this.getRightOperand().execute();

		// special integer constraint: string indexOf char != -1
		if (this.getLeftOperand() instanceof StringBinaryToIntegerExpression
				&& this.getComparator() == Comparator.NE
				&& this.getRightOperand() instanceof IntegerConstant) {
			IntegerConstant right_constant = (IntegerConstant) this
					.getRightOperand();
			StringBinaryToIntegerExpression left_string_expr = (StringBinaryToIntegerExpression) this
					.getLeftOperand();
			if (left_string_expr.getOperator() == Operator.INDEXOFC
					&& right_constant.getConcreteValue() == -1L) {

				Expression<?> theSymbolicString = left_string_expr.getLeftOperand();
				Expression<?> theSymbolicChar = left_string_expr.getRightOperand();

				// check theString.lenght>0
				String theConcreteString = (String) theSymbolicString.execute();
				if (theConcreteString.length()==0) {
					// if the string is empty, then the branch distance is maximum since
					// no char can be modified to satisfy the constraint
					return Long.MAX_VALUE; 
				} else {
					char theConcreteChar = (char) ((Long) theSymbolicChar.execute()).longValue();
					char[] charArray = theConcreteString.toCharArray();
					int min_distance_to_char = Integer.MAX_VALUE;
					for (char c : charArray) {
						if (Math.abs(c - theConcreteChar) < min_distance_to_char) {
							min_distance_to_char= Math.abs(c - theConcreteChar);
						}

					}
					return min_distance_to_char;
				}
				
				
			}

		}

		// special case: regex
		if (this.getLeftOperand() instanceof IntegerUnaryExpression) {
			if (((IntegerUnaryExpression) this.getLeftOperand()).getOperator() == Operator.ISDIGIT) {
				long left_operand = ((IntegerUnaryExpression) this
						.getLeftOperand()).getOperand().execute();
				char theChar = (char) left_operand;
				if ((this.getComparator() == Comparator.EQ && right == 1L)
						|| (this.getComparator() == Comparator.NE && right == 0L)) {
					if (theChar < '0')
						return '0' - theChar;
					else if (theChar > '9')
						return theChar - '9';
					else
						return 0;
				} else if ((this.getComparator() == Comparator.EQ && right == 0L)
						|| (this.getComparator() == Comparator.NE && right == 1L)) {
					if (theChar < '0' || theChar > '9')
						return 0;
					else
						return Math.min(Math.abs('9' - theChar),
								Math.abs(theChar - '0'));
				}

			} else if (((IntegerUnaryExpression) this.getLeftOperand())
					.getOperator() == Operator.ISLETTER) {
				long left_operand = ((IntegerUnaryExpression) this
						.getLeftOperand()).getOperand().execute();
				char theChar = (char) left_operand;
				if ((this.getComparator() == Comparator.EQ && right == 1L)
						|| (this.getComparator() == Comparator.NE && right == 0L)) {
					if (theChar < 'A')
						return 'A' - theChar;
					else if (theChar > 'z')
						return theChar - 'z';
					else
						return 0;
				} else if ((this.getComparator() == Comparator.EQ && right == 0L)
						|| (this.getComparator() == Comparator.NE && right == 1L)) {
					if (theChar < 'A' || theChar > 'z')
						return 0;
					else
						return Math.min(Math.abs('z' - theChar),
								Math.abs(theChar - 'A'));
				}
			}
		}

		Comparator cmpr = this.getComparator();
		log.debug("Calculating distance for " + left + " " + cmpr + " " + right);

		switch (cmpr) {

		case EQ:

			return Math.abs(left - right);
		case NE:

			return (left - right) != 0 ? 0 : 1;
		case LT:

			return left - right < 0 ? 0 : left - right + 1;
		case LE:

			return left - right <= 0 ? 0 : left - right;
		case GT:

			return left - right > 0 ? 0 : right - left + 1;
		case GE:

			return left - right >= 0 ? 0 : right - left;

		default:
			log.warn("getIntegerDist: unimplemented comparator");
			return Long.MAX_VALUE;
		}

	}
}
