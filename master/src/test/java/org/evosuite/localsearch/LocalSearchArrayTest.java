package org.evosuite.localsearch;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.TestGenerationContext;
import org.evosuite.Properties.LocalSearchBudgetType;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.localsearch.DefaultLocalSearchObjective;
import org.evosuite.ga.localsearch.LocalSearchObjective;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.localsearch.BranchCoverageMap;
import org.evosuite.testcase.statements.ArrayStatement;
import org.evosuite.testcase.statements.AssignmentStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.numeric.IntPrimitiveStatement;
import org.evosuite.testcase.variable.ArrayIndex;
import org.evosuite.testcase.variable.ArrayReference;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.localsearch.TestSuiteLocalSearch;
import org.evosuite.utils.GenericClass;
import org.evosuite.utils.GenericConstructor;
import org.evosuite.utils.GenericMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.localsearch.ArrayLengthExample;
import com.examples.with.different.packagename.localsearch.BasicArrayExample;

public class LocalSearchArrayTest extends SystemTest {

	private final double oldPrimitivePool = Properties.PRIMITIVE_POOL;
	private final double oldDseProbability = Properties.DSE_PROBABILITY;
	private final boolean oldLSReferences = Properties.LOCAL_SEARCH_REFERENCES; 
	private final boolean oldLSArrays = Properties.LOCAL_SEARCH_ARRAYS; 
	private final Properties.LocalSearchBudgetType oldLSBudgetType = Properties.LOCAL_SEARCH_BUDGET_TYPE;
	private final long oldLSBudget = Properties.LOCAL_SEARCH_BUDGET;
	
	@Before
    public void init(){
        Properties.DSE_PROBABILITY = 0.0;
        Properties.PRIMITIVE_POOL = 0.0;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.TESTS;
		Properties.LOCAL_SEARCH_BUDGET = 1000;
		Properties.LOCAL_SEARCH_REFERENCES = false;
		Properties.LOCAL_SEARCH_ARRAYS = true;
    }
	
	@After
	public void restoreProperties() {
		Properties.DSE_PROBABILITY = oldDseProbability;
		Properties.PRIMITIVE_POOL = oldPrimitivePool;
		Properties.LOCAL_SEARCH_BUDGET_TYPE = oldLSBudgetType;
		Properties.LOCAL_SEARCH_BUDGET = oldLSBudget;
		Properties.LOCAL_SEARCH_REFERENCES = oldLSReferences;
		Properties.LOCAL_SEARCH_ARRAYS = oldLSArrays;
	}
	
	private TestCase getArrayTest(int length) throws NoSuchMethodException, SecurityException, ConstructionFailedException, ClassNotFoundException {
		Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
		GenericClass clazz = new GenericClass(sut);
		
		DefaultTestCase test = new DefaultTestCase();
		GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);

		TestFactory testFactory = TestFactory.getInstance();
		VariableReference callee = testFactory.addConstructor(test, gc, 0, 0);
		VariableReference arrayVar = test.addStatement(new ArrayStatement(test, int[].class, length));

		for(int i = 0; i < length; i++) {
			// Add value
			VariableReference intVar = test.addStatement(new IntPrimitiveStatement(test, 0));
			test.addStatement(new AssignmentStatement(test, new ArrayIndex(test, (ArrayReference) arrayVar, i), intVar));

		}
		
		Method m = clazz.getRawClass().getMethod("testMe", new Class<?>[] { int[].class });
		GenericMethod method = new GenericMethod(m, sut);
		MethodStatement ms = new MethodStatement(test, method, callee, Arrays.asList(new VariableReference[] {arrayVar}));
		test.addStatement(ms);

		return test;
	}
	
	private void runArrayExample(int length, double expectedFitness) throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		TestCase test = getArrayTest(length);
		System.out.println("Test: "+test.toCode());

		TestSuiteChromosome suite = new TestSuiteChromosome();
		BranchCoverageSuiteFitness fitness = new BranchCoverageSuiteFitness();

		BranchCoverageMap.getInstance().searchStarted(null);
		assertEquals(4.0, fitness.getFitness(suite), 0.1F);
		suite.addTest(test);
		//assertEquals(1.0, fitness.getFitness(suite), 0.1F);
		
		TestSuiteLocalSearch localSearch = TestSuiteLocalSearch.getLocalSearch();
		LocalSearchObjective<TestSuiteChromosome> localObjective = new DefaultLocalSearchObjective<TestSuiteChromosome>(fitness);
		localSearch.doSearch(suite, localObjective);
		System.out.println("Fitness: "+fitness.getFitness(suite));
		System.out.println("Test suite: "+suite);
		assertEquals(expectedFitness, fitness.getFitness(suite), 0.1F);
		BranchCoverageMap.getInstance().searchFinished(null);
	}
	
	@Test
	public void testEmptyArrayLengthLocalSearch() throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		Properties.TARGET_CLASS = ArrayLengthExample.class.getCanonicalName();
		runArrayExample(0, 0.0);
	}

	@Test
	public void testArrayLengthLocalSearch() throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		Properties.TARGET_CLASS = ArrayLengthExample.class.getCanonicalName();
		runArrayExample(2, 0.0);
	}

	@Test
	public void testLongArrayLengthLocalSearch() throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		Properties.TARGET_CLASS = ArrayLengthExample.class.getCanonicalName();
		runArrayExample(10, 0.0);
	}
	
	@Test
	public void testBasicArrayLocalSearch() throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		Properties.TARGET_CLASS = BasicArrayExample.class.getCanonicalName();
		runArrayExample(4, 0.0);
	}

	@Test
	public void testBasicArrayLocalSearchAndLength() throws ClassNotFoundException, ConstructionFailedException, NoSuchMethodException, SecurityException {
		Properties.TARGET_CLASS = BasicArrayExample.class.getCanonicalName();
		runArrayExample(0, 1.0); // Requires double execution
	}

}
