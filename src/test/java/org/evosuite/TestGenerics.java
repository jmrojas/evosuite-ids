/**
 * 
 */
package org.evosuite;

import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.generic.GenericArray;
import com.examples.with.different.packagename.generic.GenericArrayWithGenericType;
import com.examples.with.different.packagename.generic.GenericArrayWithGenericTypeVariable;
import com.examples.with.different.packagename.generic.GenericConstructorParameterOnRawList;
import com.examples.with.different.packagename.generic.GenericGenericParameter;
import com.examples.with.different.packagename.generic.GenericMemberclass;
import com.examples.with.different.packagename.generic.GenericMethod;
import com.examples.with.different.packagename.generic.GenericMethodWithBounds;
import com.examples.with.different.packagename.generic.GenericOnlyInMemberclass;
import com.examples.with.different.packagename.generic.GenericParameterExtendingGenericBounds;
import com.examples.with.different.packagename.generic.GenericParameterWithBound;
import com.examples.with.different.packagename.generic.GenericParameterWithGenericBound;
import com.examples.with.different.packagename.generic.GenericParameters1;
import com.examples.with.different.packagename.generic.GenericParameters2;
import com.examples.with.different.packagename.generic.GenericParameters3;
import com.examples.with.different.packagename.generic.GenericParameters4;
import com.examples.with.different.packagename.generic.GenericParameters5;
import com.examples.with.different.packagename.generic.GenericParameters6;
import com.examples.with.different.packagename.generic.GenericParameters7;
import com.examples.with.different.packagename.generic.GenericParameters8;
import com.examples.with.different.packagename.generic.GenericSUT;
import com.examples.with.different.packagename.generic.GenericSUTString;
import com.examples.with.different.packagename.generic.GenericSUTTwoParameters;
import com.examples.with.different.packagename.generic.GenericStaticMemberclass;
import com.examples.with.different.packagename.generic.GenericSuperclassOmittingTypeParameters;
import com.examples.with.different.packagename.generic.GenericTwoDimensionalArray;
import com.examples.with.different.packagename.generic.GenericWildcardParameter;

/**
 * @author Gordon Fraser
 * 
 */
public class TestGenerics extends SystemTest {
	@Test
	public void testGenericList() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters1.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 80000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 5, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericStringListLength() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters2.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericStringMap() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters3.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericListsDifferentTypes() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters4.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 80000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 5, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericWildcardList() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters5.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 80000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericWildcardStringList() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters6.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 80000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericSUT() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericSUT.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericSUTTwoParameters() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericSUTTwoParameters.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericSUTString() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericSUTString.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericRawTypes() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters7.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 4, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericRawParameterTypes() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameters8.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericMemberclass() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericMemberclass.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericStaticMemberclass() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericStaticMemberclass.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericOnlyInMemberclass() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericOnlyInMemberclass.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericArray() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericArray.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericTwoDimensionalArray() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericTwoDimensionalArray.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericArrayWithGenericType() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericArrayWithGenericType.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		String testSuite = best.toString();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertFalse(testSuite.contains("? listArray"));
		// Assert.assertFalse(testSuite.contains("List<?>"));
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericArrayWithGenericTypeVariable() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericArrayWithGenericTypeVariable.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		String testSuite = best.toString();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertFalse(testSuite.contains("? listArray"));
		Assert.assertFalse(testSuite.contains("List<?>"));
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericGenericParameter() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericGenericParameter.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 80000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericParameterWithBounds() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameterWithBound.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 20000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericParameterWithGenericBound() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameterWithGenericBound.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericWildcardParameter() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericWildcardParameter.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);
		// String testSuite = best.toString();
		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		// Is this valid or not:
		// Assert.assertFalse(testSuite.contains("List<?>"));
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericSuperclassOmittingTypeParameter() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericSuperclassOmittingTypeParameters.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericMethod() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericMethod.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}
	
	@Test
	public void testGenericMethodWithBounds() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericMethodWithBounds.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericConstructorParameterOnRawList() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericConstructorParameterOnRawList.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testGenericTypeWithGenericParameter() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = GenericParameterExtendingGenericBounds.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		Properties.SEARCH_BUDGET = 50000;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		// int goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals().size();
		// Assert.assertEquals("Wrong number of goals: ", 3, goals);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

}
