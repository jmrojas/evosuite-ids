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
package org.evosuite.symbolic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evosuite.symbolic.expr.BinaryExpression;
import org.evosuite.symbolic.expr.Constraint;
import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.UnaryExpression;
import org.evosuite.symbolic.expr.Variable;
import org.evosuite.symbolic.solver.ConstraintCache;
import org.evosuite.symbolic.solver.Solver;
import org.evosuite.symbolic.solver.SolverFactory;
import org.evosuite.testcase.BooleanPrimitiveStatement;
import org.evosuite.testcase.BytePrimitiveStatement;
import org.evosuite.testcase.CharPrimitiveStatement;
import org.evosuite.testcase.IntPrimitiveStatement;
import org.evosuite.testcase.LongPrimitiveStatement;
import org.evosuite.testcase.PrimitiveStatement;
import org.evosuite.testcase.ShortPrimitiveStatement;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * ConcolicMutation class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class ConcolicMutation {

	protected static final Logger logger = LoggerFactory
			.getLogger(ConcolicMutation.class);

	/**
	 * Generate new constraint and ask solver for solution
	 * 
	 * @param condition
	 *            a {@link org.evosuite.symbolic.BranchCondition} object.
	 * @param test
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @return a {@link org.evosuite.testcase.TestCase} object.
	 */
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	@SuppressWarnings("unchecked")
	public static TestCase negateCondition(BranchCondition condition,
			TestCase test) {
		List<Constraint<?>> constraints = new LinkedList<Constraint<?>>();
		constraints.addAll(condition.getReachingConstraints());
		// constraints.addAll(condition.localConstraints);
		Constraint<Long> c = (Constraint<Long>) condition.getLocalConstraint();
		Constraint<Long> targetConstraint = c.negate();
		constraints.add(targetConstraint);

		if (!targetConstraint.isSolveable()) {
			logger.info("Found unsolvable constraint: " + targetConstraint);
			// TODO: This is usually the case when the same variable is used for
			// several parameters of a method
			// Could we treat this as a special case?
			return null;
		}

		int size = constraints.size();
		if (size > 0) {
			constraints = reduce(constraints);
			// logger.info("Reduced constraints from " + size + " to " +
			// constraints.size());
			// logger.info("Now solving: " + constraints);
		}

		Solver solver = SolverFactory.getInstance().buildNewSolver();
		Map<String, Object> values = ConstraintCache.getInstance().solve(
				solver, constraints);

		if (values != null) {
			// logger.info(values.toString());
			TestCase newTest = test.clone();

			for (Object key : values.keySet()) {
				Object val = values.get(key);
				if (val != null) {
					if (val instanceof Long) {
						Long value = (Long) val;
						String name = ((String) key).replace("__SYM", "");
						logger.debug("New value for " + name + " is " + value);
						PrimitiveStatement<?> p = getStatement(newTest, name);
						assert (p != null);
						if (p instanceof BooleanPrimitiveStatement) {
							BooleanPrimitiveStatement bp = (BooleanPrimitiveStatement) p;
							bp.setValue(value.intValue() > 0);
						} else if (p instanceof CharPrimitiveStatement) {
							CharPrimitiveStatement cp = (CharPrimitiveStatement) p;
							cp.setValue((char) value.intValue());
						} else if (p instanceof BytePrimitiveStatement) {
							BytePrimitiveStatement bp = (BytePrimitiveStatement) p;
							bp.setValue((byte) value.intValue());
						} else if (p instanceof ShortPrimitiveStatement) {
							ShortPrimitiveStatement sp = (ShortPrimitiveStatement) p;
							sp.setValue((short) value.intValue());
						} else if (p instanceof LongPrimitiveStatement) {
							LongPrimitiveStatement lp = (LongPrimitiveStatement) p;
							lp.setValue(value);
						} else {
							assert (p instanceof IntPrimitiveStatement);
							IntPrimitiveStatement ip = (IntPrimitiveStatement) p;
							ip.setValue(value.intValue());
						}
					} else {
						logger.debug("New value is not long " + val);
					}
				} else {
					logger.debug("New value is null");

				}
			}
			return newTest;
		} else {
			logger.debug("Got null :-(");
			return null;
		}
	}

	/**
	 * Get the statement that defines this variable
	 * 
	 * @param test
	 * @param name
	 * @return
	 */
	private static PrimitiveStatement<?> getStatement(TestCase test, String name) {
		for (StatementInterface statement : test) {
			if (statement instanceof PrimitiveStatement<?>) {
				if (statement.getReturnValue().getName().equals(name))
					return (PrimitiveStatement<?>) statement;
			}
		}
		return null;
	}

	/**
	 * Apply cone of influence reduction to constraints with respect to the last
	 * constraint in the list
	 * 
	 * @param constraints
	 * @return
	 */
	private static List<Constraint<?>> reduce(List<Constraint<?>> constraints) {

		Constraint<?> target = constraints.get(constraints.size() - 1);
		Set<Variable<?>> dependencies = getVariables(target);

		LinkedList<Constraint<?>> coi = new LinkedList<Constraint<?>>();
		coi.add(target);

		for (int i = constraints.size() - 2; i >= 0; i--) {
			Constraint<?> constraint = constraints.get(i);
			Set<Variable<?>> variables = getVariables(constraint);
			for (Variable<?> var : dependencies) {
				if (variables.contains(var)) {
					dependencies.addAll(variables);
					coi.addFirst(constraint);
					break;
				}
			}
		}
		return coi;
	}

	/**
	 * Determine the set of variable referenced by this constraint
	 * 
	 * @param constraint
	 * @return
	 */
	private static Set<Variable<?>> getVariables(Constraint<?> constraint) {
		Set<Variable<?>> variables = new HashSet<Variable<?>>();
		getVariables(constraint.getLeftOperand(), variables);
		getVariables(constraint.getRightOperand(), variables);
		return variables;
	}

	/**
	 * Recursively determine constraints in expression
	 * 
	 * @param expr
	 * @param variables
	 */
	private static void getVariables(Expression<?> expr,
			Set<Variable<?>> variables) {
		if (expr instanceof Variable<?>) {
			variables.add((Variable<?>) expr);
		} else if (expr instanceof BinaryExpression<?>) {
			BinaryExpression<?> bin = (BinaryExpression<?>) expr;
			getVariables(bin.getLeftOperand(), variables);
			getVariables(bin.getRightOperand(), variables);
		} else if (expr instanceof UnaryExpression<?>) {
			UnaryExpression<?> un = (UnaryExpression<?>) expr;
			getVariables(un.getOperand(), variables);
		} else if (expr instanceof Constraint<?>) {
			// ignore

		}
	}
}
