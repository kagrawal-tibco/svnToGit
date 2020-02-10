package com.tibco.cep.driver.jms;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;
import com.tibco.be.util.config.sharedresources.sharedjmscon.*;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Config;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Row;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.*;
import com.tibco.be.util.config.sharedresources.util.SharedResourcesHelper;
import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.Project;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.service.security.*;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.AXSecurityException;
import com.tibco.security.Cert;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 7, 2006
 * Time: 7:10:13 AM
 */
public class JMSChannelConfig
        implements ChannelConfig
{

    ChannelConfig config;
    GlobalVariables gv;
    final BEProject project;
    String providerURL;
    String userID;
    String password;
    boolean isTransacted = false;
    String clientID;

    boolean useSsl = false;
    Properties sslProperties = new Properties();

    boolean useJNDI = false;
    Context jndiContext;
    String qcf;
    String tcf;
    String admFactorySslPassword;
    String vendor = JMSConstants.J2SE;

    static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JMSChannelConfig.class);

    public JMSChannelConfig(
            ChannelConfig config,
            BEProject project)
            throws Exception
    {
        this.config = config;
        this.gv = project.getGlobalVariables();//TODO Remove this field?
        this.project  = project;
        this.vendor = System.getProperty(SystemProperty.TIBCO_SECURITY_VENDOR.getPropertyName());
        if ((null == this.vendor) || this.vendor.trim().isEmpty()) {
            this.vendor = JMSConstants.J2SE;
        }

        if (this.getConfigurationMethod() == ConfigurationMethod.PROPERTIES) {
            this.configureByProperties();
        } else {
            this.configureByReference();
        }
    }


    public String getServerType() {
        return null;
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


    private void configureByReference()
            throws Exception
    {
        final Config config = SharedResourcesHelper.loadSharedJmsConnectionConfig(
                this.makeUriString(this.getReferenceURI()));

        final ConnectionAttributes attributes = config.getConnectionAttributes();

        this.clientID = this.getSubstitutedString(attributes.getClientId());
        if ((null == this.clientID) || this.clientID.trim().isEmpty()) {
            if (this.getSubstitutedBoolean(attributes.getAutoGenClientID())) {
                final String engineName = BEProperties.getInstance()
                        .getString(SystemProperty.ENGINE_NAME.getPropertyName());
                this.clientID = "BE-" + this.gv.getVariableAsString("Deployment", null) + "-"
                        + ((engineName == null || engineName.trim().isEmpty())
                        ? this.gv.getVariableAsString("be.engine.name", null)
                        : engineName)
                        + "-" + getURI();
            }
        }

        this.configureUserNameAndPassword(attributes);

        final NamingEnvironment namingEnv = config.getNamingEnvironment();

        this.providerURL = this.getSubstitutedString(namingEnv.getProviderUrl());
        this.admFactorySslPassword = this.getSubstitutedString(config.getAdmFactorySslPassword());
        if (!((null == this.admFactorySslPassword) || this.admFactorySslPassword.isEmpty())) {
            try {
                this.admFactorySslPassword = new String(ObfuscationEngine.decrypt(admFactorySslPassword));
            } catch (Exception ignored) {
            }
        }

        this.useJNDI = this.getSubstitutedBoolean(namingEnv.getUseJndi());
        this.useSsl = this.getSubstitutedBoolean(config.getUseSsl());
        if (this.useJNDI) {
            if (this.getSubstitutedBoolean(config.getUseSharedJndiConfig())) {
                this.configureJndiUsingSharedResource(
                        this.getSubstitutedString(config.getJndiSharedConfiguration()),
                        namingEnv,
                        config.getJndiProperties(),
                        config.getSsl());
            }
            else {
                this.configureJndi(namingEnv, config.getJndiProperties(), config.getSsl());
            }
        } else if (this.useSsl) {
            this.initSsl(config.getSsl(), this.sslProperties);
        }
    }


    private void configureJndi(
            NamingEnvironment namingEnv,
            JndiProperties jndiProperties,
            Ssl sslNode)
            throws Exception
    {
        this.qcf = this.getSubstitutedString(namingEnv.getQueueFactoryName());
        this.tcf = this.getSubstitutedString(namingEnv.getTopicFactoryName());

        final Hashtable<Object, Object> env = new Hashtable<Object, Object>();
        env.put(Context.PROVIDER_URL, this.getSubstitutedString(namingEnv.getNamingUrl()));
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                this.getSubstitutedString(namingEnv.getNamingInitialContextFactory()));

        if (null != jndiProperties) {
            for (final Row row : jndiProperties.getRow()) {
                env.put(this.getSubstitutedString(row.getName()), this.getSubstitutedString(row.getValue()));
            }
            if (JMSConstants.SSL.equals(env.get(JMSConstants.SECURITY_PROTOCOL))) {
                env.put(JMSConstants.SSL_VENDOR, this.vendor);
            }
        }

        final String securityPrincipal = this.getSubstitutedString(namingEnv.getNamingPrincipal());
        if ((null != securityPrincipal) && !(securityPrincipal.trim().isEmpty())) {
            env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
            final String securityCredential = this.getSubstitutedString(namingEnv.getNamingCredential());
            if ((null != securityCredential) && !(securityCredential.trim().isEmpty())) {
                try {
                    env.put(Context.SECURITY_CREDENTIALS, new String(ObfuscationEngine.decrypt(securityCredential)));
                }
                catch (Exception e) {
                    env.put(Context.SECURITY_CREDENTIALS, securityCredential);
                }
            }
        }

        if (this.useSsl) {
            env.put(JMSConstants.SECURITY_PROTOCOL, JMSConstants.SSL);
            env.put(Context.URL_PKG_PREFIXES, JMSConstants.PKG_PREFIXES_NAMING);

            this.initSsl(sslNode, env);

            // private key password is used if connection factory SSL password is not specified
            if ((null == this.admFactorySslPassword) || this.admFactorySslPassword.isEmpty()) {
                this.admFactorySslPassword = (String) env.get(JMSConstants.SSL_PASSWORD);
            }
        }

        //todo validate using certificate?
        //String validateJndi = getSubstitutedStringValue(gv, jndiNode, JNDI_VALIDATE_SECURITY_EN);

        this.jndiContext = new InitialContext(env);
    }


    private void configureJndiUsingSharedResource(
            String jndiResourcePath,
            NamingEnvironment namingEnv,
            JndiProperties jndiProperties,
            Ssl sslNode)
            throws Exception
    {
        this.qcf = this.getSubstitutedString(namingEnv.getQueueFactoryName());
        this.tcf = this.getSubstitutedString(namingEnv.getTopicFactoryName());

        final com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config config =
                SharedResourcesHelper.loadSharedJndiConfig(
                        this.makeUriString(jndiResourcePath));

        final Hashtable<Object, Object> env = new Hashtable<Object, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, this.getSubstitutedString(config.getContextFactory()));
        env.put(Context.PROVIDER_URL, this.getSubstitutedString(config.getProviderUrl()));

        final OptionalJndiProperties optionalProps = config.getOptionalJndiProperties();
        if (null != optionalProps) {
            for (final com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row row : optionalProps.getRow()) {
                env.put(this.getSubstitutedString(row.getName()), this.getSubstitutedString(row.getValue()));
            }
            if (JMSConstants.SSL.equals(env.get(JMSConstants.SECURITY_PROTOCOL))) {
                env.put(JMSConstants.SSL_VENDOR, this.vendor);
            }
        }
        if (null != jndiProperties) {
            for (final Row row : jndiProperties.getRow()) {
                env.put(this.getSubstitutedString(row.getName()), this.getSubstitutedString(row.getValue()));
            }
            if (JMSConstants.SSL.equals(env.get(JMSConstants.SECURITY_PROTOCOL))) {
                env.put(JMSConstants.SSL_VENDOR, this.vendor);
            }
        }

        final String securityPrincipal = this.getSubstitutedString(config.getSecurityPrincipal());
        if ((null != securityPrincipal) && !(securityPrincipal.trim().isEmpty())) {
            env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
            final String securityCredential = this.getSubstitutedString(config.getSecurityCredentials());
            if ((null != securityCredential) && !(securityCredential.trim().isEmpty())) {
                try {
                    env.put(Context.SECURITY_CREDENTIALS, new String(ObfuscationEngine.decrypt(securityCredential)));
                }
                catch (Exception e) {
                    env.put(Context.SECURITY_CREDENTIALS, securityCredential);
                }
            }
        }

        if (this.useSsl) {
            env.put(JMSConstants.SECURITY_PROTOCOL, JMSConstants.SSL);
            env.put(Context.URL_PKG_PREFIXES, JMSConstants.PKG_PREFIXES_NAMING);

            this.initSsl(sslNode, env);

            // private key password is used if connection factory SSL password is not specified
            if ((null == this.admFactorySslPassword) || this.admFactorySslPassword.isEmpty()) {
                this.admFactorySslPassword = (String) env.get(JMSConstants.SSL_PASSWORD);
            }
        }

        //todo validate using certificate?
        //String validateJndi = getSubstitutedStringValue(gv, jndiNode, JNDI_VALIDATE_SECURITY_EN);

        this.jndiContext = new InitialContext(env);
    }


    @Override
    public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
        throw new UnsupportedOperationException("Operation not supported");
    }


    private void initSsl(
            Ssl ssl,
            Dictionary<Object, Object> sslProperties)
            throws Exception
    {
        sslProperties.put(
                (this.useJNDI ? JMSConstants.SSL_VENDOR : JMSConstants.VENDOR),
                this.vendor);
        sslProperties.put(
                (this.useJNDI ? JMSConstants.SSL_TRACE : JMSConstants.TRACE),
                this.getSubstitutedBoolean(ssl.getTrace()));
        sslProperties.put(
                (this.useJNDI ? JMSConstants.SSL_DEBUG_TRACE : JMSConstants.DEBUG_TRACE),
                this.getSubstitutedBoolean(ssl.getDebugTrace()));
        sslProperties.put(
                (this.useJNDI ? JMSConstants.SSL_ENABLE_VERIFY_HOSTNAME : JMSConstants.ENABLE_VERIFY_HOSTNAME),
                this.getSubstitutedBoolean(ssl.getVerifyHostName()));

        final String expectedHostname = this.getSubstitutedString(ssl.getExpectedHostName());
        if ((null != expectedHostname) && !expectedHostname.isEmpty()) {
            sslProperties.put(
                    (this.useJNDI ? JMSConstants.SSL_EXPECTED_HOSTNAME : JMSConstants.EXPECTED_HOSTNAME),
                    expectedHostname);
        }

        //TODO what if not a reference?
        final String trustedCertsUri = this.getSubstitutedString(CddTools.getValueFromMixed(ssl.getCert()));
        if ((null != trustedCertsUri) && !trustedCertsUri.isEmpty()) {
            final Cert[] certList =  BETrustedCertificateManager.getInstance()
                    .getTrustedCerts(this.project, this.gv, trustedCertsUri, true)
                    .getCertificateList();
            final Vector<Object> certificates = new Vector<Object>();
            for (final Cert c : certList) {
                certificates.add(c.getCertificate());
            }
            sslProperties.put(
                    (this.useJNDI ? JMSConstants.SSL_TRUSTED_CERTIFICATES : JMSConstants.TRUSTED_CERTIFICATES),
                    certificates);
        }

        final BEIdentity idObj = this.getIdentity(ssl);
        if (idObj != null) {
            if (idObj instanceof BECertPlusKeyIdentity) {
                BECertPlusKeyIdentity ckIdObj = (BECertPlusKeyIdentity)idObj;
                String clientCert = ckIdObj.getCertUrl();
                if ((clientCert != null) && (clientCert.length() > 0)) {
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_IDENTITY : JMSConstants.IDENTITY), clientCert);
                }
                String privateKey = ckIdObj.getKeyUrl();
                if ((privateKey !=null) && (privateKey.length() > 0)) {
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_PRIVATE_KEY : JMSConstants.PRIVATE_KEY), privateKey);
                }
                String clientPkPwd = ckIdObj.getPassword();
                if ((clientPkPwd != null) && (clientPkPwd.length() > 0)){
                    if (this.useJNDI && !ckIdObj.isPasswordDecrypted()) {
                        clientPkPwd = new String(ObfuscationEngine.decrypt(clientPkPwd));
                    }
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_PASSWORD : JMSConstants.PASSWORD), clientPkPwd);
                }
            } else if (idObj instanceof BEUrlIdentity) {
                BEUrlIdentity ckIdObj = (BEUrlIdentity)idObj;
                String clientCert = ckIdObj.getUrl();
                if ((clientCert != null) && (clientCert.length() > 0)) {
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_IDENTITY : JMSConstants.IDENTITY), clientCert);
                }
                String clientPkPwd = ckIdObj.getPassword();
                if ((clientPkPwd != null) && (clientPkPwd.length() > 0)){
                    if (this.useJNDI && !ckIdObj.isPasswordDecrypted()) {
                        clientPkPwd = new String(ObfuscationEngine.decrypt(clientPkPwd));
                    }
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_PASSWORD : JMSConstants.PASSWORD), clientPkPwd);
                }
            } else if (idObj instanceof BEKeystoreIdentity) {
                final BEKeystoreIdentity keystoreIdentity = (BEKeystoreIdentity) idObj;
                final String clientCert = keystoreIdentity.getStrKeystoreURL();
                if ((clientCert != null) && (clientCert.length() > 0)) {
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_IDENTITY : JMSConstants.IDENTITY), clientCert);
                }
                String clientPkPwd = keystoreIdentity.getStrStorePassword();
                if ((clientPkPwd != null) && (clientPkPwd.length() > 0)) {
                    if (this.useJNDI && keystoreIdentity.isPasswordObfuscated()) {
                        clientPkPwd = new String(ObfuscationEngine.decrypt(clientPkPwd));
                    }
                    sslProperties.put((this.useJNDI ? JMSConstants.SSL_PASSWORD : JMSConstants.PASSWORD), clientPkPwd);
                }
            } else if (!(idObj instanceof BEUserIdPasswordIdentity)) {
                throw new RuntimeException("Identity type: " + idObj.getObjectType() + " is not supported.");
            }
        }

        // Dummy random number generator, not secure but for testing with
        EmsHelper.initRandomNumberGenerator();
    }


    private void configureUserNameAndPassword(
            ConnectionAttributes attributes)
    {
        this.userID = this.getSubstitutedString(attributes.getUsername());
        String password = this.getSubstitutedString(attributes.getPassword());
        configurePassword(password);
    }


    private void configurePassword(String password) {
        boolean triedToDecrypt = false; //Fix for BE-24422 to prevent encryption of password when userid is null
        if ((null != this.userID)
                && (!this.userID.trim().isEmpty())
                && (null != password)
                && !password.trim().isEmpty()) {
            try {
                triedToDecrypt = true;
                password = new String(ObfuscationEngine.decrypt(password));
            } catch (Exception ex) {
                LOGGER.log(Level.WARN, "Error caught in decryption of JMS Password string, it does not contain an encryption prefix");
            }
        }
        if (null == password) {
            this.password = null;
        } else {
            try {
                if (triedToDecrypt) {
                    this.password = ObfuscationEngine.encrypt(password.toCharArray());
                }
            } catch (AXSecurityException e) {
                this.password = null;
            }
        }
    }

    private void configureByProperties()
    {
        this.clientID = this.getSubstitutedStringValue(JMSConstants.CHANNEL_PROPERTY_CLIENTID_PROP);

        this.isTransacted = this.getSubstitutedBoolean(
                this.config.getProperties().getProperty(JMSConstants.CHANNEL_PROPERTY_ISTRANSACTED.getLocalName()));

        this.providerURL = this.getSubstitutedStringValue(JMSConstants.CHANNEL_PROPERTY_PROVIDER_URL);

        this.configureUserAndPasswordByProperties();
    }


    private void configureUserAndPasswordByProperties()
    {
        this.userID = this.getSubstitutedStringValue(JMSConstants.CHANNEL_PROPERTY_USER);
        String pwd = this.getSubstitutedStringValue(JMSConstants.CHANNEL_PROPERTY_PWD);        
        configurePassword(pwd);
    }


    private String getSubstitutedStringValue(
            ExpandedName propName) {
        final CharSequence cs = this.gv.substituteVariables(
                this.config.getProperties().getProperty(propName.localName));
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }//else
    }//getSubstitutedStringValue


    protected String getSubstitutedString(
            CharSequence cs)
    {
        return (null == cs)
                ? ""
                : this.gv.substituteVariables(cs).toString();
    }


    protected boolean getSubstitutedBoolean(
            Object o)
    {
        return this.getSubstitutedBoolean(o, false);
    }


    protected boolean getSubstitutedBoolean(
            Object o,
            boolean defaultValue)
    {
        return ((null == o) || ((o instanceof CharSequence) && (((CharSequence) o).length() == 0)))
                ? defaultValue
                : Boolean.valueOf(this.getSubstitutedString(String.valueOf(o)));
    }


    public ConfigurationMethod getConfigurationMethod() {
        return config.getConfigurationMethod();
    }

    public Collection getDestinations() {
        return config.getDestinations();
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


    public String getClientID() {
        return clientID;
    }


    public Context getJndiContext() {
        return jndiContext;
    }

    public GlobalVariables getGv() {
        return gv;
    }

    public boolean isTransacted() {
        return isTransacted;
    }

    public String getPassword() {
        return password;
    }

    public Project getProject() {
        return this.project;
    }

    public String getProviderURL() {
        return providerURL;
    }

    public String getQcf() {
        return qcf;
    }

    public QueueConnectionFactory getQueueConnectionFactory(Properties prop) throws Exception {
        if (useJNDI) {
            Object obj = jndiContext.lookup(qcf);
            // todo make work with SSL
            if (useSsl) {
                EmsHelper.setSslPassword(obj, admFactorySslPassword);
            }
            return obj instanceof QueueConnectionFactory ? (QueueConnectionFactory)obj : null;
        } else {
            String connectAttempts = prop.getProperty("com.tibco.tibjms.connect.attempts");
            QueueConnectionFactory qcf = null;
            if ((connectAttempts != null) && (connectAttempts.length() > 0)) {
                String[] ftUrls = providerURL.split(",");
                if (ftUrls.length <= 1) {
                    providerURL = providerURL + "," + providerURL;
                }
                String[] attemptsNDelay = connectAttempts.split(",");

                Integer attempts = new Integer(attemptsNDelay[0]);
                Integer delay = new Integer(500);
                if (attemptsNDelay.length > 1) delay = new Integer(attemptsNDelay[1]);
                qcf = EmsHelper.newQueueConnectionFactory(providerURL, sslProperties);
                setReconnectOptions(qcf, attempts, delay);
                //                qcf.setConnAttemptCount(attempts);
                //                qcf.setConnAttemptDelay(delay);
                //                qcf.setReconnAttemptCount(attempts);
                //                qcf.setReconnAttemptDelay(delay);
            }
            else {
                qcf = EmsHelper.newQueueConnectionFactory(providerURL, sslProperties);
            }
            return qcf;
                /*
                if(sslProperties != null) {
                    return new TibjmsQueueConnectionFactory(providerURL, null, sslProperties);
                }

            return new TibjmsQueueConnectionFactory(providerURL);
            */
        }
    }


    public Properties getSslProperties() {
        return sslProperties;
    }

    public String getTcf() {
        return tcf;
    }

    public TopicConnectionFactory getTopicConnectionFactory(Properties prop) throws Exception {

        if (useJNDI) {
            Object obj = jndiContext.lookup(tcf);
            // todo make work with SSL
            if (useSsl) {
                EmsHelper.setSslPassword(obj, admFactorySslPassword);
            }
            return obj instanceof TopicConnectionFactory ? (TopicConnectionFactory)obj : null;
        } else {
            String connectAttempts = prop.getProperty("com.tibco.tibjms.connect.attempts");
            TopicConnectionFactory tcf = null;
            if ((connectAttempts != null) && (connectAttempts.length() > 0)) {
                String[] ftUrls = providerURL.split(",");
                if (ftUrls.length <= 1) {
                    providerURL = providerURL + "," + providerURL;
                }
                String[] attemptsNDelay = connectAttempts.split(",");
                Integer attempts = new Integer(attemptsNDelay[0]);
                Integer delay = new Integer(500);
                if (attemptsNDelay.length > 1) delay = new Integer(attemptsNDelay[1]);

                tcf = EmsHelper.newTopicConnectionFactory(providerURL, sslProperties);
                setReconnectOptions(tcf, attempts, delay);
                //                tcf.setConnAttemptCount(attempts);
                //                tcf.setConnAttemptDelay(delay);
                //                tcf.setReconnAttemptCount(attempts);
                //                tcf.setReconnAttemptDelay(delay);
            }
            else {
                tcf = EmsHelper.newTopicConnectionFactory(providerURL, sslProperties);
            }
            return tcf;
                /*
                if(sslProperties != null) {
                    return new TibjmsTopicConnectionFactory(providerURL, null, sslProperties);
                }

            return new TibjmsTopicConnectionFactory(providerURL);
            */
        }
    }

    public ConnectionFactory getUnifiedConnectionFactory(Properties prop) throws Exception {
        if (useJNDI) {
            if(tcf == null || qcf == null || !tcf.trim().equals(qcf.trim())) {
                throw new JMSException("Queue Connection Factory and Topic Connection Factory must be the same when " + JMSChannel_Unified.PROP_UNIFIED + " is true.\n"
                        + "Current settings: QCF = " + qcf + ", TCF = " + tcf);
            }
            Object CF = jndiContext.lookup(qcf);
            // todo make work with SSL
            if (useSsl) {
                EmsHelper.setSslPassword(CF, admFactorySslPassword);
            }
            return CF instanceof ConnectionFactory ? (ConnectionFactory)CF : null;
        } else {
            String connectAttempts = prop.getProperty("com.tibco.tibjms.connect.attempts");
            ConnectionFactory cf = null;
            if ((connectAttempts != null) && (connectAttempts.length() > 0)) {
                String[] ftUrls = providerURL.split(",");
                if (ftUrls.length <= 1) {
                    providerURL = providerURL + "," + providerURL;
                }
                String[] attemptsNDelay = connectAttempts.split(",");

                Integer attempts = new Integer(attemptsNDelay[0]);
                Integer delay = new Integer(500);
                if (attemptsNDelay.length > 1) delay = new Integer(attemptsNDelay[1]);
                cf = EmsHelper.newConnectionFactory(providerURL, sslProperties);
                setReconnectOptions(cf, attempts, delay);
            }
            else {
                cf = EmsHelper.newConnectionFactory(providerURL, sslProperties);
            }
            return cf;
        }
    }


    private void setReconnectOptions(ConnectionFactory factory, Integer attempts, Integer delay) {
        try {
            Class klazz = factory.getClass();
            Method setConnAttemptCount = klazz.getMethod("setConnAttemptCount", new Class[]{Integer.TYPE});
            Method setConnAttemptDelay = klazz.getMethod("setConnAttemptDelay", new Class[]{Integer.TYPE});
            Method setReconnAttemptCount = klazz.getMethod("setReconnAttemptCount", new Class[]{Integer.TYPE});
            Method setReconnAttemptDelay = klazz.getMethod("setReconnAttemptDelay", new Class[]{Integer.TYPE});

            setConnAttemptCount.invoke(factory, new Object[]{attempts});
            setConnAttemptDelay.invoke(factory, new Object[]{delay});
            setReconnAttemptCount.invoke(factory, new Object[]{attempts});
            setReconnAttemptDelay.invoke(factory, new Object[]{delay});

            EmsHelper.setExceptionOnFtEvents(true);
        }
        catch (Exception e) {
            //Probably customer is using JMS Client Library < 4.0}
        }
    }

    public boolean isUseJNDI() {
        return useJNDI;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isUseSsl() {
        return useSsl;
    }

    public boolean isTibcoJMS(
            javax.jms.Connection tConnection,
            javax.jms.Connection qConnection) {

        return EmsHelper.isTibcoJMS(tConnection, qConnection);
    }

    public boolean isTibcoJMS(javax.jms.Connection connection) {

        return EmsHelper.isTibcoJMS(connection);
    }


    private BEIdentity getIdentity(
            Ssl ssl)
            throws Exception
    {
        //TODO what if not a reference?
        final String idReference = this.getSubstitutedString(CddTools.getValueFromMixed(ssl.getIdentity()));
        if ((idReference != null) && !idReference.trim().isEmpty()) {
            if (idReference.startsWith("/")) {
                // Runtime; identity is passed as a string reference
                return BEIdentityUtilities.fetchIdentity(this.project, this.gv, idReference);
            }
            else {
                throw new Exception("Incorrect Trusted Certificate Folder string: " + idReference);
            }
        }
        return null;
    }


    private String makeUriString(
            String projectPath)
    {
        final String repoPath = this.project.getRepoPath();
        final File repoFile = new File(repoPath);

        final StringBuilder path = new StringBuilder();
        if (repoFile.isFile()) {
            path.append("archive:jar:").append(repoFile.toURI()).append("!/Shared%20Archive.sar!/");
        } else {
            path.append(repoFile.getAbsolutePath());
            if (!repoPath.endsWith("/")) {
                path.append("/");
            }
        }

        return path
                .append(projectPath.startsWith("/") ? projectPath.substring(1) : projectPath)
                .toString();
    }


    public boolean test()
            throws Exception
    {
        return this.test(new Properties());
    }


    public boolean test(
            Properties properties)
            throws Exception
    {
        if (Boolean.parseBoolean(properties.getProperty(
                JMSChannel_Unified.PROP_UNIFIED, Boolean.FALSE.toString()))) {

            final ConnectionFactory cf = this.getUnifiedConnectionFactory(properties);
            if (null != cf) {
                final Connection connection = JMSChannel_Unified.createConnection(this, cf, properties);
                if (null != connection) {
                    connection.close();
                    return true;
                }
            }
            return false;

        } else {
            boolean queueConnectionCreated = false;
            final QueueConnectionFactory qcf = this.getQueueConnectionFactory(properties);
            if (null != qcf) {
                final Connection connection = JMSChannel.createQueueConnection(this, qcf, properties);
                if (null != connection) {
                    queueConnectionCreated = true;
                    connection.close();
                }
            }

            boolean topicConnectionCreated = false;
            final TopicConnectionFactory tcf = this.getTopicConnectionFactory(properties);
            if (null != tcf) {
                final Connection connection = JMSChannel.createTopicConnection(this, tcf, properties);
                if (null != connection) {
                    topicConnectionCreated = true;
                    connection.close();
                }
            }

            return queueConnectionCreated && topicConnectionCreated;
        }
    }

}
