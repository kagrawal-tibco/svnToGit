package com.tibco.cep.driver.hawk;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class HawkChannelConfig implements ChannelConfig, HawkConstants {

	private String hawkDomain, transport;

	private String rvService, rvNetwork, rvDaemon;
	private String emsUrl, userName, password;

	public HawkChannelConfig(ChannelConfig config, GlobalVariables gv, ArchiveResourceProvider provider) {
		this.config = config;
		this.gv = gv;
		this.provider = provider;
		ConfigurationMethod method = getConfigurationMethod();
		if (method == ConfigurationMethod.PROPERTIES) {
			configureByProperties();
		} else if (method == ConfigurationMethod.REFERENCE) {
			configureByReference();
		}
	}
	
    /**
     * Tests whether the channel has not been marked for deactivation.
     *
     * @return a boolean
     * @since 5.0.1-HF1
     */
   	public boolean isActive() {
   		return config.isActive();
   	}

	public String getHawkDomain() {
		return hawkDomain;
	}

	public void setHawkDomain(String hawkDomain) {
		this.hawkDomain = hawkDomain;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getRvService() {
		return rvService;
	}

	public void setRvService(String rvService) {
		this.rvService = rvService;
	}

	public String getRvNetwork() {
		return rvNetwork;
	}

	public void setRvNetwork(String rvNetwork) {
		this.rvNetwork = rvNetwork;
	}

	public String getRvDaemon() {
		return rvDaemon;
	}

	public void setRvDaemon(String rvDaemon) {
		this.rvDaemon = rvDaemon;
	}

	public String getEmsUrl() {
		return emsUrl;
	}

	public void setEmsUrl(String emsUrl) {
		this.emsUrl = emsUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public ArchiveResourceProvider getProvider() {
		return provider;
	}

	public void setProvider(ArchiveResourceProvider provider) {
		this.provider = provider;
	}

	private ChannelConfig config;

	private GlobalVariables gv;

	private ArchiveResourceProvider provider;

	private void configureByProperties() {
		hawkDomain = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_HAWKDOAMIN);
		transport = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_TRANSPORT);

		rvService = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_RVSERVICE);
		rvNetwork = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_RVNETWORK);
		rvDaemon = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_RVDAEMON);

		emsUrl = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_SERVER_URL);
		userName = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_USERNAME);
		password = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_PASSWORD);

	}

	private void configureByReference() {
		String ref = this.getReferenceURI();
		XiNode rootNode = null;
		try {
			rootNode = provider.getResourceAsXiNode(ref);
		} catch (Exception e) {
			e.printStackTrace();
		}
		XiNode configNode = XiChild.getChild(rootNode, XML_NODE_CONFIG);

		hawkDomain = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_HAWKDOAMIN));
		transport = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_TRANSPORT));

		rvService = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_RVSERVICE));
		rvNetwork = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_RVNETWORK));
		rvDaemon = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_RVDAEMON));

		emsUrl = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_SERVER_URL));
		userName = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_USERNAME));
		password = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_PASSWORD));
	}

	/**
	 * @param gv
	 *            GlobalVariables instance.
	 * @param config
	 *            DriverConfig that holds the property.
	 * @param propName
	 *            String name of the property.
	 * @return String value of the property after substituting all global
	 *         variables.
	 */
	private String getSubstitutedStringValue(GlobalVariables gv, ChannelConfig config, String propName) {
		final CharSequence cs = gv.substituteVariables(config.getProperties().getProperty(propName));
		if (null == cs) {
			return "";
		} else {
			return cs.toString();
		}// else
	}// getSubstitutedStringValue

	/**
	 * @param gv
	 *            GlobalVariables instance.
	 * @param node
	 *            XiNode that is the immediate parent of the node to process.
	 * @param name
	 *            String name of the node that contains the String.
	 * @return String value of the node after substituting all global variables.
	 */
	private String getSubstitutedStringValue(GlobalVariables gv, XiNode node, ExpandedName name) {
		final CharSequence cs = gv.substituteVariables(XiChild.getString(node, name));
		if (null == cs) {
			return "";
		} else {
			return cs.toString();
		}// else
	}// getSubstitutedStringValue

	public ConfigurationMethod getConfigurationMethod() {
		return config.getConfigurationMethod();
	}

	public Properties getProperties() {
		return config.getProperties();
	}

	public String getReferenceURI() {
		return config.getReferenceURI();
	}

	public String getType() {
		return config.getType();
	}

	public String getURI() {
		return config.getURI();
	}

	public String getName() {
		return config.getName();
	}

	public Collection<?> getDestinations() {
		return config.getDestinations();
	}

	public String getServerType() {
		return null;
	}

	public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
		return null;
	}

}
