package com.tibco.rta.client.jms;

import com.tibco.rta.AbstractRtaConnection;
import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.client.ConnectionException;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.SessionInitFailedException;
import com.tibco.rta.client.notify.MessageReceptionNotifier;
import com.tibco.rta.client.notify.impl.SessionEstablishmentNotifier;
import com.tibco.rta.client.notify.impl.SyncJMSNotifier;
import com.tibco.rta.client.tcp.TCPConnectionEvent;
import com.tibco.rta.client.transport.impl.jms.JMSMessageReceptionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.service.transport.TransportTypes;
import com.tibco.rta.util.PasswordObfuscation;
import com.tibco.rta.util.ServiceConstants;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/3/13
 * Time: 11:57 AM
 * JMS based implementation of {@link com.tibco.rta.RtaConnection}
 */
public class JMSRtaConnection extends AbstractRtaConnection {

    private Map<ConfigProperty, PropertyAtom<?>> connectionConfiguration;

    /**
     * Reference to jndi context
     */
    private Context context;

    /**
     * Shared queue connection
     */
    private QueueConnection queueConnection;

    /**
     * Message consumer for responses sent.
     */
    private MessageConsumer asyncMessageConsumer;

    /**
     * Queue and message producer manager for this instance
     */
    private QueueSessionManager queueSessionManager;

    /**
     * Synchronous operations response timeout.
     */
    private long syncResponseTimeOut;

    /**
     * Synchronous operation messages should have expiry time in millis.
     */
    private long syncOpExpiryTime;

    /**
     * Endpoint names versus their queues.
     */
    private Map<String, Queue> endpoints = new ConcurrentHashMap<String, Queue>();


    /**
     * If the JMS provider is TIBCO
     */
    private boolean isTibcoJMS;


    /**
     * If the JMS provider is TIBCO should we compress message
     */
    private boolean shouldCompressTibJMSMsg;

    public JMSRtaConnection(Map<ConfigProperty, PropertyAtom<?>> connectionConfiguration) throws Exception {
        this.connectionConfiguration = connectionConfiguration;

        username = (String) ConfigProperty.CONNECTION_USERNAME.getValue(connectionConfiguration);
        password = (String) ConfigProperty.CONNECTION_PASSWORD.getValue(connectionConfiguration);
        password = PasswordObfuscation.decrypt(password);
        this.queueSessionManager = new QueueSessionManager();
        isTibcoJMS = ConfigProperty.JMS_PROVIDER_JNDI_FACTORY.getValue(connectionConfiguration) == ConfigProperty.JMS_PROVIDER_JNDI_FACTORY.getDefaultValue().getValue();
        shouldCompressTibJMSMsg = (Boolean) ConfigProperty.JMS_MESSAGE_COMPRESS.getValue(connectionConfiguration);
        initialize();
    }

    /**
     * When connection is created from existing one.
     *
     * @throws Exception
     */
    public JMSRtaConnection(Map<ConfigProperty, PropertyAtom<?>> connectionConfiguration, Set<DefaultRtaSession> sessions) throws Exception {
        this(connectionConfiguration);
        this.sessions = sessions;
    }

    private Hashtable<?, ?> initializeContext() throws Exception {
        //Initialize environment
        Hashtable<Object, Object> env = new Hashtable<Object, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, ConfigProperty.JMS_PROVIDER_JNDI_FACTORY.getValue(connectionConfiguration));
        env.put(Context.PROVIDER_URL, ConfigProperty.JMS_PROVIDER_JNDI_URL.getValue(connectionConfiguration));
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        if (username == null) {
            throw new Exception("Username to connect to JMS provider cannot be null");
        }
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);

        return env;
    }

    public void initialize() throws Exception {
        //There are some pieces here which may not work as expected hence adding debug logs.
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing JMS connection");
        }
        //Establish context
        try {
            context = new InitialContext(initializeContext());

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "JNDI context obtained [%s]", context);
            }
            //Look up and initialize factory
            String queueConnFactory = (String) ConfigProperty.JMS_QUEUE_CONN_FACTORY.getValue(connectionConfiguration);

            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup(queueConnFactory);

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Queue Connection Factory lookup obtained [%s]", queueConnectionFactory);
            }
            //Create connection and session
            queueConnection = queueConnectionFactory.createQueueConnection(username, password);

            queueConnection.setExceptionListener(new ConnectionExceptionListener());
            //Starting connection is very imp.
            queueConnection.start();

            //Get sync response timeout
            syncResponseTimeOut = (Long) ConfigProperty.SYNC_RESPONSE_TIMEOUT.getValue(connectionConfiguration);
            //Expiry time for sync ops.
            syncOpExpiryTime = (Long) ConfigProperty.SYNC_JMS_MESSAGE_EXPIRY.getValue(connectionConfiguration);

        } catch (Throwable e) {
            throw new ConnectionException(e);
        }
    }


    public Properties getConnectionConfigurationAsProperties() {
        Properties connectionConfig = new Properties();
        for (Map.Entry<ConfigProperty, PropertyAtom<?>> entry : connectionConfiguration.entrySet()) {
            connectionConfig.setProperty(entry.getKey().getPropertyName(), "" + entry.getValue().getValue());
        }
        return connectionConfig;
    }

    public Map<ConfigProperty, PropertyAtom<?>> getConnectionConfigurationAsMap() {
        return connectionConfiguration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ServiceResponse invokeService(String endpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         String payload) throws Exception {
        //Look up destination with this name
        Queue jmsDestination = lookupEndpoint(endpoint);
        return invokeService(jmsDestination, serviceOp, properties, payload);
    }

    /**
     * Attach async consumer for named sessions to outbound destination.
     */
    public <M extends MessageReceptionNotifier & MessageListener> void attachAsyncConsumer(M messageNotifier, String notificationsEndpoint, String sessionId) throws JMSException, NamingException {
        Queue notificationsDestination = (Queue) context.lookup(notificationsEndpoint);

        QueueSession queueSession = getOrCreateQueueSession();
        //Also attach listener to same destination with selector
        asyncMessageConsumer = queueSession.createConsumer(notificationsDestination, setSelector(sessionId));
        //Bad but no choice since we are retrofitting
        asyncMessageConsumer.setMessageListener(messageNotifier);
    }

    /**
     * Send a message to destination and forget. This necessarily does not need to be a service invocation.
     */
    public void sendMessage(Queue jmsDestinationEndpoint,
                            Map<String, String> properties,
                            String payload) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Sending confirmation message");
        }
        QueueSession queueSession = getOrCreateQueueSession();
        MessageProducer messageProducer = queueSession.createProducer(jmsDestinationEndpoint);
        TextMessage message = queueSession.createTextMessage(payload);

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (ServiceConstants.MESSAGE_PRIORITY.equals(key)) {
                //Add priority header
                //TODO file this as a bug.
                messageProducer.setPriority(Integer.parseInt(value));
            } else {
                message.setStringProperty(key, value);
            }
        }
        //These messages need not be persistent
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //Set expiry
        messageProducer.setTimeToLive(syncOpExpiryTime);
        messageProducer.send(message);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Message sent [%s]", message);
        }

        messageProducer.close();
    }


    public ServiceResponse invokeService(Queue jmsDestinationEndpoint,
                                         String serviceOp,
                                         Map<String, String> properties,
                                         String payload) throws Exception {

        MessageProducer messageProducer = null;
        try {
            QueueSession queueSession = getOrCreateQueueSession();
            messageProducer = queueSession.createProducer(jmsDestinationEndpoint);
            TextMessage message = queueSession.createTextMessage(payload);
            //Create temp queue and set this as response queue
            TemporaryQueue temporaryQueue = queueSession.createTemporaryQueue();

            for (Map.Entry<String, String> entry : properties.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (ServiceConstants.MESSAGE_PRIORITY.equals(key)) {
                    //Add priority header
                    //TODO file this as a bug.
                    messageProducer.setPriority(Integer.parseInt(value));
                } else {
                    message.setStringProperty(key, value);
                }
            }
            //These messages need not be persistent
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //Set expiry
            messageProducer.setTimeToLive(syncOpExpiryTime);
            message.setJMSReplyTo(temporaryQueue);
            messageProducer.send(message);

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Message sent [%s]", message);
            }

            //Set receiver on it
            MessageConsumer queueConsumer = queueSession.createConsumer(temporaryQueue);

            SyncJMSNotifier syncJMSNotifier = new SyncJMSNotifier(syncResponseTimeOut, queueConsumer, temporaryQueue);
            //We should be expecting response on the temp queue
            queueConsumer.setMessageListener(syncJMSNotifier);

            return syncJMSNotifier.getServiceResponse();
        } finally {
            if (messageProducer != null) {
                messageProducer.close();
            }
        }
    }

    /**
     * @param endpoint   A url depending on the underlying transport
     * @param serviceOp  The operation on the service
     * @param properties Any optional properties which will be sent over
     * @param payload    Payload bytes
     * @throws Exception
     */
    @Override
    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties,
                                         byte[] payload) throws Exception {

        ServiceResponse<?> serviceResponse = null;
        TemporaryQueue temporaryQueue;
        // Look up destination with this name
        Queue jmsDestination = lookupEndpoint(endpoint);
        QueueSession queueSession = getOrCreateQueueSession();
        BytesMessage message = queueSession.createBytesMessage();
        message.writeBytes(payload);

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String key = entry.getKey();

            String value = entry.getValue();

            if (ServiceConstants.MESSAGE_PRIORITY.equals(key)) {
                // Add priority header via producer
                // TODO file this as a bug.
                // messageProducer.setPriority(Integer.parseInt(value));
            } else {
                message.setStringProperty(key, value);
            }
        }

        // Add compression if TIBCO EMS

        if (isTibcoJMS && shouldCompressTibJMSMsg) {
            message.setBooleanProperty("JMS_TIBCO_COMPRESS", true);
        }

        MessageProducer messageProducer;
        //  fact publisher operation is async and persistent delivery mode
        //  all other operations are sync and with non-persistent mode
        if (serviceOp.equals("assertFact")) {
            messageProducer = getOrCreateProducer(queueSession, jmsDestination);
            // These messages need to be persistent
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            // Send and forget
            messageProducer.send(message);
        } else {
            temporaryQueue = queueSession.createTemporaryQueue();
            message.setJMSReplyTo(temporaryQueue);
            messageProducer = queueSession.createProducer(jmsDestination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            messageProducer.send(message);
            MessageConsumer queueConsumer = queueSession.createConsumer(temporaryQueue);
            SyncJMSNotifier syncJMSNotifier = new SyncJMSNotifier(syncResponseTimeOut, queueConsumer, temporaryQueue);
            // We should be expecting response on the temp queue
            queueConsumer.setMessageListener(syncJMSNotifier);
            serviceResponse = syncJMSNotifier.getServiceResponse();
        }
        return serviceResponse;
    }

    /**
     * Session establishing activity
     */
    public ServiceResponse establishPipeline(String requestEndpoint,
                                             Map<String, String> properties,
                                             String payload,
                                             DefaultRtaSession session,
                                             long timeout,
                                             TimeUnit units) throws Exception {
        ServiceResponse<?> serviceResponse = null;
        //Look up destination with this name
        Queue jmsDestination = lookupEndpoint(requestEndpoint);
        QueueSession queueSession = getOrCreateQueueSession();
        MessageProducer messageProducer = queueSession.createProducer(jmsDestination);
        Exchanger<Object> exchanger = new Exchanger<Object>();
        //Set a temp queue dest as reply queue
        TemporaryQueue temporaryDestination = queueSession.createTemporaryQueue();

        //Also attach listener to same destination with selector
        MessageConsumer connectionConsumer = queueSession.createConsumer(temporaryDestination, setSelector(properties.get(ServiceConstants.SESSION_ID)));
        SessionEstablishmentNotifier messageNotifier = new SessionEstablishmentNotifier();
        messageNotifier.setExchanger(exchanger);
        //Bad but no choice since we are retrofitting
        connectionConsumer.setMessageListener(messageNotifier);
        TextMessage message = queueSession.createTextMessage(payload);
        message.setJMSReplyTo(temporaryDestination);

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            if (ServiceConstants.MESSAGE_PRIORITY.equals(entry.getKey())) {
                messageProducer.setPriority(Integer.parseInt(entry.getValue()));
            } else {
                message.setStringProperty(entry.getKey(), entry.getValue());
            }
        }
        //Tag it as session creation activity
        message.setBooleanProperty(ServiceConstants.REQUEST_SESSION, true);
        //Send and forget
        try {
            messageProducer.send(message);
            serviceResponse = (ServiceResponse<?>) exchanger.exchange(new Object(), timeout, units);
            String errorMessage = serviceResponse.getResponseProperties().getProperty(ServiceConstants.ERROR);

            if (errorMessage == null) {
                //Presumably connection established.
                TCPConnectionEvent tcpConnectionEvent = new TCPConnectionEvent(TCPConnectionEvent.CONNECTION_ESTABLISH_EVENT);

                session.exchange(tcpConnectionEvent);
                //Open up the manager
                session.informServerStatus(TCPConnectionEvent.CONNECTION_ESTABLISH_EVENT);
            } else {
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "Error establishing session [%s]", errorMessage);
                }
                throw new Exception(errorMessage);
            }
        } catch (TimeoutException tme) {
            throw new SessionInitFailedException(tme);
        } catch (Exception jme) {
            try {
                session.exchange(jme);
            } catch (InterruptedException e1) {
                LOGGER.log(Level.ERROR, "", e1);
            }
        } finally {
            messageProducer.close();
            //Close listener.
            connectionConsumer.close();
            //Delete temp queue
            temporaryDestination.delete();
        }
        return serviceResponse;
    }

    private String setSelector(String sessionId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServiceConstants.SESSION_ID);
        stringBuilder.append(" = ");
        stringBuilder.append("'");
        stringBuilder.append(sessionId);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    /**
     * @param endpoint              A url depending on the underlying transport
     * @param serviceOp             The operation on the service
     * @param properties            Any optional properties which will be sent over
     * @param payload               Payload string
     * @param factPublisherListener Callback notified upon receiving response
     * @throws Exception
     */
    @Override
    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, String payload, ServiceInvocationListener factPublisherListener) throws Exception {

        MessageProducer messageProducer = null;

        try {
            //Look up destination with this name
            Queue jmsDestination = lookupEndpoint(endpoint);
            QueueSession queueSession = getOrCreateQueueSession();
            messageProducer = queueSession.createProducer(jmsDestination);
            TextMessage message = queueSession.createTextMessage(payload);

            for (Map.Entry<String, String> entry : properties.entrySet()) {
                message.setStringProperty(entry.getKey(), entry.getValue());
            }
            //Send and forget
            messageProducer.send(message);
        } finally {
            if (messageProducer != null) {
                messageProducer.close();
            }
        }
        return null;
    }

    @Override
    public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload, ServiceInvocationListener factPublisherListener) throws Exception {
        return null;
    }

    @Override
    public TransportTypes getTransportType() {
        return TransportTypes.JMS;
    }

    @Override
    public RtaSession createSession(Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
        DefaultRtaSession session = new DefaultRtaSession(this, sessionProps);
        sessions.add(session);
        return session;
    }

    @Override
    public RtaSession createSession(String name, Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
        DefaultRtaSession session = new DefaultRtaSession(this, name, sessionProps);
        sessions.add(session);
        return session;
    }

    public Set<DefaultRtaSession> getConnectedSessions() {
        return Collections.unmodifiableSet(sessions);
    }

    @Override
    public void close() throws RtaException {
        try {
            if (asyncMessageConsumer != null) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Closing asynchronous message consumer");
                }
                asyncMessageConsumer.close();
            }
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Closing JMS queue sessions and cached producers");
            }
            queueSessionManager.clear();
        } catch (JMSException e) {
            LOGGER.log(Level.ERROR, "Error closing connection", e);
        }
    }

    @Override
    public boolean shouldSendHeartbeat() {
        return true;
    }


    private Queue lookupEndpoint(String endpoint) throws NamingException {
        //Check if it is already created.
        Queue cachedEndpoint = endpoints.get(endpoint);
        if (cachedEndpoint == null) {
            //Perform lookup
            cachedEndpoint = (Queue) context.lookup(endpoint);
            endpoints.put(endpoint, cachedEndpoint);
        }
        return cachedEndpoint;
    }

    /**
     * Gets or creates a new {@link javax.jms.QueueSession} for this thread.
     *
     * @throws JMSException
     */
    private QueueSession getOrCreateQueueSession() throws JMSException {
        QueueSession queueSession = queueSessionManager.get();
        if (queueSession == null) {
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueSessionManager.set(queueSession);
        }
        return queueSession;
    }

    /**
     * Gets or creates a new {@link javax.jms.MessageProducer} for this thread and destination.
     *
     * @throws JMSException
     */
    private MessageProducer getOrCreateProducer(QueueSession queueSession, Queue jmsDestination) throws JMSException {
        MessageProducer messageProducer = queueSessionManager.getProducer();
        if (messageProducer == null) {
            messageProducer = queueSession.createProducer(jmsDestination);
            queueSessionManager.setProducer(messageProducer);
        }
        return messageProducer;
    }

    /**
     * Exception listener.
     */
    private class ConnectionExceptionListener implements ExceptionListener {

        @Override
        public void onException(JMSException e) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Connection to JMS provider failed..");
            }
            ConnectionException connectionException = new ConnectionException(e);

            for (DefaultRtaSession session : sessions) {
                try {
                    session.exchange(connectionException);
                    session.informServerStatus(TCPConnectionEvent.CONNECTION_DOWN_EVENT);
                    session.performExchange(((JMSMessageReceptionStrategy) session.getReceptionStrategy()).getConnectionTask());
                    //Stop heartbeats
                } catch (Exception e1) {
                    LOGGER.log(Level.ERROR, "", e1);
                }
            }
        }
    }
}
