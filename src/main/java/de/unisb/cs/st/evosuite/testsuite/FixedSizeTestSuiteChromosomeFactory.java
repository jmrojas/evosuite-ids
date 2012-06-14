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
/**
 * 
 */
package de.unisb.cs.st.evosuite.testsuite;

import de.unisb.cs.st.evosuite.ga.ChromosomeFactory;
import de.unisb.cs.st.evosuite.testcase.RandomLengthTestFactory;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;

/**
 * @author Gordon Fraser
 * 
 */
public class FixedSizeTestSuiteChromosomeFactory implements
        ChromosomeFactory<TestSuiteChromosome> {

	private static final long serialVersionUID = 6269582177138945987L;

	/** Factory to manipulate and generate method sequences */
	private final ChromosomeFactory<TestChromosome> testChromosomeFactory;

	private final int size;

	public FixedSizeTestSuiteChromosomeFactory(int size) {
		testChromosomeFactory = new RandomLengthTestFactory();
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.ChromosomeFactory#getChromosome()
	 */
	@Override
	public TestSuiteChromosome getChromosome() {
		TestSuiteChromosome chromosome = new TestSuiteChromosome(
		        new RandomLengthTestFactory());
		chromosome.tests.clear();
		CurrentChromosomeTracker<?> tracker = CurrentChromosomeTracker.getInstance();
		tracker.modification(chromosome);

		for (int i = 0; i < size; i++) {
			TestChromosome test = testChromosomeFactory.getChromosome();
			chromosome.tests.add(test);
		}
		return chromosome;
	}

}
