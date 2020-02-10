package com.tibco.rta.service.transport.jms;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.taskdefs.IdempotentRetryTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.transport.AbstractTransportService;
import com.tibco.rta.service.transport.jms.destination.DefaultInboundJMSDestination;
import com.tibco.rta.service.transport.jms.destination.JMSInboundDestination;
import com.tibco.rta.service.transport.jms.destination.JMSOutboundDestination;
import com.tibco.rta.service.transport.jms.destination.JMSSessionOutbound;
import com.tibco.rta.service.transport.jms.destination.NotificationsJMSDestination;
import com.tibco.tibjms.Tibjms;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSTransportService extends AbstractTransportService<JMSConnectorInfo> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    /**
     * Shared queue connection
     */
    private QueueConnection queueConnection;

    /**
     * Reusable session
     */
    private QueueSession queueSession;

    /**
     * Reusable query queue session
     */
    private QueueSession queryQueueSession;

    /**
     * Reusable query queue session
     */
    private QueueSession outboundSession;

    /**
     * Inbound destination.
     */
    private JMSInboundDestination inboundDestination;

    /**
     * Inbound destination for snapshot queries.
     */
    private JMSInboundDestination inboundQueryDestination;

    /**
     * Outbound notifications destination.
     */
    private JMSOutboundDestination outboundDestination;

    private boolean useFTQueue;

    private QueueSession ftQueueSession;

    private Queue ftQueue;

    /**
     * One producer for FT queue message sending.
     */
    private MessageProducer ftQueueProducer;

    private ScheduledExecutorService ftQueueSender;

    private ConnectionExceptionListener exceptionListener;

    public JMSTransportService() {

    }

    @Override
    public void init(Properties configuration) throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing JMS Transport service..");
        }
        this.configuration = configuration;

        connectorInfo = new JMSConnectorInfo(configuration);

        Context initialContext = initializeContext();

        String queueConnFactory = connectorInfo.getQueueConnFactory();

        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext.lookup(queueConnFactory);

        queueConnection = queueConnectionFactory.createQueueConnection(connectorInfo.getConnUsername(), connectorInfo.getConnPassword());

        if (!Boolean
                .parseBoolean((String) ConfigProperty.RTA_FT_GMP_ENABLED.getValue(configuration))) {
            exceptionListener = new ConnectionExceptionListener();
            queueConnection.setExceptionListener(exceptionListener);
        }

        //Starting connection is very imp.
        queueConnection.start();

        useFTQueue = Boolean.parseBoolean(configuration.getProperty(
                ConfigProperty.RTA_USE_FT_QUEUE.getPropertyName(), ConfigProperty.RTA_USE_FT_QUEUE.getDefaultValue()));

        if (useFTQueue) {
            ftQueueSession = queueConnection.createQueueSession(false, getSessionAckMode(JMSAckModes.AUTO));

            ftQueue = (Queue) initialContext.lookup(configuration.getProperty(
                    ConfigProperty.RTA_JMS_FT_QUEUE.getPropertyName(),
                    ConfigProperty.RTA_JMS_FT_QUEUE.getDefaultValue()));

            ftQueueProducer = ftQueueSession.createProducer(ftQueue);
        }

        queueSession = queueConnection.createQueueSession(false, getSessionAckMode(connectorInfo.getAckMode()));

        outboundSession = queueConnection.createQueueSession(false, getSessionAckMode(JMSAckModes.AUTO));

        Queue inboundQueue = (Queue) initialContext.lookup(connectorInfo.getInboundQueue());

        Queue inboundQueryQueue = (Queue) initialContext.lookup(connectorInfo.getInboundQueryQueue());

        Queue outboundQueue = (Queue) initialContext.lookup(connectorInfo.getOutboundQueue());

        if (Boolean.parseBoolean(configuration.getProperty(ConfigProperty.RTA_QUERY_ENGINE.getPropertyName(), "true"))) {

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Snapshot query processing enabled on this SPM server instance");
            }

            queryQueueSession = queueConnection.createQueueSession(false, getSessionAckMode(connectorInfo.getAckMode()));

            bindInboundQueryDestination(configuration, inboundQueryQueue);
        }
        //Add destinations
        bindInboundDestination(configuration, inboundQueue);

        bindOutboundDestination(configuration, outboundQueue);

        //Also see if there are existing server sessions
        //Init could come post network re-establish
        List<String> sessionNames = ServerSessionRegistry.INSTANCE.getServerSessionNames();

        for (String sessionName : sessionNames) {
            @SuppressWarnings("unchecked")
            ServerSession<JMSSessionOutbound> serverSession = (ServerSession<JMSSessionOutbound>) ServerSessionRegistry.INSTANCE.getServerSessionByName(sessionName);
            //Create new outbound
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Resetting outbound destination for server session [%s]", sessionName);
            }
            serverSession.setSessionOutbound(new JMSSessionOutbound(serverSession, outboundDestination));
        }

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "JMS Transport service initialized..");
        }
    }


    private Context initializeContext() throws NamingException {
        Hashtable<Object, Object> env = new Hashtable<Object, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, connectorInfo.getJndiFactory());
        env.put(Context.PROVIDER_URL, connectorInfo.getJmsProviderURL());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, connectorInfo.getConnUsername());
        env.put(Context.SECURITY_CREDENTIALS, connectorInfo.getConnPassword());

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Connecting to JMS provider at url [%s]", connectorInfo.getJmsProviderURL());
        }
        return new InitialContext(env);
    }


    private void bindInboundDestination(Properties connectionConfig, Queue targetQueue) throws Exception {
        boolean shouldSignalPrimary = !useFTQueue;
        inboundDestination = new DefaultInboundJMSDestination(connectionConfig, targetQueue, queueSession, shouldSignalPrimary);
    }

    private void bindInboundQueryDestination(Properties connectionConfig, Queue targetQueue) throws Exception {
        inboundQueryDestination = new DefaultInboundJMSDestination(connectionConfig, targetQueue, queryQueueSession, false);
    }

    private void bindOutboundDestination(Properties connectionConfig, Queue targetQueue) throws Exception {
        outboundDestination = new NotificationsJMSDestination(connectionConfig, targetQueue, outboundSession);
    }

    private void bindFTQueue(Queue bootstrapQueue) throws JMSException {

        MessageConsumer queueConsumer = ftQueueSession.createConsumer(bootstrapQueue);

        queueConsumer.setMessageListener(new ReceiveListener());

        ftQueueSender = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

            private static final String NAME_PREFIX = "FT-Queue-Sender-Thread";

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, NAME_PREFIX);
            }
        });

        startQueueSender();
    }

    private void startQueueSender() {
        if (exceptionListener != null) {
            ftQueueSender.scheduleWithFixedDelay(new JMSFTQueueSender(ftQueueSession, ftQueueProducer, exceptionListener), 0, 5000L, TimeUnit.MILLISECONDS);
        }
    }


    private void stopQueueSender() {
        ftQueueSender.shutdownNow();
    }


    public JMSOutboundDestination getNotificationsDestination() {
        return outboundDestination;
    }

    @Override
    public synchronized void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting JMS Transport service..");
        }

        if (useFTQueue) {
            bindFTQueue(ftQueue);
        } else {
            connectToQueues();
        }

        ServerSessionRegistry serverSessionRegistry = ServerSessionRegistry.INSTANCE;
        try {
            //Need to start HBs for JMS transport
            serverSessionRegistry.init(true);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while initializing Server session registry", e);
            throw e;
        }
        super.start();

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting JMS Transport service Complete.");
        }
    }

    private void connectToQueues() throws Exception {
        if (Boolean.parseBoolean(configuration.getProperty(ConfigProperty.RTA_QUERY_ENGINE.getPropertyName(), "true"))) {
            inboundQueryDestination.start();
        }
        inboundDestination.start();
        outboundDestination.start();
    }


    @Override
    public synchronized void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping JMS Transport service..");
        }
        //Stop all destinations
        if (inboundDestination != null) {
            inboundDestination.stop();
        }
        if (inboundQueryDestination != null) {
            inboundQueryDestination.stop();
        }
        if (outboundDestination != null) {
            outboundDestination.stop();
        }
        if (queueSession != null) {
            queueSession.close();
        }
        if (outboundSession != null) {
            outboundSession.close();
        }
        if (queryQueueSession != null) {
            queryQueueSession.close();
        }
        if (ftQueueSession != null) {
            ftQueueSession.close();
        }
        if (ftQueueProducer != null) {
            ftQueueProducer.close();
        }
        if (queueConnection != null) {
            queueConnection.close();
        }

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping JMS Transport service Complete.");
        }
    }

    private int getSessionAckMode(JMSAckModes ackMode) {
        switch (ackMode) {
            case AUTO:
                return Session.AUTO_ACKNOWLEDGE;
            case CLIENT:
                return Session.CLIENT_ACKNOWLEDGE;
            case DUPS_OK:
                return Session.DUPS_OK_ACKNOWLEDGE;
            case TIBCO_EXPLICIT:
                return Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE;
        }
        throw new IllegalArgumentException(String.format("Acknowledgement mode [%s] not supported", ackMode));
    }

    /**
     * For connection breakages.
     */
    private class ConnectionExceptionListener implements ExceptionListener, SessionStateNotifier {

        @Override
        public void onSessionClose(JMSException e) {
            onException(e);
        }

        @Override
        public void onException(JMSException e) {
            if (LOGGER.isEnabledFor(Level.ERROR)) {
                LOGGER.log(Level.ERROR, "Connection to JMS provider [%s] failed..", connectorInfo.getJmsProviderURL());
            }
            if (useFTQueue) {
                stopQueueSender();
            }
            long retryInterval = Long.parseLong((String) ConfigProperty.RTA_JMS_CONNECTION_RETRY_INTERVAL.getValue(configuration));

            try {
                GroupMembershipService gmpService = ServiceProviderManager.getInstance().getGroupMembershipService();
                gmpService.signalNetworkDisconnect();
            } catch (Exception e3) {
                LOGGER.log(Level.ERROR, "", e);
            }
            //Start retries
            try {
                JMSReConnectionTask reConnectionTask = new JMSReConnectionTask(configuration);
                IdempotentRetryTask retryConnectionTask = new IdempotentRetryTask(Integer.MAX_VALUE, retryInterval, reConnectionTask);
                retryConnectionTask.perform();
            } catch (Throwable t) {
                //throw runtime exception so as to facilitate retry
                throw new RuntimeException(t);
            }
        }
    }

    private class ReceiveListener implements MessageListener {

        private boolean firstResponse = true;

        @Override
        public void onMessage(Message arg0) {

            GroupMembershipService groupMembershipService;
            try {
                if (firstResponse) {
                    groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                    groupMembershipService.signalPrimary();
                    connectToQueues();
                    firstResponse = false;
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }
}
