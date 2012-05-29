/**
 * Copyright (C) 2012 Gordon Fraser, Andrea Arcuri
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
/**
 * 
 */
package de.unisb.cs.st.evosuite.assertion;

import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.ExampleFieldClass;
import com.examples.with.different.packagename.ExampleInheritedClass;
import com.examples.with.different.packagename.ExampleObserverClass;
import com.examples.with.different.packagename.ExampleStaticVoidSetterClass;

import de.unisb.cs.st.evosuite.EvoSuite;
import de.unisb.cs.st.evosuite.SystemTest;
import de.unisb.cs.st.evosuite.ga.GeneticAlgorithm;
import org.junit.Ignore;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteChromosome;

/**
 * @author fraser
 * 
 */
public class TestMutationAssertion extends SystemTest {
	private TestSuiteChromosome generateSuite(Class<?> clazz) {
		EvoSuite evosuite = new EvoSuite();
		int generations = 1;

		String targetClass = clazz.getCanonicalName();

		String[] command = new String[] {
		        //EvoSuite.JAVA_CMD,
		        "-generateTests", "-class", targetClass, "-Dhtml=false", "-Dplot=false",
		        "-Djunit_tests=false", "-Dshow_progress=false",
		        "-Dgenerations=" + generations, "-assertions",
		        "-Dassertion_strategy=mutation", "-Dserialize_result=true" };

		Object result = evosuite.parseCommandLine(command);

		Assert.assertTrue(result != null);
		Assert.assertTrue("Invalid result type :" + result.getClass() + ", " + result,
		                  result instanceof GeneticAlgorithm);

		GeneticAlgorithm ga = (GeneticAlgorithm) result;
		return (TestSuiteChromosome) ga.getBestIndividual();
	}

	@Ignore
	@Test
	public void test1() {
		TestSuiteChromosome suite = generateSuite(ExampleObserverClass.class);

		Assert.assertTrue(suite.size() > 0);
		for (TestCase test : suite.getTests()) {
			Assert.assertTrue("Test has no assertions: " + test.toCode(),
			                  test.hasAssertions());
		}
	}

	@Ignore
	@Test
	public void test2() {
		TestSuiteChromosome suite = generateSuite(ExampleFieldClass.class);

		Assert.assertTrue(suite.size() > 0);
		for (TestCase test : suite.getTests()) {
			Assert.assertTrue("Test has no assertions: " + test.toCode(),
			                  test.hasAssertions());
		}
	}

	@Ignore
	@Test
	public void test3() {
		TestSuiteChromosome suite = generateSuite(ExampleInheritedClass.class);

		Assert.assertTrue(suite.size() > 0);
		for (TestCase test : suite.getTests()) {
			Assert.assertTrue("Test has no assertions: " + test.toCode(),
			                  test.hasAssertions());
		}
	}

	@Ignore
	@Test
	public void test4() {
		TestSuiteChromosome suite = generateSuite(ExampleStaticVoidSetterClass.class);

		Assert.assertTrue(suite.size() > 0);
		for (TestCase test : suite.getTests()) {
			if (test.size() > 1)
				Assert.assertTrue("Test has no assertions: " + test.toCode(),
				                  test.hasAssertions());
		}
	}
}
