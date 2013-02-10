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
 *
 * @author Gordon Fraser
 */
package org.evosuite.symbolic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uta.cse.dsc.StopVMException;

public class ConstraintTooLongException extends StopVMException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4788691891779076515L;

	private static Logger logger = LoggerFactory
			.getLogger(ConstraintTooLongException.class);

	private final int constraint_size;
	public ConstraintTooLongException(int constraint_size) {
		this.constraint_size = constraint_size;
	}

	public int getConstraintSize() {
		return this.constraint_size;
	}
}
