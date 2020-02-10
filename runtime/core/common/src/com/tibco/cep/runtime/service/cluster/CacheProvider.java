package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;

public interface CacheProvider <K, E extends Entity> extends LifeCycleService {

	EntityDao getEntityDao(Class implClass);

	EntityDao createOrGetEntityDao(Class entityClass, EntityDaoConfig daoConfig, boolean b) throws Exception;
	
	Entity getEntity (Object id);

	String getCacheName(Class implClass);

	EntityDao<K, E> getEntityDao(String cacheName);
	
}
