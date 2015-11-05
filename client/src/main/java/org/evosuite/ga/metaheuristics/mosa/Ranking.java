/**
 * Copyright (C) 2010-2015 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser Public License as published by the
 * Free Software Foundation, either version 3.0 of the License, or (at your
 * option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along
 * with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.ga.metaheuristics.mosa;

import java.util.List;
import java.util.Map;

import org.evosuite.ga.Chromosome;
import org.evosuite.ga.FitnessFunction;

/**
 * Interface for ranking algorithms
 * @author Annibale, Fitusm
 *
 * @param <T>
 */
public interface Ranking<T extends Chromosome> {

	/**
	 * Returns a <code>list of chromosome</code> containing the solutions of a given rank.
	 * 
	 * @param rank
	 *            The rank
	 * @return Object representing the <code>SolutionSet</code>.
	 */
	public List<T> getSubfront(int rank); // getSubFront

	/**
	 * Returns the total number of subFronts founds.
	 */
	public int getNumberOfSubfronts(); // getNumberOfSubfronts

	/**
	 * Returns the covered goals and the corresponding covering test cases
	 */
	public Map<FitnessFunction<T>, T> getNewCoveredGoals();
}