package edu.uta.cse.dsc.vm2.string;

import static org.objectweb.asm.Type.BOOLEAN_TYPE;
import static org.objectweb.asm.Type.CHAR_TYPE;
import static org.objectweb.asm.Type.INT_TYPE;
import static org.objectweb.asm.Type.getMethodDescriptor;

import org.evosuite.symbolic.expr.IntegerExpression;
import org.evosuite.symbolic.expr.RealExpression;
import org.evosuite.symbolic.expr.StringExpression;
import org.objectweb.asm.Type;

import edu.uta.cse.dsc.vm2.Bv32Operand;
import edu.uta.cse.dsc.vm2.Bv64Operand;
import edu.uta.cse.dsc.vm2.Fp32Operand;
import edu.uta.cse.dsc.vm2.Fp64Operand;
import edu.uta.cse.dsc.vm2.Operand;
import edu.uta.cse.dsc.vm2.ReferenceOperand;
import edu.uta.cse.dsc.vm2.StringReferenceOperand;
import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public abstract class StringFunction {

	protected SymbolicEnvironment env;

	public StringFunction(SymbolicEnvironment env, String name, String desc) {
		this.env = env;
		this.owner = StringFunctionCallVM.JAVA_LANG_STRING;
		this.name = name;
		this.desc = desc;
	}

	public void INVOKEVIRTUAL() {
		/* STUB */
	}

	public final void INVOKEVIRTUAL(Object receiver) {
		/**
		 * We do nothing if receiver is null since we assume HeapVM deals with
		 * the null pointer exception effect in the symbolic state.
		 */
		if (receiver == null) {
			return;
		}

		INVOKEVIRTUAL((String) receiver);
	}

	protected StringExpression stringReceiverExpr;

	protected abstract void INVOKEVIRTUAL(String receiver);

	private final String owner;
	private final String name;
	private final String desc;
	private static final Type CHARSEQ_TYPE = Type.getType(CharSequence.class);
	private static final Type OBJECT_TYPE = Type.getType(Object.class);
	private static final Type STRING_TYPE = Type.getType(String.class);
	public static final String TO_INT_DESCRIPTOR = getMethodDescriptor(INT_TYPE);
	public static final String TO_STR_DESCRIPTOR = getMethodDescriptor(STRING_TYPE);
	public static final String STR_TO_INT_DESCRIPTOR = getMethodDescriptor(
			INT_TYPE, STRING_TYPE);
	public static final String INT_TO_INT_DESCRIPTOR = getMethodDescriptor(
			INT_TYPE, INT_TYPE);
	public static final String INT_TO_CHAR_DESCRIPTOR = getMethodDescriptor(
			CHAR_TYPE, INT_TYPE);
	public static final String STR_TO_STR_DESCRIPTOR = getMethodDescriptor(
			STRING_TYPE, STRING_TYPE);
	public static final String CHAR_CHAR_TO_STR_DESCRIPTOR = getMethodDescriptor(
			STRING_TYPE, CHAR_TYPE, CHAR_TYPE);
	public static final String INT_INT_TO_STR_DESCRIPTOR = getMethodDescriptor(
			STRING_TYPE, INT_TYPE, INT_TYPE);
	public static final String INT_INT_TO_INT_DESCRIPTOR = getMethodDescriptor(
			INT_TYPE, INT_TYPE, INT_TYPE);
	public static final String STR_STR_TO_STR_DESCRIPTOR = getMethodDescriptor(
			STRING_TYPE, STRING_TYPE, STRING_TYPE);
	public static final String STR_INT_TO_INT_DESCRIPTOR = getMethodDescriptor(
			INT_TYPE, STRING_TYPE, INT_TYPE);
	public static final String OBJECT_TO_BOOL_DESCRIPTOR = getMethodDescriptor(
			BOOLEAN_TYPE, OBJECT_TYPE);
	public static final String STR_TO_BOOL_DESCRIPTOR = getMethodDescriptor(
			BOOLEAN_TYPE, STRING_TYPE);
	public static final String STR_INT_TO_BOOL_DESCRIPTOR = getMethodDescriptor(
			BOOLEAN_TYPE, STRING_TYPE, INT_TYPE);
	public static final String BOOL_INT_STR_INT_INT_TO_BOOL_DESCRIPTOR = getMethodDescriptor(
			BOOLEAN_TYPE, Type.BOOLEAN_TYPE, INT_TYPE, STRING_TYPE, INT_TYPE,
			INT_TYPE);
	public static final String CHARSEQ_TO_BOOL_DESCRIPTOR = getMethodDescriptor(
			BOOLEAN_TYPE, CHARSEQ_TYPE);
	public static final String CHARSEQ_CHARSEQ_TO_STR_DESCRIPTOR = getMethodDescriptor(
			STRING_TYPE, CHARSEQ_TYPE, CHARSEQ_TYPE);

	public String getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public void CALL_RESULT(boolean res) {
		/* STUB */
	}

	public void CALL_RESULT(int res) {
		/* STUB */
	}

	public void CALL_RESULT(Object res) {
		/* STUB */
	}

	protected void replaceBv32Top(IntegerExpression expr) {
		this.env.topFrame().operandStack.popBv32(); // discard old top
		this.env.topFrame().operandStack.pushBv32(expr);
	}

	protected void replaceStrRefTop(StringExpression expr) {
		this.env.topFrame().operandStack.popStringRef(); // discard old top
		this.env.topFrame().operandStack.pushStringRef(expr);
	}

	protected void throwException(Exception ex) {
		this.env.topFrame().operandStack.clearOperands();
		this.env.topFrame().operandStack.pushRef(ex);
	}

	protected static RealExpression fp64(Operand operand) {
		Fp64Operand fp64 = (Fp64Operand) operand;
		return fp64.getRealExpression();
	}

	protected static RealExpression fp32(Operand operand) {
		Fp32Operand fp32 = (Fp32Operand) operand;
		return fp32.getRealExpression();
	}

	protected static IntegerExpression bv32(Operand operand) {
		Bv32Operand fp32 = (Bv32Operand) operand;
		return fp32.getIntegerExpression();
	}

	protected static IntegerExpression bv64(Operand operand) {
		Bv64Operand bv64 = (Bv64Operand) operand;
		return bv64.getIntegerExpression();
	}

	protected static StringExpression stringRef(Operand operand) {
		StringReferenceOperand stringRef = (StringReferenceOperand) operand;
		return stringRef.getStringExpression();
	}

	protected static ReferenceOperand ref(Operand operand) {
		ReferenceOperand ref = (ReferenceOperand) operand;
		return ref;
	}

	protected static boolean isNullRef(ReferenceOperand ref) {
		return ref.getReference() == null;
	}

}
