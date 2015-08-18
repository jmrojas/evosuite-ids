package org.evosuite.symbolic.solver.cvc4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.evosuite.Properties;
import org.evosuite.symbolic.solver.SolverTimeoutException;
import org.evosuite.symbolic.solver.TestSolverSimpleMath;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCVC4SimpleMath {

	private static final String DEFAULT_CVC4_PATH = Properties.CVC4_PATH;

	@BeforeClass
	public static void configureCVC4Path() {
		String cvc4Path = System.getenv("cvc4_path");
		if (cvc4Path != null) {
			Properties.CVC4_PATH = cvc4Path;
		}
	}

	@AfterClass
	public static void restoreCVC4Path() {
		Properties.CVC4_PATH = DEFAULT_CVC4_PATH;
	}

	@Test
	public void testAdd() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testAdd(solver);
	}

	@Test
	public void testSub() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testSub(solver);
	}

	@Test
	public void testMul() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testMul(solver);
	}

	@Test
	public void testDiv() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		Map<String, Object> solution = TestSolverSimpleMath.testDiv(solver);
		
		assertNotNull(solution);
		Long var0 = (Long) solution.get("var0");
		Long var1 = (Long) solution.get("var1");

		assertEquals(var0.intValue(), var1.intValue() / 5);

	}

	@Test
	public void testEq() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {
		
		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testEq(solver);
	}

	@Test
	public void testNeq() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testNeq(solver);
	}

	@Test
	public void testLt() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testLt(solver);
	}

	@Test
	public void testGt() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testGt(solver);
	}

	@Test
	public void testLte() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testLte(solver);
	}

	@Test
	public void testGte() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testGte(solver);
	}

	@Test
	public void testMod() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testMod(solver);
	}

	@Test
	public void testMul2() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testMul2(solver);
	}

	@Test
	public void testCastRealToInt() throws SecurityException,
			NoSuchMethodException, SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testCastRealToInt(solver);
	}

	@Test
	public void testCastIntToReal() throws SecurityException,
			NoSuchMethodException, SolverTimeoutException {

		if (Properties.CVC4_PATH == null) {
			System.out
					.println("Warning: cvc4_path should be configured to execute this test case");
			return;
		}

		CVC4Solver solver = new CVC4Solver();
		TestSolverSimpleMath.testCastIntToReal(solver);
	}
}
