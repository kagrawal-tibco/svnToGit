package com.tibco.cep.runtime.model.serializers.as;

import com.tibco.as.space.DateTime;
import com.tibco.as.space.impl.data.ASBlob;
import com.tibco.as.space.impl.data.ASString;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;

import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
* Author: Ashwin Jayaprakash / Date: 12/13/11 / Time: 4:02 PM
*/
public class AsPortablePojo extends PortablePojo {
    protected Map<String, Object> storage;

    protected AsPortablePojo(long id, String extId, int typeId, Map<String, Object> storage) {
        super(id, extId, typeId);

        this.storage = storage;
    }

    public AsPortablePojo(long id, String extId, int typeId) {
        this(id, extId, typeId, new HashMap<String, Object>());
    }

    static Object convertToAsType(Object value) {
        if (value instanceof Calendar) {
            return DateTime.create((Calendar) value);
        }
        else if (value instanceof String) {
            return new ASString((String) value);
        }
        else if (value instanceof Object[] || value instanceof byte[]) {
            /*
            byte[] also gets encoded as a serialized object as there is no way to tell the
            difference between a serialized Object and a raw byte[].

            The correct way would've been to serialize byte[] and Object[] separately.
            */

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(value);
                oos.close();

                return new ASBlob(baos.toByteArray());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return value;
    }

    static Object convertFromAsType(Object value) throws IOException, ClassNotFoundException {
        if (value instanceof DateTime) {
        	Calendar cal = ((DateTime) value).getTime();
            //force it to convert from GMT hours / min / sec and set its internal milliseconds member
        	//this makes it symmetric with convertToAsType which eventually creates a Calendar
        	//by setting the milliseconds internally
        	((Calendar)cal).getTimeInMillis();
            return cal;
        }
        else if (value instanceof ASString) {
            return ((ASString) value).getValue();
        }
        else if (value instanceof ASBlob) {
            byte[] bytes = ((ASBlob) value).getBytes();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            value = ois.readObject();
            ois.close();
        }

        return value;
    }

    public Map<String, Object> toAsFormat() throws IOException {
        Map<String, Object> m = storage;

        Long l = id;
        m.put(PortablePojoConstants.PROPERTY_NAME_KEY, l);
        m.put(PortablePojoConstants.PROPERTY_NAME_ID, l);
        m.put(PortablePojoConstants.PROPERTY_NAME_EXT_ID, extId);
        m.put(PortablePojoConstants.PROPERTY_NAME_TYPE_ID, typeId);

        for (Entry<String, Object> entry : m.entrySet()) {
            Object value = entry.getValue();
            value = convertToAsType(value);
            entry.setValue(value);
        }

        storage = null;

        return m;
    }

    public static AsPortablePojo toPojo(Map<String, Object> asFormatMap) throws Exception {
        HashMap<String, Object> m = new HashMap<String, Object>(asFormatMap);

        for (Entry<String, Object> entry : m.entrySet()) {
            Object value = entry.getValue();
            value = convertFromAsType(value);
            entry.setValue(value);
        }

        Long id = (Long) m.get(PortablePojoConstants.PROPERTY_NAME_ID);
        String extId = (String) m.get(PortablePojoConstants.PROPERTY_NAME_EXT_ID);
        Integer typeId = (Integer) m.get(PortablePojoConstants.PROPERTY_NAME_TYPE_ID);

        return new AsPortablePojo(id, extId, typeId, m);
    }
    
    @Override
    public Object getPropertyValue(String name) {
    	return storage.get(name);
    }

    @Override
    public Object getPropertyValue(String name, String type) {
        return storage.get(name);
    }

//    @Override
//    public void setPropertyValue(String name, boolean value) {
//        storage.put(name, value);
//    }
//
//    @Override
//    public void setPropertyValue(String name, int value) {
//        storage.put(name, value);
//    }
//
//    @Override
//    public void setPropertyValue(String name, long value) {
//        storage.put(name, value);
//    }
//
//    @Override
//    public void setPropertyValue(String name, double value) {
//        storage.put(name, value);
//    }
//
    @Override
    public void setPropertyValue(String name, Object value, String type) {
        storage.put(name, value);
    }
    
    @Override
    public void setPropertyValue(String name, Object value) {
    	storage.put(name, value);
    }

//    @Override
//    public Object getArrayPropertyValue(String name, int position) {
//        Object[] array = (Object[]) storage.get(name);
//
//        return (array == null) ? null : array[position];
//    }

//    @Override
//    public Object[] getArrayPropertyValues(String name) {
//        return (Object[]) storage.get(name);
//    }
    
    @Override
    public Object[] getArrayPropertyValues(String name, String type) {
    	return (Object[]) storage.get(name);
    }

//    private void setArrayPropertyValueInternal(String name, int totalLength, Object value, int position) {
//        Object[] array = (Object[]) storage.get(name);
//        if (array == null) {
//            array = new Object[totalLength];
//            storage.put(name, array);
//        }
//
//        array[position] = value;
//    }

    //--------------

//    @Override
//    public void setArrayPropertyValue(String name, int totalLength, boolean value, int position) {
//        setArrayPropertyValueInternal(name, totalLength, value, position);
//    }
//
//    @Override
//    public void setArrayPropertyValue(String name, int totalLength, int value, int position) {
//        setArrayPropertyValueInternal(name, totalLength, value, position);
//    }
//
//    @Override
//    public void setArrayPropertyValue(String name, int totalLength, long value, int position) {
//        setArrayPropertyValueInternal(name, totalLength, value, position);
//    }
//
//    @Override
//    public void setArrayPropertyValue(String name, int totalLength, double value, int position) {
//        setArrayPropertyValueInternal(name, totalLength, value, position);
//    }
//
//    @Override
//    public void setArrayPropertyValue(String name, int totalLength, Object value, int position) {
//        setArrayPropertyValueInternal(name, totalLength, value, position);
//    }

    //--------------

//    @Override
//    public Object getHistoryPropertyValue(String name, int position) {
//        Object[] array = (Object[]) storage.get(name);
//
//        return (array == null) ? null : array[position];
//    }

//    @Override
//    public Object[] getHistoryPropertyValues(String name) {
//        return (Object[]) storage.get(name);
//    }
    
    @Override
    public Object[] getHistoryPropertyValues(String name, String type) {
    	return (Object[]) storage.get(name);
    }

//    @Override
//    public void setHistoryPropertyValue(String name, int totalLength, boolean value, long time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setHistoryPropertyValue(String name, int totalLength, int value, long time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setHistoryPropertyValue(String name, int totalLength, long value, long time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setHistoryPropertyValue(String name, int totalLength, double value, long time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setHistoryPropertyValue(String name, int totalLength, Object value, long time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    //--------------
//
//    @Override
//    public Object getArrayHistoryPropertyValue(String name, int position) {
//        Object[] array = (Object[]) storage.get(name);
//
//        return (array == null) ? null : array[position];
//    }

//    @Override
//    public Object[] getArrayHistoryPropertyValues(String name) {
//        return (Object[]) storage.get(name);
//    }
    
    @Override
    public Object[] getArrayHistoryPropertyValues(String name, String type) {
    	return (Object[]) storage.get(name);
    }

//    @Override
//    public void setArrayHistoryPropertyValue(String name, int totalLength, boolean[] value, long[] time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setArrayHistoryPropertyValue(String name, int totalLength, int[] value, long[] time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setArrayHistoryPropertyValue(String name, int totalLength, long[] value, long[] time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setArrayHistoryPropertyValue(String name, int totalLength, double[] value, long[] time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }
//
//    @Override
//    public void setArrayHistoryPropertyValue(String name, int totalLength, Object[] value, long[] time, int position) {
//        setArrayPropertyValueInternal(name, totalLength, new Object[] { value, time }, position);
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append("{storage=").append(storage);
        sb.append('}');
        return sb.toString();
    }
}
