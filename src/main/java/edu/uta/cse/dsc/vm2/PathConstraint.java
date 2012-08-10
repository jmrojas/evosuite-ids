package edu.uta.cse.dsc.vm2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.evosuite.symbolic.BranchCondition;
import org.evosuite.symbolic.expr.Comparator;
import org.evosuite.symbolic.expr.Constraint;
import org.evosuite.symbolic.expr.IntegerConstant;
import org.evosuite.symbolic.expr.IntegerConstraint;
import org.evosuite.symbolic.expr.IntegerExpression;
import org.evosuite.symbolic.expr.StringComparison;
import org.evosuite.symbolic.expr.StringToIntCast;

public final class PathConstraint {

	private Stack<BranchCondition> branchConditions = new Stack<BranchCondition>();

	private LinkedList<Constraint<?>> currentLocalConstraints = new LinkedList<Constraint<?>>();
	private LinkedList<Constraint<?>> reachingConstraints = new LinkedList<Constraint<?>>();

	public void pushLocalConstraint(IntegerConstraint c) {

		IntegerExpression left_integer_expression = (IntegerExpression) c.getLeftOperand();
		IntegerExpression right_integer_expression = (IntegerExpression) c
				.getRightOperand();
		Comparator comp = c.getComparator();

		if (isStringConstraint(left_integer_expression, comp,
				right_integer_expression)) {

			c = createNormalizedIntegerConstraint(left_integer_expression,
					comp, right_integer_expression);
		} else

		if (isStringConstraint(right_integer_expression, comp,
				left_integer_expression)) {
			c = createNormalizedIntegerConstraint(right_integer_expression,
					comp, left_integer_expression);

		}
		currentLocalConstraints.add(c);
	}

	public void pushBranchCondition(String className, String methName,
			int branchIndex, IntegerConstraint ending_constraint) {
		this.pushLocalConstraint(ending_constraint);

		HashSet<Constraint<?>> branch_reaching_constraints = new HashSet<Constraint<?>>(
				reachingConstraints);
		LinkedList<Constraint<?>> branch_local_constraints = new LinkedList<Constraint<?>>(
				currentLocalConstraints);

		BranchCondition new_branch = new BranchCondition(methName, methName,
				branchIndex, branch_reaching_constraints,
				branch_local_constraints);

		branchConditions.push(new_branch);

		reachingConstraints.addAll(currentLocalConstraints);
		currentLocalConstraints.clear();
	}

	public List<BranchCondition> getBranchConditions() {
		return new LinkedList<BranchCondition>(branchConditions);
	}

	private IntegerConstraint createNormalizedIntegerConstraint(
			IntegerExpression left, Comparator comp, IntegerExpression right) {
		IntegerConstant integerConstant = (IntegerConstant) right;
		StringComparison stringComparison = (StringComparison) ((StringToIntCast) left)
				.getParam();

		IntegerConstraint c = new IntegerConstraint(stringComparison, comp,
				integerConstant);
		return c;

	}

	private static boolean isStringConstraint(IntegerExpression left,
			Comparator comp, IntegerExpression right) {

		return ((comp.equals(Comparator.NE) || comp.equals(Comparator.EQ))
				&& (right instanceof IntegerConstant)
				&& (left instanceof StringToIntCast) && ((StringToIntCast) left)
					.getParam() instanceof StringComparison);

	}

}
