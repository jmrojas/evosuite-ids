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
package de.unisb.cs.st.evosuite.testcase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.gentyref.GenericTypeReflector;

public class GenericClass implements Serializable {

	private static final long serialVersionUID = -3307107227790458308L;

	private static Logger logger = LoggerFactory.getLogger(GenericClass.class);

	public boolean isAssignableTo(Type lhsType) {
		return isAssignable(lhsType, type);
	}

	public boolean isAssignableFrom(Type rhsType) {
		return isAssignable(type, rhsType);
	}

	public boolean isAssignableTo(GenericClass lhsType) {
		return isAssignable(lhsType.type, type);
	}

	public boolean isAssignableFrom(GenericClass rhsType) {
		return isAssignable(type, rhsType.type);
	}

	/**
	 * Return true if variable is an enumeration
	 */
	public boolean isEnum() {
		return raw_class.isEnum();
	}

	/**
	 * Return true if variable is a primitive type
	 */
	public boolean isPrimitive() {
		return raw_class.isPrimitive();
	}

	public boolean isString() {
		return raw_class.equals(String.class);
	}

	/**
	 * Return true if variable is void
	 */
	public boolean isVoid() {
		return raw_class.equals(Void.class) || raw_class.equals(void.class);
	}

	/**
	 * Return true if variable is an array
	 */
	public boolean isArray() {
		return raw_class.isArray();
	}

	public Type getComponentType() {
		return raw_class.getComponentType();
	}

	public Type getComponentClass() {
		return GenericTypeReflector.erase(raw_class.getComponentType());
	}

	/**
	 * Set of wrapper classes
	 */
	@SuppressWarnings("unchecked")
	private static final Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>(
	        Arrays.asList(Boolean.class, Character.class, Byte.class, Short.class,
	                      Integer.class, Long.class, Float.class, Double.class,
	                      Void.class));

	/**
	 * Return true if type of variable is a primitive wrapper
	 */
	public boolean isWrapperType() {
		return WRAPPER_TYPES.contains(raw_class);
	}

	public static boolean isSubclass(Type superclass, Type subclass) {
		List<Class<?>> superclasses = ClassUtils.getAllSuperclasses((Class<?>) subclass);
		List<Class<?>> interfaces = ClassUtils.getAllInterfaces((Class<?>) subclass);
		if (superclasses.contains(superclass) || interfaces.contains(superclass)) {
			return true;
		}

		return false;
	}

	public static boolean isAssignable(Type lhsType, Type rhsType) {
		if (lhsType.equals(rhsType)) {
			//logger.info("Classes are identical: "+lhsType+" / "+rhsType);
			return true;
		}

		if (lhsType instanceof Class<?> && rhsType instanceof Class<?>) {
			//if(ClassUtils.isAssignable((Class<?>) rhsType, (Class<?>) lhsType)) {
			//	logger.info("Classes are assignable: "+lhsType+" / "+rhsType);
			//}
			// Only allow void to void assignments
			if (((Class<?>) rhsType).equals(void.class)
			        || ((Class<?>) lhsType).equals(void.class))
				return false;

			//			return ClassUtils.isAssignable((Class<?>) rhsType, (Class<?>) lhsType);
			return ((Class<?>) lhsType).isAssignableFrom((Class<?>) rhsType);
		}

		//	if(lhsType instanceof ParameterizedType && rhsType instanceof ParameterizedType) {
		//		return isAssignable((ParameterizedType) lhsType, (ParameterizedType) rhsType);
		//	}
		if (lhsType instanceof TypeVariable<?>) {
			return isAssignable(Integer.TYPE, rhsType);
		}
		if (rhsType instanceof TypeVariable<?>) {
			return isAssignable(lhsType, Integer.TYPE);
		}
		if (rhsType instanceof ParameterizedType) {
			return isAssignable(lhsType, ((ParameterizedType) rhsType).getRawType());
		}
		if (lhsType instanceof ParameterizedType) {
			return isAssignable(((ParameterizedType) lhsType).getRawType(), rhsType);
		}
		if (lhsType instanceof WildcardType) {
			return isAssignable((WildcardType) lhsType, rhsType);
		}
		//if(rhsType instanceof WildcardType) {
		//	return isAssignable(lhsType, (WildcardType) rhsType);
		//}
		if (lhsType instanceof GenericArrayType && rhsType instanceof GenericArrayType) {
			//logger.warn("Checking generic array 1 "+lhsType+"/"+rhsType);
			return isAssignable(((GenericArrayType) lhsType).getGenericComponentType(),
			                    ((GenericArrayType) rhsType).getGenericComponentType());
		}
		if (lhsType instanceof Class<?> && ((Class<?>) lhsType).isArray()
		        && rhsType instanceof GenericArrayType) {
			//logger.warn("Checking generic array 2 "+lhsType+"/"+rhsType);
			return isAssignable(((Class<?>) lhsType).getComponentType(),
			                    ((GenericArrayType) rhsType).getGenericComponentType());
		}
		if (rhsType instanceof Class<?> && ((Class<?>) rhsType).isArray()
		        && lhsType instanceof GenericArrayType) {
			//logger.warn("Checking generic array 3 "+lhsType+"/"+rhsType);
			return isAssignable(((GenericArrayType) lhsType).getGenericComponentType(),
			                    ((Class<?>) rhsType).getComponentType());
		}
		/*
		String message = "Not assignable: ";
		if (lhsType instanceof Class<?>)
			message += "Class ";
		else if (lhsType instanceof ParameterizedType)
			message += "ParameterizedType ";
		else if (lhsType instanceof WildcardType)
			message += "WildcardType ";
		else if (lhsType instanceof GenericArrayType)
			message += "GenericArrayType ";
		else if (lhsType instanceof TypeVariable<?>)
			message += "TypeVariable ";
		else
			message += "Unknown type ";
		message += lhsType;
		message += " / ";
		if (rhsType instanceof Class<?>)
			message += "Class ";
		else if (rhsType instanceof ParameterizedType)
			message += "ParameterizedType ";
		else if (rhsType instanceof WildcardType)
			message += "WildcardType ";
		else if (rhsType instanceof GenericArrayType)
			message += "GenericArrayType ";
		else if (rhsType instanceof TypeVariable<?>)
			message += "TypeVariable ";
		else
			message += "Unknown type ";
		message += rhsType;
		logger.warn(message);
		 */

		//Thread.dumpStack();
		return false;
	}

	private static boolean isAssignable(WildcardType lhsType, Type rhsType) {
		/*
		Type[] upperBounds = lhsType.getUpperBounds();
		Type[] lowerBounds = lhsType.getLowerBounds();
		for (int size = upperBounds.length, i = 0; i < size; ++i) {
			if (!isAssignable(upperBounds[i], rhsType)) {
				return false;
			}
		}
		for (int size = lowerBounds.length, i = 0; i < size; ++i) {
			if (!isAssignable(rhsType, lowerBounds[i])) {
				return false;
			}
		}
		*/
		return true;
	}

	transient Class<?> raw_class = null;
	transient Type type = null;

	public GenericClass(Type type) {
		this.type = type;
		this.raw_class = GenericTypeReflector.erase(type);
	}

	public Class<?> getRawClass() {
		return raw_class;
	}

	public Type getType() {
		return type;
	}

	public String getTypeName() {
		return GenericTypeReflector.getTypeName(type);
	}

	public String getComponentName() {
		return raw_class.getComponentType().getSimpleName();
	}

	public String getClassName() {
		return raw_class.getName();
	}

	private static List<String> primitiveClasses = Arrays.asList("char", "int", "short",
	                                                             "long", "boolean",
	                                                             "float", "double",
	                                                             "byte");

	public String getSimpleName() {
		// return raw_class.getSimpleName();
		String name = ClassUtils.getShortClassName(raw_class).replace(";", "[]");
		if (!isPrimitive() && primitiveClasses.contains(name))
			return raw_class.getSimpleName().replace(";", "[]");

		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result
		//		+ ((raw_class == null) ? 0 : raw_class.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericClass other = (GenericClass) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeObject(raw_class.getName());
	}

	private static Class<?> getClass(String name) throws ClassNotFoundException {
		if (name.equals("void"))
			return void.class;
		else if (name.equals("int") || name.equals("I"))
			return int.class;
		else if (name.equals("short") || name.equals("S"))
			return short.class;
		else if (name.equals("long") || name.equals("J"))
			return long.class;
		else if (name.equals("float") || name.equals("F"))
			return float.class;
		else if (name.equals("double") || name.equals("D"))
			return double.class;
		else if (name.equals("boolean") || name.equals("Z"))
			return boolean.class;
		else if (name.equals("byte") || name.equals("B"))
			return byte.class;
		else if (name.equals("char") || name.equals("C"))
			return char.class;
		else if (name.startsWith("[")) {
			Class<?> componentType = getClass(name.substring(1, name.length()));
			Object array = Array.newInstance(componentType, 0);
			return array.getClass();
		} else if (name.startsWith("L")) {
			return getClass(name.substring(1));
		} else if (name.endsWith(".class")) {
			return getClass(name.replace(".class", ""));
		} else if (name.equals("java.lang.String;")) {
			// TODO: This is a workaround and the bug should be fixed
			return getClass("java.lang.String");
		} else
			return TestCluster.classLoader.loadClass(name);
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		String name = (String) ois.readObject();
		this.raw_class = getClass(name);
		// TODO: Currently, type information gets lost by serialization
		this.type = raw_class;

	}

	public void changeClassLoader(ClassLoader loader) {
		try {
			raw_class = getClass(raw_class.getName());
			this.type = raw_class;
		} catch (ClassNotFoundException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		} catch (SecurityException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		}
	}

}
