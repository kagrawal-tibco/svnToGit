package com.tibco.cep.runtime.model.element.impl.property.simple;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:32:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomConceptReferenceSimple extends PropertyAtomSimple implements PropertyAtomConceptReference {

    ConceptOrReference m_value;

    public PropertyAtomConceptReferenceSimple(Object owner) {
        super(owner);
        m_value = (ConceptOrReference)DEFAULT_VALUE;
    }

    public PropertyAtomConceptReferenceSimple(Object owner, Concept defaultValue) {
        super(owner);
        if (defaultValue != null && maintainReverseRef()) {
            ((ConceptImpl)defaultValue).setReverseRef(this);
        }
        m_value = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl) defaultValue);
        setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomConceptReferenceSimple(newOwner));
    }

    protected PropertyAtomConceptReferenceSimple _copy(PropertyAtomConceptReferenceSimple ret) {
        super._copy(ret);
        if (m_value instanceof Concept) {
            ret.m_value = new Reference(m_value.getId());
        }
        else {
            ret.m_value = m_value;
        }
        return ret;
    }

    public void clearReferences() {
        if (m_value instanceof Concept) {
            m_value = new Reference( ((Concept) m_value).getId());
        }
    }

    public boolean maintainReverseRef() {
    	return maintainReverseRef(getMetaProperty(), getParent().getClass());
    }

    public Class getType() {
        return getMetaProperty().getType();
    }

    public Object getValue() {
        return getConcept();
    }

    public boolean setValue(Object obj) {
        return setConcept(objectToConcept(getType(), obj));
    }

    public boolean setValue(Object obj, long time) {
        return setConcept(objectToConcept(getType(), obj), time);
    }
    
    public void setM_value(ConceptOrReference m_value) {
        if (!isSet()) {
            setIsSet();
            setConceptModified();
        }

        this.m_value = m_value;
    }

    public ConceptOrReference getM_value() {
        return m_value;
    }

    public XmlTypedValue getXMLTypedValue() {
        return null;
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        return false;
    }

    public boolean setValue(String value) throws Exception {
        return false;
    }

    public long getConceptId() {
        if(m_value == null) return 0;
        return m_value.getId();
    }

    public Concept getConcept() {
        return (Concept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    public long getConceptId(long time) {
        return getConceptId();
    }

    public long getConceptIdAtIdx(int idx) {
        return getConceptId();
    }

    public Concept getConcept(long time) throws PropertyException {
        return (Concept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    public Concept getConceptAtIdx(int idx) throws PropertyException {
        return (Concept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    private void checkType(Concept instance) {
        if (instance != null && !getType().isAssignableFrom(instance.getClass())) {
            throw new ClassCastException(ResourceManager.getInstance().formatMessage("property.cast.concept.exception",
                                                      instance.getClass().getName(), getType().getName()));
        }
    }

    public void setNull() {
        ((ConceptImpl)getParent()).checkSession();
        m_value = null;
        setConceptModified();
    }

    private boolean setCurrent(Concept instance) {
        ConceptImpl parent = ((ConceptImpl)getParent());
        parent.checkSession();
        if (!isSet()) {
            if (instance != null) {
                if(maintainReverseRef())
                ((ConceptImpl) instance).setReverseRef(this);
                m_value = parent.setConceptPointer((ConceptImpl) instance);
            } else {
                m_value = null;
            }
            setIsSet();
            setConceptModified();
        }
        else if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES ||
                !Reference.idEquals(instance, m_value))
        {
            if (maintainReverseRef()) {
	            if (m_value != null) {
	                ConceptImpl value = resolveConceptPointer(m_value, getType()); 
	                if(value != null) value.clearReverseRef(this);
	            }
	            if (instance != null) {
	                ((ConceptImpl) instance).setReverseRef(this);
	            }
            }
            m_value = parent.setConceptPointer((ConceptImpl) instance);
            setConceptModified();
        }
        return true;
    }

    public boolean setConcept(Concept instance) {
        checkType(instance);
        return setCurrent(instance);
    }

    public boolean setConcept(Concept instance, long time) {
        checkType(instance);
        return setCurrent(instance);
    }

    static protected Concept objectToConcept(Class type, Object obj) {
        if (obj == null) {
            return null;
        }
        else if (type.isAssignableFrom(obj.getClass())) {
            return (Concept)obj;
        }
        else {
            throw new ClassCastException();
        }
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeReferenceConceptProperty(m_value);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value=deserializer.getReferenceConceptProperty();
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        if (m_value != null) {
            output.writeBoolean(true);
            output.writeLong(m_value.getId());
        } else {
            output.writeBoolean(false);
        }
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        if (input.readBoolean()) {
            m_value = new Reference(input.readLong());
        }
    }

    public void fillXiNode(XiNode node, boolean changeOnly) {
    	ConceptImpl parent = (ConceptImpl)getParent();
    	if (!parent.expandPropertyRefs()) {
			if (m_value != null) {
				XiNode refNode = node.appendElement(ExpandedName.makeName(getName()));
				refNode.setAttributeStringValue(ExpandedName.makeName("ref"), String.valueOf(m_value.getId()));
			}
		} else {
			Concept instance = getConcept();
			if (instance != null && (!changeOnly || ((ConceptImpl) instance).isModified())) {
				XiNode concept = node.appendElement(ExpandedName.makeName(getName()));
				getConcept().toXiNode(concept);
			}
		}
    }
}
