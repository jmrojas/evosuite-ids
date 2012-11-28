package org.evosuite.symbolic.vm.string;

import java.util.Iterator;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.bv.StringBinaryToIntegerExpression;
import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.vm.Operand;
import org.evosuite.symbolic.vm.SymbolicEnvironment;

public final class CompareToIgnoreCase extends StringFunction {

	private static final String COMPARE_TO_IGNORE_CASE = "compareToIgnoreCase";
	private StringValue strExpr;

	public CompareToIgnoreCase(SymbolicEnvironment env) {
		super(env, COMPARE_TO_IGNORE_CASE, Types.STR_TO_INT_DESCRIPTOR);
	}

	@Override
	protected void INVOKEVIRTUAL_String(String receiver) {
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		it.next(); // discard (for now)
		this.stringReceiverExpr = getStringExpression(it.next(), receiver);
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, Object value) {
		String string_value = (String) value;
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		this.strExpr = getStringExpression(it.next(), string_value);
	}
	
	@Override
	public void CALL_RESULT(int res) {
		if (stringReceiverExpr.containsSymbolicVariable()
				|| strExpr.containsSymbolicVariable()) {
			StringBinaryToIntegerExpression strBExpr = new StringBinaryToIntegerExpression(
					stringReceiverExpr, Operator.COMPARETOIGNORECASE, strExpr,
					(long) res);

			this.replaceTopBv32(strBExpr);
		}

	}


}
