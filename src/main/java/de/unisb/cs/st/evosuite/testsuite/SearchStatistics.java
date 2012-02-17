/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.testsuite;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.TestSuiteGenerator;
import de.unisb.cs.st.evosuite.cfg.CFGMethodAdapter;
import de.unisb.cs.st.evosuite.coverage.branch.BranchCoverageFactory;
import de.unisb.cs.st.evosuite.coverage.branch.BranchPool;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUseCoverageFactory;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.GeneticAlgorithm;
import de.unisb.cs.st.evosuite.ga.stoppingconditions.MaxFitnessEvaluationsStoppingCondition;
import de.unisb.cs.st.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import de.unisb.cs.st.evosuite.ga.stoppingconditions.MaxTestsStoppingCondition;
import de.unisb.cs.st.evosuite.testcase.ExecutionTrace;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;
import de.unisb.cs.st.evosuite.testcase.TestFitnessFunction;
import de.unisb.cs.st.evosuite.utils.ReportGenerator;
import de.unisb.cs.st.evosuite.utils.Utils;

/**
 * @author Gordon Fraser
 * 
 */
public class SearchStatistics extends ReportGenerator implements Serializable {

	private static final long serialVersionUID = 8780927435434567712L;

	private static SearchStatistics instance = null;

	private SearchStatistics() {

	}

	public static SearchStatistics getInstance() {
		if (instance == null) {
			instance = new SearchStatistics();
		}
		return instance;
	}

	public static void setInstance(SearchStatistics statistics) {
		instance = statistics;
	}

	/**
	 * Write a file for a particular run
	 * 
	 * @param run
	 */
	@Override
	protected String writeRunPage(StatisticEntry run) {

		StringBuffer sb = new StringBuffer();
		writeHTMLHeader(sb, run.className);

		sb.append("<div id=\"header\"><div id=\"logo\">");
		sb.append("<h1>");
		sb.append(run.className);
		sb.append(": ");
		sb.append(String.format("%.2f", 100.0 * run.covered_goals / run.total_goals));
		sb.append("%");
		sb.append("</h1></div></div>\n");
		sb.append("<p><a href=\"../report-generation.html\">Overview</a></p>\n");

		writeResultTable(sb, run);
		// writeMutationTable(sb);
		sb.append("<div id=\"page\"><div id=\"page-bgtop\"><div id=\"page-bgbtm\"><div id=\"content\">");
		sb.append("<div id=\"post\">");

		// Resulting test case
		sb.append("<h2 class=title>Test suite</h2>\n");
		if (run.tests != null) {
			int num = 0;
			for (TestCase test : run.tests) {
				sb.append("<h3>Test case ");
				sb.append(++num);
				sb.append("</h3>\n");
				/*
				 * if(test.exceptionThrown != null) { sb.append("<p>Raises:");
				 * sb.append(test.exceptionThrown); sb.append("</p>"); }
				 */
				sb.append("<pre class=\"prettyprint\" style=\"border: 1px solid #888;padding: 2px\">\n");
				int linecount = 1;
				String code = null;
				if (run.results.containsKey(test))
					code = test.toCode(run.results.get(test));
				else
					code = test.toCode();

				for (String line : code.split("\n")) {
					sb.append(String.format("<span class=\"nocode\"><a name=\"%d\">%3d: </a></span>",
					                        linecount, linecount));
					/*
					 * if(test.exceptionsThrown != null &&
					 * test.exception_statement == test_line)
					 * sb.append("<span style=\"background: #FF0000\">");
					 */
					sb.append(StringEscapeUtils.escapeHtml4(line));
					/*
					 * if(test.exceptionThrown != null &&
					 * test.exception_statement == test_line)
					 * sb.append("</span>");
					 */
					linecount++;
					sb.append("\n");
				}
				sb.append("</pre>\n");
			}
		} else {
			sb.append("No test cases generated");
		}
		sb.append("</div>");
		sb.append("<div id=\"post\">");

		// Source code
		/*
		 * Iterable<String> source =
		 * html_analyzer.getClassContent(run.className);
		 * sb.append("<h2>Coverage</h2>\n"); sb.append("<p>"); sb.append(
		 * "<pre class=\"prettyprint\" style=\"border: 1px solid #888;padding: 2px\">"
		 * ); int line_num = run.mutation.getLineNumber() - 3; if(line_num < 0)
		 * line_num = 0; int linecount = 1; for (String line : source) {
		 * if(linecount >= line_num && linecount < (line_num + 6)) {
		 * sb.append(String.format( "<span class=\"nocode\">%3d: </span>",
		 * linecount)); sb.append(StringEscapeUtils.escapeHtml(line));
		 * sb.append("\n"); } linecount++; } sb.append("</pre>\n");
		 */

		// Chart of fitness
		if (do_plot) {
			if (run.fitness_history.isEmpty()) {
				sb.append("<h2>No fitness history</h2>\n");
			} else {
				String filename = writeDoubleChart(run.fitness_history, run.className
				        + "-" + run.id, "Fitness");
				sb.append("<h2>Fitness</h2>\n");
				sb.append("<p>");
				sb.append("<img src=\"../img/");
				sb.append(filename);
				sb.append("\">");
				sb.append("</p>\n");
			}

			// Chart of size
			if (run.size_history.isEmpty()) {
				sb.append("<h2>No size history</h2>\n");
			} else {
				String filename = writeIntegerChart(run.size_history, run.className + "-"
				        + run.id, "Size");
				sb.append("<h2>Size</h2>\n");
				sb.append("<p>");
				sb.append("<img src=\"../img/");
				sb.append(filename);
				sb.append("\">");
				sb.append("</p>\n");
			}

			// Chart of length
			if (run.length_history.isEmpty()) {
				sb.append("<h2>No length history</h2>\n");
			} else {
				String filename = writeIntegerChart(run.length_history, run.className
				        + "-" + run.id, "Length");
				sb.append("<h2>Length</h2>\n");
				sb.append("<p>");
				sb.append("<img src=\"../img/");
				sb.append(filename);
				sb.append("\">");
				sb.append("</p>\n");
			}

			// Chart of average length
			if (run.average_length_history.isEmpty()) {
				sb.append("<h2>No average length history</h2>\n");
			} else {
				String filename = writeDoubleChart(run.average_length_history,
				                                   run.className + "-" + run.id, "Length");
				sb.append("<h2>Average Length</h2>\n");
				sb.append("<p>");
				sb.append("<img src=\"../img/");
				sb.append(filename);
				sb.append("\">");
				sb.append("</p>\n");
			}
		}
		sb.append("</div>");
		sb.append("<div id=\"post\">");

		// Source code
		try {
			Iterable<String> source = html_analyzer.getClassContent(run.className);
			sb.append("<h2 class=title>Source Code</h2>\n");
			sb.append("<p>");
			sb.append("<pre class=\"prettyprint\" style=\"border: 1px solid #888;padding: 2px\">");
			int linecount = 1;
			for (String line : source) {
				sb.append(String.format("<span class=\"nocode\"><a name=\"%d\">%3d: </a></span>",
				                        linecount, linecount));
				if (run.coverage.contains(linecount)) {
					sb.append("<span style=\"background-color: #ffffcc\">");
					sb.append(StringEscapeUtils.escapeHtml4(line));
					sb.append("</span>");
				}

				else
					sb.append(StringEscapeUtils.escapeHtml4(line));
				sb.append("\n");
				linecount++;
			}
			sb.append("</pre>\n");

			sb.append("</p>\n");
		} catch (Exception e) {
			// Don't display source if there is an error
		}
		sb.append("</div>");
		sb.append("<div id=\"post\">");

		writeParameterTable(sb, run);
		sb.append("</div>");

		writeHTMLFooter(sb);

		String filename = "report-" + run.className + "-" + run.id + ".html";
		File file = new File(REPORT_DIR.getAbsolutePath() + "/html/" + filename);
		Utils.writeFile(sb.toString(), file);
		// return file.getAbsolutePath();
		return filename;
	}

	public void mutationScore(double mutationScore) {
		StatisticEntry entry = statistics.get(statistics.size() - 1);
		entry.mutationScore = mutationScore;
	}

	@Override
	public void minimized(Chromosome result) {
		TestSuiteChromosome best = (TestSuiteChromosome) result;
		StatisticEntry entry = statistics.get(statistics.size() - 1);
		entry.tests = best.getTests();
		// TODO: Remember which lines were covered
		// This information is in ExecutionTrace.coverage
		entry.size_minimized = best.size();
		entry.length_minimized = best.totalLengthOfTestCases();
		entry.minimized_time = System.currentTimeMillis();

		entry.coverage = new HashSet<Integer>();

		// TODO isn't this more or less copy-paste of
		// BranchCoverageSuiteFitness.getFitness()?
		// DONE To make this work for other criteria too, it would be perfect if
		// one
		// could ask every suite fitness how many goals were covered

		logger.debug("Calculating coverage of best individual with fitness "
		        + result.getFitness());

		Map<Integer, Double> true_distance = new HashMap<Integer, Double>();
		Map<Integer, Double> false_distance = new HashMap<Integer, Double>();
		Map<Integer, Integer> predicate_count = new HashMap<Integer, Integer>();
		Set<String> covered_methods = new HashSet<String>();

		logger.debug("Calculating line coverage");

		for (TestChromosome test : best.tests) {
			// ExecutionTrace trace = test.last_result.trace;
			// //executeTest(test.test, entry.className);
			ExecutionTrace trace = executeTest(test.getTestCase(), entry.className);

			// if(test.last_result != null)
			// trace = test.last_result.trace;
			/*
			 * else trace = executeTest(test.test, entry.className);
			 */
			entry.coverage.addAll(getCoveredLines(trace, entry.className));

			covered_methods.addAll(trace.covered_methods.keySet());

			for (Entry<Integer, Double> e : trace.true_distances.entrySet()) {
				if (!predicate_count.containsKey(e.getKey()))
					predicate_count.put(e.getKey(), 1);
				else
					predicate_count.put(e.getKey(), predicate_count.get(e.getKey()) + 1);

				if (!true_distance.containsKey(e.getKey())
				        || true_distance.get(e.getKey()) > e.getValue()) {
					true_distance.put(e.getKey(), e.getValue());
				}
			}
			for (Entry<Integer, Double> e : trace.false_distances.entrySet()) {
				if (!predicate_count.containsKey(e.getKey()))
					predicate_count.put(e.getKey(), 1);
				else
					predicate_count.put(e.getKey(), predicate_count.get(e.getKey()) + 1);

				if (!false_distance.containsKey(e.getKey())
				        || false_distance.get(e.getKey()) > e.getValue()) {
					false_distance.put(e.getKey(), e.getValue());
				}
			}
		}

		int num_covered = 0;

		for (Integer key : predicate_count.keySet()) {
			// logger.info("Key: "+key);
			double df = true_distance.get(key);
			double dt = false_distance.get(key);
			if (df == 0.0)
				num_covered++;
			if (dt == 0.0)
				num_covered++;

		}

		entry.covered_branches = num_covered; // + covered branchless methods?
		entry.covered_methods = covered_methods.size();
		System.out.println(covered_methods);

		// DONE make this work for other criteria too. this will only work for
		// branch coverage - see searchStarted()/Finished()

		// entry.total_goals = 2 * entry.total_branches +
		// entry.branchless_methods; - moved to searchStarted()
		// entry.covered_goals = num_covered; - moved to searchFinished()

		// for(String e : CFGMethodAdapter.branchless_methods) {
		// for (String e : CFGMethodAdapter.methods) {
		// for (String e : BranchPool.getBranchlessMethods()) {
		// if (covered_methods.contains(e)) {
		// logger.info("Covered method: " + e);
		// entry.covered_goals++;
		// } else {
		// logger.info("Method is not covered: " + e);
		// }
		/*
		 * logger.debug("Covered methods: " + covered_methods.size() + "/" +
		 * entry.total_methods); for (String method : covered_methods) {
		 * logger.debug("Covered method: " + method); }
		 */
		// }

		BranchCoverageFactory factory = new BranchCoverageFactory();
		entry.goalCoverage = "";
		for (TestFitnessFunction fitness : factory.getCoverageGoals()) {
			boolean covered = false;
			for (TestChromosome test : best.tests) {
				if (fitness.isCovered(test)) {
					covered = true;
					entry.goalCoverage += "1";
					break;
				}
			}
			if (!covered)
				entry.goalCoverage += "0";
		}

	}

	public void writeStatistics() {
		makeDirs();
		writeCSV();
	}

	@Override
	public void searchFinished(GeneticAlgorithm algorithm) {
		Chromosome result = algorithm.getBestIndividual();
		StatisticEntry entry = statistics.get(statistics.size() - 1);

		entry.end_time = System.currentTimeMillis();
		entry.result_tests_executed = MaxTestsStoppingCondition.getNumExecutedTests();
		entry.result_statements_executed = MaxStatementsStoppingCondition.getNumExecutedStatements();
		entry.testExecutionTime = TestCaseExecutor.timeExecuted;
		entry.goalComputationTime = AbstractFitnessFactory.goalComputationTime;
		entry.covered_goals = TestSuiteFitnessFunction.getCoveredGoals();
		entry.timedOut = TestSuiteGenerator.global_time.isFinished();
		entry.stoppingCondition = TestSuiteGenerator.stopping_condition.getCurrentValue();
		entry.globalTimeStoppingCondition = TestSuiteGenerator.global_time.getCurrentValue();

		if (result instanceof TestSuiteChromosome) {
			TestSuiteChromosome best = (TestSuiteChromosome) result;
			entry.size_final = best.size();
			entry.length_final = best.totalLengthOfTestCases();
		}
	}

	@Override
	public void searchStarted(GeneticAlgorithm algorithm) {
		super.searchStarted(algorithm);
		StatisticEntry entry = statistics.get(statistics.size() - 1);

		entry.total_branches = Properties.TARGET_CLASS_PREFIX.isEmpty() ? BranchPool.getBranchCountForClass(Properties.TARGET_CLASS)
		        : BranchPool.getBranchCountForPrefix(Properties.TARGET_CLASS_PREFIX);

		entry.branchless_methods = Properties.TARGET_CLASS_PREFIX.isEmpty() ? BranchPool.getBranchlessMethods(Properties.TARGET_CLASS).size()
		        : BranchPool.getBranchlessMethodsPrefix(Properties.TARGET_CLASS_PREFIX).size();

		entry.total_methods = Properties.TARGET_CLASS_PREFIX.isEmpty() ? CFGMethodAdapter.getNumMethodsPrefix(Properties.TARGET_CLASS)
		        : CFGMethodAdapter.getNumMethodsPrefix(Properties.TARGET_CLASS_PREFIX);

		// TODO in order for this to work even when the criterion is neither
		// defuse nor analyze we might need to ensure that du-goal-computation
		// went through
		entry.paramDUGoalCount = DefUseCoverageFactory.getParamGoalsCount();
		entry.intraDUGoalCount = DefUseCoverageFactory.getIntraGoalsCount();
		entry.interDUGoalCount = DefUseCoverageFactory.getInterGoalsCount();

		entry.total_goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();

		// removed the code below with the one above, in order to have these
		// values for other criteria as well

		// if (algorithm.getFitnessFunction() instanceof
		// BranchCoverageSuiteFitness) {
		// BranchCoverageSuiteFitness fitness = (BranchCoverageSuiteFitness)
		// algorithm.getFitnessFunction();
		// entry.total_branches = fitness.total_branches;
		// entry.branchless_methods = fitness.branchless_methods;
		// entry.total_methods = fitness.total_methods;
		// }
	}

	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		super.iteration(algorithm);

		StatisticEntry entry = statistics.get(statistics.size() - 1);
		Chromosome best = algorithm.getBestIndividual();
		if (best instanceof TestSuiteChromosome) {
			entry.length_history.add(((TestSuiteChromosome) best).totalLengthOfTestCases());
			entry.coverage_history.add(((TestSuiteChromosome) best).coverage);
			entry.tests_executed.add(MaxTestsStoppingCondition.getNumExecutedTests());
			entry.statements_executed.add(MaxStatementsStoppingCondition.getNumExecutedStatements());
			entry.fitness_evaluations.add(MaxFitnessEvaluationsStoppingCondition.getNumFitnessEvaluations());
			entry.timeStamps.add(System.currentTimeMillis() - entry.creationTime);
		}
	}

	public StatisticEntry getLastStatisticEntry() {
		return statistics.get(statistics.size() - 1);
	}
}
