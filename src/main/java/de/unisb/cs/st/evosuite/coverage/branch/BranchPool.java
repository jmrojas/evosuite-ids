package de.unisb.cs.st.evosuite.coverage.branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

import de.unisb.cs.st.evosuite.cfg.BytecodeInstruction;

// TODO: root branches should not be special cases
// every root branch should be a branch just
// like every other branch with it's own branchId and all

/**
 * This class is supposed to hold all the available information concerning
 * Branches.
 * 
 * The addBranch()-Method gets called by the BranchInstrumentation whenever it
 * detects a BytecodeInstruction that corresponds to a Branch in the class under
 * test.
 * 
 * @author Andre Mis
 */
public class BranchPool {

	private static Logger logger = LoggerFactory.getLogger(BranchPool.class);

	// maps className -> method inside that class -> list of branches inside
	// that method
	private static Map<String, Map<String, List<Branch>>> branchMap = new HashMap<String, Map<String, List<Branch>>>();

	// maps every Method to the number of Branches inside that method
	private static Map<String, Integer> methodBranchCount = new HashMap<String, Integer>();

	// set of all known methods without a Branch
	private static Set<String> branchlessMethods = new HashSet<String>();

	// maps the branchIDs assigned by this pool to their respective Branches
	private static Map<Integer, Branch> branchIdMap = new HashMap<Integer, Branch>();

	// maps all known branch instructions to their branchId
	private static Map<BytecodeInstruction, Integer> registeredBranches = new HashMap<BytecodeInstruction, Integer>();

	// maps all known switch instructions to a map, mapping it's
	// branchIDs to their corresponding targetCaseValue (which is null for the default case) 
	private static Map<BytecodeInstruction, Map<Integer, Integer>> registeredSwitches = new HashMap<BytecodeInstruction, Map<Integer, Integer>>();
	
	private static Map<BytecodeInstruction, Branch> registeredDefaultCases = new HashMap<BytecodeInstruction, Branch>();
	
	private static Map<LabelNode, Branch> switchLabels = new HashMap<LabelNode, Branch>();

	// number of known Branches
	private static int branchCounter = 0;

	// fill the pool

	/**
	 * Get called by the BranchInstrumentation whenever it detects a CFGVertex
	 * that corresponds to a Branch in the class under test.
	 * 
	 * @param v
	 *            CFGVertex of a Branch
	 */
	public static void registerAsBranch(BytecodeInstruction v) {
		if (!(v.isActualBranch()))
			throw new IllegalArgumentException("CFGVertex of a branch expected");
		if (isKnownAsBranch(v))
			return;
		// throw new
		// IllegalArgumentException("branches can only be added to the pool once");

		registerInstruction(v);

	}

	/**
	 * Gets called by the CFGMethodAdapter whenever it detects a method without
	 * any branches.
	 * 
	 * @param methodName
	 *            Unique methodName of a method without Branches
	 */
	public static void addBranchlessMethod(String methodName) {
		branchlessMethods.add(methodName);
	}

	private static void registerInstruction(BytecodeInstruction v) {
		if (isKnownAsBranch(v))
			throw new IllegalStateException(
					"expect registerInstruction() to be called at most once for each instruction");

		if (v.isBranch())
			registerNormalBranchInstruction(v);
		else if (v.isSwitch())
			registerSwitchInstruction(v);
		else
			throw new IllegalArgumentException(
					"expect given instruction to be an actual branch");
	}

	private static void registerNormalBranchInstruction(BytecodeInstruction v) {
		if (!v.isBranch())
			throw new IllegalArgumentException(
					"normal branch instruction expceted");

		branchCounter++;
		registeredBranches.put(v, branchCounter);

		Branch b = new Branch(v, branchCounter);
		addBranchToMap(b);
		branchIdMap.put(branchCounter, b);

		logger
				.info("Branch " + branchCounter + " at line "
						+ b.getLineNumber());

	}

	private static void registerSwitchInstruction(BytecodeInstruction v) {
		if (!v.isSwitch())
			throw new IllegalArgumentException("expect a switch instruction");

		switch (v.getASMNode().getOpcode()) {
		case Opcodes.TABLESWITCH:
			TableSwitchInsnNode tableSwitchNode = (TableSwitchInsnNode) v.getASMNode();
			int num = 0;
			for (int i = tableSwitchNode.min; i <= tableSwitchNode.max; i++) {
				LabelNode targetLabel = (LabelNode) tableSwitchNode.labels.get(num);
				createSwitchBranch(v, i, targetLabel);
				num++;
			}
			// default-case:
			createSwitchBranch(v, null, tableSwitchNode.dflt);
			break;
		case Opcodes.LOOKUPSWITCH:
			LookupSwitchInsnNode lookupSwitchNode = (LookupSwitchInsnNode) v.getASMNode();
			for (int i = 0; i < lookupSwitchNode.keys.size(); i++) {
				LabelNode targetLabel = (LabelNode) lookupSwitchNode.labels.get(i);
				createSwitchBranch(v, (Integer) lookupSwitchNode.keys.get(i), targetLabel);
			}
			// default-case:
			createSwitchBranch(v, null, lookupSwitchNode.dflt);
			break;
		default:
			throw new IllegalStateException(
					"expect ASMNode of a switch to either be a LOOKUP- or TABLESWITCH");
		}
	}

	private static void createSwitchBranch(BytecodeInstruction v, Integer caseValue,
			LabelNode targetLabel) {
		
		branchCounter++;
		
		registerSwitchBranch(v, caseValue);

		Branch b = new Branch(v, caseValue, branchCounter);
		addBranchToMap(b);
		branchIdMap.put(branchCounter, b);

		registerSwitchLabel(b, targetLabel);
		
		// default case
		if(caseValue == null) {
			if(registeredDefaultCases.containsKey(v))
				throw new IllegalStateException("instruction already registered as a branch");
			registeredDefaultCases.put(v, b);
		}
			
	}

	private static void registerSwitchLabel(Branch b, LabelNode targetLabel) {

		// if(switchLabels.get(targetLabel) != null)
		// throw new
		// IllegalArgumentException("label already associated with a branch "+b.toString());

		// TODO several Branches can map to one Label, so switchLabels should
		// either map from branches to labels, not the other way around. or it
		// should map labels to a list of branches
		// this stems from the fact that empty case: blocks do not have their
		// own label
		
		// TODO STOPPED HERE

		switchLabels.put(targetLabel, b);
	}

	private static void registerSwitchBranch(BytecodeInstruction v,
			Integer targetCaseValue) {
		if (!v.isSwitch())
			throw new IllegalArgumentException("switch instruction expected");

		if (registeredSwitches.get(v) == null)
			registeredSwitches.put(v, new HashMap<Integer, Integer>());

		Map<Integer, Integer> oldMap = registeredSwitches.get(v);

		if (oldMap.get(branchCounter) != null)
			throw new IllegalArgumentException(
					"switch already registered for id "
							+ branchCounter);

		
		oldMap.put(branchCounter, targetCaseValue);

		registeredSwitches.put(v, oldMap);
	}
	
	private static void addBranchToMap(Branch b) {
		String className = b.getClassName();
		String methodName = b.getMethodName();

		if (!branchMap.containsKey(className))
			branchMap.put(className, new HashMap<String, List<Branch>>());
		if (!branchMap.get(className).containsKey(methodName))
			branchMap.get(className).put(methodName, new ArrayList<Branch>());
		branchMap.get(className).get(methodName).add(b);
	}

	// retrieve information from the pool

	/**
	 * Checks whether the given instruction is already known to be a Branch
	 * 
	 * Returns true if the given BytecodeInstruction previously passed a call to
	 * addBranch(instruction), false otherwise
	 */
	public static boolean isKnownAsBranch(BytecodeInstruction v) {
		return registeredBranches.containsKey(v); // TODO either check if v is
													// contained in
													// registeredSwitches or
													// make new methods
													// isKnownAsSwitch and
													// isKnownAsAnyBranch or
													// something along that way
	}

	public static int getActualBranchIdForInstruction(BytecodeInstruction ins) {
		if (registeredBranches.containsKey(ins))
			return registeredBranches.get(ins);

		return -1;
	}

	public static Map<Integer, Integer> getBranchIdMapForSwitch(
			BytecodeInstruction ins) {
		if (ins == null)
			throw new IllegalArgumentException("null given");
		if (!ins.isSwitch())
			throw new IllegalArgumentException("switch instruction expected");
		if(!registeredSwitches.containsKey(ins))
			throw new IllegalArgumentException("not registered as a switch instruction");

		return registeredSwitches.get(ins);
	}

	public static Branch getBranchForInstruction(BytecodeInstruction ins) {
		if (ins == null)
			throw new IllegalArgumentException("null given");

		return getBranch(registeredBranches.get(ins));
	}

	public static Branch getBranchForLabel(LabelNode label) {

		return switchLabels.get(label);
	}

	// TODO can't this just always be called private by addBranch?
	// why is this called in CFGMethodAdapter.getInstrumentation() anyways?
	// .. well it only get's called if Properties.CRITERION is set to LCSAJ
	// ... might want to change that
	public static void countBranch(String id) {
		if (!methodBranchCount.containsKey(id)) {
			methodBranchCount.put(id, 1);
		} else
			methodBranchCount.put(id, methodBranchCount.get(id) + 1);
	}

	/**
	 * Returns the number of known Branches for a given methodName.
	 * 
	 * @param methodName
	 *            Unique methodName (consisting of "className.methodName")
	 * @return The number of currently known Branches inside the given method
	 */
	public static int getBranchCountForMethod(String methodName) {
		Integer count = methodBranchCount.get(methodName);
		if (count == null)
			return 0;

		return count;
	}

	/**
	 * Returns the number of currently known Branches
	 * 
	 * @return The number of currently known Branches
	 */
	public static int getBranchCounter() {
		return branchCounter;
	}

	/**
	 * Returns the bytecodeID for the branch associated with the given branchID
	 * 
	 * @param branchId
	 *            The ID of a Branch
	 * @return If there is a Branch with the given branchID this method returns
	 *         its bytecodeID, -1 otherwise
	 */
	public static int getBytecodeIdFor(int branchId) {
		Branch branch = branchIdMap.get(branchId);
		if (branch == null)
			return -1;

		return branch.getInstructionId();
	}

	/**
	 * Returns the Branch object associated with the given branchID
	 * 
	 * @param branchId
	 *            The ID of a branch
	 * @return The branch, or null if it does not exist
	 */
	public static Branch getBranch(int branchId) {

		return branchIdMap.get(branchId);
	}

	/**
	 * Returns a set with all unique methodNames of methods without Branches.
	 * 
	 * @return A set with all unique methodNames of methods without Branches.
	 */
	public static Set<String> getBranchlessMethods() {
		return branchlessMethods;
	}

	/**
	 * Returns a Set containing all classes for which this pool knows Branches
	 * for as Strings
	 */
	public static Set<String> knownClasses() {
		Set<String> r = new HashSet<String>();
		r.addAll(branchMap.keySet());
		return r;
	}

	/**
	 * Returns a Set containing all methods in the class represented by the
	 * given String for which this pool knows Branches for as Strings
	 * 
	 */
	public static Set<String> knownMethods(String className) {
		Set<String> r = new HashSet<String>();
		Map<String, List<Branch>> methods = branchMap.get(className);
		if (methods != null)
			r.addAll(methods.keySet());

		return r;
	}

	/**
	 * Returns the branch contained in the given method of the given class
	 * 
	 * Should no such Branch exist null is returned
	 */
	public static Branch getBranchByBytecodeId(String className,
			String methodName, int instructionId) {
		List<Branch> branches = retrieveBranchesInMethod(className, methodName);
		for (Branch b : branches)
			if (b.getInstructionId() == instructionId)
				return b;

		return null;
	}

	/**
	 * Returns a List containing all Branches in the given class and method
	 * 
	 * Should no such Branch exist an empty List is returned
	 */
	public static List<Branch> retrieveBranchesInMethod(String className,
			String methodName) {
		List<Branch> r = new ArrayList<Branch>();
		if (branchMap.get(className) == null)
			return r;
		List<Branch> branches = branchMap.get(className).get(methodName);
		if (branches != null)
			r.addAll(branches);
		return r;
	}

	public static Branch getDefaultBranchForSwitch(BytecodeInstruction v) {
		if(!v.isSwitch())
			throw new IllegalArgumentException("switch instruction expected");
		if(!registeredDefaultCases.containsKey(v))
			throw new IllegalArgumentException("there is no registered default case for this instruction");
		
		return registeredDefaultCases.get(v);
	}
}
