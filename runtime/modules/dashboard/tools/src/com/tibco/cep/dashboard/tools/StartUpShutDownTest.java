package com.tibco.cep.dashboard.tools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

public class StartUpShutDownTest {
	
	protected CommandLineArguments arguments;

	protected DashboardAgentClient client;
	
	protected Logger logger;
	
	protected void setup(String[] args) throws IllegalArgumentException, IllegalStateException {
		logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
		arguments = new CommandLineArguments(args);
		String potentialURL = arguments.pop();
		try {
			int potentialPortNumber = Integer.parseInt(arguments.peek());
			arguments.pop();
			try {
				client = new RemoteDashboardAgentClient(potentialURL,potentialPortNumber);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException("Invalid pull request URL : "+potentialURL,e);
			}
		} catch (NumberFormatException ex) {
			File localConfigFile = new File(potentialURL);
			if (localConfigFile.exists() == true){
				try {
					client = new LocalDashboardAgentClient(localConfigFile);
				} catch (ManagementException e) {
					throw new IllegalStateException("Unable to start local client configuration using "+potentialURL,e);
				}
			}
			else {
				throw new IllegalArgumentException("Invalid property file name for local client configuration : "+potentialURL);
			}
		}
	}

	protected void start() throws IOException {
		client.start();
	}

	protected void pause() throws IOException {
		client.pause();
	}

	protected void unpause() throws IOException {
		client.resume();
	}

	protected void shutdown() {
		if (client != null) {
			try {
				client.stop();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		StartUpShutDownTest test = new StartUpShutDownTest();
		try {
			test.setup(args);
			test.start();
			test.pause();
		} catch (IllegalArgumentException ex){
			System.err.println(ex.getMessage());
			System.err.println("Usage : java "+StartUpShutDownTest.class.getName()+" [<localfilename>]/[<remotepullrequesturl> <portnumber>]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null) {
				test.shutdown();
			}
		}
	}

}