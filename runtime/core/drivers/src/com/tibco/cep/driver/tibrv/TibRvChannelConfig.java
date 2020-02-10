package com.tibco.cep.driver.tibrv;

import java.io.FileReader;
import java.security.cert.CertificateEncodingException;
import java.util.*;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BETrustedCertificateManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.util.Base64;
import com.tibco.security.Cert;
import com.tibco.security.TrustedCerts;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jul 26, 2006
 * Time: 5:39:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TibRvChannelConfig  implements ChannelConfig, TibRvConstants {

    ChannelConfig config;
    GlobalVariables gv;
    ArchiveResourceProvider provider;
    RuleServiceProvider rsProvider;

    private String  daemon;
    private String  service;
    private String  network;

    int workerWeight;
    int workerTasks;
    String workerCompleteTime;
    int schedulerWeight;
    double schedulerHeartbeat;
    double schedulerActivation;

    String showExpertSettings;
    String cmName;
    boolean syncLedger;
    String ledgerFile;
    String cmqName;

    private boolean useSsl;
    private BEIdentity sslIdentity;
    private Set<String> sslDaemonCertificates;


    TibRvChannelConfig (ChannelConfig config, GlobalVariables gv, ArchiveResourceProvider provider, RuleServiceProvider rsProvider) throws Exception {

        this.config = config;
        this.gv = gv;
        this.provider = provider;
        this.rsProvider = rsProvider;

        ConfigurationMethod method = getConfigurationMethod();
        if (method == ConfigurationMethod.PROPERTIES) configureByProperties();
        else configureByReference();

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

    public String getDaemon() {
        return daemon;
    }

    public String getService() {
        return service;
    }

    public String getNetwork() {
        return network;
    }

    public String getServerType() {
        return null;
    }

    public String getUri() {
        return config.getURI();
    }

    public String getCmName() {
        return cmName;
    }

    public String getCmqName() {
        return cmqName;
    }

    public String getLedgerFile() {
        return ledgerFile;
    }

    public double getSchedulerActivation() {
        return schedulerActivation;
    }

    public double getSchedulerHeartbeat() {
        return schedulerHeartbeat;
    }

    public String getShowExpertSettings() {
        return showExpertSettings;
    }

    public int getSchedulerWeight() {
        return schedulerWeight;
    }

    public String getWorkerCompleteTime() {
        return workerCompleteTime;
    }

    public int getWorkerTasks() {
        return workerTasks;
    }

    public int getWorkerWeight() {
        return workerWeight;
    }

    public boolean getSyncLedger() {
        return syncLedger;
    }

    @Override
    public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    public void configureByReference() throws Exception{
        String ref = this.getReferenceURI();
        XiNode rootNode = provider.getResourceAsXiNode(ref);
        final XiNode configNode = XiChild.getChild(rootNode, NODE_NAME_CONFIG);

        service = getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_PROPERTY_SERVICE);
        network = getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_PROPERTY_NETWORK);
        daemon = getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_PROPERTY_DAEMON);
        workerWeight = Integer.parseInt(
                getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_WORKERWEIGHT ));
        workerTasks = Integer.parseInt(
                getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_WORKERTASKS));
        workerCompleteTime = getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_WORKERCOMPLETETIME);
        schedulerWeight = Integer.parseInt(
                getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_SCHEDULEWEIGHT));
        String schedulerHeartbeatStr =
                getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_SCHEDULEHEARTBEAT);
        schedulerHeartbeat = Double.parseDouble(((schedulerHeartbeatStr == null) ||
                (schedulerHeartbeatStr.length() == 0)) ? "0" : schedulerHeartbeatStr);
        String schedulerActivationStr =
                getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_SCHEDULEACTIVATION);
        schedulerActivation = Double.parseDouble(((schedulerActivationStr == null) ||
                (schedulerActivationStr.length() == 0)) ? "0" : schedulerActivationStr);
        showExpertSettings = getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_SHOWEXPERTSETTINGS );
        this.cmName = this.getSubstitutedStringValueForRvcm(this.gv, configNode, SHARED_CONFIG_CHANNEL_CMNAME, '.');
        syncLedger = Boolean.valueOf(
                getSubstitutedStringValue(gv, configNode,SHARED_CONFIG_CHANNEL_SYNCLEDGER)).booleanValue();
        this.ledgerFile = this.getSubstitutedStringValueForRvcm(this.gv, configNode, SHARED_CONFIG_CHANNEL_LEDGERFILE, '_');
        this.cmqName = this.getSubstitutedStringValueForRvcm(this.gv, configNode, SHARED_CONFIG_CHANNEL_CMQNAME, '.');

        this.useSsl = Boolean.parseBoolean(
                this.getSubstitutedStringValue(gv, configNode, SHARED_CONFIG_CHANNEL_PROPERTY_USE_SSL));
        if (this.useSsl) {
            if (null != this.daemon) {
                if (this.daemon.toLowerCase().startsWith("ssl:")) {
                    this.daemon = "ssl:" + this.daemon.substring(4);
                } else {
                    this.daemon = "ssl:" + this.daemon;
                }
            }
            this.parseSsl(XiChild.getChild(configNode, SHARED_CONFIG_CHANNEL_PROPERTY_SSL));
        }
    }

    private void parseSsl(
            XiNode sslNode)
            throws Exception {

        this.sslDaemonCertificates = new HashSet<String>();
        final String certPath = this.getSubstitutedStringValue(gv, sslNode, SHARED_CONFIG_CHANNEL_PROPERTY_CERT);
        if ((certPath != null) && !certPath.trim().isEmpty()) {
            if (certPath.contains(":")) { // Global Variable having a file reference
                final FileReader reader = new FileReader(certPath);
                try {
                    final StringBuilder sb = new StringBuilder();
                    for (int i = 0; i != -1; ) {
                        i = reader.read();
                        sb.append((char) i);
                    }
                    this.sslDaemonCertificates.add(sb.toString());
                } finally {
                    reader.close();
                }
            } else {
                final TrustedCerts trustedCerts = BETrustedCertificateManager.getInstance()
                        .getTrustedCerts(this.provider, this.gv, certPath, true);
                for (final Cert cert : trustedCerts.getCertificateList()) {
                    this.sslDaemonCertificates.add(this.convertToPem(cert.getCertificate().getEncoded()));
                }
            }
        }

        final String idReference = this.getSubstitutedStringValue(gv, sslNode, SHARED_CONFIG_CHANNEL_PROPERTY_IDENTITY);
        if ((idReference != null) && !idReference.trim().isEmpty()) {
            if (!idReference.startsWith("/")) {
                throw new Exception("Invalid SSL ID reference: " + idReference);
            }
            this.sslIdentity = BEIdentityUtilities.fetchIdentity(provider, gv, idReference);
        }
    }

    private String convertToPem(
            byte[] bytes)
            throws CertificateEncodingException {

        final String b64Cert = Base64.encodeBytes(bytes, Base64.DONT_BREAK_LINES);

        final StringBuilder sb = new StringBuilder("-----BEGIN CERTIFICATE-----\n");
        final int length = b64Cert.length();
        for (int i = 0; i < length; i += 64) {
            sb.append(b64Cert.substring(i, Math.min(length, i + 64))).append("\n");
        }
        return sb.append("-----END CERTIFICATE-----").toString();
    }


    public void configureByProperties() {
        service = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_SERVICE.getLocalName());
        network = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_NETWORK.getLocalName());
        daemon = getSubstitutedStringValue(gv, config, CHANNEL_PROPERTY_DAEMON.getLocalName());

    }


    /**
     * @param gv GlobalVariables instance.
     * @param config DriverConfig that holds the property.
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
     * @param gv GlobalVariables instance.
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
     * @param gv GlobalVariables instance.
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

    /**
     * @param gv GlobalVariables instance.
     * @param node XiNode that is the immediate parent of the node to process.
     * @param name String name of the node that contains the String.
     * @return String value of nodes for RVCM after substituting all global variables.
     */
    protected String getSubstitutedStringValueForRvcm(
            GlobalVariables gv,
            XiNode node,
            ExpandedName name,
            char replacementChar) {
        String value = XiChild.getString(node, name);

        if (value == null) {
            return "";

        } else {
            value = value.replaceAll("%%EngineName%%",
                    this.rsProvider.getName().replace(' ', replacementChar).replace('/', replacementChar));

            value = value.replaceAll("%%ChannelURI%%",
                    this.getURI().substring(1).replace(' ', replacementChar).replace('/', replacementChar));

            value = value.replaceAll("%%ChannelName%%",
                    this.getName().replace(' ', replacementChar).replace('/', replacementChar));

            return gv.substituteVariables(value).toString();
        }//else
    }//getSubstitutedStringValueForRvcm

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


    public GlobalVariables getGv() {
        return gv;
    }

    public ArchiveResourceProvider getProvider() {
        return provider;
    }

    public boolean isUseSsl() {
        return useSsl;
    }

    public Set<String> getSslDaemonCertificates() {
        return this.sslDaemonCertificates;
    }

    public BEIdentity getSslIdentity() {
        return this.sslIdentity;
    }

}

