package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.fp.RealUnaryExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.RFunction;
import org.evosuite.symbolic.vm.SymbolicEnvironment;

public final class ToRadians extends RFunction {

	private static final String TO_RADIANS = "toRadians";

	public ToRadians(SymbolicEnvironment env) {
		super(env, Types.JAVA_LANG_MATH, TO_RADIANS, Types.D2D_DESCRIPTOR);
	}

	@Override
	public Object executeFunction() {
		double res = this.getConcDoubleRetVal();
		RealValue realExpression = this.getSymbRealArgument(0);
		RealValue toRadiansExpr;
		if (realExpression.containsSymbolicVariable()) {
			Operator op = Operator.TORADIANS;
			toRadiansExpr = new RealUnaryExpression(realExpression, op, res);
		} else {
			toRadiansExpr = this.getSymbRealRetVal();
		}
		return toRadiansExpr;
	}

}
