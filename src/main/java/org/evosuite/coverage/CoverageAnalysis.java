/**
 * 
 */
package org.evosuite.coverage;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.evosuite.TestGenerationContext;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.coverage.dataflow.DefUseCoverageTestFitness;
import org.evosuite.rmi.ClientServices;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.ExecutionTracer;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.SearchStatistics;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.ArrayUtil;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.ReportGenerator.StatisticEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gordon Fraser
 * 
 */
public class CoverageAnalysis {

	private static final Logger logger = LoggerFactory.getLogger(CoverageAnalysis.class);

	private static boolean isMutationCriterion(Properties.Criterion criterion) {
		switch (criterion) {
		case MUTATION:
		case WEAKMUTATION:
		case STRONGMUTATION:
			return true;
		default:
			return false;
		}
	}

	private static void reinstrument(TestSuiteChromosome testSuite,
	        Properties.Criterion criterion) {
		Properties.Criterion oldCriterion = Properties.CRITERION;

		if (!ExecutionTracer.isTraceCallsEnabled()) {
			ExecutionTracer.enableTraceCalls();
			testSuite.setChanged(true);
			for (TestChromosome test : testSuite.getTestChromosomes()) {
				test.setChanged(true);
				test.clearCachedResults();
				test.clearCachedMutationResults();
			}
		}

		if (oldCriterion == criterion)
			return;

		if (isMutationCriterion(criterion) && isMutationCriterion(oldCriterion)) {
			if (oldCriterion == Properties.Criterion.WEAKMUTATION) {
				testSuite.setChanged(true);
				for (TestChromosome test : testSuite.getTestChromosomes()) {
					test.setChanged(true);
					test.clearCachedResults();
					test.clearCachedMutationResults();
				}
			}
			return;
		}


		testSuite.setChanged(true);
		for (TestChromosome test : testSuite.getTestChromosomes()) {
			test.setChanged(true);
			test.clearCachedResults();
			test.clearCachedMutationResults();
		}

		/*
		List<Properties.Criterion> mutationCriteria = Arrays.asList(new Properties.Criterion[] {
		        Properties.Criterion.WEAKMUTATION, Properties.Criterion.STRONGMUTATION,
		        Properties.Criterion.MUTATION });
		if (mutationCriteria.contains(criterion)
		        && mutationCriteria.contains(oldCriterion))
			return;
			*/

		Properties.CRITERION = criterion;
		
		LoggingUtils.getEvoLogger().info("Re-instrumenting for criterion: "
		                                         + Properties.CRITERION);
		TestGenerationContext.getInstance().resetContext();
		
		// Need to load class explicitly in case there are no test cases.
		// If there are tests, then this is redundant
		Properties.getTargetClass();

		// TODO: Now all existing test cases have reflection objects pointing to the wrong classloader
		LoggingUtils.getEvoLogger().info("Changing classloader of test suite for criterion: "
		                                         + Properties.CRITERION);
		for (TestChromosome test : testSuite.getTestChromosomes()) {
			DefaultTestCase dtest = (DefaultTestCase) test.getTestCase();
			dtest.changeClassLoader(TestGenerationContext.getClassLoader());
		}

	}

	public static void analyzeCriteria(TestSuiteChromosome testSuite, String criteria) {
		Criterion oldCriterion = Properties.CRITERION;
		List<String> criteriaList = Arrays.asList(criteria.split(","));
		criteriaList.remove(oldCriterion.name());
		for (String criterion : criteriaList) {
			if (SearchStatistics.getInstance().hasCoverage(criterion)) {
				LoggingUtils.getEvoLogger().info("Skipping measuring coverage of criterion: "
				                                         + criterion);
				continue;
			}

			analyzeCoverage(testSuite, criterion);
		}
		LoggingUtils.getEvoLogger().info("Reinstrumenting for original criterion "
		                                         + oldCriterion);
		//reinstrument(testSuite, oldCriterion);
		Properties.CRITERION = oldCriterion;
		analyzeCoverage(testSuite, oldCriterion.name());
	}

	public static void analyzeCoverage(TestSuiteChromosome testSuite, String criterion) {
		try {
			LoggingUtils.getEvoLogger().info("Measuring coverage of criterion: "
			                                         + criterion);

			Properties.Criterion crit = Properties.Criterion.valueOf(criterion.toUpperCase());
			analyzeCoverage(testSuite, crit);
		} catch (IllegalArgumentException e) {
			LoggingUtils.getEvoLogger().info("* Unknown coverage criterion: " + criterion);
		}
	}

	private static RuntimeVariable getCoverageVariable(Properties.Criterion criterion) {
		switch (criterion) {
		case ALLDEFS:
			return RuntimeVariable.AllDefCoverage;
		case BRANCH:
		case EXCEPTION:
			return RuntimeVariable.BranchCoverage;
		case DEFUSE:
			return RuntimeVariable.DefUseCoverage;
		case STATEMENT:
			return RuntimeVariable.StatementCoverage;
		case RHO:
            return RuntimeVariable.RhoCoverage;
		case AMBIGUITY:
            return RuntimeVariable.AmbiguityCoverage;
		case STRONGMUTATION:
		case MUTATION:
			return RuntimeVariable.MutationScore;
		case WEAKMUTATION:
			return RuntimeVariable.WeakMutationScore;
		case IBRANCH:
		case LCSAJ:
		case PATH:
		case REGRESSION:
		default:
			throw new RuntimeException("Criterion not supported: " + criterion);

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void analyzeCoverage(TestSuiteChromosome testSuite,
	        Properties.Criterion criterion) {

		Properties.Criterion oldCriterion = Properties.CRITERION;

		reinstrument(testSuite, criterion);
		TestFitnessFactory factory = TestSuiteGenerator.getFitnessFactory(criterion);

		for(TestChromosome test : testSuite.getTestChromosomes()) {
			test.getTestCase().clearCoveredGoals();
		}
		
		int covered = 0;
		List<TestFitnessFunction> goals = factory.getCoverageGoals();
		
		for (TestFitnessFunction goal : goals) {
			if (goal.isCoveredBy(testSuite)) {
				logger.debug("Goal {} is covered", goal);
				covered++;
				//if (Properties.CRITERION == Properties.Criterion.DEFUSE) { // FIXME: remove me contains
				if (ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.DEFUSE)) {
					StatisticEntry entry = SearchStatistics.getInstance().getLastStatisticEntry();
					if (((DefUseCoverageTestFitness) goal).isInterMethodPair())
						entry.coveredInterMethodPairs++;
					else if (((DefUseCoverageTestFitness) goal).isIntraClassPair())
						entry.coveredIntraClassPairs++;
					else if (((DefUseCoverageTestFitness) goal).isParameterGoal())
						entry.coveredParameterPairs++;
					else
						entry.coveredIntraMethodPairs++;

				}
			} else {
				logger.debug("Goal {} is not covered", goal);
			}
		}

		if (goals.isEmpty()) {
			SearchStatistics.getInstance().addCoverage(criterion.toString(), 1.0);
			if (criterion == Properties.Criterion.MUTATION
			        || criterion == Properties.Criterion.STRONGMUTATION) {
				SearchStatistics.getInstance().mutationScore(1.0);
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.MutationScore,
				                                                                 1.0);
			}
			LoggingUtils.getEvoLogger().info("* Coverage of criterion " + criterion
			                                         + ": 100% (no goals)");
			ClientServices.getInstance().getClientNode().trackOutputVariable(getCoverageVariable(criterion),
			                                                                 1.0);
		} else {

			SearchStatistics.getInstance().addCoverage(criterion.toString(),
			                                           (double) covered
			                                                   / (double) goals.size());
			ClientServices.getInstance().getClientNode().trackOutputVariable(getCoverageVariable(criterion),
			                                                                 (double) covered
			                                                                         / (double) goals.size());
			if (criterion == Properties.Criterion.MUTATION
			        || criterion == Properties.Criterion.STRONGMUTATION) {
				SearchStatistics.getInstance().mutationScore((double) covered
				                                                     / (double) goals.size());
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.MutationScore,
				                                                                 (double) covered
				                                                                         / (double) goals.size());
				if (oldCriterion == criterion)
					SearchStatistics.getInstance().setCoveredGoals(covered);

			}

			LoggingUtils.getEvoLogger().info("* Coverage of criterion "
			                                         + criterion
			                                         + ": "
			                                         + NumberFormat.getPercentInstance().format((double) covered
			                                                                                            / (double) goals.size()));

		}

	}
}
