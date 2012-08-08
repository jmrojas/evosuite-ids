 package edu.uta.cse.dsc.vm2;

import static edu.uta.cse.dsc.util.Assertions.check;

import java.lang.reflect.Member;


/**
 * Frame at the bottom of the invocation stack.
 * Keep this on the stack frame, in case we accidentally
 * transitively invoke some instrumented method.  
 * 
 * @author csallner@uta.edu (Christoph Csallner)
 */
public final class FakeCallerFrame extends Frame {


	/**
	 * Constructor
	 */
	public FakeCallerFrame() {
		super(0);
		super.invokeInstrumentedCode(false);
	}
	
	@Override
	public void invokeInstrumentedCode(boolean b) {
	  check(false);
	}
	
	@Override
	public int getNrFormalParameters() {
	  check(false);
	  return 0;
	}
	
  @Override
  public int getNrFormalParametersTotal() {
    check(false);
    return 0;
  }	
	
	@Override
	public Member getMember() {
		return null;
	}
}
