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
package de.unisb.cs.st.evosuite.ga;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class of fitness functions
 * 
 * @author Gordon Fraser
 * 
 */
public abstract class FitnessFunction implements Serializable {

	private static final long serialVersionUID = -8876797554111396910L;

	protected static Logger logger = LoggerFactory.getLogger(FitnessFunction.class);

	/**
	 * Make sure that the individual gets to know about its fitness
	 * 
	 * @param individual
	 * @param fitness
	 */
	protected void updateIndividual(Chromosome individual, double fitness) {
		individual.setFitness(fitness);
	}

	/**
	 * Calculate and set fitness function #TODO the 'set fitness' part should be
	 * done by some abstract super class of all FitnessFunctions
	 * 
	 * @param individual
	 * @return new fitness
	 */
	public abstract double getFitness(Chromosome individual);

	/**
	 * Normalize a value using Andrea's normalization function
	 * 
	 * @param value
	 * @return
	 */
	public static double normalize(double value) throws IllegalArgumentException {
		if (value < 0d) {
			throw new IllegalArgumentException("Values to normalize cannot be negative");
		}
		return value / (1.0 + value);
	}

	/**
	 * Do we need to maximize, or minimize this function?
	 * 
	 * @return
	 */
	public abstract boolean isMaximizationFunction();
}
