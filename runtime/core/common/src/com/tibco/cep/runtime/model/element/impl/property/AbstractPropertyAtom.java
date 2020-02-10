package com.tibco.cep.runtime.model.element.impl.property;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Iterator;

import com.tibco.cep.runtime.model.PropertyNullValues;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.flavor.XSDLConstants;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:12:27 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractPropertyAtom extends PropertyImpl implements PropertyAtom {

    protected Object    m_owner; //possible owner - Concept and PropertyArray

    protected AbstractPropertyAtom(Object owner) {
        m_owner = owner;
    }

    public boolean isSet() {
        return (m_status & IS_SET) != 0;
    }

    public void setIsSet() {
        m_status |= IS_SET;
    }

    public void clearIsSet() {
        m_status &= ~IS_SET;
    }

    abstract public AbstractPropertyAtom copy(Object newOwner);

    protected AbstractPropertyAtom _copy(AbstractPropertyAtom ret) {
        ret.m_status = m_status;
        return ret;
    }

    public int getPropertyIndex() {
        if(m_owner instanceof PropertyArray) return ((PropertyImpl)m_owner).getPropertyIndex();
        else return super.getPropertyIndex();
    }

    public int getHistorySize() {
        return 0;
    }

    public long getTimeAtIdx(int idx) throws PropertyException {
        return 0;
    }

    public void getHistoryTimeInterval(long time, long[] interval) throws PropertyException {
        throw new PropertyException(ResourceManager.getInstance().formatMessage("property.unknown.time.exception",
                                                  getName(), getParent(), Long.toString(time)));

    }

    public long howCurrent () {
        return 0L;
    }

    public long howOld() {
        return 0L;
    }

    public int howMany(long stime, long etime) throws PropertyException {
        return 0;
    }

    public String getString() {
        Object value = getValue();
        if(value != null) return value.toString();
        else return null;
    }

    public String getString(long time) throws PropertyException {
        return getString();
    }

    public String getStringAtIdx(int idx) throws PropertyException {
        return getString();
    }

    public Object getPreviousValue(){
        return getValue();
    }

    public Object getValue(long time) throws PropertyException {
        return getValue();
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return getValue();
    }

//    public boolean setValue(Object obj, long time) {
//        return false;  //To change body of implemented methods use File | Settings | File Templates.
//    }

    protected Object getOwner() {
        return m_owner;
    }

    public Concept getParent() {
        if(m_owner instanceof Concept)
            return (Concept)m_owner;
        else if(m_owner instanceof AbstractPropertyArray) {
            return ((AbstractPropertyArray)m_owner).m_parent;
        } else {
            return null;
        }
    }

    public Iterator historyIterator() {
        return null;
    }

    public Iterator backwardHistoryIterator() {
        return null;
    }

    public Iterator forwardHistoryIterator() {
        return null;
    }

    public void fillXiNode(XiNode node, boolean changeOnly) {

    	if (PropertyNullValues.isPropertyValueNull(this)) {
			if (((ConceptImpl) getParent()).excludeNullProps()) {
				// don't append property
			} else if (((ConceptImpl) getParent()).setNilAttribs()) {
				// if xsd is defined with nillable attribute, set xsi:nil
				XiNode child = node.appendElement(ExpandedName.makeName(getName()));
				child.setAttributeStringValue(XSDLConstants.ENAME_ATTR_NIL,	"true");
			} else {
				//append the property, but not its value, since it is null
				node.appendElement(ExpandedName.makeName(getName()));
			}
		} else { // default behavior
			if (getValue() != null) {
				node.appendElement(ExpandedName.makeName(getName())).appendText(getXMLTypedValue());
			} else if (! ((ConceptImpl) getParent()).excludeNullProps()) {
				//append the property, but not its value, since it is null
				node.appendElement(ExpandedName.makeName(getName()));
			}
		}
	}

    /**
	 * 
	 * @param output
	 * @throws Exception
	 */
    public void writeToDataOutput(DataOutput output) throws Exception {
        output.writeBoolean(isSet());
        if (isSet()) {
            writeValueBytes(output);
        }
    }

    abstract protected void writeValueBytes(DataOutput output) throws Exception;


    /**
     *
     * @param input
     * @throws Exception
     */
    public void readFromDataInput(DataInput input) throws Exception {
        boolean isSet= input.readBoolean();
        if (isSet) {
            this.setIsSet();
        } else {
            this.clearIsSet();
        }
        readValueBytes(input);
    }

    abstract protected void readValueBytes(DataInput input) throws Exception;

    public void deserialize(ConceptDeserializer deserializer, int order) {
        if (deserializer.areNullPropsSerialized()) {
            deserialize_nullprops(deserializer, order);
        } else {
            deserialize_nonullprops(deserializer, order);
        }
    }

    public void serialize(ConceptSerializer serializer, int order) {
        if (serializer.areNullPropsSerialized()) {
            serialize_nullprops(serializer, order);
        } else {
            serialize_nonullprops(serializer, order);
        }
    }

    public void serialize_nullprops(ConceptSerializer serializer, int order) {
        serializer.startProperty(this.getName(), order, isSet());
    }

    /**
     *
     * @param deserializer
     * @param order
     */
    public void deserialize_nullprops(ConceptDeserializer deserializer, int order) {
        boolean isSet= deserializer.startProperty(this.getName(), order);
        if (isSet) {
            this.setIsSet();
        } else {
            this.clearIsSet();
        }

    }
    /**
     *
     * @param serializer
     * @param order
     */
    public void serialize_nonullprops(ConceptSerializer serializer, int order) {
    }

    /**
     *
     * @param deserializer
     * @param order
     */
    public void deserialize_nonullprops(ConceptDeserializer deserializer, int order) {
        setIsSet();
    }

}