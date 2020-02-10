package com.tibco.store.persistence.descriptor.impl;

import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.query.model.DataType;

public class ColumnDescriptorImpl implements ColumnDescriptor{

    private String name;

    private DataType dataType;

    private boolean isIndexed;

    public ColumnDescriptorImpl(String name) {
        this(name, DataType.STRING);
    }


    public ColumnDescriptorImpl(String name, DataType dataType) {
        this(name, dataType, false);
    }

    public ColumnDescriptorImpl(String name, DataType dataType, boolean isIndexed) {
        if (name == null) {
            throw new IllegalArgumentException("Column name cannot be null");
        }
        this.name = name;
        this.dataType = dataType;
        this.isIndexed = isIndexed;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public boolean isIndexed() {
        return isIndexed;
    }
}
