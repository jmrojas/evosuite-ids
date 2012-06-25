/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.evosuite.testcase;

import org.evosuite.Properties;
import org.evosuite.utils.Randomness;
import org.objectweb.asm.commons.GeneratorAdapter;


/**
 * @author fraser
 * 
 */
public class StringPrimitiveStatement extends PrimitiveStatement<String> {

	private static final long serialVersionUID = 274445526699835887L;

	/**
	 * @param tc
	 * @param type
	 * @param value
	 */
	public StringPrimitiveStatement(TestCase tc, String value) {
		super(tc, String.class, value);
	}

	/**
	 * @param tc
	 * @param type
	 * @param value
	 */
	public StringPrimitiveStatement(TestCase tc) {
		super(tc, String.class, "");
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#zero()
	 */
	@Override
	public void zero() {
		value = "";
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#pushBytecode(org.objectweb.asm.commons.GeneratorAdapter)
	 */
	@Override
	public void pushBytecode(GeneratorAdapter mg) {
		mg.push(value);
	}

	private static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);
	}

	private static String replaceCharAt(String s, int pos, char c) {
		return s.substring(0, pos) + c + s.substring(pos + 1);
	}

	private static String insertCharAt(String s, int pos, char c) {
		return s.substring(0, pos) + c + s.substring(pos);
	}

	private String StringInsert(String s, int pos) {
		final double ALPHA = 0.5;
		int count = 1;

		while (Randomness.nextDouble() <= Math.pow(ALPHA, count)
		        && s.length() < Properties.STRING_LENGTH) {
			count++;
			// logger.info("Before insert: '"+s+"'");
			s = insertCharAt(s, pos, Randomness.nextChar());
			// logger.info("After insert: '"+s+"'");
		}
		return s;
	}

	@Override
	public void delta() {

		String s = value;

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
			if (s.length() > 0)
				pos = Randomness.nextInt(s.length());
			s = StringInsert(s, pos);
			// }
			// }
		}
		value = s;
		// logger.info("Mutated string now is: "+value);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#increment(java.lang.Object)
	 */
	public void increment() {
		String s = value;
		if (s.isEmpty()) {
			s += Randomness.nextChar();
		} else {
			s = replaceCharAt(s, Randomness.nextInt(s.length()), Randomness.nextChar());
		}

		value = s;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.PrimitiveStatement#randomize()
	 */
	@Override
	public void randomize() {
		if (Randomness.nextDouble() >= Properties.PRIMITIVE_POOL)
			value = Randomness.nextString(Randomness.nextInt(Properties.STRING_LENGTH));
		else
			value = primitive_pool.getRandomString();
	}

}
