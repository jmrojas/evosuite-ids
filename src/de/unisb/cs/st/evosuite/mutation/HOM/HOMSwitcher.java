package de.unisb.cs.st.evosuite.mutation.HOM;

import java.util.List;

import org.apache.log4j.Logger;

import de.unisb.cs.st.javalanche.mutation.javaagent.MutationForRun;
import de.unisb.cs.st.javalanche.mutation.properties.MutationProperties;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;

public class HOMSwitcher {

	private Logger logger = Logger.getLogger(HOMSwitcher.class);
	
	int num_mutants;
	
	List<Mutation> mutants;
	
	public HOMSwitcher() {
		// TODO: All mutants?
		//num_mutants = (int)QueryManager.getNumberOfMutationsWithPrefix(MutationProperties.PROJECT_PREFIX);
		mutants = MutationForRun.getFromDefaultLocation().getMutations();
		num_mutants = mutants.size();
		logger.debug("Got "+num_mutants+" mutations");
		//mutants = QueryManager.getMutationIdListFromDb(num_mutants);
		assert(num_mutants == mutants.size());
	}

	public void switchOn(HOMChromosome hom) {
		//assert(hom.size() == num_mutants);
		for(int i=0; i<mutants.size(); i++) {
			if(hom.get(i)) {
				logger.debug("Activating "+mutants.get(i).getMutationVariable());
				System.setProperty(mutants.get(i).getMutationVariable(), "1");
				//assert(System.getProperty(mutants.get(i).getMutationVariable()).equals("1"));
				System.setProperty(MutationProperties.CURRENT_MUTATION_KEY,	mutants.get(i).getId() + "");	
			}
			else {
				System.clearProperty(mutants.get(i).getMutationVariable());
				System.clearProperty(MutationProperties.CURRENT_MUTATION_KEY);
			}
		}
	}
	
	public void switchOn(Mutation m) {
		System.setProperty(m.getMutationVariable(), "1");
		System.setProperty(MutationProperties.CURRENT_MUTATION_KEY,	m.getId() + "");	
	}

	public void switchOff(Mutation m) {
		
		if(m == null)
			switchOff();
		else {
			System.clearProperty(m.getMutationVariable());
			System.clearProperty(MutationProperties.CURRENT_MUTATION_KEY);
		}
	}

	
	public void switchOff() {
		for(Mutation m : mutants) {
			System.clearProperty(m.getMutationVariable());
			System.clearProperty(MutationProperties.CURRENT_MUTATION_KEY);
		}
	}
	
	public int getNumMutants() {
		return num_mutants;
	}
	
	public List<Mutation> getMutants() {
		return mutants;
	}
//	public void activate(Long id) {
//		System.setProperty(currentMutation.getMutationVariable(), "1");
//		System.setProperty(MutationProperties.CURRENT_MUTATION_KEY,
//		
//	}
}
