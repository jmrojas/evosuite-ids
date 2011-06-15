/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.testcase;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.objectweb.asm.commons.GeneratorAdapter;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.primitives.PrimitivePool;
import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * Statement assigning a primitive numeric value
 * 
 * @author Gordon Fraser
 * 
 * @param <T>
 */
public class PrimitiveStatement<T> extends AbstractStatement {

	private static final long serialVersionUID = -7721106626421922833L;

	private static int MAX_STRING = Properties.STRING_LENGTH;

	private static int MAX_INT = Properties.MAX_INT;

	private static double P_pool = Properties.PRIMITIVE_POOL;

	private static PrimitivePool primitive_pool = PrimitivePool.getInstance();

	/**
	 * Create random primitive statement
	 * 
	 * @param reference
	 * @param clazz
	 * @return
	 */
	public static PrimitiveStatement<?> getRandomStatement(TestCase tc, Type type, int position, Type clazz) {

		if (clazz == boolean.class) {
			return new PrimitiveStatement<Boolean>(tc, type, Randomness.nextBoolean());
		} else if (clazz == int.class) {
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<Integer>(tc, type, new Integer(
						(Randomness.nextInt(2 * MAX_INT) - MAX_INT)));
			} else {
				return new PrimitiveStatement<Integer>(tc, type, primitive_pool.getRandomInt());
			}

		} else if (clazz == char.class) {
			// Only ASCII chars?
			return new PrimitiveStatement<Character>(tc, type, (Randomness.nextChar()));
		} else if (clazz == long.class) {
			int max = Math.min(MAX_INT, 32767);
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<Long>(tc, type, new Long((Randomness.nextInt(2 * max) - max)));
			} else {
				return new PrimitiveStatement<Long>(tc, type, primitive_pool.getRandomLong());
			}

		} else if (clazz.equals(double.class)) {
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<Double>(tc, type, (Randomness.nextInt(2 * MAX_INT) - MAX_INT)
						+ Randomness.nextDouble());
			} else {
				return new PrimitiveStatement<Double>(tc, type, primitive_pool.getRandomDouble());
			}

		} else if (clazz == float.class) {
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<Float>(tc, type, (Randomness.nextInt(2 * MAX_INT) - MAX_INT)
						+ Randomness.nextFloat());
			} else {
				return new PrimitiveStatement<Float>(tc, type, primitive_pool.getRandomFloat());
			}

		} else if (clazz == short.class) {
			int max = Math.min(MAX_INT, 32767);
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<Short>(tc, type, new Short((short) (Randomness.nextInt(2 * max) - max)));
			} else {
				return new PrimitiveStatement<Short>(tc, type, new Short((short) primitive_pool.getRandomInt()));
			}

		} else if (clazz == byte.class) {
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<Byte>(tc, type, new Byte((byte) (Randomness.nextInt(256) - 128)));
			} else {
				return new PrimitiveStatement<Byte>(tc, type, new Byte((byte) (primitive_pool.getRandomInt())));
			}

		} else if (clazz.equals(String.class)) {
			if (Randomness.nextDouble() >= P_pool) {
				return new PrimitiveStatement<String>(tc, type, Randomness.nextString(Randomness.nextInt(MAX_STRING)));
			} else {
				return new PrimitiveStatement<String>(tc, type, primitive_pool.getRandomString());
			}
		}
		logger.error("Getting unknown type: " + clazz + " / " + clazz.getClass());

		assert (false);
		return null;
	}

	private static String insertCharAt(String s, int pos, char c) {
		return s.substring(0, pos) + c + s.substring(pos);
	}

	private static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);
	}

	private static String replaceCharAt(String s, int pos, char c) {
		return s.substring(0, pos) + c + s.substring(pos + 1);
	}

	/**
	 * The value
	 */
	T value;

	/**
	 * Constructor
	 * 
	 * @param reference
	 * @param value
	 */
	public PrimitiveStatement(TestCase tc, Type type, T value) {
		super(tc, new VariableReferenceImpl(tc, type));
		this.value = value;
	}

	@Override
	public StatementInterface clone(TestCase newTestCase) {
		return new PrimitiveStatement<T>(newTestCase, retval.getType(), value);
	}

	public void decrement() {
		increment(-1);
	}

	@SuppressWarnings("unchecked")
	public void delta() {

		// double delta = 40.0 * Randomness.nextDouble() - 20.0;
		int delta = Randomness.nextInt(40) - 20;
		if (value instanceof Boolean) {
			value = (T) Boolean.valueOf(!((Boolean) value).booleanValue());
		} else if (value instanceof Integer) {
			value = (T) new Integer(((Integer) value).intValue() + delta);
		} else if (value instanceof Character) {
			value = (T) new Character((char) (((Character) value).charValue() + delta));
		} else if (value instanceof Long) {
			value = (T) new Long(((Long) value).longValue() + delta);
		} else if (value instanceof Double) {
			value = (T) new Double(((Double) value).doubleValue() + delta + Randomness.nextDouble());
		} else if (value instanceof Float) {
			value = (T) new Float(((Float) value).floatValue() + delta + Randomness.nextFloat());
		} else if (value instanceof Short) {
			value = (T) new Short((short) (((Short) value).shortValue() + delta));
		} else if (value instanceof Byte) {
			value = (T) new Byte((byte) (((Byte) value).byteValue() + delta));
		} else if (value instanceof String) {
			deltaString();
		}

	}

	@Override
	public boolean equals(Object s) {
		if (this == s) {
			return true;
		}
		if (s == null) {
			return false;
		}
		if (getClass() != s.getClass()) {
			return false;
		}

		PrimitiveStatement<?> ps = (PrimitiveStatement<?>) s;
		return (retval.equals(ps.retval) && value.equals(ps.value));
	}

	@Override
	public Throwable execute(Scope scope, PrintStream out) throws InvocationTargetException, IllegalArgumentException,
			IllegalAccessException, InstantiationException {
		// Add primitive variable to pool
		scope.set(retval, value);
		return exceptionThrown;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.Statement#getBytecode(org.objectweb.
	 * asm.commons.GeneratorAdapter)
	 */
	@Override
	public void getBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals, Throwable exception) {
		Class<?> clazz = value.getClass();
		if (!clazz.equals(retval.getVariableClass())) {
			mg.cast(org.objectweb.asm.Type.getType(clazz), org.objectweb.asm.Type.getType(retval.getVariableClass()));
		}
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
			mg.push(((Boolean) value).booleanValue());
		} else if (clazz.equals(Character.class) || clazz.equals(char.class)) {
			mg.push(((Character) value).charValue());
		} else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
			mg.push(((Integer) value).intValue());
		} else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
			mg.push(((Short) value).shortValue());
		} else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
			mg.push(((Long) value).longValue());
		} else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
			mg.push(((Float) value).floatValue());
		} else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
			mg.push(((Double) value).doubleValue());
		} else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
			mg.push(((Byte) value).byteValue());
		} else if (clazz.equals(String.class)) {
			mg.push(((String) value));
		} else {
			logger.fatal("Found primitive of unknown type: " + clazz.getName());
		}
		retval.storeBytecode(mg, locals);
		// mg.storeLocal(retval.statement);
	}

	@Override
	public String getCode(Throwable exception) {
		if (retval.getVariableClass().equals(char.class) || retval.getVariableClass().equals(Character.class)) {
			return ((Class<?>) retval.getType()).getSimpleName() + " " + retval.getName() + " = '"
					+ StringEscapeUtils.escapeJava(value.toString()) + "';";
		} else if (retval.getVariableClass().equals(String.class)) {
			return ((Class<?>) retval.getType()).getSimpleName() + " " + retval.getName() + " = \""
					+ StringEscapeUtils.escapeJava((String) value) + "\";";
		} else if (retval.getVariableClass().equals(float.class) || retval.getVariableClass().equals(Float.class)) {
			return ((Class<?>) retval.getType()).getSimpleName() + " " + retval.getName() + " = " + value + "F;";
		} else if (retval.getVariableClass().equals(long.class) || retval.getVariableClass().equals(Long.class)) {
			return ((Class<?>) retval.getType()).getSimpleName() + " " + retval.getName() + " = " + value + "L;";
		} else {
			return ((Class<?>) retval.getType()).getSimpleName() + " " + retval.getName() + " = " + value + ";";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.Statement#getUniqueVariableReferences()
	 */
	@Override
	public List<VariableReference> getUniqueVariableReferences() {
		return new ArrayList<VariableReference>(getVariableReferences());
	}

	public T getValue() {
		return value;
	}

	@Override
	public Set<VariableReference> getVariableReferences() {
		Set<VariableReference> references = new HashSet<VariableReference>();
		references.add(retval);
		return references;
	}

	@Override
	public int hashCode() {
		final int prime = 21;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public void increment() {
		increment(1);
	}

	@SuppressWarnings("unchecked")
	public void increment(double val) {
		if (value instanceof Double) {
			value = (T) new Double(((Double) value).doubleValue() + val);
		} else if (value instanceof Float) {
			value = (T) new Float(((Float) value).floatValue() + val);
		}
	}

	@SuppressWarnings("unchecked")
	public void increment(long val) {
		if (value instanceof Boolean) {
			value = (T) Boolean.valueOf(!((Boolean) value).booleanValue());
		} else if (value instanceof Integer) {
			value = (T) new Integer((int) (((Integer) value).intValue() + val));
		} else if (value instanceof Character) {
			value = (T) new Character((char) (((Character) value).charValue() + val));
		} else if (value instanceof Long) {
			value = (T) new Long(((Long) value).longValue() + val);
		} else if (value instanceof Double) {
			value = (T) new Double(((Double) value).doubleValue() + val);
		} else if (value instanceof Float) {
			value = (T) new Float(((Float) value).floatValue() + val);
		} else if (value instanceof Short) {
			value = (T) new Short((short) (((Short) value).shortValue() + val));
		} else if (value instanceof Byte) {
			value = (T) new Byte((byte) (((Byte) value).byteValue() + val));
		} else if (value instanceof String) {
			incrementString();
		}
	}

	/**
	 * Create random primitive statement
	 * 
	 * @param reference
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void randomize() {
		if (value instanceof Boolean) {
			value = (T) new Boolean(Randomness.nextBoolean());
		} else if (value instanceof Integer) {
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) new Integer((Randomness.nextInt(2 * MAX_INT) - MAX_INT));
			} else {
				value = (T) new Integer(primitive_pool.getRandomInt());
			}
		} else if (value instanceof Character) {
			value = (T) new Character(Randomness.nextChar());
		} else if (value instanceof Long) {
			int max = Math.min(MAX_INT, 32767);
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) new Long((Randomness.nextInt(2 * max) - max));
			} else {
				value = (T) new Long(primitive_pool.getRandomLong());
			}
		} else if (value instanceof Double) {
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) new Double((Randomness.nextInt(2 * MAX_INT) - MAX_INT) + Randomness.nextDouble());
			} else {
				value = (T) new Double(primitive_pool.getRandomDouble());
			}

		} else if (value instanceof Float) {
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) new Float((Randomness.nextInt(2 * MAX_INT) - MAX_INT) + Randomness.nextFloat());
			} else {
				value = (T) new Float(primitive_pool.getRandomFloat());
			}
		} else if (value instanceof Short) {
			int max = Math.min(MAX_INT, 32767);
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) new Short((short) (Randomness.nextInt(2 * max) - max));
			} else {
				value = (T) new Short((short) primitive_pool.getRandomInt());
			}
		} else if (value instanceof Byte) {
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) new Byte((byte) (Randomness.nextInt(256) - 128));
			} else {
				value = (T) new Byte((byte) (primitive_pool.getRandomInt()));
			}
		} else if (value instanceof String) {
			if (Randomness.nextDouble() >= P_pool) {
				value = (T) Randomness.nextString(Randomness.nextInt(MAX_STRING));
			} else {
				value = (T) primitive_pool.getRandomString();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.StatementInterface#replace(de.unisb.
	 * cs.st.evosuite.testcase.VariableReference,
	 * de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	public void replace(VariableReference var1, VariableReference var2) {
	}

	@Override
	public boolean same(StatementInterface s) {
		if (this == s) {
			return true;
		}
		if (s == null) {
			return false;
		}
		if (getClass() != s.getClass()) {
			return false;
		}

		PrimitiveStatement<?> ps = (PrimitiveStatement<?>) s;
		return (retval.same(ps.retval) && value.equals(ps.value));
	}

	public void setValue(T val) {
		this.value = val;
	}

	@SuppressWarnings("unchecked")
	public void zero() {
		if (value instanceof Boolean) {
			value = (T) Boolean.FALSE;
		} else if (value instanceof Integer) {
			value = (T) new Integer(0);
		} else if (value instanceof Character) {
			value = (T) new Character((char) 0);
		} else if (value instanceof Long) {
			value = (T) new Long(0);
		} else if (value instanceof Double) {
			value = (T) new Double(0.0);
		} else if (value instanceof Float) {
			value = (T) new Float(0.0);
		} else if (value instanceof Short) {
			value = (T) new Short((short) 0);
		} else if (value instanceof Byte) {
			value = (T) new Byte((byte) 0);

		}
	}

	@SuppressWarnings("unchecked")
	private void deltaString() {

		String s = (String) value;

		final double P2 = 1d / 3d;
		double P = 1d / s.length();
		// Delete
		if (Randomness.nextDouble() < P2) {
			for (int i = s.length(); i > 0; i--) {
				if (Randomness.nextDouble() < P) {
					// logger.info("Before remove at "+i+": '"+s+"'");
					s = removeCharAt(s, i - 1);
					// logger.info("After remove: '"+s+"'");
				}
			}
		}
		P = 1d / s.length();
		// Change
		if (Randomness.nextDouble() < P2) {
			for (int i = 0; i < s.length(); i++) {
				if (Randomness.nextDouble() < P) {
					// logger.info("Before change: '"+s+"'");
					s = replaceCharAt(s, i, Randomness.nextChar());
					// logger.info("After change: '"+s+"'");
				}
			}
		}

		// Insert
		if (Randomness.nextDouble() < P2) {
			// for(int i = 0; i < s.length(); i++) {
			// if(Randomness.nextDouble() < P) {
			int pos = 0;
			if (s.length() > 0) {
				pos = Randomness.nextInt(s.length());
			}
			s = StringInsert(s, pos);
			// }
			// }
		}
		value = (T) s;
		// logger.info("Mutated string now is: "+value);
	}

	@SuppressWarnings("unchecked")
	private void incrementString() {

		String s = (String) value;

		final double P2 = 1d / 3d;
		double P = 1d / s.length();
		P = 1d / s.length();
		// Change
		if (Randomness.nextDouble() < P2) {
			for (int i = 0; i < s.length(); i++) {
				if (Randomness.nextDouble() < P) {
					// logger.info("Before change: '"+s+"'");
					s = replaceCharAt(s, i, Randomness.nextChar());
					// logger.info("After change: '"+s+"'");
				}
			}
		}

		value = (T) s;
		// logger.info("Mutated string now is: "+value);
	}

	private String StringInsert(String s, int pos) {
		final double ALPHA = 0.5;
		int count = 1;

		while ((Randomness.nextDouble() <= Math.pow(ALPHA, count)) && (s.length() < MAX_STRING)) {
			count++;
			// logger.info("Before insert: '"+s+"'");
			s = insertCharAt(s, pos, Randomness.nextChar());
			// logger.info("After insert: '"+s+"'");
		}
		return s;
	}

}
