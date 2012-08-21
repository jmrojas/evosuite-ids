package org.evosuite.symbolic;

import static org.evosuite.symbolic.Assertions.checkEquals;

import org.evosuite.symbolic.dsc.ConcolicMarker;

public class TestCase26 {

	private static final String STRING_VALUE_PART_1 = "Togliere sta";

	private static final String STRING_VALUE_PART_2 = " roba";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string0 = ConcolicMarker.mark(STRING_VALUE_PART_1, "string0");
		String string1 = STRING_VALUE_PART_1;
		String string2 = string0.concat(STRING_VALUE_PART_2);
		String string3 = string1.concat(STRING_VALUE_PART_2);
		int int0 = string2.length();
		int int1 = string3.length();
		checkEquals(int0, int1);
	}

}
