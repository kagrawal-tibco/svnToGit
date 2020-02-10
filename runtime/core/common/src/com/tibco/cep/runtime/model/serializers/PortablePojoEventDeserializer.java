package com.tibco.cep.runtime.model.serializers;

import java.nio.ByteBuffer;
import java.util.Calendar;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer.EventMigrator;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.xml.data.primitive.ExpandedName;

public class PortablePojoEventDeserializer implements EventDeserializer 
{
	protected PortablePojo source;
	protected TypeManager typeManager;
	protected Object currentProp;
	
	public PortablePojoEventDeserializer(PortablePojo source, TypeManager typeManager) {
		this.source = source;
		this.typeManager = typeManager;
	}
	
	@Override
	public boolean hasSchemaChanged() {
		return false;
	}

	@Override
	public EventMigrator getEventMigrator() {
		return null;
	}

	@Override
	public long startEvent() {
		return source.getId();
	}

	@Override
	public long getId() {
		return source.getId();
	}

	@Override
	public String getExtId() {
		return source.getExtId();
	}

	@Override
	public void endEvent() {
	}

	@Override
	public int getType() {
		return EventDeserializer.TYPE_NAMEVALUE;
	}

	@Override
	public boolean startProperty(String propertyName, int index) {
		currentProp = source.getPropertyValue(propertyName);
		return currentProp != null;
	}

	@Override
	public void endProperty() {
		currentProp = null;
	}

	@Override
	public String getStringProperty() {
		return (String) currentProp;
	}

	@Override
	public int getIntProperty() {
		//any NPE from this is the fault of the caller for ignoring the return value of startProperty
		return (int) currentProp;
	}

	@Override
	public boolean getBooleanProperty() {
		return (boolean) currentProp;
	}

	@Override
	public long getLongProperty() {
		return (long) currentProp;
	}

	@Override
	public ConceptOrReference getEntityRefProperty() {
		return new com.tibco.cep.runtime.model.element.impl.Reference(getLongProperty());
	}

	@Override
	public long getEntityRefPropertyAsLong() {
		return getLongProperty();
	}

	@Override
	public double getDoubleProperty() {
		return (double) currentProp;
	}

	@Override
	public Calendar getDateTimeProperty() {
		try {
			return com.tibco.be.model.types.Converter.convertDateTimeProperty(currentProp);
		} catch (RuntimeException re) { 
			throw re;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getPayload(ExpandedName eventType) {
		try {
			byte[] rawPayload = (byte[]) source.getPropertyValue(PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD, FieldType.BLOB.toString());
	        if (rawPayload != null) {
	            ByteBuffer byteBuffer = ByteBuffer.wrap(rawPayload);
	            int payloadTypeId = byteBuffer.getInt();
	            ByteBuffer slice = byteBuffer.slice();
	            byte[] payloadOnlyBytes = new byte[slice.remaining()];
	            slice.get(payloadOnlyBytes);
	
	            TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(eventType);
	            return PayloadFactoryImpl.createPayload(descriptor, payloadTypeId, payloadOnlyBytes);
	        } else {
	        	return null;
	        }
		} catch (RuntimeException re) { 
			throw re;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
