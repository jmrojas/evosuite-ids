package org.evosuite.coverage.ambiguity;

import org.evosuite.coverage.branch.BranchCoverageFactory;
import org.evosuite.coverage.branch.BranchCoverageTestFitness;
import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;

public class AmbiguityTestFitness extends
		TestFitnessFunction
{
	private static final long serialVersionUID = 3093045891696296900L;

	protected BytecodeInstruction		goalInstruction;
	protected BranchCoverageTestFitness	branchFitnesses;

	public AmbiguityTestFitness(BytecodeInstruction goalInstruction) {
		if (goalInstruction == null)
			throw new IllegalArgumentException("null given");

		this.goalInstruction = goalInstruction;
		branchFitnesses = BranchCoverageFactory.createRootBranchTestFitness(this.goalInstruction);
	}

	@Override
	public double getFitness(TestChromosome individual, ExecutionResult result)
	{
		double touch = 0.0;

		if (branchFitnesses.isCovered(result))
			touch = 1.0;

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
}
