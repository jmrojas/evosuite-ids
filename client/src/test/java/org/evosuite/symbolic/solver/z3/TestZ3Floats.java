package org.evosuite.symbolic.solver.z3;

import org.evosuite.Properties;
import org.evosuite.symbolic.solver.SolverTimeoutException;
import org.evosuite.symbolic.solver.TestSolverFloats;
import org.evosuite.symbolic.solver.z3.Z3Solver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestZ3Floats {

	private static final String DEFAULT_Z3_PATH = Properties.Z3_PATH;

	@BeforeClass
	public static void configureZ3Path() {
		String z3StrPath = System.getenv("z3_path");
		if (z3StrPath != null) {
			Properties.Z3_PATH = z3StrPath;
		}
	}

	@AfterClass
	public static void restoreZ3Path() {
		Properties.Z3_PATH = DEFAULT_Z3_PATH;
	}

	@Test
	public void testFloatEq() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testEq(solver);
	}

	@Test
	public void testFloatNeq() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testNeq(solver);
	}

	@Test
	public void testFloatLt() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {
		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testLt(solver);
	}

	@Test
	public void testFloatGt() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testGt(solver);
	}

	@Test
	public void testFloatLte() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {
		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testLte(solver);
	}

	@Test
	public void testFloatGte() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testGte(solver);
	}

	@Test
	public void testFloatFraction() throws SecurityException,
			NoSuchMethodException, SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testFraction(solver);
	}

	@Test
	public void testFloatAdd() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {
		
		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testAdd(solver);
	}

	@Test
	public void testFloatSub() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {
		
		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testSub(solver);
	}

	@Test
	public void testFloatMul() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testMul(solver);
	}

	@Test
	public void testFloatDiv() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {
		
		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testDiv(solver);
	}

	@Test
	public void testFloatMod() throws SecurityException, NoSuchMethodException,
			SolverTimeoutException {

		if (Properties.Z3_PATH == null) {
			System.out
					.println("Warning: z3_path should be configured to execute this test case");
			return;
		}

		Z3Solver solver = new Z3Solver();
		TestSolverFloats.testMod(solver);
	}
}
