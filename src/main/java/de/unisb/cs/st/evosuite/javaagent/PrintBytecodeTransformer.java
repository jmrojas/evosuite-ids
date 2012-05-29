/**
 * Copyright (C) 2012 Gordon Fraser, Andrea Arcuri
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

import de.unisb.cs.st.evosuite.Properties;

/**
 * @author Gordon Fraser
 *
 */
public class PrintBytecodeTransformer implements ClassFileTransformer {

	private static String target_class = Properties.TARGET_CLASS;

	/* (non-Javadoc)
     * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
     */
    @Override
    public byte[] transform(ClassLoader loader, String className,
            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className != null) {
			try {
				String classNameWithDots = className.replace('/', '.');
				
				ClassReader reader = new ClassReader(classfileBuffer);
				ClassWriter writer = new ClassWriter(org.objectweb.asm.ClassWriter.COMPUTE_MAXS);

				ClassVisitor cv = writer;
				if(classNameWithDots.equals(target_class) || (classNameWithDots.startsWith(target_class+"$"))) {
//				if (classNameWithDots.startsWith(Properties.PROJECT_PREFIX)) {
					cv = new TraceClassVisitor(cv, new PrintWriter(System.out));
				}
				reader.accept(cv, ClassReader.SKIP_FRAMES);
				classfileBuffer = writer.toByteArray();
				
				return classfileBuffer;
				
			} catch (Throwable t) {
				StringWriter writer = new StringWriter();
				t.printStackTrace(new PrintWriter(writer));
				t.printStackTrace();
				System.exit(0);
			}
		}
		
    	return classfileBuffer;
    }



}
