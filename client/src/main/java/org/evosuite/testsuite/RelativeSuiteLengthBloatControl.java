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
package org.evosuite.testsuite;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.bloatcontrol.BloatControlFunction;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.ga.metaheuristics.SearchListener;
import org.evosuite.testcase.TestChromosome;


/**
 * <p>RelativeSuiteLengthBloatControl class.</p>
 *
 * @author Gordon Fraser
 */
public class RelativeSuiteLengthBloatControl implements BloatControlFunction, SearchListener {

	private static final long serialVersionUID = -2352882640530431653L;

	/**
	 * Longest individual in current generation
	 */
	protected int current_max = 0;

	protected double best_fitness = Double.MAX_VALUE; // FIXXME: Assuming
	                                                  // minimizing fitness!

	/**
	 * {@inheritDoc}
	 *
	 * Reject individuals that are larger than twice the length of the current
	 * best individual
	 */
	@Override
	public boolean isTooLong(Chromosome chromosome) {

		// Always accept if fitness is better
		if (chromosome.getFitness() < best_fitness)
			return false;

		// logger.debug("Current - max: "+((TestSuiteChromosome)chromosome).length()+" - "+current_max);
		if (current_max > 0) {
			// if(((TestSuiteChromosome)chromosome).length() > bloat_factor *
			// current_max)
			// logger.debug("Bloat control: "+((TestSuiteChromosome)chromosome).length()
			// +" > "+ bloat_factor * current_max);

			int length = 0;
			if (chromosome instanceof TestSuiteChromosome)
				length = ((TestSuiteChromosome) chromosome).totalLengthOfTestCases();
			if (chromosome instanceof TestChromosome)
				length = ((TestChromosome) chromosome).size();
			return length > (Properties.BLOAT_FACTOR * current_max);
		} else
			return false; // Don't know max length so can't reject!

	}

	/**
	 * {@inheritDoc}
	 *
	 * Set current max length to max of best chromosome
	 */
	@Override
	public void iteration(GeneticAlgorithm<?> algorithm) {
		Chromosome best = algorithm.getBestIndividual();
		if (best instanceof TestSuiteChromosome)
			current_max = ((TestSuiteChromosome) best).totalLengthOfTestCases();
		if (best instanceof TestChromosome)
			current_max = ((TestChromosome) best).size();
		best_fitness = best.getFitness();
	}

	/** {@inheritDoc} */
	@Override
	public void searchFinished(GeneticAlgorithm<?> algorithm) {
	}

	/** {@inheritDoc} */
	@Override
	public void searchStarted(GeneticAlgorithm<?> algorithm) {
	}

	/** {@inheritDoc} */
	@Override
	public void fitnessEvaluation(Chromosome result) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.ga.SearchListener#mutation(org.evosuite
	 * .ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public void modification(Chromosome individual) {
		// TODO Auto-generated method stub

	}
}
