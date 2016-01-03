/**
 * Copyright (C) 2010-2015 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser Public License as published by the
 * Free Software Foundation, either version 3.0 of the License, or (at your
 * option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along
 * with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.junit;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.coverage.exception.ExceptionCoverageTestFitness;
import org.evosuite.coverage.io.input.InputCoverageGoal;
import org.evosuite.coverage.io.output.OutputCoverageGoal;
import org.evosuite.coverage.method.MethodCoverageTestFitness;
import org.evosuite.coverage.method.MethodNoExceptionCoverageTestFitness;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionTrace;

/**
 * The information from executing a JUnit test case
 * 
 * @author galeotti
 * @author José Campos
 */
public class JUnitResult {

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private boolean successful;

    /**
     * 
     */
    private long runtime;

    /**
     * 
     */
    private String trace;

    /**
     * 
     */
    private ExecutionTrace executionTrace;


    /**
     * 
     */
    private int failureCount;

    /**
     * 
     */
    private int runCount;

    /**
     * 
     */
    private ArrayList<JUnitFailure> junitFailures = new ArrayList<JUnitFailure>();

    /**
     * 
     */
    private Class<?> junitClass;

    private Set<InputCoverageGoal> inputGoals = new LinkedHashSet<>();

    private Set<OutputCoverageGoal> outputGoals = new LinkedHashSet<>();

    private Set<MethodCoverageTestFitness> methodGoals = new LinkedHashSet<>();

    private Set<MethodNoExceptionCoverageTestFitness> methodNoExceptionGoals = new LinkedHashSet<>();

    private Set<ExceptionCoverageTestFitness> exceptionGoals = new LinkedHashSet<>();

    private TestCase dummyTest = new DefaultTestCase() {
        private Object dummy = new Object();
        @Override
        public int hashCode() {
            return dummy.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return dummy.equals(obj);
        }
    };
    /**
     * 
     */
    public JUnitResult(String name) {
        this.successful = true;
        this.name = name;
        this.failureCount = 0;
        this.runCount = 0;
    }

    /**
     * 
     */
    public JUnitResult(String name, Class<?> junitClass) {
        this.successful = true;
        this.name = name;
        this.failureCount = 0;
        this.runCount = 0;
        this.junitClass = junitClass;
    }

    /**
     * 
     * @param wasSuccessful
     * @param failureCount
     * @param runCount
     */
	public JUnitResult(boolean wasSuccessful, int failureCount, int runCount) {
	    this.successful = wasSuccessful;
	    this.failureCount = failureCount;
	    this.runCount = runCount;
	}

	/**
     * 
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @param n
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * 
     * @return
     */
    public boolean wasSuccessful() {
        return this.successful;
    }

    /**
     * 
     * @param s
     */
    public void setSuccessful(boolean s) {
        this.successful = s;
    }

    /**
     * 
     * @return
     */
    public long getRuntime() {
        return this.runtime;
    }

    /**
     * 
     * @param r
     */
    public void setRuntime(long r) {
        this.runtime = r;
    }

    /**
     * 
     * @return
     */
    public String getTrace() {
        return this.trace;
    }

    /**
     * 
     * @param t
     */
    public void setTrace(String t) {
        this.trace = t;
    }

    /**
     * 
     * @return
     */
    public ExecutionTrace getExecutionTrace() {
        return this.executionTrace;
    }

    /**
     * 
     * @param et
     */
    public void setExecutionTrace(ExecutionTrace et) {
        this.executionTrace = et;
    }

    /**
     * 
     * @return
     */
    public int getFailureCount() {
        return this.failureCount;
    }

    public void incrementFailureCount() {
        this.failureCount++;
    }

    /**
     * 
     * @return
     */
    public int getRunCount() {
        return runCount;
    }

    public void incrementRunCount() {
        this.runCount++;
    }

    /**
     * 
     * @return
     */
    public List<JUnitFailure> getFailures() {
        return junitFailures;
    }

    /**
     * 
     * @param junitFailure
     */
    public void addFailure(JUnitFailure junitFailure) {
        junitFailures.add(junitFailure);
    }

    public Class<?> getJUnitClass() {
    	return this.junitClass;
    }

    public Set<OutputCoverageGoal> getOutputGoals() {
        return outputGoals;
    }

    public void addOutputGoals(Set<OutputCoverageGoal> outputGoals) {
        this.outputGoals.addAll(outputGoals);
    }

    public Set<InputCoverageGoal> getInputGoals() {
        return inputGoals;
    }

    public void addInputGoals(Set<InputCoverageGoal> inputGoals) {
        this.inputGoals.addAll(inputGoals);
    }

    public Set<MethodCoverageTestFitness> getMethodCoverageGoals() {
        return methodGoals;
    }

    public void addMethodGoals(Set<MethodCoverageTestFitness> methodGoals) {
        this.methodGoals.addAll(methodGoals);
    }

    public Set<MethodNoExceptionCoverageTestFitness> getMethodNoExceptionCoverageGoals() {
        return methodNoExceptionGoals;
    }

    public void addMethodNoExceptionGoals(Set<MethodNoExceptionCoverageTestFitness> methodNoExceptionGoals) {
        this.methodNoExceptionGoals.addAll(methodNoExceptionGoals);
    }

    public Set<ExceptionCoverageTestFitness> getExceptionCoverageGoals() {
        return exceptionGoals;
    }

    public void addExceptionGoals(Set<ExceptionCoverageTestFitness> exceptionGoals) {
        this.exceptionGoals.addAll(exceptionGoals);
    }

    /**
     * 
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.failureCount;
		result = prime * result
				+ ((this.junitFailures == null) ? 0 : this.junitFailures.hashCode());
		result = prime * result + this.runCount;
		result = prime * result + (this.successful ? 1231 : 1237);
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JUnitResult other = (JUnitResult) obj;
		if (this.failureCount != other.failureCount)
			return false;
		if (this.junitFailures == null) {
			if (other.junitFailures != null)
				return false;
		} else if (!this.junitFailures.equals(other.junitFailures))
			return false;
		if (this.runCount != other.runCount)
			return false;
		if (this.successful != other.successful)
			return false;
		return true;
	}

    public void addCoveredGoalToDummyTest(TestFitnessFunction goal) {
        dummyTest.addCoveredGoal(goal);
    }

    public TestCase getDummyTest() {
        return dummyTest;
    }
}
