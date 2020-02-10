package com.tibco.cep.as.kit.tuple;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.data.ASDateTime;
import com.tibco.cep.runtime.model.serializers.ObjectCodecHook;

/*
* Author: Ashwin Jayaprakash Date: Apr 24, 2009 Time: 4:57:37 PM
*/

public abstract class DefaultTupleCodec implements TupleCodec
{

	public abstract ObjectCodecHook getObjectCodec();
	
    @Override
    public void putInTuple(Tuple tuple, String columnName, DataType columnType, Object columnValue) {
        if (columnValue == null) {
            return;
        }

        //------------

        switch (columnType) {
            case Boolean:
                Boolean b = (Boolean) columnValue;
                tuple.putBoolean(columnName, b);
                break;

            case Short:
                Short s = (Short) columnValue;
                tuple.putShort(columnName, s);
                break;

            case Integer:
                Integer i = (Integer) columnValue;
                tuple.putInt(columnName, i);
                break;

            case Long:
                Long l = (Long) columnValue;
                tuple.putLong(columnName, l);
                break;

            case Float:
                Float f = (Float) columnValue;
                tuple.putFloat(columnName, f);
                break;

            case Double:
                Double d = (Double) columnValue;
                tuple.putDouble(columnName, d);
                break;

            case Char:
                Character c = (Character) columnValue;
                tuple.putChar(columnName, c);
                break;

            case String:
                String str = (String) columnValue;
                tuple.putString(columnName, str);
                break;

            case Date:
                Date dt = (Date) columnValue;
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(dt.getTime());

                tuple.putDateTime(columnName, ASDateTime.create(calendar));
                break;

            case RawBlob:
                byte[] rawBytes = (byte[]) columnValue;
                tuple.putBlob(columnName, rawBytes);
                break;

            case SerializedBlob:
            default:
                try {
                    byte[] bytes = getObjectCodec().encode(columnValue, columnName);

                    tuple.putBlob(columnName, bytes);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    public Object getFromTuple(Tuple tuple, String columnName, DataType columnType) {
        if( tuple == null ) {
            return null;
        }

        switch (columnType) {
            case Boolean:
                return tuple.getBoolean(columnName);

            case Short:
                return tuple.getShort(columnName);

            case Integer:
                return tuple.getInt(columnName);

            case Long:
                return tuple.getLong(columnName);

            case Float:
                return tuple.getFloat(columnName);

            case Double:
                return tuple.getDouble(columnName);

            case Char:
                return tuple.getChar(columnName);

            case String:
                return tuple.getString(columnName);

            case Date:
                return tuple.getDateTime(columnName);

            case RawBlob:
                return tuple.getBlob(columnName);

            case SerializedBlob:
            default:
                try {
                    if (!tuple.exists(columnName) || tuple.isNull(columnName)) {
                        return null;
                    }

                    byte[] bytes = tuple.getBlob(columnName);

                    return getObjectCodec().decode(bytes, columnName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }

    @Override
    public Map<String, Object> readTupleContents(Tuple tuple) {
        HashMap<String, Object> map = new HashMap<String, Object>(tuple.size());

        readTupleContentsInto(tuple, map);

        return map;
    }

    @Override
    public void readTupleContentsInto(Tuple tuple, Map<String, Object> destination) {
        for (String fieldName : tuple.getFieldNames()) {
            FieldType fieldType = tuple.getFieldType(fieldName);
            DataType dataType = DataTypeRefMap.mapToDataType(fieldType);

            Object value = getFromTuple(tuple, fieldName, dataType);

            destination.put(fieldName, value);
        }
    }

    @Override
    public Tuple readMapContents(Map<String, Object> map) {
        Tuple tuple = Tuple.create();

        readMapContentsInto(map, tuple);

        return tuple;
    }

    @Override
    public void readMapContentsInto(Map<String, Object> map, Tuple destination) {
        for (Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();

            if (value != null) {
                DataType dataType = DataTypeRefMap.mapToDataType(value.getClass());

                putInTuple(destination, entry.getKey(), dataType, value);
            }
        }
    }
}
