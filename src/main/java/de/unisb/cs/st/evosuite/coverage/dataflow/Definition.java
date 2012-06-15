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
package de.unisb.cs.st.evosuite.coverage.dataflow;

import de.unisb.cs.st.evosuite.graphs.cfg.BytecodeInstruction;

/**
 * An object of this class corresponds to a Definition inside the class under test.
 * 
 * Definitions are created by the DefUseFactory via the DefUsePool.
 * 
 * @author Andre Mis
 */

public class Definition extends DefUse {

	Definition(BytecodeInstruction wrap, int defuseId, int defId, int useId,
			boolean isParameterUse) {
	
		super(wrap, defuseId, defId, useId, isParameterUse);
		if (!isDefinition())
			throw new IllegalArgumentException(
					"Vertex of a definition expected");
	}
	
	/**
	 * Determines whether this Definition can be an active definition
	 * for the given instruction.
	 * 
	 *  This is the case if instruction constitutes a Use for the 
	 *  same variable as this Definition
	 *  
	 *  Not to be confused with DefUse.canBecomeActiveDefinitionFor,
	 *  which is sort of the dual to this method
	 */
	public boolean canBeActiveFor(BytecodeInstruction instruction) {
		if(!instruction.isUse())
			return false;
//		if(!DefUsePool.isKnownAsUse(instruction))
//			return false;
		
//		Use use = DefUseFactory.makeUse(instruction);
		return sharesVariableWith(instruction);
	}
	
//	@Override
//	public boolean equals(Object o) {
//		if(o==null)
//			return false;
//		if(o==this)
//			return true;
//		if(!(o instanceof Definition))
//			return super.equals(o);
//		
//		Definition other = (Definition)o;
//		
//		return defId == other.defId;
//	}
}
