package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsLong;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:58:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomLongSimple extends PropertyAtomSimple implements PropertyAtomLong {

    long m_value;

    public PropertyAtomLongSimple(Object owner) {
        super(owner);
        m_value = DEFAULT_VALUE;
    }

    public PropertyAtomLongSimple(Object owner, long defaultValue) {
        super(owner);
        m_value = defaultValue;
        setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomLongSimple(newOwner));
    }

    protected PropertyAtomLongSimple _copy(PropertyAtomLongSimple ret) {
        super._copy(ret);
        ret.m_value = m_value;
        return ret;
    }

    public Object getValue() {
        return new Long(m_value);
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setLong(objectToLong(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        if (obj != null) {
            return setLong(objectToLong(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public long getLong() {
        return m_value;
    }

    public long getLong(long time) throws PropertyException {
        return m_value;
    }

    public long getLongAtIdx(int idx) throws PropertyException {
        return m_value;
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsLong(m_value);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsLong());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Long.valueOf(value).longValue());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    private boolean setCurrent(long value) {
        ((ConceptImpl)getParent()).checkSession();
        if(!isSet()) {
            setIsSet();
            m_value = value;
            setConceptModified();
            return true;
        }
        else if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_value) {
            m_value = value;
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setLong(long value) {
        return setCurrent(value);
    }

    public boolean setLong(long value, long time) {
        return setCurrent(value);
    }

    public double getDouble() {
        return (double)getLong();
    }

    public double getDouble(long time) throws PropertyException {
        return (double)getLong(time);
    }

    protected Object valueToObject(int index) {
        return new Long(m_value);
    }


    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeLongProperty(m_value);
        }
    }
    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value=deserializer.getLongProperty();
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeLong(m_value);
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        m_value=input.readLong();
    }

    static protected long objectToLong(Object obj) {
        if ((null != obj) && Number.class.isAssignableFrom(obj.getClass())) {
            return ((Number) obj).longValue();
        }
        else if(obj instanceof String) {
            return Long.parseLong((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    public long preIncrement() {
        return addAssign(1);
    }

    public long preDecrement() {
        return subAssign(1);
    }

    public long postIncrement() {
        long temp = getLong();
        preIncrement();
        return temp;
    }

    public long postDecrement() {
        long temp = getLong();
        preDecrement();
        return temp;
    }

    public long multAssign(long mult) {
        setLong(getLong() * mult);
        return getLong();
    }

    public long divAssign(long div) {
        setLong(getLong() / div);
        return getLong();
    }

    public long modAssign(long mod) {
        setLong(getLong() % mod);
        return getLong();
    }

    public long addAssign(long add) {
        setLong(getLong() + add);
        return getLong();
    }

    public long subAssign(long sub) {
        setLong(getLong() - sub);
        return getLong();
    }
}
