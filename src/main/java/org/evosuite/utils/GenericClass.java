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
package org.evosuite.utils;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.evosuite.TestGenerationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.gentyref.CaptureType;
import com.googlecode.gentyref.GenericTypeReflector;

public class GenericClass implements Serializable {

	private static final long serialVersionUID = -3307107227790458308L;

	private static final Logger logger = LoggerFactory.getLogger(GenericClass.class);

	/**
	 * <p>
	 * isAssignableTo
	 * </p>
	 * 
	 * @param lhsType
	 *            a {@link java.lang.reflect.Type} object.
	 * @return a boolean.
	 */
	public boolean isAssignableTo(Type lhsType) {
		return isAssignable(lhsType, type);
	}

	/**
	 * <p>
	 * isAssignableFrom
	 * </p>
	 * 
	 * @param rhsType
	 *            a {@link java.lang.reflect.Type} object.
	 * @return a boolean.
	 */
	public boolean isAssignableFrom(Type rhsType) {
		return isAssignable(type, rhsType);
	}

	/**
	 * <p>
	 * isAssignableTo
	 * </p>
	 * 
	 * @param lhsType
	 *            a {@link org.evosuite.utils.GenericClass} object.
	 * @return a boolean.
	 */
	public boolean isAssignableTo(GenericClass lhsType) {
		return isAssignable(lhsType.type, type);
	}

	/**
	 * <p>
	 * isAssignableFrom
	 * </p>
	 * 
	 * @param rhsType
	 *            a {@link org.evosuite.utils.GenericClass} object.
	 * @return a boolean.
	 */
	public boolean isAssignableFrom(GenericClass rhsType) {
		return isAssignable(type, rhsType.type);
	}

	/**
	 * Return true if variable is an enumeration
	 * 
	 * @return a boolean.
	 */
	public boolean isEnum() {
		return raw_class.isEnum();
	}

	/**
	 * Return true if variable is a primitive type
	 * 
	 * @return a boolean.
	 */
	public boolean isPrimitive() {
		return raw_class.isPrimitive();
	}

	/**
	 * <p>
	 * isString
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isString() {
		return raw_class.equals(String.class);
	}

	public boolean isClass() {
		return raw_class.equals(Class.class);
	}

	/**
	 * Return true if variable is void
	 * 
	 * @return a boolean.
	 */
	public boolean isVoid() {
		return raw_class.equals(Void.class) || raw_class.equals(void.class);
	}

	/**
	 * Return true if variable is an array
	 * 
	 * @return a boolean.
	 */
	public boolean isArray() {
		return raw_class.isArray();
	}

	public boolean isObject() {
		return raw_class.equals(Object.class);
	}

	/**
	 * <p>
	 * getComponentType
	 * </p>
	 * 
	 * @return a {@link java.lang.reflect.Type} object.
	 */
	public Type getComponentType() {
		return GenericTypeReflector.getArrayComponentType(type);
	}

	/**
	 * <p>
	 * getComponentClass
	 * </p>
	 * 
	 * @return a {@link java.lang.reflect.Type} object.
	 */
	public Type getRawComponentClass() {
		return GenericTypeReflector.erase(raw_class.getComponentType());
	}

	public GenericClass getComponentClass() {
		if (type instanceof GenericArrayType) {
			GenericArrayType arrayType = (GenericArrayType) type;
			Type componentType = arrayType.getGenericComponentType();
			Class<?> rawComponentType = raw_class.getComponentType();
			return new GenericClass(componentType, rawComponentType);
		} else {
			return new GenericClass(raw_class.getComponentType());
		}
	}

	public GenericClass getWithComponentClass(GenericClass componentClass) {
		if (type instanceof GenericArrayType) {
			return new GenericClass(
			        GenericArrayTypeImpl.createArrayType(componentClass.getType()),
			        raw_class);
		} else {
			return new GenericClass(type, raw_class);
		}
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
	 * 
	 * @return a boolean.
	 */
	public boolean isWrapperType() {
		return WRAPPER_TYPES.contains(raw_class);
	}

	public Class<?> getUnboxedType() {
		if (isWrapperType()) {
			if (raw_class.equals(Integer.class))
				return int.class;
			else if (raw_class.equals(Byte.class))
				return byte.class;
			else if (raw_class.equals(Short.class))
				return short.class;
			else if (raw_class.equals(Long.class))
				return long.class;
			else if (raw_class.equals(Float.class))
				return float.class;
			else if (raw_class.equals(Double.class))
				return double.class;
			else if (raw_class.equals(Character.class))
				return char.class;
			else if (raw_class.equals(Boolean.class))
				return boolean.class;
			else if (raw_class.equals(Void.class))
				return void.class;
			else
				throw new RuntimeException("Unknown boxed type: " + raw_class);
		}
		return raw_class;
	}

	public Class<?> getBoxedType() {
		if (isPrimitive()) {
			if (raw_class.equals(int.class))
				return Integer.class;
			else if (raw_class.equals(byte.class))
				return Byte.class;
			else if (raw_class.equals(short.class))
				return Short.class;
			else if (raw_class.equals(long.class))
				return Long.class;
			else if (raw_class.equals(float.class))
				return Float.class;
			else if (raw_class.equals(double.class))
				return Double.class;
			else if (raw_class.equals(char.class))
				return Character.class;
			else if (raw_class.equals(boolean.class))
				return Boolean.class;
			else if (raw_class.equals(void.class))
				return Void.class;
			else
				throw new RuntimeException("Unknown unboxed type: " + raw_class);
		}
		return raw_class;
	}

	/**
	 * <p>
	 * isSubclass
	 * </p>
	 * 
	 * @param superclass
	 *            a {@link java.lang.reflect.Type} object.
	 * @param subclass
	 *            a {@link java.lang.reflect.Type} object.
	 * @return a boolean.
	 */
	public static boolean isSubclass(Type superclass, Type subclass) {
		List<Class<?>> superclasses = ClassUtils.getAllSuperclasses((Class<?>) subclass);
		List<Class<?>> interfaces = ClassUtils.getAllInterfaces((Class<?>) subclass);
		if (superclasses.contains(superclass) || interfaces.contains(superclass)) {
			return true;
		}

		return false;
	}

	public boolean isParameterizedType() {
		return type instanceof ParameterizedType;
	}

	public GenericClass getOwnerType() {
		return new GenericClass(((ParameterizedType) type).getOwnerType());
	}

	public boolean hasOwnerType() {
		if (type instanceof ParameterizedType)
			return ((ParameterizedType) type).getOwnerType() != null;
		else
			return false;
	}

	public GenericClass getWithOwnerType(GenericClass ownerClass) {
		if (type instanceof ParameterizedType) {
			ParameterizedType currentType = (ParameterizedType) type;
			return new GenericClass(new ParameterizedTypeImpl(raw_class,
			        currentType.getActualTypeArguments(), ownerClass.getType()));
		}

		return new GenericClass(type);
	}

	public boolean hasWildcardOrTypeVariables() {
		if (hasWildcardTypes())
			return true;
		if (hasTypeVariables())
			return true;

		if (hasOwnerType()) {
			if (getOwnerType().hasWildcardOrTypeVariables())
				return true;
		}

		if (type instanceof GenericArrayType) {
			if (getComponentClass().hasWildcardOrTypeVariables())
				return true;
		}

		return false;
	}

	public boolean hasWildcardTypes() {
		for (Type t : getParameterTypes()) {
			if (t instanceof WildcardType)
				return true;
		}

		return false;
	}

	public boolean hasTypeVariables() {
		for (Type type : getParameterTypes()) {
			if (type instanceof TypeVariable)
				return true;
		}

		return false;
	}

	public boolean isRawClass() {
		return type instanceof Class<?>;
	}

	public GenericClass addWildcardTypes() {
		return new GenericClass(GenericTypeReflector.addWildcardParameters(raw_class));
	}

	public List<Type> getParameterTypes() {
		if (type instanceof ParameterizedType) {
			return Arrays.asList(((ParameterizedType) type).getActualTypeArguments());
		}
		return new ArrayList<Type>();
	}

	public List<TypeVariable<?>> getTypeVariables() {
		if (type instanceof ParameterizedType) {
			List<TypeVariable<?>> typeVariables = new ArrayList<TypeVariable<?>>();
			typeVariables.addAll(Arrays.asList(raw_class.getTypeParameters()));
			return typeVariables;
		}
		return new ArrayList<TypeVariable<?>>();
	}

	public int getNumParameters() {
		if (type instanceof ParameterizedType) {
			return Arrays.asList(((ParameterizedType) type).getActualTypeArguments()).size();
		}
		return 0;
	}

	public GenericClass getWithGenericParameterTypes(List<GenericClass> parameters) {
		Type[] typeArray = new Type[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			typeArray[i] = parameters.get(i).getType();
		}
		Type ownerType = null;
		if (type instanceof ParameterizedType) {
			ownerType = ((ParameterizedType) type).getOwnerType();
		}

		return new GenericClass(
		        new ParameterizedTypeImpl(raw_class, typeArray, ownerType));
	}

	public GenericClass getWithParameterTypes(List<Type> parameters) {
		Type[] typeArray = new Type[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			typeArray[i] = parameters.get(i);
		}
		Type ownerType = null;
		if (type instanceof ParameterizedType) {
			ownerType = ((ParameterizedType) type).getOwnerType();
		}
		return new GenericClass(
		        new ParameterizedTypeImpl(raw_class, typeArray, ownerType));
	}

	public GenericClass getWithParameterTypes(Type[] parameters) {
		Type ownerType = null;
		if (type instanceof ParameterizedType) {
			ownerType = ((ParameterizedType) type).getOwnerType();
		}
		return new GenericClass(new ParameterizedTypeImpl(raw_class, parameters,
		        ownerType));
	}

	public GenericClass getRawGenericClass() {
		return new GenericClass(raw_class);
	}

	/**
	 * <p>
	 * isAssignable
	 * </p>
	 * 
	 * @param lhsType
	 *            a {@link java.lang.reflect.Type} object.
	 * @param rhsType
	 *            a {@link java.lang.reflect.Type} object.
	 * @return a boolean.
	 */
	public static boolean isAssignable(Type lhsType, Type rhsType) {
		if (lhsType.equals(rhsType)) {
			return true;
		}

		if (lhsType instanceof Class<?> && rhsType instanceof Class<?>) {
			// Only allow void to void assignments
			if (((Class<?>) rhsType).equals(void.class)
			        || ((Class<?>) lhsType).equals(void.class))
				return false;
			return ClassUtils.isAssignable((Class<?>) rhsType, (Class<?>) lhsType);
		}

		if (lhsType instanceof TypeVariable<?>) {
			if (((TypeVariable<?>) lhsType).getBounds().length == 0)
				return isAssignable(Object.class, rhsType);
			return isAssignable(((TypeVariable<?>) lhsType).getBounds()[0], rhsType);
		}
		if (rhsType instanceof TypeVariable<?>) {
			if (((TypeVariable<?>) rhsType).getBounds().length == 0)
				return isAssignable(lhsType, Object.class);
			return isAssignable(lhsType, ((TypeVariable<?>) rhsType).getBounds()[0]);
		}
		if (lhsType instanceof GenericArrayType || rhsType instanceof GenericArrayType) {
			Type lhsComponentType = GenericTypeReflector.getArrayComponentType(lhsType);
			Type rhsComponentType = GenericTypeReflector.getArrayComponentType(rhsType);
			if (lhsComponentType == null || rhsComponentType == null)
				return false;
			else
				return isAssignable(lhsComponentType, rhsComponentType);
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
		if (rhsType instanceof ParameterizedType) {
			try {
				return TypeUtils.isAssignable(rhsType, lhsType);
			} catch (IllegalStateException e) {
				logger.debug("rhs is parameterized type " + rhsType
				        + " and we got an illegal state");
				logger.debug("lhs is: " + lhsType);
				if (lhsType instanceof ParameterizedType)
					return isAssignable(((ParameterizedType) lhsType).getRawType(),
					                    rhsType);
				else if (lhsType instanceof CaptureType)
					return isAssignable(Object.class, rhsType);
				else
					return TypeUtils.isAssignable(rhsType, lhsType);
			}

			// return GenericTypeReflector.isSuperType(lhsType, rhsType);
			// return isAssignable(lhsType, ((ParameterizedType) rhsType).getRawType());
		}
		if (lhsType instanceof ParameterizedType) {
			try {
				return TypeUtils.isAssignable(rhsType, lhsType);
			} catch (IllegalStateException e) {
				logger.debug("lhs is parameterized type " + rhsType
				        + " and we got an illegal state");
				return isAssignable(((ParameterizedType) lhsType).getRawType(), rhsType);
			}
			// return GenericTypeReflector.isSuperType(lhsType, rhsType);
			//return isAssignable(((ParameterizedType) lhsType).getRawType(), rhsType);
		}
		if (lhsType instanceof WildcardType) {
			return isAssignable((WildcardType) lhsType, rhsType);
		}
		if (rhsType instanceof WildcardType) {
			return TypeUtils.isAssignable(rhsType, lhsType);
		}
		//if(rhsType instanceof WildcardType) {
		//	return isAssignable(lhsType, (WildcardType) rhsType);
		//}
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
		return TypeUtils.isAssignable(rhsType, lhsType);
		// TODO - what should go here?

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
		//return true;
	}

	transient Class<?> raw_class = null;
	transient Type type = null;

	/**
	 * Generate a generic class by from a type
	 * 
	 * @param type
	 *            a {@link java.lang.reflect.Type} object.
	 */
	public GenericClass(Type type) {
		if (type instanceof Class<?>) {
			this.type = GenericTypeReflector.addWildcardParameters((Class<?>) type);
			this.raw_class = (Class<?>) type;
		} else {
			if (!handleGenericArraySpecialCase(type)) {
				this.type = type;
				try {
					this.raw_class = erase(type);
				} catch (RuntimeException e) {
					// If there is an unresolved capture type in here
					// we delete it and replace with a wildcard

				}
			}
		}
	}

	/**
	 * Returns the erasure of the given type.
	 */
	private static Class<?> erase(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) type).getRawType();
		} else if (type instanceof TypeVariable) {
			TypeVariable<?> tv = (TypeVariable<?>) type;
			if (tv.getBounds().length == 0)
				return Object.class;
			else
				return erase(tv.getBounds()[0]);
		} else if (type instanceof GenericArrayType) {
			GenericArrayType aType = (GenericArrayType) type;
			return GenericArrayTypeImpl.createArrayType(erase(aType.getGenericComponentType()));
		} else if (type instanceof CaptureType) {
			CaptureType captureType = (CaptureType) type;
			if (captureType.getUpperBounds().length == 0)
				return Object.class;
			else
				return erase(captureType.getUpperBounds()[0]);
		} else {
			// TODO at least support CaptureType here
			throw new RuntimeException("not supported: " + type.getClass());
		}
	}

	/**
	 * Generate a generic class by setting all generic parameters to the unbound
	 * wildcard ("?")
	 * 
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 */
	public GenericClass(Class<?> clazz) {
		this.type = GenericTypeReflector.addWildcardParameters(clazz);
		this.raw_class = clazz;
	}

	public GenericClass(Type type, Class<?> clazz) {
		this.type = type;
		this.raw_class = clazz;
		handleGenericArraySpecialCase(type);
	}

	public GenericClass(GenericClass copy) {
		this.type = copy.type;
		this.raw_class = copy.raw_class;
	}

	private boolean handleGenericArraySpecialCase(Type type) {
		if (type instanceof GenericArrayType) {
			// There is some weird problem with generic methods and the component type can be null
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			if (componentType == null) {
				this.raw_class = Object[].class;
				this.type = this.raw_class;
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * getRawClass
	 * </p>
	 * 
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<?> getRawClass() {
		return raw_class;
	}

	/**
	 * <p>
	 * Getter for the field <code>type</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.reflect.Type} object.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * <p>
	 * getTypeName
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getTypeName() {
		return GenericTypeReflector.getTypeName(type);
	}

	/**
	 * <p>
	 * getComponentName
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getComponentName() {
		return raw_class.getComponentType().getSimpleName();
	}

	/**
	 * <p>
	 * getClassName
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getClassName() {
		return raw_class.getName();
	}

	private static List<String> primitiveClasses = Arrays.asList("char", "int", "short",
	                                                             "long", "boolean",
	                                                             "float", "double",
	                                                             "byte");

	/**
	 * <p>
	 * getSimpleName
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getSimpleName() {
		// return raw_class.getSimpleName();
		String name = ClassUtils.getShortClassName(raw_class).replace(";", "[]");
		if (!isPrimitive() && primitiveClasses.contains(name))
			return raw_class.getSimpleName().replace(";", "[]");

		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		if (type == null) {
			LoggingUtils.getEvoLogger().info("Type is null for raw class " + raw_class);
			for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
				LoggingUtils.getEvoLogger().info(elem.toString());
			}
			assert (false);
		}
		return type.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getTypeName().hashCode();
		//result = prime * result + ((raw_class == null) ? 0 : raw_class.hashCode());
		//result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericClass other = (GenericClass) obj;
		//return type.equals(other.type);
		return getTypeName().equals(other.getTypeName());
		/*
		if (raw_class == null) {
			if (other.raw_class != null)
				return false;
		} else if (!raw_class.equals(other.raw_class))
			return false;
			*/
		/*
		if (type == null) {
		    if (other.type != null)
			    return false;
		} else if (!type.equals(other.type))
		    return false;
		    */
		// return true;
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
		} else if (name.endsWith(";")) {
			return getClass(name.substring(0, name.length() - 1));
		} else if (name.endsWith(".class")) {
			return getClass(name.replace(".class", ""));
		} else
			return TestGenerationContext.getClassLoader().loadClass(name);
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeObject(raw_class.getName());
		if (type instanceof ParameterizedType) {
			oos.writeObject(Boolean.TRUE);
			ParameterizedType pt = (ParameterizedType) type;
			oos.writeObject(new GenericClass(pt.getRawType()));
			oos.writeObject(new GenericClass(pt.getOwnerType()));
			List<GenericClass> parameterClasses = new ArrayList<GenericClass>();
			for (Type parameterType : pt.getActualTypeArguments()) {
				parameterClasses.add(new GenericClass(parameterType));
			}
			oos.writeObject(parameterClasses);
		} else {
			oos.writeObject(Boolean.FALSE);
		}
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		String name = (String) ois.readObject();
		this.raw_class = getClass(name);

		Boolean isParameterized = (Boolean) ois.readObject();
		if (isParameterized) {
			// GenericClass rawType = (GenericClass) ois.readObject();
			GenericClass ownerType = (GenericClass) ois.readObject();
			@SuppressWarnings("unchecked")
			List<GenericClass> parameterClasses = (List<GenericClass>) ois.readObject();
			Type[] parameterTypes = new Type[parameterClasses.size()];
			for (int i = 0; i < parameterClasses.size(); i++)
				parameterTypes[i] = parameterClasses.get(i).getType();
			this.type = new ParameterizedTypeImpl(raw_class, parameterTypes,
			        ownerType.getType());
		} else {
			this.type = GenericTypeReflector.addWildcardParameters(raw_class);
		}
	}

	/**
	 * <p>
	 * changeClassLoader
	 * </p>
	 * 
	 * @param loader
	 *            a {@link java.lang.ClassLoader} object.
	 */
	public void changeClassLoader(ClassLoader loader) {
		try {
			raw_class = getClass(raw_class.getName());
			if (type instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) type;
				// GenericClass rawType = new GenericClass(pt.getRawType());
				// rawType.changeClassLoader(loader);
				GenericClass ownerType = null;
				if (pt.getOwnerType() != null) {
					ownerType = new GenericClass(pt.getOwnerType());
					ownerType.changeClassLoader(loader);
				}
				List<GenericClass> parameterClasses = new ArrayList<GenericClass>();
				boolean hasWildcard = false;
				for (Type parameterType : pt.getActualTypeArguments()) {
					if (parameterType instanceof WildcardType) {
						hasWildcard = true;
						break;
					}
					GenericClass parameter = new GenericClass(parameterType);
					parameter.changeClassLoader(loader);
					parameterClasses.add(parameter);
				}
				if (hasWildcard) {
					this.type = GenericTypeReflector.addWildcardParameters(raw_class);
				} else {
					Type[] parameterTypes = new Type[parameterClasses.size()];
					for (int i = 0; i < parameterClasses.size(); i++)
						parameterTypes[i] = parameterClasses.get(i).getType();
					this.type = new ParameterizedTypeImpl(raw_class, parameterTypes,
					        ownerType != null ? ownerType.getType() : null);
				}
			} else if (type instanceof GenericArrayType) {
				GenericClass componentClass = getComponentClass();
				componentClass.changeClassLoader(loader);
				this.type = GenericArrayTypeImpl.createArrayType(componentClass.getType());
			} else {
				this.type = GenericTypeReflector.addWildcardParameters(raw_class);
			}
		} catch (ClassNotFoundException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		} catch (SecurityException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		}
	}

}
