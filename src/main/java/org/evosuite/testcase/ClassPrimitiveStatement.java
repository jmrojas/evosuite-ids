package org.evosuite.testcase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.seeding.ConstantPoolManager;
import org.evosuite.utils.GenericClass;
import org.evosuite.utils.Randomness;
import org.objectweb.asm.commons.GeneratorAdapter;

public class ClassPrimitiveStatement extends PrimitiveStatement<Class<?>> {

	private static final long serialVersionUID = -2728777640255424791L;

	private Set<Class<?>> assignableClasses = new LinkedHashSet<Class<?>>();

	public ClassPrimitiveStatement(TestCase tc, GenericClass type,
	        Set<Class<?>> assignableClasses) {
		super(tc, type, Randomness.choice(assignableClasses));
		this.assignableClasses.addAll(assignableClasses);
	}

	public ClassPrimitiveStatement(TestCase tc, Class<?> value) {
		//		super(tc, new GenericClass(Class.class).getWithWildcardTypes(), value);
		super(
		        tc,
		        new GenericClass(Class.class).getWithParameterTypes(new Type[] { value }),
		        value);
		//		super(tc, new GenericClass(value.getClass()), value);
		this.assignableClasses.add(value);
	}

	public ClassPrimitiveStatement(TestCase tc) {
		//		super(tc, new GenericClass(Class.class).getWithWildcardTypes(),
		super(
		        tc,
		        new GenericClass(Class.class).getWithParameterTypes(new Type[] { Properties.getTargetClass() }),
		        Properties.getTargetClass());
		//		super(tc, new GenericClass(Properties.getTargetClass()),
		//		        Properties.getTargetClass());
	}

	@Override
	public boolean hasMoreThanOneValue() {
		return assignableClasses.size() != 1;
	}

	@Override
	public void delta() {
		randomize();
	}

	@Override
	public void zero() {
		this.value = Properties.getTargetClass();
	}

	@Override
	protected void pushBytecode(GeneratorAdapter mg) {
		// TODO Auto-generated method stub

	}

	private Class<?> getType(org.objectweb.asm.Type type) throws ClassNotFoundException {
		// Not quite sure why we have to treat primitives explicitly...
		switch (type.getSort()) {
		case org.objectweb.asm.Type.ARRAY:
			org.objectweb.asm.Type componentType = type.getElementType();
			Class<?> componentClass = getType(componentType);
			Class<?> arrayClass = Array.newInstance(componentClass, 0).getClass();
			return arrayClass;
		case org.objectweb.asm.Type.BOOLEAN:
			return boolean.class;
		case org.objectweb.asm.Type.BYTE:
			return byte.class;
		case org.objectweb.asm.Type.CHAR:
			return char.class;
		case org.objectweb.asm.Type.DOUBLE:
			return double.class;
		case org.objectweb.asm.Type.FLOAT:
			return float.class;
		case org.objectweb.asm.Type.INT:
			return int.class;
		case org.objectweb.asm.Type.LONG:
			return long.class;
		case org.objectweb.asm.Type.SHORT:
			return short.class;
		default:
			return Class.forName(type.getClassName(), true,
			                     TestGenerationContext.getInstance().getClassLoaderForSUT());
		}
	}

	@Override
	public void randomize() {
		if (!assignableClasses.isEmpty()) {
			value = Randomness.choice(assignableClasses);
		} else {
			org.objectweb.asm.Type type = ConstantPoolManager.getInstance().getConstantPool().getRandomType();
			try {
				value = getType(type);
			} catch (ClassNotFoundException e) {
				logger.warn("Error loading class " + type.getClassName() + ": " + e);
			} catch (NoClassDefFoundError e) {
				logger.warn("Error loading class " + type.getClassName() + ": " + e);
			} catch (ExceptionInInitializerError e) {
				logger.warn("Error loading class " + type.getClassName() + ": " + e);
			}
		}
	}

	@Override
	public void changeClassLoader(ClassLoader loader) {
		super.changeClassLoader(loader);
		GenericClass genericClass = new GenericClass(value);
		genericClass.changeClassLoader(loader);
		value = genericClass.getRawClass();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		GenericClass currentClass = new GenericClass(value);
		oos.writeObject(currentClass);
		List<GenericClass> currentAssignableClasses = new ArrayList<GenericClass>();
		for (Class<?> assignableClass : assignableClasses)
			currentAssignableClasses.add(new GenericClass(assignableClass));
		oos.writeObject(currentAssignableClasses);
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
	        IOException {
		GenericClass currentClass = (GenericClass) ois.readObject();
		value = currentClass.getRawClass();

		List<GenericClass> newAssignableClasses = (List<GenericClass>) ois.readObject();
		assignableClasses = new LinkedHashSet<Class<?>>();
		for (GenericClass assignableClass : newAssignableClasses) {
			assignableClasses.add(assignableClass.getRawClass());
		}

	}
}
