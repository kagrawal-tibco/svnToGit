package com.tibco.store.persistence.descriptor;

import com.tibco.store.query.model.DataType;

public interface ColumnDescriptor {

	public String getName();

	public DataType getDataType();

	public boolean isIndexed();
}
