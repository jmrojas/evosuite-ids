/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.evosuite;

import org.evosuite.ga.GeneticAlgorithm;
import org.evosuite.result.TestGenerationResult;
import org.evosuite.result.TestGenerationResultBuilder;
import org.evosuite.rmi.ClientServices;
import org.evosuite.runtime.agent.AgentLoader;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * ClientProcess class.
 * </p>
 * 
 * @author Gordon Fraser
 * @author Andrea Arcuri
 */
public class ClientProcess {

	private static Logger logger = LoggerFactory.getLogger(ClientProcess.class);

	public static TestGenerationResult result;
	
	/**
	 * <p>
	 * run
	 * </p>
	 */
	public void run() {
		Properties.getInstance();
		
		if(Properties.ENABLE_ASSERTS_FOR_EVOSUITE){
			/*
			 * TODO: We load the agent although we do not use it.
			 * Reason is that when we compile the generated test cases to debug
			 * EvoSuite, those will use the agent.
			 * But for some arcane reason, the loading there fails.
			 */
			AgentLoader.loadAgent(); 
		}
		
		LoggingUtils.getEvoLogger().info("* Connecting to master process on port "
		                                         + Properties.PROCESS_COMMUNICATION_PORT);
	
		boolean registered = ClientServices.getInstance().registerServices();
	
		if (!registered) {
			result = TestGenerationResultBuilder.buildErrorResult("Could not connect to master process on port "
                    + Properties.PROCESS_COMMUNICATION_PORT);
			throw new RuntimeException("Could not connect to master process on port "
                    + Properties.PROCESS_COMMUNICATION_PORT);
		}
		
		/*
		 * Now the client node is registered with RMI.
		 * The master will control this node directly.
		 */
		
		ClientServices.getInstance().getClientNode().waitUntilDone();
		ClientServices.getInstance().stopServices();
	}

	/**
	 * <p>
	 * main
	 * </p>
	 * 
	 * @param args
	 *            an array of {@link java.lang.String} objects.
	 */
	public static void main(String[] args) {
		
		/*
		 * important to have it in a variable, otherwise 
		 * might be issues with following System.exit if successive
		 * threads change it if this thread is still running
		 */
		boolean onThread = Properties.CLIENT_ON_THREAD; 
		
		try {
			LoggingUtils.getEvoLogger().info("* Starting client");
			ClientProcess process = new ClientProcess();
			TimeController.resetSingleton();
			process.run();
			if (!onThread) {
				/*
				 * If we we are in debug mode in which we run client on separated thread,
				 * then do not kill the JVM
				 */
				System.exit(0);
			}
		} catch (Throwable t) {
			logger.error("Error when generating tests for: " + Properties.TARGET_CLASS
			        + " with seed " + Randomness.getSeed()+". Configuration id : "+Properties.CONFIGURATION_ID, t);
			t.printStackTrace();

			//sleep 1 sec to be more sure that the above log is recorded
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}

			if (!onThread) {
				System.exit(1);
			}
		}
	}
}
