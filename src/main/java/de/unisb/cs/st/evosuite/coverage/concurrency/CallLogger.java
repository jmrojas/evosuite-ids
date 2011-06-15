/**
 * 
 */
package de.unisb.cs.st.evosuite.coverage.concurrency;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.coverage.concurrency.ScheduleLogWrapper.callReporter;
import de.unisb.cs.st.evosuite.testcase.StatementInterface;

/**
 * @author Sebastian Steenbuck
 *
 */
public class CallLogger implements callReporter, Scheduler.scheduleObserver {
	private static final Logger logger = Logger.getLogger(CallLogger.class);
	private final Object SYNC = new Object(); //guard for access to the statementToSchedule and threadIdToCurrentStatement, as those might be accessed concurrently
	/*
	 * Maps statements to schedule.
	 * That is if during the execution of a statement 
	 * Each integer represents a position in the schedule list (0 to schedule.size())
	 */
	private final Map<sameWrapper, Set<Integer>> statementToSchedule = new HashMap<sameWrapper, Set<Integer>>();

	/**
	 * Maps threadIDs to the currently executed statement
	 */
	private final Map<Integer, sameWrapper> threadIdToCurrentStatement = new HashMap<Integer, sameWrapper>();

	public CallLogger(){

	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.coverage.concurrency.ScheduleLogWrapper.callReporter#callEnd(de.unisb.cs.st.evosuite.testcase.StatementInterface, java.lang.Integer)
	 */
	@Override
	public void callEnd(StatementInterface caller, Integer threadID) {
		synchronized (SYNC) {
			sameWrapper wrapped = new sameWrapper(caller);
			assert(threadID!=null);
			assert(statementToSchedule.containsKey(wrapped)) : "caller " + caller + " code: " + caller.getCode();
			assert(threadIdToCurrentStatement.containsKey(threadID));
			assert(threadIdToCurrentStatement.get(threadID)!=null);
			threadIdToCurrentStatement.put(threadID, null);
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.coverage.concurrency.ScheduleLogWrapper.callReporter#callStart(de.unisb.cs.st.evosuite.testcase.StatementInterface, java.lang.Integer)
	 */
	@Override
	public void callStart(StatementInterface caller, Integer threadID) {
		synchronized (SYNC) {
			sameWrapper wrapped = new sameWrapper(caller);
			assert(threadID!=null);
			assert(threadIdToCurrentStatement.get(threadID)==null) : "We expected that no statement would be registered for the thread " + threadID + " . But found " + threadIdToCurrentStatement.get(threadID);
			if(!statementToSchedule.containsKey(wrapped)){
				statementToSchedule.put(wrapped, new HashSet<Integer>());
			}
			threadIdToCurrentStatement.put(threadID, wrapped);
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.coverage.concurrency.Scheduler.scheduleObserver#notify(int, int)
	 */
	@Override
	public void notify(int position, Integer threadid) {	
		synchronized (SYNC) {
			assert(threadid!=null);
			Thread.State s = LockRuntime.controller.idToThread.get(threadid).getState();
			assert(s.equals(Thread.State.WAITING)) : ("state : " + LockRuntime.controller.idToThread.get(threadid).getState());
			assert(threadIdToCurrentStatement.containsKey(threadid));
			assert(threadIdToCurrentStatement.get(threadid)!=null) : LockRuntime.controller.idToThread.get(threadid).getState();

			sameWrapper currentStatement = threadIdToCurrentStatement.get(threadid);
			assert(statementToSchedule.get(currentStatement)!=null) : LockRuntime.controller.idToThread.get(threadid).getState(); 
			statementToSchedule.get(currentStatement).add(position);
		}
	}
	
	@Override
	public Set<Integer> getScheduleForStatement(StatementInterface st) {
		assert(st!=null);
		sameWrapper wrapped = new sameWrapper(st);
		Set<Integer> res =  statementToSchedule.get(wrapped);
		if(res==null){
			logger.info("Look up for the schedule points of " + st + " showed that the statement is unkwown " + st.getCode());
			res = new HashSet<Integer>();
		}
		logger.debug("Statement " + st + " was influenced by " + res.size() + " scheduling decisions ");
		return res;
	}
	
	private class sameWrapper {
		public final StatementInterface wrapped;
		public sameWrapper(StatementInterface st){
			assert(st!=null);
			wrapped=st;
		}
		
		@Override
		public boolean equals(Object obj){
			assert(obj!=null);
			assert(obj instanceof sameWrapper) : "We expect a sameWrapper, not an " + obj.getClass();
			if(obj instanceof sameWrapper){
				sameWrapper other = (sameWrapper)obj;
				return wrapped.same(other.wrapped);
			}
			return false;
		}
		
		@Override 
		public int hashCode(){
			return 0; //#TODO this should be more complex ;)
		}
	}

}
