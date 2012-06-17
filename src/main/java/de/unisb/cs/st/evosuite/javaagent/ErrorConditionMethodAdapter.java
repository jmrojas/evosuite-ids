/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.Frame;

/**
 * @author Gordon Fraser
 * 
 */
public class ErrorConditionMethodAdapter extends GeneratorAdapter {

	private final String className;

	private final String methodName;

	private final MethodVisitor next;

	private Frame[] frames;

	/**
     * 
     */
	public ErrorConditionMethodAdapter(MethodVisitor mv, String className,
	        String methodName, int access, String desc) {
		//super(Opcodes.ASM4, mv, access, methodName, desc);
		super(Opcodes.ASM4,
		        new AnnotatedMethodNode(access, methodName, desc, null, null), access,
		        methodName, desc);
		this.className = className;
		this.methodName = methodName;
		next = mv;
	}

	private void tagBranch() {
		Label dummyTag = new AnnotatedLabel();
		dummyTag.info = Boolean.TRUE;
		super.visitLabel(dummyTag);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitMethodInsn(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		// If non-static, add a null check
		if (opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEINTERFACE) {
			Type[] args = Type.getArgumentTypes(desc);
			Map<Integer, Integer> to = new HashMap<Integer, Integer>();
			for (int i = args.length - 1; i >= 0; i--) {
				int loc = newLocal(args[i]);
				storeLocal(loc);
				to.put(i, loc);
			}

			dup();//callee
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;

			tagBranch();
			super.visitJumpInsn(Opcodes.IFNONNULL, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/NullPointerException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/NullPointerException", "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);

			for (int i = 0; i < args.length; i++) {
				loadLocal(to.get(i));
			}
		}
		super.visitMethodInsn(opcode, owner, name, desc);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitFieldInsn(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		// If non-static, add a null check
		if (opcode == Opcodes.GETFIELD) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNONNULL, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/NullPointerException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/NullPointerException", "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);
		} else if (opcode == Opcodes.PUTFIELD && !methodName.equals("<init>")) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			if (Type.getType(desc).getSize() == 2) {
				// 2 words
				// v1 v2 v3
				super.visitInsn(Opcodes.DUP2_X1);
				// v2 v3 v1 v2 v3

				super.visitInsn(Opcodes.POP2);
				// v2 v3 v1

				super.visitInsn(Opcodes.DUP_X2);
				// v1 v2 v3 v1

			} else {
				// 1 word
				super.visitInsn(Opcodes.DUP2);
				//super.visitInsn(Opcodes.SWAP);
				super.visitInsn(Opcodes.POP);
			}
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNONNULL, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/NullPointerException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/NullPointerException", "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);
		}
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitTypeInsn(int, java.lang.String)
	 */
	@Override
	public void visitTypeInsn(int opcode, String type) {

		if (opcode == Opcodes.CHECKCAST) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNULL, origTarget);
			super.visitInsn(Opcodes.DUP);
			super.visitTypeInsn(Opcodes.INSTANCEOF, type);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNE, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ClassCastException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ClassCastException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);

		}
		super.visitTypeInsn(opcode, type);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitInsn(int)
	 */
	@Override
	public void visitInsn(int opcode) {
		// Check *DIV for divisonbyzero
		if (opcode == Opcodes.IDIV) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNE, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);
		} else if (opcode == Opcodes.FDIV) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP);
			super.visitLdcInsn(0F);
			super.visitInsn(Opcodes.FCMPL);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNE, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);
		} else if (opcode == Opcodes.LDIV || opcode == Opcodes.DDIV) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP2);
			if (opcode == Opcodes.LDIV) {
				super.visitLdcInsn(0L);
				super.visitInsn(Opcodes.LCMP);
			} else {
				super.visitLdcInsn(0.0);
				super.visitInsn(Opcodes.DCMPL);
			}
			tagBranch();
			super.visitJumpInsn(Opcodes.IFNE, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);

		}

		// Check array accesses
		if (opcode == Opcodes.IALOAD || opcode == Opcodes.BALOAD
		        || opcode == Opcodes.CALOAD || opcode == Opcodes.SALOAD
		        || opcode == Opcodes.LALOAD || opcode == Opcodes.FALOAD
		        || opcode == Opcodes.DALOAD || opcode == Opcodes.AALOAD) {
			Label origTarget = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			Label intermediateTarget = new Label();

			super.visitInsn(Opcodes.DUP);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFGE, intermediateTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/NegativeArraySizeException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/NegativeArraySizeException", "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(intermediateTarget);

			super.visitInsn(Opcodes.DUP2);
			super.visitInsn(Opcodes.SWAP);
			//super.visitInsn(Opcodes.POP);
			super.visitInsn(Opcodes.ARRAYLENGTH);
			tagBranch();
			super.visitJumpInsn(Opcodes.IF_ICMPLT, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArrayIndexOutOfBoundsException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/ArrayIndexOutOfBoundsException", "<init>",
			                      "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget);
		} else if (opcode == Opcodes.IASTORE || opcode == Opcodes.BASTORE
		        || opcode == Opcodes.CASTORE || opcode == Opcodes.SASTORE
		        || opcode == Opcodes.AASTORE || opcode == Opcodes.LASTORE
		        || opcode == Opcodes.FASTORE || opcode == Opcodes.DASTORE) {
			Label origTarget = new Label();
			//Label origTarget = new AnnotatedLabel();
			//			origTarget.info = Boolean.FALSE;

			int loc = 0;
			if (opcode == Opcodes.IASTORE)
				loc = newLocal(Type.INT_TYPE);
			else if (opcode == Opcodes.BASTORE)
				loc = newLocal(Type.BYTE_TYPE);
			else if (opcode == Opcodes.CASTORE)
				loc = newLocal(Type.CHAR_TYPE);
			else if (opcode == Opcodes.SASTORE)
				loc = newLocal(Type.SHORT_TYPE);
			else if (opcode == Opcodes.AASTORE)
				loc = newLocal(Type.getType(Object.class));
			else if (opcode == Opcodes.LASTORE)
				loc = newLocal(Type.LONG_TYPE);
			else if (opcode == Opcodes.FASTORE)
				loc = newLocal(Type.FLOAT_TYPE);
			else if (opcode == Opcodes.DASTORE)
				loc = newLocal(Type.DOUBLE_TYPE);
			else
				throw new RuntimeException("Unknown type");
			storeLocal(loc);

			Label intermediateTarget = new Label();

			super.visitInsn(Opcodes.DUP);
			tagBranch();
			super.visitJumpInsn(Opcodes.IFGE, intermediateTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/NegativeArraySizeException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/NegativeArraySizeException", "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(intermediateTarget);

			super.visitInsn(Opcodes.DUP2);
			super.visitInsn(Opcodes.SWAP);
			//super.visitInsn(Opcodes.POP);
			super.visitInsn(Opcodes.ARRAYLENGTH);
			tagBranch();
			super.visitJumpInsn(Opcodes.IF_ICMPLT, origTarget);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArrayIndexOutOfBoundsException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL,
			                      "java/lang/ArrayIndexOutOfBoundsException", "<init>",
			                      "()V");
			super.visitInsn(Opcodes.ATHROW);

			// FIXXME: There is something wrong to require us to include
			// this workaround, but I can't find it.
			// origTarget should be an AnnotatedLabel!
			//AnnotatedLabel instrumentationOff = new AnnotatedLabel();
			//instrumentationOff.info = Boolean.FALSE;
			//super.visitLabel(instrumentationOff);
			super.visitLabel(origTarget);
			loadLocal(loc);
		}

		// Overflow checks
		switch (opcode) {
		case Opcodes.IADD:
		case Opcodes.ISUB:
		case Opcodes.IMUL:
			Label origTarget1 = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP2);
			super.visitLdcInsn(opcode);
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
			                      "de/unisb/cs/st/evosuite/javaagent/ErrorConditionChecker",
			                      "overflowDistance", "(III)I");

			tagBranch();
			super.visitJumpInsn(Opcodes.IFGT, origTarget1);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget1);
			break;
		case Opcodes.FADD:
		case Opcodes.FSUB:
		case Opcodes.FMUL:
			Label origTarget2 = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			super.visitInsn(Opcodes.DUP2);
			super.visitLdcInsn(opcode);
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
			                      "de/unisb/cs/st/evosuite/javaagent/ErrorConditionChecker",
			                      "overflowDistance", "(FFI)I");

			tagBranch();
			super.visitJumpInsn(Opcodes.IFGE, origTarget2);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget2);

			break;
		case Opcodes.DADD:
		case Opcodes.DSUB:
		case Opcodes.DMUL:
			Label origTarget3 = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			int loc = newLocal(Type.DOUBLE_TYPE);
			storeLocal(loc);
			super.visitInsn(Opcodes.DUP2);
			loadLocal(loc);
			super.visitInsn(Opcodes.DUP2_X2);
			super.visitLdcInsn(opcode);
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
			                      "de/unisb/cs/st/evosuite/javaagent/ErrorConditionChecker",
			                      "overflowDistance", "(DDI)I");

			tagBranch();
			super.visitJumpInsn(Opcodes.IFGE, origTarget3);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget3);
			break;

		case Opcodes.LADD:
		case Opcodes.LSUB:
		case Opcodes.LMUL:
			Label origTarget4 = new Label();
			// Label origTarget = new AnnotatedLabel();
			// origTarget.info = Boolean.FALSE;
			int loc2 = newLocal(Type.LONG_TYPE);
			storeLocal(loc2);
			super.visitInsn(Opcodes.DUP2);
			loadLocal(loc2);
			super.visitInsn(Opcodes.DUP2_X2);
			super.visitLdcInsn(opcode);
			super.visitMethodInsn(Opcodes.INVOKESTATIC,
			                      "de/unisb/cs/st/evosuite/javaagent/ErrorConditionChecker",
			                      "overflowDistance", "(JJI)I");

			tagBranch();
			super.visitJumpInsn(Opcodes.IFGE, origTarget4);
			super.visitTypeInsn(Opcodes.NEW, "java/lang/ArithmeticException");
			super.visitInsn(Opcodes.DUP);
			super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/ArithmeticException",
			                      "<init>", "()V");
			super.visitInsn(Opcodes.ATHROW);
			super.visitLabel(origTarget4);
			break;
		}
		// Check I*, D*, F*, L* for overflows

		super.visitInsn(opcode);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitCode()
	 */
	@Override
	public void visitCode() {
		MethodNode mn = (MethodNode) mv;
		try {
			Analyzer a = new Analyzer(new ThisInterpreter());
			a.analyze(className, mn);
			frames = a.getFrames();
		} catch (Exception e) {
			frames = new Frame[0];
		}
		super.visitCode();
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitEnd()
	 */
	@Override
	public void visitEnd() {
		MethodNode mn = (MethodNode) mv;
		mn.accept(next);
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.commons.LocalVariablesSorter#visitMaxs(int, int)
	 */
	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(maxStack + 4, maxLocals);
	}

}
