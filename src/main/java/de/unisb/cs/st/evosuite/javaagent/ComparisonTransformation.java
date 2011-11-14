/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author fraser
 * 
 */
public class ComparisonTransformation {

	ClassNode cn;

	public ComparisonTransformation(ClassNode cn) {
		this.cn = cn;
	}

	@SuppressWarnings("unchecked")
	public ClassNode transform() {
		List<MethodNode> methodNodes = cn.methods;
		for (MethodNode mn : methodNodes) {
			transformMethod(mn);
		}
		return cn;
	}

	public void transformMethod(MethodNode mn) {
		AbstractInsnNode node = mn.instructions.getFirst();
		while (node != mn.instructions.getLast()) {
			AbstractInsnNode next = node.getNext();
			if (node instanceof InsnNode) {
				InsnNode in = (InsnNode) node;
				if (in.getOpcode() == Opcodes.LCMP) {
					insertLongComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.DCMPG) {
					insertDoubleComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.DCMPL) {
					insertDoubleComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.FCMPG) {
					insertFloatComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.FCMPL) {
					insertFloatComparison(in, mn.instructions);
				}
			}
			node = next;
		}
	}

	private void insertLongComparison(AbstractInsnNode position, InsnList list) {
		list.insertBefore(position, new InsnNode(Opcodes.LSUB));
		MethodInsnNode get = new MethodInsnNode(Opcodes.INVOKESTATIC,
		        Type.getInternalName(BooleanHelper.class), "fromLong",
		        Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.LONG_TYPE }));
		list.insert(position, get);
		list.remove(position);
	}

	private void insertFloatComparison(AbstractInsnNode position, InsnList list) {
		list.insertBefore(position, new InsnNode(Opcodes.FSUB));
		MethodInsnNode get = new MethodInsnNode(Opcodes.INVOKESTATIC,
		        Type.getInternalName(BooleanHelper.class), "fromFloat",
		        Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.FLOAT_TYPE }));
		list.insert(position, get);
		list.remove(position);
	}

	private void insertDoubleComparison(AbstractInsnNode position, InsnList list) {
		list.insertBefore(position, new InsnNode(Opcodes.DSUB));
		MethodInsnNode get = new MethodInsnNode(Opcodes.INVOKESTATIC,
		        Type.getInternalName(BooleanHelper.class), "fromDouble",
		        Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.DOUBLE_TYPE }));
		list.insert(position, get);
		list.remove(position);
	}
}