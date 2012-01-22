package de.unisb.cs.st.evosuite.javaagent;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import de.unisb.cs.st.evosuite.utils.Utils;

/**
 * Visits all instructions in method, that could possibly contain references to
 * the classes used in the given class
 * 
 * @author Andrey Tarasevich
 * 
 */
public class CIMethodAdapter extends MethodVisitor {

	private final Set<String> classesReferenced = new HashSet<String>();

	public Set<String> getClassesReferenced() {
		return classesReferenced;
	}

	public CIMethodAdapter() {
		super(Opcodes.ASM4); //, new EmptyVisitor());
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		classesReferenced.addAll(Utils.classesDescFromString(owner));
		classesReferenced.addAll(Utils.classesDescFromString(desc));
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		classesReferenced.addAll(Utils.classesDescFromString(owner));
		classesReferenced.addAll(Utils.classesDescFromString(desc));
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature,
	        Label start, Label end, int index) {
		classesReferenced.addAll(Utils.classesDescFromString(desc));
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		classesReferenced.addAll(Utils.classesDescFromString(desc));
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		classesReferenced.addAll(Utils.classesDescFromString(type));
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		classesReferenced.addAll(Utils.classesDescFromString(type));
	}
}