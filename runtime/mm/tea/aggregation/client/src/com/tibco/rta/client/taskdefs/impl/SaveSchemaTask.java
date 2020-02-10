package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.serialize.impl.SerializationUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SaveSchemaTask extends AbstractClientTask {

    /**
     * The schema to save
     */
    private RtaSchema schema;

    public SaveSchemaTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);        
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ADMIN, messageTransmissionStrategy.getOwnerConnection());
        setBaseProps(ServiceType.ADMIN.getServiceURI());
        String schemaXml = SerializationUtils.serializeSchema(schema);
        messageTransmissionStrategy.transmit(endpoint, "saveSchema", properties, schemaXml);
        //Nothing to return
        return null;
    }

    public void setSchema(RtaSchema schema) {
        this.schema = schema;
    }

    @Override
    public String getTaskName() {
        return "saveSchema";
    }
}
