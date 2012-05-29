/**
 * Copyright (C) 2012 Gordon Fraser, Andrea Arcuri
 *
 * This file is part of EvoSuite.
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
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package de.unisb.cs.st.evosuite.symbolic.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.ga.DSEBudget;
import de.unisb.cs.st.evosuite.symbolic.Solver;
import de.unisb.cs.st.evosuite.symbolic.expr.BinaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.Cast;
import de.unisb.cs.st.evosuite.symbolic.expr.Constraint;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerVariable;
import de.unisb.cs.st.evosuite.symbolic.expr.RealConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.RealVariable;
import de.unisb.cs.st.evosuite.symbolic.expr.StringBuilderExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.StringComparison;
import de.unisb.cs.st.evosuite.symbolic.expr.StringConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.StringMultipleComparison;
import de.unisb.cs.st.evosuite.symbolic.expr.StringVariable;
import de.unisb.cs.st.evosuite.symbolic.expr.UnaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.Variable;
import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * @author krusev
 * 
 */
public class Seeker implements Solver {

	static Logger log = LoggerFactory.getLogger(Seeker.class);

	//static Logger log = JPF.getLogger("de.unisb.cs.st.evosuite.symbolic.search.Seeker");

	/* The idea here is to get the expressions and build the constraint 
	 * dynamically here using Java reflection. This should save us some time
	 * since we wan't do the evaluation of the constraints in JPF 
	 * 
	 * In getModel we need to build the constraints and search for better input 
	 * values for the String variables. 
	 */
	@Override
	public Map<String, Object> getModel(Collection<Constraint<?>> constr) {

		if (constr.size() <= 0)
			return null;

		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Constraint<?>> constraints = (List<Constraint<?>>) constr;

		Set<Variable<?>> vars = getVarsOfSet(constraints);

		boolean searchSuccsess = false;
		//		log.warning("Variables: " + vars.size());

		double distance = DistanceEstimator.getDistance(constraints);
		if (distance == 0.0) {
			log.info("Initial distance already is 0.0, skipping search");
			return null;
		}

		resetLoop: for (int i = 0; i <= Properties.DSE_VARIABLE_RESETS; i++) {
			boolean done = false;
			//TODO since this here is also in a loop maybe we can change 
			//this back to how it was and increase the DSE_VARIABLE_RESETS
			while (!done) {
				done = true;
				for (Variable<?> var : vars) {

					log.debug("Variable: " + var);
					Changer changer = new Changer();

					if (var instanceof StringVariable) {
						log.debug("searching for string");
						StringVariable strVar = (StringVariable) var;
						if (changer.strLocalSearch(strVar, constraints, result)) {
							searchSuccsess = true;
							done = false;
							//break;
						}
					}
					if (var instanceof IntegerVariable) {
						log.debug("searching for int" + var);
						IntegerVariable intVar = (IntegerVariable) var;
						if (changer.intLocalSearch(intVar, constraints, result)) {
							searchSuccsess = true;
							done = false;
							//break;
						}
					}
					if (var instanceof RealVariable) {
						log.debug("searching for real");
						RealVariable realVar = (RealVariable) var;
						if (changer.realLocalSearch(realVar, constraints, result)) {
							searchSuccsess = true;
							done = false;
							//break;
						}
					}
				}

				if (DistanceEstimator.getDistance(constraints) <= 0) {
					return result;
				}

				if (DSEBudget.isFinished()) {
					log.debug("Out of time");
					break resetLoop;
				}

			}

			if (i != Properties.DSE_VARIABLE_RESETS) {
				randomizeVars(vars);
			}

		}
		// This will return any improvement, even if it does not cover a new branch
		if (searchSuccsess)
			return result;
		else
			return null;
	}

	private void randomizeVars(Set<Variable<?>> vars) {
		for (Variable<?> var : vars) {
			if (var instanceof IntegerVariable) {
				IntegerVariable intV = (IntegerVariable) var;
				int max = (int) Math.min(Integer.MAX_VALUE, intV.getMaxValue());
				if (Randomness.nextBoolean())
					intV.setConcreteValue((long) Randomness.nextInt(max));
				else {
					intV.setConcreteValue((long) (-1 * Randomness.nextInt(max)));
				}
			} else if (var instanceof RealVariable) {
				RealVariable realV = (RealVariable) var;
				int max = (int) Math.min(Integer.MAX_VALUE, realV.getMaxValue());
				if (Randomness.nextBoolean())
					realV.setConcreteValue(Randomness.nextInt(max)
					        + Randomness.nextFloat());
				else
					realV.setConcreteValue(-1 * Randomness.nextInt(max)
					        + Randomness.nextFloat());
			} else if (var instanceof StringVariable) {
				StringVariable stringV = (StringVariable) var;
				stringV.setConcreteValue(Randomness.nextString(Randomness.nextInt(Properties.STRING_LENGTH)));
			}
			log.info("Reseted var: " + var);
		}
	}

	@SuppressWarnings("unused")
	private void setupTree(Expression<?> expr) {
		if (expr instanceof Variable<?>) {
			//done
		} else if (expr instanceof StringMultipleComparison) {
			StringMultipleComparison smc = (StringMultipleComparison) expr;
			smc.getLeftOperand().setParent(expr);
			setupTree(smc.getLeftOperand());
			smc.getRightOperand().setParent(expr);
			setupTree(smc.getRightOperand());

			ArrayList<Expression<?>> ar_l_ex = smc.getOther();
			Iterator<Expression<?>> itr = ar_l_ex.iterator();
			while (itr.hasNext()) {
				Expression<?> element = itr.next();
				element.setParent(expr);
				setupTree(element);
			}
		} else if (expr instanceof StringComparison) {
			StringComparison sc = (StringComparison) expr;
			sc.getLeftOperand().setParent(expr);
			setupTree(sc.getLeftOperand());
			sc.getRightOperand().setParent(expr);
			setupTree(sc.getRightOperand());

		} else if (expr instanceof BinaryExpression<?>) {
			BinaryExpression<?> bin = (BinaryExpression<?>) expr;
			bin.getLeftOperand().setParent(expr);
			setupTree(bin.getLeftOperand());
			bin.getRightOperand().setParent(expr);
			setupTree(bin.getRightOperand());

		} else if (expr instanceof UnaryExpression<?>) {
			UnaryExpression<?> un = (UnaryExpression<?>) expr;
			un.getOperand().setParent(expr);
			setupTree(un.getOperand());

		} else if (expr instanceof Constraint<?>) {
			// ignore

		}
	}

	@Override
	public boolean solve(Collection<Constraint<?>> constraints) {
		return false;
	}

	private Set<Variable<?>> getVarsOfSet(List<Constraint<?>> constraints) {
		Set<Variable<?>> variables = new HashSet<Variable<?>>();

		for (Constraint<?> cnstr : constraints) {
			getVariables(cnstr.getLeftOperand(), variables);
			getVariables(cnstr.getRightOperand(), variables);
		}
		return variables;
	}

	/**
	 * Determine the set of variable referenced by this constraint
	 * 
	 * @param constraint
	 * @return
	 */
	@SuppressWarnings("unused")
	private Set<Variable<?>> getVarsOfTarget(List<Constraint<?>> constraint) {
		Set<Variable<?>> variables = new HashSet<Variable<?>>();

		Constraint<?> target = constraint.get(constraint.size() - 1);

		getVariables(target.getLeftOperand(), variables);
		getVariables(target.getRightOperand(), variables);
		return variables;
	}

	/**
	 * Recursively determine constraints in expression
	 * 
	 * @param expr
	 * @param variables
	 */
	private void getVariables(Expression<?> expr, Set<Variable<?>> variables) {
		if (expr instanceof Variable<?>) {
			variables.add((Variable<?>) expr);
		} else if (expr instanceof StringMultipleComparison) {
			StringMultipleComparison smc = (StringMultipleComparison) expr;
			getVariables(smc.getLeftOperand(), variables);
			getVariables(smc.getRightOperand(), variables);
			ArrayList<Expression<?>> ar_l_ex = smc.getOther();
			Iterator<Expression<?>> itr = ar_l_ex.iterator();
			while (itr.hasNext()) {
				Expression<?> element = itr.next();
				getVariables(element, variables);
			}
		} else if (expr instanceof StringBuilderExpression) {
			StringBuilderExpression sB = (StringBuilderExpression) expr;
			getVariables(sB.getExpr(), variables);
		} else if (expr instanceof StringComparison) {
			StringComparison sc = (StringComparison) expr;
			getVariables(sc.getLeftOperand(), variables);
			getVariables(sc.getRightOperand(), variables);
		} else if (expr instanceof BinaryExpression<?>) {
			BinaryExpression<?> bin = (BinaryExpression<?>) expr;
			getVariables(bin.getLeftOperand(), variables);
			getVariables(bin.getRightOperand(), variables);
		} else if (expr instanceof UnaryExpression<?>) {
			UnaryExpression<?> un = (UnaryExpression<?>) expr;
			getVariables(un.getOperand(), variables);
		} else if (expr instanceof Cast<?>) {
			Cast<?> cst = (Cast<?>) expr;
			getVariables(cst.getConcreteObject(), variables);
		} else if (expr instanceof Constraint<?>) {
			// ignore
		} else if (expr instanceof IntegerConstant || expr instanceof StringConstant
		        || expr instanceof RealConstant) {
			// ignore

		} else {
			log.warn("Seeker: we schouldn't be here" + expr);
			System.exit(0);
		}
	}

}
