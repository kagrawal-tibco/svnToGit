/**
 * 
 */
package com.tibco.cep.store.as;

import java.util.Properties;

import com.tibco.cep.store.StoreConnectionInfo;
import com.tibco.cep.store.StoreType;
import com.tibco.datagrid.Connection;

/**
 * @author vpatil
 *
 */
public class ASConnectionInfo extends StoreConnectionInfo {
	
	private Properties connectionProperties;

	public ASConnectionInfo(StoreType type, String url) {
		super(type, url);
		
		connectionProperties = new Properties();
	}
	
	public void setConnectionTimeout(double timeout) {
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_DOUBLE_TIMEOUT, String.valueOf(timeout));
	}
	
	public void setWaitTime(double waitTime) {
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_DOUBLE_CONNECT_WAIT_TIME, String.valueOf(waitTime));
	}
	
	public void setUserCredentials(String userName, String password) {
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_STRING_USERNAME, userName);
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_STRING_USERPASSWORD, password);
	}
	
	public void setTrustFile(String trustFilePath) {
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_STRING_TRUST_TYPE, Connection.TIBDG_CONNECTION_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE);
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_STRING_TRUST_FILE, trustFilePath);
	}
	
	public void setTrustAll() {
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_STRING_TRUST_TYPE, Connection.TIBDG_CONNECTION_HTTPS_CONNECTION_TRUST_EVERYONE);
	}
	
	public void setSecondaryStoreUrl(String secondaryStoreUrl) {
		connectionProperties.setProperty(Connection.TIBDG_CONNECTION_PROPERTY_STRING_SECONDARY_REALM, secondaryStoreUrl);
	}
	
	public Properties getConnectionProperties() {
		return this.connectionProperties;
	}
}
