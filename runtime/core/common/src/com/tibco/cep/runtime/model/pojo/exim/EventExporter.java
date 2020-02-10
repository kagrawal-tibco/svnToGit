package com.tibco.cep.runtime.model.pojo.exim;

import java.nio.ByteBuffer;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.model.serializers.FieldType;

/*
* Author: Ashwin Jayaprakash / Date: 12/6/11 / Time: 3:23 PM
*/
public class EventExporter extends EximParent {
    protected final TypeManager typeManager;

    public EventExporter(TypeManager typeManager) {
        this.typeManager = typeManager;
    }

    public void exportEvent(SimpleEventImpl source, PortablePojo destination) {
        try {
//            long id = source.getId();
//            String extId = source.getExtId();
//            int typeId = typeManager.getTypeDescriptor(source.getClass()).getTypeId();
//
//            destination = eximHelper.getPojoManager().createPojo(id, extId, typeId);
            destination.setVersion(PortablePojo.PROPERTY_VALUE_VERSION_DEFAULT);

            destination.setPropertyValue(PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED, source.isRecovered(), FieldType.BOOLEAN.toString());
            EventPayload payload = source.getPayload();
            if (payload != null) {
                int payloadTypeId = PayloadFactoryImpl.getPayloadTypeId(payload.getClass());
                byte[] rawBytes = payload.toBytes();

                ByteBuffer byteBuffer;
                if (rawBytes != null) {
                    byteBuffer = ByteBuffer.allocate(4 + rawBytes.length);
                    byteBuffer.putInt(payloadTypeId);
                    byteBuffer.put(rawBytes);
                } else {
                    byteBuffer = ByteBuffer.allocate(4);
                    byteBuffer.putInt(payloadTypeId);
                }

                destination.setPropertyValue(PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD, byteBuffer.array(), FieldType.BLOB.toString());
            }

            for (String propertyName : source.getPropertyNames()) {
                destination.setPropertyValue(propertyName, source.getProperty(propertyName));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        return destination;
    }

    public void exportTimeEvent(TimeEventImpl source, PortablePojo destination) {
//        PortablePojo destination = null;
        try {
//            long id = source.getId();
//            String extId = source.getExtId();
//            int typeId = typeManager.getTypeDescriptor(source.getClass()).getTypeId();
//
//            destination = eximHelper.getPojoManager().createPojo(id, extId, typeId);
            destination.setVersion(PortablePojo.PROPERTY_VALUE_VERSION_DEFAULT);
            destination.setPropertyValue(PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED, true, FieldType.BOOLEAN.toString());

            //Get all the properties out piecemeal - this is because the TimeEvent does not have a consolidated getProperties()
            //closure,next,ttl,fired
            destination.setPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_CLOSURE, source.getClosure(), FieldType.STRING.toString());
            destination.setPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_NEXT, source.getScheduledTimeMillis(), FieldType.LONG.toString());
            destination.setPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_TTL, source.getTTL(), FieldType.LONG.toString());
            destination.setPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_FIRED, source.isFired(), FieldType.BOOLEAN.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        return destination;
    }
}
