package com.tibco.cep.driver.http;

import java.net.InetAddress;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.designtime.model.service.channel.impl.WebApplicationDescriptorImpl;
import com.tibco.cep.driver.http.server.ConnectorInfo;
import com.tibco.cep.driver.http.server.FilterConfiguration;
import com.tibco.cep.driver.http.server.SSLParams;
import com.tibco.cep.driver.http.server.impl.TomcatServerUtils;
import com.tibco.cep.driver.http.server.impl.filter.FilterConfigurationLoader;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.service.security.BECertPlusKeyIdentity;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityObjectFactory;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 12:20:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpChannelConfig implements ChannelConfig, HttpChannelConstants {

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HttpChannelConfig.class);

    ChannelConfig config;
    GlobalVariables gv;
    ArchiveResourceProvider provider;
    ConnectorInfo cInfo;
    String host;
    int port;
    boolean useSSL;
    private String httpServerType;
    BEIdentity bid;
    boolean strongCipherSuitesOnly;
    String trustedCertsURI;
    private Properties configProperties = null;

    /**
     * SSL Configuration
     */
    private SSLParams sslParams;
    
    private List<FilterConfiguration> filterConfigurations;


    public HttpChannelConfig(
            ChannelConfig config,
            RuleServiceProvider rsp)
            throws Exception {

        this.config = config;
        this.gv = rsp.getGlobalVariables();
        this.provider = rsp.getProject().getSharedArchiveResourceProvider();
        this.httpServerType = config.getServerType();
        ConfigurationMethod method = getConfigurationMethod();
        //fix for BE#11771
        configProperties = config.getProperties();
        
        replaceGVInProperties(configProperties, gv);
        
        if (method == ConfigurationMethod.REFERENCE) {
            configureByReference(provider);
        } else {
            //validate only port, if host is null then InetAddress returns localhost
            try {
                port = Integer.parseInt(configProperties.getProperty("port"));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("Invalid port %s specified for HTTP configuration", port));
            }
        }

        Properties props = TomcatServerUtils.readPropsFromExternalProps("be.tomcat.props");
        if (props.getProperty("host") != null) {
        	host = props.getProperty("host");
        }
        if (props.getProperty("port") != null) {
        	port = Integer.parseInt(props.getProperty("port"));
        }

        cInfo = new ConnectorInfo(InetAddress.getByName(host), port, configProperties);
        
        String sslEnabledProtocols = configProperties.getProperty(SSLUtils.SSL_SERVER_ENABLED_PROTOCOLS);
        if (sslEnabledProtocols != null && !sslEnabledProtocols.isEmpty() && sslParams != null) {
        	sslParams.setSslEnabledProtocols(sslEnabledProtocols);
        }
        
        // set ciphers if any
        String cipherSuites = configProperties.getProperty(SSLUtils.SSL_SERVER_CIPHERS, SSLUtils.HTTP_SSL_SERVER_CIPHERS);
        if (cipherSuites != null && !cipherSuites.trim().isEmpty() && sslParams != null) {
        	sslParams.setCiphers(cipherSuites);
        }
        
        // set cipher order
        boolean useServerCipherOrder = Boolean.parseBoolean(configProperties.getProperty(SSLUtils.SSL_SERVER_USE_SERVER_CIPHER_ORDER, "false"));
        if (sslParams != null) sslParams.setUseServerCipherOrder(useServerCipherOrder);
        
        // will cover for Java SSL
        if (bid instanceof BEKeystoreIdentity) {
            BEKeystoreIdentity beKeystoreId = (BEKeystoreIdentity) bid;
            sslParams.setKeyStoreFile(beKeystoreId.getStrKeystoreURL());
            sslParams.setKeyStorePassword(beKeystoreId.getStrStorePassword());
            sslParams.setPasswordObfuscated(beKeystoreId.isPasswordObfuscated());

            sslParams.setKeyStoreFileType(beKeystoreId.getStrStoreType());

            String keyManagerAlgo = configProperties.getProperty(SSLUtils.SSL_SERVER_KEYMANAGER_ALGO);
            keyManagerAlgo = (keyManagerAlgo == null || keyManagerAlgo.isEmpty()) ? "SunX509" : keyManagerAlgo;
            sslParams.setAlgorithm(keyManagerAlgo);
            sslParams.setKeyStoreType(true);
            cInfo.setSSLParams(sslParams);
        
        // Will cover for Open SSL
        } else if (bid instanceof BECertPlusKeyIdentity) {
        	BECertPlusKeyIdentity cid = (BECertPlusKeyIdentity) bid;
        	String clientCert = cid.getCertUrl();
            if ((clientCert != null) && (clientCert.length() > 0)) {
            	sslParams.setCertUrl(clientCert);
            }
            String privateKey = cid.getKeyUrl();
            if ((privateKey !=null) && (privateKey.length() > 0)) {
            	sslParams.setPrivateKeyUrl(privateKey);
            }
            String clientPkPwd = cid.getPassword();
            if ((clientPkPwd != null) && (clientPkPwd.length() > 0)){
                if (!cid.isPasswordDecrypted()) {
                    clientPkPwd = new String(ObfuscationEngine.decrypt(clientPkPwd));
                }
                sslParams.setKeyPassword(clientPkPwd);
                sslParams.setPasswordObfuscated(false);
            }
            sslParams.setKeyStoreType(false);
            cInfo.setSSLParams(sslParams);
        }
        
        this.filterConfigurations = Collections.unmodifiableList(
                new FilterConfigurationLoader()
                        .load(rsp.getProperties(), rsp.getGlobalVariables()));
    }

    public ConfigurationMethod getConfigurationMethod() {
        return config.getConfigurationMethod();
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

    /**
     * Gets the Server Type of the <code>ChannelDriver</code> for the configured <code>Channel</code>.
     * <p/>
     * <b>
     * Used for HTTP based channel
     * </b>
     * </p>
     *
     * @return a String.
     * @since 2.0
     */
    public String getServerType() {
        return httpServerType;
    }


    public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
        List<WebApplicationDescriptor> webAppDescs = config.getWebApplicationDescriptors();
        if (webAppDescs == null) {
            return null;
        }
        List<WebApplicationDescriptor> newWebAppDescriptors = new ArrayList<WebApplicationDescriptor>(webAppDescs.size());

        for (WebApplicationDescriptor webApplicationDescriptor : webAppDescs) {
            WebApplicationDescriptorImpl newWebAppDescriptor = new WebApplicationDescriptorImpl();
            String webAppPathValue = webApplicationDescriptor.getWebAppResourcePath();
            String webAppResourcePath = webAppPathValue;
            //Check if it is GV
            if (isGlobalVar(webAppPathValue)) {
                GlobalVariableDescriptor variable =
                        gv.getVariable(stripGvMarkers(webAppPathValue));
                webAppResourcePath = variable.getValueAsString();
            }
            newWebAppDescriptor.setContextURI(webApplicationDescriptor.getContextURI());
            newWebAppDescriptor.setWebAppResourcePath(webAppResourcePath);
            newWebAppDescriptors.add(newWebAppDescriptor);
        }
        return newWebAppDescriptors;
    }

    /**
     * Replace GV's if any
     * 
     * @param beProperties
     * @param gv
     */
    private void replaceGVInProperties(Properties beProperties, final GlobalVariables gv) {
        Object propValue;
        for (Map.Entry<Object, Object> prop : beProperties.entrySet()) {
            propValue = prop.getValue();
            LOGGER.log(Level.DEBUG, "Value of property [%s] is [%s]", prop.getKey(), prop.getValue());
            if (isGlobalVar(propValue)) {
                GlobalVariableDescriptor variable = gv.getVariable(stripGvMarkers((String) prop.getValue()));
                String value = variable.getValueAsString();
                LOGGER.log(Level.DEBUG, "Value of property after GV substitution [%s] is [%s]", prop.getKey(), value);
                if (value != null) {
                    prop.setValue(value);
                }
            }
        }
    }

    private boolean isGlobalVar(Object str) {
        if (!(str instanceof String)) {
            return false;
        }
        String string = (String)str;
        if (string.startsWith("%%") && string.endsWith("%%")) {
            String[] tokens = string.split("%%");
            if (tokens.length == 2) {
                return true;
            }
        }
        return false;
    }

    private static String stripGvMarkers(String variable) {
        int firstIndex = variable.indexOf("%%");
        String stripVal = variable.substring(firstIndex + 2);
        stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
        return stripVal;
    }

    private void configureByReference(ArchiveResourceProvider provider) throws Exception {
        String ref = this.getReferenceURI();
        XiNode rootNode = provider.getResourceAsXiNode(ref);
        XiNode configNode;

        if (rootNode == null) {
            throw new Exception("Unable to find resource " + ref);
        }

        if (rootNode.getName().equals(HTTP_ROOT)) {
            configNode = XiChild.getChild(rootNode, XML_NODE_CONFIG);

            host = getSubstitutedStringValue(gv, configNode, DEST_PROPERTY_HOST);
            String portString =
                    getSubstitutedStringValue(gv, configNode, DEST_PROPERTY_PORT);
            port = Integer.parseInt(portString);

            useSSL = Boolean.valueOf(this.getSubstitutedStringValue(this.gv, configNode, DEST_PROPERTY_USE_SSL));

            if (useSSL) {
                sslParams = new SSLParams();
                String trustStorePassword = null;
                final XiNode sslNode =
                        XiChild.getChild(configNode, XML_NODE_SSL);
                strongCipherSuitesOnly = Boolean.valueOf(
                        this.getSubstitutedStringValue(this.gv, sslNode, DEST_PROPERTY_SSL_STRONGCIPHERSONLY));
                XiNode requiresClientAuthNode =
                        XiChild.getChild(sslNode, DEST_PROPERTY_SSL_REQUIRESCLIENTAUTH);
                if (requiresClientAuthNode != null) {
                    sslParams.setClientAuthRequired(Boolean.valueOf(
                            this.getSubstitutedStringValue(this.gv, sslNode, DEST_PROPERTY_SSL_REQUIRESCLIENTAUTH)));
                }
                trustStorePassword = this.getSubstitutedStringValue(this.gv, sslNode, DEST_PROPERTY_SSL_TRUSTSTORE_PASSWORD);
                if(trustStorePassword == null || trustStorePassword.length() == 0) {
                	trustStorePassword = "NOTSET";
                }
                else {
                	trustStorePassword = new String(ObfuscationEngine.decrypt(trustStorePassword));
                }

                //TODO Handle client auth later
                trustedCertsURI = this.getSubstitutedStringValue(this.gv, sslNode, DEST_PROPERTY_SSL_CERT_URI);
                if ((trustedCertsURI != null) && (trustedCertsURI.length() > 0)) {
                    KeyStore keyStore =
                            SSLUtils.createKeystore(trustedCertsURI, null, provider, this.gv, true);
                    //Store it in the same location
                    String trustStoreFile = SSLUtils.storeKeystore(keyStore,trustStorePassword);
                    sslParams.setTrustStoreFile(trustStoreFile);
                    sslParams.setTrustStoreType(keyStore.getType());
                    sslParams.setTrustStorePass(trustStorePassword);
                }
                final String identityURI =
                        this.getSubstitutedStringValue(this.gv, sslNode, DEST_PROPERTY_SSL_IDENTITY_URI);
                XiNode identityRepoResource = provider.getResourceAsXiNode(identityURI);
                if (null != identityRepoResource) {
                    bid = BEIdentityUtilities.fetchIdentity(provider, gv, identityURI);//BEIdentityObjectFactory.createIdentityObject(identityRepoResource, gv);
                }

            }
        } else if (rootNode.getName().equals(PROXY_ROOT)) {

            configNode = XiChild.getChild(rootNode, XML_NODE_CONFIG);

            host = this.getSubstitutedStringValue(this.gv, configNode, DEST_PROPERTY_PROXY_HOST);
            port = Integer.valueOf(this.getSubstitutedStringValue(this.gv, configNode, DEST_PROPERTY_PROXY_PORT));
            final String identityURI = this.getSubstitutedStringValue(this.gv, configNode, DEST_PROPERTY_PROXY_IDENTITY);
            XiNode identityRepoResource = provider.getResourceAsXiNode(identityURI);
            if (null != identityRepoResource) {
                //XiNode identityNode = XiChild.getChild(identityRepoResource, XML_NODE_IDENTITY);
                bid = BEIdentityObjectFactory.createIdentityObject(identityRepoResource, gv);
            }
        }
    }

    public Collection getDestinations() {
        return config.getDestinations();
    }
    
    public List<FilterConfiguration> getFilterConfigurations(){
        return this.filterConfigurations;
    }

    public String getName() {
        return config.getName();
    }

    public Properties getProperties() {
        return configProperties;
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

    public ArchiveResourceProvider getProvider() {
        return provider;
    }

    public ConnectorInfo getConnectorInfo() {
        return cInfo;
    }

    public BEIdentity getBEIdentity() {
        return bid;
    }

    public ConnectorInfo getCInfo() {
        return cInfo;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    // helper methods

    /**
     * @param gv       GlobalVariables instance.
     * @param config   DriverConfig that holds the property.
     * @param propName String name of the property.
     * @return String value of the property after substituting all global variables.
     */
    protected String getSubstitutedStringValue(GlobalVariables gv, ChannelConfig config, String propName) {
        final CharSequence cs = gv.substituteVariables(config.getProperties().getProperty(propName));
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }//else
    }//getSubstitutedStringValue


    /**
     * @param gv   GlobalVariables instance.
     * @param node XiNode that is the immediate parent of the node to process.
     * @param name String name of the node that contains the String.
     * @return String value of the node after substituting all global variables.
     */
    protected String getSubstitutedStringValue(GlobalVariables gv, XiNode node, ExpandedName name) {
        final CharSequence cs = gv.substituteVariables(XiChild.getString(node, name));
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }//else
    }//getSubstitutedStringValue


    /**
     * @param gv   GlobalVariables instance.
     * @param node XiNode that contains the attributes to process.
     * @param name String name of the attribute that contains the String.
     * @return String value of the attribute after substituting all global variables.
     */
    protected String getSubstitutedAttributeStringValue(GlobalVariables gv, XiNode node, ExpandedName name) {
        final CharSequence cs = gv.substituteVariables(node.getAttributeStringValue(name));
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }//else
    }//getSubstitutedAttributeStringValue
}
