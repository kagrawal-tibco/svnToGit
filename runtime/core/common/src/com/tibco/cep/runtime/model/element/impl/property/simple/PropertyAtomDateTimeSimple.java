package com.tibco.cep.runtime.model.element.impl.property.simple;

import java.io.DataInput;
import java.io.DataOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.DateTimeTupleImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDateTimeImpl;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.TimeZoneSupport;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDayTimeDuration;
import com.tibco.xml.data.primitive.values.XsString;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:51:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDateTimeSimple extends PropertyAtomSimple implements PropertyAtomDateTime {

    long   m_value;
    String m_timeZone;

    public PropertyAtomDateTimeSimple(Object owner) {
        super(owner);
        m_value = -1;
    }

    public PropertyAtomDateTimeSimple(Object owner, Calendar defaultValue) {
        super(owner);
        updateInternalValue(defaultValue);
        setIsSet();
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomDateTimeSimple(newOwner));
    }

    protected PropertyAtomDateTimeSimple _copy(PropertyAtomDateTimeSimple ret) {
        super._copy(ret);
        ret.m_value = m_value;
        ret.m_timeZone = m_timeZone;
        return ret;
    }


    private void updateInternalValue(Calendar c) {
        if(c == null) {
            m_value = -1;
            m_timeZone = null;
        }
        else {
            m_value = c.getTimeInMillis();
            m_timeZone = c.getTimeZone().getID();
        }
    }

    private Calendar getInternalValue() {
        if(m_timeZone == null) {
            return null;
        }
        else {
            Calendar c = new GregorianCalendar(TimeZone.getTimeZone(m_timeZone));
            c.setTimeInMillis(m_value);
            return c;
        }
    }

    public Object getValue() {
        return getInternalValue();
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
        }
        return null;
    }

    public String getStringAtIdx(int idx) throws PropertyException {
        Calendar c = getDateTimeAtIdx(idx);
        if(c != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Format.STANDARD_DATE_FORMAT);  //todo - should we use XML format?
            sdf.setTimeZone(c.getTimeZone());
            return sdf.format(c.getTime());
        }
        return null;
    }

    public Calendar getDateTime() {
        return getInternalValue();
    }

    public Calendar getDateTime(long time) throws PropertyException {
        return getInternalValue();
    }

    public Calendar getDateTimeAtIdx(int idx) throws PropertyException {
        return getInternalValue();
    }

    public XmlTypedValue getXMLTypedValue() {
        try {
        	//the milliseconds was not considered so commented this call 
        	//have added private method to handle milliseconds.
        	//more details - JIRA BE-9991
        	//return XmlTypedValueConversionSupport.convertToXsDateTime(getInternalValue());
            return convertToXsDateTime(getInternalValue());
        } catch (Exception e) {
            // Should never reach here as we are handing over a avalid java date object
            e.printStackTrace();
            return null;
        }
    }
    
    //to handle milliseconds - JIRA BE-9991
    private XsDateTime convertToXsDateTime(Object value) throws XmlAtomicValueParseException {
        XsDateTime retVal = null;
        if(value instanceof GregorianCalendar) {
            GregorianCalendar gc = (GregorianCalendar)value;
            XsDayTimeDuration dtd =
                    TimeZoneSupport.getDayTimeDuration(gc.getTimeZone(), gc.getTime());
            retVal = new XsDateTime(gc.getTimeInMillis(), dtd);
        } else {
            throw new IllegalArgumentException();
        }
        return retVal;
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            if (value instanceof XsDateTime) {
                Calendar cal=(Calendar) PropertyAtomDateTimeImpl.xsd2java_dt_conv.convertSimpleType((XsDateTime) value);
                return setCurrent(cal);
            } else if (value instanceof XsString) {
                Calendar cal=(Calendar) PropertyAtomDateTimeImpl.xsd2java_dt_conv.convertSimpleType(((XsString) value).castAsString());
                return setCurrent(cal);
            } else if ( value.getAtom(0) != null){
                XsDateTime dateTime = value.getAtom(0).castAsDateTime();
                if (dateTime != null) {
                    Calendar cal=(Calendar) PropertyAtomDateTimeImpl.xsd2java_dt_conv.convertSimpleType(dateTime);
                    return setCurrent(cal);
                }
                else {
                    return setCurrent(DEFAULT_VALUE);
                }
            }
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
        return false;
    }

    public boolean setValue(String value) throws Exception {
        // Should be a date in gregorian date as specified by the XPath Specification
        if (value != null) {
            Calendar cal=(Calendar) PropertyAtomDateTimeImpl.xsd2java_dt_conv.convertSimpleType(value);
            return setCurrent(cal);
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    private boolean setCurrent(Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        if (!isSet()) {
            setIsSet();
            updateInternalValue(value);
            setConceptModified();
            return true;
        }
        Calendar current = getInternalValue();
        if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES ||
                 (value != null && !value.equals(current)) ||
                 (value == null && current != null)) {
            updateInternalValue(value);
            setConceptModified();
            return true;
        }
        return false;
    }

    public boolean setDateTime(Calendar value) {
        return setCurrent(value);
    }

    public boolean setDateTime(Calendar value, long time) {
        return setCurrent(value);
    }

    protected Object valueToObject(int index) {
        return getInternalValue();
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeDateTimeProperty (new DateTimeTupleImpl(m_value, m_timeZone));
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            DateTimeTuple tuple= deserializer.getDateTimeProperty();
            m_value=tuple.getTime();
            m_timeZone= tuple.getTimeZone();
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        if (m_timeZone != null) {
            output.writeBoolean(true);
            output.writeLong(m_value);
            output.writeUTF(m_timeZone);
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
            m_value=input.readLong();
            m_timeZone=input.readUTF();
        }

    }

    public static Calendar objectToDateTime(Object obj) {
        if(obj == null) {
            return null;
        }
        else if(obj instanceof Date) {
            Calendar cal = new GregorianCalendar();
            cal.setTime((Date)obj);
            return cal;
        }
        else if(obj instanceof Calendar) {
            return (Calendar)obj;
        }
        else if(obj instanceof String) {
            SimpleDateFormat df = new SimpleDateFormat(Format.STANDARD_DATE_FORMAT);   //todo - should we use XML format?
            //temp = temp.substring(0, 10) + 'T' + temp.substring(10, 21) + ":" + temp.substring(21) ;
            try {
                df.parse((String)obj);
                return df.getCalendar();
            }
            catch(ParseException ex) {
                throw new ClassCastException();
            }
        }
        else {
            throw new ClassCastException();
        }

    }
}
