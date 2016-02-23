/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.jenkins.scm;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardUsernameCredentials;
import com.cloudbees.plugins.credentials.domains.URIRequirementBuilder;

import org.eclipse.jgit.transport.URIish;
import org.evosuite.jenkins.recorder.EvoSuiteRecorder;
import org.jenkinsci.plugins.gitclient.CliGitAPIImpl;
import org.jenkinsci.plugins.gitclient.GitClient;
import org.jenkinsci.plugins.gitclient.PushCommand;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.plugins.git.Branch;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.UserRemoteConfig;
import hudson.security.ACL;
import jenkins.plugins.git.GitSCMSource;

/**
 * Git wrapper to handle git commands, such commit and push.
 * 
 * @author José Campos
 */
public class Git implements SCM {

	private GitClient gitClient;
	private final String remote;

	public Git(GitSCM gitSCM, AbstractBuild<?, ?> build, BuildListener listener)
			throws IOException, InterruptedException {

		String gitExe = gitSCM.getGitExe(build.getBuiltOn(), listener);
		EnvVars environment = build.getEnvironment(listener);

		this.gitClient = org.jenkinsci.plugins.gitclient.Git.with(listener, environment).in(build.getWorkspace())
				.using(gitExe) // only if you want to use Git CLI
				.getClient();

		// get remote configurations, e.g., URL
		List<UserRemoteConfig> remotes = gitSCM.getUserRemoteConfigs();
		UserRemoteConfig remoteConfig = remotes.get(0);
		this.remote = remoteConfig.getUrl();
		listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Remote config " + remoteConfig.toString());

		// get key's (i.e., username-password, ssh key, etc) hash
		String credentialID = remoteConfig.getCredentialsId();
		if (credentialID == null || credentialID.equals("null")) {
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX
					+ "No credentials defined, therefore new test cases generated by EvoSuite will not be pushed.");
			// TODO: should we throw an exception and make the build fail?
		} else {
			// get key (i.e., username-password or ssh key / passphrase)
			StandardUsernameCredentials credentials = this.getCredentials(credentialID);
			this.gitClient.setCredentials(credentials);
			this.gitClient.addDefaultCredentials(credentials);
		}
	}

	@Override
	public boolean commit(AbstractBuild<?, ?> build, BuildListener listener, String branchName) {
		try {
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Commiting new test cases");

			Set<String> branches = this.getBranches();
			if (!branches.contains(branchName)) {
				// create a new branch called "evosuite-tests" to commit and
				// push the new generated test suites
				listener.getLogger()
						.println(EvoSuiteRecorder.LOG_PREFIX + "There is no branch called " + branchName);
				this.gitClient.branch(branchName);
			}

			this.gitClient.setAuthor("jenkins", "jenkins@localhost.com");
			this.gitClient.setCommitter("jenkins", "jenkins@localhost.com");
			this.gitClient.checkoutBranch(branchName, "HEAD");

			// parse list of new and modified files
			String status = ((CliGitAPIImpl) this.gitClient).launchCommand("ls-files", "--deleted", "--modified",
					"--others", SCM.TESTS_DIR_TO_COMMIT);
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Status (" + status.length() + "):\n" + status);

			if (status.isEmpty()) {
				listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Nothing to commit");
				return true;
			}

			for (String toCommit : status.split("\\R")) {
				listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "commit: " + toCommit);
				// TODO check whether toCommit (which is a path of a file) exists
				this.gitClient.add(toCommit);
			}

			// commit
			String commit_msg = "EvoSuite Jenkins Plugin #" + "evosuite-"
					+ build.getProject().getName().replace(" ", "_") + "-" + build.getNumber();
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + commit_msg);
			this.gitClient.commit(commit_msg);

		} catch (InterruptedException e) {
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Commit failed " + e.getMessage());
			e.printStackTrace();
			this.rollback(build, listener);
			return false;
		}

		return true;
	}

	@Override
	public boolean push(AbstractBuild<?, ?> build, BuildListener listener, String branchName) {
		try {
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Pushing new test cases");

			PushCommand p = this.gitClient.push();
			p.ref(branchName);
			p.to(new URIish("origin"));
			p.force().execute();

		} catch (InterruptedException | URISyntaxException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public void rollback(AbstractBuild<?, ?> build, BuildListener listener) {
		try {
			listener.getLogger().println(EvoSuiteRecorder.LOG_PREFIX + "Rollback, cleaning up workspace");
			this.gitClient.clean();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private StandardUsernameCredentials getCredentials(String credentialsID) {
		GitSCMSource source = new GitSCMSource("id", this.remote, credentialsID, "*", "", false);

		return CredentialsMatchers.firstOrNull(
				CredentialsProvider.lookupCredentials(StandardUsernameCredentials.class, source.getOwner(), ACL.SYSTEM,
						URIRequirementBuilder.fromUri(source.getRemoteName()).build()),
				CredentialsMatchers.allOf(CredentialsMatchers.withId(credentialsID), GitClient.CREDENTIALS_MATCHER));
	}

	private Set<String> getBranches() throws InterruptedException {
		Set<String> branches = new LinkedHashSet<String>();
		for (Branch branch : this.gitClient.getBranches()) {
			String[] parts = branch.getName().split("/");
			branches.add(parts[parts.length - 1]);
		}
		return branches;
	}
}
