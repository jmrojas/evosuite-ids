package org.evosuite.coverage.entropy;

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

		for (ExecutionResult result : results) {
			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			double total_number_of_ones = 0.0;
			for (TestFitnessFunction goal : totalGoals){
				total_number_of_ones += goal.getFitness(tc, result);
			}

			rho += total_number_of_ones;
		}

		rho /= EntropyCoverageFactory.getNumGoals();
		// double bar_rho = rho / (EntropyCoverageFactory.getNumTests() + suite.size());
		double bar_rho = rho / suite.size();

		fitness = Math.abs(0.5 - bar_rho);
		updateIndividual(suite, fitness);

		return fitness;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}
}
