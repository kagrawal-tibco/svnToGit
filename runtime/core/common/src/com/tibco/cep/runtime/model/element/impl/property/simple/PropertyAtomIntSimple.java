package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsInt;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:57:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomIntSimple extends PropertyAtomSimple implements PropertyAtomInt {

    int m_value;

    public PropertyAtomIntSimple(Object owner) {
        super(owner);
        m_value = DEFAULT_VALUE;
    }

    public PropertyAtomIntSimple(Object owner, int defaultValue) {
        super(owner);
        m_value = defaultValue;
        setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomIntSimple(newOwner));
    }

    protected PropertyAtomIntSimple _copy(PropertyAtomIntSimple ret) {
        super._copy(ret);
        ret.m_value = m_value;
        return ret;
    }

    public Object getValue() {
        return new Integer(m_value);
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setInt(objectToInt(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        if (obj != null) {
            return setInt(objectToInt(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public int getInt() {
        return m_value;
    }

    public int getInt(long time) throws PropertyException {
        return m_value;
    }

    public int getIntAtIdx(int idx) throws PropertyException {
        return m_value;
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsInt(m_value);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsInt());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Integer.valueOf(value).intValue());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    private boolean setCurrent(int value) {
        return setCurrent(value, true);
    }
    private boolean setCurrent(int value, boolean modifyParent) {
        ((ConceptImpl)getParent()).checkSession();
        if(!isSet()) {
            setIsSet();
            m_value = value;
            setConceptModified(modifyParent);
            return true;
        }
        else if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_value) {
            m_value = value;
            setConceptModified(modifyParent);
            return true;
        }
        return false;
    }

    protected boolean setConceptModified(boolean modifyParent) {
        return (getParentConceptImpl()).modifyConcept(this, false, modifyParent);
    }
    
    public boolean setInt(int value) {
        return setCurrent(value);
    }

    public boolean setInt(int value, long time) {
        return setCurrent(value);
    }
    
    protected boolean setInt(int value, boolean modifyParent) {
        return setCurrent(value, modifyParent);
    }

    public double getDouble() {
        return (double)getInt();
    }

    public double getDouble(long time) throws PropertyException {
        return (double)getInt(time);
    }

    protected Object valueToObject(int index) {
        return new Integer(m_value);
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer,order);
        if (isSet()) {
            serializer.writeIntProperty(m_value);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value=deserializer.getIntProperty();
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeInt(m_value);
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        m_value=input.readInt();
    }

    static protected int objectToInt(Object obj) {
        if ((null != obj) && Number.class.isAssignableFrom(obj.getClass())) {
            return ((Number) obj).intValue();
        }
        else if(obj instanceof String) {
            return Integer.parseInt((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    public int preIncrement() {
        return addAssign(1);
    }

    public int preDecrement() {
        return subAssign(1);
    }

    public int postIncrement() {
        int temp = getInt();
        preIncrement();
        return temp;
    }

    public int postDecrement() {
        int temp = getInt();
        preDecrement();
        return temp;
    }

    public int multAssign(int mult) {
        setInt(getInt() * mult);
        return getInt();
    }

    public int divAssign(int div) {
        setInt(getInt() / div);
        return getInt();
    }

    public int modAssign(int mod) {
        setInt(getInt() % mod);
        return getInt();
    }

    public int addAssign(int add) {
        setInt(getInt() + add);
        return getInt();
    }

    public int subAssign(int sub) {
        setInt(getInt() - sub);
        return getInt();
    }
}
