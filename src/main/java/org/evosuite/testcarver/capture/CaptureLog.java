package org.evosuite.testcarver.capture;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;


public final class CaptureLog implements Cloneable {

	//=============   static, final fields ===================================================

	private static final Logger logger = LoggerFactory.getLogger(CaptureLog.class);

	public static final Object[] NO_ARGS           = new Object[0];
	public static final String   OBSERVED_INIT     = "<init>";
	public static final String   PLAIN_INIT        = CaptureLog.class.getName() + ".PLAIN";
	public static final String   COLLECTION_INIT = CaptureLog.class.getName() + ".COLLECTION";
	public static final String   MAP_INIT = CaptureLog.class.getName() + ".MAP";
	public static final String   ARRAY_INIT = CaptureLog.class.getName() + ".ARRAY";

	public static final String   NOT_OBSERVED_INIT = CaptureLog.class.getName() + ".XINIT";

	public static final String END_CAPTURE_PSEUDO_METHOD =  CaptureLog.class.getName() + ".END_CAPTURE";
	public static final int    PSEUDO_CAPTURE_ID		 = Integer.MAX_VALUE; // for internally created statement (PLAIN_INIT and NOT_OBSERVED_INIT)

	public static final String EMPTY_DESC = Type.getMethodDescriptor(Type.VOID_TYPE, new Type[]{});
	public static final int  NO_DEPENDENCY = -1;

	public static final String PUTFIELD  = "PUTFIELD";
	public static final String PUTSTATIC = "PUTSTATIC";
	public static final String GETFIELD  = "GETFIELD";
	public static final String GETSTATIC = "GETSTATIC";

	public static final Object RETURN_TYPE_VOID = CaptureLog.class.getName() + ".RETURN_VOID";

	private static final Set<String>  NOT_OBSERVED_INIT_METHODS = Collections.synchronizedSet(new HashSet<String>());
	static
	{
		NOT_OBSERVED_INIT_METHODS.add(NOT_OBSERVED_INIT);
		NOT_OBSERVED_INIT_METHODS.add(COLLECTION_INIT);
		NOT_OBSERVED_INIT_METHODS.add(MAP_INIT);
		NOT_OBSERVED_INIT_METHODS.add(ARRAY_INIT);
	}


	//=============   local, object fields ===================================================

	/*
	 * FIXME: the design of this class breaks OO encapsulation. 
	 * Fields are declared 'final', but their content can be accessed/changed from outside.
	 * Need re-factoring.
	 * 
	 * For example, are these lists supposed to have same length? (ie invariant)
	 */

	//--- LOG Table
	// REC_NO | OID | METHOD | PARAMS

	/*
	 * FIXME: following lists seem to be aligned
	 */

	// rec_no is implied by index
	public final List<Integer>       objectIds;
	public final List<Integer>       captureIds;
	public final ArrayList<String>   methodNames;
	/**
	 * FIXME: this seems always containing Integer objects, representing either null
	 * or an object identifier (oid). should it be <Integer[]> ?
	 */
	public final ArrayList<Object[]> params;
	public final ArrayList<Object>   returnValues;
	public final ArrayList<Boolean>  isStaticCallList;
	public final ArrayList<String>   descList;


	//--- OID Info Table
	// OID | INIT_REC_NO | CLASS

	/*
	 * FIXME: the following lists seem to be aligned.
	 * Would be better to have a single list, with object
	 * containing the different fields
	 */	
	private final List<Integer> 	   oids;

	private final List<Integer> 	   oidInitRecNo;

	private final ArrayList<String> oidClassNames;

	private final List<Integer>     oidFirstInits;

	private final List<Integer>  oidDependencies;

	/**
	 *  captureId -> field name
	 */
	private final Map<Integer, String> oidNamesOfAccessedFields;

	/**
	 *  oid -> index ==> oidInitReco.get(index) + oidClassNames.get(index)
	 */
	private final Map<Integer, Integer>    oidRecMapping; 


	private final XStream xstream;



	/**
	 * Main constructor
	 */
	public CaptureLog(){	
		this.objectIds      = new ArrayList<Integer>();
		this.methodNames    = new ArrayList<String>();
		this.params         = new ArrayList<Object[]>();
		this.captureIds     = new ArrayList<Integer>();
		this.returnValues   = new ArrayList<Object>();
		this.descList       = new ArrayList<String>();


		this.oidRecMapping = new HashMap<Integer, Integer>();
		this.oidInitRecNo  = new ArrayList<Integer>();
		this.oidClassNames = new ArrayList<String>();
		this.oids          = new ArrayList<Integer>();
		this.oidFirstInits = new ArrayList<Integer>();
		this.oidDependencies = new ArrayList<Integer>();

		this.isStaticCallList = new ArrayList<Boolean>();

		this.oidNamesOfAccessedFields = new HashMap<Integer, String>();

		this.xstream = new XStream();
	}

	public String getNameOfAccessedFields(int captureId){
		return oidNamesOfAccessedFields.get(captureId);
	}

	public int getDependencyOID(int oid){
		int index = getRecordIndex(oid);
		return oidDependencies.get(index);
	}

	public List<Integer> getTargetOIDs(final HashSet<String> observedClassNames) {
		final List<Integer> targetOIDs = new ArrayList<Integer>();
		final int numInfoRecs = oidClassNames.size();
		for(int i = 0; i < numInfoRecs; i++) {
			if(observedClassNames.contains(oidClassNames.get(i))){
				targetOIDs.add(getOID(i));
			}
		}
		return targetOIDs;
	}

	public String getTypeName(int oid) throws IllegalArgumentException{
		if(! oidRecMapping.containsKey(oid)){
			throw new IllegalArgumentException("OID "+oid+" is not recognized");
		}
		return oidClassNames.get(getRecordIndex(oid));
	}

	public int getRecordIndex(int oid){
		return oidRecMapping.get(oid);
	}

	public int getOID(int recordIndex){
		if(recordIndex<0 || recordIndex>=oids.size()){
			throw new IllegalArgumentException("index "+recordIndex+" is invalid as there are "+oids.size()+" OIDs");
		}
		return oids.get(recordIndex);
	}

	public int getRecordIndexOfWhereObjectWasInitializedFirst(int oid) throws IllegalArgumentException{
		if(!oidRecMapping.containsKey(oid)){
			throw new IllegalArgumentException("OID "+oid+" is not recognized");
		}

		int pos = oidRecMapping.get(oid);
		return oidInitRecNo.get(pos);
	}

	/**
	 * FIXME: this does not make sense... it seems like oidInitRecNo contains
	 * integers that have different meaning depending on whether their are positive or not...
	 * 
	 * @param currentRecord
	 */
	private void addNewInitRec(int currentRecord) {
		// negative log rec no indicates obj construction
		this.oidInitRecNo.add(-currentRecord);
		logger.debug("InitRecNo added "+(-currentRecord));
	}

	public void updateWhereObjectWasInitializedFirst(int oid, int recordIndex) throws IllegalArgumentException{
		if(!oidRecMapping.containsKey(oid)){
			throw new IllegalArgumentException("OID "+oid+" is not recognized");
		}
		int nRec = objectIds.size();
		/*
		 * FIXME: it seems negative indexes have special meaning...
		 */
		if(-recordIndex<= -nRec || recordIndex >= nRec){
			throw new IllegalArgumentException("New record index "+recordIndex+" is invalid, as there are only "+nRec+" records");
		}

		logger.debug("Updating init of OID "+oid+" from pos="+getRecordIndexOfWhereObjectWasInitializedFirst(oid)+" to pos="+recordIndex);

		
		// Only update init record if its number is bigger than the current init record number
		// Note that record numbers indicating fist object occurrence are marked as negative number
		// For example: constructor call at record no 8 becomes -8
		final int recentInitRecord = getRecordIndexOfWhereObjectWasInitializedFirst(oid);
		if(Math.abs(recordIndex) > Math.abs(recentInitRecord))
		{
			oidInitRecNo.set(oidRecMapping.get(oid), recordIndex);
		}
	}

	@Override
	public CaptureLog clone()
	{
		final CaptureLog log = new CaptureLog();

		log.objectIds.addAll(this.objectIds);
		log.methodNames.addAll(this.methodNames);
		log.params.addAll(this.params);
		log.captureIds.addAll(this.captureIds);
		log.returnValues.addAll(this.returnValues);
		log.descList.addAll(this.descList);


		log.oidRecMapping.putAll(this.oidRecMapping);
		log.oidInitRecNo.addAll(this.oidInitRecNo);
		log.oidClassNames.addAll(this.oidClassNames);
		log.oids.addAll(this.oids);
		log.oidNamesOfAccessedFields.putAll(this.oidNamesOfAccessedFields);
		log.isStaticCallList.addAll(this.isStaticCallList);
		log.oidDependencies.addAll(this.oidDependencies);
		log.oidFirstInits.addAll(this.oidFirstInits);

		return log;
	}


	public void clear()
	{
		this.objectIds.clear();
		this.methodNames.clear();
		this.params.clear();
		this.captureIds.clear();
		this.returnValues.clear();
		this.descList.clear();

		this.oidRecMapping.clear();
		this.oidInitRecNo.clear();
		this.oidClassNames.clear();
		this.oids.clear();
		this.oidFirstInits.clear();
		this.oidDependencies.clear();

		this.oidNamesOfAccessedFields.clear();
	}


	private boolean updateInfoTable(final int oid, final Object receiver, final boolean replace)
	{
		// update oid info table, if necessary
		// -> we assume that USUALLY the first record belonging to an object belongs to its instanciation
		if(this.oidRecMapping.containsKey(oid)){
			if(replace){
				final int logRecNo  = this.objectIds.size();
				updateWhereObjectWasInitializedFirst(oid,-logRecNo);
				return true;
			} else {
				return false;
			}
		} else {
			final int logRecNo  = this.objectIds.size();
			final int infoRecNo = this.oidInitRecNo.size();

			logger.debug("Adding mapping oid->index "+oid+"->"+infoRecNo);
			this.oidRecMapping.put(oid, infoRecNo);
			addNewInitRec(logRecNo);

			oidFirstInits.add(logRecNo);

			oidDependencies.add(NO_DEPENDENCY);

			if(receiver instanceof Class) //this can only happen, if there is a static method call 
			{
				final Class<?> c = (Class<?>) receiver;
				this.oidClassNames.add(c.getName());//.replaceFirst("\\$\\d+$", ""));
			}
			else if(this.isPlain(receiver))
			{
				// we don't need fully qualified name for plain types
				this.oidClassNames.add(receiver.getClass().getSimpleName());//.replaceFirst("\\$\\d+$", ""));
			}
			else
			{
				this.oidClassNames.add(receiver.getClass().getName());//.replaceFirst("\\$\\d+$", ""));
			}


			this.oids.add(oid);

			return true;
		}
	}


	private boolean isPlain(final Object o)
	{
		return //o instanceof Class   ||
				o instanceof String  ||
				o instanceof Integer || 
				o instanceof Double  || 
				o instanceof Float   || 
				o instanceof Long    ||
				o instanceof Byte    ||
				o instanceof Short   ||
				o instanceof Boolean ||
				o instanceof Character;
	}

	/**
	 * if there is an return value and the return value creation has not been logged before 
	 * (may happen, if, for example, the constructor is private), save the information that 
	 * the value comes from a finished method call
	 * 
	 * @param captureId
	 * @param receiver
	 * @param returnValue
	 */
	public void logEnd(final int captureId, final Object receiver, final Object returnValue)
	{
		if(returnValue != null && returnValue != RETURN_TYPE_VOID) { 			
			handleReturnValue(captureId, receiver, returnValue);
		}

		this.captureIds.add(captureId);
		this.objectIds.add(System.identityHashCode(receiver));
		this.methodNames.add(END_CAPTURE_PSEUDO_METHOD);
		this.descList.add(EMPTY_DESC);
		this.params.add(NO_ARGS);
		this.returnValues.add(RETURN_TYPE_VOID);
		this.isStaticCallList.add(Boolean.FALSE);
	}

	private void handleReturnValue(final int captureId, final Object receiver,
			final Object returnValue) {
		final int returnValueOID = System.identityHashCode(returnValue);

		boolean condition = ! this.oidRecMapping.containsKey(returnValueOID);

		if(!condition){		
			final int firstInitRecNo = this.oidFirstInits.get(this.oidRecMapping.get(returnValueOID));

			// was an accessible constructor call belonging to this object logged before?		
			condition = (! methodNames.get(firstInitRecNo).equals(OBSERVED_INIT) )&&
					(!    NOT_OBSERVED_INIT_METHODS.contains(methodNames.get(firstInitRecNo)) ) &&
					RETURN_TYPE_VOID .equals(returnValues.get(firstInitRecNo));
		}

		if(condition){
			if(! isPlain(returnValue) && ! (returnValue instanceof Class)) {
				final int oid = System.identityHashCode(receiver);

				int currentRecord = captureIds.size() - 1;

				int nestedCalls = 0;
				while(true){
					if(this.captureIds.get(currentRecord) == captureId &&
							this.objectIds.get(currentRecord)  == oid){
						if(this.methodNames.get(currentRecord).equals(END_CAPTURE_PSEUDO_METHOD)){
							nestedCalls++;
						} else{
							if(nestedCalls == 0){
								break;
							} else {
								nestedCalls--;
							}
						}
					}
					currentRecord--;
				}


				if(this.oidRecMapping.containsKey(returnValueOID)){
					final int infoRecNo = this.oidRecMapping.get(returnValueOID);						
					final int initRecNo = getRecordIndexOfWhereObjectWasInitializedFirst(returnValueOID);
					final String method = this.methodNames.get(Math.abs(initRecNo));

					if(! OBSERVED_INIT.equals(method) && ! NOT_OBSERVED_INIT_METHODS.contains(method))
					{
						this.returnValues.set(currentRecord, returnValueOID); // oid as integer works here as we exclude plain values
						updateWhereObjectWasInitializedFirst(returnValueOID, -currentRecord);							
						this.oidFirstInits.set(infoRecNo, currentRecord);
					}
				} else {
					final int infoRecNo = this.oidInitRecNo.size();
					this.oidRecMapping.put(returnValueOID, infoRecNo);
					addNewInitRec(currentRecord);
					this.oidFirstInits.add(currentRecord);

					this.returnValues.set(currentRecord, returnValueOID); // oid as integer works here as we exclude plain values

					this.oidClassNames.add(returnValue.getClass().getName());
					this.oids.add(returnValueOID);
					this.oidDependencies.add(NO_DEPENDENCY);
				}
			}
		}
	}


	/**
	 * For example:
	 * 
	 * 		public class Foo
	 *      {
	 *      	public class Bar(){}
	 *      }
	 *      
	 * 
	 * 
	 * @param receiver
	 */
	private void checkIfInstanceFromInnerInstanceClass(final Object receiver)
	{
		if(! (receiver instanceof Class))
		{
			final Class<?> receiverClass  = receiver.getClass();
			final Class<?> enclosingClass = receiverClass.getEnclosingClass();
			if(enclosingClass == null)
			{
				// do nothing
				return;
			}
			else
			{
				if( ! receiverClass.isAnonymousClass() && ! Modifier.isStatic(receiverClass.getModifiers()))
				{
					try 
					{
						/*
						 * The bytecode of the Outer$Inner class will contain a package-scoped field named this$0 of type Outer. 
						 * That's how non-static inner classes are implemented in Java, because at bytecode level there is no concept of an inner class.
						 *
						 * see http://stackoverflow.com/questions/763543/in-java-how-do-i-access-the-outer-class-when-im-not-in-the-inner-class
						 * for further details
						 */
						final Field  this$0  = receiverClass.getDeclaredField("this$0");
						this$0.setAccessible(true);
						final Object outerInstance = this$0.get(receiver);

						// the enclosing object has to b

						final int receiverOID    = System.identityHashCode(receiver);
						final int initRecNo = this.oidRecMapping.get(receiverOID);
						this.oidDependencies.set(initRecNo, System.identityHashCode(outerInstance));
					} 
					catch (final Exception e) {
						logger.warn("An error occurred while obtaining the enclosing object of an inner non-static class instance. "+
								"FIELDS: " + Arrays.toString(receiverClass.getDeclaredFields()), e);
					} 
				}
			}
		}

	}


	public void log(final int captureId, final Object receiver, final String methodName, final String methodDesc, Object...methodParams)
	{
		// TODO find nicer way
		if(PUTSTATIC.equals(methodName) || PUTFIELD.equals(methodName))
		{
			/*
			 * The first param always specifies the name of the accessed field.
			 * The second param represents the actual value.
			 */
			this.oidNamesOfAccessedFields.put(captureId, (String) methodParams[0]);
			final Object assignedValue = methodParams[1];
			methodParams               = new Object[1];
			methodParams[0]            = assignedValue; 
		}
		else if(GETSTATIC.equals(methodName) || GETFIELD.equals(methodName))
		{
			/*
			 * The param always specifies the name of the accessed field.
			 */
			this.oidNamesOfAccessedFields.put(captureId, (String) methodParams[0]);
			methodParams = new Object[0];
		}

		final int oid = System.identityHashCode(receiver);

		// update info table if necessary
		// in case of constructor calls, we want to remember the last one
		final boolean replace = OBSERVED_INIT.equals(methodName);
		this.updateInfoTable(oid, receiver, replace);	

		// save receiver class -> might be reference in later calls e.g. doSth(Person.class)
		if(receiver instanceof Class) {
			this.objectIds.add(oid);
			this.descList.add(EMPTY_DESC);
			this.methodNames.add(PLAIN_INIT);
			this.params.add(new Object[]{ receiver});
			this.returnValues.add(RETURN_TYPE_VOID);
			this.captureIds.add(PSEUDO_CAPTURE_ID);
			this.isStaticCallList.add(Boolean.FALSE);
			this.logEnd(PSEUDO_CAPTURE_ID, receiver, RETURN_TYPE_VOID);
		}

		//--- handle method params
		Object param;
		int paramOID;
		for(int i = 0; i < methodParams.length; i++) {
			param = methodParams[i];

			// null and plain params have PLAIN init stmts such as
			// Integer var0 = 122
			// Float var1 = 2.3
			// String var2 = "Hello World"
			// e.g. o.myMethod(null, var0, var1, var2);
			if(param != null) {
				// we assume that all classes (besides java and sun classes) are instrumented.
				// So if there is no foregoing entry in the oid info table, the param is a new and
				// not monitored instance. That's why this param has to be serialized.
				paramOID = System.identityHashCode(param);

				if(paramOID == oid)
				{
					logger.error("PARAM is 'this' reference -> ignore");
					// TODO remove meta inf entries
					return;
				}

				createInitLogEntries(param);


				// method param  has been created before so we link to it
				// NECESSARY as the object might be modified in between
				// exemplary output in test code: 
				// Object a = new Object();
				// ...
				// o.m(a);
				methodParams[i] = paramOID;
			}
		}

		//--- create method call record
		this.objectIds.add(oid);
		this.methodNames.add(methodName);
		this.descList.add(methodDesc);
		this.params.add(methodParams);
		this.returnValues.add(RETURN_TYPE_VOID);
		this.captureIds.add(captureId);
		this.isStaticCallList.add(receiver instanceof Class);

		this.checkIfInstanceFromInnerInstanceClass(receiver);
	}

	@SuppressWarnings("rawtypes")
	private void createInitLogEntries(final Object param)
	{
		if (param == null) {
			return;
		}

		final int     paramOID     = System.identityHashCode(param);
		final boolean isArray      = param.getClass().isArray();
		final boolean isMap        = param instanceof Map;
		final boolean isCollection = param instanceof Collection;

		if(isArray || isMap || isCollection || this.updateInfoTable(paramOID, param, false)) {

			if(isPlain(param) || param instanceof Class) {
				this.objectIds.add(paramOID);
				// exemplary output in test code: Integer number = 123;
				this.methodNames.add(PLAIN_INIT);
				this.params.add(new Object[]{ param});

			} else if(isCollection) {

				final Collection c = (Collection) param;

				final Object[] valArray = new Object[c.size()];
				int index = 0;
				for(Object o : c)
				{
					if(o != null)
					{
						createInitLogEntries(o);
						valArray[index] = System.identityHashCode(o);
					}

					index++;
				}

				if(! this.oidRecMapping.containsKey(paramOID))
				{
					this.updateInfoTable(paramOID, param, true);
				}

				this.objectIds.add(paramOID);
				this.methodNames.add(COLLECTION_INIT);
				this.params.add(valArray);

			} else if(isMap) {

				final Map m = (Map) param;
				final Object[] valArray = new Object[m.size() * 2];

				Map.Entry entry;
				Object v, k;
				int index = 0;
				for(Object oe :  m.entrySet())
				{
					entry = (Map.Entry) oe;
					k = entry.getKey();
					createInitLogEntries(k);

					valArray[index++] = System.identityHashCode(k);

					v = entry.getValue();
					if(v == null)
					{
						valArray[index++] = null;
					}
					else
					{
						createInitLogEntries(v);

						valArray[index++] = System.identityHashCode(v);
					}
				}

				if(! this.oidRecMapping.containsKey(paramOID))
				{
					this.updateInfoTable(paramOID, param, true);
				}

				this.objectIds.add(paramOID);
				this.methodNames.add(MAP_INIT);
				this.params.add(valArray);
			}
			else if(isArray)
			{
				// we use Array to handle primitive and Object arrays in the same way
				final int arraySize = Array.getLength(param);

				final Object[] valArray = new Object[arraySize];

				Object o;
				for(int index = 0; index < arraySize; index++)
				{
					o = Array.get(param, index);
					if(o != null)
					{
						createInitLogEntries(o);
						valArray[index] = System.identityHashCode(o);
					}
				}

				if(! this.oidRecMapping.containsKey(paramOID))
				{
					this.updateInfoTable(paramOID, param, true);
				}

				this.objectIds.add(paramOID);
				this.methodNames.add(ARRAY_INIT);
				this.params.add(valArray);
			}
			else
			{
				this.objectIds.add(paramOID);
				// create new serialization record for first emersion
				// exemplary output in test code: Person newJoe = (Person) xstream.fromXML(xml); 

				this.checkIfInstanceFromInnerInstanceClass(param);
				this.methodNames.add(NOT_OBSERVED_INIT);

				try
				{
					//					this.xstream.toXML(param, sout);
					//					this.sout.flush();
					//					
					//					this.params.add(new Object[]{ this.bout.toByteArray() });
					//					
					//					this.bout.reset();
					// FIXME
					this.params.add(new Object[]{ this.xstream.toXML(param) });
				}
				catch(final Exception e)
				{
					logger.warn("an error occurred while serializing param '{}' -> adding null as param instead", param, e);

					// param can not be serialized -> add null as param
					this.params.add(new Object[]{ null });
				}
			}

			this.descList.add(EMPTY_DESC);
			this.returnValues.add(RETURN_TYPE_VOID);
			this.captureIds.add(PSEUDO_CAPTURE_ID);
			this.isStaticCallList.add(Boolean.FALSE);
			this.logEnd(PSEUDO_CAPTURE_ID, param, RETURN_TYPE_VOID);
		}
	}


	@Override
	public String toString()
	{
		final String delimiter = "\t|\t";

		final StringBuilder builder = new StringBuilder(1000);

		builder.append("LOG:\n")
		.append("-------------------------------------------------------------------")
		.append('\n')
		.append("RECNO").append(delimiter)
		.append("OID").append(delimiter)
		.append("CID").append(delimiter)
		.append("METHOD").append(delimiter)
		.append("PARAMS").append(delimiter)
		.append("RETURN").append(delimiter)
		.append("IS STATIC").append(delimiter)
		.append("DESC").append(delimiter)
		.append("ACCESSED FIELDS")
		.append('\n')
		.append("-------------------------------------------------------------------")
		.append('\n');

		int captureId;

		final int numRecords = this.objectIds.size();
		for(int i = 0; i < numRecords; i++)
		{
			captureId = this.captureIds.get(i);

			builder.append(i)									.append(delimiter) // RECNO
			.append(this.objectIds.get(i))			.append(delimiter) // OID
			.append(captureId)	 					    .append(delimiter) // CID
			.append(this.methodNames.get(i))            .append(delimiter) // METHOD
			.append(Arrays.toString(this.params.get(i))).append(delimiter) // PARAMS
			.append(this.returnValues.get(i))			.append(delimiter) // RETURN
			.append(this.isStaticCallList.get(i))		.append(delimiter) // IS STATIC
			.append(this.descList.get(i))				.append(delimiter) // DESC
			.append(this.oidNamesOfAccessedFields.get(captureId))			   // ACCESSED FIELDS
			.append('\n');
		}

		builder.append('\n').append('\n');

		builder.append("META INF:\n")
		.append("-------------------------------------------------------------------")
		.append('\n')
		.append("OID").append(delimiter)
		.append("INIT RECNO").append(delimiter)
		.append("OID CLASS").append(delimiter)
		.append("ACCESSED FIELDS").append(delimiter)
		.append("FIRST INIT").append(delimiter)
		.append("DEPENCENCY")
		.append('\n')
		.append("-------------------------------------------------------------------")
		.append('\n');

		final int numMetaInfRecords = this.oids.size();
		for(int i = 0; i < numMetaInfRecords; i++)
		{
			builder.append(this.oids.get(i)).append(delimiter)             // OID
			.append(this.oidInitRecNo.get(i)).append(delimiter)     // INIT RECNO
			.append(this.oidClassNames.get(i)).append(delimiter)         // OID CLASS 
			.append(this.oidNamesOfAccessedFields.get(i)).append(delimiter) // ACCESSED FIELDS
			.append(this.oidFirstInits.get(i)).append(delimiter)       // FIRST INIT FIELDS
			.append(this.oidDependencies.get(i))                       // DEPENCENCY FIELDS
			.append('\n');
		}

		return builder.toString();
	}
}
