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
package org.evosuite.ga.localsearch;

import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.NumericalPrimitiveStatement;
import org.evosuite.testcase.TestChromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * IntegerLocalSearch class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class IntegerLocalSearch<T> extends StatementLocalSearch {

	private static final Logger logger = LoggerFactory.getLogger(TestCaseLocalSearch.class);

	private T oldValue;

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.LocalSearch#doSearch(org.evosuite.testcase.TestChromosome, int, org.evosuite.ga.LocalSearchObjective)
	 */
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public boolean doSearch(TestChromosome test, int statement,
	        LocalSearchObjective<TestChromosome> objective) {

		boolean improved = false;

		NumericalPrimitiveStatement<T> p = (NumericalPrimitiveStatement<T>) test.getTestCase().getStatement(statement);
		ExecutionResult oldResult = test.getLastExecutionResult();
		oldValue = p.getValue();
		logger.info("Applying search to: " + p.getCode());

		boolean done = false;
		while (!done) {
			done = true;
			// Try +1
			p.increment(1);
			logger.info("Trying increment of " + p.getCode());
			if (objective.hasImproved(test)) {
				done = false;
				improved = true;

				iterate(2, objective, test, p, statement);
				oldValue = p.getValue();
				oldResult = test.getLastExecutionResult();

			} else {
				// Restore original, try -1
				p.setValue(oldValue);
				test.setLastExecutionResult(oldResult);
				test.setChanged(false);

				p.increment(-1);
				logger.info("Trying decrement of " + p.getCode());
				if (objective.hasImproved(test)) {
					done = false;
					iterate(-2, objective, test, p, statement);
					oldValue = p.getValue();
					oldResult = test.getLastExecutionResult();

				} else {
					p.setValue(oldValue);
					test.setLastExecutionResult(oldResult);
					test.setChanged(false);
				}
			}
		}

		logger.info("Finished local search with result " + p.getCode());
		return improved;
	}

	private boolean iterate(long delta, LocalSearchObjective<TestChromosome> objective,
	        TestChromosome test, NumericalPrimitiveStatement<T> p, int statement) {

		boolean improvement = false;
		T oldValue = p.getValue();
		ExecutionResult oldResult = test.getLastExecutionResult();


		p.increment(delta);
		logger.info("Trying increment " + delta + " of " + p.getCode());
		while (objective.hasImproved(test)) {
			oldValue = p.getValue();
			oldResult = test.getLastExecutionResult();
			improvement = true;
			delta = 2 * delta;
			p.increment(delta);
			logger.info("Trying increment " + delta + " of " + p.getCode());
		}
		logger.info("No improvement on " + p.getCode());

		p.setValue(oldValue);
		test.setLastExecutionResult(oldResult);
		test.setChanged(false);
		logger.info("Final value of this iteration: " + p.getValue());

		return improvement;

	}

}
