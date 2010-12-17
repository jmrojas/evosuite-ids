/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with EvoSuite.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.mutation;

import java.util.ArrayList;
import java.util.List;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.coverage.TestFitnessFactory;
import de.unisb.cs.st.evosuite.mutation.HOM.HOMSwitcher;
import de.unisb.cs.st.evosuite.testcase.TestFitnessFunction;
import de.unisb.cs.st.javalanche.mutation.results.Mutation;
import de.unisb.cs.st.javalanche.mutation.results.Mutation.MutationType;

/**
 * @author Gordon Fraser
 *
 */
public class MutationGoalFactory implements TestFitnessFactory {
	
	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.coverage.TestCoverageFactory#getCoverageGoals()
	 */
	@Override
	public List<TestFitnessFunction> getCoverageGoals() {
		HOMSwitcher hom_switcher = new HOMSwitcher();
		List<TestFitnessFunction> goals = new ArrayList<TestFitnessFunction>();
		System.out.println("* Created "+hom_switcher.getNumMutants()+" mutants");
		boolean VRO = Properties.getPropertyOrDefault("VRO", false);
		for(Mutation mutation : hom_switcher.getMutants()) {
			if(VRO) {
				if(!mutation.getMethodName().equals("<clinit>()V") && mutation.getMutationType().equals(MutationType.REPLACE_VARIABLE)) {
					goals.add(new MutationTestFitness(mutation));
				}				
			} else {
				if(!mutation.getMethodName().equals("<clinit>()V") && !mutation.getMutationType().equals(MutationType.REPLACE_VARIABLE)) {
					goals.add(new MutationTestFitness(mutation));
				}
			}
		}
		
		return goals;
	}

}
