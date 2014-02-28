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
package org.evosuite.instrumentation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.graphs.cfg.CFGClassAdapter;
import org.evosuite.seeding.PrimitiveClassAdapter;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcarver.instrument.Instrumenter;
import org.evosuite.testcarver.instrument.TransformerUtil;
import org.evosuite.utils.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The bytecode transformer - transforms bytecode depending on package and
 * whether it is the class under test
 * 
 * @author Gordon Fraser
 */
public class BytecodeInstrumentation {

	private static Logger logger = LoggerFactory.getLogger(BytecodeInstrumentation.class);

	private static List<ClassAdapterFactory> externalPreVisitors = new ArrayList<ClassAdapterFactory>();

	private static List<ClassAdapterFactory> externalPostVisitors = new ArrayList<ClassAdapterFactory>();

	private final Instrumenter testCarvingInstrumenter;

	/**
	 * <p>
	 * Constructor for BytecodeInstrumentation.
	 * </p>
	 */
	public BytecodeInstrumentation() {
		this.testCarvingInstrumenter = new Instrumenter();
	}

	/**
	 * <p>
	 * addClassAdapter
	 * </p>
	 * 
	 * @param factory
	 *            a {@link org.evosuite.instrumentation.ClassAdapterFactory} object.
	 */
	public static void addClassAdapter(ClassAdapterFactory factory) {
		externalPostVisitors.add(factory);
	}

	/**
	 * <p>
	 * addPreClassAdapter
	 * </p>
	 * 
	 * @param factory
	 *            a {@link org.evosuite.instrumentation.ClassAdapterFactory} object.
	 */
	public static void addPreClassAdapter(ClassAdapterFactory factory) {
		externalPreVisitors.add(factory);
	}

	
	/**
	 * <p>
	 * getPackagesShouldNotBeInstrumented
	 * </p>
	 * 
	 * @return the names of class packages EvoSuite is not going to instrument
	 */
	public static String[] getPackagesShouldNotBeInstrumented() {
		//explicitly blocking client projects such as specmate is only a
		//temporary solution, TODO allow the user to specify 
		//packages that should not be instrumented
		return new String[] { "java.", "javax.", "sun.", "org.evosuite", "org.exsyst",
					          "de.unisb.cs.st.testcarver", "de.unisb.cs.st.evosuite",  "org.uispec4j", 
					          "de.unisb.cs.st.specmate", "org.xml", "org.w3c",
					          "testing.generation.evosuite", "com.yourkit", "com.vladium.emma.", "daikon.",
					          // Need to have these in here to avoid trouble with UnsatisfiedLinkErrors on Mac OS X and Java/Swing apps
					          "apple.", "com.apple.", "com.sun", "org.junit", "junit.framework",
					          "org.apache.xerces.dom3", "de.unisl.cs.st.bugex", "edu.uta.cse.dsc", "org.mozilla.javascript.gen.c",
					          "corina.cross.Single",  // I really don't know what is wrong with this class, but we need to exclude it
					          "org.slf4j", 
					          "org.apache.commons.logging.Log"// Leads to ExceptionInInitializerException when re-instrumenting classes that use a logger
		};
	}

	/**
	 * Check if we can instrument the given class
	 * 
	 * @param className
	 *            a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean checkIfCanInstrument(String className) {
		for (String s : BytecodeInstrumentation.getPackagesShouldNotBeInstrumented()) {
			if (className.startsWith(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * isTargetProject
	 * </p>
	 * 
	 * @param className
	 *            a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isTargetProject(String className) {
		return (className.startsWith(Properties.PROJECT_PREFIX) || (!Properties.TARGET_CLASS_PREFIX.isEmpty() && className.startsWith(Properties.TARGET_CLASS_PREFIX)))
		        && !className.startsWith("java.")
		        && !className.startsWith("sun.")
		        && !className.startsWith("org.evosuite")
		        && !className.startsWith("org.exsyst")
		        && !className.startsWith("de.unisb.cs.st.evosuite")
		        && !className.startsWith("de.unisb.cs.st.specmate")
		        && !className.startsWith("javax.")
		        && !className.startsWith("org.xml")
		        && !className.startsWith("org.w3c")
		        && !className.startsWith("apple.")
		        && !className.startsWith("com.apple.")
		        && !className.startsWith("daikon.");
	}

	/**
	 * <p>
	 * shouldTransform
	 * </p>
	 * 
	 * @param className
	 *            a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean shouldTransform(String className) {
		if (!Properties.TT)
			return false;
		switch (Properties.TT_SCOPE) {
		case ALL:
			logger.info("Allowing transformation of " + className);
			return true;
		case TARGET:
			if (className.equals(Properties.TARGET_CLASS)
			        || className.startsWith(Properties.TARGET_CLASS + "$"))
				return true;
			break;
		case PREFIX:
			if (className.startsWith(Properties.PROJECT_PREFIX))
				return true;

		}
		logger.info("Preventing transformation of " + className);
		return false;
	}

	private boolean isTargetClassName(String className) {
		// TODO: Need to replace this in the long term
		return TestCluster.isTargetClassName(className);
	}

	/**
	 * <p>
	 * transformBytes
	 * </p>
	 * 
	 * @param className
	 *            a {@link java.lang.String} object.
	 * @param reader
	 *            a {@link org.objectweb.asm.ClassReader} object.
	 * @return an array of byte.
	 */
	public byte[] transformBytes(ClassLoader classLoader, String className,
	        ClassReader reader) {
		int readFlags = ClassReader.SKIP_FRAMES;

		if (Properties.INSTRUMENTATION_SKIP_DEBUG)
			readFlags |= ClassReader.SKIP_DEBUG;

		String classNameWithDots = Utils.getClassNameFromResourcePath(className);

		if (!checkIfCanInstrument(classNameWithDots)) {
			throw new RuntimeException("Should not transform a shared class ("
			        + classNameWithDots + ")! Load by parent (JVM) classloader.");
		}

		TransformationStatistics.reset();

		/*
		 *  To use COMPUTE_FRAMES we need to remove JSR commands.
		 *  Therefore, we have a JSRInlinerAdapter in NonTargetClassAdapter
		 *  as well as CFGAdapter.
		 */
		int asmFlags = ClassWriter.COMPUTE_FRAMES;
		ClassWriter writer = new ClassWriter(asmFlags);

		ClassVisitor cv = writer;
		if (logger.isDebugEnabled()) {
			cv = new TraceClassVisitor(cv, new PrintWriter(System.err));
		}

		if (Properties.RESET_STATIC_FIELDS) {
			cv = new PutStaticClassAdapter(cv, className);
		}

		// Apply transformations to class under test and its owned
		// classes
		if (DependencyAnalysis.shouldAnalyze(classNameWithDots)) {
			logger.debug("Applying target transformation to class " + classNameWithDots);
			// Print out bytecode if debug is enabled
			if (!Properties.TEST_CARVING && Properties.MAKE_ACCESSIBLE) {
				cv = new AccessibleClassAdapter(cv, className);
			}

			for (ClassAdapterFactory factory : externalPostVisitors) {
				cv = factory.getVisitor(cv, className);
			}

			cv = new ExecutionPathClassAdapter(cv, className);

			cv = new CFGClassAdapter(classLoader, cv, className);

			if (Properties.ERROR_BRANCHES) {
				cv = new ErrorConditionClassAdapter(cv, className);
			}
			//cv = new BoundaryValueClassAdapter(cv, className);

			for (ClassAdapterFactory factory : externalPreVisitors) {
				cv = factory.getVisitor(cv, className);
			}

		} else {
			logger.debug("Not applying target transformation");
			cv = new NonTargetClassAdapter(cv, className);

			if (Properties.MAKE_ACCESSIBLE) {
				// Convert protected/default access to public access
				cv = new AccessibleClassAdapter(cv, className);
			}
			// If we are doing testability transformation on all classes we need to create the CFG first
			if (Properties.TT && classNameWithDots.startsWith(Properties.CLASS_PREFIX)) {
				cv = new CFGClassAdapter(classLoader, cv, className);
			}
		}

		// Collect constant values for the value pool
		cv = new PrimitiveClassAdapter(cv, className);

		// If we need to reset static constructors, make them
		// explicit methods
		if (Properties.RESET_STATIC_FIELDS) {
			cv = new StaticResetClassAdapter(cv, className);
		}

		// Replace calls to System.exit, Random.*, and System.currentTimeMillis
		// and/or replace calls to FileInputStream.available and FIS.skip
		if (Properties.REPLACE_CALLS || Properties.VIRTUAL_FS) {
			cv = new MethodCallReplacementClassAdapter(cv, className);
		}

		// Testability Transformations
		if (classNameWithDots.startsWith(Properties.PROJECT_PREFIX)
		        || (!Properties.TARGET_CLASS_PREFIX.isEmpty() && classNameWithDots.startsWith(Properties.TARGET_CLASS_PREFIX))
		        || shouldTransform(classNameWithDots)) {
			ClassNode cn = new AnnotatedClassNode();
			reader.accept(cn, readFlags);
			logger.info("Starting transformation of " + className);

			if (Properties.STRING_REPLACEMENT) {
				StringTransformation st = new StringTransformation(cn);
				if (isTargetClassName(classNameWithDots)
				        || shouldTransform(classNameWithDots))
					cn = st.transform();

			}
			ComparisonTransformation cmp = new ComparisonTransformation(cn);
			if (isTargetClassName(classNameWithDots)
			        || shouldTransform(classNameWithDots)) {
				cn = cmp.transform();
				ContainerTransformation ct = new ContainerTransformation(cn);
				//if (isTargetClassName(classNameWithDots))
				cn = ct.transform();
			}

			if (shouldTransform(classNameWithDots)) {
				logger.info("Testability Transforming " + className);

				//TestabilityTransformation tt = new TestabilityTransformation(cn);
				BooleanTestabilityTransformation tt = new BooleanTestabilityTransformation(
				        cn, classLoader);
				// cv = new TraceClassVisitor(writer, new
				// PrintWriter(System.out));
				//cv = new TraceClassVisitor(cv, new PrintWriter(System.out));
				//cv = new CheckClassAdapter(cv);
				try {
					//tt.transform().accept(cv);
					//if (isTargetClassName(classNameWithDots))
					cn = tt.transform();
				} catch (Throwable t) {
					throw new Error(t);
				}

				logger.info("Testability Transformation done: " + className);
			}

			//----- 

			cn.accept(cv);

			if (Properties.TEST_CARVING) {
				if (TransformerUtil.isClassConsideredForInstrumentation(className)) {
					final ClassReader cr = new ClassReader(writer.toByteArray());
					final ClassNode cn2 = new ClassNode();
					cr.accept(cn2, ClassReader.EXPAND_FRAMES);

					this.testCarvingInstrumenter.transformClassNode(cn2, className);
					final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
					cn2.accept(cw);

					if (logger.isDebugEnabled()) {
						final StringWriter sw = new StringWriter();
						cn2.accept(new TraceClassVisitor(new PrintWriter(sw)));
						logger.debug("test carving instrumentation result:\n{}", sw);
					}

					return cw.toByteArray();
				}
			}
		} else {
			reader.accept(cv, readFlags);
		}

		// Print out bytecode if debug is enabled
		// if(logger.isDebugEnabled())
		// cv = new TraceClassVisitor(cv, new PrintWriter(System.out));
		return writer.toByteArray();
	}


}
