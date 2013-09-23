package org.evosuite.testcarver.codegen;


import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evosuite.testcarver.capture.CaptureLog;
import org.evosuite.utils.CollectionUtil;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Utils;
import org.slf4j.Logger;



public final class CaptureLogAnalyzer implements ICaptureLogAnalyzer
{
	private static Logger logger = LoggingUtils.getEvoLogger();//LoggerFactory.getLogger(CaptureLogAnalyzer.class);

	@SuppressWarnings("rawtypes")
	@Override
	public void analyze(final CaptureLog originalLog, final ICodeGenerator generator, final Class<?>... observedClasses) 
	{
		this.analyze(originalLog, generator, new HashSet<Class<?>>(), observedClasses);
	}

	@SuppressWarnings("rawtypes")
	public void analyze(final CaptureLog originalLog, final ICodeGenerator generator, final Set<Class<?>> blackList, final Class<?>... observedClasses) 
	{
		if(originalLog == null)
				throw new IllegalArgumentException("captured log must not be null");
		if(generator == null)
			throw new IllegalArgumentException("code generator must not be null");
		if(blackList == null)
			throw new IllegalArgumentException("set containing black listed classes must not be null");
		if(observedClasses == null)
			throw new IllegalArgumentException("array of observed classes must not be null");
		if(observedClasses.length == 0)
			throw new IllegalArgumentException("array of observed classes must not be empty");
		
		final CaptureLog log = originalLog.clone();

		final HashSet<String> observedClassNames = extracObservedClassNames(observedClasses);
		CaptureLogAnalyzerException.check(! CollectionUtil.isNullOrEmpty(observedClassNames), "could not extract class names for ", Arrays.toString(observedClasses));

		final List<Integer> targetOIDs = log.getTargetOIDs(observedClassNames);
		if(targetOIDs.isEmpty())
		{
			logger.warn("could not find any oids for {} -> {} ==> no code is generated\n{}", observedClassNames, Arrays.toString(observedClasses), log);
			return;
		}
		
		final int[] oidExchange = analyzeLog(generator, blackList, log, targetOIDs);		
		postProcessLog(originalLog, generator, blackList, log, oidExchange, observedClasses);
	}

	private void postProcessLog(final CaptureLog originalLog,
			final ICodeGenerator<?> generator, final Set<Class<?>> blackList,
			CaptureLog log, int[] oidExchange,
			final Class<?>... observedClasses) throws RuntimeException {

		if(oidExchange == null){
			generator.after(log);
		} else {
			try
			{
				final Class<?> origClass = this.getClassFromOID(log, oidExchange[0]);
				final Class<?> destClass = this.getClassFromOID(log, oidExchange[1]);

				for(int i = 0; i < observedClasses.length; i++)
				{
					if(origClass.equals(observedClasses[i]))
					{
						observedClasses[i] = destClass;
					}
				}

				generator.clear();
				this.analyze(originalLog, generator, blackList, observedClasses);
			}
			catch(final Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private int[] analyzeLog(final ICodeGenerator<?> generator,
			final Set<Class<?>> blackList, CaptureLog log,
			List<Integer> targetOIDs) {
		//--- 3. step: analyze log

		generator.before(log);

		final int numLogRecords = log.objectIds.size();
		CaptureLogAnalyzerException.check(numLogRecords > 0, "list of captured object ids is empty for log %s", log);
		
		int currentOID    = targetOIDs.get(0);
		int[] oidExchange = null;

		// TODO knowing last logRecNo for termination criterion belonging to an observed instance would prevent processing unnecessary statements
		for(int currentRecord = Math.abs(log.getRecordIndexOfWhereObjectWasInitializedFirst(currentOID)); currentRecord < numLogRecords; currentRecord++)
		//for(int currentRecord = log.getRecordIndex(currentOID); currentRecord < numLogRecords; currentRecord++)	
		{
			
			// currentOID = log.objectIds.get(currentRecord);
			currentOID = log.objectIds.get(currentRecord);

			if(targetOIDs.contains(currentOID) && ! blackList.contains(getClassFromOID(log, currentOID)))
			{
				logger.debug("Analyzing record in position {}", currentRecord);

				oidExchange = this.restorceCodeFromLastPosTo(log, generator, currentOID, currentRecord, blackList);
				if(oidExchange != null){
					break;
				}

				// forward to end of method call sequence
				currentRecord = findEndOfMethod(log, currentRecord, currentOID);

				// each method call is considered as object state modification -> so save last object modification
				/*
				 * FIXME: a log object is got as input to be analyzed, but then in the analyzer there are side effects on it...
				 * need re-factoring
				 */
				log.updateWhereObjectWasInitializedFirst(currentOID, currentRecord);				
			} else {
				logger.debug("Skipping record in position {}",currentRecord);
			}
		}
		return oidExchange;
	}

	

	private HashSet<String> extracObservedClassNames(
			final Class<?>... observedClasses) {
		//--- 1. step: extract class names
		final HashSet<String> observedClassNames = new HashSet<String>();
		for(int i = 0; i < observedClasses.length; i++){
			observedClassNames.add(observedClasses[i].getName());
		}
		return observedClassNames;
	}


	private Class<?> getClassFromOID(final CaptureLog log,  final int oid) {
		try {
			final String typeName = log.getTypeName(oid);
			return this.getClassForName(typeName);
		} catch(final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final Class<?> getClassForName(String type)
	{
		try 
		{
			if( type.equals("boolean"))
			{
				return Boolean.TYPE;
			}
			else if(type.equals("byte"))
			{
				return Byte.TYPE;
			}
			else if( type.equals("char"))
			{
				return Character.TYPE;
			}
			else if( type.equals("double"))
			{
				return Double.TYPE;
			}
			else if(type.equals("float"))
			{
				return Float.TYPE;
			}
			else if(type.equals("int"))
			{
				return Integer.TYPE;
			}
			else if( type.equals("long"))
			{
				return Long.TYPE;
			}
			else if(type.equals("short"))
			{
				return Short.TYPE;
			}
			else if(type.equals("String") ||type.equals("Boolean") ||type.equals("Boolean") || type.equals("Short") ||type.equals("Long") ||
					type.equals("Integer") || type.equals("Float") || type.equals("Double") ||type.equals("Byte") || 
					type.equals("Character") )
			{
				return Class.forName("java.lang." + type);
			}
			else if(type.startsWith("$Proxy")) // FIXME is this approach correct?...
			{
				return Proxy.class;
			}
			
			if(type.endsWith("[]"))
			{
				type = type.replace("[]", "");
				return Class.forName("[L" + type + ";");
			}
			else
			{
				return Class.forName(Utils.getClassNameFromResourcePath(type));
			}
		} 
		catch (final ClassNotFoundException e) 
		{
			CaptureLogAnalyzerException.propagateError(e, "an error occurred while resolving class for type %s", type);
			return null; // just to satisfy compiler
		}
	}

	/**
	 * 
	 * @param log
	 * @param currentRecord
	 * @return -1, if there is no caller (very first method call),
	 * 		caller oid otherwise
	 */
	private int findCaller(final CaptureLog log, final int currentRecord)
	{
		final int numRecords = log.objectIds.size();

		//--- look for the end of the calling method
		int record = currentRecord;
		do
		{
			record = this.findEndOfMethod(log, record, log.objectIds.get(record));
			record++;
		}
		while(  record < numRecords &&
				! log.methodNames.get(record).equals(CaptureLog.END_CAPTURE_PSEUDO_METHOD));  // is not the end of the calling method

		if(record == numRecords)
		{
			// did not find any caller -> must be very first method call
			logger.warn("[currentRecord={}] - could not find caller for currentRecord -> must be very first method call", currentRecord);
			return -1;
		}
		else
		{
			// found caller
			return log.objectIds.get(record);
		}
	}


	private void updateInitRec(final CaptureLog log, final int currentOID, final int currentRecord){
		
		if(currentRecord > log.getRecordIndexOfWhereObjectWasInitializedFirst(currentOID)){
			log.updateWhereObjectWasInitializedFirst(currentOID, currentRecord);
		}
	}


	private int findEndOfMethod(final CaptureLog log, final int currentRecord, final int currentOID)
	{
		final int numRecords = log.objectIds.size();

		int record = currentRecord;

		final int captureId = log.captureIds.get(currentRecord);
		while(   record < numRecords &&
				! (log.objectIds.get(record) == currentOID &&
				log.captureIds.get(record) == captureId && 
				log.methodNames.get(record).equals(CaptureLog.END_CAPTURE_PSEUDO_METHOD)))
		{
			record++;
		}

		return record;
	}

	@SuppressWarnings({ "rawtypes" })
	private void restoreArgs(final Object[] args, final int currentRecord, final CaptureLog log, final ICodeGenerator generator, final Set<Class<?>> blackList)
	{
		Integer oid;
		for(int i = 0; i < args.length; i++)
		{
			// there can only be OIDs or null
			oid = (Integer) args[i];

			if(oid != null)
			{
				this.restorceCodeFromLastPosTo(log, generator, oid, currentRecord, blackList);
			}
		}
	}


	@SuppressWarnings({ "rawtypes" })
	private int[] restorceCodeFromLastPosTo(final CaptureLog log, final ICodeGenerator generator,final int oid, final int end, final Set<Class<?>> blackList){


		// start from last OID modification point
		int currentRecord = log.getRecordIndexOfWhereObjectWasInitializedFirst(oid);
		if(currentRecord > 0){
			// last modification of object happened here
			// -> we start looking for interesting records after retrieved record
			currentRecord++;				
		} else {
			// object new instance statement
			// -> retrieved loc record no is included
			currentRecord = -currentRecord;
		}

		String methodName;
		int    currentOID;
		Object[] methodArgs;
		Integer  methodArgOID;

		Integer returnValue;
		Object returnValueObj;
		int[] exchange;

		for(; currentRecord <= end; currentRecord++) {
			currentOID     = log.objectIds.get(currentRecord);
			returnValueObj = log.returnValues.get(currentRecord);
			returnValue    = returnValueObj.equals(CaptureLog.RETURN_TYPE_VOID) ? -1 : (Integer) returnValueObj;

			if(oid == currentOID ||	returnValue == oid) {

	     		if(oid != currentOID)
				{
					// currentOID differs to the targetOID. this happens if the targetOID appears the first time as return value 
	     			// -> so we have to make sure that currentOID is restored till this position in order to deliver the correct 
	     			//    target instance
	     			exchange = this.restorceCodeFromLastPosTo(log, generator, currentOID, currentRecord, blackList);
					if(exchange != null)
					{
						return exchange;
					}
				}
				
				
				methodName = log.methodNames.get(currentRecord);

				if(CaptureLog.PLAIN_INIT.equals(methodName)) {
					currentRecord = handlePlainInit(log, generator, currentRecord, currentOID);
				}
				else if(CaptureLog.COLLECTION_INIT.equals(methodName)){
					currentRecord = handleCollectionInit(log, generator, blackList, currentRecord, currentOID);
				}
				else if(CaptureLog.MAP_INIT.equals(methodName)){
					currentRecord = handleMapInit(log, generator, blackList, currentRecord, currentOID);
				}
				else if(CaptureLog.ARRAY_INIT.equals(methodName)){
					currentRecord = handleArrayInit(log, generator, blackList, currentRecord, currentOID);
				}
				else if(CaptureLog.NOT_OBSERVED_INIT.equals(methodName)) {
					// e.g. Person var = (Person) XSTREAM.fromXML("<xml/>");
					final int dependencyOID = log.getDependencyOID(oid);

					if(dependencyOID != CaptureLog.NO_DEPENDENCY)
					{
						exchange = this.restorceCodeFromLastPosTo(log, generator, dependencyOID, currentRecord, blackList);
						if(exchange != null)
						{
							return exchange;
						}
					}

					generator.createUnobservedInitStmt(log, currentRecord);
					currentRecord = findEndOfMethod(log, currentRecord, currentOID);
				}
				else if(CaptureLog.PUTFIELD.equals(methodName) || CaptureLog.PUTSTATIC.equals(methodName) || // field write access such as p.id = id or Person.staticVar = "something"
						CaptureLog.GETFIELD.equals(methodName) || CaptureLog.GETSTATIC.equals(methodName))   // field READ access such as "int a =  p.id" or "String var = Person.staticVar"
				{
					final int dependencyOID = log.getDependencyOID(oid);

					if(dependencyOID != CaptureLog.NO_DEPENDENCY)
					{
						exchange = this.restorceCodeFromLastPosTo(log, generator, dependencyOID, currentRecord, blackList);
						if(exchange != null)
						{
							return exchange;
						}
					}

					if(CaptureLog.PUTFIELD.equals(methodName) || CaptureLog.PUTSTATIC.equals(methodName))
					{
						// a field assignment has always one argument
						methodArgs = log.params.get(currentRecord);
						methodArgOID = (Integer) methodArgs[0];
						if(methodArgOID != null && methodArgOID != oid)
						{
							// create history of assigned value
							exchange = this.restorceCodeFromLastPosTo(log, generator, methodArgOID, currentRecord, blackList);
							if(exchange != null) {
								return exchange;
							}
						}

						generator.createFieldWriteAccessStmt(log, currentRecord);

					} else {
						generator.createFieldReadAccessStmt(log, currentRecord);
					}

					currentRecord = findEndOfMethod(log, currentRecord, currentOID);

					if(CaptureLog.GETFIELD.equals(methodName) || CaptureLog.GETSTATIC.equals(methodName))
					{
						// GETFIELD and GETSTATIC should only happen, if we obtain an instance whose creation has not been observed
						this.updateInitRec(log, currentOID, currentRecord);

						if(returnValue != -1) {
							this.updateInitRec(log, returnValue, currentRecord);
						}
					}
				} else {

					//the rest

					// var0.call(someArg) or Person var0 = new Person()
					final int dependencyOID = log.getDependencyOID(oid);

					if(dependencyOID != CaptureLog.NO_DEPENDENCY)
					{
						exchange = this.restorceCodeFromLastPosTo(log, generator, dependencyOID, currentRecord, blackList);
						if(exchange != null)
						{
							return exchange;
						}
					}

					int callerOID = this.findCaller(log, currentRecord);
					methodArgs = log.params.get(currentRecord);

					for(int i = 0; i < methodArgs.length; i++)
					{
						// there can only be OIDs or null
						methodArgOID = (Integer) methodArgs[i];

						//====================================================

						// if method argument is equal to caller oid  look for alternative instance which is restorable
						if(methodArgOID != null && (methodArgOID == callerOID )) {
							int r = currentRecord;
							while(isBlackListed(callerOID, blackList, log)){
								callerOID = this.findCaller(log, ++r);
							}

							// replace class to which the current oid belongs to with callerOID
							blackList.add(this.getClassFromOID(log, oid));

							return new int[]{oid, callerOID};
						}
						else if(methodArgOID != null && isBlackListed(methodArgOID, blackList, log)){
							logger.debug("arg in blacklist >>>> {}", blackList.contains(this.getClassFromOID(log, methodArgOID)));

							return getExchange(log, currentRecord, oid, blackList); //new int[]{oid, callerOID};
						}
						//====================================================

						if(methodArgOID != null && methodArgOID != oid) {
							exchange = this.restorceCodeFromLastPosTo(log, generator, methodArgOID, currentRecord, blackList);
							if(exchange != null) {
								// we can not resolve all dependencies because they rely on other unresolvable object
								blackList.add(this.getClassFromOID(log, oid));
								return exchange;
							}
						}
					}

					// TODO in arbeit
					if(isBlackListed(currentOID, blackList, log)) {
						logger.debug("-> is blacklisted... " + blackList + " oid: " + currentOID + " class: " + getClassFromOID(log, currentOID));

						// we can not resolve all dependencies because they rely on other unresolvable object
						blackList.add(this.getClassFromOID(log, oid));
						return getExchange(log, currentRecord, currentOID, blackList);
					}

					generator.createMethodCallStmt(log, currentRecord);

					// forward to end of method call sequence

					currentRecord = findEndOfMethod(log, currentRecord, currentOID);

					// each method call is considered as object state modification -> so save last object modification
					this.updateInitRec(log, currentOID, currentRecord);

					if(returnValue != -1){
						// if returnValue has not type VOID, mark current log record as record where the return value instance was created
						// --> if an object is created within an observed method, it would not be semantically correct
						//     (and impossible to handle properly) to create an extra instance of the return value type outside this method
						this.updateInitRec(log, returnValue, currentRecord);
					}



					// consider each passed argument as being modified at the end of the method call sequence
					for(int i = 0; i < methodArgs.length; i++){
						// there can only be OIDs or null
						methodArgOID = (Integer) methodArgs[i];

						if(methodArgOID != null && methodArgOID != oid) {
							this.updateInitRec(log, methodArgOID, currentRecord);
						}
					}
				}
			}
		}

		return null;
	}

	
	private int handleArrayInit(final CaptureLog log,
			final ICodeGenerator<?> generator, final Set<Class<?>> blackList,
			int currentRecord, int currentOID) {
		try
		{
			final Object[] methodArgs = log.params.get(currentRecord);
			restoreArgs(methodArgs, currentRecord, log, generator, blackList);
			generator.createArrayInitStmt(log, currentRecord);
			currentRecord = findEndOfMethod(log, currentRecord, currentOID);
			this.updateInitRec(log, currentOID, currentRecord);
			return currentRecord;
		}
		catch(final Exception e)
		{
			CaptureLogAnalyzerException.propagateError(e, "[currentRecord = %s, currentOID = %s, blackList = %s] - an error occurred while creating array init stmt\n%s", currentRecord, currentOID, blackList, log);
			return -1; // just to satisfy compiler
		}

	}

	private int handleMapInit(final CaptureLog log,
			final ICodeGenerator<?> generator, final Set<Class<?>> blackList,
			int currentRecord, int currentOID) {
		try
		{
			final Object[] methodArgs = log.params.get(currentRecord);
			restoreArgs(methodArgs, currentRecord, log, generator, blackList);
			generator.createMapInitStmt(log, currentRecord);
			currentRecord = findEndOfMethod(log, currentRecord, currentOID);
			this.updateInitRec(log, currentOID, currentRecord);
			return currentRecord;
		}
		catch(final Exception e)
		{
			CaptureLogAnalyzerException.propagateError(e, "[currentRecord = %s, currentOID = %s, blackList = %s] - an error occurred while creating map init stmt\n%s", currentRecord, currentOID, blackList, log);
			return -1; // just to satisfy compiler
		}
	}

	private int handleCollectionInit(final CaptureLog log,
			final ICodeGenerator<?> generator, final Set<Class<?>> blackList,
			int currentRecord, int currentOID) {
		
		try
		{
			final Object[] methodArgs = log.params.get(currentRecord);
			restoreArgs(methodArgs, currentRecord, log, generator, blackList);
			generator.createCollectionInitStmt(log, currentRecord);
			currentRecord = findEndOfMethod(log, currentRecord, currentOID);
			this.updateInitRec(log, currentOID, currentRecord);
			return currentRecord;
		}
		catch(final Exception e)
		{
			CaptureLogAnalyzerException.propagateError(e, "[currentRecord = %s, currentOID = %s, blackList = %s] - an error occurred while creating collection init stmt\n%s", currentRecord, currentOID, blackList, log);
			return -1; // just to satisfy compiler
		}
	}

	private int handlePlainInit(final CaptureLog log,
			final ICodeGenerator<?> generator, int currentRecord, int currentOID) {
		// e.g. String var = "Hello World";
		generator.createPlainInitStmt(log, currentRecord);
		currentRecord = findEndOfMethod(log, currentRecord, currentOID);
		this.updateInitRec(log, currentOID, currentRecord);
		return currentRecord;
	}


	private boolean isBlackListed(final int oid, final Set<Class<?>> blackList, final CaptureLog log)
	{
		final String typeName = log.getTypeName(oid);
		if(typeName.contains("$Proxy"))
		{
			return true;
		}
		
		return blackList.contains(this.getClassFromOID(log, oid));
	}

	private int[] getExchange(final CaptureLog log, final int currentRecord, final int oid, final Set<Class<?>> blackList)
	{
		int callerOID;
		int r = currentRecord;

		do 
		{
			callerOID = this.findCaller(log, ++r);
		}
		while(this.isBlackListed(callerOID, blackList, log)); //   blackList.contains(this.getClassFromOID(log, callerOID)));

		blackList.add(this.getClassFromOID(log, oid));

		return new int[]{oid, callerOID};
	}

}