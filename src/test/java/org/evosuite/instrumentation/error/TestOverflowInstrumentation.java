package org.evosuite.instrumentation.error;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.ga.GeneticAlgorithm;
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

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 7d/7d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testIntSubOverflow() {
		
		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntSubOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 7d/7d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testIntDivOverflow() {
		
		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntDivOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		// No underflow for int div
		Assert.assertEquals("Non-optimal coverage: ", 6d/7d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testIntMulOverflow() {
		
		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntMulOverflow.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ERROR_BRANCHES = true;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 7, goals);
		Assert.assertEquals("Non-optimal coverage: ", 7d/7d, best.getCoverage(), 0.001);
	}
}
