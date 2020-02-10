package com.tibco.cep.runtime.model.element.impl.property.history;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 25, 2006
 * Time: 3:17:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomBooleanImpl extends PropertyAtomImpl implements PropertyAtomBoolean {

    protected boolean[] m_values = null;

    public PropertyAtomBooleanImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values = new boolean[historySize];
            m_values[0] = DEFAULT_VALUE;
        }
    }

   public PropertyAtomBooleanImpl(int historySize, Object owner, boolean defaultValue) {
       super(historySize, owner);
       m_values = new boolean[historySize];
       m_values[0] = defaultValue;
       m_time[0] = System.currentTimeMillis();
       setIsSet();
    }

    @Override
    public PropertyAtomBooleanImpl copy(Object newOwner) {
        return _copy(new PropertyAtomBooleanImpl(0, newOwner));
    }

    protected PropertyAtomBooleanImpl _copy(PropertyAtomBooleanImpl ret) {
        super._copy(ret);
        ret.m_values = m_values.clone();
        return ret;
    }

    public Object getValue() {
        return m_values[m_index]? Boolean.TRUE:Boolean.FALSE;
    }

    public Object getPreviousValue() {
        if ((m_index-1)>=0) return m_values[m_index-1]? Boolean.TRUE:Boolean.FALSE;
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return m_values[m_values.length-1]? Boolean.TRUE:Boolean.FALSE;
        return null; // or exception :\
    }

    public Object getValue(long time) throws PropertyException {
        return m_values[getIndex(time)]? Boolean.TRUE:Boolean.FALSE;
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return m_values[mapIndex(idx)]? Boolean.TRUE:Boolean.FALSE;
    }

    public boolean setValue(Object obj) {
        if(obj != null) {
            return setBoolean(objectToBoolean(obj));
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public boolean setValue(Object obj, long time) {
        if(obj != null) {
            return setBoolean(objectToBoolean(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE,time);
        }
    }

    public XmlTypedValue getXMLTypedValue() {
        return m_values[m_index] ? XsBoolean.TRUE: XsBoolean.FALSE;
    }

    public boolean setValue(XmlTypedValue value) throws Exception{
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsBoolean(), System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
        }
    }

    public boolean setValue(String value) {
        if (value != null) {
            return setCurrent(valueOf(value),System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public boolean getBoolean() {
        return m_values[m_index];
    }

    public boolean getBoolean(long time) throws PropertyException {
        return m_values[getIndex(time)];
    }

    public boolean getBooleanAtIdx(int idx) throws PropertyException {
        return m_values[mapIndex(idx)];
    }

    private boolean setCurrent(boolean value, long time) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
            setIsSet();
            m_index = 0;  // making sure
            m_values[m_index] = value;
            m_time[m_index] = time;
            setConceptModified();
            return true;
        }
        else if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_values[m_index]) {
            m_index++;
            if (m_index == m_values.length) {
                m_index = 0;
            }
            m_values[m_index] = value;
            m_time[m_index] = time;
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setBoolean(boolean value) {
        return setCurrent(value, System.currentTimeMillis());
    }

    public boolean setBoolean(boolean value, long time) {
        if(!isSet()) return setCurrent(value, time);
        ((ConceptImpl)getParent()).checkSession();
        if(time >= m_time[m_index]) {
            return setCurrent(value, time);
        }
        else {
            if(m_index == m_values.length-1) {
                for(int i = m_values.length-2; i >= 0; i --) {
                    if(time >= m_time[i]) {
                        if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY && m_values[i] == value) {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, 1, m_time, 0, i);
                            System.arraycopy(m_values, 1, m_values, 0, i);
                            m_time[i]   = time;
                            m_values[i] = value;
                            setConceptModified();
                            return true;
                        }
                    }
                }
            }
            else if(m_time[0] <= time && time < m_time[m_index]) {
                for(int i = m_index-1; i >= 0; i--) {
                    if(time >= m_time[i]) {
                        if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY && m_values[i] == value) {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, i+1, m_time, i+2, m_index - i);
                            System.arraycopy(m_values, i+1, m_values, i+2, m_index - i);
                            m_time[i+1]   = time;
                            m_values[i+1] = value;
                            m_index++;
                            setConceptModified();
                            return true;
                        }
                    }
                }
            } else if (m_time[m_index + 1] == 0) {
                //buffer is not full, should put in index 0
                if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) {
                    if(m_values[0] == value) {
                        //update the time only
                        m_time[0] = time;
                    }
                    else {
                        //update both
                        System.arraycopy(m_time, 0, m_time, 1, m_index +1);
                        System.arraycopy(m_values, 0, m_values, 1, m_index +1);
                        m_time[0] = time;
                        m_values[0] = value;
                        m_index++;
                    }
                }
                else { //all values
                    System.arraycopy(m_time, 0, m_time, 1, m_index +1);
                    System.arraycopy(m_values, 0, m_values, 1, m_index +1);
                    m_time[0] = time;
                    m_values[0] = value;
                    m_index++;
                }
                setConceptModified();
                return true;
            }
            else if (m_time[m_index+1] <= time) {
                for(int i = m_values.length-1; i > m_index; i-- ) {
                    if(time >= m_time[i]) {
                        if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY && m_values[i] == value) {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, m_index+2, m_time, m_index+1, i- m_index -1);
                            System.arraycopy(m_values, m_index+2, m_values, m_index+1, i- m_index -1);
                            m_time[i]   = time;
                            m_values[i] = value;
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
        return m_values[index]? Boolean.TRUE:Boolean.FALSE;
    }

    static protected boolean objectToBoolean(Object obj) {
        if(obj instanceof Boolean) {
            return ((Boolean)obj).booleanValue();
        }
        else if(obj instanceof String) {
            return valueOf((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    static public boolean valueOf(String value) {
        if(value == null) return false;
        String v = value.trim();
        if(v.equalsIgnoreCase("TRUE")) return true;
        if(v.equalsIgnoreCase("1")) return true;
        if(v.equalsIgnoreCase("T")) return true;
        return false;
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        try {
            super.serialize(serializer, order);
            if (isSet()) {
                serializer.writeBooleanProperty(m_values, m_time);
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
            deserializer.getBooleanProperty(m_values, m_time);
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    void writeValueBytes(DataOutput output, int howMany) throws Exception {
        for (int i=0; i < howMany;i++) {
            output.writeBoolean(m_values[i]);
        }
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    void readValueBytes(DataInput input,int historySize) throws Exception {
        for (int i=0; i<historySize;i++ ) {
            m_values[i]= input.readBoolean();
        }
    }
}
