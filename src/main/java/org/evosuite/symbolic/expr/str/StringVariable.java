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
/**
 * 
 */
package org.evosuite.symbolic.expr.str;

import gnu.trove.set.hash.THashSet;

import java.util.Set;

import org.evosuite.symbolic.expr.AbstractExpression;
import org.evosuite.symbolic.expr.Variable;

/**
 * <p>
 * StringVariable class.
 * </p>
 * 
 * @author krusev
 */
public final class StringVariable extends AbstractExpression<String> implements
        StringValue, Variable<String> {

	private static final long serialVersionUID = 5925030390824261492L;

	private final String name;

	private String maxValue;

	/**
	 * <p>
	 * Constructor for StringVariable.
	 * </p>
	 * 
	 * @param name
	 *            a {@link java.lang.String} object.
	 * @param concVal
	 *            a {@link java.lang.String} object.
	 * @param minValue
	 *            a {@link java.lang.String} object.
	 * @param maxValue
	 *            a {@link java.lang.String} object.
	 */
	public StringVariable(String name, String concVal) {
		super(concVal, 1, true);
		this.name = name;
		this.maxValue = concVal;
	}

	/**
	 * <p>
	 * setConcreteValue
	 * </p>
	 * 
	 * @param concValue
	 *            the concValue to set
	 */
	public void setConcreteValue(String concValue) {
		this.concreteValue = concValue;
	}

	/*
	 * store the better value here
	 */
	/** {@inheritDoc} */
	@Override
	public String getMaxValue() {
		return maxValue;
	}

	/**
	 * <p>
	 * Setter for the field <code>maxValue</code>.
	 * </p>
	 * 
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name + "(" + concreteValue + ")";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof StringVariable) {
			StringVariable v = (StringVariable) obj;
			return this.getName().equals(v.getName());
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String execute() {
		return concreteValue;

	}

	@Override
	public Set<Variable<?>> getVariables() {
		Set<Variable<?>> variables = new THashSet<Variable<?>>();
		variables.add(this);
		return variables;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.symbolic.expr.Variable#getMinValue()
	 */
	@Override
	public String getMinValue() {
		return concreteValue;
	}

}
