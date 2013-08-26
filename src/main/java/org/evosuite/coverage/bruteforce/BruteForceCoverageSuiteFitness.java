package org.evosuite.coverage.bruteforce;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;

/**
 * 
 */
public class BruteForceCoverageSuiteFitness extends
		TestSuiteFitnessFunction
{
	private static final long	serialVersionUID = -878646285915396403L;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		List<ExecutionResult> results = runTestSuite(suite);
		Set<Double>	coverage = new HashSet<Double>(BruteForceCoverageFactory.coverage);

		List<? extends TestFitnessFunction> totalGoals = BruteForceCoverageFactory.retrieveCoverageGoals();

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
			coverage.add(category);
		}

		BigDecimal one = BigDecimal.ONE;

		BigDecimal numerator = BigDecimal.valueOf(coverage.size());
		BigDecimal bg = new BigDecimal(2);
		BigDecimal denominator = bg.pow(totalGoals.size());

		BigDecimal fitness = one.subtract(numerator.divide(denominator));

		updateIndividual(suite, fitness.doubleValue());
		return (fitness.doubleValue());
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}
}
