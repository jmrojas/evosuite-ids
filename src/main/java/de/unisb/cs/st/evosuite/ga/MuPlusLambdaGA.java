/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of the GA library.
 * 
 * GA is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * GA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * GA. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.ga;

/**
 * Alternative version of steady state GA
 * 
 * @author Gordon Fraser
 * 
 */
public class MuPlusLambdaGA extends SteadyStateGA {

	/**
	 * Generate a new search object
	 * 
	 * @param factory
	 */
	public MuPlusLambdaGA(ChromosomeFactory factory) {
		super(factory);
	}

	private boolean keepOffspring(Chromosome parent1, Chromosome parent2,
	        Chromosome offspring1, Chromosome offspring2) {
		for (SecondaryObjective objective : secondaryObjectives) {
			int c = objective.compareGenerations(parent1, parent2, offspring1,
			        offspring2);
			if (c != 0) {
				return c > 0;
			}
		}

		return true;
	}

	/**
	 * Perform one iteration of the search
	 */
	@Override
	protected void evolve() {
		logger.debug("Generating offspring");
		current_iteration++;

		Chromosome parent1 = selection_function.select(population);
		Chromosome parent2 = selection_function.select(population);

		Chromosome offspring1 = parent1.clone();
		Chromosome offspring2 = parent2.clone();

		try {
			// Crossover
			if (randomness.nextDouble() <= crossover_rate) {
				crossover_function.crossOver(offspring1, offspring2);
			}

			// Mutation
			notifyMutation(offspring1);
			offspring1.mutate();
			notifyMutation(offspring2);
			offspring2.mutate();

		} catch (ConstructionFailedException e) {
			logger.info("CrossOver/Mutation failed");
			return;
		}

		// The two offspring replace the parents if and only if one of
		// the offspring is not worse than the best parent.

		fitness_function.getFitness(offspring1);
		notifyEvaluation(offspring1);

		fitness_function.getFitness(offspring2);
		notifyEvaluation(offspring2);

		// if (replacement_function.keepOffspring(parent1, parent2, offspring1,
		if (keepOffspring(parent1, parent2, offspring1, offspring2)) {
			logger.debug("Keeping offspring");

			if (!isTooLong(offspring1)) {
				population.remove(parent1);
				population.add(offspring1);
			}
			if (!isTooLong(offspring2)) {
				population.remove(parent2);
				population.add(offspring2);
			}
		} else {
			logger.debug("Keeping parents");
		}

	}

}
