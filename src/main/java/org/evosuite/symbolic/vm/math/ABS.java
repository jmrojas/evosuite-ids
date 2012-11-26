package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.bv.IntegerUnaryExpression;
import org.evosuite.symbolic.expr.bv.IntegerValue;
import org.evosuite.symbolic.expr.fp.RealUnaryExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.vm.RFunction;
import org.evosuite.symbolic.vm.SymbolicEnvironment;

public abstract class ABS {

	private static final String ABS_FUNCTION_NAME = "abs";

	public final static class ABS_D extends RFunction {

		public ABS_D(SymbolicEnvironment env) {
			super(env, Types.JAVA_LANG_MATH, ABS_FUNCTION_NAME,
					Types.D2D_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {
			double res = this.getConcDoubleRetVal();
			RealValue realExpression = this.getSymbRealArgument(0);

			RealValue sym_val;
			if (realExpression.containsSymbolicVariable()) {
				sym_val = new RealUnaryExpression(realExpression, Operator.ABS,
						res);
			} else {
				sym_val = this.getSymbRealRetVal();
			}
			return sym_val;
		}

	}

	public final static class ABS_F extends RFunction {

		public ABS_F(SymbolicEnvironment env) {
			super(env, Types.JAVA_LANG_MATH, ABS_FUNCTION_NAME,
					Types.F2F_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {
			float res = this.getConcFloatRetVal();
			RealValue realExpression = this.getSymbRealArgument(0);

			RealValue sym_val;
			if (realExpression.containsSymbolicVariable()) {
				sym_val = new RealUnaryExpression(realExpression, Operator.ABS,
						(double) res);
			} else {
				sym_val = this.getSymbRealRetVal();
			}
			return sym_val;
		}
	}

	public final static class ABS_I extends RFunction {

		public ABS_I(SymbolicEnvironment env) {
			super(env, Types.JAVA_LANG_MATH, ABS_FUNCTION_NAME,
					Types.I2I_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {
			int res = this.getConcIntRetVal();
			IntegerValue intExpression = this.getSymbIntegerArgument(0);
			IntegerValue sym_val;
			if (intExpression.containsSymbolicVariable()) {
				sym_val = new IntegerUnaryExpression(intExpression,
						Operator.ABS, (long) res);
			} else {
				sym_val = this.getSymbIntegerRetVal();
			}
			return sym_val;
		}

	}

	public final static class ABS_L extends RFunction {

		public ABS_L(SymbolicEnvironment env) {
			super(env, Types.JAVA_LANG_MATH, ABS_FUNCTION_NAME,
					Types.L2L_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {
			long res = this.getConcLongRetVal();
			IntegerValue intExpression = this.getSymbIntegerArgument(0);
			IntegerValue sym_val;
			if (intExpression.containsSymbolicVariable()) {
				sym_val = new IntegerUnaryExpression(intExpression,
						Operator.ABS, res);
			} else {
				sym_val = this.getSymbIntegerRetVal();
			}
			return sym_val;
		}

	}

}
