/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * @author Gordon Fraser
 * 
 */
public class EnumPrimitiveStatement<T extends Enum<T>> extends PrimitiveStatement<T> {

	private static final long serialVersionUID = -7027695648061887082L;

	private T[] constants;

	private final Class<T> enumClass;

	@SuppressWarnings("unchecked")
	public EnumPrimitiveStatement(TestCase tc, Class<T> clazz) {
		super(tc, clazz, null);
		enumClass = clazz;
		try {
			if (clazz.getEnumConstants().length > 0) {
				this.value = clazz.getEnumConstants()[0];
				constants = clazz.getEnumConstants();

			} else {
				// Coping with empty enums is a bit of a mess
				constants = (T[]) new Enum[0];
			}
		} catch (Throwable t) {
			// Loading the Enum class might fail
			constants = (T[]) new Enum[0];
		}
	}

	@SuppressWarnings("unchecked")
	public EnumPrimitiveStatement(TestCase tc, T value) {
		super(tc, value.getClass(), value);
		enumClass = (Class<T>) retrieveEnumClass(value.getClass());
		constants = (T[]) retrieveEnumClass(value.getClass()).getEnumConstants();
	}

	private static Class<?> retrieveEnumClass(Class<?> clazz) {
		if (clazz.isEnum())
			return clazz;
		else if (clazz.getEnclosingClass() != null && clazz.getEnclosingClass().isEnum())
			return clazz.getEnclosingClass();
		else if (clazz.getDeclaringClass() != null && clazz.getDeclaringClass().isEnum())
			return clazz.getDeclaringClass();
		else
			throw new RuntimeException("Cannot find enum class: " + clazz);
	}

	public Class<?> getEnumClass() {
		return enumClass;
	}

	public List<T> getEnumValues() {
		return Arrays.asList(constants);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#delta()
	 */
	@Override
	public void delta() {
		if (constants.length == 0)
			return;

		int pos = 0;
		for (pos = 0; pos < constants.length; pos++) {
			if (constants[pos].equals(value)) {
				break;
			}
		}
		boolean delta = Randomness.nextBoolean();
		if (delta) {
			pos++;
		} else {
			pos--;
		}
		if (pos >= constants.length) {
			pos = 0;
		} else if (pos < 0) {
			pos = constants.length - 1;
		}
		value = constants[pos];
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#zero()
	 */
	@Override
	public void zero() {
		if (constants.length == 0)
			return;

		value = constants[0];
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#pushBytecode(org.objectweb.asm.commons.GeneratorAdapter)
	 */
	@Override
	protected void pushBytecode(GeneratorAdapter mg) {
		mg.getStatic(Type.getType(enumClass), value.name(), Type.getType(enumClass));
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#randomize()
	 */
	@Override
	public void randomize() {
		if (constants.length > 1) {
			int pos = Randomness.nextInt(constants.length);
			value = constants[pos];
		}
	}

}
