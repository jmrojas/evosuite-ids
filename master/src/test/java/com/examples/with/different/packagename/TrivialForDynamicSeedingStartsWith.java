package com.examples.with.different.packagename;

public class TrivialForDynamicSeedingStartsWith {
	
	public static boolean foo(String x) {
		String a = "This string has to be long";
		String b = " Because in such case it would be hard for EvoSuite";
		if (x.startsWith(a + b)) {
			return true;
		} else {
			return false;
		}
	}
}
