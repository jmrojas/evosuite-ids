/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import java.lang.reflect.Type;

import org.objectweb.asm.commons.GeneratorAdapter;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * @author fraser
 * 
 */
public class BytePrimitiveStatement extends NumericalPrimitiveStatement<Byte> {

	/**
	 * @param tc
	 * @param type
	 * @param value
	 */
	public BytePrimitiveStatement(TestCase tc, Byte value) {
		super(tc, byte.class, value);
	}

	/**
	 * @param tc
	 * @param type
	 * @param value
	 */
	public BytePrimitiveStatement(TestCase tc) {
		super(tc, byte.class, (byte) 0);
	}

	/**
	 * @param tc
	 * @param type
	 * @param value
	 */
	public BytePrimitiveStatement(TestCase tc, Type type) {
		super(tc, type, (byte) 0);
	}

	private static final long serialVersionUID = -8123457944460041347L;

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#zero()
	 */
	@Override
	public void zero() {
		value = (byte) 0;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#pushBytecode(org.objectweb.asm.commons.GeneratorAdapter)
	 */
	@Override
	public void pushBytecode(GeneratorAdapter mg) {
		mg.push((value).shortValue());
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#delta()
	 */
	@Override
	public void delta() {
		int delta = Randomness.nextInt(2 * Properties.MAX_DELTA) - Properties.MAX_DELTA;
		value = (byte) (value.byteValue() + delta);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#increment(java.lang.Object)
	 */
	@Override
	public void increment(long delta) {
		value = (byte) (value + delta);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#randomize()
	 */
	@Override
	public void randomize() {
		if (Randomness.nextDouble() >= P_pool)
			value = (byte) (Randomness.nextInt(256) - 128);
		else
			value = (byte) (primitive_pool.getRandomInt());
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#increment()
	 */
	@Override
	public void increment() {
		increment((byte) 1);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#increment()
	 */
	@Override
	public void decrement() {
		increment((byte) -1);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.NumericalPrimitiveStatement#setMid(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setMid(Byte min, Byte max) {
		value = (byte) (min + ((max - min) / 2));
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.NumericalPrimitiveStatement#isPositive()
	 */
	@Override
	public boolean isPositive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void negate() {
		value = (byte) -value;
	}
}
