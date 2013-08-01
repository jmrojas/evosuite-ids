package org.evosuite.stubs;



import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.Dummy;
import com.examples.with.different.packagename.NullString;
import com.examples.with.different.packagename.ReturnALocale;
import com.examples.with.different.packagename.SimpleInteger;

public class TestStubInstrumentation {

	@Test
	public void testEmptyFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.Dummy");
		Class<?> testClass = Dummy.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		boolean testResult = (boolean) testClass.getMethod("isDummy").invoke(testInstance);
		boolean stubResult = (boolean) stubClass.getMethod("isDummy").invoke(stubInstance);
		
		Assert.assertTrue(testResult);
		Assert.assertFalse(stubResult);
	}
	
	@Test
	public void testIntFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		int testResult = (int) testClass.getMethod("testInt", new Class<?> []{int.class, int.class}).invoke(testInstance, 10, 10);
		int stubResult = (int) stubClass.getMethod("testInt", new Class<?> []{int.class, int.class}).invoke(stubInstance, 10, 10);
		
		Assert.assertEquals(20, testResult);
		Assert.assertEquals(0, stubResult);
	}
	
	@Test
	public void testShortFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		short testResult = (short) testClass.getMethod("testShort", new Class<?> []{short.class, short.class}).invoke(testInstance, (short)10, (short)10);
		short stubResult = (short) stubClass.getMethod("testShort", new Class<?> []{short.class, short.class}).invoke(stubInstance, (short)10, (short)10);
		
		Assert.assertEquals(20, testResult);
		Assert.assertEquals(0, stubResult);
	}
	
	@Test
	public void testByteFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		byte testResult = (byte) testClass.getMethod("testByte", new Class<?> []{byte.class, byte.class}).invoke(testInstance, (byte)10, (byte)10);
		byte stubResult = (byte) stubClass.getMethod("testByte", new Class<?> []{byte.class, byte.class}).invoke(stubInstance, (byte)10, (byte)10);
		
		Assert.assertEquals(20, testResult);
		Assert.assertEquals(0, stubResult);
	}
	
	@Test
	public void testCharFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		char testResult = (char) testClass.getMethod("testChar", new Class<?> []{char.class, char.class}).invoke(testInstance, (char)10, (char)10);
		char stubResult = (char) stubClass.getMethod("testChar", new Class<?> []{char.class, char.class}).invoke(stubInstance, (char)10, (char)10);
		
		Assert.assertEquals(20, testResult);
		Assert.assertEquals(0, stubResult);
	}
	
	@Test
	public void testLongFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		long testResult = (long) testClass.getMethod("testLong", new Class<?> []{long.class, long.class}).invoke(testInstance, 10, 10);
		long stubResult = (long) stubClass.getMethod("testLong", new Class<?> []{long.class, long.class}).invoke(stubInstance, 10, 10);
		
		Assert.assertEquals(20L, testResult);
		Assert.assertEquals(0L, stubResult);
	}
	
	@Test
	public void testFloatFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		float testResult = (float) testClass.getMethod("testFloat", new Class<?> []{float.class, float.class}).invoke(testInstance, 10F, 10F);
		float stubResult = (float) stubClass.getMethod("testFloat", new Class<?> []{float.class, float.class}).invoke(stubInstance, 10F, 10F);
		
		Assert.assertEquals(20F, testResult, 0.1);
		Assert.assertEquals(0F, stubResult, 0.1);
	}
	
	@Test
	public void testDoubleFunction() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		double testResult = (double) testClass.getMethod("testDouble", new Class<?> []{double.class, double.class}).invoke(testInstance, 10, 10);
		double stubResult = (double) stubClass.getMethod("testDouble", new Class<?> []{double.class, double.class}).invoke(stubInstance, 10, 10);
		
		Assert.assertEquals(20, testResult, 0.1);
		Assert.assertEquals(0, stubResult, 0.1);
	}
	
	
	@Test
	public void testSingleParameter() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.NullString");
		Class<?> testClass = NullString.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		boolean testResult = (boolean) testClass.getMethod("isNull", new Class<?> []{String.class}).invoke(testInstance, new Object[] { null} );
		boolean stubResult = (boolean) stubClass.getMethod("isNull", new Class<?> []{String.class}).invoke(stubInstance, new Object[] { null} );
		
		Assert.assertTrue(testResult);
		Assert.assertFalse(stubResult);

		testResult = (boolean) testClass.getMethod("isNull", new Class<?> []{String.class}).invoke(testInstance, "test" );
		stubResult = (boolean) stubClass.getMethod("isNull", new Class<?> []{String.class}).invoke(stubInstance, "test" );
		
		Assert.assertFalse(testResult);
		Assert.assertFalse(stubResult);

	}
	
	@Test
	public void testObjectReturn() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.ReturnALocale");
		Class<?> testClass = ReturnALocale.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		Locale testResult = (Locale) testClass.getMethod("getLocale", new Class<?> []{int.class}).invoke(testInstance, 10 );
		Locale stubResult = (Locale) stubClass.getMethod("getLocale", new Class<?> []{int.class}).invoke(stubInstance, 10 );
		
		Assert.assertEquals(Locale.ENGLISH, testResult);
		Assert.assertNull(stubResult);
	}
	
	@Test
	public void testArrayReturn() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.SimpleInteger");
		Class<?> testClass = SimpleInteger.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		int[] testResult = (int[]) testClass.getMethod("testIntArray", new Class<?> []{int.class, int.class}).invoke(testInstance, 10, 10);
		int[] stubResult = (int[]) stubClass.getMethod("testIntArray", new Class<?> []{int.class, int.class}).invoke(stubInstance, 10, 10);
		
		Assert.assertEquals(2, testResult.length);
		Assert.assertEquals(0, stubResult.length);
	}
	
	@Test
	public void testObjectArrayReturn() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StubbingClassLoader classLoader = new StubbingClassLoader();

		Class<?> stubClass = classLoader.loadClass("com.examples.with.different.packagename.ReturnALocale");
		Class<?> testClass = ReturnALocale.class;
		
		Object stubInstance = stubClass.newInstance();
		Object testInstance = testClass.newInstance();
		
		Locale[] testResult = (Locale[]) testClass.getMethod("getMoreLocales", new Class<?> []{}).invoke(testInstance);
		Locale[] stubResult = (Locale[]) stubClass.getMethod("getMoreLocales", new Class<?> []{}).invoke(stubInstance);
		
		Assert.assertEquals(2, testResult.length);
		Assert.assertEquals(0, stubResult.length);
	}
}
