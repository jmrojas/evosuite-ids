package org.evosuite.statistics;

import java.util.Map;

import org.evosuite.Properties;
import org.evosuite.Properties.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This enumeration defines all the runtime variables we want to store in
 * the CSV files.
 * A runtime variable is either an output of the search (e.g., obtained branch coverage) 
 * or something that can only be determined once the CUT is analyzed (e.g., the number of branches) 
 * 
 * <p>
 * Note, it is perfectly fine to add new runtime variables in this enum, in any position.
 * But it is essential to provide JavaDoc <b>descriptions</b> for each new variable 
 * 
 * <p>
 * WARNING: do not change the name of any variable! If you do, current R
 * scripts will break. If you really need to change a name, please first
 * contact Andrea Arcuri.
 * 
 * @author arcuri
 * 
 */
public enum RuntimeVariable {

	/** Number of predicates in CUT */
	Predicates,         
	/** Number of classes in classpath  */
	Classpath_Classes,   
	/**  Number of classes analyzed for test cluster */
	Analyzed_Classes,   
	/** Total number of generators */
	Generators,         
	/** Total number of modifiers */
	Modifiers,          
	/** Total number of branches in CUT */
	Total_Branches,     
	/** Number of covered branches in CUT */
	Covered_Branches,
	/** Total number of gradient branches */
	Gradient_Branches,
	/** Total number of covered gradient branches */
	Gradient_Branches_Covered,  
	/** The number of lines in the CUT */
	Lines,
	/** The actual covered line numbers */
	Covered_Lines,
	/** Total number of methods in CUT */
	Total_Methods,       
	/** Number of methods covered */
	Covered_Methods,    
	/** Number of methods without any predicates */
	Branchless_Methods, 
	/** Number of methods without predicates covered */
	Covered_Branchless_Methods, 
	/** Total number of coverage goals for current criterion */
	Total_Goals,         
	/** Total number of covered goals */
	Covered_Goals,       
	/** Number of mutants */
	Mutants,            
	/** Total number of statements executed */
	Statements_Executed, 
	/** The total number of tests executed during the search */
	Tests_Executed, 
	/** The total number of fitness evaluations during the search */
	Fitness_Evaluations,
	/** Number of generations the search algorithm has been evolving */
	Generations,
	/** Obtained coverage of the chosen testing criterion */
	Coverage,            
	/** Fitness value of the best individual */
	Fitness,            
	/** Obtained coverage (of the chosen testing criterion) at different points in time  */
	CoverageTimeline,
	/** Obtained fitness values at different points in time */
	FitnessTimeline,
	/** Obtained size values at different points in time */
	SizeTimeline,
	/** Obtained length values at different points in time */
	LengthTimeline,
	/** The obtained statement coverage */
	StatementCoverage,
	/** The obtained rho coverage */
	RhoCoverage,
    RhoCoverageTimeline,
	/** The obtained ambiguity coverage */
	AmbiguityCoverage,
	/** Not only the covered branches ratio, but also including the branchless methods. FIXME: this will need to be changed */
	BranchCoverage,
	/** Only the covered branches ratio. */
	OnlyBranchCoverage,
    OnlyBranchFitnessTimeline,
    OnlyBranchCoverageTimeline,
    IBranchCoverage,
    IBranchInitialGoals,
    IBranchInitialGoalsInTargetClass,
    IBranchGoalsTimeline,
	/** The obtained method coverage (method calls anywhere in trace) */
	MethodTraceCoverage,
    MethodTraceFitnessTimeline,
    MethodTraceCoverageTimeline,
	/** The obtained method coverage */
	MethodCoverage,
    MethodFitnessTimeline,
    MethodCoverageTimeline,
	/** The obtained method coverage (only normal behaviour) */
	MethodNoExceptionCoverage,
    MethodNoExceptionFitnessTimeline,
    MethodNoExceptionCoverageTimeline,
	/** The obtained line coverage */
	LineCoverage,
    LineFitnessTimeline,
    LineCoverageTimeline,
	/** The obtained output value coverage */
	OutputCoverage,
    OutputFitnessTimeline,
    OutputCoverageTimeline,
	/** The obtained exception coverage */
	ExceptionCoverage,
    ExceptionFitnessTimeline,
    ExceptionCoverageTimeline,
	/** A bit string (0/1) representing whether branches (in order) are covered */
	CoveredBranchesBitString,
	/** The obtained score for weak mutation testing */
	WeakMutationScore,
    /** Only mutation = only infection distance */
	OnlyMutationScore,
	OnlyMutationFitnessTimeline,
	OnlyMutationCoverageTimeline,
    /** The obtained score for (strong) mutation testing*/
	MutationScore,
	/** The total time EvoSuite spent generating the test cases */
	Total_Time,
	/** Number of tests in resulting test suite */
	Size,                
	/** Total number of statements in final test suite */
	Length,   
	/** Number of tests in resulting test suite before minimization */
	Result_Size,
	/** Total number of statements in final test suite before minimization */
	Result_Length,
	/** Either use {@link RuntimeVariable#Size} */
	@Deprecated
	Minimized_Size,
	/** Either use  {@link RuntimeVariable#Length} */
	@Deprecated
	Minimized_Length,
	/** The random seed used during the search. A random one was used if none was specified at the beginning */
	Random_Seed,
	/** How many tests were carved, ie used as input seeds for the search */
	CarvedTests,
	/** The branch coverage of the carved tests */
	CarvedCoverage, 
	/** Was any test unstable in the generated JUnit files? */
	HadUnstableTests, 
	/** Number of unstable tests in the generated JUnit files */
	NumUnstableTests, 
	/** An estimate (ie not precise) of the maximum number of threads running at the same time in the CUT */
	Threads,
	/** Number of top-level methods throwing an undeclared exception explicitly with a 'throw new' */
	Explicit_MethodExceptions, 
	/** Number of undeclared exception types that were explicitly thrown with a 'throw new' at least once */
	Explicit_TypeExceptions, 
	/** Number of top-level methods throwing an undeclared exception implicitly (ie, no 'new throw') */
	Implicit_MethodExceptions, 
	/** Number of undeclared exception types that were implicitly thrown (ie, no 'new throw') at least once */
	Implicit_TypeExceptions,
    /** Total number of exceptions covered */
    TotalExceptionsTimeline,
	/* ----- number of unique permissions that were denied for each kind --- */
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
	/* -------------------------------------------------------------------- */
	/** Types of comparisons in branches according to the Bytecode */
	Cmp_IntZero,
	Cmp_IntInt,
	Cmp_RefNull,
	Cmp_RefRef,
	/* -------------------------------------------------------------------- */
	/* TODO following needs to be implemented/updated. Currently they are not (necessarily) supported */
	/** (FIXME: need to be implemented) The number of serialized objects that EvoSuite is going to use for seeding strategies */
	NumberOfInputPoolObjects,
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
	/* --- Dataflow stuff. FIXME: Is this stuff still valid? --- */
	AllDefCoverage,
	DefUseCoverage,	
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
	CoveredAliasParameterPairs;
	/* -------------------------------------------------- */

	
	private static Logger logger = LoggerFactory.getLogger(RuntimeVariable.class);
	
	/**
	 * check if the variables do satisfy a set of predefined constraints: eg, the
	 * number of covered targets cannot be higher than their total number
	 * 
	 * @param map from (key->variable name) to (value -> output variable)
	 * @return
	 */
	public static boolean validateRuntimeVariables(Map<String,OutputVariable<?>> map){
        if (! Properties.VALIDATE_RUNTIME_VARIABLES) {
            logger.error("Not validating runtime variables");
            return true;
        }
        boolean valid = true;

		try{
			Integer totalBranches = getIntegerValue(map,Total_Branches); 
			Integer coveredBranches = getIntegerValue(map,Covered_Branches); 

			if(coveredBranches!=null && totalBranches!=null && coveredBranches > totalBranches){
				logger.error("Obtained invalid branch count: covered "+coveredBranches+" out of "+totalBranches);
				valid = false;
			}
			
			Integer totalGoals = getIntegerValue(map,Total_Goals);
			Integer coveredGoals = getIntegerValue(map,Covered_Goals); 

			if(coveredGoals!=null && totalGoals!=null && coveredGoals > totalGoals){
				logger.error("Obtained invalid goal count: covered "+coveredGoals+" out of "+totalGoals);
				valid = false;
			}
			
			Integer totalMethods = getIntegerValue(map,Total_Methods);
			Integer coveredMethods = getIntegerValue(map,Covered_Methods); 

			if(coveredMethods!=null && totalMethods!=null && coveredMethods > totalMethods){
				logger.error("Obtained invalid method count: covered "+coveredMethods+" out of "+totalMethods);
				valid = false;
			}
			
			String criterion = null;
			if(map.containsKey("criterion")){
				criterion = map.get("criterion").toString();
			}
			
			Double coverage = getDoubleValue(map,Coverage);
			Double branchCoverage = getDoubleValue(map,BranchCoverage);
			
			if(criterion!=null && criterion.equalsIgnoreCase(Criterion.BRANCH.toString()) 
					&& coverage!=null && branchCoverage!=null){
				
				double diff = Math.abs(coverage - branchCoverage);
				if(diff>0.001){
					logger.error("Targeting branch coverage, but Coverage is different "+
							"from BranchCoverage: "+coverage+" != "+branchCoverage);
					valid = false;
				}
			}
			
			
			/*
			 * TODO there are more things we could check here
			 */
			
		} catch(Exception e){
			logger.error("Exception while validating runtime variables: "+e.getMessage(),e);
			valid = false;
		}

		return valid;
	}
	
	private static Integer getIntegerValue(Map<String,OutputVariable<?>> map, RuntimeVariable variable){
		OutputVariable<?> out = map.get(variable.toString());
		if( out != null){
			return (Integer) out.getValue();
		} else {
			return null;
		}
	}
	
	private static Double getDoubleValue(Map<String,OutputVariable<?>> map, RuntimeVariable variable){
		OutputVariable<?> out = map.get(variable.toString());
		if( out != null){
			return (Double) out.getValue();
		} else {
			return null;
		}
	}
};
