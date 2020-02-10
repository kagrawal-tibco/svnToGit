package com.tibco.cep.runtime.model.element.impl.property.simple;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsString;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:00:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomStringSimple extends PropertyAtomSimple implements PropertyAtomString {

    String m_value;

    public PropertyAtomStringSimple(Object owner) {
        super(owner);
        m_value = DEFAULT_VALUE;
    }

    public PropertyAtomStringSimple(Object owner, String defaultValue) {
        super(owner);
        m_value = defaultValue;
        setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomStringSimple(newOwner));
    }

    protected PropertyAtomStringSimple _copy(PropertyAtomStringSimple ret) {
        super._copy(ret);
        ret.m_value = m_value;
        return ret;
    }

    public Object getValue() {
        return m_value;
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setString(obj.toString());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        if (obj != null) {
            return setString(obj.toString(), time);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public String getString() {
        return m_value;
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsString(m_value);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsString());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(value);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    private boolean setCurrent(String value) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
            setIsSet();
            m_value = value;
            setConceptModified();
            return true;
        }
        else if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES ||
                 (value != null && !value.equals(m_value)) ||
                 (value == null && m_value != null)) {
            m_value = value;
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setString(String value) {
        return setCurrent(value);
    }

    public boolean setString(String value, long time) {
        return setCurrent(value);
    }

    protected Object valueToObject(int index) {
        return new String(m_value);
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeStringProperty(m_value);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value=deserializer.getStringProperty();
        }
    }

    protected void writeValueBytes(DataOutput output) throws Exception {
        if (m_value != null) {
            output.writeBoolean(true);
            output.writeUTF(m_value);
        } else {
            output.writeBoolean(false);
        }
    }

    protected void readValueBytes(DataInput input) throws Exception {
        if (input.readBoolean()) {
            m_value=input.readUTF();
        }
    }

    public String addAssign(String add) {
        setString(getString() + add);
        return getString();
    }
}
