/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.cfg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;


import de.unisb.cs.st.evosuite.cfg.CFGGenerator.CFGVertex;
import de.unisb.cs.st.evosuite.testcase.ExecutionTracer;
import de.unisb.cs.st.evosuite.testcase.TestCluster;
import de.unisb.cs.st.javalanche.mutation.bytecodeMutations.AbstractMutationAdapter;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;

/**
 * At the end of each method, create a minimized control flow graph for the method and store it.
 * In addition, this adapter also adds instrumentation for branch distance measurement
 * 
 * @author Gordon Fraser
 *
 */
public class CFGMethodAdapter extends AbstractMutationAdapter {

	MethodVisitor next;
	String plain_name;
	Label last_label = null;
	static int current_line = 0;
	List<Mutation> mutants;
	int access = 0;
	
	public static final List<String> EXCLUDE = Arrays.asList("<clinit>", "__STATIC_RESET()V", "__STATIC_RESET");
	
	public static Map<String, Integer> branch_count = new HashMap<String, Integer>();

	public static Map<String, Map<String, Map<Integer,Integer>>> branch_map = new HashMap<String, Map<String, Map<Integer,Integer>>>();

	public static Set<String> branchless_methods = new HashSet<String>();

	public static Set<String> methods = new HashSet<String>();

	public static int branch_counter = 0;

	public CFGMethodAdapter(String className, int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv, List<Mutation> mutants) {
		super(new MethodNode(access, name, desc, signature, exceptions), className, name.replace('/', '.'), null, desc);
		next = mv;
		this.className = className; //.replace('/', '.');
		this.access = access;
		this.methodName = name+desc;
		this.plain_name = name;
		this.mutants = mutants;
	}

	private static Logger logger = Logger.getLogger(CFGMethodAdapter.class);

	private String methodName, className;

	private void countBranch() {
			String id = className+"."+methodName;
			if(!branch_count.containsKey(id)) {
				branch_count.put(id, 1);
			}
			else
				branch_count.put(id, branch_count.get(id) + 1);
	}
	
	private InsnList getInstrumentation(int opcode, int id) {
		InsnList instrumentation = new InsnList();
		

		switch(opcode) {
		case Opcodes.IFEQ:
		case Opcodes.IFNE:
		case Opcodes.IFLT:
		case Opcodes.IFGE:
		case Opcodes.IFGT:
		case Opcodes.IFLE:
			instrumentation.add(new InsnNode(Opcodes.DUP));
			instrumentation.add(new LdcInsnNode(opcode));
//			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new LdcInsnNode(branch_counter));
			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
					"passedBranch", "(IIII)V"));
			countBranch();
			logger.debug("Adding passedBranch val=?, opcode="+opcode+", branch="+branch_counter+", bytecode_id="+id);

			break;
		case Opcodes.IF_ICMPEQ:
		case Opcodes.IF_ICMPNE:
		case Opcodes.IF_ICMPLT:
		case Opcodes.IF_ICMPGE:
		case Opcodes.IF_ICMPGT:
		case Opcodes.IF_ICMPLE:
			instrumentation.add(new InsnNode(Opcodes.DUP2));
			instrumentation.add(new LdcInsnNode(opcode));
//			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new LdcInsnNode(branch_counter));
			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
					"passedBranch", "(IIIII)V"));
			countBranch();


			break;
		case Opcodes.IF_ACMPEQ:
		case Opcodes.IF_ACMPNE:
			instrumentation.add(new InsnNode(Opcodes.DUP2));
			instrumentation.add(new LdcInsnNode(opcode));
			//instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new LdcInsnNode(branch_counter));
			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
					"passedBranch", "(Ljava/lang/Object;Ljava/lang/Object;III)V"));
			countBranch();
			break;
		case Opcodes.IFNULL:
		case Opcodes.IFNONNULL:
			instrumentation.add(new InsnNode(Opcodes.DUP));
			instrumentation.add(new LdcInsnNode(opcode));
//			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new LdcInsnNode(branch_counter));
			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
					"passedBranch", "(Ljava/lang/Object;III)V"));
			countBranch();
			break;
		case Opcodes.GOTO:
			break;
		case Opcodes.TABLESWITCH:
			instrumentation.add(new InsnNode(Opcodes.DUP));
			instrumentation.add(new LdcInsnNode(opcode));
//			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new LdcInsnNode(branch_counter));
			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
					"passedBranch", "(IIII)V"));
			countBranch();
			break;
		case Opcodes.LOOKUPSWITCH:
			instrumentation.add(new InsnNode(Opcodes.DUP));
			instrumentation.add(new LdcInsnNode(opcode));
//			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new LdcInsnNode(branch_counter));
			instrumentation.add(new LdcInsnNode(id));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "de/unisb/cs/st/evosuite/testcase/ExecutionTracer",
					"passedBranch", "(IIII)V"));
			countBranch();
			break;
		}	
		return instrumentation;
	}
	
	@SuppressWarnings("unchecked")
	public void visitEnd() {

		//super.visitEnd();

		// Generate CFG of method
		MethodNode mn = (MethodNode) mv;
		
		if(plain_name.equals("<clinit>")) {
			mn.accept(next);
			return;
		}

		if(EXCLUDE.contains(methodName)) {
			mn.accept(next);
			return;
		}
		
		//MethodNode mn = new CFGMethodNode((MethodNode)mv);
		//System.out.println("Generating CFG for "+ className+"."+mn.name + " ("+mn.desc +")");
		CFGGenerator g = new CFGGenerator(mutants);
		
		try {
			g.getCFG(className, methodName, mn);
			logger.trace("Method graph for "+className+"."+methodName+" contains "+g.getGraph().vertexSet().size()+" nodes for "+g.getFrames().length+" instructions");
		} catch (AnalyzerException e) {
			logger.warn("Analyzer exception while analyzing "+className+"."+methodName);
			e.printStackTrace();			
		}


		addCFG(className, methodName, g.getMinimalGraph());

		//if(!Properties.MUTATION) {
		Graph<CFGVertex, DefaultEdge> graph = g.getGraph();
		Iterator<AbstractInsnNode> j = mn.instructions.iterator(); 
		while (j.hasNext()) {
			AbstractInsnNode in = j.next();
			for(CFGVertex v : graph.vertexSet()) {
				// If this is in the CFG and it's a branch...
				if(in.equals(v.node) && v.isBranch() && !v.isMutation() && !v.isMutationBranch()) {
					mn.instructions.insert(v.node.getPrevious(), getInstrumentation(v.node.getOpcode(), v.id));
					//if(!v.isMutatedBranch()) {
						if(!branch_map.containsKey(className))
							branch_map.put(className, new HashMap<String, Map<Integer,Integer>>());
						if(!branch_map.get(className).containsKey(methodName))
							branch_map.get(className).put(methodName, new HashMap<Integer,Integer>());
						branch_map.get(className).get(methodName).put(v.id, branch_counter);

						logger.debug("Branch "+branch_counter+" at line "+v.id+" - "+current_line);
						// TODO: Associate branch_counter with v.id?
						branch_counter++;
					//}
				}
			}
		}
		//}

		String id = className+"."+methodName;
		if(!branch_count.containsKey(id)) {
			if(isUsable()) {
				logger.debug("Method has no branches: "+id);
				branchless_methods.add(id);
			}
		}
		
		if(isUsable()) {
			methods.add(id);
			logger.debug("Counting: "+id);
		}		
		mn.accept(next);
	}
	
	private boolean isUsable() {
		return !((this.access & Opcodes.ACC_SYNTHETIC) > 0 || (this.access & Opcodes.ACC_BRIDGE) > 0 ) 
				&& !methodName.contains("<clinit>")
				&& !(methodName.contains("<init>") && (access & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE);
		
	}
	
	private static Map<String, Map <String, ControlFlowGraph > > graphs = new HashMap<String, Map <String, ControlFlowGraph > >();
	private static Map<String, Map <String, Double > > diameters = new HashMap<String, Map <String, Double> > ();
		
	public static void addCFG(String classname, String methodname, DirectedMultigraph<CFGVertex, DefaultEdge> graph) {
		if(!graphs.containsKey(classname)) {
			graphs.put(classname, new HashMap<String, ControlFlowGraph >());
			diameters.put(classname, new HashMap<String, Double>());
		}
		Map<String, ControlFlowGraph > methods = graphs.get(classname);
        logger.debug("Added CFG for class "+classname+" and method "+methodname);
		methods.put(methodname, new ControlFlowGraph(graph));
		FloydWarshall<CFGVertex,DefaultEdge> f = new FloydWarshall<CFGVertex,DefaultEdge>(graph);
		diameters.get(classname).put(methodname, f.getDiameter());
        logger.debug("Calculated diameter for "+classname+": "+f.getDiameter());
	}
	
	public static ControlFlowGraph getCFG(String classname, String methodname) {
		return graphs.get(classname).get(methodname);
	}
}
