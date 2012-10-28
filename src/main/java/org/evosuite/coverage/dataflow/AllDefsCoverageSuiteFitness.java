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
package org.evosuite.coverage.dataflow;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties.Criterion;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;

/**
 * Evaluate fitness of a test suite with respect to all of its def-use pairs
 */
public class AllDefsCoverageSuiteFitness extends TestSuiteFitnessFunction {
	private static final long serialVersionUID = 1L;

	static List<? extends TestFitnessFunction> goals = TestSuiteGenerator.getFitnessFactory(Criterion.ALLDEFS).getCoverageGoals();

	/** Constant <code>totalGoals=goals.size()</code> */
	public static int totalGoals = goals.size();

	/** Constant <code>mostCoveredGoals=0</code> */
	public static int mostCoveredGoals = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.ga.FitnessFunction#getFitness(org.
	 * evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public double getFitness(
	        AbstractTestSuiteChromosome<? extends ExecutableChromosome> individual) {
		logger.trace("Calculating defuse fitness");

		TestSuiteChromosome suite = (TestSuiteChromosome) individual;
		List<ExecutionResult> results = runTestSuite(suite);
		double fitness = 0.0;

		Set<TestFitnessFunction> coveredGoals = new HashSet<TestFitnessFunction>();

		for (TestFitnessFunction goal : goals) {
			if (coveredGoals.contains(goal))
				continue;

			double goalFitness = 2.0;
			for (ExecutionResult result : results) {
				TestChromosome tc = new TestChromosome();
				tc.setTestCase(result.test);
				double resultFitness = goal.getFitness(tc, result);
				if (resultFitness < goalFitness)
					goalFitness = resultFitness;
				if (goalFitness == 0.0) {
					result.test.addCoveredGoal(goal);
					// System.out.println(goal.toString());
					// System.out.println(result.test.toCode());
					// System.out.println(resultFitness);
					coveredGoals.add(goal);
					break;
				}
			}
			fitness += goalFitness;
		}

		updateIndividual(individual, fitness);
		setSuiteCoverage(suite, coveredGoals);

		return fitness;
	}

	private void setSuiteCoverage(TestSuiteChromosome suite,
	        Set<TestFitnessFunction> coveredGoals) {

		if (goals.size() > 0)
			suite.setCoverage(coveredGoals.size() / (double) goals.size());
		else
			suite.setCoverage(1.0);
	}
}