package com.tibco.cep.runtime.model.element.impl.property.raw;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsLong;

/**
 * @author Pranab Dhar
 *
 */
abstract public class PropertyAtomLongRaw extends PropertyAtomRaw implements PropertyAtomLong
{
	protected long m_value = DEFAULT_VALUE;
	
	public PropertyAtomLongRaw(Object owner) {
		super(owner);
	}

	public PropertyAtomLongRaw(Object owner, long value) {
		super(owner);
		setValue(value);
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

	@Override
	public Object getValue() {
		return m_value;
	}

	@Override
	public boolean setValue(Object obj) {
		if(obj instanceof Number) {
			return setLong(((Number)obj).longValue());
		}
		return false;
	}

	@Override
	public boolean setValue(Object obj, long time) {
		return setValue(obj);
	}

	@Override
	public XmlTypedValue getXMLTypedValue() {
		return new XsLong(m_value);
	}

	@Override
	public boolean setValue(XmlTypedValue value) throws Exception {
		if (value != null) {
            return setLong(value.getAtom(0).castAsInt());
        } else {
            return setLong(DEFAULT_VALUE);
        }
	}

	@Override
	public boolean setValue(String value) throws Exception {
		if (value != null) {
            return setLong(Long.valueOf(value).longValue());
        } else {
            return setLong(DEFAULT_VALUE);
        }
	}

	@Override
	public long getLong() {
		return m_value;
	}

	@Override
	public long getLong(long time) throws PropertyException {
		return getLong();
	}

	@Override
	public long getLongAtIdx(int idx) throws PropertyException {
		return getLong();
	}

	@Override
	public boolean setLong(long value) {
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
	public boolean setLong(long value, long time) {
		return setLong(value);
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
	public long preIncrement() {
		setLong(m_value+1);
		return m_value;
	}

	@Override
	public long preDecrement() {
		setLong(m_value - 1);
		return m_value;
	}

	@Override
	public long postIncrement() {
		long ret = m_value;
		setLong(m_value + 1);
		return ret;
	}

	@Override
	public long postDecrement() {
		long ret = m_value;
		setLong(m_value - 1);
		return ret;
	}

	@Override
	public long multAssign(long mult) {
		setLong(m_value * mult);
		return m_value;
	}

	@Override
	public long divAssign(long div) {
		setLong(m_value / div);
		return m_value;
	}

	@Override
	public long modAssign(long mod) {
		setLong(m_value % mod);
		return m_value;
	}

	@Override
	public long addAssign(long add) {
		setLong(m_value + add);
		return m_value;
	}

	@Override
	public long subAssign(long sub) {
		setLong(m_value - sub);
		return m_value;
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

	@Override
	protected void writeValueBytes(DataOutput output) throws Exception {
		output.writeLong(m_value);
		
	}

	@Override
	protected void readValueBytes(DataInput input) throws Exception {
		m_value=input.readLong();
	}
}
