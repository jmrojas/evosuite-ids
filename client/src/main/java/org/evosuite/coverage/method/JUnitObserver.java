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
package org.evosuite.coverage.method;

import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.exception.ExceptionCoverageTestFitness;
import org.evosuite.coverage.io.input.InputCoverageGoal;
import org.evosuite.coverage.io.output.OutputCoverageGoal;
import org.evosuite.runtime.mock.OverrideMock;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.InheritanceTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by gordon on 01/01/2016.
 */
public class JUnitObserver {

    private final static Logger logger = LoggerFactory.getLogger(JUnitObserver.class);

    private static JUnitObserver instance = null;

    private JUnitObserver() {
    }

    private boolean enabled = false;

    private Set<InputCoverageGoal> inputGoals = new LinkedHashSet<>();

    private Set<OutputCoverageGoal> outputGoals = new LinkedHashSet<>();

    private Set<ExceptionCoverageTestFitness> exceptionGoals = new LinkedHashSet<>();

    private Set<MethodCoverageTestFitness> calledMethods = new LinkedHashSet<>();

    private Set<MethodNoExceptionCoverageTestFitness> calledMethodsNoException = new LinkedHashSet<>();

    private boolean validCall = true;

    public static JUnitObserver getInstance() {
        if(instance == null)
            instance = new JUnitObserver();

        return instance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        enabled = value;
    }

    public void reset() {
        inputGoals.clear();
        outputGoals.clear();
        calledMethods.clear();
        calledMethodsNoException.clear();
        exceptionGoals.clear();
        validCall = true;
    }

    private static boolean isValidClass(Object callee, String className) {
        if(callee != null) {
            try {
                Class<?> targetClass = Class.forName(className);
                if(targetClass != null && !targetClass.isAssignableFrom(callee.getClass())) {
                    return false;
                }
            } catch(ClassNotFoundException e) {

            }
        }
        return true;
    }

    public static void methodCalled(Object callee, int opcode, String className, String methodName, String methodDesc, Object[] arguments) {
        if(!getInstance().isEnabled())
            return;

        if(!isValidClass(callee, className)) {
            getInstance().validCall = false;
            return;
        }
        getInstance().validCall = true;

        String classNameWithDots = className.replace('/', '.');

        logger.info("Calling method "+className+"."+methodName+" with callee "+callee+" and arguments "+ Arrays.asList(arguments));
        getInstance().calledMethods.add(new MethodCoverageTestFitness(classNameWithDots, methodName+methodDesc));
        getInstance().inputGoals.addAll(InputCoverageGoal.createCoveredGoalsFromParameters(classNameWithDots, methodName, methodDesc, Arrays.asList(arguments)));
    }

    public static void methodReturned(Object retVal, String className, String methodName, String methodDesc) {
        if(!getInstance().isEnabled())
            return;

        if(!getInstance().validCall)
            return;


        String classNameWithDots = className.replace('/', '.');
        logger.info("Method "+className+"."+methodName+" returned: "+retVal);
        getInstance().calledMethodsNoException.add(new MethodNoExceptionCoverageTestFitness(classNameWithDots, methodName+methodDesc));

        // Output goals are only collected for methods, not for constructors
        if(!methodName.equals("<init>"))
            getInstance().outputGoals.addAll(OutputCoverageGoal.createGoalsFromObject(classNameWithDots, methodName, methodDesc, retVal));

    }

    public static void methodException(Throwable throwable, String className, String methodName, String methodDesc) {
        if(!getInstance().isEnabled())
            return;

        if(!getInstance().validCall)
            return;

        String classNameWithDots = className.replace('/', '.');
        Class<?> exceptionClass = throwable.getClass();
        if(throwable instanceof OverrideMock){
            exceptionClass = throwable.getClass().getSuperclass();
        }

        // TODO: Need to distinguish between explicit and implicit exceptions
        logger.info("Method "+className+"."+methodName+" throwed exception of class "+exceptionClass.getSimpleName());
        getInstance().exceptionGoals.add(new ExceptionCoverageTestFitness(classNameWithDots, methodName+methodDesc, exceptionClass, ExceptionCoverageTestFitness.ExceptionType.IMPLICIT));
    }


    public Set<OutputCoverageGoal> getOutputCoverageGoals() {
        return outputGoals;
    }

    public Set<InputCoverageGoal> getInputCoverageGoals() {
        return inputGoals;
    }

    public Set<MethodCoverageTestFitness> getCoveredMethodGoals() {
        return calledMethods;
    }

    public Set<MethodNoExceptionCoverageTestFitness> getCoveredMethodNoExceptionGoals() {
        return calledMethodsNoException;
    }

    public Set<ExceptionCoverageTestFitness> getExceptionGoals() {
        return exceptionGoals;
    }

}
