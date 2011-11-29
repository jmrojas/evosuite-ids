package de.unisb.cs.st.evosuite.ui.genetics;

import de.unisb.cs.st.evosuite.ga.Chromosome;
import de.unisb.cs.st.evosuite.ga.ChromosomeFactory;
import de.unisb.cs.st.evosuite.ga.LocalSearchObjective;
import de.unisb.cs.st.evosuite.testsuite.AbstractTestSuiteChromosome;

public class UITestSuiteChromosome extends AbstractTestSuiteChromosome<UITestChromosome> {
	private static final long serialVersionUID = 1L;

	public UITestSuiteChromosome(ChromosomeFactory<UITestChromosome> testFactory) {
		super(testFactory);
	}

	public UITestSuiteChromosome(UITestSuiteChromosome source) {
		super(source);
	}

	@Override
	public UITestSuiteChromosome clone() {
		return new UITestSuiteChromosome(this);
	}

	@Override
	public void localSearch(LocalSearchObjective objective) {
		throw new UnsupportedOperationException(
		        "UITestSuiteChromosome doesn't support localSearch (yet?)");
	}

	@Override
	public void applyDSE() {
		throw new UnsupportedOperationException(
		        "UITestSuiteChromosome doesn't support applyDSE() (yet?)");
	}

	@Override
	public String toString() {
		return "UITestSuiteChromosome [tests=" + tests + "]";
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.ga.Chromosome#compareSecondaryObjective(de.unisb.cs.st.evosuite.ga.Chromosome)
	 */
	@Override
	public int compareSecondaryObjective(Chromosome o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
