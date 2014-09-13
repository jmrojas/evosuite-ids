package org.evosuite.runtime.mock;

import java.util.ArrayList;
import java.util.List;


import org.evosuite.runtime.RuntimeSettings;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockFileInputStream;
import org.evosuite.runtime.mock.java.io.MockFileOutputStream;
import org.evosuite.runtime.mock.java.io.MockFileReader;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.evosuite.runtime.mock.java.io.MockRandomAccessFile;
import org.evosuite.runtime.mock.java.lang.MockException;
import org.evosuite.runtime.mock.java.lang.MockRuntime;
import org.evosuite.runtime.mock.java.lang.MockThrowable;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.evosuite.runtime.mock.java.util.MockGregorianCalendar;
import org.evosuite.runtime.mock.java.util.MockRandom;
import org.evosuite.runtime.mock.java.util.MockTimer;
import org.evosuite.runtime.mock.java.util.logging.MockFileHandler;
import org.evosuite.runtime.mock.java.util.logging.MockLogRecord;
import org.evosuite.runtime.mock.javax.swing.MockJFileChooser;
import org.evosuite.runtime.mock.javax.swing.filechooser.MockFileSystemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class used to handle all the mock objects.
 * When a new mock is defined, it has to be statically added
 * to the source code of this class.
 * 
 * <p>
 * Recall that a "override" mock M of class X has to extend X (ie 'class M extends X'),
 * and have the same constructors with same inputs, and same static methods.
 * Note: cannot use override for constructors and static methods.
 * 
 * @author arcuri
 *
 */
public class MockList {

	private static final Logger logger = LoggerFactory.getLogger(MockList.class);
	
	/**
	 * Return a list of all mock object classes used in EvoSuite.
	 * What is returned depend on which mock types are going to 
	 * be used in the search
	 * 
	 * @return a list of Class objects
	 */
	public static List<Class<? extends EvoSuiteMock>> getList(){

		List<Class<? extends EvoSuiteMock>>  list = new ArrayList<>();

		if(RuntimeSettings.useVFS){
			list.add(MockFile.class);
			list.add(MockFileInputStream.class);
			list.add(MockFileOutputStream.class);
			list.add(MockRandomAccessFile.class);
			list.add(MockFileReader.class);
			list.add(MockFileWriter.class);
			list.add(MockPrintStream.class);
			list.add(MockPrintWriter.class);
			list.add(MockFileHandler.class);
			list.add(MockJFileChooser.class);
			list.add(MockFileSystemView.class);
		}

		if(RuntimeSettings.mockJVMNonDeterminism) {
			list.add(MockDate.class);
			list.add(MockRandom.class);
			list.add(MockGregorianCalendar.class);
			list.add(MockLogRecord.class);
			list.add(MockThrowable.class);
			list.add(MockException.class);
			list.add(MockRuntime.class);
			list.add(MockTimer.class);
		}

		return list;
	}

	/**
	 * Check if the given class has been mocked
	 * 
	 * @param originalClass
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean shouldBeMocked(String originalClass) throws IllegalArgumentException{		
		return getMockClass(originalClass) != null;
	}


	/**
	 * Check if the given class is among the mock classes
	 * 
	 * @param mockClass
	 * @return
	 */
	public static boolean isAMockClass(String mockClass) {
		if(mockClass == null){
			return false;
		}

		for(Class<?> mock : getList()){
			if(mock.getCanonicalName().equals(mockClass)){
				return true;
			}
		}

		return false;
	}

	/**
	 * Return the mock class for the given target
	 * 
	 * @param originalClass
	 * @return {@code null} if the target is not mocked
	 */
	public static Class<?> getMockClass(String originalClass) throws IllegalArgumentException{
		if(originalClass==null || originalClass.isEmpty()){
			throw new IllegalArgumentException("Empty className");
		}

		for(Class<? extends EvoSuiteMock> mock : getList()){

			String name = null;
			
			if(OverrideMock.class.isAssignableFrom(mock)){
				Class<?> target = mock.getSuperclass();
				if(target==null){
					logger.error("Override mock "+mock.getCanonicalName()+" does not have a superclass");
					continue;
				}
				name = target.getCanonicalName();
				
			} else if(StaticReplacementMock.class.isAssignableFrom(mock)){
				try {
					StaticReplacementMock m = (StaticReplacementMock) mock.newInstance();
					name = m.getMockedClassName();
				} catch (Exception e) {
					logger.error("Failed to create instance of mock "+mock.getCanonicalName());
					continue;
				} 
			} else {
				//should never happen
				logger.error("Cannot handle "+mock);
				continue;
			}

			if(originalClass.equals(name)){
				return mock;
			}
		}

		return null;
	}
}
