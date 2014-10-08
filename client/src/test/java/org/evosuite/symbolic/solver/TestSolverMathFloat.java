package org.evosuite.symbolic.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.evosuite.symbolic.TestCaseBuilder;
import org.evosuite.symbolic.expr.Constraint;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.VariableReference;

import com.examples.with.different.packagename.solver.TestCaseFloatAbs;
import com.examples.with.different.packagename.solver.TestCaseFloatMax;
import com.examples.with.different.packagename.solver.TestCaseFloatMin;
import com.examples.with.different.packagename.solver.TestCaseFloatRound;
import com.examples.with.different.packagename.solver.TestCaseFloatTrigonometry;

public class TestSolverMathFloat {

	private static DefaultTestCase buildTestCaseFloatAbs()
			throws SecurityException, NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference double0 = tc.appendDoublePrimitive(-Math.PI);

		Method method = TestCaseFloatAbs.class.getMethod("test", double.class);
		tc.appendMethod(null, method, double0);
		return tc.getDefaultTestCase();
	}

	public static void testAbs(Solver solver) throws SecurityException,
			NoSuchMethodException, ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestCaseFloatAbs();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		assertNotNull(solution);
		Double var0 = (Double) solution.get("var0");

		assertTrue(Math.abs(var0.doubleValue()) > 0);
	}

	private static DefaultTestCase buildTestCaseFloatTrigonometry()
			throws SecurityException, NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference double0 = tc.appendDoublePrimitive(-Math.PI);

		Method method = TestCaseFloatTrigonometry.class.getMethod("test",
				double.class);
		tc.appendMethod(null, method, double0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testTrigonometry(Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestCaseFloatTrigonometry();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildTestCaseMax() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference double0 = tc.appendDoublePrimitive(Double.MIN_VALUE);
		VariableReference double1 = tc.appendDoublePrimitive(10);

		Method method = TestCaseFloatMax.class.getMethod("test", double.class,
				double.class);
		tc.appendMethod(null, method, double0, double1);
		return tc.getDefaultTestCase();
	}

	private static DefaultTestCase buildTestCaseMin() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference double0 = tc.appendDoublePrimitive(Double.MAX_VALUE);
		VariableReference double1 = tc.appendDoublePrimitive(10);

		Method method = TestCaseFloatMin.class.getMethod("test", double.class,
				double.class);
		tc.appendMethod(null, method, double0, double1);
		return tc.getDefaultTestCase();
	}

	public static void testMax(Solver solver) throws SecurityException,
			NoSuchMethodException, ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestCaseMax();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		assertNotNull(solution);
		Double var0 = (Double) solution.get("var0");
		Double var1 = (Double) solution.get("var1");

		assertEquals(10, Math.max(var0.doubleValue(), var1.doubleValue()),
				DELTA);
	}

	public static void testMin(Solver solver) throws SecurityException,
			NoSuchMethodException, ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestCaseMin();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		assertNotNull(solution);
		Double var0 = (Double) solution.get("var0");
		Double var1 = (Double) solution.get("var1");

		assertEquals(10, Math.min(var0.doubleValue(), var1.doubleValue()),
				DELTA);
	}

	private static final double DELTA = 1e-15;

	private static DefaultTestCase buildTestCaseRound()
			throws SecurityException, NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference double0 = tc.appendDoublePrimitive(Math.PI);
		VariableReference int1 = tc.appendIntPrimitive((int) Math
				.round(Math.PI));

		Method method = TestCaseFloatRound.class.getMethod("test",
				double.class, int.class);
		tc.appendMethod(null, method, double0, int1);
		return tc.getDefaultTestCase();
	}

	public static void testRound(Solver solver) throws SecurityException,
			NoSuchMethodException, ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestCaseRound();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		assertNotNull(solution);
		Double var0 = (Double) solution.get("var0");
		Long var1 = (Long) solution.get("var1");

		assertEquals(Math.round(var0.doubleValue()), var1.intValue());
	}
}
