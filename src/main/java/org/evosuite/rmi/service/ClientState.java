package org.evosuite.rmi.service;

import org.evosuite.Properties;

/**
 * FIXME: sync with ProgressMonitor, and finish set it in all client code
 * 
 * @author arcuri
 *
 */


public enum ClientState {

	NOT_STARTED("Not started", "EvoSuite has not started client process", 0),
	STARTED("Started", "Client process has been launched", 1),
	INITIALIZATION("Initializing", "Analyzing classpath and dependencies", 2),
	SEARCH("Search", "Generating test cases", 3),
	INLINING("Inlining", "Inlining constants", 4),
	MINIMIZING_VALUES("Minimizing values", "Mininizing primitive values in the tests", 5),
	MINIMIZATION("Minimizing", "Minimizing test cases", 6),
	ASSERTION_GENERATION("Generating assertions", "Adding assertions to the test cases", 7),
	WRITING_STATISTICS("Statistics", "Writing statistics to disk", 8),
	WRITING_TESTS("JUnit", "Writing JUnit tests to disk", 9),
	DONE("Done", "EvoSuite is finished", 10);

	private String name;
	private String description;
	private int numPhase = 0;
	private int progress = 0;
	
	private int startProgress = 0;
	private int maxProgress = 0;
	
	ClientState(String name, String description, int numPhase) {
		this.name = name;
		this.description = description;
		this.numPhase = numPhase;
		setProgressBoundaries(numPhase);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getNumPhase() {
		return numPhase;
	}
	
	public int getOverallProgress() {
		return progress;
	}
	
	public int getStartProgress() {
		return startProgress;
	}
	
	public int getEndProgress() {
		return maxProgress;
	}
	
	public int getPhaseProgress() {
		int divisor = maxProgress - startProgress;
		if(divisor == 0)
			return 0;
		return (progress - startProgress) / divisor; 
	}
	
	public void setProgressOfPhase(float progress) {
		this.progress = startProgress + (int)((maxProgress - startProgress) * progress);
	}
	
	
	
	private void setProgressBoundaries(int numPhase) {

		switch(numPhase) {
		case 1: // client started
			progress = 1;
			startProgress = 1;
			maxProgress = 1;
			break;
			
		case 2: // Static analysis started
			progress = 2;
			startProgress = 2;
			maxProgress = 9;
			break;

		case 3: // Search has started
			progress = 10;
			startProgress = 10;
			if(Properties.ASSERTIONS) {
				maxProgress = 33;
			} else {
				maxProgress = 66;
			}
			break;

		case 4: // inlining
			if(Properties.ASSERTIONS) {
				startProgress = 33;
			} else {
				startProgress = 66;				
			}
			maxProgress = startProgress + 5;
			progress = startProgress;
			break;
		case 5: // minimizing values
			if(Properties.ASSERTIONS) {
				startProgress = Properties.INLINE ? 33 + 5 : 33;
			} else {
				startProgress = Properties.INLINE ? 66 + 5 : 66;
			}
			maxProgress = startProgress + 5;
			progress = startProgress;
			break;
			
		case 6: // minimizing tests
			if(Properties.ASSERTIONS) {
				startProgress = Properties.INLINE ? 33 + 5 : 33;
				maxProgress = 66;
			} else {
				startProgress = Properties.INLINE ? 66 + 5 : 66;
				maxProgress = 93;
			}
			if(Properties.MINIMIZE_VALUES) {
				startProgress += 5;
			}
			progress = startProgress;
			break;

		case 7: // generating assertions
			startProgress = 67;				
			maxProgress = 92;
			progress = startProgress;
			break;

		case 8: // writing statistics
			startProgress = 93;
			maxProgress = 94;
			progress = startProgress;
			break;
			
		case 9: // writing tests
			startProgress = 95;				
			maxProgress = 100;
			progress = startProgress;
			break;
		default:
			startProgress = 100;				
			maxProgress = 100;
			progress = 100;
		}
	}

}
