/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.unisb.cs.st.evosuite.testcase;


import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.ChromosomeFactory;
import de.unisb.cs.st.evosuite.ga.Randomness;


/**
 * @author Gordon Fraser
 *
 */
public class RandomLengthTestFactory implements ChromosomeFactory {

	protected static Logger logger = Logger.getLogger(FixedLengthTestChromosomeFactory.class);
	
	/** Attempts before giving up construction */
	protected int max_attempts     = Properties.getPropertyOrDefault("max_attempts", 1000);

	/** Factory to manipulate and generate method sequences */
	private DefaultTestFactory test_factory = DefaultTestFactory.getInstance();
	
	/**
	 * Create a random individual
	 * @param size
	 */
	private TestCase getRandomTestCase(int size) {
		TestCase test = getNewTestCase();
		int num = 0;
		
		// Choose a random length in 0 - size
		Randomness randomness = Randomness.getInstance();
		int length = randomness.nextInt(size);
		while(length == 0)
			length = randomness.nextInt(size);

		// Then add random stuff
		while(test.size() < length && num < max_attempts) {
			test_factory.insertRandomStatement(test);
			num++;
		}
		if(logger.isDebugEnabled())
			logger.debug("Randomized test case:" + test.toCode());

		return test;
	}
	
	/**
	 * Generate a random chromosome
	 */
	public Chromosome getChromosome() {
		TestChromosome c = new TestChromosome();
		c.test = getRandomTestCase(Properties.CHROMOSOME_LENGTH);
		return c;
	}
	
	/**
	 * Provided so that subtypes of this factory type can modify the returned TestCase
	 * @return
	 */
	protected TestCase getNewTestCase(){
		return new TestCase();
	}

}
