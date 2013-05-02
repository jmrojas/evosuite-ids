/**
 * 
 */
package org.evosuite.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.evosuite.TestGenerationContext;
import org.evosuite.setup.TestClusterGenerator;
import org.evosuite.testcase.VariableReference;

import com.googlecode.gentyref.GenericTypeReflector;

/**
 * @author Gordon Fraser
 * 
 */
public class GenericMethod extends GenericAccessibleObject<GenericMethod> {

	private static final long serialVersionUID = 6091851133071150237L;

	private transient Method method;

	public GenericMethod(Method method, GenericClass type) {
		super(type);
		this.method = method;
	}

	public GenericMethod(Method method, Class<?> type) {
		super(new GenericClass(type));
		this.method = method;
	}

	public GenericMethod(Method method, Type type) {
		super(new GenericClass(type));
		this.method = method;
	}

	@Override
	public GenericMethod copyWithNewOwner(GenericClass newOwner) {
		GenericMethod copy = new GenericMethod(method, newOwner);
		copy.getParameterTypes();
		copy.typeVariables.addAll(typeVariables);
		return copy;
	}

	@Override
	public GenericMethod copyWithOwnerFromReturnType(GenericClass returnType) {
		if (returnType.isParameterizedType()) {
			GenericClass newOwner = new GenericClass(
			        getTypeFromExactReturnType((ParameterizedType) returnType.getType(),
			                                   (ParameterizedType) getOwnerType()));
			GenericMethod copy = new GenericMethod(method, newOwner);
			copy.typeVariables.addAll(typeVariables);
			return copy;
		} else if (returnType.isArray()) {
			GenericClass newOwner = new GenericClass(
			        getTypeFromExactReturnType((ParameterizedType) returnType.getComponentType(),
			                                   (ParameterizedType) getOwnerType()));
			GenericMethod copy = new GenericMethod(method, newOwner);
			copy.typeVariables.addAll(typeVariables);
			return copy;
		} else {
			throw new RuntimeException("Invalid type: " + returnType.getType()
			        + " of type " + returnType.getType().getClass() + " with owner type "
			        + getOwnerClass().getTypeName());
		}
	}

	@Override
	public GenericMethod copy() {
		GenericMethod copy = new GenericMethod(method, new GenericClass(owner));
		copy.typeVariables.addAll(typeVariables);
		return copy;
	}

	public Method getMethod() {
		return method;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#getDeclaringClass()
	 */
	@Override
	public Class<?> getDeclaringClass() {
		return method.getDeclaringClass();
	}

	public Type[] getParameterTypes() {
		return getExactParameterTypes(method, owner.getType());
	}

	public Type[] getGenericParameterTypes() {
		return method.getGenericParameterTypes();
	}

	public Class<?>[] getRawParameterTypes() {
		return method.getParameterTypes();
	}

	@Override
	public Type getGeneratedType() {
		return getReturnType();
	}

	public Type getReturnType() {
		Type returnType = getExactReturnType(method, owner.getType());
		if (returnType == null) {
			LoggingUtils.getEvoLogger().info("Exact return type is null for " + method
			                                         + " with owner " + owner);
			for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
				LoggingUtils.getEvoLogger().info(elem.toString());
			}
			assert (false);

			returnType = method.getGenericReturnType();
		}
		return returnType;
	}

	@Override
	public Type getGenericGeneratedType() {
		return method.getGenericReturnType();
	}

	@Override
	public Class<?> getRawGeneratedType() {
		return method.getReturnType();
	}

	/**
	 * Returns the exact return type of the given method in the given type. This
	 * may be different from <tt>m.getGenericReturnType()</tt> when the method
	 * was declared in a superclass, or <tt>type</tt> has a type parameter that
	 * is used in the return type, or <tt>type</tt> is a raw type.
	 */
	public Type getExactReturnType(Method m, Type type) {
		Type returnType = m.getGenericReturnType();
		Type exactDeclaringType = GenericTypeReflector.getExactSuperType(GenericTypeReflector.capture(type),
		                                                                 m.getDeclaringClass());
		if (exactDeclaringType == null) { // capture(type) is not a subtype of m.getDeclaringClass()
			throw new IllegalArgumentException("The method " + m
			        + " is not a member of type " + type);
		}
		return mapTypeParameters(returnType, exactDeclaringType);
	}

	/**
	 * Returns the exact parameter types of the given method in the given type.
	 * This may be different from <tt>m.getGenericParameterTypes()</tt> when the
	 * method was declared in a superclass, or <tt>type</tt> has a type
	 * parameter that is used in one of the parameters, or <tt>type</tt> is a
	 * raw type.
	 */
	public Type[] getExactParameterTypes(Method m, Type type) {
		Type[] parameterTypes = m.getGenericParameterTypes();
		Type exactDeclaringType = GenericTypeReflector.getExactSuperType(GenericTypeReflector.capture(type),
		                                                                 m.getDeclaringClass());
		if (exactDeclaringType == null) { // capture(type) is not a subtype of m.getDeclaringClass()
			throw new IllegalArgumentException("The method " + m
			        + " is not a member of type " + type.hashCode() + ": "
			        + m.getDeclaringClass().hashCode());
		}

		Type[] result = new Type[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			result[i] = mapTypeParameters(parameterTypes[i], exactDeclaringType);
		}
		return result;
	}

	@Override
	public TypeVariable<?>[] getTypeParameters() {
		return method.getTypeParameters();
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#isMethod()
	 */
	@Override
	public boolean isMethod() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#isStatic()
	 */
	@Override
	public boolean isStatic() {
		return Modifier.isStatic(method.getModifiers());
	}

	public boolean isOverloaded(List<VariableReference> parameters) {
		String methodName = getName();
		Class<?> declaringClass = method.getDeclaringClass();
		Class<?>[] parameterTypes = method.getParameterTypes();
		boolean isExact = true;
		Class<?>[] parameterClasses = new Class<?>[parameters.size()];
		int num = 0;
		for (VariableReference parameter : parameters) {
			parameterClasses[num] = parameter.getVariableClass();
			if (!parameterClasses[num].equals(parameterTypes[num])) {
				isExact = false;
			}
		}
		if (isExact)
			return false;
		try {
			java.lang.reflect.Method otherMethod = declaringClass.getMethod(methodName,
			                                                                parameterTypes);
			if (otherMethod != null)
				return true;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}

		return false;
	}

	@Override
	public int getNumParameters() {
		return method.getGenericParameterTypes().length;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#getName()
	 */
	@Override
	public String getName() {
		return method.getName();
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#toString()
	 */
	@Override
	public String toString() {
		return method.toGenericString();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		// Write/save additional fields
		oos.writeObject(method.getDeclaringClass().getName());
		oos.writeObject(method.getName());
		oos.writeObject(org.objectweb.asm.Type.getMethodDescriptor(method));
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
				if (org.objectweb.asm.Type.getMethodDescriptor(method).equals(methodDesc)) {
					this.method = method;
					return;
				}
			}
		}
	}

	@Override
	public void changeClassLoader(ClassLoader loader) {
		super.changeClassLoader(loader);

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
						return;
					}
				}
			}
			LoggingUtils.getEvoLogger().info("Method not found - keeping old class loader ");
		} catch (ClassNotFoundException e) {
			LoggingUtils.getEvoLogger().info("Class not found - keeping old class loader ",
			                                 e);
		} catch (SecurityException e) {
			LoggingUtils.getEvoLogger().info("Class not found - keeping old class loader ",
			                                 e);
		}
	}

}
