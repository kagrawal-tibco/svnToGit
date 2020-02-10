package com.tibco.cep.runtime.model.element.impl.property.raw;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsInt;

abstract public class PropertyAtomIntRaw extends PropertyAtomRaw implements PropertyAtomInt
{
	protected int m_value = DEFAULT_VALUE;
	
	public PropertyAtomIntRaw(Object owner) {
		super(owner);
	}

	@Override
	public Object getValue() {
		return m_value;
	}

	@Override
	public boolean setValue(Object obj) {
		if(obj instanceof Number) {
			return setInt(((Number)obj).intValue());
		}
		return false;
	}

	@Override
	public boolean setValue(Object obj, long time) {
		return setValue(obj);
	}

	@Override
	public XmlTypedValue getXMLTypedValue() {
		return new XsInt(m_value);
	}

	@Override
	public boolean setValue(XmlTypedValue value) throws Exception {
		if (value != null) {
            return setInt(value.getAtom(0).castAsInt());
        } else {
            return setInt(DEFAULT_VALUE);
        }
	}

	@Override
	public boolean setValue(String value) throws Exception {
		if (value != null) {
            return setInt(Integer.valueOf(value).intValue());
        } else {
            return setInt(DEFAULT_VALUE);
        }
	}

	@Override
	public int getInt() {
		return m_value;
	}

	@Override
	public int getInt(long time) throws PropertyException {
		return getInt();
	}

	@Override
	public int getIntAtIdx(int idx) throws PropertyException {
		return getInt();
	}

	@Override
	public boolean setInt(int value) {
        if(!isSet()) {
            setIsSet();
            m_value = value;
            return true;
        }
        else if(value != m_value) {
            m_value = value;
            return true;
        }
        return false;
	}

	@Override
	public boolean setInt(int value, long time) {
		return setInt(value);
	}

	@Override
	public double getDouble() {
		return m_value;
	}

	@Override
	public double getDouble(long time) throws PropertyException {
		return getDouble();
	}

	@Override
	public int preIncrement() {
		setInt(m_value+1);
		return m_value;
	}

	@Override
	public int preDecrement() {
		setInt(m_value - 1);
		return m_value;
	}

	@Override
	public int postIncrement() {
		int ret = m_value;
		setInt(m_value + 1);
		return ret;
	}

	@Override
	public int postDecrement() {
		int ret = m_value;
		setInt(m_value - 1);
		return ret;
	}

	@Override
	public int multAssign(int mult) {
		setInt(m_value * mult);
		return m_value;
	}

	@Override
	public int divAssign(int div) {
		setInt(m_value / div);
		return m_value;
	}

	@Override
	public int modAssign(int mod) {
		setInt(m_value % mod);
		return m_value;
	}

	@Override
	public int addAssign(int add) {
		setInt(m_value + add);
		return m_value;
	}

	@Override
	public int subAssign(int sub) {
		setInt(m_value - sub);
		return m_value;
	}

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
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

	@Override
	protected void writeValueBytes(DataOutput output) throws Exception {
		output.writeInt(m_value);
		
	}

	@Override
	protected void readValueBytes(DataInput input) throws Exception {
		m_value=input.readInt();
	}
}
