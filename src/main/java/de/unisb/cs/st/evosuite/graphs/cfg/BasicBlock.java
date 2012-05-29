/**
 * Copyright (C) 2012 Gordon Fraser, Andrea Arcuri
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
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unisb.cs.st.evosuite.graphs.cfg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.graphs.GraphPool;
import de.unisb.cs.st.evosuite.graphs.cdg.ControlDependenceGraph;

/**
 * This class is used to represent basic blocks in the control flow graph.
 * 
 * A basic block is a list of instructions for which the following holds:
 * 
 * Whenever control flow reaches the first instruction of this blocks list,
 * control flow will pass through all the instructions of this list successively
 * and not pass another instruction in the mean time. The first element in this
 * blocks list does not have a parent in the CFG that can be prepended to the
 * list and the same would still hold true Finally the last element in this list
 * does not have a child inside the CFG that could be appended to the list such
 * that the above still holds true
 * 
 * In other words: - the first/last element of this blocks list has either 0 or
 * >=2 parents/children in the CFG - every other element in the list has exactly
 * 1 parent and exactly 1 child in the raw CFG
 * 
 * 
 * Taken from:
 * 
 * "Efficiently Computing Static Single Assignment Form and the Control
 * Dependence Graph" RON CYTRON, JEANNE FERRANTE, BARRY K. ROSEN, and MARK N.
 * WEGMAN IBM Research Division and F. KENNETH ZADECK Brown University 1991
 * 
 * 
 * @see cfg.ActualControlFlowGraph
 * @author Andre Mis
 */
public class BasicBlock implements Serializable, Iterable<BytecodeInstruction> {

	private static final long serialVersionUID = -3465486470017841484L;

	private static Logger logger = LoggerFactory.getLogger(BasicBlock.class);

	private static int blockCount = 0;

	private int id = -1;
	protected String className;
	protected String methodName;

	// experiment: since finding the control dependent branches in the CDG might
	// take a little to long, we might want to remember them
	private Set<ControlDependency> controlDependencies;
	private Set<Integer> controlDependentBranchIDs;

	protected boolean isAuxiliaryBlock = false;

	private final List<BytecodeInstruction> instructions = new ArrayList<BytecodeInstruction>();

	// DONE reference each BytecodeInstruction's BasicBlock at the instruction
	// DONE determine ControlDependentBranches once for each BasicBlock, then
	// ask BasicBloc, whenever instruction is asked
	// TODO remember distance to each control dependent Branch in order to speed
	// up ControlFlowDistance calculation even more

	public BasicBlock(String className, String methodName,
	        List<BytecodeInstruction> blockNodes) {
		if (className == null || methodName == null || blockNodes == null)
			throw new IllegalArgumentException("null given");

		this.className = className;
		this.methodName = methodName;

		setId();
		setInstructions(blockNodes);

		checkSanity();
	}

	/**
	 * Used by Entry- and ExitBlocks
	 */
	protected BasicBlock(String className, String methodName) {
		if (className == null || methodName == null)
			throw new IllegalArgumentException("null given");

		this.className = className;
		this.methodName = methodName;

		this.isAuxiliaryBlock = true;
	}

	// CDs

	/**
	 * Returns the ControlDependenceGraph of this instructions method
	 * 
	 * Convenience method. Redirects the call to GraphPool.getCDG()
	 */
	public ControlDependenceGraph getCDG() {

		ControlDependenceGraph myCDG = GraphPool.getCDG(className, methodName);
		if (myCDG == null)
			throw new IllegalStateException(
			        "expect GraphPool to know CDG for every method for which an instruction is known");

		return myCDG;
	}

	/**
	 * Returns all branchIds of Branches this instruction is directly control
	 * dependent on as determined by the ControlDependenceGraph for this
	 * instruction's method.
	 * 
	 * If this instruction is control dependent on the root branch the id -1
	 * will be contained in this set
	 */
	public Set<Integer> getControlDependentBranchIds() {

		ControlDependenceGraph myDependence = getCDG();

		if (controlDependentBranchIDs == null)
			controlDependentBranchIDs = myDependence.getControlDependentBranchIds(this);

		return controlDependentBranchIDs;
	}

	/**
	 * Returns a cfg.Branch object for each branch this instruction is control
	 * dependent on as determined by the ControlDependenceGraph. If this
	 * instruction is only dependent on the root branch this method returns an
	 * empty set
	 * 
	 * If this instruction is a Branch and it is dependent on itself - which can
	 * happen in loops for example - the returned set WILL contain this. If you
	 * do not need the full set in order to avoid loops, call
	 * getAllControlDependentBranches instead
	 */
	public Set<ControlDependency> getControlDependencies() {

		if (controlDependencies == null)
			controlDependencies = getCDG().getControlDependentBranches(this);

		//		return new HashSet<ControlDependency>(controlDependentBranches);
		return controlDependencies;
	}

	public boolean hasControlDependenciesSet() {
		return controlDependencies != null;
	}

	// initialization

	private void setInstructions(List<BytecodeInstruction> blockNodes) {
		for (BytecodeInstruction instruction : blockNodes) {
			if (!appendInstruction(instruction))
				throw new IllegalStateException(
				        "internal error while addind instruction to basic block list");
		}
		if (instructions.isEmpty())
			throw new IllegalStateException(
			        "expect each basic block to contain at least one instruction");
	}

	private boolean appendInstruction(BytecodeInstruction instruction) {
		if (instruction == null)
			throw new IllegalArgumentException("null given");
		if (!className.equals(instruction.getClassName()))
			throw new IllegalArgumentException(
			        "expect elements of a basic block to be inside the same class");
		if (!methodName.equals(instruction.getMethodName()))
			throw new IllegalArgumentException(
			        "expect elements of a basic block to be inside the same class");
		if (instruction.hasBasicBlockSet())
			throw new IllegalArgumentException(
			        "expect to get instruction without BasicBlock already set");
		if (instructions.contains(instruction))
			throw new IllegalArgumentException(
			        "a basic block can not contain the same element twice");

		// not sure if this holds:
		// .. apparently it doesn't. at least check
		// fails for java2.util2.Pattern TODO

		// BytecodeInstruction previousInstruction = getLastInstruction();
		// if (previousInstruction != null
		// && instruction.getInstructionId() < previousInstruction
		// .getInstructionId())
		// throw new IllegalStateException(
		// "expect instructions in a basic block to be ordered by their instructionId");

		instruction.setBasicBlock(this);

		return instructions.add(instruction);
	}

	private void setId() {
		blockCount++;
		this.id = blockCount;
	}

	// retrieve information

	public boolean containsInstruction(BytecodeInstruction instruction) {
		if (instruction == null)
			throw new IllegalArgumentException("null given");

		return instructions.contains(instruction);
	}

	public boolean constainsInstruction(AbstractInsnNode insnNode) {
		for (BytecodeInstruction instruction : instructions) {
			if (instruction.getASMNode().equals(instruction))
				return true;
		}
		return false;
	}

	public BytecodeInstruction getFirstInstruction() {
		if (instructions.isEmpty())
			return null;
		return instructions.get(0);
	}

	public BytecodeInstruction getLastInstruction() {
		if (instructions.isEmpty())
			return null;
		return instructions.get(instructions.size() - 1);
	}

	public int getFirstLine() {
		for (BytecodeInstruction ins : instructions)
			if (ins.hasLineNumberSet())
				return ins.getLineNumber();

		return -1;
	}

	public int getLastLine() {

		int r = -1;

		for (BytecodeInstruction ins : instructions)
			if (ins.hasLineNumberSet())
				r = ins.getLineNumber();

		return r;
	}

	public String getName() {
		return (isAuxiliaryBlock ? "aux" : "") + "BasicBlock " + id;
		// +" - "+methodName;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public String explain() {
		StringBuilder r = new StringBuilder();
		r.append(getName() + ":\n");

		int i = 0;
		for (BytecodeInstruction instruction : instructions) {
			i++;
			r.append("\t" + i + ")\t" + instruction.toString() + "\n");
		}

		return r.toString();
	}

	// inherited from Object

	@Override
	public String toString() {

		String r = "BB" + id;

		if (instructions.size() < 5)
			for (BytecodeInstruction ins : instructions)
				r = r.trim() + " " + ins.getInstructionType();
		else
			r += " " + getFirstInstruction().getInstructionType() + " ... "
			        + getLastInstruction().getInstructionType();

		int startLine = getFirstLine();
		int endLine = getLastLine();
		r += " l" + (startLine == -1 ? "?" : startLine + "");
		r += "-l" + (endLine == -1 ? "?" : endLine + "");

		return r;
	}

	@Override
	public boolean equals(Object obj) {

		// logger.debug(getName() + " got asked asked for equality");

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BasicBlock))
			return false;

		BasicBlock other = (BasicBlock) obj;

		// logger.debug(".. other object different instance of BasicBlock "+other.getName());

		if (!className.equals(other.className))
			return false;
		if (!methodName.equals(other.methodName))
			return false;
		if (this.instructions.size() != other.instructions.size())
			return false;
		for (BytecodeInstruction instruction : other.instructions)
			if (!this.instructions.contains(instruction))
				return false;

		// logger.debug("was different instance but equal");

		return true;
	}

	// sanity check

	public void checkSanity() {

		logger.debug("checking sanity of " + toString());

		// TODO

		// not true, there are branches that don't really jump
		// for example if you have no statement in a then-part:
		// if (exp) { ; }
		// you will not have a second outgoing edge for that if

		// for(BytecodeInstruction instruction : instructions) {
		// if (!instruction.equals(getLastInstruction())
		// && instruction.isActualBranch())
		// throw new IllegalStateException(
		// "expect actual branches to always end a basic block: "+instruction.toString()+" \n"+explain());
		// }

		// TODO handle specialBlocks
	}

	public boolean isEntryBlock() {
		return false;
	}

	public boolean isExitBlock() {
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<BytecodeInstruction> iterator() {
		return instructions.iterator();
	}
}
