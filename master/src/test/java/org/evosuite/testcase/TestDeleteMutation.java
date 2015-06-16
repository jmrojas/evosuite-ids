package org.evosuite.testcase;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.numeric.IntPrimitiveStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.GenericClass;
import org.evosuite.utils.GenericConstructor;
import org.evosuite.utils.GenericMethod;
import org.junit.After;
import org.junit.Test;

import com.examples.with.different.packagename.TrivialInt;
import com.examples.with.different.packagename.coverage.IntExampleWithNoElse;

public class TestDeleteMutation extends SystemTest {
	private double oldPInsert = Properties.P_TEST_INSERT;
	private double oldPDelete = Properties.P_TEST_DELETE;
	private double oldPChange = Properties.P_TEST_CHANGE;
	private double oldPPool   = Properties.PRIMITIVE_POOL;
	
	@After
	public void restoreProperties() {
		Properties.P_TEST_INSERT = oldPInsert;
		Properties.P_TEST_DELETE = oldPDelete;
		Properties.P_TEST_CHANGE = oldPChange;
		Properties.PRIMITIVE_POOL = oldPPool;
	}
	
	private TestCase getIntTest(int x, int y) throws NoSuchMethodException, SecurityException, ConstructionFailedException, ClassNotFoundException {
		Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
		GenericClass clazz = new GenericClass(sut);
		
		DefaultTestCase test = new DefaultTestCase();
		GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);

		TestFactory testFactory = TestFactory.getInstance();
		VariableReference callee = testFactory.addConstructor(test, gc, 0, 0);
		test.addStatement(new IntPrimitiveStatement(test, x));
		VariableReference wrongIntVar = test.addStatement(new IntPrimitiveStatement(test, y));

		Method m = clazz.getRawClass().getMethod("testMe", new Class<?>[] { int.class});
		GenericMethod method = new GenericMethod(m, sut);
		MethodStatement ms = new MethodStatement(test, method, callee, Arrays.asList(new VariableReference[] {wrongIntVar}));
		test.addStatement(ms);

		return test;
	}
	
	private TestCase getTwoIntTest(int x, int y, int x1, int y1) throws NoSuchMethodException, SecurityException, ConstructionFailedException, ClassNotFoundException {
		Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
		GenericClass clazz = new GenericClass(sut);
		
		DefaultTestCase test = new DefaultTestCase();
		GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);

		TestFactory testFactory = TestFactory.getInstance();
		VariableReference callee = testFactory.addConstructor(test, gc, 0, 0);
		test.addStatement(new IntPrimitiveStatement(test, x));
		test.addStatement(new IntPrimitiveStatement(test, y));
		VariableReference wrongIntVar1 = test.addStatement(new IntPrimitiveStatement(test, x1));
		VariableReference wrongIntVar2 = test.addStatement(new IntPrimitiveStatement(test, y1));

		Method m = clazz.getRawClass().getMethod("testMe", new Class<?>[] { int.class, int.class});
		GenericMethod method = new GenericMethod(m, sut);
		MethodStatement ms = new MethodStatement(test, method, callee, Arrays.asList(new VariableReference[] {wrongIntVar1, wrongIntVar2}));
		test.addStatement(ms);

		return test;
	}
	
	@Test
	public void testSimpleInt() throws NoSuchMethodException, SecurityException, ClassNotFoundException, ConstructionFailedException {
		Properties.TARGET_CLASS = TrivialInt.class.getCanonicalName();
		TestChromosome test1 = new TestChromosome();
		test1.setTestCase(getIntTest(2938, -1000000));
		TestChromosome test2 = (TestChromosome) test1.clone();
		
		TestSuiteChromosome suite = new TestSuiteChromosome();
		BranchCoverageSuiteFitness fitness = new BranchCoverageSuiteFitness();

		assertEquals(4.0, fitness.getFitness(suite), 0.0F);
		suite.addTest(test1);
		assertEquals(1.0, fitness.getFitness(suite), 0.0F);
		suite.addTest(test2);
		assertEquals(1.0, fitness.getFitness(suite), 0.01F); // 0.99...
		
		Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
		Method m = sut.getMethod("testMe", new Class<?>[] { int.class});
		GenericMethod method = new GenericMethod(m, sut);

		TestCluster.getInstance().addTestCall(method);
		
		Properties.P_TEST_CHANGE  = 0.0;
		Properties.P_TEST_DELETE  = 1.0;
		Properties.P_TEST_INSERT  = 0.0;
		
		double oldFitness = suite.getFitness();
		int notChanged = 0;
		for(int i = 0; i < 100; i++) {
			TestChromosome testNew = (TestChromosome) test1.clone();
			testNew.mutate();
			if(testNew.isChanged()) {
				suite.deleteTest(test1);
				suite.addTest(testNew);
				System.out.println(testNew.getTestCase().toCode());
				double newFitness = fitness.getFitness(suite);
				if(newFitness < oldFitness) {
					System.out.println("Improved: "+newFitness);
					test1 = testNew;
					oldFitness = newFitness;
					System.out.println(""+i+":"+((IntPrimitiveStatement)test1.getTestCase().getStatement(1)).getValue());
					if(newFitness == 0.0) {
						System.out.println("Iterations: "+i);
						System.out.println("Not changed: "+notChanged);
						break;
					}
				} else {
					System.out.println("Not improved: "+newFitness);
					suite.deleteTest(testNew);
					suite.addTest(test1);
					fitness.getFitness(suite);
				}
			} else {
				notChanged++;
			}
		}
		
		System.out.println("Fitness: "+fitness.getFitness(suite));
		System.out.println("Test suite: "+suite);
		assertEquals(0.0, fitness.getFitness(suite), 0.1F);
	}
	
	@Test
	public void testTwoInts() throws NoSuchMethodException, SecurityException, ClassNotFoundException, ConstructionFailedException {
		Properties.TARGET_CLASS = IntExampleWithNoElse.class.getCanonicalName();
		TestChromosome test1 = new TestChromosome();
		test1.setTestCase(getTwoIntTest(1, 22, 22, 22));
		TestChromosome test2 = new TestChromosome();
		test2.setTestCase(getTwoIntTest(-23423423, 234234234, -23423423, 234234234));
		TestChromosome test3 = new TestChromosome();
		test3.setTestCase(getTwoIntTest(0, 0, 0, 0));
		
		TestSuiteChromosome suite = new TestSuiteChromosome();
		BranchCoverageSuiteFitness fitness = new BranchCoverageSuiteFitness();

		assertEquals(6.0, fitness.getFitness(suite), 0.0F);
		suite.addTest(test1);
		assertEquals(2.0, fitness.getFitness(suite), 0.0F);
		suite.addTest(test2);
		suite.addTest(test3);
		
		Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
		Method m = sut.getMethod("testMe", new Class<?>[] { int.class, int.class });
		GenericMethod method = new GenericMethod(m, sut);

		TestCluster.getInstance().addTestCall(method);

		Properties.P_TEST_CHANGE  = 0.0;
		Properties.P_TEST_DELETE  = 1.0;
		Properties.P_TEST_INSERT  = 0.0;

		double oldFitness = fitness.getFitness(suite);
		int notChanged = 0;
		System.out.println("Original: "+test1);
		for(int i = 0; i < 100; i++) {
			TestChromosome testNew = (TestChromosome) test1.clone();
			testNew.mutate();
			if(testNew.isChanged()) {
				System.out.println("Trying: "+testNew);
				suite.deleteTest(test1);
				suite.addTest(testNew);
				double newFitness = fitness.getFitness(suite);
				if(newFitness < oldFitness) {
					test1 = testNew;
					oldFitness = newFitness;
					if(newFitness == 0.0) {
						System.out.println("Iterations: "+i);
						System.out.println("Not changed: "+notChanged);
						break;
					}
				} else {
					suite.deleteTest(testNew);
					suite.addTest(test1);
					fitness.getFitness(suite);
				}
			} else {
				notChanged++;
			}
		}
		
		System.out.println("Fitness: "+fitness.getFitness(suite));
		System.out.println("Test suite: "+suite);
		assertEquals(0.0, fitness.getFitness(suite), 0.1F);
	}
}
