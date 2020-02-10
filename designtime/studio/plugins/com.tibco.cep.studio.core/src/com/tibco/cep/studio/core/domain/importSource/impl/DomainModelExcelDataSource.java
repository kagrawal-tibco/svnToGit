/**
 * 
 */
package com.tibco.cep.studio.core.domain.importSource.impl;

import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;

/**
 * @author aathalye
 *
 */
public class DomainModelExcelDataSource implements IDomainImportSource<String> {
	
	private String excelFilePath;
	
	public DomainModelExcelDataSource(String dataSource) {
		this.excelFilePath = dataSource;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.domain.importHandler.IDomainImportSource#getParams()
	 */
	@Override
	public Object[] getParams() {
		return new Object[]{};
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.domain.importHandler.IDomainImportSource#getSource()
	 */
	@Override
	public String getSource() {
		return excelFilePath;
	}
}
