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

import gov.nasa.jpf.jvm.ThreadInfo;
import de.unisb.cs.st.evosuite.symbolic.expr.Comparator;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstraint;

public class IFGE extends gov.nasa.jpf.jvm.bytecode.IFGE {
	public IFGE(int targetPc) {
		super(targetPc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean popConditionValue(ThreadInfo ti) {
		Expression<Long> sym = (Expression<Long>) ti.getOperandAttr();
		if (sym == null) {
			return super.popConditionValue(ti);
		}
		int pop = ti.pop();
		if (pop >= 0) {
			PathConstraint.getInstance().addConstraint(new IntegerConstraint(sym,
			                                                   Comparator.GE,
			                                                   new IntegerConstant(0)));
			return true;
		} else {
			PathConstraint.getInstance().addConstraint(new IntegerConstraint(sym,
			                                                   Comparator.LT,
			                                                   new IntegerConstant(0)));
			return false;
		}
	}

}
