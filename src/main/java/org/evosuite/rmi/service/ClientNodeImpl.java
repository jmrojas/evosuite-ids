package org.evosuite.rmi.service;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.evosuite.ClientProcess;
import org.evosuite.Properties;
import org.evosuite.TestSuiteGenerator;
import org.evosuite.TimeController;
import org.evosuite.classpath.ClassPathHandler;
import org.evosuite.coverage.ClassStatisticsPrinter;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.ga.stoppingconditions.RMIStoppingCondition;
import org.evosuite.junit.CoverageAnalysis;
import org.evosuite.result.TestGenerationResult;
import org.evosuite.result.TestGenerationResultBuilder;
import org.evosuite.sandbox.Sandbox;
import org.evosuite.setup.DependencyAnalysis;
import org.evosuite.setup.TestCluster;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Randomness;
import org.evosuite.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientNodeImpl implements ClientNodeLocal, ClientNodeRemote {

	private static Logger logger = LoggerFactory.getLogger(ClientNodeImpl.class);

	/**
	 * The current state/phase in which this client process is (eg, search or assertion generation)
	 */
	private volatile ClientState state;

	/**
	 * RMI reference used to communicate with the master node
	 */
	private MasterNodeRemote masterNode;

	/**
	 * A unique identifier for this client process (needed if running several clients in parallel)
	 */
	private String clientRmiIdentifier;

	/**
	 * A latch used to wait till the test generation is done
	 */
	protected volatile CountDownLatch doneLatch;

	/**
	 * A latch used to wait for this process to be fully finished
	 */
	protected volatile CountDownLatch finishedLatch;


	protected Registry registry;

	protected final ExecutorService searchExecutor = Executors.newSingleThreadExecutor();

	private final BlockingQueue<OutputVariable> outputVariableQueue = new LinkedBlockingQueue<OutputVariable>();

	private Thread statisticsThread; 

	//only for testing
	protected ClientNodeImpl() {
	}

	public ClientNodeImpl(Registry registry) {
		this.registry = registry;
		state = ClientState.NOT_STARTED;
		/*
		 * TODO: for now it is a constant because we have only one client
		 */
		clientRmiIdentifier = "ClientNode";
		doneLatch = new CountDownLatch(1);
		finishedLatch = new CountDownLatch(1);
	}

	private static class OutputVariable {
		public RuntimeVariable variable;
		public Object value;

		public OutputVariable(RuntimeVariable variable, Object value) {
			super();
			this.variable = variable;
			this.value = value;
		}
	}

	@Override
	public void startNewSearch() throws RemoteException, IllegalStateException {
		if (!state.equals(ClientState.NOT_STARTED)) {
			throw new IllegalArgumentException("Search has already been started");
		}

		/*
		 * Needs to be done on separated thread, otherwise the master will block on this
		 * function call until end of the search, even if it is on remote process
		 */
		searchExecutor.submit(new Runnable() {
			@Override
			public void run() {
				changeState(ClientState.STARTED);

				//Before starting search, let's activate the sandbox
				if (Properties.SANDBOX) {
					Sandbox.initializeSecurityManagerForSUT();
				}
				TestGenerationResult result;

				try {
					// Starting a new search
					TestSuiteGenerator generator = new TestSuiteGenerator();
					result = generator.generateTestSuite();
					GeneticAlgorithm<?> ga = generator.getEmployedGeneticAlgorithm();

					if (Properties.CLIENT_ON_THREAD) {
						/*
						 * this is done when the client is run on same JVM, to avoid
						 * problems of serializing ga
						 */
						ClientProcess.geneticAlgorithmStatus = ga;
					}
					masterNode.evosuite_collectTestGenerationResult(clientRmiIdentifier, result);
				} catch (Throwable t) {
					logger.error("Error when generating tests for: "
							+ Properties.TARGET_CLASS + " with seed "
							+ Randomness.getSeed() + ". Configuration id : "
							+ Properties.CONFIGURATION_ID, t);
					result = TestGenerationResultBuilder.buildErrorResult("Error when generating tests for: "
							+ Properties.TARGET_CLASS+": "+t); 
				}

				changeState(ClientState.DONE);

				if (Properties.SANDBOX) {
					/*
					 * Note: this is mainly done for debugging purposes, to simplify how test cases are run/written 
					 */
					Sandbox.resetDefaultSecurityManager();
				}

				/*
				 * System is special due to the handling of properties
				 * 
				 *  TODO: re-add it once we save JUnit code in the 
				 *  best individual. Otherwise, we wouldn't
				 *  be able to properly create the JUnit files in the
				 *  system test cases after the search
				 */
				//org.evosuite.runtime.System.fullReset();
			}
		});
	}

	@Override
	public void cancelCurrentSearch() throws RemoteException {
		if (this.state == ClientState.INITIALIZATION)
			System.exit(1);
		//LoggingUtils.getEvoLogger().info("Cancelling client");
		RMIStoppingCondition.getInstance().stop();

	}

	@Override
	public boolean waitUntilFinished(long timeoutInMs) throws RemoteException,
	InterruptedException {
		return finishedLatch.await(timeoutInMs, TimeUnit.MILLISECONDS);
	}

	@Override
	public void waitUntilDone() {
		try {
			doneLatch.await();
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void changeState(ClientState state) {
		changeState(state, new ClientStateInformation(state));
	}

	@Override
	public void changeState(ClientState state, ClientStateInformation information) {
		if (this.state != state){
			logger.info("Client changing state from " + this.state + " to " + state);
		}

		this.state = state;

		TimeController.getInstance().updateState(state);

		try {
			masterNode.evosuite_informChangeOfStateInClient(clientRmiIdentifier, state,information);
		} catch (RemoteException e) {
			logger.error("Cannot inform master of change of state", e);
		}

		if (this.state.equals(ClientState.DONE)) {
			doneLatch.countDown();
		}

		if (this.state.equals(ClientState.FINISHED)) {
			finishedLatch.countDown();
		}
	}

	@Override
	public void updateStatistics(Chromosome individual) {
		logger.info("Sending current best individual to master process");

		try {
			masterNode.evosuite_collectStatistics(clientRmiIdentifier, individual);
		} catch (RemoteException e) {
			logger.error("Cannot inform master of change of state", e);
		}
	}

	@Override
	public void trackOutputVariable(RuntimeVariable variable, Object value) {
		logger.info("Sending output variable to master process: "+variable+" = "+value);

		/*
		 * As this code might be called from unsafe blocks, we just put the values
		 * on a queue, and have a privileged thread doing the RMI connection to master
		 */
		outputVariableQueue.offer(new OutputVariable(variable, value));
	}

	public void stop(){
		if(statisticsThread!=null){
			statisticsThread.interrupt();
			List<OutputVariable> vars = new ArrayList<OutputVariable>();
			outputVariableQueue.drainTo(vars);
			for(OutputVariable ov : vars) {
				try {
					masterNode.evosuite_collectStatistics(clientRmiIdentifier, ov.variable, ov.value);
				} catch (RemoteException e) {
					logger.error("Error when exporting statistics: "+ov.variable+"="+ov.value, e);
					break;
				}
			}

			try {
				statisticsThread.join(3000);
			} catch (InterruptedException e) {
				logger.error("Failed to stop statisticsThread in time");
			}
			statisticsThread = null;
		}

		changeState(ClientState.FINISHED);
	}

	@Override
	public boolean init() {
		try {
			masterNode = (MasterNodeRemote) registry.lookup(MasterNodeRemote.RMI_SERVICE_NAME);
			masterNode.evosuite_registerClientNode(clientRmiIdentifier);
			masterNode.evosuite_informChangeOfStateInClient(clientRmiIdentifier, state,
					new ClientStateInformation(state));

			statisticsThread = new Thread() {
				@Override
				public void run() {
					while (!this.isInterrupted()) {
						OutputVariable ov = null;
						try {
							ov = outputVariableQueue.take(); //this is blocking
							masterNode.evosuite_collectStatistics(clientRmiIdentifier, ov.variable, ov.value);
						} catch (InterruptedException e) {
							break;
						} catch (RemoteException e) {
							logger.error("Error when exporting statistics: "+ov.variable+"="+ov.value, e);
							break;
						}
					}
				}
			};
			Sandbox.addPriviligedThread(statisticsThread);
			statisticsThread.start();

		} catch (Exception e) {
			logger.error("Error when connecting to master via RMI", e);
			return false;
		}
		return true;
	}

	public String getClientRmiIdentifier() {
		return clientRmiIdentifier;
	}

	@Override
	public void doCoverageAnalysis() throws RemoteException {
		if (!state.equals(ClientState.NOT_STARTED)) {
			throw new IllegalArgumentException("Search has already been started");
		}

		/*
		 * Needs to be done on separated thread, otherwise the master will block on this
		 * function call until end of the search, even if it is on remote process
		 */
		searchExecutor.submit(new Runnable() {
			@Override
			public void run() {
				changeState(ClientState.STARTED);
				//Before starting search, let's activate the sandbox
				if (Properties.SANDBOX) {
					Sandbox.initializeSecurityManagerForSUT();
				}

				try {
					CoverageAnalysis.analyzeCoverage();

				} catch (Throwable t) {
					logger.error("Error when analysing coverage for: "
							+ Properties.TARGET_CLASS + " with seed "
							+ Randomness.getSeed() + ". Configuration id : "
							+ Properties.CONFIGURATION_ID, t);
				}

				changeState(ClientState.DONE);
				if (Properties.SANDBOX) {
					/*
					 * Note: this is mainly done for debugging purposes, to simplify how test cases are run/written 
					 */
					Sandbox.resetDefaultSecurityManager();
				}

			}
		});
	}

	@Override
	public void doDependencyAnalysis(final String fileName) throws RemoteException {
		if (!state.equals(ClientState.NOT_STARTED)) {
			throw new IllegalArgumentException("Search has already been started");
		}

		/*
		 * Needs to be done on separated thread, otherwise the master will block on this
		 * function call until end of the search, even if it is on remote process
		 */
		searchExecutor.submit(new Runnable() {
			@Override
			public void run() {
				changeState(ClientState.STARTED);
				Sandbox.goingToExecuteSUTCode();
				Sandbox.goingToExecuteUnsafeCodeOnSameThread();

				try {
					LoggingUtils.getEvoLogger().info("* Analyzing classpath");
					DependencyAnalysis.analyze(Properties.TARGET_CLASS,
							Arrays.asList(ClassPathHandler.getInstance().getClassPathElementsForTargetProject()));
					StringBuffer fileNames = new StringBuffer();
					for(Class<?> clazz : TestCluster.getInstance().getAnalyzedClasses()) {
						fileNames.append(clazz.getName());
						fileNames.append("\n");
					}
					LoggingUtils.getEvoLogger().info("* Writing class dependencies to file "+fileName);
					Utils.writeFile(fileNames.toString(), fileName);
				} catch (Throwable t) {
					logger.error("Error when analysing coverage for: "
							+ Properties.TARGET_CLASS + " with seed "
							+ Randomness.getSeed() + ". Configuration id : "
							+ Properties.CONFIGURATION_ID, t);
				} finally {
					Sandbox.doneWithExecutingUnsafeCodeOnSameThread();
					Sandbox.doneWithExecutingSUTCode();
				}

				changeState(ClientState.DONE);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.evosuite.rmi.service.ClientNodeRemote#printClassStatistics()
	 */
	@Override
	public void printClassStatistics() throws RemoteException {
		if (!state.equals(ClientState.NOT_STARTED)) {
			throw new IllegalArgumentException("Search has already been started");
		}

		/*
		 * Needs to be done on separated thread, otherwise the master will block on this
		 * function call until end of the search, even if it is on remote process
		 */
		searchExecutor.submit(new Runnable() {
			@Override
			public void run() {
				changeState(ClientState.STARTED);
				if (Properties.SANDBOX) {
					Sandbox.initializeSecurityManagerForSUT();
				}

				try {
					ClassStatisticsPrinter.printClassStatistics();

				} catch (Throwable t) {
					logger.error("Error when analysing coverage for: "
							+ Properties.TARGET_CLASS + " with seed "
							+ Randomness.getSeed() + ". Configuration id : "
							+ Properties.CONFIGURATION_ID, t);
				}

				changeState(ClientState.DONE);
			}
		});
	}

}
