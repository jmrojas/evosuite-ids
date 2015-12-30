package org.evosuite.idNaming;

import com.examples.with.different.packagename.ClassWithOverloadedConstructor;
import org.evosuite.coverage.branch.Branch;
import org.evosuite.coverage.branch.BranchCoverageGoal;
import org.evosuite.coverage.branch.BranchCoverageTestFitness;
import org.evosuite.coverage.exception.ExceptionCoverageTestFitness;
import org.evosuite.coverage.input.InputCoverageGoal;
import org.evosuite.coverage.input.InputCoverageTestFitness;
import org.evosuite.coverage.method.MethodCoverageTestFitness;
import org.evosuite.coverage.method.MethodNoExceptionCoverageTestFitness;
import org.evosuite.coverage.output.OutputCoverageFactory;
import org.evosuite.coverage.output.OutputCoverageGoal;
import org.evosuite.coverage.output.OutputCoverageTestFitness;
import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.evosuite.runtime.mock.java.lang.MockArithmeticException;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.numeric.IntPrimitiveStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericMethod;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by gordon on 22/12/2015.
 */
public class TestCoverageGoalNameGeneration {

    @Test
    public void testUniqueMethod() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal = new MethodCoverageTestFitness("FooClass", "toString");
        test.addCoveredGoal(goal);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testToString", generatedName);
    }

    @Test
    public void testUniqueConstructor() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal = new MethodCoverageTestFitness("FooClass", "<init>");
        test.addCoveredGoal(goal);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testCreatesFooClass", generatedName);
    }

    @Test
    public void testTwoTestsUniqueMethods() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "getSomeStuff");
        test2.addCoveredGoal(goal2);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToString",     naming.getName(test1));
        assertEquals("testGetSomeStuff", naming.getName(test2));
    }

    @Test
    public void testTwoTestsMethodsWithSharedGoals() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "getSomeStuff");
        test2.addCoveredGoal(goal2);

        MethodCoverageTestFitness goal3 = new MethodCoverageTestFitness("FooClass", "doFooBar");
        test1.addCoveredGoal(goal3);
        test2.addCoveredGoal(goal3);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToString",     naming.getName(test1));
        assertEquals("testGetSomeStuff", naming.getName(test2));
    }

    @Test
    public void testMethodWithAndWithoutException() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString()");
        test1.addCoveredGoal(goal1);
        MethodNoExceptionCoverageTestFitness goal1a = new MethodNoExceptionCoverageTestFitness("FooClass", "toString()");
        test1.addCoveredGoal(goal1a);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal1);
        ExceptionCoverageTestFitness goal2 = new ExceptionCoverageTestFitness("FooClass", "toString()", RuntimeException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test2.addCoveredGoal(goal2);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToString", naming.getName(test1));
        assertEquals("testToStringThrowsRuntimeException", naming.getName(test2));
    }

    @Test
    public void testMethodWithAndWithoutMockException() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString()");
        test1.addCoveredGoal(goal1);
        MethodNoExceptionCoverageTestFitness goal1a = new MethodNoExceptionCoverageTestFitness("FooClass", "toString()");
        test1.addCoveredGoal(goal1a);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal1);
        ExceptionCoverageTestFitness goal2 = new ExceptionCoverageTestFitness("FooClass", "toString()", MockArithmeticException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test2.addCoveredGoal(goal2);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToString", naming.getName(test1));
        assertEquals("testToStringThrowsArithmeticException", naming.getName(test2));
    }


    @Test
    public void testMethodWithExceptions() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString()");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "toString2()");
        test2.addCoveredGoal(goal2);

        ExceptionCoverageTestFitness goal3 = new ExceptionCoverageTestFitness("FooClass", "toString()", MockArithmeticException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test1.addCoveredGoal(goal3);
        test2.addCoveredGoal(goal3);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToString", naming.getName(test1));
        assertEquals("testToString2", naming.getName(test2));
    }

    @Test
    public void testConstructorWithAndWithoutException() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "<init>()");
        test1.addCoveredGoal(goal1);
        MethodNoExceptionCoverageTestFitness goal1a = new MethodNoExceptionCoverageTestFitness("FooClass", "<init>()");
        test1.addCoveredGoal(goal1a);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal1);
        ExceptionCoverageTestFitness goal2 = new ExceptionCoverageTestFitness("FooClass", "<init>()", RuntimeException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test2.addCoveredGoal(goal2);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testCreatesFooClass", naming.getName(test1));
        assertEquals("testFailsToCreateFooClassThrowsRuntimeException", naming.getName(test2));
    }

    @Test
    public void testMethodWithOutputGoals() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString");
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "toString", "String", "Null");
        OutputCoverageTestFitness goal2 = new OutputCoverageTestFitness(outputGoal1);
        test1.addCoveredGoal(goal1);
        test1.addCoveredGoal(goal2);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal1);
        OutputCoverageGoal outputGoal2 = new OutputCoverageGoal("FooClass", "toString", "String", "NonNull");
        OutputCoverageTestFitness goal3 = new OutputCoverageTestFitness(outputGoal2);
        test2.addCoveredGoal(goal3);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToStringReturningNull",    naming.getName(test1));
        assertEquals("testToStringReturningNonNull", naming.getName(test2));
    }

    @Test
    public void testMethodWithInputGoals() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString");
        InputCoverageGoal inputGoal1 = new InputCoverageGoal("FooClass", "toString", 0, "String", "Null");
        InputCoverageTestFitness goal2 = new InputCoverageTestFitness(inputGoal1);
        test1.addCoveredGoal(goal1);
        test1.addCoveredGoal(goal2);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal1);
        InputCoverageGoal inputGoal2 = new InputCoverageGoal("FooClass", "toString", 0, "String", "NonNull");
        InputCoverageTestFitness goal3 = new InputCoverageTestFitness(inputGoal2);
        test2.addCoveredGoal(goal3);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToStringWithNull",    naming.getName(test1));
        assertEquals("testToStringWithNonNull", naming.getName(test2));
    }

    @Test
    public void testMethodWithInputOutputGoals() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString");
        InputCoverageGoal inputGoal1 = new InputCoverageGoal("FooClass", "toString", 0, "String", "Null");
        InputCoverageTestFitness goal2 = new InputCoverageTestFitness(inputGoal1);
        test1.addCoveredGoal(goal1);
        test1.addCoveredGoal(goal2);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal1);
        OutputCoverageGoal outputGoal2 = new OutputCoverageGoal("FooClass", "toString", "String", "NonNull");
        OutputCoverageTestFitness goal3 = new OutputCoverageTestFitness(outputGoal2);
        test2.addCoveredGoal(goal3);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToStringWithNull",    naming.getName(test1));
        assertEquals("testToStringReturningNonNull", naming.getName(test2));
    }

    @Test
    public void testOverloadedMethod() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "foo()");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "foo(I)");
        test2.addCoveredGoal(goal2);

        TestCase test3 = new DefaultTestCase();
        test3.addStatement(new IntPrimitiveStatement(test3, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal3 = new MethodCoverageTestFitness("FooClass", "foo(II)");
        test3.addCoveredGoal(goal3);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooWithoutArguments",     naming.getName(test1));
        assertEquals("testFooWithInt", naming.getName(test2));
        assertEquals("testFooWith2Arguments", naming.getName(test3));
    }

    @Test
    public void testOverloadedMethodWithObject() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "foo()");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "foo(Ljava/util/List;)");
        test2.addCoveredGoal(goal2);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooWithoutArguments",     naming.getName(test1));
        assertEquals("testFooWithList", naming.getName(test2));
    }

    @Test
    public void testOverloadedMethodWithArray() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "foo(I)");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "foo([I)");
        test2.addCoveredGoal(goal2);

        TestCase test3 = new DefaultTestCase();
        test3.addStatement(new IntPrimitiveStatement(test3, 0)); // Need to add statements to change hashCode
        MethodCoverageTestFitness goal3 = new MethodCoverageTestFitness("FooClass", "foo([[I)");
        test3.addCoveredGoal(goal3);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooWithInt",     naming.getName(test1));
        assertEquals("testFooWithIntArray", naming.getName(test2));
        assertEquals("testFooWithIntArrayArray", naming.getName(test3));
    }


    @Test
    public void testTwoTestsDifferOnlyInBranches() {
        Branch b1 = mock(Branch.class);
        BytecodeInstruction bi = mock(BytecodeInstruction.class);
        when(b1.getInstruction()).thenReturn(bi);
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness methodGoal = new MethodCoverageTestFitness("FooClass", "toString");
        test1.addCoveredGoal(methodGoal);
        BranchCoverageTestFitness branchGoal1 = new BranchCoverageTestFitness(new BranchCoverageGoal(b1, true, "FooClass", "toStringBarFooBlubb", 0));
        test1.addCoveredGoal(branchGoal1);

        TestCase test2 = new DefaultTestCase();
        test2.addCoveredGoal(methodGoal);
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        BranchCoverageTestFitness branchGoal2 = new BranchCoverageTestFitness(new BranchCoverageGoal(b1, false, "FooClass", "toString", 0));
        test2.addCoveredGoal(branchGoal2);


        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testToString0", naming.getName(test1));
        assertEquals("testToString1", naming.getName(test2));
    }

    @Test
    public void testTwoUniqueMethods() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "foo");
        test.addCoveredGoal(goal1);
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "bar");
        test.addCoveredGoal(goal2);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testBarAndFoo", generatedName);
    }

    @Test
    public void testMultipleMethods() throws NoSuchMethodException {
        TestCase test = new DefaultTestCase();
        GenericConstructor gc = new GenericConstructor(Object.class.getConstructor(), Object.class);
        VariableReference callee = test.addStatement(new ConstructorStatement(test, gc, new ArrayList<VariableReference>()));
        GenericMethod gm = new GenericMethod(Object.class.getMethod("toString"), Object.class);
        test.addStatement(new MethodStatement(test, gm, callee, new ArrayList<VariableReference>()));
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "toString()Ljava/lang/String;");
        test.addCoveredGoal(goal1);
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "foo()Ljava/lang/String;");
        test.addCoveredGoal(goal2);
        MethodCoverageTestFitness goal3 = new MethodCoverageTestFitness("FooClass", "bar()Ljava/lang/String;");
        test.addCoveredGoal(goal3);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testToString", generatedName);
        // TODO: What should be the name now? Need some heuristic, currently sorted alphabetically
        //       Better heuristic would consider other things, like e.g. which method has more goals covered
        //       or which one is the last one called?
    }

    @Test
    public void testTwoOutputGoals() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "toString", "String", "NonNull");
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        OutputCoverageGoal outputGoal2 = new OutputCoverageGoal("FooClass", "bar", "String", "NonNull");
        OutputCoverageTestFitness goal2 = new OutputCoverageTestFitness(outputGoal2);
        test.addCoveredGoal(goal1);
        test.addCoveredGoal(goal2);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testBarReturningNonNullAndToStringReturningNonNull", generatedName);
    }

    @Test
    public void testTwoInputGoals() {
        TestCase test = new DefaultTestCase();
        InputCoverageGoal inputGoal1 = new InputCoverageGoal("FooClass", "foo", 0, "String", "NonNull");
        InputCoverageTestFitness goal1 = new InputCoverageTestFitness(inputGoal1);
        InputCoverageGoal inputGoal2 = new InputCoverageGoal("FooClass", "foo", 1, "String", "Null");
        InputCoverageTestFitness goal2 = new InputCoverageTestFitness(inputGoal2);
        test.addCoveredGoal(goal1);
        test.addCoveredGoal(goal2);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testFooWithNonNullAndNull", generatedName);
    }

    @Test
    public void testTwoMethodsOneWithException() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "foo");
        test.addCoveredGoal(goal1);
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "bar");
        test.addCoveredGoal(goal2);
        ExceptionCoverageTestFitness goal3 = new ExceptionCoverageTestFitness("FooClass", "bar", RuntimeException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test.addCoveredGoal(goal3);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testBarThrowsRuntimeException", generatedName);
    }

    @Test
    public void testTwoTestsTwoMethodsOneWithException() {
        TestCase test1 = new DefaultTestCase();
        TestCase test2 = new DefaultTestCase();
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode

        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "foo");
        test1.addCoveredGoal(goal1);
        test2.addCoveredGoal(goal1);

        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "bar()I");
        test1.addCoveredGoal(goal2);
        test2.addCoveredGoal(goal2);

        ExceptionCoverageTestFitness goal3 = new ExceptionCoverageTestFitness("FooClass", "bar()I", RuntimeException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test1.addCoveredGoal(goal3);
        MethodNoExceptionCoverageTestFitness goal4 = new MethodNoExceptionCoverageTestFitness("FooClass", "bar()I");
        test2.addCoveredGoal(goal4);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName1 = naming.getName(test1);
        String generatedName2 = naming.getName(test2);
        assertEquals("testBarThrowsRuntimeException", generatedName1);
        assertEquals("testBar", generatedName2);
    }

    @Test
    public void testTwoConstructorsDifferentClasses() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("Foo", "<init>()");
        test.addCoveredGoal(goal1);
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("Bar", "<init>()");
        test.addCoveredGoal(goal2);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testCreatesBarAndCreatesFoo", generatedName);
    }

    @Test
    public void testTwoConstructorsSameClass() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("FooClass", "<init>()");
        test.addCoveredGoal(goal1);
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("FooClass", "<init>(I)");
        test.addCoveredGoal(goal2);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testCreatesFooClassWithoutArgumentsAndCreatesFooClassWithInt", generatedName);
    }

    @Test
    public void testConstructorAndMethod() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness("Foo", "<init>()");
        test.addCoveredGoal(goal1);
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness("Foo", "bar()I");
        test.addCoveredGoal(goal2);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testCreatesFooAndCallsBar", generatedName);
    }


    @Test
    public void testConstructorWithFullyQualifiedClassName() {
        TestCase test = new DefaultTestCase();
        MethodCoverageTestFitness goal = new MethodCoverageTestFitness("org.package.name.FooClass", "<init>()");
        test.addCoveredGoal(goal);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testCreatesFooClass", generatedName);
    }

    @Test
    public void testConstructorExceptionWithFullyQualifiedClassName() {
        TestCase test = new DefaultTestCase();
        ExceptionCoverageTestFitness goal = new ExceptionCoverageTestFitness("org.package.name.FooClass", "<init>()", RuntimeException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test.addCoveredGoal(goal);
        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName = naming.getName(test);
        assertEquals("testFailsToCreateFooClassThrowsRuntimeException", generatedName);
    }

    @Test
    public void testOverloadedConstructor() {
        TestCase test1 = new DefaultTestCase();
        MethodCoverageTestFitness goal1 = new MethodCoverageTestFitness(ClassWithOverloadedConstructor.class.getCanonicalName(), "<init>()");
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        MethodCoverageTestFitness goal2 = new MethodCoverageTestFitness(ClassWithOverloadedConstructor.class.getCanonicalName(), "<init>(Ljava/lang/String;)");
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal2);

        TestCase test3 = new DefaultTestCase();
        MethodCoverageTestFitness goal3 = new MethodCoverageTestFitness(ClassWithOverloadedConstructor.class.getCanonicalName(), "<init>(II)");
        test3.addStatement(new IntPrimitiveStatement(test3, 1)); // Need to add statements to change hashCode
        test3.addCoveredGoal(goal3);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName1 = naming.getName(test1);
        String generatedName2 = naming.getName(test2);
        String generatedName3 = naming.getName(test3);
        assertEquals("testCreatesClassWithOverloadedConstructorWithoutArguments", generatedName1);
        assertEquals("testCreatesClassWithOverloadedConstructorWithString", generatedName2);
        assertEquals("testCreatesClassWithOverloadedConstructorWith2Arguments", generatedName3);
    }

    @Test
    public void testExceptionInOverloadedConstructor() {
        TestCase test1 = new DefaultTestCase();
        ExceptionCoverageTestFitness goal1 = new ExceptionCoverageTestFitness(ClassWithOverloadedConstructor.class.getCanonicalName(), "<init>()", NullPointerException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test1.addCoveredGoal(goal1);

        TestCase test2 = new DefaultTestCase();
        ExceptionCoverageTestFitness goal2 = new ExceptionCoverageTestFitness(ClassWithOverloadedConstructor.class.getCanonicalName(), "<init>(Ljava/lang/String;)", NullPointerException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test2.addStatement(new IntPrimitiveStatement(test2, 0)); // Need to add statements to change hashCode
        test2.addCoveredGoal(goal2);

        TestCase test3 = new DefaultTestCase();
        ExceptionCoverageTestFitness goal3 = new ExceptionCoverageTestFitness(ClassWithOverloadedConstructor.class.getCanonicalName(), "<init>(II)", NullPointerException.class, ExceptionCoverageTestFitness.ExceptionType.EXPLICIT);
        test3.addStatement(new IntPrimitiveStatement(test3, 1)); // Need to add statements to change hashCode
        test3.addCoveredGoal(goal3);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        String generatedName1 = naming.getName(test1);
        String generatedName2 = naming.getName(test2);
        String generatedName3 = naming.getName(test3);
        assertEquals("testFailsToCreateClassWithOverloadedConstructorWithoutArgumentsThrowsNullPointerException", generatedName1);
        assertEquals("testFailsToCreateClassWithOverloadedConstructorWithStringThrowsNullPointerException", generatedName2);
        assertEquals("testFailsToCreateClassWithOverloadedConstructorWith2ArgumentsThrowsNullPointerException", generatedName3);
    }

    @Test
    public void testBooleanOutputGoal() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "boolean", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.BOOL_TRUE));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningTrue",    naming.getName(test));
    }

    @Test
    public void testNumericOutputGoal() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "int", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.NUM_POSITIVE));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningPositive", naming.getName(test));
    }

    @Test
    public void testCharOutputGoal() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "char", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.CHAR_ALPHA));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningAlpha", naming.getName(test));
    }

    @Test
    public void testNullOutputGoal() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "Object", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.REF_NULL));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningNull", naming.getName(test));
    }

    @Test
    public void testEmptyArray() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "Object[]", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.EMPTY));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningEmpty", naming.getName(test));
    }

    @Test
    public void testStringOutputGoal() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "String", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.REF_NONNULL + ":" + OutputCoverageFactory.NONEMPTY));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningNonempty", naming.getName(test));
    }

    @Test
    public void testInspectorOutputGoal() {
        TestCase test = new DefaultTestCase();
        OutputCoverageGoal outputGoal1 = new OutputCoverageGoal("FooClass", "foo", "String", OutputCoverageFactory.goalString("FooClass", "foo", OutputCoverageFactory.REF_NONNULL + ":" + "Bar" + ":" + "isFoo" + ":" + OutputCoverageFactory.NUM_NEGATIVE));
        OutputCoverageTestFitness goal1 = new OutputCoverageTestFitness(outputGoal1);
        test.addCoveredGoal(goal1);

        List<TestCase> tests = new ArrayList<>();
        tests.add(test);
        CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);
        assertEquals("testFooReturningBarIsFooNegative", naming.getName(test));
    }

}
