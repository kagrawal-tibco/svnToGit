package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.impl.QueryImpl;
import com.tibco.rta.query.impl.SnapshotBrowserProxy;
import com.tibco.rta.util.ServiceConstants;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryRegistrationTask extends AbstractClientTask {

    private QueryImpl query;

    public QueryRegistrationTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        QueryDef queryDef = null;

        if (query.getQueryByKeyDef() != null) {
            queryDef = query.getQueryByKeyDef();
        } else if (query.getQueryByFilterDef() != null) {
            queryDef = query.getQueryByFilterDef();
        }

        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.QUERY, messageTransmissionStrategy.getOwnerConnection(), query.getQueryType() == QueryType.SNAPSHOT);
        setBaseProps(ServiceType.QUERY.getServiceURI());        
		String queryDefSerialized = SerializationUtils.serializeQuery(queryDef);

        ServiceResponse serviceResponse =
                messageTransmissionStrategy.transmit(endpoint,
                                                     getTaskName(),
                                                     properties,
                                                     queryDefSerialized);

        String browserCorId = serviceResponse.getResponseProperties().getProperty(ServiceConstants.BROWSER_ID);
        SnapshotBrowserProxy proxy = null;

        if (browserCorId != null) {
            proxy = new SnapshotBrowserProxy();
            proxy.setId(browserCorId);
            query.setId(browserCorId);
        } else {
            //Check if there is any error
            String errorMessage = serviceResponse.getResponseProperties().getProperty(ServiceConstants.ERROR);
            if (errorMessage != null) {
                throw new Exception(errorMessage);
            }
        }
        return proxy;
    }

    @Override
    public String getTaskName() {
        return "registerQuery";
    }

    public void setQuery(QueryImpl query) {
        this.query = query;
    }
}
