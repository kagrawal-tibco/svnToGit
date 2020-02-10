package com.tibco.cep.studio.core.domain.importSource.impl;

import java.util.Map;

import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;

public class DomainModelDBConceptDataSource implements IDomainImportSource<Map<String, Object>> {
	
	final private Map<String, Object> dbParams;

	public DomainModelDBConceptDataSource(Map<String, Object> dbParams) {
		this.dbParams = dbParams;
	}

	@Override
	public Map<String, Object> getSource() {
		return dbParams;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.domain.importHandler.IDomainImportSource#getParams()
	 */
	@Override
	public Object[] getParams() {
		return new Object[]{};
	}
}
