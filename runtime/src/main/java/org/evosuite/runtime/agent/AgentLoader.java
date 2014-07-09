package org.evosuite.runtime.agent;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.VirtualMachine;

/**
 * This class is responsible to load the jar with the agent
 * definition (in its manifest) and then hook it to the current
 * running JVM 
 * 
 * @author arcuri
 *
 */
public class AgentLoader {

	private static final Logger logger = LoggerFactory.getLogger(AgentLoader.class);

	private static volatile boolean alreadyLoaded = false; 

	public synchronized static void loadAgent() throws RuntimeException{

		if(alreadyLoaded){
			return;
		}

		logger.info("dynamically loading javaagent");
		String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
		int p = nameOfRunningVM.indexOf('@');
		String pid = nameOfRunningVM.substring(0, p);

		String jarFilePath = getJarPath();
		if(jarFilePath==null){
			throw new RuntimeException("Cannot find either the compilation target folder nor the EvoSuite jar in classpath: "+System.getProperty("java.class.path"));
		} else {
			logger.info("Using JavaAgent in "+jarFilePath);
		}

		/*
		 * We need to use reflection on a new instantiated ClassLoader because
		 * we can make no assumption whatsoever on the class loader of AgentLoader
		  *
		  * TODO: it is likely that here, instead of null, we should access to an environment variable
		  * to identify where tools.jar is.
		  * This is because maybe there can be problems if tests generated on local machine are then
		  * published on a remote Continuous Integration server
		 */
		ClassLoader toolLoader = new ToolsJarLocator(null).getLoaderForToolsJar();

		logger.info("Classpath: "+System.getProperty("java.class.path"));

		try {
			logger.info("Going to attach agent to process "+pid);

			// FIXME giving problems with library loading
						  
			Class<?> string = toolLoader.loadClass("java.lang.String");
			Class<?> clazz = toolLoader.loadClass("com.sun.tools.attach.VirtualMachine");
			Method attach = clazz.getMethod("attach", string);

			Object instance = attach.invoke(null, pid);

			Method loadAgent = clazz.getMethod("loadAgent", string, string);
			loadAgent.invoke(instance, jarFilePath, "");

			Method detach = clazz.getMethod("detach");
			detach.invoke(instance);
			
			
			
			//FIXME: This will fail if tools.jar not on classpath
			/*
			VirtualMachine vm = VirtualMachine.attach(pid);
			vm.loadAgent(jarFilePath, "");
			vm.detach(); 
			*/
		} catch (Exception e) {
			Throwable cause = e.getCause();
			String causeDescription = cause==null ? "" : " , cause "+cause.getClass()+" "+cause.getMessage();
			logger.error("Exception "+e.getClass()+": "+e.getMessage()+causeDescription,e);
			try {
				Thread.sleep(5000);
				logger.error("Trying again:");
				logger.error("VM: "+nameOfRunningVM);
				logger.error("PID: "+pid);
				Class<?> string = toolLoader.loadClass("java.lang.String");
				Class<?> clazz = toolLoader.loadClass("com.sun.tools.attach.VirtualMachine");
				Method attach = clazz.getMethod("attach", string);

				Object instance = attach.invoke(null, pid);

				Method loadAgent = clazz.getMethod("loadAgent", string, string);
				loadAgent.invoke(instance, jarFilePath, "");

				Method detach = clazz.getMethod("detach");
				detach.invoke(instance);
			} catch(Exception e2) {
				throw new RuntimeException(e2);				
			}
		}

		alreadyLoaded = true;
	}

	private static boolean isEvoSuiteMainJar(String path) throws IllegalArgumentException{
		
		File file = new File(path);
		if(!file.exists()){
			throw new IllegalArgumentException("Non-existing file "+path);
		}
		
		String name = file.getName();

		if(name.toLowerCase().contains("evosuite") && name.endsWith(".jar")){
			try (JarFile jar = new JarFile(file);){
				Manifest manifest = jar.getManifest();
				if(manifest == null){
					return false;
				}
				
				Attributes attributes = manifest.getMainAttributes();
				String agentClass = attributes.getValue("Agent-Class");
				String agent = InstrumentingAgent.class.getName(); // this is hardcoded in the pom.xml file
				if(agentClass != null && agentClass.trim().equalsIgnoreCase(agent)){
					return true; 
				}
			} catch (IOException e) {
				return false;
			}
		}

		return false;
	}


	private static String getJarPath(){
		String jarFilePath = null;	
		String classPath = System.getProperty("java.class.path");
		String[] tokens = classPath.split(File.pathSeparator); 

		for(String entry : tokens){
			if(entry==null || entry.isEmpty()){
				continue;
			}
			if(isEvoSuiteMainJar(entry)){
				jarFilePath = entry;
				break;
			}
		}

		if(jarFilePath==null){
			jarFilePath = searchInCurrentClassLoaderIfUrlOne();    
		}

		if(jarFilePath==null){
			/*
			 * this could happen in Eclipse or during test execution in Maven, and so search in compilation 'target' folder 
			 */    			
			jarFilePath = searchInFolder("target");    			
		}

		if(jarFilePath==null){
			/*
			 * this could happen in Eclipse or during test execution in Maven, and so search in compilation 'target' folder 
			 */    			
			jarFilePath = searchInFolder("lib");    			
		}

		if(jarFilePath==null){
			/*
			 * nothing seems to work, so try .m2 folder
			 */
			//this can really mess up things
			//jarFilePath = searchInM2();    			
		}

		return jarFilePath; 
	}

	private static String searchInCurrentClassLoaderIfUrlOne() {

		Set<URI> uris = new HashSet<URI>();

		ClassLoader loader = AgentLoader.class.getClassLoader();
		while(loader != null){
			if(loader instanceof URLClassLoader){
				URLClassLoader urlLoader = (URLClassLoader) loader;
				for(URL url : urlLoader.getURLs()){
					try {
						URI uri = url.toURI();
						uris.add(uri);

						File file = new File(uri);
						if(isEvoSuiteMainJar(file.getAbsolutePath())){
							return file.getAbsolutePath();
						}
					} catch (Exception e) {
						logger.error("Error while parsing URL "+url);
						continue;
					}
				}
			}

			loader = loader.getParent();
		}

		String msg = "Failed to find EvoSuite jar in current classloader. URLs of classloader:";
		for(URI uri : uris){
			msg += "\n"+uri.toString();
		}
		logger.warn(msg);

		return null;
	}

	@Deprecated
	private static String searchInM2() {

		File home = new File(System.getProperty("user.home"));
		File m2 = new File(home.getAbsolutePath()+"/.m2");
		if(!m2.exists()){
			logger.debug("Cannot find the .m2 folder in home directory in "+m2);
			return null;
		}

		//FIXME we would need a more robust approach, as this is just an hack for now
		String relativePath = "/repository/org/evosuite/evosuite/0.1-SNAPSHOT/evosuite-0.1-SNAPSHOT-jar-minimal.jar";
		File jar = new File(m2.getAbsolutePath()+relativePath);

		if(!jar.exists()){
			logger.debug("No jar file at: "+jar);
			return null;
		} else {
			return jar.getAbsolutePath();
		}
	}

	private static String searchInFolder(String folder) {
		File target = new File(folder);
		if(!target.exists()){
			logger.debug("No target folder "+target.getAbsolutePath());
			return null;
		}

		if(!target.isDirectory()){
			logger.debug("'target' exists, but it is not a folder");
			return null;
		}

		for(File file : target.listFiles()){
			String path = file.getAbsolutePath();
			if(isEvoSuiteMainJar(path)){
				return path;
			}
		}

		return null;
	}
}