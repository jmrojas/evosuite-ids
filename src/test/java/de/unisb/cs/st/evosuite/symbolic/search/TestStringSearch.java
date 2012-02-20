package de.unisb.cs.st.evosuite.symbolic.search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.unisb.cs.st.evosuite.symbolic.expr.Comparator;
import de.unisb.cs.st.evosuite.symbolic.expr.Constraint;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstraint;
import de.unisb.cs.st.evosuite.symbolic.expr.Operator;
import de.unisb.cs.st.evosuite.symbolic.expr.StringComparison;
import de.unisb.cs.st.evosuite.symbolic.expr.StringConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.StringMultipleComparison;
import de.unisb.cs.st.evosuite.symbolic.expr.StringVariable;

public class TestStringSearch {

	@Test
	public void testEqualsTrueConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		Expression<?> strComp = new StringComparison(strVar, Operator.EQUALS, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.NE, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( const2.equals(result.get("test1").toString()) );
	}
	
	@Test
	public void testEqualsFalseConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "foo";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		Expression<?> strComp = new StringComparison(strVar, Operator.EQUALS, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.EQ, new IntegerConstant(0)));
		
		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( !const2.equals(result.get("test1").toString()) );
	}
	
	@Test
	public void testEqualsIgnoreCaseTrueConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "Fest";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		Expression<?> strComp = new StringComparison(strVar, Operator.EQUALSIGNORECASE, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.NE, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( const2.equalsIgnoreCase(result.get("test1").toString()) );
	}
	
	@Test
	public void testEqualsIgnoreCaseFalseConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "FOO";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		Expression<?> strComp = new StringComparison(strVar, Operator.EQUALSIGNORECASE, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.EQ, new IntegerConstant(0)));
		
		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertFalse( const2.equalsIgnoreCase(result.get("test1").toString()) );
	}
	
	@Test
	public void testStartsWithTrueConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		IntegerConstant offs_expr = new IntegerConstant(2);
		ArrayList<Expression<?>> other = new ArrayList<Expression<?>>();
		other.add(offs_expr);

		Expression<?> strComp = new StringMultipleComparison(strVar, Operator.STARTSWITH, strConst, other, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.NE, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( (result.get("test1").toString()).startsWith(const2, 2) );
	}
	
	@Test
	public void testStartsWithFalseConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "footest";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		IntegerConstant offs_expr = new IntegerConstant(3);
		ArrayList<Expression<?>> other = new ArrayList<Expression<?>>();
		other.add(offs_expr);

		Expression<?> strComp = new StringMultipleComparison(strVar, Operator.STARTSWITH, strConst, other, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.EQ, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertFalse( (result.get("test1").toString()).startsWith(const2, 3) );
	}
	
	@Test
	public void testEndsWithTrueConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);

		Expression<?> strComp = new StringComparison(strVar, Operator.ENDSWITH, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.NE, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( (result.get("test1").toString()).endsWith(const2) );
	}
	
	@Test
	public void testEndsWithFalseConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "footest";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);

		Expression<?> strComp = new StringComparison(strVar, Operator.ENDSWITH, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.EQ, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertFalse( (result.get("test1").toString()).endsWith(const2) );
	}
	
	@Test
	public void testContainsTrueConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foo";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);

		Expression<?> strComp = new StringComparison(strVar, Operator.CONTAINS, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.NE, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( (result.get("test1").toString()).contains(const2) );
	}
	
	@Test
	public void testContainsFalseConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "fotesto";
		String const2 = "test";
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);

		Expression<?> strComp = new StringComparison(strVar, Operator.CONTAINS, strConst, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.EQ, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertFalse( (result.get("test1").toString()).contains(const2) );
	}
	
	@Test
	public void testRegionMatchesICTrueConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "fotesto";
		String const2 = "rtestooo";
		boolean ignore_case = true;
		int offset1 = 0;
		int offset2 = 1;
		int len = 4;
		
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		IntegerConstant len_expr = new IntegerConstant(len);
		IntegerConstant offs_two = new IntegerConstant(offset2);
		IntegerConstant offs_one = new IntegerConstant(offset1);
		IntegerConstant ign_case = new IntegerConstant(ignore_case ? 1 : 0);
		
		ArrayList<Expression<?>> other = new ArrayList<Expression<?>>();
		other.add(offs_one);
		other.add(offs_two);
		other.add(len_expr);
		other.add(ign_case);
		
		Expression<?> strComp = new StringMultipleComparison(strVar, Operator.REGIONMATCHES, strConst, other, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.NE, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertTrue( (result.get("test1").toString()).regionMatches(ignore_case, offset1, const2, offset2, len) );
	}
	
	public void testRegionMatchesICFalseConstant() {
		List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();
		String var1 = "foTESTo";
		String const2 = "rtestooo";
		boolean ignore_case = true;
		int offset1 = 2;
		int offset2 = 1;
		int len = 4;
		
		StringVariable strVar = new StringVariable("test1", var1, var1, var1);
		StringConstant strConst = new StringConstant(const2);
		IntegerConstant len_expr = new IntegerConstant(len);
		IntegerConstant offs_two = new IntegerConstant(offset2);
		IntegerConstant offs_one = new IntegerConstant(offset1);
		IntegerConstant ign_case = new IntegerConstant(ignore_case ? 1 : 0);
		
		ArrayList<Expression<?>> other = new ArrayList<Expression<?>>();
		other.add(offs_one);
		other.add(offs_two);
		other.add(len_expr);
		other.add(ign_case);
		
		Expression<?> strComp = new StringMultipleComparison(strVar, Operator.REGIONMATCHES, strConst, other, 0L);
		constraints.add(new IntegerConstraint(strComp, Comparator.EQ, new IntegerConstant(0)));

		Seeker skr = new Seeker();
		Map<String, Object> result = skr.getModel(constraints);
		assertNotNull(result);
		assertNotNull(result.get("test1"));
		assertFalse( (result.get("test1").toString()).regionMatches(ignore_case, offset1, const2, offset2, len) );
	}
	
}
