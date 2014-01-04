package org.evosuite.setup;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.setup.PutStaticMethodCollector.MethodIdentifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Type;

import com.examples.with.different.packagename.staticusage.FooBar1;
import com.examples.with.different.packagename.staticusage.FooBar2;

public class TestPutStaticCollector {

	@Test
	public void testFooBar1() {
		PutStaticMethodCollector collector = new PutStaticMethodCollector();
		String className = FooBar1.class.getName();

		MethodIdentifier expected_method_id = new MethodIdentifier(
				FooBar2.class.getName(), "init_used_int_field",
				Type.getMethodDescriptor(Type.VOID_TYPE));
		Set<MethodIdentifier> expected_methods = Collections
				.singleton(expected_method_id);

		Set<MethodIdentifier> methods = collector.collectMethods(className);
		assertEquals(expected_methods, methods);
	}
}
