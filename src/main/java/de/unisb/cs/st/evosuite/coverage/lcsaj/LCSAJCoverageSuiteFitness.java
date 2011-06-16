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

package de.unisb.cs.st.evosuite.coverage.lcsaj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import de.unisb.cs.st.evosuite.coverage.branch.Branch;
import de.unisb.cs.st.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.ExecutionTrace;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteChromosome;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteFitnessFunction;

/**
 * Evaluate fitness of a test suite with respect to all LCSAJs of a class
 * 
 * @author Merlin Lang
 * 
 */
public class LCSAJCoverageSuiteFitness extends TestSuiteFitnessFunction {
	private static final long serialVersionUID = 1L;

	public HashMap<Integer, Integer> expectedBranchExecutions = new HashMap<Integer, Integer>();

	public HashSet<LCSAJCoverageTestFitness> LCSAJFitnessFunctions = new HashSet<LCSAJCoverageTestFitness>();

	public double best_fitness = Double.MAX_VALUE;

	private final BranchCoverageSuiteFitness branchFitness = new BranchCoverageSuiteFitness();

	public LCSAJCoverageSuiteFitness() {
		ExecutionTrace.enableTraceCalls();
		for (String className : LCSAJPool.lcsaj_map.keySet()) {
			for (String methodName : LCSAJPool.lcsaj_map.get(className).keySet())
				for (LCSAJ lcsaj : LCSAJPool.lcsaj_map.get(className).get(methodName)) {
					LCSAJFitnessFunctions.add(new LCSAJCoverageTestFitness(lcsaj));

					for (Branch branch : lcsaj.getBranchInstructions()) {
						if (!expectedBranchExecutions.containsKey(branch.getActualBranchId()))
							expectedBranchExecutions.put(branch.getActualBranchId(), 1);
						else
							expectedBranchExecutions.put(branch.getActualBranchId(),
							                             expectedBranchExecutions.get(branch.getActualBranchId()) + 1);
					}
				}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.FitnessFunction#getFitness(de.unisb.cs.st.
	 * evosuite.ga.Chromosome)
	 */
	@Override
	public double getFitness(Chromosome individual) {

		TestSuiteChromosome suite = (TestSuiteChromosome) individual;
		List<ExecutionResult> results = runTestSuite(suite);

		//Map<String, Integer> call_count = new HashMap<String, Integer>();
		//HashMap<Integer, Integer> branchExecutions = new HashMap<Integer, Integer>();
		HashMap<LCSAJ, Double> LCSAJFitnesses = new HashMap<LCSAJ, Double>();

		for (ExecutionResult result : results) {
			/*
			for (Entry<String, Integer> entry : result.getTrace().covered_methods.entrySet()) {
				if (!call_count.containsKey(entry.getKey()))
					call_count.put(entry.getKey(), entry.getValue());
				else {
					call_count.put(entry.getKey(),
					               call_count.get(entry.getKey()) + entry.getValue());
				}
			}

			for (Entry<Integer, Integer> entry : result.getTrace().covered_predicates.entrySet()) {
				if (!branchExecutions.containsKey(entry.getKey()))
					branchExecutions.put(entry.getKey(), entry.getValue());
				else {
					branchExecutions.put(entry.getKey(),
					                     branchExecutions.get(entry.getKey())
					                             + entry.getValue());
				}
			}
<<<<<<< local

			for (String className : LCSAJPool.getLCSAJMap().keySet())
				for (String methodName : LCSAJPool.getLCSAJMap().get(className).keySet())
					for (LCSAJ lcsaj : LCSAJPool.getLCSAJMap().get(className).get(methodName)) {
						LCSAJFitnessFunctions.add(new LCSAJCoverageTestFitness(lcsaj));

						for (TestChromosome t : suite.getTestChromosomes()) {
							double oldFitness;
							for (LCSAJCoverageTestFitness testFitness : LCSAJFitnessFunctions) {
								oldFitness = LCSAJFitnesses.get(lcsaj);
								double newFitness = testFitness.getFitness(t, result);
								if (newFitness < oldFitness)
									LCSAJFitnesses.put(lcsaj, newFitness);
							}
						}
=======
			*/
			for (LCSAJCoverageTestFitness testFitness : LCSAJFitnessFunctions) {
				TestChromosome chromosome = new TestChromosome();
				chromosome.test = result.test;
				chromosome.last_result = result;
				double newFitness = testFitness.getFitness(chromosome, result);
				if (!LCSAJFitnesses.containsKey(testFitness.lcsaj))
					LCSAJFitnesses.put(testFitness.lcsaj, newFitness);
				else {
					double oldFitness = LCSAJFitnesses.get(testFitness.lcsaj);
					if (newFitness < oldFitness)
						LCSAJFitnesses.put(testFitness.lcsaj, newFitness);
				}
			}
		}
		double fitness = branchFitness.getFitness(individual);
		/*
				for (String e : CFGMethodAdapter.methods) {
					if (!call_count.containsKey(e)) {
						fitness += 1.0;
>>>>>>> other
					}
				}
				*/

		for (LCSAJ l : LCSAJFitnesses.keySet()) {
			fitness += normalize(LCSAJFitnesses.get(l));
		}
		/*
				for (Integer executedID : expectedBranchExecutions.keySet()) {
					if (!branchExecutions.containsKey(executedID))
						fitness += expectedBranchExecutions.get(executedID);
					else {
						if (branchExecutions.get(executedID) < expectedBranchExecutions.get(executedID))
							fitness += expectedBranchExecutions.get(executedID)
							        - branchExecutions.get(executedID);
					}
				}
				*/

		updateIndividual(individual, fitness);

		double coverage = 0.0;

		for (LCSAJ l : LCSAJFitnesses.keySet()) {
			if (LCSAJFitnesses.get(l) == 0)
				coverage += 1;
		}

		suite.setCoverage(coverage / LCSAJFitnesses.size());

		return fitness;
	}

}
