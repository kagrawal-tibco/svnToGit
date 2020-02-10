package com.tibco.cep.driver.jms;


import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.model.event.CommandListener;
import com.tibco.security.AXSecurityException;

import javax.jms.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class JMSChannel
        extends BaseJMSChannel
        implements CommandChannel {


    protected QueueConnection        m_jmsQueueConnection = null;
    protected TopicConnection        m_jmsTopicConnection = null;

    protected QueueSession           m_commandQueueSession = null;
    protected TopicSession           m_commandTopicSession = null;

    protected QueueConnectionFactory m_QCF = null;
    protected TopicConnectionFactory m_TCF = null;


    protected JMSChannel(ChannelManager manager, String uri, JMSChannelConfig config) {
        super(manager, uri, config);
    }

    protected void createCommandSenderSessions(String key) throws Exception{
        if (commandQueueSenders.get(key) == null) {
            Queue commandQueue = this.m_commandQueueSession.createQueue(key + ".$commandQueue" );
            QueueSender qs= this.m_commandQueueSession.createSender(commandQueue);
            commandQueueSenders.put(key, qs);

        }

        if (commandTopicSenders.get(key) == null) {
            Topic commandTopic = this.m_commandTopicSession.createTopic(key + ".$commandTopic");
            TopicPublisher tp= this.m_commandTopicSession.createPublisher(commandTopic);
            commandTopicSenders.put(key, tp);

        }

        // create senders
    }

    protected void createCommandReceiverSessions(String key, CommandListener commandListener) throws Exception{
        if (commandQueueListeners.get(key) == null) {
            Queue commandQueue = this.m_commandQueueSession.createQueue(key + ".$commandQueue" );
            QueueReceiver qr = this.m_commandQueueSession.createReceiver(commandQueue, null);
            qr.setMessageListener(new CommandMsgListener(commandListener, getChannelManager().getCommandFactory()));
            commandQueueListeners.put(key, qr);
        }

        if (commandTopicListeners.get(key) == null) {
            Topic commandTopic = this.m_commandTopicSession.createTopic(key + ".$commandTopic");
            TopicSubscriber ts = this.m_commandTopicSession.createSubscriber(commandTopic, null, false);
            ts.setMessageListener(new CommandMsgListener(commandListener, getChannelManager().getCommandFactory()));
            commandTopicListeners.put(key, ts);
        }
    }

    protected void createCommandSessions(String key, CommandListener commandListener) throws Exception{
        createCommandReceiverSessions(key, commandListener);
        if (commandListener != null)
            createCommandSenderSessions(key);
    }


    protected BytesMessage createCommandMessage(
            boolean oneToMany)
            throws Exception {

        if (oneToMany) {
            if (null == this.m_commandTopicSession) {
                return null;
            } else {
                return this.m_commandTopicSession.createBytesMessage();
            }
        } else {
            if (null == this.m_commandQueueSession) {
                return null;
            } else {
                return this.m_commandQueueSession.createBytesMessage();
            }
        }
    }


    protected void createSessions() throws JMSException {

        final boolean transacted = ((JMSChannelConfig) channelConfig).isTransacted();

        if (null != this.m_jmsTopicConnection) {
            this.m_commandTopicSession =
                    this.m_jmsTopicConnection.createTopicSession(transacted, this.getCommandTopicAckMode());
        }
        if (null != this.m_jmsQueueConnection) {
            this.m_commandQueueSession =
                    this.m_jmsQueueConnection.createQueueSession(transacted, this.getCommandQueueAckMode());
        }
    }

    protected void createConnections()
            throws JMSException, AXSecurityException
    {
        final Properties p = this.getServiceProviderProperties();

        this.m_jmsQueueConnection = createQueueConnection(
                (JMSChannelConfig) channelConfig,
                this.m_QCF,
                p);
        if (null != this.m_jmsQueueConnection) {
            this.m_jmsQueueConnection.setExceptionListener(this.exceptionListener);
        }

        this.m_jmsTopicConnection = createTopicConnection(
                (JMSChannelConfig) channelConfig,
                this.m_TCF,
                p);
        if (null != this.m_jmsTopicConnection) {
            this.m_jmsTopicConnection.setExceptionListener(this.exceptionListener);
        }
    }


    public static QueueConnection createQueueConnection(
            JMSChannelConfig config,
            QueueConnectionFactory cf,
            Properties p)
            throws JMSException, AXSecurityException
    {
        if (null != cf) {
            final String uri = config.getURI();
            final List<String> disabledQueueUris = Arrays.asList(
                    (p.getProperty(PROP_DISABLED_QUEUE_URIS, "").trim() + " ").split("[,\\s]+"));

            if (!disabledQueueUris.contains(uri)) {
                final String user = config.getUserID();
                final QueueConnection jmsConnection = ((null == user) || user.trim().isEmpty())
                        ? cf.createQueueConnection()
                        : cf.createQueueConnection(user, getConnectionPassword(config));

                if (null != jmsConnection) {
                    String clientId = config.clientID;
                    if ((null != clientId) && !clientId.trim().isEmpty()) {
                        clientId = clientId.split("\\s+")[0];
                        setClientId(jmsConnection, clientId, 0, p);
                    }

                    return jmsConnection;
                }
            }
        }

        return null;
    }

    public static TopicConnection createTopicConnection(
            JMSChannelConfig config,
            TopicConnectionFactory cf,
            Properties p)
            throws JMSException, AXSecurityException
    {
        if (null != cf) {
            final String uri = config.getURI();
            final List<String> disabledTopicUris = Arrays.asList(
                    (p.getProperty(PROP_DISABLED_TOPIC_URIS, "").trim() + " ").split("[,\\s]+"));

            if (!disabledTopicUris.contains(uri)) {
                final String user = config.getUserID();
                final TopicConnection jmsConnection = ((null == user) || user.trim().isEmpty())
                        ? cf.createTopicConnection()
                        : cf.createTopicConnection(user, getConnectionPassword(config));

                if (null != jmsConnection) {
                    String clientId = config.clientID;
                    if ((null != clientId) && !clientId.trim().isEmpty()) {
                        String[] clientIds = config.clientID.split("\\s+");
                        if(clientIds.length > 1) {
                            clientId = clientIds[1];
                        } else {
                            clientId = clientIds[0];
                        }
                        setClientId(jmsConnection, clientId, 0, p);
                    }

                    return jmsConnection;
                }
            }
        }

        return null;
    }


    protected void createConnectionFactories() throws Exception {
    	JMSChannelConfig config = (JMSChannelConfig)channelConfig;
        m_QCF = config.getQueueConnectionFactory(this.getServiceProviderProperties());
        if (m_QCF == null && hasQueue) {
            throw new JMSException("JMS QueueConnectionFactory is null while Queue destination is configured.");
        }
        m_TCF = config.getTopicConnectionFactory(this.getServiceProviderProperties());
        if (m_TCF == null && hasTopic) {
            throw new JMSException("JMS TopicConnectionFactory is null while Topic destination is configured.");
        }    
    }
    
    protected boolean isTibcoJMS() {
        return ((JMSChannelConfig)channelConfig).isTibcoJMS(m_jmsTopicConnection, m_jmsQueueConnection);
    }

    protected void startConnections() throws JMSException {
    	if (m_jmsTopicConnection != null) m_jmsTopicConnection.start();
    	if (m_jmsQueueConnection != null) m_jmsQueueConnection.start();
    }
    
    protected void stopConnections() throws JMSException {
	    if (m_jmsQueueConnection != null) m_jmsQueueConnection.stop();
	    if (m_jmsTopicConnection != null) m_jmsTopicConnection.stop();
    }
    
    protected void closeConnections() {
    	try {
            if (m_jmsQueueConnection != null) m_jmsQueueConnection.close();
        } catch (JMSException e) {
            //e.printStackTrace(); eat the exception
        }
        try {
            if (m_jmsTopicConnection != null) m_jmsTopicConnection.close();
        } catch (JMSException e) {
            //e.printStackTrace(); //eat the exception
        }
    }
    
    protected void doChildShutdown() {
    	if (m_jmsQueueConnection != null) {
            try {
                m_jmsQueueConnection.stop();
            } catch(Exception ex) {}//eat}

            try {
                m_jmsQueueConnection.close();
            } catch(Exception ex) {}//eat}

            m_jmsQueueConnection = null;
        }
        if (m_jmsTopicConnection != null) {
            try {
                m_jmsTopicConnection.stop();
            } catch(Exception ex) {}//eat}

            try {
                m_jmsTopicConnection.close();
            } catch(Exception ex) {}//eat}

            m_jmsTopicConnection= null;
        }

        m_QCF=null;
        m_TCF=null;
    }

    public TopicConnection getTopicConnection() {
        return m_jmsTopicConnection;
    }

    public QueueConnection getQueueConnection() {
        return m_jmsQueueConnection;
    }
    
    protected void _setClientID(String clientId) throws JMSException {
	    if (m_jmsQueueConnection != null) m_jmsQueueConnection.setClientID(clientId + "_Queue");
	    if (m_jmsTopicConnection != null) m_jmsTopicConnection.setClientID(clientId + "_Topic");
    }
}