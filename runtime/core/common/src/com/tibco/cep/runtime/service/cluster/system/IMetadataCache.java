package com.tibco.cep.runtime.service.cluster.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;

public interface IMetadataCache {
    public final static int BE_TYPE_START = 1000;

    public final static int BE_TYPE_INTERNAL_END = 1010;

	void init(Cluster cluster) throws Exception;

	void start() throws Exception;

	boolean hasStarted();

	int registerType(Class entityClass) throws Exception;

//	com.tibco.cep.kernel.model.entity.Entity createEntity(int typeId, Id id) throws Exception;

	Class[] getRegisteredTypes();

	List<Integer> getInheritedTypes(int typeId);

	List<Integer> getContainedTypes(String resourceURI) throws Exception;

	ArrayList getRegisteredConceptTypes();

	ArrayList getRegisteredSimpleEventTypes();

	ArrayList getRegisteredTimeEventTypes();

	Integer getTypeId(Class entityClz);

	int getTypeId(String clzName) throws Exception;

	Class getClass(int typeId);

	EntityDao<Long, com.tibco.cep.kernel.model.entity.Entity> getEntityDao(int typeId);

	boolean isValidTypeId(int typeId);

//	EntityDao getEntityDao(Class clazz);

	EntityDaoConfig getEntityDaoConfig(Class aClass);
	
	EntityDaoConfig getEntityDaoConfig(int typeId);

	Map<Class, EntityDaoConfig> getEntityConfigurations();

	void reloadTypes() throws Exception;

	int size();
}
