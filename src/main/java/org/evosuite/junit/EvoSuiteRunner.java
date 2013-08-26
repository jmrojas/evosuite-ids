package org.evosuite.junit;

import org.evosuite.instrumentation.InstrumentingClassLoader;
import org.evosuite.utils.LoggingUtils;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class EvoSuiteRunner extends BlockJUnit4ClassRunner {

	
	public EvoSuiteRunner(Class<?> klass) throws InitializationError {
		super(getFromEvoSuiteClassloader(klass));
	}

	
	private static Class<?> getFromEvoSuiteClassloader(Class<?> clazz) throws InitializationError {
	    try {
	    	/*
	    	 *  properties like REPLACE_CALLS will be set directly in the JUnit files
	    	 */

	    	LoggingUtils.loadLogbackForEvoSuite();
	    	InstrumentingClassLoader classLoader = new InstrumentingClassLoader();
	        return Class.forName(clazz.getName(), true, classLoader);
	    } catch (ClassNotFoundException e) {
	        throw new InitializationError(e);
	    }
	}
	
}
