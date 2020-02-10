package com.tibco.be.util.config;

import java.util.Properties;

import com.tibco.cep.runtime.cluster.ClusterDriverPojo;

/**
 * BE Cluster configuration, all ClusterProviders should use this (or extended) class for their configurations.
 * @author moshaikh
 */
public class BEClusterConfiguration {

	private ClusterDriverPojo cdp;
	private Properties properties = new Properties();
	
	public BEClusterConfiguration(ClusterDriverPojo cdp) {
		this.cdp = cdp;
	}
	
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

	public ClusterDriverPojo getCdp() {
		return cdp;
	}

	public void setCdp(ClusterDriverPojo cdp) {
		this.cdp = cdp;
	}
	
	

}
