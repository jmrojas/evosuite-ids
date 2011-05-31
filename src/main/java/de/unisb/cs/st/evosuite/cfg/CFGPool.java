package de.unisb.cs.st.evosuite.cfg;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Gives access to all CFGs created by the CFGGenerator during byteCode analysis
 * in the CFGMethodAdapter
 * 
 * For each CUT and each of their methods a Raw- and an ActualControlFlowGraph instance
 * are stored within this pool
 * 
 * @author Andre Mis
 */
public class CFGPool {

	private static Logger logger = Logger.getLogger(CFGPool.class);
	
	
	/**
	 * Complete control flow graph, contains each bytecode instruction, each
	 * label and line number node Think of the direct Known Subclasses of
	 * http://
	 * asm.ow2.org/asm33/javadoc/user/org/objectweb/asm/tree/AbstractInsnNode
	 * .html for a complete list of the nodes in this cfg
	 */
	private static Map<String, Map<String, RawControlFlowGraph>> rawCFGs = new HashMap<String, Map<String, RawControlFlowGraph>>();

	/**
	 * Minimized control flow graph. This graph only contains the first and last
	 * node (usually a LABEL and IRETURN), nodes which create branches (all
	 * jumps/switches except GOTO) and nodes which were mutated.
	 */
	private static Map<String, Map<String, ActualControlFlowGraph>> actualCFGs = new HashMap<String, Map<String, ActualControlFlowGraph>>();
	
//	private static Map<String, Map<String, DominatorTree>> dominatorTrees = new HashMap<String, Map<String,DominatorTree>>();

	//TODO do these get used anywhere?
//	private static Map<String, Map<String, Double>> diameters = new HashMap<String, Map<String, Double>>();	
	
	
	
	public static void registerActualCFG(ActualControlFlowGraph cfg) {
		String className = cfg.getClassName();
		String methodName = cfg.getMethodName();
		
		if (!actualCFGs.containsKey(className)) {
			actualCFGs.put(className, new HashMap<String, ActualControlFlowGraph>());
//			diameters.put(className, new HashMap<String, Double>());
		}
		Map<String, ActualControlFlowGraph> methods = actualCFGs.get(className);
		logger.debug("Added CFG for class " + className + " and method " + methodName);
		cfg.finalize();
		methods.put(methodName, cfg);

		cfg.toDot();
		
		createControlDependence(cfg);
		
//		diameters.get(className).put(methodName, cfg.getDiameter());
//		logger.debug("Calculated diameter for " + className + ": " + cfg.getDiameter());
	}

	private static void createControlDependence(ActualControlFlowGraph cfg) {

		ControlDependenceGraph cd = new ControlDependenceGraph(cfg);
		// TODO stopped here
	}

	public static void registerRawCFG(RawControlFlowGraph cfg) {
		String className = cfg.getClassName();
		String methodName = cfg.getMethodName();
		
		if (!rawCFGs.containsKey(className)) {
			rawCFGs.put(className, new HashMap<String, RawControlFlowGraph>());
		}
		Map<String, RawControlFlowGraph> methods = rawCFGs.get(className);
		logger.debug("Added complete CFG for class " + className + " and method "
		        + methodName);
		methods.put(methodName, cfg);
		
		cfg.toDot();
		
		//ControlFlowGraph cfg = new ControlFlowGraph(graph, false);
		//cfg.toDot(classname + "_" + methodname + ".dot");
	}

	public static ActualControlFlowGraph getActualCFG(String className, String methodName) {
		logger.debug("Getting minimzed CFG for " + className + "." + methodName);
		if(actualCFGs.get(className) == null)
			return null;
		
		return actualCFGs.get(className).get(methodName);
	}

	public static RawControlFlowGraph getRawCFG(String className, String methodName) {
		logger.debug("Getting complete CFG for " + className + "." + methodName);
		if (rawCFGs.get(className) == null)
			return null;
		
		return rawCFGs.get(className).get(methodName);
	}
}
