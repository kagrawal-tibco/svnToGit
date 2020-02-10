package com.tibco.rta.service.transport.jms;

import com.tibco.rta.common.service.ServiceType;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aathalye
 * Date : 31/12/14
 * Time : 7:19 PM
 */
public class JMSServiceEndpointFactory {

    private static ConcurrentHashMap<ServiceType, JMSServiceEndpoint> CACHED_INSTANCES = new ConcurrentHashMap<ServiceType, JMSServiceEndpoint>(ServiceType.values().length);

    public static JMSServiceEndpoint getOrCreateServiceEndpoint(Properties connectionConfiguration, ServiceType serviceType) {
        if (CACHED_INSTANCES.containsKey(serviceType)) {
            return CACHED_INSTANCES.get(serviceType);
        }
        JMSServiceEndpoint jmsServiceEndpoint = new JMSServiceEndpoint(connectionConfiguration, serviceType);
        CACHED_INSTANCES.put(serviceType, jmsServiceEndpoint);
        return jmsServiceEndpoint;
    }
}
