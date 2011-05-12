package de.unisb.cs.st.evosuite.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent basic blocks in the control flow graph.
 * 
 * A basic block is a list of instructions for which the following holds:
 *  
 *  Whenever control flow reaches the first instruction of this blocks list,
 *   control flow will pass through all the instructions of this list
 *   successively and not pass another instruction in the mean time.
 *  The first element in this blocks list does not have a parent in the
 *   CFG that can be prepended to the list and the same would still hold true
 *  Finally the last element in this list does not have a child inside the CFG
 *   that could be appended to the list such that the above still holds true
 *   
 *  In other words:
 *   - the first/last element of this blocks list has either 0 or >=2 parents/children in the CFG
 *   - every other element in the list has exactly 1 parent and exactly 1 child in the CFG
 *
 * TODO implement
 *
 * Taken from: 
 * "Efficiently Computing Static Single Assignment Form 
 * and the Control Dependence Graph" 
 * RON CYTRON, JEANNE FERRANTE, BARRY K. ROSEN, and 
 * MARK N. WEGMAN 
 * IBM Research Division 
 * and 
 * F. KENNETH ZADECK 
 * Brown University
 * 
 * 
 * @see ControlFlowGraph
 * @author Andre Mis
 */
public class BasicBlock {

	private List<BytecodeInstruction> instructions = new ArrayList<BytecodeInstruction>();
	
	private String className;
	private String methodName;
	
	public BasicBlock(String className, String methodName) {
		if(className==null || methodName==null)
			throw new IllegalArgumentException("null given");
		
		this.className = className;
		this.methodName = methodName;
	}
	
	public boolean appendInstruction(BytecodeInstruction instruction) {
		if (instruction == null)
			throw new IllegalArgumentException("null given");
		if (!className.equals(instruction.getClassName()))
			throw new IllegalArgumentException(
					"expect elements of a basic block to be inside the same class");
		if (!methodName.equals(instruction.getMethodName()))
			throw new IllegalArgumentException(
					"expect elements of a basic block to be inside the same class");
		if (instructions.contains(instruction))
			throw new IllegalArgumentException(
					"a basic block can not contain the same element twice");
		
		
		return instructions.add(instruction);
	}
}
