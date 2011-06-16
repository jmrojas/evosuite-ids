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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.coverage.concurrency.ConcurrencyTestCaseFactory;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.ChromosomeFactory;
import de.unisb.cs.st.evosuite.ga.ConstructionFailedException;
import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;
import de.unisb.cs.st.evosuite.testcase.RandomLengthTestFactory;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;
import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * @author Gordon Fraser
 * 
 */
public class TestSuiteChromosome extends Chromosome {

	private static final long serialVersionUID = 88380759969800800L;

	/** The genes are test cases */
	public List<TestChromosome> tests = new ArrayList<TestChromosome>();

	protected double coverage = 0.0;

	/** Factory to manipulate and generate method sequences */
	private static ChromosomeFactory test_factory = null;

	static {

		test_factory = new RandomLengthTestFactory();

		if (Properties.CRITERION == Criterion.CONCURRENCY) {
			//#TODO steenbuck we should wrap the original factory not replace it.
			test_factory = new ConcurrencyTestCaseFactory();
		}

	}

	public void addTest(TestCase test) {
		TestChromosome c = new TestChromosome();
		c.test = test;
		tests.add(c);
	}

	public void addTest(TestChromosome test) {
		tests.add(test);
	}

	/**
	 * Create a deep copy of this test suite
	 */
	@Override
	public Chromosome clone() {
		TestSuiteChromosome copy = new TestSuiteChromosome();
		for (TestChromosome test : tests) {
			// copy.tests.add((TestChromosome) test.clone());
			TestChromosome testCopy = (TestChromosome) test.clone();
			copy.tests.add(testCopy);
		}
		copy.setFitness(getFitness());
		copy.setChanged(isChanged());
		copy.coverage = coverage;
		return copy;
	}

	/**
	 * Keep up to position 1, copy from position 2 on
	 */
	@Override
	public void crossOver(Chromosome other, int position1, int position2)
	        throws ConstructionFailedException {

		TestSuiteChromosome chromosome = (TestSuiteChromosome) other;

		while (tests.size() > position1)
			tests.remove(position1);
		for (int num = position2; num < other.size(); num++) {
			// tests.add((TestChromosome) chromosome.tests.get(num).clone());
			TestChromosome testCopy = (TestChromosome) chromosome.tests.get(num).clone();
			tests.add(testCopy);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof TestSuiteChromosome))
			return false;

		TestSuiteChromosome other = (TestSuiteChromosome) obj;
		if (other.size() != size())
			return false;

		for (int i = 0; i < size(); i++) {
			if (!tests.get(i).equals(other.tests.get(i)))
				return false;
		}

		return true;
	}

	/**
	 * Apply mutation on test suite level
	 */
	@Override
	public void mutate() {
		// Mutate test cases
		for (TestChromosome test : tests) {
			if (Randomness.nextDouble() < 1.0 / tests.size()) {
				test.mutate();
			}
		}

		Iterator<TestChromosome> it = tests.iterator();
		int num = 0;
		while (it.hasNext()) {
			TestChromosome t = it.next();
			if (t.size() == 0) {
				it.remove();
				for (TestChromosome test : tests) {
					for (StatementInterface s : test.test) {
						if (s instanceof TestCallStatement) {
							TestCallStatement call = (TestCallStatement) s;
							if (call.getTestNum() > num) {
								call.setTestNum(call.getTestNum() - 1);
							}
						}
					}
				}
			} else {
				num++;
			}
		}

		final double ALPHA = 0.1;
		int count = 1;

		while (Randomness.nextDouble() <= Math.pow(ALPHA, count)
		        && size() < Properties.MAX_SIZE) {
			count++;
			// Insert at position as during initialization (i.e., using helper
			// sequences)

			TestChromosome test = (TestChromosome) test_factory.getChromosome();
			tests.add(test);
			// tests.add((TestChromosome) test_factory.getChromosome());
			logger.debug("Adding new test case ");
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.Chromosome#localSearch()
	 */
	@Override
	public void localSearch(LocalSearchObjective objective) {
		double fitnessBefore = getFitness();
		for (int i = 0; i < tests.size(); i++) {
			TestSuiteLocalSearchObjective testObjective = new TestSuiteLocalSearchObjective(
			        (TestSuiteFitnessFunction) objective.getFitnessFunction(), this, i);
			tests.get(i).localSearch(testObjective);
		}
		TestSuiteFitnessFunction fitnessFunction = (TestSuiteFitnessFunction) objective.getFitnessFunction();
		fitnessFunction.getFitness(this);

		/*
		if (fitnessBefore < getFitness()) {
			logger.warn("Fitness was " + fitnessBefore + " and now is " + getFitness());
			//for (TestChromosome chromosome : tests) {
			//	chromosome.setChanged(true);
			//	chromosome.last_result = null;
			//}
			fitnessFunction = (TestSuiteFitnessFunction) objective.getFitnessFunction();
			fitnessFunction.getFitness(this);
			logger.warn("After checking: Fitness was " + fitnessBefore + " and now is "
			        + getFitness());
			assert (false);
		}
		*/
		assert (fitnessBefore >= getFitness());
	}

	/**
	 * Number of test cases
	 */
	@Override
	public int size() {
		return tests.size();
	}

	/**
	 * 
	 * @return Sum of the lengths of the test cases
	 */
	public int length() {
		int length = 0;
		for (TestChromosome test : tests)
			length += test.size();
		return length;
	}

	/**
	 * Determine relative ordering of this chromosome to another chromosome If
	 * fitness is equal, the shorter chromosome comes first
	 */
	/*
	 * public int compareTo(Chromosome o) { if(RANK_LENGTH && getFitness() ==
	 * o.getFitness()) { return (int) Math.signum((length() -
	 * ((TestSuiteChromosome)o).length())); } else return (int)
	 * Math.signum(getFitness() - o.getFitness()); }
	 */

	@Override
	public String toString() {
		String result = "TestSuite: " + tests.size() + "\n";
		for (TestChromosome test : tests) {
			result += test.test.toCode() + "\n";
		}
		return result;
	}

	public List<TestCase> getTests() {
		List<TestCase> testcases = new ArrayList<TestCase>();
		for (TestChromosome test : tests) {
			testcases.add(test.test);
		}
		return testcases;
	}

	public TestChromosome getTestChromosome(int index) {
		return tests.get(index);
	}

	public List<TestChromosome> getTestChromosomes() {
		return tests;
	}

	public void setTestChromosome(int index, TestChromosome test) {
		tests.set(index, test);
	}

	public double getCoverage() {
		return coverage;
	}

	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.Chromosome#applyDSE()
	 */
	@Override
	public void applyDSE() {
		TestSuiteDSE dse = new TestSuiteDSE();
		dse.applyDSE(this);
	}
}
