package com.tibco.rta.service.transport.jms;

import com.tibco.rta.Fact;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.service.session.SessionCreationException;
import com.tibco.rta.common.service.transport.jms.JMSFactMessageContext;
import com.tibco.rta.common.service.transport.jms.JMSMessageContext;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.runtime.model.serialize.RuntimeModelJSONDeserializer;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.persistence.SessionPersistenceService;
import com.tibco.rta.service.query.QueryService;
import com.tibco.rta.service.transport.ServiceDelegate;
import com.tibco.rta.service.transport.TransportServiceImpl;
import com.tibco.rta.service.transport.jms.destination.JMSSessionOutbound;
import com.tibco.rta.util.IOUtils;
import com.tibco.rta.util.JMSUtil;
import com.tibco.rta.util.ServiceConstants;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 9:58 AM
 * Default inbound message listener for both inbound and inbound query queue.
 */
public class InboundMessageReceiver implements MessageListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());


    /**
     * All connection config.
     */
    private Properties configuration;

    /**
     * Reusable session.
     */
    private QueueSession queueSession;

    /**
     * One time instantiation.
     */
    private ServiceDelegate serviceDelegate;

    /**
     * Whether sync ops should use EMS session thread or not.
     */
    private boolean useCallersThread;

    /**
     * Workitem service to dispatch sync ops.
     */
    private WorkItemService workItemService;

    /**
     * For FT recovery. For primary this will be true.
     */
    private volatile boolean recovered;

    private boolean shouldSignalPrimary;

    public InboundMessageReceiver(Properties configuration,
                                  QueueSession queueSession,
                                  boolean recovered,
                                  boolean shouldSignalPrimary) {
        this.configuration = configuration;
        useCallersThread = Boolean.parseBoolean((String) ConfigProperty.RTA_USE_CALLERS_THREAD.getValue(configuration));
        this.queueSession = queueSession;
        this.recovered = recovered;
        this.shouldSignalPrimary = shouldSignalPrimary;
        serviceDelegate = new ServiceDelegate();

        if (!useCallersThread) {
            try {
                workItemService = ServiceProviderManager.getInstance().newWorkItemService("sync-ops-thread");
                workItemService.init(configuration);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }

    public InboundMessageReceiver(Properties configuration,
                                  QueueSession queueSession,
                                  boolean recovered) {
        this(configuration, queueSession, recovered, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        JMSMessageContext messageContext = null;
        Queue replyToQueue = null;
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Registration request message delivered to listener is %s", message);
            }
            if (!recovered) {
                if (shouldSignalPrimary) {
                    // Notify first message received to listener.
                    GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                    groupMembershipService.signalPrimary();
                }
                // Perform recovery
                // TODO this should be performed once primary is established in
                // GMP case.
                if (ServiceProviderManager.getInstance().isFTEnabled()) {
                    SessionPersistenceService sessionPersistenceService = ServiceProviderManager.getInstance().getSessionPersistenceService();
                    ServerSession<JMSSessionOutbound>[] serverSessions = (ServerSession<JMSSessionOutbound>[]) sessionPersistenceService.recover();
                    TransportServiceImpl transportService = (TransportServiceImpl) ServiceProviderManager.getInstance().getTransportService();
                    JMSTransportService jmsTransportService = (JMSTransportService) transportService.getDelegatedTransportService();

                    for (ServerSession<JMSSessionOutbound> serverSession : serverSessions) {
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "Setting outbound destination for server session [%s]", serverSession.getSessionName());
                        }
                        // Bind it to outbound destination for writing.
                        serverSession.setSessionOutbound(new JMSSessionOutbound(serverSession, jmsTransportService.getNotificationsDestination()));
                    }
                }
                recovered = true;
            }
            // Get replyto
            replyToQueue = (Queue) message.getJMSReplyTo();
            String operation = message.getStringProperty(ServiceConstants.REQUEST_OP);
            // Check if it has request
            boolean isRequestSession = message.getBooleanProperty(ServiceConstants.REQUEST_SESSION);
            if (isRequestSession) {
                messageContext = new JMSMessageContext(message);
                processConnectionMessage(message);
            } else {
                if (operation.equals("assertFact")) {
                    messageContext = new JMSFactMessageContext(message);
                } else {
                    messageContext = new JMSMessageContext(message);
                }
                com.tibco.rta.service.transport.Message responseMessage = dispatch(message, messageContext);

                Object payload = responseMessage.getPayload();

                if (replyToQueue != null) {
                    // Should be a temp queue
                    MessageProducer messageProducer = queueSession.createProducer(replyToQueue);

                    byte[] payloadBytes = (payload instanceof byte[]) ? (byte[]) payload : IOUtils
                            .convertStringToByteArray((String) payload, "UTF-8");

                    BytesMessage bytesMessage = queueSession.createBytesMessage();
                    if (payloadBytes != null) {
                        bytesMessage.writeBytes(payloadBytes);
                    }
                    // Add properties
                    for (Map.Entry<Object, Object> entry : responseMessage.getMessageProperties().entrySet()) {
                        bytesMessage.setObjectProperty((String) entry.getKey(), entry.getValue());
                    }
                    messageProducer.send(bytesMessage);
                    messageProducer.close();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "", e);
            if (e instanceof SessionCreationException) {
                SessionCreationException sessionCreationException = (SessionCreationException) e;
                sendConnectionResponse(replyToQueue, sessionCreationException.getSessionId(), sessionCreationException.getMessage());
            } else {
                // Issue cleanup
                try {
                    cleanup(message);
                } catch (Exception e1) {
                    LOGGER.log(Level.ERROR, "", e);
                }
            }
        } finally {
            // Common acknowledge message
            // Fact messages are acknowledged differently.
            if (!(messageContext instanceof JMSFactMessageContext) && !messageContext.isAcked()) {
                try {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Acknowledging message [%s]", messageContext);
                    }
                    messageContext.acknowledge();
                } catch (JMSException e) {
                    LOGGER.log(Level.ERROR, "", e);
                }
            }
        }
    }

    /**
     * Perform some cleanup.
     *
     * @throws Exception
     */
    private void cleanup(Message message) throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Performing cleanup");
        }
        String requestUri = message.getStringProperty(ServiceConstants.REQUEST_URI);
        String requestop = message.getStringProperty(ServiceConstants.REQUEST_OP);
        String correlationId = message.getStringProperty(ServiceConstants.BROWSER_ID);
        String queryName = message.getStringProperty(ServiceConstants.QUERY_NAME);

        if (ServiceType.QUERY.getServiceURI().equals(requestUri)) {
            QueryService queryService = ServiceProviderManager.getInstance().getQueryService();
            if ("hasNextResult".equals(requestop) || "nextResult".equals(requestop)) {
                queryService.removeBrowserMapping(correlationId);
            } else if ("registerQuery".equals(requestop)) {
                queryService.unregisterQuery(queryName);
            }
        }
    }


    private void sendConnectionResponse(Queue responseDestination, String sessionId, String errorMessage) {
        try {
            Properties properties = new Properties();
            properties.setProperty(ServiceConstants.SESSION_ID, sessionId);
            //Success case
            String outboundMessage = sessionId;
            if (errorMessage != null) {
                properties.setProperty(ServiceConstants.ERROR, errorMessage);
                outboundMessage = errorMessage;
            }
            MessageProducer messageProducer = queueSession.createProducer(responseDestination);
            TextMessage textMessage = queueSession.createTextMessage(outboundMessage);
            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = (String) propertyNames.nextElement();
                textMessage.setStringProperty(propertyName, properties.getProperty(propertyName));
            }
            messageProducer.send(textMessage);
            messageProducer.close();
        } catch (Exception e1) {
            LOGGER.log(Level.ERROR, "", e1);
        }
    }

    @SuppressWarnings("unchecked")
    private void processConnectionMessage(Message message) throws Exception {
        String sessionId = message.getStringProperty(ServiceConstants.SESSION_ID);
        String sessionName = message.getStringProperty(ServiceConstants.SESSION_NAME);

        if (sessionName != null) {
            //Only do this for named sessions.
            ServerSession<JMSSessionOutbound> serverSession =
                    (ServerSession<JMSSessionOutbound>) ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionId, sessionName);
            setSessionOutbound(serverSession);
        }
        sendConnectionResponse((Queue) message.getJMSReplyTo(), sessionId, null);
    }

    private void setSessionOutbound(ServerSession<JMSSessionOutbound> serverSession) throws Exception {
        TransportServiceImpl transportService = (TransportServiceImpl) ServiceProviderManager.getInstance().getTransportService();
        JMSTransportService jmsTransportService = (JMSTransportService) transportService.getDelegatedTransportService();
        //Bind it to outbound destination for writing.
        serverSession.setSessionOutbound(new JMSSessionOutbound(serverSession, jmsTransportService.getNotificationsDestination()));
    }

    /**
     * Dispatch to appropriate service
     *
     */
    @SuppressWarnings("unchecked")
    private com.tibco.rta.service.transport.Message dispatch(Message requestMessage, JMSMessageContext messageContext) throws SessionCreationException {
        try {
            String requestUri = requestMessage.getStringProperty(ServiceConstants.REQUEST_URI);
            String operation = requestMessage.getStringProperty(ServiceConstants.REQUEST_OP);

            if (ServiceProviderManager.getInstance().isQueryEnabled()) {
                String sessionId = requestMessage.getStringProperty(ServiceConstants.SESSION_ID);
                ServerSession<JMSSessionOutbound> serverSession = (ServerSession<JMSSessionOutbound>) ServerSessionRegistry.INSTANCE.getServerSession(sessionId);
                if (serverSession == null) {
                    String sessionName = requestMessage.getStringProperty(ServiceConstants.SESSION_NAME);
                    //Only named sessions should create server session.
                    if (sessionName != null && !sessionName.isEmpty()) {
                        //Create session if one doesn't exist
                        serverSession =
                                (ServerSession<JMSSessionOutbound>) ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionId, sessionName);
                        setSessionOutbound(serverSession);
                    }
                }
            }

            JMSServiceEndpoint serviceEndpoint = new JMSServiceEndpoint(configuration, ServiceType.getByURI(requestUri));
            StartStopService startStopService = JMSServiceProviderFactory.INSTANCE.getServiceProviderInstance(serviceEndpoint);

            Properties operationProps = new Properties();
            Enumeration<String> propertyNames = requestMessage.getPropertyNames();

            while (propertyNames.hasMoreElements()) {
                String propertyName = propertyNames.nextElement();
                if (propertyName != null) {
                    Object value = requestMessage.getStringProperty(propertyName);
                    if (value != null) {
                        operationProps.put(propertyName, value);
                    }
                }
            }

            if (!operation.equals("assertFact")) {
                if (!useCallersThread) {
                    //Submit to different thread pool and release EMS thread.
                    if (requestMessage instanceof TextMessage) {
                        return workItemService.addWorkItem(
                                new InvokeSyncService(startStopService, operation, operationProps,
                                        ((TextMessage) requestMessage).getText())).get();
                    } else if (requestMessage instanceof BytesMessage) {
                        return workItemService.addWorkItem(
                                new InvokeSyncService(startStopService, operation, operationProps,
                                        ((BytesMessage) requestMessage))).get();
                    }
                } else {
                    if (requestMessage instanceof TextMessage) {
                        return invokeService(startStopService, operation, operationProps, ((TextMessage) requestMessage).getText());
                    } else if (requestMessage instanceof BytesMessage) {
                        return invokeService(startStopService, operation, operationProps, (BytesMessage) requestMessage);
                    }
                }
            } else if (requestMessage instanceof BytesMessage) {
                WorkItemService coreWorkitemService = ServiceProviderManager.getInstance().getWorkItemService();
                AssertFactItem workitem = new AssertFactItem(startStopService, (JMSFactMessageContext) messageContext, operationProps, (BytesMessage) requestMessage);
                coreWorkitemService.addWorkItem(workitem);

                //Simply return message and dont wait for actual message return thus avoiding blocking EMS thread.
                return new com.tibco.rta.service.transport.Message();
            }
        } catch (Exception e) {
            if (e instanceof SessionCreationException) {
                // Rethrow
                throw (SessionCreationException) e;
            } else {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
        return null;
    }


    private com.tibco.rta.service.transport.Message invokeService(StartStopService startStopService,
                                                                  String operation,
                                                                  Properties properties,
                                                                  String payload) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(payload.getBytes());
        return invokeService(startStopService, operation, properties, bis);
    }

    private com.tibco.rta.service.transport.Message invokeService(StartStopService startStopService, String operation,
                                                                  Properties properties, BytesMessage payload) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(JMSUtil.readBytesFrom(payload));
        return invokeService(startStopService, operation, properties, bis);
    }


    private com.tibco.rta.service.transport.Message invokeService(StartStopService startStopService,
                                                                  String operation,
                                                                  Properties properties,
                                                                  Object payload) throws Exception {
        Method[] allMethods = serviceDelegate.getClass().getMethods();

        Method invokeMethod = null;
        for (Method method : allMethods) {
            if (method.getName().equals(operation)) {
                invokeMethod = method;
                break;
            }
        }
        if (invokeMethod != null) {
            return (com.tibco.rta.service.transport.Message) invokeMethod.invoke(serviceDelegate, startStopService, properties, payload);
        }
        return null;
    }

    private class AssertFactItem implements WorkItem<com.tibco.rta.service.transport.Message> {

        private StartStopService startStopService;

        private JMSFactMessageContext messageContext;

        private Properties properties;

        private BytesMessage requestMessage;

        private AssertFactItem(StartStopService startStopService, JMSFactMessageContext messageContext, Properties properties, BytesMessage requestMessage) {
            this.startStopService = startStopService;
            this.messageContext = messageContext;
            this.properties = properties;
            this.requestMessage = requestMessage;
        }

        @Override
        public com.tibco.rta.service.transport.Message get() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public com.tibco.rta.service.transport.Message call() throws Exception {
            byte[] payload = JMSUtil.readBytesFrom(requestMessage);
            List<Fact> facts = new RuntimeModelJSONDeserializer().deserializeFacts(payload);
            return serviceDelegate.assertFacts(startStopService, properties, messageContext, facts);
        }
    }

    /**
     * Execute all synchronous ops.
     */
    private class InvokeSyncService implements WorkItem<com.tibco.rta.service.transport.Message> {

        private StartStopService startStopService;

        private String operation;

        private Properties properties;

        private ByteArrayInputStream payload;

        private InvokeSyncService(StartStopService startStopService, String operation, Properties properties, String payload) {
            this.startStopService = startStopService;
            this.operation = operation;
            this.properties = properties;
            this.payload = new ByteArrayInputStream(payload.getBytes());
        }

        public InvokeSyncService(StartStopService startStopService, String operation, Properties properties,
                                 BytesMessage payload) throws JMSException {
            this.startStopService = startStopService;
            this.operation = operation;
            this.properties = properties;
            this.payload = new ByteArrayInputStream(JMSUtil.readBytesFrom(payload));
        }

        @Override
        public com.tibco.rta.service.transport.Message get() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public com.tibco.rta.service.transport.Message call() throws Exception {
            return invokeService(startStopService, operation, properties, payload);
        }
    }
}
