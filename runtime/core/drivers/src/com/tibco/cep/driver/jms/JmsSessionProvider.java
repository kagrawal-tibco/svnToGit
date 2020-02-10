package com.tibco.cep.driver.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;


/**
 * User: nprade
 * Date: 9/13/12
 * Time: 4:49 PM
 */
public class JmsSessionProvider
{
    private int ackMode;
    private Connection connection;
    private boolean transacted;

    public JmsSessionProvider(
            Connection connection,
            int ackMode,
            boolean transacted) {

        this.connection = connection;
        this.ackMode = ackMode;
        this.transacted = transacted;
    }

    public Session createSession() throws JMSException {
        return this.connection.createSession(this.transacted, this.ackMode);
    }
}
