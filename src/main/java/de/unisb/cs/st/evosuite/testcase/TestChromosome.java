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

package de.unisb.cs.st.evosuite.testcase;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.Properties.Criterion;
import de.unisb.cs.st.evosuite.coverage.concurrency.ConcurrentTestCase;
import de.unisb.cs.st.evosuite.coverage.concurrency.Schedule;
import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.ConstructionFailedException;
import de.unisb.cs.st.evosuite.ga.LocalSearchBudget;
import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;
import de.unisb.cs.st.evosuite.symbolic.ConcolicMutation;
import de.unisb.cs.st.evosuite.testsuite.CurrentChromosomeTracker;
import de.unisb.cs.st.evosuite.testsuite.TestSuiteFitnessFunction;
import de.unisb.cs.st.evosuite.utils.Randomness;

/**
 * Chromosome representation of test cases
 * 
 * @author Gordon Fraser
 * 
 */
public class TestChromosome extends ExecutableChromosome {

	private static final long serialVersionUID = 7532366007973252782L;

	/** The test case encoded in this chromosome */
	public TestCase test = new DefaultTestCase();

	/** Factory to manipulate and generate method sequences */
	private static AbstractTestFactory test_factory = null;

	/** True if this leads to an exception */
	private final boolean has_exception = false;

	public TestChromosome() {

		//#TODO steenbuck similar logic is repeated in TestSuiteChromosomeFactory
		if (test_factory == null) {
			test_factory = DefaultTestFactory.getInstance();
		}
	}

	@Override
	public void setChanged(boolean changed) {
		super.setChanged(changed);
		CurrentChromosomeTracker.getInstance().changed(this);
	}

	/**
	 * Create a deep copy of the chromosome
	 */
	@Override
	public Chromosome clone() {
		TestChromosome c = new TestChromosome();
		c.test = test.clone();
		//assert (test.toCode().equals(c.test.toCode()));
		assert (test.isValid());
		try {
			c.test.isValid();
		} catch (Throwable t) {
			logger.warn(c.test.toCode());
		}
		assert (c.test.isValid());

		c.setFitness(getFitness());
		c.solution = solution;
		c.setChanged(isChanged());
		if (getLastExecutionResult() != null) {
			c.setLastExecutionResult(this.lastExecutionResult); //.clone(); // TODO: Clone?
			c.getLastExecutionResult().test = c.test;
		}

		return c;
	}

	/**
	 * Single point cross over
	 * 
	 * @throws ConstructionFailedException
	 */
	@Override
	public void crossOver(Chromosome other, int position1, int position2)
	        throws ConstructionFailedException {
		logger.debug("Crossover starting");
		TestChromosome offspring = new TestChromosome();

		for (int i = 0; i < position1; i++) {
			offspring.test.addStatement(test.getStatement(i).clone(offspring.test));
		}
		for (int i = position2; i < other.size(); i++) {
			test_factory.appendStatement(offspring.test,
			                             ((TestChromosome) other).test.getStatement(i));
		}
		if (!Properties.CHECK_MAX_LENGTH
		        || offspring.test.size() <= Properties.CHROMOSOME_LENGTH) {
			test = offspring.test;
		}
		// logger.warn("Size exceeded!");
		setChanged(true);
	}

	/**
	 * Two chromosomes are equal if their tests are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TestChromosome other = (TestChromosome) obj;
		if (test == null) {
			if (other.test != null)
				return false;
		} else if (!test.equals(other.test))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.Chromosome#localSearch()
	 */
	@Override
	public void localSearch(LocalSearchObjective objective) {
		//logger.info("Test before local search: " + test.toCode());
		double oldFitness = getFitness();

		for (int i = 0; i < test.size(); i++) {
			if (LocalSearchBudget.isFinished())
				break;

			if (test.getStatement(i) instanceof PrimitiveStatement<?>) {
				Class<?> type = test.getReturnValue(i).getVariableClass();
				LocalSearch search = null;
				if (type.equals(Integer.class) || type.equals(int.class)) {
					search = new IntegerLocalSearch<Integer>();
				} else if (type.equals(Byte.class) || type.equals(byte.class)) {
					search = new IntegerLocalSearch<Byte>();
				} else if (type.equals(Short.class) || type.equals(short.class)) {
					search = new IntegerLocalSearch<Short>();
				} else if (type.equals(Long.class) || type.equals(long.class)) {
					search = new IntegerLocalSearch<Long>();
				} else if (type.equals(Character.class) || type.equals(char.class)) {
					search = new IntegerLocalSearch<Character>();
				} else if (type.equals(Float.class) || type.equals(float.class)) {
					search = new FloatLocalSearch<Float>();
				} else if (type.equals(Double.class) || type.equals(double.class)) {
					search = new FloatLocalSearch<Double>();
				} else if (type.equals(String.class)) {
					search = new StringLocalSearch();
				} else if (type.equals(Boolean.class)) {
					search = new BooleanLocalSearch();
				}

				if (search != null)
					search.doSearch(this, i, objective);
			}
		}
		assert (getFitness() <= oldFitness);
		//logger.info("Test after local search: " + test.toCode());

		// TODO: Handle arrays in local search
		// TODO: mutating an int might have an effect on array lengths
	}

	/**
	 * Each statement is mutated with probability 1/l
	 */
	@Override
	public void mutate() {
		boolean changed = false;
		double P;

		//#TODO steenbuck TestChromosome should be subclassed
		if (Properties.CRITERION == Criterion.CONCURRENCY) {
			assert (test instanceof ConcurrentTestCase);

			P = 1d / 6d;

			// Delete from schedule
			if (Randomness.nextDouble() <= P) {
				changed = mutationDeleteSchedule();
			}

			// Change in schedule
			if (Randomness.nextDouble() <= P) {
				if (mutationChangeSchedule())
					changed = true;
			}

			// Insert into schedule
			if (Randomness.nextDouble() <= P) {
				if (mutationInsertSchedule())
					changed = true;
			}
		} else {
			P = 1d / 3d;
		}

		logger.debug("Mutation: delete");
		// Delete
		if (Randomness.nextDouble() <= P) {
			changed = mutationDelete();
		}

		logger.debug("Mutation: change");
		// Change
		if (Randomness.nextDouble() <= P) {
			if (mutationChange())
				changed = true;
		}

		logger.debug("Mutation: insert");
		// Insert
		if (Randomness.nextDouble() <= P) {
			if (mutationInsert())
				changed = true;
		}

		if (changed) {
			setChanged(true);
			setLastExecutionResult(null);
		}
	}

	/**
	 * Each statement is deleted with probability 1/length
	 * 
	 * @return
	 */
	private boolean mutationDelete() {
		boolean changed = false;
		double pl = 1d / test.size();
		for (int num = test.size() - 1; num >= 0; num--) {

			// Each statement is deleted with probability 1/l
			if (Randomness.nextDouble() <= pl) {
				// if(!test.hasReferences(test.getStatement(num).getReturnValue()))
				// {
				try {
					TestCase copy = test.clone();
					// test_factory.deleteStatement(test, num);
					changed = true;
					test_factory.deleteStatementGracefully(copy, num);
					test = copy;

				} catch (ConstructionFailedException e) {
					logger.warn("Deletion of statement failed: "
					        + test.getStatement(num).getCode());
					logger.warn(test.toCode());
				}
				// }
			}
		}

		return changed;
	}

	/**
	 * Each schedule entry is deleted with probability 1/length
	 * 
	 * @return
	 */
	private boolean mutationDeleteSchedule() {
		ConcurrentTestCase test = (ConcurrentTestCase) this.test;
		Schedule schedule = test.getSchedule();
		boolean changed = false;
		double pl = 1d / schedule.size();
		for (int num = schedule.size() - 1; num >= 0; num--) {

			// Each schedulePoint is deleted with probability 1/l
			if (Randomness.nextDouble() <= pl) {
				schedule.removeElement(num);
				changed = true;
			}
		}

		return changed;
	}

	/**
	 * With exponentially decreasing probability, insert schedule points at
	 * random position
	 * 
	 * @return
	 */
	private boolean mutationInsertSchedule() {
		ConcurrentTestCase test = (ConcurrentTestCase) this.test;
		Schedule schedule = test.getSchedule();
		boolean changed = false;
		final double ALPHA = 0.5;
		int count = 0;

		while (Randomness.nextDouble() <= Math.pow(ALPHA, count)) { //#TODO steenbuck removed length check, should maybe be added (compare: mutateInsert)
			count++;
			// Insert at position as during initialization (i.e., using helper
			// sequences)
			int pos = (schedule.size() == 0) ? 0 : Randomness.nextInt(schedule.size());
			schedule.add(pos, schedule.getRandomThreadID());
			changed = true;
		}
		return changed;
	}

	/**
	 * Each schedule is replaced with probability 1/length
	 * 
	 * @return
	 */
	private boolean mutationChangeSchedule() {
		ConcurrentTestCase test = (ConcurrentTestCase) this.test;
		Schedule schedule = test.getSchedule();
		boolean changed = false;
		double pl = 1d / schedule.size();
		for (int num = schedule.size() - 1; num >= 0; num--) {

			// Each schedulePoint is deleted with probability 1/l
			if (Randomness.nextDouble() <= pl) {
				schedule.removeElement(num);
				schedule.add(num, schedule.getRandomThreadID());
				changed = true;
			}
		}

		return changed;
	}

	/**
	 * Each statement is replaced with probability 1/length
	 * 
	 * @return
	 */
	private boolean mutationChange() {
		boolean changed = false;
		double pl = 1d / test.size();

		if (Randomness.nextDouble() < Properties.CONCOLIC_MUTATION) {
			ConcolicMutation mutation = new ConcolicMutation();
			changed = mutation.mutate(test);
			if (changed) {
				logger.info("Changed test case is: " + test.toCode());
			}
		}

		if (!changed) {
			for (StatementInterface statement : test) {
				if (Randomness.nextDouble() <= pl) {
					assert (test.isValid());
					if (statement.mutate(test, test_factory)) {
						changed = true;
						assert (test.isValid());
					} else if (!statement.isAssignmentStatement()) {
						if (test_factory.changeRandomCall(test, statement))
							changed = true;
						assert (test.isValid());
					}

					//					} else if (statement.getReturnValue() instanceof ArrayReference) {
				}
			}
		}
		return changed;
	}

	/**
	 * With exponentially decreasing probability, insert statements at random
	 * position
	 * 
	 * @return
	 */
	private boolean mutationInsert() {
		boolean changed = false;
		final double ALPHA = 0.5;
		int count = 0;

		while (Randomness.nextDouble() <= Math.pow(ALPHA, count)
		        && (!Properties.CHECK_MAX_LENGTH || size() < Properties.CHROMOSOME_LENGTH)) {
			count++;
			// Insert at position as during initialization (i.e., using helper
			// sequences)
			test_factory.insertRandomStatement(test);
			changed = true;
		}
		return changed;
	}

	/**
	 * The size of a chromosome is the length of its test case
	 */
	@Override
	public int size() {
		return test.size();
	}

	@Override
	public String toString() {
		return test.toCode();
	}

	public boolean hasException() {
		return has_exception;
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.Chromosome#applyDSE()
	 */
	@Override
	public void applyDSE() {
		// TODO Auto-generated method stub

	}

	@Override
	public ExecutionResult executeForFitnessFunction(TestSuiteFitnessFunction testSuiteFitnessFunction) {
		return testSuiteFitnessFunction.runTest(this.test);
	}
}
