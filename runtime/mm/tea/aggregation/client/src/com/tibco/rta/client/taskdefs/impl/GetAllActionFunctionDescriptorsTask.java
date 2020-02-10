package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.serialize.impl.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Map;

import static com.tibco.rta.util.ServiceConstants.ERROR;


public class GetAllActionFunctionDescriptorsTask extends AbstractClientTask {

    public GetAllActionFunctionDescriptorsTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());

        ServiceResponse<byte[]> serviceResponse = (ServiceResponse<byte[]>) messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");

        byte[] functionSerializedBytes = serviceResponse.getPayload();

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) serviceResponse.getResponseProperties().get(ERROR));
        }

        if (functionSerializedBytes.length != 0) {
            Map<String, ActionFunctionDescriptor> actionFunctionDescriptorMap = SerializationUtils.deserializeActionFunctions(new ByteArrayInputStream(functionSerializedBytes));
            ActionFunctionsRepository actionFunctionsRepository = ActionFunctionsRepository.INSTANCE;

            for (Map.Entry<String, ActionFunctionDescriptor> entry : actionFunctionDescriptorMap.entrySet()) {
                actionFunctionsRepository.addAction(entry.getKey(), entry.getValue());
            }
            return new ArrayList<ActionFunctionDescriptor>(actionFunctionsRepository.getActionFunctionDescriptors());
        }
        return new ArrayList<ActionFunctionDescriptor>(0);
    }

    @Override
    public String getTaskName() {
        return "getAllActionFunctionDescriptors";
    }

}
