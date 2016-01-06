/**
 * Copyright (C) 2010-2015 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser Public License as published by the
 * Free Software Foundation, either version 3.0 of the License, or (at your
 * option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along
 * with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.testcase;

import com.googlecode.gentyref.CaptureType;
import com.googlecode.gentyref.GenericTypeReflector;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.assertion.ArrayEqualsAssertion;
import org.evosuite.assertion.Assertion;
import org.evosuite.assertion.CompareAssertion;
import org.evosuite.assertion.EqualsAssertion;
import org.evosuite.assertion.InspectorAssertion;
import org.evosuite.assertion.NullAssertion;
import org.evosuite.assertion.PrimitiveAssertion;
import org.evosuite.assertion.PrimitiveFieldAssertion;
import org.evosuite.assertion.SameAssertion;
import org.evosuite.classpath.ResourceList;
import org.evosuite.testcase.fm.MethodDescriptor;
import org.evosuite.testcase.statements.ArrayStatement;
import org.evosuite.testcase.statements.AssignmentStatement;
import org.evosuite.testcase.statements.ClassPrimitiveStatement;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.FieldStatement;
import org.evosuite.testcase.statements.FunctionalMockStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.NullStatement;
import org.evosuite.testcase.statements.PrimitiveExpression;
import org.evosuite.testcase.statements.PrimitiveStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.ArrayIndex;
import org.evosuite.testcase.variable.ConstantValue;
import org.evosuite.testcase.variable.FieldReference;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericClass;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericField;
import org.evosuite.utils.generic.GenericMethod;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The TestCodeImportsVisitor is a visitor that produces a String representation of a
 * test case. This is the preferred way to produce executable code from EvoSuite
 * tests.
 * 
 * @author Gordon Fraser
 */
public class ImportsTestCodeVisitor extends AbstractTestCodeVisitor {

	protected TestCase test = null;

	protected final Map<Class<?>, String> classNames = new HashMap<Class<?>, String>();

	/**
	 * Retrieve a list of classes that need to be imported to make this unit
	 * test compile
	 *
	 * @return a {@link Set} object.
	 */
	public Set<Class<?>> getImports() {
		Set<Class<?>> imports = new HashSet<Class<?>>();
		for (Class<?> clazz : classNames.keySet()) {
			String name = classNames.get(clazz);
			// If there's a dot in the name, then we assume this is the
			// fully qualified name and we don't need to import
			if (!name.contains(".")) {
				imports.add(clazz);
			}
		}
		return imports;
	}

	/**
	 * <p>
	 * clearExceptions
	 * </p>
	 */
	public void clearExceptions() {
		this.exceptions.clear();
	}

	/**
	 * <p>
	 * Setter for the field <code>exceptions</code>.
	 * </p>
	 *
	 * @param exceptions
	 *            a {@link java.util.Map} object.
	 */
	public void setExceptions(Map<Integer, Throwable> exceptions) {
		this.exceptions.putAll(exceptions);
	}

	/**
	 * <p>
	 * getClassName
	 * </p>
	 *
	 * @param var
	 *            a {@link org.evosuite.testcase.variable.VariableReference} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getClassName(VariableReference var) {
		return getTypeName(var.getType());
	}

	/**
	 * <p>
	 * getTypeName
	 * </p>
	 *
	 * @param type
	 *            a {@link java.lang.reflect.Type} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getTypeName(Type type) {
		if (type instanceof Class<?>) {
			return getClassName((Class<?>) type);
		} else if (type instanceof ParameterizedType) {
			return getTypeName((ParameterizedType) type);
		} else if (type instanceof WildcardType) {
			String ret = "?";
			boolean first = true;
			for (Type bound : ((WildcardType) type).getLowerBounds()) {
				// If there are lower bounds we need to state them, even if Object
				if (bound == null) // || GenericTypeReflector.erase(bound).equals(Object.class))
					continue;

				if (!first)
					ret += ", ";
				ret += " super " + getTypeName(bound);
				first = false;
			}
			for (Type bound : ((WildcardType) type).getUpperBounds()) {
				if (bound == null
						|| (!(bound instanceof CaptureType) && GenericTypeReflector.erase(bound).equals(Object.class)))
					continue;

				if (!first)
					ret += ", ";
				ret += " extends " + getTypeName(bound);
				first = false;
			}
			return ret;
		} else if (type instanceof TypeVariable) {
			return "?";
		} else if (type instanceof CaptureType) {
			CaptureType captureType = (CaptureType) type;
			if (captureType.getLowerBounds().length == 0)
				return "?";
			else
				return getTypeName(captureType.getLowerBounds()[0]);
		} else if (type instanceof GenericArrayType) {
			return getTypeName(((GenericArrayType) type).getGenericComponentType())
					+ "[]";
		} else {
			throw new RuntimeException("Unsupported type:" + type + ", class"
					+ type.getClass());
		}
	}

	public String getTypeName(VariableReference var) {

		GenericClass clazz = var.getGenericClass();
		return getTypeName(clazz.getType());
	}

	private String getTypeName(ParameterizedType type) {
		String name = getClassName((Class<?>) type.getRawType());
		Type[] types = type.getActualTypeArguments();
		boolean isDefined = false;
		for(Type parameterType : types) {
			if(parameterType instanceof Class<?> ||
					parameterType instanceof ParameterizedType ||
					parameterType instanceof WildcardType ||
					parameterType instanceof GenericArrayType) {
				isDefined = true;
				break;
			}
		}
		if(isDefined) {
			if (types.length > 0) {
				name += "<";
				for (int i = 0; i < types.length; i++) {
					if (i != 0)
						name += ", ";

					name += getTypeName(types[i]);
				}
				name += ">";
			}
		}
		return name;
	}

	/**
	 * <p>
	 * getClassName
	 * </p>
	 *
	 * @param type
	 *            a {@link Type} object.
	 *
	 */
	private void getClassName(Type type) {
		if (type instanceof Class<?>) {
			getClassName((Class<?>) type);
		} else if (type instanceof ParameterizedType) {
			getClassName((Class<?>) ((ParameterizedType)type).getRawType());
		} else if (type instanceof WildcardType) {
			for (Type bound : ((WildcardType) type).getLowerBounds()) {
				// If there are lower bounds we need to state them, even if Object
				if (bound == null) // || GenericTypeReflector.erase(bound).equals(Object.class))
					continue;

				getClassName(bound);
			}
			for (Type bound : ((WildcardType) type).getUpperBounds()) {
				if (bound == null
						|| (!(bound instanceof CaptureType) && GenericTypeReflector.erase(bound).equals(Object.class)))
					continue;

				getClassName(bound);
			}
		} else if (type instanceof TypeVariable) {
			// Do nothing
		} else if (type instanceof CaptureType) {
			CaptureType captureType = (CaptureType) type;
			if (! (captureType.getLowerBounds().length == 0))
				getClassName(captureType.getLowerBounds()[0]);
		} else if (type instanceof GenericArrayType) {
			getClassName(((GenericArrayType) type).getGenericComponentType());
		} else {
			throw new RuntimeException("Unsupported type:" + type + ", class"
					+ type.getClass());
		}
	}

	/**
	 * <p>
	 * getClassName
	 * </p>
	 *
	 * @param clazz
	 *            a {@link Class} object.
	 * @return a {@link String} object.
	 */
	public String getClassName(Class<?> clazz) {
		if (classNames.containsKey(clazz))
			return classNames.get(clazz);

		if (clazz.isArray()) {
			return getClassName(clazz.getComponentType()) + "[]";
		}

		GenericClass c = new GenericClass(clazz);
		String name = c.getSimpleName();
		if (classNames.values().contains(name)) {
			name = clazz.getCanonicalName();
		} else {
			/*
			 * If e.g. there is a foo.bar.IllegalStateException with
			 * foo.bar being the SUT package, then we need to use the
			 * full package name for java.lang.IllegalStateException
			 */
			String fullName = Properties.CLASS_PREFIX +"."+name;
			if(!fullName.equals(clazz.getCanonicalName())) {
				try {
					if(ResourceList.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT()).hasClass(fullName)) {
						name = clazz.getCanonicalName();
					}
				} catch(IllegalArgumentException e) {
					// If the classpath is not correct, then we just don't check
					// because that cannot happen in regular EvoSuite use, only
					// from test cases
				}
			}
		}
		// Ensure outer classes are imported as well
		Class<?> outerClass = clazz.getEnclosingClass();
		if(outerClass != null) {
			String enclosingName = getClassName(outerClass);
			String simpleOuterName = outerClass.getSimpleName();
			if(simpleOuterName.equals(enclosingName)) {
				name = enclosingName + name.substring(simpleOuterName.length());
			}
		}

		Class<?> declaringClass = clazz.getDeclaringClass();
		if(declaringClass != null) {
			getClassName(declaringClass);
		}

		// We can't use "Test" because of JUnit
		if (name.equals("Test")) {
			name = clazz.getCanonicalName();
		}
		classNames.put(clazz, name);

		return name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.evosuite.testcase.TestVisitor#visitTestCase(org.evosuite.testcase.TestCase)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitTestCase(TestCase test) {
		this.test = test;
	}

	/**
	 * <p>
	 * visitPrimitiveAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link PrimitiveAssertion} object.
	 */
	protected void visitPrimitiveAssertion(PrimitiveAssertion assertion) {
		VariableReference source = assertion.getSource();
		Object value = assertion.getValue();

		getClassName(source);

		if ((value != null) && value.getClass().isEnum()) {
			// Make sure the enum is imported in the JUnit test
			getClassName(value.getClass());
		}
	}

	/**
	 * <p>
	 * visitArrayEqualsAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link ArrayEqualsAssertion}
	 *            object.
	 */
	protected void visitArrayEqualsAssertion(ArrayEqualsAssertion assertion) {
		VariableReference source = assertion.getSource();

		getClassName(source);
		if(source.getComponentClass().equals(Boolean.class) || source.getComponentClass().equals(boolean.class)) {
			// Make sure that the Arrays class is imported
			getClassName(Arrays.class);
		}
	}

	/**
	 * <p>
	 * visitPrimitiveFieldAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link PrimitiveFieldAssertion}
	 *            object.
	 */
	protected void visitPrimitiveFieldAssertion(PrimitiveFieldAssertion assertion) {
		VariableReference source = assertion.getSource();
		Object value = assertion.getValue();
		Field field = assertion.getField();

		if(Modifier.isStatic(field.getModifiers())) {
			getClassName(field.getDeclaringClass());
		} else {
			getClassName(source);
		}

		if (value != null && value.getClass().isEnum()) {
			// Make sure the enum is imported in the JUnit test
			getClassName(value.getClass());
		}
	}

	/**
	 * <p>
	 * visitInspectorAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link InspectorAssertion} object.
	 */
	protected void visitInspectorAssertion(InspectorAssertion assertion) {
		VariableReference source = assertion.getSource();
		Object value = assertion.getValue();

		getClassName(source);
		if ((value != null) && (value.getClass().isEnum() || value instanceof Enum)) {
			// Make sure the enum is imported in the JUnit test
			getClassName(value.getClass());
		}
	}

	/**
	 * <p>
	 * visitNullAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link NullAssertion} object.
	 */
	protected void visitNullAssertion(NullAssertion assertion) {
		VariableReference source = assertion.getSource();

		getClassName(source);
	}

	/**
	 * <p>
	 * visitCompareAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link CompareAssertion} object.
	 */
	protected void visitCompareAssertion(CompareAssertion assertion) {
		VariableReference source = assertion.getSource();
		VariableReference dest = assertion.getDest();

		getClassName(source);
		getClassName(dest);
	}

	/**
	 * <p>
	 * visitEqualsAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link EqualsAssertion} object.
	 */
	protected void visitEqualsAssertion(EqualsAssertion assertion) {
		VariableReference source = assertion.getSource();
		VariableReference dest = assertion.getDest();

		getClassName(source);
		getClassName(dest);

		if (! (source.isPrimitive() && dest.isPrimitive()))
			getClassName(Object.class);
	}

	/**
	 * <p>
	 * visitSameAssertion
	 * </p>
	 *
	 * @param assertion
	 *            a {@link SameAssertion} object.
	 */
	protected void visitSameAssertion(SameAssertion assertion) {
		VariableReference source = assertion.getSource();
		VariableReference dest = assertion.getDest();

		getClassName(source);
		getClassName(dest);
	}

	protected void visitAssertion(Assertion assertion) {

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
		} else if (assertion instanceof SameAssertion) {
			visitSameAssertion((SameAssertion) assertion);
		} else if (assertion instanceof ArrayEqualsAssertion) {
			visitArrayEqualsAssertion((ArrayEqualsAssertion) assertion);
		} else {
			throw new RuntimeException("Unknown assertion type: " + assertion);
		}
	}

	private void visitAssertions(Statement statement) {
		if (getException(statement) != null) {
			// Assumption: The statement that throws an exception is the last statement of a test.
			VariableReference returnValue = statement.getReturnValue();
			for (Assertion assertion : statement.getAssertions()) {
				if (assertion != null && !assertion.getReferencedVariables().contains(returnValue)) {
					visitAssertion(assertion);
				}
			}
		} else {
			for (Assertion assertion : statement.getAssertions()) {
				if (assertion != null) {
					visitAssertion(assertion);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitPrimitiveStatement(org.evosuite.testcase.PrimitiveStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitPrimitiveStatement(PrimitiveStatement<?> statement) {
		VariableReference retval = statement.getReturnValue();
		Object value = statement.getValue();

		getClassName(retval);
		if (statement instanceof ClassPrimitiveStatement)
			getClassName(((Class<?>) value));

		visitAssertions(statement);
	}

	/** {@inheritDoc} */
	@Override
	public void visitPrimitiveExpression(PrimitiveExpression statement) {
		VariableReference retval = statement.getReturnValue();

		getClassName(retval);
		getClassName(statement.getLeftOperand());
		getClassName(statement.getRightOperand());

		visitAssertions(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitFieldStatement(org.evosuite.testcase.FieldStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitFieldStatement(FieldStatement statement) {
		Throwable exception = getException(statement);

		VariableReference retval = statement.getReturnValue();
		GenericField field = statement.getField();

		getClassName(retval);

		if (!field.isStatic()) {
			VariableReference source = statement.getSource();
			getClassName(source);
		} else {
			getClassName(field.getField().getDeclaringClass());
		}
		if (exception != null) {
			Class<?> ex = exception.getClass();
			while (!Modifier.isPublic(ex.getModifiers()))
				ex = ex.getSuperclass();
			getClassName(ex);
		}
		visitAssertions(statement);
	}

	private void collectFromParameters(Type[] parameterTypes,
	        List<VariableReference> parameters, boolean isGenericMethod,
	        boolean isOverloaded, int startPos) {

		for (int i = startPos; i < parameters.size(); i++) {
			Type declaredParamType = parameterTypes[i];
			Type actualParamType = parameters.get(i).getType();
			getClassName(declaredParamType);
			getClassName(parameters.get(i));

			Class<?> rawParamClass = declaredParamType instanceof WildcardType ? Object.class : GenericTypeReflector.erase(declaredParamType);
			if (rawParamClass.isPrimitive()) {
				getClassName(rawParamClass);
				getClassName(ClassUtils.primitiveToWrapper(rawParamClass));
			} else if (isGenericMethod && !(declaredParamType instanceof WildcardType )) {

			} else if (!GenericClass.isAssignable(declaredParamType, actualParamType)) {

				if (TypeUtils.isArrayType(declaredParamType) && TypeUtils.isArrayType(actualParamType)) {
					Class<?> componentClass = GenericTypeReflector.erase(declaredParamType).getComponentType();
					if (componentClass.equals(Object.class)) {
						GenericClass genericComponentClass = new GenericClass(componentClass);
						if (genericComponentClass.hasWildcardOrTypeVariables()) {
							// If we are assigning a generic array, then we don't need to cast
						} else {
							// If we are assigning a non-generic array, then we do need to cast
							getClassName(declaredParamType);
						}
					} else { //if (!GenericClass.isAssignable(GenericTypeReflector.getArrayComponentType(declaredParamType), GenericTypeReflector.getArrayComponentType(actualParamType))) {
						getClassName(declaredParamType);
					}
				} else if (!(actualParamType instanceof ParameterizedType)) {
					getClassName(declaredParamType);
				}
			} else {
				// We have to cast between wrappers and primitives in case there
				// are overloaded signatures. This could be optimized by checking
				// if there actually is a problem of overloaded signatures
				GenericClass parameterClass = new GenericClass(declaredParamType);
				if (parameterClass.isWrapperType() && parameters.get(i).isPrimitive()) {
					getClassName(declaredParamType);
				} else if (parameterClass.isPrimitive()
				        && parameters.get(i).isWrapperType()) {
					getClassName(declaredParamType);
				} else if (isOverloaded) {
					// If there is an overloaded method, we need to cast to make sure we use the right version
					if (!declaredParamType.equals(actualParamType)) {
						getClassName(declaredParamType);
					}
				}
			}
		}
	}


	@Override
	public void visitFunctionalMockStatement(FunctionalMockStatement st) {

		VariableReference retval = st.getReturnValue();

		//by construction, we should avoid cases like:
		//  Object obj = mock(Foo.class);
		//as it leads to problems when setting up "when(...)", and anyway it would make no sense
		Class<?> rawClass = new GenericClass(retval.getType()).getRawClass();
		Class<?> targetClass = st.getTargetClass();
		assert  rawClass.getName().equals(targetClass.getName()) :
				"Mismatch between variable raw type "+rawClass+" and mocked "+targetClass;

		getClassName(rawClass);
		getClassName(retval);

		//when(...).thenReturn(...)
		for(MethodDescriptor md : st.getMockedMethods()){
			if(!md.shouldBeMocked()){
				continue;
			}
			List<VariableReference> params = st.getParameters(md.getID());

			Class<?> returnType = md.getMethod().getReturnType();

			if(! returnType.isPrimitive()) {
				Type[] types = new Type[params.size()];
				for (int i = 0; i < types.length; i++) {
					types[i] = params.get(i).getType();
				}

				collectFromParameters(types, params, false, false, 0);//TODO unsure of these parameters
			} else {
				for (int i = 0; i < params.size(); i++) {
					getClassName(params.get(i));
				}
			}
		}
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitMethodStatement(org.evosuite.testcase.MethodStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitMethodStatement(MethodStatement statement) {
		VariableReference retval = statement.getReturnValue();
		GenericMethod method = statement.getMethod();
		Throwable exception = getException(statement);
		List<VariableReference> parameters = statement.getParameterReferences();
		boolean isGenericMethod = method.hasTypeParameters();

		boolean lastStatement = statement.getPosition() == statement.getTestCase().size() - 1;
		boolean unused = !Properties.ASSERTIONS ? exception != null : test != null
		        && !test.hasReferences(retval);

		if (!retval.isVoid() && retval.getAdditionalVariableReference() == null && !unused) {
			if (exception != null) {
				if (!lastStatement || statement.hasAssertions()) {
					getClassName(retval);
				}
			} else {
				getClassName(retval);
			}
		}

		collectFromParameters(method.getParameterTypes(),
				parameters, isGenericMethod,
                method.isOverloaded(parameters), 0);

		if (!unused && !retval.isAssignableFrom(method.getReturnType())
		        && !retval.getVariableClass().isAnonymousClass()
		        // Static generic methods are a special case where we shouldn't add a cast
		        && !(isGenericMethod && method.getParameterTypes().length == 0 && method.isStatic())) {
			getClassName(retval);
		}

		if (method.isStatic()) {
			getClassName(method.getMethod().getDeclaringClass());
		} else {
			VariableReference callee = statement.getCallee();
			getClassName(callee);
			if (callee instanceof ConstantValue) {
				getClassName(method.getMethod().getDeclaringClass());
			} else {
				if(!callee.isAssignableTo(method.getMethod().getDeclaringClass())) {
					getClassName(method.getMethod().getDeclaringClass());
				}
			}
		}

		if (! retval.isVoid() && !unused) {
			getClassName(retval);
		}

		if (exception != null && !test.isFailing()) {
			Class<?> ex = getExceptionClassToUse(exception);
			getClassName(ex);
		}

		visitAssertions(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitConstructorStatement(org.evosuite.testcase.ConstructorStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitConstructorStatement(ConstructorStatement statement) {
		GenericConstructor constructor = statement.getConstructor();
		VariableReference retval = statement.getReturnValue();
		Throwable exception = getException(statement);
		boolean isGenericConstructor = constructor.hasTypeParameters();
		boolean isNonStaticMemberClass = constructor.getConstructor().getDeclaringClass().isMemberClass()
		        && !constructor.isStatic()
		        && !Modifier.isStatic(constructor.getConstructor().getDeclaringClass().getModifiers());

		List<VariableReference> parameters = statement.getParameterReferences();

		Type[] parameterTypes = constructor.getParameterTypes();
		collectFromParameters(parameterTypes, parameters,
				isGenericConstructor,
		        constructor.isOverloaded(parameters),
				isNonStaticMemberClass ? 1 : 0);

		getClassName(retval);
		getClassName(constructor.getOwnerType());

		if (isNonStaticMemberClass)
			getClassName(parameters.get(0));

		if (exception != null) {
			Class<?> ex = getExceptionClassToUse(exception);
			getClassName(ex);
		}

		visitAssertions(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitArrayStatement(org.evosuite.testcase.ArrayStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitArrayStatement(ArrayStatement statement) {
		VariableReference retval = statement.getReturnValue();

		getClassName(retval);
		if (retval.getGenericClass().isGenericArray()) {
			getClassName(Array.class);
			getClassName(retval.getComponentClass());
		}

		visitAssertions(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitAssignmentStatement(org.evosuite.testcase.AssignmentStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitAssignmentStatement(AssignmentStatement statement) {

		VariableReference retval = statement.getReturnValue();
		VariableReference parameter = statement.getValue();

		getClassName(retval);
		getClassName(parameter);

		visitAssertions(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.evosuite.testcase.TestVisitor#visitNullStatement(org.evosuite.testcase.NullStatement)
	 */
	/** {@inheritDoc} */
	@Override
	public void visitNullStatement(NullStatement statement) {
		VariableReference retval = statement.getReturnValue();
		getClassName(retval);
	}

	@Override
	public void visitStatement(Statement statement) {
		super.visitStatement(statement);
	}

	public Map<Class<?>, String> getClassNames() {
		return classNames;
	}

	public Map<Integer, Throwable> getExceptions() {
		return exceptions;
	}
}
