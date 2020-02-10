package com.tibco.cep.driver.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;


public class JmsMessageContext
        extends AbstractEventContext {


    private final boolean ack;
    private final JMSDestination destination;
    private final boolean destroySessionOnAckOrRollback;
    private final Message msg;
    private final JmsSessionContext sessionContext;
    private final boolean recoverSessionOnAckFailure;


    JmsMessageContext(
            JMSDestination destination,
            Message msg,
            JmsSessionContext sessionContext,
            boolean ack,
            boolean destroySessionOnAckOrRollback,
            boolean recoverSessionOnAckFailure) {

        this.ack = ack;
        this.destination = destination;
        this.destroySessionOnAckOrRollback = destroySessionOnAckOrRollback;
        this.msg = msg;
        this.sessionContext = sessionContext;
        this.recoverSessionOnAckFailure = recoverSessionOnAckFailure;
    }


    @Override
    public void acknowledge() {
        try {

            final Session jmsSession = this.sessionContext.getJmsSession();

            synchronized(jmsSession) {
                if (jmsSession.getTransacted()) {
                    jmsSession.commit();
                    this.getLogger().log(Level.TRACE, "Committed session for %s", this.msg);
                }

                else if (this.ack
                        && ((null == this.optionalAckInterceptor) || this.optionalAckInterceptor.acknowledge())) {
                    try {
                        this.msg.acknowledge();
                        this.getLogger().log(Level.TRACE, "Acknowledged %s", this.msg);
                    } catch(JMSException ackException) {
                    	// check for message expiry
                        boolean msgExpired = (this.msg.getJMSExpiration() > 0) ? (System.currentTimeMillis() > this.msg.getJMSExpiration()) : false;
                        
                        this.getLogger().log(Level.ERROR, ackException,
                                "Message expired [%s]. Failed to acknowledge for %s",
                                msgExpired, this.msg);
                        
                        if (!msgExpired && recoverSessionOnAckFailure) {
                        	this.getLogger().log(Level.TRACE, ackException,
                                    "Will attempt to recover session for %s",
                                    this.msg);
                        	this.sessionContext.getJmsSession().recover();
                        }
                    }
                }

                if (this.destroySessionOnAckOrRollback) {
                    this.sessionContext.destroy();
                    this.getLogger().log(Level.TRACE, "Destroyed session context for %s", this.msg);
                }
            }
        }
        catch (JMSException e) {
            this.getLogger().log(Level.ERROR, e, "Failed to acknowledge. %s", this.msg);
        }
    }


    public boolean canReply() {
        try {
            return (null != this.msg.getJMSReplyTo());
        }
        catch (JMSException e) {
            return false;
        }
    }


    public JMSDestination getDestination() {
        return this.destination;
    }


    private Logger getLogger() {
        return this.destination.getLogger();
    }


    public Message getMessage() {
    	return msg;
    }


    @SuppressWarnings("UnusedDeclaration")
    public JmsSessionContext getSessionContext() {
        return this.sessionContext;
    }


    public boolean reply(
            SimpleEvent replyEvent) {

        try {
            final Destination replyToDestination = this.msg.getJMSReplyTo();

            if (null != replyToDestination) {

                if(this.destination.getChannel().shouldUseSenderPool() && !this.destroySessionOnAckOrRollback) {
                    JmsSenderSessionContext jmsSenderSessionContext = this.destination.getChannel().getSenderSessionContextPool().takeFromPool();
                    try {
                        return -1 != jmsSenderSessionContext.send(replyEvent, this, replyToDestination, this.destination, null);
                    } finally {
                        this.destination.getChannel().getSenderSessionContextPool().returnToPool(jmsSenderSessionContext);
                    }
                } else {
                    return -1 != this.destination.getCurrentSessionContext().send(replyEvent, this, replyToDestination, this.destination, null);
                }
            }
        }
        catch (Exception e) {
            this.destination.getLogger().log(Level.ERROR, e, "Failed to send JMS reply.");
            //TODO? : any JMSConnectionException will be requeued, and retried later
        }
        return false;
    }


    @Override
    public String replyImmediate(
            SimpleEvent replyEvent) {

        try {
            Destination replyToDestination = msg.getJMSReplyTo();

            if (replyToDestination != null) {
                
                if(this.destination.getChannel().shouldUseSenderPool() && !this.destroySessionOnAckOrRollback) {
                    JmsSenderSessionContext jmsSenderSessionContext = this.destination.getChannel().getSenderSessionContextPool().takeFromPool();
                    try {
                        return jmsSenderSessionContext.sendImmediate(replyEvent, this, replyToDestination, this.destination, null);
                    } finally {
                        this.destination.getChannel().getSenderSessionContextPool().returnToPool(jmsSenderSessionContext);
                    }
                } else {
                    return this.destination.getCurrentSessionContext().sendImmediate(replyEvent, this, replyToDestination, this.destination, null);
                }
            }
        }
        catch (Exception e) {
            this.destination.getLogger().log(Level.ERROR, e, "Failed to send JMS reply (immediately).");
            //TODO? : any JMSConnectionException will be requeued, and retried later
        }
        return null;
    }


    @Override
    public void rollBack()
    {
        try {
            final Session jmsSession = this.sessionContext.getJmsSession();

            synchronized(jmsSession) {
                if (jmsSession.getTransacted()) {
                    jmsSession.rollback();
                    this.getLogger().log(Level.TRACE, "Rolled back session for %s", this.msg);
                }

                else if (this.ack
                        && ((null == this.optionalAckInterceptor) || this.optionalAckInterceptor.rollBack())) {
                    try {
                        jmsSession.recover();
                        this.getLogger().log(Level.TRACE, "Recovered session %s for %s", jmsSession, this.msg);
                    } catch(JMSException recoverException) {
                        this.getLogger().log(Level.ERROR, recoverException, "Failed to recover session %s", jmsSession);
                    }
                }
                
                if (this.destroySessionOnAckOrRollback) {
                    this.sessionContext.destroy();
                    this.getLogger().log(Level.TRACE, "Destroyed session context for %s", this.msg);
                }
            }
        }
        catch (JMSException e) {
            this.getLogger().log(Level.ERROR, e, "Failed to roll back %s", this.msg);
        }
    }
}
