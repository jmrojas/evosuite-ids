/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.assertion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisb.cs.st.evosuite.Properties;
import de.unisb.cs.st.evosuite.testcase.TestCluster;

public class InspectorManager {

	private static InspectorManager instance = null;

	private static Logger logger = LoggerFactory.getLogger(InspectorManager.class);

	Map<Class<?>, List<Inspector>> inspectors = new HashMap<Class<?>, List<Inspector>>();

	private InspectorManager() {
		readInspectors();
	}

	private void addInspector(Class<?> clazz, Method m) {
		if (!inspectors.containsKey(clazz))
			inspectors.put(clazz, new ArrayList<Inspector>());
		List<Inspector> i = inspectors.get(clazz);
		i.add(new Inspector(clazz, m));
	}

	private void readInspectors() {
		FilenameFilter inspector_filter = new FilenameFilter() {
			@Override
			public boolean accept(File f, String s) {
				return s.toLowerCase().endsWith(".inspectors");
			}
		};

		int num = 0;
		int num_old = 0;
		File basedir = new File(Properties.OUTPUT_DIR);
		for (File f : basedir.listFiles(inspector_filter)) {
			//			String name = f.getName().replaceAll("_\\d+.inspectors$", "").replace("_", "$");
			String name = f.getName().replaceAll(".inspectors", "").replace("_", "$");
			try {
				Class<?> clazz = TestCluster.classLoader.loadClass(name);
				Scanner scanner = new Scanner(f);
				Set<String> inspector_names = new HashSet<String>();
				try {
					//first use a Scanner to get each line
					while (scanner.hasNextLine()) {
						inspector_names.add(scanner.nextLine().trim());
					}
				} finally {
					//ensure the underlying stream is always closed
					scanner.close();
				}

				try {
					for (Method m : clazz.getMethods()) {
						if (inspector_names.contains(m.getName()
						        + Type.getMethodDescriptor(m))) {
							addInspector(clazz, m);
							num++;
						}
					}
				} catch (Throwable t) {
					logger.warn("Error while determining inspectors of class "
					        + clazz.getSimpleName() + ": " + t);
				}
				logger.debug("Found inspector: " + name + " -> " + (num - num_old)
				        + " for class " + clazz.getName() + " in file " + name);
				num_old = num;
			} catch (FileNotFoundException e) {
				logger.info("Could not find file " + name);
			} catch (ClassNotFoundException e) {
				logger.info("Could not find inspector class " + name);
			}
		}
		logger.info("Loaded " + num + " inspectors");
	}

	public static InspectorManager getInstance() {
		if (instance == null) {
			instance = new InspectorManager();
		}
		return instance;
	}

	private void determineInspectors(Class<?> clazz) {
		List<Inspector> inspectorList = new ArrayList<Inspector>();
		for (Method method : clazz.getMethods()) {
			if (!Modifier.isProtected(method.getModifiers())
			        && !Modifier.isPrivate(method.getModifiers())
			        && (method.getReturnType().isPrimitive()
			                || method.getReturnType().equals(String.class) || method.getReturnType().isEnum())
			        && !method.getReturnType().equals(void.class)
			        && method.getParameterTypes().length == 0
			        && !method.getName().equals("hashCode")
			        && !method.getDeclaringClass().equals(Object.class)
			        && !method.getName().equals("pop")) { // FIXXME
				logger.info("Inspector for class " + clazz.getSimpleName() + ": "
				        + method.getName());
				inspectorList.add(new Inspector(clazz, method));
			}
		}
		inspectors.put(clazz, inspectorList);
	}

	public List<Inspector> getInspectors(Class<?> clazz) {
		if (!inspectors.containsKey(clazz))
			determineInspectors(clazz);
		return inspectors.get(clazz);
	}
}
