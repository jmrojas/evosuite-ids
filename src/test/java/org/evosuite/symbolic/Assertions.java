package org.evosuite.symbolic;

public class Assertions {

	public static void checkEquals(long l, long r) {
		if (l != r) {
			throw new RuntimeException("check failed!");
		}
	}

	public static void checkEquals(float l, float r) {
		if (l != r) {
			throw new RuntimeException("check failed!");
		}
	}

	public static void checkEquals(int l, int r) {
		if (l != r) {
			throw new RuntimeException("check failed!");
		}
	}

	public static void checkEquals(double l, double r) {
		if (l != r) {
			throw new RuntimeException("check failed!");
		}
	}
}
