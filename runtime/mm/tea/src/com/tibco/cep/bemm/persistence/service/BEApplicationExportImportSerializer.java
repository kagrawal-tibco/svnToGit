/**
 * 
 */
package com.tibco.cep.bemm.persistence.service;

/**
 * @author dijadhav
 *
 */
public interface BEApplicationExportImportSerializer {

	public String getContentType();

	public com.tibco.cep.bemm.management.export.model.Application deserialize(byte[] applicationTopologyContents,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws Exception;

	public byte[] serialize(com.tibco.cep.bemm.management.export.model.Application exportedApplication)
			throws Exception;
}
