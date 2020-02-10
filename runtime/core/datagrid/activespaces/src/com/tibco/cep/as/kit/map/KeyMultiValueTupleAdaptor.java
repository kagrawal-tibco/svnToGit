package com.tibco.cep.as.kit.map;

import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.TupleCodec;

/*
* Author: Ashwin Jayaprakash / Date: 6/9/11 / Time: 4:20 PM
*/
public abstract class KeyMultiValueTupleAdaptor<K, V> extends KeyValueTupleAdaptor<K, V> {
    protected String[] fieldNames;

    protected DataType[] dataTypes;

    protected KeyMultiValueTupleAdaptor(String keyColumnName, DataType keyType,
                                        String[] fieldNames, DataType[] dataTypes, TupleCodec tupleCodec) {
        super(keyColumnName, keyType, null, null, tupleCodec);

        this.fieldNames = fieldNames;
        this.dataTypes = dataTypes;
    }

    protected KeyMultiValueTupleAdaptor(String keyColumnName, Class<K> keyClass,
                                        String[] fieldNames, DataType[] dataTypes, TupleCodec tupleCodec) {
        super(keyColumnName, keyClass, null, null, tupleCodec);

        this.fieldNames = fieldNames;
        this.dataTypes = dataTypes;
    }

    protected KeyMultiValueTupleAdaptor(KeyValueTupleDef tupleDef,
                                        String[] fieldNames, DataType[] dataTypes, TupleCodec tupleCodec) {
        super(tupleDef, tupleCodec);

        this.fieldNames = fieldNames;
        this.dataTypes = dataTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public DataType[] getDataTypes() {
        return dataTypes;
    }
}
