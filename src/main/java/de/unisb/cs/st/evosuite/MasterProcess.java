/**
 * 
 */
package de.unisb.cs.st.evosuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.unisb.cs.st.evosuite.utils.ExternalProcessHandler;

/**
 * @author Gordon Fraser
 * @author Andrea Arcuri
 * 
 */
public class MasterProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalProcessHandler handler = new ExternalProcessHandler();
		handler.openServer();
		int port = handler.getServerPort();
		List<String> cmdLine = new ArrayList<String>();
		cmdLine.addAll(Arrays.asList(args));
		cmdLine.add(cmdLine.size() - 1, "-Dprocess_communication_port=" + port);
		String[] newArgs = cmdLine.toArray(new String[cmdLine.size()]);
		if (handler.startProcess(newArgs)) {
			handler.waitForResult(Properties.GLOBAL_TIMEOUT * 2 * 1000); // FIXXME
		} else {
			System.out.println("Could not connect to client process");
		}
		System.exit(0);
	}
}
