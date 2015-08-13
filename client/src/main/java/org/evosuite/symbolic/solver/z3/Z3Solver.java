package org.evosuite.symbolic.solver.z3;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.evosuite.Properties;
import org.evosuite.symbolic.expr.Constraint;
import org.evosuite.symbolic.expr.Variable;
import org.evosuite.symbolic.expr.bv.IntegerVariable;
import org.evosuite.symbolic.expr.fp.RealVariable;
import org.evosuite.symbolic.expr.str.StringVariable;
import org.evosuite.symbolic.solver.SmtExprBuilder;
import org.evosuite.symbolic.solver.Solver;
import org.evosuite.symbolic.solver.SolverErrorException;
import org.evosuite.symbolic.solver.SolverParseException;
import org.evosuite.symbolic.solver.SolverResult;
import org.evosuite.symbolic.solver.SolverTimeoutException;
import org.evosuite.symbolic.solver.SolverEmptyQueryException;
import org.evosuite.symbolic.solver.smt.SmtAssertion;
import org.evosuite.symbolic.solver.smt.SmtCheckSatQuery;
import org.evosuite.symbolic.solver.smt.SmtConstantDeclaration;
import org.evosuite.symbolic.solver.smt.SmtExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Z3Solver extends Solver {

	public Z3Solver() {
		super();
	}

	public Z3Solver(boolean addMissingVariables) {
		super(addMissingVariables);
	}

	static Logger logger = LoggerFactory.getLogger(Z3Solver.class);

	@Override
	public SolverResult solve(Collection<Constraint<?>> constraints) throws SolverTimeoutException, IOException,
			SolverParseException, SolverEmptyQueryException, SolverErrorException {

		long timeout = Properties.DSE_CONSTRAINT_SOLVER_TIMEOUT_MILLIS;

		Set<Variable<?>> variables = new HashSet<Variable<?>>();
		for (Constraint<?> c : constraints) {
			Set<Variable<?>> c_variables = c.getVariables();
			variables.addAll(c_variables);
		}

		SmtCheckSatQuery smtCheckSatQuery = buildSmtQuery(constraints, variables);

		if (smtCheckSatQuery.getConstantDeclarations().isEmpty()) {
			logger.debug("Z3 SMT query has no variables");
			throw new SolverEmptyQueryException("Z3 SMT query has no variables");
		}

		Z3QueryPrinter printer = new Z3QueryPrinter();
		String smtQueryStr = printer.print(smtCheckSatQuery, timeout);

		logger.debug("Z3 Query:");
		logger.debug(smtQueryStr);

		if (Properties.Z3_PATH == null) {
			String errMsg = "Property Z3_PATH should be setted in order to use the Z3 Solver!";
			logger.error(errMsg);
			throw new IllegalStateException(errMsg);
		}
		String z3Cmd = Properties.Z3_PATH + " -smt2 -in";

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		launchNewProcess(z3Cmd, smtQueryStr, (int) timeout, stdout);

		String z3ResultStr = stdout.toString("UTF-8");

		Map<String, Object> initialValues = getConcreteValues(variables);
		Z3ResultParser resultParser;
		if (this.addMissingVariables()) {
			resultParser = new Z3ResultParser(initialValues);
		} else {
			resultParser = new Z3ResultParser();
		}

		SolverResult result = resultParser.parseResult(z3ResultStr);

		return result;
	}

	private static SmtCheckSatQuery buildSmtQuery(Collection<Constraint<?>> constraints, Set<Variable<?>> variables) {
		List<SmtConstantDeclaration> constantDeclarations = new LinkedList<SmtConstantDeclaration>();
		for (Variable<?> v : variables) {
			String varName = v.getName();
			if (v instanceof IntegerVariable) {
				SmtConstantDeclaration intVar = SmtExprBuilder.mkIntConstantDeclaration(varName);
				constantDeclarations.add(intVar);
			} else if (v instanceof RealVariable) {
				SmtConstantDeclaration realVar = SmtExprBuilder.mkRealConstantDeclaration(varName);
				constantDeclarations.add(realVar);

			} else if (v instanceof StringVariable) {
				// ignore string variables
			} else {
				throw new RuntimeException("Unknown variable type " + v.getClass().getCanonicalName());
			}
		}

		List<SmtAssertion> assertions = new LinkedList<SmtAssertion>();
		for (Constraint<?> c : constraints) {
			ConstraintToZ3Visitor v = new ConstraintToZ3Visitor();
			SmtExpr bool_expr = c.accept(v, null);
			if (bool_expr != null && bool_expr.isSymbolic()) {
				SmtAssertion newAssertion = new SmtAssertion(bool_expr);
				assertions.add(newAssertion);
			}
		}

		SmtCheckSatQuery smtCheckSatQuery = new SmtCheckSatQuery(constantDeclarations, assertions);
		return smtCheckSatQuery;
	}

	private static final class TimeoutTask extends TimerTask {
		private final Process process;

		private TimeoutTask(Process process) {
			this.process = process;
		}

		@Override
		public void run() {
			process.destroy();
		}
	}

	private static int launchNewProcess(String z3Cmd, String smtQuery, int timeout, OutputStream outputStream)
			throws IOException {

		final Process process = Runtime.getRuntime().exec(z3Cmd);

		InputStream stdout = process.getInputStream();
		InputStream stderr = process.getErrorStream();
		OutputStream stdin = process.getOutputStream();

		stdin.write(smtQuery.getBytes());
		stdin.flush();
		stdin.close();

		logger.debug("Process output:");

		Timer t = new Timer();
		t.schedule(new TimeoutTask(process), timeout);

		do {
			readInputStream(stdout, outputStream);
			readInputStream(stderr, null);
		} while (!isFinished(process));

		int exitValue = process.exitValue();
		return exitValue;
	}

	private static void readInputStream(InputStream in, OutputStream out) throws IOException {
		InputStreamReader is = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();
		while (read != null) {
			logger.debug(read);
			if (out != null) {
				byte[] bytes = (read + "\n").getBytes();
				out.write(bytes);
			}
			read = br.readLine();
		}
	}

	private static boolean isFinished(Process process) {
		try {
			process.exitValue();
			return true;
		} catch (IllegalThreadStateException ex) {
			return false;
		}
	}

}
