/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package de.unisb.cs.st.evosuite.junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.Properties.OutputFormat;
import de.unisb.cs.st.evosuite.Properties.OutputGranularity;
import de.unisb.cs.st.evosuite.coverage.dataflow.DefUseCoverageTestFitness;
import de.unisb.cs.st.evosuite.repair.JUnit4AssertionLogAdapter;
import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;
import de.unisb.cs.st.evosuite.testcase.TestCodeVisitor;
import de.unisb.cs.st.evosuite.testcase.TestFitnessFunction;
import de.unisb.cs.st.evosuite.utils.Utils;

/**
 * @author Gordon Fraser
 * 
 */
public class TestSuiteWriter implements Opcodes {

	protected final static Logger logger = LoggerFactory.getLogger(TestSuiteWriter.class);

	protected TestCaseExecutor executor = TestCaseExecutor.getInstance();

	protected List<TestCase> testCases = new ArrayList<TestCase>();

	protected Map<Integer, String> testComment = new HashMap<Integer, String>();

	private final UnitTestAdapter adapter = TestSuiteWriter.getAdapter();

	private final TestCodeVisitor visitor = new TestCodeVisitor();

	class TestFilter implements IOFileFilter {
		@Override
		public boolean accept(File f, String s) {
			return s.toLowerCase().endsWith(".java") && s.startsWith("Test");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.io.filefilter.IOFileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".java")
			        && file.getName().startsWith("Test");
		}
	}

	/**
	 * Check if there are test cases
	 * 
	 * @return True if there are no test cases
	 */
	public boolean isEmpty() {
		return testCases.isEmpty();
	}

	public int size() {
		return testCases.size();
	}

	/**
	 * Check if test suite has a test case that is a prefix of test.
	 * 
	 * @param test
	 * @return
	 */
	public boolean hasPrefix(TestCase test) {
		for (TestCase t : testCases) {
			if (t.isPrefix(test))
				return true;
		}
		return false;
	}

	/**
	 * Add test to suite. If the test is a prefix of an existing test, just keep
	 * existing test. If an existing test is a prefix of the test, replace the
	 * existing test.
	 * 
	 * @param test
	 * @return Index of the test case
	 */
	public int insertTest(TestCase test) {
		if (Properties.CALL_PROBABILITY <= 0) {
			for (int i = 0; i < testCases.size(); i++) {
				if (test.isPrefix(testCases.get(i))) {
					// It's shorter than an existing one
					// test_cases.set(i, test);
					logger.info("This is a prefix of an existing test");
					testCases.get(i).addAssertions(test);
					return i;
				} else {
					// Already have that one...
					if (testCases.get(i).isPrefix(test)) {
						test.addAssertions(testCases.get(i));
						testCases.set(i, test);
						logger.info("We have a prefix of this one");
						return i;
					}
				}
			}
		}
		logger.info("Adding new test case:");
		if (logger.isDebugEnabled()) {
			logger.debug(test.toCode());
		}
		testCases.add(test);
		return testCases.size() - 1;
	}

	public int insertTest(TestCase test, String comment) {
		int id = insertTest(test);
		if (testComment.containsKey(id)) {
			if (!testComment.get(id).contains(comment))
				testComment.put(id, testComment.get(id) + "\n   //" + comment);
		} else
			testComment.put(id, comment);
		return id;
	}

	public void insertTests(List<TestCase> tests) {
		for (TestCase test : tests)
			insertTest(test);
	}

	/**
	 * Get all test cases
	 * 
	 * @return
	 */
	public List<TestCase> getTestCases() {
		return testCases;
	}

	public ExecutionResult runTest(TestCase test) {

		ExecutionResult result = new ExecutionResult(test, null);

		try {
			logger.debug("Executing test");
			result = executor.execute(test);
		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			e.printStackTrace();
			logger.error("TG: Exception caught: ", e);
			System.exit(1);
		}

		return result;
	}

	/**
	 * Create subdirectory for package in test directory
	 * 
	 * @param directory
	 * @return
	 */
	protected String makeDirectory(String directory) {
		String dirname = directory + "/" + Properties.CLASS_PREFIX.replace('.', '/'); // +"/GeneratedTests";
		File dir = new File(dirname);
		logger.debug("Target directory: " + dirname);
		dir.mkdirs();
		return dirname;
	}

	/**
	 * Create subdirectory for package in test directory
	 * 
	 * @param directory
	 * @return
	 */
	protected String mainDirectory(String directory) {
		String dirname = directory + File.separator
		        + Properties.PROJECT_PREFIX.replace('.', File.separatorChar); // +"/GeneratedTests";
		File dir = new File(dirname);
		logger.debug("Target directory: " + dirname);
		dir.mkdirs();
		return dirname;
	}

	/**
	 * Determine packages that need to be imported in the JUnit file
	 * 
	 * @return
	 */
	protected String getImports(List<ExecutionResult> results) {
		StringBuilder builder = new StringBuilder();
		Set<Class<?>> imports = new HashSet<Class<?>>();

		for (ExecutionResult result : results) {
			result.test.accept(visitor);

			// Iterate over declared exceptions to make sure they are known to the visitor
			Set<Class<?>> exceptions = result.test.getDeclaredExceptions();
			if (!exceptions.isEmpty()) {
				for (Class<?> exception : exceptions) {
					visitor.getClassName(exception);
				}
			}
			imports.addAll(visitor.getImports());
			// imports.addAll(result.test.getAccessedClasses());
			//for (Throwable t : result.getAllThrownExceptions()) {
			//	imports.add(t.getClass());
			//}
		}
		Set<String> import_names = new HashSet<String>();
		for (Class<?> imp : imports) {
			while (imp.isArray())
				imp = imp.getComponentType();
			if (imp.isPrimitive())
				continue;
			if (imp.getName().startsWith("java.lang")) {
				String name = imp.getName().replace("java.lang.", "");
				if (!name.contains("."))
					continue;
			}
			if (!imp.getName().contains("."))
				continue;
			// TODO: Check for anonymous type?
			if (imp.getName().contains("$"))
				//	import_names.add(imp.getName().substring(0, imp.getName().indexOf("$")));
				import_names.add(imp.getName().replace("$", "."));
			else
				import_names.add(imp.getName());
		}
		List<String> imports_sorted = new ArrayList<String>(import_names);
		Collections.sort(imports_sorted);
		for (String imp : imports_sorted) {
			builder.append("import ");
			builder.append(imp);
			builder.append(";\n");
		}
		builder.append("\n");
		return builder.toString();
	}

	/**
	 * When writing out the JUnit test file, each test can have a text comment
	 * 
	 * @param num
	 *            Index of test case
	 * @return Comment for test case
	 */
	protected String getInformation(int num) {

		if (testComment.containsKey(num))
			return testComment.get(num);

		TestCase test = testCases.get(num);
		Set<TestFitnessFunction> coveredGoals = test.getCoveredGoals();

		StringBuilder builder = new StringBuilder();
		builder.append("Test case number: " + num);

		if (!coveredGoals.isEmpty()) {
			builder.append("\n  /*\n");
			builder.append("   * ");
			builder.append(coveredGoals.size() + " covered goal");
			if (coveredGoals.size() != 1)
				builder.append("s");
			builder.append(":");
			int nr = 1;
			for (TestFitnessFunction goal : coveredGoals) {
				builder.append("\n   * " + nr + " " + goal.toString());
				// TODO only for debugging purposes
				if (Properties.CRITERION == Criterion.DEFUSE
				        && (goal instanceof DefUseCoverageTestFitness)) {
					DefUseCoverageTestFitness duGoal = (DefUseCoverageTestFitness) goal;
					if (duGoal.getCoveringTrace() != null) {
						String traceInformation = duGoal.getCoveringTrace().toDefUseTraceInformation(duGoal.getGoalVariable(),
						                                                                             duGoal.getCoveringObjectId());
						traceInformation = traceInformation.replaceAll("\n", "");
						builder.append("\n     * DUTrace: " + traceInformation);
					}
				}
				nr++;
			}

			builder.append("\n   */");
		}

		return builder.toString();
	}

	private static UnitTestAdapter getAdapter() {
		if (Properties.TEST_FORMAT == OutputFormat.JUNIT3)
			return new JUnit3TestAdapter();
		else if (Properties.TEST_FORMAT == OutputFormat.JUNIT4)
			return new JUnit4TestAdapter();
		else if (Properties.TEST_FORMAT == OutputFormat.JUNIT4_LOG)
			return new JUnit4AssertionLogAdapter();
		else
			throw new RuntimeException("Unknown output format: " + Properties.TEST_FORMAT);
	}

	/**
	 * JUnit file header
	 * 
	 * @param name
	 * @return
	 */
	protected String getHeader(String name, List<ExecutionResult> results) {
		StringBuilder builder = new StringBuilder();
		builder.append("/*\n");
		builder.append(" * This file was automatically generated by EvoSuite\n");
		builder.append(" */\n\n");

		if (!Properties.CLASS_PREFIX.equals("")) {
			builder.append("package ");
			builder.append(Properties.CLASS_PREFIX);
			builder.append(";\n");
		}
		builder.append("\n");

		builder.append(adapter.getImports());
		builder.append(getImports(results));

		builder.append(adapter.getClassDefinition(name));
		builder.append(" {\n");
		return builder.toString();
	}

	/**
	 * JUnit file footer
	 * 
	 * @return
	 */
	protected String getFooter() {
		return "}\n";
	}

	/**
	 * Create JUnit file for given class name
	 * 
	 * @param name
	 *            Name of the class file
	 * @return String representation of JUnit test file
	 */
	public String getUnitTest(String name) {
		List<ExecutionResult> results = new ArrayList<ExecutionResult>();
		for (int i = 0; i < testCases.size(); i++) {
			results.add(runTest(testCases.get(i)));
		}

		StringBuilder builder = new StringBuilder();

		builder.append(getHeader(name, results));
		for (int i = 0; i < testCases.size(); i++) {
			builder.append(testToString(i, results.get(i)));
		}
		builder.append(getFooter());

		return builder.toString();
	}

	/**
	 * Create JUnit file for given class name
	 * 
	 * @param name
	 *            Name of the class file
	 * @return String representation of JUnit test file
	 */
	public String getUnitTest(String name, int testId) {
		List<ExecutionResult> results = new ArrayList<ExecutionResult>();
		results.add(runTest(testCases.get(testId)));

		StringBuilder builder = new StringBuilder();

		builder.append(getHeader(name + "_" + testId, results));
		builder.append(testToString(testId, results.get(0)));
		builder.append(getFooter());

		return builder.toString();
	}

	/**
	 * Convert one test case to a Java method
	 * 
	 * @param id
	 *            Index of the test case
	 * @return String representation of test case
	 */
	protected String testToString(int id, ExecutionResult result) {

		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("   //");
		builder.append(getInformation(id));
		builder.append("\n");
		builder.append(adapter.getMethodDefinition("test" + id));
		Set<Class<?>> exceptions = testCases.get(id).getDeclaredExceptions();
		if (!exceptions.isEmpty()) {
			builder.append("throws ");
			boolean first = true;
			for (Class<?> exception : exceptions) {
				if (first)
					first = false;
				else
					builder.append(", ");
				builder.append(visitor.getClassName(exception));
			}
		}
		builder.append(" {\n");
		for (String line : adapter.getTestString(id, testCases.get(id),
		                                         result.exposeExceptionMapping(), visitor).split("\\r?\\n")) {
			builder.append("      ");
			builder.append(line);
			// builder.append(";\n");
			builder.append("\n");
		}
		builder.append("   }\n");

		return builder.toString();
	}

	/**
	 * Update/create the main file of the test suite. The main test file simply
	 * includes all automatically generated test suites in the same directory
	 * 
	 * @param directory
	 *            Directory of generated test files
	 */
	public void writeTestSuiteMainFile(String directory) {

		File file = new File(mainDirectory(directory) + "/GeneratedTestSuite.java");

		StringBuilder builder = new StringBuilder();
		if (!Properties.PROJECT_PREFIX.equals("")) {
			builder.append("package ");
			builder.append(Properties.PROJECT_PREFIX);
			// builder.append(".GeneratedTests;");
			builder.append(";\n\n");
		}
		List<String> suites = new ArrayList<String>();

		File basedir = new File(directory);
		Iterator<File> i = FileUtils.iterateFiles(basedir, new TestFilter(),
		                                          TrueFileFilter.INSTANCE);
		while (i.hasNext()) {
			File f = i.next();
			String name = f.getPath().replace(directory, "").replace(".java", "").replace("/",
			                                                                              ".");

			if (name.startsWith("."))
				name = name.substring(1);
			suites.add(name);
		}
		builder.append(adapter.getSuite(suites));
		Utils.writeFile(builder.toString(), file);
	}

	/**
	 * Create JUnit test suite for class
	 * 
	 * @param name
	 *            Name of the class
	 * @param directory
	 *            Output directory
	 */
	public void writeTestSuite(String name, String directory) {
		String dir = makeDirectory(directory);

		if (Properties.OUTPUT_GRANULARITY == OutputGranularity.MERGED) {
			File file = new File(dir + "/" + name + ".java");
			executor.newObservers();
			Utils.writeFile(getUnitTest(name), file);
		} else {
			for (int i = 0; i < testCases.size(); i++) {
				File file = new File(dir + "/" + name + "_" + i + ".java");
				executor.newObservers();
				Utils.writeFile(getUnitTest(name, i), file);
			}
		}
	}

	private void testToBytecode(TestCase test, GeneratorAdapter mg,
	        Map<Integer, Throwable> exceptions) {
		Map<Integer, Integer> locals = new HashMap<Integer, Integer>();
		mg.visitAnnotation("Lorg/junit/Test;", true);
		int num = 0;
		for (StatementInterface statement : test) {
			logger.debug("Current statement: " + statement.getCode());
			statement.getBytecode(mg, locals, exceptions.get(num));
			num++;
		}
		mg.visitInsn(Opcodes.RETURN);
		mg.endMethod();

	}

	/**
	 * Get bytecode representation of test class
	 * 
	 * @param name
	 * @return
	 */
	public byte[] getBytecode(String name) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		String prefix = Properties.TARGET_CLASS.substring(0,
		                                                  Properties.TARGET_CLASS.lastIndexOf(".")).replace(".",
		                                                                                                    "/");
		cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, prefix + "/" + name, null,
		         "junit/framework/TestCase", null);

		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(junit.framework.TestCase.class), m);
		mg.returnValue();
		mg.endMethod();

		int num = 0;
		for (TestCase test : testCases) {
			ExecutionResult result = runTest(test);
			m = Method.getMethod("void test" + num + " ()");
			mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
			testToBytecode(test, mg, result.exposeExceptionMapping());
			num++;
		}

		// main method
		m = Method.getMethod("void main (String[])");
		mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		mg.push(1);
		mg.newArray(Type.getType(String.class));
		mg.dup();
		mg.push(0);
		mg.push(Properties.CLASS_PREFIX + "." + name);
		mg.arrayStore(Type.getType(String.class));
		// mg.invokeStatic(Type.getType(org.junit.runner.JUnitCore.class),
		// Method.getMethod("void main (String[])"));
		mg.invokeStatic(Type.getType(junit.textui.TestRunner.class),
		                Method.getMethod("void main (String[])"));
		mg.returnValue();
		mg.endMethod();

		cw.visitEnd();
		return cw.toByteArray();
	}

	/**
	 * Create JUnit test suite in bytecode
	 * 
	 * @param name
	 * @param directory
	 */
	public void writeTestSuiteClass(String name, String directory) {
		String dir = makeDirectory(directory);
		File file = new File(dir + "/" + name + ".class");
		byte[] bytecode = getBytecode(name);
		try {
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(bytecode);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		/*
		 * ClassReader reader = new ClassReader(bytecode); ClassVisitor cv = new
		 * TraceClassVisitor(new PrintWriter(System.out)); cv = new
		 * CheckClassAdapter(cv); reader.accept(cv, ClassReader.SKIP_FRAMES);
		 */

		// getBytecode(name);
	}
}
