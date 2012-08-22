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
package org.evosuite.assertion;

import org.evosuite.testcase.ExecutionObserver;
import org.evosuite.testcase.Scope;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract AssertionTraceObserver class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public abstract class AssertionTraceObserver<T extends OutputTraceEntry> extends
        ExecutionObserver {

	/** Constant <code>logger</code> */
	protected static Logger logger = LoggerFactory.getLogger(AssertionTraceObserver.class);

	protected OutputTrace<T> trace = new OutputTrace<T>();

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.ExecutionObserver#output(int, java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public void output(int position, String output) {
		// Default behavior is to ignore console output

	}

	/**
	 * <p>
	 * visitDependencies
	 * </p>
	 * 
	 * @param statement
	 *            a {@link org.evosuite.testcase.StatementInterface} object.
	 * @param scope
	 *            a {@link org.evosuite.testcase.Scope} object.
	 */
	protected void visitDependencies(StatementInterface statement, Scope scope) {
		for (VariableReference var : currentTest.getDependencies(statement.getReturnValue())) {
			if (!var.isVoid()) {
				visit(statement, scope, var);
			}
		}
	}

	/**
	 * <p>
	 * visitReturnValue
	 * </p>
	 * 
	 * @param statement
	 *            a {@link org.evosuite.testcase.StatementInterface} object.
	 * @param scope
	 *            a {@link org.evosuite.testcase.Scope} object.
	 */
	protected void visitReturnValue(StatementInterface statement, Scope scope) {
		if (!statement.getReturnClass().equals(void.class))
			visit(statement, scope, statement.getReturnValue());
	}

	/**
	 * <p>
	 * visit
	 * </p>
	 * 
	 * @param statement
	 *            a {@link org.evosuite.testcase.StatementInterface} object.
	 * @param scope
	 *            a {@link org.evosuite.testcase.Scope} object.
	 * @param var
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 */
	protected abstract void visit(StatementInterface statement, Scope scope,
	        VariableReference var);

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.ExecutionObserver#statement(org.evosuite.testcase.StatementInterface, org.evosuite.testcase.Scope, java.lang.Throwable)
	 */
	/** {@inheritDoc} */
	@Override
	public void afterStatement(StatementInterface statement, Scope scope,
	        Throwable exception) {
		//visitReturnValue(statement, scope);
		visitDependencies(statement, scope);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.ExecutionObserver#beforeStatement(org.evosuite.testcase.StatementInterface, org.evosuite.testcase.Scope)
	 */
	@Override
	public void beforeStatement(StatementInterface statement, Scope scope) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.ExecutionObserver#clear()
	 */
	/** {@inheritDoc} */
	@Override
	public void clear() {
		trace.clear();
	}

	/**
	 * <p>
	 * Getter for the field <code>trace</code>.
	 * </p>
	 * 
	 * @return a {@link org.evosuite.assertion.OutputTrace} object.
	 */
	public OutputTrace<T> getTrace() {
		return trace.clone();
	}

}
