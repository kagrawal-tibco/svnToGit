package com.tibco.cep.runtime.channel.impl;


import javax.jms.Message;
import javax.jms.Session;

import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Implementation of SerializationContext used only for serializing events to JMS messages
 *
 * @version 3.0.2
 * @since 3.0.2
 */
public class JMSSerializationContext extends DefaultSerializationContext {

    protected Session jSession;
    private Message requestMessage;

    public JMSSerializationContext(RuleSession session, Channel.Destination dest) {
        super(session, dest);
    }

    public void setSession(Session jSession) {
        this.jSession = jSession;
    }
    
    public Session getSession() {
        return this.jSession;
    }

    public void setRequestMessage(Message requestMessage) {
        this.requestMessage = requestMessage;
    }

    public Message getRequestMessage() {
        return this.requestMessage;
    }
}