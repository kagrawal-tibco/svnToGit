package com.tibco.cep.driver.hawk.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class TestConnection {

	public static boolean testConnection(String serverUrl, String userName, String password) {
		ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
		try {
			Connection connection = factory.createConnection(userName, password);
			if (connection != null) {
				return true;
			}
		} catch (JMSException e) {
			return false;
		}

		return false;
	}

}
