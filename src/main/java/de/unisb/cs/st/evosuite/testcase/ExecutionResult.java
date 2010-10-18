/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.unisb.cs.st.evosuite.testcase;

import java.util.HashMap;
import java.util.Map;

import de.unisb.cs.st.evosuite.assertion.ComparisonTrace;
import de.unisb.cs.st.evosuite.assertion.InspectorTrace;
import de.unisb.cs.st.evosuite.assertion.NullOutputTrace;
import de.unisb.cs.st.evosuite.assertion.PrimitiveFieldTrace;
import de.unisb.cs.st.evosuite.assertion.PrimitiveOutputTrace;
import de.unisb.cs.st.evosuite.assertion.StringOutputTrace;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;


public class ExecutionResult {
	public enum Outcome { SUCCESS, FAIL };
	public Outcome result;
	public TestCase test;
	public Mutation mutation;
	public Throwable exception;
	public int exception_statement = 0;
	public Map<Integer, Throwable> exceptions = new HashMap<Integer, Throwable>();
	public ExecutionTrace trace;
	public StringOutputTrace output_trace;
	public ComparisonTrace comparison_trace;
	public PrimitiveOutputTrace primitive_trace;
	public InspectorTrace inspector_trace;
	public PrimitiveFieldTrace field_trace;
	public NullOutputTrace null_trace;
	public boolean touched = false;

	public ExecutionResult(TestCase t, Mutation m) {
		exception = null;
		exception_statement = 0;
		trace = null;
		mutation = m;
		test = t;
	}
	
	public ExecutionResult clone() {
		ExecutionResult copy = new ExecutionResult(test, mutation);
		copy.trace = trace.clone();
		return copy;
	}
	
	public String toString() {
		String result = "";
		result += "Trace:";
		result += trace;
		return result;
	}
	
	// Killed mutants
	//List<Mutation> dead = new ArrayList<Mutation>();
	
	// Live mutants
	//List<Mutation> live = new ArrayList<Mutation>();
	
	// Objects with mutants
	//List<Mutation> have_object = new ArrayList<Mutation>();
	
	// Mutated methods called
	//List<Mutation> have_methodcall = new ArrayList<Mutation>();
	
	// Mutations touched
	//List<Mutation> touched = new ArrayList<Mutation>();
	
	
	//Map<Mutation, List<ExecutionTracer.TraceEntry> > mutant_traces = new HashMap<Mutation, List<TraceEntry> >();

	//Map<Long, Double>   distance = new HashMap<Long, Double>();
	//Map<Long, Integer>  levenshtein = new HashMap<Long, Integer>();
	
	// Fitness(M) = A * have_object(M) + B * have_methodcall(M) + C * touched(M) + D * impact(M)

	// impact(M) = Sum(E * distance_method * 3)
	
	//int num_mutants;	
	//double average_length = 0.0;
	//double max_length = 0.0;
	
	//int getNumKilled() {
	//	return dead.size();
	//}
}
