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
/**
 * 
 */
package org.evosuite.testcase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

import org.evosuite.Properties;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * FieldReference class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class FieldReference extends VariableReferenceImpl {

	private static final long serialVersionUID = 834164966411781655L;

	private final Logger logger = LoggerFactory.getLogger(FieldReference.class);

	private transient Field field;

	private VariableReference source;

	/**
	 * <p>
	 * Constructor for FieldReference.
	 * </p>
	 * 
	 * @param testCase
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 * @param source
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 */
	public FieldReference(TestCase testCase, Field field, VariableReference source) {
		super(testCase, field.getGenericType());
		assert (source != null || Modifier.isStatic(field.getModifiers())) : "No source object was supplied, therefore we assumed the field to be static. However asking the field if it was static, returned false";
		this.field = field;
		this.source = source;
		//		logger.info("Creating new field assignment for field " + field + " of object "
		//		        + source);

	}

	/**
	 * We need this constructor to work around a bug in Java Generics which
	 * causes a java.lang.reflect.GenericSignatureFormatError when accessing
	 * getType
	 * 
	 * @param testCase
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 * @param fieldType
	 *            a {@link java.lang.reflect.Type} object.
	 * @param source
	 *            a {@link org.evosuite.testcase.VariableReference} object.
	 */
	public FieldReference(TestCase testCase, Field field, Type fieldType,
	        VariableReference source) {
		super(testCase, fieldType);
		assert (field != null);
		assert (source != null || Modifier.isStatic(field.getModifiers())) : "No source object was supplied, therefore we assumed the field to be static. However asking the field if it was static, returned false";
		this.field = field;
		this.source = source;
		//		logger.info("Creating new field assignment for field " + field + " of object "
		//		        + source);

	}

	/**
	 * <p>
	 * Constructor for FieldReference.
	 * </p>
	 * 
	 * @param testCase
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 */
	public FieldReference(TestCase testCase, Field field) {
		super(testCase, field.getGenericType());
		this.field = field;
		this.source = null;
	}

	/**
	 * We need this constructor to work around a bug in Java Generics which
	 * causes a java.lang.reflect.GenericSignatureFormatError when accessing
	 * getType
	 * 
	 * @param testCase
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param type
	 *            a {@link java.lang.reflect.Type} object.
	 * @param field
	 *            a {@link java.lang.reflect.Field} object.
	 */
	public FieldReference(TestCase testCase, Field field, Type type) {
		super(testCase, type);
		this.field = field;
		this.source = null;
	}

	/**
	 * Access the field
	 * 
	 * @return a {@link java.lang.reflect.Field} object.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Access the source object
	 * 
	 * @return a {@link org.evosuite.testcase.VariableReference} object.
	 */
	public VariableReference getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Return the actual object represented by this variable for a given scope
	 */
	@Override
	public Object getObject(Scope scope) throws CodeUnderTestException {

		Object s;
		if (Modifier.isStatic(field.getModifiers())) {
			s = null;
		} else {
			s = source.getObject(scope);
		}

		try {
			return field.get(s);
		} catch (IllegalArgumentException e) {
			logger.debug("Error accessing field " + field + " of object " + source + ": "
			        + e, e);
			throw new CodeUnderTestException(e.getCause());
		} catch (IllegalAccessException e) {
			logger.error("Error accessing field " + field + " of object " + source + ": "
			        + e, e);
			throw new EvosuiteError(e);
		} catch (NullPointerException e) {
			throw new CodeUnderTestException(e);
		} catch (ExceptionInInitializerError e) {
			throw new CodeUnderTestException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Set the actual object represented by this variable in a given scope
	 */
	@Override
	public void setObject(Scope scope, Object value) throws CodeUnderTestException {
		Object sourceObject = null;
		try {

			if (source != null) {
				sourceObject = source.getObject(scope);
				if (sourceObject == null) {
					/*
					 * #FIXME this is dangerously far away from the java semantics
					 *	That means we can have a testcase
					 *  SomeObject var1 = null;
					 *  var1.someAttribute = test;
					 *  and the testcase will execute in evosuite, executing it with junit will however lead to a nullpointer exception
					 */
					return;
				}
			}
			if (field.getType().equals(int.class))
				field.setInt(sourceObject, ((Number) value).intValue());
			else if (field.getType().equals(boolean.class))
				field.setBoolean(sourceObject, (Boolean) value);
			else if (field.getType().equals(byte.class))
				field.setByte(sourceObject, ((Number) value).byteValue());
			else if (field.getType().equals(char.class)) {
				if (value instanceof Character)
					field.setChar(sourceObject, (Character) value);
				else if (value instanceof Number) {
					Number num = (Number) value;
					char c = (char) num.intValue();
					field.setChar(sourceObject, c);
				} else {
					throw new RuntimeException(
					        "Assigning incompatible class to char field: " + value
					                + " of class "
					                + (value != null ? value.getClass() : "null"));
				}
			} else if (field.getType().equals(double.class))
				field.setDouble(sourceObject, ((Number) value).doubleValue());
			else if (field.getType().equals(float.class))
				field.setFloat(sourceObject, ((Number) value).floatValue());
			else if (field.getType().equals(long.class))
				field.setLong(sourceObject, ((Number) value).longValue());
			else if (field.getType().equals(short.class))
				field.setShort(sourceObject, ((Number) value).shortValue());
			else {
				if (value != null && !field.getType().isAssignableFrom(value.getClass())) {
					logger.error("Not assignable: " + value + " of class "
					        + value.getClass() + " to field " + field + " of variable "
					        + source);
				}
				// FIXXME: isAssignableFrom does not work with autoboxing
				// assert (value==null || field.getType().isAssignableFrom(value.getClass()));
				if (sourceObject != null
				        && !field.getDeclaringClass().isAssignableFrom(sourceObject.getClass())) {

					String msg = "Field " + field + " defined in class "
					        + field.getDeclaringClass() + ". Source object "
					        + sourceObject + " has class " + sourceObject.getClass()
					        + ". Value object " + value + " has class "
					        + value.getClass();
					logger.error("Class " + Properties.TARGET_CLASS + ". " + msg);
					//FIXME: is it correct to throw an exception here? if yes, which kind?						
				}
				//assert (field.getDeclaringClass().isAssignableFrom(sourceObject.getClass()));
				field.set(sourceObject, value);
			}
		} catch (IllegalArgumentException e) {
			logger.error("Error while assigning field: " + getName() + " with value "
			        + value + " on object " + sourceObject + ": " + e, e);
			throw e;
		} catch (IllegalAccessException e) {
			logger.error("Error while assigning field: " + e, e);
			throw new EvosuiteError(e);
		} catch (NullPointerException e) {
			throw new CodeUnderTestException(e);
		} catch (ExceptionInInitializerError e) {
			throw new CodeUnderTestException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.VariableReference#getAdditionalVariableReference()
	 */
	/** {@inheritDoc} */
	@Override
	public VariableReference getAdditionalVariableReference() {
		if (source != null && source.getAdditionalVariableReference() != null)
			return source.getAdditionalVariableReference();
		else
			return source;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.VariableReference#setAdditionalVariableReference(org.evosuite.testcase.VariableReference)
	 */
	/** {@inheritDoc} */
	@Override
	public void setAdditionalVariableReference(VariableReference var) {
		if (source != null
		        && !field.getDeclaringClass().isAssignableFrom(var.getVariableClass())) {
			logger.info("Not assignable: " + field.getDeclaringClass() + " and " + var);
			assert (false);
		}
		source = var;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.VariableReference#replaceAdditionalVariableReference(org.evosuite.testcase.VariableReference, org.evosuite.testcase.VariableReference)
	 */
	/** {@inheritDoc} */
	@Override
	public void replaceAdditionalVariableReference(VariableReference var1,
	        VariableReference var2) {
		if (source != null) {
			if (source.equals(var1))
				source = var2;
			else
				source.replaceAdditionalVariableReference(var1, var2);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getStPosition() {
		for (int i = 0; i < testCase.size(); i++) {
			if (testCase.getStatement(i).getReturnValue().equals(this)) {
				return i;
			}
		}
		if (source != null)
			return source.getStPosition();
		else {
			for (int i = 0; i < testCase.size(); i++) {
				if (testCase.getStatement(i).references(this)) {
					return i;
				}
			}
			throw new AssertionError(
			        "A VariableReferences position is only defined if the VariableReference is defined by a statement in the testCase.");
		}

		//			return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Return name for source code representation
	 */
	@Override
	public String getName() {
		if (source != null)
			return source.getName() + "." + field.getName();
		else
			return field.getDeclaringClass().getSimpleName() + "." + field.getName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Create a copy of the current variable
	 */
	@Override
	public VariableReference copy(TestCase newTestCase, int offset) {
		Type fieldType = field.getType();
		try {
			fieldType = field.getGenericType();
		} catch (java.lang.reflect.GenericSignatureFormatError e) {
			// Ignore
			fieldType = field.getType();
		}
		if (source != null) {
			//			VariableReference otherSource = newTestCase.getStatement(source.getStPosition()).getReturnValue();
			VariableReference otherSource = source.copy(newTestCase, offset);
			return new FieldReference(newTestCase, field, fieldType, otherSource);
		} else {
			return new FieldReference(newTestCase, field, fieldType);
		}
	}

	/**
	 * Determine the nesting level of the field access (I.e., how many dots in
	 * the expression)
	 * 
	 * @return a int.
	 */
	public int getDepth() {
		int depth = 1;
		if (source instanceof FieldReference) {
			depth += ((FieldReference) source).getDepth();
		}
		return depth;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldReference other = (FieldReference) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	private boolean isStatic() {
		return Modifier.isStatic(field.getModifiers());
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.VariableReferenceImpl#loadBytecode(org.objectweb.asm.commons.GeneratorAdapter, java.util.Map)
	 */
	@Override
	public void loadBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals) {
		if (!isStatic()) {
			source.loadBytecode(mg, locals);
			mg.getField(org.objectweb.asm.Type.getType(source.getVariableClass()),
			            field.getName(),
			            org.objectweb.asm.Type.getType(getVariableClass()));
		} else {
			mg.getStatic(org.objectweb.asm.Type.getType(source.getVariableClass()),
			             field.getName(),
			             org.objectweb.asm.Type.getType(getVariableClass()));
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.VariableReferenceImpl#storeBytecode(org.objectweb.asm.commons.GeneratorAdapter, java.util.Map)
	 */
	@Override
	public void storeBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals) {
		if (!isStatic()) {
			source.loadBytecode(mg, locals);
			mg.swap();
			mg.putField(org.objectweb.asm.Type.getType(source.getVariableClass()),
			            field.getName(),
			            org.objectweb.asm.Type.getType(getVariableClass()));
		} else {
			mg.putStatic(org.objectweb.asm.Type.getType(source.getVariableClass()),
			             field.getName(),
			             org.objectweb.asm.Type.getType(getVariableClass()));
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.VariableReference#changeClassLoader(java.lang.ClassLoader)
	 */
	/** {@inheritDoc} */
	@Override
	public void changeClassLoader(ClassLoader loader) {
		try {
			Class<?> oldClass = field.getDeclaringClass();
			Class<?> newClass = loader.loadClass(oldClass.getName());
			// this.field = newClass.getField(field.getName());
			this.field = newClass.getDeclaredField(field.getName());
			this.field.setAccessible(true);
		} catch (ClassNotFoundException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		} catch (SecurityException e) {
			logger.warn("Class not found - keeping old class loader ", e);
		} catch (NoSuchFieldException e) {
			logger.warn("Field " + field.getName() + " not found in class "
			        + field.getDeclaringClass());

		}
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		// Write/save additional fields
		oos.writeObject(field.getDeclaringClass());
		Field[] fields = field.getDeclaringClass().getDeclaredFields();
		boolean done = false;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals(field)) {
				oos.writeObject(new Integer(i));
				done = true;
			}
		}
		assert (done);
	}

	// assumes "static java.util.Date aDate;" declared
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		ois.defaultReadObject();

		// Read/initialize additional fields
		Class<?> clazz = (Class<?>) ois.readObject();
		int num = (Integer) ois.readObject();

		field = clazz.getDeclaredFields()[num];
	}
}
