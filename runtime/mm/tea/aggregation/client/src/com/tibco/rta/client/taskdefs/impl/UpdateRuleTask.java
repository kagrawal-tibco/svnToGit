package com.tibco.rta.client.taskdefs.impl;

import static com.tibco.rta.util.ServiceConstants.ERROR;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.SerializationUtils;

public class UpdateRuleTask extends AbstractClientTask {

    private RuleDef ruleDef;

    public UpdateRuleTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.RULE, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.RULE.getServiceURI());

        String ruleDefSerialized = SerializationUtils.serializeRule(ruleDef);
        ServiceResponse<?> serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, ruleDefSerialized);

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) serviceResponse.getResponseProperties().get(ERROR));
        }
        return null;
    }

    public void setRule(RuleDef ruleDef) {
        this.ruleDef = ruleDef;
    }


    @Override
    public String getTaskName() {
        return "updateRule";
    }

}
