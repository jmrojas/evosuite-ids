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
package de.unisb.cs.st.evosuite.graphs.cfg;

import java.io.Serializable;

import de.unisb.cs.st.evosuite.coverage.branch.Branch;

public class ControlDependency implements Serializable {

	private static final long serialVersionUID = 6288839964561655730L;

	private final Branch branch;
	private final boolean branchExpressionValue;

	public ControlDependency(Branch branch, boolean branchExpressionValue) {
		if (branch == null)
			throw new IllegalArgumentException(
			        "control dependencies for the root branch are not permitted (null)");

		this.branch = branch;
		this.branchExpressionValue = branchExpressionValue;
	}

	public Branch getBranch() {
		return branch;
	}

	public boolean getBranchExpressionValue() {
		return branchExpressionValue;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
//		result = prime * result + (branchExpressionValue ? 1231 : 1237);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ControlDependency other = (ControlDependency) obj;
//		if (branch == null) {
//			if (other.branch != null)
//				return false;
//		} else if (!branch.equals(other.branch))
//			return false;
//		if (branchExpressionValue != other.branchExpressionValue)
//			return false;
//		return true;
//	}

	@Override
	public String toString() {

		String r = "CD " + branch.toString();

		if (!branch.isSwitchCaseBranch()) {
			if (branchExpressionValue)
				r += " - TRUE";
			else
				r += " - FALSE";
		}

		return r;
	}
}
