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
 * 
 * @author Gordon Fraser
 */
package org.evosuite.testcase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evosuite.TestGenerationContext;
import org.evosuite.setup.TestClusterGenerator;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

public class MethodStatement extends AbstractStatement {

	private static final long serialVersionUID = 6134126797102983073L;

	protected transient Method method;

	protected VariableReference callee;

	protected List<VariableReference> parameters;

	/**
	 * <p>
	 * Constructor for MethodStatement.
	 * </p>
	 * 
	 * @param tc
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param method
	 *            a {@link java.lang.reflect.Method} object.
	 * @param callee
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 * @param type
	 *            a {@link java.lang.reflect.Type} object.
	 * @param parameters
	 *            a {@link java.util.List} object.
	 */
	public MethodStatement(TestCase tc, Method method, VariableReference callee,
	        java.lang.reflect.Type type, List<VariableReference> parameters) {
		super(tc, type);
		assert (Modifier.isStatic(method.getModifiers()) || callee != null);
		assert (parameters != null);
		assert (method.getParameterTypes().length == parameters.size()) : method.getParameterTypes().length
		        + " != " + parameters.size();
		this.method = method;
		if (isStatic())
			this.callee = null;
		else
			this.callee = callee;
		this.parameters = parameters;
	}

	/**
	 * <p>
	 * Getter for the field <code>parameters</code>.
	 * </p>
	 * 
	 * @return a {@link java.util.List} object.
	 */
	public List<VariableReference> getParameters() {
		return this.parameters;
	}

	/**
	 * This constructor allows you to use an already existing VariableReference
	 * as retvar. This should only be done, iff an old statement is replaced
	 * with this statement. And already existing objects should in the future
	 * reference this object.
	 * 
	 * @param tc
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param method
	 *            a {@link java.lang.reflect.Method} object.
	 * @param callee
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 * @param retvar
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 * @param parameters
	 *            a {@link java.util.List} object.
	 */
	public MethodStatement(TestCase tc, Method method, VariableReference callee,
	        VariableReference retvar, List<VariableReference> parameters) {
		super(tc, retvar);
		assert (tc.size() > retvar.getStPosition()); //as an old statement should be replaced by this statement
		assert (Modifier.isStatic(method.getModifiers()) || callee != null);
		assert (parameters != null);
		assert (method.getParameterTypes().length == parameters.size());
		this.method = method;
		if (isStatic())
			this.callee = null;
		else
			this.callee = callee;
		this.parameters = parameters;
	}

	/**
	 * <p>
	 * Getter for the field <code>method</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.reflect.Method} object.
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * <p>
	 * Setter for the field <code>method</code>.
	 * </p>
	 * 
	 * @param method
	 *            a {@link java.lang.reflect.Method} object.
	 */
	public void setMethod(Method method) {
		assert (method.getReturnType().equals(this.method.getReturnType()));
		this.method = method;
	}

	/**
	 * <p>
	 * Getter for the field <code>callee</code>.
	 * </p>
	 * 
	 * @return a {@link org.evosuite.testcase.VariableReference} object.
	 */
	public VariableReference getCallee() {
		return callee;
	}

	/**
	 * <p>
	 * Setter for the field <code>callee</code>.
	 * </p>
	 * 
	 * @param callee
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 */
	public void setCallee(VariableReference callee) {
		if (!isStatic())
			this.callee = callee;
	}

	/**
	 * <p>
	 * isStatic
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isStatic() {
		return Modifier.isStatic(method.getModifiers());
	}

	private boolean isInstanceMethod() {
		return !Modifier.isStatic(method.getModifiers());
	}

	/** {@inheritDoc} */
	@Override
	public Throwable execute(final Scope scope, PrintStream out)
	        throws InvocationTargetException, IllegalArgumentException,
	        IllegalAccessException, InstantiationException {
		logger.trace("Executing method " + method.getName());
		final Object[] inputs = new Object[parameters.size()];
		//PrintStream old_out = System.out;
		//PrintStream old_err = System.err;
		//System.setOut(out);
		//System.setErr(out);

		try {
			return super.exceptionHandler(new Executer() {

				@Override
				public void execute() throws InvocationTargetException,
				        IllegalArgumentException, IllegalAccessException,
				        InstantiationException, CodeUnderTestException {
					Object callee_object;
					try {
						for (int i = 0; i < parameters.size(); i++) {
							inputs[i] = parameters.get(i).getObject(scope);
						}

						callee_object = (Modifier.isStatic(method.getModifiers())) ? null
						        : callee.getObject(scope);
						if (!Modifier.isStatic(method.getModifiers())
						        && callee_object == null) {
							throw new CodeUnderTestException(new NullPointerException());
						}
					} catch (CodeUnderTestException e) {
						throw e;
						// throw CodeUnderTestException.throwException(e.getCause());
					} catch (Throwable e) {
						e.printStackTrace();
						throw new EvosuiteError(e);
					}

					Object ret = method.invoke(callee_object, inputs);

					try {
						retval.setObject(scope, ret);
					} catch (CodeUnderTestException e) {
						throw e;
						// throw CodeUnderTestException.throwException(e);
					} catch (Throwable e) {
						e.printStackTrace();
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
			exceptionThrown = e.getCause();
			//System.setOut(old_out);
			//System.setErr(old_err);
			logger.debug("Exception thrown in method {}: {}", method.getName(),
			             exceptionThrown);
			//} finally {
			//	System.setOut(old_out);
			//	System.setErr(old_err);
		}
		return exceptionThrown;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDeclaredException(Throwable t) {
		for (Class<?> declaredException : method.getExceptionTypes()) {
			if (declaredException.isAssignableFrom(t.getClass()))
				return true;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public StatementInterface copy(TestCase newTestCase, int offset) {
		ArrayList<VariableReference> new_params = new ArrayList<VariableReference>();
		for (VariableReference r : parameters) {
			new_params.add(r.copy(newTestCase, offset));
		}

		MethodStatement m;
		if (isStatic()) {
			// FIXXME: If callee is an array index, this will return an invalid
			// copy of the cloned variable!
			m = new MethodStatement(newTestCase, method, null, retval.getType(),
			        new_params);
		} else {
			VariableReference newCallee = callee.copy(newTestCase, offset);
			m = new MethodStatement(newTestCase, method, newCallee, retval.getType(),
			        new_params);

		}
		if (retval instanceof ArrayReference
		        && !(m.getReturnValue() instanceof ArrayReference)) {
			// logger.info("Copying array retval: " + retval.getGenericClass());
			//	assert (retval.getGenericClass() != null);
			//	assert (retval.getGenericClass().isArray()) : method.toString();
			ArrayReference newRetVal = new ArrayReference(newTestCase,
			        retval.getGenericClass(), ((ArrayReference) retval).getArrayLength());
			m.setRetval(newRetVal);

		}

		// m.assertions = copyAssertions(newTestCase, offset);

		return m;
	}

	/** {@inheritDoc} */
	@Override
	public Set<VariableReference> getVariableReferences() {
		Set<VariableReference> references = new LinkedHashSet<VariableReference>();
		references.add(retval);
		if (isInstanceMethod()) {
			references.add(callee);
			if (callee.getAdditionalVariableReference() != null)
				references.add(callee.getAdditionalVariableReference());
		}
		references.addAll(parameters);
		for (VariableReference param : parameters) {
			if (param.getAdditionalVariableReference() != null)
				references.add(param.getAdditionalVariableReference());
		}
		references.addAll(getAssertionReferences());
		return references;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.StatementInterface#replace(org.evosuite.testcase.VariableReference, org.evosuite.testcase.VariableReference)
	 */
	/** {@inheritDoc} */
	@Override
	public void replace(VariableReference var1, VariableReference var2) {
		if (retval.equals(var1))
			retval = var2;

		if (isInstanceMethod()) {
			if (callee.equals(var1))
				callee = var2;
			else
				callee.replaceAdditionalVariableReference(var1, var2);
		}
		for (int i = 0; i < parameters.size(); i++) {

			if (parameters.get(i).equals(var1))
				parameters.set(i, var2);
			else
				parameters.get(i).replaceAdditionalVariableReference(var1, var2);
		}
	}

	/**
	 * <p>
	 * getParameterReferences
	 * </p>
	 * 
	 * @return a {@link java.util.List} object.
	 */
	public List<VariableReference> getParameterReferences() {
		return parameters;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.StatementInterface#getNumParameters()
	 */
	@Override
	public int getNumParameters() {
		return parameters.size() + (isStatic() ? 0 : 1);
	}

	/**
	 * <p>
	 * replaceParameterReference
	 * </p>
	 * 
	 * @param var
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 * @param numParameter
	 *            a int.
	 */
	public void replaceParameterReference(VariableReference var, int numParameter) {
		assert (numParameter >= 0);
		assert (numParameter < parameters.size());
		parameters.set(numParameter, var);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return method.getName() + Type.getMethodDescriptor(method);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		MethodStatement ms = (MethodStatement) s;
		if (ms.parameters.size() != parameters.size())
			return false;

		if (!this.method.equals(ms.method))
			return false;

		for (int i = 0; i < parameters.size(); i++) {
			if (!parameters.get(i).equals(ms.parameters.get(i)))
				return false;
		}

		if (!retval.equals(ms.retval))
			return false;

		if ((callee == null && ms.callee != null)
		        || (callee != null && ms.callee == null)) {
			return false;
		} else {
			if (callee == null)
				return true;
			else
				return (callee.equals(ms.callee));
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callee == null) ? 0 : callee.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.testcase.Statement#getBytecode(org.objectweb.
	 * asm.commons.GeneratorAdapter)
	 */
	/** {@inheritDoc} */
	@Override
	public void getBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        Throwable exception) {
		Label start = mg.newLabel();
		Label end = mg.newLabel();

		// if(exception != null)
		mg.mark(start);

		if (!isStatic()) {
			callee.loadBytecode(mg, locals);
			if (!method.getDeclaringClass().equals(callee.getVariableClass())) {
				logger.debug("Types don't match - casting!");
				mg.cast(Type.getType(callee.getVariableClass()),
				        Type.getType(method.getDeclaringClass()));
			}
		}
		int num = 0;
		for (VariableReference parameter : parameters) {
			parameter.loadBytecode(mg, locals);
			if (method.getParameterTypes()[num].isPrimitive()) {
				if (parameter.getGenericClass().isWrapperType()) {
					mg.unbox(Type.getType(parameter.getGenericClass().getUnboxedType()));
				} else if (!parameter.getGenericClass().isPrimitive()) {
					Class<?> parameterClass = new GenericClass(
					        method.getParameterTypes()[num]).getBoxedType();
					Type parameterType = Type.getType(parameterClass);
					mg.checkCast(parameterType);
					mg.unbox(Type.getType(method.getParameterTypes()[num]));
				}

				if (!method.getParameterTypes()[num].equals(parameter.getVariableClass())) {
					logger.debug("Types don't match - casting!");
					mg.cast(Type.getType(parameter.getVariableClass()),
					        Type.getType(method.getParameterTypes()[num]));
				}
			} else if (parameter.getVariableClass().isPrimitive()) {
				mg.box(Type.getType(parameter.getVariableClass()));
			}
			num++;
		}
		logger.debug("Invoking method");
		// if(exception != null) {
		//
		// mg.visitTryCatchBlock(start, end, handler,
		// exception.getClass().getName().replace('.', '/'));
		// }
		if (isStatic())
			mg.invokeStatic(Type.getType(method.getDeclaringClass()),
			                org.objectweb.asm.commons.Method.getMethod(method));
		else {
			if (!callee.getVariableClass().isInterface()) {
				mg.invokeVirtual(Type.getType(callee.getVariableClass()),
				                 org.objectweb.asm.commons.Method.getMethod(method));
			} else {
				mg.invokeInterface(Type.getType(callee.getVariableClass()),
				                   org.objectweb.asm.commons.Method.getMethod(method));
			}
		}

		if (!retval.isVoid()) {
			if (!retval.getVariableClass().equals(method.getReturnType())) {
				if (!retval.getVariableClass().isPrimitive()) {
					mg.checkCast(Type.getType(retval.getVariableClass()));
				} else {
					mg.cast(Type.getType(method.getReturnType()),
					        Type.getType(retval.getVariableClass()));
				}
			}
			retval.storeBytecode(mg, locals);
		}

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
	 * @see org.evosuite.testcase.Statement#getDeclaredExceptions()
	 */
	/** {@inheritDoc} */
	@Override
	public Set<Class<?>> getDeclaredExceptions() {
		Set<Class<?>> ex = super.getDeclaredExceptions();
		for (Class<?> t : method.getExceptionTypes())
			ex.add(t);
		return ex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.testcase.Statement#getUniqueVariableReferences()
	 */
	/** {@inheritDoc} */
	@Override
	public List<VariableReference> getUniqueVariableReferences() {
		List<VariableReference> references = new ArrayList<VariableReference>();
		references.add(retval);
		if (isInstanceMethod()) {
			references.add(callee);
			if (callee instanceof ArrayIndex)
				references.add(((ArrayIndex) callee).getArray());
		}
		references.addAll(parameters);
		for (VariableReference param : parameters) {
			if (param instanceof ArrayIndex)
				references.add(((ArrayIndex) param).getArray());
		}
		return references;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.StatementInterface#isValid()
	 */
	/** {@inheritDoc} */
	@Override
	public boolean isValid() {
		assert (super.isValid());
		for (VariableReference v : parameters) {
			v.getStPosition();
		}
		if (!isStatic()) {
			callee.getStPosition();
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean same(StatementInterface s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		MethodStatement ms = (MethodStatement) s;
		if (ms.parameters.size() != parameters.size())
			return false;

		for (int i = 0; i < parameters.size(); i++) {
			if (!parameters.get(i).same(ms.parameters.get(i)))
				return false;
		}

		if (!this.method.equals(ms.method))
			return false;

		if (!retval.same(ms.retval))
			return false;

		if ((callee == null && ms.callee != null)
		        || (callee != null && ms.callee == null)) {
			return false;
		} else {
			if (callee == null)
				return true;
			else
				return (callee.same(ms.callee));
		}
	}

	/** {@inheritDoc} */
	@Override
	public AccessibleObject getAccessibleObject() {
		return method;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAssignmentStatement() {
		return false;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		// Write/save additional fields
		oos.writeObject(method.getDeclaringClass().getName());
		oos.writeObject(method.getName());
		oos.writeObject(Type.getMethodDescriptor(method));
	}

	// assumes "static java.util.Date aDate;" declared
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		ois.defaultReadObject();

		// Read/initialize additional fields
		Class<?> methodClass = TestGenerationContext.getClassLoader().loadClass((String) ois.readObject());

		// TODO: What was the point of this??
		// methodClass = TestCluster.classLoader.loadClass(methodClass.getName());

		String methodName = (String) ois.readObject();
		String methodDesc = (String) ois.readObject();

		for (Method method : methodClass.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				if (Type.getMethodDescriptor(method).equals(methodDesc)) {
					this.method = method;
					return;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.StatementInterface#changeClassLoader(java.lang.ClassLoader)
	 */
	/** {@inheritDoc} */
	@Override
	public void changeClassLoader(ClassLoader loader) {
		try {
			Class<?> oldClass = method.getDeclaringClass();
			Class<?> newClass = loader.loadClass(oldClass.getName());
			for (Method newMethod : TestClusterGenerator.getMethods(newClass)) {
				if (newMethod.getName().equals(this.method.getName())) {
					boolean equals = true;
					Class<?>[] oldParameters = this.method.getParameterTypes();
					Class<?>[] newParameters = newMethod.getParameterTypes();
					if (oldParameters.length != newParameters.length)
						continue;

					for (int i = 0; i < newParameters.length; i++) {
						if (!oldParameters[i].getName().equals(newParameters[i].getName())) {
							equals = false;
							break;
						}
					}
					if (equals) {
						this.method = newMethod;
						this.method.setAccessible(true);
						break;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		} catch (SecurityException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		}
		super.changeClassLoader(loader);
	}
}
