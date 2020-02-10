package com.tibco.cep.bemm.persistence.service;

import com.tibco.cep.bemm.model.Application;

public interface BEApplicationSerializer {

	public String getContentType();

	public Application deserialize(byte[] applicationTopologyContents,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws Exception;

	public byte[] serialize(Application application) throws Exception;
}
