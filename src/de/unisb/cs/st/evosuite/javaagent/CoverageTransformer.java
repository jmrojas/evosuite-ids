/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.javaagent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import de.unisb.cs.st.evosuite.cfg.CFGClassAdapter;
import de.unisb.st.bytecodetransformer.processFiles.BytecodeTransformer;

/**
 * Instrument classes to extract a CFG necessary for mot types of coverage
 * 
 * @author Gordon Fraser
 *
 */
public class CoverageTransformer extends BytecodeTransformer {

	String className = "";

	@Override
	protected ClassVisitor classVisitorFactory(ClassWriter arg) {
		ClassVisitor cv = new CFGClassAdapter(arg, className);
		return cv; 
	}

}
