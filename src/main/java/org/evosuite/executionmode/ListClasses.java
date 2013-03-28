package org.evosuite.executionmode;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.utils.ClassPathHacker;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.ResourceList;

public class ListClasses {

	public static final String NAME = "listClasses";
	
	public static Option getOption(){
		return new Option(NAME, "list the testable classes found in the specified classpath/prefix");
	}
	
	public static Object execute(Options options, CommandLine line, String cp) {
		if (line.hasOption("prefix"))
			listClassesPrefix(line.getOptionValue("prefix"), cp);
		else if (line.hasOption("target"))
			listClassesTarget(line.getOptionValue("target"));
		else if (EvoSuite.hasLegacyTargets())
			listClassesLegacy();
		else {
			LoggingUtils.getEvoLogger().error("Please specify target prefix ('-prefix' option) or classpath entry ('-target' option) to list testable classes");
			Help.execute(options);
		}
		return null;
	}


	private static void listClassesTarget(String target) {
		Pattern pattern = Pattern.compile("[^\\$]*.class");
		Collection<String> resources = ResourceList.getResources(pattern, target);
		try {
			ClassPathHacker.addFile(target);
		} catch (IOException e) {
			// Ignore?
		}
		for (String resource : resources) {
			try {
				if (EvoSuite.isInterface(resource)) {
					continue;
				}
			} catch (IOException e) {
				LoggingUtils.getEvoLogger().error("Could not load class: " + resource);
				continue;
			}
			LoggingUtils.getEvoLogger().info(resource.replace(".class", "").replace('/',
			                                                                        '.'));
		}
	}
	private static void listClassesLegacy() {
		File directory = new File(Properties.OUTPUT_DIR);
		String[] extensions = { "task" };
		for (File file : FileUtils.listFiles(directory, extensions, false)) {
			LoggingUtils.getEvoLogger().info(file.getName().replace(".task", ""));
		}
	}

	private static void listClassesPrefix(String prefix, String cp) {
		Pattern pattern = Pattern.compile(prefix.replace("\\.", "/") // FIXME replace "/" by File.separator? (not sure)
		        + "[^\\$]*.class");
		Set<String> resources = new HashSet<String>();
		for (String classPathElement : cp.split(File.pathSeparator)) {
			resources.addAll(ResourceList.getResources(pattern, classPathElement));
			try {
				ClassPathHacker.addFile(classPathElement);
			} catch (IOException e) {
				// Ignore?
			}
		}
		for (String resource : resources) {
			try {
				if (EvoSuite.isInterface(resource)) {
					continue;
				}
			} catch (IOException e) {
				LoggingUtils.getEvoLogger().error("Could not load class: " + resource);
				continue;
			}
			LoggingUtils.getEvoLogger().info(resource.replace(".class", "").replace('/',
			                                                                        '.'));
		}
	}
}
