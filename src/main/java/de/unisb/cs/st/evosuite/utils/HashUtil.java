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
package de.unisb.cs.st.evosuite.utils;

public abstract class HashUtil {
	public static final int DEFAULT_PRIME = 31;
	
	public static final int hashCodeWithPrime(int prime, Object... components) {
		int result = 1;
		
		for (Object component : components) {
			result = prime * result + component.hashCode();
		}
		
		return result;
	}
	
	public static final int hashCodeWithPrime(int prime, int... componentHashCodes) {
		int result = 1;
		
		for (int componentHashCode : componentHashCodes) {
			result = prime * result + componentHashCode;
		}
		
		return result;
	}
	
	public static final int hashCode(Object... components) {
		return hashCodeWithPrime(DEFAULT_PRIME, components);
	}
/*	
	public static final int hashCode(int... componentHashCodes) {
		return hashCodeWithPrime(DEFAULT_PRIME, componentHashCodes);
	}
*/
}
