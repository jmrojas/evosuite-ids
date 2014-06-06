package org.evosuite.assertion.purity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.assertion.CheapPurityAnalyzer;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.testsuite.SearchStatistics;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.ReportGenerator.StatisticEntry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Type;

import com.examples.with.different.packagename.purity.AbstractInspector;
import com.examples.with.different.packagename.purity.SpecialInspector;

public class TestSpecialInspector extends SystemTest {
	private final boolean DEFAULT_RESET_STATIC_FIELDS = Properties.RESET_STATIC_FIELDS;
	private final boolean DEFAULT_JUNIT_CHECK = Properties.JUNIT_CHECK;
	private final boolean DEFAULT_JUNIT_TESTS = Properties.JUNIT_TESTS;
	private final boolean DEFAULT_PURE_INSPECTORS = Properties.PURE_INSPECTORS;
	private final boolean DEFAULT_SANDBOX = Properties.SANDBOX;

	@Before
	public void saveProperties() {
		Properties.SANDBOX = true;
		Properties.RESET_STATIC_FIELDS = true;
		Properties.JUNIT_CHECK = true;
		Properties.JUNIT_TESTS = true;
		Properties.PURE_INSPECTORS = true;
	}

	@After
	public void restoreProperties() {
		Properties.RESET_STATIC_FIELDS = DEFAULT_RESET_STATIC_FIELDS;
		Properties.JUNIT_CHECK = DEFAULT_JUNIT_CHECK;
		Properties.JUNIT_TESTS = DEFAULT_JUNIT_TESTS;
		Properties.PURE_INSPECTORS = DEFAULT_PURE_INSPECTORS;
		Properties.SANDBOX = DEFAULT_SANDBOX;
	}

	@Test
	public void test() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = SpecialInspector.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;
		String[] command = new String[] { "-generateSuite", "-class",
				targetClass };

		Object result = evosuite.parseCommandLine(command);

		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);
		double best_fitness = best.getFitness();
		Assert.assertTrue("Optimal coverage was not achieved ",
				best_fitness == 0.0);

		CheapPurityAnalyzer purityAnalyzer = CheapPurityAnalyzer.getInstance();

		String descriptor = Type.getMethodDescriptor(Type.BOOLEAN_TYPE);
		boolean greaterthanZeroIsPure = purityAnalyzer.isPure(targetClass,
				"greaterThanZero", descriptor);
		assertTrue(greaterthanZeroIsPure);

		boolean notPureGreaterthanZeroIsPure = purityAnalyzer.isPure(
				targetClass, "notPureGreaterThanZero", descriptor);
		assertFalse(notPureGreaterthanZeroIsPure);

		boolean notPureCreationOfObjectIsPure = purityAnalyzer.isPure(
				targetClass, "notPureCreationOfObject", descriptor);
		assertFalse(notPureCreationOfObjectIsPure);

		boolean pureCreationOfObjectIsPure = purityAnalyzer.isPure(targetClass,
				"pureCreationOfObject", descriptor);
		assertFalse(pureCreationOfObjectIsPure);

		boolean superPureCall = purityAnalyzer.isPure(targetClass,
				"superPureCall", descriptor);
		assertTrue(superPureCall);

		boolean notPureGreaterThanZero = purityAnalyzer.isPure(
				AbstractInspector.class.getCanonicalName(),
				"notPureGreaterThanZero", descriptor);
		assertFalse(notPureGreaterThanZero);

		boolean superNotPureCall = purityAnalyzer.isPure(targetClass,
				"superNotPureCall", descriptor);
		assertFalse(superNotPureCall);

		StatisticEntry entry = SearchStatistics.getInstance()
				.getLastStatisticEntry();
		assertFalse(entry.hadUnstableTests);
	}

}
