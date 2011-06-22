/*
 * Copyright (C) 2011 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.symbolic;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import de.unisb.cs.st.evosuite.testcase.ExecutionResult;
import de.unisb.cs.st.evosuite.testcase.PrimitiveStatement;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;
import de.unisb.cs.st.evosuite.testcase.TestCase;
import de.unisb.cs.st.evosuite.testcase.TestCaseExecutor;
import de.unisb.cs.st.evosuite.testcase.TestChromosome;

/**
 * @author Gordon Fraser
 * 
 */
public class ConcolicExecution {

	@SuppressWarnings("unused")
	private List<gov.nasa.jpf.Error> errors;

	private static Logger logger = Logger.getLogger(ConcolicExecution.class);

	private PathConstraintCollector pcg;

	protected static String dirName = System.getProperty("java.io.tmpdir")
	        + "/TempClasses";

	protected static String className = "TestCase";
	//	        + Properties.TARGET_CLASS.substring(Properties.TARGET_CLASS.indexOf("."),
	//	                                            Properties.TARGET_CLASS.lastIndexOf("."));

	protected static String classPath = System.getProperty("java.class.path") + ":"
	        + dirName;

	public List<BranchCondition> executeConcolic(String targetName, String classPath) {
		logger.debug("Setting up JPF");

		String[] strs = new String[0];
		Config config = JPF.createConfig(strs);
		config.setProperty("classpath", config.getProperty("classpath") + "," + classPath);
		config.setTarget(targetName);

		config.setProperty("vm.insn_factory.class",
		                   "de.unisb.cs.st.evosuite.symbolic.bytecode.IntegerConcolicInstructionFactory");
		config.setProperty("peer_packages",
		                   "de.unisb.cs.st.evosuite.symbolic.nativepeer,"
		                           + config.getProperty("peer_packages"));

		// We don't want JPF output
		config.setProperty("report.class",
		                   "de.unisb.cs.st.evosuite.symbolic.SilentReporter");

		//Configure the search class;
		config.setProperty("search.class", "de.unisb.cs.st.evosuite.symbolic.PathSearch");
		config.setProperty("jm.numberOfIterations", "1");

		//Generate the JPF Instance
		JPF jpf = new JPF(config);
		this.pcg = new PathConstraintCollector();
		jpf.getVM().addListener(pcg);
		jpf.getSearch().addListener(pcg);

		//Run the SUT
		logger.debug("Running concolic execution");
		jpf.run();
		logger.debug("Finished concolic execution");
		logger.debug("Conditions collected: " + pcg.conditions.size());

		this.errors = jpf.getSearch().getErrors();

		return pcg.conditions;
	}

	/**
	 * Retrieve the path condition for a given test cae
	 * 
	 * @param test
	 * @return
	 */
	public List<BranchCondition> getSymbolicPath(TestChromosome test) {

		writeTestCase(getPrimitives(test.test), test);
		List<BranchCondition> conditions = executeConcolic(className, classPath);

		return conditions;
	}

	/**
	 * Get the method that needs to be used to mark this primitive value as
	 * symbolic
	 * 
	 * @param statement
	 * @return
	 */
	private Method getMarkMethod(PrimitiveStatement<?> statement) {
		Class<?> clazz = statement.getReturnValue().getVariableClass();
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
			return org.objectweb.asm.commons.Method.getMethod("boolean mark(boolean,String)");
		else if (clazz.equals(Character.class) || clazz.equals(char.class))
			return org.objectweb.asm.commons.Method.getMethod("char mark(char,String)");
		else if (clazz.equals(Integer.class) || clazz.equals(int.class))
			return org.objectweb.asm.commons.Method.getMethod("int mark(int,String)");
		else if (clazz.equals(Short.class) || clazz.equals(short.class))
			return org.objectweb.asm.commons.Method.getMethod("short mark(short,String)");
		else if (clazz.equals(Long.class) || clazz.equals(long.class))
			return org.objectweb.asm.commons.Method.getMethod("long mark(long,String)");
		else if (clazz.equals(Float.class) || clazz.equals(float.class))
			return org.objectweb.asm.commons.Method.getMethod("float mark(float,String)");
		else if (clazz.equals(Double.class) || clazz.equals(double.class))
			return org.objectweb.asm.commons.Method.getMethod("double mark(double,String)");
		else if (clazz.equals(Byte.class) || clazz.equals(byte.class))
			return org.objectweb.asm.commons.Method.getMethod("byte mark(byte,String)");
		else if (clazz.equals(String.class))
			// FIXME: Probably not for Strings?
			return org.objectweb.asm.commons.Method.getMethod("String mark(String,String)");
		else {
			logger.fatal("Found primitive of unknown type: " + clazz.getName());
			return null; // FIXME
		}
	}

	/**
	 * Concrete execution
	 * 
	 * @param test
	 * @return
	 */
	private ExecutionResult runTest(TestCase test) {

		ExecutionResult result = new ExecutionResult(test, null);

		try {
			logger.debug("Executing test");
			result = TestCaseExecutor.getInstance().execute(test);
		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			e.printStackTrace();
			System.exit(1);
		}

		return result;
	}

	public List<PrimitiveStatement> getPrimitives(TestCase test) {
		List<PrimitiveStatement> p = new ArrayList<PrimitiveStatement>();
		for (StatementInterface s : test) {
			if (s instanceof PrimitiveStatement) {
				PrimitiveStatement ps = (PrimitiveStatement) s;
				Class<?> t = ps.getReturnClass();
				if (t.equals(Integer.class) || t.equals(int.class)) {
					p.add(ps);
				} else if (t.equals(Boolean.class) || t.equals(boolean.class)) {
					p.add(ps);
				} else if (t.equals(Short.class) || t.equals(short.class)) {
					p.add(ps);
				} else if (t.equals(Byte.class) || t.equals(byte.class)) {
					p.add(ps);
				} else if (t.equals(Long.class) || t.equals(long.class)) {
					p.add(ps);
				} else if (t.equals(Character.class) || t.equals(char.class)) {
					p.add(ps);
				}
			}
		}

		return p;
	}

	/**
	 * Get concrete value for parameter
	 * 
	 * @param mg
	 * @param locals
	 * @param statement
	 */
	private void getPrimitiveValue(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        PrimitiveStatement<?> statement) {
		//Class<?> clazz = statement.getReturnValue().getVariableClass();
		Class<?> clazz = statement.getValue().getClass();
		if (!clazz.equals(statement.getReturnValue().getVariableClass())) {
			mg.cast(org.objectweb.asm.Type.getType(clazz),
			        org.objectweb.asm.Type.getType(statement.getReturnValue().getVariableClass()));
		}

		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
			mg.push(((Boolean) statement.getValue()).booleanValue());
		else if (clazz.equals(Character.class) || clazz.equals(char.class))
			mg.push(((Character) statement.getValue()).charValue());
		else if (clazz.equals(Integer.class) || clazz.equals(int.class))
			mg.push(((Integer) statement.getValue()).intValue());
		else if (clazz.equals(Short.class) || clazz.equals(short.class))
			mg.push(((Short) statement.getValue()).shortValue());
		else if (clazz.equals(Long.class) || clazz.equals(long.class))
			mg.push(((Long) statement.getValue()).longValue());
		else if (clazz.equals(Float.class) || clazz.equals(float.class))
			mg.push(((Float) statement.getValue()).floatValue());
		else if (clazz.equals(Double.class) || clazz.equals(double.class))
			mg.push(((Double) statement.getValue()).doubleValue());
		else if (clazz.equals(Byte.class) || clazz.equals(byte.class))
			mg.push(((Byte) statement.getValue()).byteValue());
		else if (clazz.equals(String.class))
			mg.push(((String) statement.getValue()));
		else
			logger.fatal("Found primitive of unknown type: " + clazz.getName());
	}

	/**
	 * Create the bytecode of a class that calls the test with the primitives
	 * marked as symbolic
	 * 
	 * @param target
	 * @param test
	 * @return
	 */
	private byte[] getBytecode(List<PrimitiveStatement> target, TestChromosome test) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, className, null,
		         "java/lang/Object", null);

		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(Opcodes.ACC_PUBLIC, m, null, null, cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(Object.class), m);
		mg.returnValue();
		mg.endMethod();

		int num = 0;
		ExecutionResult result = test.last_result;
		if (result == null)
			result = runTest(test.test);

		// main method
		m = Method.getMethod("void main (String[])");
		mg = new GeneratorAdapter(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, m, null, null,
		        cw);
		Map<Integer, Integer> locals = new HashMap<Integer, Integer>();
		for (StatementInterface statement : test.test) {
			logger.debug("Current statement: " + statement.getCode());
			if (target.contains(statement)) {
				PrimitiveStatement<?> p = (PrimitiveStatement<?>) statement;
				getPrimitiveValue(mg, locals, p); // TODO: Possibly cast?
				mg.push(p.getReturnValue().getName());
				//mg.invokeStatic(Type.getType("Ljpf/mytest/primitive/ConcolicMarker;"),
				//                getMarkMethod(p));
				mg.invokeStatic(Type.getType("Lde/unisb/cs/st/evosuite/symbolic/nativepeer/ConcolicMarker;"),
				                getMarkMethod(p));
				p.getReturnValue().storeBytecode(mg, locals);

			} else {
				statement.getBytecode(mg, locals, result.exceptions.get(num));
			}
			num++;
		}
		mg.visitInsn(Opcodes.RETURN);
		mg.endMethod();
		cw.visitEnd();

		return cw.toByteArray();
	}

	/**
	 * Write a test case to disk using the specified symbolic values
	 * 
	 * @param statements
	 * @param test
	 */
	public void writeTestCase(List<PrimitiveStatement> statements, TestChromosome test) {
		File dir = new File(dirName);
		dir.mkdir();
		File file = new File(dirName + "/", className + ".class");
		try {
			FileOutputStream stream = new FileOutputStream(file);
			byte[] bytecode = getBytecode(statements, test);
			stream.write(bytecode);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

}
