//
//Copyright (C) 2005 United States Government as represented by the
//Administrator of the National Aeronautics and Space Administration
//(NASA).  All Rights Reserved.
//
//This software is distributed under the NASA Open Source Agreement
//(NOSA), version 1.3.  The NOSA has been approved by the Open Source
//Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
//directory tree for the complete NOSA document.
//
//THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
//KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
//LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
//SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
//A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
//THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
//DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//
package de.unisb.cs.st.evosuite.symbolic.expr;

public enum Operator {

	//ALL with ### are not yet implemented in the execute of the according expression
	// Simple operators
	NEG("-",1),//negate
	DIV("/", 2), //
	MUL("*", 2), //
	MINUS("-", 2), //
	PLUS("+", 2), //
	REM("%",2),//Remainder

	// Logical operators
	AND("&", 2), //
	OR("|", 2), //
	XOR("^", 2), //

	// special operators
	D2L("d2l", 1), // isn't used anywhere
	L2D("l2d", 1), // isn't used anywhere
	CMP("cmp", 2), // this is automatically removed so don't need it in execute

	// Integer operators
	IAND("iand", 2), //
	IOR("ior", 2), //
	SHR("ishr", 2), //
	SHL("ishl", 2), //
	IXOR("ixor", 2), //

	// More complex Math operators
	
	//Unary
	ABS("abs", 1), // 
	ACOS("acos", 1), // 
	ASIN("asin", 1), // 
	ATAN("atan", 1), // 
	CBRT("cbrt", 1), // 
	CEIL("ceil",1), // 
	COS("cos", 1), // 
	COSH("cosh", 1), // 
	EXP("exp", 1), // 
	EXPM1("expm1", 1), // 
	FLOOR("floor",1), // 
	GETEXPONENT("getExponent", 1), //  
	LOG("log", 1), // 
	LOG10("log10", 1), // 
	LOG1P("log1p", 1), // 
	NEXTUP("nextUp", 1), // 
	RINT("rint", 1), // 
	ROUND("round", 1), // this is never used
	SIGNUM("signum", 1), // 
	SIN("sin", 1), // 
	SINH("sinh", 1), // 
	SQRT("sqrt", 1), // 
	TAN("tan", 1), // 
	TANH("tanh", 1), // 
	TODEGREES("toDegrees", 1), // 
	TORADIANS("toRadians", 1), // 
	ULP("ulp", 1), // 
	
	//Binary
	ATAN2("atan2", 2), // ###
	COPYSIGN("copySign", 2), // ###
	HYPOT("hypot", 2), // ###
	IEEEREMAINDER("IEEEremainder", 2), // ###
	MAX("max", 2), // ###
	MIN("min", 2), // ###
	NEXTAFTER("nextAfter", 2), // ###
	POW("pow", 2), // ###
	SCALB("scalb", 2), // ###
	
	
	// String comparisons
	EQUALSIGNORECASE("equalsIgnoreCase", 2),
	EQUALS("equals", 2),
	ENDSWITH("endsWith", 2),
	CONTAINS("contains", 2),
	STARTSWITH("startsWith", 3),
	REGIONMATCHES("regionMatches", 6), 


	// String operators
	TRIM("trim", 1), 
	LENGTH("length", 1), 
	TOLOWERCASE("toLowerCase", 1), 
	TOUPPERCASE("toUpperCase", 1),
	
	COMPARETO("compareTo", 2), 
	COMPARETOIGNORECASE("compareToIgnoreCase", 2),
	CONCAT("concat", 2), 
	APPEND("append", 2), 
	INDEXOFC("indexOfC", 2),
	INDEXOFS("indexOfS", 2),
	CHARAT("charAt", 2),
	
	REPLACEC("replacec", 3),
	REPLACECS("replacecs", 3), 
	REPLACEALL("replaceAll", 3),
	REPLACEFIRST("replaceFirst", 3),
	INDEXOFCI("indexOfCI", 3),
	INDEXOFSI("indexOfSI", 3),
	SUBSTRING("substring", 3);

	private final String str;
	private final int arity;

	Operator(String str, int arity) {
		this.str = str;
		this.arity = arity;
	}

	public int getArity() {
		return arity;
	}

	@Override
	public String toString() {
		return " " + str + " ";
	}
}