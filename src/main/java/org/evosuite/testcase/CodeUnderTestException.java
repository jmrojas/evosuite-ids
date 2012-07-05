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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to wrap exceptions thrown in code under test. This is needed as VariableReference.getObjects/.setObject 
 * and AbstractStatement.execute() do not operate on the same layer.
 * 
 * With the introduction of FieldReferences VariableReferences can throw arbitrary (of cource wrapped) exceptions, 
 * as a Field.get() can trigger static{} blocks
 * 
 * @author Sebastian Steenbuck
 *
 */
public class CodeUnderTestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CodeUnderTestException.class);
	
	public CodeUnderTestException(Throwable cause){
		super(cause);
	}

	/**
	 * Used by code calling VariableReference.setObject/2 and .getObject()/1 
	 * @param e
	 * @return only there to make the compiler happy, this method always throws an exception
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 * @throws ExceptionInInitializerError
	 * @throws AssertionError if e wasn't one of listed for types
	 */
	public static Error throwException(Throwable e) throws IllegalAccessException, IllegalArgumentException, NullPointerException, ExceptionInInitializerError{
		if(e instanceof CodeUnderTestException){
			e=e.getCause();
		}
		if(e instanceof IllegalAccessException){
			throw (IllegalAccessException)e;
		}else if(e instanceof IllegalArgumentException){
			throw (IllegalArgumentException)e;
		}else if(e instanceof NullPointerException){
			throw (NullPointerException)e;
		}else if(e instanceof ArrayIndexOutOfBoundsException){
			throw (ArrayIndexOutOfBoundsException)e;
		}else if(e instanceof ExceptionInInitializerError){
			throw (ExceptionInInitializerError)e;
		}else{
			logger.error("We expected the exception to be one of the listed but it was", e);
			throw new AssertionError("We expected the exception to be one of the listed but it was" + e.getClass());
		}
	}
}
