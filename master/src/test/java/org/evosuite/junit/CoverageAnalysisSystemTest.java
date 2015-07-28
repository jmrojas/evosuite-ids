package org.evosuite.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.Properties.StatisticsBackend;
import org.evosuite.continuous.persistency.CsvJUnitData;
import org.evosuite.SystemTest;
import org.evosuite.statistics.OutputVariable;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.statistics.SearchStatistics;
import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.Calculator;
import com.examples.with.different.packagename.CalculatorTest;

import au.com.bytecode.opencsv.CSVReader;

public class CoverageAnalysisSystemTest extends SystemTest {

	@Test
	public void testOneClassOneCriterion() {

		EvoSuite evosuite = new EvoSuite();

        String targetClass = Calculator.class.getCanonicalName();
        String testClass = CalculatorTest.class.getCanonicalName();
        Properties.TARGET_CLASS = targetClass;

        Properties.CRITERION = new Properties.Criterion[] {
        	Properties.Criterion.BRANCH
        };

        String[] command = new String[] {
            "-class", targetClass,
            "-junit", testClass,
            "-Djunit_prefix=" + testClass,
            "-measureCoverage"
        };

        SearchStatistics statistics = (SearchStatistics) evosuite.parseCommandLine(command);
        Assert.assertNotNull(statistics);

        Map<String, OutputVariable<?>> outputVariables = statistics.getOutputVariables();

        assertEquals(0.80, (Double) outputVariables.get("BranchCoverage").getValue(), 0.0);
        assertEquals(0.80, (Double) outputVariables.get("Coverage").getValue(), 0.0);
        assertEquals(4, (Integer) outputVariables.get("Tests_Executed").getValue(), 0);
        assertEquals(4, (Integer) outputVariables.get("Covered_Goals").getValue(), 0);
        assertEquals(5, (Integer) outputVariables.get("Total_Goals").getValue(), 0);
        assertEquals("10111", outputVariables.get("BranchCoverageBitString").getValue());
	}

	@Test
	public void testOneClassMoreThanOneCriterion() throws IOException {

		EvoSuite evosuite = new EvoSuite();

        String targetClass = Calculator.class.getCanonicalName();
        String testClass = CalculatorTest.class.getCanonicalName();
        Properties.TARGET_CLASS = targetClass;

        Properties.CRITERION = new Properties.Criterion[] {
        	Properties.Criterion.BRANCH,
        	Properties.Criterion.LINE
        };

        Properties.OUTPUT_VARIABLES = "TARGET_CLASS,criterion," +
        		RuntimeVariable.Coverage.name() + "," + RuntimeVariable.Covered_Goals + "," + RuntimeVariable.Total_Goals + "," +
        		RuntimeVariable.BranchCoverage + "," + RuntimeVariable.BranchCoverageBitString + "," +
        		RuntimeVariable.LineCoverage + "," + RuntimeVariable.LineCoverageBitString;
        Properties.STATISTICS_BACKEND = StatisticsBackend.CSV;

        String[] command = new String[] {
            "-class", targetClass,
            "-junit", testClass,
            "-Djunit_prefix=" + testClass,
            "-measureCoverage"
        };

        SearchStatistics statistics = (SearchStatistics) evosuite.parseCommandLine(command);
        Assert.assertNotNull(statistics);

        String statistics_file = System.getProperty("user.dir") + File.separator + Properties.REPORT_DIR + File.separator + "statistics.csv";
        System.out.println(statistics_file);

        CSVReader reader = new CSVReader(new FileReader(statistics_file));
        List<String[]> rows = reader.readAll();
        assertTrue(rows.size() == 2);
        reader.close();

        assertTrue(CsvJUnitData.getValue(rows, "TARGET_CLASS").equals("com.examples.with.different.packagename.Calculator"));
        assertTrue(CsvJUnitData.getValue(rows, "criterion").equals(Properties.Criterion.BRANCH.toString() + ";" + Properties.Criterion.LINE.toString()));

        assertEquals(Double.valueOf(CsvJUnitData.getValue(rows, "Coverage")), 0.88, 0.01);
        assertEquals(Integer.valueOf(CsvJUnitData.getValue(rows, "Covered_Goals")), 8, 0);
        assertEquals(Integer.valueOf(CsvJUnitData.getValue(rows, "Total_Goals")), 9, 0);

        assertEquals(Double.valueOf(CsvJUnitData.getValue(rows, "BranchCoverage")), 0.8, 0.0);
        assertEquals(Double.valueOf(CsvJUnitData.getValue(rows, "LineCoverage")), 1.0, 0.0);

        assertTrue(CsvJUnitData.getValue(rows, "BranchCoverageBitString").equals("10111"));
        assertTrue(CsvJUnitData.getValue(rows, "LineCoverageBitString").equals("1111"));
	}

	@Test
	public void testMoreThanOneClassOneCriterion() {
		fail("Implementation missing...");
	}

	@Test
	public void testMoreThanOneClassMoreThanOneCriterion() {
		fail("Implementation missing...");
	}
}
