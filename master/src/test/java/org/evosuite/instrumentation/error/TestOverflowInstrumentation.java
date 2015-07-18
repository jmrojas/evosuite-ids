package org.evosuite.instrumentation.error;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.strategy.TestGenerationStrategy;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.errorbranch.IntAddOverflow;
import com.examples.with.different.packagename.errorbranch.IntDivOverflow;
import com.examples.with.different.packagename.errorbranch.IntMulOverflow;
import com.examples.with.different.packagename.errorbranch.IntSubOverflow;

public class TestOverflowInstrumentation extends SystemTest {

	@Test
	public void testIntAddOverflow() {

		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntAddOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestGenerationStrategy.getFitnessFactories().get(0).getCoverageGoals().size(); // assuming single fitness function
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 7d / 7d, best.getCoverage(), 0.001);
	}

	@Test
	public void testIntSubOverflow() {

		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntSubOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;
		Properties.SEARCH_BUDGET = 50000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestGenerationStrategy.getFitnessFactories().get(0).getCoverageGoals().size(); // assuming single fitness function
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 7d / 7d, best.getCoverage(), 0.001);
	}

	@Test
	public void testIntDivOverflow() {

		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntDivOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;
		Properties.SEARCH_BUDGET = 20000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println(best.toString());
		int goals = TestGenerationStrategy.getFitnessFactories().get(0).getCoverageGoals().size(); // assuming single fitness function
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1, best.getCoverage(), 0.001);
	}

	@Test
	public void testIntMulOverflow() {

		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntMulOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestGenerationStrategy.getFitnessFactories().get(0).getCoverageGoals().size(); // assuming single fitness function
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 7d / 7d, best.getCoverage(), 0.001);
	}
}
