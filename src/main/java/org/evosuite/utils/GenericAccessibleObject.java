/**
 * 
 */
package org.evosuite.utils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.apache.commons.lang3.reflect.TypeUtils;

import com.googlecode.gentyref.GenericTypeReflector;

/**
 * @author Gordon Fraser
 * 
 */
public abstract class GenericAccessibleObject implements Serializable {

	
	private static final long serialVersionUID = 7069749492563662621L;

	protected GenericClass owner;

	public GenericAccessibleObject(GenericClass owner) {
		this.owner = owner;
	}

	public Type getOwnerType() {
		return owner.getType();
	}

	public GenericClass getOwnerClass() {
		return owner;
	}
	
	public abstract GenericAccessibleObject copyWithNewOwner(GenericClass newOwner);
	
	public abstract GenericAccessibleObject copyWithOwnerFromReturnType(ParameterizedType returnType);
	
	public abstract Class<?> getDeclaringClass();

	public boolean isMethod() {
		return false;
	}

	public boolean isConstructor() {
		return false;
	}

	public boolean isField() {
		return false;
	}

	public boolean isStatic() {
		return false;
	}
	
	public int getNumParameters() {
		return 0;
	}

	public abstract String getName();

	@Override
	public abstract String toString();

	public void changeClassLoader(ClassLoader loader) {
		owner.changeClassLoader(loader);
	}

	/**
	 * Maps type parameters in a type to their values.
	 * 
	 * @param toMapType
	 *            Type possibly containing type arguments
	 * @param typeAndParams
	 *            must be either ParameterizedType, or (in case there are no
	 *            type arguments, or it's a raw type) Class
	 * @return toMapType, but with type parameters from typeAndParams replaced.
	 */
	protected static Type mapTypeParameters(Type toMapType, Type typeAndParams) {
		if (isMissingTypeParameters(typeAndParams)) {
			LoggingUtils.getEvoLogger().info("Is missing type parameters, so erasing types");
			return GenericTypeReflector.erase(toMapType);
		} else {
			VarMap varMap = new VarMap();
			Type handlingTypeAndParams = typeAndParams;
			while (handlingTypeAndParams instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType) handlingTypeAndParams;
				Class<?> clazz = (Class<?>) pType.getRawType(); // getRawType should always be Class
				varMap.addAll(clazz.getTypeParameters(), pType.getActualTypeArguments());
				handlingTypeAndParams = pType.getOwnerType();
			}
			return varMap.map(toMapType);
		}
	}

	/**
	 * Checks if the given type is a class that is supposed to have type
	 * parameters, but doesn't. In other words, if it's a really raw type.
	 */
	protected static boolean isMissingTypeParameters(Type type) {
		if (type instanceof Class) {
			for (Class<?> clazz = (Class<?>) type; clazz != null; clazz = clazz.getEnclosingClass()) {
				if (clazz.getTypeParameters().length != 0)
					return true;
			}
			return false;
		} else if (type instanceof ParameterizedType) {
			return false;
		} else {
			throw new AssertionError("Unexpected type " + type.getClass());
		}
	}
	
	/**
     * Returns the exact return type of the given method in the given type.
     * This may be different from <tt>m.getGenericReturnType()</tt> when the method was declared in a superclass,
     * or <tt>type</tt> has a type parameter that is used in the return type, or <tt>type</tt> is a raw type.
     */
    public static Type getTypeFromExactReturnType(ParameterizedType returnType, ParameterizedType type) {
    	Map<TypeVariable<?>, Type> typeMap = TypeUtils.getTypeArguments(returnType);
    	Type[] actualParameters = new Type[type.getActualTypeArguments().length];
    	int num = 0;
    	for(TypeVariable<?> parameterType : ((Class<?>)type.getRawType()).getTypeParameters()) {
    		//for(Type parameterType : type.getActualTypeArguments()) {
    		//	if(parameterType instanceof TypeVariable<?>) {
    		boolean replaced = false;
    		for(TypeVariable<?> var : typeMap.keySet()) {
    			// D'oh! Why the heck do we need this?? 
    			if(var.getName().equals(parameterType.getName())) {
    				//if(typeMap.containsKey(parameterType)) {
    				actualParameters[num] = typeMap.get(var);
    				replaced = true;
    				break;
    				//} else {
    			}
    		}
    		if(!replaced) {
    			actualParameters[num] = parameterType;
    		}
    		//}
    		//    	} else {
    		//    		LoggingUtils.getEvoLogger().info("Not a type variable "+parameterType);
    		//    		actualParameters[num] = parameterType;
    		//    		}
    		num++;
    	}
    	
    	return new ParameterizedTypeImpl((Class<?>)type.getRawType(), actualParameters, null);
    }
}
