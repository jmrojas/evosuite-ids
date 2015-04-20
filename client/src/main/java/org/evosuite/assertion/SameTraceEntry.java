/**
 * 
 */
package org.evosuite.assertion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.evosuite.testcase.variable.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>SameTraceEntry class.</p>
 *
 * @author Gordon Fraser
 */
public class SameTraceEntry implements OutputTraceEntry {

	private final VariableReference var;

	private final Map<VariableReference, Boolean> equalityMap = new HashMap<VariableReference, Boolean>();
	
	private final Map<Integer,VariableReference> equalityMapIntVar = new HashMap<Integer, VariableReference>();
	
	private final static Logger logger = LoggerFactory.getLogger(SameTraceEntry.class);

	/**
	 * <p>Constructor for SameTraceEntry.</p>
	 *
	 * @param var a {@link org.evosuite.testcase.variable.VariableReference} object.
	 */
	public SameTraceEntry(VariableReference var) {
		this.var = var;
	}

	/**
	 * <p>addEntry</p>
	 *
	 * @param other a {@link org.evosuite.testcase.variable.VariableReference} object.
	 * @param value a boolean.
	 */
	public void addEntry(VariableReference other, boolean value) {
		equalityMap.put(other, value);
		equalityMapIntVar.put(other.getStPosition(),other);
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.OutputTraceEntry#differs(org.evosuite.assertion.OutputTraceEntry)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean differs(OutputTraceEntry other) {
		if (other instanceof SameTraceEntry) {
			if (!((SameTraceEntry) other).var.equals(var))
				return false;

			SameTraceEntry otherEntry = (SameTraceEntry) other;
			for (VariableReference otherVar : equalityMap.keySet()) {
				if (!otherEntry.equalityMap.containsKey(otherVar))
					continue;

				if (!otherEntry.equalityMap.get(otherVar).equals(equalityMap.get(otherVar)))
					return true;
			}

		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.OutputTraceEntry#getAssertions(org.evosuite.assertion.OutputTraceEntry)
	 */
	/** {@inheritDoc} */
	@Override
	public Set<Assertion> getAssertions(OutputTraceEntry other) {
		Set<Assertion> assertions = new HashSet<Assertion>();

		if (other instanceof SameTraceEntry) {
			SameTraceEntry otherEntry = (SameTraceEntry) other;
			for (Integer otherVar : equalityMapIntVar.keySet()) {
				if (!otherEntry.equalityMapIntVar.containsKey(otherVar))
					continue;

				if (!otherEntry.equalityMap.get(otherEntry.equalityMapIntVar.get(otherVar)).equals(equalityMap.get(equalityMapIntVar.get(otherVar)))) {
					//logger.warn("not the same : " + equalityMap.get(equalityMapIntVar.get(otherVar)));
					SameAssertion assertion = new SameAssertion();
					assertion.source = var;
					assertion.dest = equalityMapIntVar.get(otherVar);
					assertion.value = equalityMap.get(equalityMapIntVar.get(otherVar));
					assertion.setcomment("// Original Value: " + equalityMap.get(equalityMapIntVar.get(otherVar)) +" | Regression Value: " + otherEntry.equalityMap.get(otherEntry.equalityMapIntVar.get(otherVar)));
					assertions.add(assertion);
					assert (assertion.isValid());
				}
			}
		}
		return assertions;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.OutputTraceEntry#getAssertions()
	 */
	/** {@inheritDoc} */
	@Override
	public Set<Assertion> getAssertions() {
		Set<Assertion> assertions = new HashSet<Assertion>();

		for (VariableReference otherVar : equalityMap.keySet()) {
			if (otherVar == null)
				continue;

			SameAssertion assertion = new SameAssertion();
			assertion.source = var;
			assertion.dest = otherVar;
			assertion.value = equalityMap.get(otherVar);
			assertions.add(assertion);
			assert (assertion.isValid());
		}
		return assertions;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.OutputTraceEntry#isDetectedBy(org.evosuite.assertion.Assertion)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean isDetectedBy(Assertion assertion) {
		if (assertion instanceof SameAssertion) {
			SameAssertion ass = (SameAssertion) assertion;
			if (ass.source.equals(var) && equalityMap.containsKey(ass.dest))
				return !equalityMap.get(ass.dest).equals(ass.value);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.assertion.OutputTraceEntry#cloneEntry()
	 */
	/** {@inheritDoc} */
	@Override
	public OutputTraceEntry cloneEntry() {
		SameTraceEntry copy = new SameTraceEntry(var);
		copy.equalityMap.putAll(equalityMap);
		copy.equalityMapIntVar.putAll(equalityMapIntVar);
		return copy;
	}

}
