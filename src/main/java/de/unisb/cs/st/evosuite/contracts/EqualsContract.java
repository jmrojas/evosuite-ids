/**
 * 
 */
package de.unisb.cs.st.evosuite.contracts;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;

/**
 * An object always has to equal itself
 * 
 * @author Gordon Fraser
 * 
 */
public class EqualsContract extends Contract {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Contract.class);

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
				if (!object.equals(object))
					return false;

			} catch (NullPointerException e) {
				// No nullpointer exceptions may be thrown if the parameter was not null
				return false;
			} catch (Throwable t) {
				continue;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "Equality check";
	}

}
