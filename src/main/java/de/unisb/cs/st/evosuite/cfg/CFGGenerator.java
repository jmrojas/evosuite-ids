package de.unisb.cs.st.evosuite.cfg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Frame;

import de.unisb.cs.st.evosuite.mutation.HOM.HOMObserver;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;

/**
 * When analyzing a CUT the BytecodeAnalyzer creates an instance of this class
 * It's methods get called in the following order:
 * 
 * - upon constructing the method at hand is registered via registerMethodNode()
 * this sets the field of this class and fills the BytecodeInstructionPool with
 * all instructions inside that method - then the registerControlFlowEdge()
 * methods get called by the BytecodeAnalyzer for each possible transition from
 * one byteCode instruction to another inside the CUT in this step the
 * CFGGenerator asks the BytecodeInstructionPool for the previously created
 * instructions and fills up it's raw graph
 * 
 * After those calls the raw CFG of the method at hand is complete It should
 * contain a Vertex for every BytecodeInstruction inside the specified method
 * and an edge for every possible transition between these instructions
 * 
 * 
 * TODO this raw graph should be turned into a nice CFG containing basic blocks
 * as vertices
 * 
 * 
 * @author Andre Mis
 */
public class CFGGenerator {

	private static Logger logger = Logger.getLogger(CFGGenerator.class);

	List<Mutation> mutants;
	DefaultDirectedGraph<BytecodeInstruction, DefaultEdge> rawGraph = new DefaultDirectedGraph<BytecodeInstruction, DefaultEdge>(
			DefaultEdge.class);

	boolean nodeRegistered = false;
	MethodNode currentMethod;
	String className;
	String methodName;

	/**
	 * Initializes this generator to generate the CFG for the method identified
	 * by the given parameters
	 * 
	 * Calls registerMethodNode() which in turn calls
	 * BytecodeInstructionPool.registerMethodNode() leading to the creation of
	 * all BytecodeInstruction instances for the method at hand
	 * 
	 * TODO might not want to give asm.MethodNode to the outside, but rather a
	 * MyMethodNode extended from BytecodeInstruction or something
	 */
	public CFGGenerator(String className, String methodName, MethodNode node,
			List<Mutation> mutants) {
		this.mutants = mutants;
		registerMethodNode(node, className, methodName);
	}

	private void registerMethodNode(MethodNode currentMethod, String className,
			String methodName) {
		if (nodeRegistered)
			throw new IllegalStateException(
					"registerMethodNode must not be called more than once for each instance of CFGGenerator");
		if (currentMethod == null || methodName == null
				|| currentMethod == null)
			throw new IllegalArgumentException("null given");

		this.currentMethod = currentMethod;
		this.className = className;
		this.methodName = methodName;

		BytecodeInstructionPool.registerMethodNode(currentMethod, className,
				methodName);

		nodeRegistered = true;
	}

	// build up the graph

	/**
	 * Internal management of fields and actual building up of the rawGraph
	 */
	public void registerControlFlowEdge(int src, int dst, Frame[] frames) {
		if (!nodeRegistered)
			throw new IllegalStateException(
					"CFGGenrator.registerControlFlowEdge() cannot be called unless registerMethodNode() was called first");
		if (frames == null)
			throw new IllegalArgumentException("null given");
		CFGFrame srcFrame = (CFGFrame) frames[src];
		Frame dstFrame = frames[dst];
		if (srcFrame == null || dstFrame == null)
			throw new IllegalArgumentException(
					"expect expect given frames to know src and dst");

		srcFrame.successors.put(dst, (CFGFrame) dstFrame);

		AbstractInsnNode srcNode = currentMethod.instructions.get(src);
		AbstractInsnNode dstNode = currentMethod.instructions.get(dst);

		// those nodes should have gotten registered by registerMethodNode()
		BytecodeInstruction srcInstruction = BytecodeInstructionPool
				.getInstruction(className, methodName, src, srcNode);
		BytecodeInstruction dstInstruction = BytecodeInstructionPool
				.getInstruction(className, methodName, dst, dstNode);

		if (srcInstruction == null || dstInstruction == null)
			throw new IllegalStateException(
					"expect BytecodeInstructionPool to know the instructions in the method of this edge");

//		if(srcInstruction.isLabel() || dstInstruction.isLabel())
//			System.out.println("LABELEDGE: "+srcInstruction.toString()+" to "+dstInstruction.toString());
		
		rawGraph.addVertex(srcInstruction);
		rawGraph.addVertex(dstInstruction);
		rawGraph.addEdge(srcInstruction, dstInstruction);
	}

	// retrieve information about the graph

	public DefaultDirectedGraph<BytecodeInstruction, DefaultEdge> getCompleteGraph() {
		return rawGraph;
	}

	public DirectedMultigraph<BytecodeInstruction, DefaultEdge> getMinimalGraph() {

		setMutationIDs();
		setMutationBranches();

		DirectedMultigraph<BytecodeInstruction, DefaultEdge> min_graph = new DirectedMultigraph<BytecodeInstruction, DefaultEdge>(
				DefaultEdge.class);

		// Get minimal cfg vertices
		for (BytecodeInstruction vertex : rawGraph.vertexSet()) {
			// Add initial nodes and jump targets
			if (rawGraph.inDegreeOf(vertex) == 0) {
				min_graph.addVertex(vertex);
				// Add end nodes
			} else if (rawGraph.outDegreeOf(vertex) == 0) {
				min_graph.addVertex(vertex);
			} else if (vertex.isJump() && !vertex.isGoto()) {
				min_graph.addVertex(vertex);
			} else if (vertex.isTableSwitch() || vertex.isLookupSwitch()) {
				min_graph.addVertex(vertex);
			} else if (vertex.isMutation()) {
				min_graph.addVertex(vertex);
			}
		}
		// Get minimal cfg edges
		for (BytecodeInstruction vertex : min_graph.vertexSet()) {
			Set<DefaultEdge> handled = new HashSet<DefaultEdge>();

			Queue<DefaultEdge> queue = new LinkedList<DefaultEdge>();
			queue.addAll(rawGraph.outgoingEdgesOf(vertex));
			while (!queue.isEmpty()) {
				DefaultEdge edge = queue.poll();
				if (handled.contains(edge))
					continue;
				handled.add(edge);
				if (min_graph.containsVertex(rawGraph.getEdgeTarget(edge))) {
					min_graph.addEdge(vertex, rawGraph.getEdgeTarget(edge));
				} else {
					queue.addAll(rawGraph.outgoingEdgesOf(rawGraph
							.getEdgeTarget(edge)));
				}
			}
		}

		// debug/experiment
		computeCFG();

		return min_graph;
	}

	/**
	 * TODO supposed to build the final CFG with BasicBlocks as nodes and stuff!
	 * 
	 * WORK IN PROGRESS
	 * 
	 * soon
	 */
	public ActualControlFlowGraph computeCFG() {

		BytecodeInstructionPool.logInstructionsIn(className,methodName);

		ActualControlFlowGraph cfg = new ActualControlFlowGraph(this);

		return cfg;
	}

	public BasicBlock determineBasicBlockFor(BytecodeInstruction instruction) {
		if (instruction == null)
			throw new IllegalArgumentException("null given");

		logger.debug("creating basic block for " + instruction.toString());

		List<BytecodeInstruction> blockNodes = new ArrayList<BytecodeInstruction>();
		blockNodes.add(instruction);

		Set<BytecodeInstruction> handled = new HashSet<BytecodeInstruction>();

		Queue<BytecodeInstruction> queue = new LinkedList<BytecodeInstruction>();
		queue.add(instruction);
		while (!queue.isEmpty()) {
			BytecodeInstruction current = queue.poll();
			handled.add(current);

			// add child to queue
			if (rawGraph.inDegreeOf(current) == 1)
				for (DefaultEdge edge : rawGraph.incomingEdgesOf(current)) {
					// this must be only one edge if inDegree was 1
					BytecodeInstruction parent = rawGraph.getEdgeSource(edge);
					if (handled.contains(parent))
						continue;
					handled.add(parent);

					if(rawGraph.outDegreeOf(parent)<2) {
						// insert child right before current
						// ... always thought ArrayList had insertBefore() and insertAfter() methods ... well
						blockNodes.add(blockNodes.indexOf(current), parent);
						
						queue.add(parent);
					}
				}

			// add parent to queue
			if (rawGraph.outDegreeOf(current) == 1)
				for (DefaultEdge edge : rawGraph.outgoingEdgesOf(current)) {
					// this must be only one edge if outDegree was 1
					BytecodeInstruction child = rawGraph.getEdgeTarget(edge);
					if (handled.contains(child))
						continue;
					handled.add(child);

					if(rawGraph.inDegreeOf(child)<2) {
						// insert parent right after current
						blockNodes.add(blockNodes.indexOf(current) + 1, child);
						
						queue.add(child);
					}
				}
		}

		BasicBlock r = new BasicBlock(className, methodName, blockNodes);

		logger.debug("created nodeBlock: "+r.toString());
		return r;
	}

	public BytecodeInstruction determineEntryPoint() {
		BytecodeInstruction r = null;

		for (BytecodeInstruction instruction : rawGraph.vertexSet())
			if (rawGraph.inDegreeOf(instruction) == 0) {
				if (r != null)
					throw new IllegalStateException(
							"expect raw CFG of a method to contain exactly one instruction with no parent");
				r = instruction;
			}
		if (r == null)
			throw new IllegalStateException(
					"expect raw CFG of a method to contain exactly one instruction with no parent");

		return r;
	}

	public Set<BytecodeInstruction> determineExitPoints() {
		Set<BytecodeInstruction> r = new HashSet<BytecodeInstruction>();

		for (BytecodeInstruction instruction : rawGraph.vertexSet())
			if (rawGraph.outDegreeOf(instruction) == 0) {
				r.add(instruction);
			}
		if (r.isEmpty())
			throw new IllegalStateException(
					"expect raw CFG of a method to contain at least one instruction with no child");

		return r;
	}

	public Set<BytecodeInstruction> determineBranches() {
		Set<BytecodeInstruction> r = new HashSet<BytecodeInstruction>();

		for (BytecodeInstruction instruction : rawGraph.vertexSet())
			if (rawGraph.outDegreeOf(instruction) > 1)
				r.add(instruction);

		return r;
	}

	public Set<BytecodeInstruction> determineJoins() {
		Set<BytecodeInstruction> r = new HashSet<BytecodeInstruction>();

		for (BytecodeInstruction instruction : rawGraph.vertexSet())
			if (rawGraph.inDegreeOf(instruction) > 1)
				r.add(instruction);

		return r;
	}

	// mark mutations

	/**
	 * Sets the mutation IDs for each node
	 */
	private void setMutationIDs() {
		for (Mutation m : mutants) {
			if (m.getMethodName().equals(methodName)
					&& m.getClassName().equals(className)) {
				for (BytecodeInstruction v : rawGraph.vertexSet()) {
					if (v.isLineNumber()
							&& v.getLineNumber() == m.getLineNumber()) {
						v.setMutation(m.getId());
						// TODO: What if there are several mutations with the
						// same line number?
					}
				}
			}
		}
	}

	/**
	 * This method sets the mutationBranchAttribute on fields.
	 */
	private void setMutationBranches() {
		for (BytecodeInstruction v : rawGraph.vertexSet()) {
			if (v.isIfNull()) {
				for (DefaultEdge e : rawGraph.incomingEdgesOf(v)) {
					BytecodeInstruction v2 = rawGraph.getEdgeSource(e);
					// #TODO the magic string "getProperty" should be in some
					// String variable, near the getProperty function
					// declaration (which I couldn't find (steenbuck))
					if (v2.isMethodCall("getProperty")) {
						v.setMutationBranch();
					}
				}
			} else if (v.isBranch() || v.isTableSwitch() || v.isLookupSwitch()) {
				for (DefaultEdge e : rawGraph.incomingEdgesOf(v)) {
					BytecodeInstruction v2 = rawGraph.getEdgeSource(e);
					// #TODO method signature should be used here
					if (v2.isMethodCall(HOMObserver.NAME_OF_TOUCH_METHOD)) {
						logger.debug("Found mutated branch ");
						v.setMutatedBranch();
					} else {
						if (v2.isMethodCall())
							logger.debug("Edgesource: " + v2.getMethodName());
					}
				}
			}
		}
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
}
