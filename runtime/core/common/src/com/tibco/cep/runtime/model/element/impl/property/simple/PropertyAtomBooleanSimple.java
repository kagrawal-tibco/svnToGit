package com.tibco.cep.runtime.model.element.impl.property.simple;

import java.io.DataInput;
import java.io.DataOutput;

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
public class PropertyAtomBooleanSimple extends PropertyAtomSimple implements PropertyAtomBoolean {

    boolean m_value;

    public PropertyAtomBooleanSimple(Object owner) {
        super(owner);
        m_value = DEFAULT_VALUE;
    }

    public PropertyAtomBooleanSimple(Object owner, boolean defaultValue) {
       super(owner);
       m_value = defaultValue;
       setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomBooleanSimple(newOwner));
    }

    protected PropertyAtomBooleanSimple _copy(PropertyAtomBooleanSimple ret) {
        super._copy(ret);
        ret.m_value = m_value;
        return ret;
    }

    public Object getValue() {
        return m_value? Boolean.TRUE:Boolean.FALSE;
    }

    public boolean setValue(Object obj) {
        if(obj != null) {
            return setBoolean(objectToBoolean(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        if(obj != null) {
            return setBoolean(objectToBoolean(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
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
        return m_value;
    }

    public boolean getBooleanAtIdx(int idx) throws PropertyException {
        return m_value;
    }

    private boolean setCurrent(boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
            setIsSet();
            m_value = value;
            setConceptModified();
            return true;
        }
        else if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_value) {
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
        return m_value? Boolean.TRUE:Boolean.FALSE;
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
            m_value=deserializer.getBooleanProperty();
        }
    }

    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeBoolean(m_value);
    }

    protected void readValueBytes(DataInput input) throws Exception {
        m_value=input.readBoolean();
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

