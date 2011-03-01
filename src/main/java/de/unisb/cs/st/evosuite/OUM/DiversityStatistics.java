/**
 * 
 */
package de.unisb.cs.st.evosuite.OUM;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import de.unisb.cs.st.evosuite.Properties;

/**
 * @author fraser
 *
 */
public class DiversityStatistics extends ClassAdapter {

	private String targetClass = Properties.getProperty("target");
	
	private String className;
	/**
	 * @param arg0
	 */
	public DiversityStatistics(ClassVisitor cv, String className) {
		super(cv);
		this.className = className;
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions); 
		if(className.equals(targetClass)) {
			 mv = new DiversityMethodAdapter(mv);
		}
		return mv; 
	}
}
