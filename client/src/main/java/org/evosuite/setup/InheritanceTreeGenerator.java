/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.evosuite.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.classpath.ResourceList;
import org.evosuite.rmi.ClientServices;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.utils.LoggingUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 * @author Gordon Fraser
 * 
 */
public class InheritanceTreeGenerator {

	private static Logger logger = LoggerFactory.getLogger(InheritanceTreeGenerator.class);

	/**
	 * Iterate over items in classpath and analyze them
	 * 
	 * @param classPath
	 * @return
	 */
	public static InheritanceTree createFromClassPath(List<String> classPath) {
		if (!Properties.INHERITANCE_FILE.isEmpty()) {
			try {
				InheritanceTree tree = readInheritanceTree(Properties.INHERITANCE_FILE);
				LoggingUtils.getEvoLogger().info("* Inheritance tree loaded from {}",
				                                 Properties.INHERITANCE_FILE);
				return tree;

			} catch (IOException e) {
				LoggingUtils.getEvoLogger().warn("* Error loading inheritance tree: {}",
				                                 e);
			}
		}
		
		logger.debug("Reading JDK data");
		InheritanceTree inheritanceTree = readJDKData();
        if(inheritanceTree==null){
            inheritanceTree = new InheritanceTree();
        }

		logger.debug("CP: "+classPath);
		for (String classPathEntry : classPath) {
			logger.debug("Looking at CP entry: "+classPathEntry);
			if (classPathEntry.isEmpty())
				continue;

			if (classPathEntry.matches(".*evosuite-.*\\.jar"))
				continue;

			logger.debug("Analyzing classpath entry " + classPathEntry);
			LoggingUtils.getEvoLogger().info("  - " + classPathEntry);
			analyze(inheritanceTree, classPathEntry);
		}
		return inheritanceTree;
	}

	/**
	 * Create inheritance tree only for the classes passed as parameter
	 * 
	 * <p>
	 * Private classes will be ignored
	 * 
	 * @param classNames
	 * @return
	 */
	public static InheritanceTree createFromClassList(Collection<String> classNames)
	        throws IllegalArgumentException {

		if (classNames == null || classNames.isEmpty()) {
			throw new IllegalArgumentException("No class name defined");
		}

		InheritanceTree inheritanceTree = new InheritanceTree();
		for (String className : classNames) {

			if (className == null) {
				throw new IllegalArgumentException("Null class name");
			}

			analyzeClassName(inheritanceTree, className);
		}

		// Remove all classes not in the parameter list, otherwise we get the superclasses from outside the list
		Set<String> classes = new HashSet<String>(inheritanceTree.getAllClasses());
		for (String className : classes) {
			if (!classNames.contains(className))
				inheritanceTree.removeClass(className);
		}
		return inheritanceTree;

	}

	public static void gatherStatistics(InheritanceTree inheritanceTree) {
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Classpath_Classes,
		                                                                 inheritanceTree.getNumClasses());
	}

	/**
	 * 
	 * @param inheritanceTree
	 * @param entry
	 */
	private static void analyze(InheritanceTree inheritanceTree, String entry) {
		analyze(inheritanceTree, new File(entry));
	}

	/**
	 * 
	 * @param inheritanceTree
	 * @param entry
	 */
	private static void analyze(InheritanceTree inheritanceTree, File file) {
		if (!file.canRead()) {
			return;
		}
		if (file.getName().endsWith(".jar")) {
			// handle jar file
			analyzeJarFile(inheritanceTree, file);
		} else if (file.getName().endsWith(".class")) {
			// handle individual class
			analyzeClassFile(inheritanceTree, file);
		} else if (file.isDirectory()) {
			// handle directory
			analyzeDirectory(inheritanceTree, file);
		} else {
			// Invalid entry?
		}
	}

	private static void analyzeJarFile(InheritanceTree inheritanceTree, File jarFile) {

		ZipFile zf;
		try {
			zf = new ZipFile(jarFile);
		} catch (Exception e) {
			logger.warn("Failed to open/analyze jar file " + jarFile.getAbsolutePath()
			        + " , " + e.getMessage());
			return;
		}

		final Enumeration<?> e = zf.entries();
		while (e.hasMoreElements()) {
			final ZipEntry ze = (ZipEntry) e.nextElement();
			final String fileName = ze.getName();
			if (!fileName.endsWith(".class"))
				continue;

			try {
				analyzeClassStream(inheritanceTree, zf.getInputStream(ze), false);
			} catch (IOException e1) {
				/*
				 * even if there is a problem with one of the entries, we can still
				 * go on and look at the others
				 */
				logger.error("Error while analyzing class " + fileName + " in the jar "
				        + jarFile.getAbsolutePath(), e1);
			}
		}
		try {
			zf.close();
		} catch (final IOException e1) {
			logger.warn("Failed to close jar file " + jarFile.getAbsolutePath() + " , "
			        + e1.getMessage());
		}
	}

	private static void analyzeDirectory(InheritanceTree inheritanceTree, File directory) {
		for (File file : directory.listFiles()) {
			analyze(inheritanceTree, file);
		}
	}

	private static void analyzeClassFile(InheritanceTree inheritanceTree, File classFile) {
		try {
			analyzeClassStream(inheritanceTree, new FileInputStream(classFile), false);
		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
	}

	private static void analyzeClassName(InheritanceTree inheritanceTree, String className) {

		InputStream stream = ResourceList.getClassAsStream(className);
		if(stream==null){
			throw new IllegalArgumentException("Failed to locate/load class: "+className);
		}
		
		logger.debug("Going to analyze: "+className);
		analyzeClassStream(inheritanceTree, stream, false);
	}

	@SuppressWarnings("unchecked")
	private static void analyzeClassStream(InheritanceTree inheritanceTree,
	        InputStream inputStream, boolean onlyPublic) {
		try {
			ClassReader reader = new ClassReader(inputStream);
			inputStream.close();

			ClassNode cn = new ClassNode();
			reader.accept(cn, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG
			        | ClassReader.SKIP_CODE);
			logger.debug("Analyzing class " + cn.name);

			if ((Opcodes.ACC_INTERFACE & cn.access) != Opcodes.ACC_INTERFACE) {
				for (Object m : cn.methods) {
					MethodNode mn = (MethodNode) m;
					inheritanceTree
							.addAnalyzedMethod(cn.name, mn.name, mn.desc);
				}
				if ((Opcodes.ACC_ABSTRACT & cn.access) == Opcodes.ACC_ABSTRACT) {
					inheritanceTree.registerAbstractClass(cn.name);
				}
			}else{
				inheritanceTree.registerInterface(cn.name);
			}
			if (onlyPublic) {
				if ((cn.access & Opcodes.ACC_PUBLIC) == 0) {
					return;
				}
			} else {
				if (!canUse(cn)) {
					return;
				}
			}

			if (cn.superName != null)
				inheritanceTree.addSuperclass(cn.name, cn.superName, cn.access);

			List<String> interfaces = cn.interfaces;
			for (String interfaceName : interfaces) {
				inheritanceTree.addInterface(cn.name, interfaceName);
			}

		} catch (IOException e) {
			logger.error("", e);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			logger.error("ASM Error while reading class ("+e.getMessage()+")");
		}
	}

	private static final Pattern ANONYMOUS_MATCHER1 = Pattern.compile(".*\\$\\d+.*$");
	private static final Pattern ANONYMOUS_MATCHER2 = Pattern.compile(".*\\.\\d+.*$");

	public static boolean canUse(ClassNode cn) {

		if ((cn.access & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE) {
			logger.debug(cn.name + " is private, ignoring it");
			return false;
		}
		if (ANONYMOUS_MATCHER1.matcher(cn.name).matches()) {
			logger.debug(cn.name + " looks like an anonymous class, ignoring it");
			return false;
		}

		if (ANONYMOUS_MATCHER2.matcher(cn.name).matches()) {
			logger.debug(cn.name + " looks like an anonymous class, ignoring it");
			return false;
		}

		//		TODO: Handle Deprecated

		if (cn.name.startsWith("junit"))
			return false;

		// If the SUT is not in the default package, then
		// we cannot import classes that are in the default
		// package
		/*
		if ((cn.access & Opcodes.ACC_PUBLIC) != Opcodes.ACC_PUBLIC) {
			String nameWithDots = cn.name.replace('/', '.');
			String packageName = ClassUtils.getPackageName(nameWithDots);
			if (!packageName.equals(Properties.CLASS_PREFIX)) {
				return false;
			}
		}
		*/

		// ASM has some problem with the access of inner classes
		// so we check if the inner class name is the current class name
		// and if so, check if the inner class is actually accessible
		@SuppressWarnings("unchecked")
		List<InnerClassNode> in = cn.innerClasses;
		for (InnerClassNode inc : in) {
			if (cn.name.equals(inc.name)) {
				// logger.debug("ASM weird behaviour: Inner class equals class: " + inc.name);
				if ((inc.access & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE) {
					return false;
				}
				/*
				if ((inc.access & Opcodes.ACC_PUBLIC) != Opcodes.ACC_PUBLIC) {
					String nameWithDots = inc.name.replace('/', '.');
					String packageName = ClassUtils.getPackageName(nameWithDots);
					if (!packageName.equals(Properties.CLASS_PREFIX)) {
						return false;
					}
				}
				*/
				logger.debug("Can use inner class: " + inc.name);
				return true;
			}
		}

		logger.debug(cn.name + " is accessible");
		return true;
	}

	private static List<String> classExceptions = Arrays.asList(new String[] {
	        "java/lang/Class", "java/lang/Object", "java/lang/String",
	        "java/lang/Comparable", "java/io/Serializable", "com/apple", "apple/",
	        "sun/", "com/sun", "com/oracle", "sun/awt",
	        "java/util/prefs/MacOSXPreferences" });

	/**
	 * During runtime, we do not want to consider standard classes to safe some
	 * time, so we perform this analysis only once.
	 */
	public static void generateJDKCluster(String... filters) {
		Collection<String> list = getAllResources();
		InheritanceTree inheritanceTree = new InheritanceTree();
		List<InheritanceTree> others = new ArrayList<InheritanceTree>();

		/*
		 * Filtering against other inheritance trees is necessary to remove any
		 * version specific classes. For example, first generate an inheritance tree
		 * with JDK6 and then one with JDK7, filtering against JDK6, to keep only
		 * the intersection of classes. 
		 */
		for (String filterFile : filters) {
			logger.info("Trying to load " + filterFile);
			try {
				InheritanceTree tree = readUncompressedInheritanceTree(filterFile);
				others.add(tree);
			} catch (IOException e) {
				logger.info("Error: " + e);
			}
		}

		EXCEPTION: for (String name : list) {
			// We do not consider sun.* and apple.* and com.* 
			for (String exception : classExceptions) {
				if (name.startsWith(exception)) {
					logger.info("Skipping excluded class " + name);
					continue EXCEPTION;
				}
			}
			for (InheritanceTree other : others) {
				if (!other.hasClass(ResourceList.getClassNameFromResourcePath(name))) {
					logger.info("Skipping " + name
					        + " because it is not in other inheritance tree");
					continue EXCEPTION;
				} else {
					logger.info("Not skipping " + name
					        + " because it is in other inheritance tree");
				}
			}
			if (name.matches(".*\\$\\d+$")) {
				logger.info("Skipping anonymous class");
				continue;
			}
			InputStream stream = TestGenerationContext.getInstance().getClassLoaderForSUT().getResourceAsStream(name);
			analyzeClassStream(inheritanceTree, stream, true);
		}

		logger.info("Finished checking classes, writing data");

		// Write data to XML file
		try {
			FileOutputStream stream = new FileOutputStream(
			        new File("src/main/resources/JDK_inheritance.xml"));
			XStream xstream = new XStream();
			xstream.toXML(inheritanceTree, stream);
		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
	}

	public static InheritanceTree readJDKData() {
		XStream xstream = new XStream();
		String fileName = "/JDK_inheritance.xml";
		InputStream inheritance = InheritanceTreeGenerator.class.getResourceAsStream(fileName);

        if (inheritance != null) {
            return (InheritanceTree) xstream.fromXML(inheritance);
        } else {
            logger.warn("Found no JDK inheritance tree in the resource path: "+fileName);
            return null;
        }
	}

	public static InheritanceTree readInheritanceTree(String fileName) throws IOException {
		XStream xstream = new XStream();
		// InputStream inheritance = new FileInputStream(new File(fileName));
		GZIPInputStream inheritance = new GZIPInputStream(new FileInputStream(new File(
		        fileName)));
		return (InheritanceTree) xstream.fromXML(inheritance);
	}

	public static InheritanceTree readUncompressedInheritanceTree(String fileName)
	        throws IOException {
		XStream xstream = new XStream();
		InputStream inheritance = new FileInputStream(new File(fileName));
		return (InheritanceTree) xstream.fromXML(inheritance);
	}

	public static void writeInheritanceTree(InheritanceTree tree, File file)
	        throws IOException {
		XStream xstream = new XStream();
		//		OutputStream output = new FileOutputStream(file);	
		GZIPOutputStream output = new GZIPOutputStream(new FileOutputStream(file));
		xstream.toXML(tree, output);
		output.close();
	}


	public static Collection<String> getAllResources() {
		Collection<String> retval = getResources(System.getProperty("java.class.path",
		                                                            "."));
		retval.addAll(getResources(System.getProperty("sun.boot.class.path")));
		return retval;
	}

	private static Collection<String> getResources(String classPath) {
		final ArrayList<String> retval = new ArrayList<String>();
		String[] classPathElements = classPath.split(File.pathSeparator);

		for (final String element : classPathElements) {
			if (element.contains("evosuite-0.1-SNAPSHOT-dependencies.jar"))
				continue;
			if (element.endsWith("jpf-annotations.jar"))
				continue;
			if (element.endsWith("jpf-classes.jar"))
				continue;
			if (element.contains("evosuite"))
				continue;
			try {
				//retval.addAll(ResourceList.getAllClassesAsResources(element, false));
				//TODO: need to fix based on new ResourceList 
				throw new RuntimeException("ERROR: this functionality is temporarely disabled");
			} catch (IllegalArgumentException e) {
				System.err.println("Does not exist: " + element);
			}
		}

		return retval;
	}

	public static void main(String[] args) {
		generateJDKCluster(args);
	}

}
