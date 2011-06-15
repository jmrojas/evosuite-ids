package de.unisb.cs.st.evosuite.coverage.dataflow;

import de.unisb.cs.st.evosuite.cfg.BytecodeInstruction;

/**
 * An object of this class corresponds to a Use inside the class under test.
 * 
 * Uses are created by the DefUseFactory via the DefUsePool.
 * 
 * @author Andre Mis
 */

public class Use extends DefUse {

	Use(BytecodeInstruction wrap, int defuseId, int defId, int useId, boolean isParameterUse) {

		super(wrap, defuseId, defId, useId, isParameterUse);
		if (!isUse()) {
			throw new IllegalArgumentException("Vertex of a use expected");
		}
	}

}
