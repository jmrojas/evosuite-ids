/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.evosuite;

import java.io.Serializable;

import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.ga.SearchListener;
import org.evosuite.ga.stoppingconditions.StoppingCondition;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.rmi.service.ClientStateInformation;
import org.evosuite.testsuite.TestSuiteChromosome;

/**
 * <p>
 * ProgressMonitor class.
 * </p>
 * 
 * @author gordon
 */
public class ProgressMonitor implements SearchListener, Serializable {

	private static final long serialVersionUID = -8518559681906649686L;

	protected int lastCoverage = 0;
	protected int lastProgress = 0;
	protected String currentTask = "";
	protected int phases = 0;
	protected int currentPhase = 0;
	protected ClientState state = ClientState.INITIALIZATION;

	/**
	 * <p>
	 * Constructor for ProgressMonitor.
	 * </p>
	 */
	public ProgressMonitor() {
		this(1);
	}

	/**
	 * <p>
	 * Constructor for ProgressMonitor.
	 * </p>
	 */
	public ProgressMonitor(int phases) {
		this.phases = phases;
	}

	/**
	 * <p>
	 * updateStatus
	 * </p>
	 * 
	 * @param percent
	 *            a int.
	 * @param coverage
	 *            a int.
	 */
	public void updateStatus(int percent) {
		ClientStateInformation stateInformation = new ClientStateInformation(ClientState.SEARCH);
		stateInformation.setCoverage(currentCoverage);
		stateInformation.setProgress(percent);
		stateInformation.setDescription(currentTask);
		ClientServices.getInstance().getClientNode().changeState(stateInformation);
		lastProgress = percent;
		// lastCoverage = coverage;
		//out.writeInt(percent);
		//out.writeInt(currentPhase);
		//out.writeInt(phases);
		//out.writeInt(currentCoverage);
		//out.writeObject(currentTask);
	}

	private StoppingCondition stoppingCondition = null;

	private long max = 1;

	private int currentCoverage = 0;

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#searchStarted(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void searchStarted(GeneticAlgorithm algorithm) {
		stoppingCondition = TestSuiteGenerator.getStoppingCondition();
		max = stoppingCondition.getLimit();
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#iteration(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void iteration(GeneticAlgorithm algorithm) {
		long current = stoppingCondition.getCurrentValue();
		currentCoverage = (int) Math.floor(((TestSuiteChromosome) algorithm.getBestIndividual()).getCoverage() * 100);
		updateStatus((int) (100 * current / max));
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#searchFinished(org.evosuite.ga.GeneticAlgorithm)
	 */
	/** {@inheritDoc} */
	@Override
	public void searchFinished(GeneticAlgorithm algorithm) {
		currentCoverage = (int) Math.floor(((TestSuiteChromosome) algorithm.getBestIndividual()).getCoverage() * 100);
		// updateStatus((int) (100 * stoppingCondition.getCurrentValue() / max));
		// System.out.println("");
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#fitnessEvaluation(org.evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public void fitnessEvaluation(Chromosome individual) {
		long current = stoppingCondition.getCurrentValue();
		updateStatus((int) (100 * current / max));
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.SearchListener#modification(org.evosuite.ga.Chromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public void modification(Chromosome individual) {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the name of the current task. As everything is done in sequence in
	 * EvoSuite currently we just make this static.
	 * 
	 * @param task
	 */
	public void setCurrentPhase(String task) {
		currentTask = task;
		currentPhase++;
		updateStatus(0);
	}

	public void updateAssertionStatus(int assertions, int totalAssertions) {

	}

	public void setNumberOfPhases(int phases) {
		this.phases = phases;
	}

}
