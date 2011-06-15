/**
 * 
 */
package de.unisb.cs.st.evosuite.cfg.instrumentation;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.cfg.BytecodeInstruction;
import de.unisb.cs.st.evosuite.cfg.CFGPool;
import de.unisb.cs.st.evosuite.cfg.RawControlFlowGraph;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUse;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUseFactory;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUsePool;

/**
 * @author copied from CFGMethodAdapter
 * 
 */
public class DefUseInstrumentation implements MethodInstrumentation {

	private static Logger logger = Logger.getLogger(DefUseInstrumentation.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.cfg.MethodInstrumentation#analyze(org.objectweb
	 * .asm.tree.MethodNode, org.jgrapht.Graph, java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void analyze(MethodNode mn, String className, String methodName, int access) {
		RawControlFlowGraph completeCFG = CFGPool.getRawCFG(className, methodName);
		Iterator<AbstractInsnNode> j = mn.instructions.iterator();
		while (j.hasNext()) {

			AbstractInsnNode in = j.next();
			for (BytecodeInstruction v : completeCFG.vertexSet()) {

				// if (in.equals(v.getASMNode()))
				// v.branchId =
				// completeCFG.getInstruction(v.getId()).getBranchId();

				if ((Properties.CRITERION == Criterion.DEFUSE) && in.equals(v.getASMNode()) && (v.isDefUse())) {

					// keeping track of uses
					boolean isValidDU = false;
					if (v.isUse()) {
						isValidDU = DefUsePool.addAsUse(v);
					}
					// keeping track of definitions
					if (v.isDefinition()) {
						isValidDU = DefUsePool.addAsDefinition(v) || isValidDU;
					}

					if (isValidDU) {
						boolean staticContext = v.isStaticDefUse() || ((access & Opcodes.ACC_STATIC) > 0);
						// adding instrumentation for defuse-coverage
						mn.instructions.insert(
								v.getASMNode().getPrevious(),
								getInstrumentation(v, v.getControlDependentBranchId(), staticContext, className,
										methodName));
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.cfg.MethodInstrumentation#executeOnExcludedMethods
	 * ()
	 */
	@Override
	public boolean executeOnExcludedMethods() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.cfg.MethodInstrumentation#executeOnMainMethod()
	 */
	@Override
	public boolean executeOnMainMethod() {
		return false;
	}

	/**
	 * Creates the instrumentation needed to track defs and uses
	 * 
	 */
	private InsnList getInstrumentation(BytecodeInstruction v, int currentBranch, boolean staticContext,
			String className, String methodName) {
		InsnList instrumentation = new InsnList();

		// TODO sanity check matching method/class names and field values of v?
		if (!v.isDefUse()) {
			logger.warn("unexpected DefUseInstrumentation call for a non-DU-instruction");
			return instrumentation;
		}

		DefUse targetDU = DefUseFactory.makeInstance(v);

		if (v.isUse()) {
			instrumentation.add(new LdcInsnNode(className));
			instrumentation.add(new LdcInsnNode(targetDU.getDUVariableName()));
			instrumentation.add(new LdcInsnNode(methodName));
			if (staticContext) {
				instrumentation.add(new InsnNode(Opcodes.ACONST_NULL));
			} else {
				instrumentation.add(new VarInsnNode(Opcodes.ALOAD, 0));
			}
			instrumentation.add(new LdcInsnNode(currentBranch));
			instrumentation.add(new LdcInsnNode(DefUsePool.getUseCounter()));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
					"de/unisb/cs/st/evosuite/testcase/ExecutionTracer", "passedUse",
					"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;II)V"));
		}

		if (v.isDefinition()) {
			instrumentation.add(new LdcInsnNode(className));
			instrumentation.add(new LdcInsnNode(targetDU.getDUVariableName()));
			instrumentation.add(new LdcInsnNode(methodName));
			if (staticContext) {
				instrumentation.add(new InsnNode(Opcodes.ACONST_NULL));
			} else {
				instrumentation.add(new VarInsnNode(Opcodes.ALOAD, 0));
			}
			instrumentation.add(new LdcInsnNode(currentBranch));
			instrumentation.add(new LdcInsnNode(DefUsePool.getDefCounter()));
			instrumentation.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
					"de/unisb/cs/st/evosuite/testcase/ExecutionTracer", "passedDefinition",
					"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;II)V"));
		}

		return instrumentation;
	}

}
