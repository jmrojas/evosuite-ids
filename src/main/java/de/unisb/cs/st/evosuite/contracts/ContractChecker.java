/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite contributors
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
package de.unisb.cs.st.evosuite.contracts;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.testcase.ExecutionObserver;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;

/**
 * @author Gordon Fraser
 * 
 */
public class ContractChecker extends ExecutionObserver {

	private static Logger logger = LoggerFactory.getLogger(ContractChecker.class);

	private final Set<Contract> contracts = new HashSet<Contract>();

	/*
	 * Maybe it was not a problem, but it all depends on when Properties.CHECK_CONTRACTS_END 
	 * is initialized. Maybe best to just call it directly
	 */
	//private static final boolean checkAtEnd = Properties.CHECK_CONTRACTS_END;

	private static boolean valid = true;

	private static boolean active = true;

	public ContractChecker() {
		// Default from EvoSuite
		contracts.add(new UndeclaredExceptionContract());
		contracts.add(new JCrasherExceptionContract());

		// Defaults from Randoop paper
		contracts.add(new NullPointerExceptionContract());
		contracts.add(new AssertionErrorContract());
		contracts.add(new EqualsContract());
		contracts.add(new ToStringReturnsNormallyContract());
		contracts.add(new HashCodeReturnsNormallyContract());

		// Further Randoop contracts, not in paper
		contracts.add(new EqualsHashcodeContract());
		contracts.add(new EqualsNullContract());
		contracts.add(new EqualsSymmetricContract());
	}

	public static void setActive(boolean isActive) {
		active = isActive;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.ExecutionObserver#output(int, java.lang.String)
	 */
	@Override
	public void output(int position, String output) {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the current test case, on which we check oracles while it is executed
	 * 
	 * @param test
	 */
	public static void currentTest(TestCase test) {
		currentTest = test;
		valid = true;
		// TODO: Keep track of objects that raised an exception, and exclude them from contract checking
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.ExecutionObserver#statement(int, de.unisb.cs.st.evosuite.testcase.Scope, de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	public void statement(StatementInterface statement, Scope scope, Throwable exception) {

		if (!valid) {
			/*
			 * once we get a contract that is violated, no point in checking the following statements,
			 * because the internal state of the SUT is corrupted.
			 * 
			 * TODO: at this point, for the fitness function we still consider the coverage given by the 
			 * following statements. Maybe that should be changed? At the moment, we only stop if exceptions 
			 */
			logger.warn("Not checking contracts for invalid test");
			return;
		}

		if (!active) {
			return;
		}

		if (Properties.CHECK_CONTRACTS_END
		        && statement.getPosition() < (currentTest.size() - 1))
			return;

		for (Contract contract : contracts) {
			try {

				if (!contract.check(statement, scope, exception)) {
					logger.debug("Contract failed: " + contract + " on statement "
					        + statement.getCode());
					FailingTestSet.addFailingTest(currentTest, contract, statement,
					                              exception);
					valid = false;
					//break;
				}
			} catch (Throwable t) {
				//logger.info("Caught exception during contract checking");
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.ExecutionObserver#clear()
	 */
	@Override
	public void clear() {
		valid = true;
	}

}
