package com.tibco.rta.service.transport.jms;

import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.property.impl.PropertyAtomString;
import com.tibco.rta.service.transport.ServiceEndpoint;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class JMSServiceEndpoint implements ServiceEndpoint {

    /**
     * Inbound for server.
     */
    private static final String JMS_INBOUND_QUEUE = "tea.agent.client.jms.inbound.queue";

    /**
     * Inbound for snapshot queries.
     */
    private static final String JMS_INBOUND_QUERY_QUEUE = "tea.agent.client.jms.inbound.query.queue";


    /**
     * The referenced connection
     */
    private Properties connectionConfiguration;

    private ServiceType serviceType;

    private String heartbeatQueueStrategy;

    public JMSServiceEndpoint(Properties connectionConfiguration, ServiceType serviceType) {
        this.connectionConfiguration = connectionConfiguration;
        this.serviceType = serviceType;
        this.heartbeatQueueStrategy = System.getProperty("rta.client.jms.heartbeat.queue.strategy", "inbound.queue");
    }

    public String getDestination() {
        switch (serviceType) {
        	case HEARTBEAT : {
        		if ("query.queue".equals(heartbeatQueueStrategy)) {
        			Object value = connectionConfiguration.getProperty(JMS_INBOUND_QUERY_QUEUE, "spm.inbound.query.queue");
					return (value != null) ? (String) value : ((PropertyAtomString) connectionConfiguration.get(JMS_INBOUND_QUERY_QUEUE)).getValue();
        		} else if ("inbound.queue".equals(heartbeatQueueStrategy)) {
					Object value = connectionConfiguration.getProperty(JMS_INBOUND_QUEUE, "spm.inbound.queue");
					return (value != null) ? (String) value : ((PropertyAtomString) connectionConfiguration.get(JMS_INBOUND_QUEUE)).getValue();
        		} else {
        			return heartbeatQueueStrategy;
        		}
        	}
        	case ADMIN :
            case RULE :
            case ENGINE :
            case ASYNC :
            case METRICS :
            case SESSION :
                Object value = connectionConfiguration.getProperty(JMS_INBOUND_QUEUE, "spm.inbound.queue");
                return (value != null) ? (String) value : ((PropertyAtomString) connectionConfiguration.get(JMS_INBOUND_QUEUE)).getValue();
            case METRICS_INTROSPECTION :
            case QUERY :
                String prop = connectionConfiguration.getProperty("JMS.INBOUND.USE.QUERY.QUEUE", "false");
                boolean useQueryQueue = Boolean.parseBoolean(prop);
                return (useQueryQueue) ?
                       connectionConfiguration.getProperty(JMS_INBOUND_QUERY_QUEUE, "spm.inbound.query.queue") :
                       connectionConfiguration.getProperty(JMS_INBOUND_QUEUE, "spm.inbound.queue");
            default :
                throw new UnsupportedOperationException("TBD");
        }
    }

    public ServiceType getServiceType() {
        return serviceType;
    }
}
