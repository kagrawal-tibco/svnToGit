package com.tibco.cep.runtime.model.element.impl.property.history;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsLong;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:44:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomLongImpl extends PropertyAtomImpl implements PropertyAtomLong {

    long[] m_values = null;

    public PropertyAtomLongImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values = new long[historySize];
            m_values[0] = DEFAULT_VALUE;
        }
    }

    public PropertyAtomLongImpl(int historySize, Object owner, long defaultValue) {
        super(historySize, owner);
        m_values = new long[historySize];
        m_values[0] = defaultValue;
        m_time[0] = System.currentTimeMillis();
        setIsSet();
    }

    @Override
    public PropertyAtomLongImpl copy(Object newOwner) {
        return _copy(new PropertyAtomLongImpl(0, newOwner));
    }

    protected PropertyAtomLongImpl _copy(PropertyAtomLongImpl ret) {
        super._copy(ret);
        ret.m_values = m_values.clone();
        return ret;
    }

    public Number maxOverTime(long from_no_of_msec_ago) {
        if (!isSet()) return 0L; // nothing there
        if(from_no_of_msec_ago==0) {  //check all history
            long max = m_values[0];
            for(int i = 1; i < m_values.length && m_time[i] != 0L; i++ ) {
                if(m_values[i] > max)
                    max = m_values[i];
            }
            return max;
        }
        long startTime = System.currentTimeMillis() - from_no_of_msec_ago;
        if(m_time[m_time.length-1]==0L) {  //start from m_index to 0
            long max = Long.MIN_VALUE;
            int i = m_index;
            while (i >= 0) {
                if(m_values[i] > max)
                   max = m_values[i];
                if(startTime >= m_time[i])
                    return max;
                i--;
            }
            return max;            
        }
        else { //need to rotate
            long max = Long.MIN_VALUE;
            int i = m_index;
            while (i >= 0) {
                if(m_values[i] > max)
                   max = m_values[i];
                if(startTime >= m_time[i])
                    return max;
                i--;
            }
            i = m_time.length-1;
            while (i > m_index) {
                if(m_values[i] > max)
                   max = m_values[i];
                if(startTime >= m_time[i])
                    return max;
                i--;
            }
            return max;
        }
    }

    public Number minOverTime(long from_no_of_msec_ago) {
        if (!isSet()) return 0L; // nothing there
        if(from_no_of_msec_ago==0) {  //check all history
            long min = m_values[0];
            for(int i = 1; i < m_values.length && m_time[i] != 0L; i++ ) {
                if(m_values[i] < min)
                    min = m_values[i];
            }
            return min;
        }
        long startTime = System.currentTimeMillis() - from_no_of_msec_ago;
        if(m_time[m_time.length-1]==0L) {  //start from m_index to 0
            long min = Long.MAX_VALUE;
            int i = m_index;
            while (i >= 0) {
                if(m_values[i] < min)
                   min = m_values[i];
                if(startTime >= m_time[i])
                    return min;
                i--;
            }
            return min;
        }
        else { //need to rotate
            long min = Long.MAX_VALUE;
            int i = m_index;
            while (i >= 0) {
                if(m_values[i] < min)
                   min = m_values[i];
                if(startTime >= m_time[i])
                    return min;
                i--;
            }
            i = m_time.length-1;
            while (i > m_index) {
                if(m_values[i] < min)
                   min = m_values[i];
                if(startTime >= m_time[i])
                    return min;
                i--;
            }
            return min;
        }
    }


    public Object getValue() {
        return new Long(m_values[m_index]);
    }

    public Object getPreviousValue(){
        if ((m_index-1)>=0) return new Long(m_values[m_index-1]);
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return new Long(m_values[m_values.length-1]);
        return null; // or exception :\
    }

    public Object getValue(long time) throws PropertyException {
        return new Long(m_values[getIndex(time)]);
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return new Long(m_values[mapIndex(idx)]);
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setLong(objectToLong(obj));
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public boolean setValue(Object obj, long time) {
        if (obj != null) {
            return setLong(objectToLong(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE, time);
        }
    }

    public long getLong() {
        return m_values[m_index];
    }

    public long getLong(long time) throws PropertyException {
        return m_values[getIndex(time)];
    }

    public long getLongAtIdx(int idx) throws PropertyException {
        return m_values[mapIndex(idx)];
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsLong(m_values[m_index]);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsLong(),System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Long.valueOf(value).longValue(),System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    private boolean setCurrent(long value, long time) {
        ((ConceptImpl)getParent()).checkSession();
        if(!isSet()) {
            setIsSet();
            m_index = 0;  // making sure
            m_values[m_index] = value;
            m_time[m_index] = time;
            setConceptModified();
            return true;
        }
        else if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_values[m_index]) {
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

    public boolean setLong(long value) {
        return setCurrent(value, System.currentTimeMillis());
    }

    public boolean setLong(long value, long time) {
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

    public double getDouble() {
        return (double)getLong();
    }

    public double getDouble(long time) throws PropertyException {
        return (double)getLong(time);
    }

    protected Object valueToObject(int index) {
        return new Long(m_values[index]);
    }

    static protected long objectToLong(Object obj) {
        if(obj instanceof Integer) {
            return ((Integer)obj).longValue();
        }
        else if(obj instanceof Long) {
            return ((Long)obj).longValue();
        }
        else if(obj instanceof String) {
            return Long.parseLong((String)obj);
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
        try {
            super.serialize(serializer, order);
            if (isSet()) {
                serializer.writeLongProperty(m_values, m_time);
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
            deserializer.getLongProperty(m_values,m_time);
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    void writeValueBytes(DataOutput output,int howMany) throws Exception {
        for (int i=0; i < howMany;i++) {
            output.writeLong(m_values[i]);
        }
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    void readValueBytes(DataInput input,int historySize) throws Exception {
        for (int i=0; i<historySize;i++ ) {
            m_values[i]= input.readLong();
        }
    }

    public long preIncrement() {
        return addAssign(1);
    }

    public long preDecrement() {
        return subAssign(1);
    }

    public long postIncrement() {
        long temp = getLong();
        preIncrement();
        return temp;
    }

    public long postDecrement() {
        long temp = getLong();
        preDecrement();
        return temp;
    }

    public long multAssign(long mult) {
        setLong(getLong() * mult);
        return getLong();
    }

    public long divAssign(long div) {
        setLong(getLong() / div);
        return getLong();
    }

    public long modAssign(long mod) {
        setLong(getLong() % mod);
        return getLong();
    }

    public long addAssign(long add) {
        setLong(getLong() + add);
        return getLong();
    }

    public long subAssign(long sub) {
        setLong(getLong() - sub);
        return getLong();
    }
}

