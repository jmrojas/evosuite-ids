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

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
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

	private boolean[][] coverage;

	public CoverageReportGenerator(boolean[][] cov) {
		this.coverage = cov;
	}

	public void writeCoverage()
	{
		if (!Properties.COVERAGE_MATRIX)
			return ;
		else
		{
			StringBuilder suite = new StringBuilder();
			for (int i = 0; i < this.coverage.length; i++)
			{
				StringBuilder test = new StringBuilder();
				for (int j = 0; j < this.coverage[i].length - 1; j++)
				{
					if (this.coverage[i][j])
						test.append("1 ");
					else
						test.append("0 ");
				}

				if (this.coverage[i][this.coverage[i].length - 1])
					test.append("+\n");
				else
					test.append("-\n");

				suite.append(test);
			}

			Utils.writeFile(suite.toString(), new File(getReportDir().getAbsolutePath() + "/data/" + Properties.TARGET_CLASS + ".matrix"));
		}
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
