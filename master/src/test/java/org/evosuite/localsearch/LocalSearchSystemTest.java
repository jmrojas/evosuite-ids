package org.evosuite.localsearch;


import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.Properties.LocalSearchBudgetType;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.examples.with.different.packagename.localsearch.ArrayLocalSearchExample;
import com.examples.with.different.packagename.localsearch.DoubleLocalSearchExample;
import com.examples.with.different.packagename.localsearch.FloatLocalSearchExample;
import com.examples.with.different.packagename.localsearch.IntegerLocalSearchExample;
import com.examples.with.different.packagename.localsearch.StringLocalSearchExample;

public class LocalSearchSystemTest extends SystemTest {

	private static final double oldPrimitivePool = Properties.PRIMITIVE_POOL;
	private static final double oldDseProbability = Properties.DSE_PROBABILITY;
	
	@Before
    public void init(){
        Properties.DSE_PROBABILITY = 0.0;
        Properties.PRIMITIVE_POOL = 0.0;
    }
	
	@After
	public void restoreProperties() {
		Properties.DSE_PROBABILITY = oldDseProbability;
		Properties.PRIMITIVE_POOL = oldPrimitivePool;
	}
	
	@Ignore // This seems to be trivial now?
	@Test
	public void testIntegerGlobalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntegerLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 20000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertTrue("Did not expect optimal coverage", best.getCoverage() < 1.0);
	}
	
	@Test
	public void testIntegerLocalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = IntegerLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.LOCAL_SEARCH_RATE = 1;
		Properties.LOCAL_SEARCH_PROBABILITY = 1.0;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.SUITES;
		Properties.LOCAL_SEARCH_BUDGET = 10;
		Properties.LOCAL_SEARCH_REFERENCES = false;
		Properties.LOCAL_SEARCH_ARRAYS = false;
		Properties.SEARCH_BUDGET = 50000;
		
		// Make sure that local search will have effect
		Properties.CHROMOSOME_LENGTH = 5;
		Properties.MAX_INITIAL_TESTS = 2;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}
	
	
	
	@Test
	public void testFloatGlobalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = FloatLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		// Properties.SEARCH_BUDGET = 20000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertTrue("Did not expect optimal coverage", best.getCoverage() < 1.0);
	}
	
	
	@Test
	public void testFloatLocalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = FloatLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.LOCAL_SEARCH_RATE = 2;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.TESTS;
		Properties.LOCAL_SEARCH_REFERENCES = false;
		Properties.LOCAL_SEARCH_ARRAYS = false;
		
		// Make sure that local search will have effect
		Properties.CHROMOSOME_LENGTH = 5;
		Properties.MAX_INITIAL_TESTS = 2;
		// Properties.SEARCH_BUDGET = 20000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testDoubleGlobalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = DoubleLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		//Properties.SEARCH_BUDGET = 30000;
		
		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertTrue("Did not expect optimal coverage", best.getCoverage() < 1.0);
	}
	
	@Test
	public void testDoubleLocalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = DoubleLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.LOCAL_SEARCH_RATE = 2;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.TESTS;
		Properties.LOCAL_SEARCH_REFERENCES = false;
		Properties.LOCAL_SEARCH_ARRAYS = false;
		//Properties.SEARCH_BUDGET = 30000;

		// Make sure that local search will have effect
		Properties.CHROMOSOME_LENGTH = 5;
		Properties.MAX_INITIAL_TESTS = 2;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testStringGlobalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = StringLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		//Properties.SEARCH_BUDGET = 20000;
		
		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertTrue("Did not expect optimal coverage", best.getCoverage() < 1.0);
	}
	
	@Test
	public void testStringLocalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = StringLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.LOCAL_SEARCH_RATE = 2;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.TESTS;
		Properties.LOCAL_SEARCH_REFERENCES = false;
		Properties.LOCAL_SEARCH_ARRAYS = false;
		//Properties.SEARCH_BUDGET = 30000;
		
		// Make sure that local search will have effect
		Properties.CHROMOSOME_LENGTH = 5;
		Properties.MAX_INITIAL_TESTS = 2;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testArrayGlobalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = ArrayLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 50000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertTrue("Did not expect optimal coverage", best.getCoverage() < 1.0);
	}
	
	@Test
	public void testArrayLocalSearch() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = ArrayLocalSearchExample.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.LOCAL_SEARCH_RATE = 2;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.SUITES;
		Properties.LOCAL_SEARCH_BUDGET = 1;
		Properties.LOCAL_SEARCH_REFERENCES = false;
		Properties.LOCAL_SEARCH_ARRAYS = true;
		Properties.SEARCH_BUDGET = 50000;
		
		// Make sure that local search will have effect
		Properties.CHROMOSOME_LENGTH = 5;
		Properties.MAX_INITIAL_TESTS = 2;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}
}
