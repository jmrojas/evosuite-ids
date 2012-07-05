package de.unisb.cs.st.testcarver.agent;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.testcarver.capture.Capturer;
import de.unisb.cs.st.testcarver.configuration.Configuration;
import de.unisb.cs.st.testcarver.exception.CapturerException;
import de.unisb.cs.st.testcarver.instrument.Transformer;


//TODO param for deleting modified bin?
public final class Agent 
{
	// TODO just for convenience during development -> set system properties via command line later on
	private static final Logger LOG = LoggerFactory.getLogger(Agent.class);
	
	private Agent() {}
	
	/**
	 * @param args
	 */
	public static void premain(final String agentArgs, final Instrumentation inst) 
	{
		Configuration.INSTANCE.initLogger();
		
		LOG.debug("starting agent with with args={}", agentArgs);
		try
		{
			try
			{
				// start Capturer if not active yet
				// NOTE: Stopping the capture and saving the corresponding logs is handled in the ShutdownHook
				//       which is automatically initialized in the Capturer

				if(! Capturer.isCapturing())
				{
					Capturer.startCapture(agentArgs);
				}
			}
			catch(CapturerException e)
			{
				LOG.error(e.getMessage(), e);
				System.exit(-1);
			}
			
			final Transformer trans = new Transformer(agentArgs.split("\\s+"));
			
			// install our class transformer which performs the instrumentation
			inst.addTransformer(trans);
		}
		catch(Throwable t)
		{
			LOG.error("an errorr occurred while executing agent (premain)", t);
		}


	}
}
