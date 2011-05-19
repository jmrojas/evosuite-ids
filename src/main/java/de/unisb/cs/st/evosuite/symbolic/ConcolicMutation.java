/**
 * 
 */
package de.unisb.cs.st.evosuite.symbolic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jpf.mytest.concolic.PathConstraintGathererParent;
import jpf.mytest.integer.IntegerNextChoiceProvider;

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

/**
 * @author Gordon Fraser
 * 
 */
public class ConcolicMutation {

	protected static Logger logger = Logger.getLogger(ConcolicMutation.class);

	protected TestCaseExecutor executor = TestCaseExecutor.getInstance();

	protected static String dirName = System.getProperty("java.io.tmpdir")
	        + "/TempClasses";

	protected static String className = "TestCase";
	//	        + Properties.TARGET_CLASS.substring(Properties.TARGET_CLASS.indexOf("."),
	//	                                            Properties.TARGET_CLASS.lastIndexOf("."));

	protected static String classPath = System.getProperty("java.class.path") + ":"
	        + dirName;

	public ExecutionResult runTest(TestCase test) {

		ExecutionResult result = new ExecutionResult(test, null);

		try {
			logger.debug("Executing test");
			result = executor.execute(test);
			executor.setLogging(true);
		} catch (Exception e) {
			System.out.println("TG: Exception caught: " + e);
			e.printStackTrace();
			System.exit(1);
		}

		return result;
	}

	private Method getMarkMethod(PrimitiveStatement statement) {
		Class<?> clazz = statement.getReturnValue().getVariableClass();
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
			return org.objectweb.asm.commons.Method.getMethod("boolean mark(boolean)");
		else if (clazz.equals(Character.class) || clazz.equals(char.class))
			return org.objectweb.asm.commons.Method.getMethod("char mark(char)");
		else if (clazz.equals(Integer.class) || clazz.equals(int.class))
			return org.objectweb.asm.commons.Method.getMethod("int mark(int)");
		else if (clazz.equals(Short.class) || clazz.equals(short.class))
			return org.objectweb.asm.commons.Method.getMethod("short mark(short)");
		else if (clazz.equals(Long.class) || clazz.equals(long.class))
			return org.objectweb.asm.commons.Method.getMethod("long mark(long)");
		else if (clazz.equals(Float.class) || clazz.equals(float.class))
			return org.objectweb.asm.commons.Method.getMethod("float mark(float)");
		else if (clazz.equals(Double.class) || clazz.equals(double.class))
			return org.objectweb.asm.commons.Method.getMethod("double mark(double)");
		else if (clazz.equals(Byte.class) || clazz.equals(byte.class))
			return org.objectweb.asm.commons.Method.getMethod("byte mark(byte)");
		else if (clazz.equals(String.class))
			// FIXME: Probably not for Strings?
			return org.objectweb.asm.commons.Method.getMethod("String mark(String)");
		else {
			logger.fatal("Found primitive of unknown type: " + clazz.getName());
			return null; // FIXME
		}
	}

	private void getPrimitiveValue(GeneratorAdapter mg, Map<Integer, Integer> locals,
	        PrimitiveStatement statement) {
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

	private byte[] getBytecode(List<PrimitiveStatement> target, TestCase test) {
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
		ExecutionResult result = runTest(test);

		// main method
		m = Method.getMethod("void main (String[])");
		mg = new GeneratorAdapter(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, m, null, null,
		        cw);
		Map<Integer, Integer> locals = new HashMap<Integer, Integer>();
		for (StatementInterface statement : test) {
			logger.debug("Current statement: " + statement.getCode());
			if (target.contains(statement)) {
				PrimitiveStatement<?> p = (PrimitiveStatement<?>) statement;
				getPrimitiveValue(mg, locals, p); // TODO: Possibly cast?
				mg.invokeStatic(Type.getType("Ljpf/mytest/primitive/ConcolicMarker;"),
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

	public void writeTestCase(List<PrimitiveStatement> statements, TestCase test) {
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

	public boolean mutate(TestCase test) {
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
		if (p.isEmpty())
			return false;

		return mutate(p, test);
	}

	public boolean mutate(PrimitiveStatement<?> statement, TestCase test) {
		List<PrimitiveStatement> statements = new ArrayList<PrimitiveStatement>();
		statements.add(statement);
		return mutate(statements, test);
	}

	// TODO: Add jpf-classes and jpf-annotation
	public boolean mutate(List<PrimitiveStatement> statements, TestCase test) {
		logger.info("Generating test for class " + className + " with classPath "
		        + classPath);
		logger.info("Generating new values for " + statements.size()
		        + " primitive statements"); // for statement " + statement.getCode());
		logger.info(test.toCode());
		writeTestCase(statements, test);
		jpf.mytest.generator.execution.TestCase jpfTest = new jpf.mytest.generator.execution.TestCase(
		        new IntegerNextChoiceProvider(), new IntegerNextChoiceProvider(),
		        className, classPath, null);
		PathConstraintGathererParent pcg = jpfTest.getPCG();
		for (gov.nasa.jpf.Error error : jpfTest.getErrors()) {
			logger.info("Found error: " + error.getDescription());
			logger.info(error.getDetails());
		}
		int num_constraints = pcg.getNumberOfPathConstraints();
		if (num_constraints <= 0) {
			logger.info("Empty constraint set");
			return false;
		}

		Random random = new Random();
		int c = random.nextInt(num_constraints);
		if (!pcg.isNegatable(c)) {
			logger.info("Is not negatable");
			return false;
		}

		// pcg.getCondition(...).getName()

		Map<String, Object> values = jpfTest.getPCG().getAlternativePath(c);
		logger.info("Concolic execution done.");
		if (values != null && !values.isEmpty()) {
			int num = 0;

			for (Object val : values.values()) {
				//			Object val = values.values().toArray()[0];
				if (val != null) {
					if (val instanceof Long) {
						Long value = (Long) val;
						logger.info("New value is " + value);
						statements.get(num).setValue(value.intValue());
					} else {
						logger.info("New value is not long " + val);
					}
				} else {
					logger.info("New value is null");

				}
				num++;
			}
			return true;
			//logger.info("Created value: " + values.values().toArray()[0]);
		} else {
			if (values == null) {
				logger.info("Return value is null");
				/*
				values = jpfTest.getPCG().getAlternativePath();
				logger.info("Second concolic execution done.");
				if (values != null && !values.isEmpty()) {
					int num = 0;

					for (Object val : values.values()) {

						//					Object val = values.values().toArray()[0];
						if (val != null) {
							if (val instanceof Long) {
								Long value = (Long) val;
								logger.info("New value is " + value);
								statements.get(num).setValue(value.intValue());
							} else {
								logger.info("New value is not long " + val);
							}
						} else {
							logger.info("New value is null");

						}
						num++;
					}
					return true;
				} else {
					logger.info("Second constraint system failed");
					return false;
				}
				*/
			} else {
				logger.info("Return value is empty");
				return false;
			}
		}
		return false;
	}
}
