package org.evosuite.idnaming;

import com.examples.with.different.packagename.idnaming.gnu.trove.TCollections;
import com.examples.with.different.packagename.idnaming.gnu.trove.decorator.TIntShortMapDecorator;
import com.examples.with.different.packagename.idnaming.gnu.trove.impl.unmodifiable.TUnmodifiableIntByteMap;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.coverage.TestFitnessFactory;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.strategy.TestGenerationStrategy;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Assert;
import org.junit.Test;

public class TestIdNamingAssertion extends SystemTest {

	@Test
	public void testGeneratesDuplicatedNamesWhenExceptionIsThrown() {
		// non-deterministic
		// when two tests throw the same exception, their names are duplicated.

		EvoSuite evosuite = new EvoSuite();

		String targetClass = TIntShortMapDecorator.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ID_NAMING = true;
		Properties.JUNIT_TESTS = true;

		Properties.CRITERION = new Properties.Criterion[5];
		Properties.CRITERION[0] = Properties.Criterion.METHOD;
		Properties.CRITERION[1] = Properties.Criterion.OUTPUT;
		Properties.CRITERION[2] = Properties.Criterion.INPUT;
		Properties.CRITERION[3] = Properties.Criterion.BRANCH;
		Properties.CRITERION[4] = Properties.Criterion.EXCEPTION;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = 0;
		for (TestFitnessFactory ff : TestGenerationStrategy.getFitnessFactories()) {
			goals += ff.getCoverageGoals().size();
		}
		Assert.assertEquals("Wrong number of goals: ", 171, goals);
	}

	@Test
	public void testGeneratesExceptionDuringTestGeneration() {

		EvoSuite evosuite = new EvoSuite();
		String targetClass = TCollections.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ID_NAMING = true;
		Properties.JUNIT_TESTS = true;

		Properties.CRITERION = new Properties.Criterion[1];
		Properties.CRITERION[0] = Properties.Criterion.INPUT;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = 0;
		for (TestFitnessFactory ff : TestGenerationStrategy.getFitnessFactories()) {
			goals += ff.getCoverageGoals().size();
		}
		Assert.assertEquals("Wrong number of goals: ", 420, goals);
	}

	@Test
	public void testWithUnstableTestName() {

		EvoSuite evosuite = new EvoSuite();
		String targetClass = TUnmodifiableIntByteMap.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.ID_NAMING = true;
		Properties.JUNIT_TESTS = true;

		Properties.CRITERION = new Properties.Criterion[5];
		Properties.CRITERION[0] = Properties.Criterion.METHOD;
		Properties.CRITERION[1] = Properties.Criterion.OUTPUT;
		Properties.CRITERION[2] = Properties.Criterion.INPUT;
		Properties.CRITERION[3] = Properties.Criterion.BRANCH;
		Properties.CRITERION[4] = Properties.Criterion.EXCEPTION;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = 0;
		for (TestFitnessFactory ff : TestGenerationStrategy.getFitnessFactories()) {
			goals += ff.getCoverageGoals().size();
		}
		Assert.assertEquals("Wrong number of goals: ", 228, goals);
	}
}