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

import org.evosuite.PackageInfo;
import org.evosuite.Properties;
import org.evosuite.coverage.method.JUnitObserver;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.InheritanceTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by gordon on 01/01/2016.
 */
public class JUnitCoverageMethodAdapter  extends GeneratorAdapter {

    private static Logger logger = LoggerFactory.getLogger(JUnitCoverageMethodAdapter.class);

    private final String fullMethodName;

    private final String methodName;

    private final String className;

    private boolean isJUnitTest = false;

    private Set<String> subClasses = new LinkedHashSet<>();

    private Set<String> superClasses = new LinkedHashSet<>();

    /**
     * <p>Constructor for LineNumberMethodAdapter.</p>
     *
     * @param mv a {@link org.objectweb.asm.MethodVisitor} object.
     * @param className a {@link java.lang.String} object.
     * @param methodName a {@link java.lang.String} object.
     * @param desc a {@link java.lang.String} object.
     */
    public JUnitCoverageMethodAdapter(MethodVisitor mv, int access, String className, String methodName,
                                   String desc, boolean isTestClass) {
        super(Opcodes.ASM5, mv, access, methodName, desc);
        fullMethodName = methodName + desc;
        this.className = className;
        this.methodName = methodName;
        if(isTestClass) {
            // JUnit 3 test
            if(methodName.startsWith("test") || methodName.equals("setUp") || methodName.equals("tearDown"))
                this.isJUnitTest = true;
        }
        InheritanceTree tree = DependencyAnalysis.getInheritanceTree();
        if(tree != null) {
            if(tree.hasClass(Properties.TARGET_CLASS)) {
                subClasses.addAll(tree.getSubclasses(Properties.TARGET_CLASS));
                superClasses.addAll(tree.getSuperclasses(Properties.TARGET_CLASS));
            }
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if(Type.getDescriptor(Test.class).equals(desc) ||
                Type.getDescriptor(Before.class).equals(desc) ||
                Type.getDescriptor(After.class).equals(desc)
                ) {
            logger.debug("Method "+className+"."+fullMethodName+" has JUnit annotation: "+desc);
            isJUnitTest = true;
        }

        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if(!isJUnitTest) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }

        String classNameWithDots = owner.replace('/', '.');
        String instrumentedOwner = owner;
        if(!DependencyAnalysis.shouldAnalyze(classNameWithDots)) {
            if(subClasses.contains(classNameWithDots)) {
                logger.info("Using target class instead of subclass");
                // The reason for this hack is that manually written tests often use dummy-subclasses and then we would miss coverage
                instrumentedOwner = Properties.TARGET_CLASS.replace('.', '/');
            } else if(superClasses.contains(classNameWithDots)) {
                // Can be a virtual call, so is ok
                instrumentedOwner = Properties.TARGET_CLASS.replace('.', '/');
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
                return;
            }

        }
        Label startLabel = mark();
        Type[] argumentTypes = Type.getArgumentTypes(desc);
        int[] locals = new int[argumentTypes.length];

        for (int i = argumentTypes.length - 1; i >= 0; i--) {
            int local = newLocal(argumentTypes[i]);
            storeLocal(local, argumentTypes[i]);
            locals[i] = local;
        }
        if (opcode == Opcodes.INVOKESPECIAL) {
            // dup(); // for return value
            push((String)null);
        }
        else if (opcode == Opcodes.INVOKESTATIC) {
            push((String)null);
        } else {
            dup(); // Callee
        }
        push(opcode);
        push(instrumentedOwner);
        push(name);
        push(desc);
        push(argumentTypes.length);
        Type objectType = Type.getObjectType("java/lang/Object");
        newArray(objectType);
        for (int i = 0; i < argumentTypes.length; i++) {
            dup();
            push(i);
            loadLocal(locals[i]);
            box(argumentTypes[i]);
            arrayStore(objectType);
        }

        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                PackageInfo.getNameWithSlash(JUnitObserver.class),
                "methodCalled",
                "(Ljava/lang/Object;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);

        for (int i = 0; i < argumentTypes.length; i++) {
            loadLocal(locals[i]);
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);

        Label l = newLabel();
        goTo(l);
        Label endLabel = mark();

        TryCatchBlock block = new TryCatchBlock(startLabel, endLabel, endLabel, Type.getType(Throwable.class).getInternalName());
        instrumentedTryCatchBlocks.add(block);

//        catchException(startLabel, endLabel, Type.getType(Throwable.class));

        dup(); // Exception
        push(instrumentedOwner);
        push(name);
        push(desc);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                PackageInfo.getNameWithSlash(JUnitObserver.class),
                "methodException",
                "(Ljava/lang/Throwable;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);

        throwException(); // re-throw
        mark(l);

        Type returnType = Type.getReturnType(desc);
        if(opcode == Opcodes.INVOKESPECIAL) {
            // dup();
            push((String)null);
        } else if (returnType == Type.VOID_TYPE) {
            push((String)null);
        } else {
            if(returnType.getSize() == 1)
                dup();
            else if(returnType.getSize() == 2)
                dup2();
            else
                assert(false); // Cannot happen
            box(Type.getReturnType(desc));
        }
        push(instrumentedOwner);
        push(name);
        push(desc);
//        if ((opcode & Opcodes.INVOKESTATIC) > 0) {
//            mv.visitInsn(Opcodes.ACONST_NULL);
//        } else {
//            mv.visitVarInsn(Opcodes.ALOAD, 0);
//        }
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                PackageInfo.getNameWithSlash(JUnitObserver.class),
                "methodReturned",
                "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);

    }


    private final List<TryCatchBlock> instrumentedTryCatchBlocks = new LinkedList<>();
    private final List<TryCatchBlock> tryCatchBlocks = new LinkedList<>();

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler,
                                   String type) {
        TryCatchBlock block = new TryCatchBlock(start, end, handler, type);
        tryCatchBlocks.add(block);
    }

    @Override
    public void visitEnd() {
        // regenerate try-catch table
        for (TryCatchBlock tryCatchBlock : instrumentedTryCatchBlocks) {
            super.visitTryCatchBlock(tryCatchBlock.start,
                    tryCatchBlock.end, tryCatchBlock.handler,
                    tryCatchBlock.type);
        }
        for (TryCatchBlock tryCatchBlock : tryCatchBlocks) {
            super.visitTryCatchBlock(tryCatchBlock.start,
                    tryCatchBlock.end, tryCatchBlock.handler,
                    tryCatchBlock.type);
        }
        super.visitEnd();
    }
}
