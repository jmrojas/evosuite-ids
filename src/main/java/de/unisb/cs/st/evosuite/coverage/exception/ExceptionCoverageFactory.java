package de.unisb.cs.st.evosuite.coverage.exception;

import java.util.List;

import de.unisb.cs.st.evosuite.testcase.TestFitnessFunction;
import de.unisb.cs.st.evosuite.testsuite.AbstractFitnessFactory;

public class ExceptionCoverageFactory extends AbstractFitnessFactory{

	@Override
	public List<TestFitnessFunction> getCoverageGoals() {
		
		throw new RuntimeException("Not really sure what this method should do...");
	}

}
