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
package org.evosuite.symbolic.expr.token;

import gnu.trove.set.hash.THashSet;

import java.util.Set;
import java.util.StringTokenizer;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.evosuite.symbolic.DSEStats;
import org.evosuite.symbolic.expr.AbstractExpression;
import org.evosuite.symbolic.expr.Variable;
import org.evosuite.symbolic.expr.str.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * StringBinaryExpression class.
 * </p>
 * 
 * @author krusev
 */
public final class StringNextTokenExpr extends AbstractExpression<String> implements
        StringValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4594444543728364120L;

	protected static Logger log = LoggerFactory.getLogger(StringNextTokenExpr.class);

	private final TokenizerExpr tokenizerExpr;

	public StringNextTokenExpr(TokenizerExpr tokenizerExpr, String con) {
		super(con, 1 + tokenizerExpr.getSize(), tokenizerExpr.containsSymbolicVariable());
		this.tokenizerExpr = tokenizerExpr;

		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH) {
			DSEStats.reportConstraintTooLong(getSize());
			throw new ConstraintTooLongException(getSize());
		}
	}

	public TokenizerExpr getTokenizerExpr() {
		return tokenizerExpr;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "nextToken(" + tokenizerExpr.toString() + ")";
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof StringNextTokenExpr) {
			StringNextTokenExpr other = (StringNextTokenExpr) obj;
			return this.tokenizerExpr.equals(other.tokenizerExpr);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.tokenizerExpr.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String execute() {
		StringTokenizer tokenizer = tokenizerExpr.execute();
		return tokenizer.nextToken();

	}

	@Override
	public Set<Variable<?>> getVariables() {
		Set<Variable<?>> variables = new THashSet<Variable<?>>();
		variables.addAll(this.tokenizerExpr.getVariables());
		return variables;
	}

	@Override
	public Set<Object> getConstants() {
		return tokenizerExpr.getConstants();
	}

}
