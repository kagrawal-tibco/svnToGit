package com.tibco.rta.client.taskdefs.impl;

import static com.tibco.rta.util.ServiceConstants.ERROR;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.QueryDef;


public class ClearAlertsTask extends AbstractClientTask  {
	
	private QueryDef queryDef;
	
    public ClearAlertsTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }


	@Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.RULE, messageTransmissionStrategy.getOwnerConnection(), true);
        setBaseProps(ServiceType.RULE.getServiceURI());        
		String queryDefSerialized = SerializationUtils.serializeQuery(queryDef);

        ServiceResponse response =
                messageTransmissionStrategy.transmit(endpoint,
                                                     getTaskName(),
                                                     properties,
                                                     queryDefSerialized);

		if (response.getResponseProperties().get(ERROR) != null) {
			throw new Exception((String) response.getResponseProperties().get(ERROR));
		}

        return null;
    }
	
	public void setQuery (QueryDef queryDef) {
		this.queryDef = queryDef;
	}
    


    @Override
    public String getTaskName() {
        return "clearAlerts";
    }

}
