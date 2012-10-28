/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
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
package org.evosuite.coverage.loop;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.coverage.branch.Branch;
import org.evosuite.coverage.branch.BranchCoverageFactory;
import org.evosuite.coverage.branch.BranchCoverageTestFitness;
import org.evosuite.coverage.branch.BranchPool;
import org.evosuite.coverage.lcsaj.LCSAJPool;
import org.evosuite.graphs.cfg.CFGMethodAdapter;
import org.evosuite.javaagent.LinePool;
import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.MethodStatement;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fitness function for a whole test suite for all branches
 * 
 * @author Gordon Fraser
 * 
 */
public class LoopInvCandidateFalseBranchCoverageSuiteFitness extends
        TestSuiteFitnessFunction {

	private static final long serialVersionUID = 2991632394620406243L;

	private static Logger logger = LoggerFactory.getLogger(TestSuiteFitnessFunction.class);

	public final int totalMethods;
	public final int totalBranches;
	public final int numBranchlessMethods;
	public final Set<Integer> lines;
	private final Set<String> branchlessMethods;

	public int covered_branches = 0;

	public int covered_methods = 0;

	public double best_fitness = Double.MAX_VALUE;

	public int totalGoals;

	public static int mostCoveredGoals = 0;

	protected boolean check = false;

	private final Set<String> publicTargetMethods = new HashSet<String>();

	private final int totalCheckLoopInvariantCandidateBranchs;

	public LoopInvCandidateFalseBranchCoverageSuiteFitness() {

		String prefix = Properties.TARGET_CLASS_PREFIX;

		if (prefix.isEmpty()) {
			prefix = Properties.TARGET_CLASS;
			totalMethods = CFGMethodAdapter.getNumMethodsMemberClasses(prefix);
			totalBranches = BranchPool.getBranchCountForMemberClasses(prefix);
			numBranchlessMethods = BranchPool.getNumBranchlessMethodsMemberClasses(prefix);
			branchlessMethods = BranchPool.getBranchlessMethodsMemberClasses(prefix);
		} else {
			totalMethods = CFGMethodAdapter.getNumMethodsPrefix(prefix);
			totalBranches = BranchPool.getBranchCountForPrefix(prefix);
			numBranchlessMethods = BranchPool.getNumBranchlessMethodsPrefix(prefix);
			branchlessMethods = BranchPool.getBranchlessMethodsPrefix(prefix);
		}

		int checkLoopInvariantCandidateBranchCount = 0;
		for (int i = 1; i <= totalBranches; i++) {
			Branch branch = BranchPool.getBranch(i);
			if (branch == null)
				throw new IllegalStateException("unknown branch!");

			if (branch.getMethodName().startsWith("check_loop_invariant_candidate")) {
				checkLoopInvariantCandidateBranchCount++;
			}
		}
		totalCheckLoopInvariantCandidateBranchs = checkLoopInvariantCandidateBranchCount;

		/* TODO: Would be nice to use a prefix here */
		lines = LinePool.getLines(Properties.TARGET_CLASS);

		totalGoals = 2 * totalBranches + numBranchlessMethods;

		logger.info("Total branch coverage goals: " + totalGoals);
		logger.info("Total branches: " + totalBranches);
		logger.info("Total branchless methods: " + numBranchlessMethods);
		logger.info("Total methods: " + totalMethods + ": "
		        + CFGMethodAdapter.methods.get(Properties.TARGET_CLASS));

		getPublicMethods();
		determineCoverageGoals();
	}

	public int maxCoveredBranches = 0;

	public int maxCoveredMethods = 0;

	public double bestFitness = Double.MAX_VALUE;

	private final Map<Integer, TestFitnessFunction> branchCoverageTrueMap = new HashMap<Integer, TestFitnessFunction>();
	private final Map<Integer, TestFitnessFunction> branchCoverageFalseMap = new HashMap<Integer, TestFitnessFunction>();

	private final Map<String, TestFitnessFunction> branchlessMethodCoverageMap = new HashMap<String, TestFitnessFunction>();

	private void determineCoverageGoals() {
		List<BranchCoverageTestFitness> goals = new BranchCoverageFactory().getCoverageGoals();
		for (BranchCoverageTestFitness goal : goals) {
			if (goal.getBranch() == null) {
				branchlessMethodCoverageMap.put(goal.getClassName() + "."
				                                        + goal.getMethod(), goal);
			} else {
				if (goal.getBranchExpressionValue())
					branchCoverageTrueMap.put(goal.getBranch().getActualBranchId(), goal);
				else
					branchCoverageFalseMap.put(goal.getBranch().getActualBranchId(), goal);
			}
		}
	}

	private void getPublicMethods() {
		for (Method method : Properties.getTargetClass().getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers())) {
				String name = method.getName() + Type.getMethodDescriptor(method);
				publicTargetMethods.add(name);
			}
		}
	}

	private Set<String> getDirectlyCoveredMethods(
	        AbstractTestSuiteChromosome<ExecutableChromosome> suite) {
		Set<String> covered = new HashSet<String>();
		for (ExecutableChromosome test : suite.getTestChromosomes()) {
			ExecutionResult result = test.getLastExecutionResult();
			int limit = test.size();
			if (!result.noThrownExceptions()) {
				limit = result.getFirstPositionOfThrownException() + 1;
			}
			for (int i = 0; i < limit; i++) {
				StatementInterface statement = result.test.getStatement(i);
				if (statement instanceof MethodStatement) {
					MethodStatement methodStatement = (MethodStatement) statement;
					Method method = methodStatement.getMethod();
					if (method.getDeclaringClass().equals(Properties.getTargetClass())
					        && Modifier.isPublic(method.getModifiers())) {
						String name = method.getName() + Type.getMethodDescriptor(method);
						covered.add(name);
					}
				}
			}
		}
		return covered;
	}

	/**
	 * Execute all tests and count covered branches
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double getFitness(
	        AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite) {

		logger.trace("Calculating branch fitness");

		long start = System.currentTimeMillis();

		long estart = System.currentTimeMillis();
		List<ExecutionResult> results = runTestSuite(suite);
		long eend = System.currentTimeMillis();
		double fitness = 0.0;
		Map<Integer, Double> trueDistance = new HashMap<Integer, Double>();
		Map<Integer, Double> falseDistance = new HashMap<Integer, Double>();
		Map<Integer, Integer> predicateCount = new HashMap<Integer, Integer>();
		Map<String, Integer> callCount = new HashMap<String, Integer>();
		Set<Integer> covered_lines = new HashSet<Integer>();
		boolean hasTimeoutOrTestException = false;

		for (ExecutionResult result : results) {
			if (result.hasTimeout() || result.hasTestException()) {
				hasTimeoutOrTestException = true;
			}

			for (Entry<String, Integer> entry : result.getTrace().getMethodExecutionCount().entrySet()) {
				if (!callCount.containsKey(entry.getKey()))
					callCount.put(entry.getKey(), entry.getValue());
				else {
					callCount.put(entry.getKey(),
					              callCount.get(entry.getKey()) + entry.getValue());
				}
				if (branchlessMethodCoverageMap.containsKey(entry.getKey())) {
					result.test.addCoveredGoal(branchlessMethodCoverageMap.get(entry.getKey()));
				}

			}
			for (Entry<Integer, Integer> entry : result.getTrace().getPredicateExecutionCount().entrySet()) {
				if (!LCSAJPool.isLCSAJBranch(BranchPool.getBranch(entry.getKey()))) {
					if (!predicateCount.containsKey(entry.getKey()))
						predicateCount.put(entry.getKey(), entry.getValue());
					else {
						predicateCount.put(entry.getKey(),
						                   predicateCount.get(entry.getKey())
						                           + entry.getValue());
					}
				}
			}
			for (Entry<Integer, Double> entry : result.getTrace().getTrueDistances().entrySet()) {
				if (!LCSAJPool.isLCSAJBranch(BranchPool.getBranch(entry.getKey()))) {
					if (!trueDistance.containsKey(entry.getKey()))
						trueDistance.put(entry.getKey(), entry.getValue());
					else {
						trueDistance.put(entry.getKey(),
						                 Math.min(trueDistance.get(entry.getKey()),
						                          entry.getValue()));
					}
					if (entry.getValue() == 0.0) {
						result.test.addCoveredGoal(branchCoverageTrueMap.get(entry.getKey()));
					}
				}
			}
			for (Entry<Integer, Double> entry : result.getTrace().getFalseDistances().entrySet()) {
				if (!LCSAJPool.isLCSAJBranch(BranchPool.getBranch(entry.getKey()))) {
					if (!falseDistance.containsKey(entry.getKey()))
						falseDistance.put(entry.getKey(), entry.getValue());
					else {
						falseDistance.put(entry.getKey(),
						                  Math.min(falseDistance.get(entry.getKey()),
						                           entry.getValue()));
					}
				}
				if (entry.getValue() == 0.0) {
					result.test.addCoveredGoal(branchCoverageFalseMap.get(entry.getKey()));
				}
			}

			if (Properties.BRANCH_STATEMENT) {
				// Add requirement on statements
				for (Map<String, Map<Integer, Integer>> coverage : result.getTrace().getCoverageData().values()) {
					for (Map<Integer, Integer> coveredLines : coverage.values())
						covered_lines.addAll(coveredLines.keySet());
				}
			}
			//if (result.hasUndeclaredException())
			//	fitness += 1.0;
		}

		int numCoveredBranches = 0;
		int loopInvCandidatePredicateCount = 0;

		for (Integer key : predicateCount.keySet()) {

			if (!trueDistance.containsKey(key) || !falseDistance.containsKey(key))
				continue;

			Branch branch = BranchPool.getBranch(key);

			String method_name = branch.getMethodName();
			if (method_name.startsWith("check_loop_invariant_candidate")) {
				loopInvCandidatePredicateCount++;

				int numExecuted = predicateCount.get(key);

				if (numExecuted == 0)
					throw new IllegalStateException("numExecuted cannot be 0!");
				else {
					double df = trueDistance.get(key);
					fitness += normalize(df);

					if (df == 0.0)
						numCoveredBranches++;
				}
			}

		}

		// add unreached loop invariant predicates
		fitness += (totalCheckLoopInvariantCandidateBranchs - loopInvCandidatePredicateCount);

		// logger.info("Fitness: "+fitness+", size: "+suite.size()+", length: "+suite.length());

		/*
		 * Set<String> coveredPublicMethods = getDirectlyCoveredMethods(suite);
		 * Set<String> uncoveredPublicMethods = new
		 * HashSet<String>(publicTargetMethods);
		 * uncoveredPublicMethods.removeAll(coveredPublicMethods); fitness +=
		 * uncoveredPublicMethods.size();
		 * logger.debug("Adding penalty for public methods: " +
		 * uncoveredPublicMethods.size()); for (String method :
		 * uncoveredPublicMethods) { logger.debug(method); }
		 */

		long end = System.currentTimeMillis();
		if (end - start > 1000) {
			logger.info("Executing tests took    : " + (eend - estart) + "ms");
			logger.info("Calculating fitness took: " + (end - start) + "ms");
		}
		int coverage = numCoveredBranches;
		for (String e : branchlessMethods) {
			if (callCount.keySet().contains(e)) {
				coverage++;
			}

		}
		if (mostCoveredGoals < coverage)
			mostCoveredGoals = coverage;

		assert (coverage <= totalGoals) : "Covered " + coverage + " vs total goals "
		        + totalGoals;
		suite.setCoverage((double) coverage / (double) totalGoals);
		assert (fitness != 0.0 || coverage == totalGoals) : "Fitness: " + fitness + ", "
		        + "coverage: " + coverage + "/" + totalGoals;
		if (hasTimeoutOrTestException) {
			logger.info("Test suite has timed out, setting fitness to max value "
			        + (totalCheckLoopInvariantCandidateBranchs));
			fitness = totalCheckLoopInvariantCandidateBranchs;
			// suite.setCoverage(0.0);
		}

		updateIndividual(suite, fitness);

		assert (suite.getCoverage() <= 1.0) && (suite.getCoverage() >= 0.0) : "Wrong coverage value "
		        + suite.getCoverage();
		// if (!check)
		// checkFitness(suite, fitness);
		return fitness;
	}

	/**
	 * Some useful debug information
	 * 
	 * @param coveredBranches
	 * @param coveredMethods
	 * @param fitness
	 */
	private void printStatusMessages(
	        AbstractTestSuiteChromosome<ExecutableChromosome> suite, int coveredBranches,
	        int coveredMethods, double fitness) {
		if (coveredBranches > maxCoveredBranches) {
			maxCoveredBranches = coveredBranches;
			logger.info("(Branches) Best individual covers " + coveredBranches + "/"
			        + (totalBranches * 2) + " branches and " + coveredMethods + "/"
			        + totalMethods + " methods");
			logger.info("Fitness: " + fitness + ", size: " + suite.size() + ", length: "
			        + suite.totalLengthOfTestCases());
		}
		if (coveredMethods > maxCoveredMethods) {
			logger.info("(Methods) Best individual covers " + coveredBranches + "/"
			        + (totalBranches * 2) + " branches and " + coveredMethods + "/"
			        + totalMethods + " methods");
			maxCoveredMethods = coveredMethods;
			logger.info("Fitness: " + fitness + ", size: " + suite.size() + ", length: "
			        + suite.totalLengthOfTestCases());

		}
		if (fitness < bestFitness) {
			logger.info("(Fitness) Best individual covers " + coveredBranches + "/"
			        + (totalBranches * 2) + " branches and " + coveredMethods + "/"
			        + totalMethods + " methods");
			bestFitness = fitness;
			logger.info("Fitness: " + fitness + ", size: " + suite.size() + ", length: "
			        + suite.totalLengthOfTestCases());

		}
	}

	/**
	 * This method can be used for debugging purposes to ensure that the fitness
	 * calculation is deterministic
	 * 
	 * @param suite
	 * @param fitness
	 */
	protected void checkFitness(AbstractTestSuiteChromosome<ExecutableChromosome> suite,
	        double fitness) {
		for (ExecutableChromosome test : suite.getTestChromosomes()) {
			test.setChanged(true);
		}
		logger.info("Running double check");
		check = true;
		double fitness2 = getFitness(suite);
		check = false;
		// assert (fitness == fitness2) : "Fitness is " + fitness +
		// " but should be "
		// + fitness2;
		if (fitness != fitness2)
			logger.error("Fitness is " + fitness + " but should be " + fitness2);
	}
}