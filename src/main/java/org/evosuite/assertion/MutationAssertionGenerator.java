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
package org.evosuite.assertion;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.coverage.mutation.Mutation;
import org.evosuite.coverage.mutation.MutationObserver;
import org.evosuite.coverage.mutation.MutationPool;
import org.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.MethodStatement;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCaseExecutor;
import org.evosuite.testcase.VariableReference;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class executes a test case on a unit and all mutants and infers
 * assertions from the resulting traces.
 * 
 * TODO: This class is a mess.
 * 
 * @author Gordon Fraser
 */
public class MutationAssertionGenerator extends AssertionGenerator {

	private final Map<Integer, Mutation> mutants = new HashMap<Integer, Mutation>();

	private final static Logger logger = LoggerFactory.getLogger(MutationAssertionGenerator.class);

	private static PrimitiveTraceObserver primitiveObserver = new PrimitiveTraceObserver();
	private static ComparisonTraceObserver comparisonObserver = new ComparisonTraceObserver();
	private static SameTraceObserver sameObserver = new SameTraceObserver();
	private static InspectorTraceObserver inspectorObserver = new InspectorTraceObserver();
	private static PrimitiveFieldTraceObserver fieldObserver = new PrimitiveFieldTraceObserver();
	private static NullTraceObserver nullObserver = new NullTraceObserver();

	private final static Map<Mutation, Integer> timedOutMutations = new HashMap<Mutation, Integer>();

	/** Constant <code>observerClasses</code> */
	protected static Class<?>[] observerClasses = { PrimitiveTraceEntry.class,
	        ComparisonTraceEntry.class, SameTraceEntry.class, InspectorTraceEntry.class,
	        PrimitiveFieldTraceEntry.class, NullTraceEntry.class };

	/**
	 * Default constructor
	 */
	public MutationAssertionGenerator() {
		for (Mutation m : MutationPool.getMutants()) {
			mutants.put(m.getId(), m);
		}
		TestCaseExecutor.getInstance().newObservers();
		TestCaseExecutor.getInstance().addObserver(primitiveObserver);
		TestCaseExecutor.getInstance().addObserver(comparisonObserver);
		TestCaseExecutor.getInstance().addObserver(sameObserver);
		TestCaseExecutor.getInstance().addObserver(inspectorObserver);
		TestCaseExecutor.getInstance().addObserver(fieldObserver);
		TestCaseExecutor.getInstance().addObserver(nullObserver);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Execute a test case on the original unit
	 */
	@Override
	protected ExecutionResult runTest(TestCase test) {
		return runTest(test, null);
	}

	/**
	 * Execute a test case on a mutant
	 * 
	 * @param test
	 *            The test case that should be executed
	 * @param mutant
	 *            The mutant on which the test case shall be executed
	 */
	private ExecutionResult runTest(TestCase test, Mutation mutant) {
		ExecutionResult result = new ExecutionResult(test, mutant);
		//resetObservers();
		comparisonObserver.clear();
		sameObserver.clear();
		primitiveObserver.clear();
		inspectorObserver.clear();
		fieldObserver.clear();
		nullObserver.clear();
		try {
			logger.debug("Executing test");
			MutationObserver.activateMutation(mutant);
			result = TestCaseExecutor.getInstance().execute(test);
			MutationObserver.deactivateMutation(mutant);

			int num = test.size();
			MaxStatementsStoppingCondition.statementsExecuted(num);

			result.setTrace(comparisonObserver.getTrace(), ComparisonTraceEntry.class);
			result.setTrace(sameObserver.getTrace(), SameTraceEntry.class);
			result.setTrace(primitiveObserver.getTrace(), PrimitiveTraceEntry.class);
			result.setTrace(inspectorObserver.getTrace(), InspectorTraceEntry.class);
			result.setTrace(fieldObserver.getTrace(), PrimitiveFieldTraceEntry.class);
			result.setTrace(nullObserver.getTrace(), NullTraceEntry.class);

		} catch (Exception e) {
			throw new Error(e);
		}

		return result;
	}

	/**
	 * Return a minimal subset of the assertions that covers all killable
	 * mutants
	 * 
	 * @param test
	 *            The test case that should be executed
	 * @param mutants
	 *            The list of mutants of the unit
	 * @param assertions
	 *            All assertions that can be generated for the test case
	 * @param killMap
	 *            Mapping of assertion to mutant ids that are killed by the
	 *            assertion
	 */
	private void minimize(TestCase test, List<Mutation> mutants,
	        List<Assertion> assertions, Map<Integer, Set<Integer>> killMap) {

		class Pair implements Comparable<Object> {
			Integer assertion;
			Integer num_killed;

			public Pair(int a, int k) {
				assertion = a;
				num_killed = k;
			}

			@Override
			public int compareTo(Object o) {
				Pair other = (Pair) o;
				if (num_killed.equals(other.num_killed))
					return assertion.compareTo(other.assertion);
				//				return other.assertion.compareTo(assertion);
				else
					return num_killed.compareTo(other.num_killed);
			}
		}
		Set<Integer> to_kill = new HashSet<Integer>();
		for (Entry<Integer, Set<Integer>> entry : killMap.entrySet()) {
			to_kill.addAll(entry.getValue());
		}
		logger.debug("Need to kill mutants: " + to_kill.size());

		Set<Integer> killed = new HashSet<Integer>();
		Set<Assertion> result = new HashSet<Assertion>();

		boolean done = false;
		while (!done) {
			// logger.info("Have to kill "+to_kill.size());
			List<Pair> a = new ArrayList<Pair>();
			for (Entry<Integer, Set<Integer>> entry : killMap.entrySet()) {
				int num = 0;
				for (Integer m : entry.getValue()) {
					if (!killed.contains(m))
						num++;
				}
				if (num > 0) {
					a.add(new Pair(entry.getKey(), num));
				}
			}
			if (a.isEmpty())
				done = true;
			else {
				Pair best = Collections.max(a);
				// logger.info("Chosen "+best.assertion);
				result.add(assertions.get(best.assertion));
				for (Integer m : killMap.get(best.assertion)) {
					// logger.info("Killed "+m);
					killed.add(m);
					to_kill.remove(m);
				}
			}
		}
		logger.debug("Killed mutants: " + killed.size());

		// sort by number of assertions killed
		// pick assertion that kills most
		// remove all mutations that are already killed
		logger.debug("Minimized assertions from " + assertions.size() + " to "
		        + result.size());

		if (!result.isEmpty()) {
			test.removeAssertions();

			for (Assertion assertion : result) {
				assertion.getStatement().addAssertion(assertion);
			}
		} else {
			logger.debug("Not removing assertions because no new assertions were found");
		}

	}

	/**
	 * <p>
	 * addAssertions
	 * </p>
	 * 
	 * @param test
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param mutant
	 *            a {@link org.evosuite.coverage.mutation.Mutation} object.
	 */
	public void addAssertions(TestCase test, Mutation mutant) {
		ExecutionResult origResult = runTest(test);
		ExecutionResult mutantResult = runTest(test, mutant);

		for (Class<?> observerClass : observerClasses) {
			origResult.getTrace(observerClass).getAssertions(test,
			                                                 mutantResult.getTrace(observerClass));
		}

		logger.debug("Generated " + test.getAssertions().size() + " assertions");
	}

	/**
	 * Generate assertions to kill all the mutants defined in the pool
	 * 
	 * @param test
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param killed
	 *            a {@link java.util.Set} object.
	 */
	public void addAssertions(TestCase test, Set<Integer> killed) {
		addAssertions(test, killed, mutants);
	}

	/**
	 * Add assertions to current test set for given set of mutants
	 * 
	 * @param test
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param killed
	 *            a {@link java.util.Set} object.
	 * @param mutants
	 *            a {@link java.util.Map} object.
	 */
	public void addAssertions(TestCase test, Set<Integer> killed,
	        Map<Integer, Mutation> mutants) {

		if (test.isEmpty())
			return;

		logger.debug("Generating assertions");

		int s1 = killed.size();

		logger.debug("Running on original");
		ExecutionResult origResult = runTest(test);

		if (origResult.hasTimeout() || origResult.hasTestException()) {
			logger.debug("Skipping test, as it has timeouts or exceptions");
			return;
		}

		Map<Mutation, List<OutputTrace<?>>> mutationTraces = new HashMap<Mutation, List<OutputTrace<?>>>();
		List<Mutation> executedMutants = new ArrayList<Mutation>();

		for (Integer mutationId : origResult.getTrace().getTouchedMutants()) {
			if (!mutants.containsKey(mutationId)) {
				//logger.warn("Mutation ID unknown: " + mutationId);
				//logger.warn(mutants.keySet().toString());
			} else
				executedMutants.add(mutants.get(mutationId));
		}

		Randomness.shuffle(executedMutants);

		int numExecutedMutants = 0;
		for (Mutation m : executedMutants) {

			numExecutedMutants++;

			assert (m != null);
			if (timedOutMutations.containsKey(m)) {
				if (timedOutMutations.get(m) >= Properties.MUTATION_TIMEOUTS) {
					logger.debug("Skipping timed out mutant");
					continue;
				}
			}
			if (Properties.MAX_MUTANTS_PER_TEST > 0
			        && numExecutedMutants > Properties.MAX_MUTANTS_PER_TEST)
				break;

			/*
			if (killed.contains(m.getId())) {
				logger.info("Skipping dead mutant");
				continue;
			}
			*/

			logger.debug("Running test " + test.hashCode() + " on mutation "
			        + m.getMutationName());
			ExecutionResult mutantResult = runTest(test, m);

			int numKilled = 0;
			for (Class<?> observerClass : observerClasses) {
				if (mutantResult.getTrace(observerClass) == null
				        || origResult.getTrace(observerClass) == null)
					continue;
				numKilled += origResult.getTrace(observerClass).getAssertions(test,
				                                                              mutantResult.getTrace(observerClass));
			}

			List<OutputTrace<?>> traces = new ArrayList<OutputTrace<?>>(
			        mutantResult.getTraces());
			mutationTraces.put(m, traces);

			if (mutantResult.hasTimeout()) {
				if (!timedOutMutations.containsKey(m)) {
					timedOutMutations.put(m, 1);
				} else {
					timedOutMutations.put(m, timedOutMutations.get(m) + 1);
				}
			}

			if (numKilled > 0 || mutantResult.hasTimeout()) {
				killed.add(m.getId());
			}
		}

		List<Assertion> assertions = test.getAssertions();
		logger.info("Got " + assertions.size() + " assertions");
		Map<Integer, Set<Integer>> killMap = new HashMap<Integer, Set<Integer>>();
		int num = 0;
		for (Assertion assertion : assertions) {
			Set<Integer> killedMutations = new HashSet<Integer>();
			for (Mutation m : executedMutants) {

				boolean isKilled = false;
				if (mutationTraces.containsKey(m)) {
					for (OutputTrace<?> trace : mutationTraces.get(m)) {
						if (trace.isDetectedBy(assertion)) {
							isKilled = true;
							break;
						}
					}
				}
				if (isKilled) {
					killedMutations.add(m.getId());
				}
			}
			killMap.put(num, killedMutations);
			//logger.info("Assertion " + num + " kills mutants " + killedMutations);
			num++;
		}

		int killedBefore = getNumKilledMutants(test, mutationTraces, executedMutants);

		logger.debug("Need to kill mutants: " + killedBefore);
		logger.debug(killMap.toString());
		minimize(test, executedMutants, assertions, killMap);

		int killedAfter = getNumKilledMutants(test, mutationTraces, executedMutants);

		int s2 = killed.size() - s1;
		assert (killedBefore == killedAfter) : "Mutants killed before / after / should be: "
		        + killedBefore + "/" + killedAfter + "/" + s2 + ": " + test.toCode();
		logger.info("Mutants killed before / after / should be: " + killedBefore + "/"
		        + killedAfter + "/" + s2);

		logger.info("Assertions in this test: "+test.getAssertions().size());
		//TestCase clone = test.clone();

		// IF there are no mutant killing assertions on the last statement, still assert something
		if (test.getStatement(test.size() - 1).getAssertions().isEmpty()
		        || justNullAssertion(test.getStatement(test.size() - 1))) {
			logger.info("Last statement has no assertions: " + test.toCode());

			if (test.getStatement(test.size() - 1).getAssertions().isEmpty()) {
				logger.debug("Last statement: "
				        + test.getStatement(test.size() - 1).getCode());
			}
			if (origResult.isThereAnExceptionAtPosition(test.size() - 1))
				logger.debug("Exception on last statement!");

			if (justNullAssertion(test.getStatement(test.size() - 1)))
				logger.debug("Just null assertions on last statement: " + test.toCode());

			boolean haveAssertion = false;
			for (Assertion assertion : assertions) {
				if (assertion instanceof PrimitiveAssertion) {
					if (assertion.getStatement().equals(test.getStatement(test.size() - 1))) {
						logger.debug("Adding a primitive assertion " + assertion);
						test.getStatement(test.size() - 1).addAssertion(assertion);
						haveAssertion = true;
						break;
					}
				}
			}
			if (!haveAssertion) {
				for (Assertion assertion : assertions) {
					if (assertion.getStatement().equals(test.getStatement(test.size() - 1))) {
						logger.info("Adding a assertion: " + assertion);
						test.getStatement(test.size() - 1).addAssertion(assertion);
						haveAssertion = true;
						break;
					}
				}
			}

			if (!test.hasAssertions()) {
				Method inspectorMethod = null;
				if (test.getStatement(test.size() - 1) instanceof MethodStatement) {
					MethodStatement methodStatement = (MethodStatement) test.getStatement(test.size() - 1);
					Method method = methodStatement.getMethod();
					if (method.getParameterTypes().length == 0) {
						if (method.getReturnType().isPrimitive()
						        && !method.getReturnType().equals(void.class)) {
							inspectorMethod = method;
						}
					}
				}
				for (OutputTrace<?> trace : origResult.getTraces()) {
					trace.getAllAssertions(test);
				}

				Set<Assertion> target = new HashSet<Assertion>(
				        test.getStatement(test.size() - 1).getAssertions());
				logger.debug("Found assertions: " + target.size());

				test.removeAssertions();
				//test.addAssertions(clone);
				VariableReference targetVar = test.getStatement(test.size() - 1).getReturnValue();
				if (!targetVar.isVoid()) {
					logger.debug("Return value is non void: " + targetVar.getClassName());

					int maxAssertions = 1;
					int numAssertions = 0;
					for (Assertion ass : target) {
						if (ass.getReferencedVariables().contains(targetVar)
						        && !(ass instanceof NullAssertion)) {

							if (ass instanceof InspectorAssertion) {
								if (((InspectorAssertion) ass).inspector.getMethod().equals(inspectorMethod)) {
									continue;
								}
							}

							test.getStatement(test.size() - 1).addAssertion(ass);
							logger.debug("Adding assertion " + ass.getCode());
							if (++numAssertions >= maxAssertions)
								break;
						} else {
							logger.debug("Assertion does not contain target: "
							        + ass.getCode());
						}
					}
					if (numAssertions == 0) {
						for (Assertion ass : target) {
							if (ass.getReferencedVariables().contains(targetVar)) {

								test.getStatement(test.size() - 1).addAssertion(ass);
								logger.debug("Adding assertion " + ass.getCode());
								if (++numAssertions >= maxAssertions)
									break;
							} else {
								logger.debug("Assertion does not contain target: "
								        + ass.getCode());
							}
						}
					}
				} else {
					logger.debug("Return value is void");

					Set<VariableReference> targetVars = test.getStatement(test.size() - 1).getVariableReferences();
					int maxAssertions = 1;
					int numAssertions = 0;
					for (Assertion ass : target) {
						Set<VariableReference> vars = ass.getReferencedVariables();
						vars.retainAll(targetVars);
						if (!vars.isEmpty()) {

							test.getStatement(test.size() - 1).addAssertion(ass);
							if (++numAssertions >= maxAssertions)
								break;
						}
					}

				}
			}
		}

		if (!origResult.noThrownExceptions()) {
			if (!test.getStatement(test.size() - 1).getAssertions().isEmpty()) {
				logger.debug("Removing assertions after exception");
				test.getStatement(test.size() - 1).removeAssertions();
			}
		}

	}

	/**
	 * @param test
	 * @param mutation_traces
	 * @param executedMutants
	 * @return
	 */
	private int getNumKilledMutants(TestCase test,
	        Map<Mutation, List<OutputTrace<?>>> mutation_traces,
	        List<Mutation> executedMutants) {
		List<Assertion> assertions;
		Set<Integer> killed = new HashSet<Integer>();
		assertions = test.getAssertions();
		for (Assertion assertion : assertions) {
			for (Mutation m : executedMutants) {

				boolean isKilled = false;
				if (mutation_traces.containsKey(m)) {
					int i = 0;
					for (OutputTrace<?> trace : mutation_traces.get(m)) {
						isKilled = trace.isDetectedBy(assertion);
						if (isKilled) {
							logger.debug("Mutation killed: " + m.getId() + " by trace "
							        + i++);
							killed.add(m.getId());
							break;
						}
						i++;
					}
				} else {
					isKilled = true;
				}
			}
		}
		logger.debug("Killed mutants: " + killed);
		return killed.size();
	}

	private boolean justNullAssertion(StatementInterface statement) {
		Set<Assertion> assertions = statement.getAssertions();
		if (assertions.isEmpty())
			return false;
		else {
			Iterator<Assertion> iterator = assertions.iterator();
			VariableReference ret = statement.getReturnValue();
			boolean just = true;
			while (iterator.hasNext()) {
				Assertion ass = iterator.next();
				if (!(ass instanceof NullAssertion)) {
					if (ass.getReferencedVariables().contains(ret)) {
						just = false;
						break;
					}
				}
			}

			return just;
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.AssertionGenerator#addAssertions(org.evosuite.testcase.TestCase)
	 */
	/** {@inheritDoc} */
	@Override
	public void addAssertions(TestCase test) {
		// TODO Auto-generated method stub

	}

}
