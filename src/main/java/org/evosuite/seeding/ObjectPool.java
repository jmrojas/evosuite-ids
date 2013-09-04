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
/**
 * 
 */
package org.evosuite.seeding;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.testcarver.extraction.CarvingRunListener;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.DebuggingObjectOutputStream;
import org.evosuite.utils.GenericClass;
import org.evosuite.utils.Randomness;
import org.junit.runner.JUnitCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Pool of interesting method sequences for different objects
 *
 * @author Gordon Fraser
 */
public class ObjectPool implements Serializable {

	private static final long serialVersionUID = 2016387518459994272L;

	/** The actual object pool */
	protected final Map<GenericClass, Set<TestCase>> pool = new HashMap<GenericClass, Set<TestCase>>();

	protected static Logger logger = LoggerFactory.getLogger(ObjectPool.class);
	
	/**
	 * Insert a new sequence for given Type
	 *
	 * @param clazz a {@link java.lang.reflect.Type} object.
	 * @param sequence a {@link org.evosuite.testcase.TestCase} object.
	 */
	public void addSequence(GenericClass clazz, TestCase sequence) {
		ObjectSequence seq = new ObjectSequence(clazz, sequence);
		addSequence(seq);
	}

	/**
	 * Helper method to add sequences
	 * 
	 * @param sequence
	 */
	private void addSequence(ObjectSequence sequence) {
		if (!pool.containsKey(sequence.getGeneratedClass()))
			pool.put(sequence.getGeneratedClass(), new HashSet<TestCase>());

		pool.get(sequence.getGeneratedClass()).add(sequence.getSequence());
		logger.info("Added new sequence for " + sequence.getGeneratedClass());
		logger.info(sequence.getSequence().toCode());

	}


	/**
	 * Randomly choose a sequence for a given Type
	 *
	 * @param clazz a {@link java.lang.reflect.Type} object.
	 * @return a {@link org.evosuite.testcase.TestCase} object.
	 */
	public TestCase getRandomSequence(GenericClass clazz) {
		return Randomness.choice(pool.get(clazz));
	}
	
	/**
	 * Retrieve all possible sequences for a given Type
	 *
	 * @param clazz a {@link java.lang.reflect.Type} object.
	 * @return a {@link java.util.Set} object.
	 */
	public Set<TestCase> getSequences(GenericClass clazz) {
		return pool.get(clazz);
	}
	
	public Set<GenericClass> getClasses() {
		return pool.keySet();
	}

	/**
	 * Check if there are sequences for given Type
	 *
	 * @param clazz a {@link java.lang.reflect.Type} object.
	 * @return a boolean.
	 */
	public boolean hasSequence(GenericClass clazz) {
		return pool.containsKey(clazz);
	}
	
	public int getNumberOfClasses() {
		return pool.size();
	}
	
	public int getNumberOfSequences() {
		int num = 0;
		for(Set<TestCase> p : pool.values()) {
			num += p.size();
		}
		return num;
	}

	public boolean isEmpty() {
		return pool.isEmpty();
	}
	
	/**
	 * Read a serialized pool
	 * @param fileName
	 */
	public static ObjectPool getPoolFromFile(String fileName) {
		try {
			InputStream in = new FileInputStream(fileName);
			ObjectInputStream objectIn = new ObjectInputStream(in);
			ObjectPool pool = (ObjectPool) objectIn.readObject();
			in.close();
			return pool;
		} catch (Exception e) {
			logger.error("Exception while trying to get object pool from "+fileName+" , "+e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * Convert a test suite to a pool
	 * 
	 * @param testSuite
	 */
	public static ObjectPool getPoolFromTestSuite(TestSuiteChromosome testSuite) {
		ObjectPool pool = new ObjectPool();
		
		for(TestChromosome testChromosome : testSuite.getTestChromosomes()) {
			TestCase test = testChromosome.getTestCase().clone();
			if(testChromosome.hasException()) {
				test.chop(test.size() - 2);
			}
			if(test.hasObject(Properties.getTargetClass(), test.size())) {
				pool.addSequence(new GenericClass(Properties.getTargetClass()), test);
			}
		}
		/*
		 for(TestCase test : testSuite.getTests()) {
		 	pool.addSequence(new GenericClass(Properties.getTargetClass()), test);
		 }
		 */
		return pool;
	}
	
	public static ObjectPool getPoolFromTestCases(Collection<TestCase> tests) {
		ObjectPool pool = new ObjectPool();
		for(TestCase test : tests) {
			TestCase copy = test.clone();
			copy.removeAssertions();
			pool.addSequence(new GenericClass(Properties.getTargetClass()), copy);
		}
		return pool;
	}

	/**
	 * Execute all tests in a JUnit test suite and add resulting sequences from carver
	 * 
	 * @param targetClass
	 * @param testSuite
	 */
	public static ObjectPool getPoolFromJUnit(GenericClass targetClass, Class<?> testSuite) {
		final JUnitCore runner = new JUnitCore();
		final CarvingRunListener listener = new CarvingRunListener();
		runner.addListener(listener);
		
		final org.evosuite.testcarver.extraction.CarvingClassLoader classLoader = new org.evosuite.testcarver.extraction.CarvingClassLoader(); 
		
		try {
			// instrument target class
			classLoader.loadClass(Properties.getTargetClass().getCanonicalName());
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		ObjectPool pool = new ObjectPool();
		//final Result result = 
		runner.run(testSuite);
		for(TestCase test : listener.getTestCases()) {
			// TODO: Maybe we would get the targetClass from the last object generated in the sequence?
			pool.addSequence(targetClass, test);
		}
		
		// TODO: Some messages based on result
		
		return pool;
		
	}
	
	public void writePool(String fileName) {
		try {
			ObjectOutputStream out = new DebuggingObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(this);
			out.close();
		} catch(IOException e) {
			// TODO
		}
	}
	
	

}
