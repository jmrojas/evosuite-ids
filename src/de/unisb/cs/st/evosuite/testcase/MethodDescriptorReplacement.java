/**
 * 
 */
package de.unisb.cs.st.evosuite.testcase;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.unisb.cs.st.ds.util.io.Io;
import de.unisb.cs.st.evosuite.Properties;

/**
 * @author Gordon Fraser
 *
 */
public class MethodDescriptorReplacement {

	/** Singleton instance */
	private static MethodDescriptorReplacement instance = null;

	private static Logger logger = Logger.getLogger(MethodDescriptorReplacement.class);

	/** Map from class to method pairs */
	private Map<String, String > descriptors = new HashMap<String, String >();

	
	private Map<Method, Type> return_types = new HashMap<Method, Type>();
	
	private Map<Method, List<Type>> method_parameters = new HashMap<Method, List<Type>>();

	private Map<Constructor<?>, List<Type>> constructor_parameters = new HashMap<Constructor<?>, List<Type>>();

	/**
	 * Private constructor
	 */
	private MethodDescriptorReplacement() {
		getDescriptorMapping();
	}
	
	/**
	 * Singleton accessor
	 * @return
	 */
	public static MethodDescriptorReplacement getInstance() {
		if(instance == null)
			instance = new MethodDescriptorReplacement();
		
		return instance;
	}
	
	/**
	 * Check if we need to change anything here
	 * 
	 * @param className
	 * @param methodName
	 * @param descriptor
	 * @return
	 */
	public boolean hasKey(String className, String methodName, String descriptor) {
		//if(!descriptors.containsKey(className))
		//	return false;
		
		return descriptors.containsKey(className+"."+methodName+descriptor);
	}

	/**
	 * Get the actual replacement, if there is one
	 * @param className
	 * @param methodName
	 * @param descriptor
	 * @return
	 */
	public String get(String className, String methodName, String descriptor) {
		if(hasKey(className, methodName, descriptor))
			return descriptors.get(className+"."+methodName+descriptor);
		else
			return descriptor;
	}
	
	
	
	public List<Type> getParameterTypes(Method method) {
		return getParameterTypes(method.getDeclaringClass(), method);
	}	
	
	public List<Type> getParameterTypes(Type callee, Method method) {
		if(method_parameters.containsKey(method))
			return method_parameters.get(method);

		
		String className = null;
		if(callee == null) {
			className = method.getDeclaringClass().getName();
		} else {
			GenericClass c = new GenericClass(callee);
			className = c.getRawClass().getName(); 
		}
		
		if(hasKey(className, method.getName(), org.objectweb.asm.Type.getMethodDescriptor(method))) {
			String replacement = descriptors.get(className+"."+method.getName() + org.objectweb.asm.Type.getMethodDescriptor(method));
			logger.debug("Found replacement: "+replacement);
			List<Type> parameters = new ArrayList<Type>();
			for(org.objectweb.asm.Type asm_param : org.objectweb.asm.Type.getArgumentTypes(replacement)) {
				parameters.add(getType(asm_param));				
			}
			method_parameters.put(method, parameters);
			return parameters;
			
		} else {
			method_parameters.put(method, Arrays.asList(method.getGenericParameterTypes()));
			return Arrays.asList(method.getGenericParameterTypes());
		}			
	}

	public List<Type> getParameterTypes(Constructor<?> constructor) {
		if(constructor_parameters.containsKey(constructor))
			return constructor_parameters.get(constructor);

		
		String className = constructor.getDeclaringClass().getName();
		
		if(hasKey(className, "<init>", org.objectweb.asm.Type.getConstructorDescriptor(constructor))) {
			String replacement = descriptors.get(className+".<init>" + org.objectweb.asm.Type.getConstructorDescriptor(constructor));
			logger.debug("Found replacement: "+replacement);
			List<Type> parameters = new ArrayList<Type>();
			for(org.objectweb.asm.Type asm_param : org.objectweb.asm.Type.getArgumentTypes(replacement)) {
				parameters.add(getType(asm_param));				
			}
			constructor_parameters.put(constructor, parameters);
			return parameters;
			
		} else {
			constructor_parameters.put(constructor, Arrays.asList(constructor.getGenericParameterTypes()));
			return Arrays.asList(constructor.getGenericParameterTypes());
		}			
	}
	
	private Type getType(org.objectweb.asm.Type asm_type) {
		switch(asm_type.getSort()) {
		case org.objectweb.asm.Type.BOOLEAN:
			return boolean.class;
		case org.objectweb.asm.Type.BYTE:
			return byte.class;
		case org.objectweb.asm.Type.CHAR:
			return char.class;
		case org.objectweb.asm.Type.DOUBLE:
			return double.class;
		case org.objectweb.asm.Type.FLOAT:
			return float.class;
		case org.objectweb.asm.Type.INT:
			return int.class;
		case org.objectweb.asm.Type.LONG:
			return long.class;
		case org.objectweb.asm.Type.SHORT:
			return short.class;
		case org.objectweb.asm.Type.VOID:
			return void.class;
		case org.objectweb.asm.Type.ARRAY:
			//logger.trace("Converting to array of type "+asm_type.getElementType());
			return Array.newInstance((Class<?>) getType(asm_type.getElementType()), 0).getClass();
		default:
				try {
					Class<?> clazz = Class.forName(asm_type.getClassName());
					
					return clazz;
				} catch (ClassNotFoundException e) {
					logger.error("Could not find replacement type for "+asm_type);
				}				
		}
		return null;

	}
	
	/**
	 * Type of return value of a method
	 * @param className
	 * @param method
	 * @return
	 */
	public Type getReturnType(String className, Method method) {
		if(return_types.containsKey(method))
			return return_types.get(method);
		
		if(hasKey(className, method.getName(), org.objectweb.asm.Type.getMethodDescriptor(method))) {
			String replacement = descriptors.get(className+"."+method.getName() + org.objectweb.asm.Type.getMethodDescriptor(method));
			logger.debug("Found replacement: "+replacement);

			Type type = getType(org.objectweb.asm.Type.getReturnType(replacement));
			return_types.put(method, type);
			return type;
			
		} else {
			return_types.put(method, method.getGenericReturnType());
			return method.getGenericReturnType();
		}					
	}

	private void getDescriptorMapping() {
		//String className = System.getProperty("target.class");;
		//File file = new File(MutationProperties.OUTPUT_DIR+"/"+className+".obj");
		File dir = new File(Properties.getProperty("OUTPUT_DIR"));

		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".obj"); // && !dir.isDirectory();
		    }
		};
		File[] files = dir.listFiles(filter);
		
		for(File file : files) {
			if(file.isDirectory())
				continue;
			List<String> lines = Io.getLinesFromFile(file);
			//descriptors.put(className, new HashMap<String, String>());
			for(String line : lines) {
				//logger.debug("Read line: "+line);
				line = line.trim();
				// Skip comments
				if(line.startsWith("#"))
					continue;
				
				String[] parameters = line.split(",");
				if(parameters.length == 2) {
					if(!parameters[0].endsWith(parameters[1])) {
						descriptors.put(parameters[0], parameters[1]);
						logger.debug("Adding descriptor for class "+parameters[0]);
					}
				}
			}	
		}		
	}
}
