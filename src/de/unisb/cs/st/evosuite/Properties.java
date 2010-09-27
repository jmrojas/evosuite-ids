/**
 * 
 */
package de.unisb.cs.st.evosuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Gordon Fraser
 *
 */
public class Properties {

	private static Properties instance = null;
	
	private java.util.Properties properties;
	
	public static String PROJECT_PREFIX = "";
	
	public static String OUTPUT_DIR = "";
	
	public static String TARGET_CLASS= "";
	
	private static Class<?> TARGET_CLASS_INSTANCE = null;
	
	static {
		instance = new Properties();
	}
	
	private Properties() {
		properties = new java.util.Properties();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("evosuite.properties");
			properties.load(in);
			PROJECT_PREFIX = properties.getProperty("PROJECT_PREFIX");
			if(PROJECT_PREFIX == null) {
				PROJECT_PREFIX=System.getProperty("PROJECT_PREFIX");
			}
			OUTPUT_DIR = properties.getProperty("OUTPUT_DIR");
			TARGET_CLASS = properties.getProperty("TARGET_CLASS");
			if(System.getProperty("TARGET_CLASS") != null) {
				TARGET_CLASS=System.getProperty("TARGET_CLASS");
			}
			if(System.getProperty("OUTPUT_DIR") != null) {
				OUTPUT_DIR=System.getProperty("OUTPUT_DIR");
			}
			if(TARGET_CLASS != null) {
				properties.setProperty("TARGET_CLASS", TARGET_CLASS);
			}

			properties.setProperty("PROJECT_PREFIX", PROJECT_PREFIX);
			properties.setProperty("OUTPUT_DIR", OUTPUT_DIR);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	private static Properties getInstance() {
		if(instance == null) {
			instance = new Properties();
		}
		return instance;
	}
	
	public static Set<Object> getKeys() {
		return getInstance().properties.keySet();
	}
	
	public static Class<?> getTargetClass() {
		if(TARGET_CLASS_INSTANCE != null)
			return TARGET_CLASS_INSTANCE;
		
		try {
			TARGET_CLASS_INSTANCE = Class.forName(TARGET_CLASS);
			return TARGET_CLASS_INSTANCE;
		} catch(ClassNotFoundException e) {
			System.err.println("Could not find class under test "+TARGET_CLASS);
		}
		return null;
	}
	
	public static String getPropertyOrDefault(String property, String default_value) {
		String result = getProperty(property);
		if (result == null) {
			result = default_value;
		}
		return result;
	}
	
	public static void setProperty(String property, String value) {
		Properties.getInstance().properties.setProperty(property, value);
	}
	
	public static int getPropertyOrDefault(String property, int default_value) {
		String result = getProperty(property);
		if (result == null) {
			return default_value;
		}
		return Integer.parseInt(result);
	}
	
	public static double getPropertyOrDefault(String property, double default_value) {
		String result = getProperty(property);
		if (result == null) {
			return default_value;
		}
		return Double.parseDouble(result);
	}
	
	public static boolean getPropertyOrDefault(String property, boolean default_value) {
		String result = getProperty(property);
		if (result == null) {
			return default_value;
		}
		return Boolean.parseBoolean(result);
	}
	
	public static String getProperty(String key) {
		String value = System.getProperty(key);
		if(value == null)
			return Properties.getInstance().properties.getProperty(key);
		else {
			Properties.setProperty(key, value);
			return value; // System properties override config file
		}
	}
	
}
