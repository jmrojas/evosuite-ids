package org.evosuite.coverage.ambiguity;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.evosuite.graphs.cfg.BytecodeInstructionPool;
import org.evosuite.testsuite.AbstractFitnessFactory;
import org.evosuite.utils.LoggingUtils;

public class AmbiguityFactory extends
		AbstractFitnessFactory<AmbiguityTestFitness>
{
	private static boolean	called = false;
	private static List<AmbiguityTestFitness> goals = new ArrayList<AmbiguityTestFitness>();

	private static void computeGoals()
	{
		if (called)
			return ;

		Properties.MINIMIZE = false; // FIXME put this somewhere else

		long start = System.currentTimeMillis();
		String targetMethod = Properties.TARGET_METHOD;
		String targetClass = Properties.TARGET_CLASS;

		for (String className : BytecodeInstructionPool.getInstance(TestGenerationContext.getClassLoader()).knownClasses())
		{
			if (!(targetClass.equals("") || className.endsWith(targetClass)))
				continue;

			for (String methodName : BytecodeInstructionPool.getInstance(TestGenerationContext.getClassLoader()).knownMethods(className))
			{
				if (!targetMethod.equals("") && !methodName.equals(targetMethod))
					continue;
				for (BytecodeInstruction ins : BytecodeInstructionPool.getInstance(TestGenerationContext.getClassLoader()).getInstructionsIn(className, methodName)) {
					if (isUsable(ins))
						goals.add(new AmbiguityTestFitness(ins));
				}
			}
		}
		long end = System.currentTimeMillis();
		LoggingUtils.getEvoLogger().info("* Total number of coverage goals: "
		                                         + goals.size() + " took "
		                                         + (end - start) + "ms");
		called = true;
		//for (AmbiguityTestFitness e : goals)
		//	LoggingUtils.getEvoLogger().info(e.toString());

		//loadOriginalMatrix();
	}

	private static boolean isUsable(BytecodeInstruction ins) {
		return ins.isLineNumber();
	}

	@Override
	public List<AmbiguityTestFitness> getCoverageGoals() {
		if (!called)
			computeGoals();
		return goals;
	}
	public static List<AmbiguityTestFitness> retrieveCoverageGoals() {
		if (!called)
			computeGoals();
		return goals;
	}
}
