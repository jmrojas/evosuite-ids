package de.unisb.cs.st.evosuite.coverage.branch;

import org.objectweb.asm.tree.LabelNode;

import de.unisb.cs.st.evosuite.cfg.BytecodeInstruction;

/**
 * An object of this class corresponds to a Branch inside the class under test.
 * 
 * Branches are created by the CFGMethodAdapter via the BranchPool. Each Branch
 * holds its corresponding CFGVertex from the ControlFlowGraph.
 * 
 * For SWITCH statements each case <key>: block corresponds to a Branch that can
 * be created by constructing a Branch with the SWITCH statement and the <key>
 * as the targetCaseValue. The default: case of switch statement can also be
 * modeled this way - it has the targetCaseValue set to null.
 * 
 * TODO:
 * 
 * don't extend from BytecodeInstruction! rather hold one where necessary
 * 
 * otherwise a Branch "is a" BytecodeInstruction even though it is not
 * associated with an instruction (root-branch, case's in a switch ... )
 *
 * @author Andre Mis
 */
public class Branch {

	private final int actualBranchId;

	private boolean isSwitch = false;

	// for switch branches this value indicates to which case of the switch this
	// branch belongs. if this value is null and this is in fact a switch this
	// means this branch is the default: case of that switch
	private Integer targetCaseValue = null;
	
	private LabelNode targetLabel = null;
	
	private BytecodeInstruction instruction;

	/**
	 * Constructor for usual jump instruction Branches, that are not SWITCH
	 * instructions. 
	 */
	public Branch(BytecodeInstruction branchInstruction, int actualBranchId) {
		if (!branchInstruction.isBranch())
			throw new IllegalArgumentException(
					"only branch instructions are accepted");

		this.instruction = branchInstruction;
		this.actualBranchId = actualBranchId;

		if (this.actualBranchId < 1)
			throw new IllegalStateException(
					"expect branch to have actualBranchId set to positive value");
	}

	/**
	 * Constructor for SWITCH-Case: Branches
	 * 
	 */
	public Branch(BytecodeInstruction switchInstruction, Integer targetCaseValue, LabelNode targetLabel,
			int actualBranchId) {
		if (!switchInstruction.isSwitch())
			throw new IllegalArgumentException("switch instruction expected");
		if(targetLabel == null)
			throw new IllegalArgumentException("expect targetLabel to not be null for case branches");

		this.instruction = switchInstruction;
		this.actualBranchId = actualBranchId;

		this.targetLabel = targetLabel;
		this.targetCaseValue = targetCaseValue;
		this.isSwitch = true;

		if (this.actualBranchId < 1)
			throw new IllegalStateException(
					"expect branch to have actualBranchId set to positive value");
	}

	public int getActualBranchId() {
		return actualBranchId;
	}

	public boolean isDefaultCase() {
		return isSwitch && targetCaseValue == null;
	}
	
	public boolean isActualCase() {
		return isSwitch && targetCaseValue != null;
	}
	
	public Integer getTargetCaseValue() {
		// in order to avoid confusion when targetCaseValue is null
		if (!isSwitch)
			throw new IllegalStateException(
					"method only allowed to be called on non-switch-Branches");

		return targetCaseValue; // null for default case
	}
	
	public LabelNode getTargetLabel() {
		if(!isSwitch)
			throw new IllegalStateException("call only allowed on switch instructions");
		
		return targetLabel;
	}
	
	public BytecodeInstruction getInstruction() {
		return instruction;
	}
	
	public String getClassName() {
		return instruction.getClassName();
	}
	
	public String getMethodName() {
		return instruction.getMethodName();
	}
	
	public boolean isSwitch() {
		return isSwitch;
	}

	@Override
	public String toString() {
		String r = "I" + instruction.getInstructionId();
		r += " Branch " + getActualBranchId();
		r += " " + instruction.getInstructionType();
		if (isSwitch) {
			if (targetCaseValue != null)
				r += " Case " + targetCaseValue;
			else
				r += " Default-Case";
		}

		r += " L" + instruction.getLineNumber();

		return r;
	}
}
