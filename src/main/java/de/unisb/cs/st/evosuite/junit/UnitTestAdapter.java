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
/**
 * 
 */
package de.unisb.cs.st.evosuite.junit;

import java.util.List;
import java.util.Map;

import de.unisb.cs.st.evosuite.testcase.TestCase;

/**
 * @author fraser
 * 
 */
public interface UnitTestAdapter {

	/**
	 * Get all the framework dependent imports
	 * 
	 * @return
	 */
	public String getImports();

	/**
	 * Get the framework specific definition of the test class
	 * 
	 * @param testName
	 * @return
	 */
	public String getClassDefinition(String testName);

	/**
	 * Get the framework specific definition of a test method
	 * 
	 * @param testName
	 * @return
	 */
	public String getMethodDefinition(String testName);

	/**
	 * Get the class definition of a test suite
	 * 
	 * @param tests
	 * @return
	 */
	public String getSuite(List<String> tests);

	/**
	 * Return the sequence of method calls for a test
	 * 
	 * @param test
	 * @param exceptions
	 * @return
	 */
	public String getTestString(int id, TestCase test, Map<Integer, Throwable> exceptions);
}
