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

package de.unisb.cs.st.evosuite.testcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.TestSuiteGenerator;
import de.unisb.cs.st.evosuite.coverage.branch.Branch;
import de.unisb.cs.st.evosuite.coverage.concurrency.ConcurrencyTracer;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUse;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUsePool;
import de.unisb.cs.st.evosuite.coverage.dataflow.Definition;
import de.unisb.cs.st.evosuite.coverage.dataflow.Use;

/**
 * Keep a trace of the program execution
 * 
 * @author Gordon Fraser
 * 
 */
public class ExecutionTrace {

	private static Logger logger = LoggerFactory.getLogger(ExecutionTrace.class);

	public static boolean trace_calls = false;

	public static void disableTraceCalls() {
		trace_calls = false;
	}

	public static void enableTraceCalls() {
		trace_calls = true;
	}

	// used schedule
	//#TODO steenbuck this should be somewhere else. This is not nice. We should be able to infer from THIS if concurrencyTracer is filled
	public ConcurrencyTracer concurrencyTracer;

	public static class MethodCall {
		public String className;
		public String methodName;
		public List<Integer> lineTrace;
		public List<Integer> branchTrace;
		public List<Double> trueDistanceTrace;
		public List<Double> falseDistanceTrace;
		public List<Integer> defuseCounterTrace;
		public int methodId;
		public int callingObjectID;
		public int callDepth;

		public MethodCall(String className, String methodName, int methodId,
		        int callingObjectID, int callDepth) {
			this.className = className;
			this.methodName = methodName;
			lineTrace = new ArrayList<Integer>();
			branchTrace = new ArrayList<Integer>();
			trueDistanceTrace = new ArrayList<Double>();
			falseDistanceTrace = new ArrayList<Double>();
			defuseCounterTrace = new ArrayList<Integer>();
			this.methodId = methodId;
			this.callingObjectID = callingObjectID;
			this.callDepth = callDepth;
		}

		@Override
		public String toString() {
			StringBuffer ret = new StringBuffer();
			ret.append(className);
			ret.append(":");
			ret.append(methodName);
			ret.append("\n");
			// ret.append("Lines: ");
			// for(Integer line : line_trace) {
			// ret.append(" "+line);
			// }
			// ret.append("\n");
			ret.append("Branches: ");
			for (Integer branch : branchTrace) {
				ret.append(" " + branch);
			}
			ret.append("\n");
			ret.append("True Distances: ");
			for (Double distance : trueDistanceTrace) {
				ret.append(" " + distance);
			}
			ret.append("\nFalse Distances: ");
			for (Double distance : falseDistanceTrace) {
				ret.append(" " + distance);
			}
			ret.append("\n");
			return ret.toString();
		}

		public String explain() {
			// TODO StringBuilder-explain() functions to construct string templates like explainList()
			StringBuffer r = new StringBuffer();
			r.append(className);
			r.append(":");
			r.append(methodName);
			r.append("\n");
			r.append("Lines: ");
			if (lineTrace == null) {
				r.append("null");
			} else {
				for (Integer line : lineTrace) {
					r.append("\t" + line);
				}
				r.append("\n");
			}
			r.append("Branches: ");
			if (branchTrace == null) {
				r.append("null");
			} else {
				for (Integer branch : branchTrace) {
					r.append("\t" + branch);
				}
				r.append("\n");
			}
			r.append("True Distances: ");
			if (trueDistanceTrace == null) {
				r.append("null");
			} else {
				for (Double distance : trueDistanceTrace) {
					r.append("\t" + distance);
				}
				r.append("\n");
			}
			r.append("False Distances: ");
			if (falseDistanceTrace == null) {
				r.append("null");
			} else {
				for (Double distance : falseDistanceTrace) {
					r.append("\t" + distance);
				}
				r.append("\n");
			}
			r.append("DefUse Trace:");
			if (defuseCounterTrace == null) {
				r.append("null");
			} else {
				for (Integer duCounter : defuseCounterTrace) {
					r.append("\t" + duCounter);
				}
				r.append("\n");
			}
			return r.toString();
		}

		@Override
		public MethodCall clone() {
			MethodCall copy = new MethodCall(className, methodName, methodId,
			        callingObjectID, callDepth);
			copy.lineTrace = new ArrayList<Integer>(lineTrace);
			copy.branchTrace = new ArrayList<Integer>(branchTrace);
			copy.trueDistanceTrace = new ArrayList<Double>(trueDistanceTrace);
			copy.falseDistanceTrace = new ArrayList<Double>(falseDistanceTrace);
			copy.defuseCounterTrace = new ArrayList<Integer>(defuseCounterTrace);
			return copy;
		}
	}

	public static boolean trace_coverage = true;

	public static void enableTraceCoverage() {
		trace_coverage = true;
	}

	private static void checkSaneCall(MethodCall call) {
		if (!((call.trueDistanceTrace.size() == call.falseDistanceTrace.size())
		        && (call.falseDistanceTrace.size() == call.defuseCounterTrace.size()) && (call.defuseCounterTrace.size() == call.branchTrace.size()))) {
			throw new IllegalStateException(
			        "insane MethodCall: traces should all be of equal size. "
			                + call.explain());
		}

	}

	/**
	 * Removes from the given ExecutionTrace all finished_calls with an index in
	 * removableCalls
	 */
	private static void removeFinishCalls(ExecutionTrace trace,
	        ArrayList<Integer> removableCalls) {
		Collections.sort(removableCalls);
		for (int i = removableCalls.size() - 1; i >= 0; i--) {
			int toRemove = removableCalls.get(i);
			MethodCall removed = trace.finished_calls.remove(toRemove);
			if (removed == null) {
				throw new IllegalStateException(
				        "trace.finished_calls not allowed to contain null");
			}
		}
	}

	/**
	 * Removes from the given MethodCall all trace information with an index in
	 * removableIndices
	 */
	private static void removeFromFinishCall(MethodCall call,
	        ArrayList<Integer> removableIndices) {
		checkSaneCall(call);

		Collections.sort(removableIndices);
		for (int i = removableIndices.size() - 1; i >= 0; i--) {
			int removableIndex = removableIndices.get(i);
			Integer removedBranch = call.branchTrace.remove(removableIndex);
			Double removedTrue = call.trueDistanceTrace.remove(removableIndex);
			Double removedFalse = call.falseDistanceTrace.remove(removableIndex);
			Integer removedCounter = call.defuseCounterTrace.remove(removableIndex);
			if ((removedCounter == null) || (removedBranch == null)
			        || (removedTrue == null) || (removedFalse == null)) {
				throw new IllegalStateException(
				        "trace.finished_calls-traces not allowed to contain null");
			}
		}
	}

	// finished_calls;
	public List<MethodCall> finished_calls = Collections.synchronizedList(new ArrayList<MethodCall>());

	// active calls
	Deque<MethodCall> stack = new LinkedList<MethodCall>();

	// Coverage information
	public Map<String, Map<String, Map<Integer, Integer>>> coverage = Collections.synchronizedMap(new HashMap<String, Map<String, Map<Integer, Integer>>>());

	// Data information
	public Map<String, Map<String, Map<Integer, Integer>>> return_data = Collections.synchronizedMap(new HashMap<String, Map<String, Map<Integer, Integer>>>());

	// for each Variable-Name these maps hold the data for which objectID
	// at which time (duCounter) which Definition or Use was passed
	public Map<String, HashMap<Integer, HashMap<Integer, Integer>>> passedDefinitions = Collections.synchronizedMap(new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>());
	public Map<String, HashMap<Integer, HashMap<Integer, Integer>>> passedUses = Collections.synchronizedMap(new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>());

	public Map<String, Integer> covered_methods = Collections.synchronizedMap(new HashMap<String, Integer>());
	public Map<Integer, Integer> covered_predicates = Collections.synchronizedMap(new HashMap<Integer, Integer>());
	public Map<Integer, Integer> covered_true = Collections.synchronizedMap(new HashMap<Integer, Integer>());
	public Map<Integer, Integer> covered_false = Collections.synchronizedMap(new HashMap<Integer, Integer>());
	public Map<Integer, Double> true_distances = Collections.synchronizedMap(new HashMap<Integer, Double>());
	public Map<Integer, Double> false_distances = Collections.synchronizedMap(new HashMap<Integer, Double>());
	public Map<Integer, Double> mutant_distances = Collections.synchronizedMap(new HashMap<Integer, Double>());
	public Set<Integer> touchedMutants = Collections.synchronizedSet(new HashSet<Integer>());

	public Map<Integer, Double> true_distances_sum = Collections.synchronizedMap(new HashMap<Integer, Double>());
	public Map<Integer, Double> false_distances_sum = Collections.synchronizedMap(new HashMap<Integer, Double>());

	// number of seen Definitions and uses for indexing purposes
	private int duCounter = 0;

	// for defuse-coverage it is important to keep track of all the objects that called the ExecutionTracer
	private int objectCounter = 0;

	public Map<Integer, Object> knownCallerObjects = Collections.synchronizedMap(new HashMap<Integer, Object>());

	// to differentiate between different MethodCalls
	private int methodId = 0;

	public ExecutionTrace() {
		stack.add(new MethodCall("", "", 0, 0, 0)); // Main method
	}

	/**
	 * Add branch to currently active method call
	 * 
	 * @param branch
	 * @param true_distance
	 * @param false_distance
	 */
	public void branchPassed(int branch, int bytecode_id, double true_distance,
	        double false_distance) {

		assert (true_distance >= 0.0);
		assert (false_distance >= 0.0);
		updateTopStackMethodCall(branch, bytecode_id, true_distance, false_distance);

		if (trace_coverage) {
			if (!covered_predicates.containsKey(branch))
				covered_predicates.put(branch, 1);
			else
				covered_predicates.put(branch, covered_predicates.get(branch) + 1);

			if (true_distance == 0.0) {
				if (!covered_true.containsKey(branch))
					covered_true.put(branch, 1);
				else
					covered_true.put(branch, covered_true.get(branch) + 1);

			}

			if (false_distance == 0.0) {
				if (!covered_false.containsKey(branch))
					covered_false.put(branch, 1);
				else
					covered_false.put(branch, covered_false.get(branch) + 1);
			}
		}

		if (!true_distances.containsKey(branch))
			true_distances.put(branch, true_distance);
		else
			true_distances.put(branch,
			                   Math.min(true_distances.get(branch), true_distance));

		if (!false_distances.containsKey(branch))
			false_distances.put(branch, false_distance);
		else
			false_distances.put(branch,
			                    Math.min(false_distances.get(branch), false_distance));

		if (!true_distances_sum.containsKey(branch))
			true_distances_sum.put(branch, true_distance);
		else
			true_distances_sum.put(branch, true_distances_sum.get(branch) + true_distance);

		if (!false_distances_sum.containsKey(branch))
			false_distances_sum.put(branch, false_distance);
		else
			false_distances_sum.put(branch, false_distances_sum.get(branch)
			        + false_distance);

		branchesTrace.add(new BranchEval(branch, true_distance, false_distance));
	}

	public static class BranchEval {
		private final int branchId;
		private final double trueDistance;
		private final double falseDistance;

		public BranchEval(int branchId, double trueDistance, double falseDistance) {
			this.branchId = branchId;
			this.trueDistance = trueDistance;
			this.falseDistance = falseDistance;
		}

		public int getBranchId() {
			return branchId;
		}

		public double getTrueDistance() {
			return trueDistance;
		}

		public double getFalseDistance() {
			return falseDistance;
		}

		@Override
		public String toString() {
			return "BranchEval [branchId=" + branchId + ", trueDistance=" + trueDistance
			        + ", falseDistance=" + falseDistance + "]";
		}
	}

	private List<BranchEval> branchesTrace = new ArrayList<BranchEval>();

	public List<BranchEval> getBranchesTrace() {
		return branchesTrace;
	}

	/**
	 * Reset to 0
	 */
	public void clear() {
		finished_calls = new ArrayList<MethodCall>();
		stack = new LinkedList<MethodCall>();

		// stack.clear();
		// finished_calls.clear();
		stack.add(new MethodCall("", "", 0, 0, 0)); // Main method
		coverage = new HashMap<String, Map<String, Map<Integer, Integer>>>();
		return_data = new HashMap<String, Map<String, Map<Integer, Integer>>>();

		methodId = 0;
		duCounter = 0;
		objectCounter = 0;
		knownCallerObjects = new HashMap<Integer, Object>();
		true_distances = new HashMap<Integer, Double>();
		false_distances = new HashMap<Integer, Double>();
		covered_methods = new HashMap<String, Integer>();
		covered_predicates = new HashMap<Integer, Integer>();
		covered_true = new HashMap<Integer, Integer>();
		covered_false = new HashMap<Integer, Integer>();
		passedDefinitions = new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>();
		passedUses = new HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>();
		branchesTrace = new ArrayList<BranchEval>();
	}

	/**
	 * Create a deep copy
	 */
	@Override
	public ExecutionTrace clone() {

		ExecutionTrace copy = new ExecutionTrace();
		for (MethodCall call : finished_calls) {
			copy.finished_calls.add(call.clone());
		}
		// copy.finished_calls.addAll(finished_calls);
		copy.coverage = new HashMap<String, Map<String, Map<Integer, Integer>>>();
		if (coverage != null) {
			copy.coverage.putAll(coverage);
		}
		copy.return_data = new HashMap<String, Map<String, Map<Integer, Integer>>>();
		copy.return_data.putAll(return_data);
		/*
		 * if(stack != null && !stack.isEmpty() && stack.peek().method_name !=
		 * null && stack.peek().method_name.equals("")) {
		 * logger.info("Copying main method");
		 * copy.finished_calls.add(stack.peek()); }
		 */
		copy.true_distances.putAll(true_distances);
		copy.false_distances.putAll(false_distances);
		copy.covered_methods.putAll(covered_methods);
		copy.covered_predicates.putAll(covered_predicates);
		copy.covered_true.putAll(covered_true);
		copy.covered_false.putAll(covered_false);
		copy.touchedMutants.addAll(touchedMutants);
		copy.mutant_distances.putAll(mutant_distances);
		copy.passedDefinitions.putAll(passedDefinitions);
		copy.passedUses.putAll(passedUses);
		copy.methodId = methodId;
		copy.duCounter = duCounter;
		copy.objectCounter = objectCounter;
		copy.knownCallerObjects.putAll(knownCallerObjects);
		return copy;
	}

	/**
	 * Adds Definition-Use-Coverage trace information for the given definition.
	 * 
	 * Registers the given caller-Object Traces the occurrence of the given
	 * definition in the passedDefs-field Sets the given definition as the
	 * currently active one for the definitionVariable in the
	 * activeDefinitions-field Adds fake trace information to the currently
	 * active MethodCall in this.stack
	 */
	public void definitionPassed(Object caller, int defID) {

		if (!trace_calls) {
			return;
		}

		Definition def = DefUsePool.getDefinitionByDefId(defID);
		String varName = def.getDUVariableName();

		if (def == null) {
			throw new IllegalStateException(
			        "expect DefUsePool to known defIDs that are passed by instrumented code");
		}

		int objectID = registerObject(caller);

		// if this is a static variable, treat objectID as zero for consistency in the representation of static data
		if (objectID != 0 && def.isStaticDefUse())
			objectID = 0;
		if (passedDefinitions.get(varName) == null)
			passedDefinitions.put(varName,
			                      new HashMap<Integer, HashMap<Integer, Integer>>());
		HashMap<Integer, Integer> defs = passedDefinitions.get(varName).get(objectID);
		if (defs == null)
			defs = new HashMap<Integer, Integer>();
		defs.put(duCounter, defID);
		passedDefinitions.get(varName).put(objectID, defs);

		//		logger.trace(duCounter+": set active definition for var "+def.getDUVariableName()+" on object "+objectID+" to Def "+defID);
		duCounter++;
	}

	/**
	 * Add a new method call to stack
	 * 
	 * @param className
	 * @param methodName
	 */
	public void enteredMethod(String className, String methodName, Object caller) {
		if (trace_coverage) {
			String id = className + "." + methodName;
			if (!covered_methods.containsKey(id)) {
				covered_methods.put(id, 1);
			} else {
				covered_methods.put(id, covered_methods.get(id) + 1);
			}
		}
		if (trace_calls) {
			int callingObjectID = registerObject(caller);
			methodId++;
			MethodCall call = new MethodCall(className, methodName, methodId,
			        callingObjectID, stack.size());
			if (Properties.CRITERION == Criterion.DEFUSE
			        || Properties.CRITERION == Criterion.ALLDEFS
			        || TestSuiteGenerator.analyzing) {
				call.branchTrace.add(-1);
				call.trueDistanceTrace.add(1.0);
				call.falseDistanceTrace.add(0.0);
				call.defuseCounterTrace.add(duCounter);
				// TODO line_trace ?
			}
			stack.push(call);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExecutionTrace other = (ExecutionTrace) obj;
		if (coverage == null) {
			if (other.coverage != null) {
				return false;
			}
		} else if (!coverage.equals(other.coverage)) {
			return false;
		}
		if (finished_calls == null) {
			if (other.finished_calls != null) {
				return false;
			}
		} else if (!finished_calls.equals(other.finished_calls)) {
			return false;
		}
		if (return_data == null) {
			if (other.return_data != null) {
				return false;
			}
		} else if (!return_data.equals(other.return_data)) {
			return false;
		}
		if (stack == null) {
			if (other.stack != null) {
				return false;
			}
		} else if (!stack.equals(other.stack)) {
			return false;
		}
		return true;
	}

	/**
	 * Pop last method call from stack
	 * 
	 * @param classname
	 * @param methodname
	 */
	public void exitMethod(String classname, String methodname) {
		if (trace_calls) {
			if (!stack.isEmpty() && !(stack.peek().methodName.equals(methodname))) {
				logger.debug("Expecting " + stack.peek().methodName + ", got "
				        + methodname);

				if (stack.peek().methodName.equals("")
				        && !stack.peek().branchTrace.isEmpty()) {
					logger.debug("Found main method");
					finished_calls.add(stack.pop());
				} else {
					logger.debug("Bugger!");
					// Usually, this happens if we use mutation testing and the
					// mutation
					// causes an unexpected exception or timeout
					stack.pop();
				}
			} else {
				finished_calls.add(stack.pop());
			}
		}
	}

	public void finishCalls() {
		logger.debug("At the end, we have " + stack.size() + " calls left on stack");
		while (!stack.isEmpty()) {
			finished_calls.add(stack.pop());
		}
	}

	/**
	 * Returns a copy of this trace where all MethodCall-information traced from
	 * objects other then the one identified by the given objectID is removed
	 * from the finished_calls-field
	 * 
	 * WARNING: this will not affect this.true_distances and other fields of
	 * ExecutionTrace this only affects the finished_calls field (which should
	 * suffice for BranchCoverageFitness-calculation)
	 */
	public ExecutionTrace getTraceForObject(int objectId) {
		ExecutionTrace r = clone();
		ArrayList<Integer> removableCalls = new ArrayList<Integer>();
		for (int i = 0; i < r.finished_calls.size(); i++) {
			MethodCall call = r.finished_calls.get(i);
			if ((call.callingObjectID != objectId) && (call.callingObjectID != 0)) {
				removableCalls.add(i);
			}
		}
		removeFinishCalls(r, removableCalls);
		return r;
	}

	/**
	 * Returns a copy of this trace where all MethodCall-information associated
	 * with duCounters outside the range of the given duCounter-Start and -End
	 * is removed from the finished_calls-traces
	 * 
	 * finished_calls without any point in the trace at which the given
	 * duCounter range is hit are removed completely
	 * 
	 * Also traces for methods other then the one that holds the given targetDU
	 * are removed as well as trace information that would pass the branch of
	 * the given targetDU If wantToCoverTargetDU is false instead those
	 * targetDUBranch information is removed that would pass the alternative
	 * branch of targetDU
	 * 
	 * The latter is because this method only gets called when the given
	 * targetDU was not active in the given duCounter-range if and only if
	 * wantToCoverTargetDU is set, and since useFitness calculation is on branch
	 * level and the branch of the targetDU can be passed before the targetDU is
	 * passed this can lead to a flawed branchFitness.
	 * 
	 * 
	 * WARNING: this will not affect this.true_distances and other fields of
	 * ExecutionTrace this only affects the finished_calls field (which should
	 * suffice for BranchCoverageFitness-calculation)
	 */
	public ExecutionTrace getTraceInDUCounterRange(DefUse targetDU,
	        boolean wantToCoverTargetDU, int duCounterStart, int duCounterEnd) {

		if (duCounterStart > duCounterEnd) {
			throw new IllegalArgumentException("start has to be lesser or equal end");
			/*
			// DONE: bug
			// this still has a major flaw: s. MeanTestClass.mean():
			// right now its like we map branches to activeDefenitions
			// but especially in the root branch of a method
			// activeDefenitions change during execution time
			// FIX: in order to avoid these false positives remove all information 
			//		for a certain branch if some information for that branch is supposed to be removed
			//  subTodo	since branchPassed() only gets called when a branch is passed initially
			// 			fake calls to branchPassed() have to be made whenever a DU is passed 
			// 			s. definitionPassed(), usePassed() and addFakeActiveMethodCallInformation()

			// DONE: new bug
			// 	turns out thats an over-approximation that makes it 
			// 	impossible to cover some potentially coverable goals
			
			// completely new:
			// if your definition gets overwritten in a trace
			// the resulting fitness should be the fitness of not taking the branch with the overwriting definition
			// DONE: in order to do that don't remove older trace information for an overwritten branch
			// 		 but rather set the true and false distance of that previous branch information to the distance of not taking the overwriting branch
			// done differently: s. DefUseCoverageTestFitness.getFitness()
			 */
		}

		ExecutionTrace r = clone();
		Branch targetDUBranch = targetDU.getControlDependentBranch();
		ArrayList<Integer> removableCalls = new ArrayList<Integer>();
		for (int callPos = 0; callPos < r.finished_calls.size(); callPos++) {
			MethodCall call = r.finished_calls.get(callPos);
			// check if call is for the method of targetDU
			if (!call.methodName.equals(targetDU.getMethodName())) {
				removableCalls.add(callPos);
				continue;
			}
			ArrayList<Integer> removableIndices = new ArrayList<Integer>();
			for (int i = 0; i < call.defuseCounterTrace.size(); i++) {
				int currentDUCounter = call.defuseCounterTrace.get(i);
				int currentBranchBytecode = call.branchTrace.get(i);

				if (currentDUCounter < duCounterStart || currentDUCounter > duCounterEnd)
					removableIndices.add(i);
				else if (currentBranchBytecode == targetDUBranch.getInstruction().getInstructionId()) {
					// only remove this point in the trace if it would cover targetDU
					boolean targetExpressionValue = targetDU.getControlDependentBranchExpressionValue();
					if (targetExpressionValue) {
						if (call.trueDistanceTrace.get(i) == 0.0)
							removableIndices.add(i);
					} else {
						if (call.falseDistanceTrace.get(i) == 0.0)
							removableIndices.add(i);
					}

				}
			}
			removeFromFinishCall(call, removableIndices);
			if (call.defuseCounterTrace.size() == 0)
				removableCalls.add(callPos);
		}
		removeFinishCalls(r, removableCalls);
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coverage == null) ? 0 : coverage.hashCode());
		result = prime * result
		        + ((finished_calls == null) ? 0 : finished_calls.hashCode());
		result = prime * result + ((return_data == null) ? 0 : return_data.hashCode());
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		return result;
	}

	/**
	 * Add line to currently active method call
	 * 
	 * @param line
	 */
	public void linePassed(String className, String methodName, int line) {
		if (trace_calls) {
			if (stack.isEmpty()) {
				logger.warn("Method stack is empty: " + className + "." + methodName
				        + " - l" + line); // TODO switch back
				// logger.debug to
				// logger.warn
			} else {
				boolean empty = false;
				if (!stack.peek().methodName.equals(methodName)) {
					if (stack.peek().methodName.equals(""))
						return;

					logger.warn("Popping method " + stack.peek().methodName
					        + " because we were looking for " + methodName);
					finished_calls.add(stack.pop());
					if (stack.isEmpty()) {
						logger.warn("Method stack is empty: " + className + "."
						        + methodName + " - l" + line); // TODO switch back
						empty = true;
					}
				}
				if (!empty)
					stack.peek().lineTrace.add(line);
			}
		}
		if (trace_coverage) {
			if (!coverage.containsKey(className)) {
				coverage.put(className, new HashMap<String, Map<Integer, Integer>>());
			}

			if (!coverage.get(className).containsKey(methodName)) {
				coverage.get(className).put(methodName, new HashMap<Integer, Integer>());
			}

			if (!coverage.get(className).get(methodName).containsKey(line)) {
				coverage.get(className).get(methodName).put(line, 1);
			} else {
				coverage.get(className).get(methodName).put(line,
				                                            coverage.get(className).get(methodName).get(line) + 1);
			}
		}
	}

	public void mutationPassed(int mutationId, double distance) {

		touchedMutants.add(mutationId);
		if (!mutant_distances.containsKey(mutationId)) {
			mutant_distances.put(mutationId, distance);
		} else {
			mutant_distances.put(mutationId,
			                     Math.min(distance, mutant_distances.get(mutationId)));
		}
	}

	public void returnValue(String className, String methodName, int value) {
		if (!return_data.containsKey(className)) {
			return_data.put(className, new HashMap<String, Map<Integer, Integer>>());
		}

		if (!return_data.get(className).containsKey(methodName)) {
			return_data.get(className).put(methodName, new HashMap<Integer, Integer>());
		}

		if (!return_data.get(className).get(methodName).containsKey(value)) {
			// logger.info("Got return value "+value);
			return_data.get(className).get(methodName).put(value, 1);
		} else {
			// logger.info("Got return value again "+value);
			return_data.get(className).get(methodName).put(value,
			                                               return_data.get(className).get(methodName).get(value) + 1);
		}
	}

	/**
	 * Returns a String containing the information in passedDefs and passedUses
	 * 
	 * Used for Definition-Use-Coverage-debugging
	 */
	public String toDefUseTraceInformation() {
		StringBuffer r = new StringBuffer();
		for (String var : passedDefinitions.keySet()) {
			r.append("  for variable: " + var + ": ");
			for (Integer objectId : passedDefinitions.get(var).keySet()) {
				if (passedDefinitions.get(var).keySet().size() > 1) {
					r.append("\n\ton object " + objectId + ": ");
				}
				r.append(toDefUseTraceInformation(var, objectId));
			}
			r.append("\n  ");
		}
		return r.toString();
	}

	/**
	 * Returns a String containing the information in passedDefs and passedUses
	 * filtered for a specific variable
	 * 
	 * Used for Definition-Use-Coverage-debugging
	 */
	public String toDefUseTraceInformation(String targetVar) {
		StringBuffer r = new StringBuffer();
		for (Integer objectId : passedDefinitions.get(targetVar).keySet()) {
			if (passedDefinitions.get(targetVar).keySet().size() > 1) {
				r.append("\n\ton object " + objectId + ": ");
			}
			r.append(toDefUseTraceInformation(targetVar, objectId));
		}
		r.append("\n  ");

		return r.toString();
	}

	/**
	 * Returns a String containing the information in passedDefs and passedUses
	 * for the given variable
	 * 
	 * Used for Definition-Use-Coverage-debugging
	 */
	public String toDefUseTraceInformation(String var, int objectId) {
		if (passedDefinitions.get(var) == null) {
			return "";
		}
		if ((objectId == -1) && (passedDefinitions.get(var).keySet().size() == 1)) {
			objectId = (Integer) passedDefinitions.get(var).keySet().toArray()[0];
		}
		if (passedDefinitions.get(var).get(objectId) == null) {
			return "";
		}
		// gather all DUs
		String[] duTrace = new String[this.duCounter];
		for (int i = 0; i < this.duCounter; i++) {
			duTrace[i] = "";
		}
		for (Integer duPos : passedDefinitions.get(var).get(objectId).keySet()) {
			duTrace[duPos] = "(" + duPos + ":Def "
			        + passedDefinitions.get(var).get(objectId).get(duPos) + ")";
		}
		if ((passedUses.get(var) != null) && (passedUses.get(var).get(objectId) != null)) {
			for (Integer duPos : passedUses.get(var).get(objectId).keySet()) {
				duTrace[duPos] = "(" + duPos + ":Use "
				        + passedUses.get(var).get(objectId).get(duPos) + ")";
			}
		}
		// build up the String
		StringBuffer r = new StringBuffer();
		for (String s : duTrace) {
			r.append(s);
			if (s.length() > 0) {
				r.append(", ");
			}
		}
		// remove last ", "
		String traceString = r.toString();
		if (traceString.length() > 2) {
			return traceString.substring(0, traceString.length() - 2);
		}
		return traceString;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		for (MethodCall m : finished_calls) {
			ret.append(m);
		}
		ret.append("\nCovered methods: ");
		for (Entry<String, Integer> entry : covered_methods.entrySet()) {
			ret.append(entry.getKey() + ": " + entry.getValue() + ", ");
		}
		ret.append("\nCovered predicates: ");
		for (Entry<Integer, Integer> entry : covered_predicates.entrySet()) {
			ret.append(entry.getKey() + ": " + entry.getValue() + ", ");
		}
		ret.append("\nTrue distances: ");
		for (Entry<Integer, Double> entry : true_distances.entrySet()) {
			ret.append(entry.getKey() + ": " + entry.getValue() + ", ");
		}
		ret.append("\nFalse distances: ");
		for (Entry<Integer, Double> entry : false_distances.entrySet()) {
			ret.append(entry.getKey() + ": " + entry.getValue() + ", ");
		}
		return ret.toString();
	}

	/**
	 * Adds Definition-Use-Coverage trace information for the given use.
	 * 
	 * Registers the given caller-Object Traces the occurrence of the given use
	 * in the passedUses-field
	 */
	public void usePassed(Object caller, int useID) {

		if (!trace_calls) // TODO ???
			return;

		Use use = DefUsePool.getUseByUseId(useID);
		String varName = use.getDUVariableName();

		int objectID = registerObject(caller);
		// if this is a static variable, treat objectID as zero for consistency in the representation of static data
		if (objectID != 0) {
			if (use == null)
				throw new IllegalStateException(
				        "expect DefUsePool to known defIDs that are passed by instrumented code");
			if (use.isStaticDefUse())
				objectID = 0;
		}
		if (passedUses.get(varName) == null)
			passedUses.put(varName, new HashMap<Integer, HashMap<Integer, Integer>>());

		HashMap<Integer, Integer> uses = passedUses.get(varName).get(objectID);
		if (uses == null)
			uses = new HashMap<Integer, Integer>();

		uses.put(duCounter, useID);
		passedUses.get(varName).put(objectID, uses);
		duCounter++;
	}

	/**
	 * Returns the objecectId for the given object.
	 * 
	 * The ExecutionTracer keeps track of all objects it gets called from in
	 * order to distinguish them later in the fitness calculation for the
	 * defuse-Coverage-Criterion.
	 */
	private int registerObject(Object caller) {
		if (caller == null) {
			return 0;
		}
		for (Integer objectId : knownCallerObjects.keySet()) {
			if (knownCallerObjects.get(objectId) == caller) {
				return objectId;
			}
		}
		// object unknown so far
		objectCounter++;
		knownCallerObjects.put(objectCounter, caller);
		return objectCounter;
	}

	/**
	 * Adds trace information to the active MethodCall in this.stack
	 */
	private void updateTopStackMethodCall(int branch, int bytecode_id,
	        double true_distance, double false_distance) {

		if (trace_calls) {
			stack.peek().branchTrace.add(branch); // was: bytecode_id
			stack.peek().trueDistanceTrace.add(true_distance);
			stack.peek().falseDistanceTrace.add(false_distance);
			assert ((true_distance == 0.0) || (false_distance == 0.0));
			// TODO line_trace ?
			if (Properties.CRITERION == Criterion.DEFUSE
			        || Properties.CRITERION == Criterion.ALLDEFS
			        || TestSuiteGenerator.analyzing) {
				stack.peek().defuseCounterTrace.add(duCounter);
			}
		}
	}

}
