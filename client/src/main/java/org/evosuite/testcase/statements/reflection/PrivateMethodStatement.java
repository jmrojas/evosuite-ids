/**
 * Copyright (C) 2010-2015 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser Public License as published by the
 * Free Software Foundation, either version 3.0 of the License, or (at your
 * option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along
 * with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.testcase.statements.reflection;

import org.evosuite.runtime.PrivateAccess;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.ConstantValue;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericClass;
import org.evosuite.utils.generic.GenericMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Test case statement representing a reflection call to a private method of the SUT
 *
 * Created by Andrea Arcuri on 22/02/15.
 */
public class PrivateMethodStatement extends MethodStatement {

	private static final long serialVersionUID = -4555899888145880432L;

    private String className;

    private String methodName;

	public PrivateMethodStatement(TestCase tc, Class<?> klass , String methodName, VariableReference callee, List<VariableReference> params) {
        super(
                tc,
                new GenericMethod(PrivateAccess.getCallMethod(params.size()),PrivateAccess.class),
                null, //it is static
                getReflectionParams(tc,klass,methodName,callee,params)
        );
        List<GenericClass> parameterTypes = new ArrayList<>();
        parameterTypes.add(new GenericClass(klass));
        this.method.setTypeParameters(parameterTypes);
        this.methodName = methodName;
        this.className = klass.getCanonicalName();
    }

    private static List<VariableReference> getReflectionParams(TestCase tc, Class<?> klass , String methodName,
                                                               VariableReference callee, List<VariableReference> inputs) {

        List<VariableReference> list = new ArrayList<>(3 + inputs.size()*2);
        list.add(new ConstantValue(tc,new GenericClass(Class.class),klass));
        list.add(callee);
        list.add(new ConstantValue(tc, new GenericClass(String.class), methodName));

        for(VariableReference vr : inputs){
            list.add(vr);
            list.add(new ConstantValue(tc,new GenericClass(Class.class),vr.getVariableClass()));
        }

        return list;
    }

    @Override
    public boolean mutate(TestCase test, TestFactory factory) {
        // just for simplicity
        return false;
        //return super.mutate(test,factory); //tricky, as should do some restrictions
    }

    @Override
    public Statement copy(TestCase newTestCase, int offset) {
        PrivateMethodStatement pm;
        List<VariableReference> newParams = new ArrayList<>();
        for(int i = 3; i < parameters.size(); i = i + 2) {
            newParams.add(parameters.get(i).copy(newTestCase, offset));
        }

        VariableReference newCallee = parameters.get(1).copy(newTestCase, offset);
        Class<?> klass = (Class<?>)((ConstantValue)parameters.get(0)).getValue(); // TODO: Make this nice

        if (isStatic()) {
            pm = new PrivateMethodStatement(newTestCase, klass, methodName, newCallee, newParams);
        } else {
            pm = new PrivateMethodStatement(newTestCase, klass, methodName, newCallee, newParams);
        }

        assert pm.parameters.size() == this.parameters.size();

        return pm;
    }


    @Override
	public boolean isReflectionStatement() {
		return true;
	}

}
