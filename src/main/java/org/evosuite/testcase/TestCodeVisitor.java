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
package org.evosuite.testcase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.evosuite.Properties;
import org.evosuite.assertion.Assertion;
import org.evosuite.assertion.CompareAssertion;
import org.evosuite.assertion.EqualsAssertion;
import org.evosuite.assertion.Inspector;
import org.evosuite.assertion.InspectorAssertion;
import org.evosuite.assertion.NullAssertion;
import org.evosuite.assertion.PrimitiveAssertion;
import org.evosuite.assertion.PrimitiveFieldAssertion;
import org.evosuite.parameterize.InputVariable;
import org.evosuite.runtime.EvoSuiteFile;
import org.evosuite.utils.NumberFormatter;


/**
 * @author fraser
 * 
 */
public class TestCodeVisitor implements TestVisitor {

	protected String testCode = "";

	protected final Map<Integer, Throwable> exceptions = new HashMap<Integer, Throwable>();

	protected TestCase test = null;

	protected final Map<VariableReference, String> variableNames = new HashMap<VariableReference, String>();

	protected final Map<Class<?>, String> classNames = new HashMap<Class<?>, String>();

	protected final Map<String, Integer> nextIndices = new HashMap<String, Integer>();

	public String getCode() {
		return testCode;
	}

	/**
	 * Retrieve a list of classes that need to be imported to make this unit
	 * test compile
	 * 
	 * @return
	 */
	public Set<Class<?>> getImports() {
		Set<Class<?>> imports = new HashSet<Class<?>>();
		for (Class<?> clazz : classNames.keySet()) {
			String name = classNames.get(clazz);
			if (!name.contains("."))
				imports.add(clazz);
		}
		return imports;
	}

	public void setExceptions(Map<Integer, Throwable> exceptions) {
		this.exceptions.putAll(exceptions);
	}

	public void setException(StatementInterface statement, Throwable exception) {
		exceptions.put(statement.getPosition(), exception);
	}

	protected Throwable getException(StatementInterface statement) {
		if (exceptions != null && exceptions.containsKey(statement.getPosition()))
			return exceptions.get(statement.getPosition());

		return null;
	}

	public String getClassName(VariableReference var) {
		Class<?> clazz = var.getVariableClass();

		if (classNames.containsKey(clazz))
			return classNames.get(clazz);

		String name = var.getSimpleClassName();
		if (classNames.values().contains(name)) {
			name = clazz.getCanonicalName();
		}

		// We can't use "Test" because of JUnit 
		if (name.equals("Test")) {
			name = clazz.getCanonicalName();
		}

		classNames.put(clazz, name);

		return name;
	}

	public String getClassName(Class<?> clazz) {
		if (classNames.containsKey(clazz))
			return classNames.get(clazz);

		GenericClass c = new GenericClass(clazz);
		String name = c.getSimpleName();
		if (classNames.values().contains(name)) {
			name = clazz.getCanonicalName();
		}

		// We can't use "Test" because of JUnit 
		if (name.equals("Test")) {
			name = clazz.getCanonicalName();
		}

		classNames.put(clazz, name);

		return name;
	}

	public String getVariableName(VariableReference var) {
		if (var instanceof ConstantValue) {
			return var.getName();
		} else if (var instanceof InputVariable) {
			return var.getName();
		} else if (var instanceof FieldReference) {
			VariableReference source = ((FieldReference) var).getSource();
			Field field = ((FieldReference) var).getField();
			if (source != null)
				return getVariableName(source) + "." + field.getName();
			else
				return field.getDeclaringClass().getSimpleName() + "." + field.getName();
		} else if (var instanceof ArrayIndex) {
			VariableReference array = ((ArrayIndex) var).getArray();
			List<Integer> indices = ((ArrayIndex) var).getArrayIndices(); 
			String result = getVariableName(array);
			for (Integer index : indices) {
				result += "[" + index + "]";
			}
			return result;
		} else if (var instanceof ArrayReference) {
			String className = var.getSimpleClassName();
			//			int num = 0;
			//			for (VariableReference otherVar : variableNames.keySet()) {
			//				if (!otherVar.equals(var)
			//				        && otherVar.getVariableClass().equals(var.getVariableClass()))
			//					num++;
			//			}
			String variableName = className.substring(0, 1).toLowerCase()
			        + className.substring(1) + "Array";
			variableName = variableName.replace(".", "_").replace("[]", "");
			if (!variableNames.containsKey(var)) {
				if (!nextIndices.containsKey(variableName)) {
					nextIndices.put(variableName, 0);
				}

				int index = nextIndices.get(variableName);
				nextIndices.put(variableName, index + 1);

				variableName += index;

				variableNames.put(var, variableName);
			}

		} else if (!variableNames.containsKey(var)) {
			String className = var.getSimpleClassName();
			//			int num = 0;
			//			for (VariableReference otherVar : variableNames.keySet()) {
			//				if (otherVar.getVariableClass().equals(var.getVariableClass()))
			//					num++;
			//			}

			String variableName = className.substring(0, 1).toLowerCase()
			        + className.substring(1);
			if (CharUtils.isAsciiNumeric(variableName.charAt(variableName.length() - 1)))
				variableName += "_";

			if (variableName.contains("[]")) {
				variableName = variableName.replace("[]", "Array");
			}
			variableName = variableName.replace(".", "_");

			if (!nextIndices.containsKey(variableName)) {
				nextIndices.put(variableName, 0);
			}

			int index = nextIndices.get(variableName);
			nextIndices.put(variableName, index + 1);

			variableName += index;

			variableNames.put(var, variableName);
		}
		return variableNames.get(var);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitTestCase(org.evosuite.testcase.TestCase)
	 */
	@Override
	public void visitTestCase(TestCase test) {
		this.test = test;
		this.testCode = "";
		this.variableNames.clear();
		this.nextIndices.clear();
	}

	protected void visitPrimitiveAssertion(PrimitiveAssertion assertion) {
		VariableReference source = assertion.getSource();
		Object value = assertion.getValue();

		if (value == null) {
			testCode += "assertNull(" + getVariableName(source) + ");";
		} else if (source.getVariableClass().equals(float.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + ", 0.01F);";
		} else if (source.getVariableClass().equals(double.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + ", 0.01D);";
		} else if (value.getClass().isEnum()) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + ");";
		} else if (source.isWrapperType()) {
			if (source.getVariableClass().equals(Float.class)) {
				testCode += "assertEquals(" + NumberFormatter.getNumberString(value)
				        + "(float)" + getVariableName(source) + ", 0.01F);";
			} else if (source.getVariableClass().equals(Double.class)) {
				testCode += "assertEquals(" + NumberFormatter.getNumberString(value)
				        + "(double)" + getVariableName(source) + ", 0.01D);";
			} else if (value.getClass().isEnum()) {
				testCode += "assertEquals(" + NumberFormatter.getNumberString(value)
				        + ", " + getVariableName(source) + ");";
			} else
				testCode += "assertEquals(" + NumberFormatter.getNumberString(value)
				        + ", (" + NumberFormatter.getBoxedClassName(value) + ")"
				        + getVariableName(source) + ");";
		} else
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + ");";
	}

	protected void visitPrimitiveFieldAssertion(PrimitiveFieldAssertion assertion) {
		VariableReference source = assertion.getSource();
		Object value = assertion.getValue();
		Field field = assertion.getField();

		if (value == null) {
			testCode += "assertNull(" + getVariableName(source) + "." + field.getName()
			        + ");";
		} else if (value.getClass().equals(Long.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + field.getName() + ");";
		} else if (value.getClass().equals(Float.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + field.getName() + ", 0.01F);";
		} else if (value.getClass().equals(Double.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + field.getName() + ", 0.01D);";
		} else if (value.getClass().equals(Character.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + field.getName() + ");";
		} else if (value.getClass().equals(String.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + field.getName() + ");";
		} else
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + field.getName() + ");";
	}

	protected void visitInspectorAssertion(InspectorAssertion assertion) {
		VariableReference source = assertion.getSource();
		Object value = assertion.getValue();
		Inspector inspector = assertion.getInspector();

		if (value == null) {
			testCode += "assertNull(" + getVariableName(source) + "."
			        + inspector.getMethodCall() + "());";
		} else if (value.getClass().equals(Long.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + inspector.getMethodCall() + "());";
		} else if (value.getClass().equals(Float.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + inspector.getMethodCall()
			        + "(), 0.01F);";
		} else if (value.getClass().equals(Double.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + inspector.getMethodCall()
			        + "(), 0.01D);";
		} else if (value.getClass().equals(Character.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + inspector.getMethodCall() + "());";
		} else if (value.getClass().equals(String.class)) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + inspector.getMethodCall() + "());";
		} else if (value.getClass().isEnum()) {
			testCode += "assertEquals(" + NumberFormatter.getNumberString(value) + ", "
			        + getVariableName(source) + "." + inspector.getMethodCall() + "());";

		} else
			testCode += "assertEquals(" + value + ", " + getVariableName(source) + "."
			        + inspector.getMethodCall() + "());";
	}

	protected void visitNullAssertion(NullAssertion assertion) {
		VariableReference source = assertion.getSource();
		Boolean value = (Boolean) assertion.getValue();
		if (value.booleanValue()) {
			testCode += "assertNull(" + getVariableName(source) + ");";
		} else
			testCode += "assertNotNull(" + getVariableName(source) + ");";
	}

	protected void visitCompareAssertion(CompareAssertion assertion) {
		VariableReference source = assertion.getSource();
		VariableReference dest = assertion.getDest();
		Object value = assertion.getValue();

		if (source.getType().equals(Integer.class)) {
			if ((Integer) value == 0)
				testCode += "assertTrue(" + getVariableName(source) + " == "
				        + getVariableName(dest) + ");";
			else if ((Integer) value < 0)
				testCode += "assertTrue(" + getVariableName(source) + " < "
				        + getVariableName(dest) + ");";
			else
				testCode += "assertTrue(" + getVariableName(source) + " > "
				        + getVariableName(dest) + ");";

		} else {
			testCode += "assertEquals(" + getVariableName(source) + ".compareTo("
			        + getVariableName(dest) + "), " + value + ");";
		}
	}

	protected void visitEqualsAssertion(EqualsAssertion assertion) {
		VariableReference source = assertion.getSource();
		VariableReference dest = assertion.getDest();
		Object value = assertion.getValue();

		if (source.isPrimitive() && dest.isPrimitive()) {
			if (((Boolean) value).booleanValue())
				testCode += "assertTrue(" + getVariableName(source) + " == "
				        + getVariableName(dest) + ");";
			else
				testCode += "assertFalse(" + getVariableName(source) + " == "
				        + getVariableName(dest) + ");";
		} else {
			if (((Boolean) value).booleanValue())
				testCode += "assertTrue(" + getVariableName(source) + ".equals("
				        + getVariableName(dest) + "));";
			else
				testCode += "assertFalse(" + getVariableName(source) + ".equals("
				        + getVariableName(dest) + "));";
		}
	}

	private void visitAssertion(Assertion assertion) {
		if (assertion instanceof PrimitiveAssertion) {
			visitPrimitiveAssertion((PrimitiveAssertion) assertion);
		} else if (assertion instanceof PrimitiveFieldAssertion) {
			visitPrimitiveFieldAssertion((PrimitiveFieldAssertion) assertion);
		} else if (assertion instanceof InspectorAssertion) {
			visitInspectorAssertion((InspectorAssertion) assertion);
		} else if (assertion instanceof NullAssertion) {
			visitNullAssertion((NullAssertion) assertion);
		} else if (assertion instanceof CompareAssertion) {
			visitCompareAssertion((CompareAssertion) assertion);
		} else if (assertion instanceof EqualsAssertion) {
			visitEqualsAssertion((EqualsAssertion) assertion);
		} else {
			throw new RuntimeException("Unknown assertion type: " + assertion);
		}
	}

	private void addAssertions(StatementInterface statement) {
		boolean assertionAdded = false;
		if (getException(statement) != null) {
			// Assumption: The statement that throws an exception is the last statement of a test.
			VariableReference returnValue = statement.getReturnValue();
			for (Assertion assertion : statement.getAssertions()) {
				if (assertion != null
				        && !assertion.getReferencedVariables().contains(returnValue)) {
					visitAssertion(assertion);
					testCode += "\n";
					assertionAdded = true;
				}
			}
		} else {
			for (Assertion assertion : statement.getAssertions()) {
				if (assertion != null) {
					visitAssertion(assertion);
					testCode += "\n";
					assertionAdded = true;
				}
			}
		}
		if (assertionAdded)
			testCode += "\n";
	}

	private String getEnumValue(EnumPrimitiveStatement<?> statement) {
		Object value = statement.getValue();
		Class<?> clazz = statement.getEnumClass();
		String className = getClassName(clazz);

		try {
			if (value.getClass().getField(value.toString()) != null)
				return className + "." + value;

		} catch (NoSuchFieldException e) {
			// Ignore
		}

		for (Field field : value.getClass().getDeclaredFields()) {
			if (field.isEnumConstant()) {
				try {
					if (field.get(value).equals(value)) {
						return className + "." + field.getName();
					}
				} catch (Exception e) {
					// ignore
				}
			}
		}
		return className + ".valueOf(\"" + value + "\")";

	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitPrimitiveStatement(org.evosuite.testcase.PrimitiveStatement)
	 */
	@Override
	public void visitPrimitiveStatement(PrimitiveStatement<?> statement) {
		VariableReference retval = statement.getReturnValue();
		Object value = statement.getValue();

		if (statement instanceof StringPrimitiveStatement) {
			testCode += ((Class<?>) retval.getType()).getSimpleName() + " "
			        + getVariableName(retval) + " = \""
			        + StringEscapeUtils.escapeJava((String) value) + "\";\n";
		} else if (statement instanceof LongPrimitiveStatement) {
			testCode += ((Class<?>) retval.getType()).getSimpleName() + " "
			        + getVariableName(retval) + " = " + value + "L;\n";
		} else if (statement instanceof FloatPrimitiveStatement) {
			testCode += ((Class<?>) retval.getType()).getSimpleName() + " "
			        + getVariableName(retval) + " = " + value + "F;\n";
		} else if (statement instanceof CharPrimitiveStatement) {
			testCode += ((Class<?>) retval.getType()).getSimpleName() + " "
			        + getVariableName(retval) + " = '"
			        + StringEscapeUtils.escapeJava(value.toString()) + "';\n";
		} else if (statement instanceof EnumPrimitiveStatement) {
			if (value != null)
				testCode += getClassName(retval) + " " + getVariableName(retval) + " = "
				        + getEnumValue((EnumPrimitiveStatement<?>) statement) + ";\n";
			else
				testCode += getClassName(retval) + " " + getVariableName(retval) + " = ("
				        + getClassName(retval) + ") null;\n";
		} else if (statement instanceof FileNamePrimitiveStatement) {
			// changed by Daniel
			testCode += ((Class<?>) retval.getType()).getSimpleName() + " "
			        + getVariableName(retval) + " = new "
			        + ((Class<?>) retval.getType()).getSimpleName() + "(\""
			        + ((EvoSuiteFile) value).getPath() + "\");\n";
		} else {
			testCode += getClassName(retval) + " " + getVariableName(retval) + " = "
			        + value + ";\n";
		}
		addAssertions(statement);
	}

	@Override
	public void visitPrimitiveExpression(PrimitiveExpression statement) {
		VariableReference retval = statement.getReturnValue();
		String expression = ((Class<?>) retval.getType()).getSimpleName() + " " + getVariableName(retval) + " = ";
		expression += getVariableName(statement.getLeftOperand()) + " " + statement.getOperator().toCode() + " " + getVariableName(statement.getRightOperand());
		testCode += expression+";\n";
		addAssertions(statement);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitFieldStatement(org.evosuite.testcase.FieldStatement)
	 */
	@Override
	public void visitFieldStatement(FieldStatement statement) {
		Throwable exception = getException(statement);

		String cast_str = "";
		StringBuilder builder = new StringBuilder();

		VariableReference retval = statement.getReturnValue();
		Field field = statement.getField();

		if (!retval.getVariableClass().isAssignableFrom(field.getType())) {
			cast_str += "(" + getClassName(retval) + ")";
		}

		if (exception != null) {
			builder.append(getClassName(retval));
			builder.append(" ");
			builder.append(getVariableName(retval));
			builder.append(" = null;\n");
			builder.append("try {\n  ");
		} else {
			builder.append(getClassName(retval));
			builder.append(" ");
		}
		if (!Modifier.isStatic(field.getModifiers())) {
			VariableReference source = statement.getSource();
			builder.append(getVariableName(retval));
			builder.append(" = ");
			builder.append(cast_str);
			builder.append(getVariableName(source));
			builder.append(".");
			builder.append(field.getName());
			builder.append(";");
		} else {
			builder.append(getVariableName(retval));
			builder.append(" = ");
			builder.append(cast_str);
			builder.append(getClassName(field.getDeclaringClass()));
			builder.append(".");
			builder.append(field.getName());
			builder.append(";");
		}
		if (exception != null) {
			Class<?> ex = exception.getClass();
			while (!Modifier.isPublic(ex.getModifiers()))
				ex = ex.getSuperclass();
			builder.append("\n} catch(");
			builder.append(getClassName(ex));
			builder.append(" e) {}");
		}
		builder.append("\n");

		testCode += builder.toString();
		addAssertions(statement);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitMethodStatement(org.evosuite.testcase.MethodStatement)
	 */
	@Override
	public void visitMethodStatement(MethodStatement statement) {
		String result = "";
		VariableReference retval = statement.getReturnValue();
		Method method = statement.getMethod();
		Throwable exception = getException(statement);
		List<VariableReference> parameters = statement.getParameterReferences();

		if (exception != null && !statement.isDeclaredException(exception)) {
			result += "// Undeclared exception!\n";
		}

		boolean lastStatement = statement.getPosition() == statement.tc.size() - 1;
		boolean unused = exception != null
		        || (test != null && Properties.ASSERTIONS ? !test.hasReferences(retval)
		                : false);

		if (retval.getType() != Void.TYPE
		        && retval.getAdditionalVariableReference() == null && !unused) {
			if (exception != null) {
				if (!lastStatement || statement.hasAssertions())
					result += getClassName(retval) + " " + getVariableName(retval)
					        + " = " + retval.getDefaultValueString() + ";\n";
			} else
				result += getClassName(retval) + " ";
		}
		if (exception != null)
			result += "try {\n  ";

		String parameter_string = "";
		for (int i = 0; i < parameters.size(); i++) {
			if (i > 0) {
				parameter_string += ", ";
			}
			Class<?> declaredParamType = method.getParameterTypes()[i];
			Class<?> actualParamType = parameters.get(i).getVariableClass();
			String name = getVariableName(parameters.get(i));
			if ((!declaredParamType.isAssignableFrom(actualParamType) || name.equals("null"))
			        && !method.getParameterTypes()[i].equals(Object.class)
			        && !method.getParameterTypes()[i].equals(Comparable.class)) {
				parameter_string += "(" + getClassName(method.getParameterTypes()[i])
				        + ") ";
			}
			parameter_string += name;
		}

		String callee_str = "";
		if (!retval.getVariableClass().isAssignableFrom(method.getReturnType())
		        && !retval.getVariableClass().isAnonymousClass() && !unused) {
			String name = getClassName(retval);
			if (!name.matches(".*\\.\\d+$")) {
				callee_str = "(" + name + ")";
			}
		}

		if (Modifier.isStatic(method.getModifiers())) {
			callee_str += getClassName(method.getDeclaringClass());
		} else {
			VariableReference callee = statement.getCallee();
			callee_str += getVariableName(callee);
		}

		if (retval.getType() == Void.TYPE) {
			result += callee_str + "." + method.getName() + "(" + parameter_string + ");";
		} else {
			//			if (exception == null || !lastStatement)
			if (!unused)
				result += getVariableName(retval) + " = ";

			result += callee_str + "." + method.getName() + "(" + parameter_string + ");";
		}

		if (exception != null) {
			//boolean isExpected = getDeclaredExceptions().contains(exception.getClass());
			Class<?> ex = exception.getClass();
			while (!Modifier.isPublic(ex.getModifiers()))
				ex = ex.getSuperclass();
			//if (isExpected)
			result += "\n  fail(\"Expecting exception: " + getClassName(ex) + "\");";
			result += "\n} catch(" + getClassName(ex) + " e) {\n";
			if (exception.getMessage() != null) {
				//if (!isExpected)
				//	result += "\n  fail(\"Undeclared exception: "
				//	        + ClassUtils.getShortClassName(ex) + "\");\n";
				result += "  /*\n";
				for (String msg : exception.getMessage().split("\n")) {
					result += "   * " + StringEscapeUtils.escapeJava(msg) + "\n";
				}
				result += "   */\n";
			}
			result += "}";
		}

		testCode += result + "\n";
		addAssertions(statement);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitConstructorStatement(org.evosuite.testcase.ConstructorStatement)
	 */
	@Override
	public void visitConstructorStatement(ConstructorStatement statement) {
		String parameter_string = "";
		String result = "";
		Constructor<?> constructor = statement.getConstructor();
		VariableReference retval = statement.getReturnValue();
		Throwable exception = getException(statement);

		List<VariableReference> parameters = statement.getParameterReferences();
		if (!parameters.isEmpty()) {
			for (int i = 0; i < parameters.size(); i++) {
				if (constructor.getDeclaringClass().isMemberClass()
				        && !Modifier.isStatic(constructor.getDeclaringClass().getModifiers())) {
					if (i > 1)
						parameter_string += ", ";
					else if (i < 1)
						continue;
				} else {
					if (i > 0) {
						parameter_string += ", ";
					}
				}

				Class<?> declaredParamType = constructor.getParameterTypes()[i];
				Class<?> actualParamType = parameters.get(i).getVariableClass();
				String name = getVariableName(parameters.get(i));

				if ((!declaredParamType.isAssignableFrom(actualParamType) || name.equals("null"))
				        && !constructor.getParameterTypes()[i].equals(Object.class)
				        && !constructor.getParameterTypes()[i].equals(Comparable.class)) {
					parameter_string += "("
					        + getClassName(constructor.getParameterTypes()[i]) + ") ";
				}

				parameter_string += name;
			}

		}
		// String result = ((Class<?>) retval.getType()).getSimpleName()
		// +" "+getVariableName(retval)+ " = null;\n";
		if (exception != null) {
			result = getClassName(retval) + " " + getVariableName(retval) + " = null;\n";
			result += "try {\n  ";
		} else {
			result += getClassName(retval) + " ";
		}
		if (constructor.getDeclaringClass().isMemberClass()
		        && !Modifier.isStatic(constructor.getDeclaringClass().getModifiers())) {
			result += getVariableName(retval) + " = "
			        + getVariableName(parameters.get(0))
			        //			        + new GenericClass(
			        //			                constructor.getDeclaringClass().getEnclosingClass()).getSimpleName()
			        + ".new "
			        // + ConstructorStatement.getReturnType(constructor.getDeclaringClass())
			        + constructor.getDeclaringClass().getSimpleName() + "("
			        //+ getClassName(constructor.getDeclaringClass()) + "("
			        + parameter_string + ");";

		} else {

			result += getVariableName(retval) + " = new "
			        + getClassName(constructor.getDeclaringClass())
			        //			        + ConstructorStatement.getReturnType(constructor.getDeclaringClass())
			        + "(" + parameter_string + ");";
		}

		if (exception != null) {
			Class<?> ex = exception.getClass();
			//boolean isExpected = getDeclaredExceptions().contains(ex);

			while (!Modifier.isPublic(ex.getModifiers()))
				ex = ex.getSuperclass();
			//if (isExpected)
			result += "\n  fail(\"Expecting exception: " + getClassName(ex) + "\");";

			result += "\n} catch(" + getClassName(ex) + " e) {\n";
			if (exception.getMessage() != null) {
				//if (!isExpected)
				//	result += "\n  fail(\"Undeclared exception: "
				//	        + ClassUtils.getShortClassName(ex) + "\");\n";
				result += "  /*\n";
				for (String msg : exception.getMessage().split("\n")) {
					result += "   * " + StringEscapeUtils.escapeJava(msg) + "\n";
				}
				result += "   */\n";
			}
			result += "}";
		}

		testCode += result + "\n";
		addAssertions(statement);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitArrayStatement(org.evosuite.testcase.ArrayStatement)
	 */
	@Override
	public void visitArrayStatement(ArrayStatement statement) {
		VariableReference retval = statement.getReturnValue();
		int[] lengths = statement.getLengths();

		String type = getClassName(retval);
		String multiDimensions = "";
		if (lengths.length == 1) {
			type = type.replaceFirst("\\[\\]", "");
			multiDimensions = "[" + lengths[0] + "]";
		while (type.contains("[]")) {
			multiDimensions += "[]";
			type = type.replaceFirst("\\[\\]", "");
		}
		} else {
			type = type.replaceAll("\\[\\]", "");
			for (int length : lengths) {
				multiDimensions += "[" + length + "]";
			}
		}
		testCode += getClassName(retval) + " " + getVariableName(retval) + " = new "
		        + type + multiDimensions + ";\n";
		addAssertions(statement);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitAssignmentStatement(org.evosuite.testcase.AssignmentStatement)
	 */
	@Override
	public void visitAssignmentStatement(AssignmentStatement statement) {
		String cast = "";
		VariableReference retval = statement.getReturnValue();
		VariableReference parameter = statement.parameter;

		if (!retval.getVariableClass().equals(parameter.getVariableClass()))
			cast = "(" + getClassName(retval) + ") ";

		testCode += getVariableName(retval) + " = " + cast + getVariableName(parameter)
		        + ";\n";
		addAssertions(statement);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.TestVisitor#visitNullStatement(org.evosuite.testcase.NullStatement)
	 */
	@Override
	public void visitNullStatement(NullStatement statement) {
		VariableReference retval = statement.getReturnValue();

		testCode += getClassName(retval) + " " + getVariableName(retval) + " = null;\n";
	}

	public void visitStatement(StatementInterface statement) {
		if (statement instanceof PrimitiveStatement<?>)
			visitPrimitiveStatement((PrimitiveStatement<?>) statement);
		else if (statement instanceof FieldStatement)
			visitFieldStatement((FieldStatement) statement);
		else if (statement instanceof ConstructorStatement)
			visitConstructorStatement((ConstructorStatement) statement);
		else if (statement instanceof MethodStatement)
			visitMethodStatement((MethodStatement) statement);
		else if (statement instanceof AssignmentStatement)
			visitAssignmentStatement((AssignmentStatement) statement);
		else if (statement instanceof ArrayStatement)
			visitArrayStatement((ArrayStatement) statement);
		else if (statement instanceof NullStatement)
			visitNullStatement((NullStatement) statement);
		else
			throw new RuntimeException("Unknown statement type: " + statement);
	}
}
