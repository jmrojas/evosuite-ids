/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.cfg;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;

/**
 * The CFGClassAdapter calls a CFG generator for relevant methods
 * 
 * @author Gordon Fraser
 * 
 */
public class CFGClassAdapter extends ClassVisitor {

	private static Logger logger = LoggerFactory.getLogger(CFGClassAdapter.class);

	/** Current class */
	private final String className;

	/** Skip methods on enums - at least some */
	private boolean isEnum = false;

	/**
	 * Constructor
	 * 
	 * @param visitor
	 * @param className
	 */
	public CFGClassAdapter(ClassVisitor visitor, String className) {
		super(Opcodes.ASM4, visitor);
		this.className = className;
	}

	/* (non-Javadoc)
	 * @see org.objectweb.asm.ClassAdapter#visit(int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void visit(int version, int access, String name, String signature,
	        String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		if (superName.equals("java/lang/Enum"))
			isEnum = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.objectweb.asm.ClassAdapter#visitMethod(int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public MethodVisitor visitMethod(int methodAccess, String name, String descriptor,
	        String signature, String[] exceptions) {

		MethodVisitor mv = super.visitMethod(methodAccess, name, descriptor, signature,
		                                     exceptions);

		if ((methodAccess & Opcodes.ACC_SYNTHETIC) > 0
		        || (methodAccess & Opcodes.ACC_BRIDGE) > 0) {
			return mv;
		}

		if (!Properties.USE_DEPRECATED
		        && (methodAccess & Opcodes.ACC_DEPRECATED) == Opcodes.ACC_DEPRECATED) {
			logger.info("Skipping deprecated method " + name);
			return mv;
		}

		if (isEnum && (name.equals("valueOf") || name.equals("values"))) {
			logger.info("Skipping enum valueOf");
			return mv;
		}

		String classNameWithDots = className.replace('/', '.');

		mv = new CFGMethodAdapter(classNameWithDots, methodAccess, name, descriptor,
		        signature, exceptions, mv);
		/*
				if (!exclude) {
					if(false) {
						MutationForRun mm = MutationForRun.getFromDefaultLocation();
						mv = new CFGMethodAdapter(className, methodAccess, name, descriptor, signature, exceptions, mv, mm.getMutations());
					}
					else {
						mv = new CFGMethodAdapter(className, methodAccess, name, descriptor, signature, exceptions, mv, new ArrayList<Mutation>());
					}
				}
				*/
		return mv;
	}
}
