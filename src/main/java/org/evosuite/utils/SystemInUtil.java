package org.evosuite.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.evosuite.Properties;
import org.evosuite.setup.TestCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This singleton class is used to handle calls to System.in by
 * replacing them with a smart stub
 * 
 * @author arcuri
 *
 */
public class SystemInUtil extends InputStream{

	/**
	 * Need to keep reference to original {@code System.in} for
	 * when we reset this singleton	
	 */
	private static final InputStream defaultIn = System.in;

	private static Logger logger = LoggerFactory.getLogger(SystemInUtil.class);

	private static final SystemInUtil singleton = new SystemInUtil();

	/**
	 * Has System.in ever be used by the SUT?
	 */
	private volatile boolean beingUsed;

	/**
	 * The data that will be taken from System.in
	 */
	private volatile List<Byte> data;

	/**
	 *  the position in the stream
	 */
	private volatile AtomicInteger counter;

	/**
	 * Need to add support function to EvoSuite search just once
	 */
	private boolean hasAddedSupport;

	/**
	 * This is needed to simulate blocking calls when there is 
	 * no input 
	 */
	private static final Object monitor = new Object();
	
	private volatile boolean endReached;
	
	//--------------------------------
	
	/**
	 * Hidden constructor
	 */
	protected SystemInUtil(){
		super();
	}

	public static synchronized SystemInUtil getInstance(){
		return singleton;
	}

	/**
	 * Reset the static state be re-instantiate the singleton
	 */
	public static synchronized void resetSingleton(){
		singleton.beingUsed = false;	
		singleton.hasAddedSupport = false;
		singleton.data = new ArrayList<Byte>();
		singleton.counter = new AtomicInteger(0);
		singleton.endReached = false;
		System.setIn(defaultIn);
	}

	/**
	 * Setup mocked/stubbed System.in for the test case
	 */
	public void initForTestCase(){
		data = new ArrayList<Byte>();
		counter = new AtomicInteger(0);
		if(Properties.REPLACE_SYSTEM_IN){
			System.setIn(this);
		}
	}

	/**
	 * Use given <code>input</code> string to represent the data
	 * that will be provided by System.in.
	 * The string will be appended to current buffer as a new line,
	 * i.e. by adding "\n" to the <code>input</code> string
	 * 
	 * @param input	A string representing an input on the console
	 */
	public static void addInputLine(String input){
		if(input==null){
			return;
		} 

		/*
		 * Note: this method needs to be static, as we call it directly in the test cases.
		 */

		synchronized(monitor){
			String line = input+"\n";
			for(byte b : line.getBytes()){
				singleton.data.add((Byte)b);
			}	
			singleton.endReached = false;
		}
	}

	/**
	 * If System.in was used, add methods to handle/simulate it
	 */
	public void addSupportInTestClusterIfNeeded(){
		if(!beingUsed || hasAddedSupport){
			return;
		}

		logger.debug("Going to add support for System.in");
		hasAddedSupport = true;

		try {
			TestCluster.getInstance().addTestCall(new GenericMethod(
					SystemInUtil.class.getMethod("addInputLine",new Class<?>[] { String.class }),
					new GenericClass(SystemInUtil.class)));
		} catch (SecurityException e) {
			logger.error("Error while handling Random: "+e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			logger.error("Error while handling Random: "+e.getMessage(),e);
		}
	}


	@Override
	public int read() throws IOException {

		beingUsed = true;
		
		synchronized(monitor){
			
			int current = counter.get();
			
			if(Thread.currentThread().isInterrupted()){
				/*
				 *  if by the time this thread acquires the monitor it has been interrupted,
				 *  and the buffered data is finished, then return -1 to represent the end of
				 *  the stream.
				 *  
				 *  Note: the real System.in would not return (would block). Here
				 *  we need to return, otherwise the test case thread would never end
				 */
				return -1;
			}

			while(current >= data.size()){
				
				if(!endReached){
					endReached = true;
					return -1;
				}
				
				/*
				 * instead of having the thread waiting on new input that might never come (eg
				 * if the SUT code is run on same thread as test case, or if there is no console input
				 * in the following test case statements), let's just simulate an exception.
				 */
				throw new IOException("Simulated exception in System.in");
				/*
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					return -1; // simulate end of stream
				}
				*/
			}
			
			int i = counter.getAndIncrement();

			return (int) data.get(i);
		}
	}

	@Override
	public int available() throws IOException {
        synchronized(monitor){
        		return data.size() - counter.get();
        }
    }
	
	/**
	 * Has there be any call to System.in.read()?
	 * @return
	 */
	public boolean hasBeenUsed() {
		return beingUsed;
	}

}
