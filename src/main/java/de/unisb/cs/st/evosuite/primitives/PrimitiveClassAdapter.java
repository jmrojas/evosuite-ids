/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite contributors
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
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unisb.cs.st.evosuite.primitives;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Gordon Fraser
 * 
 */
public class PrimitiveClassAdapter extends ClassVisitor {

	//private final String className;

	//private static String target_class = Properties.TARGET_CLASS;

	//private static final boolean REPLACE_STRING = Properties.STRING_REPLACEMENT
	//        && !Properties.TT;

	private final PrimitivePool primitive_pool = PrimitivePool.getInstance();

	/**
	 * @param arg0
	 */
	public PrimitiveClassAdapter(ClassVisitor visitor, String className) {
		super(Opcodes.ASM4, visitor);
		//this.className = className;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
	        String signature, Object value) {
		if (value instanceof String) {
			primitive_pool.add(value);
		}
		return super.visitField(access, name, desc, signature, value);
	}

	/*
	 * Set default access rights to public access rights
	 * 
	 * @see org.objectweb.asm.ClassAdapter#visitMethod(int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public MethodVisitor visitMethod(int methodAccess, String name, String descriptor,
	        String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(methodAccess, name, descriptor, signature,
		                                     exceptions);

		/*
		String classNameWithDots = className.replace('/', '.');
		if (REPLACE_STRING
		        && (classNameWithDots.equals(target_class) || (classNameWithDots.startsWith(target_class
		                + "$")))) {
			mv = new StringReplacementMethodAdapter(methodAccess, descriptor, mv);
		}
		*/
		mv = new PrimitivePoolMethodAdapter(mv);

		return mv;
	}
}
