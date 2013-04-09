/**
 * Copyright (C) 2011,2012,2013 Gordon Fraser, Andrea Arcuri, José Campos and EvoSuite
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
package org.evosuite.junit;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.utils.ReportGenerator;
import org.evosuite.utils.Utils;

/**
 * <p>
 * CoverageReportGenerator class.
 * </p>
 * 
 * @author José Campos
 */
public class CoverageReportGenerator extends ReportGenerator
{
	private static final long serialVersionUID = -1722842583357963373L;

	private List<TestResult> testResults;
	private List<? extends TestFitnessFunction> goals;

	public CoverageReportGenerator(List<TestResult> tRS, List<? extends TestFitnessFunction> gs) {
		this.testResults = tRS;
		this.goals = gs;
	}

	public void writeCoverage()
	{
		if (!Properties.COVERAGE_MATRIX)
			return ;
		if (Properties.CRITERION == Criterion.STATEMENT)
			this.writeStatementsCoverage();
		else
		{
			StringBuilder suite = new StringBuilder();
			for (TestResult tR : this.testResults)
			{
				TestChromosome dummy = new TestChromosome();
				ExecutionResult executionResult = new ExecutionResult(dummy.getTestCase());
				executionResult.setTrace(tR.getExecutionTrace());
				dummy.setLastExecutionResult(executionResult);
				dummy.setChanged(false);

				StringBuilder test = new StringBuilder();
				for (TestFitnessFunction goal : this.goals) {
					if (goal.isCovered(dummy))
						test.append("1 ");
					else
						test.append("0 ");
				}

				if (test.toString().contains("1")) {
					test.append(tR.wasSuccessful() ? "+\n" : "-\n");					
					suite.append(test);
				}
			}

			Utils.writeFile(suite.toString(), new File(getReportDir().getAbsolutePath() + "/data/" + Properties.TARGET_CLASS + ".matrix"));
		}
	}

	private void writeStatementsCoverage()
	{
		if (!Properties.COVERAGE_MATRIX)
			return ;

		HashMap<String, Integer> linesID = new HashMap<String, Integer>();
		int index = 0;
		for (TestFitnessFunction goal : this.goals) {
			String[] splits = goal.toString().split(" ");
			if (!linesID.containsKey(splits[splits.length - 1])) {
				linesID.put(splits[splits.length - 1], index++);
			}
		}

		boolean[][] matrix = new boolean[this.testResults.size()][linesID.size() + 1];

		int test_index = 0;
		for (TestResult tR : this.testResults)
		{
			TestChromosome dummy = new TestChromosome();
			ExecutionResult executionResult = new ExecutionResult(dummy.getTestCase());
			executionResult.setTrace(tR.getExecutionTrace());
			dummy.setLastExecutionResult(executionResult);
			dummy.setChanged(false);

			StringBuilder test = new StringBuilder();
			for (TestFitnessFunction goal : this.goals)
			{
				if (goal.isCovered(dummy))
				{
					test.append("1 ");

					String[] splits = goal.toString().split(" ");
					matrix[test_index][linesID.get(splits[splits.length - 1])] = true;
				}
				else
					test.append("0 ");
			}

			if (test.toString().contains("1")) {
				matrix[test_index][linesID.size()] = tR.wasSuccessful() ? false : true;
			}

			test_index++;
		}

		StringBuilder suite = new StringBuilder();
		for (int i = 0; i < matrix.length; i++)
		{
			StringBuilder test = new StringBuilder();
			for (int j = 0; j < matrix[0].length - 1; j++)
			{
				if (matrix[i][j] == true)
					test.append("1 ");
				else
					test.append("0 ");
			}

			if (test.toString().contains("1")) {
				test.append(matrix[i][linesID.size()] ? "-\n" : "+\n");
				suite.append(test);
			}
		}

		Utils.writeFile(suite.toString(), new File(getReportDir().getAbsolutePath() + "/data/" + Properties.TARGET_CLASS + ".matrix"));
	}

	@Override
	public void searchFinished(GeneticAlgorithm<?> algorithm) {
		// TODO Auto-generated method stub
	}

	@Override
	public void minimized(Chromosome result) {
		// TODO Auto-generated method stub	
	}
}
