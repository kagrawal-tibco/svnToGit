package com.tibco.rta.client.util;

import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.jms.JMSRtaConnection;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.service.transport.TransportTypes;
import com.tibco.rta.service.transport.http.HTTPServiceEndpoints;
import com.tibco.rta.service.transport.jms.JMSServiceEndpoint;
import com.tibco.rta.service.transport.jms.JMSServiceEndpointFactory;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/11/12
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class RtaSessionUtil {

    public static <R extends RtaConnectionEx> String getEndpoint(ServiceType serviceType, R connection) {
        TransportTypes transportTypes = connection.getTransportType();
        switch (transportTypes) {
            case HTTP :
            case TCP :
                switch (serviceType) {
                    case ADMIN :
                        return HTTPServiceEndpoints.ADMIN_SERVICE.getServiceURI();
                    case METRICS :
                        return HTTPServiceEndpoints.METRICS_SERVICE.getServiceURI();
                    case METRICS_INTROSPECTION :
                        return HTTPServiceEndpoints.METRIC_INTROSPECTION_SERVICE.getServiceURI();
                    case RULE :
                        return HTTPServiceEndpoints.RULE_SERVICE.getServiceURI();
                    case QUERY :
                        return HTTPServiceEndpoints.QUERY_SERVICE.getServiceURI();
                    case ENGINE :
                        return HTTPServiceEndpoints.RUNTIME_SERVICE.getServiceURI();
                    case ASYNC :
                        return HTTPServiceEndpoints.ASYNC_SERVICE.getServiceURI();
                }
            case JMS : {
                JMSRtaConnection jmsConnection = (JMSRtaConnection)connection;
                return JMSServiceEndpointFactory.getOrCreateServiceEndpoint(jmsConnection.getConnectionConfigurationAsProperties(), serviceType).getDestination();
            }
        }
        return null;
    }

    public static <R extends RtaConnectionEx, C extends AbstractClientTask> String getEndpoint(ServiceType serviceType, R connection, boolean useQueryDestination) {
        TransportTypes transportTypes = connection.getTransportType();
        switch (transportTypes) {
            case HTTP :
            case TCP :
                switch (serviceType) {
                    case ADMIN :
                        return HTTPServiceEndpoints.ADMIN_SERVICE.getServiceURI();
                    case METRICS :
                        return HTTPServiceEndpoints.METRICS_SERVICE.getServiceURI();
                    case METRICS_INTROSPECTION :
                        return HTTPServiceEndpoints.METRIC_INTROSPECTION_SERVICE.getServiceURI();
                    case RULE :
                        return HTTPServiceEndpoints.RULE_SERVICE.getServiceURI();
                    case QUERY :
                        return HTTPServiceEndpoints.QUERY_SERVICE.getServiceURI();
                    case ENGINE :
                        return HTTPServiceEndpoints.RUNTIME_SERVICE.getServiceURI();
                    case ASYNC :
                        return HTTPServiceEndpoints.ASYNC_SERVICE.getServiceURI();
                }
                break;
            case JMS : {
                JMSRtaConnection jmsConnection = (JMSRtaConnection)connection;
                Properties connectionConfiguration = jmsConnection.getConnectionConfigurationAsProperties();
                connectionConfiguration.setProperty("JMS.INBOUND.USE.QUERY.QUEUE", Boolean.toString(useQueryDestination));
                return new JMSServiceEndpoint(connectionConfiguration, serviceType).getDestination();
            }
        }
        return null;
    }
}
