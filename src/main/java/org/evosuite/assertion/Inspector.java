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
package org.evosuite.assertion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.evosuite.TestGenerationContext;
import org.objectweb.asm.Type;

public class Inspector implements Serializable {

	private static final long serialVersionUID = -6865880297202184953L;

	private transient Class<?> clazz;

	private transient Method method;

	/**
	 * <p>
	 * Constructor for Inspector.
	 * </p>
	 * 
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param m
	 *            a {@link java.lang.reflect.Method} object.
	 */
	public Inspector(Class<?> clazz, Method m) {
		this.clazz = clazz;
		method = m;
	}

	/**
	 * <p>
	 * getValue
	 * </p>
	 * 
	 * @param object
	 *            a {@link java.lang.Object} object.
	 * @return a {@link java.lang.Object} object.
	 * @throws java.lang.IllegalArgumentException
	 *             if any.
	 * @throws java.lang.IllegalAccessException
	 *             if any.
	 * @throws java.lang.reflect.InvocationTargetException
	 *             if any.
	 */
	public Object getValue(Object object) throws IllegalArgumentException,
	        IllegalAccessException, InvocationTargetException {

		Object ret = this.method.invoke(object);
		//if (ret instanceof String) {
		//	ret = ((String) ret).replaceAll("@[abcdef\\d]+", "");
		//}
		return ret;
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
	 * getMethodCall
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getMethodCall() {
		return method.getName();
	}

	/**
	 * <p>
	 * getClassName
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getClassName() {
		return clazz.getName();
	}

	/**
	 * <p>
	 * getReturnType
	 * </p>
	 * 
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inspector other = (Inspector) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		// Write/save additional fields
		oos.writeObject(clazz.getName());
		oos.writeObject(method.getDeclaringClass().getName());
		oos.writeObject(method.getName());
		oos.writeObject(Type.getMethodDescriptor(method));
	}

	// assumes "static java.util.Date aDate;" declared
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		ois.defaultReadObject();

		// Read/initialize additional fields
		this.clazz = TestGenerationContext.getClassLoader().loadClass((String) ois.readObject());
		Class<?> methodClass = TestGenerationContext.getClassLoader().loadClass((String) ois.readObject());

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
}
