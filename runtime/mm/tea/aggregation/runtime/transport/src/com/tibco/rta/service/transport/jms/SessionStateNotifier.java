package com.tibco.rta.service.transport.jms;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/8/14
 * Time: 2:10 PM
 *
 * Notify JMS session state change when JMS does not send explicit connection loss exception.
 */
public interface SessionStateNotifier {

    /**
     *
     * The exception resulting in session close.
     */
    public <E extends JMSException> void onSessionClose(E exception);
}
