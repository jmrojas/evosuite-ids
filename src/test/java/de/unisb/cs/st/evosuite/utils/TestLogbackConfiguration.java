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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.*;

import com.vladium.logging.Logger;

import de.unisb.cs.st.evosuite.TestUtil;

public class TestLogbackConfiguration {

	public static final PrintStream defaultOut = System.out;
	public static final PrintStream defaultErr = System.err;
	
	@After
	public void resetDefaultPrinters(){
		System.setOut(defaultOut);
		System.setErr(defaultErr);
	}
	
	@Test
	public void testStdOutErr(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		System.setOut(new PrintStream(out));
		
		ByteArrayOutputStream err = new ByteArrayOutputStream();		
		System.setErr(new PrintStream(err));
		
		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestLogbackConfiguration.class);
		
		final String warnMsg = "this should go to std out"; 
		final String errMsg = "this should go to std err"; 
		
		logger.warn(warnMsg);
		logger.error(errMsg);
	
		String printedOut = out.toString();
		String printedErr = err.toString();
		
		Assert.assertTrue("Content of std out is: "+printedOut,printedOut.contains(warnMsg));
		Assert.assertTrue("Content of std err is: "+printedErr,printedErr.contains(errMsg));
		Assert.assertTrue("Content of std out is: "+printedOut, ! printedOut.contains(errMsg));
		Assert.assertTrue("Content of std err is: "+printedErr, ! printedErr.contains(warnMsg));
	}
	
}
