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

package de.unisb.cs.st.evosuite.javaagent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;

/**
 * Take care of all instrumentation that is necessary to trace executions
 * 
 * @author Gordon Fraser
 * 
 */
public class ExecutionPathClassAdapter extends ClassVisitor {

	private final String className;

	private static boolean isMutation() {
		return Properties.CRITERION == Criterion.MUTATION
		        || Properties.CRITERION == Criterion.STRONGMUTATION
		        || Properties.CRITERION == Criterion.WEAKMUTATION;
	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ExecutionPathClassAdapter.class);

	public ExecutionPathClassAdapter(ClassVisitor visitor, String className) {
		super(Opcodes.ASM4, visitor);
		this.className = className.replace('/', '.');
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

		// Don't touch bridge and synthetic methods
		if ((methodAccess & Opcodes.ACC_SYNTHETIC) > 0
		        || (methodAccess & Opcodes.ACC_BRIDGE) > 0) {
			return mv;
		}
		if (name.equals("<clinit>"))
			return mv;

		if (isMutation()) {
			mv = new ReturnValueAdapter(mv, className, name, descriptor);
		}
		mv = new MethodEntryAdapter(mv, methodAccess, className, name, descriptor);
		mv = new LineNumberMethodAdapter(mv, className, name, descriptor);
		mv = new ExplicitExceptionHandler(mv, className, name, descriptor);
		return mv;
	}

}
