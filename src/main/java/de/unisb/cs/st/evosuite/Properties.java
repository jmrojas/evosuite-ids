/*
 * Copyright (C) 2010 Saarland University
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

package de.unisb.cs.st.evosuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Central property repository. All global parameters of EvoSuite should be
 * declared as fields here, using the appropriate annotation. Access is possible
 * directly via the fields, or with getter/setter methods.
 * 
 * @author Gordon Fraser
 * 
 */
public class Properties {

	/**
	 * Parameters are fields of the Properties class, annotated with this
	 * annotation. The key parameter is used to identify values in property
	 * files or on the command line, the group is used in the config file or
	 * input plugins to organize parameters, and the description is also
	 * displayed there.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface Parameter {
		String key();

		String group() default "Experimental";

		String description();
	}

	@interface IntValue {
		int min() default Integer.MIN_VALUE;

		int max() default Integer.MAX_VALUE;
	}

	@interface DoubleValue {
		double min() default -(Double.MAX_VALUE - 1); // FIXXME: Check

		double max() default Double.MAX_VALUE;
	}

	//---------------------------------------------------------------
	// Test sequence creation
	@Parameter(key = "test_excludes", group = "Test Creation", description = "File containing methods that should not be used in testing")
	public static String TEST_EXCLUDES = "test.excludes";

	@Parameter(key = "test_includes", group = "Test Creation", description = "File containing methods that should be included in testing")
	public static String TEST_INCLUDES = "test.includes";

	@Parameter(key = "make_accessible", group = "TestCreation", description = "Change default package rights to public package rights (?)")
	public static boolean MAKE_ACCESSIBLE = false;

	@Parameter(key = "string_replacement", group = "Test Creation", description = "Replace string.equals with levenshtein distance")
	public static boolean STRING_REPLACEMENT = true;

	@Parameter(key = "static_hack", group = "Test Creation", description = "Call static constructors after each test execution")
	public static boolean STATIC_HACK = false;

	@Parameter(key = "null_probability", group = "Test Creation", description = "Probability to use null instead of constructing an object")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double NULL_PROBABILITY = 0.1;

	@Parameter(key = "object_reuse_probability", group = "Test Creation", description = "Probability to reuse an existing reference, if available")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double OBJECT_REUSE_PROBABILITY = 0.9;

	@Parameter(key = "primitive_reuse_probability", group = "Test Creation", description = "Probability to reuse an existing primitive, if available")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double PRIMITIVE_REUSE_PROBABILITY = 0.5;

	@Parameter(key = "primitive_pool", group = "Test Creation", description = "Probability to use a primitive from the pool rather than a random value")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double PRIMITIVE_POOL = 0.5;

	@Parameter(key = "object_pool", group = "Test Creation", description = "Probability to use a predefined sequence from the pool rather than a random generator")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double OBJECT_POOL = 0.0;

	@Parameter(key = "string_length", group = "Test Creation", description = "Maximum length of randomly generated strings")
	public static int STRING_LENGTH = 20;

	@Parameter(key = "epsilon", group = "Test Creation", description = "Epsilon for floats in local search")
	public static double EPSILON = 0.001;

	@Parameter(key = "max_int", group = "Test Creation", description = "Maximum size of randomly generated integers (minimum range = -1 * max)")
	public static int MAX_INT = 256;

	@Parameter(key = "max_array", group = "Test Creation", description = "Maximum length of randomly generated arrays")
	public static int MAX_ARRAY = 20;

	@Parameter(key = "max_attempts", group = "Test Creation", description = "Number of attempts when generating an object before giving up")
	public static int MAX_ATTEMPTS = 1000;

	@Parameter(key = "max_recursion", group = "Test Creation", description = "Recursion depth when trying to create objects")
	public static int MAX_RECURSION = 10;

	@Parameter(key = "max_length", group = "Test Creation", description = "Maximum length of test suites (0 = no check)")
	public static int MAX_LENGTH = 0;

	@Parameter(key = "max_size", group = "Test Creation", description = "Maximum number of test cases in a test suite")
	public static int MAX_SIZE = 50;

	@Parameter(key = "num_tests", group = "Test Creation", description = "Number of tests in initial test suites")
	public static int NUM_TESTS = 2;

	@Parameter(key = "use_deprecated", group = "Test Creation", description = "Include deprecated methods in tests")
	public static boolean USE_DEPRECATED = false;

	@Parameter(key = "generator_tournament", group = "Test Creation", description = "Size of tournament when choosing a generator")
	@Deprecated
	public static int GENERATOR_TOURNAMENT = 1;

	@Parameter(key = "generate_objects", group = "Test Creation", description = "Generate .object files that allow adapting method signatures")
	public static boolean GENERATE_OBJECTS = false;

	//---------------------------------------------------------------
	// Search algorithm
	public enum Algorithm {
		STANDARDGA, STEADYSTATEGA, ONEPLUSONEEA, MUPLUSLAMBDAGA
	}

	@Parameter(key = "algorithm", group = "Search Algorithm", description = "Search algorithm")
	public static Algorithm ALGORITHM = Algorithm.STEADYSTATEGA;

	@Parameter(key = "check_best_length", group = "Search Algorithm", description = "Check length against length of best individual")
	public static boolean CHECK_BEST_LENGTH = true;

	@Parameter(key = "check_parents_length", group = "Search Algorithm", description = "Check length against length of parents")
	public static boolean CHECK_PARENTS_LENGTH = true;

	@Parameter(key = "check_rank_length", group = "Search Algorithm", description = "Use length in rank selection")
	public static boolean CHECK_RANK_LENGTH = true;

	@Parameter(key = "parent_check", group = "Search Algorithm", description = "Check against parents in Mu+Lambda algorithm")
	public static boolean PARENT_CHECK = true;

	@Parameter(key = "check_max_length", group = "Search Algorithm", description = "Check length against fixed maximum")
	public static boolean CHECK_MAX_LENGTH = true;

	@Parameter(key = "local_search_rate", group = "Search Algorithm", description = "Apply local search at every X generation")
	public static int LOCAL_SEARCH_RATE = -1;

	@Parameter(key = "local_search_probes", group = "Search Algorithm", description = "How many mutations to apply to a string to check whether it improves coverage")
	public static int LOCAL_SEARCH_PROBES = 10;

	@Parameter(key = "crossover_rate", group = "Search Algorithm", description = "Probability of crossover")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double CROSSOVER_RATE = 0.75;

	@Parameter(key = "kincompensation", group = "Search Algorithm", description = "Penalty for duplicate individuals")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double KINCOMPENSATION = 1.0;

	@Parameter(key = "elite", group = "Search Algorithm", description = "Elite size for search algorithm")
	public static int ELITE = 1;

	@Parameter(key = "tournament_size", group = "Search Algorithm", description = "Number of individuals for tournament selection")
	public static int TOURNAMENT_SIZE = 5;

	@Parameter(key = "rank_bias", group = "Search Algorithm", description = "Bias for better individuals in rank selection")
	public static double RANK_BIAS = 1.7;

	@Parameter(key = "chromosome_length", group = "Search Algorithm", description = "Maximum length of chromosomes during search")
	@IntValue(min = 1, max = 100000)
	public static int CHROMOSOME_LENGTH = 100;

	@Parameter(key = "population", group = "Search Algorithm", description = "Population size of genetic algorithm")
	@IntValue(min = 1)
	public static int POPULATION = 100;

	@Parameter(key = "generations", group = "Search Algorithm", description = "Maximum search duration")
	@IntValue(min = 1)
	public static int GENERATIONS = 1000;

	public enum StoppingCondition {
		MAXSTATEMENTS, MAXTESTS, MAXTIME, MAXGENERATIONS, MAXFITNESSEVALUATIONS
	}

	@Parameter(key = "stopping_condition", group = "Search Algorithm", description = "What condition should be checked to end the search")
	public static StoppingCondition STOPPING_CONDITION = StoppingCondition.MAXGENERATIONS;

	public enum CrossoverFunction {
		SINGLEPOINTRELATIVE, SINGLEPOINTFIXED, SINGLEPOINT
	}

	@Parameter(key = "crossover_function", group = "Search Algorithm", description = "Crossover function during search")
	public static CrossoverFunction CROSSOVER_FUNCTION = CrossoverFunction.SINGLEPOINT;

	public enum SelectionFunction {
		RANK, ROULETTEWHEEL, TOURNAMENT
	}

	@Parameter(key = "selection_function", group = "Search Algorithm", description = "Selection function during search")
	public static SelectionFunction SELECTION_FUNCTION = SelectionFunction.RANK;

	// TODO: Fix values
	@Parameter(key = "secondary_objectives", group = "Search Algorithm", description = "Secondary objective during search")
	//	@SetValue(values = { "maxlength", "maxsize", "avglength" })
	public static String SECONDARY_OBJECTIVE = "maxlength";

	@Parameter(key = "bloat_factor", group = "Search Algorithm", description = "Maximum relative increase in length")
	public static int BLOAT_FACTOR = 2;

	@Parameter(key = "stop_zero", group = "Search Algorithm", description = "Stop optimization once goal is covered")
	public static boolean STOP_ZERO = true;

	@Parameter(key = "dynamic_limit", group = "Search Algorithm", description = "Multiply search budget by number of test goals")
	public static boolean DYNAMIC_LIMIT = false;

	@Parameter(key = "global_timeout", group = "Search Algorithm", description = "Seconds allowed for entire search")
	@IntValue(min = 0)
	public static int GLOBAL_TIMEOUT = 600;

	//---------------------------------------------------------------
	// Single branch mode
	@Parameter(key = "random_tests", group = "Single Branch Mode", description = "Number of random tests to run before test generation (Single branch mode)")
	public static int RANDOM_TESTS = 0;

	@Parameter(key = "skip_covered", group = "Single Branch Mode", description = "Skip coverage goals that have already been (coincidentally) covered")
	public static boolean SKIP_COVERED = true;

	@Parameter(key = "reuse_budget", group = "Single Branch Mode", description = "Use leftover budget on unsatisfied test goals (Single branch mode)")
	public static boolean REUSE_BUDGET = true;

	@Parameter(key = "shuffle_goals", group = "Single Branch Mode", description = "Shuffle test goals before test generation (Single branch mode)")
	public static boolean SHUFFLE_GOALS = true;

	@Parameter(key = "recycle_chromosomes", group = "Single Branch Mode", description = "Seed initial population with related individuals (Single branch mode)")
	public static boolean RECYCLE_CHROMOSOMES = true;

	//---------------------------------------------------------------
	// Output
	@Parameter(key = "print_to_system", group = "Output", description = "Allow test output on console")
	public static boolean PRINT_TO_SYSTEM = false;

	@Parameter(key = "plot", group = "Output", description = "Create plots of size and fitness")
	public static boolean PLOT = false;

	@Parameter(key = "html", group = "Output", description = "Create html reports")
	public static boolean HTML = false;

	@Parameter(key = "junit_tests", group = "Output", description = "Create JUnit test suites")
	public static boolean JUNIT_TESTS = true;

	@Parameter(key = "log_goals", group = "Output", description = "Create a CSV file for each individual evolution")
	public static boolean LOG_GOALS = false;

	@Parameter(key = "minimize", group = "Output", description = "Minimize test suite after generation")
	public static boolean MINIMIZE = true;

	@Parameter(key = "inline", group = "Output", description = "Inline all constants")
	public static boolean INLINE = false;

	@Parameter(key = "write_pool", group = "Output", description = "Keep sequences for object pool")
	public static boolean WRITE_POOL = false;

	@Parameter(key = "report_dir", group = "Output", description = "Directory in which to put HTML and CSV reports")
	public static String REPORT_DIR = "evosuite-report";

	@Parameter(key = "print_current_goals", group = "Output", description = "Print out current goal during test generation")
	public static boolean PRINT_CURRENT_GOALS = true;

	@Parameter(key = "print_covered_goals", group = "Output", description = "Print out covered goals during test generation")
	public static boolean PRINT_COVERED_GOALS = true;

	@Parameter(key = "assertions", group = "Output", description = "Create assertions")
	public static boolean ASSERTIONS = false;

	@Parameter(key = "test_dir", group = "Output", description = "Directory in which to place JUnit tests")
	public static String TEST_DIR = "evosuite-tests";

	@Parameter(key = "write_cfg", group = "Output", description = "Create CFG graphs")
	public static boolean WRITE_CFG = false;

	//---------------------------------------------------------------
	// Sandbox
	@Parameter(key = "sandbox", group = "Sandbox", description = "Execute tests in a sandbox environment")
	public static boolean SANDBOX = false;

	@Parameter(key = "mocks", group = "Sandbox", description = "Usage of the mocks for the IO, Network etc")
	public static boolean MOCKS = false;

	@Parameter(key = "sandbox_folder", group = "Sandbox", description = "Folder used for IO, when mocks are enabled")
	public static String SANDBOX_FOLDER = "evosuite-sandbox";

	@Parameter(key = "stubs", group = "Sandbox", description = "Stub generation for abstract classes")
	public static boolean STUBS = false;

	//---------------------------------------------------------------
	// Experimental
	@Parameter(key = "remote_testing", description = "Include remote calls")
	public static boolean REMOTE_TESTING = false;

	@Parameter(key = "cpu_timeout", description = "Measure timeouts on CPU time, not global time")
	public static boolean CPU_TIMEOUT = false;

	@Parameter(key = "log_timeout", description = "Produce output each time a test times out")
	public static boolean LOG_TIMEOUT = false;

	@Parameter(key = "call_probability", description = "Probability to reuse an existing test case, if it produces a required object")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double CALL_PROBABILITY = 0.0;

	@Parameter(key = "usage_models", description = "Names of usage model files")
	public static String USAGE_MODELS = "";

	@Parameter(key = "usage_rate", description = "Probability with which to use transitions out of the OUM")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double USAGE_RATE = 0.5;

	@Parameter(key = "instrument_parent", description = "Also count coverage goals in superclasses")
	public static boolean INSTRUMENT_PARENT = false;

	@Parameter(key = "check_contracts", description = "Check contracts during test execution")
	public static boolean CHECK_CONTRACTS = false;

	@Parameter(key = "check_contracts_end", description = "Check contracts only once per test")
	public static boolean CHECK_CONTRACTS_END = false;

	public enum TestFactory {
		RANDOM, OUM
	}

	@Parameter(key = "test_factory", description = "Which factory creates tests")
	public static TestFactory TEST_FACTORY = TestFactory.RANDOM;

	@Parameter(key = "concolic_mutation", description = "Probability of using concolic mutation operator")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double CONCOLIC_MUTATION = 0.0;

	@Parameter(key = "testability_transformation", description = "Apply testability transformation (Yanchuan)")
	public static boolean TESTABILITY_TRANSFORMATION = false;

	@Parameter(key = "TT.stack", description = "Maximum stack depth for testability transformation")
	public static int TT_stack = 10;

	@Parameter(key = "TT", description = "Testability transformation")
	public static boolean TT = false;

	//---------------------------------------------------------------
	// Test Execution
	@Parameter(key = "timeout", group = "Test Execution", description = "Milliseconds allowed per test")
	public static int TIMEOUT = 5000;

	@Parameter(key = "mutation_timeouts", group = "Test Execution", description = "Number of timeouts before we consider a mutant killed")
	public static int MUTATION_TIMEOUTS = 3;

	//---------------------------------------------------------------
	// TODO: Fix description
	public enum AlternativeFitnessCalculationMode {
		SUM, MIN, MAX, AVG, SINGLE
	}

	@Parameter(key = "alternative_fitness_calculation_mode", description = "")
	public static AlternativeFitnessCalculationMode ALTERNATIVE_FITNESS_CALCULATION_MODE = AlternativeFitnessCalculationMode.SUM;

	@Parameter(key = "initially_enforced_randomness", description = "")
	@DoubleValue(min = 0.0, max = 1.0)
	public static double INITIALLY_ENFORCED_RANDOMNESS = 0.4;

	@Parameter(key = "alternative_fitness_range", description = "")
	public static double ALTERNATIVE_FITNESS_RANGE = 100.0;

	@Parameter(key = "preorder_goals_by_difficulty", description = "")
	public static boolean PREORDER_GOALS_BY_DIFFICULTY = false;

	@Parameter(key = "starve_by_fitness", description = "")
	public static boolean STARVE_BY_FITNESS = true;

	@Parameter(key = "penalize_overwriting_definitions_flat", description = "")
	public static boolean PENALIZE_OVERWRITING_DEFINITIONS_FLAT = false;

	@Parameter(key = "penalize_overwriting_definitions_linearly", description = "")
	public static boolean PENALIZE_OVERWRITING_DEFINITIONS_LINEARLY = false;

	@Parameter(key = "enable_alternative_fitness_calculation", description = "")
	public static boolean ENABLE_ALTERNATIVE_FITNESS_CALCULATION = true;

	@Parameter(key = "defuse_debug_mode", description = "")
	public static boolean DEFUSE_DEBUG_MODE = false;

	@Parameter(key = "randomize_difficulty", description = "")
	public static boolean RANDOMIZE_DIFFICULTY = true;

	//---------------------------------------------------------------
	// Runtime parameters

	public enum Criterion {
		CONCURRENCY, LCSAJ, DEFUSE, PATH, BRANCH, MUTATION
	}

	/** Cache target class */
	private static Class<?> TARGET_CLASS_INSTANCE = null;

	@Parameter(key = "PROJECT_PREFIX", group = "Runtime", description = "Package name of target package")
	public static String PROJECT_PREFIX = null;

	@Parameter(key = "PROJECT_DIR", group = "Runtime", description = "Directory name of target package")
	public static String PROJECT_DIR = null;

	/** Package name of target class (might be a subpackage) */
	public static String CLASS_PREFIX = "";

	/** Sub-package name of target class */
	public static String SUB_PREFIX = "";

	/** Class under test */
	@Parameter(key = "TARGET_CLASS", group = "Runtime", description = "Class under test")
	public static String TARGET_CLASS = "";

	/** Method under test */
	@Parameter(key = "target_method", group = "Runtime", description = "Method for which to generate tests")
	public static String TARGET_METHOD = "";

	@Parameter(key = "OUTPUT_DIR", group = "Runtime", description = "Directory in which to put generated files")
	public static String OUTPUT_DIR = "evosuite-files";

	@Parameter(key = "criterion", group = "Runtime", description = "Coverage criterion")
	public static Criterion CRITERION = Criterion.BRANCH;

	public enum Strategy {
		ONEBRANCH, EVOSUITE
	}

	@Parameter(key = "strategy", group = "Runtime", description = "Which mode to use")
	public static Strategy STRATEGY = Strategy.EVOSUITE;

	@Parameter(key = "process_communication_port", group = "Runtime", description = "Port at which the communication with the external process is done")
	public static int PROCESS_COMMUNICATION_PORT = -1;

	@Parameter(key = "max_stalled_threads", group = "Runtime", description = "Number of stalled threads")
	public static int MAX_STALLED_THREADS = 10;

	/**
	 * Get all parameters that are available
	 * 
	 * @return
	 */
	public static Set<String> getParameters() {
		return parameterMap.keySet();
	}

	/**
	 * Determine fields that are declared as parameters
	 */
	private static void reflectMap() {
		for (Field f : Properties.class.getFields()) {
			if (f.isAnnotationPresent(Parameter.class)) {
				Parameter p = f.getAnnotation(Parameter.class);
				parameterMap.put(p.key(), f);
			}
		}
	}

	/**
	 * Initialize properties from property file or commandline parameters
	 */
	private void loadProperties() {
		properties = new java.util.Properties();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("evosuite.properties");
			properties.load(in);
			System.out.println("* Properties loaded from configuration file evosuite.properties");
		} catch (FileNotFoundException e) {
			System.err.println("- Error: Could not find configuration file evosuite.properties");
		} catch (IOException e) {
			System.err.println("- Error: Could not find configuration file evosuite.properties");
		} catch (Exception e) {
			System.err.println("- Error: Could not find configuration file evosuite.properties");
		}

		for (String parameter : parameterMap.keySet()) {
			try {
				if (System.getProperty(parameter) != null) {
					setValue(parameter, System.getProperty(parameter));
				} else if (properties.getProperty(parameter) != null) {
					setValue(parameter, properties.getProperty(parameter));
				}
			} catch (NoSuchParameterException e) {
				System.out.println("- No such parameter: " + parameter);
			} catch (IllegalArgumentException e) {
				System.out.println("- Error setting parameter \"" + parameter + "\": "
				        + e);
			} catch (IllegalAccessException e) {
				System.out.println("- Error setting parameter \"" + parameter + "\": "
				        + e);
			}
		}
	}

	/** All fields representing values, inserted via reflection */
	private static Map<String, Field> parameterMap = new HashMap<String, Field>();

	/**
	 * Get class of parameter
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 */
	public static Class<?> getType(String key) throws NoSuchParameterException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		return f.getType();
	}

	/**
	 * Get description string of parameter
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 */
	public static String getDescription(String key) throws NoSuchParameterException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		Parameter p = f.getAnnotation(Parameter.class);
		return p.description();
	}

	/**
	 * Get group name of parameter
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 */
	public static String getGroup(String key) throws NoSuchParameterException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		Parameter p = f.getAnnotation(Parameter.class);
		return p.group();
	}

	/**
	 * Get integer boundaries
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 */
	public static IntValue getIntLimits(String key) throws NoSuchParameterException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		return f.getAnnotation(IntValue.class);
	}

	/**
	 * Get double boundaries
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 */
	public static DoubleValue getDoubleLimits(String key) throws NoSuchParameterException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		return f.getAnnotation(DoubleValue.class);
	}

	/**
	 * Get an integer parameter value
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static int getIntegerValue(String key) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		return parameterMap.get(key).getInt(null);
	}

	/**
	 * Get a boolean parameter value
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean getBooleanValue(String key) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		return parameterMap.get(key).getBoolean(null);
	}

	/**
	 * Get a double parameter value
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static double getDoubleValue(String key) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		return parameterMap.get(key).getDouble(null);
	}

	/**
	 * Get parameter value as string (works for all types)
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchParameterException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String getStringValue(String key) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		return parameterMap.get(key).toString();
	}

	/**
	 * Set parameter to new integer value
	 * 
	 * @param key
	 * @param value
	 * @throws NoSuchParameterException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void setValue(String key, int value) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);

		if (f.isAnnotationPresent(IntValue.class)) {
			IntValue i = f.getAnnotation(IntValue.class);
			if (value < i.min() || value > i.max())
				throw new IllegalArgumentException();
		}

		f.setInt(this, value);
	}

	/**
	 * Set parameter to new boolean value
	 * 
	 * @param key
	 * @param value
	 * @throws NoSuchParameterException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void setValue(String key, boolean value) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		f.setBoolean(this, value);
	}

	/**
	 * Set parameter to new double value
	 * 
	 * @param key
	 * @param value
	 * @throws NoSuchParameterException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void setValue(String key, double value) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		if (f.isAnnotationPresent(DoubleValue.class)) {
			DoubleValue i = f.getAnnotation(DoubleValue.class);
			if (value < i.min() || value > i.max())
				throw new IllegalArgumentException();
		}
		f.setDouble(this, value);
	}

	/**
	 * Set parameter to new value from String
	 * 
	 * @param key
	 * @param value
	 * @throws NoSuchParameterException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setValue(String key, String value) throws NoSuchParameterException,
	        IllegalArgumentException, IllegalAccessException {
		if (!parameterMap.containsKey(key))
			throw new NoSuchParameterException(key);

		Field f = parameterMap.get(key);
		if (f.getType().isEnum()) {
			f.set(null, Enum.valueOf((Class<Enum>) f.getType(), value.toUpperCase()));
		} else if (f.getType().equals(int.class)) {
			setValue(key, Integer.parseInt(value));
		} else if (f.getType().equals(boolean.class)) {
			setValue(key, Boolean.parseBoolean(value));
		} else if (f.getType().equals(double.class)) {
			setValue(key, Double.parseDouble(value));
		} else {
			f.set(null, value);
		}
	}

	/** Singleton instance */
	private static Properties instance = new Properties();

	/** Internal properties hashmap */
	private java.util.Properties properties;

	/**
	 * Singleton accessor
	 * 
	 * @return
	 */
	public static Properties getInstance() {
		if (instance == null)
			instance = new Properties();
		return instance;
	}

	/**
	 * This exception is used when a non-existent parameter is accessed
	 * 
	 * 
	 */
	public static class NoSuchParameterException extends Exception {

		private static final long serialVersionUID = 9074828392047742535L;

		public NoSuchParameterException(String key) {
			super("No such property defined: " + key);
		}
	}

	/** Constructor */
	private Properties() {
		reflectMap();
		loadProperties();
		if (TARGET_CLASS != null && !TARGET_CLASS.equals("")) {
			CLASS_PREFIX = TARGET_CLASS.substring(0, TARGET_CLASS.lastIndexOf('.'));
			SUB_PREFIX = CLASS_PREFIX.replace(PROJECT_PREFIX + ".", "");
		}
	}

	/**
	 * Get class object of class under test
	 * 
	 * @return
	 */
	public static Class<?> getTargetClass() {
		if (TARGET_CLASS_INSTANCE != null)
			return TARGET_CLASS_INSTANCE;

		try {
			TARGET_CLASS_INSTANCE = Class.forName(TARGET_CLASS);
			return TARGET_CLASS_INSTANCE;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not find class under test " + TARGET_CLASS);
		}
		return null;
	}

	/**
	 * Update the evosuite.properties file with the current setting
	 */
	public void writeConfiguration() {
		try {
			URL fileURL = this.getClass().getClassLoader().getResource("evosuite.properties");
			String name = fileURL.getFile();
			OutputStream out = new FileOutputStream(new File(name));
			// TODO: Update the properties!
			properties.store(out, "This file was automatically produced by EvoSuite");
		} catch (FileNotFoundException e) {
			System.err.println("Error: Could not find configuration file evosuite.properties");
		} catch (IOException e) {
			System.err.println("Error: Could not find configuration file evosuite.properties");
		} catch (Exception e) {
			System.err.println("Error: Could not find configuration file evosuite.properties");
		}

	}
}
