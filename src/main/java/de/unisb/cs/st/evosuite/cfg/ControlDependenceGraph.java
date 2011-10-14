package de.unisb.cs.st.evosuite.cfg;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.coverage.branch.Branch;

public class ControlDependenceGraph extends
		EvoSuiteGraph<BasicBlock, ControlFlowEdge> {

	private static Logger logger = LoggerFactory
			.getLogger(ControlDependenceGraph.class);

	private final ActualControlFlowGraph cfg;

	private final String className;
	private final String methodName;

	public ControlDependenceGraph(ActualControlFlowGraph cfg) {
		super(ControlFlowEdge.class);

		this.cfg = cfg;
		this.className = cfg.getClassName();
		this.methodName = cfg.getMethodName();

		computeGraph();
		// TODO check sanity
	}

	/**
	 * Convenience method redirecting to getControlDependentBranches(BasicBlock)
	 * if the given instruction is known to this CDG. Otherwise an
	 * IllegalArgumentException will be thrown.
	 * 
	 * Should no longer be used: rather ask a BasicBlock for its CDs, so it can
	 * cache it.
	 */
	// public Set<ControlDependency>
	// getControlDependentBranches(BytecodeInstruction ins) {
	// if (ins == null)
	// throw new IllegalArgumentException("null not accepted");
	// if (!knowsInstruction(ins))
	// throw new IllegalArgumentException(
	// "instruction not known to this CDG: " + methodName
	// + ins.toString());
	//
	// BasicBlock insBlock = ins.getBasicBlock();
	//
	// return getControlDependentBranches(insBlock);
	// }

	/**
	 * Checks whether this graph knows the given instruction. That is there is a
	 * BasicBlock in this graph's vertexSet containing the given instruction.
	 */
	public boolean knowsInstruction(BytecodeInstruction ins) {
		return cfg.knowsInstruction(ins);
	}

	/**
	 * Returns a Set containing all Branches the given BasicBlock is control
	 * dependent on.
	 * 
	 * This is for each incoming ControlFlowEdge of the given block within this
	 * CDG, the branch instruction of that edge will be added to the returned
	 * set.
	 */
	public Set<ControlDependency> getControlDependentBranches(
			BasicBlock insBlock) {
		if (insBlock == null)
			throw new IllegalArgumentException("null not accepted");
		if (!containsVertex(insBlock))
			throw new IllegalArgumentException("unknown block: "
					+ insBlock.getName());

		if(insBlock.hasControlDependenciesSet())
			return insBlock.getControlDependencies();
		
		Set<ControlDependency> r = retrieveControlDependencies(insBlock, new HashSet<ControlFlowEdge>()); 

		return r;
	}

	private Set<ControlDependency> retrieveControlDependencies(
			BasicBlock insBlock, Set<ControlFlowEdge> handled) {
		
		Set<ControlDependency> r = new HashSet<ControlDependency>();

		for (ControlFlowEdge e : incomingEdgesOf(insBlock)) {
			if(handled.contains(e))
				continue;
			handled.add(e);
			
			ControlDependency cd = e.getControlDependency();
			if (cd != null)
				r.add(cd);
			else {
				BasicBlock in = getEdgeSource(e);
				if (!in.equals(insBlock))
					r.addAll(retrieveControlDependencies(in,handled));
			}

		}

		// TODO need RootBranch Object!!!
		// TODO the following does not hold! a node can be dependent on the root
		// branch AND another branch! TODO !!!
		// // sanity check
		// if (r.isEmpty()) {
		// Set<BasicBlock> insParents = getParents(insBlock);
		// if (insParents.size() != 1) {
		//
		// for (BasicBlock b : insParents)
		// logger.error(b.toString());
		//
		// throw new IllegalStateException(
		// "expect instruction dependent on root branch to have exactly one parent in it's CDG namely the EntryBlock: "
		// + insBlock.toString());
		// }
		//
		// for (BasicBlock b : insParents)
		// if (!b.isEntryBlock() && !getControlDependentBranches(b).isEmpty())
		// throw new IllegalStateException(
		// "expect instruction dependent on root branch to have exactly one parent in it's CDG namely the EntryBlock"
		// + insBlock.toString() + methodName);
		// }

		return r;
	}

	public Set<Integer> getControlDependentBranchIds(BasicBlock ins) {

		Set<ControlDependency> dependentBranches = getControlDependentBranches(ins);

		Set<Integer> r = new HashSet<Integer>();

		for (ControlDependency cd : dependentBranches) {
			if (cd == null)
				throw new IllegalStateException(
						"expect set returned by getControlDependentBranches() not to contain null");

			r.add(cd.getBranch().getActualBranchId());
		}

		// to indicate this is only dependent on root branch,
		// meaning entering the method
		if (isRootDependent(ins))
			r.add(-1);

		return r;
	}

	// /**
	// * Determines whether the given Branch has to be evaluated to true or to
	// * false in order to reach the given BytecodeInstruction - given the
	// * instruction is directly control dependent on the given Branch.
	// *
	// * In other words this method checks whether there is an incoming
	// * ControlFlowEdge to the given instruction's BasicBlock containing the
	// * given Branch as it's BranchInstruction and if so, that edges
	// * branchExpressionValue is returned. If the given instruction is directly
	// * control dependent on the given branch such a ControlFlowEdge must
	// exist.
	// * Should this assumption be violated an IllegalStateException is thrown.
	// *
	// * If the given instruction is not known to this CDG or not directly
	// control
	// * dependent on the given Branch an IllegalArgumentException is thrown.
	// */
	// public boolean getBranchExpressionValue(BytecodeInstruction ins, Branch
	// b) {
	// if (ins == null)
	// throw new IllegalArgumentException("null given");
	// if (!ins.isDirectlyControlDependentOn(b))
	// throw new IllegalArgumentException(
	// "only allowed to call this method for instructions and their directly control dependent branches");
	// if (b == null)
	// return true; // root branch special case
	//
	// BasicBlock insBlock = ins.getBasicBlock();
	//
	// for (ControlFlowEdge e : incomingEdgesOf(insBlock)) {
	// if (e.isExceptionEdge() && !e.hasControlDependency())
	// continue;
	//
	// Branch current = e.getBranchInstruction();
	// if (current == null) {
	// try {
	// BasicBlock in = getEdgeSource(e);
	// return getBranchExpressionValue(in.getFirstInstruction(), b);
	// } catch (Exception ex) {
	// continue;
	// }
	// } else if (current.equals(b))
	// return e.getBranchExpressionValue();
	// }
	//
	// throw new IllegalStateException(
	// "expect CDG to contain an incoming edge to the given instructions basic block containing the given branch if isControlDependent() returned true on those two ");
	// }

	// initialization

	/**
	 * Determines whether the given BytecodeInstruction is directly control
	 * dependent on the given Branch. It's BasicBlock is control dependent on
	 * the given Branch.
	 * 
	 * If b is null, it is assumed to be the root branch.
	 * 
	 * If the given instruction is not known to this CDG an
	 * IllegalArgumentException is thrown.
	 */
	public boolean isDirectlyControlDependentOn(BytecodeInstruction ins,
			Branch b) {
		if (ins == null)
			throw new IllegalArgumentException("null given");

		BasicBlock insBlock = ins.getBasicBlock();

		return isDirectlyControlDependentOn(insBlock, b);
	}

	/**
	 * Determines whether the given BasicBlock is directly control dependent on
	 * the given Branch. Meaning within this CDG there is an incoming
	 * ControlFlowEdge to this instructions BasicBlock holding the given Branch
	 * as it's branchInstruction.
	 * 
	 * If b is null, it is assumed to be the root branch.
	 * 
	 * If the given instruction is not known to this CDG an
	 * IllegalArgumentException is thrown.
	 */
	public boolean isDirectlyControlDependentOn(BasicBlock insBlock, Branch b) {
		Set<ControlFlowEdge> incomming = incomingEdgesOf(insBlock);

		if (incomming.size() == 1) {
			// in methods with a try-catch-block it is possible that there
			// are nodes in the CDG that have exactly one parent with an
			// edge without a branchInstruction that is a non exceptional
			// edge
			// should the given instruction be such a node, follow the parents
			// until
			// you reach one where the above conditions are not met

			for (ControlFlowEdge e : incomming) {
				if (!e.hasControlDependency() && !e.isExceptionEdge()) {
					return isDirectlyControlDependentOn(getEdgeSource(e), b);
				}
			}
		}

		boolean isRootDependent = isRootDependent(insBlock);
		if (b == null)
			return isRootDependent;
		if (isRootDependent && b != null)
			return false;

		for (ControlFlowEdge e : incomming) {
			Branch current = e.getBranchInstruction();

			if (e.isExceptionEdge()) {
				if (current != null)
					throw new IllegalStateException(
							"expect exception edges to have no BranchInstruction set");
				else
					continue;
			}

			if (current == null)
				continue;
			// throw new IllegalStateException(
			// "expect non exceptional ControlFlowEdges whithin the CDG that don't come from EntryBlock to have branchInstructions set "
			// + insBlock.toString() + methodName);

			if (current.equals(b))
				return true;
		}

		return false;

	}

	/**
	 * Checks whether the given instruction is dependent on the root branch of
	 * it's method
	 * 
	 * This is the case if the BasicBlock of the given instruction is directly
	 * adjacent to the EntryBlock
	 */
	public boolean isRootDependent(BytecodeInstruction ins) {

		return isRootDependent(ins.getBasicBlock());
	}

	/**
	 * Checks whether the given basicBlock is dependent on the root branch of
	 * it's method
	 * 
	 * This is the case if the BasicBlock of the given instruction is directly
	 * adjacent to the EntryBlock
	 */
	public boolean isRootDependent(BasicBlock insBlock) {
		if (isAdjacentToEntryBlock(insBlock))
			return true;

		for (ControlFlowEdge in : incomingEdgesOf(insBlock)) {
			if (in.hasControlDependency())
				continue;

			BasicBlock inBlock = getEdgeSource(in);
			if (isRootDependent(inBlock) && !!inBlock.equals(insBlock))
				return true;
		}

		return false;

	}

	/**
	 * Returns true if the given BasicBlock has an incoming edge from this CDG's
	 * EntryBlock or is itself the EntryBlock
	 */
	public boolean isAdjacentToEntryBlock(BasicBlock insBlock) {

		if (insBlock.isEntryBlock())
			return true;

		Set<BasicBlock> parents = getParents(insBlock);
		for (BasicBlock parent : parents)
			if (parent.isEntryBlock())
				return true;

		return false;
	}

	// /**
	// * If the given instruction is known to this graph, the BasicBlock holding
	// * that instruction is returned. Otherwise an IllegalArgumentException
	// will
	// * be thrown.
	// *
	// * Just a convenience method that more or less just redirects the call to
	// * the CFG
	// */
	// public BasicBlock getBlockOf(BytecodeInstruction ins) {
	// if (ins == null)
	// throw new IllegalArgumentException("null given");
	// if (!cfg.knowsInstruction(ins))
	// throw new IllegalArgumentException("unknown instruction");
	//
	// BasicBlock insBlock = cfg.getBlockOf(ins);
	// if (insBlock == null)
	// throw new IllegalStateException(
	// "expect CFG to return non-null BasicBlock for instruction it knows");
	//
	// return insBlock;
	// }

	// init

	private void computeGraph() {

		createGraphNodes();
		computeControlDependence();
	}

	private void createGraphNodes() {
		// copy CFG nodes
		addVertices(cfg);

		for (BasicBlock b : vertexSet())
			if (b.isExitBlock() && !graph.removeVertex(b)) // TODO refactor
				throw new IllegalStateException(
						"internal error building up CDG");

	}

	private void computeControlDependence() {

		ActualControlFlowGraph rcfg = cfg.computeReverseCFG();
		DominatorTree<BasicBlock> dt = new DominatorTree<BasicBlock>(rcfg);

		for (BasicBlock b : rcfg.vertexSet())
			if (!b.isExitBlock()) {

				logger.debug("DFs for: " + b.getName());
				for (BasicBlock cd : dt.getDominatingFrontiers(b)) {
					ControlFlowEdge orig = cfg.getEdge(cd, b);

					if (!cd.isEntryBlock() && orig == null) {
						// in for loops for example it can happen that cd and b
						// were not directly adjacent to each other in the CFG
						// but rather there were some intermediate nodes between
						// them and the needed information is inside one of the
						// edges
						// from cd to the first intermediate node. more
						// precisely cd is expected to be a branch and to have 2
						// outgoing edges, one for evaluating to true (jumping)
						// and one for false. one of them can be followed and b
						// will eventually be reached, the other one can not be
						// followed in that way. TODO TRY!

						logger.debug("cd: " + cd.toString());
						logger.debug("b: " + b.toString());

						// TODO this is just for now! unsafe and probably not
						// even correct!
						Set<ControlFlowEdge> candidates = cfg
								.outgoingEdgesOf(cd);
						if (candidates.size() < 2)
							throw new IllegalStateException("unexpected");

						boolean leadToB = false;
						boolean skip = false;

						for (ControlFlowEdge e : candidates) {
							if (!e.hasControlDependency()) {
								skip = true;
								break;
							}

							if (cfg.leadsToNode(e, b)) {
								if (leadToB)
									orig = null;
								// throw new
								// IllegalStateException("unexpected");
								leadToB = true;

								orig = e;
							}
						}
						if (skip)
							continue;
						if (!leadToB)
							throw new IllegalStateException("unexpected");
					}

					if (orig == null)
						logger.debug("orig still null!");

					if (!addEdge(cd, b, new ControlFlowEdge(orig)))
						throw new IllegalStateException(
								"internal error while adding CD edge");

					logger.debug("  " + cd.getName());
				}
			}
	}

	@Override
	public String getName() {
		// return "CDG" + graphId + "_" + methodName;
		return methodName + "_" +  "CDG";
	}

	@Override
	protected String dotSubFolder() {
		return toFileString(className) + "/CDG/";
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
}
