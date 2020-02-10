package com.tibco.cep.runtime.model.element.impl.property.history;

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
 * Date: Jun 23, 2006
 * Time: 5:47:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomConceptReferenceImpl extends PropertyAtomImpl implements PropertyAtomConceptReference {

    ConceptOrReference[] m_values = null;

    public PropertyAtomConceptReferenceImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values = new ConceptOrReference[historySize];
            m_values[0] = (ConceptImpl) DEFAULT_VALUE;
        }
    }

    public PropertyAtomConceptReferenceImpl(int historySize, Object owner, Concept defaultValue) {
        super(historySize, owner);
        m_values = new ConceptOrReference[historySize];
        if(defaultValue != null && maintainReverseRef()) {
            ((ConceptImpl)defaultValue).setReverseRef(this);
        }
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
        this.m_index = (short)idx;
    }

    @Override
    public PropertyAtomConceptReferenceImpl copy(Object newOwner) {
        return _copy(new PropertyAtomConceptReferenceImpl(0, newOwner));
    }

    protected PropertyAtomConceptReferenceImpl _copy(PropertyAtomConceptReferenceImpl ret) {
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

    public boolean maintainReverseRef() {
    	return maintainReverseRef(getMetaProperty(), getParent().getClass());
    }

    public Class getType() {
        return getMetaProperty().getType();
    }

    public Object getValue() {
        return getConcept();
    }

    public Object getPreviousValue() {
        if ((m_index-1)>=0) return resolveConceptPointer(m_values[m_index-1], getType());
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return resolveConceptPointer(m_values[m_values.length-1], getType());
        return null; // or exception :\
    }

    public Object getValue(long time) throws PropertyException {
        return getConcept(time);
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return getConceptAtIdx(idx);
    }

    public boolean setValue(Object obj) {
        return setConcept(objectToConcept(getType(), obj));
    }

    public boolean setValue(Object obj, long time) {
        return setConcept(objectToConcept(getType(), obj), time);
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

    public Concept getConcept() {
        return resolveConceptPointer(m_values[m_index], getType());
    }

    public Concept getConcept(long time) throws PropertyException {
        return resolveConceptPointer(m_values[getIndex(time)], getType());
    }

    public Concept getConceptAtIdx(int idx) throws PropertyException {
        return resolveConceptPointer(m_values[mapIndex(idx)], getType());
    }

    public long getConceptId() {
        if(m_values[m_index] == null) return 0;
        return m_values[m_index].getId();
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

    /*
    private ConceptImpl restoreConcept(int index) {
        if(m_values[index] instanceof Reference) {
            m_values[index] = (ConceptImpl)((Reference)m_values[index]).getConcept();
        }
        return (ConceptImpl)m_values[index];
    }*/


    private void checkType(com.tibco.cep.runtime.model.element.Concept instance) {
        if(instance != null && !getType().isAssignableFrom(instance.getClass())) {
            throw new ClassCastException(ResourceManager.getInstance().formatMessage("property.cast.concept.exception",
                                                      instance.getClass().getName(), getType().getName()));
        }
    }

    /**
     * This methods should be called only in ConceptImpl when deleting a reference concept
     */
    public void setNull() {
        ((ConceptImpl)getParent()).checkSession();
        m_index++;
        if (m_index == m_values.length) {
            m_index = 0;
        }
        m_values[m_index] = null;
        m_time[m_index] = System.currentTimeMillis();
        setConceptModified();
    }

    private boolean setCurrent(ConceptImpl instance, long time) {
        ConceptImpl parent = ((ConceptImpl)getParent());
        parent.checkSession();
        if(!isSet()) {
            m_index = 0;  // making sure
            if(instance != null) {
                if(maintainReverseRef())
                instance.setReverseRef(this);
                m_values[m_index] = parent.setConceptPointer(instance);
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
                 !Reference.idEquals(instance, m_values[m_index]))
        {
            if(maintainReverseRef()) {
            if(m_values[m_index] != null) {
                ConceptImpl value = resolveConceptPointer(m_values[m_index], getType());
                if(value != null) value.clearReverseRef(this);
            }
            if(instance != null)
                instance.setReverseRef(this);
            }
            m_index++;
            if (m_index == m_values.length) {
                m_index = 0;
            }
            m_values[m_index] = parent.setConceptPointer(instance);
            m_time[m_index] = time;
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setConcept(Concept instance) {
        checkType(instance);
        return setCurrent((ConceptImpl) instance, System.currentTimeMillis());
    }

    public boolean setConcept(Concept instance, long time) {
        checkType(instance);

        if(!isSet()) return setCurrent((ConceptImpl) instance, time);
        ((ConceptImpl)getParent()).checkSession();
        if(time >= m_time[m_index]) {
            return setCurrent((ConceptImpl) instance, time);
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

    static protected Concept objectToConcept(Class type, Object obj) {
        if(obj == null) {
            return null;
        }
        else if(type.isAssignableFrom(obj.getClass())) {
            return (Concept)obj;
        }
        else {
            throw new ClassCastException();
        }
    }

    public void fillXiNode(XiNode node, boolean changeOnly) {
        if (m_values[m_index] != null) {
            node.appendElement(ExpandedName.makeName(getName())).
                    setAttributeStringValue(ExpandedName.makeName("ref"), String.valueOf(m_values[m_index].getId()));
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
                output.writeLong(m_values[i].getId());
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