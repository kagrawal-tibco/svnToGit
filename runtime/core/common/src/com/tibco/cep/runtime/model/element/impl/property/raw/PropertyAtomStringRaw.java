package com.tibco.cep.runtime.model.element.impl.property.raw;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsString;

/**
 * @author Pranab Dhar
 * 
 */
abstract public class PropertyAtomStringRaw extends PropertyAtomRaw implements PropertyAtomString {
	protected String m_value = DEFAULT_VALUE;

	public PropertyAtomStringRaw(Object owner) {
		super(owner);
	}

	public PropertyAtomStringRaw(Object owner, String value) throws Exception {
		super(owner);
		setValue(value);
	}

	@Override
	public Object getValue() {
		return m_value;
	}

	@Override
	public boolean setValue(Object obj) {
		if (obj != null) {
			return setString(obj.toString());
		} else {
			return setCurrent(DEFAULT_VALUE);
		}
	}

	@Override
	public boolean setValue(Object obj, long time) {
		return setValue(obj);
	}

	@Override
	public XmlTypedValue getXMLTypedValue() {
		return new XsString(m_value);
	}

	@Override
	public boolean setValue(XmlTypedValue value) throws Exception {
		if (value != null) {
			return setCurrent(value.getAtom(0).castAsString());
		} else {
			return setCurrent(DEFAULT_VALUE);
		}
	}

	@Override
	public boolean setValue(String value) throws Exception {
		if (value != null) {
			return setCurrent(value);
		} else {
			return setCurrent(DEFAULT_VALUE);
		}
	}

	private boolean setCurrent(String value) {
		if (!isSet()) {
			setIsSet();
			m_value = value;
			setConceptModified();
			return true;
		} else if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || (value != null && !value.equals(m_value)) || (value == null && m_value != null)) {
			m_value = value;
			setConceptModified();
			return true;
		}
		return false;
	}

	@Override
	public String getString() {
		return m_value;
	}

	@Override
	public String getString(long time) throws PropertyException {
		return getString();
	}

	@Override
	public String getStringAtIdx(int idx) throws PropertyException {
		return getString();
	}

	@Override
	public boolean setString(String value) {
		return setCurrent(value);
	}

	@Override
	public boolean setString(String value, long time) {
		return setCurrent(value);
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
			m_value = deserializer.getStringProperty();
		}
	}

	@Override
	protected void writeValueBytes(DataOutput output) throws Exception {
		if (m_value != null) {
			output.writeBoolean(true);
			output.writeUTF(m_value);
		} else {
			output.writeBoolean(false);
		}

	}

	@Override
	protected void readValueBytes(DataInput input) throws Exception {
		if (input.readBoolean()) {
			m_value = input.readUTF();
		}
	}

	public String addAssign(String add) {
		setString(getString() + add);
		return getString();
	}
}