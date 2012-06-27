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
package org.evosuite.testcase;

/**
 * This error can be used to signal an throwable from evosuite code, below the
 * class under test. E.g. the class under tests is instrumented to call the
 * method evosuite.something() which throws and error. If the error is of the
 * type EvosuiteError the exception is thrown. If it is of any other type, the
 * exception is catched and it is assumed, that the exception was thrown by the
 * class under test
 * 
 * @author Sebastian Steenbuck
 * 
 */
public class EvosuiteError extends Error {
	private static final long serialVersionUID = 454018150971425158L;

	public EvosuiteError(Throwable cause) {
		super(cause);
	}
	
	public EvosuiteError(String msg){
		super(msg);
	}
}
