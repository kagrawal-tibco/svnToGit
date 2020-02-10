package com.tibco.cep.runtime.model.element.impl.property.history;

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
 * Date: Jun 24, 2006
 * Time: 1:27:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomContainedConceptImpl extends PropertyAtomImpl implements PropertyAtomContainedConcept, PropertyAtomContainedConcept_CalledFromCondition {

    ConceptOrReference[]  m_values = null;

    public PropertyAtomContainedConceptImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values = new ConceptOrReference[historySize];
            m_values[0] = (ConceptImpl) DEFAULT_VALUE;
        }
    }

    public PropertyAtomContainedConceptImpl(int historySize, Object owner, ContainedConcept defaultValue) {
        super(historySize, owner);
        m_values = new ConceptOrReference[historySize];
        if(defaultValue != null)
            defaultValue.setParent(getParent());
        m_values[0] = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl) defaultValue);
        m_time[0] = System.currentTimeMillis();
        setIsSet();
    }

    public void setM_value(ConceptOrReference m_value, long m_time, int idx) {
        if (!isSet()) {
            setIsSet();
            setConceptModified();
        }

        this.m_values[idx] = m_value;
        this.m_time[idx] = m_time;
        this.m_index = (short) idx;
    }

    @Override
    public PropertyAtomContainedConceptImpl copy(Object newOwner) {
        return _copy(new PropertyAtomContainedConceptImpl(0, newOwner));
    }

    protected PropertyAtomContainedConceptImpl _copy(PropertyAtomContainedConceptImpl ret) {
        super._copy(ret);
        ret.m_values = new ConceptOrReference[m_values.length];
        for(int i = 0; i < m_values.length; i++) {
            ConceptOrReference cor = m_values[i];
            if(cor instanceof Concept) {
                ret.m_values[i] = new Reference(cor.getId());
            } else {
                ret.m_values[i] = cor;
            }
        }
        return ret;
    }

    public void clearReferences() {
        for(int i = 0; i < m_values.length; i++) {
            if(m_values[i] instanceof Concept)
                m_values[i] = new Reference(m_values[i].getId());
            m_time[i]   = m_time[i];
        }
    }

    public Number minOverTime(long from_no_of_msec_ago) {
        throw new RuntimeException("PropertyAtomImpl.minOf is not implemented for PropertyAtomContainedConceptImpl");
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

    public Object getValue(long time) throws PropertyException {
        return getContainedConcept(time);
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return getContainedConceptAtIdx(idx);
    }

    public Object getPreviousValue() {
        if ((m_index-1)>=0) return resolveConceptPointer(m_values[m_index-1], getType());
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return resolveConceptPointer(m_values[m_values.length-1], getType());
        return null; // or exception :\
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
        return (ContainedConcept) resolveConceptPointer(m_values[m_index], getType());
    }

    public ContainedConcept getContainedConcept(boolean calledFromCondition) {
        ContainedConcept cc = getContainedConcept();
        if(calledFromCondition && cc == null) {
            return getParentConceptImpl().getNullContainedConcept();
        } else {
            return cc;
        }
    }

    public long getContainedConceptId() {
        if(m_values[m_index] == null) return 0;
        else return m_values[m_index].getId();
    }

    //to comply with PropertyAtom interface
    public long getConceptId() {
        if(m_values[m_index] == null) return 0;
        return m_values[m_index].getId();
    }

    public Concept getConcept() {
        return resolveConceptPointer(m_values[m_index], getType());
    }

    public long getConceptId(long time) throws PropertyException {
        ConceptOrReference r = m_values[getIndex(time)];
        if(r == null) return 0;
        return r.getId();
    }

    public long getConceptIdAtIdx(int idx) throws PropertyException {
        ConceptOrReference r = m_values[mapIndex(idx)];
        if(r == null) return 0;
        return r.getId();
    }

    public long getContainedConceptId(long time) throws PropertyException {
        ConceptOrReference r = m_values[getIndex(time)];
        if(r == null) return 0;
        return r.getId();
    }

    public long getContainedConceptIdAtIdx(int idx) throws PropertyException {
        ConceptOrReference r = m_values[mapIndex(idx)];
        if(r == null) return 0;
        return r.getId();
    }

    public Concept getConcept(long time) throws PropertyException {
        return getContainedConcept(time);
    }

    public ContainedConcept getContainedConcept(long time) throws PropertyException {
        return (ContainedConcept)resolveConceptPointer(m_values[getIndex(time)], getType());
    }

    public ContainedConcept getContainedConceptAtIdx(int idx) throws PropertyException {
        return (ContainedConcept)resolveConceptPointer(m_values[mapIndex(idx)], getType());
    }

    /*
    private ContainedConcept restoreContainedConcept(int index) {
        if(m_values[index] instanceof Reference) {
            m_values[index] = (ConceptImpl)((Reference)m_values[index]).getConcept();
        }
        return (ContainedConcept)m_values[index];
    } */

    public void setNull(ConceptImpl deletedChild) {
        ConceptImpl parent = ((ConceptImpl)getParent());
        parent.checkSession();
        m_index++;
        if (m_index == m_values.length) {
            m_index = 0;
        }
        m_values[m_index] = null;
        m_time[m_index] = System.currentTimeMillis();
        setConceptModified();
    }

    private void checkType(ContainedConcept instance) {
        if(instance != null && !getType().isAssignableFrom(instance.getClass())) {
            throw new ClassCastException(ResourceManager.getInstance().formatMessage("property.cast.concept.exception",
                                                      instance.getClass().getName(), getType().getName()));
        }
    }

    private boolean setCurrent(ContainedConcept instance, long time) {
        ConceptImpl parent = ((ConceptImpl)getParent());
        parent.checkSession();
        if(!isSet()) {
            m_index = 0;  // making sure
            if(instance != null) {
                instance.setParent(parent);
                m_values[m_index] = parent.setConceptPointer((ConceptImpl) instance);
            }
            else {
                m_values[m_index] = null;
            }
            m_time[m_index] = time;
            setIsSet();
            setConceptModified();
            return true;
        }
        else if ((getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) ||
                 (!Reference.idEquals(instance, m_values[m_index]))) 
        {
            if(m_values[m_index] != null) {
                ConceptImpl value = resolveConceptPointer(m_values[m_index], getType());
                if(value != null) value.nullParent();
            }
            if(instance != null)
                instance.setParent(parent);
            m_index++;
            if (m_index == m_values.length) {
                m_index = 0;
            }
            m_values[m_index] = parent.setConceptPointer((ConceptImpl) instance);
            m_time[m_index] = time;
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setContainedConcept(ContainedConcept instance) {
        checkType(instance);
        return setCurrent(instance, System.currentTimeMillis());
    }

    public boolean setContainedConcept(ContainedConcept instance, long time) {
        checkType(instance);

        if(!isSet()) return setCurrent(instance, time);
        ((ConceptImpl)getParent()).checkSession();
        if(time >= m_time[m_index]) {
            return setCurrent(instance, time);
        }
        else {
            if(m_index == m_values.length-1) {
                for(int i = m_values.length-2; i >= 0; i --) {
                    if(time >= m_time[i]) {
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            Reference.idEquals(m_values[i], instance))
                        {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, 1, m_time, 0, i);
                            System.arraycopy(m_values, 1, m_values, 0, i);
                            m_time[i]   = time;
                            m_values[i] = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl)instance);
                            setConceptModified();
                            return true;
                        }
                    }
                }
            }
            else if(m_time[0] <= time && time < m_time[m_index]) {
                for(int i = m_index-1; i >= 0; i--) {
                    if(time >= m_time[i]) {
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            Reference.idEquals(m_values[i], instance))
                        {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, i+1, m_time, i+2, m_index - i);
                            System.arraycopy(m_values, i+1, m_values, i+2, m_index - i);
                            m_time[i+1]   = time;
                            m_values[i+1] = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl)instance);
                            m_index++;
                            setConceptModified();
                            return true;
                        }
                    }
                }
            } else if (m_time[m_index + 1] == 0) {
                //buffer is not full, should put in index 0
                if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) {
                    if(Reference.idEquals(m_values[0], instance))
                    {
                        //update the time only
                        m_time[0] = time;
                    }
                    else {
                        //update both
                        System.arraycopy(m_time, 0, m_time, 1, m_index +1);
                        System.arraycopy(m_values, 0, m_values, 1, m_index +1);
                        m_time[0] = time;
                        m_values[0] = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl)instance);
                        m_index++;
                    }
                }
                else { //all values
                    System.arraycopy(m_time, 0, m_time, 1, m_index +1);
                    System.arraycopy(m_values, 0, m_values, 1, m_index +1);
                    m_time[0] = time;
                    m_values[0] = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl)instance);
                    m_index++;
                }
                setConceptModified();
                return true;
            }
            else if (m_time[m_index+1] <= time) {
                for(int i = m_values.length-1; i > m_index; i-- ) {
                    if(time >= m_time[i]) {
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            Reference.idEquals(m_values[i], instance))
                        {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, m_index+2, m_time, m_index+1, i- m_index -1);
                            System.arraycopy(m_values, m_index+2, m_values, m_index+1, i- m_index -1);
                            m_time[i]   = time;
                            m_values[i] = ((ConceptImpl)getParent()).setConceptPointer((ConceptImpl)instance);
                            setConceptModified();
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    protected Object valueToObject(int index) {
        return resolveConceptPointer(m_values[index], getType());
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

    public void fillXiNode(XiNode node, boolean changeOnly) {
        ContainedConcept instance = getContainedConcept();
        if (instance != null && (!changeOnly || ((ConceptImpl)instance).isModified())) {
            XiNode concept = node.appendElement(ExpandedName.makeName(getName()));
            getContainedConcept().toXiNode(concept);
        }
    }
    
    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        try {
            super.serialize(serializer, order);
            if (isSet()) {
                serializer.writeReferenceConceptProperty(m_values, m_time);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            deserializer.getReferenceConceptProperty(m_values,m_time);
        }
    }
    
    /**
     *
     * @param output
     * @throws Exception
     */
    void writeValueBytes(DataOutput output,int howMany) throws Exception {
        for (int i=0; i < howMany;i++) {
            if (m_values[i] != null) {
                output.writeBoolean(true);
                output.writeLong( m_values[i].getId());
            } else {
                output.writeBoolean(false);
            }
        }
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    void readValueBytes(DataInput input,int historySize) throws Exception {
        for(int i=0; i < historySize; i++) {
            if(input.readBoolean())
                m_values[i] = new Reference(input.readLong());
            else
                m_values[i] = null;
        }
    }
}
