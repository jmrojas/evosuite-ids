/*
 * Copyright (C) 2010 Saarland University
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
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.coverage.branch;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.coverage.lcsaj.LCSAJPool;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.graphs.cfg.CFGMethodAdapter;
import de.unisb.cs.st.evosuite.javaagent.LinePool;
import de.unisb.cs.st.evosuite.testcase.ExecutableChromosome;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.MethodStatement;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testsuite.AbstractTestSuiteChromosome;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteFitnessFunction;

/**
 * Fitness function for a whole test suite for all branches
 * 
 * @author Gordon Fraser
 * 
 */
public class BranchCoverageSuiteFitness extends TestSuiteFitnessFunction {

	private static final long serialVersionUID = 2991632394620406243L;

	private static Logger logger = LoggerFactory.getLogger(TestSuiteFitnessFunction.class);

	public static final int total_methods;
	public static final int total_branches;
	public static final int numBranchlessMethods;
	public static final Set<Integer> lines;
	private static final Set<String> branchlessMethods;

	static {
		String prefix = Properties.TARGET_CLASS_PREFIX;

		if (prefix.isEmpty()) {
			prefix = Properties.TARGET_CLASS;
			total_methods = CFGMethodAdapter.getNumMethodsMemberClasses(prefix);
			total_branches = BranchPool.getBranchCountForMemberClasses(prefix);
			numBranchlessMethods = BranchPool.getNumBranchlessMethodsMemberClasses(prefix);
			branchlessMethods = BranchPool.getBranchlessMethodsMemberClasses(prefix);
		} else {
			total_methods = CFGMethodAdapter.getNumMethodsPrefix(prefix);
			total_branches = BranchPool.getBranchCountForPrefix(prefix);
			numBranchlessMethods = BranchPool.getNumBranchlessMethodsPrefix(prefix);
			branchlessMethods = BranchPool.getBranchlessMethodsPrefix(prefix);
		}

		/* TODO: Would be nice to use a prefix here */
		lines = LinePool.getLines(Properties.TARGET_CLASS);
	}

	public int covered_branches = 0;

	public int covered_methods = 0;

	public double best_fitness = Double.MAX_VALUE;

	public static final int total_goals = 2 * total_branches + numBranchlessMethods;

	public static int mostCoveredGoals = 0;

	protected boolean check = false;

	private final Set<String> publicTargetMethods = new HashSet<String>();

	public BranchCoverageSuiteFitness() {
		logger.info("Total branch coverage goals: " + total_goals);
		logger.info("Total branches: " + total_branches);
		logger.info("Total branchless methods: " + numBranchlessMethods);
		logger.info("Total methods: " + total_methods + ": "
		        + CFGMethodAdapter.methods.get(Properties.TARGET_CLASS));

		getPublicMethods();
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
			if (!result.exceptions.isEmpty()) {
				limit = result.exceptions.keySet().iterator().next() + 1;
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
	public double getFitness(Chromosome individual) {
		logger.trace("Calculating branch fitness");

		long start = System.currentTimeMillis();

		AbstractTestSuiteChromosome<ExecutableChromosome> suite = (AbstractTestSuiteChromosome<ExecutableChromosome>) individual;
		long estart = System.currentTimeMillis();
		List<ExecutionResult> results = runTestSuite(suite);
		long eend = System.currentTimeMillis();
		double fitness = 0.0;
		Map<Integer, Double> true_distance = new HashMap<Integer, Double>();
		Map<Integer, Double> false_distance = new HashMap<Integer, Double>();
		Map<Integer, Integer> predicate_count = new HashMap<Integer, Integer>();
		Map<String, Integer> call_count = new HashMap<String, Integer>();
		Set<Integer> covered_lines = new HashSet<Integer>();
		boolean hasTimeout = false;

		for (ExecutionResult result : results) {
			if (hasTimeout(result)) {
				hasTimeout = true;
			}

			for (Entry<String, Integer> entry : result.getTrace().covered_methods.entrySet()) {
				if (!call_count.containsKey(entry.getKey()))
					call_count.put(entry.getKey(), entry.getValue());
				else {
					call_count.put(entry.getKey(),
					               call_count.get(entry.getKey()) + entry.getValue());
				}
			}
			for (Entry<Integer, Integer> entry : result.getTrace().covered_predicates.entrySet()) {
				if (!LCSAJPool.isLCSAJBranch(BranchPool.getBranch(entry.getKey()))) {
					if (!predicate_count.containsKey(entry.getKey()))
						predicate_count.put(entry.getKey(), entry.getValue());
					else {
						predicate_count.put(entry.getKey(),
						                    predicate_count.get(entry.getKey())
						                            + entry.getValue());
					}
				}
			}
			for (Entry<Integer, Double> entry : result.getTrace().true_distances.entrySet()) {
				if (!LCSAJPool.isLCSAJBranch(BranchPool.getBranch(entry.getKey()))) {
					if (!true_distance.containsKey(entry.getKey()))
						true_distance.put(entry.getKey(), entry.getValue());
					else {
						true_distance.put(entry.getKey(),
						                  Math.min(true_distance.get(entry.getKey()),
						                           entry.getValue()));
					}
				}
			}
			for (Entry<Integer, Double> entry : result.getTrace().false_distances.entrySet()) {
				if (!LCSAJPool.isLCSAJBranch(BranchPool.getBranch(entry.getKey()))) {
					if (!false_distance.containsKey(entry.getKey()))
						false_distance.put(entry.getKey(), entry.getValue());
					else {
						false_distance.put(entry.getKey(),
						                   Math.min(false_distance.get(entry.getKey()),
						                            entry.getValue()));
					}
				}
			}

			if (Properties.BRANCH_STATEMENT) {
				// Add requirement on statements
				for (Map<String, Map<Integer, Integer>> coverage : result.getTrace().coverage.values()) {
					for (Map<Integer, Integer> coveredLines : coverage.values())
						covered_lines.addAll(coveredLines.keySet());
				}
			}
			//if (result.hasUndeclaredException())
			//	fitness += 1.0;
		}

		int num_covered = 0;
		int uncovered = 0;

		//logger.info("Got data for predicates: " + predicate_count.size()+"/"+total_branches);
		for (Integer key : predicate_count.keySet()) {
			//logger.info("Key: "+key);
			if (!true_distance.containsKey(key) || !false_distance.containsKey(key))
				continue;
			int num_executed = predicate_count.get(key);
			double df = true_distance.get(key);
			double dt = false_distance.get(key);
			if (num_executed == 1) {
				fitness += 1.0; // + normalize(df) + normalize(dt);
			} else {
				fitness += normalize(df) + normalize(dt);
			}
			if (df == 0.0)
				num_covered++;
			else
				uncovered++;
			if (dt == 0.0)
				num_covered++;
			else
				uncovered++;
		}
		//logger.info("Fitness after branch distances: "+fitness);
		//for(String call : call_count.keySet()) {
		//	logger.info("  "+call+": "+call_count.get(call));
		//}
		/*
		if(call_count.size() < total_methods) { // +1 for the call of the test case
			//logger.info("Missing calls: "+(total_methods - call_count.size())+"/"+total_methods);
			fitness += total_methods - call_count.size();
		}
		*/
		//		logger.info("Method calls : "+(total_methods - call_count.size())+"/"+total_methods+" ("+CFGMethodAdapter.methods.size()+")");
		int missing_methods = 0;

		Set<String> methods = Properties.TARGET_CLASS_PREFIX.isEmpty() ? CFGMethodAdapter.getMethods(Properties.TARGET_CLASS)
		        : CFGMethodAdapter.getMethodsPrefix(Properties.TARGET_CLASS_PREFIX);

		for (String e : methods) {
			if (!call_count.containsKey(e)) {
				//logger.debug("Missing method: " + e);
				fitness += 1.0;
				missing_methods += 1;
			}
		}
		//for (String method : call_count.keySet()) {
		//	logger.debug("Got method: " + method);
		//}

		//logger.info("Fitness after missing methods: "+fitness);

		// How many branches are there in total?
		//fitness += 2 * total_branches - num;
		fitness += 2 * (total_branches - predicate_count.size());
		//logger.info("Missing branches: "+(2*(total_branches - predicate_count.size()))+"/"+(2*total_branches));
		//logger.info("Missing methods: "+missing_methods+"/"+total_methods);
		//logger.info("Uncovered branches: "+uncovered);
		//logger.info("Fitness after missing branches: "+fitness);
		//logger.info("Got data for "+predicate_count.size()+" predicates, covered "+num_covered+" total "+(total_branches*2)+", covered "+call_count.size()+" methods out of "+total_methods);

		if (num_covered > covered_branches) {
			covered_branches = Math.max(covered_branches, num_covered);
			logger.info("(Branches) Best individual covers " + num_covered + "/"
			        + (total_branches * 2) + " branches and "
			        + (total_methods - missing_methods) + "/" + total_methods
			        + " methods");
			logger.info("Fitness: " + fitness + ", size: " + suite.size() + ", length: "
			        + suite.totalLengthOfTestCases());
		}
		//		if(call_count.size() > covered_methods) {
		if ((total_methods - missing_methods) > covered_methods) {
			logger.info("(Methods) Best individual covers " + num_covered + "/"
			        + (total_branches * 2) + " branches and "
			        + (total_methods - missing_methods) + "/" + total_methods
			        + " methods");
			covered_methods = (total_methods - missing_methods);
			logger.info("Fitness: " + fitness + ", size: " + suite.size() + ", length: "
			        + suite.totalLengthOfTestCases());

		}
		if (fitness < best_fitness) {
			logger.info("(Fitness) Best individual covers " + num_covered + "/"
			        + (total_branches * 2) + " branches and "
			        + (total_methods - missing_methods) + "/" + total_methods
			        + " methods");
			best_fitness = fitness;
			logger.info("Fitness: " + fitness + ", size: " + suite.size() + ", length: "
			        + suite.totalLengthOfTestCases());

		}

		//		covered_methods  = Math.max(covered_methods,  call_count.size());
		if (Properties.BRANCH_STATEMENT) {
			int totalLines = lines.size();
			logger.info("Covered " + covered_lines.size() + " out of " + totalLines
			        + " lines");
			//fitness += totalLines - covered_lines.size();
		}
		//logger.info("Fitness: "+fitness+", size: "+suite.size()+", length: "+suite.length());

		/*
		Set<String> coveredPublicMethods = getDirectlyCoveredMethods(suite);
		Set<String> uncoveredPublicMethods = new HashSet<String>(publicTargetMethods);
		uncoveredPublicMethods.removeAll(coveredPublicMethods);
		fitness += uncoveredPublicMethods.size();
		logger.debug("Adding penalty for public methods: "
		        + uncoveredPublicMethods.size());
		for (String method : uncoveredPublicMethods) {
			logger.debug(method);
		}
		*/

		long end = System.currentTimeMillis();
		if (end - start > 1000) {
			logger.info("Executing tests took    : " + (eend - estart) + "ms");
			logger.info("Calculating fitness took: " + (end - start) + "ms");
		}
		double coverage = num_covered;
		for (String e : branchlessMethods) {
			if (call_count.keySet().contains(e))
				coverage += 1.0;

		}
		if (mostCoveredGoals < coverage)
			mostCoveredGoals = (int) coverage;

		if (coverage > total_goals) {
			logger.warn("Total goals: " + total_goals);
			logger.warn("Covered goals: " + coverage);
			logger.warn("Total branches: " + total_branches * 2);
			logger.warn("Covered branches: " + num_covered);
		}
		assert (coverage <= total_goals) : "Covered " + coverage + " vs total goals "
		        + total_goals;
		suite.setCoverage(coverage / total_goals);
		assert (fitness != 0.0 || coverage == total_goals) : "Fitness: " + fitness + ", "
		        + "coverage: " + coverage + "/" + total_goals;
		if (coverage / total_goals > 1) {
			logger.warn("Coverage > 1:");
			logger.warn("Covered branches: " + num_covered);

		}
		if (hasTimeout) {
			logger.info("Test suite has timed out, setting fitness to max value "
			        + (total_branches * 2 + total_methods));
			fitness = total_branches * 2 + total_methods;
			//suite.setCoverage(0.0);
		}

		updateIndividual(individual, fitness);

		assert (suite.getCoverage() <= 1.0);
		assert (suite.getCoverage() >= 0.0);
		//if (!check)
		//	checkFitness(suite, fitness);
		return fitness;
	}

	public void checkFitness(AbstractTestSuiteChromosome<ExecutableChromosome> suite,
	        double fitness) {
		for (ExecutableChromosome test : suite.getTestChromosomes()) {
			test.setChanged(true);
		}
		logger.info("Running double check");
		check = true;
		double fitness2 = getFitness(suite);
		check = false;
		//		assert (fitness == fitness2) : "Fitness is " + fitness + " but should be "
		//		        + fitness2;
		if (fitness != fitness2)
			logger.error("Fitness is " + fitness + " but should be " + fitness2);
	}
}
