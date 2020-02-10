package com.tibco.cep.runtime.model.element.impl.property.simple;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.element.impl.property.PropertyAtomContainedConcept_CalledFromCondition;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:37:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomContainedConceptSimple extends PropertyAtomSimple implements PropertyAtomContainedConcept, PropertyAtomContainedConcept_CalledFromCondition {

    ConceptOrReference m_value;

    public PropertyAtomContainedConceptSimple(Object owner) {
        super(owner);
        m_value = (ConceptImpl)DEFAULT_VALUE;
    }

    public PropertyAtomContainedConceptSimple(Object owner, ContainedConcept defaultValue) {
        super(owner);
        ConceptImpl parent = (ConceptImpl) getParent();
        if(defaultValue != null)
            defaultValue.setParent(parent);
        m_value = parent.setConceptPointer((ConceptImpl) defaultValue);
        setIsSet();
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

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomContainedConceptSimple(newOwner));
    }

    protected PropertyAtomContainedConceptSimple _copy(PropertyAtomContainedConceptSimple ret) {
        super._copy(ret);
        if(m_value instanceof Concept)
            ret.m_value = new Reference(m_value.getId());
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

    public Class getType() {
        return getMetaProperty().getType();
    }

    public int modifiedIndex() {
        return getParentConceptImpl().getMaxDirtyBitIdx() - getMetaProperty().getContainedPropIndex();
    }

    public Object getValue() {
        return getContainedConcept();
    }

    public boolean setValue(Object obj) {
        return setContainedConcept(objectToContainedConcept(getType(), obj));
    }

    public boolean setValue(Object obj, long time) {
        return setContainedConcept(objectToContainedConcept(getType(), obj), time);
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

    public ContainedConcept getContainedConcept() {
        return (ContainedConcept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    //generated property atoms will override this method
    //property atoms that are elements in a property array will use this method
    public ContainedConcept getContainedConcept(boolean calledFromCondition) {
        ContainedConcept cc = getContainedConcept();
        if(calledFromCondition && cc == null) {
            return getParentConceptImpl().getNullContainedConcept();
        } else {
            return cc;
        }
    }

    public long getContainedConceptId() {
        if(m_value == null) return 0;
        else return m_value.getId();
    }


    public ContainedConcept getContainedConceptAtIdx(int idx) {
        return (ContainedConcept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    //to comply with PropertyAtom interface
    public Concept getConcept() {
        return (ContainedConcept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    public long getConceptId() {
        if(m_value == null) return 0;
        else return m_value.getId();
    }

    public long getConceptId(long time) {
        return getConceptId();
    }

    public long getConceptIdAtIdx(int idx) {
        return getConceptId();
    }

    public long getContainedConceptId(long time) throws PropertyException {
        return getConceptId();
    }

    public long getContainedConceptIdAtIdx(int idx) throws PropertyException {
        return getConceptId();
    }

    public ContainedConcept getContainedConcept(long time) throws PropertyException {
        return (ContainedConcept) (m_value = resolveConceptPointer(m_value, getType()));
    }

    /*
    private ContainedConcept restoreContainedConcept() {
        if(m_value instanceof Reference) {
            m_value = (ConceptImpl)((Reference)m_value).getConcept();
        }
        return (ContainedConcept)m_value;
    }
    */

    public void setNull(ConceptImpl deletedChild) {
        ConceptImpl parent = ((ConceptImpl)getParent());
        parent.checkSession();
        m_value = null;
        setConceptModified();
    }

    private void checkType(ContainedConcept instance) {
        if(instance != null && !getType().isAssignableFrom(instance.getClass())) {
            throw new ClassCastException(ResourceManager.getInstance().formatMessage("property.cast.concept.exception",
                                                      instance.getClass().getName(), getType().getName()));
        }
    }

    protected boolean setCurrent(ContainedConcept instance) {
        ConceptImpl parent = (ConceptImpl)getParent();
        parent.checkSession();
        if (!isSet()) {
            if (instance != null) {
                instance.setParent(parent);
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
            if (m_value != null) {
                ConceptImpl value = resolveConceptPointer(m_value, getType());
                if(value != null) nullParent(value);
            }
            if (instance != null)
                instance.setParent(parent);
            m_value = parent.setConceptPointer((ConceptImpl) instance);
            setConceptModified();
        }
        return true;
    }

    public boolean setContainedConcept(ContainedConcept instance) {
        checkType(instance);
        return setCurrent(instance);
    }

    public boolean setContainedConcept(ContainedConcept instance, long time) {
        checkType(instance);
        return setCurrent(instance);
    }

    static protected ContainedConcept objectToContainedConcept(Class type, Object obj) {
        if(obj == null) {
            return null;
        }
        else if(type.isAssignableFrom(obj.getClass())) {
            return (ContainedConcept)obj;
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
            serializer.writeContainedConceptProperty(m_value);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value=deserializer.getContainedConceptProperty();
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
        ContainedConcept instance = getContainedConcept();
        
        if (instance != null && (!changeOnly || ((ConceptImpl)instance).isModified())) {
            XiNode concept = node.appendElement(ExpandedName.makeName(getName()));
            instance.toXiNode(concept);
        }
    }

}
