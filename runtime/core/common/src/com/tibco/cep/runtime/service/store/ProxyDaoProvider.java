package com.tibco.cep.runtime.service.store;

import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterProvider;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.runtime.service.om.api.InvocationService;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;

/**
 * A wrapper class over the new decoupled Store & Cluster interfaces. Brings them under DaoProvider api to control impact in all of the BE code base.
 */
public class ProxyDaoProvider implements DaoProvider {
	
	private StoreProvider storeProvider;
	private ClusterProvider clusterProvider;
	
	public ProxyDaoProvider(StoreProvider storeProvider, ClusterProvider clusterProvider) {
		this.storeProvider = storeProvider;
		this.clusterProvider = clusterProvider;
	}

	@Override
	public void init(Cluster cluster) throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Override
	public <K, E extends Entity> EntityDao<K, E> createEntityDao(Class<E> entityClass, EntityDaoConfig daoConfig) {
		try {
			return storeProvider.getCacheProvider().createOrGetEntityDao(entityClass, daoConfig);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public <K, E extends Entity> EntityDao<K, E> createEntityDao(Class<E> entityClass, EntityDaoConfig daoConfig,
			boolean overwrite) {
		return createEntityDao(entityClass, daoConfig);
	}

	@Override
	public <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass, ControlDaoType daoType,
			Object... additionalProps) {
		try {
			return clusterProvider.createControlDao(keyClass, valueClass, daoType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <K, E extends Entity> EntityDao<K, E> getEntityDao(Class<E> entityClass) {
		return storeProvider.getCacheProvider().getEntityDao(entityClass);
	}

	@Override
	public <K, E extends Entity> EntityDao<K, E> getEntityDao(String cacheName) {
		return storeProvider.getCacheProvider().getEntityDao(cacheName);
	}

	@Override
	public <K, V> ControlDao<K, V> getControlDao(String daoName) {
		return clusterProvider.getControlDao(daoName);
	}

	@Override
	public LocalCache newLocalCache() {
		throw new RuntimeException("Implement me!");//TODO:60
	}

	@Override
	public Collection<? extends EntityDao> getAllEntityDao() {
		return storeProvider.getCacheProvider().getAllEntityDao();
	}

	@Override
	public Collection<? extends ControlDao> getAllControlDao() {
		return clusterProvider.getAllControlDao();
	}

	@Override
	public InvocationService getInvocationService() {
		throw new RuntimeException("Implement me!");//TODO:60
	}

	@Override
	public void recoverCluster(Cluster cluster) throws Exception {
		throw new RuntimeException("Implement me!");//TODO:60
	}

	@Override
	public GroupMemberMediator newGroupMemberMediator() {
		throw new RuntimeException("Implement me!");//TODO:60
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public GenericBackingStore getBackingStore() {
		return this.storeProvider.getBackingStore();
	}

	@Override
	public void stop() {
		throw new RuntimeException("Implement me!");//TODO:60
	}

	@Override
	public void modelChanged() {
		throw new RuntimeException("Implement me!");//TODO:60
	}
}
