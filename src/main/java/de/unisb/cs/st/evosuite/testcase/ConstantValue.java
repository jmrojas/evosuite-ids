/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import java.lang.reflect.Type;

import de.unisb.cs.st.evosuite.utils.NumberFormatter;

/**
 * @author Gordon Fraser
 * 
 */
public class ConstantValue extends VariableReferenceImpl {

	private static final long serialVersionUID = -3760942087575495415L;

	/**
	 * @param testCase
	 * @param type
	 */
	public ConstantValue(TestCase testCase, GenericClass type) {
		super(testCase, type);
	}

	public ConstantValue(TestCase testCase, Type type) {
		this(testCase, new GenericClass(type));
	}

	/**
	 * Create a copy of the current variable
	 */
	@Override
	public VariableReference copy(TestCase newTestCase, int offset) {
		ConstantValue ret = new ConstantValue(newTestCase, type);
		ret.setValue(value);
		return ret;
	}

	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * The position of the statement, defining this VariableReference, in the
	 * testcase.
	 * 
	 * @return
	 */
	@Override
	public int getStPosition() {
		for (int i = 0; i < testCase.size(); i++) {
			if (testCase.getStatement(i).references(this)) {
				return i;
			}
		}

		throw new AssertionError(
		        "A ConstantValue position is only defined if the VariableReference is defined by a statement");
	}

	/**
	 * Return name for source code representation
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return NumberFormatter.getNumberString(value);
	}

	/**
	 * Return the actual object represented by this variable for a given scope
	 * 
	 * @param scope
	 *            The scope of the test case execution
	 */
	@Override
	public Object getObject(Scope scope) {
		return value;
	}

	@Override
	public boolean same(VariableReference r) {
		if (r == null)
			return false;

		if (!this.type.equals(r.getGenericClass()))
			return false;

		if (r instanceof ConstantValue) {
			ConstantValue v = (ConstantValue) r;
			if (this.value == null) {
				if (v.getValue() == null)
					return true;
			} else {
				if (this.value.equals(v.getValue()))
					return true;
			}
		}

		return false;
	}

}
