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
package org.evosuite.localsearch;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.NumericalPrimitiveStatement;
import org.evosuite.testcase.TestChromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * FloatLocalSearch class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class FloatLocalSearch<T extends Number> extends StatementLocalSearch {

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
		double value = p.getValue().doubleValue();
		if (Double.isInfinite(value) || Double.isNaN(value)) {
			return false;
		}
		logger.info("Applying search to: " + p.getCode());

		int change = doSearch(test, statement, objective, 1.0, 2, p);
		if(change < 0)
			improved = true;
		else if(change == 0) {
			// Only apply search after the comma if the fitness was affected by the first part of the search
			logger.info("Stopping search as variable doesn't influence fitness");
			return improved;
		}
		logger.info("Checking after the comma: " + p.getCode());

		int maxPrecision = p.getValue().getClass().equals(Float.class) ? 7 : 15;
		for (int precision = 1; precision <= maxPrecision; precision++) {
			if(LocalSearchBudget.getInstance().isFinished())
				break;

			roundPrecision(test, objective, precision, p);
			logger.debug("Current precision: " + precision);
			change = doSearch(test, statement, objective, Math.pow(10.0, -precision), 2, p);
			if(change < 0)
				improved = true;
		}

		logger.info("Finished local search with result " + p.getCode());
		return improved;
	}

	@SuppressWarnings("unchecked")
	private boolean roundPrecision(TestChromosome test,
	        LocalSearchObjective<TestChromosome> objective, int precision,
	        NumericalPrimitiveStatement<T> p) {
		double value = p.getValue().doubleValue();
		if (Double.isInfinite(value) || Double.isNaN(value)) {
			return false;
		}

		BigDecimal bd = new BigDecimal(value).setScale(precision, RoundingMode.HALF_EVEN);
		if (bd.doubleValue() == value) {
			return false;
		}

		double newValue = bd.doubleValue();
		oldValue = p.getValue();
		ExecutionResult oldResult = test.getLastExecutionResult();

		if (p.getValue().getClass().equals(Float.class))
			p.setValue((T) (new Float(newValue)));
		else
			p.setValue((T) (new Double(newValue)));

		logger.info("Trying to chop precision " + precision + ": " + value + " -> "
		        + newValue);

		if (objective.hasNotWorsened(test)) {
			return true;
		} else {
			logger.info("Restoring old value: "+value);
			p.setValue(oldValue);
			test.setLastExecutionResult(oldResult);
			test.setChanged(false);
			return false;
		}

	}

	private int doSearch(TestChromosome test, int statement,
	        LocalSearchObjective<TestChromosome> objective, double initialDelta, double factor,
	        NumericalPrimitiveStatement<T> p) {

		int changed = 0;

		oldValue = p.getValue();
		ExecutionResult oldResult = test.getLastExecutionResult();

		boolean done = false;
		while (!done) {
			done = true;
			// Try +1
			p.increment(initialDelta);
			logger.info("Trying increment of " + p.getCode());
			//logger.info(" -> " + p.getCode());
			int change = objective.hasChanged(test);
			if(change != 0)
				changed = change;
			
			if (change < 0) {
				done = false;
				// changed = true;

				iterate(factor * initialDelta, factor, objective, test, p, statement);
				oldValue = p.getValue();
				oldResult = test.getLastExecutionResult();

			} else {
				// Restore original, try -1
				p.setValue(oldValue);
				test.setLastExecutionResult(oldResult);
				test.setChanged(false);

				p.increment(-initialDelta);
				logger.info("Trying decrement of " + p.getCode());
				//logger.info(" -> " + p.getCode());
				change = objective.hasChanged(test);
				if (change < 0) {
					changed = change;
					done = false;
					iterate(-factor * initialDelta, factor, objective, test, p, statement);
					oldValue = p.getValue();
					oldResult = test.getLastExecutionResult();

				} else {
					p.setValue(oldValue);
					test.setLastExecutionResult(oldResult);
					test.setChanged(false);
				}
			}
		}

		logger.debug("Finished local search with result " + p.getCode());
		return changed;
	}

	private boolean iterate(double delta, double factor, LocalSearchObjective<TestChromosome> objective,
	        TestChromosome test, NumericalPrimitiveStatement<T> p, int statement) {

		boolean improvement = false;
		T oldValue = p.getValue();
		ExecutionResult oldResult = test.getLastExecutionResult();
		logger.info("Trying increment " + delta + " of " + p.getCode());

		p.increment(delta);
		while (objective.hasImproved(test)) {
			oldValue = p.getValue();
			oldResult = test.getLastExecutionResult();
			test.setChanged(false);
			improvement = true;
			delta = factor * delta;
			//if (delta > 1)
			//	return improvement;
			logger.info("Trying increment " + delta + " of " + p.getCode());
			p.increment(delta);
		}

		p.setValue(oldValue);
		test.setLastExecutionResult(oldResult);
		test.setChanged(false);

		return improvement;

	}
}
