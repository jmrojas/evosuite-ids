package org.evosuite.symbolic.vm.string;

import java.util.Iterator;
import java.util.regex.PatternSyntaxException;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.bv.StringComparison;
import org.evosuite.symbolic.expr.str.StringConstant;
import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.vm.ExpressionFactory;
import org.evosuite.symbolic.vm.Operand;
import org.evosuite.symbolic.vm.SymbolicEnvironment;

public final class Matches extends StringFunction {

	private static final String MATCHES = "matches";
	private StringValue regExStrExpr;

	public Matches(SymbolicEnvironment env) {
		super(env, MATCHES, Types.STR_TO_BOOL_DESCRIPTOR);
	}

	@Override
	protected void INVOKEVIRTUAL_String(String receiver) {
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		it.next(); // discard now
		this.stringReceiverExpr = getStringExpression(it.next(), receiver);
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, Object value) {
		String string_value = (String) value;
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		this.regExStrExpr = getStringExpression(it.next(), string_value);
	}

	@Override
	public void CALL_RESULT(boolean res) {
		if (stringReceiverExpr.containsSymbolicVariable()) {
			int conV = res ? 1 : 0;

			String regEx = (String) regExStrExpr.getConcreteValue();
			StringConstant strRegEx = ExpressionFactory
					.buildNewStringConstant(regEx);

			StringComparison strBExpr = new StringComparison(strRegEx,
					Operator.PATTERNMATCHES, stringReceiverExpr, (long) conV);

			this.replaceTopBv32(strBExpr);
		}

	}
}