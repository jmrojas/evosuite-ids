/**
 * 
 */
package de.unisb.cs.st.evosuite.contracts;

import java.lang.reflect.Method;

import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;

/**
 * An object must never equal null
 * 
 * @author Gordon Fraser
 * 
 */
public class EqualsNullContract extends Contract {

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.contracts.Contract#check(de.unisb.cs.st.evosuite.testcase.TestCase, de.unisb.cs.st.evosuite.testcase.Statement, de.unisb.cs.st.evosuite.testcase.Scope, java.lang.Throwable)
	 */
	@Override
	public boolean check(StatementInterface statement, Scope scope, Throwable exception) {
		for (Object object : getAllObjects(scope)) {
			if (object == null)
				continue;

			// We do not want to call equals if it is the default implementation
			Class<?>[] parameters = { Object.class };
			try {
				Method equalsMethod = object.getClass().getMethod("equals", parameters);
				if (equalsMethod.getDeclaringClass().equals(Object.class))
					continue;

			} catch (SecurityException e1) {
				continue;
			} catch (NoSuchMethodException e1) {
				continue;
			}

			try {
				// An object always has to equal itself
				if (object.equals(null))
					return false;

			} catch (Throwable t) {
				continue;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "Equals null check";
	}

}
