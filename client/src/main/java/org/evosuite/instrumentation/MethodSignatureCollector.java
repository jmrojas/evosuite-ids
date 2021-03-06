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
package org.evosuite.instrumentation;

import org.evosuite.junit.naming.variables.VariableNameCollector;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gordon on 22/12/2015.
 */
public class MethodSignatureCollector extends MethodVisitor {

    private String className;

    private String methodName;

    private String methodDesc;

    private int numParams;

    private int startIndex;

    private int endIndex;

    private boolean isStatic;

    private static final Logger logger = LoggerFactory.getLogger(MethodSignatureCollector.class);

    public MethodSignatureCollector(MethodVisitor mv, String className, String methodName, String desc, boolean isStatic) {
        super(Opcodes.ASM5, mv);
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = desc;
        numParams = Type.getArgumentTypes(desc).length;
        this.isStatic = isStatic;
        if(!isStatic) {
            startIndex = 1;
        }
        endIndex = startIndex + numParams;
    }

    /* (non-Javadoc)
     * @see org.objectweb.asm.commons.LocalVariablesSorter#visitLocalVariable(java.lang.String, java.lang.String, java.lang.String, org.objectweb.asm.Label, org.object
     */
    @Override
    public void visitLocalVariable(String name, String desc, String signature,
                                   Label start, Label end, int index) {
        if(index >= startIndex && index < endIndex) {
            logger.debug("Collecting name for parameter {} of method {}: {}", index, methodName, name);
            VariableNameCollector.getInstance().addParameterName(className.replace('/', '.'), methodName+methodDesc, index, name);
        }
        VariableNameCollector.getInstance().addVariableName(Type.getType(desc).getClassName().replace('/', '.'), name);

        super.visitLocalVariable(name, desc, signature, start, end, index);
    }
}
