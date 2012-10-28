
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
 *
 * @author Gordon Fraser
 */
package org.evosuite.testsuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.evosuite.Properties;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.Properties.Criterion;
import org.evosuite.Properties.Strategy;
import org.evosuite.coverage.dataflow.DefUseCoverageSuiteFitness;
import org.evosuite.coverage.dataflow.DefUseCoverageTestFitness;
import org.evosuite.coverage.dataflow.DefUseCoverageTestFitness.DefUsePairType;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.ReportGenerator;
import org.evosuite.utils.ReportGenerator.StatisticEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class CoverageStatistics {

	/** Constant <code>logger</code> */
	protected static final Logger logger = LoggerFactory
			.getLogger(ReportGenerator.class);

	/** Constant <code>coverages</code> */
	protected static Map<Criterion, Map<Criterion, Double>> coverages = new HashMap<Criterion, Map<Criterion, Double>>();
	/** Constant <code>defuseCoverage</code> */
	protected static Map<Criterion, DefUseCoverageSuiteFitness> defuseCoverage = new HashMap<Criterion, DefUseCoverageSuiteFitness>();
	/** Constant <code>combinedCoverages</code> */
	protected static Map<Criterion, Double> combinedCoverages = new HashMap<Criterion, Double>();
	/** Constant <code>statistics</code> */
	protected static Map<Criterion, StatisticEntry> statistics = new HashMap<Criterion, StatisticEntry>();
	/** Constant <code>tests</code> */
	protected static Map<Criterion, List<TestCase>> tests = new HashMap<Criterion, List<TestCase>>();

	/** Constant <code>REPORT_DIR</code> */
	protected static final File REPORT_DIR = new File(Properties.REPORT_DIR);

	/** Constant <code>outputFile="REPORT_DIR.getAbsolutePath()/coverage.c"{trunked}</code> */
	protected static String outputFile = REPORT_DIR.getAbsolutePath()
			+ "/coverage.csv";

	/** Constant <code>supportedCriteria</code> */
	public static Criterion[] supportedCriteria = { Criterion.DEFUSE,
			Criterion.ALLDEFS, Criterion.BRANCH, Criterion.STATEMENT };

	/**
	 * <p>analyzeCoverage</p>
	 *
	 * @param best a {@link org.evosuite.testsuite.TestSuiteChromosome} object.
	 */
	public static void analyzeCoverage(TestSuiteChromosome best) {

		rememberTests(best.getTests());

		LoggingUtils.getEvoLogger().info("* Measured Coverage:");

		for (Criterion criterion : supportedCriteria) {
			TestSuiteFitnessFunction fitness = getFitness(criterion, best);
			LoggingUtils.getEvoLogger().info("\t- "
					+ criterion
					+ ": "
					+ NumberFormat.getPercentInstance().format(
							best.getCoverage()));
			setCoverage(criterion, best.getCoverage());
			if (fitness instanceof DefUseCoverageSuiteFitness) {
				defuseCoverage.put(Properties.CRITERION,
						(DefUseCoverageSuiteFitness) fitness);
			}
		}

		setStatisticEntry(SearchStatistics.getInstance()
				.getLastStatisticEntry());
	}

	/**
	 * <p>computeCombinedCoverages</p>
	 */
	public static void computeCombinedCoverages() {

		// create big suite
		ChromosomeFactory<TestChromosome> fac = null;
		TestSuiteChromosome testChrom = new TestSuiteChromosome(fac);
		for (Criterion criterion : supportedCriteria)
			for (TestCase test : tests.get(criterion))
				testChrom.addTest(test);

		TestSuiteGenerator.writeJUnitTests(testChrom.getTests());

		for (Criterion criterion : supportedCriteria) {
			TestSuiteFitnessFunction fitness = getFitness(criterion, testChrom);
			combinedCoverages.put(criterion, testChrom.getCoverage());

			if (fitness instanceof DefUseCoverageSuiteFitness) {
				defuseCoverage.put(Criterion.ANALYZE,
						(DefUseCoverageSuiteFitness) fitness);
			}
		}
	}

	private static TestSuiteFitnessFunction getFitness(Criterion criterion,
			TestSuiteChromosome best) {
		TestSuiteFitnessFunction fitness = TestSuiteGenerator
				.getFitnessFunction(criterion);
		fitness.getFitness(best);

		return fitness;
	}

	/**
	 * <p>writeCSV</p>
	 */
	public static void writeCSV() {

		try {
			ensureCSVHeader();
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile,
					true));

			for (Criterion testCoverage : supportedCriteria) {
				out.write(Properties.TARGET_CLASS + ",");
				out.write(testCoverage.toString() + ",");

				for (Criterion currentCoverage : supportedCriteria)
					out.write(formatCoverage(coverages.get(testCoverage).get(
							currentCoverage))
							+ ",");

				DefUseCoverageSuiteFitness duCoverage = defuseCoverage
						.get(testCoverage);
				
				for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType
						.values()) {
					out.write(duCoverage.coveredGoals.get(type) + ",");
					out.write(DefUseCoverageSuiteFitness.totalGoals.get(type) + ",");
				}
				
				double combined = combinedCoverages.get(testCoverage);
				out.write(formatCoverage(combined) + ",");
				out.write(formatCoverage(combined
						- coverages.get(testCoverage).get(testCoverage))
						+ ",");

				duCoverage = defuseCoverage.get(Criterion.ANALYZE);
				for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType
						.values()) {
					out.write(duCoverage.coveredGoals.get(type) + ",");
				}

				if (Properties.STRATEGY == Strategy.EVOSUITE)
					out.write("suite,");
				else
					out.write("tests,");
				StatisticEntry statistic = statistics.get(testCoverage);
				out.write(statistic.stoppingCondition + ",");
				out.write(statistic.globalTimeStoppingCondition + ",");
				out.write(statistic.timedOut + ",");
				out.write(statistic.getCSVData());
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
			logger.info("Exception while writing CSV data: " + e);
		}
	}

	private static void setCoverage(Criterion criterion, double coverage) {

		if (coverages.get(Properties.CRITERION) == null)
			coverages.put(Properties.CRITERION,
					new HashMap<Criterion, Double>());

		coverages.get(Properties.CRITERION).put(criterion, coverage);
	}

	private static String formatCoverage(double coverage) {
		// TODO put in some utils class or something
		return String.format("%.2f", 100.0 * coverage).replaceAll(",", ".")
				+ "%";

	}

	private static void ensureCSVHeader() throws IOException {

		File output = new File(outputFile);
		if (!output.exists()) {
			if (!output.createNewFile())
				logger.error("unable to create coverage.csv");

			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile,
					true));
			out.write(getCSVHeader() + "\n");

			out.close();
		}

	}

	private static String getCSVHeader() {

		StatisticEntry dummyStat = SearchStatistics.getInstance()
				.getLastStatisticEntry();

		String r = "Class,TestCriterion,DefUse-Coverage,AllDefs-Coverage,Branch-Coverage,Statement-Coverage,";

		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType
				.values()) {
			r += "Covered " + type + " goals,";
			r += "Total " + type + " goals,";
		}
		r += "Combined-Coverage,Combined-Boost,";
		for (DefUsePairType type : DefUseCoverageTestFitness.DefUsePairType
				.values()) {
			r += "Combined " + type + " goals,";
		}
		r += "Mode,Stopping Condition,Global Time,Timed Out,";
		r += dummyStat.getCSVHeader();

		return r;
	}

	private static void setStatisticEntry(StatisticEntry lastStatisticEntry) {
		statistics.put(Properties.CRITERION, lastStatisticEntry);
	}

	private static void rememberTests(List<TestCase> testCases) {
		tests.put(Properties.CRITERION, testCases);
	}

}