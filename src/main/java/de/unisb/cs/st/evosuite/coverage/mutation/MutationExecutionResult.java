/**
 * 
 */
package de.unisb.cs.st.evosuite.coverage.mutation;

/**
 * @author fraser
 * 
 */
public class MutationExecutionResult {

	private int numAssertions = 0;

	private double impact = 0.0;

	boolean hasTimeout = false;

	boolean hasException = false;

	/**
	 * @return the numAssertions
	 */
	public int getNumAssertions() {
		return numAssertions;
	}

	/**
	 * @param numAssertions
	 *            the numAssertions to set
	 */
	public void setNumAssertions(int numAssertions) {
		this.numAssertions = numAssertions;
	}

	/**
	 * @return the impact
	 */
	public double getImpact() {
		return impact;
	}

	/**
	 * @param impact
	 *            the impact to set
	 */
	public void setImpact(double impact) {
		this.impact = impact;
	}

	/**
	 * @return the hasTimeout
	 */
	public boolean hasTimeout() {
		return hasTimeout;
	}

	/**
	 * @param hasTimeout
	 *            the hasTimeout to set
	 */
	public void setHasTimeout(boolean hasTimeout) {
		this.hasTimeout = hasTimeout;
	}

	/**
	 * @return the hasException
	 */
	public boolean hasException() {
		return hasException;
	}

	/**
	 * @param hasTimeout
	 *            the hasTimeout to set
	 */
	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}
}
