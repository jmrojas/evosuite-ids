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


package de.unisb.cs.st.evosuite.junit;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import de.unisb.cs.st.ds.util.io.Io;
import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.testcase.Statement;
import de.unisb.cs.st.evosuite.testcase.TestCase;

/**
 * Abstract test suite class.
 * 
 * @author Gordon Fraser
 *
 */
public class TestSuite implements Opcodes {
	
	private Logger logger = Logger.getLogger(TestSuite.class);
	
	protected List<TestCase> test_cases = new ArrayList<TestCase>();
	
	class TestFilter implements FilenameFilter
	{
	  public boolean accept( File f, String s )
	  {
	    return s.toLowerCase().endsWith( ".java" ) && s.startsWith("Test");
	  }
	}

	public TestSuite() {
		
	}
	
	public TestSuite(List<TestCase> tests) {
		for(TestCase test : tests)
			insertTest(test);
	}
	
	/**
	 * Check if there are test cases
	 * @return
	 *   True if there are no test cases
	 */
	public boolean isEmpty() {
		return test_cases.isEmpty();
	}
	
	/**
	 * Check if test suite has a test case that is a prefix of test.
	 * 
	 * @param test
	 * @return
	 */
	public boolean hasPrefix(TestCase test) {
		for(TestCase t : test_cases) {
			if(t.isPrefix(test))
				return true;
		}
		return false;
	}
	
	/**
	 * Add test to suite.
	 * If the test is a prefix of an existing test, just keep existing test.
	 * If an existing test is a prefix of the test, replace the existing test.
	 * 
	 * @param test
	 * @return
	 *    Index of the test case
	 */
	public int insertTest(TestCase test) {
		for(int i = 0; i<test_cases.size(); i++) {
			if(test.isPrefix(test_cases.get(i))) {
				// It's shorter than an existing one
				//test_cases.set(i, test);
				logger.debug("This is a prefix of an existing test");
				test_cases.get(i).addAssertions(test);
				return i;
			} else {
				// Already have that one...
				if(test_cases.get(i).isPrefix(test)) {
					test.addAssertions(test_cases.get(i));
					test_cases.set(i, test);
					logger.debug("We have a prefix of this one");
					return i;
				}
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("Adding new test case:");
			logger.debug(test.toCode());
		}
		test_cases.add(test);
		return test_cases.size() - 1;
	}
	
	/**
	 * Get all test cases
	 * 
	 * @return
	 */
	public List<TestCase> getTestCases() {
		return test_cases;
	}
	
	/**
	 * When writing out the JUnit test file, each test can have a text comment
	 * @param num
	 *    Index of test case
	 * @return
	 *    Comment for test case
	 */
	protected String getInformation(int num) {
		return "Test case number: "+num;
	}
	
	/**
	 * JUnit file header
	 * 
	 * @param name
	 * @return
	 */
	protected String getHeader(String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("/*\n");
		builder.append(" * This file was automatically generated by EvoSuite\n");
		builder.append(" *\n");
		builder.append(" * Do not edit this directly as it might be overwritten\n");
		builder.append(" *\n");
		builder.append(" */\n\n");
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
		builder.append("package ");
		builder.append(Properties.PROJECT_PREFIX);

		//String target_class = System.getProperty("test.classes").replace("_\\d+.task", "").replace('_', '.');
//		String package_string = Properties.PROJECT_PREFIX.replace('_','.').replaceFirst("TestSuite.", "").replaceFirst("\\.[^\\.]+$", "");
//		builder.append(package_string);
		//builder.append(MutationProperties.PROJECT_PREFIX);
		//builder.append(".GeneratedTests;");
		builder.append(";\n\n");
		builder.append("import junit.framework.Test;\n");
		builder.append("import junit.framework.TestCase;\n");
        builder.append("import junit.framework.TestSuite;\n\n");
        builder.append(getImports());
		builder.append("public class ");
		builder.append(name);
		builder.append(" extends TestCase {\n");
		return builder.toString();		
	}
	
	/**
	 * Determine packages that need to be imported in the JUnit file
	 * @return
	 */
	protected String getImports() {
		StringBuilder builder = new StringBuilder();
		Set<Class<?>> imports = new HashSet<Class<?>>();
		for(TestCase test : test_cases) {
			imports.addAll(test.getAccessedClasses());
		}
		List<String> imports_sorted = new ArrayList<String>();
		for(Class<?> imp : imports) {
			if(imp.isPrimitive())
				continue;
			if(imp.getName().startsWith("java.lang"))
				continue;
			imports_sorted.add(imp.getName());
		}
		Collections.sort(imports_sorted);
		for(String imp : imports_sorted) {
			builder.append("import ");
			builder.append(imp);
			builder.append(";\n");
		}
		builder.append("\n");
		return builder.toString();
	}
	
	/**
	 * JUnit file footer
	 * @return
	 */
	protected String getFooter() {
		StringBuilder builder = new StringBuilder();
		builder.append("}\n");
		return builder.toString();
	}
	
	/**
	 * Create JUnit file for given class name
	 * @param name
	 *   Name of the class file
	 * @return
	 *   String representation of JUnit test file
	 */
	protected String getUnitTest(String name) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getHeader(name));
		for(int i = 0; i<test_cases.size(); i++) {
			builder.append(testToString(i));
		}
		builder.append(getFooter());
		
		return builder.toString();
	}
	
	/**
	 * Convert one test case to a Java method
	 * @param id
	 *   Index of the test case
	 * @return
	 *   String representation of test case
	 */
	protected String testToString(int id) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("   //");
		builder.append(getInformation(id));
		builder.append("\n   public void test");
		builder.append(id);
		builder.append("() {\n");
		for(String line : test_cases.get(id).toCode().split("\\r?\\n")) {
			builder.append("      ");
			builder.append(line);
//			builder.append(";\n");
			builder.append("\n");
		}
		builder.append("   }\n");
		return builder.toString();
	}
	
	/**
	 * Create subdirectory for package in test directory
	 * @param directory
	 * @return
	 */
	protected String makeDirectory(String directory) {
		String dirname = directory+"/"+Properties.PROJECT_PREFIX.replace('.', '/'); //+"/GeneratedTests";
		File dir = new File(dirname);
		logger.debug("Target directory: "+dirname);
		dir.mkdirs();
		return dirname;
	}
	
	/**
	 * Update/create the main file of the test suite.
	 * The main test file simply includes all automatically generated test suites in the same directory
	 * 
	 * @param directory
	 *    Directory of generated test files
	 */
	protected void writeTestSuiteMainFile(String directory) {
		File file = new File(directory+"/GeneratedTestSuite.java");
		//if(file.exists())
		//	return;
		
		StringBuilder builder = new StringBuilder();
		builder.append("package ");
		builder.append(Properties.PROJECT_PREFIX);
		//builder.append(".GeneratedTests;");
		builder.append(";\n\n");
		builder.append("import junit.framework.Test;\n");
		builder.append("import junit.framework.TestCase;\n");
        builder.append("import junit.framework.TestSuite;\n\n");
        builder.append("import java.io.File;\n");
        builder.append("import java.io.FilenameFilter;\n\n");
/*
        builder.append("class TestFilter implements FilenameFilter\n");
        builder.append("{\n");
        builder.append("  public boolean accept( File f, String s )\n");
        builder.append("  {\n");
        builder.append("    return s.toLowerCase().endsWith( \".java\" ) && s.toLowerCase().startsWith(\"Test\");\n");
        builder.append("  }\n");
        builder.append("}\n\n");
        */
        builder.append("public class GeneratedTestSuite extends TestCase {\n");
		builder.append("  public static Test suite() {\n");
		builder.append("    TestSuite suite = new TestSuite();\n");
		File basedir = new File(directory);
		for(File f : basedir.listFiles(new TestFilter())) {
			builder.append("    suite.addTestSuite(");
			builder.append(f.getName().replace(".java", ""));
			builder.append(".class);\n");
	    }

		/*
		builder.append("    File basedir = new File(\"");
		builder.append(directory);
		builder.append("\");\n");
		builder.append("    for(File f : basedir.listFiles(new TestFilter())) {\n");
		builder.append("    	try {\n");
		builder.append("    		Class clazz = Class.forName(f.getAbsolutePath());\n");
		builder.append("    		suite.addTestSuite(clazz);\n");
		builder.append("    	} catch (ClassNotFoundException e) {}\n");
		builder.append("    }\n");
		*/
		builder.append("    return suite;\n");
		builder.append("  }\n");
		builder.append("}\n");
		Io.writeFile(builder.toString(), file);		
	}
	
	/**
	 * Create JUnit test suite for class
	 * @param name
	 *   Name of the class
	 * @param directory
	 *   Output directory
	 */
	public void writeTestSuite(String name, String directory) {
		String dir = makeDirectory(directory);
		writeTestSuiteMainFile(dir);
		File file = new File(dir+"/"+name+".java");
		Io.writeFile(getUnitTest(name), file);
	}
	
	
	private void testToBytecode(TestCase test, GeneratorAdapter mg) {
		
		for(Statement statement : test.getStatements()) {
			statement.getBytecode(mg);
		}
		
		mg.endMethod();

	}
	
	public void getBytecode(String name) {
		ClassWriter cw = new ClassWriter(0);
		
		cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, "ncs/TestTriangle2", null, "java/lang/Object", null);

		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(Object.class), m);
		mg.returnValue();
		mg.endMethod();

		int num = 0;
		for(TestCase test : test_cases) {
			m = Method.getMethod("void test"+num+" ()");
			mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
			testToBytecode(test, mg);
			num++;
		}

		// TODO: Return bytecode or write to file
	}
	
	public void writeTestSuiteClass(String name, String directory) {
		getBytecode(name);
	}
}
