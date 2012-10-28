package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.fp.RealUnaryExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.SymbolicEnvironment;


public final class LOG1P extends MathFunction_D2D {

	private static final String LOG1P = "log1p";

	public LOG1P(SymbolicEnvironment env) {
		super(env, LOG1P);
	}

	@Override
	protected RealValue executeFunction(double res) {
		Operator op = Operator.LOG1P;
		return new RealUnaryExpression(realExpression, op, res);
	}

}
