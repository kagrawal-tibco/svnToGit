package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.util.ServiceConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.tibco.rta.util.ServiceConstants.ERROR;

public class GetRulesTask extends AbstractClientTask {

    public GetRulesTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.RULE, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.RULE.getServiceURI());

        ServiceResponse<?> serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");

        List<String> list = new ArrayList<String>();
        Properties properties = serviceResponse.getResponseProperties();

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) serviceResponse.getResponseProperties().get(ERROR));
        }

        Set<Object> keys = properties.keySet();
        List<String> strKeys = new ArrayList<String>();

        for (Object key : keys) {
            String strValue = (String) key;
            if (strValue.startsWith(ServiceConstants.RULENAME)) {
                strKeys.add(strValue);
            }
        }

        for (String value : strKeys) {
            list.add(properties.getProperty(value));
        }
        return list;
    }

    @Override
    public String getTaskName() {
        return "getRules";
    }

}
