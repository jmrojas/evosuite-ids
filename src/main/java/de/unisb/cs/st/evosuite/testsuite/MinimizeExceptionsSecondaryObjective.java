/**
 * Copyright (C) 2012 Gordon Fraser, Andrea Arcuri
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
/**
 * 
 */
package de.unisb.cs.st.evosuite.testsuite;

import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.SecondaryObjective;
import de.unisb.cs.st.evosuite.testcase.ExecutableChromosome;

/**
 * @author Gordon Fraser
 * 
 */
public class MinimizeExceptionsSecondaryObjective extends SecondaryObjective {

	private static final long serialVersionUID = -4405276303273532040L;

	private int getNumExceptions(Chromosome chromosome) {
		int sum = 0;
		for (ExecutableChromosome test : ((TestSuiteChromosome) chromosome).tests) {
			if (test.getLastExecutionResult() != null)
				sum += test.getLastExecutionResult().getNumberOfThrownExceptions();
		}
		return sum;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.SecondaryObjective#compareChromosomes(de.unisb.cs.st.evosuite.ga.Chromosome, de.unisb.cs.st.evosuite.ga.Chromosome)
	 */
	@Override
	public int compareChromosomes(Chromosome chromosome1, Chromosome chromosome2) {
		return getNumExceptions(chromosome1) - getNumExceptions(chromosome2);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.SecondaryObjective#compareGenerations(de.unisb.cs.st.evosuite.ga.Chromosome, de.unisb.cs.st.evosuite.ga.Chromosome, de.unisb.cs.st.evosuite.ga.Chromosome, de.unisb.cs.st.evosuite.ga.Chromosome)
	 */
	@Override
	public int compareGenerations(Chromosome parent1, Chromosome parent2,
	        Chromosome child1, Chromosome child2) {
		return Math.min(getNumExceptions(parent1), getNumExceptions(parent2))
		        - Math.min(getNumExceptions(child1), getNumExceptions(child2));
	}

}
