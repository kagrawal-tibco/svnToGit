/**
 * 
 */
package com.tibco.cep.driver.http.server.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Utility functions related to Tomcat Server
 * 
 * @author vpatil
 */
public class TomcatServerUtils {
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(TomcatServerUtils.class);

	/**
	 * Load any external properties, these help to add newer properties or
	 * override any existing ones
	 * 
	 * @param propertyName
	 * 
	 * @return
	 */
	public static Properties readPropsFromExternalProps(String propertyName) {
		Properties props = new Properties();
		String filename = null;
		try {
			// Add additional properties
			filename = System.getProperty(propertyName);
			if (filename != null) {
				FileInputStream fis = new FileInputStream(filename);
				props.load(fis);
				fis.close();
			}
		} catch (Exception e) {
			LOGGER.log(
					Level.WARN,
					String.format(
							"Not loading the properties. Check path specified for System property [%s]=%s",
							propertyName, filename));
		}
		return props;
	}
	
	/**
	 * Check if a specified port is available
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isPortAvailable(int port) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
			socket.setReuseAddress(true);
			return true;
		} catch (IOException e) {
			LOGGER.log(
					Level.WARN,
					String.format(
							"Port [%s] is not avaliable",
							port));
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					LOGGER.log(
							Level.WARN,
							String.format(
									"Error closing port [%s]",
									port));
				}
			}
		}

		return false;

	}
}
