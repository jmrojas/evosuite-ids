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
package org.evosuite.instrumentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.evosuite.Properties;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

/**
 * <p>
 * MethodCallReplacementMethodAdapter class.
 * </p>
 * 
 * @author fraser
 */
public class MethodCallReplacementMethodAdapter extends GeneratorAdapter {

	private class MethodCallReplacement {
		private final String className;
		private final String methodName;
		private final String desc;

		private final String replacementClassName;
		private final String replacementMethodName;
		private final String replacementDesc;

		private final boolean popCallee;

		public MethodCallReplacement(String className, String methodName, String desc,
		        String replacementClassName, String replacementMethodName,
		        String replacementDesc, boolean pop) {
			this.className = className;
			this.methodName = methodName;
			this.desc = desc;
			this.replacementClassName = replacementClassName;
			this.replacementMethodName = replacementMethodName;
			this.replacementDesc = replacementDesc;
			this.popCallee = pop;
		}

		public boolean isTarget(String owner, String name, String desc) {
			return className.equals(owner) && methodName.equals(name)
			        && this.desc.equals(desc);
		}

		public void insertMethodCall(MethodVisitor mv, int opcode) {
			if (popCallee) {
				Type[] args = Type.getArgumentTypes(desc);
				Map<Integer, Integer> to = new HashMap<Integer, Integer>();
				for (int i = args.length - 1; i >= 0; i--) {
					int loc = newLocal(args[i]);
					storeLocal(loc);
					to.put(i, loc);
				}

				pop();//callee

				for (int i = 0; i < args.length; i++) {
					loadLocal(to.get(i));
				}
			}
			mv.visitMethodInsn(opcode, replacementClassName, replacementMethodName,
			                   replacementDesc);
		}
	}

	/**
	 * method replacements, which are called with Opcodes.INVOKESTATIC
	 */
	private final Set<MethodCallReplacement> replacementCalls = new HashSet<MethodCallReplacement>();

	/**
	 * method replacements, which are called with Opcodes.INVOKEVIRTUAL
	 */
	private final Set<MethodCallReplacement> virtualReplacementCalls = new HashSet<MethodCallReplacement>();

	/**
	 * <p>
	 * Constructor for MethodCallReplacementMethodAdapter.
	 * </p>
	 * 
	 * @param mv
	 *            a {@link org.objectweb.asm.MethodVisitor} object.
	 * @param className
	 *            a {@link java.lang.String} object.
	 * @param methodName
	 *            a {@link java.lang.String} object.
	 * @param access
	 *            a int.
	 * @param desc
	 *            a {@link java.lang.String} object.
	 */
	public MethodCallReplacementMethodAdapter(MethodVisitor mv, String className,
	        String methodName, int access, String desc) {
		super(Opcodes.ASM4, mv, access, methodName, desc);
		if (Properties.REPLACE_CALLS) {
			replacementCalls.add(new MethodCallReplacement("java/lang/System", "exit",
			        "(I)V", "org/evosuite/runtime/System", "exit", "(I)V", false));
			replacementCalls.add(new MethodCallReplacement("java/lang/System",
			        "currentTimeMillis", "()J", "org/evosuite/runtime/System",
			        "currentTimeMillis", "()J", false));

			replacementCalls.add(new MethodCallReplacement("java/util/Date", "<init>",
			        "()V", "org/evosuite/runtime/Date", "getDate", "()Ljava/util/Date;",
			        false));

			replacementCalls.add(new MethodCallReplacement("java/util/Calendar",
			        "getInstance", "()Ljava/util/Calendar;",
			        "org/evosuite/runtime/Calendar", "getCalendar",
			        "()Ljava/util/Calendar;", false));

			replacementCalls.add(new MethodCallReplacement("java/util/Calendar",
			        "getInstance", "(Ljava/util/Locale;)Ljava/util/Calendar;",
			        "org/evosuite/runtime/Calendar", "getCalendar",
			        "(Ljava/util/Locale;)Ljava/util/Calendar;", false));

			replacementCalls.add(new MethodCallReplacement("java/util/Calendar",
			        "getInstance", "(Ljava/util/TimeZone;)Ljava/util/Calendar;",
			        "org/evosuite/runtime/Calendar", "getCalendar",
			        "(Ljava/util/TimeZone;)Ljava/util/Calendar;", false));

			replacementCalls.add(new MethodCallReplacement("java/util/Calendar",
			        "getInstance",
			        "(Ljava/util/TimeZone;Ljava/util/Locale;)Ljava/util/Calendar;",
			        "org/evosuite/runtime/Calendar", "getCalendar",
			        "(Ljava/util/TimeZone;Ljava/util/Locale;)Ljava/util/Calendar;", false));

			replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextInt",
			        "()I", "org/evosuite/runtime/Random", "nextInt", "()I", true));
			replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextInt",
			        "(I)I", "org/evosuite/runtime/Random", "nextInt", "(I)I", true));
			replacementCalls.add(new MethodCallReplacement("java/util/Random",
			        "nextDouble", "()D", "org/evosuite/runtime/Random", "nextDouble",
			        "()D", true));
			replacementCalls.add(new MethodCallReplacement("java/lang/Math", "random",
			        "()D", "org/evosuite/runtime/Random", "nextDouble", "()D", false));
			replacementCalls.add(new MethodCallReplacement("java/util/Random",
			        "nextFloat", "()F", "org/evosuite/runtime/Random", "nextFloat",
			        "()F", true));
			replacementCalls.add(new MethodCallReplacement("java/util/Random",
			        "nextLong", "()J", "org/evosuite/runtime/Random", "nextLong", "()J",
			        true));
		}
		if (Properties.VIRTUAL_FS) {
			virtualReplacementCalls.add(new MethodCallReplacement(
			        "java/io/FileInputStream", "available", "()I",
			        "java/io/FileInputStream", "availableNew", "()I", false));
			virtualReplacementCalls.add(new MethodCallReplacement(
			        "java/io/FileInputStream", "skip", "(J)J", "java/io/FileInputStream",
			        "skipNew", "(J)J", false));
		}
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitMethodInsn(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		boolean isReplaced = false;
		// static replacement methods
		for (MethodCallReplacement replacement : replacementCalls) {
			if (replacement.isTarget(owner, name, desc)) {
				isReplaced = true;
				replacement.insertMethodCall(this, Opcodes.INVOKESTATIC);
				break;
			}
		}
		// non-static replacement methods
		for (MethodCallReplacement replacement : virtualReplacementCalls) {
			if (replacement.isTarget(owner, name, desc)) {
				isReplaced = true;
				replacement.insertMethodCall(this, Opcodes.INVOKEVIRTUAL);
				break;
			}
		}
		if (!isReplaced)
			super.visitMethodInsn(opcode, owner, name, desc);
	}
}
