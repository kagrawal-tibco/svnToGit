package com.tibco.cep.runtime.model.element.impl.property;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:17:35 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractPropertyArray extends PropertyImpl implements PropertyArray {

    protected ConceptImpl          m_parent;
    protected AbstractPropertyAtom[] m_properties = null;
    protected int                  m_len;

    protected AbstractPropertyArray(ConceptImpl parent, int length) {
        m_parent     = parent;
        m_len = length;
        init();
    }

    protected void init() {
        if (m_len > 0) {
            m_properties = new AbstractPropertyAtom[m_len];
            for (int ii = 0; ii < m_len; ii++) {
                m_properties[ii] = newEmptyAtom(this);
            }
        }
    }

    public int getHistorySize() {
        return 0;
    }

    abstract public AbstractPropertyArray copy(Object newOwner);

    public AbstractPropertyArray _copy(AbstractPropertyArray ret) {
        ret.m_status = m_status;
        if(m_len > 0) {
            ret.m_len = m_len;
            ret.m_properties = new AbstractPropertyAtom[m_len];
            for(int ii = 0; ii < m_len; ii++) {
                if(m_properties[ii] != null) {
                    ret.m_properties[ii] = m_properties[ii].copy(ret);
                } else {
                    ret.m_properties[ii] = newEmptyAtom(ret);
                }
            }
        }
        return ret;
    }

    abstract protected AbstractPropertyAtom newEmptyAtom(AbstractPropertyArray newOwner);

    public Concept getParent() {
        return m_parent;
    }

    protected Object getOwner() {
        return m_parent;
    }

    public int length() {
        return m_len;
    }

    public void clear() {
        checkSession();
        if(m_len == 0) return;
        m_properties = null;
        m_len = 0;
        setConceptModified();
    }

    protected void pre_addToEnd() {
        pre_add(m_len);
    }

    protected void pre_add(int index) {
        checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        if(m_len ==0) {
            m_properties = new AbstractPropertyAtom[2];
        }
        else if (m_len == m_properties.length) {
            int newlen = (m_properties.length * 3) / 2 + 1;
            AbstractPropertyAtom[] newArr = new AbstractPropertyAtom[newlen];
            //copy first part
            System.arraycopy(m_properties, 0, newArr, 0, index);
            //copy 2nd part
            int numShift = m_len - index;
            System.arraycopy(m_properties, index, newArr, index + 1, numShift);
            m_properties = newArr;
        }
        else { //m_len < m_properties.length
            int numShift = m_len - index;
            System.arraycopy(m_properties, index, m_properties, index + 1, numShift);
        }
    }

    public PropertyAtom remove(int index) {
        checkSession();
        if(0 <= index && index < m_len) {
            PropertyAtom p = m_properties[index];
            if( m_len == 1) {
                m_properties = null;
                m_len = 0;
                setConceptModified();
                return p;
            }
            else {
                int numMoved = m_len - index - 1;
                System.arraycopy(m_properties, index+1, m_properties, index, numMoved);
                m_len--;
                m_properties[m_len] = null;
                setConceptModified();
                return p;
            }
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    abstract public PropertyAtom add();

    public PropertyAtom get(int index) {
        if(0 <= index && index < m_len) {
            return m_properties[index];
        }
        else {
            throw new IndexOutOfBoundsException();
        }

    }

    public PropertyAtom[] toArray() {
        if(m_len == 0) {
            return new PropertyAtom[0];
        }
        else {
            PropertyAtom[] newArr = new PropertyAtom[m_len];
            System.arraycopy(m_properties, 0, newArr, 0, m_len);
            return newArr;
        }
    }

    public PropertyAtom[] toArray(PropertyAtom[] a) {
        if(m_len == 0 && a.length > 0) {
            a[0] = null;
        }
        if (a.length < m_len)
            a = (PropertyAtom[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), m_len);
        if(m_properties == null) {
            a = new PropertyAtom[0];
        } else {
            System.arraycopy(m_properties, 0, a, 0, m_len);

            if (a.length > m_len)
                a[m_len] = null;

        }

        return a;

    }

    public String getString() {
        StringBuffer sb = new StringBuffer();
        if( m_len == 0) {
            sb.append("[ 0 ] { ");
        }
        else {
            sb.append("[ " + m_len + " ] { ");
            for(int ii=0; ii < m_len; ii++) {
                PropertyAtom pa = m_properties[ii];
                sb.append(pa.getString());
                if(ii < m_len - 1)
                    sb.append(", ");
            }
        }
        sb.append(" }");
        return sb.toString();

    }


    public void fillXiNode(XiNode node, boolean changeOnly) {
        for (int i = 0; i < m_len; i++) {
            PropertyAtom pa = m_properties[i];
            ((AbstractPropertyAtom)pa).fillXiNode(node, changeOnly);
        }
    }

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
        serializer.startPropertyArray(this.getName(), order, m_len);
        for (int i = 0; i < m_len; i++) {
            PropertyAtom pa=m_properties[i];
            serializer.startPropertyArrayElement(i);
            pa.serialize(serializer, order);
            serializer.endPropertyArrayElement();
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize_nullprops(ConceptDeserializer deserializer, int order) {
        m_len= deserializer.startPropertyArray(this.getName(), order);
        init();
        for (int i=0; i < m_len; i++) {
            deserializer.startPropertyArrayElement(i);
            m_properties[i].deserialize(deserializer, order);
            deserializer.endPropertyArrayElement();
        }
    }
    /**
     *
     * @param serializer
     */
    public void serialize_nonullprops(ConceptSerializer serializer, int order) {
        //serializer.startProperty(getName(), order, m_len > 0);
        if (m_len > 0) {
            serializer.startPropertyArray(this.getName(), order, m_len);
            for (int i = 0; i < m_len; i++) {
                PropertyAtom pa=m_properties[i];
                boolean isSet= pa.isSet();
                serializer.startPropertyArrayElement(i, isSet);
                if (isSet)
                    pa.serialize(serializer, order);
                serializer.endPropertyArrayElement();
            }
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize_nonullprops(ConceptDeserializer deserializer, int order) {
        m_len= deserializer.startPropertyArray(this.getName(), order);
        init();
        for (int i=0; i < m_len; i++) {
            boolean isSet=deserializer.startPropertyArrayElement(i);
            if (isSet)
                m_properties[i].deserialize(deserializer, order);
            else
                m_properties[i].clearIsSet();
            deserializer.endPropertyArrayElement();
        }
    }
    
    protected void checkSession() {
    	((ConceptImpl)getParent()).checkSession();
    }
}
