package edu.uta.cse.dsc.vm2.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.RealExpression;
import org.evosuite.symbolic.expr.RealUnaryExpression;

import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public final class ACOS extends MathFunction_D2D {

	private static final String ACOS = "acos";

	public ACOS(SymbolicEnvironment env) {
		super(env, ACOS);
	}

	@Override
	protected RealExpression executeFunction(double res) {
		Operator op = Operator.ACOS;
		RealUnaryExpression acosExpr = new RealUnaryExpression(realExpression,
				op, res);
		return acosExpr;
	}

}
