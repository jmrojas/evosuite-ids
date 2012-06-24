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
 */
/**
 * 
 */
package de.unisb.cs.st.evosuite.cfg.instrumentation;

import org.objectweb.asm.tree.MethodNode;


/**
 * An interface which criteria (like defUse, concurrency, LCSAJs) can use to instrument the code of methods
 * @author Sebastian Steenbuck
 *
 */
public interface MethodInstrumentation {
	
	/**
	 * 
	 * @param mn the ASM Node of the method
	 * @param graph the current CFG
	 * @param className the name of current class
	 * @param methodName the name of the current method. This name includes the description of the method and is therefore unique per class.
	 * @param access the access of the current method (see org.objectweb.asm.ClassAdapter#visitMethod(int access, String name,
	        String descriptor, String signature, String[] exceptions))
	 */
	public void analyze(MethodNode mn, String className, String methodName, int access);
	
	/**
	 * If this method returns true, the analyze method is also called on 
	 * public static void main() methods
	 * @return
	 */
	public boolean executeOnMainMethod();
	
	/**
	 * if this method returns true the analyze method is also called on methods which are excluded in
	 * CFGMethodAdapter.EXCLUDE
	 * @return
	 */
	public boolean executeOnExcludedMethods();
	
}
