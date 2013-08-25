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
package org.evosuite.contracts;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.evosuite.testcase.MethodStatement;
import org.evosuite.testcase.Scope;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.VariableReference;
import org.evosuite.testcase.TestCaseExecutor.TimeoutExceeded;
import org.evosuite.utils.GenericMethod;


/**
 * <p>HashCodeReturnsNormallyContract class.</p>
 *
 * @author Gordon Fraser
 */
public class HashCodeReturnsNormallyContract extends Contract {

	/* (non-Javadoc)
	 * @see org.evosuite.contracts.Contract#check(org.evosuite.testcase.Statement, org.evosuite.testcase.Scope, java.lang.Throwable)
	 */
	/** {@inheritDoc} */
	@Override
	public ContractViolation check(StatementInterface statement, Scope scope, Throwable exception) {
		for(VariableReference var : getAllVariables(scope)) {
			Object object = scope.getObject(var);
			if (object == null)
				continue;

			// We do not want to call hashCode if it is the default implementation
			Class<?>[] parameters = {};
			try {
				Method equalsMethod = object.getClass().getMethod("hashCode", parameters);
				if (equalsMethod.getDeclaringClass().equals(Object.class))
					continue;

			} catch (SecurityException e1) {
				continue;
			} catch (NoSuchMethodException e1) {
				continue;
			}

			try {
				// hashCode must not throw an exception
				object.hashCode();

			} catch (Throwable t) {
				if (!(t instanceof TimeoutExceeded))
					return new ContractViolation(this, statement, exception, var);
			}
		}

		return null;
	}

	@Override
	public void addAssertionAndComments(StatementInterface statement,
			List<VariableReference> variables, Throwable exception) {
		TestCase test = statement.getTestCase();
		
		VariableReference a = variables.get(0);

		try {
			Method hashCodeMethod = a.getGenericClass().getRawClass().getMethod("hashCode", new Class<?>[] {});

			GenericMethod method = new GenericMethod(hashCodeMethod, a.getGenericClass());

			StatementInterface st1 = new MethodStatement(test, method, a, Arrays.asList(new VariableReference[] {}));
			test.addStatement(st1, statement.getPosition());
			st1.addComment("Throws exception: "+exception.getMessage());
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "hashCode returns normally check";
	}
}
