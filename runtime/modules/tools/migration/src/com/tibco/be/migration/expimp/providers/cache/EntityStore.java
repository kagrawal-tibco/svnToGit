package com.tibco.be.migration.expimp.providers.cache;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.ConcurrentMap;
import com.tangosol.util.TransactionMap;
import com.tangosol.util.filter.AlwaysFilter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 21, 2008
 * Time: 10:41:25 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntityStore {
    protected Entity entityModel;
    protected Class implClass;
    protected Constructor entityConstructor;
    protected String masterCacheName;
//    protected String cacheName;
    protected NamedCache entityCache;
    protected TransactionMap m_currentTransaction;
    protected Logger logger;
    protected HashMap m_addedObjects = new HashMap();
    protected ArrayList m_deletedObjects = new ArrayList();
    protected RuleServiceProvider mrsp;
    private boolean customSetting;

    public final static int CACHE_MODE_WMONLY=0;
    public final static int CACHE_MODE_CACHEONLY=1;
    public final static int CACHE_MODE_CACHEANDWM=2;
    public final static String [] cacheModes = new String[] {"memory", "cache", "cacheAndMemory"};
    String uri;
    boolean isDeployed;
    int cacheMode;
    String recoveryFunction;
    private Properties cacheConfig;



    public EntityStore(RuleServiceProvider rsp, String masterCacheName, Entity entity, Properties cacheConfig) throws Exception{
        this.mrsp = rsp;
        this.cacheConfig = cacheConfig;
        this.masterCacheName=masterCacheName;
        this.logger = rsp.getLogger(EntityStore.class);
        this.entityModel = entity;
        this.implClass =rsp.getTypeManager().getTypeDescriptor(entity.getFullPath()).getImplClass();
        entityConstructor = implClass.getConstructor(new Class[] {long.class, String.class});
//        cacheName= masterCacheName + "." + implClass.getName();
        loadAdvancedSettings(cacheConfig,entity.getFullPath());
    }

    public EntityStore(RuleServiceProvider rsp, String masterCacheName,Class implclass , Properties cacheConfig) throws Exception{
        this.mrsp = rsp;
        this.cacheConfig = cacheConfig;
        this.masterCacheName=masterCacheName;
        this.logger = rsp.getLogger(EntityStore.class);
        final TypeManager.TypeDescriptor td = rsp.getTypeManager().getTypeDescriptor(implclass);
        if(null != td) {
            this.entityModel = rsp.getProject().getOntology().getEntity(td.getURI());
            loadAdvancedSettings(cacheConfig,td.getURI());
        }
        this.implClass =implclass;
        entityConstructor = implClass.getConstructor(new Class[] {long.class, String.class});
//        cacheName= masterCacheName + "." + implClass.getName();
    }


    protected TypeManager getTypeManager() {
        return mrsp.getTypeManager();
    }

    public NamedCache getCoherenceCache()
    {
        return entityCache;
    }

    public boolean isCustomSetting() {
        return customSetting;
    }

    public Logger getLogger() {
        return logger;
    }

    public Class getImplClass() {
        return implClass;
    }

    public Entity getModelEntity() {
        return entityModel;
    }

    public com.tibco.cep.kernel.model.entity.Entity newEntity(long id, String extId) throws Exception {
        return (com.tibco.cep.kernel.model.entity.Entity) entityConstructor.newInstance(new Object[] {new Long(id), extId});
    }


    public String getCacheName() {
        return masterCacheName + "." + implClass.getName();
    }

    public String getMasterCacheName() {
        return masterCacheName;
    }


    public void connect() throws Exception {
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        try {
            PrintStream ps = new PrintStream(os);
            System.setOut(ps);
            System.setErr(ps);
            entityCache = CacheFactory.getCache(getCacheName(), (ClassLoader) getTypeManager());
            this.getLogger().log(Level.INFO, "Connected to cache: %s", entityCache.getCacheName());
        } catch (Exception e) {
            System.setOut(stdout);
            System.setErr(stderr);
           this.getLogger().log(Level.FATAL, e, "Error connecting cache: %s", this.getCacheName());
           throw new RuntimeException(e);
        }
        catch (Throwable t) {
            System.setOut(stdout);
            System.setErr(stderr);
            this.getLogger().log(Level.FATAL, t, "Error connecting cache: %s", this.getCacheName());
            throw new Exception(t);
        } finally {
            System.setOut(stdout);
            System.setErr(stderr);
        }
    }

    public void release() {
        if(entityCache != null) {
            entityCache.release();
            this.getLogger().log(Level.INFO, "Releasing store cache: %s", this.entityCache.getCacheName());
        }
        entityCache = null;

    }

     public void put(com.tibco.cep.kernel.model.entity.Entity instance) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        this.m_addedObjects.put(new Long(instance.getId()), (ExternalizableLite) instance);

    }


    public com.tibco.cep.kernel.model.entity.Entity get(long id) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        return (com.tibco.cep.runtime.model.element.Concept) entityCache.get(new Long(id));
    }


    public void remove(long id) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        this.m_deletedObjects.add(new Long(id));
    }


    public Iterator getAllEntities() {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        int numRecovered = 0;
        long maxId = Long.MIN_VALUE;
        Set valueSet  = new HashSet();
        Set entrySet = entityCache.entrySet(new AlwaysFilter());
        int totalSize = entrySet.size();
        return entityCache.values().iterator();
//        Iterator iter = entrySet.iterator();
//        while (iter.hasNext()) {
//            com.tibco.cep.runtime.model.element.Concept cept = (com.tibco.cep.runtime.model.element.Concept) ((Map.Entry) iter.next()).getValue();
//        }
    }


    public void beginTransaction() {
        m_currentTransaction= CacheFactory.getLocalTransaction(entityCache);
    }


    public void rollbackTransaction() {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        if (m_currentTransaction != null) {
            m_currentTransaction.rollback();
        }
        m_currentTransaction=null;
    }


    public void commitTransaction() {
        Thread.currentThread().setContextClassLoader((ClassLoader)getTypeManager());
        if (m_currentTransaction != null) {
            m_currentTransaction.prepare();
            m_currentTransaction.commit();
        }
        m_currentTransaction=null;
    }


    /**
     *
     * @param txnMap
     */
    public void addObjectsToTransaction(TransactionMap txnMap) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        txnMap.putAll(m_addedObjects);
        m_addedObjects.clear();
    }


    /**
     *
     * @param txnMap
     */
    public void removeObjectsToTransaction(TransactionMap txnMap) {
        Thread.currentThread().setContextClassLoader((ClassLoader)getTypeManager());
        Iterator allRemovedKeys= m_deletedObjects.iterator();
        while (allRemovedKeys.hasNext()) {
            Long key = (Long) allRemovedKeys.next();
            txnMap.remove(key);
        }
        m_deletedObjects.clear();
    }


    protected ConcurrentMap currentMap() {
        if (m_currentTransaction != null) {
            return m_currentTransaction;
        } else {
            return entityCache;
        }
    }


    public String toString() {
        return ("[ Cache:" + getCacheName() +"]");
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public int getCacheMode() {
        return cacheMode;
    }

    public String getURI() {
        return uri;
    }

    public boolean isCacheEnabled() {
        return isDeployed() && ((cacheMode == CACHE_MODE_CACHEONLY) || (cacheMode == CACHE_MODE_CACHEANDWM));
    }

    protected void loadAdvancedSettings(Properties cacheConfig,String entityUri ) throws Exception {
        ArrayList configs = new ArrayList();
        XiNode advancedSettings= (XiNode) cacheConfig.get("omtgAdvancedEntitySettings");
        String [] cacheModes = new String[] {"memory", "cache", "cacheAndMemory"};
        if (advancedSettings != null) {
            Iterator allRows= advancedSettings.getChildren();
            while (allRows.hasNext()) {
                XiNode rowNode= (XiNode) allRows.next();
                loadEntitySettings(rowNode);
                this.customSetting = true;
            }
        }
        return;
    }

    private void  loadEntitySettings(XiNode configNode) throws Exception{
        this.uri= XiChild.getString(configNode, ExpandedName.makeName("uri"));
        this.isDeployed=XiChild.getBoolean(configNode, ExpandedName.makeName("deployed"));
        String sCacheMode=XiChild.getString(configNode, ExpandedName.makeName("cacheMode"));
        for (int i=0; i < cacheModes.length;i++) {
            if (cacheModes[i].equalsIgnoreCase(sCacheMode)) {
                this.cacheMode=i;
                break;
            }
        }
    }

}
