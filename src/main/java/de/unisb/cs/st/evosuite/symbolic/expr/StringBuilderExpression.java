/**
 * 
 */
package de.unisb.cs.st.evosuite.symbolic.expr;

/**
 * @author krusev
 *
 */
public class StringBuilderExpression extends StringExpression {


	private static final long serialVersionUID = 5944852577642617563L;

	protected boolean undef_func;
	protected Expression<String> expr;




	public StringBuilderExpression(Expression<String> _expr) {
		this.expr = _expr;
		this.undef_func = false;
	}


	public Expression<String> getExpr() {
		return expr;
	}

	public void setExpr(Expression<String> _expr) {
		expr = _expr;
	}

	
	public boolean has_undef_func() {
		return undef_func;
	}


	public void set_undef_func() {
		undef_func = true;
	}

	
	@Override
	public String toString() {
		return expr.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof StringBuilderExpression) {
			StringBuilderExpression other = (StringBuilderExpression) obj;
			return this.expr.equals(other.expr) && this.getSize() == other.getSize();
		}

		return false;
	}

	protected int size = 0;

	@Override
	public int getSize() {
		//TODO fix this
		return -1;
//		if (size == 0) {
//			size = 1 + getLeftOperand().getSize() + getRightOperand().getSize();
//		}
//		return size;
	}


	@Override
	public String getConcreteValue() {
		
		return (String) expr.getConcreteValue();
	}


	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return (String) expr.execute();
	}

}
