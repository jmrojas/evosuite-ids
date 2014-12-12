package org.evosuite.symbolic.solver;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.evosuite.symbolic.TestCaseBuilder;
import org.evosuite.symbolic.expr.Constraint;
import org.evosuite.symbolic.solver.cvc4.CVC4Solver;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.VariableReference;

import com.examples.with.different.packagename.solver.TestCaseRegex;

public abstract class TestSolverRegex {

	private static DefaultTestCase buildTestConcat() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("aaaaaaaab");

		Method method = TestCaseRegex.class.getMethod("testConcat",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testConcat(Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestConcat();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildTestUnion() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("a");

		Method method = TestCaseRegex.class
				.getMethod("testUnion", String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	private static DefaultTestCase buildTestOptional()
			throws SecurityException, NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("a");

		Method method = TestCaseRegex.class.getMethod("testOptional",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testUnion(Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {

		DefaultTestCase tc = buildTestUnion();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	public static Map<String, Object> testOptional(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildTestOptional();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	public static Map<String, Object> testString(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildTestString();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildTestString() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("hello");

		Method method = TestCaseRegex.class.getMethod("testString",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testAnyChar(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildTestAnyChar();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildTestAnyChar() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("X");

		Method method = TestCaseRegex.class.getMethod("testAnyChar",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testEmpty(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildTestEmpty();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildTestEmpty() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("");

		Method method = TestCaseRegex.class
				.getMethod("testEmpty", String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testCross(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildTestCross();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildTestCross() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("a");

		Method method = TestCaseRegex.class
				.getMethod("testCross", String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testRepeatMin(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildRepeatMin();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildRepeatMin() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("aaa");

		Method method = TestCaseRegex.class.getMethod("testRepeatMin",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testRepeatMinMax(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildRepeatMinMax();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildRepeatMinMax()
			throws SecurityException, NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("aaaa");

		Method method = TestCaseRegex.class.getMethod("testRepeatMinMax",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}

	public static Map<String, Object> testRepeatN(CVC4Solver solver)
			throws SecurityException, NoSuchMethodException,
			ConstraintSolverTimeoutException {
		DefaultTestCase tc = buildRepeatN();
		Collection<Constraint<?>> constraints = DefaultTestCaseConcolicExecutor
				.execute(tc);
		Map<String, Object> solution = solver.solve(constraints);
		return solution;
	}

	private static DefaultTestCase buildRepeatN() throws SecurityException,
			NoSuchMethodException {
		TestCaseBuilder tc = new TestCaseBuilder();
		VariableReference string0 = tc.appendStringPrimitive("aaaaa");

		Method method = TestCaseRegex.class.getMethod("testRepeatN",
				String.class);
		tc.appendMethod(null, method, string0);
		return tc.getDefaultTestCase();
	}
}
