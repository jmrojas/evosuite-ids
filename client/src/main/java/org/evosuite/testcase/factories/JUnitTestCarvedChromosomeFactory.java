package org.evosuite.testcase.factories;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientNodeLocal;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.testcarver.extraction.CarvingManager;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitTestCarvedChromosomeFactory implements
        ChromosomeFactory<TestChromosome> {

	private static final long serialVersionUID = -569338946355072318L;

	private static final Logger logger = LoggerFactory.getLogger(JUnitTestCarvedChromosomeFactory.class);

	private final List<TestCase> junitTests = new ArrayList<TestCase>();

	private final ChromosomeFactory<TestChromosome> defaultFactory;

	// These two variables will go once the new statistics frontend is finally finished
	private static int totalNumberOfTestsCarved = 0;

	private static double carvedCoverage = 0.0;

	/**
	 * The carved test cases are used only with a certain probability P. So,
	 * with probability 1-P the 'default' factory is rather used.
	 * 
	 * @param defaultFactory
	 * @throws IllegalStateException
	 *             if Properties are not properly set
	 */
	public JUnitTestCarvedChromosomeFactory(
	        ChromosomeFactory<TestChromosome> defaultFactory)
	        throws IllegalStateException {
		this.defaultFactory = defaultFactory;
		readTestCases();
	}


	private void readTestCases() throws IllegalStateException {
		CarvingManager manager = CarvingManager.getInstance();
		List<TestCase> tests = manager.getTestsForClass(Properties.getTargetClass());
		junitTests.addAll(tests);

		if (junitTests.size() > 0) {
			totalNumberOfTestsCarved = junitTests.size();

			LoggingUtils.getEvoLogger().info("* Using {} carved tests from existing JUnit tests for seeding",
			                                 junitTests.size());
			if (logger.isDebugEnabled()) {
				for (TestCase test : junitTests) {
					logger.debug("Carved Test: {}", test.toCode());
				}
			}
			BranchCoverageSuiteFitness f = new BranchCoverageSuiteFitness();
			TestSuiteChromosome suite = new TestSuiteChromosome();
			for (TestCase test : junitTests) {
				suite.addTest(test);
			}
			f.getFitness(suite);
			carvedCoverage = suite.getCoverage();
		}
		
		ClientNodeLocal client = ClientServices.getInstance().getClientNode();
		client.trackOutputVariable(RuntimeVariable.CarvedTests, totalNumberOfTestsCarved);
		client.trackOutputVariable(RuntimeVariable.CarvedCoverage,carvedCoverage);
	}

	public boolean hasCarvedTestCases() {
		return junitTests.size() > 0;
	}

	public int getNumCarvedTestCases() {
		return junitTests.size();
	}

	@Override
	public TestChromosome getChromosome() {
		final int N_mutations = Properties.SEED_MUTATIONS;
		final double P_clone = Properties.SEED_CLONE;

		double r = Randomness.nextDouble();

		if (r >= P_clone || junitTests.isEmpty()) {
			logger.debug("Using random test");
			return defaultFactory.getChromosome();
		}

		// Cloning
		logger.info("Cloning user test");
		TestCase test = Randomness.choice(junitTests);
		TestChromosome chromosome = new TestChromosome();
		chromosome.setTestCase(test.clone());
		if (N_mutations > 0) {
			int numMutations = Randomness.nextInt(N_mutations);
			logger.debug("Mutations: " + numMutations);

			// Delta
			for (int i = 0; i < numMutations; i++) {
				chromosome.mutate();
			}
		}

		return chromosome;
	}

	public static int getTotalNumberOfTestsCarved() {
		return totalNumberOfTestsCarved;
	}

	public static double getCoverageOfCarvedTests() {
		return carvedCoverage;
	}

}
