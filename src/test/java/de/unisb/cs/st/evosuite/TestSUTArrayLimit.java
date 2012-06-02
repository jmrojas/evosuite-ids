/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unisb.cs.st.evosuite;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.ArrayLimit;
import com.examples.with.different.packagename.DivisionByZero;

import de.unisb.cs.st.evosuite.ga.GeneticAlgorithm;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteChromosome;




public class TestSUTArrayLimit extends SystemTest{

	public static final int defaultArrayLimit = Properties.ARRAY_LIMIT;

	@After
	public void resetProperties(){
		Properties.ARRAY_LIMIT = defaultArrayLimit;
	}


	@Test
	public void testWithinLimits() {
		EvoSuite evosuite = new EvoSuite();
		String targetClass = ArrayLimit.class.getCanonicalName();
		
		Properties.TARGET_CLASS = targetClass;
		
		String[] command = new String[]{				
				"-generateSuite",
				"-class",
				targetClass
		};
		
		Object result = evosuite.parseCommandLine(command);
		
		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :"+result.getClass(), result instanceof GeneticAlgorithm);
		
		GeneticAlgorithm ga = (GeneticAlgorithm) result;
		TestSuiteChromosome best = (TestSuiteChromosome)ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n"+best);

		Assert.assertEquals("Non-optimal coverage: ",1d, best.getCoverage(), 0.001);
	}

	@Test
	public void testAboveLimits() {
		EvoSuite evosuite = new EvoSuite();
		String targetClass = ArrayLimit.class.getCanonicalName();
		
		Properties.ARRAY_LIMIT = 10;
		Properties.TARGET_CLASS = targetClass;
		
		String[] command = new String[]{				
				"-generateSuite",
				"-class",
				targetClass
		};
		
		Object result = evosuite.parseCommandLine(command);
		
		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :"+result.getClass(), result instanceof GeneticAlgorithm);
		
		GeneticAlgorithm ga = (GeneticAlgorithm) result;
		TestSuiteChromosome best = (TestSuiteChromosome)ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n"+best);

		Assert.assertTrue("Optimal coverage: ", best.getCoverage() < 0.99);
	}

}
