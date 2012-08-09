package edu.uta.cse.dsc.vm2.string;

import java.util.Iterator;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.StringComparison;
import org.evosuite.symbolic.expr.StringExpression;
import org.evosuite.symbolic.expr.StringToIntCast;

import edu.uta.cse.dsc.vm2.Operand;
import edu.uta.cse.dsc.vm2.ReferenceOperand;
import edu.uta.cse.dsc.vm2.StringReferenceOperand;
import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public final class Contains extends StringFunction {

	private StringExpression strExpr;
	private static final String FUNCTION_NAME = "contains";

	public Contains(SymbolicEnvironment env) {
		super(env, FUNCTION_NAME, StringFunction.CHARSEQ_TO_BOOL_DESCRIPTOR);
	}

	@Override
	protected void INVOKEVIRTUAL(String receiver) {
		Iterator<Operand> it = env.topFrame().operandStack.iterator();

		ReferenceOperand strRefOperand = (ReferenceOperand) it.next();
		if (strRefOperand.getReference() == null) {
			throwException(new NullPointerException());
			return;
		}

		if (strRefOperand instanceof StringReferenceOperand) {
			this.strExpr = ((StringReferenceOperand) strRefOperand)
					.getStringExpression();
		} else {
			this.strExpr = null;
		}

		this.stringReceiverExpr = stringRef(it.next());
	}

	@Override
	public void CALL_RESULT(boolean res) {
		if (strExpr != null) {
			if (stringReceiverExpr.containsSymbolicVariable()
					|| strExpr.containsSymbolicVariable()) {

				int concrete_value = res ? 1 : 0;

				StringComparison strComp = new StringComparison(
						stringReceiverExpr, Operator.CONTAINS, strExpr,
						(long) concrete_value);
				StringToIntCast castExpr = new StringToIntCast(strComp,
						(long) concrete_value);

				replaceBv32Top(castExpr);
			}
		}
	}
}