package org.evosuite.coverage.archive;

import org.evosuite.Properties;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.factories.RandomLengthTestFactory;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiveTestChromosomeFactory implements ChromosomeFactory<TestChromosome> {

	private static final long serialVersionUID = -8499807341782893732L;

	private final static Logger logger = LoggerFactory.getLogger(ArchiveTestChromosomeFactory.class);
	
	private ChromosomeFactory<TestChromosome> defaultFactory = new RandomLengthTestFactory();
	
	@Override
	public TestChromosome getChromosome() {
		TestChromosome test = null;
		if(TestsArchive.instance.getNumberOfTestsInArchive() > 0 && Randomness.nextDouble() < Properties.SEED_CLONE) {
			logger.info("Creating test based on archive");
			test = (TestChromosome) Randomness.choice(TestsArchive.instance.getBestChromosome().getTestChromosomes()).clone();
		} else {
			logger.info("Creating random test");
			test = defaultFactory.getChromosome();
		}
		
		int mutations = Randomness.nextInt(Properties.SEED_MUTATIONS);
		for(int i = 0; i < mutations; i++) {
			test.mutate();
		}
		return test;
	}

}
