/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.ga.stoppingconditions;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.GeneticAlgorithm;

/**
 * Stop search when a maximum (average) length has been reached. Used for
 * experiments on length bloat.
 * 
 * @author Gordon Fraser
 * 
 */
public class MaxLengthStoppingCondition extends StoppingConditionImpl {
	
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MaxLengthStoppingCondition.class);

	private static final long serialVersionUID = 8537667219135128366L;

	private double average_length = 0.0;
	private int max_length = Properties.MAX_LENGTH;

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.ga.StoppingCondition#isFinished()
	 */
	@Override
	public boolean isFinished() {
		if (average_length >= max_length)
			logger.info("Maximum average length reached, stopping");
		return average_length >= max_length;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.ga.StoppingCondition#reset()
	 */
	@Override
	public void reset() {
		average_length = 0.0;
		max_length = Properties.MAX_LENGTH;
	}

	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		double avg = 0.0;
		for (Chromosome c : algorithm.getPopulation()) {
			avg += c.size();
		}
		average_length = avg / algorithm.getPopulation().size();
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.StoppingCondition#getCurrentValue()
	 */
	@Override
	public long getCurrentValue() {
		return (long) average_length;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.StoppingCondition#setLimit(int)
	 */
	@Override
	public void setLimit(long limit) {
		max_length = (int) limit;
	}

	@Override
	public long getLimit() {
		return (long) (max_length + 0.5);
	}

	@Override
	public void forceCurrentValue(long value) {
		// TODO Auto-generated method stub
		// TODO ?
	}
}
