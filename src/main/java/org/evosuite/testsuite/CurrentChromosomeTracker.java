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
package org.evosuite.testsuite;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.ga.SearchListener;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestChromosome;


/**
 * @author Gordon Fraser
 * 
 */
@SuppressWarnings("unchecked")
//we can't know CType at instantiation type
public class CurrentChromosomeTracker<CType extends Chromosome> implements SearchListener {

	/** The current chromosome */
	private CType currentSuite = null;

	/** Singleton instance */
	private static CurrentChromosomeTracker<?> instance = null;

	/**
	 * Private constructor for singleton
	 */
	private CurrentChromosomeTracker() {

	}

	/**
	 * Singleton accessor
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static CurrentChromosomeTracker<?> getInstance() {
		if (instance == null)
			instance = new CurrentChromosomeTracker();

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.SearchListener#searchStarted(de.unisb.cs.st
	 * .evosuite.ga.FitnessFunction)
	 */
	@Override
	public void searchStarted(GeneticAlgorithm algorithm) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisb.cs.st.evosuite.ga.SearchListener#iteration(java.util.List)
	 */
	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.SearchListener#searchFinished(java.util.List)
	 */
	@Override
	public void searchFinished(GeneticAlgorithm algorithm) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.SearchListener#fitnessEvaluation(de.unisb.
	 * cs.st.evosuite.ga.Chromosome)
	 */
	@Override
	public void fitnessEvaluation(Chromosome individual) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.SearchListener#mutation(de.unisb.cs.st.evosuite
	 * .ga.Chromosome)
	 */
	@Override
	public void modification(Chromosome individual) {
		currentSuite = (CType) individual;
	}

	public CType getCurrentChromosome() {
		return currentSuite;
	}

	// TODO: This is very inefficient
	public void changed(TestChromosome changed) {
		if (Properties.CALL_PROBABILITY > 0) {
			TestSuiteChromosome suite = (TestSuiteChromosome) currentSuite;
			for (TestChromosome test : suite.tests) {
				if (test == changed || changed.getTestCase() == test.getTestCase())
					continue;
				for (StatementInterface s : test.getTestCase()) {
					if (s instanceof TestCallStatement) {
						TestCallStatement call = (TestCallStatement) s;
						if (call.getTest() != null
						        && call.getTest().equals(changed.getTestCase())) {
							if (!test.isChanged())
								test.setChanged(true);
							break;
						}
					}
				}
			}
		}
	}
}
