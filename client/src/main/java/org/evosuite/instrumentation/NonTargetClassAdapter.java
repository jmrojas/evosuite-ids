/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.evosuite.instrumentation;

import org.evosuite.runtime.instrumentation.RemoveFinalClassAdapter;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.InheritanceTree;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.JSRInlinerAdapter;

/**
 * <p>NonTargetClassAdapter class.</p>
 *
 * @author Gordon Fraser
 */
public class NonTargetClassAdapter extends ClassVisitor {

	private final String className;

	private boolean isJUnit3TestCase = false;

	/**
	 * <p>Constructor for NonTargetClassAdapter.</p>
	 *
	 * @param cv a {@link org.objectweb.asm.ClassVisitor} object.
	 * @param className a {@link java.lang.String} object.
	 */
	public NonTargetClassAdapter(ClassVisitor cv, String className) {
		super(Opcodes.ASM5, cv);
		this.className = className;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if((access & Opcodes.ACC_FINAL) == Opcodes.ACC_FINAL) {
			RemoveFinalClassAdapter.finalClasses.add(name.replace('/', '.'));
		}

        String classNameWithDots = name.replace('/', '.');
        InheritanceTree inheritanceTree = DependencyAnalysis.getInheritanceTree();
        if(inheritanceTree != null && inheritanceTree.hasClass(classNameWithDots)) {
            if(inheritanceTree.getSuperclasses(classNameWithDots).contains(junit.framework.TestCase.class.getCanonicalName())) {
                isJUnit3TestCase = true;
            }
        }

		// We are removing final access to allow mocking
		super.visit(version, access & ~Opcodes.ACC_FINAL, name, signature, superName, interfaces);
	}
	
	/** {@inheritDoc} */
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
	        String signature, final String[] exceptions) {

		MethodVisitor mv = super.visitMethod(access & ~Opcodes.ACC_FINAL, name, desc, signature, exceptions);
		mv = new JSRInlinerAdapter(mv, access, name, desc, signature, exceptions);
		mv = new MethodSignatureCollector(mv, className, name, desc, (access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC);
		if(!"<clinit>".equals(name)) {
			mv = new YieldAtLineNumberMethodAdapter(mv, className, name);
			mv = new JUnitCoverageMethodAdapter(mv, access, className, name, desc, isJUnit3TestCase);
		}
		return mv; //new ArrayAllocationLimitMethodAdapter(mv, className, name, access, desc);
	}
	
	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if((access & Opcodes.ACC_FINAL) == Opcodes.ACC_FINAL) {
			RemoveFinalClassAdapter.finalClasses.add(name.replace('/', '.'));
		}
		// We are removing final access to allow mocking
		super.visitInnerClass(name, outerName, innerName, access & ~Opcodes.ACC_FINAL);
	}
}
