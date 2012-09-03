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
package org.evosuite.ga;

import java.io.Serializable;

import org.evosuite.Properties;
import org.evosuite.Properties.LocalSearchBudgetType;
import org.evosuite.Properties.Strategy;
import org.evosuite.testsuite.TestSuiteChromosome;

/**
 * <p>
 * LocalSearchBudget class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class LocalSearchBudget implements SearchListener, Serializable {

	private static final long serialVersionUID = 9152147170303160131L;

	/** Constant <code>attempts=0</code> */
	protected static int attempts = 0;

	protected static int individuals = 0;

	/** Constant <code>startTime=0L</code> */
	protected static long startTime = 0L;

	/** Constant <code>endTime=0L</code> */
	protected static long endTime = 0L;

	protected static GeneticAlgorithm ga = null;

	/**
	 * <p>
	 * isFinished
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public static boolean isFinished() {
		if (ga.isFinished())
			return true;

		if (Properties.LOCAL_SEARCH_BUDGET_TYPE == LocalSearchBudgetType.STATEMENTS)
			return attempts >= Properties.LOCAL_SEARCH_BUDGET;
		else if (Properties.LOCAL_SEARCH_BUDGET_TYPE == LocalSearchBudgetType.TIME)
			return System.currentTimeMillis() > endTime;
		else if (Properties.LOCAL_SEARCH_BUDGET_TYPE == LocalSearchBudgetType.INDIVIDUALS)
			return individuals >= Properties.LOCAL_SEARCH_BUDGET;
		else
			throw new RuntimeException("Unknown budget type: "
			        + Properties.LOCAL_SEARCH_BUDGET_TYPE);
	}

	/**
	 * <p>
	 * evaluation
	 * </p>
	 */
	public static void evaluation() {
		attempts++;
	}

	public static void individualImproved(Chromosome chromosome) {
		if (Properties.STRATEGY == Strategy.EVOSUITE) {
			if (chromosome instanceof TestSuiteChromosome)
				individuals++;
		} else {
			individuals++;
		}
	}

	/**
	 * <p>
	 * localSearchStarted
	 * </p>
	 */
	public static void localSearchStarted() {
		startTime = System.currentTimeMillis();
		endTime = startTime + Properties.LOCAL_SEARCH_BUDGET;
		individuals = 0;
		attempts = 0;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#searchStarted(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void searchStarted(GeneticAlgorithm algorithm) {
		ga = algorithm;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#iteration(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		attempts = 0;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#searchFinished(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void searchFinished(GeneticAlgorithm algorithm) {
		ga = null;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#fitnessEvaluation(org.evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public void fitnessEvaluation(Chromosome individual) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#modification(org.evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public void modification(Chromosome individual) {
		// TODO Auto-generated method stub

	}

}
