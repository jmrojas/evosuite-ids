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
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.evosuite.TestGenerationContext;
import org.evosuite.classpath.ClassPathHandler;
import org.evosuite.classpath.ResourceList;
import org.evosuite.coverage.FitnessFunctions;
import org.evosuite.coverage.TestFitnessFactory;
import org.evosuite.coverage.mutation.Mutation;
import org.evosuite.coverage.mutation.MutationObserver;
import org.evosuite.coverage.mutation.MutationPool;
import org.evosuite.rmi.ClientServices;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.TestCluster;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.strategy.TestGenerationStrategy;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.execution.ExecutionTrace;
import org.evosuite.utils.ArrayUtil;
import org.evosuite.utils.ExternalProcessUtilities;
import org.evosuite.utils.LoggingUtils;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * CoverageAnalysis class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class CoverageAnalysis {

	private final ExternalProcessUtilities util = new ExternalProcessUtilities();

	private final static Logger logger = LoggerFactory.getLogger(CoverageAnalysis.class);

	private static int totalGoals = 0;
	private static int totalCoveredGoals = 0;

	/**
	 * Identify all JUnit tests starting with the given name prefix, instrument
	 * and run tests
	 */
	public static void analyzeCoverage() {
		Sandbox.goingToExecuteSUTCode();
        TestGenerationContext.getInstance().goingToExecuteSUTCode();
		Sandbox.goingToExecuteUnsafeCodeOnSameThread();
		try {
			String cp = ClassPathHandler.getInstance().getTargetProjectClasspath();
			DependencyAnalysis.analyze(Properties.TARGET_CLASS,
			                           Arrays.asList(cp.split(File.pathSeparator)));
			LoggingUtils.getEvoLogger().info("* Finished analyzing classpath");
		} catch (Throwable e) {
			LoggingUtils.getEvoLogger().error("* Error while initializing target class: "
			                                          + (e.getMessage() != null ? e.getMessage()
			                                                  : e.toString()));
			logger.error("Problem for " + Properties.TARGET_CLASS + ". Full stack:", e);
			return;
		} finally {
			Sandbox.doneWithExecutingUnsafeCodeOnSameThread();
			Sandbox.doneWithExecutingSUTCode();
            TestGenerationContext.getInstance().doneWithExecuteingSUTCode();
		}
		// TestCluster.getInstance();

		List<Class<?>> junitTests = getClasses();
		LoggingUtils.getEvoLogger().info("* Found " + junitTests.size() + " unit test class(es)");
		if (junitTests.isEmpty())
			return;

		/*
         * sort them in a deterministic way, in case there are 
         * static state dependencies
         */
		sortTestClasses(junitTests);

		Class<?>[] classes =junitTests.toArray(new Class<?>[junitTests.size()]);
		LoggingUtils.getEvoLogger().info("* Executing test(s)");

		try {
			EvoRunner.useAgent = false; //avoid double instrumentation 

			if (ArrayUtil.contains(Properties.CRITERION, Criterion.MUTATION)
					|| ArrayUtil.contains(Properties.CRITERION, Criterion.STRONGMUTATION)) {
				junitMutationAnalysis(classes);
			} else {
				long startTime = System.currentTimeMillis();
				List<JUnitResult> results = executeTests(classes);
				printReport(results, junitTests, startTime);
			}
		} finally {
			EvoRunner.useAgent = true;
		}

	}

	public static void junitMutationAnalysis(Class<?>[] classes) {
		long startTime = System.currentTimeMillis();
		Set<Mutation> killed = executeTestsForMutationAnalysis(classes);
		List<Mutation> mutants = MutationPool.getMutants();
		if(Properties.NEW_STATISTICS) {
			ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Goals, mutants.size());
			ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Covered_Goals, killed.size());
			if(mutants.isEmpty()) {
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.BranchCoverage, 0.0); // TODO
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Coverage, 1.0);
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.MutationScore, 1.0);
			}
			else {
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.BranchCoverage, 0.0); // TODO
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Coverage, (double)killed.size() / (double)mutants.size());
				ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.MutationScore, (double)killed.size() / (double)mutants.size());
			}
			ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Time, System.currentTimeMillis() - startTime);

			// FIXXME: Need to give some time for transmission before client is killed
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Return the coverage
	 * 
	 * @return
	 */
	public static double getCoverage() {

		TestCluster.getInstance();

		List<Class<?>> junitTests = getClasses();
		LoggingUtils.getEvoLogger().info("* Found " + junitTests.size()
		                                         + " unit test class(es)");
		if (junitTests.isEmpty())
			return 0.0;

		Class<?>[] classes = new Class<?>[junitTests.size()];
		junitTests.toArray(classes);

		LoggingUtils.getEvoLogger().info("* Executing test(s)");
		List<JUnitResult> result = executeTests(classes);

		return getCoverage(result);
	}

	private static double getCoverage(List<JUnitResult> results) {

        LoggingUtils.getEvoLogger().info("* Executed " + results.size() + " test(s)");

        TestChromosome dummy = new TestChromosome();
        ExecutionResult executionResult = new ExecutionResult(dummy.getTestCase());
        dummy.setChanged(false);

        int covered = 0;
        List<? extends TestFitnessFunction> goals = TestGenerationStrategy.getFitnessFactories().get(0).getCoverageGoals(); // FIXME: can we assume that CoverageAnalysis class is only called with one fitness function?

        for (JUnitResult testResult : results) {
            executionResult.setTrace(testResult.getExecutionTrace());
            dummy.setLastExecutionResult(executionResult);

            for (TestFitnessFunction goal : goals) {
                if (goal.isCovered(dummy))
                    covered++;
            }
        }

        return (double) covered / (double) goals.size();
    }

	/**
	 * Return the number of covered goals
	 * 
	 * @param testClass
	 * @param allGoals
	 * @return
	 */
	public static List<TestFitnessFunction> getCoveredGoals(Class<?> testClass, List<TestFitnessFunction> allGoals) {

	    // A dummy Chromosome
	    TestChromosome dummy = new TestChromosome();
        dummy.setChanged(false);

        // Execution result of a dummy Test Case
        ExecutionResult executionResult = new ExecutionResult(dummy.getTestCase());

		List<TestFitnessFunction> coveredGoals = new ArrayList<TestFitnessFunction>();

		List<JUnitResult> results = executeTests(testClass);
		for (JUnitResult testResult : results) {
		    executionResult.setTrace(testResult.getExecutionTrace());
            dummy.setLastExecutionResult(executionResult);

            for(TestFitnessFunction goal : allGoals) {
                if (goal.isCovered(dummy))
                    coveredGoals.add(goal);
            }
		}

		return coveredGoals;
	}

	private static List<Class<?>> getClassesFromClasspath() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for(String prefix : Properties.JUNIT_PREFIX.split(":")) {
			
			Set<String> suts = ResourceList.getAllClasses(
					ClassPathHandler.getInstance().getTargetProjectClasspath(), prefix, false);
			
			LoggingUtils.getEvoLogger().info("* Found " + suts.size() + " classes with prefix " + prefix);
			if (!suts.isEmpty()) {
				for (String sut : suts) {
					try {
						Class<?> clazz = Class.forName(
								sut,true,TestGenerationContext.getInstance().getClassLoaderForSUT());
						
						if (isTest(clazz)) {
							classes.add(clazz);
						}
					} catch (ClassNotFoundException e2) {
						logger.info("Could not find class "+sut);
					} catch(Throwable t) {
						logger.info("Error while initialising class "+sut);
					}
				}

			}
		}
		return classes;
	}

	private static List<Class<?>> getClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		logger.debug("JUNIT_PREFIX: "+Properties.JUNIT_PREFIX);
		
		for(String prefix : Properties.JUNIT_PREFIX.split(":")) {
			
			LoggingUtils.getEvoLogger().info("* Analyzing entry: "+prefix);
			
			// If the target name is a path analyze it
			File path = new File(prefix);
			if (path.exists()) {
				if (Properties.JUNIT_PREFIX.endsWith(".jar"))
					classes.addAll(getClassesJar(path));
				else
					classes.addAll(getClasses(path));
			} else {

				try {
					Class<?> junitClass = Class.forName(prefix,
							true,
							TestGenerationContext.getInstance().getClassLoaderForSUT());
					classes.add(junitClass);
				} catch (ClassNotFoundException e) {
					// Second, try if the target name is a package name
					classes.addAll(getClassesFromClasspath());
				}
			}
		}
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
				                               TestGenerationContext.getInstance().getClassLoaderForSUT());
				LoggingUtils.restorePreviousOutAndErrStream();

				//clazz = Class.forName(clazz.getName());
				if (isTest(clazz))
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
			/*if (fileName.contains("$"))
                continue;*/

			PrintStream old_out = System.out;
			PrintStream old_err = System.err;
			//System.setOut(outStream);
			//System.setErr(outStream);

			try {
				Class<?> clazz = Class.forName(fileName.replace(".class", "").replace("/",
				                                                                      "."),
				                               true,
				                               TestGenerationContext.getInstance().getClassLoaderForSUT());

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
		return classes;

	}

	private static void analyzeCoverageCriterion(List<JUnitResult> results, Properties.Criterion criterion) {

		logger.info("analysing coverage of " + criterion);

		// Factory
		TestFitnessFactory<? extends TestFitnessFunction> factory = FitnessFunctions.getFitnessFactory(criterion);

		// Goals
		List<? extends TestFitnessFunction> goals = factory.getCoverageGoals();
		totalGoals += goals.size();

		// A dummy Chromosome
        TestChromosome dummy = new TestChromosome();
        dummy.setChanged(false);

        // Execution result of a dummy Test Case
        ExecutionResult executionResult = new ExecutionResult(dummy.getTestCase());

        // coverage matrix (each row represents the coverage of each test case
        // and each column represents the coverage of each component (e.g., line)
        // this coverage matrix is useful for Rho fitness
    	boolean[][] coverage_matrix = new boolean[results.size()][goals.size() + 1]; // +1 because we also want to include the test result
        Set<Integer> covered = new LinkedHashSet<Integer>();

        for (int index_test = 0; index_test < results.size(); index_test++) {
        	JUnitResult tR = results.get(index_test);

            executionResult.setTrace(tR.getExecutionTrace());
            dummy.setLastExecutionResult(executionResult);

            for (int index_component = 0; index_component < goals.size(); index_component++) {
            	TestFitnessFunction goal = goals.get(index_component);

                if (goal.isCovered(dummy)) {
                	covered.add(index_component);
                	coverage_matrix[index_test][index_component] = true;
                }
                else {
                	coverage_matrix[index_test][index_component] = false;
                }
            }

            coverage_matrix[index_test][goals.size()] = tR.wasSuccessful();
        }
        totalCoveredGoals += covered.size();

        if (Properties.COVERAGE_MATRIX) {
		    CoverageReportGenerator.writeCoverage(coverage_matrix);
        }

        if (goals.isEmpty()) {
			LoggingUtils.getEvoLogger().info("* Coverage of criterion " + criterion + ": 100% (no goals)");
			ClientServices.getInstance().getClientNode().trackOutputVariable(org.evosuite.coverage.CoverageAnalysis.getCoverageVariable(criterion), 1.0);
		} 
        else {
        	double coverage = ((double) covered.size()) / ((double) goals.size());
        	LoggingUtils.getEvoLogger().info("* Coverage of criterion " + criterion + ": " + NumberFormat.getPercentInstance().format(coverage));
			LoggingUtils.getEvoLogger().info("* Number of covered goals: " + covered.size() + " / " + goals.size());

			ClientServices.getInstance().getClientNode().trackOutputVariable(org.evosuite.coverage.CoverageAnalysis.getCoverageVariable(criterion), coverage);
        }
	}

	private static void printReport(List<JUnitResult> results, List<Class<?>> classes, long startTime) {

		LoggingUtils.getEvoLogger().info("* Executed " + results.size() + " test(s)");
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Tests_Executed, results.size());

		// restart variables
		totalGoals = 0;
		totalCoveredGoals = 0;

		for (Properties.Criterion criterion : Properties.CRITERION) {
			analyzeCoverageCriterion(results, criterion);
		}

		LoggingUtils.getEvoLogger().info("* Total number of covered goals: " + totalCoveredGoals + " / " + totalGoals);
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Goals, totalGoals);
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Covered_Goals, totalCoveredGoals);

		double coverage = totalGoals == 0 ? 1.0 : ((double) totalCoveredGoals) / ((double) totalGoals);
		LoggingUtils.getEvoLogger().info("* Total coverage: " + NumberFormat.getPercentInstance().format(coverage));
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Coverage, coverage);

		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Time, System.currentTimeMillis() - startTime);

		// Need to give some time for transmission before client is killed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static List<JUnitResult> executeTests(Class<?>... junitClasses) {

		List<JUnitResult> results = new ArrayList<JUnitResult>();
        for (Class<?> junitClass : junitClasses) {
            logger.info("Running test "+junitClass.getSimpleName());
            JUnitRunner jR = new JUnitRunner();
            jR.run(junitClass);
            results.addAll(jR.getTestResults());
        }

        return results;
	}
	
	private static Set<Mutation> executeTestsForMutationAnalysis(Class<?>... junitClasses) {

        //List<Class<?>> passingClasses = new ArrayList<Class<?>>();
	    //RunNotifier notifier = new RunNotifier();
	    Set<Mutation> allMutants = new LinkedHashSet<Mutation>(MutationPool.getMutants());
	    Set<Mutation> killed = new HashSet<Mutation>();

	    for(Class<?> clazz : junitClasses) {
	        try {
	            logger.info("Remaining mutants: "+allMutants.size());
	            logger.info("Running test class: "+clazz.getSimpleName());
	            //if(junit.framework.TestCase.class.isAssignableFrom(clazz)) {
	            //	logger.info("Found JUnit 3.8 test");
	                List<JUnitResult> results = executeTests(clazz);
                    for (JUnitResult tR : results) {
                        ExecutionTrace trace = tR.getExecutionTrace();

                        for (Integer mutationID : trace.getTouchedMutants()) {
                            Mutation m = MutationPool.getMutant(mutationID);
                            if (allMutants.contains(m))
                            {
                                MutationObserver.activateMutation(mutationID);
                                List<JUnitResult> mutationResults = executeTests(clazz);
                                MutationObserver.deactivateMutation();

                                for (JUnitResult mR : mutationResults) {
                                    if (mR.getFailureCount() != tR.getFailureCount()) {
                                        logger.info("Mutation killed: "+mutationID);
                                        allMutants.remove(m);
                                        killed.add(m);
                                    }
                                }
                            }
                        }
                    }
//			} else {
//				MutationAnalysisRunner runner = new MutationAnalysisRunner(clazz, allMutants);
//				//Result result = JUnitCore.runClasses(clazz);
//				runner.run(notifier);
//				allMutants.removeAll(runner.getKilledMutants());
//			}
                //if(result.wasSuccessful())
                //	passingClasses.add(clazz);
	        } catch(Throwable t) {
	            logger.warn("Error during test execution: "+t+", "+t.getMessage());
	            for(StackTraceElement elem : t.getStackTrace()) {
	                logger.warn(elem.toString());
	            }
	        }
	    }
// TODO: The problem is that the runner only works for JUnit 4 tests
//		List<Mutation> mutants = MutationPool.getMutants();
//		for(Mutation mutation : mutants) {
//			logger.info("Current mutant: "+mutation.getId());
//
//			MutationObserver.activateMutation(mutation.getId());
//
//			for(Class<?> clazz : passingClasses) {
//				try {
//					logger.info("Running test "+clazz.getSimpleName());
//					Result result = runner.run(clazz);
//					if(!result.wasSuccessful()) {
//						// killed!
//						killed.add(mutation);
//						break;
//					}
//				} catch(Throwable t) {
//					logger.warn("Error during test execution: "+t);
//				}
//			}
//		}

	    return killed;
	}

	/**
	 * Determine if a class contains JUnit tests
	 * 
	 * @param class
	 * @return
	 */
	public static boolean isTest(Class<?> cls) {
		if (Modifier.isAbstract(cls.getModifiers())) {
			return false;
		}

		// JUnit 4
		try {
			List<FrameworkMethod> methods = new TestClass(cls).getAnnotatedMethods(Test.class);
			for (FrameworkMethod method : methods) {
		        List<Throwable> errors = new ArrayList<Throwable>();
		        method.validatePublicVoidNoArg(false, errors);
		        if (errors.isEmpty()) {
		        	return true;
		        }
			}
		} catch (IllegalArgumentException e) {
			return false;
		}

		// JUnit 3
		Class<?> superClass = cls; 
		while ((superClass = superClass.getSuperclass()) != null) {
			if (superClass.getCanonicalName().equals(Object.class.getCanonicalName())) {
				break ;
			}
			else if (superClass.getCanonicalName().equals(TestCase.class.getCanonicalName())) {
				return true;
			}
		}

		// FIXME add support for other frameworks, e.g., TestNG ?

		return false;
	}

	/**
     * re-order test classes
     * 
     * @param tests
     */
    private static void sortTestClasses(List<Class<?>> tests) {
        Collections.sort(tests, new Comparator<Class<?>>() {
            @Override
            public int compare(Class<?> t0, Class<?> t1) {
                return Integer.compare(t1.getName().length(), t0.getName().length());
            }
        });
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

	/**
	 * <p>
	 * main
	 * </p>
	 * 
	 * @param args
	 *            an array of {@link java.lang.String} objects.
	 */
	@Deprecated
	public static void main(String[] args) {
		LoggingUtils.getEvoLogger().error("Cannot start CoverageAnalysis directly");
		return;
		/*
		try {
			LoggingUtils.getEvoLogger().info("* Starting client");
			CoverageAnalysis process = new CoverageAnalysis();
			process.run();
			if (!Properties.CLIENT_ON_THREAD) {
				System.exit(0);
			}
		} catch (Throwable t) {
			LoggingUtils.getEvoLogger().error("Error when analyzing coveragetests for: "
			                                          + Properties.TARGET_CLASS
			                                          + " with seed "
			                                          + Randomness.getSeed(), t);

			//sleep 1 sec to be more sure that the above log is recorded
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}

			if (!Properties.CLIENT_ON_THREAD) {
				System.exit(1);
			}
		}
		*/
	}

}
