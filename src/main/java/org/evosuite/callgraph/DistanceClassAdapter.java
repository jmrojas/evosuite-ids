
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
 *
 * @author Gordon Fraser
 */
package org.evosuite.callgraph;

import java.util.Set;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
public class DistanceClassAdapter extends ClassVisitor {

	private String className;
	private final ConnectionData connectionData;
	private final Set<String> packageClasses;

	/**
	 * <p>Constructor for DistanceClassAdapter.</p>
	 *
	 * @param cv a {@link org.objectweb.asm.ClassVisitor} object.
	 * @param connectionData a {@link org.evosuite.callgraph.ConnectionData} object.
	 * @param packageClasses a {@link java.util.Set} object.
	 */
	public DistanceClassAdapter(ClassVisitor cv, ConnectionData connectionData,
	        Set<String> packageClasses) {
		super(Opcodes.ASM4, cv);
		this.connectionData = connectionData;
		this.packageClasses = packageClasses;
	}

	/** {@inheritDoc} */
	@Override
	public void visit(int version, int access, String name, String signature,
	        String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		this.className = name;
	}

	/** {@inheritDoc} */
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
	        String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
		return new DistanceMethodAdapter(mv, className, name, desc, connectionData,
		        packageClasses);
	}
}
