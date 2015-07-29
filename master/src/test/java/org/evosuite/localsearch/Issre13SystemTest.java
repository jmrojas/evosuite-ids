package org.evosuite.localsearch;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.Properties.Criterion;
import org.evosuite.Properties.LocalSearchBudgetType;
import org.evosuite.Properties.StoppingCondition;
import org.evosuite.SystemTest;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.localsearch.DefaultLocalSearchObjective;
import org.evosuite.ga.localsearch.LocalSearchObjective;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.localsearch.BranchCoverageMap;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.StringPrimitiveStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.localsearch.TestSuiteLocalSearch;
import org.evosuite.utils.generic.GenericClass;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericMethod;
import org.evosuite.utils.Randomness;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.localsearch.DseBar;
import com.examples.with.different.packagename.localsearch.DseFoo;

/**
 * Created by Andrea Arcuri on 19/03/15.
 */
public class Issre13SystemTest extends SystemTest {

	private static final double DEFAULT_LS_PROBABILITY = Properties.LOCAL_SEARCH_PROBABILITY;
	private static final int DEFAULT_LS_RATE = Properties.LOCAL_SEARCH_RATE;
	private static final LocalSearchBudgetType DEFAULT_LS_BUDGET_TYPE = Properties.LOCAL_SEARCH_BUDGET_TYPE;
	private static final long DEFAULT_LS_BUDGET = Properties.LOCAL_SEARCH_BUDGET;
	private static final long DEFAULT_SEARCH_BUDGET = Properties.SEARCH_BUDGET;
	private static final int DEFAULT_CONCOLIC_TIMEOUT = Properties.CONCOLIC_TIMEOUT;

	@Before
	public void init() {
		Properties.LOCAL_SEARCH_PROBABILITY = 1.0;
		Properties.LOCAL_SEARCH_RATE = 1;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = Properties.LocalSearchBudgetType.TESTS;
		Properties.LOCAL_SEARCH_BUDGET = 100;
		Properties.SEARCH_BUDGET = 50000;
	}

	@After
	public void restoreProperties() {
		Properties.LOCAL_SEARCH_PROBABILITY = DEFAULT_LS_PROBABILITY;
		Properties.LOCAL_SEARCH_RATE = DEFAULT_LS_RATE;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = DEFAULT_LS_BUDGET_TYPE;
		Properties.LOCAL_SEARCH_BUDGET = DEFAULT_LS_BUDGET;
		Properties.SEARCH_BUDGET = DEFAULT_SEARCH_BUDGET;
		Properties.CONCOLIC_TIMEOUT = DEFAULT_CONCOLIC_TIMEOUT;
	}

//	@Test
	public void testLocalSearch() {

		// it should be trivial for LS

		EvoSuite evosuite = new EvoSuite();
		String targetClass = DseBar.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		Properties.DSE_PROBABILITY = 0.0; // force using only LS, no DSE

		String[] command = new String[] { "-generateSuite", "-class",
				targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(),
				0.001);
	}
	
	@Test
	public void testOnSpecificTest() throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		Properties.TARGET_CLASS = DseBar.class.getCanonicalName();
		Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
		Class<?> fooClass = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(DseFoo.class.getCanonicalName());
		GenericClass clazz = new GenericClass(sut);

		DefaultTestCase test = new DefaultTestCase();

		// String string0 = "baz5";
		VariableReference stringVar = test.addStatement(new StringPrimitiveStatement(test, "baz5"));

		// DseFoo dseFoo0 = new DseFoo();
		GenericConstructor fooConstructor = new GenericConstructor(fooClass.getConstructors()[0], fooClass);
		ConstructorStatement fooConstructorStatement = new ConstructorStatement(test, fooConstructor, Arrays.asList(new VariableReference[] {}));
		VariableReference fooVar = test.addStatement(fooConstructorStatement);

		Method fooIncMethod = fooClass.getMethod("inc", new Class<?>[] { });
		GenericMethod incMethod = new GenericMethod(fooIncMethod, fooClass);
		test.addStatement(new MethodStatement(test, incMethod, fooVar, Arrays.asList(new VariableReference[] {})));
		test.addStatement(new MethodStatement(test, incMethod, fooVar, Arrays.asList(new VariableReference[] {})));
		test.addStatement(new MethodStatement(test, incMethod, fooVar, Arrays.asList(new VariableReference[] {})));
		test.addStatement(new MethodStatement(test, incMethod, fooVar, Arrays.asList(new VariableReference[] {})));
		test.addStatement(new MethodStatement(test, incMethod, fooVar, Arrays.asList(new VariableReference[] {})));

		// DseBar dseBar0 = new DseBar(string0);
		GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);
		ConstructorStatement constructorStatement = new ConstructorStatement(test, gc, Arrays.asList(new VariableReference[] {stringVar}));
		VariableReference callee = test.addStatement(constructorStatement);

		// dseBar0.coverMe(dseFoo0);
		Method m = clazz.getRawClass().getMethod("coverMe", new Class<?>[] { fooClass});
		GenericMethod method = new GenericMethod(m, sut);
		MethodStatement ms = new MethodStatement(test, method, callee, Arrays.asList(new VariableReference[] {fooVar}));
		test.addStatement(ms);
		System.out.println(test);
		
		TestSuiteChromosome suite = new TestSuiteChromosome();
		BranchCoverageSuiteFitness fitness = new BranchCoverageSuiteFitness();

		BranchCoverageMap.getInstance().searchStarted(null);
		assertEquals(4.0, fitness.getFitness(suite), 0.1F);
		suite.addTest(test);
		assertEquals(1.0, fitness.getFitness(suite), 0.1F);

		System.out.println("Test suite: "+suite);
		
		Properties.CONCOLIC_TIMEOUT = Integer.MAX_VALUE;
		
		TestSuiteLocalSearch localSearch = TestSuiteLocalSearch.getLocalSearch();
		LocalSearchObjective<TestSuiteChromosome> localObjective = new DefaultLocalSearchObjective<TestSuiteChromosome>();
		localObjective.addFitnessFunction(fitness);
		localSearch.doSearch(suite, localObjective);
		System.out.println("Fitness: "+fitness.getFitness(suite));
		System.out.println("Test suite: "+suite);
		assertEquals(0.0, fitness.getFitness(suite), 0.1F);
		BranchCoverageMap.getInstance().searchFinished(null);
	}

	@Test
	public void testDSE() {

		// should it be trivial for DSE ?

		EvoSuite evosuite = new EvoSuite();
		String targetClass = DseBar.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		Properties.MINIMIZE = false;
		Properties.CONCOLIC_TIMEOUT = Integer.MAX_VALUE;
		Properties.DSE_PROBABILITY = 1.0; // force using only DSE, no LS
		Properties.TEST_ARCHIVE = true;
		// Properties.DSE_SOLVER = SolverType.Z3_STR_SOLVER;
		// Properties.Z3_STR_PATH = "/home/galeotti/Z3-str/str";

		String[] command = new String[] { "-generateSuite", "-class",
				targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(),
				0.001);
	}

	@Test
	public void testDSEMultiObjective() {

		Randomness.setSeed(1545420);

		// should it be trivial for DSE ?
		Properties.SEARCH_BUDGET = 20;
		Properties.TIMEOUT = Integer.MAX_VALUE;
		Properties.CONCOLIC_TIMEOUT = Integer.MAX_VALUE;
		Properties.LOCAL_SEARCH_RATE = 1;
		Properties.DSE_PROBABILITY = 1.0;
//		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.TIME;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.TESTS;
		Properties.LOCAL_SEARCH_BUDGET = 5;
		Properties.CRITERION = new Criterion[] { Criterion.LINE,
				Criterion.BRANCH, Criterion.EXCEPTION, Criterion.WEAKMUTATION,
				Criterion.OUTPUT, Criterion.METHOD };
		Properties.STOPPING_CONDITION = StoppingCondition.MAXTIME;

		EvoSuite evosuite = new EvoSuite();
		String targetClass = DseBar.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		Properties.DSE_PROBABILITY = 1.0; // force using only DSE, no LS

		// Properties.DSE_SOLVER = SolverType.Z3_STR_SOLVER;
		// Properties.Z3_STR_PATH = "/home/galeotti/Z3-str/str";

		String[] command = new String[] { "-generateSuite", "-class",
				targetClass };

		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(),
				0.001);
	}

}
