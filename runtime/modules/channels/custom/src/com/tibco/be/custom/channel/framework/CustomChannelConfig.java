package com.tibco.be.custom.channel.framework;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;

/**
 * @.category public-api
 * @author vasharma
 */

public class CustomChannelConfig implements ChannelConfig {

	protected ChannelConfig config;
	protected GlobalVariables gv;
	protected BEProperties beProperties;
	protected Logger logger;
	protected Properties properties;

	public CustomChannelConfig(ChannelConfig config, GlobalVariables globalVariables, Properties channelProperties,
			BEProperties properties, Logger logger) throws Exception {
		this.config = config;
		this.gv = globalVariables;
		this.properties = channelProperties;
		this.beProperties = (BEProperties) properties;
		this.logger = logger;
	}

	public ChannelConfig getConfig() {
		return config;
	}

	public void setConfig(ChannelConfig config) {
		this.config = config;
	}

	public GlobalVariables getGv() {
		return gv;
	}

	public void setGv(GlobalVariables gv) {
		this.gv = gv;
	}

	public BEProperties getBeProperties() {
		return beProperties;
	}

	public void setBeProperties(BEProperties beProperties) {
		this.beProperties = beProperties;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public ConfigurationMethod getConfigurationMethod() {
		return config.getConfigurationMethod();
	}

	@Override
	public Collection getDestinations() {
		return config.getDestinations();
	}

	@Override
	public String getName() {
		return config.getName();
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public String getReferenceURI() {
		return config.getReferenceURI();
	}

	@Override
	public String getType() {
		return config.getType();
	}

	@Override
	public String getServerType() {
		return config.getServerType();
	}

	@Override
	public String getURI() {
		return config.getURI();
	}

	@Override
	public boolean isActive() {
		return config.isActive();
	}

	@Override
	public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
		return null;
	}
}
