/**
 * 
 */
package org.evosuite.assertion;

import java.lang.reflect.Array;

import org.evosuite.testcase.ArrayStatement;
import org.evosuite.testcase.AssignmentStatement;
import org.evosuite.testcase.CodeUnderTestException;
import org.evosuite.testcase.PrimitiveStatement;
import org.evosuite.testcase.Scope;
import org.evosuite.testcase.StatementInterface;
import org.evosuite.testcase.VariableReference;

/**
 * @author Gordon Fraser
 * 
 */
public class ArrayTraceObserver extends AssertionTraceObserver<ArrayTraceEntry> {

	/** {@inheritDoc} */
	@Override
	public synchronized void afterStatement(StatementInterface statement, Scope scope,
	        Throwable exception) {
		// By default, no assertions are created for statements that threw exceptions
		if(exception != null)
			return;

		visitReturnValue(statement, scope);
		visitDependencies(statement, scope);
	}

	private Object[] getArray(Object val) {
		int arrlength = Array.getLength(val);
		Object[] outputArray = new Object[arrlength];
		for (int i = 0; i < arrlength; ++i) {
			outputArray[i] = Array.get(val, i);
		}
		return outputArray;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.AssertionTraceObserver#visit(org.evosuite.testcase.StatementInterface, org.evosuite.testcase.Scope, org.evosuite.testcase.VariableReference)
	 */
	/** {@inheritDoc} */
	@Override
	protected void visit(StatementInterface statement, Scope scope, VariableReference var) {
		logger.debug("Checking array " + var);
		try {
			// Need only legal values
			if (var == null)
				return;

			// We don't need assertions on constant values
			if (statement instanceof PrimitiveStatement<?>)
				return;

			// We don't need assertions on array assignments
			if (statement instanceof AssignmentStatement)
				return;

			// We don't need assertions on array declarations
			if (statement instanceof ArrayStatement)
				return;

			Object object = var.getObject(scope);

			// We don't need to compare to null
			if (object == null)
				return;

			// We are only interested in arrays
			if (!object.getClass().isArray())
				return;

			// We are only interested in primitive arrays
			if (!object.getClass().getComponentType().isPrimitive())
				return;

			if (var.getComponentClass() == null)
				return;

			logger.debug("Observed value " + object + " for statement "
			        + statement.getCode());
			trace.addEntry(statement.getPosition(), var, new ArrayTraceEntry(var,
			        getArray(object)));

		} catch (CodeUnderTestException e) {
			logger.debug("", e);
		}
	}
}
