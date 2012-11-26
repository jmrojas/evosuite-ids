package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.bv.IntegerValue;
import org.evosuite.symbolic.expr.bv.RealUnaryToIntegerExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.RFunction;
import org.evosuite.symbolic.vm.SymbolicEnvironment;

public abstract class Round {

	private static final String ROUND = "round";

	public static class Round_D extends RFunction {

		public Round_D(SymbolicEnvironment env) {
			super(env, Types.JAVA_LANG_MATH, ROUND, Types.D2L_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {
			long res = this.getConcLongRetVal();
			RealValue realExpression = this.getSymbRealArgument(0);
			IntegerValue roundExpr;
			if (realExpression.containsSymbolicVariable()) {
				Operator op = Operator.ROUND;
				roundExpr = new RealUnaryToIntegerExpression(realExpression,
						op, res);
			} else {
				roundExpr = this.getSymbIntegerRetVal();
			}
			return roundExpr;
		}

	}

	public static class Round_F extends RFunction {

		public Round_F(SymbolicEnvironment env) {
			super(env, Types.JAVA_LANG_MATH, ROUND, Types.F2I_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {
			int res = this.getConcIntRetVal();
			RealValue realExpression = this.getSymbRealArgument(0);
			IntegerValue roundExpr;
			if (realExpression.containsSymbolicVariable()) {
				Operator op = Operator.ROUND;
				roundExpr = new RealUnaryToIntegerExpression(realExpression,
						op, (long) res);
			} else {
				roundExpr = this.getSymbIntegerRetVal();
			}
			return roundExpr;
		}

	}

}
