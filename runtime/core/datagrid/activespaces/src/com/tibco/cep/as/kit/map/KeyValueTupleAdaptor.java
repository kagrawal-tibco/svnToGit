package com.tibco.cep.as.kit.map;

import java.io.Serializable;
import java.util.Map;

import com.tibco.as.space.Tuple;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.as.kit.tuple.TupleCodec;

/*
* Author: Ashwin Jayaprakash / Date: Dec 2, 2010 / Time: 12:00:02 PM
*/

/**
 * Not {@link Serializable}.
 *
 * @param <K>
 * @param <V>
 */
public class KeyValueTupleAdaptor<K, V> extends KeyValueTupleDef implements TupleCodec {
    protected transient TupleCodec tupleCodec;

    public KeyValueTupleAdaptor(String keyColumnName, DataType keyType, String valueColumnName, DataType valueType,
                                TupleCodec tupleCodec) {

        super(keyColumnName, keyType, valueColumnName, valueType);

        this.tupleCodec = tupleCodec;
    }

    /**
     * @param keyColumnName
     * @param keyClass        The supported data types as listed in {@link DataType}. See {@link
     *                        DataTypeRefMap#mapToDataType(Class)}.
     * @param valueColumnName
     * @param valueClass      The supported data types as listed in {@link DataType}. See {@link
     *                        DataTypeRefMap#mapToDataType(Class)}.
     * @param tupleCodec
     */
    public KeyValueTupleAdaptor(String keyColumnName, Class<K> keyClass,
                                String valueColumnName, Class<V> valueClass,
                                TupleCodec tupleCodec) {

        super(keyColumnName, DataTypeRefMap.mapToDataType(keyClass),
                valueColumnName, (valueClass == null) ? null : DataTypeRefMap.mapToDataType(valueClass));

        this.tupleCodec = tupleCodec;
    }

    public KeyValueTupleAdaptor(KeyValueTupleDef tupleDef, TupleCodec tupleCodec) {
        this(tupleDef.getKeyColumnName(), tupleDef.getKeyType(),
                tupleDef.getValueColumnName(), tupleDef.getValueType(),
                tupleCodec);
    }

    //--------------

    public Tuple makeTuple(K key) {
        Tuple tuple = Tuple.create();

        tupleCodec.putInTuple(tuple, keyColumnName, keyType, key);

        return tuple;
    }

    // This is executed when ObjectTable and ObjectExtIdTable is populated,
	// or explicit-tuple=false is used for concept storage.
    public Tuple setValue(Tuple tuple, V value) {
        tupleCodec.putInTuple(tuple, valueColumnName, valueType, value);

        return tuple;
    }

    /**
     * Utility method.
     *
     * @param tuple
     * @return
     */
    public K extractKey(Tuple tuple) {
        return (K) tupleCodec.getFromTuple(tuple, keyColumnName, keyType);
    }

    /**
     * Utility method.
     *
     * @param tuple
     * @return
     */
    public V extractValue(Tuple tuple) {
        return (V) tupleCodec.getFromTuple(tuple, valueColumnName, valueType);
    }

    //---------------

    @Override
    public void putInTuple(Tuple tuple, String columnName, DataType columnType, Object columnValue) {
        tupleCodec.putInTuple(tuple, columnName, columnType, columnValue);
    }

    @Override
    public Object getFromTuple(Tuple tuple, String columnName, DataType columnType) {
        return tupleCodec.getFromTuple(tuple, columnName, columnType);
    }

    @Override
    public Map<String, Object> readTupleContents(Tuple tuple) {
        return tupleCodec.readTupleContents(tuple);
    }

    @Override
    public void readTupleContentsInto(Tuple tuple, Map<String, Object> destination) {
        tupleCodec.readTupleContentsInto(tuple, destination);
    }

    @Override
    public Tuple readMapContents(Map<String, Object> map) {
        return tupleCodec.readMapContents(map);
    }

    @Override
    public void readMapContentsInto(Map<String, Object> map, Tuple destination) {
        tupleCodec.readMapContentsInto(map, destination);
    }
}
