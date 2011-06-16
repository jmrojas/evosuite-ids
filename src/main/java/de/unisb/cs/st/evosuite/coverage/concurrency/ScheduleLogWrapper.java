/**
 * 
 */
package de.unisb.cs.st.evosuite.coverage.concurrency;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.objectweb.asm.commons.GeneratorAdapter;

import de.unisb.cs.st.evosuite.assertion.Assertion;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.VariableReference;

/**
 * Wraps a StatementInterface and calls
 * 
 * @author Sebastian Steenbuck
 * 
 */
public class ScheduleLogWrapper implements StatementInterface {

	public interface callReporter {
		public void callStart(StatementInterface caller, Integer threadID);

		public void callEnd(StatementInterface caller, Integer threadID);

		public Set<Integer> getScheduleForStatement(StatementInterface st);
	}

	private static Logger logger = Logger.getLogger(ScheduleLogWrapper.class);

	public final StatementInterface wrapped;
	private callReporter callReporter;

	public ScheduleLogWrapper(StatementInterface wrapped) {
		assert (wrapped != null) : "undefined behaviour lurks behind one statement beeing executed by multiple threads";
		this.wrapped = wrapped;
	}

	@Override
	public StatementInterface clone() {
		throw new UnsupportedOperationException();
	}

	public void setCallReporter(callReporter callReporter) {
		;
		this.callReporter = callReporter;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#addAssertion(de.unisb.cs.st.evosuite.assertion.Assertion)
	 */
	@Override
	public void addAssertion(Assertion assertion) {
		wrapped.addAssertion(assertion);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#equals(de.unisb.cs.st.evosuite.testcase.StatementInterface)
	 */
	@Override
	public boolean equals(Object s) {
		if (s instanceof ScheduleLogWrapper) {
			return wrapped.equals(((ScheduleLogWrapper) s).wrapped);
		} else {
			return wrapped.equals(s);
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#execute(de.unisb.cs.st.evosuite.testcase.Scope, java.io.PrintStream)
	 */
	@Override
	public Throwable execute(Scope scope, PrintStream out)
	        throws InvocationTargetException, IllegalArgumentException,
	        IllegalAccessException, InstantiationException {
		assert (LockRuntime.controller != null);
		assert (callReporter != null) : "SetCallReporter/2 must be called before a wrapped statement may be executed";
		try {
			callReporter.callStart(this,
			                       LockRuntime.controller.getThreadID(Thread.currentThread()));
		} catch (Throwable e) {
			logger.fatal("test", e);
		}
		Throwable t = wrapped.execute(scope, out);
		callReporter.callEnd(this,
		                     LockRuntime.controller.getThreadID(Thread.currentThread()));
		return t;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getAssertionCode()
	 */
	@Override
	public String getAssertionCode() {
		return wrapped.getAssertionCode();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getAssertions()
	 */
	@Override
	public Set<Assertion> getAssertions() {
		return wrapped.getAssertions();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getBytecode(org.objectweb.asm.commons.GeneratorAdapter, java.util.Map, java.lang.Throwable)
	 */
	@Override
	public void getBytecode(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        Throwable exception) {
		wrapped.getBytecode(mg, locals, exception);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getCode()
	 */
	@Override
	public String getCode() {
		return wrapped.getCode();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getCode(java.lang.Throwable)
	 */
	@Override
	public String getCode(Throwable exception) {
		return wrapped.getCode(exception);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getDeclaredExceptions()
	 */
	@Override
	public Set<Class<?>> getDeclaredExceptions() {
		return wrapped.getDeclaredExceptions();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getPosition()
	 */
	@Override
	public int getPosition() {
		return wrapped.getPosition();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getReturnClass()
	 */
	@Override
	public Class<?> getReturnClass() {
		return wrapped.getReturnClass();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getReturnType()
	 */
	@Override
	public Type getReturnType() {
		return wrapped.getReturnType();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getReturnValue()
	 */
	@Override
	public VariableReference getReturnValue() {
		return wrapped.getReturnValue();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getUniqueVariableReferences()
	 */
	@Override
	public List<VariableReference> getUniqueVariableReferences() {
		return wrapped.getUniqueVariableReferences();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#getVariableReferences()
	 */
	@Override
	public Set<VariableReference> getVariableReferences() {
		return wrapped.getVariableReferences();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#replace(de.unisb.cs.st.evosuite.testcase.VariableReference, de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	public void replace(VariableReference var1, VariableReference var2) {
		wrapped.replace(var1, var2);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#hasAssertions()
	 */
	@Override
	public boolean hasAssertions() {
		return wrapped.hasAssertions();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#references(de.unisb.cs.st.evosuite.testcase.VariableReference)
	 */
	@Override
	public boolean references(VariableReference var) {
		return wrapped.references(var);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#removeAssertion(de.unisb.cs.st.evosuite.assertion.Assertion)
	 */
	@Override
	public void removeAssertion(Assertion assertion) {
		wrapped.removeAssertion(assertion);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#removeAssertions()
	 */
	@Override
	public void removeAssertions() {
		wrapped.removeAssertions();
	}

	@Override
	public StatementInterface clone(TestCase tc) {
		return new ScheduleLogWrapper(wrapped.clone(tc));
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#isValid()
	 */
	@Override
	public boolean isValid() {
		return wrapped.isValid();
	}

	@Override
	public boolean same(StatementInterface s) {
		if (s instanceof ScheduleLogWrapper) {
			return wrapped.same(((ScheduleLogWrapper) s).wrapped);
		} else {
			return wrapped.same(s);
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.StatementInterface#isValidException(java.lang.Throwable)
	 */
	@Override
	public boolean isValidException(Throwable t) {
		// TODO Auto-generated method stub
		return true;
	}
}
