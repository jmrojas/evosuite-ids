package org.evosuite.coverage.entropy;

import org.evosuite.coverage.branch.BranchCoverageFactory;
import org.evosuite.coverage.branch.BranchCoverageTestFitness;
import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;

/**
 * 
 */
public class EntropyCoverageTestFitness extends
		TestFitnessFunction
{
	private static final long	serialVersionUID = -2250185650388052675L;

	private static boolean		isToSaveCoverage = false;
	private static boolean[]	test_coverage;
	private static int			component_index;

	protected BytecodeInstruction goalInstruction;
	protected BranchCoverageTestFitness branchFitnesses;

	public EntropyCoverageTestFitness(BytecodeInstruction goalInstruction) {
		if (goalInstruction == null)
			throw new IllegalArgumentException("null given");

		this.goalInstruction = goalInstruction;
		branchFitnesses = BranchCoverageFactory.createRootBranchTestFitness(this.goalInstruction);
	}

	@Override
	public double getFitness(TestChromosome individual, ExecutionResult result)
	{
		double touch = 0.0;

		if (branchFitnesses.isCovered(result)) {
			touch = 1.0;
			if (isToSaveCoverage)
				test_coverage[component_index] = true;
		}

		component_index++;

		updateIndividual(individual, touch);
		return touch;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Statement Goal: " + goalInstruction.getMethodName() + " "
		        + goalInstruction.toString();
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(TestFitnessFunction other) {
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMaximizationFunction() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getTargetClass() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getTargetMethod() {
		return null;
	}

	public static void init(int nG) {
		test_coverage = new boolean[nG];
		component_index = 0;
	}

	public static void enableSaveCoverage() {
		isToSaveCoverage = true;
	}
	public static void disableSaveCoverage() {
		isToSaveCoverage = false;
	}

	public static boolean[] getCoverage() {
		return test_coverage;
	}
}
