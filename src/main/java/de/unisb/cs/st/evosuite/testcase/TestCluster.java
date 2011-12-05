/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.ga.ConstructionFailedException;
import de.unisb.cs.st.evosuite.javaagent.InstrumentingClassLoader;

/**
 * @author Gordon Fraser
 * 
 */
public abstract class TestCluster {

	protected static Logger logger = LoggerFactory.getLogger(TestCluster.class);

	/**
	 * This is the classloader that does the instrumentation - it needs to be
	 * used by all test code
	 */
	public static ClassLoader classLoader = new InstrumentingClassLoader();

	/** Singleton instance */
	private static TestCluster instance = null;

	/**
	 * Instance accessor
	 * 
	 * @return
	 */
	public static TestCluster getInstance() {
	    if (instance == null) {
			instance = new StaticTestCluster();
			instance.init();
	    }

		// TODO: Need property to switch between test clusters

		return instance;
	}

        protected void init() {}

	private static List<String> finalClasses = new ArrayList<String>();

	private static Set<Method> staticInitializers = new HashSet<Method>();

	public static void registerStaticInitializer(String className) {
		finalClasses.add(className);
	}

	private static void loadStaticInitializers() {
		for (String className : finalClasses) {
			try {
				Class<?> clazz = classLoader.loadClass(className);
				Method m = clazz.getMethod("__STATIC_RESET", (Class<?>[]) null);
				m.setAccessible(true);
				staticInitializers.add(m);
				logger.info("Adding static class: " + className);
			} catch (ClassNotFoundException e) {
				logger.info("Static: Could not find class: " + className);
			} catch (SecurityException e) {
				logger.info("Static: Security exception: " + className);
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.info("Static: Could not find method clinit in : " + className);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finalClasses.clear();
	}

	/**
	 * Call each of the duplicated static constructors
	 */
	public void resetStaticClasses() {
		ExecutionTracer.disable();
		loadStaticInitializers();
		logger.debug("Static initializers: " + staticInitializers.size());
		for (Method m : staticInitializers) {
			try {
				m.invoke(null, (Object[]) null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			;
		}
		ExecutionTracer.enable();
	}

	/**
	 * Unload all classes; perform cleanup
	 */
	public abstract void resetCluster();

	/**
	 * Find a class that matches the given name
	 * 
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	public abstract Class<?> getClass(String name) throws ClassNotFoundException;

	/**
	 * Integrate a new class into the test cluster
	 * 
	 * @param name
	 * @throws ClassNotFoundException
	 */
	public abstract Class<?> importClass(String name) throws ClassNotFoundException;

	/**
	 * Set of classes that have been analyzed already
	 * 
	 * @return
	 */
	public abstract Set<Class<?>> getAnalyzedClasses();

	/**
	 * Determine if there are generators
	 * 
	 * @param type
	 * @return
	 * @throws ConstructionFailedException
	 */
	public abstract boolean hasGenerator(Type type);

	/**
	 * Randomly select one generator
	 * 
	 * @param type
	 * @return
	 * @throws ConstructionFailedException
	 */
	public abstract AccessibleObject getRandomGenerator(Type type)
	        throws ConstructionFailedException;

	/**
	 * Randomly select one generator
	 * 
	 * @param type
	 * @return
	 * @throws ConstructionFailedException
	 */
	public abstract AccessibleObject getRandomGenerator(Type type,
	        Set<AccessibleObject> excluded) throws ConstructionFailedException;

	/**
	 * Get a list of all generator objects for the type
	 * 
	 * @param type
	 * @return
	 * @throws ConstructionFailedException
	 */
	public abstract Set<AccessibleObject> getGenerators(Type type)
	        throws ConstructionFailedException;

	/**
	 * Return all calls that have a parameter with given type
	 * 
	 * @param type
	 * @return
	 */
	public abstract List<AccessibleObject> getCallsFor(Type type);

	/**
	 * Return all calls that have a parameter with given type
	 * 
	 * @param type
	 * @return
	 */
	public abstract List<AccessibleObject> getTestCallsWith(Type type);

	/**
	 * Get random method or constructor of unit under test
	 * 
	 * @return
	 * @throws ConstructionFailedException
	 */
	public abstract AccessibleObject getRandomTestCall();

	/**
	 * Get a list of all test calls (i.e., constructors and methods)
	 * 
	 * @return
	 */
	public abstract List<AccessibleObject> getTestCalls();

	/**
	 * Determine if we have generators for all parameters, and delete method if
	 * not
	 * 
	 * @param o
	 */
	public abstract void checkDependencies(AccessibleObject o);

	/**
	 * Get the set of constructors defined in this class and its superclasses
	 * 
	 * @param clazz
	 * @return
	 */
	public static Set<Constructor<?>> getConstructors(Class<?> clazz) {
		Map<String, Constructor<?>> helper = new HashMap<String, Constructor<?>>();

		Set<Constructor<?>> constructors = new HashSet<Constructor<?>>();
		/*
		if (clazz.getSuperclass() != null) {
			// constructors.addAll(getConstructors(clazz.getSuperclass()));
			for (Constructor<?> c : getConstructors(clazz.getSuperclass())) {
				helper.put(org.objectweb.asm.Type.getConstructorDescriptor(c), c);
			}
		}
		for (Class<?> in : clazz.getInterfaces()) {
			for (Constructor<?> c : getConstructors(in)) {
				helper.put(org.objectweb.asm.Type.getConstructorDescriptor(c), c);
			}
			// constructors.addAll(getConstructors(in));
		}
		*/

		// for(Constructor c : clazz.getConstructors()) {
		// constructors.add(c);
		// }
		for (Constructor<?> c : clazz.getDeclaredConstructors()) {
			// constructors.add(c);
			helper.put(org.objectweb.asm.Type.getConstructorDescriptor(c), c);
		}
		for (Constructor<?> c : helper.values()) {
			constructors.add(c);
		}
		return constructors;
	}

	/**
	 * Get the set of methods defined in this class and its superclasses
	 * 
	 * @param clazz
	 * @return
	 */
	public static Set<Method> getMethods(Class<?> clazz) {

		Map<String, Method> helper = new HashMap<String, Method>();

		if (clazz.getSuperclass() != null) {
			// constructors.addAll(getConstructors(clazz.getSuperclass()));
			for (Method m : getMethods(clazz.getSuperclass())) {
				helper.put(m.getName() + org.objectweb.asm.Type.getMethodDescriptor(m), m);
			}
		}
		for (Class<?> in : clazz.getInterfaces()) {
			for (Method m : getMethods(in)) {
				helper.put(m.getName() + org.objectweb.asm.Type.getMethodDescriptor(m), m);
			}
			// constructors.addAll(getConstructors(in));
		}

		// for(Constructor c : clazz.getConstructors()) {
		// constructors.add(c);
		// }
		for (Method m : clazz.getDeclaredMethods()) {
			helper.put(m.getName() + org.objectweb.asm.Type.getMethodDescriptor(m), m);
		}

		Set<Method> methods = new HashSet<Method>();
		methods.addAll(helper.values());
		/*
		for (Method m : helper.values()) {
			String name = m.getName() + "|"
			        + org.objectweb.asm.Type.getMethodDescriptor(m);

			methods.add(m);
		}
		*/
		return methods;
	}

	/**
	 * Get the set of fields defined in this class and its superclasses
	 * 
	 * @param clazz
	 * @return
	 */
	public static Set<Field> getFields(Class<?> clazz) {
		// TODO: Helper not necessary here!
		Map<String, Field> helper = new HashMap<String, Field>();

		Set<Field> fields = new HashSet<Field>();
		if (clazz.getSuperclass() != null) {
			// fields.addAll(getFields(clazz.getSuperclass()));
			for (Field f : getFields(clazz.getSuperclass())) {
				helper.put(f.toGenericString(), f);
			}

		}
		for (Class<?> in : clazz.getInterfaces()) {
			// fields.addAll(getFields(in));
			for (Field f : getFields(in)) {
				helper.put(f.toGenericString(), f);
			}
		}

		for (Field f : clazz.getDeclaredFields()) {
			// fields.add(m);
			helper.put(f.toGenericString(), f);
		}
		// for(Field m : clazz.getDeclaredFields()) {
		// fields.add(m);
		// }
		fields.addAll(helper.values());

		return fields;
	}
}
