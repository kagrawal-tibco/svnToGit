package com.tibco.rta.service.transport.jms;

import java.util.Properties;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.transport.AbstractRtaConnectorInfo;
import com.tibco.rta.util.PasswordObfuscation;
import com.tibco.rta.util.RTASecurityException;
import com.tibco.security.AXSecurityException;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSConnectorInfo extends AbstractRtaConnectorInfo {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    private String jndiFactory;

    private String jmsProviderURL;

    private String queueConnFactory;

    private String inboundQueue;

    private String inboundQueryQueue;

    private String outboundQueue;

    private String connUsername;

    private String connPassword;

    private JMSAckModes ackMode;

    public JMSConnectorInfo(Properties properties) {
        jndiFactory = properties.getProperty(ConfigProperty.RTA_JMS_JNDI_CONTEXT_FACTORY.getPropertyName(), ConfigProperty.RTA_JMS_JNDI_CONTEXT_FACTORY.getDefaultValue());
        jmsProviderURL = properties.getProperty(ConfigProperty.RTA_JMS_JNDI_URL.getPropertyName(), ConfigProperty.RTA_JMS_JNDI_URL.getDefaultValue());
        queueConnFactory = properties.getProperty(ConfigProperty.RTA_JMS_QUEUE_CONN_FACTORY.getPropertyName(), ConfigProperty.RTA_JMS_QUEUE_CONN_FACTORY.getDefaultValue());
        inboundQueue = properties.getProperty(ConfigProperty.RTA_JMS_INBOUND_QUEUE.getPropertyName(), ConfigProperty.RTA_JMS_INBOUND_QUEUE.getDefaultValue());
        inboundQueryQueue = properties.getProperty(ConfigProperty.RTA_JMS_INBOUND_QUERY_QUEUE.getPropertyName(), ConfigProperty.RTA_JMS_INBOUND_QUERY_QUEUE.getDefaultValue());
        outboundQueue = properties.getProperty(ConfigProperty.RTA_JMS_OUTBOUND_QUEUE.getPropertyName(), ConfigProperty.RTA_JMS_OUTBOUND_QUEUE.getDefaultValue());
        connUsername = properties.getProperty(ConfigProperty.RTA_JMS_CONNECTION_USERNAME.getPropertyName(), ConfigProperty.RTA_JMS_CONNECTION_USERNAME.getDefaultValue());
        connPassword = properties.getProperty(ConfigProperty.RTA_JMS_CONNECTION_PASSWORD.getPropertyName(), ConfigProperty.RTA_JMS_CONNECTION_PASSWORD.getDefaultValue());
        ackMode = JMSAckModes.valueOf(properties.getProperty(ConfigProperty.RTA_JMS_ACK_MODE.getPropertyName(), ConfigProperty.RTA_JMS_ACK_MODE.getDefaultValue()));

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Logging JMS Transport Configuration");

            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_JNDI_CONTEXT_FACTORY.getPropertyName(), jndiFactory);
            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_JNDI_URL.getPropertyName(), jmsProviderURL);
            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_QUEUE_CONN_FACTORY.getPropertyName(), queueConnFactory);
            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_INBOUND_QUEUE.getPropertyName(), inboundQueue);
            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_OUTBOUND_QUEUE.getPropertyName(), outboundQueue);
            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_CONNECTION_USERNAME.getPropertyName(), connUsername);
            LOGGER.log(Level.INFO, "[%s] : Value [%s]", ConfigProperty.RTA_JMS_ACK_MODE.getPropertyName(), ackMode);
        }
    }

    public String getJndiFactory() {
        return jndiFactory;
    }

    public String getJmsProviderURL() {
        return jmsProviderURL;
    }

    public String getQueueConnFactory() {
        return queueConnFactory;
    }

    public String getInboundQueue() {
        return inboundQueue;
    }

    public String getInboundQueryQueue() {
        return inboundQueryQueue;
    }

    public String getOutboundQueue() {
        return outboundQueue;
    }

    public String getConnUsername() {
        return connUsername;
    }

    public String getConnPassword() {
        try {
            return PasswordObfuscation.decrypt(connPassword);
        } catch (RTASecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JMSAckModes getAckMode() {
        return ackMode = (ackMode == null) ? JMSAckModes.TIBCO_EXPLICIT : ackMode;
    }
}
