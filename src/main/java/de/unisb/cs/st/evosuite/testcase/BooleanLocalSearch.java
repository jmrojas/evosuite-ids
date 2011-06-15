/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;

/**
 * @author fraser
 * 
 */
public class BooleanLocalSearch implements LocalSearch {

	private static Logger logger = Logger.getLogger(LocalSearch.class);

	private boolean oldValue;

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.LocalSearch#doSearch(de.unisb.cs.st.evosuite.testcase.TestChromosome, int, de.unisb.cs.st.evosuite.ga.LocalSearchObjective)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doSearch(TestChromosome test, int statement,
	        LocalSearchObjective objective) {
		PrimitiveStatement<Boolean> p = (PrimitiveStatement<Boolean>) test.test.getStatement(statement);
		ExecutionResult oldResult = test.last_result;
		oldValue = p.getValue();

		p.setValue(!oldValue);

		if (!objective.hasImproved(test)) {
			// Restore original
			p.setValue(oldValue);
			test.last_result = oldResult;
			test.setChanged(false);
		}

		logger.debug("Finished local search with result " + p.getCode());
	}

}
