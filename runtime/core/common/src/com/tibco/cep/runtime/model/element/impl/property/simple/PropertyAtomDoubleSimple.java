package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsDouble;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:56:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDoubleSimple extends PropertyAtomSimple implements PropertyAtomDouble {

    double m_value;

    public PropertyAtomDoubleSimple(Object owner) {
        super(owner);
        m_value = DEFAULT_VALUE;
    }

    public PropertyAtomDoubleSimple(Object owner, double defaultValue) {
        super(owner);
        m_value = defaultValue;
        setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomDoubleSimple(newOwner));
    }

    protected PropertyAtomDoubleSimple _copy(PropertyAtomDoubleSimple ret) {
        super._copy(ret);
        ret.m_value = m_value;
        return ret;
    }

    public Object getValue() {
        return new Double(m_value);
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setDouble(objectToDouble(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        if (obj != null) {
            return setDouble(objectToDouble(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsDouble(m_value);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsDouble());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Double.valueOf(value).doubleValue());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public double getDouble() {
        return m_value;
    }

    public double getDouble(long time) throws PropertyException {
        return m_value;
    }

    public double getDoubleAtIdx(int idx) throws PropertyException {
        return m_value;
    }

    private boolean setCurrent(double value) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
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

    public boolean setDouble(double value) {
        return setCurrent(value);
    }

    public boolean setDouble(double value, long time) {
        return setCurrent(value);
    }

    protected Object valueToObject(int index) {
        return new Double(m_value);
    }

    public int getInt() {
        return (int)getDouble();
    }

    public int getInt(long time) throws PropertyException {
        return (int)getDouble(time);
    }
    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeDoubleProperty(m_value);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value=deserializer.getDoubleProperty();
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeDouble(m_value);
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        m_value=input.readDouble();
    }

    static protected double objectToDouble(Object obj) {
        if ((null != obj) && Number.class.isAssignableFrom(obj.getClass())) {
            return ((Number) obj).doubleValue();
        }
        else if(obj instanceof String) {
            return Double.parseDouble((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    public double preIncrement() {
        return addAssign(1);
    }

    public double preDecrement() {
        return subAssign(1);
    }

    public double postIncrement() {
        double temp = getDouble();
        preIncrement();
        return temp;
    }

    public double postDecrement() {
        double temp = getDouble();
        preDecrement();
        return temp;
    }

    public double multAssign(double mult) {
        setDouble(getDouble() * mult);
        return getDouble();
    }

    public double divAssign(double div) {
        setDouble(getDouble() / div);
        return getDouble();
    }

    public double modAssign(double mod) {
        setDouble(getDouble() % mod);
        return getDouble();
    }

    public double addAssign(double add) {
        setDouble(getDouble() + add);
        return getDouble();
    }

    public double subAssign(double sub) {
        setDouble(getDouble() - sub);
        return getDouble();
    }
}
