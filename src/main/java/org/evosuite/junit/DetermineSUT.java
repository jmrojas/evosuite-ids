/**
 * 
 */
package org.evosuite.junit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.evosuite.instrumentation.BytecodeInstrumentation;
import org.evosuite.utils.ClassPathHacker;
import org.evosuite.utils.ResourceList;
import org.evosuite.utils.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For a given JUnit test class, determine which class it seems to be testing.
 * 
 * @author Gordon Fraser
 * 
 */
public class DetermineSUT {

	private final static Logger logger = LoggerFactory.getLogger(DetermineSUT.class);

	private String targetName = "";

	private String targetClassPath = "";
	
	private Set<String> superClasses = new HashSet<String>();

	public static class NoJUnitClassException extends Exception {
		
	}
	
	private class TargetClassSorter implements Comparator<String> {
		private final String targetClass;

		public TargetClassSorter(String target) {
			this.targetClass = target;
		}

		@Override
		public int compare(String arg0, String arg1) {
			double distance1 = StringUtils.getLevenshteinDistance(targetClass, arg0);
			double distance2 = StringUtils.getLevenshteinDistance(targetClass, arg1);
			return Double.compare(distance1, distance2);
		}
	}

	public String getSUTName(String fullyQualifiedTargetClass, String targetClassPath) throws NoJUnitClassException {
		this.targetName = fullyQualifiedTargetClass;
		this.targetClassPath = targetClassPath;
		try {
			ClassPathHacker.addFile(targetClassPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Set<String> targetClasses = analyzeTargetClasspath(targetClassPath);
		Set<String> candidateClasses = new HashSet<String>();
		boolean hasJUnit = false;
		try {
			candidateClasses.addAll(determineCalledClasses(fullyQualifiedTargetClass,
			                                               targetClasses));
			hasJUnit = true;
		} catch (ClassNotFoundException e) {
			// Ignore, the set will be empty?
			logger.error("Class not found: " + e, e);
			return "";
		} catch(NoJUnitClassException e) {
			
		}

		if(!hasJUnit)
			throw new NoJUnitClassException();
		
		if (candidateClasses.isEmpty())
			return "<UNKNOWN>";

		List<String> sortedNames = new ArrayList<String>(candidateClasses);
		Collections.sort(sortedNames, new TargetClassSorter(fullyQualifiedTargetClass));

		//System.out.println("Sorted candidate classes: " + sortedNames);
		return sortedNames.get(0);
	}

	public Set<String> determineCalledClasses(String fullyQualifiedTargetClass,
	        Set<String> targetClasses) throws ClassNotFoundException, NoJUnitClassException {
		Set<String> calledClasses = new HashSet<String>();

		String className = fullyQualifiedTargetClass.replace('.', '/');
		try {

			InputStream is = ClassLoader.getSystemResourceAsStream(className + ".class");
			if (is == null) {
				throw new ClassNotFoundException("Class '" + className + ".class"
						+ "' should be in target project, but could not be found!");
			}
			ClassReader reader = new ClassReader(is);
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.SKIP_FRAMES);
			superClasses = getSuperClasses(classNode);

			if (isJUnitTest(classNode)) {
				handleClassNode(calledClasses, classNode, targetClasses);
			} else {
				throw new NoJUnitClassException(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		calledClasses.remove("java.lang.Object");
		calledClasses.remove(fullyQualifiedTargetClass);

		return calledClasses;
	}

	private Set<String> analyzeTargetClasspath(String classPath) {
		Pattern pattern = Pattern.compile(".*\\.class");
		Collection<String> classes = ResourceList.getResources(classPath, pattern);
		Set<String> classNames = new HashSet<String>();
		for (String fileName : classes) {
			classNames.add(fileName.replace(".class", "").replaceAll(File.separator, "."));
		}
		return classNames;
	}

	@SuppressWarnings("unchecked")
	private void handleClassNode(Set<String> calledClasses, ClassNode cn,
	        Set<String> targetClasses) throws IOException {
		List<MethodNode> methods = cn.methods;
		for (MethodNode mn : methods) {
			handleMethodNode(calledClasses, cn, mn, targetClasses);
		}
	}

	private boolean isValidClass(String name) throws IOException {
		if (BytecodeInstrumentation.isJavaClass(name))
			return false;

		if (name.startsWith("junit"))
			return false;

		if (name.startsWith("org.junit"))
			return false;

		if (name.startsWith(targetName))
			return false;

		if (superClasses.contains(name))
			return false;

		ClassNode sutNode = loadClassNode(name);
		if (isJUnitTest(sutNode)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private void handleMethodNode(Set<String> calledClasses, ClassNode cn, MethodNode mn,
	        Set<String> targetClasses) throws IOException {
		InsnList instructions = mn.instructions;
		Iterator<AbstractInsnNode> iterator = instructions.iterator();

		while (iterator.hasNext()) {
			AbstractInsnNode insn = iterator.next();
			if (insn instanceof MethodInsnNode) {
				String name = Utils.getClassNameFromResourcePath(((MethodInsnNode) insn).owner);
				if (!targetClasses.contains(name))
					continue;

				if (isValidClass(name))
					calledClasses.add(name);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private boolean isJUnitTest(ClassNode cn) throws IOException {
		// We do not consider abstract classes
		if ((cn.access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT)
			return false;

		if (hasJUnitSuperclass(cn))
			return true;

		List<MethodNode> methods = cn.methods;
		for (MethodNode mn : methods) {
			List<AnnotationNode> annotations = mn.visibleAnnotations;
			if (annotations != null) {
				for (AnnotationNode an : annotations) {
					if (an.desc.equals("Lorg/junit/Test;"))
						return true;
				}
			}
		}

		return false;
	}

	private Set<String> getSuperClasses(ClassNode cn) throws IOException {
		Set<String> superClasses = new HashSet<String>();
		String currentSuper = cn.superName;
		while (!currentSuper.equals("java/lang/Object")) {
			superClasses.add(Utils.getClassNameFromResourcePath(currentSuper));
			ClassNode superNode = loadClassNode(currentSuper);
			currentSuper = superNode.superName;
		}
		return superClasses;
	}

	private boolean hasJUnitSuperclass(ClassNode cn) throws IOException {
		if (cn.superName.equals("java/lang/Object"))
			return false;

		if (cn.superName.equals("junit/framework/TestCase"))
			return true;

		ClassNode superClass = loadClassNode(cn.superName);
		return hasJUnitSuperclass(superClass);
	}

	private ClassNode loadClassNode(String className) throws IOException {
		ClassReader reader = new ClassReader(className);

		ClassNode cn = new ClassNode();
		reader.accept(cn, ClassReader.SKIP_FRAMES); // | ClassReader.SKIP_DEBUG);	
		return cn;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Expected parameters: <TestCase> <Target Classpath>");
			return;
		}
		DetermineSUT det = new DetermineSUT();
		try {
			System.out.println(det.getSUTName(args[0], args[1]));
		} catch(NoJUnitClassException e) {
			System.err.println("Found no JUnit test case");
		}
	}

}