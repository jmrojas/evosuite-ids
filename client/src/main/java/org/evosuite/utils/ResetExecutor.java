package org.evosuite.utils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.TimeController;
import org.evosuite.coverage.mutation.MutationObserver;
import org.evosuite.runtime.Runtime;
import org.evosuite.runtime.classhandling.ClassResetter;
import org.evosuite.runtime.classhandling.ResetManager;
import org.evosuite.runtime.sandbox.Sandbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetExecutor {

	private final static Logger logger = LoggerFactory.getLogger(ResetExecutor.class);

	private static final ResetExecutor instance = new ResetExecutor();

	private ResetExecutor() {
	}
	

	public synchronized static ResetExecutor getInstance() {
		return instance;
	}

	public void resetAllClasses() {
		List<String> classesToReset = ResetManager.getInstance().getClassResetOrder();
		resetClasses(classesToReset);
	}

	public void resetClasses(List<String> classesToReset) {
		ClassLoader loader = TestGenerationContext.getInstance().getClassLoaderForSUT();
		resetClasses(classesToReset, loader);
	}
	
	public void resetClasses(List<String> classesToReset, ClassLoader loader) {
		//try to reset each collected class

		ClassResetter.getInstance().setClassLoader(loader);

		long start = System.currentTimeMillis();

		for (String className : classesToReset) {
			//this can be expensive

			long elapsed = System.currentTimeMillis() - start;

			if(! TimeController.getInstance().isThereStillTimeInThisPhase() || elapsed > Properties.TIMEOUT_RESET){
				logger.warn("Stopped resetting of classes due to timeout");
				break;
			}
			resetClass(className);
		}
	}

	private void resetClass(String className) {

		//className.__STATIC_RESET() exists
		logger.debug("Resetting class " + className);
		
		int mutationActive = MutationObserver.activeMutation;
		MutationObserver.deactivateMutation();

		//execute __STATIC_RESET()
		Sandbox.goingToExecuteSUTCode();
        TestGenerationContext.getInstance().goingToExecuteSUTCode();

		Runtime.getInstance().resetRuntime(); //it is important to initialize the VFS

		try {
			Method resetMethod = ClassResetter.getInstance().getResetMethod(className);
			if (resetMethod!=null) {
				resetMethod.invoke(null, (Object[]) null);
			}
		} catch (Throwable  e) {
			ClassResetter.getInstance().logWarn(className, e.getClass() + " thrown during execution of method  __STATIC_RESET() for class " + className + ", " + e.getCause());
		}  finally {
			Sandbox.doneWithExecutingSUTCode();
            TestGenerationContext.getInstance().doneWithExecuteingSUTCode();
			MutationObserver.activateMutation(mutationActive);
		}
	}
}
