package org.evosuite.coverage.diversity;

import java.util.HashMap;
import java.util.List;

import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;

/**
 * 
 */
public class DiversityCoverageSuiteFitness extends
		TestSuiteFitnessFunction
{
	private static final long	serialVersionUID = -878646285915396403L;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		List<ExecutionResult> results = runTestSuite(suite);
		double fitness = 0.0;

		HashMap<Double, Integer> coverage = new HashMap<Double, Integer>(DiversityCoverageFactory.coverage);

		List<? extends TestFitnessFunction> totalGoals = DiversityCoverageFactory.retrieveCoverageGoals();

		for (ExecutionResult result : results)
		{
			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			int count = 0;
			double category = 0;

			int i = 0;
			for (TestFitnessFunction goal : totalGoals)
			{
				double f = goal.getFitness(tc, result);
				count += f;

				if (f >= 1.0) {
					category += (Math.pow(2.0, i) / 100.0);
				}

				i++;
			}

			category *= ((double)count);
			if (coverage.get(category) != null)
				coverage.put(category, coverage.get(category) + 1);
			else
				coverage.put(category, 1);
		}

		double N = 0.0;
		for (Integer ni : coverage.values()) {
			N += ni;
			fitness += (ni * (ni - 0.01));
		}

		fitness /= (N * (N - 0.01));

		updateIndividual(suite, fitness);
		return (fitness);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}
}
