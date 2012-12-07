package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.fp.RealUnaryExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.SymbolicEnvironment;


public final class ToDegrees extends MathFunction_D2D {

	private static final String TO_DEGREES = "toDegrees";

	public ToDegrees(SymbolicEnvironment env) {
		super(env, TO_DEGREES);
	}

	@Override
	protected RealValue executeFunction(double res) {
		Operator op = Operator.TODEGREES;
		return new RealUnaryExpression(realExpression, op, res);
	}

}
