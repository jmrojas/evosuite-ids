package de.unisb.cs.st.evosuite.testcase;

import java.lang.reflect.AccessibleObject;
import java.util.List;

import de.unisb.cs.st.ga.Chromosome;
import de.unisb.cs.st.ga.ConstructionFailedException;
import de.unisb.cs.st.ga.GAProperties;

/**
 * Chromosome representation of test cases
 * @author Gordon Fraser
 *
 */
public class TestChromosome extends Chromosome {
	

	/** The test case encoded in this chromosome */
	public TestCase test = new TestCase();
	
	/** Factory to manipulate and generate method sequences */
	private TestFactory test_factory;

	/** True if this leads to an exception */
	private boolean has_exception = false;

	public TestChromosome() {
		test_factory = TestFactory.getInstance();
	}
	
	public ExecutionResult last_result = null;
	
	/*
	public TestChromosome(TestFactory test_factory, TestMutationFactory mutation_factory) {
		this.test_factory = test_factory;
		this.mutation_factory = mutation_factory;
	}
	*/
	
	/**
	 * Create a deep copy of the chromosome
	 */
	public Chromosome clone() {
		TestChromosome c = new TestChromosome();
		c.test = test.clone();
		
		c.setFitness(getFitness());
		c.solution = solution;
		c.setChanged(isChanged());
		if(last_result != null)
			c.last_result = last_result.clone(); // TODO: Clone?
		
		return c;
	}
	
	
	/**
	 * Single point cross over
	 * @throws ConstructionFailedException 
	 */
	public void crossOver(Chromosome other, int position1, int position2) throws ConstructionFailedException  {
		
		TestChromosome offspring = new TestChromosome();
		
		for(int i=0; i<position1; i++) {
			offspring.test.addStatement(test.getStatement(i).clone());
		}
		for(int i=position2; i<other.size(); i++) {
			test_factory.appendStatement(offspring.test, ((TestChromosome)other).test.getStatement(i));
		}
		if(offspring.test.size() <= GAProperties.chromosome_length)
			test = offspring.test;
			//logger.warn("Size exceeded!");
		setChanged(true);
	}

	
	/**
	 * Two chromosomes are equal if their tests are equal
	 */
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
	
	/**
	 * Each statement is mutated with probability 1/l 
	 */
	public void mutate() {
		boolean changed = false;
		final double P = 1d/3d;
		
		// Delete
		if(randomness.nextDouble() <= P) {
			changed = mutationDelete();
		}
		
		// Change
		if(randomness.nextDouble() <= P) {
			changed = changed || mutationChange();
		//	changed = true;
		}
		
		// Insert
		if(randomness.nextDouble() <= P) {
			changed = changed || mutationInsert();
			//changed = true;
		}
		
		if(changed) {
			setChanged(true);
			last_result = null;
		}
	}
	
	/**
	 * Each statement is deleted with probability 1/length
	 * @return
	 */
	private boolean mutationDelete() {
		boolean changed = false;
		double pl = 1d/test.size();
		for(int num = test.size() - 1; num >= 0; num--) {

			// Each statement is deleted with probability 1/l
			if(randomness.nextDouble() <= pl) {
				//if(!test.hasReferences(test.getStatement(num).getReturnValue())) {
					try {
//						test_factory.deleteStatement(test, num);
						changed = true;
						test_factory.deleteStatementGracefully(test, num);

					} catch (ConstructionFailedException e) {
						logger.warn("Deletion of statement failed: "+test.getStatement(num).getCode());
						logger.warn(test.toCode());
					}
				//}
			}
		}
		
		return changed;
	}

	/**
	 * Each statement is replaced with probability 1/length
	 * @return
	 */
	private boolean mutationChange() {
		boolean changed = false;
		double pl = 1d/test.size();

		for(Statement statement : test.getStatements()) {
			if(randomness.nextDouble() <= pl) {

				if(statement instanceof PrimitiveStatement<?>) {
					//  do some mutation of values with what probability?
					((PrimitiveStatement<?>)statement).delta();
					
					int position = statement.retval.statement;
					test.setStatement(statement, position);
					changed = true;	
				} else if(statement instanceof AssignmentStatement) {
					//logger.info("Before change at:");
					//logger.info(test.toCode());
					AssignmentStatement as = (AssignmentStatement) statement;
					if(randomness.nextDouble() < 0.5) {
						List<VariableReference> objects = test.getObjects(statement.retval.getType(), statement.retval.statement);
						objects.remove(statement.retval);
						objects.remove(as.parameter);
						if(!objects.isEmpty()) {
							as.parameter = randomness.choice(objects);
						}
					} else if(as.retval.array_length > 0){
						as.retval.array_index = randomness.nextInt(as.retval.array_length);
					}
					//logger.info("After change:");
					//logger.info(test.toCode());
				} else {
					List<VariableReference> objects = test.getObjects(statement.retval.statement);
					objects.remove(statement.retval);
					List<AccessibleObject> calls = test_factory.getPossibleCalls(statement.getReturnType(), objects);
					logger.debug("Got "+calls.size()+" possible calls for "+objects.size()+" objects");
					AccessibleObject call = randomness.choice(calls);
					try {
						test_factory.changeCall(test, statement, call);
						changed = true;
					} catch (ConstructionFailedException e) {
						// Ignore
						logger.info("Change failed");
					}
					
				}
			}
		}
		
		return changed;
	}
	
	/**
	 * With exponentially decreasing probability, insert statements at random position
	 * @return
	 */
	private boolean mutationInsert() {
		boolean changed = false;
		final double ALPHA = 0.5;
		int count = 1;
		
		while(randomness.nextDouble() <= Math.pow(ALPHA, count) && size() < GAProperties.chromosome_length)
		{
			count++;				
			// Insert at position as during initialization (i.e., using helper sequences)
			test_factory.insertRandomStatement(test);
			changed = true;
		}
		return changed;
	}
	


	/**
	 * The size of a chromosome is the length of its test case
	 */
	public int size() {
		return test.size();
	}

	public String toString() {
		return test.toCode();
	}
	
	public boolean hasException() {
		return has_exception;
	}
}
