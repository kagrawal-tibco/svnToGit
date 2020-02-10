package com.tibco.cep.as.kit.tuple;

import java.util.Map;

import com.tibco.as.space.Tuple;

/*
* Author: Ashwin Jayaprakash / Date: Dec 3, 2010 / Time: 11:13:11 AM
*/

public interface TupleCodec {
    void putInTuple(Tuple tuple, String columnName, DataType columnType, Object columnValue);

    Object getFromTuple(Tuple tuple, String columnName, DataType columnType);

    /**
     * @param tuple
     * @return Map with field names as keys and correctly deserialized objects as field values.
     */
    Map<String, Object> readTupleContents(Tuple tuple);

    /**
     * @param tuple
     * @param destination Map into which the tuple contents will be written. Field names as keys and correctly
     *                    deserialized objects as field values.
     */
    void readTupleContentsInto(Tuple tuple, Map<String, Object> destination);

    /**
     * @param map
     * @return Tuple with the key-value pairs just like in the map. Null values are not written.
     */
    Tuple readMapContents(Map<String, Object> map);

    /**
     * @param map
     * @param destination Tuple in to which key-value pairs from the map are written. Null values are not written.
     */
    void readMapContentsInto(Map<String, Object> map, Tuple destination);
}
