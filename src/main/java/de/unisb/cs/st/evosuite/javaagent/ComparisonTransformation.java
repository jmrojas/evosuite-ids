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
 * @author Gordon Fraser
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
					TransformationStatistics.transformedComparison();
				} else if (in.getOpcode() == Opcodes.DCMPG) {
					// TODO: Check treatment of NaN
					TransformationStatistics.transformedComparison();
					insertDoubleComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.DCMPL) {
					TransformationStatistics.transformedComparison();
					insertDoubleComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.FCMPG) {
					TransformationStatistics.transformedComparison();
					insertFloatComparison(in, mn.instructions);
				} else if (in.getOpcode() == Opcodes.FCMPL) {
					TransformationStatistics.transformedComparison();
					insertFloatComparison(in, mn.instructions);
				}
			}
			node = next;
		}
	}

	private void insertLongComparison(AbstractInsnNode position, InsnList list) {
		MethodInsnNode get = new MethodInsnNode(Opcodes.INVOKESTATIC,
		        Type.getInternalName(BooleanHelper.class), "longSub",
		        Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.LONG_TYPE,
		                Type.LONG_TYPE }));
		list.insert(position, get);
		list.remove(position);
	}

	private void insertFloatComparison(AbstractInsnNode position, InsnList list) {
		MethodInsnNode get = new MethodInsnNode(Opcodes.INVOKESTATIC,
		        Type.getInternalName(BooleanHelper.class), "floatSub",
		        Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.FLOAT_TYPE,
		                Type.FLOAT_TYPE }));
		list.insert(position, get);
		list.remove(position);
	}

	private void insertDoubleComparison(AbstractInsnNode position, InsnList list) {
		MethodInsnNode get = new MethodInsnNode(Opcodes.INVOKESTATIC,
		        Type.getInternalName(BooleanHelper.class), "doubleSub",
		        Type.getMethodDescriptor(Type.INT_TYPE, new Type[] { Type.DOUBLE_TYPE,
		                Type.DOUBLE_TYPE }));
		list.insert(position, get);
		list.remove(position);
	}
}
