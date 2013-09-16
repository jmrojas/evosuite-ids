package org.evosuite.symbolic.vm.string;

import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.vm.NonNullReference;
import org.evosuite.symbolic.vm.SymbolicEnvironment;
import org.evosuite.symbolic.vm.SymbolicFunction;
import org.evosuite.symbolic.vm.SymbolicHeap;

public final class ToString extends SymbolicFunction {

	private static final String TO_STRING = "toString";

	public ToString(SymbolicEnvironment env) {
		super(env, Types.JAVA_LANG_STRING, TO_STRING, Types.TO_STR_DESCRIPTOR);
	}

	@Override
	public Object executeFunction() {

		// object receiver
		NonNullReference symb_str = this.getSymbReceiver();
		String conc_str = (String) this.getConcReceiver();

		// return value
		String conc_ret_val = (String) this.getConcRetVal();
		NonNullReference symb_ret_val = (NonNullReference) this.getSymbRetVal();

		StringValue string_expr = env.heap.getField(Types.JAVA_LANG_STRING,
				SymbolicHeap.$STRING_VALUE, conc_str, symb_str, conc_str);

		env.heap.putField(Types.JAVA_LANG_STRING, SymbolicHeap.$STRING_VALUE,
				conc_ret_val, symb_ret_val, string_expr);

		return this.getSymbRetVal();
	}
}
