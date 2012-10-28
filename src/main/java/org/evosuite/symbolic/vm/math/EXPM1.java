package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.fp.RealUnaryExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.SymbolicEnvironment;


public final class EXPM1 extends MathFunction_D2D {

	private static final String EXPM1 = "expm1";

	public EXPM1(SymbolicEnvironment env) {
		super(env, EXPM1);
	}

	@Override
	protected RealValue executeFunction(double res) {
		Operator op = Operator.EXPM1;
		return new RealUnaryExpression(realExpression, op, res);
	}

}
