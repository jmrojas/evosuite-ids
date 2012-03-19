/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

/**
 * @author fraser
 * 
 */
public class RemoveNondeterministicMethodAdapter extends GeneratorAdapter {

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
			// Currently assume that the methods take identical params 
			assert (desc.equals(replacementDesc));
		}

		public boolean isTarget(String owner, String name, String desc) {
			return className.equals(owner) && methodName.equals(name)
			        && this.desc.equals(desc);
		}

		public void insertMethodCall(MethodVisitor mv) {
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
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, replacementClassName,
			                   replacementMethodName, replacementDesc);
		}
	}

	private final Set<MethodCallReplacement> replacementCalls = new HashSet<MethodCallReplacement>();

	/**
	 * @param api
	 */
	public RemoveNondeterministicMethodAdapter(MethodVisitor mv, String className,
	        String methodName, int access, String desc) {
		super(Opcodes.ASM4, mv, access, methodName, desc);
		replacementCalls.add(new MethodCallReplacement("java/lang/System", "exit",
		        "(I)V", "de/unisb/cs/st/evosuite/runtime/System", "exit", "(I)V", false));
		replacementCalls.add(new MethodCallReplacement("java/lang/System",
		        "currentTimeMillis", "()J", "de/unisb/cs/st/evosuite/runtime/System",
		        "currentTimeMillis", "()J", false));
		replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextInt",
		        "()I", "de/unisb/cs/st/evosuite/runtime/Random", "nextInt", "()I", true));
		replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextInt",
		        "(I)I", "de/unisb/cs/st/evosuite/runtime/Random", "nextInt", "(I)I", true));
		replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextDouble",
		        "()D", "de/unisb/cs/st/evosuite/runtime/Random", "nextDouble", "()D",
		        true));
		replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextFloat",
		        "()F", "de/unisb/cs/st/evosuite/runtime/Random", "nextFloat", "()F", true));
		replacementCalls.add(new MethodCallReplacement("java/util/Random", "nextLong",
		        "()J", "de/unisb/cs/st/evosuite/runtime/Random", "nextLong", "()J", true));
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.MethodVisitor#visitMethodInsn(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		boolean isReplaced = false;
		for (MethodCallReplacement replacement : replacementCalls) {
			if (replacement.isTarget(owner, name, desc)) {
				isReplaced = true;
				replacement.insertMethodCall(this);
				break;
			}
		}
		if (!isReplaced)
			super.visitMethodInsn(opcode, owner, name, desc);
	}
}
