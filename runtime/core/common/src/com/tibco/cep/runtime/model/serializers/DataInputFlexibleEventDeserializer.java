package com.tibco.cep.runtime.model.serializers;

import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.DATETIME;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.DOUBLE;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.ENTITYREFLONG;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.INT;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.LONG;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.STRING;

import java.io.DataInput;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types;
import com.tibco.xml.data.primitive.ExpandedName;

public class DataInputFlexibleEventDeserializer extends DataInputEventDeserializer
{
	private static int unsetIntNotFinal;
	protected static final int unsetInt = unsetIntNotFinal;
	private static long unsetLongNotFinal;
	protected static final long unsetLong = unsetLongNotFinal;
	private static double unsetDoubleNotFinal;
	protected static final double unsetDouble = unsetDoubleNotFinal;
	private static boolean unsetBooleanNotFinal;
	protected static final boolean unsetBoolean = unsetBooleanNotFinal;
	
	protected HashMap<String, PropEntry> props;
	protected Object payload; 
	
	protected String currPropName;
	
    public DataInputFlexibleEventDeserializer(DataInput buf, TypeManager typeManager) throws IOException {
    	super(buf, typeManager);
    }

    @Override
    public long startEvent() {
        long id = super.startEvent();
        try{
    		int numProps = buf.readInt();
    		props = new HashMap<>(numProps);
    		
    		for(int ii = 0; ii < numProps; ii++) {
    			if(_startProperty()) {
    				String name = buf.readUTF();
    				Types type = Types.values()[buf.readByte()];
    				Object value = readProperty(type);
					props.put(name, new PropEntry(type, value));
    			}
    			_endProperty();
    		}
    		if(buf.readBoolean()) {
    			payload = readPayload();
    		}
    	} catch (Exception e) {
            this.msg =e.getMessage();
            error=true;
            throw new RuntimeException(e);
        }
        
        return id;
    }
    
    protected Object readProperty(Types propType) {
    	switch(propType) {
			case BOOLEAN:
				return super.getBooleanProperty();
			case DATETIME:
				return super.getDateTimeProperty();
			case DOUBLE:
				return super.getDoubleProperty();
			case ENTITYREFLONG:
				return super.getEntityRefPropertyAsLong();
			case INT:
				return super.getIntProperty();
			case LONG:
				return super.getLongProperty();
			case STRING:
				return super.getStringProperty();
    	}
    	
    	return null;
    }
    
    @Override
    public boolean startProperty(String propertyName, int propertyIndex) {
    	currPropName = propertyName;
        return props.containsKey(propertyName);
    }
    
    @Override
    public String getStringProperty() {
    	PropEntry prop = props.remove(currPropName);
    	if(prop.type == STRING) return (String) prop.value;
    	else return null;
    }

    @Override
    public int getIntProperty() {
    	PropEntry prop = props.remove(currPropName);
    	if(prop.value != null && (prop.type == INT || prop.type == DOUBLE || prop.type == LONG)) return ((Number)prop.value).intValue();
    	else return unsetInt;
    }

    @Override
    public boolean getBooleanProperty() {
    	PropEntry prop = props.remove(currPropName);
    	if(prop.type == Types.BOOLEAN && prop.type != null) return (Boolean)prop.value;
    	else return unsetBoolean;
    }

    @Override
    public long getLongProperty() {
    	PropEntry prop = props.remove(currPropName);
    	if(prop.value != null && (prop.type == INT || prop.type == DOUBLE || prop.type == LONG)) return ((Number)prop.value).longValue();
    	else return unsetLong;
    }

    @Override
    public long getEntityRefPropertyAsLong() {
    	PropEntry prop = props.remove(currPropName);
	   	if(prop.value != null && prop.type == ENTITYREFLONG) return (Long)prop.value;
	   	else return unsetLong;
    }
   
    @Override
    public ConceptOrReference getEntityRefProperty() {
	   PropEntry prop = props.remove(currPropName);
	   	if(prop.value != null && prop.type == ENTITYREFLONG) return new com.tibco.cep.runtime.model.element.impl.Reference((Long)prop.value);
	   	return new com.tibco.cep.runtime.model.element.impl.Reference(unsetLong);
    }
	
    @Override
   	public double getDoubleProperty() {
    	PropEntry prop = props.remove(currPropName);
    	if(prop.value != null && (prop.type == INT || prop.type == DOUBLE || prop.type == LONG)) return ((Number)prop.value).doubleValue();
    	else return unsetDouble;
    }

    @Override
	public Calendar getDateTimeProperty() {
    	PropEntry prop = props.remove(currPropName);
    	if(prop.type == DATETIME) return (Calendar)prop.value;
    	else return null;
    }
    
    @Override
    public Object getPayload(ExpandedName eventType) {
    	if(payload != null) {
    		Object[] payloadRec = (Object[])payload;
    		payload = null;
    		return makePayload(eventType, payloadRec);
    	}
    	return null;
    }

    protected static class PropEntry
    {
    	protected Types type;
    	protected Object value;
    	
    	protected PropEntry(Types type, Object value) {
    		this.type = type;
    		this.value = value;
    	}
    }
}