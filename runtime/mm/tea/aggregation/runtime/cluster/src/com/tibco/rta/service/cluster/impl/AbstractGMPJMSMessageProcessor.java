package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.QuorumBasedGMPCluster;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.cluster.event.GMPEventRegistry;
import com.tibco.rta.service.transport.jms.JMSConnectorInfo;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGMPJMSMessageProcessor {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());


    /**
     * Handle for publishing messages to topic.
     */
    protected TopicSession topicSession;

    /**
     * Topic to be used for GMP messages.
     */
    protected Topic gmpTopic;

    /**
     * Flag indicating whether connection was established.
     */
    protected volatile boolean connectionEstablished;


	private JMSConnectorInfo connectorInfo;


	private TopicConnection topicConnection;

    /**
     * Shared synchronization point for publisher and subscriber.
     */
    protected static final CyclicBarrier CONNECTION_BARRIER = new CyclicBarrier(2, new ConnectionEstablishmentRunnable());



    public void init(Properties configuration, String subscriberType) throws JMSException, NamingException {
        connectorInfo = new JMSConnectorInfo(configuration);

    	Context context = initializeContext(configuration, subscriberType);

        String topicConnFactory = (String) ConfigProperty.GMP_JMS_TOPIC_CONN_FACTORY.getValue(configuration);

        String subjectName = (String) ConfigProperty.GMP_SUBJECT.getValue(configuration);

        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup(topicConnFactory);

        topicConnection = topicConnectionFactory.createTopicConnection(connectorInfo.getConnUsername(), connectorInfo.getConnPassword());
        //Attach exception listener to get notified of JMS disconnects.
        topicConnection.setExceptionListener(new ConnectionExceptionListener(configuration, subscriberType));

        topicConnection.start();
        //Start session
        topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        gmpTopic = (Topic) context.lookup(subjectName);

        connectionEstablished = true;
    }


    private Context initializeContext(Properties configuration, String subscriberType) throws NamingException {

        String jndiFactory = (String) ConfigProperty.RTA_JMS_JNDI_CONTEXT_FACTORY.getValue(configuration);

        String jmsProviderURL = (String) ConfigProperty.RTA_JMS_JNDI_URL.getValue(configuration);

        Hashtable<Object, Object> env = new Hashtable<Object, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, jndiFactory);

        env.put(Context.PROVIDER_URL, jmsProviderURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, connectorInfo.getConnUsername());
        env.put(Context.SECURITY_CREDENTIALS, connectorInfo.getConnPassword());


        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Connecting to JMS provider at url [%s] for message [%s]", jmsProviderURL, subscriberType);
        }

        return new InitialContext(env);
    }


    /**
     * For connection breakages.
     */
    private class ConnectionExceptionListener implements ExceptionListener {

        private Properties configuration;

        private String subscriberType;

        private ConnectionExceptionListener(Properties configuration, String subscriberType) {
            this.configuration = configuration;
            this.subscriberType = subscriberType;
        }

        @Override
        public void onException(JMSException e) {

            connectionEstablished = false;

            String jmsProviderURL = (String) ConfigProperty.RTA_JMS_JNDI_URL.getValue(configuration);

            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Connection to JMS provider [%s] by subscriber type [%s] failed..", jmsProviderURL, subscriberType);
            }
            //Send network failure event
            GMPEventRegistry.INSTANCE.onNetworkFailure(jmsProviderURL);

            long retryInterval = Long.parseLong((String) ConfigProperty.RTA_JMS_CONNECTION_RETRY_INTERVAL.getValue(configuration));

            for (int loop = 0; loop < Integer.MAX_VALUE; loop++) {

                if (connectionEstablished) {
                    break;
                }

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Retrying Connection to JMS provider [%s] : Retry counter [%d] in [%d] milliseconds", jmsProviderURL, loop, retryInterval);
                }
                try {
                    init(configuration, subscriberType);

                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Connection to JMS provider [%s] established by subscriber type [%s]", jmsProviderURL, subscriberType);
                    }
                    connectionEstablished = true;
                    //Synchronization point for link readers from publisher and processor.
                    //Need publisher and processor both to finish reconnect so as to avoid
                    //case where publisher attempts to send heartbeats but the thread is interrupted.
                    CONNECTION_BARRIER.await();

                } catch (Exception e1) {
                    try {
                        Thread.sleep(retryInterval);
                    } catch (InterruptedException e2) {
                        LOGGER.log(Level.ERROR, "", e2);
                    }
                }
            }
        }
    }
    
    public void stop() throws Exception {
    	if (topicConnection != null) {
    		topicConnection.close();
    	}
    }

    /**
     * Runnable action to execute upon barrier completion.
     */
    private static class ConnectionEstablishmentRunnable implements Runnable {

        @Override
        public void run() {
            try {

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Get and set the state it was before failure
                cluster.setCurrentState(cluster.getPreviousState());
                //Reset barrier
                CONNECTION_BARRIER.reset();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }
}
