package com.tibco.rta.client.taskdefs.impl;

import static com.tibco.rta.util.ServiceConstants.ERROR;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;
import com.tibco.rta.model.MetricFunctionsRepository.MetricFunctionsRepositoryImpl;
import com.tibco.rta.model.serialize.impl.FunctionsCatalogDeserializer;


public class GetAllMetricFunctionDescriptorsTask extends AbstractClientTask {

    private boolean hasLoadedFunctions;

    public GetAllMetricFunctionDescriptorsTask(MessageTransmissionStrategy messageTransmissionStrategy, boolean hasLoadedFunctions) {
        super(messageTransmissionStrategy);
        this.hasLoadedFunctions = hasLoadedFunctions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());

        ServiceResponse<byte[]> serviceResponse = (ServiceResponse<byte[]>)  messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");

        byte[] functionSerializedBytes = serviceResponse.getPayload();
        Map<String, MetricFunctionDescriptor> funcMap = new LinkedHashMap<String, MetricFunctionDescriptor>();

        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) serviceResponse.getResponseProperties().get(ERROR));
        }

        if (functionSerializedBytes.length != 0) {
            FunctionsCatalogDeserializer modelDeserializer = new FunctionsCatalogDeserializer();
            modelDeserializer.deserializeCatalogElements(new ByteArrayInputStream(functionSerializedBytes), funcMap);
            List<MetricFunctionDescriptor> mList = new ArrayList<MetricFunctionDescriptor>(funcMap.values());

            if (!hasLoadedFunctions) {
            	((MetricFunctionsRepositoryImpl)MetricFunctionsRepository.INSTANCE).setMetricFunctionDescritpors(funcMap);
            }
            return mList;
        }
        return new ArrayList<MetricFunctionDescriptor>(0);
    }

    @Override
    public String getTaskName() {
        return "getAllMetricFunctionDescriptors";
    }

}
