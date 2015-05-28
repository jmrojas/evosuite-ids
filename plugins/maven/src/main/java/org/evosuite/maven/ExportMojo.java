package org.evosuite.maven;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.evosuite.continuous.ContinuousTestGeneration;

/**
 * When run, EvoSuite generate tests in a specific folder.
 * New runs of EvoSuite can exploit the tests in such folder, and/or modify them.
 * 
 * <p>
 * So, with "export" we can copy all generated tests to a specific folder, which
 * by default points to where Maven searches for tests.
 * If another folder is rather used (or if we want to run with Maven the tests in the default EvoSuite folder),
 * then Maven plugins like build-helper-maven-plugin are needed 
 * 
 * @author arcuri
 *
 */
@Mojo( name = "export")
public class ExportMojo extends AbstractMojo{


	@Parameter( property = "targetFolder", defaultValue = "src/test/java" )
	private String targetFolder;
	
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;
	
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		getLog().info("Exporting tests");

		File basedir = project.getBasedir();

		try {
			boolean exported = ContinuousTestGeneration.exportToFolder(basedir.getAbsolutePath(),targetFolder);
			if(!exported){
				getLog().info("Nothing to export");
				return;
			}
		} catch (IOException e) {
			String msg = "Error while exporting tests: "+e.getMessage();
			getLog().error(msg);
			throw new MojoFailureException(msg);
		}

		File target = new File(basedir.getAbsolutePath()+File.separator+targetFolder);
		getLog().info("Exported tests to "+target);
	}

}
