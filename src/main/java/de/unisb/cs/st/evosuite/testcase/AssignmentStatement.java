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
package de.unisb.cs.st.evosuite.testcase;

import java.io.PrintStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.commons.GeneratorAdapter;

import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * An assignment statement assigns a variable to another variable. This is only
 * used to assign to array indices
 * 
 * @author Gordon Fraser
 * 
 */
public class AssignmentStatement extends AbstractStatement {

	private static final long serialVersionUID = 2051431241124468349L;

	protected VariableReference parameter;

	public AssignmentStatement(TestCase tc, VariableReference var, VariableReference value) {
		super(tc, var);
		this.parameter = value;
		// TODO:
		// Assignment of an "unassignable" type may happen if we have no generator for
		// the target class, as we then attempt generating a superclass and try to case
		// down to the actual class
		//
		//assert (this.parameter.getType().equals(value.getType()));
		//if (!this.parameter.isAssignableTo(retval.getType()))
		//	logger.warn(parameter.getSimpleClassName() + " " + parameter.getName()
		//	        + " is not assignable to " + retval.getSimpleClassName() + " "
		//	        + retval.getName() + " in test " + tc.toCode());
		//assert (this.parameter.isAssignableTo(retval.getType()));
		//
		//		assert (retval.getVariableClass().isAssignableFrom(parameter.getVariableClass()));
	}

	@Override
	public StatementInterface copy(TestCase newTestCase, int offset) {
		try {
			//logger.info("CLoning : " + getCode());
			VariableReference newParam = parameter.copy(newTestCase, offset);
			VariableReference newTarget;

			// FIXXME: Return value should always be an existing variable
			//if (retval.getAdditionalVariableReference() != null)
			newTarget = retval.copy(newTestCase, offset);
			//else
			//	newTarget = retval.copy(newTestCase, offset);
			//newTarget = new VariableReferenceImpl(newTestCase, retval.getType());
			AssignmentStatement copy = new AssignmentStatement(newTestCase, newTarget,
			        newParam);
			// copy.assertions = copyAssertions(newTestCase, offset);

			//logger.info("Copy of statement is: " + copy.getCode());
			return copy;
		} catch (Exception e) {
			logger.info("Error cloning statement " + getCode());
			logger.info("In test: " + this.tc.toCode());
			logger.info("New test: " + newTestCase.toCode());
			e.printStackTrace();
			assert (false) : e.toString();
		}
		return null;
	}

	@Override
	public Throwable execute(final Scope scope, PrintStream out)
	        throws InvocationTargetException, IllegalArgumentException,
	        IllegalAccessException, InstantiationException {

		return super.exceptionHandler(new Executer() {

			@Override
			public void execute() throws InvocationTargetException,
			        IllegalArgumentException, IllegalAccessException,
			        InstantiationException {
				try {
					final Object value = parameter.getObject(scope);
					retval.setObject(scope, value);
					//} catch (CodeUnderTestException e) {
					//	throw CodeUnderTestException.throwException(e.getCause());
				} catch (IllegalArgumentException e) {
					// FIXXME: IllegalArgumentException may happen when we only have generators
					// for an abstract supertype and not the concrete type that we need!
					throw e;
				} catch (Throwable e) {
					throw new EvosuiteError(e);
				}
			}

			@Override
			public Set<Class<? extends Throwable>> throwableExceptions() {
				Set<Class<? extends Throwable>> t = new HashSet<Class<? extends Throwable>>();
				t.add(AssertionError.class);
				return t;
			}
		});

	}

	@Override
	public Set<VariableReference> getVariableReferences() {
		Set<VariableReference> vars = new HashSet<VariableReference>();
		vars.add(retval);
		vars.add(parameter);

		if (retval.getAdditionalVariableReference() != null)
			vars.add(retval.getAdditionalVariableReference());
		if (parameter.getAdditionalVariableReference() != null)
			vars.add(parameter.getAdditionalVariableReference());
		vars.addAll(getAssertionReferences());

		return vars;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#replace(de.unisb.cs.st.evosuite.testcase.VariableReference, de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	public void replace(VariableReference var1, VariableReference var2) {
		if (parameter.equals(var1))
			parameter = var2;
		//else if (retval.equals(var1))
		//	retval = var2;
		else
			parameter.replaceAdditionalVariableReference(var1, var2);
		//else if (var1.equals(retval.getAdditionalVariableReference()))
		//	retval.setAdditionalVariableReference(var2);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + retval.hashCode()
		        + +((parameter == null) ? 0 : parameter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignmentStatement other = (AssignmentStatement) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (retval == null) {
			if (other.retval != null)
				return false;
		} else if (!retval.equals(other.retval))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.Statement#getBytecode(org.objectweb.
	 * asm.commons.GeneratorAdapter)
	 */
	@Override
	public void getBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        Throwable exception) {
		parameter.loadBytecode(mg, locals);

		Class<?> clazz = parameter.getVariableClass();
		if (!clazz.equals(retval.getVariableClass())) {
			mg.cast(org.objectweb.asm.Type.getType(clazz),
			        org.objectweb.asm.Type.getType(retval.getVariableClass()));
		}

		parameter.storeBytecode(mg, locals);
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

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#isValid()
	 */
	@Override
	public boolean isValid() {
		assert (super.isValid());
		parameter.getStPosition();
		//if (!retval.getVariableClass().isAssignableFrom(parameter.getVariableClass())) {
		//	logger.error("Type mismatch: " + retval.getVariableClass() + " and "
		//	        + parameter.getVariableClass());
		//	logger.error(tc.toCode());
		//}

		//assert (retval.getVariableClass().isAssignableFrom(parameter.getVariableClass()));
		return true;
	}

	@Override
	public boolean same(StatementInterface s) {
		if (this == s)
			return true;
		if (s == null)
			return false;
		if (getClass() != s.getClass())
			return false;

		AssignmentStatement other = (AssignmentStatement) s;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.same(other.parameter))
			return false;
		if (retval == null) {
			if (other.retval != null)
				return false;
		} else if (!retval.same(other.retval))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#mutate(de.unisb.cs.st.evosuite.testcase.TestCase)
	 */
	@Override
	public boolean mutate(TestCase test, AbstractTestFactory factory) {
		assert (isValid());

		// Either mutate parameter, or source
		if (Randomness.nextDouble() < 0.5) {
			// TODO: Should we restrict to field and array assignments?
			List<VariableReference> objects = test.getObjects(parameter.getType(),
			                                                  retval.getStPosition());
			objects.remove(retval);
			objects.remove(parameter);
			Iterator<VariableReference> var = objects.iterator();
			while (var.hasNext()) {
				// Only try other array/field references
				VariableReference v = var.next();
				if (v.getAdditionalVariableReference() == null)
					var.remove();
				// else if (v instanceof ArrayReference) {
				//	if (!v.isAssignableTo(retval) || !retval.isAssignableTo(v))
				//		var.remove();

				//}
			}
			//for (VariableReference v : objects) {
			//	if (!v.isAssignableTo(retval.getType())) {
			//		assert (false);
			//	}
			//}
			if (!objects.isEmpty()) {
				VariableReference newRetVal = Randomness.choice(objects);
				retval = newRetVal;
				assert (isValid());
				//test.clone();

				return true;
			}

		} else {

			List<VariableReference> objects = test.getObjects(parameter.getType(),
			                                                  retval.getStPosition());
			objects.remove(retval);
			objects.remove(parameter);
			if (!objects.isEmpty()) {
				parameter = Randomness.choice(objects);
				assert (isValid());
				return true;
			}
		}

		return false;
	}

	@Override
	public AccessibleObject getAccessibleObject() {
		return null;
	}

	@Override
	public boolean isAssignmentStatement() {
		return true;
	}
}
