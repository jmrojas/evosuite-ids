/**
 * 
 */
package de.unisb.cs.st.evosuite.contracts;

import java.lang.reflect.Method;

import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor.TimeoutExceeded;

/**
 * @author Gordon Fraser
 * 
 */
public class HashCodeReturnsNormallyContract extends Contract {

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.contracts.Contract#check(de.unisb.cs.st.evosuite.testcase.Statement, de.unisb.cs.st.evosuite.testcase.Scope, java.lang.Throwable)
	 */
	@Override
	public boolean check(StatementInterface statement, Scope scope, Throwable exception) {
		for (Object object : getAllObjects(scope)) {
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
					return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "hashCode returns normally check";
	}
}
