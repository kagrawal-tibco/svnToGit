package com.tibco.cep.runtime.managed;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Metaspace;
import com.tibco.cep.as.kit.collection.DiscardableSet;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/*
* Author: Ashwin Jayaprakash / Date: 11/22/11 / Time: 3:07 PM
*/
public class DataGridManagedObjectSpi extends AbstractGridManagedObjectSpi {
    protected Metaspace metaspace;

    public DataGridManagedObjectSpi() {
        Cluster cluster = RuleServiceProviderManager.getInstance().getDefaultProvider().getCluster();
        ASDaoProvider asDaoProvider = (ASDaoProvider) cluster.getDaoProvider();
        this.metaspace = asDaoProvider.getMetaspace();
    }

    //todo Use some kind of locking or compare put? Clone entire tuples?

    @Override
    public EntityImpl fetchByExtId(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (entityClass == null) {
            return null;
        }

        EntityDao dao = getDao(entityClass);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        try {
            DiscardableSet set = spaceMap.entrySet(PortablePojoConstants.PROPERTY_NAME_EXT_ID + " = \"" + extId + "\"");
            for (Object o : set) {
                Entry entry = (Entry) o;

                return (EntityImpl) entry.getValue();
            }

            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EntityImpl> fetchByExtIds(Collection<String> extIds, Class entityClass, ManagedObjectLockType locktype) {
        throw new UnsupportedOperationException("fetchByExtIds not implemented for DataGridManagedObjectSpi");
    }
    
    @Override
    public void begin() {
        try {
            metaspace.beginTransaction();
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        try {
            metaspace.commitTransaction();
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            metaspace.rollbackTransaction();
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }
}
