package com.tibco.rta.client.transport;

import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.http.HTTPRtaConnection;
import com.tibco.rta.client.jms.JMSRtaConnection;
import com.tibco.rta.client.transport.impl.http.DefaultMessageReceptionStrategy;
import com.tibco.rta.client.transport.impl.http.DefaultMessageTransmissionStrategy;
import com.tibco.rta.client.transport.impl.jms.JMSMessageReceptionStrategy;
import com.tibco.rta.client.transport.impl.jms.JMSMessageTransmissionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.service.transport.TransportTypes;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyFactory {

    public static final StrategyFactory INSTANCE = new StrategyFactory();

    private StrategyFactory() {}

    public <R extends RtaConnectionEx> MessageTransmissionStrategy getTransmissionStrategy(DefaultRtaSession session, R connection) {
        if (connection.getTransportType() == TransportTypes.JMS) {
            return new JMSMessageTransmissionStrategy(session, (JMSRtaConnection) connection);
        } else if (connection.getTransportType() == TransportTypes.HTTP) {
            return new DefaultMessageTransmissionStrategy(session, (HTTPRtaConnection) connection);
        }
        return null;
    }

    public <R extends RtaConnectionEx> MessageReceptionStrategy getReceptionStrategy(DefaultRtaSession session, R connection) {
        if (connection.getTransportType() == TransportTypes.JMS) {
            return new JMSMessageReceptionStrategy(session);
        } else if (connection.getTransportType() == TransportTypes.HTTP) {
            return new DefaultMessageReceptionStrategy(session);
        }
        return null;
    }
}
