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
package de.unisb.cs.st.evosuite;

import org.junit.*;

/**
 * @author Andrea Arcuri
 * 
 */
public class TestEnabledAssertions {

	/*
	 * when we run the test cases in series, we need to be reminded that we should activate 
	 * the assertions inside EvoSuite with -ea:de.unisb.cs.st...
	 */
	@Test
	public void testIfAssertionsAreEnabled(){
		boolean enabled = false;
		
		assert (enabled=true)  ; 
		
		Assert.assertTrue(enabled);
	}
	
}
