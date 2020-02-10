package com.tibco.rta.client.taskdefs.impl;

import java.io.ByteArrayInputStream;

import org.xml.sax.InputSource;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.serialize.impl.FunctionsCatalogDeserializer;

public class GetMetricFunctionDescriptorForMeasurementTask extends AbstractClientTask {

    public GetMetricFunctionDescriptorForMeasurementTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());

        ServiceResponse serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        byte[] functionSerializedBytes = (byte[]) serviceResponse.getPayload();

        if (functionSerializedBytes.length != 0) {
            FunctionsCatalogDeserializer modelDeserializer = new FunctionsCatalogDeserializer(); 
            InputSource in = new InputSource(new ByteArrayInputStream(functionSerializedBytes));
            return modelDeserializer.deserializeCatalogElement(in);
        }
        return null;
    }

    @Override
    public String getTaskName() {
        return "getMetricFunctionDescriptorForMeasurement";
    }

}
