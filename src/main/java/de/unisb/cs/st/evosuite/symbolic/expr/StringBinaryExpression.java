/**
 * 
 */
package de.unisb.cs.st.evosuite.symbolic.expr;

import gov.nasa.jpf.JPF;

import java.util.logging.Logger;

/**
 * @author krusev
 *
 */
public class StringBinaryExpression extends StringExpression implements
BinaryExpression<String>{

	private static final long serialVersionUID = -986689442489666986L;

	static Logger log = JPF.getLogger("de.unisb.cs.st.evosuite.symbolic.expr.StringBinaryExpression");
	
	protected String concretValue;

	protected Operator op;

	protected Expression<String> left;
	protected Expression<?> right;

	public StringBinaryExpression(Expression<String> left2, Operator op2,
	        Expression<?> right2, String con) {
		this.concretValue = con;
		this.left = left2;
		this.right = right2;
		this.op = op2;
	}

	@Override
	public String getConcreteValue() {
		return concretValue;
	}

	@Override
	public Operator getOperator() {
		return op;
	}

	@Override
	public Expression<String> getLeftOperand() {
		return left;
	}

	@Override
	public Expression<?> getRightOperand() {
		return right;
	}

	@Override
	public String toString() {
		return "(" + left + op.toString() + right + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof StringBinaryExpression) {
			StringBinaryExpression other = (StringBinaryExpression) obj;
			return this.op.equals(other.op) 
//					&& this.getSize() == other.getSize()
			        && this.left.equals(other.left) && this.right.equals(other.right);
		}

		return false;
	}

	protected int size = 0;

//	@Override
//	public int getSize() {
//		//TODO fix this
//		return -1;
////		if (size == 0) {
////			size = 1 + getLeftOperand().getSize() + getRightOperand().getSize();
////		}
////		return size;
//	}

	@Override
	public String execute() {
		String first = (String)left.execute();
		Object second = right.execute();
		
		switch (op) {
		
		case COMPARETO:
			return Integer.toString(first.compareTo((String)second));
		case COMPARETOIGNORECASE:
			return Integer.toString(first.compareToIgnoreCase((String)second));
		case CONCAT:
			return first.concat((String)second);
		case INDEXOFC:
			long ch = ExpressionHelper.getLongResult(right);
			return Integer.toString(first.indexOf((char)ch));
		case INDEXOFS:
			return Integer.toString(first.indexOf((String)second));
		case APPEND: 
			return first + ((String) second);
		case CHARAT:
			//TODO handle exception here
			int indx = (int) ExpressionHelper.getLongResult(right);
			return Integer.toString(first.charAt(indx));
		default:
			log.warning("StringBinaryExpression: unimplemented operator!");
			return null;
		}
		
		
		
	}

}
