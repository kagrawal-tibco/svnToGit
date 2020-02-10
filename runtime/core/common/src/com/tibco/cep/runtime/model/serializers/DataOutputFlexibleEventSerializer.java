package com.tibco.cep.runtime.model.serializers;

import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.BOOLEAN;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.DATETIME;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.DOUBLE;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.ENTITYREFLONG;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.INT;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.LONG;
import static com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer.Types.STRING;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

public class DataOutputFlexibleEventSerializer extends DataOutputEventSerializer
{
	protected enum Types {STRING, INT, BOOLEAN, LONG, DOUBLE, DATETIME, ENTITYREFLONG}

	protected final int numProps;
	protected int currProp;
	protected Object payload;

    public DataOutputFlexibleEventSerializer(DataOutput buf, Class entityClz, int numProps) {
    	super(buf, entityClz);
    	this.numProps = numProps;
    	currProp = 0;
    	payload = null;
    }
    
    @Override
    public void startEvent(Class clz, long key, String extKey, int state) {
    	super.startEvent(clz, key, extKey, state);
    	try {
            if (!error) {
            	buf.writeInt(numProps);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }
    
    @Override
    public void startProperty(String propertyName, int index, boolean isSet) {
    	if(currProp >= numProps) return;
    	super.startProperty(propertyName, index, isSet);
    	try {
            if (!error && isSet) {
                buf.writeUTF(propertyName);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }
    
    @Override
    public void endProperty() {
    	if(currProp >= numProps) return;
    	currProp++;
    }
    
    @Override
    public void writeStringProperty(String s) {
    	if(currProp >= numProps) return;
        try {
            if (!error) {
            	buf.writeByte(STRING.ordinal());
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
        super.writeStringProperty(s);
    }
    
    @Override
    public void writeIntProperty(int i) {
    	if(currProp >= numProps) return;
        try {
            if (!error) {
            	buf.writeByte(INT.ordinal());
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
        super.writeIntProperty(i);
    }

    @Override
    public void writeBooleanProperty(boolean b) {
    	if(currProp >= numProps) return;
        try {
            if (!error) {
            	buf.writeByte(BOOLEAN.ordinal());
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
        super.writeBooleanProperty(b);
    }

    @Override
    public void writeLongProperty(long l) {
    	if(currProp >= numProps) return;
        try {
            if (!error) {
            	buf.writeByte(LONG.ordinal());
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
        super.writeLongProperty(l);
    }
        
    @Override
    public void writeDoubleProperty(double d) {
    	if(currProp >= numProps) return;
    	try {
            if (!error) {
            	buf.writeByte(DOUBLE.ordinal());
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    	super.writeDoubleProperty(d);
    }

    @Override
    public void writeDateTimePropertyCalendar(Calendar cal) {
    	if(currProp >= numProps) return;
    	if (cal != null) {
	    	try {
	            if (!error) {
                	buf.writeByte(DATETIME.ordinal());
                }
	        } catch (IOException ex) {
	            error=true;
	            msg= ex.getMessage();
	        }
    	}
    	super.writeDateTimePropertyCalendar(cal);
    }

    @Override
    public void writeEntityRefProperty(long l) {
    	if(currProp >= numProps) return;
        try {
        	if (!error) {
        		buf.writeByte(ENTITYREFLONG.ordinal());
        	}
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
        super.writeEntityRefProperty(l);
    }
    
    @Override
    public void writeEntityRefProperty(ConceptOrReference ref) {
    	if(currProp >= numProps) return;
        try {
            if (!error) {
            	buf.writeByte(ENTITYREFLONG.ordinal());
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
        super.writeEntityRefProperty(ref);
    }
    
    @Override
    public void writePayload(Object payload) {
    	this.payload = payload;
    }
    
    @Override
    public void endEvent() {
        try {
            if(!error) {
                buf.writeBoolean(payload != null);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
        if(payload != null) super.writePayload(payload);
    }
}
