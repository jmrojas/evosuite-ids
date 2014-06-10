package org.evosuite.runtime.agent;

import java.lang.instrument.Instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the entry point for the JavaAgent.
 * This is responsible to instrument the code in
 * the generated JUnit test cases.
 * During EvoSuite search, EvoSuite does not need an agent,
 * as instrumentation can be done at classloader level.
 * 
 * <p>
 * Note: we need JavaAgent in JUnit as the classes could have already
 * been loaded/instrumented, eg as it happens when tools like
 * Emma, Cobertura, Javalanche, etc., are used.
 * 
 * @author arcuri
 *
 */
public class  InstrumentingAgent {

	private static final Logger logger = LoggerFactory.getLogger(InstrumentingAgent.class);

	private static volatile TransformerForTests transformer;

	static{
		try{
			transformer = new TransformerForTests();
		} catch(Exception e){
			logger.error("Failed to initialize TransformerForTests: "+e.getMessage(),e);
			transformer = null;
		}
	}
	
	/**
	 * This is called by JVM when agent starts
	 * @param args
	 * @param inst
	 * @throws Exception
	 */
	public static void premain(String args, Instrumentation inst) throws Exception {
		logger.info("Executing premain of JavaAgent");
		checkTransformerState();
		inst.addTransformer(transformer);
	}

	/**
	 * This is called by JVM when agent starts
	 * @param args
	 * @param inst
	 * @throws Exception
	 */
	public static void agentmain(String args, Instrumentation inst) throws Exception {
		logger.info("Executing agentmain of JavaAgent");
		checkTransformerState();
		inst.addTransformer(transformer);
	}

	private static void checkTransformerState() throws IllegalStateException{
		if(transformer == null){
			String msg = "TransformerForTests was not properly initialized";
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
	}

	/**
	 * Force the dynamic loading of the agent
	 */
	public static void initialize() {
		AgentLoader.loadAgent();
	}

	public static TransformerForTests getTransformer() throws IllegalStateException{
		checkTransformerState();
		return transformer;
	}

	/**
	 * Once loaded, an agent will always read the byte[] 
	 * of the loaded classes. Here we tell it if those byte[]
	 * should be instrumented
	 */
	public static void activate(){
		checkTransformerState();
		transformer.activate();
	}
	
	/**
	 * Stop instrumenting classes
	 */
	public static void deactivate(){
		checkTransformerState();
		transformer.deacitvate();
	}
}