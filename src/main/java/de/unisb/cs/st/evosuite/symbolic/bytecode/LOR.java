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
package de.unisb.cs.st.evosuite.symbolic.bytecode;

import gov.nasa.jpf.jvm.KernelState;
import gov.nasa.jpf.jvm.StackFrame;
import gov.nasa.jpf.jvm.SystemState;
import gov.nasa.jpf.jvm.ThreadInfo;
import gov.nasa.jpf.jvm.bytecode.Instruction;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerBinaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.Operator;

public class LOR extends gov.nasa.jpf.jvm.bytecode.LOR {
	@SuppressWarnings("unchecked")
	@Override
	public Instruction execute(SystemState ss, KernelState ks, ThreadInfo th) {
		StackFrame sf = th.getTopFrame();
		Expression<Long> v0 = (Expression<Long>) sf.getOperandAttr(1);

		Expression<Long> v1 = (Expression<Long>) sf.getOperandAttr(3);
		if (v0 == null && v1 == null) {
			return super.execute(ss, ks, th);
		}

		if (v0 == null) {
			v0 = new IntegerConstant(sf.longPeek(0));
		}
		if (v1 == null) {
			v1 = new IntegerConstant(sf.longPeek(1));
		}

		Instruction ret = super.execute(ss, ks, th);
		long con = sf.longPeek();
		sf.setLongOperandAttr(new IntegerBinaryExpression(v1, Operator.OR, v0, con));
		return ret;

	}
}
