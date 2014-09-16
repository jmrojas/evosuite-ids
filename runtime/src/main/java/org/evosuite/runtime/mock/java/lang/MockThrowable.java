package org.evosuite.runtime.mock.java.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.naming.InitialContext;

import org.evosuite.runtime.Thread;
import org.evosuite.runtime.mock.MockFramework;
import org.evosuite.runtime.mock.OverrideMock;

public class MockThrowable extends Throwable  implements OverrideMock {

	private static final long serialVersionUID = 4078375023919805371L;

	private StackTraceElement[]  stackTraceElements;

	// ------ constructors -------------

	public MockThrowable() {
		super();
		init();
	}

	public MockThrowable(String message) {
		super(message);
		init();
	}

	public MockThrowable(Throwable cause) {
		super(cause);
		init();
	}

	public MockThrowable(String message, Throwable cause) {
		super(message, cause);
		init();
	}

	protected MockThrowable(String message, Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
		init();
	}

	// ----- just for mock --------

	private void init(){
		stackTraceElements = new StackTraceElement[3];
		stackTraceElements[0] = new StackTraceElement("<evosuite>", "<evosuite>", "<evosuite>", -1);
		stackTraceElements[1] = new StackTraceElement("<evosuite>", "<evosuite>", "<evosuite>", -1);
		stackTraceElements[2] = new StackTraceElement("<evosuite>", "<evosuite>", "<evosuite>", -1);
	}


	// ----- unmodified public methods ----------

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return super.getCause();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}


	// ------  mocked public methods --------

	@Override
	public synchronized Throwable initCause(Throwable cause) {

		if(!MockFramework.isEnabled()){
			return super.initCause(cause);
		}

		try{
			return super.initCause(cause);
		} catch(IllegalStateException e){
			throw new IllegalStateException(e.getMessage()); //FIXME
		} catch(IllegalArgumentException e){
			throw new IllegalArgumentException(e.getMessage()); //FIXME
		}
	}

	@Override
	public void printStackTrace(PrintStream p) {

		if(!MockFramework.isEnabled()){
			super.printStackTrace(p);
			return;
		}

		for(StackTraceElement elem : getStackTrace()) {
			p.append(elem.toString());
			p.append("\n");
		}
	}

	@Override
	public void printStackTrace(PrintWriter p) {

		if(!MockFramework.isEnabled()){
			super.printStackTrace(p);
			return;
		}

		for(StackTraceElement elem : getStackTrace()) {
			p.append(elem.toString());
			p.append("\n");
		}		
	}


	@Override
	public synchronized Throwable fillInStackTrace() {
		if(!MockFramework.isEnabled()){
			return super.fillInStackTrace();
		}

		return this;
	}

	@Override
	public StackTraceElement[] getStackTrace() {		

		if(!MockFramework.isEnabled()){
			return super.getStackTrace();
		}


		return stackTraceElements;
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {

		if(!MockFramework.isEnabled()){
			super.setStackTrace(stackTrace);
			return;
		}


		StackTraceElement[] defensiveCopy = stackTrace.clone();
		for (int i = 0; i < defensiveCopy.length; i++) {
			if (defensiveCopy[i] == null)
				throw new NullPointerException("stackTrace[" + i + "]");  //FIXME
		}

		synchronized (this) {            
			this.stackTraceElements = defensiveCopy;
		}
	}

	/*
	 *  getSuppressed() and addSuppressed() are final, and likely not
	 *  needed to be mocked anyway
	 *
	 */
}
