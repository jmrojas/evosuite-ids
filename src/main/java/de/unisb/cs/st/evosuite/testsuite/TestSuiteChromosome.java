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

import de.unisb.cs.st.evosuite.ga.ChromosomeFactory;
import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;

/**
 * @author Gordon Fraser
 * 
 */
public class TestSuiteChromosome extends AbstractTestSuiteChromosome<TestChromosome> {
	protected TestSuiteChromosome(ChromosomeFactory<TestChromosome> factory) {
		super(factory);
	}

	private static final long serialVersionUID = 88380759969800800L;

	/** The genes are test cases */
	public List<TestChromosome> tests = new ArrayList<TestChromosome>();

	public void addTest(TestCase test) {
		TestChromosome c = new TestChromosome();
		c.test = test;
		addTest(c);
	}

	/**
	 * Create a deep copy of this test suite
	 */
	@Override
	public TestSuiteChromosome clone() {
		TestSuiteChromosome copy = new TestSuiteChromosome(this.testChromosomeFactory);
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
	 * Apply mutation on test suite level
	 */
	@Override
	public void mutate() {
		super.mutate();

		// Handle TestCallStatements
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
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.Chromosome#localSearch()
	 */
	@Override
	public void localSearch(LocalSearchObjective objective) {
		for (int i = 0; i < tests.size(); i++) {
			TestSuiteLocalSearchObjective testObjective = new TestSuiteLocalSearchObjective(
			        (TestSuiteFitnessFunction) objective.getFitnessFunction(), this, i);
			tests.get(i).localSearch(testObjective);
		}
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
			result += test.test.toCode();
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
}
