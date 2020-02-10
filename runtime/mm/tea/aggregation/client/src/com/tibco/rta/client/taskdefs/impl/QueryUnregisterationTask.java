package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.impl.QueryImpl;

import static com.tibco.rta.util.ServiceConstants.QUERY_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 14/3/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryUnregisterationTask extends AbstractClientTask {
	
	private QueryImpl query;

    public QueryUnregisterationTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.QUERY, messageTransmissionStrategy.getOwnerConnection(), query.getQueryType() == QueryType.SNAPSHOT);
        setBaseProps(ServiceType.QUERY.getServiceURI());

        if (query.getQueryType() == QueryType.STREAMING) {
        	addProperty(QUERY_NAME, query.getName());
        } else {
        	addProperty(QUERY_NAME, query.getId());
        }
        //Send query name
        messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        return null;
    }
    
    

    public void setQuery(QueryImpl query) {
		this.query = query;
	}

	@Override
    public String getTaskName() {
        return "unregisterQuery";
    }
}
