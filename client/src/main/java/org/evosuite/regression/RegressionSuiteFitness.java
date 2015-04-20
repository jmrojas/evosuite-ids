/**
 * 
 */
package org.evosuite.regression;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.branch.Branch;
import org.evosuite.coverage.branch.BranchCoverageSuiteFitness;
import org.evosuite.coverage.branch.BranchPool;
import org.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.execution.ExecutionTracer;
import org.evosuite.testcase.execution.MethodCall;
import org.evosuite.testcase.execution.TestCaseExecutor;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;

/**
 * @author Gordon Fraser
 * 
 */
public class RegressionSuiteFitness extends TestSuiteFitnessFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979463801167353053L;
	
	private double bestFitness = Double.MAX_VALUE;

	Map<Integer, Integer> branchIdMap = new HashMap<Integer, Integer>();

	private HashMap<Integer, Double> tempBranchDistanceMap;

	public int max_branch_fitness_valueO = 0;
	public int max_branch_fitness_valueR = 0;

	RegressionExecutionObserver observer;
	//RegressionMethodExecutionObserver methodObserver;

	BranchCoverageSuiteFitness bcFitness;
	BranchCoverageSuiteFitness bcFitnessRegression;

	private int numDifferentExceptions;
	private long diffTime;

	private int totalExceptions;

	Map<Integer, Double> branchDistanceMap;
	boolean firstF = false;
	
	public RegressionSuiteFitness(boolean underTest) {
		branchDistanceMap = new HashMap<Integer, Double>();
	}
	
	public RegressionSuiteFitness() {
		super();
logger.warn("initialising regression Suite Fitness... #################################################");
		if(Properties.REGRESSION_ANALYZE){
			Properties.REGRESSION_USE_FITNESS = 1;
			Properties.REGRESSION_DIFFERENT_BRANCHES = true;
		}
		

		String className = Properties.TARGET_CLASS;

		if (Properties.REGRESSION_DIFFERENT_BRANCHES) {
			//DependencyAnalysis.doJdiff(className);
			//branchIdMap = DependencyAnalysis.branchIdMap;
		}
		// System.exit(0);
		// //////////////////////////////////////////////////////////////////////

		// populate a temp branch distance map with initial data for all
		// branches(if they are not covered, 4 will be considered).
		tempBranchDistanceMap = new HashMap<Integer, Double>();
		int max_branch_value = 4;

		for (Branch b : BranchPool.getInstance(
				TestGenerationContext.getInstance().getClassLoaderForSUT()).getAllBranches()) {
			tempBranchDistanceMap.put(b.getActualBranchId(), 4.0);
		}

		try {
			TestGenerationContext.getInstance().getRegressionClassLoaderForSUT().loadClass(
					Properties.TARGET_CLASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bcFitness = new BranchCoverageSuiteFitness();
		max_branch_fitness_valueO = bcFitness.getMaxValue();
		bcFitnessRegression = new BranchCoverageSuiteFitness(
				TestGenerationContext.getInstance().getRegressionClassLoaderForSUT());
		max_branch_fitness_valueR = bcFitnessRegression.getMaxValue();

		observer = new RegressionExecutionObserver();
		//methodObserver = new RegressionMethodExecutionObserver();

		ExecutionTracer.enableTraceCalls();

	}
	
	protected void executeChangedTestsAndUpdateResults(
			AbstractTestSuiteChromosome<? extends ExecutableChromosome> s) {
		observer.clearPools();
		RegressionTestSuiteChromosome suite = (RegressionTestSuiteChromosome)s;
		for (RegressionTestChromosome chromosome : suite.getTestChromosomes()) {

			observer.off = false;
			observer.requestNewPools();
			observer.regressionFlag(false);

			TestChromosome testChromosome = chromosome.getTheTest();
			TestChromosome otherChromosome = chromosome
					.getTheSameTestForTheOtherClassLoader();
			ClassLoader a = testChromosome.getClass().getClassLoader();
			ClassLoader b = otherChromosome.getClass().getClassLoader();

			// Only execute test if it hasn't been changed
			if (testChromosome.isChanged()
					|| testChromosome.getLastExecutionResult() == null) {

				ExecutionResult result = TestCaseExecutor
						.runTest(testChromosome.getTestCase());
				result.objectPool.addAll(observer.currentObjectMapPool);
				switch (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE) {
				case 3:
				//	methodObserver.regressionFlag(true);
					break;
				case 0:
				default:
					observer.regressionFlag(true);
					break;

				}

				ExecutionResult otherResult = TestCaseExecutor
						.runTest(otherChromosome.getTestCase());
				otherResult.objectPool
						.addAll(observer.currentRegressionObjectMapPool);
				switch (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE) {
				case 3:
					/*methodObserver.regressionFlag(false);
					methodObserver.off = true;*/
					break;
				case 0:
				default:
					observer.regressionFlag(false);
					observer.off = true;
					break;

				}

				if (result != null && otherResult != null) {

					testChromosome.setLastExecutionResult(result);
					testChromosome.setChanged(false);

					otherChromosome.setLastExecutionResult(otherResult);
					otherChromosome.setChanged(false);
				}
			}
		}
	}
	
	/*
	 * Table for name:
	 * 
	 * @ a: all measures
	 * 
	 * @ s: state difference
	 * 
	 * @ b: branch distance
	 * 
	 * @ c: coverage
	 * 
	 * @ o: coverage old
	 * 
	 * @ n: coverage new
	 */
	public boolean useMeasure(char name) {
		boolean flag = false;
		switch (Properties.REGRESSION_USE_FITNESS) {
		case 6:
			if (name == 'c' || name == 'o')
				flag = true;
			break;
		case 5:
			if (name == 'c' || name == 'b' || name == 'o' || name == 'n')
				flag = true;
			break;
		case 4:
			if (name == 'c' || name == 's' || name == 'o' || name == 'n')
				flag = true;
			break;
		case 3:
			if (name == 'b')
				flag = true;
			break;
		case 2:
			if (name == 's')
				flag = true;
			break;
		case 1:
			if (name == 'c' || name == 'o' || name == 'n')
				flag = true;
			break;
		case 0:
		default:
			if (name == 'c' || name == 's' || name == 'b' || name == 'o'
					|| name == 'n')
				flag = true;
			break;

		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.FitnessFunction#getFitness(org.evosuite.ga.Chromosome)
	 */
	@Override
	public double getFitness(
	        AbstractTestSuiteChromosome<? extends ExecutableChromosome> individual) {
		if (useMeasure('s')) {
			switch (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE) {
			case 3:
				//TestCaseExecutor.getInstance().addObserver(methodObserver);
			case 0:
			default:
				TestCaseExecutor.getInstance().addObserver(observer);
				break;

			}
		}
		// TODO Auto-generated method stub
		if (useMeasure('s')) {
			switch (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE) {
			case 3:
				//methodObserver.clearPools();
				break;
			case 0:
			default:
				observer.clearPools();
				break;

			}
		}
		double distance = 0.0;
		double fitness = 0.0;

		// double branchDistance = 0.0;

		// populate branches with a value of 2 (branch not covered yet)
		// branchDistanceMap = new HashMap<Integer, Double>();
		branchDistanceMap = (Map<Integer, Double>) tempBranchDistanceMap
				.clone();

		/*
		 * for (Branch b : BranchPool.getInstance(
		 * TestGenerationContext.getClassLoader()).getAllBranches()) {
		 * logger.warn("branch id " + b.getActualBranchId());
		 * branchDistanceMap.put(b.getActualBranchId(), 2.0); }
		 */

		/*
		 * branchDistanceMap.put(1, 1000.0); branchDistanceMap.put(2, 1000.0);
		 * branchDistanceMap.put(3, 1000.0); branchDistanceMap.put(4, 1000.0);
		 * branchDistanceMap.put(5, 1000.0);
		 */
		// branchDistanceMap.put(6, 1000.0);
		numDifferentExceptions = 0;
		totalExceptions = 0;
		diffTime = 0;

		long startTime = System.nanoTime();
		executeChangedTestsAndUpdateResults(individual);
		RegressionSearchListener.testExecutionTime += System.nanoTime()
				- startTime;

		RegressionTestSuiteChromosome suite = (RegressionTestSuiteChromosome)individual;
		
		for (RegressionTestChromosome regressionTest : suite
				.getTestChromosomes()) {

			ExecutionResult result1 = regressionTest.getTheTest()
					.getLastExecutionResult();

			ExecutionResult result2 = regressionTest
					.getTheSameTestForTheOtherClassLoader()
					.getLastExecutionResult();

			int numExceptionOrig = result1.getNumberOfThrownExceptions();
			int numExceptionReg = result2.getNumberOfThrownExceptions();
			
			long timeExecOrig = result1.getExecutionTime();
			long timeExecReg = result2.getExecutionTime();
			
			double execTimeDiff = Math
					.abs((double) (numExceptionOrig - numExceptionReg));
			if(execTimeDiff>0.003)
				diffTime += execTimeDiff;

			double exDiff = Math
					.abs((double) (numExceptionOrig - numExceptionReg));

			totalExceptions += numExceptionOrig + numExceptionReg;

			numDifferentExceptions += exDiff;

			startTime = System.nanoTime();

			if (useMeasure('b')) {
				this.getBranchDistance(result1.getTrace().getMethodCalls(),
						result2.getTrace().getMethodCalls());
			}
			RegressionSearchListener.branchDistanceTime += System.nanoTime()
					- startTime;

			/*
			 * switch (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE) { case 3:
			 * methodObserver
			 * .addToPools(regressionTest.getOriginalObjectPool(),regressionTest
			 * .getRegressionObjectPool()); break; case 0: default:
			 */
			if (useMeasure('s')) {
				observer.addToPools(result1.objectPool, result2.objectPool);
			}
			/*
			 * break;
			 * 
			 * }
			 */
		}
		firstF = true;


		startTime = System.nanoTime();
		double objectDfitness = 0;
		if (useMeasure('s')) {
			switch (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE) {
			case 3:
				//distance = getTestObjectsDistancePerMethod();
				break;
			case 2:
				//distance = getTestObjectsDistancePerStatement();
				break;
			case 1:
				//distance = getTestObjectsDistancePerSuite();
				break;
			case 0:
			default:
				// logger.warn("" + individual.getTestSuite());
				distance = getTestObjectsDistancePerTestcase();
				break;

			}
			objectDfitness = (1.0 / (1.0 + distance))
					* (max_branch_fitness_valueO + max_branch_fitness_valueR);

		}
		RegressionSearchListener.ObjectDistanceTime += System.nanoTime()
				- startTime;

		startTime = System.nanoTime();
		AbstractTestSuiteChromosome<TestChromosome> testSuiteChromosome = suite
				.getTestSuite();

		AbstractTestSuiteChromosome<TestChromosome> testRegressionSuiteChromosome = null;
		if (useMeasure('n')) {
			testRegressionSuiteChromosome = suite
					.getTestSuiteForTheOtherClassLoader();
		}
		/*
		 * fitness += bcFitness.getFitness(testSuiteChromosome); fitness +=
		 * bcFitnessRegression .getFitness(testRegressionSuiteChromosome);
		 */
		double coverage_old = 0, coverage_new = 0;
		if (useMeasure('o')) {
			coverage_old = bcFitness.getFitness(testSuiteChromosome);
		}
		if (useMeasure('n')) {
			coverage_new = bcFitnessRegression
					.getFitness(testRegressionSuiteChromosome);
		}
		double coverage = coverage_old + coverage_new;

		RegressionSearchListener.coverageTime += System.nanoTime() - startTime;
		// coverage = normalize(coverage);

		// fitness += coverage;
		double branchDfitness = 0;

		double totalBranchDistanceFitness = 0.0;
		if (useMeasure('b')) {
			for (Map.Entry<Integer, Double> br : branchDistanceMap.entrySet()) {
				// logger.warn("branch " + br.getKey() +" value " +
				// br.getValue());
				totalBranchDistanceFitness += br.getValue();
			}

			branchDfitness = totalBranchDistanceFitness;
		}
		// fitness += (1.0 / (1.0 + branchDistance));

		switch (Properties.REGRESSION_USE_FITNESS) {
		case 6:
			fitness += coverage_old;
			break;
		case 5:
			fitness += coverage;
			fitness += branchDfitness;
			break;
		case 4:
			fitness += coverage;
			fitness += objectDfitness;
			break;
		case 3:
			fitness += branchDfitness;
			break;
		case 2:
			fitness += objectDfitness;
			break;
		case 1:
			fitness += coverage;
			break;
		case 0:
		default:
			fitness += coverage;
			fitness += branchDfitness;
			fitness += objectDfitness;
			break;

		}

		double exceptionDistance = (1.0 / (1.0 + numDifferentExceptions));
		// * (max_branch_fitness_valueO + max_branch_fitness_valueR);

		fitness += exceptionDistance;
		
		//fitness += (1.0 / (1.0 + diffTime));

		// double totalExDistance = normalize(totalExceptions) *
		// (max_branch_fitness_valueO + max_branch_fitness_valueR);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		// fitness = normalize(fitness);

		// fitness += 1.0/(1.0+distance);

		// double
		// thebranchcoverageofthefirsttestsuiteandpleasechangethisnamebecauseIonlyusesuchnamestoillustratethingswhenItalktoyou
		// = f.getFitness(testSuiteChromosome);

		// BranchCoverageFactory y = new BranchCoverageFactory();

		// List<BranchCoverageTestFitness> z = y.getCoverageGoals("");

		// fitness = x.getFitness(individual);
		String covered_old = String
				.format("%.2f", bcFitness.totalCovered * 100);
		String covered_new = String.format("%.2f",
				bcFitnessRegression.totalCovered * 100);

		suite.diffExceptions = numDifferentExceptions;
		
		if(RegressionSearchListener.killTheSearch){
			//updateIndividual(individual, 0);
			//return 0;
		}
		
		if(Properties.REGRESSION_ANALYZE){
			if(bcFitness.totalCovered>=0.5 && bcFitnessRegression.totalCovered>=0.5){
				
				RegressionSearchListener.analysisReport = "Coverage: Successful | Orig: " + covered_old + "% | New: " + covered_new + "%";
				//RegressionSearchListener.killTheSearch = true;
			} else {
				RegressionSearchListener.analysisReport = "Coverage: Failed | Orig: " + covered_old + "% | New: " + covered_new + "%";
			}
			

		}
		
		
		
		
		suite.fitnessData = fitness + "," + testSuiteChromosome.size()
				+ "," + testSuiteChromosome.totalLengthOfTestCases() + ","
				+ branchDfitness + "," + objectDfitness + "," + coverage
				+ ",numDifferentExceptions," + totalExceptions + "," + covered_old
				+ "," + covered_new + ","
				+ MaxStatementsStoppingCondition.getNumExecutedStatements();

		suite.objDistance = objectDfitness;

		/*logger.warn("OBJ distance: " + distance + " - fitness:" + fitness
				+ " - branchDistance:" + totalBranchDistanceFitness
				+ " - coverage:" + coverage + " - ex: " + numExceptions
				+ " - tex: " + totalExceptions);*/
		individual
				.setCoverage(this, (bcFitness.totalCovered + bcFitnessRegression.totalCovered) / 2.0);
		updateIndividual(this, individual, fitness);

		if (fitness < bestFitness) {
			bestFitness = fitness;

			// String data
			/*
			 * individual.fitnessData = fitness + "," +
			 * testSuiteChromosome.size() + "," +
			 * testSuiteChromosome.totalLengthOfTestCases() + "," +
			 * branchDfitness + "," + objectDfitness + "," + coverage + "," +
			 * numExceptions + "," + totalExceptions;
			 */
			/*
			 * try {
			 * 
			 * 
			 * FileUtils.writeStringToFile(statsFile, "\r\n" + data, true); }
			 * catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */// logger.warn("Test suite: "+testSuiteChromosome.toString());

			/*
			 * int maxAdds = 0; int totalAdds = 0; for (TestChromosome test :
			 * testSuiteChromosome.getTestChromosomes()) {
			 * Map<VariableReference, Integer> addsSeen = new
			 * HashMap<VariableReference, Integer>(); for (StatementInterface
			 * statement : test.getTestCase()) { if (statement instanceof
			 * MethodStatement) { MethodStatement ms = (MethodStatement)
			 * statement; if (ms.getMethod().getName().equals("withdraw")) {
			 * VariableReference callee = ms.getCallee(); if
			 * (!addsSeen.containsKey(callee)) { addsSeen.put(callee, 1); } else
			 * { addsSeen.put(callee, addsSeen.get(callee) + 1); } } } } for
			 * (Integer i : addsSeen.values()) { totalAdds += i; } if
			 * (!addsSeen.isEmpty()) maxAdds = Math.max(maxAdds,
			 * Collections.max(addsSeen.values())); }
			 */
			logger.warn("Timings so far: Test Execution - "
					+ (RegressionSearchListener.testExecutionTime + 1)
					/ 1000000 + " | Assertion - "
					+ (RegressionSearchListener.assertionTime + 1) / 1000000
					+ " | Coverage - "
					+ (RegressionSearchListener.coverageTime + 1) / 1000000
					+ " | Obj Distance - "
					+ (RegressionSearchListener.ObjectDistanceTime + 1)
					/ 1000000 + " | Branch Distance - "
					+ (RegressionSearchListener.branchDistanceTime + 1)
					/ 1000000 + " | Obj Collection - "
					+ (RegressionSearchListener.odCollectionTime + 1) / 1000000);
			logger.warn("Best Fitness " + fitness + ", number of tests: "
					+ testSuiteChromosome.size() + ", total length: "
					+ testSuiteChromosome.totalLengthOfTestCases() /*
																	 * +
																	 * ", max adds: "
																	 * +maxAdds
																	 * +
																	 * ", total adds: "
																	 * +
																	 * totalAdds
																	 */);
		}


		return fitness;
	}
	
	public void getBranchDistance(List<MethodCall> methodCallsOrig,
			List<MethodCall> methodCallsReg) {

		for (int i = 0, j = 0; i < methodCallsOrig.size()
				&& j < methodCallsReg.size();) {
			MethodCall mO = methodCallsOrig.get(i);
			MethodCall mR = methodCallsReg.get(j);

			if (mO.methodName.equals(mR.methodName)) {
				// logger.warn("mO is mR: " + mO.methodName);

				List<Integer> branchesO = mO.branchTrace;
				List<Integer> branchesR = mR.branchTrace;

				for (int k = 0, l = 0; k < branchesO.size()
						&& l < branchesR.size();) {
					Integer branchO = branchesO.get(k);
					Integer branchR = branchesR.get(l);

					if (Properties.REGRESSION_DIFFERENT_BRANCHES) {

						if (branchIdMap.containsKey(branchO)) {
							branchR = branchIdMap.get(branchO);
						} else {

							if ((branchO == branchR))
								k++;
							l++;
							continue;
						}
					}


					if (branchO == branchR) {

						double trueDisO = normalize(mO.trueDistanceTrace.get(k));
						double trueDisR = normalize(mR.trueDistanceTrace.get(l));

						double falseDisO = normalize(mO.falseDistanceTrace
								.get(k));
						double falseDisR = normalize(mR.falseDistanceTrace
								.get(l));

						double tempBranchDistance = 2.0 * (1 - (Math
								.abs(trueDisO - trueDisR) + Math.abs(falseDisO
								- falseDisR)));
						tempBranchDistance += (Math.abs(trueDisR) + Math
								.abs(falseDisR)) / 2.0;

						tempBranchDistance += (Math.abs(trueDisO) + Math
								.abs(falseDisO)) / 2.0;

						if ((trueDisO == 0 && falseDisR == 0)
								|| (falseDisO == 0 && trueDisR == 0))
							tempBranchDistance = 0;

						if (!branchDistanceMap.containsKey(branchO)
								|| branchDistanceMap.get(branchO) > tempBranchDistance)
							branchDistanceMap.put(branchO, tempBranchDistance);

						k++;
						l++;
						continue;
					} else {
						break;
					}
				}

				i++;
				j++;
				continue;
			} else if (mO.callDepth == 1 && mR.callDepth > 1) {
				j++;
				continue;
			} else if (mR.callDepth == 1 && mO.callDepth > 1) {
				i++;
				continue;
			} else {
				i++;
				j++;
			}
		}


	}
	
	private double getTestObjectsDistancePerTestcase() {

		// logger.warn("original Objects: " + observer.objectMapPool);
		// logger.warn("regression Objects: " +
		// observer.regressionObjectMapPool);

		ObjectDistanceCalculator.different_variables = 0;
		
		double distance = 0.0;
		for (int i = 0; i < observer.objectMapPool.size(); i++) {
			List<Map<Integer, Map<String, Map<String, Object>>>> topmap1 = observer.objectMapPool
					.get(i);
			List<Map<Integer, Map<String, Map<String, Object>>>> topmap2 = observer.regressionObjectMapPool
					.get(i);

			// logger.warn("" + topmap1 + topmap2);

			Map<String, Double> maxClassDistance = new HashMap<String, Double>();

			for (int j = 0; j < topmap1.size(); j++) {
				Map<Integer, Map<String, Map<String, Object>>> map1 = topmap1
						.get(j);

				if (topmap2.size() <= j)
					continue;
				Map<Integer, Map<String, Map<String, Object>>> map2 = topmap2
						.get(j);

				for (Map.Entry<Integer, Map<String, Map<String, Object>>> map1_entry : map1
						.entrySet()) {
					// Map.Entry<Integer, Map<String, Object>> map2_entry =
					// (Entry<Integer, Map<String, Object>>) map2.get(
					// map1_entry.getKey());

					// logger.warn("key: " + map1_entry.getKey());

					Map<String, Map<String, Object>> map1_values = map1_entry
							.getValue();
					Map<String, Map<String, Object>> map2_values = map2
							.get(map1_entry.getKey());
					// logger.warn("" + map1_values + map2_values);
					if (map1_values == null || map2_values == null)
						continue;
					for (Map.Entry<String, Map<String, Object>> internal_map1_entries : map1_values
							.entrySet()) {

						Map<String, Object> map1_value = internal_map1_entries
								.getValue();
						Map<String, Object> map2_value = map2_values
								.get(internal_map1_entries.getKey());
						if (map1_value == null || map2_value == null)
							continue;
						
						double objectDistance = ObjectDistanceCalculator
								.getObjectMapDistance(map1_value, map2_value);
						// logger.warn("oDistance: " + objectDistance);
						// logger.warn("var1: " +map1_value + " | var2: " +
						// map2_value);
						/*if(map1_value.containsKey("fake_var_java_lang_Double") && (Double)map1_value.get("fake_var_java_lang_Double")==0.5){
						logger.warn("Map1: {} | Map2: {} " , map1_value,map2_value);
						
						}*/
						if (!maxClassDistance.containsKey(internal_map1_entries
								.getKey())
								|| (maxClassDistance.get(internal_map1_entries
										.getKey()) < objectDistance))

							maxClassDistance.put(
									internal_map1_entries.getKey(),
									Double.valueOf(objectDistance));
						// logger.warn(internal_map1_entries.getKey() + ": " +
						// map1_value + " --VS-- "+ map2_value);
						/*
						 * 
						 * for (Map.Entry<String, Object> internal_map1_entry :
						 * map1_value .entrySet()) { // Map.Entry<String,
						 * Object> internal_map2_entry = // (Entry<String,
						 * Object>) //
						 * map2_entry.getValue().get(internal_map1_entry
						 * .getKey());
						 * 
						 * Object internal_map1_value = internal_map1_entry
						 * .getValue(); Object itnernal_map2_value = map2_value
						 * .get(internal_map1_entry.getKey());
						 * 
						 * 
						 * logger.warn("Key: " + internal_map1_entry.getKey() +
						 * "= Normal: " + internal_map1_entry.getValue() +
						 * ", Regression: " + itnernal_map2_value + ", type:" +
						 * itnernal_map2_value.getClass());
						 * 
						 * 
						 * double temp_dist = ObjectDistanceCalculator
						 * .getObjectDistance(internal_map1_value,
						 * itnernal_map2_value);
						 * 
						 * if (!maxClassDistance
						 * .containsKey(internal_map1_entries .getKey()) ||
						 * (maxClassDistance .get(internal_map1_entries
						 * .getKey()) < temp_dist))
						 * 
						 * maxClassDistance.put( internal_map1_entries
						 * .getKey(), Double.valueOf(temp_dist)); // temp_dist =
						 * temp_dist; normalize(temp_dist); // fitness +=
						 * temp_dist; // distance += temp_dist;
						 * 
						 * 
						 * if(temp_dist>0) logger.warn("Key: " +
						 * internal_map1_entry.getKey() + "= Normal: " +
						 * internal_map1_entry.getValue() + ", Regression: " +
						 * itnernal_map2_value + ", type:" +
						 * itnernal_map2_value.getClass() + ", distance:" +
						 * temp_dist);
						 * 
						 * 
						 * }
						 */
					}
				}

				// logger.warn("maxClassDistance size:" +
				// maxClassDistance.size() + " > " + entries);

			}
			String entries = "";
			double temp_dis = 0.0;
			for (Map.Entry<String, Double> maxEntry : maxClassDistance
					.entrySet()) {
				temp_dis += maxEntry.getValue();
				/*
				 * entries += maxEntry.getKey().toString() + " : " +
				 * maxEntry.getValue().toString() + " | ";
				 */
			}

			if (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE == 4) {
				temp_dis = Collections.max(maxClassDistance.values());
			}

			if (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE == 5) {
				if (maxClassDistance.size() > 0)
					temp_dis = temp_dis / (maxClassDistance.size());
			}

			if (Properties.REGRESSION_ANALYSIS_OBJECTDISTANCE == 6) {
				temp_dis = Collections.min(maxClassDistance.values());
			}

			distance += temp_dis;
			// logger.warn(entries + " < " + observer.objectMapPool.size());
		}
		// logger.warn("dis is " + distance);
		// return 0;
		distance += ObjectDistanceCalculator.different_variables;
		//if(distance>0)
		//	logger.warn("distance was {}",distance);
		return distance
				/ ((observer.objectMapPool.size() == 0) ? 1
						: observer.objectMapPool.size());

	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.FitnessFunction#isMaximizationFunction()
	 */
	@Override
	public boolean isMaximizationFunction() {
		// TODO Auto-generated method stub
		return false;
	}

}
