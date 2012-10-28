package org.evosuite.symbolic.vm;

import java.util.Iterator;
import java.util.Map;


import org.evosuite.symbolic.expr.bv.IntegerValue;
import org.evosuite.symbolic.expr.fp.RealValue;
import org.evosuite.symbolic.vm.string.buffer.StringBuffer_Ctor.StringBufferCtor_S;
import org.evosuite.symbolic.vm.string.buffer.StringBuffer_ToString;
import org.evosuite.symbolic.vm.string.buffer.StringBuffer_Append.*;
import org.objectweb.asm.Type;

import edu.uta.cse.dsc.AbstractVM;
import gnu.trove.map.hash.THashMap;

/**
 * 
 * @author galeotti
 * 
 */
public final class RFunctionVM extends AbstractVM {

	private static class FunctionKey {
		public FunctionKey(String owner, String name, String desc) {
			super();
			this.owner = owner;
			this.name = name;
			this.desc = desc;
		}

		public String owner;
		public String name;
		public String desc;

		@Override
		public int hashCode() {
			return owner.hashCode() + name.hashCode() + desc.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || !o.getClass().equals(FunctionKey.class)) {
				return false;
			} else {
				FunctionKey that = (FunctionKey) o;
				return this.owner.equals(that.owner)
						&& this.name.equals(that.name)
						&& this.desc.equals(that.desc);
			}
		}
	}

	private final SymbolicEnvironment env;

	public RFunctionVM(SymbolicEnvironment env) {
		this.env = env;
		fillFunctionsTable();
	}

	private void fillFunctionsTable() {

		// java.lang.StringBuffer
		addFunctionToTable(new StringBufferCtor_S(env));
		addFunctionToTable(new StringBuffer_ToString(env));
		addFunctionToTable(new StringBufferAppend_B(env));
		addFunctionToTable(new StringBufferAppend_C(env));
		addFunctionToTable(new StringBufferAppend_I(env));
		addFunctionToTable(new StringBufferAppend_L(env));
		addFunctionToTable(new StringBufferAppend_F(env));
		addFunctionToTable(new StringBufferAppend_D(env));
		addFunctionToTable(new StringBufferAppend_STR(env));

		// // java.lang.Integer
		// addFunctionToTable(new I_Init(env));
		// addFunctionToTable(new I_ValueOf(env));
		// addFunctionToTable(new I_IntValue(env));
		//
		// // java.lang.Long
		// addFunctionToTable(new J_Init(env));
		// addFunctionToTable(new J_ValueOf(env));
		// addFunctionToTable(new J_LongValue(env));
		//
		// // java.lang.Float
		// addFunctionToTable(new F_Init(env));
		// addFunctionToTable(new F_ValueOf(env));
		// addFunctionToTable(new F_FloatValue(env));
		//
		// // java.lang.Double
		// addFunctionToTable(new D_Init(env));
		// addFunctionToTable(new D_ValueOf(env));
		// addFunctionToTable(new D_DoubleValue(env));
		//
		// // java.lang.Short
		// addFunctionToTable(new S_Init(env));
		// addFunctionToTable(new S_ValueOf(env));
		// addFunctionToTable(new S_ShortValue(env));
		//
		// // java.lang.Byte
		// addFunctionToTable(new B_Init(env));
		// addFunctionToTable(new B_ValueOf(env));
		// addFunctionToTable(new B_ByteValue(env));
		//
		// // java.lang.Character
		// addFunctionToTable(new C_Init(env));
		// addFunctionToTable(new C_ValueOf(env));
		// addFunctionToTable(new C_CharValue(env));
		//
		// // java.lang.Boolean
		// addFunctionToTable(new Z_Init(env));
		// addFunctionToTable(new Z_ValueOf(env));
		// addFunctionToTable(new Z_BooleanValue(env));
		//
		// // java.lang.Math
		// addFunctionToTable(new ABS.ABS_I(env));
		// addFunctionToTable(new ABS.ABS_L(env));
		// addFunctionToTable(new ABS.ABS_F(env));
		// addFunctionToTable(new ABS.ABS_D(env));
		// addFunctionToTable(new ACOS(env));
		// addFunctionToTable(new ASIN(env));
		// addFunctionToTable(new ATAN(env));
		// addFunctionToTable(new ATAN2(env));
		// addFunctionToTable(new CBRT(env));
		// addFunctionToTable(new CEIL(env));
		// addFunctionToTable(new CopySign.CopySign_F(env));
		// addFunctionToTable(new CopySign.CopySign_D(env));
		// addFunctionToTable(new COS(env));
		// addFunctionToTable(new COSH(env));
		// addFunctionToTable(new EXP(env));
		// addFunctionToTable(new EXPM1(env));
		// addFunctionToTable(new FLOOR(env));
		// addFunctionToTable(new GetExponent.GetExponent_F(env));
		// addFunctionToTable(new GetExponent.GetExponent_D(env));
		// addFunctionToTable(new HYPOT(env));
		// addFunctionToTable(new IEEEremainder(env));
		// addFunctionToTable(new LOG(env));
		// addFunctionToTable(new LOG10(env));
		// addFunctionToTable(new LOG1P(env));
		// addFunctionToTable(new MIN.MIN_I(env));
		// addFunctionToTable(new MIN.MIN_L(env));
		// addFunctionToTable(new MIN.MIN_F(env));
		// addFunctionToTable(new MIN.MIN_D(env));
		// addFunctionToTable(new MAX.MAX_I(env));
		// addFunctionToTable(new MAX.MAX_L(env));
		// addFunctionToTable(new MAX.MAX_F(env));
		// addFunctionToTable(new MAX.MAX_D(env));
		// addFunctionToTable(new NextAfter.NextAfter_F(env));
		// addFunctionToTable(new NextAfter.NextAfter_D(env));
		// addFunctionToTable(new NextUp.NextUp_F(env));
		// addFunctionToTable(new NextUp.NextUp_D(env));
		// addFunctionToTable(new POW(env));
		// addFunctionToTable(new RINT(env));
		// addFunctionToTable(new Round.Round_F(env));
		// addFunctionToTable(new Round.Round_D(env));
		// addFunctionToTable(new SCALB.SCALB_F(env));
		// addFunctionToTable(new SCALB.SCALB_D(env));
		// addFunctionToTable(new SIGNUM.SIGNUM_F(env));
		// addFunctionToTable(new SIGNUM.SIGNUM_D(env));
		// addFunctionToTable(new SIN(env));
		// addFunctionToTable(new SINH(env));
		// addFunctionToTable(new SQRT(env));
		// addFunctionToTable(new TAN(env));
		// addFunctionToTable(new TANH(env));
		// addFunctionToTable(new ToDegrees(env));
		// addFunctionToTable(new ToRadians(env));
		// addFunctionToTable(new ULP.ULP_F(env));
		// addFunctionToTable(new ULP.ULP_D(env));
		//
		// // java.lang.String
		// addFunctionToTable(new CharAt(env));
		// addFunctionToTable(new CompareTo(env));
		// addFunctionToTable(new CompareToIgnoreCase(env));
		// addFunctionToTable(new Concat(env));
		// addFunctionToTable(new Contains(env));
		// addFunctionToTable(new EndsWith(env));
		// addFunctionToTable(new Matches(env));
		// addFunctionToTable(new Equals(env));
		// addFunctionToTable(new EqualsIgnoreCase(env));
		// addFunctionToTable(new IndexOf.IndexOf_C(env));
		// addFunctionToTable(new IndexOf.IndexOf_S(env));
		// addFunctionToTable(new IndexOf.IndexOf_CI(env));
		// addFunctionToTable(new IndexOf.IndexOf_SI(env));
		// addFunctionToTable(new LastIndexOf.LastIndexOf_C(env));
		// addFunctionToTable(new LastIndexOf.LastIndexOf_S(env));
		// addFunctionToTable(new LastIndexOf.LastIndexOf_CI(env));
		// addFunctionToTable(new LastIndexOf.LastIndexOf_SI(env));
		// addFunctionToTable(new Length(env));
		// addFunctionToTable(new RegionMatches(env));
		// addFunctionToTable(new Replace.Replace_C(env));
		// addFunctionToTable(new Replace.Replace_CS(env));
		// addFunctionToTable(new ReplaceAll(env));
		// addFunctionToTable(new ReplaceFirst(env));
		// addFunctionToTable(new StartsWith(env));
		// addFunctionToTable(new Substring(env));
		// addFunctionToTable(new ToLowerCase(env));
		// addFunctionToTable(new ToString(env));
		// addFunctionToTable(new ToUpperCase(env));
		// addFunctionToTable(new Trim(env));
		// addFunctionToTable(new ValueOf.ValueOf_O(env));
		//
		// // java.lang.StringBuilder
		// addFunctionToTable(new SB_Init.StringBuilderInit_CS(env));
		// addFunctionToTable(new SB_Init.StringBuilderInit_S(env));
		// addFunctionToTable(new SB_Append.Append_B(env));
		// addFunctionToTable(new SB_Append.Append_C(env));
		// addFunctionToTable(new SB_Append.Append_D(env));
		// addFunctionToTable(new SB_Append.Append_F(env));
		// addFunctionToTable(new SB_Append.Append_I(env));
		// addFunctionToTable(new SB_Append.Append_L(env));
		// addFunctionToTable(new SB_Append.Append_O(env));
		// addFunctionToTable(new SB_Append.Append_S(env));
		// addFunctionToTable(new SB_ToString(env));
		//
		// // java.util.regex.Pattern
		// addFunctionToTable(new Pattern_Matches(env));
		// addFunctionToTable(new Pattern_Matcher(env));
		//
		// // java.util.regex.Matcher
		// addFunctionToTable(new Matcher_Matches(env));
	}

	private void addFunctionToTable(RFunction f) {
		FunctionKey k = new FunctionKey(f.getOwner(), f.getName(), f.getDesc());
		functionsTable.put(k, f);
	}

	@Override
	public void INVOKESTATIC(String owner, String name, String desc) {
		functionUnderExecution = getFunction(owner, name, desc);
		if (functionUnderExecution != null) {
			if (Type.getArgumentTypes(desc).length == 0) {
				functionUnderExecution.beforeExecuteFunction();
			}
		}
	}

	@Override
	public void INVOKEVIRTUAL(Object conc_receiver, String owner, String name,
			String desc) {
		functionUnderExecution = getFunction(owner, name, desc);
		if (functionUnderExecution != null) {
			Reference symb_receiver = getReceiverFromStack();
			functionUnderExecution.setReceiver(conc_receiver, symb_receiver);
			if (Type.getArgumentTypes(desc).length == 0) {
				functionUnderExecution.beforeExecuteFunction();
			}
		}
	}

	private Reference getReceiverFromStack() {
		String desc = this.functionUnderExecution.getDesc();
		Type[] argTypes = Type.getArgumentTypes(desc);
		Iterator<Operand> it = this.env.topFrame().operandStack.iterator();
		for (int i = 0; i < argTypes.length; i++) {
			it.next(); // move cursor until reference
		}
		Operand op = it.next();
		return ((ReferenceOperand) op).getReference();
	}

	private Operand getOperandFromStack(int nr) {
		String desc = functionUnderExecution.getDesc();
		Type[] argTypes = Type.getArgumentTypes(desc);
		int moves = argTypes.length - 1 - nr;

		Iterator<Operand> it = this.env.topFrame().operandStack.iterator();
		for (int i = 0; i < moves; i++) {
			it.next(); // move cursor until reference
		}
		Operand op = it.next();
		return op;
	}

	private IntegerValue getIntegerExprFromStack(int nr) {
		IntegerOperand op = (IntegerOperand) getOperandFromStack(nr);
		return op.getIntegerExpression();
	}

	private RealValue getRealExprFromStack(int moves) {
		RealOperand op = (RealOperand) getOperandFromStack(moves);
		return op.getRealExpression();
	}

	private Reference getReferenceFromStack(int moves) {
		ReferenceOperand op = (ReferenceOperand) getOperandFromStack(moves);
		return op.getReference();
	}

	private final Map<FunctionKey, RFunction> functionsTable = new THashMap<FunctionKey, RFunction>();

	private RFunction getFunction(String owner, String name, String desc) {
		FunctionKey k = new FunctionKey(owner, name, desc);
		RFunction f = functionsTable.get(k);
		return f;
	}

	@Override
	public void CALL_RESULT(int conc_ret_val, String owner, String name,
			String desc) {
		if (functionUnderExecution != null) {
			IntegerValue symb_ret_val = this.env.topFrame().operandStack
					.peekBv32();
			functionUnderExecution.setReturnValue(conc_ret_val, symb_ret_val);
			IntegerValue new_symb_ret_val = (IntegerValue) functionUnderExecution
					.executeFunction();
			this.replaceTopBv32(new_symb_ret_val);
		}
		functionUnderExecution = null;
	}

	@Override
	public void CALL_RESULT(Object conc_ret_val, String owner, String name,
			String desc) {
		if (functionUnderExecution != null) {
			Reference symb_ret_val = this.env.topFrame().operandStack.peekRef();
			functionUnderExecution.setReturnValue(conc_ret_val, symb_ret_val);
			Reference new_symb_ret_val = (Reference) functionUnderExecution
					.executeFunction();
			this.replaceTopRef(new_symb_ret_val);
		}
		functionUnderExecution = null;
	}

	private void replaceTopRef(Reference ref) {
		env.topFrame().operandStack.popRef();
		env.topFrame().operandStack.pushRef(ref);
	}

	@Override
	public void CALL_RESULT(String owner, String name, String desc) {
		if (functionUnderExecution != null) {
			functionUnderExecution.executeFunction();
		}
		functionUnderExecution = null;
	}

	@Override
	public void CALL_RESULT(boolean conc_ret_val, String owner, String name,
			String desc) {
		if (functionUnderExecution != null) {
			IntegerValue symb_ret_val = this.env.topFrame().operandStack
					.peekBv32();
			functionUnderExecution.setReturnValue(conc_ret_val, symb_ret_val);
			IntegerValue new_symb_ret_val = (IntegerValue) functionUnderExecution
					.executeFunction();
			this.replaceTopBv32(new_symb_ret_val);
		}
		functionUnderExecution = null;
	}

	@Override
	public void CALL_RESULT(long conc_ret_val, String owner, String name,
			String desc) {
		if (functionUnderExecution != null) {
			IntegerValue symb_ret_val = this.env.topFrame().operandStack
					.peekBv64();
			functionUnderExecution.setReturnValue(conc_ret_val, symb_ret_val);
			IntegerValue new_symb_ret_val = (IntegerValue) functionUnderExecution
					.executeFunction();
			this.replaceTopBv64(new_symb_ret_val);
		}
		functionUnderExecution = null;
	}

	@Override
	public void CALL_RESULT(double conc_ret_val, String owner, String name,
			String desc) {
		if (functionUnderExecution != null) {
			RealValue symb_ret_val = this.env.topFrame().operandStack
					.peekFp64();
			functionUnderExecution.setReturnValue(conc_ret_val, symb_ret_val);
			RealValue new_symb_ret_val = (RealValue) functionUnderExecution
					.executeFunction();
			this.replaceTopFp64(new_symb_ret_val);
		}
		functionUnderExecution = null;
	}

	@Override
	public void CALL_RESULT(float conc_ret_val, String owner, String name,
			String desc) {
		if (functionUnderExecution != null) {
			RealValue symb_ret_val = this.env.topFrame().operandStack
					.peekFp32();
			functionUnderExecution.setReturnValue(conc_ret_val, symb_ret_val);
			RealValue new_symb_ret_val = (RealValue) functionUnderExecution
					.executeFunction();
			this.replaceTopFp32(new_symb_ret_val);
		}
		functionUnderExecution = null;
	}

	@Override
	public void INVOKESPECIAL(String owner, String name, String desc) {
		functionUnderExecution = getFunction(owner, name, desc);
		if (functionUnderExecution != null) {
			Reference symb_receiver = getReceiverFromStack();
			functionUnderExecution.setReceiver(
					null /* receiver not yet ready */, symb_receiver);
			if (Type.getArgumentTypes(desc).length == 0) {
				functionUnderExecution.beforeExecuteFunction();
			}
		}
	}

	@Override
	public void INVOKESPECIAL(Object conc_receiver, String owner, String name,
			String desc) {
		functionUnderExecution = getFunction(owner, name, desc);
		if (functionUnderExecution != null) {
			Reference symb_receiver = getReceiverFromStack();
			functionUnderExecution.setReceiver(conc_receiver, symb_receiver);
			if (Type.getArgumentTypes(desc).length == 0) {
				functionUnderExecution.beforeExecuteFunction();
			}
		}

	}

	private RFunction functionUnderExecution;

	@Override
	public void INVOKEINTERFACE(Object conc_receiver, String owner,
			String name, String desc) {
		functionUnderExecution = getFunction(owner, name, desc);
		if (functionUnderExecution != null) {
			Reference symb_receiver = getReceiverFromStack();
			functionUnderExecution.setReceiver(conc_receiver, symb_receiver);
			if (Type.getArgumentTypes(functionUnderExecution.getDesc()).length == 0) {
				functionUnderExecution.beforeExecuteFunction();
			}
		}

	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, int conc_arg) {
		if (functionUnderExecution != null) {
			IntegerValue symb_arg = getIntegerExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex,
			boolean conc_arg) {
		if (functionUnderExecution != null) {
			IntegerValue symb_arg = getIntegerExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);
		}
	}

	private void beforeExecuteFunction(int nr) {
		String desc = functionUnderExecution.getDesc();
		if (Type.getArgumentTypes(desc).length - 1 == nr) {
			functionUnderExecution.beforeExecuteFunction();
		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, short conc_arg) {
		if (functionUnderExecution != null) {
			IntegerValue symb_arg = getIntegerExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, byte conc_arg) {
		if (functionUnderExecution != null) {
			IntegerValue symb_arg = getIntegerExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, char conc_arg) {
		if (functionUnderExecution != null) {
			IntegerValue symb_arg = getIntegerExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, long conc_arg) {
		if (functionUnderExecution != null) {
			IntegerValue symb_arg = getIntegerExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, float conc_arg) {
		if (functionUnderExecution != null) {
			RealValue symb_arg = getRealExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex,
			double conc_arg) {
		if (functionUnderExecution != null) {
			RealValue symb_arg = getRealExprFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex,
			Object conc_arg) {
		if (functionUnderExecution != null) {
			Reference symb_arg = getReferenceFromStack(nr);
			functionUnderExecution.setParam(nr, conc_arg, symb_arg);
			beforeExecuteFunction(nr);

		}
	}

	private void replaceTopBv32(IntegerValue expr) {
		env.topFrame().operandStack.popBv32();
		env.topFrame().operandStack.pushBv32(expr);
	}

	private void replaceTopBv64(IntegerValue expr) {
		env.topFrame().operandStack.popBv64();
		env.topFrame().operandStack.pushBv64(expr);
	}

	private void replaceTopFp32(RealValue expr) {
		this.env.topFrame().operandStack.popFp32();
		this.env.topFrame().operandStack.pushFp32(expr);
	}

	private void replaceTopFp64(RealValue expr) {
		env.topFrame().operandStack.popFp64();
		env.topFrame().operandStack.pushFp64(expr);
	}

}