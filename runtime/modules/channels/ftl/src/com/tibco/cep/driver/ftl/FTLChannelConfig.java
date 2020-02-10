package com.tibco.cep.driver.ftl;

import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_REALMSERVER;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_SECONDARY;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.ftl.FTLConstants.XML_NODE_CONFIG;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_FILE_LOCATION;
import static com.tibco.cep.driver.ftl.FTLConstants.AEMETA_SERVICES_2002_NS;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USESSL;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_SSL_NODE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_FILE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_STRING;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_STRING_TEXT;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class FTLChannelConfig implements ChannelConfig {
	private ChannelConfig config;

	private GlobalVariables gv;

	private ArchiveResourceProvider provider;

	private String realmServer, username, password, secondary;
	private String trustType, trustFileLocation, trustFileString;
	private boolean useSsl;

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

	public String getRealmServer() {
		return realmServer;
	}

	public void setRealmServer(String realmServer) {
		this.realmServer = realmServer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSecondary() {
		return secondary;
	}

	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	
	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public String getTrustFileLocation() {
		return trustFileLocation;
	}

	public void setTrustFileLocation(String trustFileLocation) {
		this.trustFileLocation = trustFileLocation;
	}
	
	public String getTrustFileString() {
		return trustFileString;
	}

	public void setTrustFileString(String trustString) {
		this.trustFileString = trustString;
	}

	public boolean isUseSsl() {
		return useSsl;
	}

	public void setUseSsl(boolean useSsl) {
		this.useSsl = useSsl;
	}

	public FTLChannelConfig(ChannelConfig config, GlobalVariables gv, ArchiveResourceProvider provider) throws Exception{
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

	private void configureByReference() throws Exception{
		String ref = this.getReferenceURI();
		XiNode rootNode = null;
		try {
			rootNode = provider.getResourceAsXiNode(ref);
		} catch (Exception e) {
			e.printStackTrace();
		}
		XiNode configNode = XiChild.getChild(rootNode, XML_NODE_CONFIG);
		realmServer = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_REALMSERVER));
		username = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_USERNAME));
		password = decryptPwd(getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_PASSWORD)));
		secondary = getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_SECONDARY));
		
		useSsl = Boolean.parseBoolean(getSubstitutedStringValue(gv, configNode, ExpandedName.makeName(CHANNEL_PROPERTY_USESSL)));
		if(useSsl){
			XiNode sslNode = XiChild.getChild(configNode, ExpandedName.makeName(AEMETA_SERVICES_2002_NS, CHANNEL_PROPERTY_SSL_NODE));
		    trustType = getSubstitutedStringValue(gv, sslNode, ExpandedName.makeName(AEMETA_SERVICES_2002_NS, CHANNEL_PROPERTY_TRUST_TYPE));
		    if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_FILE)){
		    	trustFileLocation = parseIdentity(sslNode);
		    }else if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_STRING)){
		    	trustFileString = getSubstitutedStringValue(gv, sslNode, ExpandedName.makeName(AEMETA_SERVICES_2002_NS, CHANNEL_PROPERTY_TRUST_STRING_TEXT));
		    }
		}
	}
	
	private String parseIdentity(XiNode sslNode) throws Exception{
		String trustFileLoc = null;
		final String idReference = getSubstitutedStringValue(gv, sslNode, ExpandedName.makeName(AEMETA_SERVICES_2002_NS, CHANNEL_PROPERTY_TRUST_FILE_LOCATION));
    	if ((idReference != null) && !idReference.trim().isEmpty()) {
            if (!idReference.startsWith("/")) {
                throw new Exception("Invalid SSL ID reference: " + idReference);
            }
            BEIdentity identity = BEIdentityUtilities.fetchIdentity(provider, gv, idReference);
            if (identity instanceof BEKeystoreIdentity) {
            	BEKeystoreIdentity id = (BEKeystoreIdentity) identity;
            	trustFileLoc = id.getStrKeystoreURL();
            }else{
            	throw new Exception("Invalid identity File configuration: ");
            }
            
        }
    	return trustFileLoc;
	}

	private void configureByProperties() {
		realmServer = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_REALMSERVER);
		username = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_USERNAME);
		password = decryptPwd(getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_PASSWORD));
		secondary = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_SECONDARY);
		trustType = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_TRUST_TYPE);
		if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_FILE)){
			trustFileLocation = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_TRUST_FILE_LOCATION);
		}else if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_STRING)){
			trustFileString = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_TRUST_STRING_TEXT);
		}
		
		
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
		}
	}

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
		}
	}

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

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Decrypts a password only if it is encrypted, otherwise returns back same string.
	 * @param encryptedPwd
	 * @return
	 */
	public static String decryptPwd(String encryptedPwd) {
        try {
        	if (encryptedPwd == null || encryptedPwd.trim().isEmpty()) {
    			return encryptedPwd;
    		}
            String decryptedPwd = encryptedPwd;
            if (ObfuscationEngine.hasEncryptionPrefix(encryptedPwd)) {
                decryptedPwd = new String(ObfuscationEngine.decrypt(encryptedPwd));
            }
            return decryptedPwd;
        } catch (AXSecurityException e) {
            return encryptedPwd;
        }
    }

}
