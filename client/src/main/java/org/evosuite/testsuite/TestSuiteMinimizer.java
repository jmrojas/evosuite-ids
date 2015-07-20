/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
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
package org.evosuite.testsuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.Properties.AssertionStrategy;
import org.evosuite.TestGenerationContext;
import org.evosuite.TimeController;
import org.evosuite.coverage.TestFitnessFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.junit.CoverageAnalysis;
import org.evosuite.junit.writer.TestSuiteWriter;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.rmi.service.ClientStateInformation;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.StructuredTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * TestSuiteMinimizer class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class TestSuiteMinimizer {

    /**
     * Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(TestSuiteMinimizer.class);

    private final List<TestFitnessFactory<?>> testFitnessFactories = new ArrayList<TestFitnessFactory<?>>();

    /**
     * Assume the search has not started until startTime != 0
     */
    protected static long startTime = 0L;

    /**
     * <p>
     * Constructor for TestSuiteMinimizer.
     * </p>
     *
     * @param factory a {@link org.evosuite.coverage.TestFitnessFactory} object.
     */
    public TestSuiteMinimizer(TestFitnessFactory<?> factory) {
        this.testFitnessFactories.add(factory);
    }

    public TestSuiteMinimizer(List<TestFitnessFactory<? extends TestFitnessFunction>> factories) {
        this.testFitnessFactories.addAll(factories);
    }

    /**
     * <p>
     * minimize
     * </p>
     *
     * @param suite             a {@link org.evosuite.testsuite.TestSuiteChromosome} object.
     * @param minimizePerTest true is to minimize tests, false is to minimize suites.
     */
    public void minimize(TestSuiteChromosome suite, boolean minimizePerTest) {
        startTime = System.currentTimeMillis();

        String strategy = Properties.SECONDARY_OBJECTIVE;
        if (strategy.contains(":"))
            strategy = strategy.substring(0, strategy.indexOf(':'));

        ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Result_Size,
                suite.size());
        ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Result_Length,
                suite.totalLengthOfTestCases());

        logger.info("Minimization Strategy: " + strategy + ", " + suite.size() + " tests");
        suite.clearMutationHistory();

        if (minimizePerTest)
            minimizeTests(suite);
        else
            minimizeSuite(suite);

        ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Minimized_Size,
                suite.size());
        ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Minimized_Length,
                suite.totalLengthOfTestCases());
    }

    private void updateClientStatus(int progress) {
        ClientState state = ClientState.MINIMIZATION;
        ClientStateInformation information = new ClientStateInformation(state);
        information.setProgress(progress);
        ClientServices.getInstance().getClientNode().changeState(state, information);
    }

    private void filterJUnitCoveredGoals(List<TestFitnessFunction> goals) {
        if (!Properties.JUNIT_EXTEND.isEmpty()) {

            try {
                Class<?> junitClass = Class.forName(Properties.JUNIT_EXTEND, true, TestGenerationContext.getInstance().getClassLoaderForSUT());
                List<TestFitnessFunction> coveredGoals = CoverageAnalysis.getCoveredGoals(junitClass, goals);
                logger.warn("Removing " + coveredGoals.size() + " goals already covered by JUnit (total: " + goals.size() + ")");
                logger.warn("Removing " + coveredGoals + " goals already covered by JUnit (total: " + goals + ")");
                goals.removeAll(coveredGoals);
            } catch (ClassNotFoundException e) {
                logger.warn("Failed to find JUnit test suite: " + Properties.JUNIT_EXTEND);
            }
        }
    }

    /**
     * Minimize test suite with respect to the isCovered Method of the goals
     * defined by the supplied TestFitnessFactory
     *
     * @param suite a {@link org.evosuite.testsuite.TestSuiteChromosome} object.
     */
    private void minimizeTests(TestSuiteChromosome suite) {

        logger.info("Minimizing per test");

        ExecutionTracer.enableTraceCalls();

        for (TestChromosome test : suite.getTestChromosomes()) {
            test.setChanged(true);
            test.clearCachedResults();
        }

        List<TestFitnessFunction> goals = new ArrayList<TestFitnessFunction>();
        for (TestFitnessFactory<?> ff : testFitnessFactories)
            goals.addAll(ff.getCoverageGoals());
        filterJUnitCoveredGoals(goals);

        int currentGoal = 0;
        int numGoals = goals.size();

        if (Properties.MINIMIZE_SORT)
            Collections.sort(goals);

        Set<TestFitnessFunction> covered = new LinkedHashSet<TestFitnessFunction>();
        List<TestChromosome> minimizedTests = new ArrayList<TestChromosome>();
        TestSuiteWriter minimizedSuite = new TestSuiteWriter();

        for (TestFitnessFunction goal : goals) {
            updateClientStatus(numGoals > 0 ? 100 * currentGoal / numGoals : 100);
            currentGoal++;
            if (isTimeoutReached()) {
				/*
				 * FIXME: if timeout, this algorithm should be changed in a way that the modifications
				 * done so far are not lost
				 */
                logger.warn("Minimization timeout. Roll back to original test suite");
                return;
            }
            logger.info("Considering goal: " + goal);
            if (Properties.MINIMIZE_SKIP_COINCIDENTAL) {
                for (TestChromosome test : minimizedTests) {
                    if (isTimeoutReached()) {
                        logger.warn("Minimization timeout. Roll back to original test suite");
                        return;
                    }
                    if (goal.isCovered(test)) {
                        if (Properties.ASSERTION_STRATEGY == AssertionStrategy.STRUCTURED) {
                            StructuredTestCase structuredTest = (StructuredTestCase) test.getTestCase();
                            if (structuredTest.getTargetMethods().contains(goal.getTargetMethod())) {
                                logger.info("Covered by minimized test targeting "
                                        + structuredTest.getTargetMethods() + ": " + goal
                                        + " ");
                                covered.add(goal);
                                structuredTest.addPrimaryGoal(goal);
                                break;
                            }

                        } else {
                            logger.info("Covered by minimized test: " + goal);
                            covered.add(goal);
                            //test.getTestCase().addCoveredGoal(goal); // FIXME why? goal.isCovered(test) is already adding the goal
                            break;
                        }
                    }
                }
            }
            if (covered.contains(goal)) {
                logger.info("Already covered: " + goal);
                logger.info("Now the suite covers " + covered.size() + "/"
                        + goals.size() + " goals");
                continue;
            }

            List<TestChromosome> coveredTests = new ArrayList<TestChromosome>();
            for (TestChromosome test : suite.getTestChromosomes()) {
                if (goal.isCovered(test)) {
                    coveredTests.add(test);
                }
            }
            Collections.sort(coveredTests);
            if (!coveredTests.isEmpty()) {
                TestChromosome test = coveredTests.get(0);
                org.evosuite.testcase.TestCaseMinimizer minimizer = new org.evosuite.testcase.TestCaseMinimizer(
                        goal);
                TestChromosome copy = (TestChromosome) test.clone();
                if (Properties.ASSERTION_STRATEGY == AssertionStrategy.STRUCTURED) {
                    copy.setTestCase(new StructuredTestCase(test.getTestCase()));
                }
                minimizer.minimize(copy);
                if (Properties.ASSERTION_STRATEGY == AssertionStrategy.STRUCTURED) {
                    // TODO: Find proper way to determine statements
                    ((StructuredTestCase) copy.getTestCase()).setExerciseStatement(copy.size() - 1);
                }

                // TODO: Need proper list of covered goals
                copy.getTestCase().clearCoveredGoals();
                if (Properties.ASSERTION_STRATEGY == AssertionStrategy.STRUCTURED) {
                    ((StructuredTestCase) copy.getTestCase()).addPrimaryGoal(goal);
                } else {
                    copy.getTestCase().addCoveredGoal(goal);
                }
                minimizedTests.add(copy);
                minimizedSuite.insertTest(copy.getTestCase());
                covered.add(goal);

                logger.info("After new test the suite covers " + covered.size() + "/"
                        + goals.size() + " goals");

            } else {
                logger.info("Goal is not covered: " + goal);
            }
        }

        logger.info("Minimized suite covers " + covered.size() + "/" + goals.size()
                + " goals");
        suite.tests.clear();
        for (TestCase test : minimizedSuite.getTestCases()) {
            suite.addTest(test);
        }

        if (Properties.MINIMIZE_SECOND_PASS) {
            removeRedundantTestCases(suite);
        }

        double suiteCoverage = suite.getCoverage();
        logger.info("Setting coverage to: " + suiteCoverage);

        ClientState state = ClientState.MINIMIZATION;
        ClientStateInformation information = new ClientStateInformation(state);
        information.setProgress(100);
        information.setCoverage((int) (Math.round(suiteCoverage * 100)));
        ClientServices.getInstance().getClientNode().changeState(state, information);

        for (TestFitnessFunction goal : goals) {
            if (!covered.contains(goal))
                logger.info("Failed to cover: " + goal);
        }
        // suite.tests = minimizedTests;
    }

    private boolean isTimeoutReached() {
        return !TimeController.getInstance().isThereStillTimeInThisPhase();
    }

    /**
     * Minimize test suite with respect to the isCovered Method of the goals
     * defined by the supplied TestFitnessFactory
     *
     * @param suite a {@link org.evosuite.testsuite.TestSuiteChromosome} object.
     */
    private void minimizeSuite(TestSuiteChromosome suite) {

        CurrentChromosomeTracker.getInstance().modification(suite);

        // Remove previous results as they do not contain method calls
        // in the case of whole suite generation
        for (ExecutableChromosome test : suite.getTestChromosomes()) {
            test.setChanged(true);
            test.clearCachedResults();
        }

        String strategy = Properties.SECONDARY_OBJECTIVE;
        if (strategy.contains(":"))
            strategy = strategy.substring(0, strategy.indexOf(':'));

        boolean size = false;
        if (strategy.equals("size")) {
            size = true;
            // If we want to remove tests, start with shortest
            Collections.sort(suite.tests, new Comparator<TestChromosome>() {
                @Override
                public int compare(TestChromosome chromosome1, TestChromosome chromosome2) {
                    return chromosome1.size() - chromosome2.size();
                }
            });
        } else if (strategy.equals("maxlength")) {
            // If we want to remove the longest test, start with longest
            Collections.sort(suite.tests, new Comparator<TestChromosome>() {
                @Override
                public int compare(TestChromosome chromosome1, TestChromosome chromosome2) {
                    return chromosome2.size() - chromosome1.size();
                }
            });
        }

        List<Double> fitness = new ArrayList<Double>();
        for (TestFitnessFactory<?> ff : testFitnessFactories)
            fitness.add(ff.getFitness(suite));

        boolean changed = true;
        while (changed && !isTimeoutReached()) {
            changed = false;

            removeEmptyTestCases(suite);

            for (TestChromosome testChromosome : suite.tests) {
                if (isTimeoutReached())
                    break;

                for (int i = testChromosome.size() - 1; i >= 0; i--) {
                    if (isTimeoutReached())
                        break;

                    logger.debug("Current size: " + suite.size() + "/"
                            + suite.totalLengthOfTestCases());
                    logger.debug("Deleting statement "
                            + testChromosome.getTestCase().getStatement(i).getCode()
                            + " from test");
                    TestChromosome originalTestChromosome = (TestChromosome) testChromosome.clone();

                    try {
                        TestFactory testFactory = TestFactory.getInstance();
                        testFactory.deleteStatementGracefully(testChromosome.getTestCase(), i);
                        testChromosome.setChanged(true);
                        testChromosome.getTestCase().clearCoveredGoals();
                    } catch (ConstructionFailedException e) {
                        testChromosome.setChanged(false);
                        testChromosome.setTestCase(originalTestChromosome.getTestCase());
                        logger.debug("Deleting failed");
                        continue;
                    }

                    List<Double> modifiedVerFitness = new ArrayList<Double>();
                    for (TestFitnessFactory<?> ff : testFitnessFactories)
                        modifiedVerFitness.add(ff.getFitness(suite));

                    int compare_ff = 0;
                    for (int i_fit = 0; i_fit < modifiedVerFitness.size(); i_fit++) {
                        if (Double.compare(modifiedVerFitness.get(i_fit), fitness.get(i_fit)) < 0) {
                            compare_ff = -1; // new value is lower than previous one
                            break;
                        } else if (Double.compare(modifiedVerFitness.get(i_fit), fitness.get(i_fit)) > 0) {
                            compare_ff = 1; // new value is greater than previous one
                            break;
                        }
                    }

                    // the value 0 if d1 (previous fitness) is numerically equal to d2 (new fitness)
                    if (compare_ff == 0) {
                        continue; // if we can guarantee that we have the same fitness value with less statements, better
                    } else if (compare_ff < -1) // a value less than 0 if d1 is numerically less than d2
                    {
                        fitness = modifiedVerFitness;
                        changed = true;
                        /**
                         * This means, that we try to delete statements equally
                         * from each test case (If size is 'false'.) The hope is
                         * that the median length of the test cases is shorter,
                         * as opposed to the average length.
                         */
                        if (!size)
                            break;
                    }
                    // and a value greater than 0 if d1 is numerically greater than d2
                    else if (compare_ff == 1) {
                        // Restore previous state
                        logger.debug("Can't remove statement "
                                + originalTestChromosome.getTestCase().getStatement(i).getCode());
                        logger.debug("Restoring fitness from " + modifiedVerFitness
                                + " to " + fitness);
                        testChromosome.setTestCase(originalTestChromosome.getTestCase());
                        testChromosome.setLastExecutionResult(originalTestChromosome.getLastExecutionResult());
                        testChromosome.setChanged(false);
                    }
                }
            }
        }

        this.removeEmptyTestCases(suite);
        this.removeRedundantTestCases(suite);
    }

    private void removeEmptyTestCases(TestSuiteChromosome suite) {
        Iterator<TestChromosome> it = suite.tests.iterator();
        while (it.hasNext()) {
            ExecutableChromosome test = it.next();
            if (test.size() == 0) {
                logger.debug("Removing empty test case");
                it.remove();
            }
        }
    }

    private void removeRedundantTestCases(TestSuiteChromosome suite) {
        // Subsuming tests are inserted in the back, so we start inserting the final tests from there
        List<TestChromosome> tests = suite.getTestChromosomes();
        logger.debug("Before removing redundant tests: " + tests.size());

        Collections.reverse(tests);
        List<TestChromosome> finalTests = new ArrayList<TestChromosome>();
        Set<TestFitnessFunction> coveredGoals = new LinkedHashSet<TestFitnessFunction>();
        List<TestFitnessFunction> goals = new ArrayList<TestFitnessFunction>();

        for (TestFitnessFactory<?> tf : testFitnessFactories) {
            goals.addAll(tf.getCoverageGoals());
        }

        for (TestChromosome test : tests) {
            boolean addsNewGoals = false;
            for (TestFitnessFunction goal : goals) {
                if (!coveredGoals.contains(goal)) {
                    if (goal.isCovered(test)) {
                        addsNewGoals = true;
                        coveredGoals.add(goal);
                    }
                }
            }

            if (addsNewGoals) {
                coveredGoals.addAll(test.getTestCase().getCoveredGoals());
                finalTests.add(test);
            }
        }
        Collections.reverse(finalTests);
        suite.getTestChromosomes().clear();
        suite.getTestChromosomes().addAll(finalTests);
        logger.debug("After removing redundant tests: " + tests.size());

    }
}
