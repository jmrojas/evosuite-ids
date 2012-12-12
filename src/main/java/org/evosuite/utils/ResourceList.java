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
package org.evosuite.utils;

/**
 * @author fraser
 * 
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.evosuite.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * list resources available from the classpath @ *
 * 
 * @author Gordon Fraser
 */
public class ResourceList {

	private static Logger logger = LoggerFactory.getLogger(ResourceList.class);
	
	public static boolean hasClass(String className) {
		// for windows quote File.separator "\"
		className = className.replaceAll(Pattern.quote(File.separator),
				Matcher.quoteReplacement(Pattern.quote(File.separator)));
		// replace "." with File.separator
		className = className.replaceAll("\\.", Matcher.quoteReplacement(Pattern.quote(File.separator)));
		
		Pattern pattern = Pattern.compile(".*"+className+".class");
		final String[] classPathElements = Properties.CP.split(File.pathSeparator);
		for (final String element : classPathElements) {
			if (!getResources(element, pattern).isEmpty())
				return true;
		}
		return false;
	}

	/**
	 * for all elements of java.class.path get a Collection of resources Pattern
	 * pattern = Pattern.compile(".*"); gets all resources
	 * 
	 * @param pattern
	 *            the pattern to match
	 * @return the resources in the order they are found
	 */
	public static Collection<String> getResources(final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		final String[] classPathElements = Properties.CP.split(File.pathSeparator);
		for (final String element : classPathElements) {
			retval.addAll(getResources(element, pattern));
		}
		return retval;
	}

	public static Collection<String> getResources(final Pattern pattern,
	        String classPathElement) {
		final ArrayList<String> retval = new ArrayList<String>();
		retval.addAll(getResources(classPathElement, pattern));
		return retval;
	}

	/**
	 * <p>
	 * getAllResources
	 * </p>
	 * 
	 * @param pattern
	 *            a {@link java.util.regex.Pattern} object.
	 * @return a {@link java.util.Collection} object.
	 */
	public static Collection<String> getAllResources(final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		String[] classPathElements = System.getProperty("java.class.path", ".").split(File.pathSeparator);
		for (final String element : classPathElements) {
			retval.addAll(getResources(element, pattern));
		}
		return retval;
	}

	/**
	 * for all elements of java.class.path get a Collection of resources Pattern
	 * pattern = Pattern.compile(".*"); gets all resources
	 * 
	 * @param pattern
	 *            the pattern to match
	 * @return the resources in the order they are found
	 */
	public static Collection<String> getBootResources(final Pattern pattern) {
		Collection<String> result = getResources(pattern);
		String classPath = System.getProperty("sun.boot.class.path", ".");
		String[] classPathElements = classPath.split(File.pathSeparator);
		for (final String element : classPathElements) {
			result.addAll(getResources(element, pattern));
		}
		return result;
	}

	private static Collection<String> getResources(final String element,
	        final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		final File file = new File(element);
		if (file.isDirectory()) {
			try {
				retval.addAll(getResourcesFromDirectory(file, pattern,
				                                        file.getCanonicalPath()));
			} catch (IOException e) {
				logger.error("Error in getting resources",e);
			}
		} else if (!file.exists()) {
			//do nothing
		} else if (file.getName().endsWith(".jar")) {
			retval.addAll(getResourcesFromJarFile(file, pattern));
		}
		
		return retval;
	}

	private static Collection<String> getResourcesFromJarFile(final File file,
	        final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		ZipFile zf;
		try {
			zf = new ZipFile(file);
		} catch (final ZipException e) {
			throw new Error(e);
		} catch (final IOException e) {
			throw new Error(e);
		}
		final Enumeration<?> e = zf.entries();
		while (e.hasMoreElements()) {
			final ZipEntry ze = (ZipEntry) e.nextElement();
			// On Windows we assume File.separatorChar, but ZipFile will always give /
			final String fileName = ze.getName().replace('/', File.separatorChar);
			final boolean accept = pattern.matcher(fileName).matches();
			if (accept) {
				retval.add(fileName);
			}
		}
		try {
			zf.close();
		} catch (final IOException e1) {
			throw new Error(e1);
		}
		return retval;
	}

	private static Collection<String> getResourcesFromDirectory(final File directory,
	        final Pattern pattern, final String dirName) {
		final ArrayList<String> retval = new ArrayList<String>();
		final File[] fileList = directory.listFiles();
		for (final File file : fileList) {
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern, dirName));
			} else {
				try {
					final String fileName = file.getCanonicalPath().replace(dirName
					                                                                + File.separator,
					                                                        "");
					final boolean accept = pattern.matcher(fileName).matches();
					if (accept) {
						retval.add(fileName);
					}
				} catch (final IOException e) {
					throw new Error(e);
				}
			}
		}
		return retval;
	}

}
