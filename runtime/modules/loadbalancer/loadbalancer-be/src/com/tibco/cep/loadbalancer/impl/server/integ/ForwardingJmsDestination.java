package com.tibco.cep.loadbalancer.impl.server.integ;

import com.tibco.cep.driver.jms.BaseJMSChannel;
import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.jms.JmsMessageContext;
import com.tibco.cep.driver.jms.JMSInterceptor;
import com.tibco.cep.loadbalancer.impl.message.DistributableJmsMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.server.Server;
import com.tibco.cep.loadbalancer.server.core.Kernel;
import com.tibco.cep.loadbalancer.server.core.LoadBalancer;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.tibjms.Tibjms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/*
* Author: Ashwin Jayaprakash / Date: Apr 13, 2010 / Time: 3:43:18 PM
*/

@LogCategory("loadbalancer.be.server.destination.jms")
public class ForwardingJmsDestination extends RouterSideNoOpDestination<BaseJMSChannel, JMSDestination>
        implements JMSInterceptor {
    /**
     * {@value}.
     */
    public static final int TOTAL_ROUTING_KEYS = 4;

    protected boolean runningInActiveMode;

    private boolean debugOn;

    protected boolean processAcks;
    
    public ForwardingJmsDestination() {
        this.processAcks = true;
    }

    @Override
    public void setProperties(Map<Object, Object> properties) {
        super.setProperties(properties);
    }

    @Override
    public boolean startInActiveMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        start(session, channel, destination);
        
        //---------------
        
        switch (destination.getConfig().getJmsAckMode()) {
            case Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE:
            case Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE:
                logger.log(Level.INFO,
                        String.format("[%s : %s : %s : %s] acknowledgements will%s be relayed", session.getName(),
                                channel.getName(), destination.getName(), getClass().getSimpleName(),
                                processAcks ? "" : " not"));
                break;

            case Session.AUTO_ACKNOWLEDGE:
            case Session.SESSION_TRANSACTED: {
                processAcks = false;
                //No break. Continue further.
            }
            case Session.CLIENT_ACKNOWLEDGE:
            case Session.DUPS_OK_ACKNOWLEDGE:
            default: {
                logger.log(Level.WARNING,
                        String.format("[%s : %s : %s : %s] acknowledgement mode is not" +
                                " EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE. This is not advisable." +
                                " Acknowledgements will% be relayed",
                                session.getName(), channel.getName(), destination.getName(),
                                getClass().getSimpleName(), processAcks ? "" : " not"));
            }
        }

        //---------------

        runningInActiveMode = true;

        return true;
    }

    @Override
    protected void hookFirstStepInStart(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        if (!destination.getConfig().isQueue()) {
            String msg = String.format(
                    "The Destination [%s] in Session [%s] is not configured to a Queue. Only Queues are supported",
                    destination.getConfig().getURI(), session.getName());

            throw new IllegalArgumentException(msg);
        }

        if (destination.isMQ()) {
            String msg = String.format(
                    "The Destination [%s] in Session [%s] is configured to use MQ. Only [%s] is supported",
                    destination.getConfig().getURI(), session.getName(), Tibjms.class.getSimpleName());

            throw new IllegalArgumentException(msg);
        }

        //----------------

        super.hookFirstStepInStart(session, channel, destination);

        this.debugOn = logger.isLoggable(Level.FINE);
    }

    @Override
    protected void hookJustAfterAddingLB(Server server, Kernel kernel, LoadBalancer loadBalancer) {
    }

    @Override
    public boolean startInPassiveMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        hookFirstStepInStart(session, channel, destination);

        logger.log(Level.INFO,
                String.format("Starting [%s : %s : %s : %s] in passive mode", session.getName(), channel.getName(),
                        destination.getName(), getClass().getSimpleName()));

        return true;
    }

    @Override
    public boolean startInSuspendMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        hookFirstStepInStart(session, channel, destination);

        logger.log(Level.INFO,
                String.format("Starting [%s : %s : %s : %s] in suspend mode", session.getName(), channel.getName(),
                        destination.getName(), getClass().getSimpleName()));

        return true;
    }

    @Override
    public JmsMessageContext onMessage(JmsMessageContext messageContext) {
        Message jmsMsg = (Message) messageContext.getMessage();
        String jmsMsgId = null;
        boolean messageSent = false;

        while(!messageSent) {
            try {
                jmsMsgId = jmsMsg.getJMSMessageID();
                String key = createRoutingKey(jmsMsg);
                
                DistributableJmsMessage distributableMessage = new DistributableJmsMessage(jmsMsg, key, TOTAL_ROUTING_KEYS);
    
                if (debugOn) {
                    logger.log(Level.FINE,
                            String.format("Attempting to send message [%s] in [%s : %s : %s : %s]", jmsMsgId,
                                    session.getName(), channel.getName(), destination.getName(),
                                    getClass().getSimpleName()));
                }
    
                if (this.processAcks) {
                    distributableMessage.setHeaderValue(KnownHeader.__ack_expected__, Boolean.TRUE);
    
                    JmsAckProcessor.INSTANCE.registerMessage(distributableMessage, messageContext);
    
                    routerDestinationMbean.incrementTotalPendingAcks();
                }
    
                MessageHandle messageHandle = loadBalancer.send(distributableMessage);
    
                Exception ex = messageHandle.getPostSendException();
                if ((ex != null) && this.processAcks) {
    // for now do not ack failures // JmsAckProcessor.INSTANCE.ackMessageId(distributableMessage);
                    throw ex;
                }
    
                routerDestinationMbean.incrementTotalMessagesSent();
                messageSent = true;
            }
            catch (Exception e) {
                this.logger.log(Level.WARNING, String.format("Error occurred while sending message [%s] in [%s : %s : %s : %s], will retry", jmsMsgId,
                                session.getName(), channel.getName(), destination.getName(), getClass().getSimpleName()), e);
                //wait for 5 secs before retry
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                   //ignore
                }
            }
        }

        return null;
    }

    private String createRoutingKey(Message jmsMsg) throws JMSException {
        String jmsMsgId = jmsMsg.getJMSMessageID();
        StringBuilder keyBuilder = new StringBuilder();
        for (String key : routingKeys) {
            String valFromMsg = jmsMsg.getStringProperty(key);
            if (valFromMsg == null) {
                throw new IllegalArgumentException("Message [" + jmsMsgId + "] does not have property [" + key + "]"
                        + " defined, to be used as the routing key.");
            } else {
                keyBuilder.append(valFromMsg);
            }
        }
        return keyBuilder.toString();
    }

    @Override
    public void stop() {
        if (!runningInActiveMode) {
            logger.log(Level.INFO, String.format("Stopped [%s : %s : %s : %s]", session.getName(), channel.getName(),
                    destination.getName(), getClass().getSimpleName()));

            return;
        }

        //--------------

        super.stop();

        runningInActiveMode = false;
    }

    @Override
    protected void hookJustBeforeRemovingLB(Server server, Kernel kernel, LoadBalancer loadBalancer) {
    }

    @Override
    protected void hookJustAfterRemovingLB(Server server, Kernel kernel) {
        logger.log(Level.INFO, "Clearing the JmsAckProcessor"); // TODO is this really OK?
        JmsAckProcessor.INSTANCE.clear();
    }


    public enum JmsAckProcessor {

        INSTANCE;


        protected Map<Object, JmsMessageContext> messageIdToContext = new ConcurrentHashMap<Object, JmsMessageContext>();


        public boolean ackMessage(
                DistributableJmsMessage msg)
        {
            final JmsMessageContext ctx = this.messageIdToContext.remove(msg.getHeaderValue(KnownHeader.__content_id__));
            if (null == ctx) {
                return false;
            }
            else {
                ctx.acknowledge();
                return true;
            }
        }
        
        public boolean rollback(DistributableJmsMessage msg)
        {
            final JmsMessageContext ctx = this.messageIdToContext.remove(msg.getHeaderValue(KnownHeader.__content_id__));
            if (null == ctx) {
                return false;
            }
            else {
                ctx.rollBack();
                return true;
            }
        }


        public void clear() {
            this.messageIdToContext.clear();
        }


        public void registerMessage(
                DistributableJmsMessage msg,
                JmsMessageContext ctx)
        {
            this.messageIdToContext.put(msg.getHeaderValue(KnownHeader.__content_id__), ctx);
        }


    }

}