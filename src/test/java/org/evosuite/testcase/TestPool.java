package org.evosuite.testcase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTest;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.seeding.ObjectPool;
import org.evosuite.seeding.ObjectPoolManager;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.pool.DependencyClass;
import com.examples.with.different.packagename.pool.OtherClass;

public class TestPool extends SystemTest {

	private String pools = "";
	
	private double pPool = 0.0;
	
	@Before
	public void storeProperties() {
		pools = Properties.OBJECT_POOLS;
		pPool = Properties.P_OBJECT_POOL;
	}
	
	@After
	public void restoreProperties() {
		Properties.OBJECT_POOLS = pools;
		Properties.P_OBJECT_POOL = pPool;
	}
	
	@Test
	public void testPool() throws IOException {
		File f = File.createTempFile("EvoSuiteTestPool",null, FileUtils.getTempDirectory());
		String filename = f.getAbsolutePath();
		f.delete();
		System.out.println(filename);
		
		
		
		EvoSuite evosuite = new EvoSuite();

		String targetClass = DependencyClass.class.getCanonicalName();

		Properties.TARGET_CLASS = targetClass;
		String[] command = new String[] { "-generateSuite", "-class", targetClass };
		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = (GeneticAlgorithm<?>) result;
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		ObjectPool pool = ObjectPool.getPoolFromTestSuite(best);
		pool.writePool(filename);
		System.out.println("EvolvedTestSuite:\n" + best);

		targetClass = OtherClass.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;
		Properties.P_OBJECT_POOL = 1.0;
		Properties.OBJECT_POOLS = filename;
		ObjectPoolManager.getInstance().initialisePool();
		//Properties.SEARCH_BUDGET = 50000;

		command = new String[] { "-generateSuite", "-class", targetClass, "-Dobject_pools=" + filename };

		result = evosuite.parseCommandLine(command);
		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass(),
		                  result instanceof GeneticAlgorithm);

		ga = (GeneticAlgorithm<?>) result;
		best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
		f = new File(filename);
		f.delete();

	}
}
