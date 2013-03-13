package org.evosuite.symbolic.expr;

import java.util.StringTokenizer;
import java.util.Vector;

import org.evosuite.Properties;
import org.evosuite.symbolic.ConstraintTooLongException;
import org.evosuite.symbolic.DSEStats;
import org.evosuite.symbolic.expr.bv.IntegerConstant;
import org.evosuite.symbolic.expr.bv.StringBinaryComparison;
import org.evosuite.symbolic.expr.bv.StringComparison;
import org.evosuite.symbolic.expr.bv.StringMultipleComparison;
import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.expr.token.HasMoreTokensExpr;
import org.evosuite.symbolic.expr.token.TokenizerExpr;
import org.evosuite.symbolic.search.RegexDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StringConstraint extends Constraint<String> {

	static Logger log = LoggerFactory.getLogger(StringConstraint.class);

	public StringConstraint(StringComparison left, Comparator comp,
			IntegerConstant right) {
		super();
		this.left = left;
		this.cmp = comp;
		this.right = right;
		if (getSize() > Properties.DSE_CONSTRAINT_LENGTH) {
			DSEStats.reportConstraintTooLong(getSize());
			throw new ConstraintTooLongException(getSize());
		}
	}

	private final StringComparison left;
	private final Comparator cmp;
	private final IntegerConstant right;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187023627540040535L;

	@Override
	public Comparator getComparator() {
		return cmp;
	}

	@Override
	public Expression<?> getLeftOperand() {
		return left;
	}

	@Override
	public Expression<?> getRightOperand() {
		return right;
	}

	@Override
	public String toString() {
		return left + cmp.toString() + right;
	}

	@Override
	public Constraint<String> negate() {
		return new StringConstraint(left, cmp.not(), right);
	}

	public double getStringDist() {
		Expression<?> exprLeft = this.getLeftOperand();
		Comparator cmpr = this.getComparator();
		double distance = 0.0;

		if (exprLeft instanceof StringBinaryComparison) {
			StringBinaryComparison scTarget = (StringBinaryComparison) exprLeft;
			distance = getStringDistance(scTarget);
			log.debug("Calculating distance of constraint " + this);
		} else if (exprLeft instanceof StringMultipleComparison) {
			StringMultipleComparison scTarget = (StringMultipleComparison) exprLeft;
			distance = getStringDistance(scTarget);
			log.debug("Calculating distance of constraint " + this);
		} else if (exprLeft instanceof HasMoreTokensExpr) {
			HasMoreTokensExpr hasMoreTokensExpr = (HasMoreTokensExpr) exprLeft;
			distance = getStringDistance(hasMoreTokensExpr);
			log.debug("Calculating distance of constraint " + this);
		} else {
			assert (false) : "Invalid string comparison";
		}
		assert (this.right.concreteValue.intValue() == 0);
		if (cmpr == Comparator.NE) {
			return distance;
		} else {
			// if we don't want to satisfy return 0
			// if not satisfied Long.MAX_VALUE else
			return distance > 0 ? 0.0 : Double.MAX_VALUE;
		}
	}

	private static double getStringDistance(HasMoreTokensExpr hasMoreTokensExpr) {
		TokenizerExpr tokenizerExpr = hasMoreTokensExpr.getTokenizerExpr();

		StringValue string = tokenizerExpr.getString();
		StringValue delimiter = tokenizerExpr.getDelimiter();
		int nextTokenCount = tokenizerExpr.getNextTokenCount();

		String concreteString = string.execute();
		String concreteDelimiter = delimiter.execute();

		if (concreteString.length() < concreteDelimiter.length()
				* nextTokenCount) {
			// not enough characters in original string to perform so many
			// nextToken operations
			return Double.MAX_VALUE;
		}

		StringTokenizer tokenizer = new StringTokenizer(concreteString,
				concreteDelimiter);
		Vector<String> tokens = new Vector<String>();
		while (tokenizer.hasMoreTokens()) {
			tokens.add(tokenizer.nextToken());
		}

		if (tokens.size() > nextTokenCount) {
			// we already have enough tokens to make this true
			return 0;
		} else {
			return StrEquals("", concreteDelimiter);
		}
	}

	private static double getStringDistance(StringBinaryComparison comparison) {
		try {
			String first = comparison.getLeftOperand().execute();
			String second = (String) comparison.getRightOperand().execute();

			switch (comparison.getOperator()) {
			case EQUALSIGNORECASE:
				return StrEqualsIgnoreCase(first, second);
			case EQUALS:
				log.debug("Edit distance between " + first + " and " + second
						+ " is: " + StrEquals(first, second));
				return StrEquals(first, second);
			case ENDSWITH:
				return StrEndsWith(first, second);
			case CONTAINS:
				return StrContains(first, second);
			case PATTERNMATCHES:
				return RegexMatches(second, first);
			case APACHE_ORO_PATTERN_MATCHES:
				return RegexMatches(second, first);

			default:
				log.warn("StringComparison: unimplemented operator!"
						+ comparison.getOperator());
				return Double.MAX_VALUE;
			}
		} catch (Exception e) {
			return Double.MAX_VALUE;
		}
	}

	private static double StrContains(String val, CharSequence subStr) {
		int val_length = val.length();
		int subStr_length = subStr.length();
		double min_dist = Double.MAX_VALUE;
		String sub = subStr.toString();

		if (subStr_length > val_length) {
			return avmDistance(val, sub);
			// return editDistance(val, sub);
		} else {
			int diff = val_length - subStr_length;
			for (int i = 0; i < diff + 1; i++) {
				double res = StrEquals(val.substring(i, subStr_length + i), sub);
				if (res < min_dist) {
					min_dist = res;
				}
			}
		}
		return min_dist;
	}

	private static double StrEndsWith(String value, String suffix) {
		int len = Math.min(suffix.length(), value.length());
		String val1 = value.substring(value.length() - len);
		return StrEquals(val1, suffix);
	}

	private static double avmDistance(String s, String t) {
		double distance = Math.abs(s.length() - t.length());
		int max = Math.min(s.length(), t.length());
		for (int i = 0; i < max; i++) {
			distance += normalize(Math.abs(s.charAt(i) - t.charAt(i)));
		}
		return distance;
	}

	private static double getStringDistance(StringMultipleComparison comparison) {
		try {
			String first = comparison.getLeftOperand().execute();
			String second = (String) comparison.getRightOperand().execute();

			switch (comparison.getOperator()) {
			case STARTSWITH:
				long start = (Long) comparison.getOther().get(0).execute();
				return StrStartsWith(first, second, (int) start);
			case REGIONMATCHES:
				long frstStart = (Long) comparison.getOther().get(0).execute();
				long secStart = (Long) comparison.getOther().get(1).execute();
				long length = (Long) comparison.getOther().get(2).execute();
				long ignoreCase = (Long) comparison.getOther().get(3).execute();

				return StrRegionMatches(first, (int) frstStart, second,
						(int) secStart, (int) length, ignoreCase != 0);
			default:
				log.warn("StringComparison: unimplemented operator!"
						+ comparison.getOperator());
				return Double.MAX_VALUE;
			}
		} catch (Exception e) {
			return Double.MAX_VALUE;
		}
	}

	private static double StrRegionMatches(String value, int thisStart,
			String string, int start, int length, boolean ignoreCase) {
		if (value == null || string == null)
			throw new NullPointerException();

		if (start < 0 || string.length() - start < length) {
			return length - string.length() + start;
		}

		if (thisStart < 0 || value.length() - thisStart < length) {
			return length - value.length() + thisStart;
		}
		if (length <= 0) {
			return 0;
		}

		String s1 = value;
		String s2 = string;
		if (ignoreCase) {
			s1 = s1.toLowerCase();
			s2 = s2.toLowerCase();
		}

		return StrEquals(s1.substring(thisStart, length + thisStart),
				s2.substring(start, length + start));
	}

	private static double StrEqualsIgnoreCase(String first, String second) {
		return StrEquals(first.toLowerCase(), second.toLowerCase());
	}

	private static double StrEquals(String first, Object second) {
		if (first.equals(second))
			return 0; // Identical
		else {
			return avmDistance(first, second.toString());
			// return editDistance(first, second.toString());
		}
	}

	private static double StrStartsWith(String value, String prefix, int start) {
		int len = Math.min(prefix.length(), value.length());
		int end = (start + len > value.length()) ? value.length() : start + len;
		return StrEquals(value.substring(start, end), prefix);
	}

	private static double RegexMatches(String val, String regex) {
		return RegexDistance.getDistance(val, regex);
	}

}
