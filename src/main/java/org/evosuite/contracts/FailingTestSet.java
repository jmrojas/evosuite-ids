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
package org.evosuite.contracts;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.junit.TestSuiteWriter;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCaseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * FailingTestSet class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class FailingTestSet {

	private static Logger logger = LoggerFactory.getLogger(FailingTestSet.class);

	/*
	 * FIXME: if actually used, need way to reset them
	 */

	/** The violated tracked */
	private static final List<ContractViolation> violations = new ArrayList<ContractViolation>();

	private static int violationCount = 0;

	/**
	 * Keep track of a new observed contract violation
	 * 
	 * @param test
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param contract
	 *            a {@link org.evosuite.contracts.Contract} object.
	 * @param statement
	 *            a {@link org.evosuite.testcase.StatementInterface} object.
	 * @param exception
	 *            a {@link java.lang.Throwable} object.
	 */
	public static void addFailingTest(TestCase test, Contract contract,
	        StatementInterface statement, Throwable exception) {
		violationCount++;
		ContractViolation violation = new ContractViolation(contract, test, statement,
		        exception);

		if (!hasViolation(violation)) {
			violations.add(violation);
		}
	}

	/**
	 * How many violations have we observed in total?
	 * 
	 * @return a int.
	 */
	public static int getNumberOfViolations() {
		return violationCount;
	}

	/**
	 * How many violations of this contract have we observed in total?
	 * 
	 * @param contract
	 *            a {@link org.evosuite.contracts.Contract} object.
	 * @return a int.
	 */
	public static int getNumberOfViolations(Contract contract) {
		int num = 0;
		for (ContractViolation violation : violations) {
			if (violation.getContract().equals(contract))
				num++;
		}
		return num;
	}

	/**
	 * How many violations of this contract have we observed in total?
	 * 
	 * @param contractClass
	 *            a {@link java.lang.Class} object.
	 * @return a int.
	 */
	public static int getNumberOfViolations(Class<?> contractClass) {
		int num = 0;
		for (ContractViolation violation : violations) {
			if (violation.getContract().getClass().equals(contractClass))
				num++;
		}
		return num;
	}

	/**
	 * How many unique violations have we observed?
	 * 
	 * @return a int.
	 */
	public static int getNumberOfUniqueViolations() {
		return violations.size();
	}

	public static List<TestCase> getFailingTests() {
		List<TestCase> tests = new ArrayList<TestCase>();
		ContractChecker.setActive(false);
		TestCaseExecutor.getInstance().newObservers();

		for (int i = 0; i < violations.size(); i++) {
			logger.debug("Writing test {}/{}", i, violations.size());
			ContractViolation violation = violations.get(i);
			violation.minimizeTest();
			tests.add(violation.getTestCase());
		}
		return tests;
	}

	/**
	 * Output the failing tests in a JUnit test suite
	 */
	public static void writeJUnitTestSuite() {
		logger.info("Writing {} failing tests", violations.size());
		TestSuiteWriter writer = new TestSuiteWriter();
		writeJUnitTestSuite(writer);
		String name = Properties.TARGET_CLASS.substring(Properties.TARGET_CLASS.lastIndexOf(".") + 1);
		String testDir = Properties.TEST_DIR;
		writer.writeTestSuite("Failures" + name, testDir);
	}

	/**
	 * Output the failing tests in a JUnit test suite
	 */
	public static void writeJUnitTestSuite(TestSuiteWriter writer) {
		logger.info("Writing {} failing tests", violations.size());
		ContractChecker.setActive(false);
		TestCaseExecutor.getInstance().newObservers();
		for (int i = 0; i < violations.size(); i++) {
			logger.debug("Writing test {}/{}", i, violations.size());
			ContractViolation violation = violations.get(i);
			violation.minimizeTest();
			TestCase test = violation.getTestCase();
			// TODO: Add comment about contract violation
			writer.insertTest(test, " Contract violation: "
			        + violation.getContract().toString());
		}
	}

	/**
	 * Determine if we already have an instance of this violation
	 * 
	 * @param violation
	 *            a {@link org.evosuite.contracts.ContractViolation} object.
	 * @return a boolean.
	 */
	public static boolean hasViolation(ContractViolation violation) {
		for (ContractViolation oldViolation : violations) {
			if (oldViolation.same(violation))
				return true;
		}

		return false;
	}

}
