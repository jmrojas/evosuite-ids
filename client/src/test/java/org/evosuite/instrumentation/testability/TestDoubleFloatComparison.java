package org.evosuite.instrumentation.testability;

import static org.junit.Assert.*;

import org.evosuite.instrumentation.testability.BooleanHelper;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.numeric.DoublePrimitiveStatement;
import org.junit.Test;

public class TestDoubleFloatComparison {

	@Test
	public void testDoubleComparison() {
		int x = BooleanHelper.doubleSub(0.0, 0.0);
		int y = BooleanHelper.doubleSub(10.0, 0.0);
		assertTrue(y > x);

		int z = BooleanHelper.doubleSub(20.0, 0.0);
		assertTrue(z > x);
		assertTrue(z > y);
	}

	@Test
	public void testDoubleComparison2() {
		assertEquals(Double.compare(1000.0, 1.0) > 0, BooleanHelper.doubleSub(1000.0, 1.0) > 0);
		assertEquals(Double.compare(1000.0, 1.0) < 0, BooleanHelper.doubleSub(1000.0, 1.0) < 0);
		assertEquals(Double.compare(1000.0, 1.0) == 0, BooleanHelper.doubleSub(1000.0, 1.0) == 0);
		
		assertEquals(Double.compare(1.0, 1000.0) > 0, BooleanHelper.doubleSub(1.0, 1000.0) > 0);
		assertEquals(Double.compare(1.0, 1000.0) < 0, BooleanHelper.doubleSub(1.0, 1000.0) < 0);
		assertEquals(Double.compare(1.0, 1000.0) == 0, BooleanHelper.doubleSub(1.0, 1000.0) == 0);
	}

	@Test
	public void testDoubleNegativeComparison() {
		int x = BooleanHelper.doubleSub(0.0, 0.0);
		int y = BooleanHelper.doubleSub(-10.0, 0.0);
		assertTrue(x > y);

		int z = BooleanHelper.doubleSub(-20.0, 0.0);
		assertTrue(z < x);
		assertTrue(z < y);
	}
	
	@Test
	public void testLargeDoubleComparison() {
		int x = BooleanHelper.doubleSub(29380.0, 3266.3);
		int y = BooleanHelper.doubleSub(23562985.124125, 2938.2525);
		assertTrue(y > x);

		int z = BooleanHelper.doubleSub(238628629.23423, 2352.2323);
		assertTrue(z > x);
		assertTrue(z > y);
	}
	
	@Test
	public void testLargeDoubleNegativeComparison() {
		int x = BooleanHelper.doubleSub(-29380.0, 3266.3);
		int y = BooleanHelper.doubleSub(-23562985.124125, 2938.2525);
		assertTrue(y < x);

		int z = BooleanHelper.doubleSub(-238628629.23423, 2352.2323);
		assertTrue(z < x);
		assertTrue(z < y);
	}
	
	@Test
	public void testExamples() {
		double x1 = -1939.9207985389992;
		double x2 = -1941.2134374492741;
		double y1 = -89.0;
		double y2 = -95.11816569743506;
		double z1 = 291.0;
		double z2 = 291.35140748465363;
		
		int a1 = BooleanHelper.doubleSub(x1, 0.0);
		int a2 = BooleanHelper.doubleSub(x2, 0.0);
		assertTrue(a1 > a2);

		int b1 = BooleanHelper.doubleSub(y1, 0.0);
		int b2 = BooleanHelper.doubleSub(y2, 0.0);
		assertTrue(b1 > b2);

		int c1 = BooleanHelper.doubleSub(z1, 0.0);
		int c2 = BooleanHelper.doubleSub(z2, 0.0);
		assertTrue(c1 < c2);
	}
	
	@Test
	public void testDelta() {
		TestCase test = new DefaultTestCase();
		DoublePrimitiveStatement statement1 = new DoublePrimitiveStatement(test);
		DoublePrimitiveStatement statement2 = new DoublePrimitiveStatement(test);
		
		double d1 = statement1.getValue();
		double d2 = statement2.getValue();
		int val = BooleanHelper.doubleSub(d1, d2);
		assertEquals(val > 0, d1 > d2);
		assertEquals(val < 0, d1 < d2);
		assertEquals(val == 0, d1 == d2);
		
		for(int i = 0; i < 100; i++) {
			statement1.delta();
			statement2.delta();
			d1 = statement1.getValue();
			d2 = statement2.getValue();
			val = BooleanHelper.doubleSub(d1, d2);
			assertEquals(val > 0, d1 > d2);
			assertEquals(val < 0, d1 < d2);
			assertEquals(val == 0, d1 == d2);
		}
		for(int i = 0; i < 100; i++) {
			statement1.randomize();
			statement2.randomize();
			d1 = statement1.getValue();
			d2 = statement2.getValue();
			val = BooleanHelper.doubleSub(d1, d2);
			assertEquals(val > 0, d1 > d2);
			assertEquals(val < 0, d1 < d2);
			assertEquals(val == 0, d1 == d2);
		}
	}
	
	@Test
	public void testInfinityAndDouble() {
		assertTrue(BooleanHelper.doubleSub(Double.POSITIVE_INFINITY, 0.0) != 0);
		assertTrue(BooleanHelper.doubleSub(Double.NEGATIVE_INFINITY, 0.0) != 0);
		assertTrue(BooleanHelper.doubleSub(0.0, Double.POSITIVE_INFINITY) != 0);
		assertTrue(BooleanHelper.doubleSub(0.0, Double.NEGATIVE_INFINITY) != 0);
	}

	@Test
	public void testNaNAndDouble() {
		assertTrue(BooleanHelper.doubleSub(Double.NaN, 0.0) != 0);
		assertTrue(BooleanHelper.doubleSub(0.0, Double.NaN) != 0);
	}
	
	@Test
	public void testDoubleNaN2() {
		double p = Double.NaN;
		assertEquals(Double.compare(p, Double.NaN) > 0, BooleanHelper.doubleSub(p, Double.NaN) > 0);
		assertEquals(Double.compare(p, Double.NaN) == 0, BooleanHelper.doubleSub(p, Double.NaN) == 0);
		assertEquals(Double.compare(p, Double.NaN) < 0, BooleanHelper.doubleSub(p, Double.NaN) < 0);
		assertEquals(Double.compare(p, 0.0) < 0, BooleanHelper.doubleSub(p, 0.0) < 0);
		assertEquals(Double.compare(p, 0.0) == 0, BooleanHelper.doubleSub(p, 0.0) == 0);
		assertEquals(Double.compare(p, 0.0) > 0, BooleanHelper.doubleSub(p, 0.0) > 0);
		assertEquals(Double.compare(p, 1.0) > 0 , BooleanHelper.doubleSub(p, 1.0) > 0);
		assertEquals(Double.compare(p, 1.0) == 0 , BooleanHelper.doubleSub(p, 1.0) == 0);
		assertEquals(Double.compare(p, 1.0) < 0 , BooleanHelper.doubleSub(p, 1.0) < 0);
		assertEquals(Double.compare(0.0, p) < 0, BooleanHelper.doubleSub(0.0, p) < 0);
		assertEquals(Double.compare(0.0, p) == 0, BooleanHelper.doubleSub(0.0, p) == 0);
		assertEquals(Double.compare(0.0, p) > 0, BooleanHelper.doubleSub(0.0, p) > 0);
		assertEquals(Double.compare(1.0, p) > 0 , BooleanHelper.doubleSub(1.0, p) > 0);
		assertEquals(Double.compare(1.0, p) == 0 , BooleanHelper.doubleSub(1.0, p) == 0);
		assertEquals(Double.compare(1.0, p) < 0 , BooleanHelper.doubleSub(1.0, p) < 0);
	}

	@Test
	public void testFloatNaN2() {
		float p = Float.NaN;
		assertEquals(Float.compare(p, Float.NaN) > 0, BooleanHelper.floatSub(p, Float.NaN) > 0);
		assertEquals(Float.compare(p, Float.NaN) == 0, BooleanHelper.floatSub(p, Float.NaN) == 0);
		assertEquals(Float.compare(p, Float.NaN) < 0, BooleanHelper.floatSub(p, Float.NaN) < 0);
		assertEquals(Float.compare(p, 0.0f) < 0, BooleanHelper.floatSub(p, 0.0f) < 0);
		assertEquals(Float.compare(p, 0.0f) == 0, BooleanHelper.floatSub(p, 0.0f) == 0);
		assertEquals(Float.compare(p, 0.0f) > 0, BooleanHelper.floatSub(p, 0.0f) > 0);
		assertEquals(Float.compare(p, 1.0f) > 0 , BooleanHelper.floatSub(p, 1.0f) > 0);
		assertEquals(Float.compare(p, 1.0f) == 0 , BooleanHelper.floatSub(p, 1.0f) == 0);
		assertEquals(Float.compare(p, 1.0f) < 0 , BooleanHelper.floatSub(p, 1.0f) < 0);
	}

	
	@Test
	public void testInfinityAndFloat() {
		assertTrue(BooleanHelper.floatSub(Float.POSITIVE_INFINITY, 0.0F) != 0);
		assertTrue(BooleanHelper.floatSub(Float.NEGATIVE_INFINITY, 0.0F) != 0);
		assertTrue(BooleanHelper.floatSub(0.0F, Float.POSITIVE_INFINITY) != 0);
		assertTrue(BooleanHelper.floatSub(0.0F, Float.NEGATIVE_INFINITY) != 0);
	}

	@Test
	public void testNaNAndFloat() {
		assertTrue(BooleanHelper.floatSub(Float.NaN, 0.0F) != 0);
		assertTrue(BooleanHelper.floatSub(0.0F, Float.NaN) != 0);
	}

}
