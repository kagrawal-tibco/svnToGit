package com.tibco.tea.agent.be.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.remote.rmi.RMIConnectorServer;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnServer;
import com.tibco.tea.agent.be.BETeaAgentManager;
import com.tibco.tea.agent.be.version.be_teagentVersion;
import com.tibco.tea.agent.internal.MachineUtil;

/**
 * This class is used to launch/start the Business Event TEA agent.
 * 
 * @author dijadhav
 *
 */
public class BETeaAgentLauncher {

	// Logger Variable
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaAgentLauncher.class);

	// Array of command line arguments
	private String[] args;

	// BE TEA agent profile file
	private String agentPropFile;

	private Properties configuration;

	/**
	 * Initialize the argument
	 * 
	 * @param args
	 *            - Array of command line arguments
	 */
	public BETeaAgentLauncher(String[] args) {
		this.args = args;
	}

	/**
	 * Main method of application.
	 * 
	 * @param args
	 *            - Array of command line argument
	 * @throws Exception
	 */
	public static void main(String[] args) {
		try {
			String prologue = printPrologue(args);
			LOGGER.log(Level.INFO, prologue);
			BETeaAgentLauncher beAgentLauncher = new BETeaAgentLauncher(args);
			if (!beAgentLauncher.parseArgs()) {
				return;
			}
			Properties configuration = beAgentLauncher.getConfiguration();
			LogManagerFactory.init(null);

			registerJMXConnectorServer(configuration);

			beAgentLauncher.launch();
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, "Failed to launch "+be_teagentVersion.getComponent(), ex);
		}
	}

	/**
	 * Launch the BE TEA agent
	 * 
	 * @throws Exception
	 */
	private void launch() throws Exception {

		LOGGER.log(Level.INFO, "Starting "+be_teagentVersion.getComponent());
		// Initialize the BETeaAgent and register the the agent
		BETeaAgentManager instance = BETeaAgentManager.INSTANCE;
		instance.init(configuration);
		instance.start();
		if (instance.isStarted())
			LOGGER.log(Level.INFO, be_teagentVersion.getComponent() + " started successfully.");
		else {
			LOGGER.log(Level.INFO, "Failed to start " + be_teagentVersion.getComponent());
			instance.stop();
		}

	}

	/**
	 * Parse the command line arguments
	 */
	private boolean parseArgs() {
		if (this.args.length >= 2) {

			LOGGER.log(Level.DEBUG, "Parsing commandline arguments");

			if (args[0].compareTo("-propFile") == 0) {
				agentPropFile = args[1];
			}

			LOGGER.log(Level.DEBUG, "Parsed commandline arguments");
			return true;
		} else {
			usage();
			return false;
		}
	}

	/**
	 * Show usage error for property file
	 */
	private void usage() {
		LOGGER.log(Level.ERROR, " -propFile <filename>");
	}

	static public String printPrologue(String args[]) {

		final StringBuffer buffer = new StringBuffer(be_teagentVersion.line_separator)
				.append(be_teagentVersion.asterisks).append(be_teagentVersion.line_separator).append("\t")
				.append(be_teagentVersion.getComponent()).append(" ").append(be_teagentVersion.version).append(".")
				.append(be_teagentVersion.build).append(" (").append(be_teagentVersion.buildDate).append(")");

		buffer.append(be_teagentVersion.line_separator).append("\t").append("Using arguments :");
		final StringBuffer userArgs = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			userArgs.append(args[i]).append(" ");
		}

		buffer.append(userArgs).append(be_teagentVersion.line_separator).append("\t")
				.append(be_teagentVersion.copyright).append(be_teagentVersion.line_separator)
				.append(be_teagentVersion.line_separator).append(be_teagentVersion.asterisks)
				.append(be_teagentVersion.line_separator);

		return buffer.toString();
	}

	public Properties getConfiguration() throws Exception {
		if (configuration == null) {
			configuration = new Properties();
			configuration.load(new FileInputStream(new File(agentPropFile)));

			updateSystemProperties(configuration);
		}
		return configuration;
	}

	/**
	 * Reads the values from configuration and sets them as system property.
	 * 
	 * @param configuration
	 */
	private static void updateSystemProperties(Properties configuration) {
		setAsSystemProperty(ConfigProperty.BE_TEA_AGENT_SSH_PRIVATEKEY_FILE.getPropertyName(),
				(String) ConfigProperty.BE_TEA_AGENT_SSH_PRIVATEKEY_FILE.getValue(configuration));
		setAsSystemProperty(ConfigProperty.BE_TEA_AGENT_SSH_PRIVATEKEY_PASSPHRASE.getPropertyName(),
				(String) ConfigProperty.BE_TEA_AGENT_SSH_PRIVATEKEY_PASSPHRASE.getValue(configuration));
	}

	private static void setAsSystemProperty(String key, String value) {
		if (key != null && value != null) {
			System.setProperty(key, value);
		}
	}

	private static void registerJMXConnectorServer(Properties configuration) {
		Map<String, Object> env = new HashMap<String, Object>();

		try {
			int jmxPort = Integer
					.parseInt(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JMX_PORT.getPropertyName(),
							ConfigProperty.BE_TEA_AGENT_JMX_PORT.getDefaultValue()));
			// TODO : Revisit to handle authentication enabled cases

			boolean useSinglePort = Boolean.parseBoolean(
					configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JMX_USE_SINGLEPORT.getPropertyName(),
							ConfigProperty.BE_TEA_AGENT_JMX_USE_SINGLEPORT.getDefaultValue()));

			boolean useSsl = Boolean
					.parseBoolean(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JMX_USE_SSL.getPropertyName(),
							ConfigProperty.BE_TEA_AGENT_JMX_USE_SSL.getDefaultValue()));
			if (useSsl) {
				SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
				SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
				env.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE, csf);
				env.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE, ssf);
				useSinglePort = false;
			}
			if (useSinglePort)
				JMXConnServer.createSinglePortSecureJMXConnectorServer(MachineUtil.getHostAddress(), jmxPort, env);
			else
				JMXConnServer.createSecureJMXConnectorServer(MachineUtil.getHostAddress(), jmxPort, env);

		} catch (IOException e) {
			LOGGER.log(Level.ERROR, "Failed to initialize JMX Connection server", e);
		}

	}

}
