package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.RtaSchemaModelFactory;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;

import static com.tibco.rta.util.ServiceConstants.ERROR;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetSchemaTask extends AbstractClientTask {
	
    public GetSchemaTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public String getTaskName() {
        return "getSchema";
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());

        ServiceResponse serviceResponse = messageTransmissionStrategy.transmit(endpoint, getTaskName(), properties, "");
        
        if (serviceResponse.getResponseProperties().get(ERROR) != null) {
            throw new Exception((String) serviceResponse.getResponseProperties().get(ERROR));
        }
        byte[] schemaSerializedBytes = (byte[]) serviceResponse.getPayload();
		
        if (schemaSerializedBytes.length != 0) {
			InputSource in = new InputSource(new ByteArrayInputStream(schemaSerializedBytes));
			return RtaSchemaModelFactory.getInstance().createSchema(in);

		}
        return null;
    }
}
