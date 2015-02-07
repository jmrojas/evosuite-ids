package org.evosuite.symbolic.vm.string.buffer;

import java.util.ArrayList;
import java.util.Collections;

import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.IntegerConstraint;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.str.StringConstant;
import org.evosuite.symbolic.expr.str.StringMultipleExpression;
import org.evosuite.symbolic.expr.bv.IntegerConstant;
import org.evosuite.symbolic.expr.bv.IntegerValue;
import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.vm.NonNullReference;
import org.evosuite.symbolic.vm.SymbolicFunction;
import org.evosuite.symbolic.vm.SymbolicEnvironment;
import org.evosuite.symbolic.vm.SymbolicHeap;

public final class StringBuffer_SetLength extends SymbolicFunction {

	private static final String SET_LENGTH = "setLength";

	public StringBuffer_SetLength(SymbolicEnvironment env) {
		super(env, Types.JAVA_LANG_STRING_BUFFER, SET_LENGTH,
				Types.INT_TO_VOID_DESCRIPTOR);
	}

	private String pre_conc_value = null;

	@Override
	public Object executeFunction() {
		NonNullReference symb_str_buffer = this.getSymbReceiver();
		StringBuffer conc_str_buffer = (StringBuffer) this.getConcReceiver();

		IntegerValue newSymbLength = this.getSymbIntegerArgument(0);
		int newConcLength = this.getConcIntArgument(0);

		// retrieve symbolic value from heap
		String conc_value = conc_str_buffer.toString();
		StringValue symb_value = env.heap.getField(
				Types.JAVA_LANG_STRING_BUFFER,
				SymbolicHeap.$STRING_BUFFER_CONTENTS, conc_str_buffer,
				symb_str_buffer, pre_conc_value);

		if (symb_value.containsSymbolicVariable()
				|| newSymbLength.containsSymbolicVariable()) {

			StringValue new_symb_value = null;
			if (!newSymbLength.containsSymbolicVariable() && newConcLength == 0) {
				// StringBuffer contents equals to "" string
				new_symb_value = new StringConstant("");
			} else {
				// StringBuffer contents equ
				new_symb_value = new StringMultipleExpression(symb_value,
						Operator.SUBSTRING, new IntegerConstant(0),
						new ArrayList<Expression<?>>(Collections
								.singletonList(newSymbLength)),
						conc_value);
			}

			env.heap.putField(Types.JAVA_LANG_STRING_BUFFER,
					SymbolicHeap.$STRING_BUFFER_CONTENTS, conc_str_buffer,
					symb_str_buffer, new_symb_value);

		}

		// return void
		return null;
	}

	@Override
	public IntegerConstraint beforeExecuteFunction() {
		StringBuffer conc_str_buffer = (StringBuffer) this.getConcReceiver();
		if (conc_str_buffer != null) {
			pre_conc_value = conc_str_buffer.toString();
		} else {
			pre_conc_value = null;
		}
		return null;
	}
}
