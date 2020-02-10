package com.tibco.cep.runtime.model.element.impl.property.raw;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.xml.data.primitive.XmlTypedValue;

/*
* Author: Suresh Subramani / Date: 8/10/12 / Time: 1:48 PM
*/
abstract public class PropertyAtomConceptReferenceRaw extends PropertyAtomRaw implements PropertyAtomConceptReference {


    protected ConceptOrReference m_value;

    public PropertyAtomConceptReferenceRaw(Concept owner) {
        super(owner);
        m_value = (ConceptOrReference)DEFAULT_VALUE;
    }

    @Override
    public boolean setConcept(Concept instance) {
        return setConcept(instance, 0);
    }

    @Override
    public boolean setConcept(Concept instance, long time) {
        m_value = (ConceptOrReference)instance;
        setIsSet();
        return true;
    }

    @Override
    public void clearReferences() {
        return;
    }

    @Override
    public Concept getConcept() {
        return (Concept) (m_value = m_value != null ? resolveConceptPointer(m_value, getType()):null);
    }

    @Override
    public long getConceptId() {
        return m_value == null ? 0 : m_value.getId();
    }

    @Override
    public long getConceptId(long time) throws PropertyException {
        return getConceptId();
    }

    @Override
    public long getConceptIdAtIdx(int idx) throws PropertyException {
        return getConceptId();
    }

    @Override
    protected void writeValueBytes(DataOutput output) throws Exception {
         if (m_value != null) {
            output.writeBoolean(true);
            output.writeLong(m_value.getId());
        } else {
            output.writeBoolean(false);
        }
    }

    @Override
    protected void readValueBytes(DataInput input) throws Exception {
        if (input.readBoolean()) {
            m_value = new Reference(input.readLong());
        }
    }

    @Override
    public Object getValue() {
        return m_value;
    }

    @Override
    public boolean setValue(Object obj) {
        if (obj instanceof Concept) return setConcept((Concept)obj);
        return false;
    }

    @Override
    public boolean setValue(Object obj, long time) {
        return setValue(obj);
    }

    @Override
    public XmlTypedValue getXMLTypedValue() {
        return null;
    }

    @Override
    public boolean setValue(XmlTypedValue value) throws Exception {
        return false;
    }

    @Override
    public boolean setValue(String value) throws Exception {
        return false;
    }

    @Override
    public boolean maintainReverseRef() {
        return false;
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeReferenceConceptProperty(new Reference(m_value.getId()));
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value = deserializer.getReferenceConceptProperty();
        }
    }
}