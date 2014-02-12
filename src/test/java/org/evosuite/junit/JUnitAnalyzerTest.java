package org.evosuite.junit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.evosuite.Properties;
import org.evosuite.sandbox.Sandbox;
import org.evosuite.testcase.JUnitTestCarvedChromosomeFactory;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.utils.ClassPathHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.sandbox.OpenStream;

public class JUnitAnalyzerTest {

	//we use carver to simplify the creation of test case chromosomes
	
	private static final String defaultSelectedJUnit = Properties.SELECTED_JUNIT;
	private static final int defaultSeedMutations = Properties.SEED_MUTATIONS;
	private static final double defaultSeedClone = Properties.SEED_CLONE;
	private static final boolean DEFAULT_VFS = Properties.VIRTUAL_FS; 
	private static final boolean DEFAULT_SANDBOX = Properties.SANDBOX; 
	private static final boolean DEFAULT_ASSERTS_FOR_EVO = Properties.ENABLE_ASSERTS_FOR_EVOSUITE;
	
	private File file = new File(OpenStream.FILE_NAME);

	
	@Before
	public void init() {
		
		ClassPathHandler.getInstance().changeTargetCPtoTheSameAsEvoSuite();

		if(file.exists()){
			file.delete();
		}
		file.deleteOnExit();
	}

	@After
	public void reset(){
		Properties.SELECTED_JUNIT = defaultSelectedJUnit;
		Properties.SEED_MUTATIONS = defaultSeedMutations;
		Properties.SEED_CLONE = defaultSeedClone;
		Properties.VIRTUAL_FS = DEFAULT_VFS;	
		Properties.SANDBOX = DEFAULT_SANDBOX;
		Properties.ENABLE_ASSERTS_FOR_EVOSUITE = DEFAULT_ASSERTS_FOR_EVO;
	}
	
	@Test 
	public void testSandboxIssue(){

		//First, get a TestCase from a carved JUnit
		
		Properties.SELECTED_JUNIT = com.examples.with.different.packagename.sandbox.OpenStreamInATryCatch_FakeTestToCarve.class.getCanonicalName();
		Properties.TARGET_CLASS = com.examples.with.different.packagename.sandbox.OpenStreamInATryCatch.class.getCanonicalName();

		Properties.SEED_MUTATIONS = 0;
		Properties.SEED_CLONE = 1;
		Properties.VIRTUAL_FS = false;		
		Properties.SANDBOX = true;
		Properties.ENABLE_ASSERTS_FOR_EVOSUITE = true; //needed for setLoggingForJUnit
		
		
		//FIXME
		Sandbox.initializeSecurityManagerForSUT();
		
		//file should never be created
		Assert.assertFalse(file.exists());

		JUnitTestCarvedChromosomeFactory factory = new JUnitTestCarvedChromosomeFactory(null);
		TestChromosome carved = factory.getChromosome();

		/*
		 * FIXME: issue with carver
		 */
		file.delete();
		
		Assert.assertFalse(file.exists());

		Assert.assertNotNull(carved);
		
		TestCase test = carved.getTestCase();
		
		Assert.assertEquals("Shouble be: constructor, 1 variable and 1 method", 3, test.size());

		//Now that we have a test case, we check its execution after
		//recompiling it to JUnit, and see if sandbox kicks in
		
		List<TestCase> list = new ArrayList<TestCase>();
		list.add(test);
		
		Assert.assertFalse(file.exists());

		//NOTE: following order of checks reflects what is done
		// in EvoSuite after the search is finished
		
		//first try to compile (which implies execution)
		JUnitAnalyzer.removeTestsThatDoNotCompile(list);
		Assert.assertEquals(1, list.size());
		Assert.assertFalse(file.exists()); 
		
		//try once
		JUnitAnalyzer.handleTestsThatAreUnstable(list);
		Assert.assertEquals(1, list.size());
		Assert.assertFalse(file.exists()); 		

		//try again
		JUnitAnalyzer.handleTestsThatAreUnstable(list);
		Assert.assertEquals(1, list.size());
		Assert.assertFalse(file.exists()); 		
		
		JUnitAnalyzer.verifyCompilationAndExecution(list);
		Assert.assertEquals(1, list.size());
		Assert.assertFalse(file.exists()); 			
	}
	
	@Test
	public void testCreationOfTmpDir() throws IOException{
		
		File dir = JUnitAnalyzer.createNewTmpDir();
		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		
		FileUtils.deleteDirectory(dir);
		Assert.assertFalse(dir.exists());
	}
	
	
}
