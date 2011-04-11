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

package de.unisb.cs.st.evosuite.mutation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.unisb.cs.st.evosuite.assertion.ComparisonTraceObserver;
import de.unisb.cs.st.evosuite.assertion.InspectorTraceObserver;
import de.unisb.cs.st.evosuite.assertion.NullOutputObserver;
import de.unisb.cs.st.evosuite.assertion.PrimitiveFieldTraceObserver;
import de.unisb.cs.st.evosuite.assertion.PrimitiveOutputTraceObserver;
import de.unisb.cs.st.evosuite.coverage.ControlFlowDistance;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.mutation.HOM.HOMObserver;
import de.unisb.cs.st.evosuite.mutation.HOM.HOMSwitcher;
import de.unisb.cs.st.evosuite.testcase.ExecutionObserver;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.ExecutionTrace;
import de.unisb.cs.st.evosuite.testcase.ExecutionTracer;
import de.unisb.cs.st.evosuite.testcase.MaxStatementsStoppingCondition;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;
import de.unisb.cs.st.evosuite.testcase.TestFitnessFunction;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;

/**
 * @author Gordon Fraser
 * 
 */
public class MutationTestFitness extends TestFitnessFunction {

	private final Mutation target_mutation;

	private final MutationGoal mutation_goal;

	private final HOMSwitcher hom_switcher = new HOMSwitcher();

	protected List<ExecutionObserver> observers;

	protected PrimitiveOutputTraceObserver primitive_observer = new PrimitiveOutputTraceObserver();
	protected ComparisonTraceObserver comparison_observer = new ComparisonTraceObserver();
	protected InspectorTraceObserver inspector_observer = new InspectorTraceObserver();
	protected PrimitiveFieldTraceObserver field_observer = new PrimitiveFieldTraceObserver();
	protected NullOutputObserver null_observer = new NullOutputObserver();

	public MutationTestFitness(Mutation mutation) {
		target_mutation = mutation;
		mutation_goal = new MutationGoal(mutation);

		executor.addObserver(primitive_observer);
		executor.addObserver(comparison_observer);
		executor.addObserver(inspector_observer);
		executor.addObserver(field_observer);
		executor.addObserver(null_observer);
	}

	public Mutation getTargetMutation() {
		return target_mutation;
	}

	@Override
	public ExecutionResult runTest(TestCase test) {
		return runTest(test, null);
	}

	/**
	 * Execute a test case
	 * 
	 * @param test
	 *            The test case to execute
	 * @param mutant
	 *            The mutation to active (null = no mutation)
	 * 
	 * @return Result of the execution
	 */
	public ExecutionResult runTest(TestCase test, Mutation mutant) {

		ExecutionResult result = new ExecutionResult(test, mutant);

		try {
			logger.debug("KExecuting test");
			HOMObserver.resetTouched(); // TODO - is this the right place?
			if (mutant != null) {
				hom_switcher.switchOn(mutant);
				executor.setLogging(false);
			}
			result.exceptions = executor.run(test);
			executor.setLogging(true);
			if (mutant != null)
				hom_switcher.switchOff(mutant);

			result.trace = ExecutionTracer.getExecutionTracer().getTrace();
			// result.output_trace = executor.getTrace();
			result.comparison_trace = comparison_observer.getTrace();
			result.primitive_trace = primitive_observer.getTrace();
			result.inspector_trace = inspector_observer.getTrace();
			result.field_trace = field_observer.getTrace();
			result.null_trace = null_observer.getTrace();

			int num = test.size();

			MaxStatementsStoppingCondition.statementsExecuted(num);
			result.touched.addAll(HOMObserver.getTouched());
		} catch (Exception e) {
			logger.fatal("MutationTestFitness: Exception caught: " + e);
			logger.fatal(test.toCode());
			e.printStackTrace();
			System.exit(1);
		}

		// System.out.println("TG: Killed "+result.getNumKilled()+" out of "+mutants.size());
		return result;
	}

	private Set<String> getDifference(
	        Map<String, Map<String, Map<Integer, Integer>>> orig,
	        Map<String, Map<String, Map<Integer, Integer>>> mutant) {
		Map<String, Set<String>> handled = new HashMap<String, Set<String>>();
		Set<String> differ = new HashSet<String>();

		for (Entry<String, Map<String, Map<Integer, Integer>>> entry : orig
		        .entrySet()) {
			if (!handled.containsKey(entry.getKey()))
				handled.put(entry.getKey(), new HashSet<String>());

			for (Entry<String, Map<Integer, Integer>> method_entry : entry
			        .getValue().entrySet()) {
				if (!mutant.containsKey(entry.getKey())) {
					// Class was not executed on mutant, so add method
					logger.debug("Found class difference: " + entry.getKey());
					differ.add(entry.getKey());
				} else {
					// Class was also executed on mutant

					if (!mutant.get(entry.getKey()).containsKey(
					        method_entry.getKey())) {
						// Method was not executed on mutant, so add method
						logger.debug("Found method difference: "
						        + method_entry.getKey());
						differ.add(entry.getKey() + "." + method_entry.getKey());
					} else {
						// Method was executed on mutant
						for (Entry<Integer, Integer> line_entry : method_entry
						        .getValue().entrySet()) {
							if (!mutant.get(entry.getKey())
							        .get(method_entry.getKey())
							        .containsKey(line_entry.getKey())) {
								// Line was not executed on mutant, so add
								logger.debug("Found line difference: "
								        + line_entry.getKey() + ": "
								        + line_entry.getValue());
								differ.add(entry.getKey() + "."
								        + method_entry.getKey() + ":"
								        + line_entry.getKey());
							} else {
								if (!mutant.get(entry.getKey())
								        .get(method_entry.getKey())
								        .get(line_entry.getKey())
								        .equals(line_entry.getValue())) {
									// Line coverage differs, so add
									differ.add(entry.getKey() + "."
									        + method_entry.getKey() + ":"
									        + line_entry.getKey());
									logger.debug("Found line difference: "
									        + line_entry.getKey() + ": "
									        + line_entry.getValue());
								}
							}
						}
						if (!method_entry.getValue().equals(
						        mutant.get(entry.getKey()).get(
						                method_entry.getKey()))) {
							differ.add(entry.getKey() + "."
							        + method_entry.getKey());

							logger.debug("Found other difference: "
							        + entry.getKey());
							// logger.info("Coverage difference on : "+entry.getKey()+":"+method_entry.getKey());
						}
					}
				}
			}
		}

		return differ;
	}

	/**
	 * Compare two coverage maps
	 * 
	 * @param orig
	 * @param mutant
	 * @return unique number of methods with coverage difference
	 */
	private int getCoverageDifference(
	        Map<String, Map<String, Map<Integer, Integer>>> orig,
	        Map<String, Map<String, Map<Integer, Integer>>> mutant) {
		Set<String> differ = getDifference(orig, mutant);
		differ.addAll(getDifference(mutant, orig));
		return differ.size();
	}

	private double getSumDistance(ExecutionTrace orig_trace,
	        ExecutionTrace mutant_trace) {

		// double sum = getCoverageDifference(getCoverage(orig_trace),
		// getCoverage(mutant_trace));
		double coverage_impact = getCoverageDifference(orig_trace.coverage,
		        mutant_trace.coverage);
		logger.debug("Coverage impact: " + coverage_impact);
		double data_impact = getCoverageDifference(orig_trace.return_data,
		        mutant_trace.return_data);
		logger.debug("Data impact: " + data_impact);

		return coverage_impact + data_impact;
	}

	private int getNumAssertions(ExecutionResult orig_result,
	        ExecutionResult mutant_result) {
		int num = 0;
		if (orig_result.test.size() == 0)
			return 0;

		int last_num = 0;
		// num +=
		// orig_result.output_trace.numDiffer(mutant_result.output_trace);
		// if (num > last_num)
		// logger.debug("Found " + (num - last_num) + " output assertions!");
		// last_num = num;
		num += orig_result.comparison_trace
		        .numDiffer(mutant_result.comparison_trace);
		if (num > last_num)
			logger.debug("Found " + (num - last_num)
			        + " comparison assertions!");
		last_num = num;
		num += orig_result.primitive_trace
		        .numDiffer(mutant_result.primitive_trace);
		if (num > last_num)
			logger.debug("Found " + (num - last_num) + " primitive assertions!");
		last_num = num;
		num += orig_result.inspector_trace
		        .numDiffer(mutant_result.inspector_trace);
		if (num > last_num)
			logger.debug("Found " + (num - last_num) + " inspector assertions!");
		last_num = num;
		num += orig_result.field_trace.numDiffer(mutant_result.field_trace);
		if (num > last_num)
			logger.debug("Found " + (num - last_num) + " field assertions!");
		last_num = num;
		num += orig_result.null_trace.numDiffer(mutant_result.null_trace);
		if (num > last_num)
			logger.debug("Found " + (num - last_num) + " null assertions!");

		logger.debug("Found " + num + " assertions!");
		return num;
	}

	protected boolean hasAssertions(ExecutionResult orig_result,
	        ExecutionResult mutant_result) {

		// if(orig_result.output_trace.differs(mutant_result.output_trace))
		// num += 0.5;
		if (orig_result.comparison_trace
		        .differs(mutant_result.comparison_trace)) {
			logger.info("Difference in comparison trace");
			return true;
		}
		if (orig_result.primitive_trace.differs(mutant_result.primitive_trace)) {
			logger.info("Difference in primitive trace");
			return true;
		}
		if (orig_result.inspector_trace.differs(mutant_result.inspector_trace)) {
			logger.info("Difference in inspector trace");
			return true;
		}
		if (orig_result.field_trace.differs(mutant_result.field_trace)) {
			logger.info("Difference in field trace");
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.TestFitnessFunction#getFitness(de.unisb
	 * .cs.st.evosuite.testcase.TestChromosome,
	 * de.unisb.cs.st.evosuite.testcase.ExecutionResult)
	 */
	@Override
	public double getFitness(TestChromosome individual, ExecutionResult result) {

		/*
		 * ExecutionResult check_result = runTest(individual.test); int n =
		 * getNumAssertions(result, check_result); if(n > 0) {
		 * logger.info("Found change: "); logger.info(individual.test.toCode());
		 * result = check_result; }
		 */
		/*
		 * logger.info("Execution check 1"); ExecutionResult mutant_result =
		 * runTest(individual.test, target_mutation); getNumAssertions(result,
		 * mutant_result);
		 * 
		 * logger.info("Execution check 2"); result = runTest(individual.test);
		 * getNumAssertions(result, mutant_result);
		 * 
		 * logger.info("Execution check 3"); mutant_result =
		 * runTest(individual.test, target_mutation); getNumAssertions(result,
		 * mutant_result);
		 */
		// First, get the distance. TODO: What about branch distance?
		ControlFlowDistance cfg_distance = mutation_goal.getDistance(result);

		if (cfg_distance.approach > 1
		        || (cfg_distance.approach == 1 && cfg_distance.branch > 0.0)) {
			logger.debug("Distance is " + cfg_distance.approach + "/"
			        + cfg_distance.branch);
			if (cfg_distance.approach == 1)
				logger.debug(individual.test.toCode());
			// return 1.0 + cfg_distance.approach +
			// normalize(cfg_distance.branch);
			/*
			 * ExecutionResult mutant_result = runTest(individual.test,
			 * target_mutation); if(!mutant_result.touched) {
			 * logger.warn("Mutant not touched!"); } else {
			 * logger.warn("Mutant touched!"); }
			 */
			return cfg_distance.approach + normalize(cfg_distance.branch); // 1
			                                                               // =
			                                                               // distance
			                                                               // for
			                                                               // mutation
			                                                               // activation
			/*
			 * } else if(cfg_distance.approach == 1) {
			 * logger.info("Distance is "+cfg_distance.approach+"/" +
			 * cfg_distance.branch); ExecutionResult mutant_result =
			 * runTest(individual.test, target_mutation);
			 * if(!mutant_result.touched) { logger.warn("Mutant not touched!");
			 * } else { logger.warn("Mutant touched!"); } return
			 * cfg_distance.approach + normalize(cfg_distance.branch); // 1 =
			 * distance for mutation activation
			 */
		}

		logger.debug("Distance is: " + cfg_distance.approach);

		// If the distance is zero, then try
		ExecutionResult mutant_result = runTest(individual.test,
		        target_mutation);
		if (!mutant_result.touched.contains(target_mutation.getId())) {
			logger.warn("Distance is 1 but mutant " + target_mutation.getId()
			        + " not executed: " + target_mutation.getClassName() + "."
			        + target_mutation.getMethodName());
			logger.warn(result.test.toCode());
			logger.warn(cfg_distance.approach + "/" + cfg_distance.branch);
		}

		if (MutationGoal.hasTimeout(mutant_result)) {
			logger.debug("Found timeout in mutant!");
			MutationTimeoutStoppingCondition.timeOut(target_mutation);
		}

		// if(!hasAssertions(result, mutant_result))
		if (getNumAssertions(result, mutant_result) == 0) {
			double impact = getSumDistance(result.trace, mutant_result.trace);
			logger.debug("Impact is " + impact + " (" + (1.0 / (1.0 + impact))
			        + ")");
			return 1.0 / (1.0 + impact);
		} else {
			logger.debug("Mutant is asserted!");
			/*
			 * individual.test.removeAssertions(); AssertionGenerator gen = new
			 * AssertionGenerator(); gen.addAssertions(individual.test,
			 * target_mutation); logger.info(individual.test.toCode());
			 * logger.info("Mutant assertions checked.\n");
			 */
			return 0.0; // This mutant is asserted
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.ga.FitnessFunction#updateIndividual(de.unisb.
	 * cs.st.evosuite.ga.Chromosome, double)
	 */
	@Override
	protected void updateIndividual(Chromosome individual, double fitness) {
		individual.setFitness(fitness);
	}

	@Override
	public String toString() {
		return "Mutation goal: " + target_mutation.getId() + ": "
		        + target_mutation.getClassName() + "."
		        + target_mutation.getMethodName() + ":"
		        + target_mutation.getLineNumber() + " - "
		        + target_mutation.getMutationType();
	}
}
