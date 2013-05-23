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
	private static final long serialVersionUID = -878646285915396403L;

	private boolean[][]	matrix;
	private int			test;

	/** {@inheritDoc} */
	@Override
	public double getFitness(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite)
	{
		this.test = 0;

		List<ExecutionResult> results = runTestSuite(suite);
		double fitness = 0.0;

		List<? extends TestFitnessFunction> totalGoals = EntropyCoverageFactory.retrieveCoverageGoals();
		this.initMatrix(suite.size(), totalGoals.size());
		double rho = 0.0;

		int test_index = 0;
		int number_of_invalid_solutions = 0;

		for (ExecutionResult result : results)
		{
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
			if ( this.testExist(test_coverage) || (total_number_of_ones == 0.0) ) {
				testC.setSolution(false);
				number_of_invalid_solutions++;
			}
			else {
				rho += total_number_of_ones;

				testC.setSolution(true);
				this.addCoverage(test_coverage);
			}

			EntropyCoverageTestFitness.disableSaveCoverage();
		}

		rho /= EntropyCoverageFactory.getNumGoals();
		// double bar_rho = rho / (EntropyCoverageFactory.getNumTests() + suite.size());
		double bar_rho = rho / (suite.size() - number_of_invalid_solutions); // FIXME check if number_of_invalid_solutions isn't zero!!!

		fitness = Math.abs(0.5 - bar_rho);
		updateIndividual(suite, fitness);

		return fitness;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}

	public void initMatrix(int nT, int nG) {
		this.matrix = new boolean[nT][nG];
	}
	public void addCoverage(boolean[] b) {
		this.matrix[this.test++] = b;
	}
	public boolean[][] getMatrix() {
		return this.matrix;
	}

	public boolean testExist(boolean b[]) {
		for (int i = 0; i < this.test; i++)
			if (Arrays.equals(this.matrix[i], b))
				return true;
		return false;
	}
}
