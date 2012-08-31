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
package org.exsyst.ui.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.UIManager;

import org.evosuite.Properties;
import org.evosuite.testcarver.capture.Capturer;
import org.evosuite.testcase.ExecutionResult;
import org.evosuite.testcase.InterfaceTestRunnable;
import org.evosuite.testcase.TestCaseExecutor;
import org.evosuite.testcase.TimeoutHandler;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.exsyst.ui.genetics.ChromosomeUIController;
import org.exsyst.ui.genetics.ReplayChromosomeUIController;
import org.exsyst.ui.genetics.UITestChromosome;
import org.uispec4j.UISpec4J;

import sun.awt.AWTAutoShutdown;

public final class ReplayUITestHelper {
	
	
	private ReplayUITestHelper(){
		
	}
	
	/**
	 * @param args
	 */
	public static void run(UITestChromosome test) {
		
		
		
		final TimeoutHandler<ExecutionResult> handler = new TimeoutHandler<ExecutionResult>();
		final ChromosomeUIController callable = new ChromosomeUIController(test);
		final ExecutorService executor = Executors.newSingleThreadExecutor(TestCaseExecutor.getInstance());

		try 
		{
			System.setProperty("uispec4j.test.library", "junit");
			
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			// Really, really important, otherwise event loops get killed
			// randomly!
			AWTAutoShutdown.getInstance().notifyThreadBusy(Thread.currentThread());
			// Also important to do this very early (here), otherwise we might
			// end up with multiple event loops from loading classes
			UISpec4J.init();
	
//			Properties.TIMEOUT
			handler.execute(callable, executor, Integer.MAX_VALUE, Properties.CPU_TIMEOUT);
		
		}
		catch(final Exception e)
		{
			e.printStackTrace();
			executor.shutdownNow();
			try {
				System.out.println("waiting for termination");
				executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
				System.out.println("terminated");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally
		{
			System.out.println("Finished!");
			AWTAutoShutdown.getInstance().notifyThreadFree(Thread.currentThread());
//			executor.shutdownNow();
		}
	}
}