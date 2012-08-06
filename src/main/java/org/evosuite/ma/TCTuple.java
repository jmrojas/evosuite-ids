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
package org.evosuite.ma;

import java.util.HashSet;
import java.util.Set;

import org.evosuite.testcase.TestCase;


/**
 * The <code>TCTuple</code> class is a data structure which are used by
 * {@link Editor} class to store and manage test cases.
 *
 * @author Yury Pavlov
 */
public class TCTuple implements Cloneable {

	private TestCase testCase;

	private Set<Integer> coverage = new HashSet<Integer>();

	// Used by WideTestEditorGUI to store written source code 
	private String origSourceCode = "";

	/**
	 * <p>Constructor for TCTuple.</p>
	 *
	 * @param testCase a {@link org.evosuite.testcase.TestCase} object.
	 * @param coverage a {@link java.util.Set} object.
	 */
	public TCTuple(TestCase testCase, Set<Integer> coverage) {
		this.testCase = testCase;
		this.coverage = coverage;
	}

	/**
	 * <p>Constructor for TCTuple.</p>
	 *
	 * @param testCase a {@link org.evosuite.testcase.TestCase} object.
	 * @param coverage a {@link java.util.Set} object.
	 * @param origSourceCode a {@link java.lang.String} object.
	 */
	public TCTuple(TestCase testCase, Set<Integer> coverage, String origSourceCode) {
		this.testCase = testCase;
		this.coverage = coverage;
		this.origSourceCode = origSourceCode;
	}

	/**
	 * <p>Getter for the field <code>coverage</code>.</p>
	 *
	 * @return the coverage
	 */
	public Set<Integer> getCoverage() {
		return coverage;
	}

	/**
	 * <p>Getter for the field <code>testCase</code>.</p>
	 *
	 * @return the testCase
	 */
	public TestCase getTestCase() {
		return testCase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		return false;
	}

	/**
	 * Returns a shallow copy of this <tt>TestCaseTuple</tt> instance. (The
	 * elements themselves are not copied.)
	 *
	 * @return a clone of this <tt>TestCaseTuple</tt> instance
	 */
	public TCTuple clone() {
		try {
			TCTuple v = (TCTuple) super.clone();
			v.testCase = testCase.clone();
			v.coverage = new HashSet<Integer>(coverage);
			return v;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		// main target of this representation just to identify diff.
		// TestCaseTuple
		String res = "TestCase: " + this.hashCode() + ". Coverage: " + coverage + "\n" + "Source code: \n"
				+ testCase.toCode();

		return res;
	}

	/**
	 * <p>Setter for the field <code>origSourceCode</code>.</p>
	 *
	 * @param origSourceCode
	 *            the origSourceCode to set
	 */
	public void setOrigSourceCode(String origSourceCode) {
		this.origSourceCode = origSourceCode;
	}

	/**
	 * <p>Getter for the field <code>origSourceCode</code>.</p>
	 *
	 * @return the origSourceCode
	 */
	public String getOrigSourceCode() {
		if (origSourceCode.toString().equals("")) {
			return testCase.toCode();
		}
		return origSourceCode;
	}

}
