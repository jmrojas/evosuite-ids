package org.evosuite.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.rmi.MasterServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.rmi.service.ClientStateInformation;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.Listener;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This singleton collects all the data values reported by the client node
 * 
 * @author gordon
 *
 */
public class SearchStatistics implements Listener<ClientStateInformation>{

	private static final long serialVersionUID = -1859683466333302151L;

	/* Singleton instance */
	private static SearchStatistics instance = null;
	
	private static Logger logger = LoggerFactory.getLogger(SearchStatistics.class);
	
	/* Map of client id to best individual received from that client so far */
	private Map<String, TestSuiteChromosome> bestIndividual = new HashMap<String, TestSuiteChromosome>();
	
	/* Backend used to output the data */
	private StatisticsBackend backend = null;
	
	/* Output variables and their values */ 
	private Map<String, OutputVariable<?>> outputVariables = new TreeMap<String, OutputVariable<?>>();

	/* Variable factories to extract output variables from chromosomes */
	private Map<String, ChromosomeOutputVariableFactory<?>> variableFactories = new TreeMap<String, ChromosomeOutputVariableFactory<?>>(); 
	
	/* Variable factories to extract sequence variables */
	private Map<String, SequenceOutputVariableFactory<?>> sequenceOutputVariableFactories = new TreeMap<String, SequenceOutputVariableFactory<?>>();

	/* Keep track of how far EvoSuite progressed */
	private ClientState currentState = ClientState.INITIALIZATION;
	
	private long currentStateStarted = System.currentTimeMillis();
	
	private long startTime = 0L;
	
	/**
	 * <p>
	 * This enumeration defines all the runtime variables we want to store in
	 * the CSV files. Note, it is perfectly fine to add new ones, in any
	 * position. Just be sure to define a proper mapper in {@code getCSVvalue}.
	 * </p>
	 * 
	 * <p>
	 * WARNING: do not change the name of any variable! If you do, current R
	 * scripts will break. If you really need to change a name, please first
	 * contact Andrea Arcuri.
	 * </p>
	 * 
	 * @author arcuri
	 * 
	 */
	public enum RuntimeVariable {
		Class,               // Class under test
		Predicates,          // Number of predicates in CUT
		Classpath_Classes,   // Number of classes in classpath 
		Analyzed_Classes,    // Number of classes analyzed for test cluster
		Generators,          // Total number of generators
		Modifiers,           // Total number of modifiers
		Total_Branches,      // Total number of branches in CUT
		Covered_Branches,    // Number of covered branches in CUT
		Total_Methods,       // Total number of methods in CUT
		Branchless_Methods,  // Number of methods without any predicates
		Covered_Methods,     // Number of methods covered
		Covered_Branchless_Methods, // Number of methods without predicates covered
		Total_Goals,         // Total number of coverage goals for current criterion
		Covered_Goals,       // Total number of covered goals
		Mutants,             // Number of mutants
		Statements_Executed, // Total number of statements executed
		Coverage,            // Obtained coverage of the chosen testing criterion
		Fitness,             // Fitness value of the best individual
		Size,                // Number of tests in resulting test suite
		Length,              // Total number of statements in final test suite
		/**
		 * Obtained coverage at different points in time
		 */
		CoverageTimeline,
		FitnessTimeline,
		SizeTimeline,
		LengthTimeline,
		/**
		 * Not only the covered branches ratio, but also including the
		 * branchless methods
		 */
		BranchCoverage,
		NumberOfGeneratedTestCases,
		/**
		 * The number of serialized objects that EvoSuite is
		 * going to use for seeding strategies
		 */
		NumberOfInputPoolObjects,
		AllDefCoverage,
		DefUseCoverage,
		WeakMutationScore,
		Creation_Time,
		Minimization_Time,
		Total_Time,
		Test_Execution_Time,
		Goal_Computation_Time,
		Result_Size,
		Result_Length,
		Minimized_Size,
		Minimized_Length,
		Chromosome_Length,
		Population_Size,
		Random_Seed,
		Budget,
		Lines,
		AllPermission,
		SecurityPermission,
		UnresolvedPermission,
		AWTPermission,
		FilePermission,
		SerializablePermission,
		ReflectPermission,
		RuntimePermission,
		NetPermission,
		SocketPermission,
		SQLPermission,
		PropertyPermission,
		LoggingPermission,
		SSLPermission,
		AuthPermission,
		AudioPermission,
		OtherPermission,
		Threads,
//		JUnitTests,
		Branches,
		StatementCoverage,
		MutationScore,
		Explicit_MethodExceptions,
		Explicit_TypeExceptions,
		Implicit_MethodExceptions,
		Implicit_TypeExceptions,
		Error_Predicates,
		Error_Branches_Covered,
		Error_Branchless_Methods,
		Error_Branchless_Methods_Covered,
		AssertionContract,
		EqualsContract,
		EqualsHashcodeContract,
		EqualsNullContract,
		EqualsSymmetricContract,
		HashCodeReturnsNormallyContract,
		JCrasherExceptionContract,
		NullPointerExceptionContract,
		ToStringReturnsNormallyContract,
		UndeclaredExceptionContract,
		Contract_Violations,
		Unique_Violations,
		Data_File,
		/**
		 * Dataflow stuff
		 */
		Definitions,
		Uses,
		DefUsePairs,
		IntraMethodPairs,
		InterMethodPairs,
		IntraClassPairs,
		ParameterPairs,
		LCSAJs,
		AliasingIntraMethodPairs,
		AliasingInterMethodPairs,
		AliasingIntraClassPairs,
		AliasingParameterPairs,
		CoveredIntraMethodPairs,
		CoveredInterMethodPairs,
		CoveredIntraClassPairs,
		CoveredParameterPairs,
		CoveredAliasIntraMethodPairs,
		CoveredAliasInterMethodPairs,
		CoveredAliasIntraClassPairs,
		CoveredAliasParameterPairs,
		CarvedTests,
		CarvedCoverage
	};
	
	
	private SearchStatistics() { 
		switch(Properties.STATISTICS_BACKEND) {
		case CONSOLE:
			backend = new ConsoleStatisticsBackend();
			break;
		case CSV:
			backend = new CSVStatisticsBackend();
			break;
		case NONE:
		default:
			// If no backend is specified, there is no output
			backend = null;
		}
		initFactories();
		setOutputVariable(RuntimeVariable.Random_Seed, Randomness.getSeed());
		sequenceOutputVariableFactories.put(RuntimeVariable.CoverageTimeline.name(), new CoverageSequenceOutputVariableFactory());
		sequenceOutputVariableFactories.put(RuntimeVariable.FitnessTimeline.name(), new FitnessSequenceOutputVariableFactory());
		sequenceOutputVariableFactories.put(RuntimeVariable.SizeTimeline.name(), new SizeSequenceOutputVariableFactory());
		sequenceOutputVariableFactories.put(RuntimeVariable.LengthTimeline.name(), new LengthSequenceOutputVariableFactory());
		// sequenceOutputVariableFactories.put("Generation_History", new GenerationSequenceOutputVariableFactory());
		MasterServices.getInstance().getMasterNode().addListener(this);
	}
	
	public static SearchStatistics getInstance() {
		if(instance == null)
			instance = new SearchStatistics();
		
		return instance;
	}
	
	/**
	 * This method is called when a new individual is sent from a client.
	 * The individual represents the best individual of the current generation.
	 * 
	 * @param rmiClientIdentifier
	 * @param individual
	 */
	public void currentIndividual(String rmiClientIdentifier, Chromosome individual) {
		if(backend == null)
			return;
		
		logger.debug("Received individual");
		bestIndividual.put(rmiClientIdentifier, (TestSuiteChromosome) individual);
		for(SequenceOutputVariableFactory<?> v : sequenceOutputVariableFactories.values()) {
			v.update((TestSuiteChromosome) individual);
		}
		for(ChromosomeOutputVariableFactory<?> v : variableFactories.values()) {
			setOutputVariable(v.getVariable((TestSuiteChromosome) individual));
		}
	}
	
	/**
	 * Set an output variable to a value directly 
	 * 
	 * @param name
	 * @param value
	 */
	public void setOutputVariable(RuntimeVariable variable, Object value) {
		// TODO: If there already exists that key and the value is different, issue warning?
		outputVariables.put(variable.toString(), new OutputVariable<Object>(variable.toString(), value));
	}
	
	public void setOutputVariable(OutputVariable<?> variable) {
		// TODO: If there already exists that key and the value is different, issue warning?
		outputVariables.put(variable.getName(), variable);
	}
	
	/**
	 * Retrieve list of possible variables
	 *  
	 * @return
	 */
	private List<String> getAllOutputVariableNames() {
		String[] essentials = new String[] { "TARGET_CLASS" };
		List<String> variableNames = new ArrayList<String>();
		variableNames.addAll(Arrays.asList(essentials));
		variableNames.addAll(outputVariables.keySet());
		variableNames.addAll(variableFactories.keySet());
		variableNames.addAll(sequenceOutputVariableFactories.keySet());
		return variableNames;
	}
	
	/**
	 * Retrieve list of output variables that the user will get to see.
	 * If output_variables is not set, then all variables will be returned
	 * 
	 * @return
	 */
	private Collection<String> getOutputVariableNames() {
		List<String> variableNames = new ArrayList<String>();
		if(Properties.OUTPUT_VARIABLES == null) {
			variableNames.addAll(getAllOutputVariableNames());
		} else {
			variableNames.addAll(Arrays.asList(Properties.OUTPUT_VARIABLES.split(",")));
		}
		return variableNames;
	}
	
	/**
	 * Extract output variables from current best individual
	 * 
	 * @param individual
	 * @return
	 */
	private List<OutputVariable<?>> getOutputVariables(Chromosome individual) {
		List<OutputVariable<?>> variables = new ArrayList<OutputVariable<?>>();
		
		for(String variableName : getOutputVariableNames()) {
			if(outputVariables.containsKey(variableName)) {
				variables.add(outputVariables.get(variableName));
			} else if(Properties.getParameters().contains(variableName)) {
				variables.add(new PropertyOutputVariableFactory(variableName).getVariable());
			} else if(variableFactories.containsKey(variableName)) {
				// TODO: Iterator mess
				variables.add(variableFactories.get(variableName).getVariable((TestSuiteChromosome) bestIndividual.values().iterator().next()));
			} else if(sequenceOutputVariableFactories.containsKey(variableName)) {
				variables.addAll(sequenceOutputVariableFactories.get(variableName).getOutputVariables());
			}
			else {
				throw new IllegalArgumentException("No such output variable: "+variableName+". Available variables: "+getAllOutputVariableNames());
			}
		}
		
		return variables;
	}
	
	/**
	 * Write result to disk using selected backend
	 */
	public void writeStatistics() {
		logger.info("Writing statistics");
		if(backend == null)
			return;
		
		if(!bestIndividual.isEmpty()) {
			Chromosome individual = bestIndividual.values().iterator().next();
			backend.writeData(getOutputVariables(individual));
		} else {
			logger.info("No statistics has been saved because EvoSuite failed to generate any test case");
		}
	}

	/**
	 * Process status update event received from client
	 */
	@Override
	public void receiveEvent(ClientStateInformation information) {
		if(information.getState() != currentState) {
			logger.info("Received status update: "+information);
			if(information.getState() == ClientState.SEARCH) {
				startTime = System.currentTimeMillis();
				for(SequenceOutputVariableFactory<?> factory : sequenceOutputVariableFactories.values()) {
					factory.setStartTime(startTime);
				}
			}
			OutputVariable<Long> time = new OutputVariable<Long>("time_"+currentState.getName(), System.currentTimeMillis() - currentStateStarted);
			outputVariables.put(time.getName(), time);
			currentState = information.getState();
			currentStateStarted = System.currentTimeMillis();
		}

	}
	
	/**
	 * Create default factories
	 */
	private void initFactories() {
		variableFactories.put(RuntimeVariable.Length.name(), new ChromosomeLengthOutputVariableFactory());
		variableFactories.put(RuntimeVariable.Size.name(), new ChromosomeSizeOutputVariableFactory());
		variableFactories.put(RuntimeVariable.Coverage.name(), new ChromosomeCoverageOutputVariableFactory());
		variableFactories.put(RuntimeVariable.Fitness.name(), new ChromosomeFitnessOutputVariableFactory());
	}
	
	/**
	 * Total length of a test suite
	 */
	private static class ChromosomeLengthOutputVariableFactory extends ChromosomeOutputVariableFactory<Integer> {
		public ChromosomeLengthOutputVariableFactory() {
			super(RuntimeVariable.Length);
		}

		@Override
		protected Integer getData(TestSuiteChromosome individual) {
			return individual.totalLengthOfTestCases();
		}
	}
	
	/**
	 * Number of tests in a test suite
	 */
	private static class ChromosomeSizeOutputVariableFactory extends ChromosomeOutputVariableFactory<Integer> {
		public ChromosomeSizeOutputVariableFactory() {
			super(RuntimeVariable.Size);
		}

		@Override
		protected Integer getData(TestSuiteChromosome individual) {
			return individual.size();
		}
	}

	/**
	 * Fitness value of a test suite
	 */
	private static class ChromosomeFitnessOutputVariableFactory extends ChromosomeOutputVariableFactory<Double> {
		public ChromosomeFitnessOutputVariableFactory() {
			super(RuntimeVariable.Fitness);
		}

		@Override
		protected Double getData(TestSuiteChromosome individual) {
			return individual.getFitness();
		}
	}

	/**
	 * Coverage value of a test suite
	 */
	private static class ChromosomeCoverageOutputVariableFactory extends ChromosomeOutputVariableFactory<Double> {
		public ChromosomeCoverageOutputVariableFactory() {
			super(RuntimeVariable.Coverage);
		}

		@Override
		protected Double getData(TestSuiteChromosome individual) {
			return individual.getCoverage();
		}
	}
	
	/**
	 * Sequence variable for fitness values
	 */
	private static class FitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

		public FitnessSequenceOutputVariableFactory() {
			super(RuntimeVariable.FitnessTimeline);
		}
		
		@Override
		protected Double getValue(TestSuiteChromosome individual) {
			return individual.getFitness();
		}
	}

	/**
	 * Sequence variable for coverage values
	 */
	private static class CoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

		public CoverageSequenceOutputVariableFactory() {
			super(RuntimeVariable.CoverageTimeline);
		}
		
		@Override
		public Double getValue(TestSuiteChromosome individual) {
			return individual.getCoverage();
		}
	}

	/**
	 * Sequence variable for number of tests
	 */
	private static class SizeSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Integer> {

		public SizeSequenceOutputVariableFactory() {
			super(RuntimeVariable.SizeTimeline);
		}
		
		@Override
		public Integer getValue(TestSuiteChromosome individual) {
			return individual.size();
		}
	}

	/**
	 * Sequence variable for total length of tests
	 */
	private static class LengthSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Integer> {

		public LengthSequenceOutputVariableFactory() {
			super(RuntimeVariable.LengthTimeline);
		}
		
		@Override
		public Integer getValue(TestSuiteChromosome individual) {
			return individual.totalLengthOfTestCases();
		}
	}
}
