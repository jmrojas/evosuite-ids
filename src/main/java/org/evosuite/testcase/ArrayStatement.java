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

package org.evosuite.testcase;

import java.io.PrintStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Randomness;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;


/**
 * An array statement creates a new array
 * 
 * @author Gordon Fraser
 * 
 */
public class ArrayStatement extends AbstractStatement {
	
	public static int determineDimensions(java.lang.reflect.Type type) {
		String name = type.toString().replace("class", "").trim();
	    int count = 0;
	    for (int i=0; i < name.length(); i++) {
	        if (name.charAt(i) == '[') {
	             count++;
	        }
	    }
	    return count;
	}
	
	private static int[] createRandom(int dimensions) {
		int[] result = new int[dimensions];
		for (int idx = 0; idx < dimensions; idx++) {
			result[idx] = Randomness.nextInt(Properties.MAX_ARRAY) + 1;
		}
		return result;
	}

	private static final long serialVersionUID = -2858236370873914156L;

	private int[] lengths;

	public ArrayStatement(TestCase tc, ArrayReference arrayReference) {
		this(tc, arrayReference, createRandom(determineDimensions(arrayReference.getType())));
	}
	
	public ArrayStatement(TestCase tc, java.lang.reflect.Type type) {
		this(tc, type, createRandom(determineDimensions(type)));
	}

	public ArrayStatement(TestCase tc, java.lang.reflect.Type type, int length) {
		this(tc, type, new int[] {length});
	}

	public ArrayStatement(TestCase tc, java.lang.reflect.Type type, int[] length) {
		this(tc, new ArrayReference(tc, new GenericClass(type), length), length);
	}
	
	public ArrayStatement(TestCase tc, ArrayReference arrayReference, int[] length) {
		super(tc, arrayReference);
		this.lengths = length;
	}
	
	public int size() {
		assert lengths.length == 1;
		return lengths[0];
	}

	public void setSize(int size) {
		assert lengths.length == 1;
		this.lengths[0] = size;
	}

	@Override
	public StatementInterface copy(TestCase newTestCase, int offset) {
		ArrayStatement copy = new ArrayStatement(newTestCase, retval.getType(), lengths);
		// copy.assertions = copyAssertions(newTestCase, offset);
		return copy;
	}

	@Override
	public boolean equals(Object s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		ArrayStatement as = (ArrayStatement) s;
		if (!Arrays.equals(lengths, as.lengths))
			return false;
		if (retval.equals(as.retval)) {
			return true;
		} else {
			return false;
		}

		// if (!Arrays.equals(variables, other.variables))
		// return false;

	}

	@Override
	public Throwable execute(Scope scope, PrintStream out)
	        throws InvocationTargetException, IllegalArgumentException,
	        IllegalAccessException, InstantiationException {
		// Add array variable to pool
		try {
			retval.setObject(scope,
			                 Array.newInstance((Class<?>) retval.getComponentType(),
			                                   lengths));
		} catch (CodeUnderTestException e) {
			exceptionThrown = e.getCause();
		}
		return exceptionThrown;

	}

	@Override
	public Set<VariableReference> getVariableReferences() {
		Set<VariableReference> references = new HashSet<VariableReference>();
		references.add(retval);
		return references;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.StatementInterface#replace(org.evosuite.testcase.VariableReference, org.evosuite.testcase.VariableReference)
	 */
	@Override
	public void replace(VariableReference var1, VariableReference var2) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = retval.hashCode();
		result = prime * result + Arrays.hashCode(lengths);	
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.testcase.Statement#getBytecode(org.objectweb.
	 * asm.commons.GeneratorAdapter)
	 */
	@Override
	public void getBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        Throwable exception) {
		if (lengths.length > 1) {
			throw new RuntimeException("Not yet implemented for multidimensional arrays!");
		}
		mg.push(lengths[0]);
		mg.newArray(Type.getType((Class<?>) retval.getComponentType()));
		retval.storeBytecode(mg, locals);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.evosuite.testcase.Statement#getUniqueVariableReferences()
	 */
	@Override
	public List<VariableReference> getUniqueVariableReferences() {
		return new ArrayList<VariableReference>(getVariableReferences());
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.StatementInterface#isValid()
	 */
	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public boolean same(StatementInterface s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		ArrayStatement as = (ArrayStatement) s;
		if (!Arrays.equals(lengths, as.lengths))
			return false;
		if (retval.same(as.retval)) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.AbstractStatement#mutate(org.evosuite.testcase.TestCase, org.evosuite.testcase.AbstractTestFactory)
	 */
	@Override
	public boolean mutate(TestCase test, AbstractTestFactory factory) {
		int maxAssignment = 0;
		for (StatementInterface statement : test) {
			for (VariableReference var : statement.getVariableReferences()) {
				if (var.getAdditionalVariableReference() == this.retval) {
					VariableReference currentVar = var;
					while (currentVar instanceof FieldReference) {
						currentVar = ((FieldReference) currentVar).getSource();
					}
					if (!(currentVar instanceof ArrayIndex)) {
						LoggingUtils.getEvoLogger().error("Found assignment to array without ArrayIndex:");
						LoggingUtils.getEvoLogger().error(test.toCode());
						LoggingUtils.getEvoLogger().error(statement.getPosition() + ", "
						                                          + statement.getCode());
					}
					ArrayIndex index = (ArrayIndex) currentVar;
					maxAssignment = Math.max(maxAssignment, index.getArrayIndex());
				}
			}
		}

		int dim = Randomness.nextInt(lengths.length - 1);
		int newLength = lengths[dim];
		while (newLength == lengths[dim]) {
			if (Randomness.nextDouble() <= Properties.RANDOM_PERTURBATION) {
				newLength = Randomness.nextInt(maxAssignment, 
											   Math.max(maxAssignment + 1,
													   	Properties.MAX_ARRAY)) + 1;
			} else {
				int max = Math.min(Math.abs(lengths[dim] - maxAssignment - 1), Properties.MAX_DELTA);
				if (max > 0)
					newLength = lengths[dim] + Randomness.nextInt(2 * max) - max;
				else
					newLength = lengths[dim] + Randomness.nextInt(Properties.MAX_DELTA);
			}
		}

		// TODO: Need to make sure this doesn't happen by construction
		if (newLength <= 0)
			newLength = 1;
		
		logger.debug("Changing array length from " + lengths[dim] + " to " + newLength);
		lengths[dim] = newLength;
		return true;
	}

	@Override
	public AccessibleObject getAccessibleObject() {
		return null;
	}

	@Override
	public boolean isAssignmentStatement() {
		return false;
	}

	public void setLengths(int[] lengths) {
		this.lengths = lengths;
	}
	
	public int[] getLengths() {
		return lengths;
	}
}
