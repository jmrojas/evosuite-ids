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
package org.evosuite.junit;

import java.util.List;
import java.util.Map;

import org.evosuite.Properties;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCodeVisitor;

/**
 * <p>
 * JUnit4TestAdapter class.
 * </p>
 * 
 * @author fraser
 */
public class JUnit4TestAdapter implements UnitTestAdapter {

	/* (non-Javadoc)
	 * @see org.evosuite.junit.UnitTestAdapter#getImports()
	 */
	/** {@inheritDoc} */
	@Override
	public String getImports() {
		String imports = "import org.junit.Test;\n"
		        + "import static org.junit.Assert.*;\n";
		
		if(Properties.JUNIT_RUNNER)
			imports += "import org.junit.runner.RunWith;\nimport org.evosuite.junit.EvoSuiteRunner;\n";
		return imports;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.junit.UnitTestAdapter#getClassDefinition(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public String getClassDefinition(String testName) {
		if(Properties.JUNIT_RUNNER)
			return "@RunWith(EvoSuiteRunner.class)\npublic class " + testName;
		else
			return "public class " + testName;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.junit.UnitTestAdapter#getMethodDefinition(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public String getMethodDefinition(String testName) {
		return "  @Test\n  public void " + testName + "() ";
	}

	/* (non-Javadoc)
	 * @see org.evosuite.junit.UnitTestAdapter#getSuite(java.util.List)
	 */
	/** {@inheritDoc} */
	@Override
	public String getSuite(List<String> suites) {
		StringBuilder builder = new StringBuilder();
		builder.append("import org.junit.runner.RunWith;\n");
		builder.append("import org.junit.runners.Suite;\n\n");

		for (String suite : suites) {
			if (suite.contains(".")) {
				builder.append("import ");
				builder.append(suite);
				builder.append(";\n");
			}
		}
		builder.append("\n");

		builder.append("@RunWith(Suite.class)\n");
		builder.append("@Suite.SuiteClasses({\n");
		boolean first = true;
		for (String suite : suites) {
			if (!first) {
				builder.append(",\n");
			}
			first = false;
			builder.append("  ");
			builder.append(suite.substring(suite.lastIndexOf(".") + 1));
			builder.append(".class");
		}
		builder.append("})\n");

		builder.append(getClassDefinition("GeneratedTestSuite"));
		builder.append(" {\n");
		builder.append("}\n");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see org.evosuite.junit.UnitTestAdapter#getTestString(org.evosuite.testcase.TestCase, java.util.Map)
	 */
	/** {@inheritDoc} */
	@Override
	public String getTestString(int id, TestCase test, Map<Integer, Throwable> exceptions) {
		return test.toCode(exceptions);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.junit.UnitTestAdapter#getTestString(int, org.evosuite.testcase.TestCase, java.util.Map, org.evosuite.testcase.TestCodeVisitor)
	 */
	/** {@inheritDoc} */
	@Override
	public String getTestString(int id, TestCase test,
	        Map<Integer, Throwable> exceptions, TestCodeVisitor visitor) {
		visitor.setExceptions(exceptions);
		test.accept(visitor);
		visitor.clearExceptions();
		return visitor.getCode();
	}
}
