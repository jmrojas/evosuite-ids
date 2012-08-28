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

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * StringBinaryExpression class.
 * </p>
 * 
 * @author krusev
 */
public class StringBinaryExpression extends StringExpression implements
		BinaryExpression<String> {

	private static final long serialVersionUID = -986689442489666986L;

	protected static Logger log = LoggerFactory
			.getLogger(StringBinaryExpression.class);

	protected String concretValue;

	protected final Operator op;

	protected final Expression<String> left;
	protected final Expression<?> right;

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
		this.concretValue = con;
		this.left = left2;
		this.right = right2;
		this.op = op2;
		this.containsSymbolicVariable = this.left.containsSymbolicVariable()
				|| this.right.containsSymbolicVariable();
		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH)
			throw new ConstraintTooLongException();
	}

	/** {@inheritDoc} */
	@Override
	public String getConcreteValue() {
		return concretValue;
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
		if (obj instanceof StringBinaryExpression) {
			StringBinaryExpression other = (StringBinaryExpression) obj;
			return this.op.equals(other.op)
					&& this.getSize() == other.getSize()
					&& this.left.equals(other.left)
					&& this.right.equals(other.right);
		}

		return false;
	}

	protected int size = 0;

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		if (size == 0) {
			size = 1 + left.getSize() + right.getSize();
		}
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public String execute() {
		String first = (String) left.execute();
		Object second = right.execute();
		long ch;

		switch (op) {

		case COMPARETO:
			return Integer.toString(first.compareTo((String) second));
		case COMPARETOIGNORECASE:
			return Integer.toString(first.compareToIgnoreCase((String) second));
		case CONCAT:
			return first.concat((String) second);
		case INDEXOFC:
			ch = ExpressionHelper.getLongResult(right);
			return Integer.toString(first.indexOf((char) ch));
		case INDEXOFS:
			return Integer.toString(first.indexOf((String) second));
		case LASTINDEXOFC:
			ch = ExpressionHelper.getLongResult(right);
			return Integer.toString(first.lastIndexOf((char) ch));
		case LASTINDEXOFS:
			return Integer.toString(first.lastIndexOf((String) second));
		case APPEND:
			return first + ((String) second);
		case CHARAT:
			int indx = (int) ExpressionHelper.getLongResult(right);
			return Integer.toString(first.charAt(indx));
		default:
			log.warn("StringBinaryExpression: unimplemented operator! Operator"
					+ op.toString());
			return null;
		}

	}

}
