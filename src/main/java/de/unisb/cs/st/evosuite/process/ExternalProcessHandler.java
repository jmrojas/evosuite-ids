package de.unisb.cs.st.evosuite.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import de.unisb.cs.st.evosuite.Properties;

/*
 * this code should be used by the main process
 */

public class ExternalProcessHandler {
	protected static Logger logger = Logger.getLogger(ExternalProcessHandler.class);

	protected ServerSocket server;
	protected Process process;
	protected int port;
	protected String last_command;
	protected Thread output_printer;
	protected Thread message_handler;

	protected Socket connection;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;

	protected Object final_result;
	protected final Object MONITOR = new Object();

	public ExternalProcessHandler() {
		this.port = Properties.PROCESS_COMMUNICATION_PORT;

		//the following thread is important to make sure that the external process is killed
		//when current process ends

		Thread t = new Thread() {
			@Override
			public void run() {
				killProcess();
				closeServer();
			}
		};

		Runtime.getRuntime().addShutdownHook(t);
	}

	public boolean startProcess(String command) {
		return startProcess(command, null);
	}

	protected boolean startProcess(String command, Object population_data) {
		if (process != null) {
			logger.debug("already running an external process");
			return false;
		}

		//first, start a TCP server for communications.
		//this needs to be done only once
		if (server == null) {
			try {
				server = new ServerSocket(port);
				server.setSoTimeout(3000);
			} catch (Exception e) {
				logger.debug("not possible to start TCP server: ", e);
				return false;
			}
		}

		// now start the process

		File dir = new File(System.getProperty("user.dir"));
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(dir);
		builder.redirectErrorStream(true);

		try {
			process = builder.start();
		} catch (IOException e) {
			logger.debug("not possible to start external process");
			return false;
		}

		startExternalProcessPrinter();

		//wait for connection from external process

		try {
			connection = server.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());

			if (population_data == null) {
				//tell the external process to start search from scratch
				out.writeObject(Messages.NEW_SEARCH);
				out.flush();
			} else {
				out.writeObject(Messages.CONTINUE_SEARCH);
				out.flush();
				out.writeObject(population_data);
				out.flush();
			}
		} catch (Exception e) {
			logger.debug("error when waiting for connection from external process", e);
			return false;
		}

		startExternalProcessMessageHandler();

		last_command = command;

		return true;
	}

	public void killProcess() {
		if (process != null)
			process.destroy();
		process = null;

		if (output_printer != null && output_printer.isAlive())
			output_printer.interrupt();
		output_printer = null;

		if (message_handler != null && message_handler.isAlive())
			message_handler.interrupt();
		message_handler = null;
	}

	public void closeServer() {
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				logger.debug("error in closing the TCP server", e);
			}

			server = null;
		}
	}

	protected void startExternalProcessPrinter() {
		output_printer = new Thread() {
			@Override
			public void run() {
				try {
					BufferedReader proc_in = new BufferedReader(new InputStreamReader(
					        process.getInputStream()));
					String data = "";
					while (data != null) {
						data = proc_in.readLine();
						if (data != null)
							logger.debug("<External Process> " + data);
					}
				} catch (Exception e) {
					logger.debug(e.toString());
				}
			}
		};

		output_printer.start();
	}

	protected void startExternalProcessMessageHandler() {
		if (message_handler != null && message_handler.isAlive())
			return;

		message_handler = new Thread() {
			@Override
			public void run() {
				boolean read = true;
				while (read) {
					try {
						String message = (String) in.readObject();
						Object data = in.readObject();

						if (message.equals(Messages.FINISHED_COMPUTATION)) {
							read = false;
							killProcess();
							final_result = data;
							synchronized (MONITOR) {
								MONITOR.notifyAll();
							}
						} else if (message.equals(Messages.NEED_RESTART)) {
							//now data represent the current generation
							killProcess();
							startProcess(last_command, data);
						} else {
							logger.debug("error, received invalid message: " + message);
						}
					} catch (Exception e) {
						logger.debug("error in reading message", e);
						return;
					}
				}
			}
		};

		message_handler.start();
	}

	public Object waitForResult(int timeout) {
		try {
			synchronized (MONITOR) {
				MONITOR.wait(timeout);
			}
		} catch (InterruptedException e) {
		}

		return final_result;
	}

}
