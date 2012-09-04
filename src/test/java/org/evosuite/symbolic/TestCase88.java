package org.evosuite.symbolic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestCase88 {

	public int callbackMethodToIgnore(int left, int right) {
		int counter = 0;
		for (int i = 0; i <= right; i++) {
			counter += left;
		}
		return counter;
	}

	public static void test() throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		TestCase88 instance0 = new TestCase88();
		Method method = TestCase88.class.getMethod("callbackMethodToIgnore",
				int.class, int.class);
		int int0 = 10;
		int int1 = 20;

		Object ret = method.invoke(instance0, int0, int1);
		int int2 = (Integer) ret;
		int int3 = 210;
		
		Assertions.checkEquals(int2, int3);
	}

}
