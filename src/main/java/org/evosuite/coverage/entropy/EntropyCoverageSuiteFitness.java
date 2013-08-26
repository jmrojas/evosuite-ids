package org.evosuite.coverage.entropy;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
	private static final long	serialVersionUID = -878646285915396403L;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		double fitness = 0.0;
		int number_of_ones = EntropyCoverageFactory.getNumberOfOnes();
		//int number_of_tests = EntropyCoverageFactory.getNumberOfTests() + suite.size();
		int number_of_tests = EntropyCoverageFactory.getNumberOfTests();

		List<? extends TestFitnessFunction> totalGoals = EntropyCoverageFactory.retrieveCoverageGoals();

		LinkedHashSet<List<Boolean>>	matrix_tmp = new LinkedHashSet<List<Boolean>>();
		List<ExecutionResult> results = runTestSuite(suite);
		
		for (int i = 0; i < results.size(); i++)
		{
			ExecutionResult result = results.get(i);

			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			/*for (TestFitnessFunction goal : totalGoals)
				number_of_ones += goal.getFitness(tc, result);*/

			List<Boolean> t = new ArrayList<Boolean>();
			int t_ones = 0;
			for (TestFitnessFunction goal : totalGoals)
			{
				double g = goal.getFitness(tc, result);
				if (g > 0.0)
					t.add(true);
				else
					t.add(false);

				t_ones += g;
			}

			if (t_ones == 0) {
				suite.getTestChromosome(i).setSolution(false);
			}
			else if (matrix_tmp.add(t) == false) {
				suite.getTestChromosome(i).setSolution(false);
			}
			else if (EntropyCoverageFactory.exists(t) == true) {
				suite.getTestChromosome(i).setSolution(false);
			}
			else {
				number_of_tests++;
				number_of_ones += t_ones;

				suite.getTestChromosome(i).setSolution(true);
			}
		}

		// was not possible to generate new test cases
		if (number_of_tests == EntropyCoverageFactory.getNumberOfTests()) {
			fitness = 1.0; // penalyse this suite
			suite.setSolution(false);
		}
		else {
			suite.setSolution(true);

			fitness = ((double)number_of_ones) / ((double)(number_of_tests * totalGoals.size()));
			fitness = Math.abs(0.5 - fitness);
		}

		updateIndividual(suite, fitness);
		return fitness;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}
}
