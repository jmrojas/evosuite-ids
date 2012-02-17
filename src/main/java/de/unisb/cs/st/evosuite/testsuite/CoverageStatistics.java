package de.unisb.cs.st.evosuite.testsuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.TestSuiteGenerator;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.Properties.Strategy;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUseCoverageSuiteFitness;
import de.unisb.cs.st.evosuite.ga.ChromosomeFactory;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;
import de.unisb.cs.st.evosuite.utils.ReportGenerator;
import de.unisb.cs.st.evosuite.utils.ReportGenerator.StatisticEntry;

public class CoverageStatistics {

	protected static final Logger logger = LoggerFactory
			.getLogger(ReportGenerator.class);

	protected static Map<Criterion, Map<Criterion, Double>> coverages = new HashMap<Criterion, Map<Criterion, Double>>();
	protected static Map<Criterion, DefUseCoverageSuiteFitness> defuseCoverage = new HashMap<Criterion, DefUseCoverageSuiteFitness>();
	protected static Map<Criterion, Double> combinedCoverages = new HashMap<Criterion, Double>();
	protected static Map<Criterion, StatisticEntry> statistics = new HashMap<Criterion, StatisticEntry>();
	protected static Map<Criterion, List<TestCase>> tests = new HashMap<Criterion, List<TestCase>>();

	protected static final File REPORT_DIR = new File(Properties.REPORT_DIR);

	protected static String outputFile = REPORT_DIR.getAbsolutePath()
			+ "/coverage.csv";

	public static Criterion[] supportedCriteria = { Criterion.DEFUSE, Criterion.ALLDEFS,
			Criterion.BRANCH, Criterion.STATEMENT };

	public static void analyzeCoverage(TestSuiteChromosome best) {

		rememberTests(best.getTests());

		System.out.println("* Measured Coverage:");

		for (Criterion criterion : supportedCriteria) {
			TestSuiteFitnessFunction fitness = getFitness(criterion, best);
			System.out.println("\t- "
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
				out.write(duCoverage.coveredParamGoals + ",");
				out.write(DefUseCoverageSuiteFitness.totalParamGoals + ",");
				out.write(duCoverage.coveredIntraGoals + ",");
				out.write(DefUseCoverageSuiteFitness.totalIntraGoals + ",");
				out.write(duCoverage.coveredInterGoals + ",");
				out.write(DefUseCoverageSuiteFitness.totalInterGoals + ",");

				double combined = combinedCoverages.get(testCoverage);
				out.write(formatCoverage(combined) + ",");
				out.write(formatCoverage(combined
						- coverages.get(testCoverage).get(testCoverage))
						+ ",");

				duCoverage = defuseCoverage.get(Criterion.ANALYZE);
				out.write(duCoverage.coveredParamGoals + ",");
				out.write(duCoverage.coveredIntraGoals + ",");
				out.write(duCoverage.coveredInterGoals + ",");

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

		return "Class,TestCriterion,DefUse-Coverage,AllDefs-Coverage,Branch-Coverage,Statement-Coverage,"
				+ "Covered param goals,Total param goals,Covered intra goals,Total intra goals,Covered inter goals,Total inter goals,"
				+ "Combined-Coverage,Combined-Boost,Combined param goals,Combined intra goals,Combined inter goals,"
				+ "Mode,Stopping Condition,Global Time,Timed Out,"
				+ dummyStat.getCSVHeader();
	}

	private static void setStatisticEntry(StatisticEntry lastStatisticEntry) {
		statistics.put(Properties.CRITERION, lastStatisticEntry);
	}

	private static void rememberTests(List<TestCase> testCases) {
		tests.put(Properties.CRITERION, testCases);
	}

}
