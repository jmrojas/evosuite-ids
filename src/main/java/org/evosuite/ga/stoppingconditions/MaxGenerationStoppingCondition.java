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
package org.evosuite.ga.stoppingconditions;

import org.evosuite.Properties;
import org.evosuite.ga.GeneticAlgorithm;


/**
 * Stop search after a predefined number of iterations
 *
 * @author Gordon Fraser
 */
public class MaxGenerationStoppingCondition extends StoppingConditionImpl {
	
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MaxGenerationStoppingCondition.class);

	private static final long serialVersionUID = 251196904115160351L;

	/** Maximum number of iterations */
	protected long max_iterations = Properties.SEARCH_BUDGET;

	/** Maximum number of iterations */
	protected long current_iteration = 0;

	/**
	 * <p>setMaxIterations</p>
	 *
	 * @param max a int.
	 */
	public void setMaxIterations(int max) {
		max_iterations = max;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Increase iteration counter
	 */
	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		current_iteration++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Stop search after a number of iterations
	 */
	@Override
	public boolean isFinished() {
		logger.debug("Is finished? Current generation: " + current_iteration
		        + " Max iteration: " + max_iterations);
		return current_iteration >= max_iterations;
	}

	/** {@inheritDoc} */
	@Override
	public void searchFinished(GeneticAlgorithm algorithm) {
		current_iteration = 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Reset counter
	 */
	@Override
	public void reset() {
		current_iteration = 0;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.StoppingCondition#setLimit(int)
	 */
	/** {@inheritDoc} */
	@Override
	public void setLimit(long limit) {
		max_iterations = limit;
	}

	/** {@inheritDoc} */
	@Override
	public long getLimit() {
		return max_iterations;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.StoppingCondition#getCurrentValue()
	 */
	/** {@inheritDoc} */
	@Override
	public long getCurrentValue() {
		return current_iteration;
	}

	/** {@inheritDoc} */
	@Override
	public void forceCurrentValue(long value) {
		current_iteration = value;
	}
}
