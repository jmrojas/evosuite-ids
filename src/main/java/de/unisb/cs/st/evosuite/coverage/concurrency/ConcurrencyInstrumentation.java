/**
 * 
 */
package de.unisb.cs.st.evosuite.coverage.concurrency;

import java.util.Iterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import de.unisb.cs.st.evosuite.cfg.instrumentation.MethodInstrumentation;
import de.unisb.cs.st.evosuite.graphs.GraphPool;
import de.unisb.cs.st.evosuite.graphs.cfg.BytecodeInstruction;
import de.unisb.cs.st.evosuite.graphs.cfg.RawControlFlowGraph;

/**
 * @author Sebastian Steenbuck
 *
 */
public class ConcurrencyInstrumentation implements MethodInstrumentation{
	
	private String className;
	private String methodName;
	private GeneratorAdapter generatorAdapter;
	
	public String getClassName(){
		assert(className!=null) : "This should only be called after a call to analyze";
		return className;
	}
	
	public String getMethodName(){
		assert(className!=null) : "This should only be called after a call to analyze";
		return methodName;
	}
	
	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.cfg.MethodInstrumentation#analyze(org.objectweb.asm.tree.MethodNode, org.jgrapht.Graph, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void analyze(MethodNode mn, String className, String methodName, int access) {
		this.generatorAdapter=new GeneratorAdapter(mn, mn.access, mn.name, mn.desc);
		this.className=className;
		this.methodName=methodName;
		
		RawControlFlowGraph completeCFG = GraphPool.getRawCFG(className, methodName);
		Iterator<AbstractInsnNode> instructions = mn.instructions.iterator();
		while (instructions.hasNext()) {
			AbstractInsnNode instruction = instructions.next();
			for (BytecodeInstruction v : completeCFG.vertexSet()) {
//				if (instruction.equals(v.getASMNode())){
//					v.branchId = completeCFG.getInstruction(v.getId()).getBranchId();
//				}
				//#TODO steenbuck the true should be some command line option to activate the concurrency stuff
				if (true && 
						instruction.equals(v.getASMNode()) && 
						v.isFieldUse() &&
						instruction instanceof FieldInsnNode )
						//#FIXME steenbuck we should also instrument fields, which access primitive types.
						//#FIXME steenbuck apparently some objects (like Boolean, Integer) are not working with this, likely a different Signature (disappears when all getfield/getstatic points are instrumented)
						{ //we only want objects, as primitive types are passed by value
					// adding instrumentation for scheduling-coverage
					getConcurrencyInstrumentation(v, v.getControlDependentBranchId(), !((FieldInsnNode)instruction).desc.startsWith("L"));
					//mn.instructions.insert(v.getASMNode(),	);

					// keeping track of definitions
					/*if (v.isDefinition())
						DefUsePool.addDefinition(v);

					// keeping track of uses
					if (v.isUse())
						DefUsePool.addUse(v);*/

				}

			}
		}
	}
	
	private InsnList getConcurrencyInstrumentation(BytecodeInstruction v, int currentBranch, boolean isPrimitiveType) {
		InsnList instrumentation = new InsnList();
		switch (v.getASMNode().getOpcode()) {
		case Opcodes.GETFIELD:
		case Opcodes.GETSTATIC:
			//System.out.println("as seen in instrument:" + v.node.getClass() + " branchID: " + currentBranch +  " line " + v.line_no);
			int accessID = LockRuntime.getFieldAccessID();
			//#TODO all this static communication crap should be in one place
			LockRuntime.fieldAccToConcInstr.put(accessID, this);
			LockRuntime.mapFieldAccessIDtoCFGid(accessID, currentBranch, v);
			instrumentation.add(new InsnNode(Opcodes.DUP));
			//generatorAdapter.dup();
			//generatorAdapter.push(accessID);
			Type owner = Type.getType(LockRuntime.class);
			Class<?>[] params = {Object.class, int.class};
			java.lang.reflect.Method m;
			try {
				m = LockRuntime.class.getMethod(LockRuntime.RUNTIME_SCHEDULER_CALL_METHOD, params);
			} catch (Exception e) {
				throw new AssertionError("This method should exist");
			}
			Method method = Method.getMethod(m);
			
			//generatorAdapter.invokeStatic(owner, method);
			//generatorAdapter.valueOf();
			instrumentation.add(new IntInsnNode(Opcodes.BIPUSH, accessID));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC, 
					LockRuntime.RUNTIME_CLASS, 
					LockRuntime.RUNTIME_SCHEDULER_CALL_METHOD,
					"(Ljava/lang/Object;I)V"));
			break;
		default:
			//Should never be reached. As the method calling this method checks for the opcodes
			throw new AssertionError();
		}
		return instrumentation;
	}



	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.cfg.MethodInstrumentation#executeOnExcludedMethods()
	 */
	@Override
	public boolean executeOnExcludedMethods() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.cfg.MethodInstrumentation#executeOnMainMethod()
	 */
	@Override
	public boolean executeOnMainMethod() {
		return true;
	}
	
	
}
