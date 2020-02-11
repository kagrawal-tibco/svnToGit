package com.tibco.be.util.config;

import java.util.Properties;

import com.tibco.cep.runtime.driver.DriverPojo;

/**
 * @author ssinghal
 *
 */
public abstract class BEProviderConfiguration {
	
	public Properties properties = new Properties();
	protected DriverPojo dp;
	
	public void setProperty(String key, String value) {
		this.properties.setProperty(key, value);
	}
	
	public String getProperty(String key, String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
	public Properties getProperties() {
		return this.properties;
	}
	
	public DriverPojo getDp() {
		return dp;
	}

	public void setCdp(DriverPojo dp) {
		this.dp = dp;
	}

}
