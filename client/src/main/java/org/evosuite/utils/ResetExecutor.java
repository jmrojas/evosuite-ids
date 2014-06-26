package org.evosuite.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;

import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.mutation.MutationObserver;
import org.evosuite.runtime.Runtime;
import org.evosuite.runtime.reset.ClassResetter;
import org.evosuite.runtime.reset.ResetManager;
import org.evosuite.runtime.sandbox.Sandbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetExecutor {

	private final static Logger logger = LoggerFactory.getLogger(ResetExecutor.class);

	private ResetExecutor() {
		
	}
	
	private static ResetExecutor instance;
	
	public synchronized static ResetExecutor getInstance() {
		if (instance == null) {
			instance = new ResetExecutor();
		}
		return instance;
	}

	public void resetAllClasses() {
		List<String> classesToReset = ResetManager.getInstance().getClassResetOrder();
		resetClasses(classesToReset);
	}

	public void resetClasses(List<String> classesToReset) {
		//try to reset each collected class
		for (String className : classesToReset) {
			resetClass(className);
		}
	}

	private final HashSet<String> confirmedResettableClasses = new HashSet<String>();

	private static Method getResetMethod(String className) {
		try {
			ClassLoader classLoader = TestGenerationContext.getInstance().getClassLoaderForSUT();
			Class<?> clazz = Class.forName(className, true, classLoader);
			Method m = clazz.getMethod(ClassResetter.STATIC_RESET, (Class<?>[]) null);
			m.setAccessible(true);
			return m;
		
		} catch (ClassNotFoundException e) {
			logger.debug("Class " + className + " could not be found during setting up of assertion generation ");
		} catch (NoSuchMethodException e) {
			logger.debug("__STATIC_RESET() method does not exists in class " + className);
		} catch (SecurityException e) {
			logger.warn("Security exception thrown during loading of method  __STATIC_RESET() for class " + className);
		} catch (ExceptionInInitializerError ex) {
			logger.warn("Class " + className + " could not be initialized during __STATIC_RESET() execution: "+ex.getMessage());
		} catch (LinkageError ex) {
			logger.warn("Class " + className + "  initialization led to a Linkage error during during __STATIC_RESET() execution: "+ex.getMessage());
		}
		return null;
	}

	private void resetClass(String className) {
		int mutationActive = MutationObserver.activeMutation;
		MutationObserver.deactivateMutation();
		logger.info("Resetting class "+className);
		try {
			Method resetMethod = getResetMethod(className);
			if (resetMethod!=null) {
				//className.__STATIC_RESET() exists
				confirmedResettableClasses.add(className);
				//execute __STATIC_RESET()
				Sandbox.goingToExecuteSUTCode();
                TestGenerationContext.getInstance().goingToExecuteSUTCode();

				Runtime.getInstance().resetRuntime(); //it is important to initialize the VFS
				resetMethod.invoke(null, (Object[]) null);
				
				Sandbox.doneWithExecutingSUTCode();
                TestGenerationContext.getInstance().doneWithExecuteingSUTCode();

			}
		} catch (SecurityException e) {
			logger.warn("Security exception thrown during loading of method  __STATIC_RESET() for class " + className);
			logger.warn(""+e.getCause());
		} catch (IllegalAccessException e) {
			logger.warn("IllegalAccessException during execution of method  __STATIC_RESET() for class " + className);
			logger.warn(""+e.getCause());
		} catch (IllegalArgumentException e) {
			logger.warn("IllegalArgumentException during execution of method  __STATIC_RESET() for class " + className);
			logger.warn(""+e.getCause());
		} catch (InvocationTargetException e) {
			logger.warn("InvocationTargetException during execution of method  __STATIC_RESET() for class " + className);
			logger.warn(""+e.getCause());
		} finally {
			MutationObserver.activateMutation(mutationActive);
		}
	}

	public void reloadClasses() {
		for (String className : ResetManager.getInstance().getClassResetOrder()) {
			Runtime.getInstance().resetRuntime(); //it is important to initialize the VFS
			try {
				ClassLoader classLoader = TestGenerationContext.getInstance().getClassLoaderForSUT();
				Class.forName(className, true, classLoader);
			} catch (ClassNotFoundException e) {
				logger.warn("Class " + className + " could not be found during setting up of assertion generation ");;
			} catch (ExceptionInInitializerError ex) {
				logger.warn("Class " + className + " could not be initialized during setting up of assertion generation ");;
			} catch (LinkageError ex) {
				logger.warn("Class " + className + "  initialization led to a Linkage error ");;
			}
		}
	
	}

}
