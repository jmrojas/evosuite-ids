package de.unisb.cs.st.evosuite.junit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.IVariableBinding;

import de.unisb.cs.st.evosuite.assertion.Assertion;
import de.unisb.cs.st.evosuite.ga.ConstructionFailedException;
import de.unisb.cs.st.evosuite.testcase.DefaultTestCase;
import de.unisb.cs.st.evosuite.testcase.Scope;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestFitnessFunction;
import de.unisb.cs.st.evosuite.testcase.TestVisitor;
import de.unisb.cs.st.evosuite.testcase.VariableReference;
import de.unisb.cs.st.evosuite.utils.Listener;

/**
 * A compound test case is a test case that is read from an existing JUnit test
 * file, and may have a complex structure. It contains static code sections,
 * fields, constructors, @BeforeClass, @Before, @After, @AfterClass annotated
 * methods and possibly a class hierarchy. A CompoundTestCase is used to gather
 * all those statements to eventually combine them into a normal
 * {@link TestCase}.
 * 
 * @author roessler
 * 
 */
public class CompoundTestCase implements TestCase {
	public static enum TestScope {
		BEFORE_CLASS, STATIC, BEFORE, FIELDS, CONSTRUCTOR, TEST_METHOD, AFTER, AFTER_CLASS;
	}

	private static final long serialVersionUID = 1L;

	private final List<StatementInterface> after = new ArrayList<StatementInterface>();
	private final List<StatementInterface> afterClass = new ArrayList<StatementInterface>();
	private final List<StatementInterface> before = new ArrayList<StatementInterface>();
	private final List<StatementInterface> beforeClass = new ArrayList<StatementInterface>();
	private final List<StatementInterface> constructor = new ArrayList<StatementInterface>();
	private final List<StatementInterface> fields = new ArrayList<StatementInterface>();
	private final List<StatementInterface> staticCode = new ArrayList<StatementInterface>();
	private final List<StatementInterface> testMethod = new ArrayList<StatementInterface>();
	private final Map<IVariableBinding, VariableReference> methodVars = new HashMap<IVariableBinding, VariableReference>();
	private final Map<IVariableBinding, VariableReference> fieldVars = new HashMap<IVariableBinding, VariableReference>();
	private final Set<Listener<Void>> listeners = new HashSet<Listener<Void>>();
	private CompoundTestCase parent;
	private TestCase delegate = null;
	private TestScope currentScope = TestScope.FIELDS;

	@Override
	public void accept(TestVisitor visitor) {
		delegate.accept(visitor);
	}

	@Override
	public void addAssertions(TestCase other) {
		delegate.addAssertions(other);
	}

	@Override
	public void addCoveredGoal(TestFitnessFunction goal) {
		delegate.addCoveredGoal(goal);
	}

	@Override
	public void addListener(Listener<Void> listener) {
		listeners.add(listener);
	}

	@Override
	public VariableReference addStatement(StatementInterface statement) {
		return delegate.addStatement(statement);
	}

	@Override
	public VariableReference addStatement(StatementInterface statement, int position) {
		return delegate.addStatement(statement, position);
	}

	@Override
	public void addStatements(List<? extends StatementInterface> statements) {
		for (StatementInterface statement : statements) {
			delegate.addStatement(statement);
		}
	}

	public void addStatementToScope(StatementInterface statement) {
		switch (currentScope) {
		case BEFORE_CLASS:
			beforeClass.add(statement);
			return;
		case STATIC:
			staticCode.add(statement);
			return;
		case BEFORE:
			before.add(statement);
			return;
		case CONSTRUCTOR:
			constructor.add(statement);
			return;
		case FIELDS:
			fields.add(statement);
			return;
		case TEST_METHOD:
			testMethod.add(statement);
			return;
		case AFTER_CLASS:
			afterClass.add(statement);
			return;
		case AFTER:
			after.add(statement);
			return;
		default:
			throw new RuntimeException("Scope " + currentScope + " not considered!");
		}
	}

	public void addVariableToScope(IVariableBinding varBinding, VariableReference varRef) {
		if (currentScope == TestScope.FIELDS) {
			fieldVars.put(varBinding, varRef);
			return;
		}
		methodVars.put(varBinding, varRef);
	}

	@Override
	public void chop(int length) {
		delegate.chop(length);
	}

	@Override
	public TestCase clone() {
		return delegate.clone();
	}

	@Override
	public void deleteListener(Listener<Void> listener) {
		listeners.remove(listener);
	}

	public void finalizeTestCase() {
		delegate = new DefaultTestCase();
		addStatements(getInitializationCode());
		addStatements(testMethod);
		addStatements(getDeinitializationCode());
		for (Listener<Void> listener : listeners) {
			delegate.addListener(listener);
		}
	}

	@Override
	public Set<Class<?>> getAccessedClasses() {
		return delegate.getAccessedClasses();
	}

	@Override
	public List<Assertion> getAssertions() {
		return delegate.getAssertions();
	}

	@Override
	public Set<TestFitnessFunction> getCoveredGoals() {
		return delegate.getCoveredGoals();
	}

	@Override
	public Set<Class<?>> getDeclaredExceptions() {
		return delegate.getDeclaredExceptions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.unisb.cs.st.evosuite.testcase.TestCase#getDependencies(de.unisb.cs
	 * .st.evosuite.testcase.VariableReference)
	 */
	@Override
	public Set<VariableReference> getDependencies(VariableReference var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(VariableReference reference, Scope scope) {
		return delegate.getObject(reference, scope);
	}

	@Override
	public List<VariableReference> getObjects(int position) {
		return delegate.getObjects(position);
	}

	@Override
	public List<VariableReference> getObjects(Type type, int position) {
		return delegate.getObjects(type, position);
	}

	public CompoundTestCase getParent() {
		return parent;
	}

	@Override
	public VariableReference getRandomObject() {
		return delegate.getRandomObject();
	}

	@Override
	public VariableReference getRandomObject(int position) {
		return delegate.getRandomObject(position);
	}

	@Override
	public VariableReference getRandomObject(Type type) throws ConstructionFailedException {
		return delegate.getRandomObject(type);
	}

	@Override
	public VariableReference getRandomObject(Type type, int position) throws ConstructionFailedException {
		return delegate.getRandomObject(type, position);
	}

	@Override
	public Set<VariableReference> getReferences(VariableReference var) {
		return delegate.getReferences(var);
	}

	@Override
	public VariableReference getReturnValue(int position) {
		return delegate.getReturnValue(position);
	}

	@Override
	public StatementInterface getStatement(int position) {
		return delegate.getStatement(position);
	}

	public IVariableBinding getVariableBinding(VariableReference varRef) {
		for (Map.Entry<IVariableBinding, VariableReference> entry : methodVars.entrySet()) {
			if (entry.getValue() == varRef) {
				return entry.getKey();
			}
		}
		for (Map.Entry<IVariableBinding, VariableReference> entry : fieldVars.entrySet()) {
			if (entry.getValue() == varRef) {
				return entry.getKey();
			}
		}
		return null;
	}

	public VariableReference getVariableReference(IVariableBinding varBinding) {
		VariableReference varRef = methodVars.get(varBinding);
		if (varRef != null) {
			return varRef;
		}
		return fieldVars.get(varBinding);
	}

	@Override
	public boolean hasAssertions() {
		return delegate.hasAssertions();
	}

	@Override
	public boolean hasCalls() {
		return delegate.hasCalls();
	}

	@Override
	public boolean hasCastableObject(Type type) {
		return delegate.hasCastableObject(type);
	}

	@Override
	public boolean hasObject(Type type, int position) {
		return delegate.hasObject(type, position);
	}

	@Override
	public boolean hasReferences(VariableReference var) {
		return delegate.hasReferences(var);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean isFinished() {
		return delegate != null;
	}

	@Override
	public boolean isPrefix(TestCase t) {
		return delegate.isPrefix(t);
	}

	@Override
	public boolean isValid() {
		return delegate.isValid();
	}

	@Override
	public Iterator<StatementInterface> iterator() {
		return delegate.iterator();
	}

	@Override
	public void remove(int position) {
		delegate.remove(position);
	}

	@Override
	public void removeAssertions() {
		delegate.removeAssertions();
	}

	@Override
	public void replace(VariableReference var1, VariableReference var2) {
		delegate.replace(var1, var2);
	}

	public void setCurrentScope(TestScope scope) {
		this.currentScope = scope;
		methodVars.clear();
	}

	public void setParent(CompoundTestCase parent) {
		this.parent = parent;
	}

	@Override
	public VariableReference setStatement(StatementInterface statement, int position) {
		return delegate.setStatement(statement, position);
	}

	@Override
	public int size() {
		if (delegate == null) {
			return 0;
		}
		return delegate.size();
	}

	@Override
	public String toCode() {
		return delegate.toCode();
	}

	@Override
	public String toCode(Map<Integer, Throwable> exceptions) {
		return delegate.toCode(exceptions);
	}

	@Override
	public String toString() {
		if (delegate != null) {
			return delegate.toString();
		}
		return super.toString();
	}

	private List<StatementInterface> getDeinitializationCode() {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		result.addAll(after);
		result.addAll(afterClass);
		if (parent != null) {
			List<StatementInterface> parentStatements = parent.getDeinitializationCode();
			result.addAll(parentStatements);
		}
		return result;
	}

	private List<StatementInterface> getInitializationCode() {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		if (parent != null) {
			List<StatementInterface> parentStatements = parent.getInitializationCode();
			result.addAll(parentStatements);
		}
		result.addAll(staticCode);
		result.addAll(fields);
		result.addAll(constructor);
		result.addAll(beforeClass);
		result.addAll(before);
		return result;
	}
}
