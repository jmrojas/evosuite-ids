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
package org.evosuite.runtime;

/**
 * <p>
 * Random class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class Random {

	private static boolean wasAccessed = false;

	/**
	 * We have a unique number that is increased every time a new random number
	 * is accessed
	 */
	private static int currentNumber = 0;

	/**
	 * Replacement function for nextInt
	 * 
	 * @return a int.
	 */
	public static int nextInt() {
		wasAccessed = true;
		return currentNumber++;
	}

	/**
	 * Replacement function for nextInt
	 * 
	 * @param max
	 *            a int.
	 * @return a int.
	 */
	public static int nextInt(int max) {
		wasAccessed = true;
		return currentNumber % max;
	}

	/**
	 * Replacement function for nextFloat
	 * 
	 * @return a float.
	 */
	public static float nextFloat() {
		wasAccessed = true;
		return currentNumber++;
	}

	/**
	 * Replacement function for nextFloat
	 * 
	 * @return a float.
	 */
	public static float nextDouble() {
		wasAccessed = true;
		return currentNumber++;
	}

	/**
	 * Replacement function for nextLong
	 * 
	 * @return a long.
	 */
	public static long nextLong() {
		wasAccessed = true;
		return currentNumber++;
	}

	/**
	 * Set the next random number to a value
	 * 
	 * @param number
	 *            a int.
	 */
	public static void setNextRandom(int number) {
		currentNumber = number;
	}

	/**
	 * Reset runtime to initial state
	 */
	public static void reset() {
		currentNumber = 0;
		wasAccessed = false;
	}

	/**
	 * Getter to check whether this runtime replacement was accessed during test
	 * execution
	 * 
	 * @return a boolean.
	 */
	public static boolean wasAccessed() {
		return wasAccessed;
	}

}
