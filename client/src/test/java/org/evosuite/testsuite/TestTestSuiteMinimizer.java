package org.evosuite.testsuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.coverage.branch.BranchPool;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.graphs.cfg.CFGMethodAdapter;
import org.evosuite.testcase.ConstructorStatement;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.IntPrimitiveStatement;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.VariableReference;
import org.evosuite.utils.GenericClass;
import org.evosuite.utils.GenericConstructor;
import org.evosuite.utils.GenericMethod;
import org.evosuite.utils.Randomness;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.examples.with.different.packagename.FlagExample1;

@SuppressWarnings("unused")
public class TestTestSuiteMinimizer
{
    @Before
    public void setUp()
    {
        Properties.MINIMIZE_OLD = true;
        Randomness.setSeed(42);

        Properties.TARGET_CLASS = "";
    }

    @Test
    public void minimizeEmptySuite() throws ClassNotFoundException
    {
        CFGMethodAdapter.reset();
        BranchPool.reset();

        DefaultTestCase test = new DefaultTestCase();

        TestSuiteChromosome tsc = new TestSuiteChromosome();
        tsc.addTest(test);
        TestSuiteFitnessFunction ff = new BranchCoverageSuiteFitness();
        double previous_fitness = ff.getFitness(tsc);
        tsc.setFitness(ff, previous_fitness);
        assertEquals(previous_fitness, 0.0, 0.0);

        TestSuiteMinimizer minimizer = new TestSuiteMinimizer(null);
        minimizer.minimize(tsc);
        assertTrue(tsc.getTestChromosomes().size() == 0);

        double fitness = ff.getFitness(tsc);
        assertEquals(previous_fitness, fitness, 0.0);
    }

    @Test
    public void minimizeSuiteOnlyWithVariables()
    {
        CFGMethodAdapter.reset();
        BranchPool.reset();

        DefaultTestCase test = new DefaultTestCase();
        for (int i = 0; i < 10; i++) {
            IntPrimitiveStatement ips = new IntPrimitiveStatement(test, i);
            test.addStatement(ips);
        }

        assertEquals(10, test.size());

        TestSuiteChromosome tsc = new TestSuiteChromosome();
        tsc.addTest(test);
        TestSuiteFitnessFunction ff = new BranchCoverageSuiteFitness();
        double previous_fitness = ff.getFitness(tsc);
        tsc.setFitness(ff, previous_fitness);
        assertEquals(previous_fitness, 0.0, 0.0);

        TestSuiteMinimizer minimizer = new TestSuiteMinimizer(null);
        minimizer.minimize(tsc);
        assertTrue(tsc.getTestChromosomes().size() == 0);

        double fitness = ff.getFitness(tsc);
        assertEquals(previous_fitness, fitness, 0.0);
    }

    @Test
    public void minimizeSuiteHalfCoverage() throws ClassNotFoundException, NoSuchFieldException, SecurityException, ConstructionFailedException, NoSuchMethodException
    {
        Properties.TARGET_CLASS = FlagExample1.class.getCanonicalName();
        Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
        GenericClass clazz = new GenericClass(sut);

        DefaultTestCase test = new DefaultTestCase();
        GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);

        TestFactory testFactory = TestFactory.getInstance();
        testFactory.addConstructor(test, gc, 0, 0);

        List<VariableReference> parameters = new ArrayList<VariableReference>();
        for (int i = 0; i < 10; i++) {
            IntPrimitiveStatement ips = new IntPrimitiveStatement(test, 28234 + i);
            VariableReference vr = test.addStatement(ips, i + 1);
            parameters.add(vr);
        }

        ConstructorStatement ct = new ConstructorStatement(test, gc, parameters);

        Method m = clazz.getRawClass().getMethod("testMe", new Class<?>[] { int.class });
        GenericMethod method = new GenericMethod(m, sut);
        testFactory.addMethod(test, method, 11, 0);

        assertEquals(12, test.size());

        TestSuiteChromosome tsc = new TestSuiteChromosome();
        tsc.addTest(test);
        TestSuiteFitnessFunction ff = new BranchCoverageSuiteFitness();
        double previous_fitness = ff.getFitness(tsc);
        tsc.setFitness(ff, previous_fitness);
        assertEquals(previous_fitness, 2.0, 0.0);

        TestSuiteMinimizer minimizer = new TestSuiteMinimizer(null);
        minimizer.minimize(tsc);
        assertTrue(tsc.getTests().get(0).toCode().equals("FlagExample1 flagExample1_0 = new FlagExample1();\nint int0 = 28234;\nboolean boolean0 = flagExample1_0.testMe(int0);\n"));

        double fitness = ff.getFitness(tsc);
        assertEquals(previous_fitness, fitness, 0.0);
    }

    @Test
    public void minimizeSuiteFullCoverage() throws ClassNotFoundException, NoSuchFieldException, SecurityException, ConstructionFailedException, NoSuchMethodException
    {
        Properties.TARGET_CLASS = FlagExample1.class.getCanonicalName();
        Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
        GenericClass clazz = new GenericClass(sut);

        DefaultTestCase test = new DefaultTestCase();
        GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);

        TestFactory testFactory = TestFactory.getInstance();
        testFactory.addConstructor(test, gc, 0, 0);

        List<VariableReference> parameters = new ArrayList<VariableReference>();
        for (int i = 0; i < 10; i++) {
            IntPrimitiveStatement ips = new IntPrimitiveStatement(test, 28234 + i);
            VariableReference vr = test.addStatement(ips, i + 1);
            parameters.add(vr);
        }

        ConstructorStatement ct = new ConstructorStatement(test, gc, parameters);

        Method m = clazz.getRawClass().getMethod("testMe", new Class<?>[] { int.class });
        GenericMethod method = new GenericMethod(m, sut);
        testFactory.addMethod(test, method, 11, 0);

        parameters = new ArrayList<VariableReference>();
        for (int i = 12; i < 15; i++) {
            IntPrimitiveStatement ips = new IntPrimitiveStatement(test, i);
            VariableReference vr = test.addStatement(ips, i);
            parameters.add(vr);
        }
        ct = new ConstructorStatement(test, gc, parameters);
        testFactory.addMethod(test, method, 15, 0);

        assertEquals(16, test.size());

        TestSuiteChromosome tsc = new TestSuiteChromosome();
        tsc.addTest(test);
        TestSuiteFitnessFunction ff = new BranchCoverageSuiteFitness();
        double previous_fitness = ff.getFitness(tsc);
        tsc.setFitness(ff, previous_fitness);
        assertEquals(previous_fitness, 0.0, 0.0);

        TestSuiteMinimizer minimizer = new TestSuiteMinimizer(null);
        minimizer.minimize(tsc);
        assertTrue(tsc.getTests().get(0).toCode().equals("FlagExample1 flagExample1_0 = new FlagExample1();\nint int0 = 28234;\nint int1 = 28241;\nboolean boolean0 = flagExample1_0.testMe(int1);\nboolean boolean1 = flagExample1_0.testMe(int0);\n"));

        double fitness = ff.getFitness(tsc);
        assertEquals(previous_fitness, fitness, 0.0);
    }

    @Test
    @Ignore
    public void testBloodyAnnoyingBug() throws ClassNotFoundException, NoSuchFieldException, SecurityException, ConstructionFailedException, NoSuchMethodException
    {
        Properties.TARGET_CLASS = FlagExample1.class.getCanonicalName();
        Class<?> sut = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass(Properties.TARGET_CLASS);
        GenericClass clazz = new GenericClass(sut);

        GenericConstructor gc = new GenericConstructor(clazz.getRawClass().getConstructors()[0], clazz);

        DefaultTestCase test = new DefaultTestCase();

        List<VariableReference> parameters = new ArrayList<VariableReference>();
        IntPrimitiveStatement ips = new IntPrimitiveStatement(test, 0);
        VariableReference vr = test.addStatement(ips, 0);
        parameters.add(vr);

        ConstructorStatement ct = new ConstructorStatement(test, gc, parameters);

        Method m = clazz.getRawClass().getMethod("testMe", new Class<?>[] { int.class });
        GenericMethod method = new GenericMethod(m, sut);

        TestFactory testFactory = TestFactory.getInstance();
        testFactory.addConstructor(test, gc, 0, 0);
        testFactory.addMethodFor(test, ct.getReturnValue(), method, 1);
    }
}
