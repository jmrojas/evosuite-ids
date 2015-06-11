package org.evosuite.classpath;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.evosuite.SystemTest;
import org.junit.Test;

import com.examples.with.different.packagename.listclasses.AbstractClass;
import com.examples.with.different.packagename.listclasses.ClassWithDefaultMethods;
import com.examples.with.different.packagename.listclasses.ClassWithProtectedMethods;
import com.examples.with.different.packagename.listclasses.ClassWithoutPublicMethods;

public class TestResourceList extends SystemTest {

	@Test
	public void testCanAccessAbstractClass() throws IOException {
		assertTrue(ResourceList.isClassTestable(AbstractClass.class.getCanonicalName()));
	}
	
	@Test
	public void testCanAccessAClassWithDefaultMethods() throws IOException {
		assertTrue(ResourceList.isClassTestable(ClassWithDefaultMethods.class.getCanonicalName()));
	}

	@Test
	public void testCanAccessClassWithoutPublicMethods() throws IOException {
		assertFalse(ResourceList.isClassTestable(ClassWithoutPublicMethods.class.getCanonicalName()));
	}

	@Test
	public void testCanAccessClassWithProtectedMethods() throws IOException {
		assertTrue(ResourceList.isClassTestable(ClassWithProtectedMethods.class.getCanonicalName()));
	}

	@Test
	public void testCanAccessNonPublicClass() throws IOException {
		assertTrue(ResourceList.isClassTestable("com.examples.with.different.packagename.listclasses.NonPublicClass"));
	}

}
