package org.evosuite.runtime.reset;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class resets the static fields of a given class by invoking the <clinit> class initializer.
 * In order to re-invoke the <clinit> this is duplicated with the method name "__STATIC_RESET".
 * 
 * @author galeotti
 *
 */
public class ClassResetter {

    private static Logger logger = LoggerFactory.getLogger(ClassResetter.class);

	/**
	 * The name of the instrumented duplication of the class initializer <clinit>
	 */
	public static final String STATIC_RESET = "__STATIC_RESET";

	/**
	 * Singleton instance of this class
	 */
	private static final ClassResetter instance = new ClassResetter();

	private ClassLoader loader;
	
	private Map<ClassLoader, Map<String, Method>> resetMethodCache = new HashMap<>();
	
	/**
	 * Return singleton instance
	 * @return
	 */
	public static ClassResetter getInstance() {
		return instance;
	}

	public void setClassLoader(ClassLoader loader) throws IllegalArgumentException{
		if(loader==null){
			throw new IllegalArgumentException("Null class loader");
		}
		this.loader = loader;
	}
	
	
	private void cacheResetMethod(String classNameWithDots) {
        if (!resetMethodCache.containsKey(loader)) {
            resetMethodCache.put(loader, new HashMap<String, Method>());
        }
        Map<String, Method> methodMap = resetMethodCache.get(loader);
        if (methodMap.containsKey(classNameWithDots))
            return;

        try {
            Class<?> clazz = loader.loadClass(classNameWithDots);
            if(clazz.isInterface())
            	return;
            
            Method m = clazz.getMethod(STATIC_RESET, (Class<?>[]) null);
            m.setAccessible(true);
            methodMap.put(classNameWithDots, m);
        } catch (NoSuchMethodException | ClassNotFoundException | SecurityException | IllegalArgumentException e) {
            logger.error(""+e,e);
        }
    }
	
	private Method getResetMethod(String classNameWithDots) {
		cacheResetMethod(classNameWithDots);
		return resetMethodCache.get(loader).get(classNameWithDots);
	}

	/**
	 * Invoke the duplicated version of class initializar <clinit> 
	 *  
	 * @param classNameWithDots the class for invoking the duplicated version of class initializer <clinit>
	 */
	public void reset(String classNameWithDots) throws IllegalArgumentException, IllegalStateException{
		if(classNameWithDots==null || classNameWithDots.isEmpty()){
			throw new IllegalArgumentException("Empty class name in input");
		}
		
		if(loader == null){					
			new IllegalStateException("No specified loader");
		}
		
		Method m = getResetMethod(classNameWithDots);
		if(m == null)
			return; // TODO: Error handling
		try {
			m.invoke(null, (Object[]) null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoClassDefFoundError e) {
            logger.error(""+e,e);
        }
	}

}
