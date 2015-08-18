package org.evosuite.symbolic.solver.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.evosuite.Properties;
import org.evosuite.Properties.LocalSearchBudgetType;
import org.evosuite.symbolic.expr.Comparator;
import org.evosuite.symbolic.expr.Constraint;
import org.evosuite.symbolic.expr.RealConstraint;
import org.evosuite.symbolic.expr.fp.RealConstant;
import org.evosuite.symbolic.expr.fp.RealVariable;
import org.evosuite.symbolic.solver.SolverEmptyQueryException;
import org.evosuite.symbolic.solver.SolverResult;
import org.evosuite.symbolic.solver.SolverTimeoutException;
import org.junit.Test;

public class TestRealConstraint {

	private static final double INIT_DOUBLE = 0;
	private static final double EXPECTED_DOUBLE = Math.PI;

	private static Collection<Constraint<?>> buildConstraintSystem() {

		RealVariable var0 = new RealVariable("var0", INIT_DOUBLE, Double.MIN_VALUE, Double.MAX_VALUE);

		RealConstant constPi = new RealConstant(Math.PI);

		RealConstraint constr1 = new RealConstraint(var0, Comparator.EQ, constPi);

		return Arrays.<Constraint<?>> asList(constr1);
	}

	@Test
	public void test() throws SolverEmptyQueryException {
		Properties.LOCAL_SEARCH_BUDGET = 100; // 5000000000000L; TODO - ??
		Properties.LOCAL_SEARCH_BUDGET_TYPE = LocalSearchBudgetType.FITNESS_EVALUATIONS;

		Collection<Constraint<?>> constraints = buildConstraintSystem();

		System.out.println("Constraints:");
		for (Constraint<?> c : constraints) {
			System.out.println(c.toString());
		}

		System.out.println("");
		System.out.println("Initial: " + String.valueOf(INIT_DOUBLE));

		EvoSuiteSolver seeker = new EvoSuiteSolver();
		try {
			SolverResult result = seeker.solve(constraints);
			if (result.isUNSAT()) {
				fail("search was unsuccessfull");
			} else {
				Map<String, Object> model = result.getModel();

				Object var0 = model.get("var0");
				System.out.println("Expected: " + EXPECTED_DOUBLE);
				System.out.println("Found: " + var0);

				assertEquals(EXPECTED_DOUBLE, var0);
			}
		} catch (SolverTimeoutException e) {
			fail();
		}

	}
}
