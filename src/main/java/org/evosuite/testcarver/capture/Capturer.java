package org.evosuite.testcarver.capture;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.evosuite.testcarver.codegen.PostProcessor;
import org.evosuite.testcarver.exception.CapturerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;



public final class Capturer 
{
	private static CaptureLog                  currentLog;
	private static boolean                     isCaptureStarted    = false;
	private static boolean                     isShutdownHookAdded = false;
	private static final ArrayList<CaptureLog> logs                = new ArrayList<CaptureLog>();
	
	public static final String DEFAULT_SAVE_LOC = "captured.log";
	
	
	private static final ArrayList<String[]> classesToBeObserved = new ArrayList<String[]>();
	
	private static final transient Logger LOG = LoggerFactory.getLogger(Capturer.class);


	/*
	 * TODO this needs refactoring. 
	 */
	@Deprecated
	private static void initShutdownHook()
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				LOG.info("shutting down...");
				Capturer.stopCapture();
				Capturer.postProcess();
				LOG.info("shut down");
			}
		}));
	}
	
	public static void postProcess()
	{
		if(! Capturer.isCapturing())
		{
			if(! logs.isEmpty())
			{
				try
				{
//					   LOG.info("Saving captured log to {}", DEFAULT_SAVE_LOC);
//					   final File targetFile = new File(DEFAULT_SAVE_LOC);
//					   Capturer.save(new FileOutputStream(targetFile));
						
					   PostProcessor.init();
					   
					   final ArrayList<String>     pkgNames    = new ArrayList<String>();
					   final ArrayList<Class<?>[]> obsClasses = new ArrayList<Class<?>[]>();
			
					   int searchIndex;
					   for(String[] classNames : Capturer.classesToBeObserved)
					   {
						   searchIndex = classNames[0].lastIndexOf('.');
						   if(searchIndex > -1)
						   {
							   pkgNames.add(classNames[0].substring(0, searchIndex));  
						   }
						   else
						   {
							   pkgNames.add("");
						   }
						   
						   final Class<?> [] clazzes = new Class<?>[classNames.length];
						   for(int j = 0; j < classNames.length; j++)
						   {
							   clazzes[j] = Class.forName(classNames[j]);
						   }
						   obsClasses.add(clazzes);
					   }
					   
					   
					   PostProcessor.process(logs, pkgNames, obsClasses);
					   
					   Capturer.clear();
				}
				catch(final Exception e)
				{
					LOG.error("an error occurred while post proccessin", e);
				}
			}
		}
	}
	
	
	public static void save(final OutputStream out) throws IOException
	{
		if(out == null)
		{
			throw new NullPointerException("given OutputStream must not be null");
		}
		
		final XStream xstream = new XStream();
		xstream.toXML(logs, out);
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void load(final InputStream in)
	{
		if(in == null)
		{
			throw new NullPointerException("given InputStream must not be null");
		}
		
		final XStream xstream = new XStream(new StaxDriver());
		logs.addAll( (ArrayList<CaptureLog>) xstream.fromXML(in));
	}
	
	synchronized
	public static void clear()
	{
		currentLog = null;
		logs.clear();
		classesToBeObserved.clear();
		isCaptureStarted = false;
		
		FieldRegistry.clear();
	}
	
	synchronized
	public static void startCapture()
	{
		LOG.info("Starting Capturer...");
		
		if(isCaptureStarted)
		{
			throw new IllegalStateException("Capture has already been started");
		}
		
		currentLog       = new CaptureLog();
		isCaptureStarted = true;
		
		FieldRegistry.restoreForegoingGETSTATIC();
		
		LOG.info("Capturer has been started successfully");

	}
	
	synchronized
	public static void startCapture(final String classesToBeObservedString)
	{
		if(classesToBeObservedString == null)
		{
			final String msg = "no arguments specified";
			LOG.error(msg);
			throw new CapturerException(msg);
		}
		
		final ArrayList<String> args = new ArrayList<String>(Arrays.asList(classesToBeObservedString.split("\\s+")));
		if(args.isEmpty())
		{
			final String msg = "no class to be observed specified";
			LOG.error(msg);
			throw new CapturerException(msg);
		}
		
		// start Capturer if not active yet
		// NOTE: Stopping the capture and saving the corresponding logs is handled in the ShutdownHook
		//       which is automatically initialized in the Capturer
		Capturer.startCapture(args);
	}
	
	synchronized
	public static void startCapture(final List<String> classesToBeObserved)
	{
		LOG.info("Starting Capturer...");
	
		if(isCaptureStarted)
		{
			throw new IllegalStateException("Capture has already been started");
		}
		
		/*
		 * TODO need refactoring
		 * 
		if(! isShutdownHookAdded)
		{
			initShutdownHook();
			isShutdownHookAdded = true;
		}
	*/
		currentLog       = new CaptureLog();
		isCaptureStarted = true;
		
		
		final int      size    = classesToBeObserved.size();
		final String[] clazzes = new String[size];
		for(int i = 0; i < size; i++)
		{
			clazzes[i] = classesToBeObserved.get(i);
		}
		Capturer.classesToBeObserved.add(clazzes);
		
		FieldRegistry.restoreForegoingGETSTATIC();
		
		LOG.info("Capturer has been started successfully");
	}
	
	synchronized
	public static CaptureLog stopCapture()
	{
		LOG.info("Stopping Capturer...");
		
		if(isCaptureStarted)
		{
			isCaptureStarted = false;
			
			logs.add(currentLog);
			
			final CaptureLog log = currentLog;
			currentLog = null;
			
			LOG.info("Capturer has been stopped successfully");
			
			FieldRegistry.clear();
			
			return log;
		}
		
		return null;
	}
	
	synchronized
	public static boolean isCapturing()
	{
		return isCaptureStarted;
	}

	synchronized
	public static void setCapturing(final boolean isCapturing)
	{
		Capturer.isCaptureStarted = isCapturing;
	}
	
	
	public static void capture(final int captureId, final Object receiver, final String methodName, final String methodDesc, final Object[] methodParams)
	{
		//FIXME only for debugging
		if(methodName.equals(CaptureLog.OBSERVED_INIT))
		{
			if(receiver != null && receiver.getClass().getName().contains("Ekit"))
			{
				System.out.println("INIT OID: " + System.identityHashCode(receiver) + " isCapturing: " + isCapturing());
			}
		}
		
			if(isCapturing())
			{
				synchronized (currentLog)
				{
//					final Runnable r = new Runnable() 
//					{
//						@Override
//						public void run() 
//						{
							setCapturing(false);
							
							if(LOG.isDebugEnabled())
							{
								LOG.debug("captured:  captureId={} receiver={} type={} method={} methodDesc={} " + Arrays.toString(methodParams), 
																									  new Object[]{ captureId, 
																									 System.identityHashCode(receiver), 
																									 receiver.getClass().getName(), 
																									 methodName,
																									 methodDesc});				
								
							}
							
							currentLog.log(captureId, receiver, methodName, methodDesc, methodParams);
							
							setCapturing(true);
//						}
//					};
//					
//					if(true && ! EventQueue.isDispatchThread()) //isGUI
//					{
//						SwingUtilities.invokeLater(r);
//					}
//					else
//					{
//						r.run();
//					}
						
				}
			}
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<CaptureLog> getCaptureLogs()
	{
		return (List<CaptureLog>) logs.clone();
	}
	
	
	public static void enable(final int captureId, final Object receiver, final Object returnValue)
	{
			if(isCapturing())
			{
				synchronized (currentLog) {
//					final Runnable r = new Runnable() {
//						@Override
//						public void run() 
//						{
						   setCapturing(false);

						   if(LOG.isDebugEnabled())
						   {
							  LOG.debug("enabled: capturedId={} receiver={} returnValue={} returnValueOID={}", new Object[]{ captureId, System.identityHashCode(receiver), returnValue, System.identityHashCode(returnValue)});
						   }
							
						   currentLog.logEnd(captureId, receiver, returnValue);
						   setCapturing(true);
//						}
//					};
//					
//					if(true && ! EventQueue.isDispatchThread()) //isGUI
//					{
//						SwingUtilities.invokeLater(r);
//					}
//					else
//					{
//						r.run();
//					}
				}
			}
	}
}
