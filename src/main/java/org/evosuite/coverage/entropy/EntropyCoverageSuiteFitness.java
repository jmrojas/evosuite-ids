package org.evosuite.coverage.entropy;

import java.util.Arrays;
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

	private boolean[][]			generated_matrix;
	private int					number_of_tests;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		this.number_of_tests = 0;

		List<ExecutionResult> results = runTestSuite(suite);
		double fitness = 0.0;

		List<? extends TestFitnessFunction> totalGoals = EntropyCoverageFactory.retrieveCoverageGoals();
		this.initGeneratedMatrix(suite.size(), totalGoals.size());
		double rho = 0.0;

		int test_index = 0;
		int number_of_invalid_solutions = 0;

		for (ExecutionResult result : results)
		{
			EntropyCoverageTestFitness.enableSaveCoverage();
			EntropyCoverageTestFitness.init(totalGoals.size());

			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			double total_number_of_ones = 0.0;
			for (TestFitnessFunction goal : totalGoals)
				total_number_of_ones += goal.getFitness(tc, result);

			boolean[] test_coverage = EntropyCoverageTestFitness.getCoverage();
			TestChromosome testC = (TestChromosome) suite.getTestChromosomes().get(test_index++);

			/*
			 * This TestChromosome already exists? or Doesn't cover any component?
			 */
			if ( this.testExists(test_coverage, totalGoals.size()) || (total_number_of_ones == 0.0) ) {
				testC.setSolution(false);
				number_of_invalid_solutions++;
			}
			else {
				rho += total_number_of_ones;

				testC.setSolution(true);
				this.addTest(test_coverage);
			}

			EntropyCoverageTestFitness.disableSaveCoverage();
		}

		double bar_rho = 0.0;
		if (suite.size() == number_of_invalid_solutions) {
			bar_rho = 1.0;
			suite.setSolution(false);
		}
		else {
			rho += EntropyCoverageFactory.getNumberOfOnes();
			rho /= EntropyCoverageFactory.getNumberOfGoals();
	
			bar_rho = rho / ((suite.size() - number_of_invalid_solutions) + EntropyCoverageFactory.getNumberOfTests());
		}

		fitness = Math.abs(0.5 - bar_rho);
		updateIndividual(suite, fitness);

		return fitness;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}

	private void initGeneratedMatrix(int nT, int nG) {
		this.generated_matrix = new boolean[nT][nG];
	}
	private void addTest(boolean[] coverage) {
		this.generated_matrix[this.number_of_tests++] = coverage;
	}

	private boolean testExists(boolean coverage[], int numberGoals)
	{
		for (int i = 0; i < this.number_of_tests; i++)
			if (Arrays.equals(this.generated_matrix[i], coverage))
			//if (Hamming.isSimilar(this.generated_matrix[i], coverage, numberGoals))
				return true;

		return EntropyCoverageFactory.testExists(coverage);
	}
}
