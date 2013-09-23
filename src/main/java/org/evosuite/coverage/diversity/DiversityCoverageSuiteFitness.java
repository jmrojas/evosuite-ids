/**
 * Copyright (C) 2013 José Campos and EvoSuite
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
 * <p>
 * DiversityCoverageSuiteFitness class.
 * </p>
 * 
 * @author José Campos
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
		List<? extends TestFitnessFunction> totalGoals = DiversityCoverageFactory.retrieveCoverageGoals();
		HashMap<String, Double> categories = new HashMap<String, Double>(DiversityCoverageFactory.getCategories());

		List<ExecutionResult> results = runTestSuite(suite);

		for (int i = 0; i < results.size(); i++)
		{
			ExecutionResult result = results.get(i);

			TestChromosome tc = new TestChromosome();
			tc.setTestCase(result.test);

			StringBuilder test_coverage = new StringBuilder("");
			for (TestFitnessFunction goal : totalGoals)
			{
				double g = goal.getFitness(tc, result);
				if (g > 0.0)
					test_coverage.append("1");
				else
					test_coverage.append("0");
			}

			String hash = MD5.hash(test_coverage.toString());
			if (categories.get(hash) == null)
				categories.put(hash, 1.0);
			else
				categories.put(hash, categories.get(hash) + 1.0);
		}

		double N = 0.0;
		double sum_ni = 0.0;

		for (Double number_of_individual_in_a_category : categories.values()) {
			N += number_of_individual_in_a_category;
			double ni = number_of_individual_in_a_category * (number_of_individual_in_a_category - 1.0);

			sum_ni += ni;
		}

		N = N * (N - 1.0);

		double fitness = sum_ni / N;
		updateIndividual(suite, fitness);
		return (fitness);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}
}
