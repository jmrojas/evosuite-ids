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
package org.evosuite.coverage.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evosuite.graphs.GraphPool;
import org.evosuite.graphs.cfg.RawControlFlowGraph;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractFitnessFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>AllDefsCoverageFactory class.</p>
 *
 * @author Andre Mis
 */
public class AllDefsCoverageFactory extends AbstractFitnessFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.coverage.TestFitnessFactory#getCoverageGoals()
	 */
	/** {@inheritDoc} */
	@Override
	public List<TestFitnessFunction> getCoverageGoals() {

		List<TestFitnessFunction> goals = new ArrayList<TestFitnessFunction>();

		Set<Definition> defs = DefUseCoverageFactory.getRegsiteredDefinitions();

		for (Definition def : defs) {
			Map<Use, DefUseCoverageTestFitness> uses = DefUseCoverageFactory
					.getRegisteredGoalsForDefinition(def);
			
			goals.add(createGoal(def,uses));
		}

		return goals;
	}

	
	private static AllDefsCoverageTestFitness createGoal(Definition def,
			Map<Use,DefUseCoverageTestFitness> uses) {

		return new AllDefsCoverageTestFitness(def,uses);
	}

}
