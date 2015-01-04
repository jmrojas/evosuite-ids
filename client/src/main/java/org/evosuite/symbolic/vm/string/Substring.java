package org.evosuite.symbolic.vm.string;

import java.util.ArrayList;
import java.util.Collections;

import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.bv.IntegerValue;
import org.evosuite.symbolic.expr.bv.StringUnaryToIntegerExpression;
import org.evosuite.symbolic.expr.str.StringBinaryExpression;
import org.evosuite.symbolic.expr.str.StringMultipleExpression;
import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.vm.NonNullReference;
import org.evosuite.symbolic.vm.SymbolicEnvironment;
import org.evosuite.symbolic.vm.SymbolicFunction;
import org.evosuite.symbolic.vm.SymbolicHeap;

public abstract class Substring extends SymbolicFunction {

	private static final String SUBSTRING = "substring";

	public Substring(SymbolicEnvironment env, String desc) {
		super(env, Types.JAVA_LANG_STRING, SUBSTRING, desc);
	}

	public static final class Substring_II extends Substring {
		public Substring_II(SymbolicEnvironment env) {
			super(env, Types.INT_INT_TO_STR_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {

			NonNullReference symb_receiver = this.getSymbReceiver();
			String conc_receiver = (String) this.getConcReceiver();

			IntegerValue beginIndexExpr = this.getSymbIntegerArgument(0);
			IntegerValue endIndexExpr = this.getSymbIntegerArgument(1);

			StringValue str_expr = env.heap.getField(Types.JAVA_LANG_STRING,
					SymbolicHeap.$STRING_VALUE, conc_receiver, symb_receiver,
					conc_receiver);

			NonNullReference symb_ret_val = (NonNullReference) this
					.getSymbRetVal();
			String conc_ret_val = (String) this.getConcRetVal();

			StringMultipleExpression symb_value = new StringMultipleExpression(
					str_expr, Operator.SUBSTRING, beginIndexExpr,
					new ArrayList<Expression<?>>(Collections
							.singletonList(endIndexExpr)),
					conc_ret_val);

			env.heap.putField(Types.JAVA_LANG_STRING,
					SymbolicHeap.$STRING_VALUE, conc_ret_val, symb_ret_val,
					symb_value);

			return this.getSymbRetVal();
		}
	}

	public static final class Substring_I extends Substring {
		public Substring_I(SymbolicEnvironment env) {
			super(env, Types.INT_TO_STR_DESCRIPTOR);
		}

		@Override
		public Object executeFunction() {

			NonNullReference symb_receiver = this.getSymbReceiver();
			String conc_receiver = (String) this.getConcReceiver();

			IntegerValue beginIndexExpr = this.getSymbIntegerArgument(0);

			StringValue str_expr = env.heap.getField(Types.JAVA_LANG_STRING,
					SymbolicHeap.$STRING_VALUE, conc_receiver, symb_receiver,
					conc_receiver);

			NonNullReference symb_ret_val = (NonNullReference) this
					.getSymbRetVal();
			String conc_ret_val = (String) this.getConcRetVal();

			IntegerValue lengthExpr = new StringUnaryToIntegerExpression(
					str_expr, Operator.LENGTH, (long) conc_receiver.length());

			StringMultipleExpression symb_value = new StringMultipleExpression(
					str_expr, Operator.SUBSTRING, beginIndexExpr,
					new ArrayList<Expression<?>>(Collections
							.singletonList(lengthExpr)),
					conc_ret_val);

			env.heap.putField(Types.JAVA_LANG_STRING,
					SymbolicHeap.$STRING_VALUE, conc_ret_val, symb_ret_val,
					symb_value);

			return this.getSymbRetVal();
		}
	}

}
