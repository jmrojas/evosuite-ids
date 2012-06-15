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
package de.unisb.cs.st.evosuite.graphs.cfg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.objectweb.asm.tree.LabelNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.coverage.branch.Branch;
import de.unisb.cs.st.evosuite.coverage.branch.BranchPool;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUse;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUseFactory;
import de.unisb.cs.st.evosuite.coverage.dataflow.Definition;
import de.unisb.cs.st.evosuite.coverage.dataflow.Use;
import de.unisb.cs.st.evosuite.graphs.GraphPool;
import de.unisb.cs.st.evosuite.utils.ReverseComparator;

/**
 * Represents the complete CFG of a method
 * 
 * Essentially this is a graph containing all BytecodeInstrucions of a method as
 * nodes. From each such instruction there is an edge to each possible
 * instruction the control flow can reach immediately after that instruction.
 * 
 * 
 * @author Andre Mis
 */
public class RawControlFlowGraph extends ControlFlowGraph<BytecodeInstruction> {

	private static Logger logger = LoggerFactory
			.getLogger(RawControlFlowGraph.class);

	public RawControlFlowGraph(String className, String methodName, int access) {
		super(className, methodName, access);
	}

	// inherited from ControlFlowGraph

	@Override
	public boolean containsInstruction(BytecodeInstruction instruction) {

		return containsVertex(instruction);
	}

	@Override
	public BytecodeInstruction getInstruction(int instructionId) {
		for (BytecodeInstruction v : vertexSet()) {
			if (v.getInstructionId() == instructionId) {
				return v;
			}
		}
		return null;
	}

	// @Override
	// public BytecodeInstruction getBranch(int branchId) {
	// for (BytecodeInstruction v : vertexSet()) {
	// if (v.isBranch() && v.getControlDependentBranchId() == branchId) {
	// return v;
	// }
	// }
	// return null;
	// }

	protected ControlFlowEdge addEdge(BytecodeInstruction src,
			BytecodeInstruction target, boolean isExceptionEdge) {

		if (src.isBranch())
			return addBranchEdge(src, target, isExceptionEdge);
		else if (src.isSwitch())
			return addSwitchBranchEdge(src, target, isExceptionEdge);

		return addUnlabeledEdge(src, target, isExceptionEdge);
	}

	private ControlFlowEdge addUnlabeledEdge(BytecodeInstruction src,
			BytecodeInstruction target, boolean isExceptionEdge) {

		return internalAddEdge(src, target,
				new ControlFlowEdge(isExceptionEdge));
	}

	private ControlFlowEdge addBranchEdge(BytecodeInstruction src,
			BytecodeInstruction target, boolean isExceptionEdge) {

		boolean isJumping = !isNonJumpingEdge(src, target);
		ControlDependency cd = new ControlDependency(src.toBranch(), isJumping);

		ControlFlowEdge e = new ControlFlowEdge(cd, isExceptionEdge);

		return internalAddEdge(src, target, e);
	}

	private ControlFlowEdge addSwitchBranchEdge(BytecodeInstruction src,
			BytecodeInstruction target, boolean isExceptionEdge) {
		if (!target.isLabel())
			throw new IllegalStateException(
					"expect control flow edges from switch statements to always target labelNodes");

		LabelNode label = (LabelNode) target.getASMNode();

		List<Branch> switchCaseBranches = BranchPool.getBranchForLabel(label);

		if (switchCaseBranches == null) {
			logger.debug("not a switch case label: " + label.toString() + " "
					+ target.toString());
			return internalAddEdge(src, target, new ControlFlowEdge(
					isExceptionEdge));
		}
		// throw new IllegalStateException(
		// "expect BranchPool to contain a Branch for each switch-case-label"+src.toString()+" to "+target.toString());

		// TODO there is an inconsistency when it comes to switches with
		// empty case: blocks. they do not have their own label, so there
		// can be multiple ControlFlowEdges from the SWITCH instruction to
		// one LabelNode.
		// But currently our RawCFG does not permit multiple edges between
		// two nodes

		for (Branch switchCaseBranch : switchCaseBranches) {

			// TODO n^2
			Set<ControlFlowEdge> soFar = incomingEdgesOf(target);
			boolean handled = false;
			for (ControlFlowEdge old : soFar)
				if (switchCaseBranch.equals(old.getBranchInstruction()))
					handled = true;

			if (handled)
				continue;
			/*
			 * previous try to add fake intermediate nodes for each empty case
			 * block to help the CDG - unsuccessful:
			 * if(switchCaseBranches.size()>1) { // // e = new
			 * ControlFlowEdge(isExceptionEdge); //
			 * e.setBranchInstruction(switchCaseBranch); //
			 * e.setBranchExpressionValue(true); // BytecodeInstruction
			 * fakeInstruction =
			 * BytecodeInstructionPool.createFakeInstruction(className
			 * ,methodName); // addVertex(fakeInstruction); //
			 * internalAddEdge(src,fakeInstruction,e); // // e = new
			 * ControlFlowEdge(isExceptionEdge); //
			 * e.setBranchInstruction(switchCaseBranch); //
			 * e.setBranchExpressionValue(true); // // e =
			 * internalAddEdge(fakeInstruction,target,e); // } else {
			 */

			ControlDependency cd = new ControlDependency(switchCaseBranch, true);
			ControlFlowEdge e = new ControlFlowEdge(cd, isExceptionEdge);

			e = internalAddEdge(src, target, e);

		}

		return new ControlFlowEdge(isExceptionEdge);
	}

	private ControlFlowEdge internalAddEdge(BytecodeInstruction src,
			BytecodeInstruction target, ControlFlowEdge e) {

		if (!super.addEdge(src, target, e)) {
			// TODO find out why this still happens
			logger.debug("unable to add edge from " + src.toString() + " to "
					+ target.toString() + " into the rawCFG of "
					+ getMethodName());
			e = super.getEdge(src, target);
			if (e == null)
				throw new IllegalStateException(
						"internal graph error - completely unexpected");
		}

		return e;
	}

	private boolean isNonJumpingEdge(BytecodeInstruction src, // TODO move to
			// ControlFlowGraph
			// and implement
			// analog method
			// in ActualCFG
			BytecodeInstruction dst) {

		return Math.abs(src.getInstructionId() - dst.getInstructionId()) == 1;
	}

	// functionality used to create ActualControlFlowGraph

	public BasicBlock determineBasicBlockFor(BytecodeInstruction instruction) {
		if (instruction == null)
			throw new IllegalArgumentException("null given");

		// TODO clean this up

		logger.debug("creating basic block for " + instruction.toString());

		List<BytecodeInstruction> blockNodes = new ArrayList<BytecodeInstruction>();
		blockNodes.add(instruction);

		Set<BytecodeInstruction> handledChildren = new HashSet<BytecodeInstruction>();
		Set<BytecodeInstruction> handledParents = new HashSet<BytecodeInstruction>();

		Queue<BytecodeInstruction> queue = new LinkedList<BytecodeInstruction>();
		queue.add(instruction);
		while (!queue.isEmpty()) {
			BytecodeInstruction current = queue.poll();
			logger.debug("handling " + current.toString());

			// add child to queue
			if (outDegreeOf(current) == 1)
				for (BytecodeInstruction child : getChildren(current)) {
					// this must be only one edge if inDegree was 1

					if (blockNodes.contains(child))
						continue;

					if (handledChildren.contains(child))
						continue;
					handledChildren.add(child);

					if (inDegreeOf(child) < 2) {
						// insert child right after current
						// ... always thought ArrayList had insertBefore() and
						// insertAfter() methods ... well
						blockNodes.add(blockNodes.indexOf(current) + 1, child);

						logger.debug("  added child to queue: "
								+ child.toString());
						queue.add(child);
					}
				}

			// add parent to queue
			if (inDegreeOf(current) == 1)
				for (BytecodeInstruction parent : getParents(current)) {
					// this must be only one edge if outDegree was 1

					if (blockNodes.contains(parent))
						continue;

					if (handledParents.contains(parent))
						continue;
					handledParents.add(parent);

					if (outDegreeOf(parent) < 2) {
						// insert parent right before current
						blockNodes.add(blockNodes.indexOf(current), parent);

						logger.debug("  added parent to queue: "
								+ parent.toString());
						queue.add(parent);
					}
				}
		}

		BasicBlock r = new BasicBlock(className, methodName, blockNodes);

		logger.debug("created nodeBlock: " + r.toString());
		return r;
	}

	@Override
	public BytecodeInstruction determineEntryPoint() {

		BytecodeInstruction noParent = super.determineEntryPoint();
		if (noParent != null)
			return noParent;

		// copied from ControlFlowGraph.determineEntryPoint():
		// there was a back loop to the first instruction within this CFG, so no
		// candidate

		return getInstructionWithSmallestId();
	}

	@Override
	public Set<BytecodeInstruction> determineExitPoints() {

		Set<BytecodeInstruction> r = super.determineExitPoints();

		// if the last instruction loops back to a previous instruction there is
		// no node without a child, so just take the last byteCode instruction

		if (r.isEmpty())
			r.add(getInstructionWithBiggestId());

		return r;

	}

	public BytecodeInstruction getInstructionWithSmallestId() {

		BytecodeInstruction r = null;

		for (BytecodeInstruction ins : vertexSet()) {
			if (r == null || r.getInstructionId() > ins.getInstructionId())
				r = ins;
		}

		return r;
	}

	public BytecodeInstruction getInstructionWithBiggestId() {
		BytecodeInstruction r = null;

		for (BytecodeInstruction ins : vertexSet()) {
			if (r == null || r.getInstructionId() < ins.getInstructionId())
				r = ins;
		}

		return r;
	}

	/**
	 * In some cases there can be isolated nodes within a CFG. For example in an
	 * completely empty try-catch-finally. Since these nodes are not reachable
	 * but cause trouble when determining the entry point of a CFG they get
	 * removed.
	 * 
	 * @return
	 */
	public int removeIsolatedNodes() {
		Set<BytecodeInstruction> candidates = determineEntryPoints();

		int removed = 0;
		if (candidates.size() > 1) {

			for (BytecodeInstruction instruction : candidates) {
				if (outDegreeOf(instruction) == 0) {
					if (graph.removeVertex(instruction)) {
						removed++;
						BytecodeInstructionPool.forgetInstruction(instruction);
					}
				}
			}

		}
		return removed;
	}

	// control distance functionality

	/**
	 * Returns the Set of BytecodeInstructions that can potentially be executed
	 * from entering the method of this CFG until the given BytecodeInstruction
	 * is reached.
	 */
	public Set<BytecodeInstruction> getPreviousInstructionsInMethod(
			BytecodeInstruction v) {
		Set<BytecodeInstruction> visited = new HashSet<BytecodeInstruction>();
		PriorityQueue<BytecodeInstruction> queue = new PriorityQueue<BytecodeInstruction>(
				graph.vertexSet().size(), new BytecodeInstructionIdComparator());
		queue.add(v);
		while (queue.peek() != null) {
			BytecodeInstruction current = queue.poll();
			if (visited.contains(current))
				continue;
			Set<ControlFlowEdge> incomingEdges = graph.incomingEdgesOf(current);
			for (ControlFlowEdge incomingEdge : incomingEdges) {
				BytecodeInstruction source = graph.getEdgeSource(incomingEdge);
				if (source.getInstructionId() >= current.getInstructionId())
					continue;
				queue.add(source);
			}
			visited.add(current);
		}
		return visited;
	}

	/**
	 * Returns the Set of BytecodeInstructions that can potentially be executed
	 * from passing the given BytecodeInstruction until the end of the method of
	 * this CFG is reached.
	 */
	@SuppressWarnings("unchecked")
	public Set<BytecodeInstruction> getLaterInstructionsInMethod(
			BytecodeInstruction v) {
		Set<BytecodeInstruction> visited = new HashSet<BytecodeInstruction>();
		Comparator<BytecodeInstruction> reverseComp = new ReverseComparator(
				new BytecodeInstructionIdComparator());
		PriorityQueue<BytecodeInstruction> queue = new PriorityQueue<BytecodeInstruction>(
				graph.vertexSet().size(), reverseComp);
		queue.add(v);
		while (queue.peek() != null) {
			BytecodeInstruction current = queue.poll();
			if (visited.contains(current))
				continue;
			Set<ControlFlowEdge> outgoingEdges = graph.outgoingEdgesOf(current);
			for (ControlFlowEdge outgoingEdge : outgoingEdges) {
				BytecodeInstruction target = graph.getEdgeTarget(outgoingEdge);
				if (target.getInstructionId() < current.getInstructionId())
					continue;
				queue.add(target);
			}
			visited.add(current);
		}
		return visited;
	}

	// functionality for defUse coverage

	public Set<Use> getUsesForDef(Definition def) {
		if (!graph.containsVertex(def))
			throw new IllegalArgumentException("unknown Definition");

		return getUsesForDef(def, def, new HashSet<BytecodeInstruction>());
	}

	private Set<Use> getUsesForDef(Definition targetDef,
			BytecodeInstruction currentInstruction,
			Set<BytecodeInstruction> handled) {
		if (!graph.containsVertex(currentInstruction))
			throw new IllegalArgumentException("vertex not in graph");

		Set<Use> r = new HashSet<Use>();

		if (handled.contains(currentInstruction))
			return r;
		handled.add(currentInstruction);

		Set<ControlFlowEdge> outgoingEdges = graph
				.outgoingEdgesOf(currentInstruction);
		for (ControlFlowEdge e : outgoingEdges) {
			BytecodeInstruction edgeTarget = graph.getEdgeTarget(e);

			if (targetDef.canBeActiveFor(edgeTarget))
				r.add(DefUseFactory.makeUse(edgeTarget));
			if (canOverwriteDU(targetDef, edgeTarget))
				continue;
			// don not follow edges going to previous instructions (avoid loops)
			// if (edgeTarget.getInstructionId() >
			// currentInstruction.getInstructionId())
			r.addAll(getUsesForDef(targetDef, edgeTarget, handled));
		}
		return r;
	}

	public boolean hasDefClearPathToMethodExit(Definition duVertex) {
		if (!graph.containsVertex(duVertex))
			throw new IllegalArgumentException("vertex not in graph");
		if (duVertex.isLocalDU())
			return false;

		return hasDefClearPathToMethodExit(duVertex, duVertex,
				new HashSet<BytecodeInstruction>());
	}

	public boolean hasDefClearPathFromMethodEntry(Use duVertex) {
		if (!graph.containsVertex(duVertex))
			throw new IllegalArgumentException("vertex not in graph");
		if (duVertex.isLocalDU())
			return false;

		return hasDefClearPathFromMethodEntry(duVertex, duVertex,
				new HashSet<BytecodeInstruction>());
	}

	private boolean hasDefClearPathToMethodExit(Definition targetDefUse,
			BytecodeInstruction currentVertex, Set<BytecodeInstruction> handled) {
		if (!graph.containsVertex(currentVertex))
			throw new IllegalArgumentException("vertex not in graph");

		if (handled.contains(currentVertex))
			return false;
		handled.add(currentVertex);

		Set<ControlFlowEdge> outgoingEdges = graph
				.outgoingEdgesOf(currentVertex);
		if (outgoingEdges.size() == 0)
			return true;

		for (ControlFlowEdge e : outgoingEdges) {
			BytecodeInstruction edgeTarget = graph.getEdgeTarget(e);

			if (canOverwriteDU(targetDefUse, edgeTarget))
				continue;

			// if (edgeTarget.getInstructionId() >
			// currentVertex.getInstructionId() // dont follow backedges (loops)
			// && hasDefClearPathToMethodExit(targetDefUse, edgeTarget,
			// handled))
			if (hasDefClearPathToMethodExit(targetDefUse, edgeTarget, handled))
				return true;
		}
		return false;
	}

	private boolean hasDefClearPathFromMethodEntry(Use targetDefUse,
			BytecodeInstruction currentVertex, Set<BytecodeInstruction> handled) {
		if (!graph.containsVertex(currentVertex))
			throw new IllegalArgumentException("vertex not in graph");

		if (handled.contains(currentVertex))
			return false;
		handled.add(currentVertex);

		Set<ControlFlowEdge> incomingEdges = graph
				.incomingEdgesOf(currentVertex);
		if (incomingEdges.size() == 0)
			return true;

		for (ControlFlowEdge e : incomingEdges) {
			BytecodeInstruction edgeStart = graph.getEdgeSource(e);

			// skip edges coming from a def for the same field
			if (canOverwriteDU(targetDefUse, edgeStart, new HashSet<String>()))
				continue;

			// if (edgeTarget.getInstructionId() >
			// currentVertex.getInstructionId() // dont follow backedges (loops)
			// && hasDefClearPathToMethodExit(targetDefUse, edgeTarget,
			// handled))
			if (hasDefClearPathFromMethodEntry(targetDefUse, edgeStart, handled))
				return true;

		}
		return false;
	}

	private boolean callsOverwritingMethod(DefUse targetDefUse,
			BytecodeInstruction edgeTarget, Set<String> handle) {

		if (canBeOverwritingMethod(targetDefUse, edgeTarget)) {

			// DONE in this case we should check if there is a deffree path
			// for this field in the called method if the called method is
			// also a method from the class of our targetDU

			// TODO this does not take into account if the method call is
			// invoked on the same object. we should actually check if
			// "this" is on top of the stack (ALOAD_0 previous instruction
			// before call)

			RawControlFlowGraph calledGraph = edgeTarget.getCalledCFG();

			if (calledGraph == null) {
				logger.debug("expected cfg to exist for: "
						+ edgeTarget.getCalledMethod()
						+ " ... abstract method?");
				return false;
			}

			if (!calledGraph.hasDefClearPath(targetDefUse, handle))
				return true;
		}

		return false;
	}

	/**
	 * Checks if the given DefUse has a definition-clear path to its methods
	 * exit
	 */
	public boolean hasDefClearPath(DefUse targetDU, Set<String> handle) {
		BytecodeInstruction entry = determineEntryPoint();

		return hasDefClearPath(targetDU, entry, handle);

	}

	/**
	 * Auxiliary method for hasDefClearPath(DefUse, Set)
	 */
	private boolean hasDefClearPath(DefUse targetDU,
			BytecodeInstruction currentVertex, Set<String> handle) {
		if (!graph.containsVertex(currentVertex))
			throw new IllegalArgumentException("vertex not in graph");

		handle.add(methodName);

		String targetVariable = targetDU.getDUVariableName();

		if (currentVertex.isDefinitionForVariable(targetVariable))
			return false;

		Set<ControlFlowEdge> outgoingEdges = graph
				.outgoingEdgesOf(currentVertex);
		if (outgoingEdges.size() == 0)
			return true;

		for (ControlFlowEdge e : outgoingEdges) {
			BytecodeInstruction edgeTarget = graph.getEdgeTarget(e);

			// handle edgeTarget being another method call!
			if (canBeOverwritingMethod(targetDU, edgeTarget)
					&& !handle.contains(edgeTarget.getCalledMethod())
					&& canOverwriteDU(targetDU, edgeTarget, handle))
				continue;

			if (/*
				 * edgeTarget.getInstructionId() > currentVertex
				 * .getInstructionId() // dont follow backedges (loops) &&
				 */hasDefClearPath(targetDU, edgeTarget, handle))
				return true;
		}

		return false;
	}

	private boolean canOverwriteDU(Definition targetDefUse,
			BytecodeInstruction edgeTarget) {

		return canOverwriteDU(targetDefUse, edgeTarget, new HashSet<String>());
	}

	private boolean canOverwriteDU(DefUse targetDefUse,
			BytecodeInstruction edgeTarget, Set<String> handle) {

		// skip edges going into another def for the same field
		if (targetDefUse.canBecomeActiveDefinition(edgeTarget))
			return true;

		if (callsOverwritingMethod(targetDefUse, edgeTarget, handle))
			return true;

		return false;
	}

	private boolean canBeOverwritingMethod(DefUse targetDefUse,
			BytecodeInstruction edgeTarget) {

		return targetDefUse.isFieldDU()
				&& edgeTarget.isMethodCallForClass(targetDefUse.getClassName());
	}

	// miscellaneous

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (ControlFlowEdge e : graph.edgeSet()) {
			sb.append(graph.getEdgeSource(e) + " -> " + graph.getEdgeTarget(e));
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String getCFGType() {
		return "RCFG";
	}

	// CCFG util

	public List<BytecodeInstruction> determineMethodCalls() {
		List<BytecodeInstruction> calls = new ArrayList<BytecodeInstruction>();
		for (BytecodeInstruction ins : graph.vertexSet()) {
			if (ins.isMethodCall()) {
				calls.add(ins);
			}
		}
		return calls;
	}

	public List<BytecodeInstruction> determineMethodCallsToOwnClass() {
		List<BytecodeInstruction> calls = new ArrayList<BytecodeInstruction>();
		for (BytecodeInstruction ins : determineMethodCalls())
			if (ins.isMethodCallForClass(className)) {

				// somehow ASMs MethodInsnNode.owner and thus
				// BytecodeInstruction.getCalledMethodsClass() returns the class
				// in which the method call is defined and not the class of the
				// called method. this happens if class A extends class B, the
				// called method is declared in B and the method call is in A
				// TODO so for now we have this workaround: if A is our CUT then
				// the GraphPool does not know B.

				if (GraphPool.getRawCFG(className, ins.getCalledMethod()) != null)
					calls.add(ins);
				// TODO logger.warn or logger.debug
				// else
				// System.out.println("false positive call to own classes method: "+ins.toString());
			}

		return calls;
	}

	// TODO hack since RawControlFlowGraph is filled by CFGGenerator from the
	// outside
	@Override
	public boolean addVertex(BytecodeInstruction ins) {
		return super.addVertex(ins);
	}
}
