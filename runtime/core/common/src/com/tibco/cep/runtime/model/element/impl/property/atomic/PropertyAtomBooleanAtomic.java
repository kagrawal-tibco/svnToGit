package com.tibco.cep.runtime.model.element.impl.property.atomic;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomBooleanImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:29:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomBooleanAtomic extends PropertyAtomAtomic implements PropertyAtomBoolean {

    volatile boolean m_value = DEFAULT_VALUE;

    public PropertyAtomBooleanAtomic(Object owner) {
        super(owner);
        //not worth having synchronized blocks in the setters to keep this thread safe so just set it to true
        setIsSet();
    }

    public PropertyAtomBooleanAtomic(Object owner, boolean defaultValue) {
       super(owner);
       setIsSet();
    }

    @Override
    public PropertyAtomAtomic copy(Object newOwner) {
        return _copy(new PropertyAtomBooleanAtomic(newOwner));
    }

    protected PropertyAtomBooleanAtomic _copy(PropertyAtomBooleanAtomic ret) {
        super._copy(ret);
        ret.m_value = m_value;
        return ret;
    }

    public Object getValue() {
        return m_value;
    }

    public boolean setValue(Object obj) {
        if(obj != null) {
            return setCurrent(objectToBoolean(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        return setValue(obj);
    }

    public XmlTypedValue getXMLTypedValue() {
        return m_value ? XsBoolean.TRUE: XsBoolean.FALSE;
    }

    public boolean setValue(XmlTypedValue value) throws Exception{
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsBoolean());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) {
        if (value != null) {
            return setCurrent(PropertyAtomBooleanImpl.valueOf(value));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean getBoolean() {
        return m_value;
    }

    public boolean getBoolean(long time) throws PropertyException {
        return getBoolean();
    }

    public boolean getBooleanAtIdx(int idx) throws PropertyException {
    	return getBoolean();
    }

    private boolean setCurrent(boolean value) {
        ((ConceptImpl)getParent()).checkSession();
//        if (!isSet()) {
//            setIsSet();
//            m_value = value;
//            setConceptModified();
//            return true;
//        }
//        else 
    	if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_value) {
        	m_value = value;
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setBoolean(boolean value) {
        return setCurrent(value);
    }

    public boolean setBoolean(boolean value, long time) {
        return setCurrent(value);
    }

    protected Object valueToObject(int index) {
        return m_value;
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeBooleanProperty(m_value);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value = deserializer.getBooleanProperty();
        }
    }

    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeBoolean(m_value);
    }

    protected void readValueBytes(DataInput input) throws Exception {
        m_value = input.readBoolean();
    }

    static protected boolean objectToBoolean(Object obj) {
        if(obj instanceof Boolean) {
            return ((Boolean)obj).booleanValue();
        }
        else if(obj instanceof String) {
            return PropertyAtomBooleanImpl.valueOf((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }
}

