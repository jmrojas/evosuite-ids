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
package de.unisb.cs.st.evosuite.graphs.ccfg;

public class CCFGMethodEntryNode extends CCFGNode {

	private String method;
	private CCFGCodeNode entryInstruction;
	
	public CCFGMethodEntryNode(String method, CCFGCodeNode entryInstruction) {
		this.method = method;
		this.entryInstruction = entryInstruction;
	}
	
	public String getMethod() {
		return method;
	}

	public CCFGCodeNode getEntryInstruction() {
		return entryInstruction;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((entryInstruction == null) ? 0 : entryInstruction.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CCFGMethodEntryNode other = (CCFGMethodEntryNode) obj;
		if (entryInstruction == null) {
			if (other.entryInstruction != null)
				return false;
		} else if (!entryInstruction.equals(other.entryInstruction))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Entry: "+method;
	}
}
