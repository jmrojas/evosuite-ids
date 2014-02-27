/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Gordon Fraser
 */
package org.evosuite.assertion;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.evosuite.setup.TestClusterGenerator;
import org.evosuite.utils.PureMethodsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InspectorManager {

	private static InspectorManager instance = null;

	private static Logger logger = LoggerFactory.getLogger(InspectorManager.class);

	private Map<Class<?>, List<Inspector>> inspectors = new HashMap<Class<?>, List<Inspector>>();

	private Map<String, List<String>> blackList = new HashMap<String, List<String>>();
	
	private InspectorManager() {
		// TODO: Need to replace this with proper analysis
		// readInspectors();
		initializeBlackList();
	}
	
	private void initializeBlackList() {
		// These methods will include absolute path names and should not be in assertions
		blackList.put("java.io.File", Arrays.asList(new String[] {"getPath", "getAbsolutePath", "getCanonicalPath"}));
		
		// These methods will contain locale specific strings 
		blackList.put("java.util.Date", Arrays.asList(new String[] {"getLocaleString"}));

		// These methods will include data differing in every run 
		blackList.put("java.lang.Thread", Arrays.asList(new String[] {"activeCount", "getId", "getName", "getPriority", "toString"}));
		blackList.put("java.util.EventObject", Arrays.asList(new String[] {"toString"}));
		
		blackList.put(Locale.class.getCanonicalName(), Arrays.asList(new String[] {"getDisplay"}));
		
		// AWT identifiers are different with every run
		blackList.put("java.awt.Panel", Arrays.asList(new String[] {"toString"}));
		blackList.put("java.awt.Component", Arrays.asList(new String[] {"toString"}));
		blackList.put("java.awt.event.MouseWheelEvent", Arrays.asList(new String[] {"toString"}));
	}

	/**
	 * <p>
	 * Getter for the field <code>instance</code>.
	 * </p>
	 * 
	 * @return a {@link org.evosuite.assertion.InspectorManager} object.
	 */
	public static InspectorManager getInstance() {
		if (instance == null) {
			instance = new InspectorManager();
		}
		return instance;
	}

	private boolean isInspectorMethod(Method method) {
		return Modifier.isPublic(method.getModifiers())
				&& (method.getReturnType().isPrimitive()
		                || method.getReturnType().equals(String.class) || method.getReturnType().isEnum())
		        && !method.getReturnType().equals(void.class)
		        && method.getParameterTypes().length == 0
		        && !method.getName().equals("hashCode")
		        && !method.getDeclaringClass().equals(Object.class)
		        && !method.isSynthetic() 
		        && !method.isBridge()
		        && !method.getName().equals("pop")
		        && !isBlackListed(method)
		        && !isImpureJDKMethod(method); 
	}
	
	private boolean isImpureJDKMethod(Method method) {
		String className = method.getDeclaringClass().getCanonicalName();
		if(!className.startsWith("java."))
			return false;
		
		return !PureMethodsList.instance.isPureJDKMethod(method);
	}
	
	private boolean isBlackListed(Method method) {
		String className = method.getDeclaringClass().getCanonicalName();
		if(!blackList.containsKey(className))
			return false;
		String methodName = method.getName();
		return blackList.get(className).contains(methodName);
	}
	
	private void determineInspectors(Class<?> clazz) {
		if (!TestClusterGenerator.canUse(clazz))
			return;
		List<Inspector> inspectorList = new ArrayList<Inspector>();
		for (Method method : clazz.getMethods()) {
			if (isInspectorMethod(method)) { // FIXXME
				logger.debug("Inspector for class " + clazz.getSimpleName() + ": "
				        + method.getName() +" defined in "+method.getDeclaringClass().getCanonicalName());

				inspectorList.add(new Inspector(clazz, method));
			}
		}
		inspectors.put(clazz, inspectorList);
	}

	/**
	 * <p>
	 * Getter for the field <code>inspectors</code>.
	 * </p>
	 * 
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @return a {@link java.util.List} object.
	 */
	public List<Inspector> getInspectors(Class<?> clazz) {
		if (!inspectors.containsKey(clazz))
			determineInspectors(clazz);
		return inspectors.get(clazz);
	}

	/**
	 * <p>
	 * removeInspector
	 * </p>
	 * 
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param inspector
	 *            a {@link org.evosuite.assertion.Inspector} object.
	 */
	public void removeInspector(Class<?> clazz, Inspector inspector) {
		if (inspectors.containsKey(clazz)) {
			inspectors.get(clazz).remove(inspector);
		}
	}
}
