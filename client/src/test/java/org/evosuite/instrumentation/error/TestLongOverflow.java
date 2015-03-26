package org.evosuite.instrumentation.error;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.evosuite.instrumentation.error.ErrorConditionChecker;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.objectweb.asm.Opcodes;

@RunWith(Parameterized.class)
public class TestLongOverflow {

	private long x;
	private long y;
	
	// Creates the test data
	@Parameters
	public static Collection<Object[]> data() {
		Object[] values = new Object[] { Long.MIN_VALUE, Long.MIN_VALUE/2, 0, Long.MAX_VALUE/ 2, Long.MAX_VALUE};
		List<Object[]> valuePairs = new ArrayList<Object[]>();
		for(Object val1 : values) {
			for(Object val2 : values) {
				valuePairs.add(new Object[] {val1, val2});
			}
		}
		return valuePairs;
	}
	
	public TestLongOverflow(long x, long y) {
		this.x = x;
		this.y = y;
	}


	private void assertOverflow(BigDecimal preciseResult, int distance, long longResult) {
		BigDecimal maxResult = new BigDecimal(Long.MAX_VALUE);
		if(preciseResult.compareTo(maxResult) > 0) {
			assertTrue("Expected negative value for "+x+" and "+y+": "+distance +" for "+longResult, distance <= 0);
			
		} else {
			assertTrue("Expected positive value for "+x+" and "+y+": "+distance +" for "+longResult, distance > 0);
		}		
	}

	
	@Test
	public void testAddOverflow() {
		int result = ErrorConditionChecker.overflowDistance(x, y, Opcodes.LADD);
		assertOverflow(new BigDecimal(x).add(new BigDecimal(y)), result, x + y);
	}

	@Test
	public void testSubOverflow() {
		int result = ErrorConditionChecker.overflowDistance(x, y, Opcodes.LSUB);
		assertOverflow(new BigDecimal(x).subtract(new BigDecimal(y)), result, x - y);
	}

	@Test
	public void testMulOverflow() {
		int result = ErrorConditionChecker.overflowDistance(x, y, Opcodes.LMUL);
		assertOverflow(new BigDecimal(x).multiply(new BigDecimal(y)), result, x * y);
	}

	@Test
	public void testDivOverflow() {
		Assume.assumeTrue(y != 0L);
		System.out.println("x: "+x+", y: "+y);
		int result = ErrorConditionChecker.overflowDistance(x, y, Opcodes.LDIV);
		assertOverflow(new BigDecimal(x).divide(new BigDecimal(y), 10, RoundingMode.HALF_UP), result, x / y);
	}
}
