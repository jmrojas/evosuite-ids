package edu.uta.cse.dsc.vm2.string;

import org.evosuite.symbolic.expr.StringConstant;

import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public final class ToString extends StringVirtualFunction {

	private static final String FUNCTION_NAME = "toString";

	public ToString(SymbolicEnvironment env) {
		super(env, FUNCTION_NAME, StringFunction.TO_STR_DESCRIPTOR);
	}

	@Override
	protected void INVOKEVIRTUAL(String receiver) {
		this.stringReceiverExpr = operandToStringExpression(env.topFrame().operandStack
				.peekOperand());
	}

	@Override
	public void CALL_RESULT(Object res) {
		if (stringReceiverExpr.containsSymbolicVariable()
				&& !(stringReceiverExpr instanceof StringConstant)) {
			replaceStrRefTop(stringReceiverExpr);
		} else {
			// do nothing
		}
	}
}
