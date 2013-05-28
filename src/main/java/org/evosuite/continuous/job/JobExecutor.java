package org.evosuite.continuous.job;

import java.util.List;

import org.evosuite.continuous.persistency.StorageManager;

/**
 * Job executor will run EvoSuite on separate processes.
 * There will be no communication with these masters/clients, whose
 * visible side-effects would only be files written on local disk.
 * This does simplify the architecture a lot, especially considering we can 
 * have several instances running in parallel.
 * Furthermore, writing to disk at each search has benefit that we can recover from
 * premature crashes, reboot of machine, etc.
 * This is particularly useful considering that CTG can be left running for hours, if not
 * even days. 
 * Downside is not a big deal, as the searches in a schedule are anyway run independently. 
 * 
 * 
 * <p>
 * Note: under no case ever two different jobs should access the same files at the same time, even
 * if just for reading. Piece-of-crap OS like Windows do put locks on file based
 * on processes accessing them... for multi-process applications running on same host,
 * that is a recipe for disaster... 
 * 
 * @author arcuri
 *
 */
public class JobExecutor {

	public JobExecutor(StorageManager storage, int timeInMinutes, String projectClassPath, int totalMemoryInMB) {
		// TODO Auto-generated constructor stub
	}

	public void executeJobs(List<JobDefinition> jobs){
		//TODO
	}
	
	public void waitForJobs(){
		//TODO
	}
}
