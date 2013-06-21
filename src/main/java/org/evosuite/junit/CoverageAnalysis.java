/**
 * 
 */
package org.evosuite.junit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.evosuite.TestGenerationContext;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.utils.ExternalProcessUtilities;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.ResourceList;
import org.junit.Test;
import org.junit.runners.Suite;
import org.objectweb.asm.ClassReader;

/**
 * <p>
 * CoverageAnalysis class.
 * </p>
 * 
 * @author Gordon Fraser
 * @author José Campos
 */
public class CoverageAnalysis {

	private final ExternalProcessUtilities util = new ExternalProcessUtilities();

	/**
	 * Identify all JUnit tests starting with the given name prefix, instrument
	 * and run tests
	 */
	public static void analyzeCoverage() {
		TestCluster.getInstance();

		List<Class<?>> junitTests = getTestClasses();
		LoggingUtils.getEvoLogger().info("* Found " + junitTests.size()
		                                         + " test classes");
		if (junitTests.isEmpty())
			return ;

		Class<?>[] classes = new Class<?>[junitTests.size()];
		junitTests.toArray(classes);
		LoggingUtils.getEvoLogger().info("* Executing tests");
		long startTime = System.currentTimeMillis();
		List<TestResult> testResults = executeTests(classes);
		printReport(testResults, junitTests, startTime);
	}

	private static List<Class<?>> getClassesFromClasspath() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		Pattern pattern = Pattern.compile(Properties.JUNIT_PREFIX + ".*.class");
		Collection<String> resources = ResourceList.getResources(pattern);
		LoggingUtils.getEvoLogger().info("* Found " + resources.size()
		                                         + " classes with prefix "
		                                         + Properties.JUNIT_PREFIX);
		if (!resources.isEmpty()) {
			for (String resource : resources) {
				try {
					Class<?> clazz = Class.forName(resource.replaceAll(".class", "").replaceAll("/",
					                                                                            "."),
					                               true,
					                               TestGenerationContext.getClassLoader());
					if (isTest(clazz)) {
						classes.add(clazz);
					}
				} catch (ClassNotFoundException e2) {
					// Ignore?
				}
			}
		}
		return classes;
	}

	private static List<Class<?>> getTestClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		// If the target name is a path analyze it
		File path = new File(Properties.JUNIT_PREFIX);
		if (path.exists()) {
			if (Properties.JUNIT_PREFIX.endsWith(".jar"))
				classes.addAll(getClassesJar(path));
			else
				classes.addAll(getClasses(path));
		} else {

			try {
				Class<?> junitClass = Class.forName(Properties.JUNIT_PREFIX,
				                                    true,
				                                    TestGenerationContext.getClassLoader());
				classes.add(junitClass);
			} catch (ClassNotFoundException e) {
				System.out.println("NOT FOUND: " + e + " with " + Properties.JUNIT_PREFIX);
				// Second, try if the target name is a package name
				classes.addAll(getClassesFromClasspath());
			}
		}

		// re-order classes
		Collections.sort(classes, new Comparator<Class<?>>() {
			@Override
			public int compare(Class<?> arg0, Class<?> arg1) {
				return Integer.valueOf(arg1.getName().length()).compareTo(arg0.getName().length());
			}
		});

		return classes;
	}

	/**
	 * Analyze all classes that can be found in a given directory
	 * 
	 * @param directory
	 *            a {@link java.io.File} object.
	 * @throws ClassNotFoundException
	 *             if any.
	 * @return a {@link java.util.List} object.
	 */
	public static List<Class<?>> getClasses(File directory) {
		if (directory.getName().endsWith(".class")) {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			LoggingUtils.muteCurrentOutAndErrStream();

			try {
				File file = new File(directory.getPath());
				byte[] array = new byte[(int) file.length()];
				ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
				InputStream in = new FileInputStream(file);
				try {
					int length = in.read(array);
					while (length > 0) {
						out.write(array, 0, length);
						length = in.read(array);
					}
				} finally {
					in.close();
				}
				ClassReader reader = new ClassReader(array);
				String className = reader.getClassName();

				// Use default classLoader
				Class<?> clazz = Class.forName(className.replace("/", "."), true,
				                               TestGenerationContext.getClassLoader());
				LoggingUtils.restorePreviousOutAndErrStream();

				//clazz = Class.forName(clazz.getName());
				classes.add(clazz);

			} catch (IllegalAccessError e) {
				LoggingUtils.restorePreviousOutAndErrStream();

				System.out.println("  Cannot access class "
				        + directory.getName().substring(0,
				                                        directory.getName().length() - 6)
				        + ": " + e);
			} catch (NoClassDefFoundError e) {
				LoggingUtils.restorePreviousOutAndErrStream();

				System.out.println("  Error while loading "
				        + directory.getName().substring(0,
				                                        directory.getName().length() - 6)
				        + ": Cannot find " + e.getMessage());
				//e.printStackTrace();
			} catch (ExceptionInInitializerError e) {
				LoggingUtils.restorePreviousOutAndErrStream();

				System.out.println("  Exception in initializer of "
				        + directory.getName().substring(0,
				                                        directory.getName().length() - 6));
			} catch (ClassNotFoundException e) {
				LoggingUtils.restorePreviousOutAndErrStream();

				System.out.println("  Class not found in classpath: "
				        + directory.getName().substring(0,
				                                        directory.getName().length() - 6)
				        + ": " + e);
			} catch (Throwable e) {
				LoggingUtils.restorePreviousOutAndErrStream();

				System.out.println("  Unexpected error: "
				        + directory.getName().substring(0,
				                                        directory.getName().length() - 6)
				        + ": " + e);
			}
			return classes;
		} else if (directory.isDirectory()) {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for (File file : directory.listFiles()) {
				classes.addAll(getClasses(file));
			}
			return classes;
		} else {
			return new ArrayList<Class<?>>();
		}
	}

	/**
	 * <p>
	 * getClassesJar
	 * </p>
	 * 
	 * @param file
	 *            a {@link java.io.File} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<Class<?>> getClassesJar(File file) {

		List<Class<?>> classes = new ArrayList<Class<?>>();

		ZipFile zf;
		try {
			zf = new ZipFile(file);
		} catch (final ZipException e) {
			throw new Error(e);
		} catch (final IOException e) {
			throw new Error(e);
		}

		final Enumeration<?> e = zf.entries();
		while (e.hasMoreElements()) {
			final ZipEntry ze = (ZipEntry) e.nextElement();
			final String fileName = ze.getName();
			if (!fileName.endsWith(".class"))
				continue;
			if (fileName.contains("$")) // FIXME we want to run single inner classes?!
				continue;

			PrintStream old_out = System.out;
			PrintStream old_err = System.err;
			//System.setOut(outStream);
			//System.setErr(outStream);

			try {
				Class<?> clazz = Class.forName(fileName.replace(".class", "").replace("/",
				                                                                      "."),
				                               true,
				                               TestGenerationContext.getClassLoader());

				if (isTest(clazz))
					classes.add(clazz);				
			} catch (IllegalAccessError ex) {
				System.setOut(old_out);
				System.setErr(old_err);
				System.out.println("Cannot access class "
				        + file.getName().substring(0, file.getName().length() - 6));
			} catch (NoClassDefFoundError ex) {
				System.setOut(old_out);
				System.setErr(old_err);
				System.out.println("Cannot find dependent class " + ex);
			} catch (ExceptionInInitializerError ex) {
				System.setOut(old_out);
				System.setErr(old_err);
				System.out.println("Exception in initializer of "
				        + file.getName().substring(0, file.getName().length() - 6));
			} catch (ClassNotFoundException ex) {
				System.setOut(old_out);
				System.setErr(old_err);
				System.out.println("Cannot find class "
				        + file.getName().substring(0, file.getName().length() - 6) + ": "
				        + ex);
			} catch (Throwable t) {
				System.setOut(old_out);
				System.setErr(old_err);

				System.out.println("  Unexpected error: "
				        + file.getName().substring(0, file.getName().length() - 6) + ": "
				        + t);
			} finally {
				System.setOut(old_out);
				System.setErr(old_err);
			}
		}
		try {
			zf.close();
		} catch (final IOException e1) {
			throw new Error(e1);
		}

		// re-order classes
		Collections.sort(classes, new Comparator<Class<?>>() {
			@Override
			public int compare(Class<?> arg0, Class<?> arg1) {
				return Integer.valueOf(arg1.getName().length()).compareTo(arg0.getName().length());
			}
		});

		return classes;
	}

	private static void printReport(List<TestResult> testResults, List<Class<?>> classes, long startTime)
	{
		LoggingUtils.getEvoLogger().info("* Executed " + testResults.size() + " unit tests");

		List<? extends TestFitnessFunction> goals = TestSuiteGenerator.getFitnessFactory().getCoverageGoals();
		LinkedHashMap<String, List<TestFitnessFunction>> goalsMap = new LinkedHashMap<String, List<TestFitnessFunction>>();
		for (TestFitnessFunction goal : goals)
		{
			//LoggingUtils.getEvoLogger().info("\t" + goal);

			String[] split = goal.toString().split(" ");
			if (goalsMap.containsKey(split[split.length - 1])) {
				goalsMap.get(split[split.length - 1]).add(goal);
			}
			else {
				List<TestFitnessFunction> _new = new ArrayList<TestFitnessFunction>();
				_new.add(goal);
				goalsMap.put(split[split.length - 1], _new);
			}
		}
		/*LoggingUtils.getEvoLogger().info("-------------------------------------------------");
		for (String key : goalsMap.keySet()) {
			LoggingUtils.getEvoLogger().info("\t" + key);
			for (TestFitnessFunction goal : goalsMap.get(key)) {
				LoggingUtils.getEvoLogger().info("\t\t" + goal);
			}
		}*/

		TestChromosome dummy = new TestChromosome();
		ExecutionResult executionResult = new ExecutionResult(dummy.getTestCase());

		//boolean[][] coverage = new boolean[testResults.size()][goals.size() + 1];
		boolean[][] coverage = new boolean[testResults.size()][goalsMap.size() + 1];
		int index_test = 0;
		BitSet total_covered = new BitSet();

		/*for (TestResult tR : testResults)
		{
			executionResult.setTrace(tR.getExecutionTrace());
			dummy.setLastExecutionResult(executionResult);
			dummy.setChanged(false);

			int index_component = 0;
			boolean isCovered = false;

			for (TestFitnessFunction goal : goals)
			{
				//
				 / FIXME When we have ENTROPY criterion, why we need to negate the returned variable of isCovered function ?! 
				 //
				if (Properties.CRITERION == Criterion.ENTROPY) {
					isCovered = !goal.isCovered(dummy);
				}
				else
					isCovered = goal.isCovered(dummy);

				if (isCovered == true) {
					total_covered.set(index_component);
					coverage[index_test][index_component] = isCovered;
				}

				index_component++;
			}

			coverage[index_test++][goals.size()] = tR.wasSuccessful();
		}*/
		for (TestResult tR : testResults)
		{
			executionResult.setTrace(tR.getExecutionTrace());
			dummy.setLastExecutionResult(executionResult);
			dummy.setChanged(false);

			int index_component = 0;

			for (String key : goalsMap.keySet())
			{
				boolean isCovered = false;

				for (TestFitnessFunction goal : goalsMap.get(key))
				{
					/*
					 * FIXME When we have ENTROPY criterion, why we need to negate the returned variable of isCovered function ?! 
					 */
					if ((Properties.CRITERION == Criterion.ENTROPY) ||
							(Properties.CRITERION == Criterion.AMBIGUITY)) {
						isCovered = !goal.isCovered(dummy);
					}
					else
						isCovered = goal.isCovered(dummy);

					if (isCovered == true)
						break ;
				}

				if (isCovered == true) {
					total_covered.set(index_component);
					coverage[index_test][index_component] = isCovered;
				}

				index_component++;
			}

			coverage[index_test++][goalsMap.size()] = tR.wasSuccessful();
		}

		/*StringBuilder str = new StringBuilder();
		for (int i = 0; i < coverage.length; i++)
		{
			int j;
			for (j = 0; j < coverage[i].length - 1; j++)
			{
				if (coverage[i][j] == true)
					str.append("1 ");
				else
					str.append("0 ");
			}

			if (coverage[i][j] == true)
				str.append("+\n");
			else
				str.append("-\n");
		}
		LoggingUtils.getEvoLogger().info(str.toString());*/

		LoggingUtils.getEvoLogger().info("* Covered "
		                                         + total_covered.cardinality()
		                                         + "/"
		                                         + goalsMap.size()
		                                         + " coverage goals: "
		                                         + NumberFormat.getPercentInstance().format((double) total_covered.cardinality()
		                                                                                            / (double) goalsMap.size()));

		JUnitReportGenerator reportGenerator = new JUnitReportGenerator(total_covered.cardinality(),
		        goals.size(),
		        executionResult.getTrace().getCoveredLines(Properties.TARGET_CLASS),
		        classes, startTime);
		reportGenerator.writeReport();

		CoverageReportGenerator.writeCoverage(coverage);
	}

	private static List<TestResult> executeTests(Class<?>... junitClasses)
	{
		List<TestResult> testResults = new ArrayList<TestResult>();

		for (Class<?> junitClass : junitClasses) {
			JUnitRunner junitRunner = new JUnitRunner();
			junitRunner.run(junitClass);
			testResults.addAll(junitRunner.getTestResults());
		}

		return testResults;
	}

	/**
	 * Determine if this class contains JUnit tests
	 * 
	 * @param className
	 * @return
	 */
	private static boolean isTest(Class<?> clazz) {

		if (Modifier.isAbstract(clazz.getModifiers()))
			return false;

		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != null && !superClazz.equals(Object.class)
		        && !superClazz.equals(clazz)) {
			if (superClazz.equals(Suite.class))
				return true;
			if (superClazz.equals(TestSuite.class))
				return true;
			if (superClazz.equals(Test.class))
				return true;
			if (superClazz.equals(TestCase.class))
				return true;

			if (superClazz.equals(clazz.getSuperclass()))
				break;

			superClazz = clazz.getSuperclass();
		}
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(Test.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * run
	 * </p>
	 */
	public void run() {

		LoggingUtils.getEvoLogger().info("* Connecting to master process on port "
		                                         + Properties.PROCESS_COMMUNICATION_PORT);
		if (!util.connectToMainProcess()) {
			throw new RuntimeException("Could not connect to master process on port "
			        + Properties.PROCESS_COMMUNICATION_PORT);
		}

		analyzeCoverage();
		/*
		 * for now, we ignore the instruction (originally was meant to support several client in parallel and
		 * restarts, but that will be done in RMI)
		 */

		util.informSearchIsFinished(null);
	}
}
