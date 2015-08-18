/**
 *
 */
package org.evosuite.utils.generic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.evosuite.TestGenerationContext;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.setup.TestClusterGenerator;
import org.evosuite.setup.TestUsageChecker;
import org.evosuite.testcase.variable.VariableReference;

import com.googlecode.gentyref.GenericTypeReflector;
import org.evosuite.utils.LoggingUtils;

/**
 * @author Gordon Fraser
 *
 */
public class GenericMethod extends GenericAccessibleObject<GenericMethod> {

	private static final long serialVersionUID = 6091851133071150237L;

	private transient Method method;

	public GenericMethod(Method method, GenericClass type) {
		super(new GenericClass(type));
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
		copyTypeVariables(copy);
		return copy;
	}

	@Override
	public GenericMethod copyWithOwnerFromReturnType(GenericClass returnType)
	        throws ConstructionFailedException {
		GenericClass newOwner = getOwnerClass().getGenericInstantiation(returnType.getTypeVariableMap());
		GenericMethod copy = new GenericMethod(method, newOwner);
		copyTypeVariables(copy);
		return copy;
	}

	@Override
	public GenericMethod copy() {
		GenericMethod copy = new GenericMethod(method, new GenericClass(owner));
		copyTypeVariables(copy);
		return copy;
	}

	public Method getMethod() {
		return method;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#getAccessibleObject()
	 */
	@Override
	public AccessibleObject getAccessibleObject() {
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

	public List<GenericClass> getParameterClasses() {
		List<GenericClass> parameters = new ArrayList<GenericClass>();
		logger.debug("Parameter types: "
		        + Arrays.asList(method.getGenericParameterTypes()));
		for (Type parameterType : getParameterTypes()) {
			logger.debug("Adding parameter: " + parameterType);
			parameters.add(new GenericClass(parameterType));
		}
		return parameters;
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
			logger.info("The method " + m + " is not a member of type " + type
			        + " - declared in " + m.getDeclaringClass());
			return m.getReturnType();
		}

		//if (exactDeclaringType.equals(type)) {
		//	logger.debug("Returntype: " + returnType + ", " + exactDeclaringType);
		//	return returnType;
		//}

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
			logger.info("The method " + m + " is not a member of type " + type
			        + " - declared in " + m.getDeclaringClass());
			return m.getParameterTypes();
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

	@Override
	public boolean isAccessible() {
		return TestUsageChecker.canUse(method);
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
	
	public boolean isOverloaded() {
		String methodName = getName();
		Class<?> declaringClass = method.getDeclaringClass();
		try {
			for(java.lang.reflect.Method otherMethod : declaringClass.getMethods()) {
				if(otherMethod.equals(method))
					continue;
				
				if(otherMethod.getName().equals(methodName)) {
					return true;
				}
			}
		} catch (SecurityException e) {
		//} catch (NoSuchMethodException e) {
		}

		return false;
	}


	public boolean isOverloaded(List<VariableReference> parameters) {
		String methodName = getName();
		Class<?> declaringClass = method.getDeclaringClass();
		Class<?>[] parameterTypes = method.getParameterTypes();
		boolean isExact = true;
		Class<?>[] parameterClasses = new Class<?>[parameters.size()];
		for (int num =0 ; num < parameters.size(); num++) {
			VariableReference parameter = parameters.get(num);
			parameterClasses[num] = parameter.getVariableClass();
			if (!parameterClasses[num].equals(parameterTypes[num])) {
				isExact = false;
			}

		}

		if (isExact) {
			return false;
		}
		try {
			for(java.lang.reflect.Method otherMethod : declaringClass.getMethods()) {
				if(otherMethod.equals(method))
					continue;
				
				if(otherMethod.getName().equals(methodName)) {
					if(!Arrays.equals(otherMethod.getParameterTypes(), parameterTypes)) {
						return true;
					}
				}
			}
//			java.lang.reflect.Method otherMethod = declaringClass.getMethod(methodName,
//			                                                                parameterTypes);
//			if (otherMethod != null && !otherMethod.equals(method)) {
//				return true;
//			}
		} catch (SecurityException e) {
		//} catch (NoSuchMethodException e) {
		}

		return false;
	}

	@Override
	public int getNumParameters() {
		return method.getGenericParameterTypes().length;
	}

	public boolean isGenericMethod() {
		return getNumParameters() > 0;
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
		Class<?> methodClass = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass((String) ois.readObject());

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

		if (this.method==null) {
			throw new IllegalStateException("Unknown method for " + methodName
					+ " in class " + methodClass.getCanonicalName());
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

					if(!newMethod.getDeclaringClass().getName().equals(method.getDeclaringClass().getName()))
						continue;

					if(!newMethod.getReturnType().getName().equals(method.getReturnType().getName()))
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericMethod other = (GenericMethod) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}


}
