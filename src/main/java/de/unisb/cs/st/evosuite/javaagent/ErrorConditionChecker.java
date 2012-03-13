/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import org.objectweb.asm.Opcodes;

/**
 * @author fraser
 * 
 */
public class ErrorConditionChecker {

	public static int scale(float value) {
		return (Integer.MAX_VALUE - 2) * (int) Math.ceil((value / (value + 1.0F)));
	}

	public static int scale(double value) {
		return (Integer.MAX_VALUE - 2) * (int) Math.ceil((value / (value + 1.0)));
	}

	public static int scale(long value) {
		return (Integer.MAX_VALUE - 2) * (int) Math.ceil((value / (value + 1L)));
	}

	public static int overflowDistance(int op1, int op2, int opcode) {
		int result = 0;

		// result = overflowDistance(opcode, op1, op2);
		// if(result <= 0) 
		//   throw OverflowException()

		switch (opcode) {
		case Opcodes.IADD:
			result = op1 + op2;
			if (op1 > 0 && op2 > 0) {
				System.out.println("Overflow check for integer addition " + op1 + " + "
				        + op2 + ": " + result + " - result = "
				        + (result < 0 ? -1 : (Integer.MAX_VALUE - result)));
				// result has to be < 0 for overflow
				return result < 0 ? -1 : (Integer.MAX_VALUE - result);
			} else if (op1 < 0 && op2 < 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : (Integer.MAX_VALUE - Math.abs(result));
			break;
		case Opcodes.ISUB:
			result = op1 - op2;
			if (op1 > 0 && op2 < 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : (Integer.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : (Integer.MAX_VALUE - Math.abs(result));
			break;
		case Opcodes.IMUL:
			result = op1 * op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0
				return result < 0 ? -1 : (Integer.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be < 0
				return result < 0 ? -1 : (Integer.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0
				return result > 0 ? -1 : (Integer.MAX_VALUE - Math.abs(result));
			else if (op1 > 0 && op2 < 0)
				// result has to be > 0
				return result > 0 ? -1 : (Integer.MAX_VALUE - Math.abs(result));
			break;
		}
		return 1;
	}

	public static int overflowDistance(float op1, float op2, int opcode) {
		float result = 0.0F;
		switch (opcode) {
		case Opcodes.FADD:
			result = op1 + op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : scale(Float.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : scale(Float.MAX_VALUE - Math.abs(result));
		case Opcodes.FSUB:
			result = op1 - op2;
			if (op1 > 0 && op2 < 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : scale(Float.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : scale(Float.MAX_VALUE - Math.abs(result));
		case Opcodes.FMUL:
			result = op1 * op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0
				return result < 0 ? -1 : scale(Float.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be < 0
				return result < 0 ? -1 : scale(Float.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0
				return result > 0 ? -1 : scale(Float.MAX_VALUE - Math.abs(result));
			else if (op1 > 0 && op2 < 0)
				// result has to be > 0
				return result > 0 ? -1 : scale(Float.MAX_VALUE - Math.abs(result));
		}
		return 1;
	}

	public static int overflowDistance(double op1, double op2, int opcode) {
		double result = 0.0;
		switch (opcode) {
		case Opcodes.DADD:
			result = op1 + op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : scale(Double.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : scale(Double.MAX_VALUE - Math.abs(result));
		case Opcodes.DSUB:
			result = op1 - op2;
			if (op1 > 0 && op2 < 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : scale(Double.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : scale(Double.MAX_VALUE - Math.abs(result));
		case Opcodes.DMUL:
			result = op1 * op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0
				return result < 0 ? -1 : scale(Double.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be < 0
				return result < 0 ? -1 : scale(Double.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0
				return result > 0 ? -1 : scale(Double.MAX_VALUE - Math.abs(result));
			else if (op1 > 0 && op2 < 0)
				// result has to be > 0
				return result > 0 ? -1 : scale(Double.MAX_VALUE - Math.abs(result));
		}
		return 1;
	}

	public static int overflowDistance(long op1, long op2, int opcode) {
		long result = 0L;

		switch (opcode) {
		case Opcodes.LADD:
			result = op1 + op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : scale(Long.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : scale(Long.MAX_VALUE - Math.abs(result));
		case Opcodes.LSUB:
			result = op1 - op2;
			if (op1 > 0 && op2 < 0)
				// result has to be < 0 for overflow
				return result < 0 ? -1 : scale(Long.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0 for overflow
				return result > 0 ? -1 : scale(Long.MAX_VALUE - Math.abs(result));
		case Opcodes.LMUL:
			result = op1 * op2;
			if (op1 > 0 && op2 > 0)
				// result has to be < 0
				return result < 0 ? -1 : scale(Long.MAX_VALUE - result);
			else if (op1 < 0 && op2 < 0)
				// result has to be < 0
				return result < 0 ? -1 : scale(Long.MAX_VALUE - result);
			else if (op1 < 0 && op2 > 0)
				// result has to be > 0
				return result > 0 ? -1 : scale(Long.MAX_VALUE - Math.abs(result));
			else if (op1 > 0 && op2 < 0)
				// result has to be > 0
				return result > 0 ? -1 : scale(Long.MAX_VALUE - Math.abs(result));
		}
		return 1;
	}
}
