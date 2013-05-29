package org.evosuite.coverage.entropy;

import static org.junit.Assert.*;

public class Hamming
{
	/**
	 * Calculating the Hamming Distance for two strings requires the string to be of the same length.
	 * 
	 * @return
	 */
	public static boolean isSimilar(boolean[] one, boolean[] two, int numberGoals)
	{
		assertTrue(one.length == two.length);

		int counter = 0;
		for (int i = 0; i < one.length; i++)
			if (one[i] != two[i])
				counter++;

		if ( (counter >= 0) && (counter <= (numberGoals*0.25)) )
			return true;
		else
			return false;
	}
}
