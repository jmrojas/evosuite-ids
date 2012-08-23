package org.evosuite.symbolic;

import static org.evosuite.symbolic.Assertions.checkEquals;

public class TestCase20 {

	static final float FLOAT_VALUE = -0.0099100191F;

	static final double DOUBLE_VALUE = Math.PI;

	public static void test(float float0, double double0) {

		{
			// test getExponent(float,float)
			float float1 = FLOAT_VALUE;
			float float2 = Math.signum(float0);
			float float3 = Math.signum(float1);
			checkEquals(float2, float3);
		}
		{
			// test getExponent(double,double)
			double double1 = DOUBLE_VALUE;
			double double2 = Math.signum(double0);
			double double3 = Math.signum(double1);
			checkEquals(double2, double3);
		}
	}
}
