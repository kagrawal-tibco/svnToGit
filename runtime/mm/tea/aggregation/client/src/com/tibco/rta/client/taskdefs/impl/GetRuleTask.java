package com.tibco.rta.client.taskdefs.impl;

import static com.tibco.rta.util.ServiceConstants.ERROR;

import java.io.ByteArrayInputStream;

import org.xml.sax.InputSource;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.serialize.impl.SerializationUtils;

public class GetRuleTask extends AbstractClientTask {	
	
    public GetRuleTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.RULE, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.RULE.getServiceURI());
        
        ServiceResponse response = (ServiceResponse)messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");      
		if (response.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) response.getResponseProperties().get(ERROR));
		}

		byte[] ruleSerializedBytes = (byte[]) response.getPayload();
		if (ruleSerializedBytes.length != 0) {
			InputSource in = new InputSource(new ByteArrayInputStream(ruleSerializedBytes));
			return SerializationUtils.deserializeRule(in);
		}
		return null;
    }

    @Override
    public String getTaskName() {
        return "getRule";
    }

}
