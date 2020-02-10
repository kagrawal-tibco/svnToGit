package com.tibco.cep.driver.as3;

import static com.tibco.cep.driver.as3.AS3Properties.AEMETA_SERVICES_2002_NS;
import static com.tibco.cep.driver.as3.AS3Properties.XML_NODE_CONFIG;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_SSL_NODE;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.runtime.channel.ChannelConfig.ConfigurationMethod;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.store.StoreType;
import com.tibco.cep.store.as.ASConnection;
import com.tibco.cep.store.as.ASConnectionInfo;
import com.tibco.datagrid.Connection;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class AS3Channel extends BaseChannel {
	private ASConnection connection;
	private boolean isConnected;

	public ASConnection getConnection() {
		return connection;
	}
	
	public boolean isConnected(){
		return isConnected;
	}

	public void init() throws Exception {

		ConfigurationMethod method = config.getConfigurationMethod();
		if (method == ConfigurationMethod.PROPERTIES) {
			configureByProperties();
		} else if (method == ConfigurationMethod.REFERENCE) {
			configureByReference();
		}
		getLogger().log(Level.INFO, "Initializing Activespaces 3.x Channel  ");
		super.init();
	}

	private void configureByProperties() throws Exception {
		ASConnectionInfo dgProperties = null;
		String realmURL = (String) getGlobalVariableValue(
				getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_REALMSERVER));
		String gridName = (String) getGlobalVariableValue(
				getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_GRIDNAME));
		String userName = (String) getGlobalVariableValue(
				getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_USERNAME));
		String password = (String) getGlobalVariableValue(
				decryptPwd(getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_PASSWORD)));
		String secondary = (String) getGlobalVariableValue(
				getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_SECONDARY_REALM));
		dgProperties = new ASConnectionInfo(StoreType.AS, realmURL);
		if (!gridName.isEmpty()) {
			dgProperties.setName(gridName);
		}
		Boolean useSSL = Boolean.parseBoolean((String) getGlobalVariableValue(
				getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_USESSL)));
		
		if (useSSL) {
			String trustType = (String) getGlobalVariableValue(
					getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_TRUST_TYPE));
			if (trustType.equals(AS3Properties.AS3_CHANNEL_PROPERTY_TRUST_TYPE_FILE)) {
				String idReference = (String) getGlobalVariableValue(
						getChannelProperties().getProperty(AS3Properties.AS3_CHANNEL_PROPERTY_TRUST_FILE_LOCATION));
				ArchiveResourceProvider provider = ((RuleServiceProvider) getRuleServiceProvider()).getProject()
						.getSharedArchiveResourceProvider();
				String trustFileLocation = getIdentityFileLocation(provider, idReference);
				dgProperties.setTrustFile(trustFileLocation);
			} else if (trustType.equals(AS3Properties.AS3_CHANNEL_PROPERTY_HTTPS_TRUST_TYPE_EVERYONE)) {
				dgProperties.setTrustAll();
			}
		}

		dgProperties.setUserCredentials(userName, password);
		if (!secondary.isEmpty()) {
			dgProperties.getConnectionProperties().put(Connection.TIBDG_CONNECTION_PROPERTY_STRING_SECONDARY_REALM,
					secondary);
		}
		connection = new ASConnection(dgProperties);
	}

	private void configureByReference() throws Exception {
		ASConnectionInfo dgProperties = null;
		String ref = config.getReferenceURI();
		XiNode rootNode = null;
		ArchiveResourceProvider provider = null;
		provider = ((RuleServiceProvider) getRuleServiceProvider()).getProject().getSharedArchiveResourceProvider();
		rootNode = provider.getResourceAsXiNode(ref);
		XiNode configNode = XiChild.getChild(rootNode, XML_NODE_CONFIG);
		String realmURL = (String) getGlobalVariableValue(
				XiChild.getString(configNode, ExpandedName.makeName(AS3Properties.AS3_CHANNEL_PROPERTY_REALMSERVER)));
		String gridName = (String) getGlobalVariableValue(
				XiChild.getString(configNode, ExpandedName.makeName(AS3Properties.AS3_CHANNEL_PROPERTY_GRIDNAME)));
		String userName = (String) getGlobalVariableValue(
				XiChild.getString(configNode, ExpandedName.makeName(AS3Properties.AS3_CHANNEL_PROPERTY_USERNAME)));
		String password = (String) getGlobalVariableValue(decryptPwd(
				XiChild.getString(configNode, ExpandedName.makeName(AS3Properties.AS3_CHANNEL_PROPERTY_PASSWORD))));
		String secondary = (String) getGlobalVariableValue(XiChild.getString(configNode,
				ExpandedName.makeName(AS3Properties.AS3_CHANNEL_PROPERTY_SECONDARY_REALM)));

		boolean useSsl = Boolean.parseBoolean(getGlobalVariableValue(XiChild.getString(configNode, ExpandedName.makeName(AS3Properties.AS3_CHANNEL_PROPERTY_USESSL))) + "");

		dgProperties = new ASConnectionInfo(StoreType.AS, realmURL);
		if (!gridName.isEmpty()) {
			dgProperties.setName(gridName);
		}
		dgProperties.setUserCredentials(userName, password);
		if (!secondary.isEmpty()) {
			dgProperties.getConnectionProperties().put(Connection.TIBDG_CONNECTION_PROPERTY_STRING_SECONDARY_REALM,
					secondary);
		}
		if (useSsl) {
			XiNode sslNode = XiChild.getChild(configNode,
					ExpandedName.makeName(AEMETA_SERVICES_2002_NS, AS3_CHANNEL_PROPERTY_SSL_NODE));
			String trustType = (String) getGlobalVariableValue(XiChild.getString(sslNode,
					ExpandedName.makeName(AEMETA_SERVICES_2002_NS, AS3Properties.AS3_CHANNEL_PROPERTY_TRUST_TYPE)));
			if (trustType.equals(AS3Properties.AS3_CHANNEL_PROPERTY_TRUST_TYPE_FILE)) {
				String trustFileLocation = parseIdentity(sslNode, provider);
				dgProperties.setTrustFile(trustFileLocation);
			} else if (trustType.equals(AS3Properties.AS3_CHANNEL_PROPERTY_HTTPS_TRUST_TYPE_EVERYONE)) {
				dgProperties.setTrustAll();
			}
		}
		connection = new ASConnection(dgProperties);
	}

	private String parseIdentity(XiNode sslNode, ArchiveResourceProvider provider) throws Exception {
		final String idReference = (String) getGlobalVariableValue(XiChild.getString(sslNode, ExpandedName
				.makeName(AEMETA_SERVICES_2002_NS, AS3Properties.AS3_CHANNEL_PROPERTY_TRUST_FILE_LOCATION)));
		return getIdentityFileLocation(provider, idReference);
	}

	private String getIdentityFileLocation(final ArchiveResourceProvider provider,
			final String idReference) throws Exception {
		String trustFileLoc = null;
		if ((idReference != null) && !idReference.trim().isEmpty()) {
			if (!idReference.startsWith("/")) {
				throw new Exception("Invalid SSL ID reference: " + idReference);
			}
			BEIdentity identity = BEIdentityUtilities.fetchIdentity(provider, config.getGv(), idReference);
			if (identity instanceof BEKeystoreIdentity) {
				BEKeystoreIdentity id = (BEKeystoreIdentity) identity;
				trustFileLoc = id.getStrKeystoreURL();
			} else {
				throw new Exception("Invalid identity File configuration: ");
			}
		}
		return trustFileLoc;
	}

	public void connect() throws Exception {
		if (!isConnected) {
			connection.connect();
			isConnected = true;
			super.connect();
		}
	}

	private static String decryptPwd(String encryptedPwd) {
		try {
			String decryptedPwd = encryptedPwd;
			if (ObfuscationEngine.hasEncryptionPrefix(encryptedPwd)) {
				decryptedPwd = new String(ObfuscationEngine.decrypt(encryptedPwd));
			}
			return decryptedPwd;
		} catch (AXSecurityException e) {
			return encryptedPwd;
		}
	}

	public void close() throws Exception {
		super.close();
		if (connection != null && isConnected) {
			connection.disconnect();
			isConnected = false;
		}
	}
}
