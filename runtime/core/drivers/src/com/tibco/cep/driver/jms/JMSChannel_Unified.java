package com.tibco.cep.driver.jms;


import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.model.event.CommandListener;
import com.tibco.security.AXSecurityException;

import javax.jms.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class JMSChannel_Unified
        extends BaseJMSChannel
        implements CommandChannel {


	public final static String PROP_UNIFIED = "be.channel.jms.unified";
	
    protected Connection        m_jmsConnection = null;

    protected Session           m_commandSession = null;

    protected ConnectionFactory m_CF = null;

    protected JMSChannel_Unified(ChannelManager manager, String uri, JMSChannelConfig config) {
        super(manager, uri, config);
    }

    protected void createCommandSenderSessions(String key) throws Exception{
        if (commandQueueSenders.get(key) == null) {
            javax.jms.Destination commandQueue = this.m_commandSession.createQueue(key + ".$commandQueue" );
            MessageProducer qs = this.m_commandSession.createProducer(commandQueue);
            commandQueueSenders.put(key, qs);

        }

        if (commandTopicSenders.get(key) == null) {
        	javax.jms.Destination commandTopic = this.m_commandSession.createTopic(key + ".$commandTopic");
            MessageProducer tp = this.m_commandSession.createProducer(commandTopic);
            commandTopicSenders.put(key, tp);

        }

        // create senders
    }

    protected void createCommandReceiverSessions(String key, CommandListener commandListener) throws Exception{
        if (commandQueueListeners.get(key) == null) {
        	javax.jms.Destination commandQueue = this.m_commandSession.createQueue(key + ".$commandQueue" );
            MessageConsumer qr = this.m_commandSession.createConsumer(commandQueue, null);
            qr.setMessageListener(new CommandMsgListener(commandListener, getChannelManager().getCommandFactory()));
            commandQueueListeners.put(key, qr);
        }

        if (commandTopicListeners.get(key) == null) {
        	javax.jms.Destination commandTopic = this.m_commandSession.createTopic(key + ".$commandTopic");
            MessageConsumer ts = this.m_commandSession.createConsumer(commandTopic, null, false);
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

        if (null == this.m_commandSession) {
            return null;
        } else {
            return this.m_commandSession.createBytesMessage();
        }
    }


    protected void createSessions()
            throws JMSException {

        final int queueAckMode =  this.getCommandQueueAckMode();
        final int topicAckMode =  this.getCommandTopicAckMode();
        if (topicAckMode != queueAckMode) {
            throw new JMSException(
                    "be.channel.tibjms.topic.ack.mode and be.channel.tibjms.queue.ack.mode must be the same when "
                            + PROP_UNIFIED + " is true.\nChannel Details:\n" + this);
        }

        final boolean transacted = ((JMSChannelConfig)channelConfig).isTransacted();

        if (m_jmsConnection != null) {
            this.m_commandSession = m_jmsConnection.createSession(transacted, queueAckMode);
        }
    }

    protected void createConnections()
            throws JMSException, AXSecurityException
    {
        this.m_jmsConnection = createConnection(
                (JMSChannelConfig) channelConfig,
                this.m_CF,
                this.getServiceProviderProperties());

        if (null != this.m_jmsConnection) {
            this.m_jmsConnection.setExceptionListener(this.exceptionListener);
        }
    }


    public static Connection createConnection(
            JMSChannelConfig config,
            ConnectionFactory cf,
            Properties p)
            throws JMSException, AXSecurityException
    {
        if (null != cf) {
            final String uri = config.getURI();
            final List<String> disabledQueueUris = Arrays.asList(
                    (p.getProperty(PROP_DISABLED_QUEUE_URIS, "").trim() + " ").split("[,\\s]+"));
            final List<String> disabledTopicUris = Arrays.asList(
                    (p.getProperty(PROP_DISABLED_TOPIC_URIS, "").trim() + " ").split("[,\\s]+"));

            if (!(disabledQueueUris.contains(uri) && disabledTopicUris.contains(uri))) {
                final String user = config.getUserID();
                final Connection jmsConnection = ((null == user) || user.trim().isEmpty())
                        ? cf.createConnection()
                        : cf.createConnection(user, getConnectionPassword(config));

                if (null != jmsConnection) {
                    final String clientId = config.clientID;
                    if ((null != clientId) && !clientId.trim().isEmpty()) {
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
        m_CF = config.getUnifiedConnectionFactory(getServiceProviderProperties());
        if (m_CF == null && (hasQueue || hasTopic)) {
            throw new JMSException("JMS ConnectionFactory is null while destination is configured.");
        }
    }
    
    protected boolean isTibcoJMS() {
        return ((JMSChannelConfig)channelConfig).isTibcoJMS(m_jmsConnection);
    }

    protected void startConnections() throws JMSException {
    	if (m_jmsConnection != null) m_jmsConnection.start();
    }
    
    protected void stopConnections() throws JMSException {
	    if (m_jmsConnection != null) m_jmsConnection.stop();
    }
    
    protected void closeConnections() {
    	try {
            if (m_jmsConnection != null) m_jmsConnection.close();
        } catch (JMSException e) {
            //e.printStackTrace(); eat the exception
        }
    }
    
    protected void doChildShutdown() {
    	if (m_jmsConnection != null) {
            try {
                m_jmsConnection.stop();
            } catch(Exception ex) {}//eat}

            try {
                m_jmsConnection.close();
            } catch(Exception ex) {}//eat}

            m_jmsConnection = null;
        }

        m_CF=null;
    }


    public Connection getTopicConnection() {
        return m_jmsConnection;
    }

    public Connection getQueueConnection() {
        return m_jmsConnection;
    }
    
    protected void _setClientID(String clientId) throws JMSException {
    	if (m_jmsConnection != null) m_jmsConnection.setClientID(clientId);
    }
}