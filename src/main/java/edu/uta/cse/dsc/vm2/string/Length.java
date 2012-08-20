package edu.uta.cse.dsc.vm2.string;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.StringToIntCast;
import org.evosuite.symbolic.expr.StringUnaryExpression;

import edu.uta.cse.dsc.vm2.Operand;
import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public final class Length extends StringFunction {

	private static final String LENGTH = "length";

	public Length(SymbolicEnvironment env) {
		super(env, LENGTH, Types.TO_INT_DESCRIPTOR);
	}

	@Override
	protected void INVOKEVIRTUAL_String(String receiver) {
		Operand operand = env.topFrame().operandStack.peekOperand();
		this.stringReceiverExpr = operandToStringExpression(operand);
	}

	@Override
	public void CALL_RESULT(int res) {
		if (stringReceiverExpr.containsSymbolicVariable()) {
			StringUnaryExpression strUnExpr = new StringUnaryExpression(
					stringReceiverExpr, Operator.LENGTH, Integer.toString(res));
			StringToIntCast castExpr = new StringToIntCast(strUnExpr,
					(long) res);
			replaceTopBv32(castExpr);
		} else {
			// do nothing
		}
	}
}
