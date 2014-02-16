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
package org.evosuite.ga;

import java.util.List;

import org.evosuite.utils.Randomness;


/**
 * Roulette wheel selection
 *
 * @author Gordon Fraser
 */
public class FitnessProportionateSelection<T extends Chromosome> extends SelectionFunction<T> {

	private static final long serialVersionUID = 5206421079815585026L;

	/**
	 * Sum of fitness values, depending on minimization/maximization of the
	 * fitness function
	 */
	private double sumValue = 0.0;

	/** {@inheritDoc} */
	@Override
	public int getIndex(List<T> population) {
		//special case
		if (sumValue == 0d) {
			//here does not matter whether maximize or not.
			//we need to take at random, otherwise it d be always the first that d be chosen
			return Randomness.nextInt(population.size());
		}

		double rnd = Randomness.nextDouble() * sumValue;

		for (int i = 0; i < population.size(); i++) {
			double fit = population.get(i).getFitness();

			if (!maximize)
				fit = invert(fit);

			if (fit >= rnd)
				return i;
			else
				rnd = rnd - fit;
		}

		//now this should never happens, but possible issues with rounding errors in for example "rnd = rnd - fit"
		//in such a case, we just return a random index and we log it

		logger.debug("ATTENTION: Possible issue in FitnessProportionateSelection");
		return Randomness.nextInt(population.size());
	}

	/**
	 * Calculate total sum of fitnesses
	 * 
	 * @param population
	 */
	private void setSum(List<T> population) {
		sumValue = 0;
		for (T c : population) {
			double v = c.getFitness();
			if (!maximize)
				v = invert(v);

			sumValue += v;
		}
	}

	/*
	 * used to handle the case of minimizing the fitness
	 */
	private double invert(double x) {
		return 1d / (x + 1d);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Return n parents
	 */
	@Override
	public List<T> select(List<T> population, int number) {

		setSum(population);
		return super.select(population, number);
	}

}
