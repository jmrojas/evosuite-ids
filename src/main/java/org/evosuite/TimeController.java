package org.evosuite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.evosuite.Properties.StoppingCondition;
import org.evosuite.Properties.TestFactory;
import org.evosuite.rmi.service.ClientState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is used to keep track of how long EvoSuite has spent
 * in each of its different phases (eg search, minimization, assertion generation).
 * 
 * 
 * <p>
 * TODO: in the long run, this should replace all the occurrences of time handling in EvoSuite
 * 
 * @author arcuri
 *
 */
public class TimeController {

	private static final Logger logger = LoggerFactory.getLogger(TimeController.class);
	
	private static final TimeController singleton = new TimeController();

	/**
	 *  The current state of the client
	 */
	private volatile ClientState state;

	/**
	 * When the client was started
	 */
	private volatile long clientStartTime;

	/**
	 * When the current phase was started
	 */
	private volatile long currentPhaseStartTime;

	/**
	 * Map from ClientState phase (key), to timeout (key)
	 * in milliseconds for that phase
	 */
	private Map<ClientState,Long> phaseTimeouts;

	/**
	 * Map from ClientState phase (key), to how long (key)
	 * in milliseconds EvoSuite was in that phase
	 */
	private Map<ClientState,Long> timeSpentInEachPhase;

	
	/**
	 * Main and only constructor
	 */
	protected TimeController(){
		init();
	}

	private void init() {
		state = ClientState.NOT_STARTED;
		clientStartTime = 0;
		initializePhaseTimeouts();
	}

	public static void resetSingleton(){
		getInstance().init();
	}

	private void initializePhaseTimeouts() {
		if(phaseTimeouts!=null){
			phaseTimeouts.clear();
		} else {
			phaseTimeouts = new ConcurrentHashMap<ClientState,Long>();
		}

		// TODO: I don't understand why, but this may not have happened at this point 
		Properties.getInstance();


		phaseTimeouts.put(ClientState.SEARCH, (Long) 1000l * getSearchBudgetInSeconds());
		phaseTimeouts.put(ClientState.MINIMIZATION, (Long) 1000l * Properties.MINIMIZATION_TIMEOUT);
		phaseTimeouts.put(ClientState.ASSERTION_GENERATION, (Long) 1000l * Properties.ASSERTION_TIMEOUT);
		phaseTimeouts.put(ClientState.CARVING, (Long) 1000l * Properties.CARVING_TIMEOUT);


		if(timeSpentInEachPhase!=null){
			timeSpentInEachPhase.clear();
		} else {
			timeSpentInEachPhase = new ConcurrentHashMap<ClientState,Long>();
		}
	}

	/**
	 * Get the singleton reference
	 * @return
	 */
	public static TimeController getInstance(){
		return singleton;
	}


	public synchronized void updateState(ClientState newState) throws NullPointerException{

		if(state.equals(newState)){
			//no change of state. do nothing
			return;
		}
		
		//first log the current state before changing it
		if(!state.equals(ClientState.NOT_STARTED)){
			long elapsed = System.currentTimeMillis() - currentPhaseStartTime;
			if(timeSpentInEachPhase.containsKey(state)){
				logger.warn("Already entered in phase: "+state+". This will mess up the timing calculations.");
			}
			timeSpentInEachPhase.put(state, elapsed);
			
			logger.debug("Phase "+state+" lasted "+ (elapsed/1000) + " seconds");
		}

		state = newState;
		currentPhaseStartTime = System.currentTimeMillis();

		if(state.equals(ClientState.STARTED)){
			clientStartTime = currentPhaseStartTime;
		}
	}
	
	public static int getSearchBudgetInSeconds(){
		if (Properties.STOPPING_CONDITION == StoppingCondition.MAXTIME) {
			return (int) Properties.SEARCH_BUDGET;
		} else {
			return (int) Properties.GLOBAL_TIMEOUT;
		}
	}

	public int calculateForHowLongClientWillRunInSeconds() {
		int time = Properties.EXTRA_TIMEOUT;

		time += getSearchBudgetInSeconds();

		if (Properties.MINIMIZE) {
			time += Properties.MINIMIZATION_TIMEOUT;
		}
		if (Properties.ASSERTIONS) {
			time += Properties.ASSERTION_TIMEOUT;
		}
		if(Properties.TEST_FACTORY == TestFactory.JUNIT) {
			time += Properties.CARVING_TIMEOUT;
		}
		return time;
	}

	/**
	 * Is there time to execute a test case?
	 * This not only depends on which phase we are in, but
	 * also on how maximum long a test case can be left run
	 * before trying to kill its threads if timeout.
	 * 
	 * @return
	 */
	public synchronized boolean hasTimeToExecuteATestCase(){				
		return isThereStillTimeInThisPhase(Properties.TIMEOUT);
	}

	public synchronized boolean isThereStillTimeInThisPhase(){
		return isThereStillTimeInThisPhase(1); 
	}

	protected boolean isThereStillTimeInThisPhase(long ms){

		if(state.equals(ClientState.NOT_STARTED)){
			return true;
		}

		//all time values are in milliseconds
		long timeSinceStart = System.currentTimeMillis() - clientStartTime;
		long totalTimeLimit = 1000 * calculateForHowLongClientWillRunInSeconds();
		long left = totalTimeLimit - timeSinceStart;

		if(ms > left){
			return false;
		}

		/*
		 * OK, there is enough time, but have we spent too long
		 * in the current phase?
		 */
		if(currentPhaseHasTimeout()){
			long timeoutInMs = getCurrentPhaseTimeout();
			long timeSincePhaseStarted = System.currentTimeMillis() - currentPhaseStartTime;
			long phaseLeft = timeoutInMs - timeSincePhaseStarted;
			logger.info("Time left for current phase "+state+": "+phaseLeft);
			if(ms > phaseLeft){
				return false;
			}
		}

		return true; 
	}

	private long getCurrentPhaseTimeout() {		
		return phaseTimeouts.get(state);
	}

	private boolean currentPhaseHasTimeout() {		
		return phaseTimeouts.containsKey(state);
	}
}
