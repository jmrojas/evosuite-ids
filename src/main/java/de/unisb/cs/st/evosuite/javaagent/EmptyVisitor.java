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
/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author fraser
 * 
 */
public class EmptyVisitor extends ClassVisitor {

	AnnotationVisitor av = new AnnotationVisitor(Opcodes.ASM4) {

		@Override
		public AnnotationVisitor visitAnnotation(String name, String desc) {
			return this;
		}

		@Override
		public AnnotationVisitor visitArray(String name) {
			return this;
		}
	};

	public EmptyVisitor() {
		super(Opcodes.ASM4);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return av;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
	        String signature, Object value) {
		return new FieldVisitor(Opcodes.ASM4) {

			@Override
			public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
				return av;
			}
		};
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
	        String signature, String[] exceptions) {
		return new MethodVisitor(Opcodes.ASM4) {

			@Override
			public AnnotationVisitor visitAnnotationDefault() {
				return av;
			}

			@Override
			public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
				return av;
			}

			@Override
			public AnnotationVisitor visitParameterAnnotation(int parameter, String desc,
			        boolean visible) {
				return av;
			}
		};
	}
}