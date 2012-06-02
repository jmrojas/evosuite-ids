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
package de.unisb.cs.st.evosuite.ga;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cross over two individuals
 * 
 * @author Gordon Fraser
 * 
 */
public abstract class CrossOverFunction implements Serializable {

	private static final long serialVersionUID = -4765602400132319324L;

	protected static Logger logger = LoggerFactory.getLogger(CrossOverFunction.class);

	/**
	 * Replace parents with crossed over individuals
	 * 
	 * @param parent1
	 * @param parent2
	 * @throws ConstructionFailedException
	 */
	public abstract void crossOver(Chromosome parent1, Chromosome parent2)
	        throws ConstructionFailedException;

}
