package org.evosuite.testcase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.classpath.ResourceList;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientNodeLocal;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.testcarver.extraction.CarvingRunListener;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Randomness;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
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

	private void chopException(TestCase test, ExecutionResult result) {
		if (!result.noThrownExceptions()) {
			// No code including or after an exception should be in the pool
			Integer pos = result.getFirstPositionOfThrownException();
			if (pos != null) {
				test.chop(pos);
			} else {
				test.chop(test.size() - 1);
			}
		}
	}

	private void readTestCases() throws IllegalStateException {
		ClientServices.getInstance().getClientNode().changeState(ClientState.CARVING);

		final JUnitCore runner = new JUnitCore();
		final CarvingRunListener listener = new CarvingRunListener();
		runner.addListener(listener);

		Collection<String> junitTestNames = getListOfJUnitClassNames();

		final List<Class<?>> junitTestClasses = new ArrayList<Class<?>>();
		final org.evosuite.testcarver.extraction.CarvingClassLoader classLoader = new org.evosuite.testcarver.extraction.CarvingClassLoader();

		try {
			// instrument target class
			classLoader.loadClass(Properties.getTargetClass().getCanonicalName());
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		for (String className : junitTestNames) {

			String classNameWithDots = ResourceList.getClassNameFromResourcePath(className);
			try {
				final Class<?> junitClass = classLoader.loadClass(classNameWithDots);
				junitTestClasses.add(junitClass);
			} catch (ClassNotFoundException e) {
				logger.warn("Error trying to load JUnit test class " + classNameWithDots
				        + ": " + e);
			}
		}

		final Class<?>[] classes = new Class<?>[junitTestClasses.size()];
		junitTestClasses.toArray(classes);
		final Result result = runner.run(classes);
		for (TestCase test : listener.getTestCases()) {
			if (test.isEmpty())
				continue;

			ExecutionResult executionResult = TestCaseExecutor.runTest(test);
			if (executionResult.noThrownExceptions()) {
				logger.info("Adding carved test without exception");
				logger.info(test.toCode());
				junitTests.add(test);
			} else {
				logger.info("Exception thrown in carved test: "
				        + executionResult.getExceptionThrownAtPosition(executionResult.getFirstPositionOfThrownException()));
				for (StackTraceElement elem : executionResult.getExceptionThrownAtPosition(executionResult.getFirstPositionOfThrownException()).getStackTrace()) {
					logger.info(elem.toString());
				}
				logger.info(test.toCode(executionResult.exposeExceptionMapping()));
				if (Properties.CHOP_CARVED_EXCEPTIONS) {
					logger.info("Chopping exception of carved test");
					chopException(test, executionResult);
					if (test.hasObject(Properties.getTargetClass(), test.size())) {
						junitTests.add(test);
					} else {
						logger.info("Chopped test is empty");
					}
				} else {
					logger.info("Not adding carved test with exception: ");
				}
			}
		}
		// junitTests.addAll(listener.getTestCases());

		if (junitTests.size() > 0) {
			totalNumberOfTestsCarved = junitTests.size();

			LoggingUtils.getEvoLogger().info("* Carved {} tests from existing JUnit tests",
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
		} else {
			String outcome = "";
			for (Failure failure : result.getFailures()) {
				outcome += "(" + failure.getDescription() + ", " + failure.getTrace()
				        + ") ";
			}
			logger.warn("It was not possible to carve any test case from: "
			        + Arrays.toString(junitTestNames.toArray())
			        + ". Test execution results: " + outcome);
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

	private Collection<String> getListOfJUnitClassNames() throws IllegalStateException {

		String prop = Properties.SELECTED_JUNIT;
		if (prop == null || prop.trim().isEmpty()) {
			throw new IllegalStateException(
			        "Trying to use a test carver factory, but empty Properties.SELECTED_JUNIT");
		}

		String[] paths = prop.split(":");
		Collection<String> junitTestNames = new HashSet<String>();
		for (String s : paths) {
			junitTestNames.add(s.trim());
		}

		/* 
		Pattern pattern = Pattern.compile(Properties.JUNIT_PREFIX+".*.class");
		Collection<String> junitTestNames = ResourceList.getResources(pattern);		
		logger.info("Found "+junitTestNames.size()+" candidate junit classes for pattern "+pattern);
		*/
		return junitTestNames;
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
