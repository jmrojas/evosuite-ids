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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.coverage.dataflow.DefUseCoverageTestFitness.DefUsePairType;
import org.evosuite.ga.Chromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.evosuite.utils.LoggingUtils;

/**
 * Evaluate fitness of a test suite with respect to all of its def-use pairs
 * 
 * First simple and naive idea: Just take each DUGoal, calculate the minimal
 * fitness over all results in the suite once a goal is covered don't check for
 * it again in the end sum up all those fitness and that is s the resulting
 * suite-fitness
 * 
 * @author Andre Mis
 */
public class DefUseCoverageSuiteFitness extends TestSuiteFitnessFunction {
	private static final long serialVersionUID = 1L;

	static List<DefUseCoverageTestFitness> goals = DefUseCoverageFactory.getDUGoals();

	/** Constant <code>totalGoals</code> */
	public final static Map<DefUsePairType, Integer> totalGoals = initTotalGoals();
	/** Constant <code>mostCoveredGoals</code> */
	public final static Map<DefUsePairType, Integer> mostCoveredGoals = new HashMap<DefUsePairType, Integer>();

	public Map<DefUsePairType, Integer> coveredGoals = new HashMap<DefUsePairType, Integer>();

	private final Map<Definition, Integer> maxDefinitionCount = new HashMap<Definition, Integer>();

	protected final BranchCoverageSuiteFitness branchFitness;

	public DefUseCoverageSuiteFitness() {
		branchFitness = new BranchCoverageSuiteFitness();

		for (DefUseCoverageTestFitness defUse : goals) {
			Definition def = defUse.getGoalDefinition();
			if (def == null) {
				logger.warn("Def is null for " + defUse);
			}
			if (!maxDefinitionCount.containsKey(def)) {
				maxDefinitionCount.put(def, 0);
			}
			maxDefinitionCount.put(def, maxDefinitionCount.get(def) + 1);
		}

		for (Definition def : maxDefinitionCount.keySet()) {
			logger.warn("Known definition: " + def + ", " + maxDefinitionCount.get(def));
		}
	}

	// Not working yet
	public double getFitnessAlternative(Chromosome individual) {
		TestSuiteChromosome suite = (TestSuiteChromosome) individual;
		List<ExecutionResult> results = runTestSuite(suite);

		Map<Definition, Set<TestChromosome>> passedDefinitions = new HashMap<Definition, Set<TestChromosome>>();
		Map<Definition, Integer> passedDefinitionCount = new HashMap<Definition, Integer>();
		for (Definition def : maxDefinitionCount.keySet()) {
			passedDefinitionCount.put(def, 0);
		}

		for (TestChromosome test : suite.getTestChromosomes()) {
			ExecutionResult result = test.getLastExecutionResult();

			if (result.hasTimeout()) {
				logger.debug("Skipping test with timeout");
				double fitness = goals.size();
				updateIndividual(individual, fitness);
				suite.setCoverage(0.0);
				logger.info("Test case has timed out, setting fitness to max value "
				        + fitness);
				return fitness;
			}

			for (Entry<Integer, Integer> entry : result.getTrace().getDefinitionExecutionCount().entrySet()) {
				Definition def = DefUsePool.getDefinitionByDefId(entry.getKey());
				if (def == null) {
					logger.warn("Could not find def " + entry.getKey());
					continue;
				}
				if (!passedDefinitions.containsKey(def))
					passedDefinitions.put(def, new HashSet<TestChromosome>());

				if (!passedDefinitionCount.containsKey(def)) {
					logger.warn("Weird, definition is not known: " + def);
					passedDefinitionCount.put(def, 0);
				}
				passedDefinitions.get(def).add(test);
				passedDefinitionCount.put(def,
				                          passedDefinitionCount.get(def)
				                                  + entry.getValue());
			}
			/*
			for (Integer id : result.getTrace().getPassedDefIDs()) {
				Definition def = DefUsePool.getDefinitionByDefId(id);
				if (!passedDefinitions.containsKey(def))
					passedDefinitions.put(def, new HashSet<TestChromosome>());

				passedDefinitions.get(def).add(test);
				passedDefinitionCount.put(def, passedDefinitionCount.get(def) + 1);
			}
			*/
		}

		// 1. Need to reach each definition
		double fitness = branchFitness.getFitness(individual);

		// 2. Need to execute each definition X times
		// TODO ...unless all defuse pairs are covered?
		for (Entry<Definition, Integer> defCount : maxDefinitionCount.entrySet()) {
			int executionCount = passedDefinitionCount.get(defCount.getKey());
			int max = maxDefinitionCount.get(defCount.getKey());
			if (executionCount < max) {
				fitness += normalize(max - executionCount);
			}
		}

		// 3. For all covered defs, calculate minimal use distance
		Set<DefUseCoverageTestFitness> coveredGoalsSet = DefUseExecutionTraceAnalyzer.getCoveredGoals(results);

		initCoverageMaps();

		for (DefUseCoverageTestFitness goal : goals) {
			if (coveredGoalsSet.contains(goal)) {
				continue;
			}

			double goalFitness = 1.0;

			// If the definition wasn't covered at all, then we don't need to dig deeper here
			if (passedDefinitions.containsKey(goal.getGoalDefinition())) {
				//LoggingUtils.getEvoLogger().info("Have passed definition: "
				//                                         + goal.getGoalDefinition());

				for (TestChromosome test : passedDefinitions.get(goal.getGoalDefinition())) {
					ExecutionResult result = test.getLastExecutionResult();

					DefUseFitnessCalculator calculator = new DefUseFitnessCalculator(
					        goal, test, result);
					double resultFitness = calculator.calculateFitnessForObjects();
					// LoggingUtils.getEvoLogger().info("Checking test - " + resultFitness);

					//double resultFitness = goal.getFitness(test, result);
					if (resultFitness < goalFitness)
						goalFitness = resultFitness;
					if (goalFitness == 0.0) {
						LoggingUtils.getEvoLogger().info("Covered new goal: " + goal);

						result.test.addCoveredGoal(goal);
						coveredGoalsSet.add(goal);
						break;
					}
				}
			}
			fitness += goalFitness;
		}

		countCoveredGoals(coveredGoalsSet);
		trackCoverageStatistics(suite);
		updateIndividual(individual, fitness);

		int coveredGoalCount = countCoveredGoals();
		int totalGoalCount = countTotalGoals();
		if (fitness == 0.0 && coveredGoalCount < totalGoalCount)
			throw new IllegalStateException("Fitness 0 implies 100% coverage "
			        + coveredGoalCount + " / " + totalGoals + " (covered / total)");

		return fitness;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.ga.FitnessFunction#getFitness(org.
	 * evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public double getFitness(Chromosome individual) {
		logger.trace("Calculating defuse fitness");

		TestSuiteChromosome suite = (TestSuiteChromosome) individual;
		List<ExecutionResult> results = runTestSuite(suite);
		double fitness = 0.0;

		Set<DefUseCoverageTestFitness> coveredGoalsSet = DefUseExecutionTraceAnalyzer.getCoveredGoals(results);

		initCoverageMaps();

		for (DefUseCoverageTestFitness goal : goals) {
			if (coveredGoalsSet.contains(goal))
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
					coveredGoalsSet.add(goal);
					break;
				}
			}
			fitness += goalFitness;
		}

		countCoveredGoals(coveredGoalsSet);
		trackCoverageStatistics(suite);
		updateIndividual(individual, fitness);

		int coveredGoalCount = countCoveredGoals();
		int totalGoalCount = countTotalGoals();
		if (fitness == 0.0 && coveredGoalCount < totalGoalCount)
			throw new IllegalStateException("Fitness 0 implies 100% coverage "
			        + coveredGoalCount + " / " + totalGoals + " (covered / total)");

		return fitness;
	}

	private static Map<DefUsePairType, Integer> initTotalGoals() {
		Map<DefUsePairType, Integer> r = new HashMap<DefUsePairType, Integer>();

		// init map
		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType.values())
			r.put(type, 0);

		// count total goals according to type
		for (DefUseCoverageTestFitness goal : goals)
			r.put(goal.getType(), r.get(goal.getType()) + 1);

		return r;
	}

	private void initCoverageMaps() {
		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType.values()) {
			coveredGoals.put(type, 0);
			if (mostCoveredGoals.get(type) == null)
				mostCoveredGoals.put(type, 0);
		}
	}

	private int countCoveredGoals() {
		return countGoalsIn(coveredGoals);
	}

	/**
	 * <p>
	 * countMostCoveredGoals
	 * </p>
	 * 
	 * @return a int.
	 */
	public static int countMostCoveredGoals() {
		return countGoalsIn(mostCoveredGoals);
	}

	private static int countTotalGoals() {
		return countGoalsIn(totalGoals);
	}

	private static int countGoalsIn(Map<DefUsePairType, Integer> goalMap) {
		int r = 0;
		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType.values()) {
			if (goalMap.get(type) != null)
				r += goalMap.get(type);
		}
		return r;
	}

	private void trackCoverageStatistics(TestSuiteChromosome suite) {

		setMostCovered();
		setSuiteCoverage(suite);
	}

	private void countCoveredGoals(Set<DefUseCoverageTestFitness> coveredGoalsSet) {
		for (DefUseCoverageTestFitness goal : coveredGoalsSet) {
			coveredGoals.put(goal.getType(), coveredGoals.get(goal.getType()) + 1);

		}
	}

	private void setSuiteCoverage(TestSuiteChromosome suite) {

		if (goals.size() > 0)
			suite.setCoverage(countCoveredGoals() / (double) goals.size());
		else
			suite.setCoverage(1.0);
	}

	private void setMostCovered() {

		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType.values()) {
			if (mostCoveredGoals.get(type) < coveredGoals.get(type)) {
				mostCoveredGoals.put(type, coveredGoals.get(type));
				if (mostCoveredGoals.get(type) > totalGoals.get(type))
					throw new IllegalStateException(
					        "Can't cover more goals than there exist of type " + type
					                + " " + mostCoveredGoals.get(type) + " / "
					                + totalGoals.get(type) + " (mostCovered / total)");
			}
		}
	}

	/**
	 * <p>
	 * printCoverage
	 * </p>
	 */
	public static void printCoverage() {

		System.out.println("* Time spent optimizing covered goals analysis: "
		        + DefUseExecutionTraceAnalyzer.timeGetCoveredGoals + "ms");

		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType.values()) {
			System.out.println("* Covered goals of type " + type + ": "
			        + mostCoveredGoals.get(type) + " / " + totalGoals.get(type));
		}

		System.out.println("* Covered " + countMostCoveredGoals() + "/"
		        + countTotalGoals() + " goals");
	}
}
