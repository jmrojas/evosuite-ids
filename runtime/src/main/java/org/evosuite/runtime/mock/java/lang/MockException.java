package org.evosuite.runtime.mock.java.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.evosuite.runtime.mock.MockFramework;
import org.evosuite.runtime.mock.OverrideMock;

public class MockException extends Exception  implements OverrideMock{

	/*
	 * "Exception" class only defines constructors, like all (?) its subclasses.
	 * So, just need to override constructors, and delegate methods  
	 */
	
	private static final long serialVersionUID = 8001149552489118355L;

	/**
	 * Instead of copy&paste functionalities from MockThrowable, use a delegate
	 */
	private volatile MockThrowable delegate;
	
	/*
	 * This is needed for when super constructors call overridden methods,
	 * and proper delegate method (right inputs) is not instantiated yet  
	 */
	private MockThrowable getDelegate(){
		if(delegate == null){
			delegate = new MockThrowable(); //placeholded
		}
		return delegate;
	}
	
	// ----- constructor --------
	
	public MockException() {
		super();
		delegate = new MockThrowable();
	}
	
	public MockException(String message) {
		super(message);
		delegate = new MockThrowable(message);
	}

	public MockException(Throwable cause) {
		super(cause);
		delegate = new MockThrowable(cause);
	}

	public MockException(String message, Throwable cause) {
		super(message, cause);
		delegate = new MockThrowable(message, cause);
	}
	
	protected MockException(String message, Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
		delegate = new MockThrowable(message, cause, enableSuppression, writableStackTrace);
	}
	
	
	// ----- delegation methods -------
	
	@Override
	public String getMessage() {		
		if(!MockFramework.isEnabled()){
			return super.getMessage();
		}		
		return getDelegate().getMessage();
	}

	@Override
	public String getLocalizedMessage() {		
		if(!MockFramework.isEnabled()){
			return super.getLocalizedMessage();
		}		
		return getDelegate().getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		if(!MockFramework.isEnabled()){
			return super.getCause();
		}
		return getDelegate().getCause();
	}

	@Override
	public String toString() {
		if(!MockFramework.isEnabled()){
			return super.toString();
		}
		return getDelegate().toString();
	}

	@Override
	public void printStackTrace() {
		if(!MockFramework.isEnabled()){
			super.printStackTrace();
			return;
		}
		getDelegate().printStackTrace();
	}


	@Override
	public synchronized Throwable initCause(Throwable cause) {
		if(!MockFramework.isEnabled()){
			return super.initCause(cause);
		}
		return getDelegate().initCause(cause);
	}

	@Override
	public void printStackTrace(PrintStream p) {
		if(!MockFramework.isEnabled()){
			super.printStackTrace(p);
			return;
		}
		getDelegate().printStackTrace(p);
	}

	public void printStackTrace(PrintWriter p) {
		if(!MockFramework.isEnabled()){
			super.printStackTrace(p);
			return;
		}
		getDelegate().printStackTrace(p);
	}


	@Override
	public synchronized Throwable fillInStackTrace() {
		if(!MockFramework.isEnabled()){
			return super.fillInStackTrace();
		}
		return getDelegate().fillInStackTrace();
	}

	@Override
	public StackTraceElement[] getStackTrace() {		
		if(!MockFramework.isEnabled()){
			return super.getStackTrace();
		}
		return getDelegate().getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		if(!MockFramework.isEnabled()){
			super.setStackTrace(stackTrace);
			return;
		}
		getDelegate().setStackTrace(stackTrace);
	}
}
