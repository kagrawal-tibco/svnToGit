package com.tibco.cep.studio.core.domain.importSource.impl;

import java.util.Map;

import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;

public class DomainModelDatabaseTableDataSource implements IDomainImportSource<Map<String,Object>> {

	//Need to make this of generic type of Domain Data type
	private Map<String, Object> connectionMap;
	
	public DomainModelDatabaseTableDataSource(Map<String, Object> connectionMap) {
		this.connectionMap = connectionMap;
	}
	@Override
	public Object[] getParams() {
		return new Object[]{};
	}

	@Override
	public Map<String, Object> getSource() {
		return connectionMap;
	}

}
