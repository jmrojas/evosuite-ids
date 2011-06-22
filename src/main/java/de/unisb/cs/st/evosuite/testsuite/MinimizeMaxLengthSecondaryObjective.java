/*
 * Copyright (C) 2010 Saarland University
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
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.testsuite;

import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.SecondaryObjective;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;

/**
 * @author Gordon Fraser
 * 
 */
public class MinimizeMaxLengthSecondaryObjective extends SecondaryObjective {

	private static final long serialVersionUID = 2270058273932360617L;

	private int getMaxLength(Chromosome chromosome) {
		int max = 0;
		for (TestChromosome test : ((TestSuiteChromosome) chromosome).tests) {
			max = Math.max(max, test.size());
		}
		return max;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.SecondaryObjective#compareChromosomes(de.unisb
	 * .cs.st.evosuite.ga.Chromosome, de.unisb.cs.st.evosuite.ga.Chromosome)
	 */
	@Override
	public int compareChromosomes(Chromosome chromosome1, Chromosome chromosome2) {
		return getMaxLength(chromosome1) - getMaxLength(chromosome2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.SecondaryObjective#compareGenerations(de.unisb
	 * .cs.st.evosuite.ga.Chromosome, de.unisb.cs.st.evosuite.ga.Chromosome,
	 * de.unisb.cs.st.evosuite.ga.Chromosome,
	 * de.unisb.cs.st.evosuite.ga.Chromosome)
	 */
	@Override
	public int compareGenerations(Chromosome parent1, Chromosome parent2,
	        Chromosome child1, Chromosome child2) {
		return Math.min(getMaxLength(parent1), getMaxLength(parent2))
		        - Math.min(getMaxLength(child1), getMaxLength(child2));
	}

}