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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.testcase.ConstructorStatement;
import org.evosuite.testcase.FieldStatement;
import org.evosuite.testcase.MethodStatement;
import org.evosuite.testcase.Scope;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCaseExecutor;
import org.evosuite.testcase.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Based on ObjectContract / Randoop
 * 
 * @author Gordon Fraser
 */
public abstract class Contract {

	/** Constant <code>logger</code> */
	protected static Logger logger = LoggerFactory.getLogger(Contract.class);

	protected static class Pair<T> {
		T object1;
		T object2;

		public Pair(T o1, T o2) {
			object1 = o1;
			object2 = o2;
		}
	}

	/**
	 * <p>
	 * getAllObjects
	 * </p>
	 * 
	 * @param scope
	 *            a {@link org.evosuite.testcase.Scope} object.
	 * @return a {@link java.util.Collection} object.
	 */
	protected Collection<Object> getAllObjects(Scope scope) {
		// TODO: Assignable classes and subclasses?
		return scope.getObjects(Properties.getTargetClass());
	}
	
	protected Collection<VariableReference> getAllVariables(Scope scope) {
		return scope.getElements(Properties.getTargetClass());
	}

	/**
	 * <p>
	 * getAllObjectPairs
	 * </p>
	 * 
	 * @param scope
	 *            a {@link org.evosuite.testcase.Scope} object.
	 * @return a {@link java.util.Collection} object.
	 */
	protected Collection<Pair<Object>> getAllObjectPairs(Scope scope) {
		Set<Pair<Object>> pairs = new HashSet<Pair<Object>>();
		for (Object o1 : scope.getObjects(Properties.getTargetClass())) {
			for (Object o2 : scope.getObjects(o1.getClass())) {
				pairs.add(new Pair<Object>(o1, o2));
			}
		}
		return pairs;
	}
	
	protected Collection<Pair<VariableReference>> getAllVariablePairs(Scope scope) {
		Set<Pair<VariableReference>> pairs = new HashSet<Pair<VariableReference>>();
		for (VariableReference o1 : scope.getElements(Properties.getTargetClass())) {
			for (VariableReference o2 : scope.getElements(o1.getClass())) {
				pairs.add(new Pair<VariableReference>(o1, o2));
			}
		}
		return pairs;
	}

	/**
	 * Check if this statement is related to the unit under test
	 * 
	 * @param statement
	 *            a {@link org.evosuite.testcase.StatementInterface} object.
	 * @return a boolean.
	 */
	protected boolean isTargetStatement(StatementInterface statement) {
		//if (statement.getReturnClass().equals(Properties.getTargetClass()))
		//	return true;
		if (statement instanceof MethodStatement) {
			MethodStatement ms = (MethodStatement) statement;
			if (Properties.getTargetClass().equals(ms.getMethod().getDeclaringClass()))
				return true;
		} else if (statement instanceof ConstructorStatement) {
			ConstructorStatement cs = (ConstructorStatement) statement;
			if (Properties.getTargetClass().equals(cs.getConstructor().getDeclaringClass()))
				return true;
		} else if (statement instanceof FieldStatement) {
			FieldStatement fs = (FieldStatement) statement;
			if (Properties.getTargetClass().equals(fs.getField().getDeclaringClass()))
				return true;
		}

		return false;
	}

	/**
	 * Run the test against this contract and determine whether it reports a
	 * failure
	 * 
	 * @param test
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @return a boolean.
	 */
	public boolean fails(TestCase test) {
		ContractChecker.setActive(false);
		TestCaseExecutor executor = TestCaseExecutor.getInstance();
		SingleContractChecker checker = new SingleContractChecker(this);
		executor.addObserver(checker);
		TestCaseExecutor.runTest(test);
		executor.removeObserver(checker);
		//ContractChecker.setActive(true);
		return !checker.isValid();
	}

	/**
	 * Check the contract on the current statement in the current scope 
	 * 
	 * @param statement
	 *            a {@link org.evosuite.testcase.StatementInterface} object.
	 * @param scope
	 *            a {@link org.evosuite.testcase.Scope} object.
	 * @param exception
	 *            a {@link java.lang.Throwable} object.
	 * @return a boolean.
	 */
	public abstract ContractViolation check(StatementInterface statement, Scope scope,
	        Throwable exception);

	/**
	 * Add an assertion to the statement based on the contract.
	 * The assertion should fail on a contract violation, and pass
	 * if the contract is satisfied.
	 * 
	 * @param statement
	 * @param variables
	 * @param exception
	 */
	public abstract void addAssertionAndComments(StatementInterface statement, List<VariableReference> variables, Throwable exception);
}
