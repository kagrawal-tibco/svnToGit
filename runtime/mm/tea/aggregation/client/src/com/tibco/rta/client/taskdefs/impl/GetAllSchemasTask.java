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

public class GetAllSchemasTask  extends AbstractClientTask {	
	
    public GetAllSchemasTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

	@Override
	public Object perform() throws Throwable {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());

        ServiceResponse response = (ServiceResponse) messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        byte[] schemaSerializedBytes = (byte[]) response.getPayload();

		if (response.getResponseProperties().get(ERROR) != null) {
			throw new Exception((String) response.getResponseProperties().get(ERROR));
		}

		if (schemaSerializedBytes.length != 0) {
			InputSource in = new InputSource(new ByteArrayInputStream(schemaSerializedBytes));
			return SerializationUtils.deserializeSchemas(in);
		}
		return null;
	}

	@Override
	public String getTaskName() {	
		return "getAllSchemas";
	}

}
