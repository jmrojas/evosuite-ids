package org.evosuite.coverage.entropy;

import java.util.List;

import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.evosuite.utils.LoggingUtils;

/**
 * 
 */
public class EntropyCoverageSuiteFitness extends
		TestSuiteFitnessFunction
{
	private static final long serialVersionUID = -878646285915396403L;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		List<ExecutionResult> results = runTestSuite(suite);
		double fitness = 0.0;

		List<? extends TestFitnessFunction> totalGoals = EntropyCoverageFactory.retrieveCoverageGoals();
		double rho = 0.0;

		/*for (TestFitnessFunction goal : totalGoals)
		{
			double num_ones = 0.0;
			for (ExecutionResult result : results)
			{
				TestChromosome tc = new TestChromosome();
				tc.setTestCase(result.test);

				num_ones += goal.getFitness(tc, result);
			}

			rho += num_ones;
		}*/
		for (ExecutionResult result : results)
		{
			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			double num_ones = 0.0;
			for (TestFitnessFunction goal : totalGoals){
				num_ones += goal.getFitness(tc, result);
			}

			rho += num_ones;
		}

		fitness = Math.abs(0.5 - (rho / EntropyCoverageFactory.getNumGoals() / (EntropyCoverageFactory.getNumTests() + suite.size())));
		LoggingUtils.getEvoLogger().info("Fitness = " + fitness);

		updateIndividual(suite, fitness);

		return fitness;
	}
}
