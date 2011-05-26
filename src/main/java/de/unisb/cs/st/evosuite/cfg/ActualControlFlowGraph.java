package de.unisb.cs.st.evosuite.cfg;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.graph.DefaultEdge;

import de.unisb.cs.st.evosuite.coverage.branch.Branch;
import de.unisb.cs.st.evosuite.coverage.branch.BranchPool;

/**
 * 
 * Supposed to become the new implementation of a control flow graph inside
 * EvoSuite
 * 
 * The "actual" CFG does not contain single cfg.BytecodeInstructions as nodes,
 * but contains cfg.BasicBlocks - look at that class for more information
 * 
 * Simply put this is a minimized version of the complete/raw CFG the
 * cfg.BytecodeAnalyzer and cfg.CFGGenerator produce - which holds a node for
 * every BytecodeInstruction
 * 
 * Out of the above described raw CFG the following "pin-points" are extracted:
 * 
 * 1) the entryNode (first instruction in the method)
 *  
 * 2) all exitNodes (outDegree 0)
 *  
 * 3.1) all branches (outDegree>1)
 * 3.2) in a subsequent step all targets of all branches
 *  
 * 4.1) all joins (inDegree>1) 
 * 4.2) in a subsequent step all sources of all joins
 * 
 * All those "pin-points" are put into a big set (some of the above categories
 * may overlap) and for all those single BytecodeInstrucions their corresponding
 * BasicBlock is computed and added to this CFGs vertexSet. After that the raw
 * CFG is asked for the parents of the first instruction of each BasicBlock and
 * the children of that blocks last instruction. Then the edges to their
 * corresponding BasicBlocks are added to this CFG 
 * 
 * TODO: verify that this method works :D
 * 
 * 
 * cfg.EvoSuiteGraph is used as the underlying data structure holding the
 * graphical representation of the CFG
 * 
 * WORK IN PROGRESS
 * 
 * TODO implement
 * 
 * @author Andre Mis
 */
public class ActualControlFlowGraph extends ControlFlowGraph<BasicBlock> {

	private static Logger logger = Logger.getLogger(ActualControlFlowGraph.class);
	
	private RawControlFlowGraph rawGraph;
	
	private BytecodeInstruction entryPoint;
	private Set<BytecodeInstruction> exitPoints;
	private Set<BytecodeInstruction> branches;
	private Set<BytecodeInstruction> branchTargets;
	private Set<BytecodeInstruction> joins;
	private Set<BytecodeInstruction> joinSources;
	
	
	public ActualControlFlowGraph(RawControlFlowGraph rawGraph) {
		super(rawGraph.getClassName(),rawGraph.getMethodName());

		if (rawGraph == null)
			throw new IllegalArgumentException("null given");

		this.rawGraph = rawGraph;
		
		fillSets();
		computeGraph();
	}
	
	// initialization
	
	private void fillSets() {

		setEntryPoint(rawGraph.determineEntryPoint());
		setExitPoints(rawGraph.determineExitPoints());
		
		setBranches(rawGraph.determineBranches());
		setBranchTargets();
		setJoins(rawGraph.determineJoins());
		setJoinSources();
	}
	
	private void setEntryPoint(BytecodeInstruction entryPoint) {
		if (entryPoint == null)
			throw new IllegalArgumentException("null given");
		if(!belongsToMethod(entryPoint))
			throw new IllegalArgumentException("entry point does not belong to this CFGs method");
		this.entryPoint=entryPoint;
	}
	
	private void setExitPoints(Set<BytecodeInstruction> exitPoints) {
		if (exitPoints == null)
			throw new IllegalArgumentException("null given");
		
		this.exitPoints = new HashSet<BytecodeInstruction>();
		
		for (BytecodeInstruction exitPoint : exitPoints) {
			if (!belongsToMethod(exitPoint))
				throw new IllegalArgumentException(
						"exit point does not belong to this CFGs method");
			if (!exitPoint.canReturnFromMethod())
				throw new IllegalArgumentException(
						"unexpected exitPoint byteCode instruction type: "
								+ exitPoint.getInstructionType());
			
			this.exitPoints.add(exitPoint);
		}
	}
	
	private void setJoins(Set<BytecodeInstruction> joins) {
		if (joins == null)
			throw new IllegalArgumentException("null given");
		
		this.joins = new HashSet<BytecodeInstruction>();

		for (BytecodeInstruction join : joins) {
			if (!belongsToMethod(join))
				throw new IllegalArgumentException(
						"join does not belong to this CFGs method");
			
			this.joins.add(join);
		}
	}
	
	private void setJoinSources() {
		if(joins==null)
			throw new IllegalStateException("expect joins to be set before setting of joinSources");
		if(rawGraph == null)
			throw new IllegalArgumentException("null given");
		
		this.joinSources = new HashSet<BytecodeInstruction>();
		
		for(BytecodeInstruction join : joins)
			for(DefaultEdge joinEdge : rawGraph.incomingEdgesOf(join))
				joinSources.add(rawGraph.getEdgeSource(joinEdge));
	}
	
	private void setBranches(Set<BytecodeInstruction> branches) {
		if (branches == null)
			throw new IllegalArgumentException("null given");
		
		this.branches = new HashSet<BytecodeInstruction>();
		
		for (BytecodeInstruction branch : branches) {
			if (!belongsToMethod(branch))
				throw new IllegalArgumentException(
						"branch does not belong to this CFGs method");
			if (!branch.isActualBranch())
				throw new IllegalArgumentException(
						"unexpected branch byteCode instruction type: "
								+ branch.getInstructionType());
			
			// TODO the following doesn't work at this point
			//		 because the BranchPool is not yet filled yet
			// BUT one could fill the pool right now and drop further analysis later
			// way cooler, because then filling of the BranchPool is unrelated to
			//  BranchInstrumentation - then again that instrumentation is needed anyways i guess
//			if (!BranchPool.isKnownAsBranch(instruction))
//				throw new IllegalStateException(
//						"expect BranchPool to know all branching instructions: "
//								+ instruction.toString());
			
			this.branches.add(branch);
		}
	}

	private void setBranchTargets() {
		if(branches==null)
			throw new IllegalStateException("expect branches to be set before setting of branchTargets");
		if(rawGraph == null)
			throw new IllegalArgumentException("null given");
		
		this.branchTargets = new HashSet<BytecodeInstruction>();
		
		for(BytecodeInstruction branch : branches)
			for(DefaultEdge branchEdge : rawGraph.outgoingEdgesOf(branch))
				branchTargets.add(rawGraph.getEdgeTarget(branchEdge));
	}
	
	private Set<BytecodeInstruction> getInitiallyKnownInstructions() {
		Set<BytecodeInstruction> r = new HashSet<BytecodeInstruction>();
		r.add(entryPoint);
		r.addAll(exitPoints);
		r.addAll(branches);
		r.addAll(branchTargets);
		r.addAll(joins);
		r.addAll(joinSources);
		
		return r;
	}
	
	//   compute actual CFG from RawControlFlowGraph
	
	private void computeGraph() {
		
		computeNodes();
		computeEdges();
		
		checkSanity();
	}
	
	private void computeNodes() {

		Set<BytecodeInstruction> nodes = getInitiallyKnownInstructions();
		
		logger.debug("Computing Basic Blocks");
		
		for(BytecodeInstruction node : nodes) {
			if(knowsInstruction(node))
				continue;
			
			BasicBlock nodeBlock = rawGraph.determineBasicBlockFor(node);
			addBlock(nodeBlock);
		}
		
		logger.info(getNodeCount()+" BasicBlocks");
	}
	
	private void computeEdges() {

		for (BasicBlock block : vertexSet()) {
			
			computeIncomingEdgesFor(block);
			computeOutgoingEdgesFor(block);
		}
		
		logger.info(getEdgeCount()+" ControlFlowEdges");
	}
	
	private void computeIncomingEdgesFor(BasicBlock block) {

		if (isEntryBlock(block))
			return;

		BytecodeInstruction blockStart = block.getFirstInstruction();
		Set<DefaultEdge> rawIncomings = rawGraph
				.incomingEdgesOf(blockStart);
		for (DefaultEdge rawIncoming : rawIncomings) {
			BytecodeInstruction incomingStart = rawGraph
					.getEdgeSource(rawIncoming);
			addEdge(incomingStart, block);
		}
	}
	
	private void computeOutgoingEdgesFor(BasicBlock block) {
		
		if(isExitBlock(block))
			return;
		
		BytecodeInstruction blockEnd = block.getLastInstruction();
		
		Set<DefaultEdge> rawOutgoings = rawGraph
				.outgoingEdgesOf(blockEnd);
		for (DefaultEdge rawOutgoing : rawOutgoings) {
			BytecodeInstruction outgoingEnd = rawGraph
					.getEdgeTarget(rawOutgoing);
			addEdge(block, outgoingEnd);
		}
	}
	
	//   internal graph handling
	
	protected void addBlock(BasicBlock nodeBlock) {
		if (nodeBlock == null)
			throw new IllegalArgumentException("null given");
		
		logger.debug("Adding block: "+nodeBlock.getName());
		
		if (containsVertex(nodeBlock))
			throw new IllegalArgumentException("block already added before");

		if (!addVertex(nodeBlock))
			throw new IllegalStateException(
					"internal error while addind basic block to CFG");
		
//		for (BasicBlock test : graph.vertexSet()) {
//			logger.debug("experimental self-equals on " + test.getName());
//			if (nodeBlock.equals(test))
//				logger.debug("true");
//			else
//				logger.debug("false");
//			if (!containsBlock(test))
//				throw new IllegalStateException("wtf");
//
//			logger.debug("experimental equals on " + test.getName() + " with "
//					+ nodeBlock.getName());
//			if (test.equals(nodeBlock))
//				logger.debug("true");
//			else
//				logger.debug("false");
//
//			logger.debug("experimental dual-equal");
//			if (nodeBlock.equals(test))
//				logger.debug("true");
//			else
//				logger.debug("false");
//
//		}
		
		if(!containsVertex(nodeBlock))
			throw new IllegalStateException("expect graph to contain the given block on returning of addBlock()");
		
		logger.debug(".. succeeded. nodeCount: "+getNodeCount());
	}
	
	protected void addEdge(BytecodeInstruction src, BasicBlock target) {
		BasicBlock srcBlock = getBlockOf(src);
		if (srcBlock == null)
			throw new IllegalStateException(
					"when adding an edge to a CFG it is expected to know both the src- and the target-instruction");

		addEdge(srcBlock, target);
	}

	protected void addEdge(BasicBlock src, BytecodeInstruction target) {
		BasicBlock targetBlock = getBlockOf(target);
		if (targetBlock == null)
			throw new IllegalStateException(
					"when adding an edge to a CFG it is expected to know both the src- and the target-instruction");

		addEdge(src, targetBlock);
	}
	
	protected DefaultEdge addEdge(BasicBlock src, BasicBlock target) {
		if (src == null || target == null)
			throw new IllegalArgumentException("null given");

		logger.debug("Adding edge from "+src.getName()+" to "+target.getName());
		
		DefaultEdge r;
		if (containsEdge(src,target)) {
			logger.debug("edge already contained in CFG");
			r = getEdge(src,target);
			if(r==null)
				throw new IllegalStateException("expect getEdge() not to retur null on parameters on which containsEdge() retruned true");
			
			return r;
		}
		
		r = super.addEdge(src, target);
		if (r == null)
			throw new IllegalStateException(
					"internal error while adding edge to CFG");
		
		logger.debug(".. succeeded, edgeCount: "+getEdgeCount());
		
		return r;
	}
	
	// convenience methods to switch between BytecodeInstructons and BasicBlocks
	
	public BasicBlock getBlockOf(BytecodeInstruction instruction) {
		
		for(BasicBlock block : vertexSet())
			if(block.containsInstruction(instruction))
				return block;
		
		logger.debug("unknown instruction "+instruction.toString());
		return null;
	}
	
	public boolean knowsInstruction(BytecodeInstruction instruction) {
		for(BasicBlock block : vertexSet())
			if(block.containsInstruction(instruction))
				return true;
		
		return false;
	}
	
	public int getDistance(BytecodeInstruction v1, BytecodeInstruction v2) {
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("null given");
		if (!knowsInstruction(v1) || !knowsInstruction(v2))
			throw new IllegalArgumentException(
					"instructions not contained in this CFG");
		
		BasicBlock b1 = getBlockOf(v1);
		BasicBlock b2 = getBlockOf(v2);

		if (b1 == null || b2 == null)
			throw new IllegalStateException(
					"expect CFG to contain the BasicBlock for each instruction knowsInstruction() returns true on");
		
		return getDistance(b1,b2);
	}
	
	public boolean isDirectSuccessor(BytecodeInstruction v1, BytecodeInstruction v2) {
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("null given");
		if (!knowsInstruction(v1) || !knowsInstruction(v2))
			throw new IllegalArgumentException(
					"instructions not contained in this CFG");
		
		BasicBlock b1 = getBlockOf(v1);
		BasicBlock b2 = getBlockOf(v2);

		if (b1 == null || b2 == null)
			throw new IllegalStateException(
					"expect CFG to contain the BasicBlock for each instruction knowsInstruction() returns true on");
	
		return isDirectSuccessor(b1,b2);
	}
	
	// retrieve information about the CFG
	
	public boolean isEntryBlock(BasicBlock block) {
		if (block == null)
			throw new IllegalArgumentException("null given");

		if (block.containsInstruction(entryPoint)) {
			// sanity check
			if (!block.getFirstInstruction().equals(entryPoint))
				throw new IllegalStateException(
						"expect entryPoint of a method to be the first instruction from the entryBlock of that method");
			return true;
		}

		return false;
	}
	
	public boolean isExitBlock(BasicBlock block) {
		if (block == null)
			throw new IllegalArgumentException("null given");

		for(BytecodeInstruction exitPoint : exitPoints)
			if (block.containsInstruction(exitPoint)) {
				// sanity check
				if (!block.getLastInstruction().equals(exitPoint))
					throw new IllegalStateException(
							"expect exitPoints of a method to be the last instruction from an exitBlock of that method");
				return true;
			}

		return false;
	}
	
	public boolean belongsToMethod(BytecodeInstruction instruction) {
		if(instruction==null)
			throw new IllegalArgumentException("null given");
		
		if(!className.equals(instruction.getClassName()))
			return false;
		if(!methodName.equals(instruction.getMethodName()))
			return false;
		
		return true;
	}
	
	// sanity checks
	
	public void checkSanity() {

		logger.debug("checking sanity of CFG for " + methodName);

		if (isEmpty())
			throw new IllegalStateException(
					"a CFG must contain at least one element");

		for (BytecodeInstruction initInstruction : getInitiallyKnownInstructions()) {
			if (!knowsInstruction(initInstruction))
				throw new IllegalStateException(
						"expect CFG to contain all initially known instructions");
		}

		logger.debug(".. all initInstructions contained");

		checkNodeSanity();
		
		logger.debug(".. CFG sanity ensured");
	}
	
	void checkNodeSanity() {
		// ensure graph is connected and isEntry and isExitBlock() work as
		// expected
		for (BasicBlock node : vertexSet()) {

			checkEntryExitPointConstraint(node);

			checkSingleCFGNodeConstraint(node);

			checkNodeMinimalityConstraint(node);
		}
		logger.debug("..all node constraints ensured");
	}

	void checkEntryExitPointConstraint(BasicBlock node) {
		// exit point constraint
		int out = outDegreeOf(node);
		if (!isExitBlock(node) && out == 0)
			throw new IllegalStateException(
					"expect nodes without outgoing edges to be exitBlocks: "
							+ node.toString());
		// entry point constraint
		int in = inDegreeOf(node);
		if (!isEntryBlock(node) && in == 0)
			throw new IllegalStateException(
					"expect nodes without incoming edges to be the entryBlock: "
							+ node.toString());
	}

	void checkSingleCFGNodeConstraint(BasicBlock node) {
		int in = inDegreeOf(node);
		int out = outDegreeOf(node);
		if (in + out == 0 && getNodeCount() != 1)
			throw new IllegalStateException(
					"node with neither child nor parent only allowed if CFG consists of a single block: "
							+ node.toString());

		if (getNodeCount() == 1 && !(isEntryBlock(node) && isExitBlock(node)))
			throw new IllegalStateException(
					"if a CFG consists of a single basic block that block must be both entry and exitBlock: "
							+ node.toString());
	}

	void checkNodeMinimalityConstraint(BasicBlock node) {

		if (hasNPartentsMChildren(node, 1, 1)) {
			for (BasicBlock child : getChildren(node))
				if (hasNPartentsMChildren(child, 1, 1))
					throw new IllegalStateException(
							"whenever a node has exactly one child and one parent, it is expected that the same is true for either of those");

			for (BasicBlock parent : getParents(node))
				if (hasNPartentsMChildren(parent, 1, 1))
					throw new IllegalStateException(
							"whenever a node has exactly one child and one parent, it is expected that the same is true for either of those");
		}
	}

	// inherited from ControlFlowGraph
	
	@Override
	public boolean containsInstruction(BytecodeInstruction v) {
		if(v==null)
			return false;
		
		for(BasicBlock block : vertexSet())
			if(block.containsInstruction(v))
				return true;
		
		return false;
	}

	@Override
	public BytecodeInstruction getInstruction(int instructionId) {
		
		BytecodeInstruction searchedFor = BytecodeInstructionPool
				.getInstruction(className, methodName, instructionId);
		
		if(containsInstruction(searchedFor))
			return searchedFor;
		
		return null;
	}
	
	@Override
	public BytecodeInstruction getBranch(int branchId) {
		
		Branch searchedFor = BranchPool.getBranch(branchId);
		if(searchedFor==null)
			return null;
		
		if(containsInstruction(searchedFor))
			return searchedFor;

		// TODO more sanity checks?
		
		return null;
	}

	public BytecodeInstruction getEntryPoint() {
		return entryPoint;
	}

	public Set<BytecodeInstruction> getExitPoints() {
		// TODO copy set, don't return private reference
		return exitPoints;
	}

	public Set<BytecodeInstruction> getBranches() {
		// TODO copy set, don't return private reference
		return branches;
	}

	public Set<BytecodeInstruction> getJoins() {
		// TODO copy set, don't return private reference		
		return joins;
	}

}
