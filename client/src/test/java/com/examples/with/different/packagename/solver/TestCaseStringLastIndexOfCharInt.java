package com.examples.with.different.packagename.solver;

public class TestCaseStringLastIndexOfCharInt {

	public static boolean test(String str) {
		if (str != null) {
			if (str.lastIndexOf('H', 3) == -1) {
				return true;
			}
		}
		return true;
	}
}
