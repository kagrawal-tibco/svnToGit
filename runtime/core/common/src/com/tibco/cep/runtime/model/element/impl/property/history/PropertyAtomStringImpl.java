package com.tibco.cep.runtime.model.element.impl.property.history;

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
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:46:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomStringImpl extends PropertyAtomImpl implements PropertyAtomString {

    String[] m_values = null;

    public PropertyAtomStringImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values = new String[historySize];
            m_values[0] = DEFAULT_VALUE;
        }
    }

    public PropertyAtomStringImpl(int historySize, Object owner, String defaultValue) {
        super(historySize, owner);
        m_values = new String[historySize];
        m_values[0] = defaultValue;
        m_time[0] = System.currentTimeMillis();
        setIsSet();
    }

    @Override
    public PropertyAtomStringImpl copy(Object newOwner) {
        return _copy(new PropertyAtomStringImpl(0, newOwner));
    }

    protected PropertyAtomStringImpl _copy(PropertyAtomStringImpl ret) {
        super._copy(ret);
        ret.m_values = m_values.clone();
        return ret;
    }

    public Object getValue() {
        return m_values[m_index];
    }

    public Object getValue(long time) throws PropertyException {
        return m_values[getIndex(time)];
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return m_values[mapIndex(idx)];
    }

    public Object getPreviousValue() {
        if ((m_index-1)>=0) return m_values[m_index-1];
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return m_values[m_values.length-1];
        return null; // or exception :\
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setString(obj.toString());
        } else {
            return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
        }
    }

    public boolean setValue(Object obj, long time) {
        if (obj != null) {
            return setString(obj.toString(), time);
        } else {
            return setCurrent(DEFAULT_VALUE, time);
        }
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsString(m_values[m_index]);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsString(), System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(value, System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
        }
    }

    private boolean setCurrent(String value, long time) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
            setIsSet();
            m_index = 0;  // making sure
            m_values[m_index] = value;
            m_time[m_index] = time;
            setConceptModified();
            return true;
        }
        else if ((getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) ||
                 (value != null && !value.equals(m_values[m_index])) ||
                 (value == null && m_values[m_index] != null)) {
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

    public boolean setString(String value) {
        return setCurrent(value, System.currentTimeMillis());
    }

    public boolean setString(String value, long time) {
        if(!isSet()) return setCurrent(value, time);
        ((ConceptImpl)getParent()).checkSession();
        if (time >= m_time[m_index]) {
            return setCurrent(value, time);
        } else {
            if (m_index == m_values.length - 1) {
                for (int i = m_values.length - 2; i >= 0; i--) {
                    if (time >= m_time[i]) {
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            ((m_values[i] != null && m_values[i].equals(value)) ||
                             (m_values[i] == null && value == null))) {
                            return false;
                        } else {
                            System.arraycopy(m_time, 1, m_time, 0, i);
                            System.arraycopy(m_values, 1, m_values, 0, i);
                            m_time[i] = time;
                            m_values[i] = value;
                            setConceptModified();
                            return true;
                        }
                    }
                }
            } else if (m_time[0] <= time && time < m_time[m_index]) {
                for (int i = m_index - 1; i >= 0; i--) {
                    if (time >= m_time[i]) {
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            ((m_values[i] != null && m_values[i].equals(value)) ||
                             (m_values[i] == null && value == null))) {
                            return false;
                        } else {
                            System.arraycopy(m_time, i + 1, m_time, i + 2, m_index - i);
                            System.arraycopy(m_values, i + 1, m_values, i + 2, m_index - i);
                            m_time[i + 1] = time;
                            m_values[i + 1] = value;
                            m_index++;
                            setConceptModified();
                            return true;
                        }
                    }
                }
            } else if (m_time[m_index + 1] == 0) {
                //buffer is not full, should put in index 0
                if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) {
                    if((m_values[0] != null && m_values[0].equals(value)) ||
                       (m_values[0] == null && value == null)) {
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
            else if (m_time[m_index + 1] <= time) {
                for (int i = m_values.length - 1; i > m_index; i--) {
                    if (time >= m_time[i]) {
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            ((m_values[i] != null && m_values[i].equals(value)) ||
                             (m_values[i] == null && value == null))) {
                            return false;
                        } else {
                            System.arraycopy(m_time, m_index + 2, m_time, m_index + 1, i - m_index - 1);
                            System.arraycopy(m_values, m_index + 2, m_values, m_index + 1, i - m_index - 1);
                            m_time[i] = time;
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
        return m_values[index];
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        try {
            super.serialize(serializer, order);
            if (isSet()) {
                serializer.writeStringProperty(m_values, m_time);
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
            deserializer.getStringProperty(m_values,m_time);
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
                output.writeUTF(m_values[i]);
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
        for (int i=0; i<historySize;i++ ) {
            boolean isSet= input.readBoolean();
            if (isSet) {
                m_values[i]= input.readUTF();
            }
        }
    }


//    static public class SerializerHelper extends PropertyAtomImpl.SerializerHelper {
//
//        public static String[] getValues(PropertyAtomStringImpl pa) { return pa.m_values; };
//
//        public static void setValues(PropertyAtomImpl pa, String[] _values) {
//            ((PropertyAtomStringImpl)pa).m_values = _values;
//        }
//    }

    public String addAssign(String add) {
        setString(getString() + add);
        return getString();
    }
}