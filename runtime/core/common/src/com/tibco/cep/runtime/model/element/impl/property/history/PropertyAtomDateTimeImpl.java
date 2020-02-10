package com.tibco.cep.runtime.model.element.impl.property.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsString;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 25, 2006
 * Time: 3:40:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDateTimeImpl extends PropertyAtomImpl implements PropertyAtomDateTime {

    long[]   m_values = null;
    String[] m_timeZones = null;

    //todo - can we move to XML SDK
    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    static public TypeConverter xsd2java_dt_conv = registry.nativeToForeign(XsDateTime.class, GregorianCalendar.class);
    static public TypeRenderer  java2xsd_dt_conv = registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);

    public PropertyAtomDateTimeImpl(int historySize, Object owner) {
        super(historySize, owner);
        if(historySize > 0) {
            m_values     = new long[historySize];
            m_timeZones  = new String[historySize];
            m_values[0]  = -1;
            m_timeZones[0]  = null;
        }
    }

    public PropertyAtomDateTimeImpl(int historySize, Object owner, Calendar defaultValue) {
        super(historySize, owner);
        m_values     = new long[historySize];
        m_timeZones  = new String[historySize];
        updateInternalValue(defaultValue, System.currentTimeMillis(), 0);
        setIsSet();
    }

    @Override
    public PropertyAtomDateTimeImpl copy(Object newOwner) {
        return _copy(new PropertyAtomDateTimeImpl(0, newOwner));
    }

    protected PropertyAtomDateTimeImpl _copy(PropertyAtomDateTimeImpl ret) {
        super._copy(ret);
        ret.m_values = m_values.clone();
        ret.m_timeZones  = m_timeZones.clone();
        return ret;
    }

    private void updateInternalValue(Calendar c, long time, int index) {
        if(c == null) {
            m_values[index]    = -1;
            m_timeZones[index] = null;
        }
        else {
            m_values[index]    = c.getTimeInMillis();
            m_timeZones[index] = c.getTimeZone().getID();
        }
        m_time[index]      = time;
    }

    private void updateInternalValue(Calendar c, int index) {
        if(c == null) {
            m_values[index]    = -1;
            m_timeZones[index] = null;
        }
        else {
            m_values[index]    = c.getTimeInMillis();
            m_timeZones[index] = c.getTimeZone().getID();
        }
    }

    private Calendar getInternalValue(int index) {
        if(m_timeZones[index] == null) {
            return null;
        }
        else {
            Calendar c = new GregorianCalendar(TimeZone.getTimeZone(m_timeZones[index]));
            c.setTimeInMillis(m_values[index]);
            return c;
        }
    }

    public Object getValue() {
        return getInternalValue(m_index);
    }

    public Object getPreviousValue() {
        if ((m_index-1)>=0) return getInternalValue(m_index-1);
        if ((m_index-1)<0 && m_time[m_time.length-1]!=0L) return getInternalValue(m_values.length-1);
        return null; // or exception :\
    }

    public Object getValue(long time) throws PropertyException {
        return getInternalValue(getIndex(time));
    }

    public Object getValueAtIdx(int idx) throws PropertyException {
        return getInternalValue(mapIndex(idx));
    }

    public boolean setValue(Object obj) {
        return setDateTime(objectToDateTime(obj));
    }

    public boolean setValue(Object obj, long time) {
        return setDateTime(objectToDateTime(obj), time);
    }

    public String getString() {
        Calendar c = getDateTime();
        if(c != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Format.STANDARD_DATE_FORMAT);  //todo - should we use XML format?
            sdf.setTimeZone(c.getTimeZone());
            return sdf.format(c.getTime());
        }
        return null;
    }

    public String getString(long time) throws PropertyException {
        Calendar c = getDateTime(time);
        if(c != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Format.STANDARD_DATE_FORMAT);  //todo - should we use XML format?
            sdf.setTimeZone(c.getTimeZone());
            return sdf.format(c.getTime());
            //temp = temp.substring(0, 10) + 'T' + temp.substring(10, 21) + ":" + temp.substring(21) ;
        }
        return null;
    }

    public String getStringAtIdx(int idx) throws PropertyException {
        Calendar c = getDateTimeAtIdx(idx);
        if(c != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Format.STANDARD_DATE_FORMAT);  //todo - should we use XML format?
            sdf.setTimeZone(c.getTimeZone());
            return sdf.format(c.getTime());
            //temp = temp.substring(0, 10) + 'T' + temp.substring(10, 21) + ":" + temp.substring(21) ;
        }
        return null;
    }

    public Calendar getDateTime() {
        return getInternalValue(m_index);
    }

    public Calendar getDateTime(long time) throws PropertyException {
        return getInternalValue(getIndex(time));
    }

    public Calendar getDateTimeAtIdx(int idx) throws PropertyException {
        return getInternalValue(mapIndex(idx));
    }

    public XmlTypedValue getXMLTypedValue() {
        try {
            return XmlTypedValueConversionSupport.convertToXsDateTime(getInternalValue(m_index));
        } catch (Exception e) {
            // Should never reach here as we are handing over a avalid java date object
            e.printStackTrace();
            return null;
        }
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            if (value instanceof XsDateTime) {
                Calendar cal=(Calendar) xsd2java_dt_conv.convertSimpleType((XsDateTime) value);
                return setCurrent(cal, System.currentTimeMillis());
            } else if (value instanceof XsString) {
                Calendar cal=(Calendar) xsd2java_dt_conv.convertSimpleType(((XsString) value).castAsString());
                return setCurrent(cal, System.currentTimeMillis());
            } else if ( value.getAtom(0) != null){
                XsDateTime dateTime = value.getAtom(0).castAsDateTime();
                if (dateTime != null) {
                    Calendar cal=(Calendar) xsd2java_dt_conv.convertSimpleType(dateTime);
                    return setCurrent(cal, System.currentTimeMillis());

                }
                else {
                    return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
                }
            }

        } else {
            return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
        }
        return false;
    }

    public boolean setValue(String value) throws Exception {
        // Should be a date in gregorian date as specified by the XPath Specification
        if (value != null) {
            Calendar cal=(Calendar) xsd2java_dt_conv.convertSimpleType(value);
            return setCurrent(cal, System.currentTimeMillis());
        } else {
            return setCurrent(DEFAULT_VALUE, System.currentTimeMillis());
        }
    }

    private boolean setCurrent(Calendar value, long time) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
            setIsSet();
            m_index = 0;  // making sure
            updateInternalValue(value, time, m_index);
            setConceptModified();
            return true;
        }
        else if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) {
            m_index++;
            if (m_index == m_values.length) {
                m_index = 0;
            }
            updateInternalValue(value, time, m_index);
            setConceptModified();
            return true;
        }
        Calendar c = getInternalValue(m_index);
        if ((value != null && !value.equals(c)) ||
            (value == null && c != null)) {
            m_index++;
            if (m_index == m_values.length) {
                m_index = 0;
            }
            updateInternalValue(value, time, m_index);
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setDateTime(Calendar value) {
        return setCurrent(value, System.currentTimeMillis());
    }

    public boolean setDateTime(Calendar value, long time) {
        if(!isSet()) return setCurrent(value, time);
        ((ConceptImpl)getParent()).checkSession();
        if(time >= m_time[m_index]) {
            return setCurrent(value, time);
        }
        else {
            if(m_index == m_values.length-1) {
                for(int i = m_values.length-2; i >= 0; i --) {
                    if(time >= m_time[i]) {
                        Calendar c = getInternalValue(i);
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            ((c != null && c.equals(value)) ||
                             (c == null && value == null))) {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, 1, m_time, 0, i);
                            System.arraycopy(m_timeZones, 1, m_timeZones, 0, i);
                            System.arraycopy(m_values, 1, m_values, 0, i);
                            updateInternalValue(value, time, i);
                            setConceptModified();
                            return true;
                        }
                    }
                }
            }
            else if(m_time[0] <= time && time < m_time[m_index]) {
                for(int i = m_index-1; i >= 0; i--) {
                    if(time >= m_time[i]) {
                        Calendar c = getInternalValue(i);
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            ((c != null && c.equals(value)) ||
                             (c == null && value == null))) {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, i+1, m_time, i+2, m_index - i);
                            System.arraycopy(m_timeZones, i+1, m_timeZones, i+2, m_index - i);
                            System.arraycopy(m_values, i+1, m_values, i+2, m_index - i);
                            updateInternalValue(value, time, i+1);
                            m_index++;
                            setConceptModified();
                            return true;
                        }
                    }
                }
            } else if (m_time[m_index + 1] == 0) {
                //buffer is not full, should put in index 0
                if(getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) {
                    Calendar c = getInternalValue(0);
                    if((c != null && c.equals(value)) ||
                       (c == null && value == null)) {
                        //update the time only
                        m_time[0] = time;
                    }
                    else {
                        //update both
                        System.arraycopy(m_time, 0, m_time, 1, m_index +1);
                        System.arraycopy(m_timeZones, 0, m_timeZones, 1, m_index +1);
                        System.arraycopy(m_values, 0, m_values, 1, m_index +1);
                        updateInternalValue(value, time, 0);
                        m_index++;
                    }
                }
                else { //all values
                    System.arraycopy(m_time, 0, m_time, 1, m_index +1);
                    System.arraycopy(m_timeZones, 0, m_timeZones, 1, m_index +1);
                    System.arraycopy(m_values, 0, m_values, 1, m_index +1);
                    updateInternalValue(value, time, 0);
                    m_index++;
                }
                setConceptModified();
                return true;
            }
            else if (m_time[m_index+1] <= time) {
                for(int i = m_values.length-1; i > m_index; i-- ) {
                    if(time >= m_time[i]) {
                        Calendar c = getInternalValue(i);
                        if ((getHistoryPolicy() == Property.HISTORY_POLICY_CHANGES_ONLY) &&
                            ((c!= null && c.equals(value)) ||
                             (c == null && value == null))) {
                            return false;
                        }
                        else {
                            System.arraycopy(m_time, m_index+2, m_time, m_index+1, i- m_index -1);
                            System.arraycopy(m_timeZones, m_index+2, m_timeZones, m_index+1, i- m_index -1);
                            System.arraycopy(m_values, m_index+2, m_values, m_index+1, i- m_index -1);
                            updateInternalValue(value, time, i);
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
        return getInternalValue(index);
    }

    static protected Calendar objectToDateTime(Object obj) {
        return PropertyAtomDateTimeSimple.objectToDateTime(obj);
    }

    protected Calendar[] getInternalValues() {
        Calendar[] cals = new Calendar[getHistorySize()];

        for (int i = 0; i < getHistorySize(); i++)
            cals[i] = getInternalValue(i);
        return cals;
    }

    protected void updateInternalValues(Calendar [] cals ) {
        for(int i =0; i < cals.length; i++) {
            updateInternalValue(cals[i], i);
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
                serializer.writeDateTimeProperty(m_values, m_timeZones, m_time);
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
            deserializer.getDateTimeProperty(m_values, m_timeZones,m_time);
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    void writeValueBytes(DataOutput output,int howMany) throws Exception {
        for(int i=0; i < howMany; i++) {
            output.writeLong(m_values[i]);
            if(m_timeZones[i] != null) {
                output.writeBoolean(true);
                //dout.writeUTF(((XsDateTime)PropertyAtomDateTimeImpl.java2xsd_dt_conv.convertToTypedValue(values[i])).castAsString());
                output.writeUTF(m_timeZones[i]);
            } else
                output.writeBoolean(false);
        }
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    void readValueBytes(DataInput input,int historySize) throws Exception {

        for(int i=0; i < historySize; i++) {
            m_values[i] = input.readLong();
            if(input.readBoolean())
                //values[i] = (Calendar) PropertyAtomDateTimeImpl.xsd2java_dt_conv.convertSimpleType(din.readUTF());
                m_timeZones[i] = input.readUTF();
            else
                //values[i] = null;
                m_timeZones[i] = null;
        }
    }

}
