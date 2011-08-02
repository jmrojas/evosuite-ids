/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import java.util.List;
import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fraser
 * 
 */
public class StringTransformation {

	private static Logger logger = LoggerFactory.getLogger(StringTransformation.class);

	ClassNode cn;

	public StringTransformation(ClassNode cn) {
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

	/**
	 * Replace boolean-returning method calls on String classes
	 * 
	 * @param mn
	 */
	@SuppressWarnings("unchecked")
	private void transformStrings(MethodNode mn) {
		ListIterator<AbstractInsnNode> iterator = mn.instructions.iterator();
		while (iterator.hasNext()) {
			AbstractInsnNode node = iterator.next();
			if (node instanceof MethodInsnNode) {
				MethodInsnNode min = (MethodInsnNode) node;
				if (min.owner.equals("java/lang/String")) {
					if (min.name.equals("equals")) {
						MethodInsnNode equalCheck = new MethodInsnNode(
						        Opcodes.INVOKESTATIC,
						        Type.getInternalName(BooleanHelper.class),
						        "StringEquals",
						        Type.getMethodDescriptor(Type.INT_TYPE,
						                                 new Type[] {
						                                         Type.getType(String.class),
						                                         Type.getType(Object.class) }));
						mn.instructions.insertBefore(node, equalCheck);
						mn.instructions.remove(node);

					} else if (min.name.equals("equalsIgnoreCase")) {
						MethodInsnNode equalCheck = new MethodInsnNode(
						        Opcodes.INVOKESTATIC,
						        Type.getInternalName(BooleanHelper.class),
						        "StringEqualsIgnoreCase",
						        Type.getMethodDescriptor(Type.INT_TYPE,
						                                 new Type[] {
						                                         Type.getType(String.class),
						                                         Type.getType(String.class) }));
						mn.instructions.insertBefore(node, equalCheck);
						mn.instructions.remove(node);

					} else if (min.name.equals("startsWith")) {
						if (min.desc.equals("(Ljava/lang/String;)Z")) {
							mn.instructions.insertBefore(node, new InsnNode(
							        Opcodes.ICONST_0));
						}
						MethodInsnNode equalCheck = new MethodInsnNode(
						        Opcodes.INVOKESTATIC,
						        Type.getInternalName(BooleanHelper.class),
						        "StringStartsWith",
						        Type.getMethodDescriptor(Type.INT_TYPE,
						                                 new Type[] {
						                                         Type.getType(String.class),
						                                         Type.getType(String.class),
						                                         Type.INT_TYPE }));
						mn.instructions.insertBefore(node, equalCheck);
						mn.instructions.remove(node);

					} else if (min.name.equals("endsWith")) {
						MethodInsnNode equalCheck = new MethodInsnNode(
						        Opcodes.INVOKESTATIC,
						        Type.getInternalName(BooleanHelper.class),
						        "StringEndsWith",
						        Type.getMethodDescriptor(Type.INT_TYPE,
						                                 new Type[] {
						                                         Type.getType(String.class),
						                                         Type.getType(String.class) }));
						mn.instructions.insertBefore(node, equalCheck);
						mn.instructions.remove(node);

					} else if (min.name.equals("isEmpty")) {
						MethodInsnNode equalCheck = new MethodInsnNode(
						        Opcodes.INVOKESTATIC,
						        Type.getInternalName(BooleanHelper.class),
						        "StringIsEmpty",
						        Type.getMethodDescriptor(Type.INT_TYPE,
						                                 new Type[] { Type.getType(String.class) }));
						mn.instructions.insertBefore(node, equalCheck);
						mn.instructions.remove(node);

					} else if (min.name.equals("regionMatches")) {
						// TODO
					}

				}
			}
		}
	}

	public void transformMethod(MethodNode mn) {
		transformStrings(mn);
		try {
			Analyzer a = new Analyzer(new StringBooleanInterpreter());
			a.analyze(cn.name, mn);
			Frame[] frames = a.getFrames();
			AbstractInsnNode node = mn.instructions.getFirst();
			while (node != mn.instructions.getLast()) {
				AbstractInsnNode next = node.getNext();
				Frame current = frames[mn.instructions.indexOf(node)];
				int size = current.getStackSize();
				if (node.getOpcode() == Opcodes.IFNE) {
					JumpInsnNode branch = (JumpInsnNode) node;
					if (current.getStack(size - 1) == StringBooleanInterpreter.STRING_BOOLEAN) {
						logger.info("IFNE -> IFGT");
						branch.setOpcode(Opcodes.IFGT);
					}
				} else if (node.getOpcode() == Opcodes.IFEQ) {
					JumpInsnNode branch = (JumpInsnNode) node;
					if (current.getStack(size - 1) == StringBooleanInterpreter.STRING_BOOLEAN) {
						logger.info("IFEQ -> IFLE");
						branch.setOpcode(Opcodes.IFLE);
					}
				}
				node = next;
			}
		} catch (Exception e) {

			return;
		}
	}
}
