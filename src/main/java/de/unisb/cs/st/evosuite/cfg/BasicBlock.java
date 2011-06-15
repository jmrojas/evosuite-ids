package de.unisb.cs.st.evosuite.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.mutation.Mutateable;

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
public class BasicBlock implements Mutateable {

	private static Logger logger = Logger.getLogger(BasicBlock.class);

	private static int blockCount = 0;

	private int id = -1;
	protected String className;
	protected String methodName;

	protected boolean isAuxiliaryBlock = false;

	private List<BytecodeInstruction> instructions = new ArrayList<BytecodeInstruction>();

	private Map<Long, Integer> mutant_distance = new HashMap<Long, Integer>();

	// TODO reference each BytecodeInstruction's BasicBlock at the instruction
	// TODO determine ControlDependentBranches once for each BasicBlock, then
	// ask BasicBloc, whenever instruction is asked
	// TODO remember distance to each control dependent Branch in order to speed
	// up ControlFlowDistance calculation even more

	public BasicBlock(String className, String methodName, List<BytecodeInstruction> blockNodes) {
		if ((className == null) || (methodName == null) || (blockNodes == null)) {
			throw new IllegalArgumentException("null given");
		}

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
		if ((className == null) || (methodName == null)) {
			throw new IllegalArgumentException("null given");
		}

		this.className = className;
		this.methodName = methodName;

		this.isAuxiliaryBlock = true;
	}

	// initialization

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

	public boolean containsInstruction(BytecodeInstruction instruction) {
		if (instruction == null) {
			throw new IllegalArgumentException("null given");
		}

		return instructions.contains(instruction);
	}

	@Override
	public boolean equals(Object obj) {

		// logger.debug(getName() + " got asked asked for equality");

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BasicBlock)) {
			return false;
		}

		BasicBlock other = (BasicBlock) obj;

		// logger.debug(".. other object different instance of BasicBlock "+other.getName());

		if (!className.equals(other.className)) {
			return false;
		}
		if (!methodName.equals(other.methodName)) {
			return false;
		}
		if (this.instructions.size() != other.instructions.size()) {
			return false;
		}
		for (BytecodeInstruction instruction : other.instructions) {
			if (!this.instructions.contains(instruction)) {
				return false;
			}
		}

		// logger.debug("was different instance but equal");

		return true;
	}

	// retrieve information

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

	public String getClassName() {
		return className;
	}

	@Override
	public int getDistance(long mutationId) {
		if (mutant_distance.containsKey(mutationId)) {
			return mutant_distance.get(mutationId);
		}
		return Integer.MAX_VALUE;
	}

	public BytecodeInstruction getFirstInstruction() {
		if (instructions.isEmpty()) {
			return null;
		}
		return instructions.get(0);
	}

	public int getFirstLine() {
		for (BytecodeInstruction ins : instructions) {
			if (ins.hasLineNumberSet()) {
				return ins.getLineNumber();
			}
		}

		return -1;
	}

	public BytecodeInstruction getLastInstruction() {
		if (instructions.isEmpty()) {
			return null;
		}
		return instructions.get(instructions.size() - 1);
	}

	public int getLastLine() {

		int r = -1;

		for (BytecodeInstruction ins : instructions) {
			if (ins.hasLineNumberSet()) {
				r = ins.getLineNumber();
			}
		}

		return r;
	}

	public String getMethodName() {
		return methodName;
	}

	// mutation part

	public BytecodeInstruction getMutation(long mutationId) {
		for (BytecodeInstruction instruction : instructions) {
			if (instruction.hasMutation(mutationId)) {
				return instruction;
			}
		}

		return null;
	}

	@Override
	public List<Long> getMutationIds() {
		List<Long> r = new ArrayList<Long>();
		for (BytecodeInstruction instruction : instructions) {
			r.addAll(instruction.getMutationIds());
		}

		return r;
	}

	@Override
	public String getName() {
		return (isAuxiliaryBlock ? "aux" : "") + "BasicBlock " + id;
		// +" - "+methodName;
	}

	@Override
	public boolean hasMutation(long mutationId) {

		return getMutationIds().contains(mutationId);
	}

	public boolean isEntryBlock() {
		return false;
	}

	public boolean isExitBlock() {
		return false;
	}

	@Override
	public boolean isMutation() {

		return !getMutationIds().isEmpty();
	}

	// inherited from Object

	@Override
	public void setDistance(long mutationId, int distance) {
		mutant_distance.put(mutationId, distance);
	}

	@Override
	public String toString() {

		String r = "BB" + id;

		if (instructions.size() < 5) {
			for (BytecodeInstruction ins : instructions) {
				r = r.trim() + " " + ins.getInstructionType();
			}
		} else {
			r += " " + getFirstInstruction().getInstructionType() + " ... " + getLastInstruction().getInstructionType();
		}

		int startLine = getFirstLine();
		int endLine = getLastLine();
		r += " l" + (startLine == -1 ? "?" : startLine + "");
		r += "-l" + (endLine == -1 ? "?" : endLine + "");

		return r;
	}

	// sanity check

	private boolean appendInstruction(BytecodeInstruction instruction) {
		if (instruction == null) {
			throw new IllegalArgumentException("null given");
		}
		if (!className.equals(instruction.getClassName())) {
			throw new IllegalArgumentException("expect elements of a basic block to be inside the same class");
		}
		if (!methodName.equals(instruction.getMethodName())) {
			throw new IllegalArgumentException("expect elements of a basic block to be inside the same class");
		}
		if (instruction.hasBasicBlockSet()) {
			throw new IllegalArgumentException("expect to get instruction without BasicBlock already set");
		}
		if (instructions.contains(instruction)) {
			throw new IllegalArgumentException("a basic block can not contain the same element twice");
		}

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

	private void setInstructions(List<BytecodeInstruction> blockNodes) {
		for (BytecodeInstruction instruction : blockNodes) {
			if (!appendInstruction(instruction)) {
				throw new IllegalStateException("internal error while addind instruction to basic block list");
			}
		}
		if (instructions.isEmpty()) {
			throw new IllegalStateException("expect each basic block to contain at least one instruction");
		}
	}
}
