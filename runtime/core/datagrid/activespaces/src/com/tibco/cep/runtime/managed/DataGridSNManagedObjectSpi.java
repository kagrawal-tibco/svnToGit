package com.tibco.cep.runtime.managed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.impl.ASSpace;
import com.tibco.cep.as.kit.collection.DiscardableIterator;
import com.tibco.cep.as.kit.collection.DiscardableSet;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.kernel.core.rete.BeTransaction;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created with IntelliJ IDEA. User: pgowrish Date: 6/19/12 Time: 5:56 PM To change this template use File | Settings | File
 * Templates.
 */
public class DataGridSNManagedObjectSpi extends AbstractGridManagedObjectSpi {
    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(DataGridSNManagedObjectSpi.class);

    protected Metaspace metaspace;
    protected boolean usebrowser = true;

    public DataGridSNManagedObjectSpi() {
        Cluster cluster = RuleServiceProviderManager.getInstance().getDefaultProvider().getCluster();
        ASDaoProvider asDaoProvider = (ASDaoProvider) cluster.getDaoProvider();
        this.metaspace = asDaoProvider.getMetaspace();
        usebrowser = Boolean.parseBoolean(
                System.getProperty(SystemProperty.PROP_TUPLE_USEBROWSER.getPropertyName(), Boolean.TRUE.toString()));
        LOGGER.log(Level.INFO, "DataGrid will use %s api to fetch objects", (usebrowser ? "browser":"get-query"));
    }

    @Override
    public EntityImpl fetchByExtId(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (usebrowser) {
            return fetchByExtIdUsingBrowser(extId, entityClass, locktype);
        }
        else {
            return fetchByExtIdUsingGet(extId, entityClass, locktype);
        }
    }

    @Override
    public List<EntityImpl> fetchByExtIds(Collection<String> extIds, Class entityClass, ManagedObjectLockType locktype) {
      if(entityClass == null || extIds == null || extIds.size() <= 0) return Collections.EMPTY_LIST;
        
        String pfx = PortablePojoConstants.PROPERTY_NAME_EXT_ID + " IN (";
        int len = 0; 
        for(String extId : extIds) len += extId.length();
        if(len == 0) return Collections.EMPTY_LIST;
        //extIds.size() * 3 for commas and double quotes, final omitted comma is replaced by close paren
        len += pfx.length() + extIds.size()*3;
        StringBuilder bldr = new StringBuilder(len);
        bldr.append(pfx);
        for(String extId : extIds) {
            if(extId != null && (extId = extId.trim()).length() > 0) {
                bldr.append('"').append(extId).append('"').append(',');
            }
        }
        bldr.setCharAt(bldr.length() - 1, ')');
        
        SpaceMap spaceMap = (SpaceMap) getDao(entityClass).getInternal();
        ASSpace space = (ASSpace)spaceMap.getSpace();
    
        if (usebrowser) {
            DiscardableSet set = spaceMap.entrySet(bldr.toString());
            ArrayList<EntityImpl> result = new ArrayList(set.size());
            Iterator discardableIterator = set.iterator();
            try {
                while(discardableIterator.hasNext()) {
                    Entry entry = (Entry) discardableIterator.next();
                    EntityImpl value = (EntityImpl) entry.getValue();
                    if(value != null) result.add(value);
                }
            } finally {
                set.discard();
            }
            return result;
        } else {
            try {
                KeyValueTupleAdaptor<Long, Entity> tupleAdaptor = spaceMap.getTupleAdaptor();
                Collection<Tuple> tuples = space.get(bldr.toString()); 
                ArrayList<EntityImpl> result = new ArrayList<EntityImpl>(tuples.size());
                for(Tuple tuple : tuples) {
                    if(tuple != null) {
                        EntityImpl value = (EntityImpl) tupleAdaptor.extractValue(tuple);
                        if(value != null) result.add(value);
                    }
                }
                return result;
            } catch (ASException e) {
                LOGGER.log(Level.WARN, "Failed to fetch %s by list of extIds", entityClass);
                return Collections.EMPTY_LIST;
            }
        }
    }
    
    protected EntityImpl fetchByExtIdUsingGet(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (entityClass == null) {
            return null;
        }
        EntityImpl entity = null;
        EntityDao dao = getDao(entityClass);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        ASSpace space = (ASSpace)spaceMap.getSpace();

        long start = System.currentTimeMillis();
        String query = PortablePojoConstants.PROPERTY_NAME_EXT_ID + " = \"" + extId + "\"";

        try {
            Iterator<Tuple> itr = space.get(query).iterator();
            if (itr.hasNext()) {
                Tuple tuple = itr.next();
                KeyValueTupleAdaptor<Long, Entity> tupleAdaptor = spaceMap.getTupleAdaptor();
                entity = (EntityImpl)tupleAdaptor.extractValue(tuple);
            }
        } catch (ASException e) {
            LOGGER.log(Level.WARN, "Failed to fetch %s by extId %s", entityClass, extId);
        }
        dao.updateGetStats(System.currentTimeMillis() - start);
        
        return entity;
    }
    
    protected EntityImpl fetchByExtIdUsingTuple(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (entityClass == null) {
            return null;
        }
        EntityImpl entity = null;
        EntityDao dao = getDao(entityClass);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        ASSpace space = (ASSpace)spaceMap.getSpace();

        long start = System.currentTimeMillis();
        Tuple query = Tuple.create();
        query.put(PortablePojoConstants.PROPERTY_NAME_EXT_ID, extId);
        // Does not work without ID (non-nullable field is set)

        try {
            Tuple tuple = space.get(query);
            if (tuple != null) {
                KeyValueTupleAdaptor<Long, Entity> tupleAdaptor = spaceMap.getTupleAdaptor();
                entity = (EntityImpl)tupleAdaptor.extractValue(tuple);
            }
        } catch (ASException e) {
            LOGGER.log(Level.WARN, "Failed to fetch %s by extId %s", entityClass, extId);
        }
        dao.updateGetStats(System.currentTimeMillis() - start);
        
        return entity;
    }
    
    public EntityImpl fetchByExtIdUsingBrowser(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (entityClass == null) {
            return null;
        }
        EntityImpl entity = null;
        EntityDao dao = getDao(entityClass);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        long start = System.currentTimeMillis();
        String query = PortablePojoConstants.PROPERTY_NAME_EXT_ID + " = \"" + extId + "\"";

        DiscardableSet set = spaceMap.entrySet(query);

        DiscardableIterator<Entry<?, ?>> discardableIterator = set.iterator();
        try {
            if (discardableIterator.hasNext()) {
                Entry entry = (Entry) discardableIterator.next();
                entity = (EntityImpl) entry.getValue();
            }
        } finally {
            discardableIterator.discard();
        }
        dao.updateGetStats(System.currentTimeMillis() - start);

        return entity;
    }
    
    public EntityImpl fetchByExtIdUsingQuery(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (entityClass == null) {
            return null;
        }
        EntityImpl entity = null;
        EntityDao dao = getDao(entityClass);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        ASSpace space = (ASSpace)spaceMap.getSpace();

        long start = System.currentTimeMillis();
        String query = PortablePojoConstants.PROPERTY_NAME_EXT_ID + " = \"" + extId + "\"";

        try {
            Browser browser = space.browse(BrowserDef.BrowserType.GET,
                                           BrowserDef.create().
                                               setTimeScope(BrowserDef.TimeScope.SNAPSHOT).
                                               setPrefetch(BrowserDef.PREFETCH_ALL), 
                                           query);
            Tuple tuple = browser.next();
            if (tuple != null) {
                KeyValueTupleAdaptor<Long, Entity> tupleAdaptor = spaceMap.getTupleAdaptor();
                entity = (EntityImpl)tupleAdaptor.extractValue(tuple);
            }
            browser.stop();
        } catch (ASException e) {
            LOGGER.log(Level.WARN, "Failed to fetch %s by extId %s", entityClass, extId);
        }

        dao.updateGetStats(System.currentTimeMillis() - start);

        return entity;
    }
    
    @Override
    public EntityImpl fetchById(long id, Class entityClass, ManagedObjectLockType locktype) {
        EntityImpl entity = (EntityImpl) BeTransaction.getFromTxn(id);
        if (entity != null) {
            return entity;
        }

        // Id contains type info encoded.
        EntityDao dao = getDao(id);

        try {
            return (EntityImpl) dao.get(id);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }
}
