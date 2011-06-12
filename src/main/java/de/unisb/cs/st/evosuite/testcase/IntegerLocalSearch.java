/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;

/**
 * @author Gordon Fraser
 * 
 */
public class IntegerLocalSearch<T> implements LocalSearch {

	private static Logger logger = Logger.getLogger(LocalSearch.class);

	private T oldValue;

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.testcase.LocalSearch#doSearch(de.unisb.cs.st.evosuite.testcase.TestChromosome, int, de.unisb.cs.st.evosuite.ga.LocalSearchObjective)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doSearch(TestChromosome test, int statement,
	        LocalSearchObjective objective) {

		PrimitiveStatement<T> p = (PrimitiveStatement<T>) test.test.getStatement(statement);
		oldValue = p.getValue();

		// Try +1
		//logger.info("Trying increment of " + p.getCode());
		p.increment();
		if (objective.hasImproved(test)) {
			while (iterate(1, objective, test, p, statement))
				;

		} else {
			// Restore original, try -1
			p.setValue(oldValue);
			//logger.info("Trying decrement of " + p.getCode());
			p.decrement();
			if (objective.hasImproved(test)) {
				while (iterate(-1, objective, test, p, statement))
					;

			} else {
				p.setValue(oldValue);
			}
		}
		//logger.info("Finished local search with result " + p.getCode());
	}

	private boolean iterate(long delta, LocalSearchObjective objective,
	        TestChromosome test, PrimitiveStatement<T> p, int statement) {

		boolean improvement = false;
		T oldValue = p.getValue();
		logger.info("Trying increment " + delta + " of " + p.getCode());

		p.increment(delta);
		while (objective.hasImproved(test)) {
			oldValue = p.getValue();
			improvement = true;
			delta = 2 * delta;
			logger.info("Trying increment " + delta + " of " + p.getCode());
			p.increment(delta);
		}

		p.setValue(oldValue);

		return improvement;

	}

}
