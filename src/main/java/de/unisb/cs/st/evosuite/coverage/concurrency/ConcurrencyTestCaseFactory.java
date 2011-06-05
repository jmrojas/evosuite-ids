/**
 * 
 */
package de.unisb.cs.st.evosuite.coverage.concurrency;

import de.unisb.cs.st.evosuite.testcase.RandomLengthTestFactory;
import de.unisb.cs.st.evosuite.testcase.TestCase;

/**
 * @author x3k6a2
 *
 */
public class ConcurrencyTestCaseFactory extends RandomLengthTestFactory{
	@Override 
	protected TestCase getNewTestCase(){
		return new ConcurrentTestCase(new BasicTestCase(true));
	}
}
