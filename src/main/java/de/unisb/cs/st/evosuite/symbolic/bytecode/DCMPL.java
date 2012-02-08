/*
 * Copyright (C) 2005,2006 United States Government as represented by the
 * Administrator of the National Aeronautics and Space Administration (NASA).
 * All Rights Reserved.
 * 
 * Copyright (C) 2011 Saarland University
 * 
 * This file is part of EvoSuite, but based on the SymbC extension of JPF
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
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.symbolic.bytecode;

import java.util.logging.Logger;

import gov.nasa.jpf.JPF;
import gov.nasa.jpf.jvm.KernelState;
import gov.nasa.jpf.jvm.StackFrame;
import gov.nasa.jpf.jvm.SystemState;
import gov.nasa.jpf.jvm.ThreadInfo;
import gov.nasa.jpf.jvm.Types;
import gov.nasa.jpf.jvm.bytecode.Instruction;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.RealComparison;
import de.unisb.cs.st.evosuite.symbolic.expr.RealConstant;

public class DCMPL extends gov.nasa.jpf.jvm.bytecode.DCMPL {
	
	static Logger log = JPF.getLogger("de.unisb.cs.st.evosuite.symbolic.bytecode.DCMPL");
	
	@SuppressWarnings("unchecked")
	@Override
	public Instruction execute(SystemState ss, KernelState ks, ThreadInfo th) {
		StackFrame sf = th.getTopFrame();
		Expression<Double> v0 = (Expression<Double>) sf.getOperandAttr(1);

		Expression<Double> v1 = (Expression<Double>) sf.getOperandAttr(3);
		if (v0 == null && v1 == null) {
			return super.execute(ss, ks, th);
		}

		if (v0 == null) {
			v0 = new RealConstant(Types.longToDouble(sf.longPeek(0)));
		}
		if (v1 == null) {
			v1 = new RealConstant(Types.longToDouble(sf.longPeek(1)));
		}

		Instruction ret = super.execute(ss, ks, th);
		long con = sf.peek();
		sf.setOperandAttr(new RealComparison(v1, v0, con));
		return ret;

	}
}
