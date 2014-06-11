/**
 * 
 */
package org.evosuite.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.evosuite.TestGenerationContext;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.setup.TestClusterGenerator;

import com.googlecode.gentyref.GenericTypeReflector;

/**
 * @author Gordon Fraser
 * 
 */
public class GenericField extends GenericAccessibleObject<GenericField> {

	private static final long serialVersionUID = -2344346234923642901L;

	private transient Field field;

	public GenericField(Field field, GenericClass owner) {
		super(new GenericClass(owner));
		this.field = field;
		field.setAccessible(true);
	}

	public GenericField(Field field, Class<?> owner) {
		super(new GenericClass(owner));
		this.field = field;
		field.setAccessible(true);
	}

	public GenericField(Field field, Type owner) {
		super(new GenericClass(owner));
		this.field = field;
		field.setAccessible(true);
	}

	@Override
	public GenericField copyWithNewOwner(GenericClass newOwner) {
		return new GenericField(field, newOwner);
	}

	@Override
	public GenericField copyWithOwnerFromReturnType(GenericClass returnType)
	        throws ConstructionFailedException {
		return new GenericField(field,
		        getOwnerClass().getGenericInstantiation(returnType.getTypeVariableMap()));
		/*
		if (returnType.isParameterizedType()) {
			GenericClass newOwner = new GenericClass(
			        getTypeFromExactReturnType(returnType.getType(), getOwnerType()));
			return new GenericField(field, newOwner);
		} else if (returnType.isArray()) {
			GenericClass newOwner = new GenericClass(
			        getTypeFromExactReturnType(returnType.getComponentType(),
			                                   getOwnerType()));
			return new GenericField(field, newOwner);
		} else if (returnType.isAssignableTo(getGeneratedType())) {
			return new GenericField(field, new GenericClass(owner));
		} else {
			throw new RuntimeException("Invalid return type: "
			        + returnType.getClassName() + " for field " + toString());
		}
		*/
	}

	@Override
	public GenericField copy() {
		return new GenericField(field, new GenericClass(owner));
	}

	public Field getField() {
		return field;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#getAccessibleObject()
	 */
	@Override
	public AccessibleObject getAccessibleObject() {
		return field;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#getDeclaringClass()
	 */
	@Override
	public Class<?> getDeclaringClass() {
		return field.getDeclaringClass();
	}

	@Override
	public Type getGeneratedType() {
		return getFieldType();
	}

	@Override
	public Class<?> getRawGeneratedType() {
		return field.getType();
	}

	@Override
	public Type getGenericGeneratedType() {
		return field.getGenericType();
	}

	public Type getFieldType() {
		return GenericTypeReflector.getExactFieldType(field, owner.getType());
		// 		try {
		// fieldType = field.getGenericType();
		// } catch (java.lang.reflect.GenericSignatureFormatError e) {
		// Ignore
		// fieldType = field.getType();
		// }
	}

	public Type getGenericFieldType() {
		return field.getGenericType();
	}

	@Override
	public boolean isAccessible() {
		return TestClusterGenerator.canUse(field);
	}
	
	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#isField()
	 */
	@Override
	public boolean isField() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#isStatic()
	 */
	@Override
	public boolean isStatic() {
		return Modifier.isStatic(field.getModifiers());
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#getName()
	 */
	@Override
	public String getName() {
		return field.getName();
	}

	/* (non-Javadoc)
	 * @see org.evosuite.utils.GenericAccessibleObject#toString()
	 */
	@Override
	public String toString() {
		return field.toGenericString();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		// Write/save additional fields
		oos.writeObject(field.getDeclaringClass().getName());
		oos.writeObject(field.getName());
	}

	// assumes "static java.util.Date aDate;" declared
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		ois.defaultReadObject();

		// Read/initialize additional fields
		Class<?> methodClass = TestGenerationContext.getInstance().getClassLoaderForSUT().loadClass((String) ois.readObject());
		String fieldName = (String) ois.readObject();

		try {
			field = methodClass.getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (SecurityException e) {
		    throw new IllegalStateException("Unknown field for " + fieldName 
		                                    + " in class " + methodClass.getCanonicalName());
		} catch (NoSuchFieldException e) {
            throw new IllegalStateException("Unknown field for " + fieldName 
                                            + " in class " + methodClass.getCanonicalName());
		}
	}

	@Override
	public void changeClassLoader(ClassLoader loader) {
		super.changeClassLoader(loader);

		try {
			Class<?> oldClass = field.getDeclaringClass();
			Class<?> newClass = loader.loadClass(oldClass.getName());
			this.field = newClass.getDeclaredField(field.getName());
			this.field.setAccessible(true);
		} catch (ClassNotFoundException e) {
			LoggingUtils.getEvoLogger().info("Class not found - keeping old class loader ",
			                                 e);
		} catch (SecurityException e) {
			LoggingUtils.getEvoLogger().info("Class not found - keeping old class loader ",
			                                 e);
		} catch (NoSuchFieldException e) {
			LoggingUtils.getEvoLogger().info("Field " + field.getName()
			                                         + " not found in class "
			                                         + field.getDeclaringClass());
		}
	}
}
