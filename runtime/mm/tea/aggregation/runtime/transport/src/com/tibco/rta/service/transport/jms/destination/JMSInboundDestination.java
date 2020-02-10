package com.tibco.rta.service.transport.jms.destination;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JMSInboundDestination {

    public void start() throws Exception;

    public void stop() throws Exception;
}
