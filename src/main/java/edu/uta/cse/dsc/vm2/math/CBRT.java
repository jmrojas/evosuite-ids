package edu.uta.cse.dsc.vm2.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.RealExpression;
import org.evosuite.symbolic.expr.RealUnaryExpression;

import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public final class CBRT extends MathFunction_D2D {

	private static final String CBRT = "cbrt";

	public CBRT(SymbolicEnvironment env) {
		super(env, CBRT);
	}

	@Override
	protected RealExpression executeFunction(double res) {
		Operator op = Operator.CBRT;
		return new RealUnaryExpression(realExpression, op, res);
	}

}
