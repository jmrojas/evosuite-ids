/**
 * 
 */
package de.unisb.cs.st.evosuite.testsuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.coverage.branch.Branch;
import de.unisb.cs.st.evosuite.coverage.branch.BranchPool;
import de.unisb.cs.st.evosuite.symbolic.BranchCondition;
import de.unisb.cs.st.evosuite.symbolic.ConcolicExecution;
import de.unisb.cs.st.evosuite.symbolic.expr.BinaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.Constraint;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstraint;
import de.unisb.cs.st.evosuite.symbolic.expr.UnaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.Variable;
import de.unisb.cs.st.evosuite.symbolic.smt.cvc3.CVC3Solver;
import de.unisb.cs.st.evosuite.testcase.ExecutableChromosome;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.PrimitiveStatement;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;

/**
 * @author Gordon Fraser
 * 
 */
public class TestSuiteDSE {

	private static Logger logger = LoggerFactory.getLogger(TestSuiteDSE.class);

	private final Set<Integer> uncoveredBranches = new HashSet<Integer>();

	private final Set<Branch> branches = new HashSet<Branch>();

	private final Map<String, Set<Integer>> jpfBranchMap = new HashMap<String, Set<Integer>>();

	/**
	 * For each uncovered branch try to add a new test
	 * 
	 * @param individual
	 */
	public void applyDSE(TestSuiteChromosome individual) {
		clearBranches();
		determineCoveredBranches(individual);

		ConcolicExecution concolicExecution = new ConcolicExecution();

		logger.info("Applying DSE to suite of size " + individual.size());
		logger.info("Starting with " + uncoveredBranches.size() + " candidate branches");

		List<TestChromosome> tests = new ArrayList<TestChromosome>(
		        individual.getTestChromosomes());
		// For each TestChromosome
		for (TestChromosome test : tests) {

			// Only apply DSE if if makes any sense
			if (hasUncoveredBranches(test)) {
				logger.info("Found uncovered branches in test, applying DSE");

				// Apply DSE to gather constraints
				List<BranchCondition> branches = concolicExecution.getSymbolicPath(test);

				// For each uncovered branch
				for (BranchCondition branch : branches) {
					if (isUncovered(branch)) {
						logger.info("Trying to cover branch "
						        + branch.ins.getInstructionIndex());

						// Try to solve negated constraint
						TestCase newTest = negateCondition(branch, test.getTestCase());

						// If successful, add resulting test to test suite
						if (newTest != null) {
							logger.info("Created new test");
							logger.info("-> Remaining " + uncoveredBranches.size()
							        + " candidate branches");
							updateTestSuite(individual, newTest);
							//newTests.add(newTest);
							//setCovered(branch);
							logger.info("-> Remaining " + uncoveredBranches.size()
							        + " candidate branches");
							logger.info("Resulting suite has size " + individual.size());
						}
					}
				}
				logger.info("Remaining " + uncoveredBranches.size()
				        + " candidate branches");
			}
		}

		logger.info("Resulting suite has size " + individual.size());

	}

	private void addBranch(Branch b) {
		String key = b.getClassName() + "." + b.getMethodName();
		if (!jpfBranchMap.containsKey(key)) {
			jpfBranchMap.put(key, new HashSet<Integer>());
		}
		jpfBranchMap.get(key).add(b.getInstruction().getJPFId());
	}

	/**
	 * Determine which of the branches are covered by the suite and which are
	 * not
	 * 
	 * @param suite
	 */
	private void determineCoveredBranches(TestSuiteChromosome suite) {
		Set<Integer> coveredTrue = new HashSet<Integer>();
		Set<Integer> coveredFalse = new HashSet<Integer>();

		for (TestChromosome test : suite.getTestChromosomes()) {
			if (test.getLastExecutionResult() == null) {
				test.setLastExecutionResult(runTest(test.getTestCase()));
				test.setChanged(false);
			}

			for (Integer branchId : test.getLastExecutionResult().getTrace().covered_predicates.keySet()) {
				if (test.getLastExecutionResult().getTrace().true_distances.get(branchId) == 0.0)
					coveredTrue.add(branchId);
				if (test.getLastExecutionResult().getTrace().false_distances.get(branchId) == 0.0)
					coveredFalse.add(branchId);
			}
		}

		for (Integer branchId : coveredTrue) {
			if (!coveredFalse.contains(branchId)) {
				Branch b = BranchPool.getBranch(branchId);
				branches.add(b);
				addBranch(b);
				uncoveredBranches.add(branchId);
			}
		}
		for (Integer branchId : coveredFalse) {
			if (!coveredTrue.contains(branchId)) {
				Branch b = BranchPool.getBranch(branchId);
				branches.add(b);
				addBranch(b);
				uncoveredBranches.add(branchId);
			}
		}
		logger.info("Found " + uncoveredBranches.size() + " candidate branches");
	}

	/**
	 * Update the information about branches we need to cover
	 * 
	 * @param test
	 */
	private void updateTestSuite(TestSuiteChromosome suite, TestCase test) {
		suite.addTest(test);
		clearBranches();
		determineCoveredBranches(suite);
	}

	/**
	 * Determine whether this test suite has covered this branch in one of its
	 * tests
	 * 
	 * @param branch
	 * @return
	 */
	private boolean isUncovered(BranchCondition branch) {
		if (!branch.ins.getMethodInfo().getClassName().equals(Properties.TARGET_CLASS)) {
			return false;
		}

		String jpfName = branch.ins.getMethodInfo().getFullName();
		if (!jpfBranchMap.containsKey(jpfName))
			return false;

		if (jpfBranchMap.get(jpfName).contains(branch.ins.getInstructionIndex())) {
			return true;
		}

		return false;
	}

	/**
	 * Determine whether this test case touches a branch that is not fully
	 * covered
	 * 
	 * @param test
	 * @return
	 */
	private boolean hasUncoveredBranches(ExecutableChromosome test) {
		for (Integer branchId : test.getLastExecutionResult().getTrace().covered_predicates.keySet()) {
			if (uncoveredBranches.contains(branchId))
				return true;
		}
		return false;
	}

	/**
	 * Generate new constraint and ask solver for solution
	 * 
	 * @param condition
	 * @param test
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TestCase negateCondition(BranchCondition condition, TestCase test) {
		List<Constraint<?>> constraints = new LinkedList<Constraint<?>>();
		constraints.addAll(condition.reachingConstraints);
		//constraints.addAll(condition.localConstraints);
		Constraint<Long> c = (Constraint<Long>) condition.localConstraints.iterator().next();
		Constraint<Long> targetConstraint = new IntegerConstraint(c.getLeftOperand(),
		        c.getComparator().not(), c.getRightOperand());
		constraints.add(targetConstraint);
		if (!targetConstraint.isSolveable()) {
			logger.info("Found unsolvable constraint: " + targetConstraint);
			// TODO: This is usually the case when the same variable is used for several parameters of a method
			// Could we treat this as a special case?
			return null;
		}

		int size = constraints.size();
		if (size > 0) {
			constraints = reduce(constraints);
			logger.info("Reduced constraints from " + size + " to " + constraints.size());
		}

		CVC3Solver solver = new CVC3Solver();
		Map<String, Object> values = solver.getModel(constraints);

		if (values != null) {
			int num = 0;
			TestCase newTest = test.clone();

			for (Object key : values.keySet()) {
				Object val = values.get(key);
				if (val != null) {
					if (val instanceof Long) {
						Long value = (Long) val;
						String name = ((String) key).replace("__SYM", "");
						logger.debug("New value for " + name + " is " + value);
						PrimitiveStatement p = getStatement(newTest, name);
						assert (p != null);
						p.setValue(value.intValue());
					} else {
						logger.debug("New value is not long " + val);
					}
				} else {
					logger.debug("New value is null");

				}
				num++;
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
	private PrimitiveStatement<?> getStatement(TestCase test, String name) {
		for (StatementInterface statement : test) {
			if (statement instanceof PrimitiveStatement<?>) {
				if (statement.getReturnValue().getName().equals(name))
					return (PrimitiveStatement<?>) statement;
			}
		}
		return null;
	}

	/**
	 * Concrete execution
	 * 
	 * @param test
	 * @return
	 */
	private ExecutionResult runTest(TestCase test) {

		ExecutionResult result = new ExecutionResult(test, null);

		try {
			logger.debug("Executing test");
			result = TestCaseExecutor.getInstance().execute(test);
		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			e.printStackTrace();
			System.exit(1);
		}

		return result;
	}

	/**
	 * Clear the information about the last DSE run
	 */
	private void clearBranches() {
		branches.clear();
		jpfBranchMap.clear();
		uncoveredBranches.clear();
	}

	/**
	 * Apply cone of influence reduction to constraints with respect to the last
	 * constraint in the list
	 * 
	 * @param constraints
	 * @return
	 */
	private List<Constraint<?>> reduce(List<Constraint<?>> constraints) {

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
	private Set<Variable<?>> getVariables(Constraint<?> constraint) {
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
	private void getVariables(Expression<?> expr, Set<Variable<?>> variables) {
		if (expr instanceof Variable) {
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
