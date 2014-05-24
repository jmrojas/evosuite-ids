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

package org.evosuite.classpath;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Utilities to list class resources (ie .class files) available from the classpath 
 * </p>
 * 
 * 
 * @author Gordon Fraser
 */
public class ResourceList {

	private static Logger logger = LoggerFactory.getLogger(ResourceList.class);

	/** Cache of file lists to avoid unnecessary IO */
	private static Map<String, Collection<String>> classPathCache = new LinkedHashMap<String, Collection<String>>();

	protected static final ResourceCache cache = new ResourceCache();

	// -------------------------------------------
	// --------- public methods  ----------------- 
	// -------------------------------------------


	/**
	 * is the target class among the ones in the SUT classpath?
	 * 
	 * @param className
	 *            a fully qualified class name
	 * @return
	 */
	public static boolean hasClass(String className) {
		return cache.hasClass(className);
	}

	public static InputStream getClassAsStream(String name) {
		String path = name.replace('.', '/') + ".class";
		String windowsPath = name.replace(".", "\\") + ".class";
		
		//first try with system classloader
		InputStream is = ClassLoader.getSystemResourceAsStream(path);
		if(is!=null){
			return is;
		}
		if (File.separatorChar != '/') {			
			is = ClassLoader.getSystemResourceAsStream(windowsPath);
			if(is!=null){
				return is;
			}
		}
		
		String escapedString = java.util.regex.Pattern.quote(path); //Important in case there is $ in the classname
		Pattern pattern = Pattern.compile(escapedString);
		InputStream resource = getResourceAsStream(pattern);

		if (resource != null) {
			return resource;
		}

		if (File.separatorChar != '/') {
			/*
			 * This can happen for example in Windows.
			 * Note: we still need to do scan above in case of Jar files (that would still use '/' inside)
			 */			
			escapedString = java.util.regex.Pattern.quote(windowsPath);
			pattern = Pattern.compile(escapedString);
			return getResourceAsStream(pattern);
		}

		return null;
	}

	//TODO JavaDoc
	public static Collection<String> getAllClassesAsResources(String classPathEntry, boolean includeAnonymousClasses){
		//TODO check input
		return getAllClassesAsResources(classPathEntry,"", includeAnonymousClasses);
	}
	
	//TODO JavaDoc
	public static Collection<String> getAllClassesAsResources(String classPathEntry, String prefix, boolean includeAnonymousClasses){
		//TODO check input
		Pattern pattern = Pattern.compile(prefix.replace('.', '/')+"[^\\$]*.class");
		Collection<String> resources = ResourceList.getResources(classPathEntry, pattern);
		return resources;
	}




	// -------------------------------------------
	// --------- private/protected methods  ------ 
	// -------------------------------------------

	/**
	 * 
	 * @param fullyQualifyingClassName
	 *            a fully qualified class name
	 * @return
	 */
	protected static String getClassAsResource(String fullyQualifyingClassName) {

		String path = fullyQualifyingClassName.replace('.', '/') + ".class";
		String escapedString = java.util.regex.Pattern.quote(path); //Important in case there is $ in the classname

		Pattern pattern = Pattern.compile(escapedString);

		Collection<String> resources = getResources(ClassPathHandler.getInstance().getTargetProjectClasspath(), pattern);

		if (!resources.isEmpty()) {
			return resources.iterator().next();
		}

		if (File.separatorChar != '/') {
			/*
			 * This can happen for example in Windows.
			 * Note: we still need to do scan above in case of Jar files (that would still use '/' inside)
			 */
			path = fullyQualifyingClassName.replace(".", "\\") + ".class";
			escapedString = java.util.regex.Pattern.quote(path);
			pattern = Pattern.compile(escapedString);
			resources = getResources(ClassPathHandler.getInstance().getTargetProjectClasspath(), pattern);
			if (!resources.isEmpty()) {
				return resources.iterator().next();
			}
		}

		return null;
	}

	/**
	 * Get all resources in the given classpath element (eg folder or jar file)
	 * according to the given pattern
	 * 
	 * @param classPathElement  a single element, or a sequence separated by {@code File.pathSeparator}
	 * @param pattern
	 * @return
	 */
	private static Collection<String> getResources(final String classPathElement,
			final Pattern pattern) throws IllegalArgumentException {

		if(classPathElement.contains(File.pathSeparator)){
			ArrayList<String> retval = new ArrayList<String>();
			for(String element : classPathElement.split(File.pathSeparator)){
				retval.addAll(getResources(element, pattern));
			}
			return retval;
		} else {		
			initialiseCacheEntry(classPathElement);
			return getResourcesFromCache(classPathElement, pattern);
		}
	}
	
	/**
	 * 
	 * @param pattern
	 * @return A InputStream object or null if no resource with this name is
	 *         found
	 */
	private static InputStream getResourceAsStream(final Pattern pattern) {
		String[] classPathElements = ClassPathHandler.getInstance().getTargetProjectClasspath().split(File.pathSeparator);
		for (final String element : classPathElements) {
			InputStream input = getResourceAsStream(element, pattern);
			if (input != null)
				return input;
		}

		return null;
	}



	private static InputStream getResourceAsStream(final String classPathElement,
			final Pattern pattern) throws IllegalArgumentException {

		final File file = new File(classPathElement);

		if (!file.exists()) {
			throw new IllegalArgumentException("The class path resource "
					+ file.getAbsolutePath() + " does not exist");
		}

		InputStream input = null;

		initialiseCacheEntry(classPathElement);
		if (!hasResourcesInCache(classPathElement, pattern)) {
			return null;
		}

		if (file.isDirectory()) {
			try {
				input = getResourceFromDirectoryAsStream(file, pattern,
						file.getCanonicalPath());
			} catch (IOException e) {
				logger.error("Error in getting resources", e);
				throw new RuntimeException(e);
			}
		} else if (file.getName().endsWith(".jar")) {
			input = getResourceFromJarFileAsStream(file, pattern);
		} else {
			throw new IllegalArgumentException("The class path resource "
					+ file.getAbsolutePath() + " is not valid");
		}
		return input;
	}

	private static InputStream getResourceFromJarFileAsStream(final File file,
			final Pattern pattern) {
		ZipFile jf;
		try {
			jf = new ZipFile(file);
		} catch (final Exception e) {
			throw new Error(e);
		}

		final Enumeration<?> e = jf.entries();
		try {
			while (e.hasMoreElements()) {
				final ZipEntry ze = (ZipEntry) e.nextElement();
				final String fileName = ze.getName();
				final boolean accept = pattern.matcher(fileName).matches();
				if (accept) {
					byte[] bytes = IOUtils.toByteArray(jf.getInputStream(ze));
					return new ByteArrayInputStream(bytes);
				}
			}
		} catch (final IOException e1) {
			throw new Error(e1);
		} finally {
			try {
				jf.close();
			} catch (final IOException e1) {
				throw new Error(e1);
			}
		}
		return null;
	}

	private static InputStream getResourceFromDirectoryAsStream(final File directory,
			final Pattern pattern, final String classPathFolder) {

		if (!directory.exists()) {
			return null;
		}
		if (!directory.isDirectory()) {
			return null;
		}
		if (!directory.canRead()) {
			return null;
		}

		final File[] fileList = directory.listFiles();
		for (final File file : fileList) {

			String relativeFilePath = null;
			try {
				relativeFilePath = file.getCanonicalPath().replace(classPathFolder
						+ File.separator,
						"");
			} catch (final IOException e) {
				throw new Error(e);
			}

			//for a file, check complete match
			Matcher matcher = pattern.matcher(relativeFilePath);
			boolean fullMatch = matcher.matches();

			if (file.isDirectory()) {

				boolean prefixMatch = matcher.hitEnd();

				if (!prefixMatch) {
					/*
					 * no point in scanning the folder if the partial match is 
					 * already not satisfied
					 */
					continue;
				}

				/*
				 * recursion till we get to a file that is not a folder.				
				 */
				InputStream input = getResourceFromDirectoryAsStream(file, pattern,
						classPathFolder);

				if (input != null) {
					return input;
				}
			} else if (fullMatch) {
				//it is a file with full matching name
				try {
					return new FileInputStream(file);
				} catch (FileNotFoundException e) {
					throw new Error(e);
				}
			}

		}

		return null;
	}


	private static Collection<String> getResourcesFromJarFile(final File file) {
		final ArrayList<String> retval = new ArrayList<String>();
		ZipFile zf;
		try {
			zf = new ZipFile(file);
		} catch (final Exception e) {
			throw new Error(e);
		}

		final Enumeration<?> e = zf.entries();
		while (e.hasMoreElements()) {
			final ZipEntry ze = (ZipEntry) e.nextElement();
			final String fileName = ze.getName();
			retval.add(fileName);
		}
		try {
			zf.close();
		} catch (final IOException e1) {
			throw new Error(e1);
		}
		return retval;
	}

	private static Collection<String> getResourcesFromDirectory(final File directory,
			final String classPathFolder) {

		final ArrayList<String> retval = new ArrayList<String>();
		if (!directory.exists()) {
			return retval;
		}
		if (!directory.isDirectory()) {
			return retval;
		}
		if (!directory.canRead()) {
			return retval;
		}

		final File[] fileList = directory.listFiles();
		for (final File file : fileList) {
			if (file.isDirectory()) {
				/*
				 * recursion till we get to a file that is not a folder.
				 * The pattern is matched only against files, not folders, and it is based
				 * on their full path names
				 */
				retval.addAll(getResourcesFromDirectory(file, classPathFolder));
			} else {
				try {

					final String relativeFilePath = file.getCanonicalPath().replace(classPathFolder
							+ File.separator,
							"");
					retval.add(relativeFilePath);
				} catch (final IOException e) {
					throw new Error(e);
				}
			}
		}

		return retval;
	}

	private static void initialiseCacheEntry(final String classPathElement) {
		if (classPathCache.containsKey(classPathElement))
			return;

		final ArrayList<String> retval = new ArrayList<String>();
		final File file = new File(classPathElement);

		if (!file.exists()) {
			throw new IllegalArgumentException("The class path resource "
					+ file.getAbsolutePath() + " does not exist");
		}

		if (file.isDirectory()) {
			try {
				retval.addAll(getResourcesFromDirectory(file, file.getCanonicalPath()));
			} catch (IOException e) {
				logger.error("Error in getting resources", e);
				throw new RuntimeException(e);
			}
		} else if (file.getName().endsWith(".jar")) {
			retval.addAll(getResourcesFromJarFile(file));
		} else {
			throw new IllegalArgumentException("The class path resource "
					+ file.getAbsolutePath() + " is not valid");
		}
		classPathCache.put(classPathElement, retval);
	}

	private static Collection<String> getResourcesFromCache(final String classPathEntry,
			final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		for (String entry : classPathCache.get(classPathEntry)) {
			if (pattern.matcher(entry).matches())
				retval.add(entry);
		}
		return retval;
	}

	private static boolean hasResourcesInCache(final String classPathEntry,
			final Pattern pattern) {
		for (String entry : classPathCache.get(classPathEntry)) {
			if (pattern.matcher(entry).matches())
				return true;
		}
		return false;
	}

}
