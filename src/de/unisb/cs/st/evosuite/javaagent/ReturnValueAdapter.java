/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.javaagent;

import org.apache.log4j.Logger;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Instrument classes to trace return values
 * 
 * @author Gordon Fraser
 *
 */
public class ReturnValueAdapter extends MethodAdapter {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(LineNumberMethodAdapter.class);
	
	private String fullMethodName;

	protected String className;

	protected String methodName;

	public ReturnValueAdapter(MethodVisitor mv, String className,
			String methodName, String desc) {
		super(mv);
		fullMethodName = methodName + desc;
		this.methodName = methodName;
		this.className = className;
	}

	// primitive data types
	private enum PDType { LONG, INTEGER, FLOAT, DOUBLE };

	public void visitInsn(int opcode) {
		if (!methodName.equals("<clinit>")) {
			switch (opcode) {
			case Opcodes.IRETURN:
				callLogIReturn();
				break;
			case Opcodes.ARETURN:
				callLogAReturn();
				break;
			case Opcodes.ATHROW:
				break;
			case Opcodes.DRETURN:
				callLogDReturn();
				break;
			case Opcodes.FRETURN:
				callLogFReturn();
				break;
			case Opcodes.LRETURN:
				callLogLReturn();
				break;
			case Opcodes.RETURN:
				break;
			default:
				break;
			}
		}
		super.visitInsn(opcode);

	}
	
	private void callLogPrototype(String traceMethod, PDType type) {
		if (type != PDType.LONG && type != PDType.DOUBLE) {
			this.visitInsn(Opcodes.DUP);
			if (type == PDType.FLOAT) {
				this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "floatToRawIntBits", "(F)I");
			}
		} else {
			this.visitInsn(Opcodes.DUP2);
			if (type == PDType.DOUBLE) {
				this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "doubleToRawLongBits", "(D)J");
			}
			this.visitInsn(Opcodes.DUP2);
			this.visitIntInsn(Opcodes.BIPUSH, 32);
			this.visitInsn(Opcodes.LSHR);
			this.visitInsn(Opcodes.LXOR);
			this.visitInsn(Opcodes.L2I);
		}

		this.visitLdcInsn(className);
		this.visitLdcInsn(fullMethodName);
		this.visitMethodInsn(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
				"returnValue", "(ILjava/lang/String;Ljava/lang/String;)V");		
	}

	private void callLogIReturn() {
		callLogPrototype("logIReturn", PDType.INTEGER);
	}

	private void callLogAReturn() {
		this.visitInsn(Opcodes.DUP);
		this.visitLdcInsn(className);
		this.visitLdcInsn(fullMethodName);
		this.visitMethodInsn(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
				"returnValue", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V");
	}


	private void callLogLReturn() {
		callLogPrototype("logLReturn", PDType.LONG);
	}

	private void callLogDReturn() {
		callLogPrototype("logDReturn", PDType.DOUBLE);
	}

	private void callLogFReturn() {
		callLogPrototype("logFReturn", PDType.FLOAT);
	}

}
