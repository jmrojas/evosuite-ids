package de.unisb.cs.st.evosuite;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import de.unisb.cs.st.evosuite.javaagent.BytecodeInstrumentation;
import de.unisb.cs.st.evosuite.javaagent.InstrumentingClassLoader;

public class TestUtil {
	public static void assertCorrectStart(Class<?> clazz) {
		Assert.assertTrue("Must start test with '-javaagent:path/javaagent.jar=generate'.",
				BytecodeInstrumentation.isJavaagent());
		String projectPrefix = clazz.getPackage().getName();
		Assert.assertEquals("Must start test with '-DDPROJECT_PREFIX=" + projectPrefix + "'.",
				Properties.PROJECT_PREFIX, projectPrefix);
		String targetClass = clazz.getName();
		Assert.assertEquals("Must start test with '-DTARGET_CLASS=" + targetClass + "'.", Properties.TARGET_CLASS,
				targetClass);
	}

	public static void assertCorrectStart(String clazz) {
		// TODO Replace with
		// Properties.OUTPUT_DIR = "examples/facts/evosuite-files/";
		// ClassTransformer.getInstance().instrumentClass(clazz);
		// TODO When doing so remember to also remove the -javaagent param from
		// the launch config
		Assert.assertTrue("Must start test with '-javaagent:path/javaagent.jar=generate'.",
				BytecodeInstrumentation.isJavaagent());
		String projectPrefix = clazz.substring(0, clazz.lastIndexOf("."));
		Assert.assertEquals("Must start test with '-DDPROJECT_PREFIX=" + projectPrefix + "'.",
				Properties.PROJECT_PREFIX, projectPrefix);
		String targetClass = clazz;
		Assert.assertEquals("Must start test with '-DTARGET_CLASS=" + targetClass + "'.", Properties.TARGET_CLASS,
				targetClass);
	}

	public static <T> Set<T> createHashSet(T... elements) {
		return new HashSet<T>(Arrays.asList(elements));
	}

	public static String getPrefix(String fullyQualifiedClass) {
		return fullyQualifiedClass.substring(0, fullyQualifiedClass.lastIndexOf("."));
	}

	public static Object invokeMethod(Object target, String methodName, Object... args) {
		try {
			Class<?>[] argClasses = getArgClasses(args);
			Method method = target.getClass().getMethod(methodName, argClasses);
			method.setAccessible(true);
			return method.invoke(target, args);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public static Object loadInstrumented(String className, Object... constructorArgs) {
		try {
			Properties.TARGET_CLASS = className;
			Properties.PROJECT_PREFIX = getPrefix(className);
			InstrumentingClassLoader classLoader = new InstrumentingClassLoader();
			Class<?> factsComparatorClass = classLoader.loadClass(className);
			Class<?>[] argClasses = getArgClasses(constructorArgs);
			Constructor<?> factsComparatorConstructor = factsComparatorClass.getConstructor(argClasses);
			Object target = factsComparatorConstructor.newInstance(constructorArgs);
			return target;
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void setField(Object target, String fieldName, Object value) {
		try {
			Class<?> clazz = target.getClass();
			Field field = clazz.getField(fieldName);
			field.set(target, value);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	private static Class<?>[] getArgClasses(Object... args) {
		Class<?>[] argClasses = new Class[args.length];
		for (int idx = 0; idx < args.length; idx++) {
			argClasses[idx] = args[idx].getClass();
		}
		return argClasses;
	}

	private TestUtil() {
		// private constructor
	}
}
