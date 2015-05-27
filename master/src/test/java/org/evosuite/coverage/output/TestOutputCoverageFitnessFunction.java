/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.coverage.output;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.evosuite.SystemTest;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.examples.with.different.packagename.coverage.MethodReturnsObject;
import com.examples.with.different.packagename.coverage.MethodReturnsPrimitive;

/**
 * @author Jose Miguel Rojas
 *
 */
public class TestOutputCoverageFitnessFunction extends SystemTest {

    private static final Criterion[] defaultCriterion = Properties.CRITERION;
    
    private static boolean defaultArchive = Properties.TEST_ARCHIVE;

	@After
	public void resetProperties() {
		Properties.CRITERION = defaultCriterion;
		Properties.TEST_ARCHIVE = defaultArchive;
	}

	@Before
	public void beforeTest() {
        Properties.CRITERION[0] = Criterion.OUTPUT;
		//Properties.MINIMIZE = false;
	}

	@Test
	public void testOutputCoveragePrimitiveTypesWithArchive() {
		Properties.TEST_ARCHIVE = true;
		testOutputCoveragePrimitiveTypes();
	}
	
	@Test
	public void testOutputCoveragePrimitiveTypesWithoutArchive() {
		Properties.TEST_ARCHIVE = false;
		testOutputCoveragePrimitiveTypes();
	}
		
	public void testOutputCoveragePrimitiveTypes() {
		EvoSuite evosuite = new EvoSuite();
		
		String targetClass = MethodReturnsPrimitive.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		String[] command = new String[] { "-generateSuite", "-class", targetClass };
		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();

		int goals = TestSuiteGenerator.getFitnessFactory().get(0).getCoverageGoals().size(); // assuming single fitness function
		Assert.assertEquals(14, goals );
		Assert.assertEquals("Non-optimal fitness: ", 0.0, best.getFitness(), 0.001);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testOutputCoverageObjectTypeWithArchive() {
		Properties.TEST_ARCHIVE = true;
		testOutputCoverageObjectType();
	}
	
	@Test
	public void testOutputCoverageObjectTypeWithoutArchive() {
		Properties.TEST_ARCHIVE = false;
		testOutputCoverageObjectType();
	}
		
	public void testOutputCoverageObjectType() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = MethodReturnsObject.class.getCanonicalName();
		Properties.TARGET_CLASS = targetClass;

		
		String[] command = new String[] { "-generateSuite", "-class", targetClass };
		Object result = evosuite.parseCommandLine(command);
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		int goals = TestSuiteGenerator.getFitnessFactory().get(0).getCoverageGoals().size(); // assuming single fitness function
		Assert.assertEquals(2, goals );
		Assert.assertEquals("Non-optimal fitness: ", 0.0, best.getFitness(), 0.001);
		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
	}

}
