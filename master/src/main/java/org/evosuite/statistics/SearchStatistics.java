package org.evosuite.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.evosuite.Properties;
import org.evosuite.coverage.branch.OnlyBranchCoverageSuiteFitness;
import org.evosuite.coverage.cbranch.CBranchSuiteFitness;
import org.evosuite.coverage.exception.ExceptionCoverageSuiteFitness;
import org.evosuite.coverage.line.LineCoverageSuiteFitness;
import org.evosuite.coverage.method.MethodCoverageSuiteFitness;
import org.evosuite.coverage.method.MethodNoExceptionCoverageSuiteFitness;
import org.evosuite.coverage.method.MethodTraceCoverageSuiteFitness;
import org.evosuite.coverage.mutation.OnlyMutationSuiteFitness;
import org.evosuite.coverage.output.OutputCoverageSuiteFitness;
import org.evosuite.coverage.rho.RhoCoverageSuiteFitness;
import org.evosuite.ga.Chromosome;
import org.evosuite.regression.RegressionTestSuiteChromosome;
import org.evosuite.result.TestGenerationResult;
import org.evosuite.rmi.MasterServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.rmi.service.ClientStateInformation;
import org.evosuite.statistics.backend.CSVStatisticsBackend;
import org.evosuite.statistics.backend.ConsoleStatisticsBackend;
import org.evosuite.statistics.backend.DebugStatisticsBackend;
import org.evosuite.statistics.backend.HTMLStatisticsBackend;
import org.evosuite.statistics.backend.StatisticsBackend;
import org.evosuite.testcase.TestCase;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.Listener;
import org.evosuite.utils.LoggingUtils;
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

	/** Singleton instance */
	private static SearchStatistics instance = null;

	private static final Logger logger = LoggerFactory.getLogger(SearchStatistics.class);

	/** Map of client id to best individual received from that client so far */
	private Map<String, TestSuiteChromosome> bestIndividual = new HashMap<String, TestSuiteChromosome>();

	/** Backend used to output the data */
	private StatisticsBackend backend = null;

	/** Output variables and their values */ 
	private Map<String, OutputVariable<?>> outputVariables = new TreeMap<String, OutputVariable<?>>();

	/** Variable factories to extract output variables from chromosomes */
	private Map<String, ChromosomeOutputVariableFactory<?>> variableFactories = new TreeMap<String, ChromosomeOutputVariableFactory<?>>(); 

	/** Variable factories to extract sequence variables */
	private Map<String, SequenceOutputVariableFactory<?>> sequenceOutputVariableFactories = new TreeMap<String, SequenceOutputVariableFactory<?>>();

	/** Keep track of how far EvoSuite progressed */
	private ClientState currentState = ClientState.INITIALIZATION;

	private long currentStateStarted = System.currentTimeMillis();

	private long searchStartTime = 0L;

	private long startTime = System.currentTimeMillis();

	private List<List<TestGenerationResult>> results = new ArrayList<List<TestGenerationResult>>();

	private SearchStatistics() { 
		switch(Properties.STATISTICS_BACKEND) {
		case CONSOLE:
			backend = new ConsoleStatisticsBackend();
			break;
		case CSV:
			backend = new CSVStatisticsBackend();
			break;
		case HTML:
			backend = new HTMLStatisticsBackend();
			break;
		case DEBUG:
			backend = new DebugStatisticsBackend();
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
        sequenceOutputVariableFactories.put(RuntimeVariable.TotalExceptionsTimeline.name(), new TotalExceptionsSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.IBranchGoalsTimeline.name(), new IBranchGoalsSequenceOutputVariableFactory());

        sequenceOutputVariableFactories.put(RuntimeVariable.OnlyBranchFitnessTimeline.name(), new OnlyBranchFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.OnlyBranchCoverageTimeline.name(), new OnlyBranchCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.CBranchFitnessTimeline.name(), new CBranchFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.CBranchCoverageTimeline.name(), new CBranchCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.MethodTraceFitnessTimeline.name(), new MethodTraceFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.MethodTraceCoverageTimeline.name(), new MethodTraceCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.MethodFitnessTimeline.name(), new MethodFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.MethodCoverageTimeline.name(), new MethodCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.MethodNoExceptionFitnessTimeline.name(), new MethodNoExceptionFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.MethodNoExceptionCoverageTimeline.name(), new MethodNoExceptionCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.LineFitnessTimeline.name(), new LineFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.LineCoverageTimeline.name(), new LineCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.OutputFitnessTimeline.name(), new OutputFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.OutputCoverageTimeline.name(), new OutputCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.ExceptionFitnessTimeline.name(), new ExceptionFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.ExceptionCoverageTimeline.name(), new ExceptionCoverageSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.OnlyMutationFitnessTimeline.name(), new OnlyMutationFitnessSequenceOutputVariableFactory());
        sequenceOutputVariableFactories.put(RuntimeVariable.OnlyMutationCoverageTimeline.name(), new OnlyMutationCoverageSequenceOutputVariableFactory());

        // sequenceOutputVariableFactories.put("Generation_History", new GenerationSequenceOutputVariableFactory());
		if(MasterServices.getInstance().getMasterNode() != null)
			MasterServices.getInstance().getMasterNode().addListener(this);
	}

	public static SearchStatistics getInstance() {
		if(instance == null)
			instance = new SearchStatistics();

		return instance;
	}

	public static void clearInstance() {
		instance = null;
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
		if (individual instanceof RegressionTestSuiteChromosome) {
			TestSuiteChromosome tmpTestSuite = new TestSuiteChromosome();
			List<TestCase> allRegressionTests = ((RegressionTestSuiteChromosome) individual).getTests();
			for (TestCase t : allRegressionTests)
				tmpTestSuite.addTest(t);
			individual = tmpTestSuite;
		}
		bestIndividual.put(rmiClientIdentifier, (TestSuiteChromosome) individual);
        for(ChromosomeOutputVariableFactory<?> v : variableFactories.values()) {
            setOutputVariable(v.getVariable((TestSuiteChromosome) individual));
        }
		for(SequenceOutputVariableFactory<?> v : sequenceOutputVariableFactories.values()) {
			v.update((TestSuiteChromosome) individual);
		}
	}

	/**
	 * Set an output variable to a value directly 
	 * 
	 * @param variable
	 * @param value
	 */
	public void setOutputVariable(RuntimeVariable variable, Object value) {
		setOutputVariable(new OutputVariable<Object>(variable.toString(), value));
	}

	public void setOutputVariable(OutputVariable<?> variable) {
        /**
         * if the output variable is contained in sequenceOutputVariableFactories,
         * then it must be a DirectSequenceOutputVariableFactory, hence we set its
         * value so that it can be used to produce the next timeline variable.
         */
        if (sequenceOutputVariableFactories.containsKey(variable.getName())) {
            DirectSequenceOutputVariableFactory<Integer> v = (DirectSequenceOutputVariableFactory<Integer>)sequenceOutputVariableFactories.get(variable.getName());
            v.setValue((Integer)variable.getValue());
        } else
            outputVariables.put(variable.getName(), variable);
    }

	public void addTestGenerationResult(List<TestGenerationResult> result) {
	    results.add(result);
	}

	public List<List<TestGenerationResult>> getTestGenerationResults() {
		return results;
	}

	/**
	 * Retrieve list of possible variables
	 *  
	 * @return
	 */
	private List<String> getAllOutputVariableNames() {
		List<String> variableNames = new ArrayList<String>();

		String[] essentials = new String[] {  //TODO maybe add some more
				"TARGET_CLASS" , "criterion", 
				RuntimeVariable.Coverage.toString(),
				//TODO: why is this fixed?
				//RuntimeVariable.BranchCoverage.toString(),
				RuntimeVariable.Total_Goals.toString(),
				RuntimeVariable.Covered_Goals.toString()
				};
		variableNames.addAll(Arrays.asList(essentials));
		
		/* cannot use what we received, as due to possible bugs/errors those might not be constant
		variableNames.addAll(outputVariables.keySet());
		variableNames.addAll(variableFactories.keySet());
		variableNames.addAll(sequenceOutputVariableFactories.keySet());
		*/
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
	 * Extract output variables from input <code>individual</code>.
	 * Add also all the other needed search-level variables. 
	 * 
	 * @param individual
	 * @return <code>null</code> if some data is missing
	 */
	private Map<String, OutputVariable<?>> getOutputVariables(TestSuiteChromosome individual) {
		Map<String, OutputVariable<?>> variables = new LinkedHashMap<String, OutputVariable<?>>();
		
		for(String variableName : getOutputVariableNames()) {
			if(outputVariables.containsKey(variableName)) {
				//values directly sent by the client
				variables.put(variableName, outputVariables.get(variableName));
			} else if(Properties.getParameters().contains(variableName)) {
				// values used to define the search, ie the -D given as input to EvoSuite
				variables.put(variableName, new PropertyOutputVariableFactory(variableName).getVariable());
			} else if(variableFactories.containsKey(variableName)) {
				//values extracted from the individual
				variables.put(variableName, variableFactories.get(variableName).getVariable(individual));
			} else if(sequenceOutputVariableFactories.containsKey(variableName)) {
				/*
				 * time related values, which will be expanded in a list of values
				 * through time
				 */
				for(OutputVariable<?> var : sequenceOutputVariableFactories.get(variableName).getOutputVariables()) {
					variables.put(var.getName(), var); 
				}
			}
			else {
				logger.error("No obtained value for output variable: "+variableName);
				return null;
			}
		}

		return variables;
	}

	/**
	 * Write result to disk using selected backend
	 * 
	 * @return true if the writing was successful
	 */
	public boolean writeStatistics() {
		logger.info("Writing statistics");
		if(backend == null)
			return false;

		outputVariables.put(RuntimeVariable.Total_Time.name(), new OutputVariable<Object>(RuntimeVariable.Total_Time.name(), System.currentTimeMillis() - startTime));

		if(bestIndividual.isEmpty()) {
			logger.error("No statistics has been saved because EvoSuite failed to generate any test case");
			return false;
		}	

		TestSuiteChromosome individual = bestIndividual.values().iterator().next();
		Map<String,OutputVariable<?>> map = getOutputVariables(individual);
		if(map==null){
			logger.error("Not going to write down statistics data, as some are missing");
			return false;
		} 			

		boolean valid = RuntimeVariable.validateRuntimeVariables(map);
		if(!valid){
			logger.error("Not going to write down statistics data, as some data is invalid");
			return false;
		} else {
			backend.writeData(individual, map);
			return true;
		}
	}
	
	/**
	 * Write result to disk using selected backend
	 * 
	 * @return true if the writing was successful
	 */
	public boolean writeStatisticsForAnalysis() {
		logger.info("Writing statistics");
		if(backend == null) {
			LoggingUtils.getEvoLogger().info("Backend is null");
			return false;
		}

		outputVariables.put(RuntimeVariable.Total_Time.name(), new OutputVariable<Object>(RuntimeVariable.Total_Time.name(), System.currentTimeMillis() - startTime));

		TestSuiteChromosome individual = new TestSuiteChromosome();
		Map<String,OutputVariable<?>> map = getOutputVariables(individual);
		if(map==null){
			logger.error("Not going to write down statistics data, as some are missing");
			return false;
		} 			

		boolean valid = RuntimeVariable.validateRuntimeVariables(map);
		if(!valid){
			logger.error("Not going to write down statistics data, as some data is invalid");
			return false;
		} else {
			backend.writeData(individual, map);
			return true;
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
				searchStartTime = System.currentTimeMillis();
				for(SequenceOutputVariableFactory<?> factory : sequenceOutputVariableFactories.values()) {
					factory.setStartTime(searchStartTime);
				}
			}
			OutputVariable<Long> time = new OutputVariable<Long>("Time_"+currentState.getName(), System.currentTimeMillis() - currentStateStarted);
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

    /**
     * Total number of exceptions
     */
    private static class TotalExceptionsSequenceOutputVariableFactory extends DirectSequenceOutputVariableFactory<Integer> {
        public TotalExceptionsSequenceOutputVariableFactory() {
            super(RuntimeVariable.TotalExceptionsTimeline);
            this.value = 0;
        }

        @Override
        public Integer getValue(TestSuiteChromosome individual) {
            return (Integer) this.value;
        }

        @Override
        public void setValue(Integer value) {
            this.value = value;
        }
    }

    /**
     * Sequence variable for coverage values
     */
    private static class IBranchGoalsSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Integer> {

        public IBranchGoalsSequenceOutputVariableFactory() {
            super(RuntimeVariable.IBranchGoalsTimeline);
        }

        @Override
        public Integer getValue(TestSuiteChromosome individual) {
            return individual.getNumOfNotCoveredGoals();
        }
    }

    private static class OnlyBranchFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public OnlyBranchFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.OnlyBranchFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(OnlyBranchCoverageSuiteFitness.class);
        }
    }

    private static class OnlyBranchCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public OnlyBranchCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.OnlyBranchCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(OnlyBranchCoverageSuiteFitness.class);
        }
    }

    private static class CBranchFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public CBranchFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.CBranchFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(CBranchSuiteFitness.class);
        }
    }

    private static class CBranchCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public CBranchCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.CBranchCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(CBranchSuiteFitness.class);
        }
    }

    private static class MethodTraceFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public MethodTraceFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.MethodTraceFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(MethodTraceCoverageSuiteFitness.class);
        }
    }

    private static class MethodTraceCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public MethodTraceCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.MethodTraceCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(MethodTraceCoverageSuiteFitness.class);
        }
    }

    private static class MethodFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public MethodFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.MethodFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(MethodCoverageSuiteFitness.class);
        }
    }

    private static class MethodCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public MethodCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.MethodCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(MethodCoverageSuiteFitness.class);
        }
    }

    private static class MethodNoExceptionFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public MethodNoExceptionFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.MethodNoExceptionFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(MethodNoExceptionCoverageSuiteFitness.class);
        }
    }

    private static class MethodNoExceptionCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public MethodNoExceptionCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.MethodNoExceptionCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(MethodNoExceptionCoverageSuiteFitness.class);
        }
    }

    private static class RhoFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public RhoFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.RhoCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(RhoCoverageSuiteFitness.class);
        }
    }

    private static class LineFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public LineFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.LineFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(LineCoverageSuiteFitness.class);
        }
    }

    private static class LineCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public LineCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.LineCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(LineCoverageSuiteFitness.class);
        }
    }

    private static class OutputFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public OutputFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.OutputFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(OutputCoverageSuiteFitness.class);
        }
    }

    private static class OutputCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public OutputCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.OutputCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(OutputCoverageSuiteFitness.class);
        }
    }

    private static class ExceptionFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public ExceptionFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.ExceptionFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(ExceptionCoverageSuiteFitness.class);
        }
    }

    private static class ExceptionCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public ExceptionCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.ExceptionCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(ExceptionCoverageSuiteFitness.class);
        }
    }

    private static class OnlyMutationFitnessSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public OnlyMutationFitnessSequenceOutputVariableFactory() {
            super(RuntimeVariable.OnlyMutationFitnessTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getFitnessInstanceOf(OnlyMutationSuiteFitness.class);
        }
    }

    private static class OnlyMutationCoverageSequenceOutputVariableFactory extends SequenceOutputVariableFactory<Double> {

        public OnlyMutationCoverageSequenceOutputVariableFactory() {
            super(RuntimeVariable.OnlyMutationCoverageTimeline);
        }

        @Override
        public Double getValue(TestSuiteChromosome individual) {
            return individual.getCoverageInstanceOf(OnlyMutationSuiteFitness.class);
        }
    }
}
