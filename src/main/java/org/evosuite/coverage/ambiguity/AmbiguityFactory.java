package org.evosuite.coverage.ambiguity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.evosuite.graphs.cfg.BytecodeInstructionPool;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractFitnessFactory;
import org.evosuite.utils.LoggingUtils;

public class AmbiguityFactory extends
		AbstractFitnessFactory<AmbiguityTestFitness>
{
	private static boolean called = false;
	private static List<AmbiguityTestFitness> goals = new ArrayList<AmbiguityTestFitness>();
	private static LinkedHashMap<String, List<TestFitnessFunction>> goalsMap = new LinkedHashMap<String, List<TestFitnessFunction>>();

	public static int MaxGroupID = 1;
	public static double fitness;
	public static HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();

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

		for (TestFitnessFunction goal : goals)
		{
			//LoggingUtils.getEvoLogger().info("\t" + goal);

			String[] split = goal.toString().split(" ");
			if (goalsMap.containsKey(split[split.length - 1])) {
				goalsMap.get(split[split.length - 1]).add(goal);
			}
			else {
				List<TestFitnessFunction> _new = new ArrayList<TestFitnessFunction>();
				_new.add(goal);
				goalsMap.put(split[split.length - 1], _new);
			}
		}
		
		for (String key : goalsMap.keySet()) {
			LoggingUtils.getEvoLogger().info(key);
		}

		LoggingUtils.getEvoLogger().info("* Total number of coverage goals: "
		                                         + goalsMap.size() + " took "
		                                         + (end - start) + "ms");
		called = true;
		//for (AmbiguityTestFitness e : goals)
		//	LoggingUtils.getEvoLogger().info(e.toString());

		// Maximum fitness
		fitness = ((goalsMap.size() - 1.0) / 2.0);

		for (int i = 0; i < goalsMap.size(); i++) {
			table.put(i, MaxGroupID);
		}

		loadOriginalMatrix();
	}

	private static boolean isUsable(BytecodeInstruction ins) {
		return !ins.isLabel() && !ins.isLineNumber();
		//return ins.isLineNumber();
	}

	private static void loadOriginalMatrix()
	{
		/*
		 * Read the original matrix if the Coverage File exists
		 */
		BufferedReader br = null;
		try
		{
			String sCurrentLine;
			br = new BufferedReader(new FileReader("evosuite-report" + File.separator + "data" + File.separator + Properties.TARGET_CLASS + ".matrix"));

			String[] split;
			while ((sCurrentLine = br.readLine()) != null)
			{
				split = sCurrentLine.split(" ");
				List<Integer> covered = new ArrayList<Integer>(split.length);

				for (int i = 0; i < split.length - 1; i++) // - 1, because we don't want the test results
				{
					if (split[i].compareTo("1") == 0)
						covered.add(i);
				}

				updateAmbiguityGroups(covered);
			}

			HashMap<Integer, Integer> groups = new HashMap<Integer, Integer>();
			for (Integer i : table.values())
			{
				if (!groups.containsKey(i))
					groups.put(i, 1);
				else
					groups.put(i, groups.get(i) + 1);
			}

			fitness = 0.0;
			for (Integer i : groups.values()) {
				fitness += (i / ((double)goalsMap.size())) * ((i - 1.0) / 2.0);
			}
			LoggingUtils.getEvoLogger().info("* Original fitness: " + fitness);
		}
		catch (IOException e) {
			// the coverage matrix file does not exist, ok no problem... we will generate new test cases from scratch
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void updateAmbiguityGroups(List<Integer> covered)
	{
		HashMap<Integer, Integer> _tmp = new HashMap<Integer, Integer>();
		for (Integer i : covered)
		{
			int value = table.get(i);
			if (_tmp.containsKey(value))
				table.put(i, _tmp.get(value));
			else
			{
				MaxGroupID++;
				_tmp.put(value, MaxGroupID);
				table.put(i, MaxGroupID);
			}
		}
	}

	@Override
	public List<AmbiguityTestFitness> getCoverageGoals() {
		if (!called)
			computeGoals();
		return goals;
	}
	/*public static List<AmbiguityTestFitness> retrieveCoverageGoals() {
		if (!called)
			computeGoals();
		return goals;
	}*/
	public static LinkedHashMap<String, List<TestFitnessFunction>> retrieveCoverageGoals() {
		if (!called)
			computeGoals();
		return goalsMap;
	}
}
