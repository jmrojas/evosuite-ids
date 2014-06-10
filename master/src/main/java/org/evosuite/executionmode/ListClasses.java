package org.evosuite.executionmode;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.classpath.ClassPathHacker;
import org.evosuite.classpath.ClassPathHandler;
import org.evosuite.classpath.ResourceList;
import org.evosuite.utils.LoggingUtils;

public class ListClasses {

	public static final String NAME = "listClasses";
	
	public static Option getOption(){
		return new Option(NAME, "list the testable classes found in the specified classpath/prefix");
	}
	
	public static Object execute(Options options, CommandLine line) {
		if (line.hasOption("prefix"))
			listClassesPrefix(line.getOptionValue("prefix"));
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
		Set<String> classes = ResourceList.getAllClasses(target, false);
		try {
			ClassPathHacker.addFile(target);
		} catch (IOException e) {
			// Ignore?
		}
		for (String sut : classes) {
			try {
				if (ResourceList.isClassAnInterface(sut)) {
					continue;
				}
			} catch (IOException e) {
				LoggingUtils.getEvoLogger().error("Could not load class: " + sut);
				continue;
			}
			
			String row = "";
			String groupId = Properties.GROUP_ID;
			if(groupId!=null && !groupId.isEmpty() && !groupId.equals("none")){
				row += groupId + "\t";
			}
			row += sut;
			
			LoggingUtils.getEvoLogger().info(row);
		}
	}
	
	private static void listClassesLegacy() {
		File directory = new File(Properties.OUTPUT_DIR);
		String[] extensions = { "task" };
		for (File file : FileUtils.listFiles(directory, extensions, false)) {
			LoggingUtils.getEvoLogger().info(file.getName().replace(".task", ""));
		}
	}

	private static void listClassesPrefix(String prefix) {
		
		String cp = ClassPathHandler.getInstance().getTargetProjectClasspath();
		
		Set<String> classes = new LinkedHashSet<>();
		
		for (String classPathElement : cp.split(File.pathSeparator)) {
			classes.addAll(ResourceList.getAllClasses(classPathElement, prefix, false));
			try {
				ClassPathHacker.addFile(classPathElement);
			} catch (IOException e) {
				// Ignore?
			}
		}
		for (String sut : classes) {
			try {
				if (ResourceList.isClassAnInterface(sut)) {
					continue;
				}
			} catch (IOException e) {
				LoggingUtils.getEvoLogger().error("Could not load class: " + sut);
				continue;
			}
			LoggingUtils.getEvoLogger().info(sut);
		}
	}
}
