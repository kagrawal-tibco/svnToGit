package com.tibco.cep.runtime.model.element.impl.property.history;

import java.io.DataInput;
import java.io.DataOutput;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsDouble;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:41:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDoubleImpl extends PropertyAtomImpl implements PropertyAtomDouble {

    double[] m_values = null;

    public PropertyAtomDoubleImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values = new double[historySize];
            m_values[0] = DEFAULT_VALUE;
        }
    }

    public PropertyAtomDoubleImpl(int historySize, Object owner, double defaultValue) {
        super(historySize, owner);
        m_values = new double[historySize];
        m_values[0] = defaultValue;
        m_time[0] = System.currentTimeMillis();
        setIsSet();
    }

    @Override
    public PropertyAtomDoubleImpl copy(Object newOwner) {
        return _copy(new PropertyAtomDoubleImpl(0, newOwner));
    }

    protected PropertyAtomDoubleImpl _copy(PropertyAtomDoubleImpl ret) {
        super._copy(ret);
        ret.m_values = m_values.clone();
        return ret;
    }

    public Number maxOverTime(long from_no_of_msec_ago) {
        if (!isSet()) return 0.0; // nothing there
        if(from_no_of_msec_ago==0) {  //check all history
            double max = m_values[0];
            for(int i = 1; i < m_values.length && m_time[i] != 0L; i++ ) {
                if(m_values[i] > max)
                    max = m_values[i];
            }
            return max;
        }
        long startTime = System.currentTimeMillis() - from_no_of_msec_ago;
        if(m_time[m_time.length-1]==0L) {  //start from m_index to 0
            double max = Double.MIN_VALUE;
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
            double max = Double.MIN_VALUE;
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
        if (!isSet()) return 0; // nothing there
        if(from_no_of_msec_ago==0) {  //check all history
            double min = m_values[0];
            for(int i = 1; i < m_values.length && m_time[i] != 0L; i++ ) {
                if(m_values[i] < min)
                    min = m_values[i];
            }
            return min;
        }
        long startTime = System.currentTimeMillis() - from_no_of_msec_ago;
        if(m_time[m_time.length-1]==0L) {  //start from m_index to 0
            double min = Double.MAX_VALUE;
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
            double min = Double.MAX_VALUE;
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
        return new Double(m_values[m_index]);
    }

    public Object getPreviousValue() {
        if ((m_index-1)>=0) return new Double(m_values[m_index-1]);
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return new Double(m_values[m_values.length-1]);
        return null; // or exception :\
    }

    public Object getValue(long time) throws PropertyException {
        return new Double(m_values[getIndex(time)]);
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return new Double(m_values[mapIndex(idx)]);
    }

    public boolean setValue(Object obj) {
        if(obj != null) {
            return setDouble(objectToDouble(obj));
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public boolean setValue(Object obj, long time) {
        if(obj != null) {
            return setDouble(objectToDouble(obj), time);
        } else {
            return setCurrent(DEFAULT_VALUE,time);
        }
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsDouble(m_values[m_index]);
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsDouble(),System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Double.valueOf(value).doubleValue(),System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE,System.currentTimeMillis());
        }
    }

    public double getDouble() {
        return m_values[m_index];
    }

    public double getDouble(long time) throws PropertyException {
        return m_values[getIndex(time)];
    }

    public double getDoubleAtIdx(int idx) throws PropertyException {
        return m_values[mapIndex(idx)];
    }

    private boolean setCurrent(double value, long time) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
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

    public boolean setDouble(double value) {
        return setCurrent(value, System.currentTimeMillis());
    }

    public boolean setDouble(double value, long time) {
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
        return new Double(m_values[index]);
    }

    public int getInt() {
        return (int)getDouble();
    }

    public int getInt(long time) throws PropertyException {
        return (int)getDouble(time);
    }

    static protected double objectToDouble(Object obj) {
        if(obj instanceof Double) {
            return ((Double)obj).doubleValue();
        }
        else if(obj instanceof String) {
            return Double.parseDouble((String)obj);
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
                serializer.writeDoubleProperty(m_values, m_time);
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
            deserializer.getDoubleProperty(m_values,m_time);
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    void writeValueBytes(DataOutput output, int howMany) throws Exception {
        for (int i=0; i < howMany;i++) {
            output.writeDouble(m_values[i]);
        }
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    void readValueBytes(DataInput input,int historySize) throws Exception {
        for (int i=0; i<historySize;i++ ) {
            m_values[i]= input.readDouble();
        }
    }

    public double preIncrement() {
        return addAssign(1);
    }

    public double preDecrement() {
        return subAssign(1);
    }

    public double postIncrement() {
        double temp = getDouble();
        preIncrement();
        return temp;
    }

    public double postDecrement() {
        double temp = getDouble();
        preDecrement();
        return temp;
    }

    public double multAssign(double mult) {
        setDouble(getDouble() * mult);
        return getDouble();
    }

    public double divAssign(double div) {
        setDouble(getDouble() / div);
        return getDouble();
    }

    public double modAssign(double mod) {
        setDouble(getDouble() % mod);
        return getDouble();
    }

    public double addAssign(double add) {
        setDouble(getDouble() + add);
        return getDouble();
    }

    public double subAssign(double sub) {
        setDouble(getDouble() - sub);
        return getDouble();
    }
}
