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
package org.evosuite.coverage.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evosuite.ga.Chromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.ExecutionTrace;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.TestSuiteChromosome;

/**
 * <p>
 * StrongMutationSuiteFitness class.
 * </p>
 * 
 * @author fraser
 */
public class StrongMutationSuiteFitness extends MutationSuiteFitness {

	private static final long serialVersionUID = -9124328839917834720L;

	/** {@inheritDoc} */
	@Override
	public ExecutionResult runTest(TestCase test) {
		return runTest(test, null);
	}

	/** {@inheritDoc} */
	@Override
	public ExecutionResult runTest(TestCase test, Mutation mutant) {

		return StrongMutationTestFitness.runTest(test, mutant);
	}

	/**
	 * Create a list of test cases ordered by their execution time. The
	 * precondition is that all TestChromomes have been executed such that they
	 * have an ExecutionResult.
	 * 
	 * @param individual
	 * @return
	 */
	private List<TestChromosome> prioritizeTests(TestSuiteChromosome individual) {
		List<TestChromosome> executionOrder = new ArrayList<TestChromosome>(
		        individual.getTestChromosomes());

		Collections.sort(executionOrder, new Comparator<TestChromosome>() {

			@Override
			public int compare(TestChromosome tc1, TestChromosome tc2) {
				ExecutionResult result1 = tc1.getLastExecutionResult();
				ExecutionResult result2 = tc2.getLastExecutionResult();
				long diff = result1.getExecutionTime() - result2.getExecutionTime();
				if (diff == 0)
					return 0;
				else if (diff < 0)
					return -1;
				else
					return 1;
			}

		});

		return executionOrder;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.FitnessFunction#getFitness(org.evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public double getFitness(Chromosome individual) {
		runTestSuite((TestSuiteChromosome) individual);

		Set<MutationTestFitness> uncoveredMutants = MutationTestPool.getUncoveredFitnessFunctions();
		TestSuiteChromosome suite = (TestSuiteChromosome) individual;

		Map<Integer, Double> infectionDistance = new HashMap<Integer, Double>();

		for (TestChromosome test : suite.getTestChromosomes()) {
			ExecutionResult result = test.getLastExecutionResult();

			if (result.hasTimeout()) {
				logger.debug("Skipping test with timeout");
				double fitness = branchFitness.totalBranches * 2
				        + branchFitness.totalMethods + 3 * mutationGoals.size();
				updateIndividual(individual, fitness);
				suite.setCoverage(0.0);
				logger.info("Test case has timed out, setting fitness to max value "
				        + fitness);
				return fitness;
			}
			for (Integer mutationId : result.getTrace().getTouchedMutants()) {
				if (infectionDistance.containsKey(mutationId)) {
					infectionDistance.put(mutationId,
					                      Math.min(infectionDistance.get(mutationId),
					                               result.getTrace().getMutationDistance(mutationId)));
				} else {
					infectionDistance.put(mutationId,
					                      result.getTrace().getMutationDistance(mutationId));
				}
			}
		}

		/*
		Set<TestFitnessFunction> coveredMutants = ((TestSuiteChromosome) individual).getCoveredGoals();
		logger.info("Test suite covers mutants: {}", coveredMutants.size());
		Set<TestFitnessFunction> uncoveredMutants = new HashSet<TestFitnessFunction>();
		for (TestFitnessFunction mutation : mutationGoals) {
			if (!coveredMutants.contains(mutation))
				uncoveredMutants.add(mutation);
		}
		*/

		// First objective: achieve branch coverage
		logger.debug("Calculating branch fitness: ");
		//double fitness = normalize(branchFitness.getFitness(individual));
		double fitness = branchFitness.getFitness(individual);
		//logger.info("Branch fitness: " + fitness);

		// Additional objective 1: all mutants need to be touched

		// Count number of touched mutations (ask MutationObserver)

		Set<Integer> touchedMutants = new HashSet<Integer>();
		//Map<Integer, Double> infectionDistance = new HashMap<Integer, Double>();
		Map<Mutation, Double> minMutantFitness = new HashMap<Mutation, Double>();
		for (TestFitnessFunction mutant : uncoveredMutants) {
			MutationTestFitness mutantFitness = (MutationTestFitness) mutant;
			minMutantFitness.put(mutantFitness.getMutation(), 3.0);
		}
		//Set<TestChromosome> safeCopies = new HashSet<TestChromosome>();
		int mutantsChecked = 0;
		List<TestChromosome> executionOrder = prioritizeTests(suite);
		for (TestChromosome test : executionOrder) {
			ExecutionResult result = test.getLastExecutionResult();
			ExecutionTrace trace = result.getTrace();
			touchedMutants.addAll(trace.getTouchedMutants());
			logger.debug("Tests touched " + touchedMutants.size() + " mutants");

			boolean coversNewMutants = false;
			for (TestFitnessFunction mutant : uncoveredMutants) {

				MutationTestFitness mutantFitness = (MutationTestFitness) mutant;
				if (MutationTimeoutStoppingCondition.isDisabled(mutantFitness.getMutation())) {
					logger.debug("Skipping timed out mutation "
					        + mutantFitness.getMutation().getId());
					continue;
				}
				if (MutationTestPool.isCovered(mutantFitness.getMutation()))
					continue;

				if (trace.getTouchedMutants().contains(mutantFitness.getMutation().getId())) {
					mutantsChecked++;
					logger.debug("Executing test against mutant "
					        + mutantFitness.getMutation());
					double mutantFitnessValue = mutant.getFitness(test, result);
					minMutantFitness.put(mutantFitness.getMutation(),
					                     Math.min(normalize(mutantFitnessValue),
					                              minMutantFitness.get(mutantFitness.getMutation())));
					if (mutantFitnessValue == 0.0) {
						MutationTestPool.addTest(mutantFitness.getMutation(), test);
						coversNewMutants = true;
						break;
					}
					//fitness += FitnessFunction.normalize(mutantFitnessValue);
				}// else
				 //fitness += 1.0;

			}
			//if (coversNewMutants) {
			//	safeCopies.add((TestChromosome) test.clone());
			//}

			/*
						for (Entry<Integer, Double> mutation : trace.mutant_distances.entrySet()) {
							if (!infectionDistance.containsKey(mutation.getKey()))
								infectionDistance.put(mutation.getKey(), mutation.getValue());
							else
								infectionDistance.put(mutation.getKey(),
								                      Math.min(mutation.getValue(),
								                               infectionDistance.get(mutation.getKey())));
						}
						*/
		}

		//for (TestChromosome copy : safeCopies) {
		//	suite.addUnmodifiableTest(copy);
		//}
		int coverage = ((TestSuiteChromosome) individual).getCoveredGoals().size();

		if (mostCoveredGoals < coverage)
			mostCoveredGoals = coverage;

		//logger.info("Fitness values for " + minMutantFitness.size() + " mutants");
		int numKilled = MutationTestPool.getCoveredMutants();
		for (Double fit : minMutantFitness.values()) {
			//if (fit == 0.0)
			//	numKilled++;
			fitness += fit;
		}
		fitness += mutationGoals.size() - MutationTestPool.getCoveredMutants();
		logger.debug("Mutants killed: {} (Checked: {})", numKilled, mutantsChecked);

		/*
		logger.info("Touched " + touchedMutants.size() + " / " + numMutations
		        + " mutants");
		fitness += numMutations - touchedMutants.size();

		// TODO: Always execute on all mutants, or first optimize for branch coverage?
		// Idea: Always calculate infection distance, and only execute again if touched && infection distance = 0.0

		// Additional objective 2: minimize infection distance for all mutants
		double infection = 0.0;
		for (Integer mutationId : infectionDistance.keySet())
			infection += infectionDistance.get(mutationId);

		logger.info("Infection distance: " + infection);
		fitness += infection;

		// Additional objective 3: each mutant needs to propagate
		// fitness += total mutants - killed mutants

		// TODO: Each statement needs to be covered at least as often as it is mutated? No.
		*/
		updateIndividual(individual, fitness);
		suite.setCoverage(1.0 * numKilled / mutationGoals.size());

		return fitness;
	}
}
