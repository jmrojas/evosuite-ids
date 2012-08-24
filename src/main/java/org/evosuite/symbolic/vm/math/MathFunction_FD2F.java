package org.evosuite.symbolic.vm.math;

import java.util.Iterator;

import org.evosuite.symbolic.expr.RealExpression;
import org.evosuite.symbolic.vm.Function;
import org.evosuite.symbolic.vm.Operand;
import org.evosuite.symbolic.vm.SymbolicEnvironment;


public abstract class MathFunction_FD2F extends Function {

	protected RealExpression left;
	protected RealExpression right;

	public MathFunction_FD2F(SymbolicEnvironment env, String name) {
		super(env, Types.JAVA_LANG_MATH, name,
				Types.FD2F_DESCRIPTOR);
	}

	@Override
	public final void INVOKESTATIC() {
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		right = fp64(it.next());
		left = fp32(it.next());
	}

	@Override
	public final void CALL_RESULT(float res) {
		if (left.containsSymbolicVariable() || right.containsSymbolicVariable()) {
			RealExpression ret_val = executeFunction(res);
			replaceTopFp32(ret_val);
		}
	}

	protected abstract RealExpression executeFunction(float res);
}
