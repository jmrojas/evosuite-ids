/*
 * Copyright (C) 2009 Saarland University
 * 
 * This file is part of Javalanche.
 * 
 * Javalanche is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Javalanche is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with Javalanche.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.assertion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.mutation.MutationStatistics;
import de.unisb.cs.st.evosuite.mutation.HOM.HOMObserver;
import de.unisb.cs.st.evosuite.mutation.HOM.HOMSwitcher;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.ExecutionTracer;
import de.unisb.cs.st.evosuite.testcase.OutputTrace;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;

/**
 * This class executes a test case on a unit and all mutants 
 * and infers assertions from the resulting traces. 
 * 
 * @author Gordon Fraser
 * 
 */
public class AssertionGenerator {

	private Logger logger = Logger.getLogger(AssertionGenerator.class);
	
	List<Mutation> mutants;
	
	private HOMSwitcher hom_switcher = new HOMSwitcher();

	private TestCaseExecutor executor = new TestCaseExecutor();

	private PrimitiveOutputTraceObserver primitive_observer = new PrimitiveOutputTraceObserver();
	private ComparisonTraceObserver comparison_observer     = new ComparisonTraceObserver();
	private InspectorTraceObserver inspector_observer       = new InspectorTraceObserver();
	private PrimitiveFieldTraceObserver field_observer      = new PrimitiveFieldTraceObserver();
	private NullOutputObserver null_observer                = new NullOutputObserver();
	
	/**
	 * Default constructor 
	 */
	public AssertionGenerator() {
		executor.addObserver(primitive_observer);
		executor.addObserver(comparison_observer);
		executor.addObserver(inspector_observer);
		executor.addObserver(field_observer);
		executor.addObserver(null_observer);

		mutants = hom_switcher.getMutants();
	}
	
	public void resetObservers() {
		executor.newObservers();
		primitive_observer  = new PrimitiveOutputTraceObserver();
		comparison_observer = new ComparisonTraceObserver();
		inspector_observer  = new InspectorTraceObserver();
		field_observer      = new PrimitiveFieldTraceObserver();
		null_observer       = new NullOutputObserver();
		executor.addObserver(primitive_observer);
		executor.addObserver(comparison_observer);
		executor.addObserver(inspector_observer);
		executor.addObserver(field_observer);
		executor.addObserver(null_observer);
	}
	
	/**
	 * Execute a test case on the original unit 
	 * @param test
	 *        The test case that should be executed 
	 */
	private ExecutionResult runTest(TestCase test) {
		return runTest(test, null);
	}
	
	/**
	 * Execute a test case on a mutant 
	 * @param test
	 *        The test case that should be executed 	 
	 * @param mutant
	 *        The mutant on which the test case shall be executed 	 
	 */
	private ExecutionResult runTest(TestCase test, Mutation mutant) {
		ExecutionResult result = new ExecutionResult(test, mutant);
        resetObservers();
		//primitive_observer.clear();
		//comparison_observer.clear();
		try {
	        logger.debug("Executing test");
			HOMObserver.resetTouched(); // TODO - is this the right place?
			if(mutant != null) {
				hom_switcher.switchOn(mutant);
				executor.setLogging(false);
			}
			result.exceptions = executor.runWithTrace(test);
			executor.setLogging(true);
			
			hom_switcher.switchOff(mutant);
			result.trace = ExecutionTracer.getExecutionTracer().getTrace();
			result.output_trace = executor.getTrace();
			result.comparison_trace = comparison_observer.getTrace();
			result.primitive_trace = primitive_observer.getTrace();
			result.inspector_trace = inspector_observer.getTrace();
			result.field_trace = field_observer.getTrace();
			result.null_trace = null_observer.getTrace();
			/*
			if(ex != null) {
				result.exception = ex;
			}
			*/
			result.touched = HOMObserver.getTouched();
		} catch(Exception e) {
			System.out.println("TG: Exception caught: "+e);
			e.printStackTrace();
			System.exit(1);
		}

		return result;
	}
	
	/**
	 * Return a minimal subset of the assertions that covers all killable mutants 
	 * @param test
	 *        The test case that should be executed 	 
	 * @param mutants
	 *        The list of mutants of the unit 	 
	 * @param assertions
	 *        All assertions that can be generated for the test case 	 
	 * @param kill_map
	 *        Mapping of assertion to mutant ids that are killed by the assertion 	 
	 */	
	private void minimize(TestCase test, List<Mutation> mutants, List<Assertion> assertions, Map<Integer, Set<Long> > kill_map) {
		
		class Pair implements Comparable<Object> {
			Integer assertion;
			Integer num_killed;
			public Pair(int a, int k) {
				assertion = a;
				num_killed = k;
			}
			public int compareTo(Object o) {
				Pair other = (Pair)o;
				return num_killed.compareTo(other.num_killed);
			}
		}
		Set<Long> to_kill = new HashSet<Long>();
		for(Entry<Integer, Set<Long>> entry : kill_map.entrySet()) {
			to_kill.addAll(entry.getValue());
		}
		
		Set<Long> killed = new HashSet<Long>();
		Set<Assertion> result = new HashSet<Assertion>();
		// First try to do this without string assertions
		boolean done = false;
		while(!done) {
			//logger.info("Have to kill "+to_kill.size());
			List<Pair> a = new ArrayList<Pair>();
			for(Entry<Integer, Set<Long>> entry : kill_map.entrySet()) {
				//if(assertions.get(entry.getKey()) instanceof StringAssertion)
				//	continue;
				//if(assertions.get(entry.getKey()) instanceof ExceptionAssertion)
				//	continue;
				int num = 0;
				for(Long m : entry.getValue()) {
					if(!killed.contains(m))
						num++;
				}
				if(num > 0) {
					//logger.info("Assertion "+entry.getKey()+" kills "+num);
					a.add(new Pair(entry.getKey(), num));
				}
			}
			if(a.isEmpty())
				done = true;
			else {
				Pair best = (Pair) Collections.max(a);
				//logger.info("Chosen "+best.assertion);
				result.add(assertions.get(best.assertion));
				for(Long m : kill_map.get(best.assertion)) {
					//logger.info("Killed "+m);
					killed.add(m);
					to_kill.remove(m);
				}
			}
		}

		done = to_kill.isEmpty();
		while(!done) {
			//logger.info("Have to kill "+to_kill.size());
			List<Pair> a = new ArrayList<Pair>();
			for(Entry<Integer, Set<Long>> entry : kill_map.entrySet()) {
				int num = 0;
				for(Long m : entry.getValue()) {
					if(!killed.contains(m))
						num++;
				}
				if(num > 0) {
					//logger.info("Assertion "+entry.getKey()+" kills "+num);
					a.add(new Pair(entry.getKey(), num));
				}
			}
			if(a.isEmpty())
				done = true;
			else {
				Pair best = (Pair) Collections.max(a);
				//logger.info("Chosen "+best.assertion);
				result.add(assertions.get(best.assertion));
				for(Long m : kill_map.get(best.assertion)) {
					//logger.info("Killed "+m);
					killed.add(m);
					to_kill.remove(m);
				}
			}
		}
		
		// sort by number of assertions killed
		// pick assertion that kills most
		// remove all mutations that are already killed
		logger.debug("Minimized assertions from "+assertions.size()+" to "+result.size());
		int num_string_assertions = 0;
		int num_inspector_assertions = 0;
		int num_field_assertions = 0;
		int num_primitive_assertions = 0;
		int num_comparison_assertions = 0;
		int num_exception_assertions = 0;
		int num_null_assertions = 0;

		for(Assertion assertion : assertions) {
			int statement = assertion.getSource().statement;
			test.getStatement(statement).addAssertion(assertion);
			if(assertion instanceof StringAssertion)
				num_string_assertions++;
			else if(assertion instanceof InspectorAssertion)
				num_inspector_assertions++;
			else if(assertion instanceof PrimitiveFieldAssertion)
				num_field_assertions++;
			else if(assertion instanceof PrimitiveAssertion)
				num_primitive_assertions++;
			else if(assertion instanceof EqualsAssertion)
				num_comparison_assertions++;
			else if(assertion instanceof CompareAssertion)
				num_comparison_assertions++;
			else if(assertion instanceof ExceptionAssertion)
				num_exception_assertions++;
			else if(assertion instanceof NullAssertion)
				num_null_assertions++;
			else
				logger.error("Found unknown assertion!");

		}
		// TODO: List exception assertion
		logger.debug("Assertions before minimization: "+test.getAssertions().size()+","+num_string_assertions+","+num_inspector_assertions+","+num_field_assertions+","+num_comparison_assertions+","+num_primitive_assertions+","+num_exception_assertions);

		if(!result.isEmpty()) {
			test.removeAssertions();
			num_string_assertions = 0;
			num_inspector_assertions = 0;
			num_field_assertions = 0;
			num_primitive_assertions = 0;
			num_comparison_assertions = 0;
			num_exception_assertions = 0;
			num_null_assertions = 0;
			for(Assertion assertion : result) {
				int statement = assertion.getSource().statement;
				test.getStatement(statement).addAssertion(assertion);
				if(assertion instanceof StringAssertion)
					num_string_assertions++;
				else if(assertion instanceof InspectorAssertion)
					num_inspector_assertions++;
				else if(assertion instanceof PrimitiveFieldAssertion)
					num_field_assertions++;
				else if(assertion instanceof PrimitiveAssertion)
					num_primitive_assertions++;
				else if(assertion instanceof EqualsAssertion)
					num_comparison_assertions++;
				else if(assertion instanceof CompareAssertion)
					num_comparison_assertions++;
				else if(assertion instanceof ExceptionAssertion)
					num_exception_assertions++;
				else if(assertion instanceof NullAssertion)
					num_null_assertions++;
				else
					logger.error("Found unknown assertion!");
			}
		} else {
			logger.info("Not removing assertions because no new assertions were found");
		}
		// TODO: List exception assertion
		logger.debug("Assertions after minimization: "+test.getAssertions().size()+","+num_string_assertions+","+num_inspector_assertions+","+num_field_assertions+","+num_comparison_assertions+","+num_primitive_assertions+","+num_exception_assertions);
	}
	
	
	public void addAssertions(TestCase test, Mutation mutant) {
		ExecutionResult orig_result = runTest(test);
		ExecutionResult mutant_result = runTest(test, mutant);
	
		orig_result.output_trace.getAssertions(test, mutant_result.output_trace);
		orig_result.comparison_trace.getAssertions(test, mutant_result.comparison_trace);
		orig_result.primitive_trace.getAssertions(test, mutant_result.primitive_trace);
		orig_result.inspector_trace.getAssertions(test, mutant_result.inspector_trace);
		orig_result.field_trace.getAssertions(test, mutant_result.field_trace);
		orig_result.null_trace.getAssertions(test, mutant_result.null_trace);
		
		logger.debug("Generated "+test.getAssertions().size()+" assertions");
	}
	
	public void addAssertions(TestCase test, Set<Long> killed) {
		addAssertions(test, killed, mutants);
	}
	
	public void addAssertions(TestCase test, Set<Long> killed, List<Mutation> mutants) { 
	
		logger.info("Generating assertions");
		MutationStatistics statistics = MutationStatistics.getInstance();
		int s1 = killed.size();
		// Get additional traces from observers directly:
		// Primitive trace - trace primitive values
		// equals trace - call equals on values of identical classes
		// compareto trace - call compareto on values of identical classes
		
		logger.debug("Running on original");
		ExecutionResult orig_result = runTest(test);
		//logger.info("Done");
		//ComparisonTrace comparison_trace = orig_result.comparison_trace;
		//PrimitiveOutputTrace primitive_trace = orig_result.primitive_trace;
		//InspectorTrace inspector_trace = orig_result.inspector_trace;
		//PrimitiveFieldTrace field_trace = orig_result.field_trace;
//		ComparisonTrace comparison_trace = comparison_observer.getTrace();
//		PrimitiveOutputTrace primitive_trace = primitive_observer.getTrace();
//		InspectorTrace inspector_trace = inspector_observer.getTrace();
//		PrimitiveFieldTrace field_trace = field_observer.getTrace();
		//ExceptionTrace exception_trace = new ExceptionTrace(orig_result.exception, orig_result.exception_statement);

		Map<Mutation, List< OutputTrace> > mutation_traces = new HashMap<Mutation, List<OutputTrace>>();
/*
		Map<Mutation, ExecutionResult> mutation_results = new HashMap<Mutation, ExecutionResult>();
		Map<Mutation, ComparisonTrace> mutation_comparison_traces = new HashMap<Mutation, ComparisonTrace>();
		Map<Mutation, PrimitiveOutputTrace> mutation_primitive_traces = new HashMap<Mutation, PrimitiveOutputTrace>();
		Map<Mutation, InspectorTrace> mutation_inspector_traces = new HashMap<Mutation, InspectorTrace>();
		Map<Mutation, PrimitiveFieldTrace> mutation_field_traces = new HashMap<Mutation, PrimitiveFieldTrace>();
*/
		
		// TODO: We can do this much nicer polymorphic!
		// Run test case on all mutations
		for(Mutation m : mutants) {
			logger.debug("Running on mutation "+m.getId());
			//logger.info(m.toString());

			ExecutionResult mutant_result = runTest(test, m);
			
			//orig_result.output_trace.getAssertions(test, mutant_result.output_trace);
			
			//ComparisonTrace mutant_comparison_trace = mutant_result.comparison_trace;
			//PrimitiveOutputTrace mutant_primitive_trace = mutant_result.primitive_trace;
			//InspectorTrace mutant_inspector_trace = mutant_result.inspector_trace;
			//PrimitiveFieldTrace mutant_field_trace = mutant_result.field_trace;
			//ExceptionTrace mutant_exception_trace = new ExceptionTrace(mutant_result.exception, mutant_result.exception_statement);
	
			
			List<OutputTrace> traces = new ArrayList<OutputTrace>();
			traces.add(mutant_result.comparison_trace);
			traces.add(mutant_result.primitive_trace);
			traces.add(mutant_result.inspector_trace);
			traces.add(mutant_result.field_trace);
			traces.add(mutant_result.output_trace);
			traces.add(mutant_result.null_trace);
			//traces.add(mutant_exception_trace); // TODO!
			mutation_traces.put(m, traces);
			
			/*
			mutation_results.put(m, mutant_result);
			mutation_comparison_traces.put(m, mutant_comparison_trace);
			mutation_primitive_traces.put(m, mutant_primitive_trace);
			mutation_inspector_traces.put(m, mutant_inspector_trace);
			mutation_field_traces.put(m, mutant_field_trace);
			*/
			
			int num_killed = 0;
			
			// TODO
			//if(orig_result.exception != null) {
			//	if(orig_result.exception.getMessage() != null && !orig_result.exception.getMessage().equals(mutant_result.exception.getMessage())) {
			//		test.statements.get(orig_result.exception_statement).addAssertion(new ExceptionAssertion(orig_result.exception, orig_result.exception_statement));
			//		num_killed ++;				
			//	}
			//}

			int last_num = 0;
			num_killed += orig_result.output_trace.getAssertions(test, mutant_result.output_trace);
			//logger.info("String: Potentially "+orig_result.output_trace.numDiffer(mutant_result.output_trace));
			if(num_killed > last_num) {
				//logger.info("Added "+num_killed+" string assertions for mutation: "+m.getId());
				last_num = num_killed;
			}
			//logger.info("Comparison: Potentially "+orig_result.comparison_trace.numDiffer(mutant_result.comparison_trace));
			num_killed += orig_result.comparison_trace.getAssertions(test, mutant_result.comparison_trace);
			if(num_killed > last_num) {
				//logger.info("Added "+num_killed+" comparison assertions for mutation: "+m.getId());
				last_num = num_killed;
			} else {
				//logger.info("Could add "+orig_result.comparison_trace.numDiffer(mutant_result.comparison_trace)+" comparison assertions for mutation: "+m.getId());
			}
			//logger.info("Primitive: Potentially "+orig_result.primitive_trace.numDiffer(mutant_result.primitive_trace));
			num_killed += orig_result.primitive_trace.getAssertions(test, mutant_result.primitive_trace);
			if(num_killed > last_num) {
				//logger.info("Added "+num_killed+" primitive assertions for mutation: "+m.getId());
				last_num = num_killed;
			}
			//logger.info("Inspector: Potentially "+orig_result.inspector_trace.numDiffer(mutant_result.inspector_trace));
			num_killed += orig_result.inspector_trace.getAssertions(test, mutant_result.inspector_trace);
			if(num_killed > last_num) {
				//logger.info("Added "+num_killed+" inspector assertions for mutation: "+m.getId());
				last_num = num_killed;
			} else {
				//logger.info("Could add "+orig_result.inspector_trace.numDiffer(mutant_result.inspector_trace)+" inspector assertions for mutation: "+m.getId());
			}
			//logger.info("String: Potentially "+orig_result.output_trace.numDiffer(mutant_result.output_trace));
			num_killed += orig_result.field_trace.getAssertions(test, mutant_result.field_trace);
			if(num_killed > last_num) {
				//logger.info("Added "+num_killed+" field assertions for mutation: "+m.getId());
				last_num = num_killed;
			}

			num_killed += orig_result.null_trace.getAssertions(test, mutant_result.null_trace);
			if(num_killed > last_num) {
				//logger.info("Added "+num_killed+" field assertions for mutation: "+m.getId());
				last_num = num_killed;
			}

			if(num_killed > 0) {
				killed.add(m.getId());

				//logger.info("Setting mutant asserted "+m.getId());
				//statistics.setAsserted(m);
			}
		}
		
		int num_before=0;
		Set<Long> killed_before = new HashSet<Long>();
		List<Assertion> assertions = test.getAssertions();
		logger.info("Got "+assertions.size()+" assertions");
		Map<Integer, Set<Long> > kill_map = new HashMap<Integer, Set<Long > >();
		int num = 0;
		for(Assertion assertion : assertions) {
			Set<Long> killed_mutations = new HashSet<Long>();
			for(Mutation m : mutants) {
				boolean is_killed = false;
				for(OutputTrace trace : mutation_traces.get(m)) {
					is_killed = trace.isDetectedBy(assertion);
					if(is_killed) {
						killed_before.add(m.getId());
						break;
					}
				}
				if(is_killed) {
					//logger.info("Found assertion for mutation: "+m.getId());
					num_before++;
					killed_mutations.add(m.getId());
					//statistics.setAsserted(m);
				//} else {
				//	logger.info("Found no assertions for mutation: "+m.getId());					
				}
			}
			kill_map.put(num, killed_mutations);
			num++;
		}
		minimize(test, mutants, assertions, kill_map);
		
		Set<Long> killed_after = new HashSet<Long>();
		int num_after=0;
		assertions = test.getAssertions();
		int num2 = 0;
		for(Assertion assertion : assertions) {
			for(Mutation m : mutants) {
				boolean is_killed = false;
				for(OutputTrace trace : mutation_traces.get(m)) {
					is_killed = trace.isDetectedBy(assertion);
					if(is_killed) {
						killed_after.add(m.getId());
						break;
					}
				}
				if(is_killed) {
					num_after++;
				}
			}
			num2++;
		}
		int s2 = killed.size() - s1;
		logger.debug("Mutants killed before / after / should be: "+killed_before.size()+"/"+killed_after.size()+"/"+s2);
		for(Mutation m : mutants) {
			if(killed_after.contains(m.getId()) && !m.isKilled())
				logger.debug("Asserted: "+m.getId());
		}
	}
	
}
