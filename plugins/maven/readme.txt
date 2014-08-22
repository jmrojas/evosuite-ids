(Working in progress notes on how to use the EvoSuite Maven Plugin).


EvoSuite has a Maven Plugin that can be used to generate new test cases as part of the build.
This has at least the following advantages:
- Can run EvoSuite from Continuous Integration servers (eg Jenkins) with minimal configuration overheads
- Generated tests can be put directly on the classpath of the system based on the pom.xml files
- No need to install EvoSuite on local machine (Maven will take care of it automatically)

To enable the use of the plugin, it needs to be configured in the pom.xml of the target project.
For example:

<pluginManagement>
	<plugins>
		<plugin>
			<groupId>org.evosuite.plugins</groupId>
			<artifactId>evosuite-maven-plugin</artifactId>
			<version>${evosuiteVersion}</version>
		</plugin>
    </plugins>
</pluginManagement>


where ${evosuiteVersion} specify the version to use. For example, "0.1.0".

Note: currently EvoSuite is not hosted yet on Maven Central. So, you need to download
its source code and install it locally, ie "mvn install". Then, you can use the
created snapshot, eg "0.1.0-SNAPSHOT".

Beside configuring the plugin, there is also the need to add the EvoSuite runtime, which
is used by the generated test cases. This can be done by adding the following Maven
dependency in the pom.xml:

<dependency>
	<groupId>org.evosuite</groupId>
	<artifactId>evosuite-runtime</artifactId>
	<version>${evosuiteVersion}</version>
	<scope>test</scope>
</dependency> 


The "evosuite" plugin provides the following targets:

1) "generate" ->  this is used to generate test cases with EvoSuite. Tests will be generated for
all classes in all submodules. This target as the following parameters:
- "memoryInMB": total amount of megabytes EvoSuite is allowed to allocate (default 800)
- "cores": total number of CPU cores EvoSuite can use (default 1)
- "timeInMinutesPerClass": how many minutes EvoSuite can spend generating tests for each class (default 2)


2) "info" -> provide info on all the generated tests so far 


3) "export" -> by default, EvoSuite creates the tests in the ".continuous_evosuite/evosuite-tests" folder.
By using "export", the generated tests will be copied over to another folder, which can
be set with the "targetFolder" option (default value is "src/test/java").
Note: if you do not export the tests into "src/test/java" with "mvn evosuite:export", then
commands like "mvn test" will not execute such tests, as their source code is not on the
build path. You can add custom source folders with "build-helper-maven-plugin" plugin, eg:

<plugin>
	<groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <version>1.8</version>
    <executions>
        <execution>
            <id>add-test-source</id>
            <phase>generate-test-sources</phase>
            <goals>
                <goal>add-test-source</goal>
            </goals>
            <configuration>
            	<sources>
                    <source>.continuous_evosuite/evosuite-tests</source>
            	</sources>
            </configuration>
        </execution>
    </executions>
</plugin>


4) "clean" -> delete _all_ data in the ".continuous_evosuite" folder, which is used to
store all the best tests generated so far.

------------------

Usage example:

mvn -DmemoryInMB=2000 -Dcores=2 evosuite:generate evosuite:export  test 

This will generate tests for all classes using 2 cores and 2GB of memory, copy the generated
tests to "src/test/java" and then execute them.
Note: if the project has already some tests, those will be executed as well as part
of the regular "test" phase.


------------------

Clover issues:
if the system has been instrumented with Clover, then the generation of new tests with EvoSuite
might fail. This can happen if Clover's runtime libraries are not on the classpath.
Either you need to be sure of having all needed libraries on the classpath, or just
simply make a clean build (e.g., "mvn clean compile") before calling the EvoSuite plugin.






 