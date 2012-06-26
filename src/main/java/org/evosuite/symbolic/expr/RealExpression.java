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
package org.evosuite.symbolic.expr;

public abstract class RealExpression implements Expression<Double> {

	private static final long serialVersionUID = 6773549380102315977L;

	protected int hash = 0;

	
	private Expression<?> parent = null;
	
	public Expression<?> getParent() {
		return this.parent;
	}
	
	public void setParent(Expression<?> expr) {
		this.parent = expr;
	}

	
	@Override
	public int hashCode() {
		if (hash == 0) {
			hash = this.getConcreteValue().hashCode();
		}
		return hash;
	}
}
