package com.tibco.cep.runtime.managed;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectSpi;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.om.AbstractBeTransactionHook;
import com.tibco.cep.runtime.service.cluster.system.IDEncoder;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.impl.AbstractDaoProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import java.util.Collections;
import java.util.HashSet;

/*
* Author: Ashwin Jayaprakash / Date: 11/22/11 / Time: 3:07 PM
*/
public abstract class AbstractGridManagedObjectSpi extends AbstractBeTransactionHook implements ManagedObjectSpi {
    protected static final ThreadLocal<HashSet<Long>> writeLockedEntities = new ThreadLocal<HashSet<Long>>();

    protected AbstractDaoProvider daoProvider;

    protected MetadataCache metadataCache;

    protected AbstractGridManagedObjectSpi() {
        Cluster cluster = RuleServiceProviderManager.getInstance().getDefaultProvider().getCluster();

        this.daoProvider = (AbstractDaoProvider) cluster.getDaoProvider();
        this.metadataCache = cluster.getMetadataCache();
    }

    /**
     * @return true.
     */
    @Override
    public boolean isComposable() {
        return true;
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void txnWork(Runnable work) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasWriteLock(EntityImpl c) {
        HashSet<Long> map = writeLockedEntities.get();

        return map != null && map.contains(c.getId());
    }

    @Override
    public void readLockObject(EntityImpl c) {
    }

    @Override
    public void writeLockObject(EntityImpl c) {
        writeLockObject(c.getId());
    }

    @Override
    public void writeLockObject(long id) {
        HashSet<Long> set = writeLockedEntities.get();

        if (set == null) {
            set = new HashSet<Long>();
            writeLockedEntities.set(set);
        }

        set.add(id);
    }

    protected final EntityDao getDao(long id) {
        int typeId = IDEncoder.decodeTypeId(id);//+ MetadataCache.BE_TYPE_START + 1;

        EntityDao dao = (EntityDao) metadataCache.getEntityDao(typeId);
        if (dao != null) {
            return dao;
        }

        throw new IllegalArgumentException("No DAO found for type id [" + typeId + "]");
    }

    protected final EntityDao getDao(Class<? extends Entity> klass) {
        EntityDao dao = (EntityDao) daoProvider.getEntityDao(klass);
        if (dao != null) {
            return dao;
        }

        throw new IllegalArgumentException("No DAO found for class [" + klass + "]");
    }

    protected final EntityDao getDao(EntityImpl c) {
        return getDao(c.getClass());
    }

    @Override
    public EntityImpl fetchById(long id, Class entityClass, ManagedObjectLockType locktype) {
        EntityDao dao = (entityClass == null) ? getDao(id) : getDao(entityClass);

        return (EntityImpl) dao.get(id);
    }

    @Override
    public void insert(EntityImpl entity) {
        EntityDao dao = getDao(entity);

        try {
            dao.put(entity);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(EntityImpl entity) {
        insert(entity);
    }

    @Override
    public void delete(long entityId, Class entityClass) {
        EntityDao dao = getDao(entityClass);
        dao.removeAll(Collections.singleton(entityId));
    }

    @Override
    public void cleanupTxnWorkArea(Object ruleSession) {
        super.cleanupTxnWorkArea(ruleSession);

        HashSet<Long> set = writeLockedEntities.get();
        if (set != null) {
            set.clear();
        }
    }
}
