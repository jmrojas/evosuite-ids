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
package org.evosuite.coverage.mutation;

import java.util.List;

import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.ga.Chromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.TestSuiteFitnessFunction;


/**
 * @author Gordon Fraser
 * 
 */
public abstract class MutationSuiteFitness extends TestSuiteFitnessFunction {

	private static final long serialVersionUID = -8320078404661057113L;

	protected final BranchCoverageSuiteFitness branchFitness;

	protected final List<TestFitnessFunction> mutationGoals;

	public static int mostCoveredGoals = 0;

	public MutationSuiteFitness() {
		MutationFactory factory = new MutationFactory(
		        Properties.CRITERION == Criterion.WEAKMUTATION ? false : true);
		mutationGoals = factory.getCoverageGoals();
		logger.info("Mutation goals: " + mutationGoals.size());
		branchFitness = new BranchCoverageSuiteFitness();
	}

	@Override
	public ExecutionResult runTest(TestCase test) {
		return runTest(test, null);
	}

	public ExecutionResult runTest(TestCase test, Mutation mutant) {

		return MutationTestFitness.runTest(test, mutant);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.FitnessFunction#getFitness(org.evosuite.ga.Chromosome)
	 */
	@Override
	public abstract double getFitness(Chromosome individual);
}
