package com.tibco.cep.driver.as.internal;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.driver.as.ASConstants;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class ASChannelConfig implements ChannelConfig, ASConstants {

	private String                  metaspaceName, memberName, discoveryUrl, listenUrl, remoteListenUrl;
	private boolean					enableSecurity;
	private String 					securityRole, policyFile, tokenFile, idPassword, credential, domain, userName, password, keyFile, privateKey;

    private ChannelConfig           config;
	private GlobalVariables         gv;
	private ArchiveResourceProvider provider;

	public ASChannelConfig(ChannelConfig config, GlobalVariables gv, ArchiveResourceProvider provider) throws Exception {
		this.config = config;
		this.gv = gv;
		this.provider = provider;

		postConstruction();
	}

	private void postConstruction() throws Exception {
		ConfigurationMethod method = getConfigurationMethod();
		if (method == ConfigurationMethod.PROPERTIES) {
			configureByProperties();
		}
		else if (method == ConfigurationMethod.REFERENCE) {
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

	private void configureByProperties() {
		metaspaceName = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_METASPACENAME.getLocalName());
		memberName = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_MEMBERNAME.getLocalName());
		discoveryUrl = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_DISCOVERYURL.getLocalName());
		listenUrl = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_LISTENURL.getLocalName());
		remoteListenUrl = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_REMOTELISTENURL.getLocalName());

		enableSecurity =  Boolean.valueOf(
				getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_ENABLE_SECURITY.getLocalName()));
		securityRole = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_SECURITY_ROLE.getLocalName());
		policyFile = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_POLICY_FILE.getLocalName());
		tokenFile = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_TOKEN_FILE.getLocalName());
		idPassword = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_ID_PASSWORD.getLocalName());

		// security auth
		credential = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_AUTH_CREDENTIAL.getLocalName());
		    // user-pwd
		    domain = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_AUTH_DOMAIN.getLocalName());
		    userName = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_AUTH_USERNAME.getLocalName());
		    password = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_AUTH_PASSWORD.getLocalName());

		    // x509v3
	        keyFile = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_AUTH_KEY_FILE.getLocalName());
	        privateKey = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_AUTH_PRIVATE_KEY.getLocalName());
	}

	private void configureByReference() throws Exception {
		String ref = this.getReferenceURI();
		XiNode rootNode = provider.getResourceAsXiNode(ref);
		XiNode configNode = XiChild.getChild(rootNode, XML_NODE_CONFIG);

		metaspaceName = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_METASPACENAME);
		memberName = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_MEMBERNAME);
		discoveryUrl = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_DISCOVERYURL);
		listenUrl = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_LISTENURL);
		remoteListenUrl = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_REMOTELISTENURL);

		enableSecurity = Boolean.valueOf(getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_ENABLE_SECURITY));
		securityRole = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_SECURITY_ROLE);
		policyFile = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_POLICY_FILE);
		tokenFile = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_TOKEN_FILE);
		idPassword = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_ID_PASSWORD);

        // security auth
        credential = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_AUTH_CREDENTIAL);

            // user-pwd
            domain = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_AUTH_DOMAIN);
            userName = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_AUTH_USERNAME);
            password = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_AUTH_PASSWORD);

            // x509v3
            keyFile = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_AUTH_KEY_FILE);
            privateKey = getSubstitutedStringValue(gv, configNode, CHANNEL_PROPERTY_AUTH_PRIVATE_KEY);
	}

	private String getSubstitutedStringValue(GlobalVariables gv, ChannelConfig config, String propName) {
		return getSubstitutedStringValue0(gv, config.getProperties().getProperty(propName));
	}

	private String getSubstitutedStringValue(GlobalVariables gv, XiNode node, ExpandedName name) {
		return getSubstitutedStringValue0(gv, XiChild.getString(node, name));
	}

    private String getSubstitutedStringValue0(GlobalVariables gv, String key) {
		CharSequence cs = gv.substituteVariables(key);
		return cs == null ? "" : cs.toString();
	}

	public String getListenUrl() {
		return listenUrl;
	}

	public String getRemoteListenUrl() {
		return remoteListenUrl;
	}

	public String getMetaspaceName() {
		return metaspaceName;
	}
	
	public String getMemberName() {
		return memberName;
	}

	public String getDiscoveryUrl() {
		return discoveryUrl;
	}
	
	public boolean isEnableSecurity() {
		return enableSecurity;
	}

	public String getSecurityRole() {
		return securityRole;
	}

	public void setSecurityRole(String securityRole) {
        this.securityRole = securityRole;
    }

    public String getPolicyFile() {
		return policyFile;
	}

	public String getTokenFile() {
		return tokenFile;
	}

    public String getIdPassword() {
        return idPassword;
    }

    public String getCredential() {
        return credential;
    }

    public String getDomain() {
        return domain;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public String getPrivateKey() {
        return privateKey;
    }

	@Override
	public ConfigurationMethod getConfigurationMethod() {
		return config.getConfigurationMethod();
	}

	@Override
	public Properties getProperties() {
		return config.getProperties();
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
	public String getURI() {
		return config.getURI();
	}

	public String getName() {
		return config.getName();
	}

	@Override
	public Collection<?> getDestinations() {
		return config.getDestinations();
	}

	@Override
	public String getServerType() {
		return null;
	}

	@Override
	public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
		return null;
	}

}
