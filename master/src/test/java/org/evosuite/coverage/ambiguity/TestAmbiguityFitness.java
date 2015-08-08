package org.evosuite.coverage.ambiguity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.Properties.TestFactory;
import org.evosuite.SystemTest;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.result.TestGenerationResult;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.Compositional;
import com.examples.with.different.packagename.coverage.IndirectlyCoverableBranches;

@SuppressWarnings("unchecked")
public class TestAmbiguityFitness extends SystemTest {

	private static String MATRIX_CONTENT =
			"1 0 0 1 +\n" +
			"0 1 1 0 -\n" +
			"0 0 1 0 +\n";

	private void writeMatrix(String MATRIX_CONTENT) {
		String path = Properties.REPORT_DIR + File.separator;
		final File tmp = new File(path);
		tmp.mkdirs();

		try {
			final File matrix = new File(path + File.separator + Properties.TARGET_CLASS + ".matrix");
			matrix.createNewFile();

			FileWriter fw = new FileWriter(matrix.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(MATRIX_CONTENT);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void prepare() {
		AmbiguityCoverageFactory.reset();
		try {
			FileUtils.deleteDirectory(new File("evosuite-report"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Properties.CRITERION = new Properties.Criterion[] {
			Properties.Criterion.AMBIGUITY
		};

		Properties.TEST_ARCHIVE = false;
		Properties.TEST_FACTORY = TestFactory.RANDOM;
		Properties.MINIMIZE = false;
		Properties.MINIMIZE_VALUES = false;
		Properties.INLINE = false;
		Properties.ASSERTIONS = false;
		Properties.USE_EXISTING_COVERAGE = false;
	}

	@Test
	public void testTransposedMatrix() {
		Properties.TARGET_CLASS = "tmpClass";
		this.writeMatrix(TestAmbiguityFitness.MATRIX_CONTENT);

		AmbiguityCoverageFactory.loadCoverage();
		List<StringBuilder> transposedMatrix = AmbiguityCoverageFactory.getTransposedMatrix();
		assertEquals(4, transposedMatrix.size());
		assertEquals(transposedMatrix.get(0).toString(), "100");
		assertEquals(transposedMatrix.get(1).toString(), "010");
		assertEquals(transposedMatrix.get(2).toString(), "011");
		assertEquals(transposedMatrix.get(3).toString(), "100");
	}

	@Test
	public void testTransposedMatrixWithoutPreviousCoverage() {
		Properties.TARGET_CLASS = "no_class";

		AmbiguityCoverageFactory.loadCoverage();
		List<StringBuilder> transposedMatrix = AmbiguityCoverageFactory.getTransposedMatrix();
		assertEquals(0, transposedMatrix.size());
	}

	@Test
	public void testMatrixAmbiguityScore() {
		Properties.TARGET_CLASS = "tmpClass";
		this.writeMatrix(TestAmbiguityFitness.MATRIX_CONTENT);

		AmbiguityCoverageFactory.loadCoverage();
		List<StringBuilder> matrix = AmbiguityCoverageFactory.getTransposedMatrix();
		assertEquals(4, matrix.size());
		assertEquals(0.25, AmbiguityCoverageFactory.getDefaultAmbiguity(matrix), 0.00);
	}

	@Test
	public void testZeroAmbiguityScore() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = Compositional.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] {
			"-class", targetClass,
			"-generateSuite"
		};

		List<List<TestGenerationResult>> result = (List<List<TestGenerationResult>>) evosuite.parseCommandLine(command);
		Assert.assertNotNull(result);

		List<?> goals = AmbiguityCoverageFactory.getGoals();
		assertEquals(11, goals.size());

		GeneticAlgorithm<?> ga = result.get(0).get(0).getGeneticAlgorithm();
		Assert.assertNotNull(ga);
		assertEquals(0.0, ga.getBestIndividual().getFitnessInstanceOf(AmbiguityCoverageSuiteFitness.class), 0.0);
	}

	@Test
	public void testZeroAmbiguityScoreWithPreviousCoverage() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = Compositional.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		String previous_tmp_coverage =
			"1 1 1 1 1 1 1 1 1 1 1 +\n" +
			"1 1 1 1 0 0 0 0 0 0 0 -\n";
		this.writeMatrix(previous_tmp_coverage);
		Properties.USE_EXISTING_COVERAGE = true;

		String[] command = new String[] {
			"-class", targetClass,
			"-generateSuite"
		};

		List<List<TestGenerationResult>> result = (List<List<TestGenerationResult>>) evosuite.parseCommandLine(command);
		Assert.assertNotNull(result);

		List<?> goals = AmbiguityCoverageFactory.getGoals();
		assertEquals(11, goals.size());

		GeneticAlgorithm<?> ga = result.get(0).get(0).getGeneticAlgorithm();
		Assert.assertNotNull(ga);
		assertEquals(0.0, ga.getBestIndividual().getFitnessInstanceOf(AmbiguityCoverageSuiteFitness.class), 0.0);
	}

	@Test
	public void testNonZeroAmbiguityScore() {

		EvoSuite evosuite = new EvoSuite();

		String targetClass = IndirectlyCoverableBranches.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] {
			"-class", targetClass,
			"-generateSuite"
		};

		List<List<TestGenerationResult>> result = (List<List<TestGenerationResult>>) evosuite.parseCommandLine(command);
		assertNotNull(result);

		List<?> goals = AmbiguityCoverageFactory.getGoals();
		assertEquals(11, goals.size());

		GeneticAlgorithm<?> ga = result.get(0).get(0).getGeneticAlgorithm();
		assertNotNull(ga);

		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		// goals { 5,6,9,10,11,12,15,16,19,20,22 }
		//
		// minimum ambiguity:
		// {5}, {6}, {9,10,11,12}, {15,16}, {19,20}, {22}
		double ambiguity = 0.0; // {5}
		ambiguity += 0.0; // {6}
		ambiguity += (4.0 / ((double) goals.size())) * (3.0 /2.0); // {9,10,11,12}
		ambiguity += (2.0 / ((double) goals.size())) * (1.0 /2.0); // {15,16}
		ambiguity += (2.0 / ((double) goals.size())) * (1.0 /2.0); // {19,20}
		ambiguity += 0.0; // {22}
		assertEquals(0.7272, ambiguity, 0.0001);
		assertEquals(ambiguity * 1.0 / ((double) goals.size()), best.getFitnessInstanceOf(AmbiguityCoverageSuiteFitness.class), 0.001);
	}
}
