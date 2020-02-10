package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;

import static com.tibco.rta.util.ServiceConstants.ERROR;

public class GetAllRuleDefsTask extends AbstractClientTask {

    public GetAllRuleDefsTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.RULE, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.RULE.getServiceURI());

        ServiceResponse response = (ServiceResponse) messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        byte[] ruleSerializedBytes = (byte[]) response.getPayload();

		if (response.getResponseProperties().get(ERROR) != null) {
			throw new Exception((String) response.getResponseProperties().get(ERROR));
		}

		if (ruleSerializedBytes.length != 0) {
			InputSource in = new InputSource(new ByteArrayInputStream(ruleSerializedBytes));
			return SerializationUtils.deserializeRules(in);
		}
		return null;
    }

    @Override
    public String getTaskName() {
        return "getAllRuleDefs";
    }

}
