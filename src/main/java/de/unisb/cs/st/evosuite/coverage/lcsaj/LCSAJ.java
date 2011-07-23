package de.unisb.cs.st.evosuite.coverage.lcsaj;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.objectweb.asm.tree.AbstractInsnNode;

import de.unisb.cs.st.evosuite.cfg.BytecodeInstruction;
import de.unisb.cs.st.evosuite.coverage.branch.Branch;
import de.unisb.cs.st.evosuite.coverage.branch.BranchPool;

public class LCSAJ {

	private static Logger logger = Logger.getLogger(LCSAJ.class);

	// All branches passed in the LCSAJ
	private final List<Branch> branches = new ArrayList<Branch>();
	// Information needed and maintained in the LCSAJ instrumentation and detection algorithm
	private AbstractInsnNode lastAccessedNode;

	private int id = -1;
	// Class and method where the LCSAJ occurs
	private final String className;
	private final String methodName;
	
	private int positionReached = 0;
	
	public LCSAJ(String className, String methodName, BytecodeInstruction start) {
		this.className = className;
		this.methodName = methodName;
		this.lastAccessedNode = start.getASMNode();

		if (!BranchPool.isKnownAsBranch(start)) {
			if (methodName.startsWith("<init>") && start.getInstructionId() <= 1) {

			}
			start.forceBranch();
			BranchPool.registerAsBranch(start);
			logger.warn("Registering new branch for start node");
		}

		Branch branch = BranchPool.getBranchForInstruction(start);
		branches.add(branch);
	}

	//Copy constructor
	public LCSAJ(LCSAJ l) {
		this.className = l.getClassName();
		this.methodName = l.getMethodName();

		this.branches.addAll(l.branches);

		this.lastAccessedNode = l.getLastNodeAccessed();
	}

	public int getID() {
		return this.id;
	}

	/**
	 * Set an (not necessarily unique) new ID for a LCSAJ. While adding a LCSAJ
	 * into the Pool the number is set to its occurrence during the detection of
	 * all LCSAJ in a method
	 */
	public void setID(int id) {
		this.id = id;
	}

	public List<Branch> getBranchInstructions() {
		return branches;
	}

	public Branch getBranch(int position) {
		return branches.get(position);
	}

	public int getBranchID(int position) {
		return branches.get(position).getActualBranchId();
	}

	public Branch getStartBranch() {
		return branches.get(0);
	}

	public Branch getLastBranch() {
		return branches.get(branches.size() - 1);
	}

	public AbstractInsnNode getLastNodeAccessed() {
		return lastAccessedNode;
	}

	public String getClassName() {
		return this.className;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void lookupInstruction(int id, BytecodeInstruction instruction) {
		lastAccessedNode = instruction.getASMNode();

		if (instruction.isBranch()) {
			Branch branch = BranchPool.getBranchForInstruction(instruction);
			branches.add(branch);

		} else if (instruction.isReturn() || instruction.isThrow()
		        || instruction.isGoto()) {

			if (!BranchPool.isKnownAsBranch(instruction)) {
				instruction.forceBranch();
				BranchPool.registerAsBranch(instruction);
				logger.warn("Registering new branch");
			}

			Branch branch = BranchPool.getBranchForInstruction(instruction);
			branches.add(branch);
		}
	}

	public int length() {
		return branches.size();
	}

	@Override
	public String toString() {
		String output = "LCSAJ no.: " + this.id;
		output += " in " + this.className + "/" + this.methodName + ". Branches passed: ";
		for (Branch b : branches)
			output += " -> " + b.getActualBranchId();
		//output += "\n";
		// for (Branch b : branches)
		//	output += " -> " + b.getASMNodeString() + "\n";
		return output;
	}
	
	public void setPositionReached(int position){
		this.positionReached = position;
	}
	
	public int getdPositionReached(){
		return this.positionReached;
	}
}
