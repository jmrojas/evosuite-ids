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
package org.evosuite.coverage.exception;

import org.evosuite.Properties;
import org.evosuite.testcase.*;
import org.evosuite.testcase.execution.CodeUnderTestException;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Fitness function for a single test on a single exception
 *
 * @author Gordon Fraser, Jose Miguel Rojas
 */
public class ExceptionCoverageTestFitness extends TestFitnessFunction {


    private static final long serialVersionUID = 1221020001417476348L;

    public enum ExceptionType {
        /** unexpected exception directly thrown with a "throw new..." */
        EXPLICIT,
        /** unexpected exception not thrown directly in the SUT, eg NPE on variable access*/
        IMPLICIT,
        /** Thrown exception which is expected, because declared in signature with "throws"*/
        DECLARED};

    /**
     * name+descriptor
     */
    protected final String methodIdentifier;

    /**
     * The class representing the thrown exception, eg NPE an IAE
     */
    protected final Class<?> exceptionClass;

    protected final ExceptionType type;

    /**
     * Constructor - fitness is specific to a method
     * @param methodIdentifier the method name
     * @param exceptionClass the exception class
     * @throws IllegalArgumentException
     */
    public ExceptionCoverageTestFitness(String methodIdentifier, Class<?> exceptionClass, ExceptionType type) throws IllegalArgumentException{
        if ((methodIdentifier == null) || (exceptionClass == null) || type==null) {
            throw new IllegalArgumentException("method name and exception class and type cannot be null");
        }
        this.exceptionClass = exceptionClass;
        this.methodIdentifier = methodIdentifier;
        this.type = type;
    }

    public String getKey(){
        return methodIdentifier + "_" + exceptionClass.getName() + "_" + type;
    }

    /**
     * <p>
     * getMethod
     * </p>
     *
     * @return a {@link String} object.
     */
    public String getMethod() {
        return methodIdentifier;
    }

    /**
     * {@inheritDoc}
     *
     * Calculate fitness
     *
     * @param individual
     *            a {@link org.evosuite.testcase.ExecutableChromosome} object.
     * @param result
     *            a {@link org.evosuite.testcase.execution.ExecutionResult} object.
     * @return a double.
     */
    @Override
    public double getFitness(TestChromosome individual, ExecutionResult result) {
        double fitness = 1.0;

        //iterate on the indexes of the statements that resulted in an exception
        for (Integer i : result.getPositionsWhereExceptionsWereThrown()) {
            if(ExceptionCoverageHelper.shouldSkip(result,i)){
                continue;
            }
            Class<?> exceptionClass = ExceptionCoverageHelper.getExceptionClass(result,i);
            String methodIdentifier = ExceptionCoverageHelper.getMethodIdentifier(result, i); //eg name+descriptor
            boolean sutException = ExceptionCoverageHelper.isSutException(result,i); // was the exception originated by a direct call on the SUT?

            /*
			 * We only consider exceptions that were thrown directly in the SUT (not called libraries)
			 */
            if (sutException) {

                ExceptionType type = ExceptionCoverageHelper.getType(result,i);

                if (this.methodIdentifier.equals(methodIdentifier) && this.exceptionClass.equals(exceptionClass) &&
                        this.type.equals(type)) {
                    fitness = 0.0;
                    break;
                }
            }
        }
        return fitness;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getKey();
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int iConst = 17;
        return 53 * iConst + methodIdentifier.hashCode() * iConst + exceptionClass.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExceptionCoverageTestFitness other = (ExceptionCoverageTestFitness) obj;
        if (! methodIdentifier.equals(other.methodIdentifier)) {
            return false;
        } else {
            if(! exceptionClass.equals(other.exceptionClass)){
                return false;
            } else {
                return this.type.equals(other.type);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.TestFitnessFunction#compareTo(org.evosuite.testcase.TestFitnessFunction)
     */
    @Override
    public int compareTo(TestFitnessFunction other) {
        if (other instanceof ExceptionCoverageTestFitness) {
            ExceptionCoverageTestFitness otherMethodFitness = (ExceptionCoverageTestFitness) other;
            if (methodIdentifier.equals(otherMethodFitness.getMethod())) {
                if (exceptionClass.equals(((ExceptionCoverageTestFitness) other).exceptionClass)) {
                    return this.type.compareTo(((ExceptionCoverageTestFitness) other).type);
                } else
                    return exceptionClass.getName().compareTo(otherMethodFitness.exceptionClass.getName());
            } else
                return methodIdentifier.compareTo(otherMethodFitness.getMethod());
        }
        return 0;
    }


    /* (non-Javadoc)
     * @see org.evosuite.testcase.TestFitnessFunction#getTargetClass()
     */
    @Override
    public String getTargetClass() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.evosuite.testcase.TestFitnessFunction#getTargetMethod()
     */
    @Override
    public String getTargetMethod() {
        return getMethod();
    }

}