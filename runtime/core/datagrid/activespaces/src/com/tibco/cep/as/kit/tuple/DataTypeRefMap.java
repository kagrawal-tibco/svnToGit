package com.tibco.cep.as.kit.tuple;

import java.util.HashMap;

import com.tibco.as.space.FieldDef.FieldType;

/*
* Author: Ashwin Jayaprakash Date: Apr 24, 2009 Time: 11:10:15 PM
*/

/**
 * Reverse map with Key = {@link DataType#getSupportedJavaClass()} or Key = {@link DataType#getFieldType()} to Value =
 * {@link DataType}.
 */
public final class DataTypeRefMap extends HashMap<Object, DataType> {
    /**
     * Singleton.
     */
    public static final DataTypeRefMap REF_MAP = new DataTypeRefMap();

    public DataTypeRefMap() {
        super();

        for (DataType dataType : DataType.values()) {
            put(dataType.getSupportedJavaClass(), dataType);

            put(dataType.getFieldType(), dataType);
        }
    }

    /**
     * @return
     * @see #REF_MAP
     */
    public static DataTypeRefMap getRefMap() {
        return REF_MAP;
    }

    /**
     * Maps the provided Java Class to the {@link DataType} if a match is found - i.e {@link
     * DataType#getSupportedJavaClass()} is the same as the parameter. The default is {@link DataType#SerializedBlob}
     * when nothing else matches.
     *
     * @param javaClass
     * @return
     */
    public static DataType mapToDataType(Class javaClass) {
        DataTypeRefMap refMap = DataTypeRefMap.getRefMap();

        DataType dataType = refMap.get(javaClass);
        if (dataType != null) {
            return dataType;
        }

        return DataType.SerializedBlob;
    }

    /**
     * Maps the provided type to the {@link DataType} if a match is found - i.e {@link DataType#getFieldType()} is the
     * same as the parameter. The default is {@link DataType#SerializedBlob} when nothing else matches.
     *
     * @param fieldType
     * @return
     */
    public static DataType mapToDataType(FieldType fieldType) {
        DataTypeRefMap refMap = DataTypeRefMap.getRefMap();

        DataType dataType = refMap.get(fieldType);
        if (dataType != null) {
            return dataType;
        }

        return DataType.SerializedBlob;
    }
}
