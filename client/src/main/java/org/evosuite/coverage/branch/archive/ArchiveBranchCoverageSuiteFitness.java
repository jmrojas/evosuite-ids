/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.coverage.branch.archive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.coverage.archive.TestsArchive;
import org.evosuite.coverage.branch.BranchCoverageFactory;
import org.evosuite.coverage.branch.BranchCoverageTestFitness;
import org.evosuite.coverage.branch.BranchPool;
import org.evosuite.graphs.cfg.CFGMethodAdapter;
import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fitness function for a whole test suite for all branches. During the
 * evolution, this fitness updates the list of goals by removing the covered
 * ones, storing at the same time the tests that covered them in an archive.
 * @author Gordon Fraser, mattia
 */
public class ArchiveBranchCoverageSuiteFitness extends TestSuiteFitnessFunction {

	private static final long serialVersionUID = 2991632394620406243L;

	private final static Logger logger = LoggerFactory.getLogger(TestSuiteFitnessFunction.class);

	// Coverage targets
	public int totalMethods;
	public int totalBranches;
	public int totalGoals;
	public final int numBranchlessMethods;
	private final Set<String> branchlessMethods;
	private final Set<String> methods;
	private final Set<Integer> branchesId;
	
	// Some stuff for debug output
	public int maxCoveredBranches = 0;
	public int maxCoveredMethods = 0;
	public double bestFitness = Double.MAX_VALUE;

	// Each test gets a set of distinct covered goals, these are mapped by branch id
	private final Map<Integer, TestFitnessFunction> branchCoverageTrueMap = new HashMap<Integer, TestFitnessFunction>();
	private final Map<Integer, TestFitnessFunction> branchCoverageFalseMap = new HashMap<Integer, TestFitnessFunction>();
	private final Map<String, TestFitnessFunction> branchlessMethodCoverageMap = new HashMap<String, TestFitnessFunction>();

	private final TestsArchive bestChromoBuilder;

	private final Set<Integer> toRemoveBranchesT = new HashSet<>();
	private final Set<Integer> toRemoveBranchesF = new HashSet<>();
	private final Set<String> toRemoveRootBranches = new HashSet<>();	
	
	private final Set<Integer> removedBranchesT = new HashSet<>();
	private final Set<Integer> removedBranchesF = new HashSet<>();
	private final Set<String> removedRootBranches = new HashSet<>();	
	
	
	
	/**
	 * <p>
	 * Constructor for BranchCoverageSuiteFitness.
	 * </p>
	 */
	public ArchiveBranchCoverageSuiteFitness() {
		this(TestsArchive.instance);
	}
	
	
	/**
	 * <p>
	 * Constructor for BranchCoverageSuiteFitness.
	 * </p>
	 */
	public ArchiveBranchCoverageSuiteFitness(TestsArchive bestChromoBuilder) {

		String prefix = Properties.TARGET_CLASS_PREFIX;

		this.bestChromoBuilder = bestChromoBuilder;
		
		if (prefix.isEmpty()) {
			prefix = Properties.TARGET_CLASS;
			totalMethods = CFGMethodAdapter.getNumMethodsPrefix(prefix);
			totalBranches = BranchPool.getBranchCountForPrefix(prefix);
			numBranchlessMethods = BranchPool.getNumBranchlessMethodsPrefix(prefix);
			branchlessMethods = BranchPool.getBranchlessMethodsPrefix(prefix);
			methods = CFGMethodAdapter.getMethodsPrefix(prefix);

		} else {
			totalMethods = CFGMethodAdapter.getNumMethodsPrefix(prefix);
			totalBranches = BranchPool.getBranchCountForPrefix(prefix);
			numBranchlessMethods = BranchPool.getNumBranchlessMethodsPrefix(prefix);
			branchlessMethods = BranchPool.getBranchlessMethodsPrefix(prefix);
			methods = CFGMethodAdapter.getMethodsPrefix(prefix);
		}
		branchesId = new HashSet<>();

		totalGoals = 2 * totalBranches + numBranchlessMethods;

		logger.info("Total branch coverage goals: " + totalGoals);
		logger.info("Total branches: " + totalBranches);
		logger.info("Total branchless methods: " + numBranchlessMethods);
		logger.info("Total methods: " + totalMethods + ": " + methods);

		determineCoverageGoals();
	}


	/**
	 * Initialize the set of known coverage goals
	 */
	private void determineCoverageGoals() {
		List<BranchCoverageTestFitness> goals = new BranchCoverageFactory().getCoverageGoals();
		for (BranchCoverageTestFitness goal : goals) {
			if(Properties.TEST_ARCHIVE)
				bestChromoBuilder.addGoalToCover(this, goal);
			
			if (goal.getBranch() == null) {
				branchlessMethodCoverageMap.put(goal.getClassName() + "."
				                                        + goal.getMethod(), goal);
			} else {
				branchesId.add(goal.getBranch().getActualBranchId());
				if (goal.getBranchExpressionValue())
					branchCoverageTrueMap.put(goal.getBranch().getActualBranchId(), goal);
				else
					branchCoverageFalseMap.put(goal.getBranch().getActualBranchId(), goal);
			}
		}
	}

	/**
	 * If there is an exception in a superconstructor, then the corresponding
	 * constructor might not be included in the execution trace
	 * 
	 * @param results
	 * @param callCount
	 */
	private void handleConstructorExceptions(AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite, List<ExecutionResult> results,
	        Map<String, Integer> callCount) {

		for (ExecutionResult result : results) {
			if (result.hasTimeout() || result.hasTestException()
			        || result.noThrownExceptions())
				continue;

			Integer exceptionPosition = result.getFirstPositionOfThrownException();
			
			// TODO: Not sure why that can happen
			if(exceptionPosition >= result.test.size())
				continue;
			
			Statement statement = result.test.getStatement(exceptionPosition);
			if (statement instanceof ConstructorStatement) {
				ConstructorStatement c = (ConstructorStatement) statement;
				String className = c.getConstructor().getName();
				String methodName = "<init>"
				        + Type.getConstructorDescriptor(c.getConstructor().getConstructor());
				String name = className + "." + methodName;
				if (!callCount.containsKey(name)) {
					callCount.put(name, 1);
					if (branchlessMethodCoverageMap.containsKey(name)) {
						result.test.addCoveredGoal(branchlessMethodCoverageMap.get(name));
						if(Properties.TEST_ARCHIVE) {
							bestChromoBuilder.putTest(this, branchlessMethodCoverageMap.get(name), result.test);
							toRemoveRootBranches.add(name);
							suite.isToBeUpdated(true);
						}
					}

				}
			}

		}
	}

	/**
	 * Iterate over all execution results and summarize statistics
	 * 
	 * @param results
	 * @param predicateCount
	 * @param callCount
	 * @param trueDistance
	 * @param falseDistance
	 * @return
	 */
	private boolean analyzeTraces( AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite, List<ExecutionResult> results,
	        Map<Integer, Integer> predicateCount, Map<String, Integer> callCount,
	        Map<Integer, Double> trueDistance, Map<Integer, Double> falseDistance) {
		boolean hasTimeoutOrTestException = false;
		for (ExecutionResult result : results) {
			if (result.hasTimeout() || result.hasTestException()) {
				hasTimeoutOrTestException = true;
			}
			
			for (Entry<String, Integer> entry : result.getTrace().getMethodExecutionCount().entrySet()) {
				if(entry.getKey()==null||!methods.contains(entry.getKey())||removedRootBranches.contains(entry.getKey())) continue;
				if (!callCount.containsKey(entry.getKey()))
					callCount.put(entry.getKey(), entry.getValue());
				else {
					callCount.put(entry.getKey(),
					              callCount.get(entry.getKey()) + entry.getValue());
				}
				//TODO why? check this
				if (branchlessMethodCoverageMap.containsKey(entry.getKey())) {
					result.test.addCoveredGoal(branchlessMethodCoverageMap.get(entry.getKey()));
					if(Properties.TEST_ARCHIVE) {
						bestChromoBuilder.putTest(this, branchlessMethodCoverageMap.get(entry.getKey()), result.test);
						toRemoveRootBranches.add(entry.getKey());
						suite.isToBeUpdated(true);
					}
				}
			}
			for (Entry<Integer, Integer> entry : result.getTrace().getPredicateExecutionCount().entrySet()) {
				if (!branchesId.contains(entry.getKey())
						|| (removedBranchesT.contains(entry.getKey())
						&& removedBranchesF.contains(entry.getKey())))
					continue;
				if (!predicateCount.containsKey(entry.getKey()))
					predicateCount.put(entry.getKey(), entry.getValue());
				else {
					predicateCount.put(entry.getKey(),
							predicateCount.get(entry.getKey())
							+ entry.getValue());
				}
			}
			for (Entry<Integer, Double> entry : result.getTrace().getTrueDistances().entrySet()) {
				if(!branchesId.contains(entry.getKey())||removedBranchesT.contains(entry.getKey())) continue;
				if (!trueDistance.containsKey(entry.getKey()))
					trueDistance.put(entry.getKey(), entry.getValue());
				else {
					trueDistance.put(entry.getKey(),
							Math.min(trueDistance.get(entry.getKey()),
									entry.getValue()));
				}
				if ((Double.compare(entry.getValue(), 0.0) ==0)) {
					result.test.addCoveredGoal(branchCoverageTrueMap.get(entry.getKey()));
					if(Properties.TEST_ARCHIVE) {
						bestChromoBuilder.putTest(this, branchCoverageTrueMap.get(entry.getKey()), result.test);
						toRemoveBranchesT.add(entry.getKey());
						suite.isToBeUpdated(true);
					}
				}
			}
			for (Entry<Integer, Double> entry : result.getTrace().getFalseDistances().entrySet()) {
				if(!branchesId.contains(entry.getKey())||removedBranchesF.contains(entry.getKey())) continue;
				if (!falseDistance.containsKey(entry.getKey()))
					falseDistance.put(entry.getKey(), entry.getValue());
				else {
					falseDistance.put(entry.getKey(),
							Math.min(falseDistance.get(entry.getKey()),
									entry.getValue()));
				}
				if ((Double.compare(entry.getValue(), 0.0) ==0)) {
					result.test.addCoveredGoal(branchCoverageFalseMap.get(entry.getKey()));
					if(Properties.TEST_ARCHIVE) {
						bestChromoBuilder.putTest(this, branchCoverageFalseMap.get(entry.getKey()), result.test);
						toRemoveBranchesF.add(entry.getKey());
						suite.isToBeUpdated(true);
					}
				}
			}
		}
		return hasTimeoutOrTestException;
	}
	
	@Override
	public boolean updateCoveredGoals() {
		
		if(!Properties.TEST_ARCHIVE)
			return false;
		
		for (String method : toRemoveRootBranches) {
			boolean removed = branchlessMethods.remove(method);
			TestFitnessFunction f = branchlessMethodCoverageMap.remove(method);
			if (removed && f != null) {
				totalMethods--;
				methods.remove(method);
				removedRootBranches.add(method);
				//removeTestCall(f.getTargetClass(), f.getTargetMethod());
			} else {
				throw new IllegalStateException("goal to remove not found");
			}
		}

		for (Integer branch : toRemoveBranchesT) {
			TestFitnessFunction f = branchCoverageTrueMap.remove(branch);
			if (f != null) {
				removedBranchesT.add(branch);
				if (removedBranchesF.contains(branch)) {
					totalBranches--;
					//if(isFullyCovered(f.getTargetClass(), f.getTargetMethod())) {
					//	removeTestCall(f.getTargetClass(), f.getTargetMethod());
					//}
				}
			} else {
				throw new IllegalStateException("goal to remove not found");
			}
		}
		for (Integer branch : toRemoveBranchesF) {
			TestFitnessFunction f = branchCoverageFalseMap.remove(branch);
			if (f != null) {
				removedBranchesF.add(branch);
				if (removedBranchesT.contains(branch)) {
					totalBranches--;
					//if(isFullyCovered(f.getTargetClass(), f.getTargetMethod())) {
					//	removeTestCall(f.getTargetClass(), f.getTargetMethod());
					//}
				}
			} else {
				throw new IllegalStateException("goal to remove not found");
			}
		}
		
		toRemoveRootBranches.clear();
		toRemoveBranchesF.clear();
		toRemoveBranchesT.clear();
		logger.info("Current state of archive: "+bestChromoBuilder.toString());
		
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Execute all tests and count covered branches
	 */
	@Override
	public double getFitness(
	        AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite) {
		logger.trace("Calculating branch fitness");
		double fitness = 0.0;

		List<ExecutionResult> results = runTestSuite(suite);
		Map<Integer, Double> trueDistance = new HashMap<Integer, Double>();
		Map<Integer, Double> falseDistance = new HashMap<Integer, Double>();
		Map<Integer, Integer> predicateCount = new HashMap<Integer, Integer>();
		Map<String, Integer> callCount = new HashMap<String, Integer>();

		// Collect stats in the traces 
		boolean hasTimeoutOrTestException = analyzeTraces(suite, results, predicateCount,
		                                                  callCount, trueDistance,
		                                                  falseDistance);
		// In case there were exceptions in a constructor
		handleConstructorExceptions(suite, results, callCount);

		// Collect branch distances of covered branches
		int numCoveredBranches = 0;

		for (Integer key : predicateCount.keySet()) {
			
			double df = 0.0;
			double dt = 0.0;
			int numExecuted = predicateCount.get(key);
			
			if(removedBranchesT.contains(key))
				numExecuted++;
			if(removedBranchesF.contains(key))
				numExecuted++;
			
			if (trueDistance.containsKey(key)) {
				dt =  trueDistance.get(key);
			}
			if(falseDistance.containsKey(key)){
				df = falseDistance.get(key);
			}
			// If the branch predicate was only executed once, then add 1 
			if (numExecuted == 1) {
				fitness += 1.0;
			} else {
				fitness += normalize(df) + normalize(dt);
			}

			fitness += normalize(df) + normalize(dt); 


			if (falseDistance.containsKey(key)&&(Double.compare(df, 0.0) == 0))
				numCoveredBranches++;

			if (trueDistance.containsKey(key)&&(Double.compare(dt, 0.0) == 0))
				numCoveredBranches++;
		}
		
		// +1 for every branch that was not executed
		fitness += 2 * (totalBranches - predicateCount.size());

		// Ensure all methods are called
		int missingMethods = 0;
		for (String e : methods) {
			if (!callCount.containsKey(e)) {
				fitness += 1.0;
				missingMethods += 1;
			}
		}
		printStatusMessages(suite, numCoveredBranches, totalMethods - missingMethods,
		                    fitness);

		// Calculate coverage
		int coverage = numCoveredBranches;
		for (String e : branchlessMethods) {
			if (callCount.keySet().contains(e)) {
				coverage++;
			}

		}

		coverage +=removedBranchesF.size();
		coverage +=removedBranchesT.size();
		coverage +=removedRootBranches.size();
	
 		
		if (totalGoals > 0)
			suite.setCoverage(this, (double) coverage / (double) totalGoals);
        else
            suite.setCoverage(this, 1);

		suite.setNumOfCoveredGoals(this, coverage);
		suite.setNumOfNotCoveredGoals(this, totalGoals-coverage);
		
		if (hasTimeoutOrTestException) {
			logger.info("Test suite has timed out, setting fitness to max value "
			        + (totalBranches * 2 + totalMethods));
			fitness = totalBranches * 2 + totalMethods;
			//suite.setCoverage(0.0);
		}

		updateIndividual(this, suite, fitness);

		assert (coverage <= totalGoals) : "Covered " + coverage + " vs total goals "
		        + totalGoals;
		assert (fitness >= 0.0);
		assert (fitness != 0.0 || coverage == totalGoals) : "Fitness: " + fitness + ", "
		        + "coverage: " + coverage + "/" + totalGoals;
		assert (suite.getCoverage(this) <= 1.0) && (suite.getCoverage(this) >= 0.0) : "Wrong coverage value "
		        + suite.getCoverage(this); 
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
	        AbstractTestSuiteChromosome<? extends ExecutableChromosome> suite,
	        int coveredBranches, int coveredMethods, double fitness) {
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

	public TestSuiteChromosome getBestStoredIndividual(){
		// TODO: There's a design problem here because
		//       other fitness functions use the same archive
		return bestChromoBuilder.getReducedChromosome();
		//return bestChromoBuilder.getBestChromosome();
	}

}
