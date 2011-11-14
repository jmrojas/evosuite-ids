package de.unisb.cs.st.evosuite.testsuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.Properties.Strategy;
import de.unisb.cs.st.evosuite.utils.ReportGenerator;

public class CoverageStatistics {

	protected static final Logger logger = LoggerFactory
			.getLogger(ReportGenerator.class);

	protected static Map<Criterion, Map<Criterion, Double>> coverages = new HashMap<Criterion, Map<Criterion, Double>>();

	protected static final File REPORT_DIR = new File(Properties.REPORT_DIR);
	
	protected static String outputFile = REPORT_DIR.getAbsolutePath() + "/coverage.csv";
	

	public static void setCoverage(Criterion criterion, double coverage) {

		if (coverages.get(Properties.CRITERION) == null)
			coverages.put(Properties.CRITERION,
					new HashMap<Criterion, Double>());

		coverages.get(Properties.CRITERION).put(criterion, coverage);
	}

	public static void writeCSV() {

		try {
			
			ensureCSVHeader();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile,
					true));
			
			for(Criterion testCoverage : coverages.keySet()) {
				out.write(Properties.TARGET_CLASS);
				out.write(","+testCoverage.toString());
				out.write(","+coverages.get(testCoverage).get(Criterion.DEFUSE));
				out.write(","+coverages.get(testCoverage).get(Criterion.BRANCH));
				out.write(","+coverages.get(testCoverage).get(Criterion.STATEMENT));
				if(Properties.STRATEGY == Strategy.EVOSUITE)
					out.write(",suite");
				else
					out.write(",tests");
				
				out.write("\n");
			}
			
			
			out.close();
		} catch (IOException e) {
			logger.info("Exception while writing CSV data: " + e);
		}
	}

	private static void ensureCSVHeader() throws IOException{
		
		File output = new File(outputFile);
		if(!output.exists()) {
			if(!output.createNewFile())
				logger.error("unable to create coverage.csv");
			
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile,
					true));
			out.write(getCSVHeader()+"\n");
			
			out.close();
		}
		
	}

	private static String getCSVHeader() {
		
		return "Class,TestCriterion,DefUse-Coverage,Branch-Coverage,Statement-Coverage,Mode";
	}
	
}