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
package de.unisb.cs.st.evosuite.junit;

import java.util.List;
import java.util.Map;

import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCodeVisitor;

/**
 * @author fraser
 * 
 */
public class JUnit3TestAdapter implements UnitTestAdapter {

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.junit.UnitTestAdapter#getImports()
	 */
	@Override
	public String getImports() {
		return "import junit.framework.TestCase;\n";
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.junit.UnitTestAdapter#getClassDefinition(java.lang.String)
	 */
	@Override
	public String getClassDefinition(String testName) {
		return "public class " + testName + " extends TestCase";
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.junit.UnitTestAdapter#getMethodDefinition(java.lang.String)
	 */
	@Override
	public String getMethodDefinition(String testName) {
		return "public void " + testName + "() ";
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.junit.UnitTestAdapter#getSuite(java.util.List)
	 */
	@Override
	public String getSuite(List<String> suites) {
		StringBuilder builder = new StringBuilder();
		builder.append("import junit.framework.Test;\n");
		builder.append("import junit.framework.TestCase;\n");
		builder.append("import junit.framework.TestSuite;\n\n");

		for (String suite : suites) {
			builder.append("import ");
			// builder.append(Properties.PROJECT_PREFIX);
			builder.append(suite);
			builder.append(";\n");
		}
		builder.append("\n");

		builder.append(getClassDefinition("GeneratedTestSuite"));
		builder.append(" {\n");
		builder.append("  public static Test suite() {\n");
		builder.append("    TestSuite suite = new TestSuite();\n");
		for (String suite : suites) {
			builder.append("    suite.addTestSuite(");
			builder.append(suite.substring(suite.lastIndexOf(".") + 1));
			builder.append(".class);\n");
		}

		builder.append("    return suite;\n");
		builder.append("  }\n");
		builder.append("}\n");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.junit.UnitTestAdapter#getTestString(de.unisb.cs.st.evosuite.testcase.TestCase, java.util.Map)
	 */
	@Override
	public String getTestString(int id, TestCase test, Map<Integer, Throwable> exceptions) {
		return test.toCode(exceptions);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.junit.UnitTestAdapter#getTestString(int, de.unisb.cs.st.evosuite.testcase.TestCase, java.util.Map, de.unisb.cs.st.evosuite.testcase.TestCodeVisitor)
	 */
	@Override
	public String getTestString(int id, TestCase test,
	        Map<Integer, Throwable> exceptions, TestCodeVisitor visitor) {
		test.accept(visitor);
		return visitor.getCode();
	}

}
