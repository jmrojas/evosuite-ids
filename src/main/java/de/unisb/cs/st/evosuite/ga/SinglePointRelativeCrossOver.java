/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of the GA library.
 * 
 * GA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with GA.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.ga;

/**
 * Cross over individuals at relative position
 * 
 * @author Gordon Fraser
 *
 */
public class SinglePointRelativeCrossOver extends CrossOverFunction {

	
	/**
	 * The splitting point is not an absolute value but a relative value (eg, at position 70% of n).
	 * For example, if n1=10 and n2=20 and splitting point is 70%, we would have position 7 in the first and 14 in the second.
	 * Therefore, the offspring d have n<=max(n1,n2)
	 */
	@Override
	public void crossOver(Chromosome parent1, Chromosome parent2)
			throws ConstructionFailedException {
		
		if(parent1.size() <=2 || parent2.size() <= 2) {
			return;
		}
		// Choose a position in the middle
		float split_point = randomness.nextFloat();

		Chromosome t1 = parent1.clone();
		Chromosome t2 = parent2.clone();
		
		parent1.crossOver(t2, Math.round(t1.size() * split_point), Math.round(t2.size() * split_point));
		parent2.crossOver(t1, Math.round(t2.size() * split_point), Math.round(t1.size() * split_point));	
	}

}
