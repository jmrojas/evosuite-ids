package edu.uta.cse.dsc.vm2.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.IntegerExpression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.StringBinaryExpression;
import org.evosuite.symbolic.expr.StringExpression;
import org.evosuite.symbolic.expr.StringMultipleExpression;
import org.evosuite.symbolic.expr.StringToIntCast;

import edu.uta.cse.dsc.vm2.Operand;
import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public abstract class LastIndexOf extends StringFunction {

	private static final String FUNCTION_NAME = "lastIndexOf";

	public LastIndexOf(SymbolicEnvironment env, String desc) {
		super(env, FUNCTION_NAME, desc);
	}

	public static class LastIndexOf_C extends LastIndexOf {

		private IntegerExpression charExpr;

		public LastIndexOf_C(SymbolicEnvironment env) {
			super(env, StringFunction.INT_TO_INT_DESCRIPTOR);
		}

		@Override
		protected void INVOKEVIRTUAL(String receiver) {
			Iterator<Operand> it = env.topFrame().operandStack.iterator();
			this.charExpr = bv32(it.next());
			this.stringReceiverExpr = operandToStringRef(it.next());
		}

		@Override
		public void CALL_RESULT(int res) {
			if (stringReceiverExpr.containsSymbolicVariable()
					|| charExpr.containsSymbolicVariable()) {
				StringBinaryExpression strBExpr = new StringBinaryExpression(
						stringReceiverExpr, Operator.LASTINDEXOFC, charExpr,
						Integer.toString(res));
				StringToIntCast castExpr = new StringToIntCast(strBExpr,
						(long) res);
				this.replaceBv32Top(castExpr);
			} else {
				// do nothing (concrete value only)
			}

		}
	}

	public static class LastIndexOf_CI extends LastIndexOf {

		private IntegerExpression charExpr;
		private IntegerExpression fromIndexExpr;

		public LastIndexOf_CI(SymbolicEnvironment env) {
			super(env, StringFunction.INT_INT_TO_INT_DESCRIPTOR);
		}

		@Override
		protected void INVOKEVIRTUAL(String receiver) {
			Iterator<Operand> it = env.topFrame().operandStack.iterator();
			this.fromIndexExpr = bv32(it.next());
			this.charExpr = bv32(it.next());
			this.stringReceiverExpr = operandToStringRef(it.next());
		}

		@Override
		public void CALL_RESULT(int res) {
			if (stringReceiverExpr.containsSymbolicVariable()
					|| charExpr.containsSymbolicVariable()
					|| fromIndexExpr.containsSymbolicVariable()) {

				StringMultipleExpression strTExpr = new StringMultipleExpression(
						stringReceiverExpr, Operator.LASTINDEXOFCI, charExpr,
						new ArrayList<Expression<?>>(Collections
								.singletonList(fromIndexExpr)),
						Integer.toString(res));

				StringToIntCast castExpr = new StringToIntCast(strTExpr,
						(long) res);
				this.replaceBv32Top(castExpr);
			} else {
				// do nothing (concrete value only)
			}

		}
	}

	public static class LastIndexOf_S extends LastIndexOf {

		private StringExpression strExpr;

		public LastIndexOf_S(SymbolicEnvironment env) {
			super(env, StringFunction.STR_TO_INT_DESCRIPTOR);
		}

		@Override
		protected void INVOKEVIRTUAL(String receiver) {
			Iterator<Operand> it = env.topFrame().operandStack.iterator();

			this.strExpr = operandToStringRef(it.next());
			this.stringReceiverExpr = operandToStringRef(it.next());

		}

		@Override
		public void CALL_RESULT(int res) {
			if (stringReceiverExpr.containsSymbolicVariable()
					|| strExpr.containsSymbolicVariable()) {
				StringBinaryExpression strBExpr = new StringBinaryExpression(
						stringReceiverExpr, Operator.LASTINDEXOFS, strExpr,
						Integer.toString(res));
				StringToIntCast castExpr = new StringToIntCast(strBExpr,
						(long) res);
				this.replaceBv32Top(castExpr);
			} else {
				// do nothing (concrete value only)
			}

		}
	}

	public static class LastIndexOf_SI extends LastIndexOf {

		private StringExpression strExpr;
		private IntegerExpression fromIndexExpr;

		public LastIndexOf_SI(SymbolicEnvironment env) {
			super(env, StringFunction.STR_INT_TO_INT_DESCRIPTOR);
		}

		@Override
		protected void INVOKEVIRTUAL(String receiver) {
			Iterator<Operand> it = env.topFrame().operandStack.iterator();
			this.fromIndexExpr = bv32(it.next());
			this.strExpr = operandToStringRef(it.next());
			this.stringReceiverExpr = operandToStringRef(it.next());

		}

		@Override
		public void CALL_RESULT(int res) {
			if (stringReceiverExpr.containsSymbolicVariable()
					|| strExpr.containsSymbolicVariable()
					|| fromIndexExpr.containsSymbolicVariable()) {
				StringMultipleExpression strTExpr = new StringMultipleExpression(
						stringReceiverExpr, Operator.LASTINDEXOFSI, strExpr,
						new ArrayList<Expression<?>>(Collections
								.singletonList(fromIndexExpr)),
						Integer.toString(res));
				StringToIntCast castExpr = new StringToIntCast(strTExpr,
						(long) res);
				this.replaceBv32Top(castExpr);
			} else {
				// do nothing (concrete value only)
			}

		}
	}

}
