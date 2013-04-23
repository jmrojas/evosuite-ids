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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	 * Set of wrapper classes
	 */
	@SuppressWarnings("unchecked")
	private static final Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>(
	        Arrays.asList(Boolean.class, Character.class, Byte.class, Short.class,
	                      Integer.class, Long.class, Float.class, Double.class,
	                      Void.class));

	private static List<String> primitiveClasses = Arrays.asList("char", "int", "short",
	                                                             "long", "boolean",
	                                                             "float", "double",
	                                                             "byte");

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

	private static Class<?> getClass(String name) throws ClassNotFoundException {
		return getClass(name, TestGenerationContext.getClassLoader());
	}

	private static Class<?> getClass(String name, ClassLoader loader)
	        throws ClassNotFoundException {
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
			Class<?> componentType = getClass(name.substring(1, name.length()), loader);
			Object array = Array.newInstance(componentType, 0);
			return array.getClass();
		} else if (name.startsWith("L")) {
			return getClass(name.substring(1), loader);
		} else if (name.endsWith(";")) {
			return getClass(name.substring(0, name.length() - 1), loader);
		} else if (name.endsWith(".class")) {
			return getClass(name.replace(".class", ""), loader);
		} else
			return loader.loadClass(name);
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
		try {
			return TypeUtils.isAssignable(rhsType, lhsType);
		} catch (IllegalStateException e) {
			logger.debug("Found unassignable type: " + e);
			return false;
		}
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

	transient Class<?> raw_class = null;

	transient Type type = null;

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

	public GenericClass(GenericClass copy) {
		this.type = copy.type;
		this.raw_class = copy.raw_class;
	}

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

	public GenericClass(Type type, Class<?> clazz) {
		this.type = type;
		this.raw_class = clazz;
		handleGenericArraySpecialCase(type);
	}

	public GenericClass addWildcardTypes() {
		return new GenericClass(GenericTypeReflector.addWildcardParameters(raw_class));
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
			raw_class = getClass(raw_class.getName(), loader);
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
			logger.warn("Class not found: " + raw_class + " - keeping old class loader ",
			            e);
		} catch (SecurityException e) {
			logger.warn("Class not found: " + raw_class + " - keeping old class loader ",
			            e);
		}
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
	 * getClassName
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getClassName() {
		return raw_class.getName();
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
	 * getComponentType
	 * </p>
	 * 
	 * @return a {@link java.lang.reflect.Type} object.
	 */
	public Type getComponentType() {
		return GenericTypeReflector.getArrayComponentType(type);
	}

	public int getNumParameters() {
		if (type instanceof ParameterizedType) {
			return Arrays.asList(((ParameterizedType) type).getActualTypeArguments()).size();
		}
		return 0;
	}

	public GenericClass getOwnerType() {
		return new GenericClass(((ParameterizedType) type).getOwnerType());
	}

	public List<Type> getParameterTypes() {
		if (type instanceof ParameterizedType) {
			return Arrays.asList(((ParameterizedType) type).getActualTypeArguments());
		}
		return new ArrayList<Type>();
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
	 * getComponentClass
	 * </p>
	 * 
	 * @return a {@link java.lang.reflect.Type} object.
	 */
	public Type getRawComponentClass() {
		return GenericTypeReflector.erase(raw_class.getComponentType());
	}

	public GenericClass getRawGenericClass() {
		return new GenericClass(raw_class);
	}

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

	public Map<TypeVariable<?>, Type> getTypeVariableMap() {
		List<TypeVariable<?>> typeVariables = getTypeVariables();
		List<Type> types = getParameterTypes();
		Map<TypeVariable<?>, Type> typeMap = new HashMap<TypeVariable<?>, Type>();
		for (int i = 0; i < typeVariables.size(); i++) {
			typeMap.put(typeVariables.get(i), types.get(i));
		}
		return typeMap;
	}

	public List<TypeVariable<?>> getTypeVariables() {
		if (type instanceof ParameterizedType) {
			List<TypeVariable<?>> typeVariables = new ArrayList<TypeVariable<?>>();
			typeVariables.addAll(Arrays.asList(raw_class.getTypeParameters()));
			return typeVariables;
		}
		return new ArrayList<TypeVariable<?>>();
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

	public GenericClass getWithComponentClass(GenericClass componentClass) {
		if (type instanceof GenericArrayType) {
			return new GenericClass(
			        GenericArrayTypeImpl.createArrayType(componentClass.getType()),
			        raw_class);
		} else {
			return new GenericClass(type, raw_class);
		}
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

	public GenericClass getWithOwnerType(GenericClass ownerClass) {
		if (type instanceof ParameterizedType) {
			ParameterizedType currentType = (ParameterizedType) type;
			return new GenericClass(new ParameterizedTypeImpl(raw_class,
			        currentType.getActualTypeArguments(), ownerClass.getType()));
		}

		return new GenericClass(type);
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

	public boolean hasOwnerType() {
		if (type instanceof ParameterizedType)
			return ((ParameterizedType) type).getOwnerType() != null;
		else
			return false;
	}

	public boolean hasTypeVariables() {
		for (Type type : getParameterTypes()) {
			if (type instanceof TypeVariable)
				return true;
		}

		return false;
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

	/**
	 * Return true if variable is an array
	 * 
	 * @return a boolean.
	 */
	public boolean isArray() {
		return raw_class.isArray();
	}

	public boolean isGenericArray() {
		GenericClass componentClass = new GenericClass(raw_class.getComponentType());
		return componentClass.hasWildcardOrTypeVariables();
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

	public boolean isGenericSuperTypeOf(Type subType) {
		return GenericTypeReflector.isSuperType(type, subType);
	}

	public boolean hasGenericSuperType(Type superType) {
		return GenericTypeReflector.isSuperType(superType, type);
	}

	public boolean isClass() {
		return raw_class.equals(Class.class);
	}

	/**
	 * Return true if variable is an enumeration
	 * 
	 * @return a boolean.
	 */
	public boolean isEnum() {
		return raw_class.isEnum();
	}

	public boolean isObject() {
		return raw_class.equals(Object.class);
	}

	public boolean isParameterizedType() {
		return type instanceof ParameterizedType;
	}

	/**
	 * Return true if variable is a primitive type
	 * 
	 * @return a boolean.
	 */
	public boolean isPrimitive() {
		return raw_class.isPrimitive();
	}

	public boolean isRawClass() {
		return type instanceof Class<?>;
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

	/**
	 * Return true if variable is void
	 * 
	 * @return a boolean.
	 */
	public boolean isVoid() {
		return raw_class.equals(Void.class) || raw_class.equals(void.class);
	}

	/**
	 * Return true if type of variable is a primitive wrapper
	 * 
	 * @return a boolean.
	 */
	public boolean isWrapperType() {
		return WRAPPER_TYPES.contains(raw_class);
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

}
