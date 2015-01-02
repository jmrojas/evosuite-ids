package org.evosuite.ga.localsearch;

import org.evosuite.ga.Chromosome;

public interface LocalSearch<T extends Chromosome> {

	public boolean doSearch(T individual, LocalSearchObjective<T> objective);
	
}
