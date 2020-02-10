package com.tibco.cep.bemm.persistence.service;

import java.util.Map;

import com.tibco.cep.bemm.model.MasterHost;

/**
 * Serialize master host details
 * 
 * @author dijadhav
 *
 */
public interface BEMasterHostSerializer {

	String getContentType();

	Map<String, MasterHost> deserialize(byte[] masterHostContents,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws Exception;

	byte[] serialize(Map<String, MasterHost> masterHostMap) throws Exception;

}
