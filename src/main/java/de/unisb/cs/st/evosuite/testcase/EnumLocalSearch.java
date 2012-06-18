/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
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
/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;

/**
 * Local search on enum values means we simply iterate over all possible values
 * the enum can take
 * 
 * @author Gordon Fraser
 * 
 */
public class EnumLocalSearch implements LocalSearch {

	private static Logger logger = LoggerFactory.getLogger(LocalSearch.class);

	private Object oldValue;

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.LocalSearch#doSearch(de.unisb.cs.st.evosuite.testcase.TestChromosome, int, de.unisb.cs.st.evosuite.ga.LocalSearchObjective)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void doSearch(TestChromosome test, int statement,
	        LocalSearchObjective objective) {
		EnumPrimitiveStatement p = (EnumPrimitiveStatement) test.test.getStatement(statement);
		ExecutionResult oldResult = test.getLastExecutionResult();
		oldValue = p.getValue();

		for (Object value : p.getEnumValues()) {
			p.setValue(value);

			if (!objective.hasImproved(test)) {
				// Restore original
				p.setValue(oldValue);
				test.setLastExecutionResult(oldResult);
				test.setChanged(false);
			} else {
				break;
			}

		}

		logger.debug("Finished local search with result " + p.getCode());

	}

}
