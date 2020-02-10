package com.tibco.store.persistence.descriptor;

import com.tibco.store.persistence.model.TableFactory;

public interface TableDescriptor<T extends TableFactory> {

	public String getTableName();

	public void setTableFactory(T tableFactory);
	
	public T getTableFactory();

}
