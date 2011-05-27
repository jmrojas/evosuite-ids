package de.unisb.cs.st.evosuite.cfg;

import java.util.Set;

import org.apache.log4j.Logger;

public class ControlDependenceGraph extends EvoSuiteGraph<BasicBlock,ControlFlowEdge> {

	private static Logger logger = Logger.getLogger(ControlDependenceGraph.class); 
	
	private ActualControlFlowGraph cfg;
	
	private String className;
	private String methodName;
	
	public ControlDependenceGraph(ActualControlFlowGraph cfg) {
		super(ControlFlowEdge.class);
		
		this.cfg = cfg;
		this.className = cfg.getClassName();
		this.methodName = cfg.getMethodName();
		
		
		computeGraph();
		
		
		toDot();
	}

	private void computeGraph() {
		
		createGraphNodes();
		
		computeControlDependence();
		
	}

	private void createGraphNodes() {
		// copy cfg nodes
		addVertices(cfg);
	}

	private void computeControlDependence() {

		ActualControlFlowGraph rcfg = cfg.computeReverseCFG();
		DominatorTree<BasicBlock> dt = new DominatorTree<BasicBlock>(rcfg);
		
		for (BasicBlock b : rcfg.vertexSet())
			if (!b.isExitBlock()) {

				logger.debug("DFs for: "+b.getName());
				for(BasicBlock cd : dt.getDominatingFrontiers(b)) {
					ControlFlowEdge orig = cfg.getEdge(cd, b);

					if(!addEdge(cd,b, new ControlFlowEdge(orig)))
						throw new IllegalStateException("internal error while adding CD edge");
					
					logger.debug("  "+cd.getName());
				}
			}

		Set<BasicBlock> exits = determineExitPoints();
		ExitBlock exitNode = getExitBlockNode();
		
		if(exitNode == null)
			throw new IllegalStateException("expect ExitBlock to exist in CDG");
		
		for(BasicBlock exit : exits)
			if(!exit.isAuxiliaryBlock && null == addEdge(exit,exitNode))
				throw new IllegalStateException("internal error while adding exit edges to CDG");
	}
	
	private ExitBlock getExitBlockNode() {
		
		for(BasicBlock b : vertexSet())
			if(b.isExitBlock())
				return (ExitBlock)b;
		
		return null;
	}

	@Override
	public String getName() {
		return "CDG"+graphId+"_"+methodName;
	}
	
	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
}
