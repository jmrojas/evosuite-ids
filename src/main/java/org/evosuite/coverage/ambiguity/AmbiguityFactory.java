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
package org.evosuite.coverage.ambiguity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.evosuite.graphs.cfg.BytecodeInstructionPool;
import org.evosuite.testsuite.AbstractFitnessFactory;
import org.evosuite.utils.LoggingUtils;

/**
 * <p>
 * AmbiguityFactory class.
 * </p>
 * 
 * @author José Campos
 */
public class AmbiguityFactory extends
		AbstractFitnessFactory<AmbiguityTestFitness>
{
	private static boolean called = false;
	private static List<AmbiguityTestFitness> goals = new ArrayList<AmbiguityTestFitness>();

	private static Set<Integer> lineNumbers = new HashSet<Integer>();

	public static int MaxGroupID = 1;
	private static double fitness;
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

		LoggingUtils.getEvoLogger().info("* Total number of coverage goals: "
		                                         + goals.size() + " took "
		                                         + (end - start) + "ms");
		called = true;

		// Maximum fitness
		fitness = ((goals.size() - 1.0) / 2.0);

		for (int i = 0; i < goals.size(); i++) {
			table.put(i, MaxGroupID);
		}

		loadOriginalMatrix();
	}

	private static boolean isUsable(BytecodeInstruction ins) {
		if (lineNumbers.add(ins.getLineNumber()) == false) // the line number already exists, and cannot be added
			return false;
		return ins.isLineNumber();
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
				fitness += (i / ((double)goals.size())) * ((i - 1.0) / 2.0);
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
	public static List<AmbiguityTestFitness> retrieveCoverageGoals() {
		if (!called)
			computeGoals();
		return goals;
	}
}
