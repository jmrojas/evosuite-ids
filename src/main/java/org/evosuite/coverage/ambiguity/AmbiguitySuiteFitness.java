package org.evosuite.coverage.ambiguity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.evosuite.utils.LoggingUtils;

public class AmbiguitySuiteFitness extends
		TestSuiteFitnessFunction
{
	private static final long serialVersionUID = -5890265920781983520L;

	private static int MaxGroupID;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		MaxGroupID = AmbiguityFactory.MaxGroupID;

		List<ExecutionResult> results = runTestSuite(suite);

		List<? extends TestFitnessFunction> totalGoals = AmbiguityFactory.retrieveCoverageGoals();
		double fitness = AmbiguityFactory.fitness;

		HashMap<Integer, Integer> table = new HashMap<Integer, Integer>(AmbiguityFactory.table);

		int test_index = 0;
		int number_of_invalid_solutions = 0;

		for (ExecutionResult result : results)
		{
			double g = 0.0;

			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			List<Integer> covered = new ArrayList<Integer>();
			int index_component = 0;

			for (TestFitnessFunction goal : totalGoals) {
				if (goal.getFitness(tc, result) == 1.0)
					covered.add(index_component);
				index_component++;
			}

			TestChromosome testC = (TestChromosome) suite.getTestChromosomes().get(test_index++);

			updateAmbiguityGroups(table, covered);
			//print(table);

			HashMap<Integer, Integer> groups = new HashMap<Integer, Integer>();
			for (Integer i : table.values())
			{
				if (!groups.containsKey(i))
					groups.put(i, 1);
				else
					groups.put(i, groups.get(i) + 1);
			}

			for (Integer i : groups.values()) {
				g += (i / ((double)totalGoals.size())) * ((i - 1.0) / 2.0);
			}

			if (g < fitness) {
				fitness = g;
				testC.setSolution(true);
			}
			else {
				testC.setSolution(false);
				number_of_invalid_solutions++;
			}
		}

		if (suite.size() == number_of_invalid_solutions) {
			fitness = 100.0;
			suite.setSolution(false);
		}

		updateIndividual(suite, fitness);
		return fitness;
	}

	private static void updateAmbiguityGroups(HashMap<Integer, Integer> table, List<Integer> covered)
	{
		HashMap<Integer, Integer> _tmp = new HashMap<Integer, Integer>();
		for (Integer i : covered)
		{
			int value = table.get(i);
			if (_tmp.containsKey(value))
				table.put(i, _tmp.get(value));
			else
			{
				MaxGroupID++;
				_tmp.put(value, MaxGroupID);
				table.put(i, MaxGroupID);
			}
		}
	}

	@SuppressWarnings("unused")
	private static void print(HashMap<Integer, Integer> table) {
		LoggingUtils.getEvoLogger().info("C\tGroupID");
		for (Integer i : table.keySet()) {
			LoggingUtils.getEvoLogger().info(i + "\t" + table.get(i));
		}
		LoggingUtils.getEvoLogger().info("---------------");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}
}
