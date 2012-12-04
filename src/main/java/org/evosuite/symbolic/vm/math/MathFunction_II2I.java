package org.evosuite.symbolic.vm.math;

import java.util.Iterator;

import org.evosuite.symbolic.expr.bv.IntegerValue;
import org.evosuite.symbolic.vm.Function;
import org.evosuite.symbolic.vm.Operand;
import org.evosuite.symbolic.vm.SymbolicEnvironment;


public abstract class MathFunction_II2I extends Function {

	protected IntegerValue left;
	protected IntegerValue right;

	public MathFunction_II2I(SymbolicEnvironment env, String name) {
		super(env, Types.JAVA_LANG_MATH, name,
				Types.II2I_DESCRIPTOR);
	}

	@Override
	public final void INVOKESTATIC() {
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		right = bv32(it.next());
		left = bv32(it.next());
	}

	@Override
	public final void CALL_RESULT(int res) {
		if (left.containsSymbolicVariable() || right.containsSymbolicVariable()) {
			IntegerValue ret_val = executeFunction(res);
			replaceTopBv32(ret_val);
		}
	}

	protected abstract IntegerValue executeFunction(int res);
}
