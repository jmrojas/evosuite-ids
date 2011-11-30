/*
 * Copyright (C) 2010 Saarland University
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
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.testcase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

/**
 * This statement represents a constructor call
 * 
 * @author Gordon Fraser
 * 
 */
public class ConstructorStatement extends AbstractStatement {

	private static final long serialVersionUID = -3035570485633271957L;

	private transient Constructor<?> constructor;

	public List<VariableReference> parameters;

	private static List<String> primitiveClasses = Arrays.asList("char", "int", "short",
	                                                             "long", "boolean",
	                                                             "float", "double",
	                                                             "byte");

	public ConstructorStatement(TestCase tc, Constructor<?> constructor,
	        java.lang.reflect.Type type, List<VariableReference> parameters) {
		super(tc, new VariableReferenceImpl(tc, type));
		this.constructor = constructor;
		// this.return_type = constructor.getDeclaringClass();
		this.parameters = parameters;
	}

	/**
	 * This constructor allows you to use an already existing VariableReference
	 * as retvar. This should only be done, iff an old statement is replaced
	 * with this statement. And already existing objects should in the future
	 * reference this object.
	 * 
	 * @param tc
	 * @param constructor
	 * @param retvar
	 * @param parameters
	 */
	public ConstructorStatement(TestCase tc, Constructor<?> constructor,
	        VariableReference retvar, List<VariableReference> parameters) {
		super(tc, retvar);
		assert (tc.size() > retvar.getStPosition()); //as an old statement should be replaced by this statement
		this.constructor = constructor;
		// this.return_type = constructor.getDeclaringClass();
		this.parameters = parameters;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	private static String getReturnType(Class<?> clazz) {
		String retVal = ClassUtils.getShortClassName(clazz);
		if (primitiveClasses.contains(retVal))
			return clazz.getSimpleName();

		return retVal;
	}

	// TODO: Handle inner classes (need instance parameter for newInstance)
	@Override
	public Throwable execute(final Scope scope, PrintStream out)
	        throws InvocationTargetException, IllegalArgumentException,
	        InstantiationException, IllegalAccessException {
		PrintStream old_out = System.out;
		PrintStream old_err = System.err;
		System.setOut(out);
		System.setErr(out);

		logger.trace("Executing constructor " + constructor.toString());
		final Object[] inputs = new Object[parameters.size()];

		try {
			return super.exceptionHandler(new Executer() {

				@Override
				public void execute() throws InvocationTargetException,
				        IllegalArgumentException, IllegalAccessException,
				        InstantiationException {

					for (int i = 0; i < parameters.size(); i++) {
						try {
							inputs[i] = parameters.get(i).getObject(scope);
						} catch (CodeUnderTestException e) {
							throw CodeUnderTestException.throwException(e.getCause());
						} catch (Throwable e) {
							logger.error("Error encountered: " + e);
							assert (false);
							throw new EvosuiteError(e);
						}
					}

					Object ret = constructor.newInstance(inputs);

					try {
						// assert(retval.getVariableClass().isAssignableFrom(ret.getClass())) :"we want an " + retval.getVariableClass() + " but got an " + ret.getClass();
						retval.setObject(scope, ret);
					} catch (CodeUnderTestException e) {
						throw CodeUnderTestException.throwException(e);
					} catch (Throwable e) {
						throw new EvosuiteError(e);
					}
				}

				@Override
				public Set<Class<? extends Throwable>> throwableExceptions() {
					Set<Class<? extends Throwable>> t = new HashSet<Class<? extends Throwable>>();
					t.add(InvocationTargetException.class);
					return t;
				}
			});

		} catch (InvocationTargetException e) {
			System.setOut(old_out);
			System.setErr(old_err);
			exceptionThrown = e.getCause();
			logger.debug("Exception thrown in constructor: " + e.getCause());

		} finally {
			System.setOut(old_out);
			System.setErr(old_err);
		}
		return exceptionThrown;
	}

	@Override
	public String getCode(Throwable exception) {
		String parameter_string = "";
		String result = "";
		if (!parameters.isEmpty()) {
			for (int i = 0; i < parameters.size(); i++) {
				if (i > 0) {
					parameter_string += ", ";
				}
				Class<?> declaredParamType = constructor.getParameterTypes()[i];
				Class<?> actualParamType = parameters.get(i).getVariableClass();
				String name = parameters.get(i).getName();
				if ((!declaredParamType.isAssignableFrom(actualParamType) || name.equals("null"))
				        && !constructor.getParameterTypes()[i].equals(Object.class)
				        && !constructor.getParameterTypes()[i].equals(Comparable.class)) {
					parameter_string += "("
					        + new GenericClass(constructor.getParameterTypes()[i]).getSimpleName()
					        + ") ";
				}
				parameter_string += name;
			}

		}
		// String result = ((Class<?>) retval.getType()).getSimpleName()
		// +" "+retval.getName()+ " = null;\n";
		if (exception != null) {
			result = retval.getSimpleClassName() + " " + retval.getName() + " = null;\n";
			result += "try {\n  ";
		} else {
			result += retval.getSimpleClassName() + " ";
		}
		result += retval.getName() + " = new "
		        + getReturnType(constructor.getDeclaringClass()) + "(" + parameter_string
		        + ");";
		logger.info("Class: " + constructor.getDeclaringClass().getName() + " ->"
		        + ClassUtils.getShortClassName(constructor.getDeclaringClass()));
		if (exception != null) {
			Class<?> ex = exception.getClass();
			//boolean isExpected = getDeclaredExceptions().contains(ex);

			while (!Modifier.isPublic(ex.getModifiers()))
				ex = ex.getSuperclass();
			//if (isExpected)
			result += "\n  fail(\"Expecting exception: "
			        + ClassUtils.getShortClassName(ex) + "\");";

			result += "\n} catch(" + ClassUtils.getShortClassName(ex) + " e) {\n";
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

		return result;
	}

	@Override
	public StatementInterface copy(TestCase newTestCase, int offset) {
		ArrayList<VariableReference> new_params = new ArrayList<VariableReference>();
		for (VariableReference r : parameters) {
			new_params.add(r.copy(newTestCase, offset));
		}

		AbstractStatement copy = new ConstructorStatement(newTestCase, constructor,
		        retval.getType(), new_params);
		// copy.assertions = copyAssertions(newTestCase, offset);

		return copy;
	}

	@Override
	public Set<VariableReference> getVariableReferences() {
		Set<VariableReference> references = new HashSet<VariableReference>();
		references.add(retval);
		references.addAll(parameters);
		for (VariableReference param : parameters) {
			if (param.getAdditionalVariableReference() != null)
				references.add(param.getAdditionalVariableReference());
		}
		return references;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#replace(de.unisb.cs.st.evosuite.testcase.VariableReference, de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	public void replace(VariableReference var1, VariableReference var2) {
		for (int i = 0; i < parameters.size(); i++) {

			if (parameters.get(i).equals(var1))
				parameters.set(i, var2);
			else
				parameters.get(i).replaceAdditionalVariableReference(var1, var2);
		}
	}

	public List<VariableReference> getParameterReferences() {
		return parameters;
	}

	@Override
	public boolean equals(Object s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		ConstructorStatement ms = (ConstructorStatement) s;
		if (ms.parameters.size() != parameters.size())
			return false;

		if (!this.constructor.equals(ms.constructor))
			return false;

		for (int i = 0; i < parameters.size(); i++) {
			if (!parameters.get(i).equals(ms.parameters.get(i)))
				return false;
		}

		return retval.equals(ms.retval);
	}

	@Override
	public int hashCode() {
		final int prime = 41;
		int result = 1;
		result = prime * result + ((constructor == null) ? 0 : constructor.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.Statement#getBytecode(org.objectweb.
	 * asm.commons.GeneratorAdapter)
	 */
	@Override
	public void getBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        Throwable exception) {
		logger.debug("Invoking constructor");
		Label start = mg.newLabel();
		Label end = mg.newLabel();

		// if(exception != null)
		mg.mark(start);

		mg.newInstance(Type.getType(retval.getVariableClass()));
		mg.dup();
		int num = 0;
		for (VariableReference parameter : parameters) {
			parameter.loadBytecode(mg, locals);
			if (constructor.getParameterTypes()[num].isPrimitive()) {
				if (!constructor.getParameterTypes()[num].equals(parameter.getVariableClass())) {
					logger.debug("Types don't match - casting "
					        + parameter.getVariableClass().getName() + " to "
					        + constructor.getParameterTypes()[num].getName());
					mg.cast(Type.getType(parameter.getVariableClass()),
					        Type.getType(constructor.getParameterTypes()[num]));
				}
			}
			num++;
		}
		mg.invokeConstructor(Type.getType(retval.getVariableClass()),
		                     Method.getMethod(constructor));
		logger.debug("Storing result");
		retval.storeBytecode(mg, locals);

		// if(exception != null) {
		mg.mark(end);
		Label l = mg.newLabel();
		mg.goTo(l);
		// mg.catchException(start, end,
		// Type.getType(getExceptionClass(exception)));
		mg.catchException(start, end, Type.getType(Throwable.class));
		mg.pop(); // Pop exception from stack
		if (!retval.isVoid()) {
			Class<?> clazz = retval.getVariableClass();
			if (clazz.equals(boolean.class))
				mg.push(false);
			else if (clazz.equals(char.class))
				mg.push(0);
			else if (clazz.equals(int.class))
				mg.push(0);
			else if (clazz.equals(short.class))
				mg.push(0);
			else if (clazz.equals(long.class))
				mg.push(0L);
			else if (clazz.equals(float.class))
				mg.push(0.0F);
			else if (clazz.equals(double.class))
				mg.push(0.0);
			else if (clazz.equals(byte.class))
				mg.push(0);
			else if (clazz.equals(String.class))
				mg.push("");
			else
				mg.visitInsn(Opcodes.ACONST_NULL);

			retval.storeBytecode(mg, locals);
		}
		mg.mark(l);
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unisb.cs.st.evosuite.testcase.Statement#getDeclaredExceptions()
	 */
	@Override
	public Set<Class<?>> getDeclaredExceptions() {
		Set<Class<?>> ex = super.getDeclaredExceptions();
		ex.addAll(Arrays.asList(constructor.getExceptionTypes()));
		return ex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.Statement#getUniqueVariableReferences()
	 */
	@Override
	public List<VariableReference> getUniqueVariableReferences() {
		List<VariableReference> references = new ArrayList<VariableReference>();
		references.add(retval);
		references.addAll(parameters);
		for (VariableReference param : parameters) {
			if (param instanceof ArrayIndex)
				references.add(((ArrayIndex) param).getArray());
		}
		return references;

	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#isValid()
	 */
	@Override
	public boolean isValid() {
		assert (super.isValid());
		for (VariableReference v : parameters) {
			v.getStPosition();
		}
		return true;
	}

	@Override
	public boolean same(StatementInterface s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		ConstructorStatement ms = (ConstructorStatement) s;
		if (ms.parameters.size() != parameters.size())
			return false;

		if (!this.constructor.equals(ms.constructor))
			return false;

		for (int i = 0; i < parameters.size(); i++) {
			if (!parameters.get(i).same(ms.parameters.get(i)))
				return false;
		}

		return retval.same(ms.retval);
	}

	@Override
	public AccessibleObject getAccessibleObject() {
		return constructor;
	}

	@Override
	public boolean isAssignmentStatement() {
		return false;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		// Write/save additional fields
		oos.writeObject(constructor.getDeclaringClass().getName());
		oos.writeObject(Type.getConstructorDescriptor(constructor));
	}

	// assumes "static java.util.Date aDate;" declared
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		ois.defaultReadObject();

		// Read/initialize additional fields
		Class<?> constructorClass = TestCluster.classLoader.loadClass((String) ois.readObject());
		String constructorDesc = (String) ois.readObject();
		for (Constructor<?> constructor : constructorClass.getDeclaredConstructors()) {
			if (Type.getConstructorDescriptor(constructor).equals(constructorDesc)) {
				this.constructor = constructor;
				return;
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#changeClassLoader(java.lang.ClassLoader)
	 */
	@Override
	public void changeClassLoader(ClassLoader loader) {
		try {
			Class<?> oldClass = constructor.getDeclaringClass();
			Class<?> newClass = loader.loadClass(oldClass.getName());
			for (Constructor<?> newConstructor : TestCluster.getConstructors(newClass)) {
				boolean equals = true;
				Class<?>[] oldParameters = this.constructor.getParameterTypes();
				Class<?>[] newParameters = newConstructor.getParameterTypes();
				if (oldParameters.length != newParameters.length)
					continue;

				for (int i = 0; i < newParameters.length; i++) {
					if (!oldParameters[i].getName().equals(newParameters[i].getName())) {
						equals = false;
						break;
					}
				}
				if (equals) {
					this.constructor = newConstructor;
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		} catch (SecurityException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		}
		super.changeClassLoader(loader);
		logger.warn("Constructor not found - keeping old class loader ");
	}
}
