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
package org.evosuite.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.evosuite.Properties;
import org.evosuite.Properties.NoSuchParameterException;
import org.evosuite.contracts.AssertionErrorContract;
import org.evosuite.contracts.EqualsContract;
import org.evosuite.contracts.EqualsHashcodeContract;
import org.evosuite.contracts.EqualsNullContract;
import org.evosuite.contracts.EqualsSymmetricContract;
import org.evosuite.contracts.FailingTestSet;
import org.evosuite.contracts.HashCodeReturnsNormallyContract;
import org.evosuite.contracts.JCrasherExceptionContract;
import org.evosuite.contracts.NullPointerExceptionContract;
import org.evosuite.contracts.ToStringReturnsNormallyContract;
import org.evosuite.contracts.UndeclaredExceptionContract;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.ga.SearchListener;
import org.evosuite.sandbox.PermissionStatistics;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.ExecutionTrace;
import org.evosuite.testcase.ExecutionTracer;
import org.evosuite.testcase.JUnitTestChromosomeFactory;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCaseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.FileTerminal;
import com.panayotis.gnuplot.terminal.GNUPlotTerminal;


/**
 * @author Gordon Fraser
 * 
 */
public abstract class ReportGenerator implements SearchListener, Serializable {

	private static final long serialVersionUID = -920540796220051609L;
	protected static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

	//FIXME: need re-factor, as dependent on Properties
	protected static final File REPORT_DIR = new File(Properties.REPORT_DIR);

	protected static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Statistics about one test generation run
	 * 
	 * @author Gordon Fraser
	 * 
	 */
	public class StatisticEntry implements Serializable {

		private static final long serialVersionUID = 8690481387977534927L;

		/** Run id */
		public int id = 0;

		public String className;

		public int population_size;

		public int chromosome_length;

		/** Total number of branches */
		public int total_branches;

		public int error_branches = 0;

		public int error_branches_covered = 0;

		public int error_branchless_methods = 0;

		public int error_branchless_methods_covered = 0;

		/** Total number of branches */
		public int covered_branches;

		public int total_methods;

		public int branchless_methods;

		public int covered_branchless_methods;

		public int covered_methods;

		public int total_goals;

		public int covered_goals;

		public Set<Integer> coverage = new HashSet<Integer>();

		public double mutationScore = 0.0;

		/** Resulting test cases */
		public List<TestCase> tests = null;

		/** History of best fitness values */
		public List<Double> fitness_history = new ArrayList<Double>();

		/** History of best test suite size */
		public List<Integer> size_history = new ArrayList<Integer>();

		/** History of best test length */
		public List<Integer> length_history = new ArrayList<Integer>();

		/** History of average test length */
		public List<Double> average_length_history = new ArrayList<Double>();

		/** History of best test coverage */
		public List<Double> coverage_history = new ArrayList<Double>();

		/** History of best test length */
		public List<Long> tests_executed = new ArrayList<Long>();

		/** History of best test length */
		public List<Long> statements_executed = new ArrayList<Long>();

		/** History of the time stamps for generations */
		public List<Long> timeStamps = new ArrayList<Long>();

		/** History of best test length */
		public List<Long> fitness_evaluations = new ArrayList<Long>();

		/** Time at which this entry was created */
		public final long creationTime = System.currentTimeMillis();

		/** Number of tests after GA */
		public int size_final = 0;

		/** Total length of tests after GA */
		public int length_final = 0;

		/** Number of tests after minimization */
		public int size_minimized = 0;

		/** Total length of tests after minimization */
		public int length_minimized = 0;

		public Map<TestCase, Map<Integer, Throwable>> results = new HashMap<TestCase, Map<Integer, Throwable>>();

		public long start_time;

		public long end_time;

		public long minimized_time;

		public long testExecutionTime;

		public long goalComputationTime;

		public int result_fitness_evaluations = 0;

		public long result_tests_executed = 0;

		public long result_statements_executed = 0;

		public int age = 0;

		public double fitness = 0.0;

		public long seed = 0;

		public long stoppingCondition;

		public long globalTimeStoppingCondition;

		public boolean timedOut;

		public int paramDUGoalCount;

		public int interDUGoalCount;

		public int intraDUGoalCount;

		public String goalCoverage;

		public int explicitMethodExceptions;

		public int explicitTypeExceptions;

		public int implicitMethodExceptions;

		public int implicitTypeExceptions;

		public Map<String, Set<Class<?>>> exceptions;

		public String getCSVHeader() {
			StringBuilder r = new StringBuilder();
			r.append("Class,Predicates,Total Branches,Covered Branches,Total Methods,Branchless Methods,Covered Methods,Covered Branchless Methods,");
			r.append("Total Goals,Covered Goals,Coverage,Creation Time,Minimization Time,Total Time,Test Execution Time,Goal Computation Time,Result Size,Result Length,"); //17
			r.append("Minimized Size,Minimized Length,");
			// "Bloat Rejections,Fitness Rejections,Fitness Accepts,"
			r.append("Chromosome Length,Population Size,Random Seed,Budget,"); //23

			// TODO since we currently don't want to change the layout of
			// statistics.csv i will leave this commented out for future use and
			// sort of copy this into CoverageStatistics
			// r.append("parameter DU-goals, intra DU-goals, inter DU-goals,");
			// r.append("Stopping Condition,Global Time,Timed Out,");

			r.append("AllPermission,SecurityPermission,UnresolvedPermission,AWTPermission,FilePermission,SerializablePermission,ReflectPermission,RuntimePermission,NetPermission,SocketPermission,SQLPermission,PropertyPermission,LoggingPermission,SSLPermission,AuthPermission,AudioPermission,OtherPermission,Threads,");//41

			r.append("JUnitTests,");
			r.append("Branches,");
			r.append("MutationScore,");
			r.append("Explicit MethodExceptions,");
			r.append("Explicit TypeExceptions,");
			r.append("Implicit MethodExceptions,");
			r.append("Implicit TypeExceptions,");
			r.append("Error Predicates,");
			r.append("Error Branches Covered,");
			r.append("Error Branchless Methods,");
			r.append("Error Branchless Methods Covered,");
			r.append("AssertionContract,");
			r.append("EqualsContract,");
			r.append("EqualsHashcodeContract,");
			r.append("EqualsNullContract,");
			r.append("EqualsSymmetricContract,");
			r.append("HashCodeReturnsNormallyContract,");
			r.append("JCrasherExceptionContract,");
			r.append("NullPointerExceptionContract,");
			r.append("ToStringReturnsNormallyContract,");
			r.append("UndeclaredExceptionContract,");
			r.append("Contract Violations,");
			r.append("Unique Violations,");
			r.append("Data File");
			return r.toString();
		}

		public String getCSVData() {
			StringBuilder r = new StringBuilder();

			r.append(className + ",");

			r.append(total_branches + ",");
			r.append((2 * total_branches) + ",");
			r.append(covered_branches + ",");

			r.append(total_methods + ",");
			r.append(branchless_methods + ",");
			r.append(covered_methods + ",");
			r.append(covered_branchless_methods + ",");

			r.append(total_goals + ",");
			r.append(covered_goals + ",");
			r.append(getCoverageDouble() + ","); // 9

			// r.append(start_time+",");
			// r.append(end_time+",");
			// r.append(minimized_time+",");

			r.append((minimized_time - start_time) + ",");
			r.append((minimized_time - end_time) + ",");
			r.append((end_time - start_time) + ",");

			r.append(testExecutionTime + ",");
			r.append(goalComputationTime + ",");

			r.append(size_final + ",");
			r.append(length_final + ",");
			r.append(size_minimized + ",");
			r.append(length_minimized + ",");

			r.append(chromosome_length + ",");
			r.append(population_size + ",");
			r.append(seed + ","); //21
			r.append(Properties.SEARCH_BUDGET + ","); //22

			// TODO since we currently don't want to change the layout of
			// statistics.csv i will leave this commented out for future use and
			// sort of copy this into CoverageStatistics
			//			r.append(paramDUGoalCount + ",");
			//			r.append(intraDUGoalCount + ",");
			//			r.append(interDUGoalCount + ",");
			// r.append(stoppingCondition + ",");
			// r.append(globalTimeStoppingCondition + ",");
			// r.append(timedOut + ",");

			PermissionStatistics pstats = PermissionStatistics.getInstance();

			// TODO: This is a bug, AllPermission should only be here once, but we need to keep it for the ICSE12 experiments
			r.append(pstats.getNumAllPermission() + ",");
			// TODO remove the line above in the future (awesome comment)
			r.append(pstats.getNumAllPermission() + ",");
			r.append(pstats.getNumSecurityPermission() + ",");
			r.append(pstats.getNumUnresolvedPermission() + ",");
			r.append(pstats.getNumAWTPermission() + ",");
			r.append(pstats.getNumFilePermission() + ",");
			r.append(pstats.getNumSerializablePermission() + ",");
			r.append(pstats.getNumReflectPermission() + ",");
			r.append(pstats.getNumRuntimePermission() + ",");
			r.append(pstats.getNumNetPermission() + ",");
			r.append(pstats.getNumSocketPermission() + ",");
			r.append(pstats.getNumSQLPermission() + ",");
			r.append(pstats.getNumPropertyPermission() + ",");
			r.append(pstats.getNumLoggingPermission() + ",");
			r.append(pstats.getNumSSLPermission() + ",");
			r.append(pstats.getNumAuthPermission() + ",");
			r.append(pstats.getNumAudioPermission() + ",");
			r.append(pstats.getNumOtherPermission() + ",");
			r.append(pstats.getMaxThreads() + ",");
			r.append(JUnitTestChromosomeFactory.getNumTests() + ",");

			r.append(goalCoverage + ",");
			r.append(mutationScore + ",");
			r.append(explicitMethodExceptions + ",");
			r.append(explicitTypeExceptions + ",");
			r.append(implicitMethodExceptions + ",");
			r.append(implicitTypeExceptions + ",");
			r.append(error_branches + ",");
			r.append(error_branches_covered + ",");
			r.append(error_branchless_methods + ",");
			r.append(error_branchless_methods_covered + ",");
			r.append(FailingTestSet.getNumberOfViolations(AssertionErrorContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(EqualsContract.class) + ",");
			r.append(FailingTestSet.getNumberOfViolations(EqualsHashcodeContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(EqualsNullContract.class) + ",");
			r.append(FailingTestSet.getNumberOfViolations(EqualsSymmetricContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(HashCodeReturnsNormallyContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(JCrasherExceptionContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(NullPointerExceptionContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(ToStringReturnsNormallyContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations(UndeclaredExceptionContract.class)
			        + ",");
			r.append(FailingTestSet.getNumberOfViolations() + ",");
			r.append(FailingTestSet.getNumberOfUniqueViolations() + ",");
			r.append(getCSVFilepath());

			return r.toString();
		}

		public String getCSVFilepath() {
			return REPORT_DIR.getAbsolutePath() + File.separator + getCSVFileName();
		}
		
		public String getCSVFileName() {
			return "data" + File.separator + "statistics_" + className + "-"
			        + id + ".csv";
		}

		public String getExceptionFilepath() {
			return REPORT_DIR.getAbsolutePath() + "/data/exceptions_" + className + "-"
			        + id + ".csv";
		}

		public String getCoverage() {
			if (total_goals == 0)
				return "100.00%";
			else
				return String.format("%.2f",
				                     (100.0 * covered_goals / (1.0 * total_goals))).replaceAll(",",
				                                                                               ".")
				        + "%";
		}

		public double getCoverageDouble() {
			if (total_goals == 0)
				return 1.0;
			else
				return covered_goals / (1.0 * total_goals);
		}
	};

	protected List<StatisticEntry> statistics = new ArrayList<StatisticEntry>();

	protected static final HtmlAnalyzer html_analyzer = new HtmlAnalyzer();

	protected String writeIntegerChart(List<Integer> values, String className,
	        String title) {
		File file = new File(REPORT_DIR.getAbsolutePath() + "/img/statistics_" + title
		        + "_" + className + ".png");
		JavaPlot plot = new JavaPlot();
		GNUPlotTerminal terminal = new FileTerminal("png", REPORT_DIR
		        + "/img/statistics_" + title + "_" + className + ".png");
		plot.setTerminal(terminal);

		plot.set("xlabel", "\"Generation\"");
		plot.set("ylabel", "\"" + title + "\"");
		// plot.set("xrange", "[0:100]");
		// plot.set("yrange", "[0:]");

		int[][] data = new int[values.size()][2];
		for (int i = 0; i < values.size(); i++) {
			data[i][0] = i;
			data[i][1] = values.get(i);
		}

		plot.addPlot(data);
		PlotStyle stl = ((AbstractPlot) plot.getPlots().get(0)).getPlotStyle();
		stl.setStyle(Style.LINESPOINTS);
		plot.setKey(JavaPlot.Key.OFF);
		plot.plot();

		return file.getName();
	}

	protected String writeDoubleChart(List<Double> values, String className, String title) {
		File file = new File(REPORT_DIR.getAbsolutePath() + "/img/statistics_" + title
		        + "_" + className + ".png");
		JavaPlot plot = new JavaPlot();
		GNUPlotTerminal terminal = new FileTerminal("png", REPORT_DIR
		        + "/img/statistics_" + title + "_" + className + ".png");
		plot.setTerminal(terminal);

		plot.set("xlabel", "\"Generation\"");
		plot.set("ylabel", "\"" + title + "\"");
		// plot.set("xrange", "[0:100]");
		// plot.set("yrange", "[0:]");

		double[][] data = new double[values.size()][2];
		for (int i = 0; i < values.size(); i++) {
			data[i][0] = i;
			data[i][1] = values.get(i);
		}
		plot.addPlot(data);

		PlotStyle stl = ((AbstractPlot) plot.getPlots().get(0)).getPlotStyle();
		stl.setStyle(Style.LINESPOINTS);
		plot.setKey(JavaPlot.Key.OFF);

		plot.plot();

		return file.getName();
	}

	/**
	 * HTML header
	 */
	protected void writeHTMLHeader(StringBuffer buffer, String title) {
		buffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">\n");
		buffer.append("<html>\n");
		buffer.append("<head>\n");
		buffer.append("<title>\n");
		buffer.append(title);
		buffer.append("\n</title>\n");

		buffer.append("<link href=\"files/prettify.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
		buffer.append("<link href=\"files/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\"/>\n");
		buffer.append("<script type=\"text/javascript\" src=\"files/prettify.js\"></script>\n");
		buffer.append("<script type=\"text/javascript\" src=\"files/jquery.js\"></script>\n");
		buffer.append("<script type=\"text/javascript\" src=\"files/foldButton.js\"></script>\n");
		buffer.append("<script type=\"text/javascript\">\n");
		buffer.append("  $(document).ready(function() {\n");
		//buffer.append("    $('div.tests').foldButton({'closedText':'open TITLE' });\n");
		//buffer.append("    $('div.source').foldButton({'closedText':'open TITLE' });\n");
		//buffer.append("    $('div.statistics').foldButton({'closedText':'open TITLE' });\n");
		buffer.append("    $('H2#tests').foldButton();\n");
		buffer.append("    $('H2#source').foldButton();\n");
		buffer.append("    $('H2#parameters').foldButton();\n");
		buffer.append("  });");
		buffer.append("</script>\n");
		buffer.append("<link href=\"files/foldButton.css\" rel=\"stylesheet\" type=\"text/css\">\n");
		buffer.append("</head>\n");
		buffer.append("<body onload=\"prettyPrint()\">\n");
		buffer.append("<div id=\"wrapper\">\n");
	}

	/**
	 * HTML footer
	 */
	protected void writeHTMLFooter(StringBuffer buffer) {
		buffer.append("</div>\n");
		buffer.append("</body>\n");
		buffer.append("</html>\n");
	}

	protected void writeCSVData(String filename, List<?>... data) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
			int length = Integer.MAX_VALUE;

			out.write("Generation,Fitness,Coverage,Size,Length,AverageLength,Evaluations,Tests,Statements,Time\n");
			for (List<?> d : data) {
				length = Math.min(length, d.size());
			}
			for (int i = 0; i < length; i++) {
				out.write("" + i);
				for (List<?> d : data) {
					out.write("," + d.get(i));
				}
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
			logger.info("Exception while writing CSV data: " + e);
		}
	}

	protected void writeExceptionData(String filename,
	        Map<String, Set<Class<?>>> exceptions) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
			out.write("Method,Exception\n");
			for (String key : exceptions.keySet()) {
				for (Class<?> exception : exceptions.get(key)) {
					out.write(key + "," + exception.getCanonicalName() + "\n");
				}
			}
			out.close();
		} catch (IOException e) {
			logger.info("Exception while writing exception data: " + e);
		}
	}

	private int getNumber(final String className) {
		int num = 0;
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("statistics_" + className)
				        && name.endsWith(".csv"); // && !dir.isDirectory();
			}
		};
		List<String> filenames = new ArrayList<String>();

		File[] files = (new File(REPORT_DIR.getAbsolutePath() + "/data")).listFiles(filter);
		if (files != null) {
			for (File f : files)
				filenames.add(f.getName());
			while (filenames.contains("statistics_" + className + "-" + num + ".csv"))
				num++;
		}

		return num;
	}

	/**
	 * Write a file for a particular run
	 * 
	 * @param run
	 */
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
		if (Properties.PLOT) {
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

	/**
	 * Write some overall stats
	 * 
	 * @param buffer
	 */
	protected void writeParameterTable(StringBuffer buffer, StatisticEntry entry) {
		buffer.append("<h2 id=parameters>EvoSuite Parameters</h2>\n");
		buffer.append("<div class=statistics><ul>\n");
		for (String key : Properties.getParameters()) {
			try {
				buffer.append("<li>" + key + ": " + Properties.getStringValue(key) + "\n"); // TODO
			} catch (NoSuchParameterException e) {

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.append("</ul></div>\n");

	}

	/**
	 * Write some overall stats
	 * 
	 * @param buffer
	 */
	protected void writeResultTable(StringBuffer buffer, StatisticEntry entry) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		//buffer.append("<h2>Statistics</h2>\n");
		buffer.append("<ul>\n");

		buffer.append("<li>");
		buffer.append(entry.result_fitness_evaluations);
		buffer.append(" fitness evaluations, ");
		buffer.append(entry.age);
		buffer.append(" generations, ");
		buffer.append(entry.result_statements_executed);
		buffer.append(" statements, ");
		buffer.append(entry.result_tests_executed);
		buffer.append(" tests.\n");

		long duration_GA = (entry.end_time - entry.start_time) / 1000;
		long duration_MI = (entry.minimized_time - entry.end_time) / 1000;
		long duration_TO = (entry.minimized_time - entry.start_time) / 1000;

		buffer.append("<li>Time: "
		        + String.format("%d:%02d:%02d", duration_TO / 3600,
		                        (duration_TO % 3600) / 60, (duration_TO % 60)));

		buffer.append("(Search: "
		        + String.format("%d:%02d:%02d", duration_GA / 3600,
		                        (duration_GA % 3600) / 60, (duration_GA % 60)) + ", ");
		buffer.append("minimization: "
		        + String.format("%d:%02d:%02d", duration_MI / 3600,
		                        (duration_MI % 3600) / 60, (duration_MI % 60)) + ")\n");

		buffer.append("<li>Coverage: " + entry.covered_branches + "/"
		        + (2 * entry.total_branches) + " branches, ");
		buffer.append(entry.covered_methods + "/" + entry.total_methods + " methods, ");
		buffer.append(entry.covered_goals + "/" + entry.total_goals + " total goals\n");

		// buffer.append("<li>Elite: "+System.getProperty("GA.elite")+"\n");
		// buffer.append("<li>Mutation rate: "+System.getProperty("GA.mutation_rate")+"\n");
		// buffer.append("<li>Crossover: "+System.getProperty("GA.crossover_rate")+"\n");
		// buffer.append("<li>Kin Compensation: "+System.getProperty("GA.kincompensation")+"\n");

		buffer.append("</ul>\n");
	}

	/**
	 * The big table of results
	 * 
	 * @param buffer
	 */
	protected void writeRunTable(StringBuffer buffer) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		for (StatisticEntry entry : statistics) {
			buffer.append("<tr>");
			buffer.append("<td>" + entry.id + "</td>");
			buffer.append("<td>");
			buffer.append(sdf.format(new Date(entry.start_time)));
			buffer.append("</td>");
			long duration_TO = (entry.minimized_time - entry.start_time) / 1000;
			buffer.append("<td>");
			buffer.append(String.format("%d:%02d:%02d", duration_TO / 3600,
			                            (duration_TO % 3600) / 60, (duration_TO % 60)));
			buffer.append("</td>");
			buffer.append("<td>");
			buffer.append(entry.getCoverage());
			buffer.append("</td>");
			buffer.append("<td><a href=\"html/");
			String filename = writeRunPage(entry);
			buffer.append(filename);
			buffer.append("\">");
			buffer.append(entry.className);
			buffer.append("</a></td>");
			buffer.append("<td><a href=\"");
			buffer.append(entry.getCSVFileName());
			buffer.append("\">CSV</a></td>");
			buffer.append("</tr>\n");
		}
		buffer.append("<!-- EVOSUITE INSERTION POINT -->\n");
		buffer.append("</table>");
	}

	public void writeCSV() {
		if (statistics.isEmpty())
			return;

		StatisticEntry entry = statistics.get(statistics.size() - 1);
		logger.info("Writing CSV!");
		try {
			File f = new File(REPORT_DIR + "/statistics.csv");
			BufferedWriter out = new BufferedWriter(new FileWriter(f, true));
			if (f.length() == 0L) {
				out.write(entry.getCSVHeader() + "\n");
			}
			out.write(entry.getCSVData() + "\n");
			out.close();

		} catch (IOException e) {
			logger.warn("Error while writing statistics: " + e.getMessage());
		}

		if (Properties.CRITERION == Properties.Criterion.EXCEPTION) {
			writeExceptionData(entry.getExceptionFilepath(), entry.exceptions);
		}
		writeCSVData(entry.getCSVFilepath(), entry.fitness_history,
		             entry.coverage_history, entry.size_history, entry.length_history,
		             entry.average_length_history, entry.fitness_evaluations,
		             entry.tests_executed, entry.statements_executed, entry.timeStamps);

	}

	protected void copyFile(URL src, File dest) {
		try {
			InputStream in;
			in = src.openStream();
			OutputStream out = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void copyFile(String name) {
		URL systemResource = ClassLoader.getSystemResource("report/" + name);
		logger.debug("Copying from resource: " + systemResource);
		copyFile(systemResource, new File(REPORT_DIR, "files/" + name));
		copyFile(systemResource, new File(REPORT_DIR.getAbsolutePath() + "/html/files/"
		        + name));
	}

	/**
	 * Write an HTML report
	 */
	public void writeReport() {
		if (!Properties.HTML)
			return;

		if (statistics.isEmpty())
			return;

		new File(REPORT_DIR.getAbsolutePath() + "/html/files/").mkdirs();
		new File(REPORT_DIR.getAbsolutePath() + "/data/").mkdirs();
		new File(REPORT_DIR.getAbsolutePath() + "/files/").mkdirs();

		copyFile("prettify.js");
		copyFile("prettify.css");
		copyFile("style.css");
		copyFile("foldButton.js");
		copyFile("foldButton.css");
		copyFile("jquery.js");
		copyFile("detected.png");
		copyFile("not_detected.png");
		copyFile("img01.jpg");
		copyFile("img02.jpg");
		copyFile("img03.jpg");
		copyFile("img04.png");
		File file = new File(REPORT_DIR, "report-generation.html");
		StringBuffer report = new StringBuffer();

		if (file.exists()) {
			List<String> lines = Utils.readFile(file);
			for (String line : lines) {
				if (line.contains("<!-- EVOSUITE INSERTION POINT -->")) {
					break;
				}
				report.append(line);
			}
		} else {

			writeHTMLHeader(report, Properties.PROJECT_PREFIX);
			report.append("<div id=\"header\"><div id=\"logo\">");
			report.append("<h1 class=title>EvoSuite: " + Properties.PROJECT_PREFIX
			        + "</h1>\n");
			report.append("</div></div>");
			try {
				report.append("Run on "
				        + java.net.InetAddress.getLocalHost().getHostName() + "\n");
			} catch (Exception e) {
			}

			report.append("<div id=\"page\"><div id=\"page-bgtop\"><div id=\"page-bgbtm\"><div id=\"content\">");
			report.append("<div id=\"post\">");
			report.append("<h2 class=\"title\">Test generation runs:</h2>\n");
			report.append("<div style=\"clear: both;\">&nbsp;</div><div class=\"entry\">");
			report.append("<table border=1 cellspacing=0 cellpadding=3>");
			report.append("<tr>");
			report.append("<td>Run</td>");
			report.append("<td>Date</td>");
			report.append("<td>Time</td>");
			report.append("<td>Coverage</td>");
			report.append("<td>Class</td>");
			report.append("<td></td>");
			report.append("</tr>\n");
		}
		writeRunTable(report);
		report.append("</div></div></div></div></div></div>");

		writeHTMLFooter(report);

		Utils.writeFile(report.toString(), file);
	}

	public Set<Integer> getCoveredLines(ExecutionTrace trace, String className) {
		return trace.getCoveredLines(className);
	}

	public ExecutionResult executeTest(TestCase test, String className) {
		ExecutionResult result = null;
		try {
			// logger.trace(test.toCode());
			TestCaseExecutor executor = TestCaseExecutor.getInstance();
			result = executor.execute(test);
			// Map<Integer, Throwable> result = executor.run(test);
			StatisticEntry entry = statistics.get(statistics.size() - 1);
			// entry.results.put(test, result);
			entry.results.put(test, result.exposeExceptionMapping());

		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			e.printStackTrace();
			try {
				Thread.sleep(1000);
				result.setTrace(ExecutionTracer.getExecutionTracer().getTrace());
			} catch (Exception e1) {
				e.printStackTrace();
				// TODO: Do some error recovery?
				System.exit(1);
			}
		}
		return result;
	}

	public abstract void minimized(Chromosome result);

	protected void makeDirs() {
		REPORT_DIR.mkdirs();
		(new File(REPORT_DIR.getAbsolutePath() + "/data")).mkdir();
		if (Properties.PLOT)
			(new File(REPORT_DIR.getAbsolutePath() + "/img")).mkdir();
		if (Properties.HTML)
			(new File(REPORT_DIR.getAbsolutePath() + "/html")).mkdir();
	}

	@Override
	public void searchStarted(GeneticAlgorithm algorithm) {
		StatisticEntry entry = new StatisticEntry();
		entry.className = Properties.TARGET_CLASS;
		entry.id = getNumber(entry.className);

		entry.start_time = System.currentTimeMillis();
		entry.population_size = Properties.POPULATION;
		entry.chromosome_length = Properties.CHROMOSOME_LENGTH;
		entry.seed = Randomness.getSeed();
		statistics.add(entry);
	}

	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		StatisticEntry entry = statistics.get(statistics.size() - 1);
		Chromosome best = algorithm.getBestIndividual();
		entry.fitness_history.add(best.getFitness());
		entry.size_history.add(best.size());

		double average = 0.0;
		for (Chromosome individual : algorithm.getPopulation()) {
			average += individual.size();
		}

		entry.average_length_history.add(average / algorithm.getPopulation().size());

		// TODO: Need to get data of average size in here - how? Pass population
		// as parameter?
		entry.age++;
	}

	@Override
	public void fitnessEvaluation(Chromosome result) {
		StatisticEntry entry = statistics.get(statistics.size() - 1);
		entry.result_fitness_evaluations++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.ga.SearchListener#mutation(org.evosuite
	 * .ga.Chromosome)
	 */
	@Override
	public void modification(Chromosome individual) {
		// TODO Auto-generated method stub

	}
}
