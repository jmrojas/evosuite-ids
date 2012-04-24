package de.unisb.cs.st.evosuite.junit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.IVariableBinding;

import de.unisb.cs.st.evosuite.testcase.DefaultTestCase;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.VariableReference;

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
public class CompoundTestCase {
	public static class MethodDef {
		private final String name;
		private final List<VariableReference> params = new ArrayList<VariableReference>();
		private final List<StatementInterface> code = new ArrayList<StatementInterface>();

		public MethodDef(String name) {
			super();
			this.name = name;
		}

		public void add(StatementInterface statement) {
			code.add(statement);
		}

		public List<StatementInterface> getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		public List<VariableReference> getParams() {
			return params;
		}
	}

	public static enum TestScope {
		BEFORE_CLASS, STATIC, STATICFIELDS, BEFORE, FIELDS, CONSTRUCTOR, AFTER, AFTER_CLASS, METHOD;
	}

	public static final String STATIC_BLOCK_METHODNAME = "<static block>";

	private static final long serialVersionUID = 1L;

	private final Map<String, MethodDef> methodDefs = new LinkedHashMap<String, MethodDef>();
	private final Map<String, VariableReference> currentMethodVars = new HashMap<String, VariableReference>();
	private final Map<String, VariableReference> fieldVars = new HashMap<String, VariableReference>();
	private final List<MethodDef> constructors = new ArrayList<MethodDef>();
	private final List<MethodDef> afterMethods = new ArrayList<MethodDef>();
	private final List<MethodDef> afterClassMethods = new ArrayList<MethodDef>();
	private final List<MethodDef> beforeMethods = new ArrayList<MethodDef>();
	private final List<MethodDef> beforeClassMethods = new ArrayList<MethodDef>();
	private final List<StatementInterface> staticFields = new ArrayList<StatementInterface>();
	private final List<StatementInterface> fields = new ArrayList<StatementInterface>();
	private final List<StatementInterface> staticCode = new ArrayList<StatementInterface>();
	private final String className;
	private final String testMethod;
	private CompoundTestCase parent;
	private final CompoundTestCase originalDescendant;
	private TestScope currentScope = TestScope.FIELDS;
	private MethodDef currentMethod = null;

	private final DelegatingTestCase delegate;

	public CompoundTestCase(String className, CompoundTestCase child) {
		child.parent = this;
		delegate = child.getReference();
		originalDescendant = child.originalDescendant;
		this.testMethod = null;
		this.className = className;
	}

	public CompoundTestCase(String className, String methodName) {
		this.testMethod = methodName;
		this.className = className;
		delegate = new DelegatingTestCase();
		originalDescendant = this;
	}

	public void addParameter(VariableReference varRef) {
		currentMethod.getParams().add(varRef);
	}

	public void addStatement(StatementInterface statement) {
		if (currentScope == TestScope.FIELDS) {
			fields.add(statement);
			return;
		}
		if (currentScope == TestScope.STATICFIELDS) {
			staticFields.add(statement);
			return;
		}
		if (currentScope == TestScope.STATIC) {
			staticCode.add(statement);
			return;
		}
		currentMethod.add(statement);
	}

	// TODO Problem: What if a variable is doubly created with the same name?
	// Which one to reference?
	// TODO Problem: What if a method is overriden? Need to call correct
	// method...
	public void addVariable(IVariableBinding varBinding, VariableReference varRef) {
		if ((currentScope == TestScope.FIELDS) || (currentScope == TestScope.STATICFIELDS)) {
			fieldVars.put(varBinding.toString(), varRef);
			return;
		}
		currentMethodVars.put(varBinding.toString(), varRef);
	}

	public void convertMethod(MethodDef methodDef, List<VariableReference> params) {
		assert methodDef.getParams().size() == params.size();
		for (StatementInterface statement : methodDef.getCode()) {
			for (int idx = 0; idx < params.size(); idx++) {
				statement.replace(methodDef.getParams().get(idx), params.get(idx));
			}
			addStatement(statement);
		}
		// for each methodstatement: if it affects an external
		// variablereference, replace in all followups
	}

	public void finalizeMethod() {
		String currentMethodName = currentMethod.getName();
		methodDefs.put(currentMethodName, currentMethod);
		switch (currentScope) {
		case AFTER:
			afterMethods.add(currentMethod);
			break;
		case AFTER_CLASS:
			afterClassMethods.add(currentMethod);
			break;
		case BEFORE:
			beforeMethods.add(currentMethod);
			break;
		case BEFORE_CLASS:
			beforeClassMethods.add(currentMethod);
			break;
		case CONSTRUCTOR:
			constructors.add(currentMethod);
			break;
		}
		currentScope = TestScope.FIELDS;
		currentMethod = null;
		currentMethodVars.clear();
	}

	public TestCase finalizeTestCase() {
		// TODO General problem: If methods change vars, this needs to be
		// reflected in followup code
		// e.g. value = 34 => int3 = 34; means that all other methods now need
		// to use int3 instead of int2.
		Set<String> overridenMethods = Collections.emptySet();
		delegate.setDelegate(new DefaultTestCase());
		delegate.addStatements(getStaticInitializationBeforeClassMethods(overridenMethods));
		delegate.addStatements(getInitializationCode());
		delegate.addStatements(getBeforeMethods(overridenMethods));
		if (testMethod == null) {
			throw new RuntimeException("Test did not contain any statements!");
		}
		delegate.addStatements(methodDefs.get(testMethod).getCode());
		delegate.addStatements(getAfterMethods(overridenMethods));
		delegate.addStatements(getAfterClassMethods(overridenMethods));
		return delegate;
	}

	public TestScope getCurrentScope() {
		return currentScope;
	}

	public MethodDef getMethod(String name) {
		if (originalDescendant != this) {
			return originalDescendant.getMethodInternally(name);
		}
		return getMethodInternally(name);
	}

	public CompoundTestCase getParent() {
		return parent;
	}

	public DelegatingTestCase getReference() {
		return delegate;
	}

	public VariableReference getVariableReference(IVariableBinding varBinding) {
		if (originalDescendant != this) {
			return originalDescendant.getVariableReferenceInternally(varBinding);
		}
		return getVariableReferenceInternally(varBinding);
	}

	public boolean isDescendantOf(Class<?> declaringClass) {
		if (parent == null) {
			return false;
		}
		if (parent.className.equals(declaringClass.getName())) {
			return true;
		}
		return parent.isDescendantOf(declaringClass);
	}

	public void newMethod(String methodName) {
		assert (currentMethod == null) || currentMethod.getCode().isEmpty();
		assert currentMethodVars.isEmpty();
		currentScope = TestScope.METHOD;
		currentMethod = new MethodDef(methodName);
	}

	public void setCurrentScope(TestScope scope) {
		this.currentScope = scope;
	}

	public void variableAssignment(VariableReference varRef, VariableReference newAssignment) {
		for (Map.Entry<String, VariableReference> entry : currentMethodVars.entrySet()) {
			if (entry.getValue() == varRef) {
				currentMethodVars.put(entry.getKey(), newAssignment);
				return;
			}
		}
		for (Map.Entry<String, VariableReference> entry : fieldVars.entrySet()) {
			if (entry.getValue() == varRef) {
				fieldVars.put(entry.getKey(), newAssignment);
				return;
			}
		}
		if (parent != null) {
			for (Map.Entry<String, VariableReference> entry : parent.fieldVars.entrySet()) {
				if (entry.getValue() == varRef) {
					parent.fieldVars.put(entry.getKey(), newAssignment);
					return;
				}
			}
		}
		throw new RuntimeException("Assignment " + varRef + " not found!");
	}

	private List<StatementInterface> getAfterClassMethods(Set<String> overridenMethods) {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		for (MethodDef methodDef : afterClassMethods) {
			if (!overridenMethods.contains(methodDef.getName())) {
				result.addAll(methodDef.getCode());
			}
		}
		if (parent != null) {
			// @AfterClass IF NOT OVERRIDEN
			// According to Kent Beck, there is no defined order
			// in which methods of the same leve within one class are called:
			// http://tech.groups.yahoo.com/group/junit/message/20758
			result.addAll(parent.getAfterClassMethods(methodDefs.keySet()));
		}
		return result;
	}

	private List<StatementInterface> getAfterMethods(Set<String> overridenMethods) {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		// @After
		for (MethodDef methodDef : afterMethods) {
			if (!overridenMethods.contains(methodDef.getName())) {
				result.addAll(methodDef.getCode());
			}
		}
		if (parent != null) {
			// parent: @After
			result.addAll(parent.getAfterMethods(methodDefs.keySet()));
		}
		return result;
	}

	private List<StatementInterface> getBeforeMethods(Set<String> overridenMethods) {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		if (parent != null) {
			result.addAll(parent.getBeforeMethods(methodDefs.keySet()));
		}
		// @Before IF NOT OVERRIDEN
		// According to Kent Beck, there is no defined order
		// in which methods of the same leve within one class are called:
		// http://tech.groups.yahoo.com/group/junit/message/20758
		for (MethodDef methodDef : beforeMethods) {
			if (!overridenMethods.contains(methodDef.getName())) {
				result.addAll(methodDef.getCode());
			}
		}
		return result;
	}

	private List<StatementInterface> getInitializationCode() {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		if (parent != null) {
			result.addAll(parent.getInitializationCode());
		}
		// initialization
		result.addAll(fields);
		// constructor
		result.addAll(getNoArgsConstructor());
		return result;
	}

	private MethodDef getMethodInternally(String name) {
		MethodDef result = methodDefs.get(name);
		if (result != null) {
			return result;
		}
		if (parent != null) {
			return parent.getMethodInternally(name);
		}
		throw new RuntimeException("Method " + name + " not found!");
	}

	private List<StatementInterface> getNoArgsConstructor() {
		for (MethodDef constructor : constructors) {
			if (constructor.getParams().isEmpty()) {
				return constructor.getCode();
			}
		}
		if (constructors.size() > 1) {
			throw new RuntimeException("Found " + constructors.size() + " constructors, but on no-args constructor!");
		}
		return Collections.emptyList();
	}

	private List<StatementInterface> getStaticInitializationBeforeClassMethods(Set<String> overridenMethods) {
		List<StatementInterface> result = new ArrayList<StatementInterface>();
		if (parent != null) {
			// @BeforeClass IF NOT OVERRIDEN
			// According to Kent Beck, there is no defined order
			// in which methods of the same leve within one class are called:
			// http://tech.groups.yahoo.com/group/junit/message/20758
			result.addAll(parent.getStaticInitializationBeforeClassMethods(methodDefs.keySet()));
		}
		// this: static initialization
		result.addAll(staticFields);
		// static blocks
		result.addAll(staticCode);
		// @BeforeClass methods
		for (MethodDef methodDef : beforeClassMethods) {
			if (!overridenMethods.contains(methodDef.getName())) {
				result.addAll(methodDef.getCode());
			}
		}
		return result;
	}

	private VariableReference getVariableReferenceInternally(IVariableBinding varBinding) {
		VariableReference varRef = currentMethodVars.get(varBinding.toString());
		if (varRef != null) {
			return varRef;
		}
		varRef = fieldVars.get(varBinding.toString());
		if (varRef != null) {
			return varRef;
		}
		if (parent != null) {
			return parent.getVariableReferenceInternally(varBinding);
		}
		return null;
	}
}
