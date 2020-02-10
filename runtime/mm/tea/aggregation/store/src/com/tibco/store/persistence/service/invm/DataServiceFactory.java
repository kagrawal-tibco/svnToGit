package com.tibco.store.persistence.service.invm;

public class DataServiceFactory {

	private static DataServiceFactory instance;
	private InMemoryDataStoreService inMemoryDataService;
	private InMemoryMetadataService inMemoryMetaDatService;

	private DataServiceFactory() {
		
	}

	public static DataServiceFactory getInstance() {
		if (instance == null) {
			instance = new DataServiceFactory();
		}
		return instance;
	}
	
	public InMemoryDataStoreService getDataStoreService() {
		if (inMemoryDataService == null) {
			return inMemoryDataService = new InMemoryDataStoreService();
		}
		return inMemoryDataService;
	}

	public InMemoryMetadataService getMetaDataService() {
		if (inMemoryMetaDatService == null) {
			return inMemoryMetaDatService = new InMemoryMetadataService();
		}
		return inMemoryMetaDatService;
	}

}
