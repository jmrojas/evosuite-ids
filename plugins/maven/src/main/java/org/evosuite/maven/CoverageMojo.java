package org.evosuite.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.eclipse.aether.RepositorySystemSession;
import org.evosuite.maven.util.EvoSuiteRunner;

/**
 * Execute the manually written test suites (usually located under src/test/java)
 * and return the coverage of each class.
 * 
 * @author José Campos
 */
@Mojo( name = "coverage", requiresProject = true, requiresDependencyResolution = ResolutionScope.RUNTIME, requiresDependencyCollection = ResolutionScope.RUNTIME )
public class CoverageMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	@Parameter(defaultValue = "${plugin.artifacts}", required = true, readonly = true)
	private List<Artifact> artifacts;

	@Component
	private ProjectBuilder projectBuilder;

	@Parameter(defaultValue="${repositorySystemSession}", required = true, readonly = true)
	private RepositorySystemSession repoSession;

	/**
	 * Coverage criterion. Can define more than one criterion by using a ':' separated list
	 */
	// FIXME would be nice to have the value of Properties.CRITERION but seems to be not possible
	@Parameter( property = "criterion", defaultValue = "LINE:BRANCH:EXCEPTION:WEAKMUTATION:OUTPUT:METHOD:METHODNOEXCEPTION:CBRANCH" )
	private String criterion;

	@Override
	public void execute() throws MojoExecutionException,MojoFailureException {

		getLog().info("Going to measure the coverage of manually written test cases with EvoSuite");

		String junit = null;
		String target = null;
		String cp = null;

		try {
			// Compile elements (i.e., classes under /src/main/java)
			for (String element : this.project.getCompileClasspathElements()) {

				if (element.endsWith(".jar")) { // we only target what has been compiled to a folder
					continue;
				}

				File file = new File(element);
				if (!file.exists()) {
					/*
					 * don't add to target an element that does not exist
					 */
					continue;
				}

				if (target == null) {
					target = element;
				} else {
					target = target + File.pathSeparator + element;
				}
			}
			// JUnit elements (i.e., classes under /src/test/java)
			for (String element : this.project.getTestClasspathElements()) {

				if (element.endsWith(".jar")) {  // we only target what has been compiled to a folder
					continue;
				}
				if (target.contains(element)) { // we don't want to also consider classes from /src/main/java
					continue;
				}

				File file = new File(element);
				if (!file.exists()) {
					/*
					 * don't add to target an element that does not exist
					 */
					continue;
				}

				if (junit == null) {
					junit = element;
				} else {
					junit = junit + File.pathSeparator + element;
				}
			}
			// Runtime elements
			for (String element : this.project.getRuntimeClasspathElements()) {

				File file = new File(element);
				if (!file.exists()) {
					/*
					 * don't add to CP an element that does not exist
					 */
					continue;
				}

				if (cp == null) {
					cp = element;
				} else {
					cp = cp + File.pathSeparator + element;
				}
			}
		} catch (DependencyResolutionRequiredException e) {
			getLog().error("Error: " + e.getMessage(), e);
			return ;
		}

		if (junit == null || target == null || cp == null){
			getLog().info("Nothing to measure coverage!");
			return ;
		}

		List<String> params = new ArrayList<>();
		params.add("-measureCoverage");
		params.add("-target");
		params.add(target);
		params.add("-DCP=" + cp + File.pathSeparator + junit);
		params.add("-Dcriterion="+criterion);

		getLog().info("Params: " + params.toString());

		EvoSuiteRunner runner = new EvoSuiteRunner(getLog(), this.artifacts, this.projectBuilder, this.repoSession);
		runner.registerShutDownHook();
		boolean ok = runner.runEvoSuite(this.project.getBasedir().toString(), params);

		if (!ok) {
			throw new MojoFailureException("Failed to correctly execute EvoSuite");
		}
    }
}
