/**
 * 
 */
package de.unisb.cs.st.evosuite.symbolic.expr;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.symbolic.ConstraintTooLongException;

/**
 * @author krusev
 *
 */
public class StringConstraint extends Constraint<String> {

	private static final long serialVersionUID = 5871101668137509725L;

	public StringConstraint(StringExpression left, Comparator cmp, StringExpression right) {
		super();
		this.left = left;
		this.cmp = cmp;
		this.right = right;
		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH)
			throw new ConstraintTooLongException();
	}

	protected Comparator cmp;

	protected Expression<String> left;
	protected Expression<String> right;

	@Override
	public Comparator getComparator() {
		return cmp;
	}

	@Override
	public Expression<String> getLeftOperand() {
		return left;
	}

	@Override
	public Expression<String> getRightOperand() {
		return right;
	}

	@Override
	public String toString() {
		return left + cmp.toString() + right;
	}
}
