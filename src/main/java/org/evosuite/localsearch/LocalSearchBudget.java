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

import java.io.Serializable;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.ga.SearchListener;
import org.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * LocalSearchBudget class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class LocalSearchBudget implements SearchListener, Serializable {

	private static final long serialVersionUID = 9152147170303160131L;

	private final static Logger logger = LoggerFactory.getLogger(LocalSearchBudget.class);
	
	// Singleton instance
	private static LocalSearchBudget instance = null;
	
	protected int fitnessEvaluations = 0;
	protected int tests          = 0;
	protected long executedStart = 0L;
	protected int suites         = 0;
	protected long startTime     = 0L;
	protected long endTime       = 0L;

	protected GeneticAlgorithm<?> ga = null;

	// Private constructor because of singleton type
	private LocalSearchBudget() {
		
	}
	
	// Singleton accessor
	public static LocalSearchBudget getInstance() {
		if(instance == null)
			instance = new LocalSearchBudget();
		
		return instance;
	}
	
	/**
	 * <p>
	 * isFinished
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isFinished() {
		// If the global search is finished then we don't want local search either
		if (ga != null && ga.isFinished())
			return true;

		boolean isFinished = false;
		
		switch(Properties.LOCAL_SEARCH_BUDGET_TYPE) {
		case FITNESS_EVALUATIONS:
			isFinished = fitnessEvaluations >= Properties.LOCAL_SEARCH_BUDGET;
			break;
		case SUITES:
			isFinished = suites >= Properties.LOCAL_SEARCH_BUDGET;
			break;
		case STATEMENTS:
			isFinished = MaxStatementsStoppingCondition.getNumExecutedStatements() > executedStart + Properties.LOCAL_SEARCH_BUDGET;
			break;
		case TESTS:
			isFinished = tests >= Properties.LOCAL_SEARCH_BUDGET;
			break;
		case TIME:
			isFinished = System.currentTimeMillis() > endTime;
			break;
		default:
			throw new RuntimeException("Unknown budget type: "
			        + Properties.LOCAL_SEARCH_BUDGET_TYPE);
		}
		if(isFinished) {
			logger.info("Local search budget used up; type: "+Properties.LOCAL_SEARCH_BUDGET_TYPE);
		}
		return isFinished;
	}

	public void countFitnessEvaluation() {
		fitnessEvaluations++;
	}

	public void countLocalSearchOnTest() {
		tests++;
	}
	
	public void countLocalSearchOnTestSuite() {
		suites++;
	}

	/**
	 * <p>
	 * localSearchStarted
	 * </p>
	 */
	public void localSearchStarted() {
		startTime     = System.currentTimeMillis();
		endTime       = startTime + Properties.LOCAL_SEARCH_BUDGET;
		tests         = 0;
		suites        = 0;
		fitnessEvaluations      = 0;
		executedStart = MaxStatementsStoppingCondition.getNumExecutedStatements();
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#searchStarted(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void searchStarted(GeneticAlgorithm<?> algorithm) {
		ga = algorithm;
		tests         = 0;
		suites        = 0;
		fitnessEvaluations      = 0;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#iteration(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void iteration(GeneticAlgorithm<?> algorithm) {
		tests         = 0;
		suites        = 0;
		fitnessEvaluations      = 0;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#searchFinished(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void searchFinished(GeneticAlgorithm<?> algorithm) {
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
