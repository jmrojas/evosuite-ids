package de.unisb.cs.st.evosuite.graphs.ccg;

import java.util.List;
import java.util.Map;

import de.unisb.cs.st.evosuite.graphs.GraphPool;
import de.unisb.cs.st.evosuite.graphs.EvoSuiteGraph;
import de.unisb.cs.st.evosuite.graphs.cfg.BytecodeInstruction;
import de.unisb.cs.st.evosuite.graphs.cfg.RawControlFlowGraph;

/**
 * Represents the method call structure of a class in a graph.
 * 
 * The graph contains a node for each of the classes methods with edges
 * going from a method node to each of its called methods. 
 * 
 * Edges are labeled with the BytecodeInstruction of the corresponding call.
 * 
 * @author Andre Mis
 */
public class ClassCallGraph extends EvoSuiteGraph<ClassCallNode, ClassCallEdge> {

	private String className;
	
	public ClassCallGraph(String className) {
		super(ClassCallEdge.class);
		
		this.className = className;
		
		compute();
	}
	
	private void compute() {
		Map<String, RawControlFlowGraph> cfgs = GraphPool.getRawCFGs(className);

		if(cfgs == null)
			throw new IllegalStateException("did not find CFGs for a class I was supposed to compute the CCG of");
		
		// add nodes
		for(String method : cfgs.keySet())
			addVertex(new ClassCallNode(method));
		
//		System.out.println("generating class call graph for "+className);
		
		// add vertices
		for(ClassCallNode methodNode : graph.vertexSet()) {
			RawControlFlowGraph rcfg = cfgs.get(methodNode.getMethod());
			List<BytecodeInstruction> calls = rcfg.determineMethodCallsToOwnClass();
//			System.out.println(calls.size()+" method calls from "+methodNode);
			for(BytecodeInstruction call : calls) {
//				System.out.println("  to "+call.getCalledMethod()+" in "+call.getCalledMethodsClass());
				ClassCallNode calledMethod = getNodeByMethodName(call.getCalledMethod());
				if(calledMethod != null) {
					ClassCallEdge e = new ClassCallEdge(call);
					addEdge(methodNode, calledMethod,e);
				}
			}
		}
	}
	
	public ClassCallNode getNodeByMethodName(String methodName) {
		ClassCallNode r = null;
//		System.out.println("getting node by methodName "+methodName);
		for(ClassCallNode node : graph.vertexSet()) {
			if(node.getMethod().equals(methodName)) {
				if(r == null) {
					r = node;
				} else {
					throw new IllegalStateException("Expect each ClassCallNode to have a unique method name");
				}
			}
		}
		// TODO logger.warn
//		if(r==null)
//			System.out.println("didn't find node by methodName "+methodName);
		return r;
	}
	
	public String getClassName() {
		return className;
	}

	// toDot util
	
	@Override
	public String getName() {
		return "CCG_" + className;
	}

	@Override
	protected String dotSubFolder() {
		return toFileString(className) + "/";
	}
}
