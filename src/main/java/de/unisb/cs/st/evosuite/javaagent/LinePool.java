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
 */
/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Keep track of the lines of code in a class
 * 
 * @author Gordon Fraser
 * 
 */
public class LinePool {

	/** Map class names to methods to sets of line numbers */
	private static Map<String, Map<String, Set<Integer>>> lineMap = new LinkedHashMap<String, Map<String, Set<Integer>>>();

	/**
	 * Insert line into map for class
	 * 
	 * @param className
	 * @param methodName
	 * @param lineNo
	 */
	public static void addLine(String className, String methodName, int lineNo) {
		if (!lineMap.containsKey(className))
			lineMap.put(className, new LinkedHashMap<String, Set<Integer>>());

		if (!lineMap.get(className).containsKey(methodName))
			lineMap.get(className).put(methodName, new LinkedHashSet<Integer>());

		lineMap.get(className).get(methodName).add(lineNo);
	}

	/**
	 * Retrieve set of lines
	 * 
	 * @param className
	 * @param methodName
	 * @return
	 */
	public static Set<Integer> getLines(String className, String methodName) {
		if (lineMap.containsKey(className))
			if (lineMap.get(className).containsKey(methodName))
				return lineMap.get(className).get(methodName);

		return new HashSet<Integer>();
	}

	/**
	 * Retrieve all lines in a class
	 * 
	 * @param className
	 * @return
	 */
	public static Set<Integer> getLines(String className) {
		Set<Integer> lines = new LinkedHashSet<Integer>();
		if (lineMap.containsKey(className))
			for (Set<Integer> methodLines : lineMap.get(className).values())
				lines.addAll(methodLines);
		return lines;
	}

	/**
	 * Retrieve all lines in the pool
	 * 
	 * @return
	 */
	public static Set<Integer> getAllLines() {
		Set<Integer> lines = new LinkedHashSet<Integer>();
		for (String className : lineMap.keySet())
			for (Set<Integer> methodLines : lineMap.get(className).values())
				lines.addAll(methodLines);
		return lines;
	}
}
