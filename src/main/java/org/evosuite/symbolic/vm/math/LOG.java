package org.evosuite.symbolic.vm.math;

import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.fp.RealUnaryExpression;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.SymbolicFunction;
import org.evosuite.symbolic.vm.SymbolicEnvironment;

public final class LOG extends SymbolicFunction {

	private static final String LOG = "log";

	public LOG(SymbolicEnvironment env) {
		super(env, Types.JAVA_LANG_MATH, LOG, Types.D2D_DESCRIPTOR);
	}

	@Override
	public Object executeFunction() {
		double res = this.getConcDoubleRetVal();
		RealValue realExpression = this.getSymbRealArgument(0);
		RealValue logExpr;
		if (realExpression.containsSymbolicVariable()) {
			Operator op = Operator.LOG;
			logExpr = new RealUnaryExpression(realExpression, op, res);
		} else {
			logExpr = this.getSymbRealRetVal();
		}
		return logExpr;
	}
}
