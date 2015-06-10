package org.evosuite.symbolic.solver;

import org.evosuite.Properties;
import org.evosuite.symbolic.solver.cvc4.CVC4Solver;
import org.evosuite.symbolic.solver.search.EvoSuiteSolver;
import org.evosuite.symbolic.solver.z3.Z3Solver;
import org.evosuite.symbolic.solver.z3str2.Z3Str2Solver;

public class SolverFactory {

	private static final SolverFactory instance = new SolverFactory();

	public static SolverFactory getInstance() {
		return instance;
	}

	public Solver buildNewSolver() {
		switch (Properties.DSE_SOLVER) {
		case Z3_SOLVER:
			return new Z3Solver();
		case Z3_STR2_SOLVER:
			return new Z3Str2Solver();
		case CVC4_SOLVER:
			return new CVC4Solver();
		case EVOSUITE_SOLVER:
		default:
			return new EvoSuiteSolver();
		}
	}

}
