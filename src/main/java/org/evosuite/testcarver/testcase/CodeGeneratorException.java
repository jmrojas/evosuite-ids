package org.evosuite.testcarver.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGeneratorException extends RuntimeException{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeGeneratorException.class);
	
	public CodeGeneratorException(final String msg)
	{
		super(msg);
	}
	
	public CodeGeneratorException(final Throwable e)
	{
		super(e);
	}

	public static void check(final boolean expr, final String msg, final Object...msgArgs)
	throws CodeGeneratorException
	{
		if(! expr)
		{
			final String finalMsg = String.format(msg, msgArgs);
			LOGGER.error(finalMsg);
			throw new CodeGeneratorException(finalMsg);
		}
	}
	
	
	public static void propagateError(final Throwable t, final String msg, final Object...msgArgs)
    throws CodeGeneratorException
	{
		final String finalMsg = String.format(msg, msgArgs);
		
		if (t == null)
		{
			LOGGER.error(msg);
		}
		else
		{
			LOGGER.error(msg, t);
		}
		
		throw new CodeGeneratorException(finalMsg);
	}
	
	public static void propagateError(final String msg, final Object...msgArgs)
    throws CodeGeneratorException
	{
		propagateError(null, msg, msgArgs);
	}
}
