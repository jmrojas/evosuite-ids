package de.unisb.cs.st.evosuite.mutation.HOM;

import java.io.PrintWriter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.javaagent.ExecutionPathClassAdapter;
import de.unisb.cs.st.evosuite.javaagent.StaticInitializationClassAdapter;
import de.unisb.cs.st.evosuite.string.StringClassAdapter;
import de.unisb.st.bytecodetransformer.processFiles.BytecodeTransformer;

public class HOMTransformer extends BytecodeTransformer {

	String className = "";
	
	protected boolean static_hack = Properties.getPropertyOrDefault("static_hack", false);

	@Override
	protected ClassVisitor classVisitorFactory(ClassWriter cw) {
		//ClassVisitor cv = new CheckClassAdapter(cw);
//		if (MutationProperties.TRACE_BYTECODE) {
//			cv = new TraceClassVisitor(cv, new PrintWriter(System.out));
//		}
		//cv = new CFGClassAdapter(cv, "dummy");
		System.out.println("Aha!");
		ClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
		cv = new HOMClassAdapter(cv);
		
		//cv = new ExecutionPathClassAdapter(cv, className);
		//cv = new StringClassAdapter(cv, className);
			//cv = new CheckClassAdapter(cv);
		//if(static_hack)
		//	cv = new StaticInitializationClassAdapter(cv, className);
		
		
//		cv = new TraceClassVisitor(cv, new PrintWriter(System.out));
		// cv = new CFGClassAdapter(cv, className);
		return cv; 
	}

}
