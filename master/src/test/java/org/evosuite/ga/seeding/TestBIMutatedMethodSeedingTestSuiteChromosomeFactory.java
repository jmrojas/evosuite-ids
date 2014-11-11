package org.evosuite.ga.seeding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.seeding.factories.BIMutatedMethodSeedingTestSuiteChromosomeFactory;
import org.evosuite.testcase.TestCase;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.staticusage.Class1;

public class TestBIMutatedMethodSeedingTestSuiteChromosomeFactory extends SystemTest {

	ChromosomeSampleFactory defaultFactory = new ChromosomeSampleFactory();
	TestSuiteChromosome bestIndividual;
	GeneticAlgorithm<TestSuiteChromosome> ga;
	private final static double SEED_PROBABILITY = Properties.SEED_PROBABILITY;
	private final static int SEED_MUTATIONS = Properties.SEED_MUTATIONS;

	@Before
	public void setup() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = Class1.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;
		String[] command = new String[] { "-generateSuite", "-class",
				targetClass };

		Object result = evosuite.parseCommandLine(command);

		ga = (GeneticAlgorithm<TestSuiteChromosome>) getGAFromResult(result);
		bestIndividual = (TestSuiteChromosome) ga.getBestIndividual();
	}
	
	@After
	public void restore() {
		Properties.SEED_PROBABILITY = SEED_PROBABILITY;
		Properties.SEED_MUTATIONS = SEED_MUTATIONS;
	}

	@Test
	public void testNotSeed() {
		Properties.SEED_PROBABILITY = 0;
		BIMutatedMethodSeedingTestSuiteChromosomeFactory bicf = new BIMutatedMethodSeedingTestSuiteChromosomeFactory(
				defaultFactory, bestIndividual);
		TestSuiteChromosome chromosome = bicf.getChromosome();
		
		boolean containsSeededMethod = false;
		for (int i = 0; i < chromosome.getTests().size(); i++){
			if (!chromosome.getTests().get(i).equals(ChromosomeSampleFactory.CHROMOSOME.getTests().get(i))){
				containsSeededMethod = true;
			}
		}
		assertFalse(containsSeededMethod);
	}

	@Test
	public void testBIMutatedMethod() {
		//probability is SEED_PROBABILITY/test cases, so 10 guarentees a seed
		Properties.SEED_PROBABILITY = 10;
		Properties.SEED_MUTATIONS = 0; // Test requires configured test cluster otherwise
		BIMutatedMethodSeedingTestSuiteChromosomeFactory factory = new BIMutatedMethodSeedingTestSuiteChromosomeFactory(
				defaultFactory, bestIndividual);
		TestSuiteChromosome chromosome = factory.getChromosome();
		boolean containsMutatedSeededMethod = false;
		for (TestCase t : chromosome.getTests()) {
			for (TestCase t2 : bestIndividual.getTests()) {
				if (!t.equals(t2) && !t.equals(TestSampleFactory.CHROMOSOME)) {
					// test case not from original BI or from sample factory,
					// so must be seeded mutated BI
					containsMutatedSeededMethod = true;
				}
			}
		}
		assertTrue(containsMutatedSeededMethod);
	}

}
