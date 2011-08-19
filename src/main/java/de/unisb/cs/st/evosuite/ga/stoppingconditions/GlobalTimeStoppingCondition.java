/**
 * 
 */
package de.unisb.cs.st.evosuite.ga.stoppingconditions;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.ga.GeneticAlgorithm;

/**
 * @author Gordon Fraser
 * 
 */
public class GlobalTimeStoppingCondition extends StoppingCondition {

	private static final long serialVersionUID = -4880914182984895075L;

	/** Maximum number of seconds. 0 = infinite time */
	protected static int max_seconds = Properties.GLOBAL_TIMEOUT;

	/** Assume the search has not started until start_time != 0 */
	protected static long start_time = 0L;

	@Override
	public void searchStarted(GeneticAlgorithm algorithm) {
		if (start_time == 0)
			reset();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.StoppingCondition#getCurrentValue()
	 */
	@Override
	public int getCurrentValue() {
		long current_time = System.currentTimeMillis();
		return (int) ((current_time - start_time) / 1000);
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.StoppingCondition#isFinished()
	 */
	@Override
	public boolean isFinished() {
		long current_time = System.currentTimeMillis();
		if (max_seconds != 0 && start_time != 0
		        && (current_time - start_time) / 1000 > max_seconds)
			logger.info("Timeout reached");

		return max_seconds != 0 && start_time != 0
		        && (current_time - start_time) / 1000 > max_seconds;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.StoppingCondition#reset()
	 */
	@Override
	public void reset() {
		if (start_time == 0)
			start_time = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.StoppingCondition#setLimit(int)
	 */
	@Override
	public void setLimit(int limit) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLimit() {
		// TODO Auto-generated method stub
		return max_seconds;
	}

}
