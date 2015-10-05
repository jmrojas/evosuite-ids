package org.evosuite.setup.callgraph;

import com.examples.with.different.packagename.setup.*;
import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.classpath.ClassPathHandler;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.TestCluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea Arcuri on 13/09/15.
 */
public class DependencyAnalysis_removeUnusableGeneratorsTest {


    @Test
    public void test() throws Exception {
        String targetClass = ClassToCheckGenerators.class.getName();
        Properties.TARGET_CLASS = targetClass;
        List<String> classpath = new ArrayList<>();
        String cp = System.getProperty("user.dir") + "/target/test-classes";
        classpath.add(cp);
        ClassPathHandler.getInstance().addElementToTargetProjectClassPath(cp);
        ClassPathHandler.getInstance().changeTargetCPtoTheSameAsEvoSuite();

        DependencyAnalysis.analyzeClass(targetClass, classpath);

        ClassLoader cl = TestGenerationContext.getInstance().getClassLoaderForSUT();


        //WithGenerator has default constructor
        Assert.assertTrue(TestCluster.getInstance().hasGenerator(cl.loadClass(WithGenerator.class.getName())));

        //Only generator for IX is GeneratorForX, but this latter has no generator itself
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(IX.class.getName())));
        //Special case of recursion
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(IGeneratorForItself.class.getName())));

        //same for abstract versions
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(AX.class.getName())));
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(AGeneratorForItself.class.getName())));


        //be sure it has no generator
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(GeneratorForX.class.getName())));

        //even for concrete versions it should not work, as they all have private constructors
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(X.class.getName())));
        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(GeneratorForItself.class.getName())));
    }


    @Test
    public void testCycle() throws Exception {
        String targetClass = ClassToCheckGetterOfInput.class.getName();
        Properties.TARGET_CLASS = targetClass;
        List<String> classpath = new ArrayList<>();
        String cp = System.getProperty("user.dir") + "/target/test-classes";
        classpath.add(cp);
        ClassPathHandler.getInstance().addElementToTargetProjectClassPath(cp);
        ClassPathHandler.getInstance().changeTargetCPtoTheSameAsEvoSuite();

        DependencyAnalysis.analyzeClass(targetClass, classpath);

        ClassLoader cl = TestGenerationContext.getInstance().getClassLoaderForSUT();

        Assert.assertFalse(TestCluster.getInstance().hasGenerator(cl.loadClass(AnInterface.class.getName())));
    }
}