/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om.impl;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.run.xml.XmlDocument;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;
import com.tibco.be.functions.cluster.DataGridFunctions;
import com.tibco.be.functions.cluster.DataGridFunctionsProvider;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.serializers.coherence.ConceptBytesHandler;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.om.mm.CoherenceClusterMBeanImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.*;
import com.tibco.cep.runtime.service.om.impl.AbstractDaoProvider;
import com.tibco.cep.runtime.service.om.impl.GridFunctionsProviderHelper.NativeGridType;
import com.tibco.cep.runtime.service.om.impl.coherence.CoherenceControlDao;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.BEEntityCache;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;

import static com.tibco.cep.runtime.service.om.impl.GridFunctionsProviderHelper.$chooseDataGridFunctionsProvider;

/*
 * Author: Ashwin Jayaprakash / Date: Sep 22, 2010 / Time: 10:56:06 AM
 */
@LogCategory("coherence.runtime.cluster.om.daoprovider")
public class CoherenceDaoProvider extends AbstractDaoProvider {
    protected CoherenceInvocationService invocationSvc;

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(CoherenceDaoProvider.class);

    // We are ensuring that the core SerializableLite interface extends tangosol externalizableLite. This makes every entity Tangosol serializable.
    static {
        try {
            BEClassLoader.injectInterfaceExtension("com.tibco.cep.runtime.model.serializers.SerializableLite",
                    "com.tangosol.io.ExternalizableLite");
            System.setProperty("com.tibco.cep.runtime.model.serializers.factory",
                    "com.tibco.cep.runtime.model.serializers.coherence.CoherenceSerializerFactory");
        }
        catch (Throwable t) {
            IllegalStateException e =
                    new IllegalStateException("Error occurred while injecting ExternalizableLite interface", t);

            /*
             * Log this here. There is not guarantee that the thrown exception will be reported correctly because
             * this class loading will fail and will cause strange errors.
             */
            e.printStackTrace();

            throw e;
        }
    }

    public CoherenceDaoProvider() {
    	
    }

    @Override
    protected void initHook() throws Exception {
        initCoherence();

        //----------------

        ClassLoader classLoader = cluster.getRuleServiceProvider().getClassLoader();
        ConceptBytesHandler.init(classLoader);

        //----------------

        DataGridFunctionsProvider dgfp = (DataGridFunctionsProvider) $chooseDataGridFunctionsProvider(NativeGridType.coherence);
        DataGridFunctions.setProvider(dgfp);

        new CoherenceClusterMBeanImpl(this.cluster);
    }

    private void initCoherence() throws Exception {
        String storageEnabled = System.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
        System.setProperty("tangosol.coherence.distributed.localstorage", storageEnabled);

        XmlElement configElement = getConfigurableCacheFactoryConfig();
        CacheFactory.setCacheFactoryBuilderConfig(configElement);

        CacheFactory.getCluster()
                .setContextClassLoader((ClassLoader) cluster.getRuleServiceProvider().getTypeManager());
        CacheFactory.ensureCluster();

        invocationSvc = new CoherenceInvocationService(this);
    }

    private XmlElement getConfigurableCacheFactoryConfig() throws Exception {
        CacheManagerConfig cacheManagerConfig = getCacheManagerConfig();
        BackingStoreConfig backingStoreConfig = cacheManagerConfig.getBackingStore();
        String persistenceOption = CddTools.getValueFromMixed(backingStoreConfig.getPersistenceOption());

        boolean sharedAll = false;
        if ((persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) == 0) ||
            (persistenceOption.compareToIgnoreCase(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL_ALT) == 0)) {
            sharedAll = true;
        }

        if (backingStoreConfig != null && sharedAll ) {
            backingStoreEnabled = true;
            isCacheAside = Boolean.valueOf(CddTools.getValueFromMixed(backingStoreConfig.getCacheAside()));
            cacheLoaderClassname = CddTools.getValueFromMixed(backingStoreConfig.getCacheLoaderClass());
        }

        //---------------

        if (cacheLoaderClassname == null || cacheLoaderClassname.length() == 0) {
            cacheLoaderClassname =
                    "com.tibco.cep.runtime.service.om.impl.coherence.cluster.backingstore.BECoherenceJdbcStore";
        }
        else {
            if (!cacheLoaderClassname
                    .equals("com.tibco.cep.runtime.service.om.impl.coherence.cluster.backingstore.BECoherenceJdbcStore") &&
                    !cacheLoaderClassname
                            .equals("com.tibco.cep.runtime.service.om.impl.coherence.cluster.backingstore.BECoherenceOracleStore")) {

                logger.log(Level.WARN, "Ignoring user defined cache loader class : " + cacheLoaderClassname);

                cacheLoaderClassname =
                        "com.tibco.cep.runtime.service.om.impl.coherence.cluster.backingstore.BECoherenceJdbcStore";
            }
        }

        //---------------

        if (backingStoreEnabled) {
            System.setProperty("be.backingstore.cacheLoaderClass", cacheLoaderClassname);

            logger.log(Level.INFO, "Using cache loader class : " + cacheLoaderClassname);
        }

        //---------------

        String backupCountStr = CddTools.getValueFromMixed(cacheManagerConfig.getBackupCopies());
        backupCountStr = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(backupCountStr).toString();
        
        if (null != backupCountStr && !backupCountStr.trim().isEmpty()) {
            System.setProperty("tangosol.coherence.distributed.backupcount", backupCountStr);
        }

        //---------------

        final ClassLoader classLoader = getClass().getClassLoader();

        final String tangosolOverrideProperty = "tangosol.coherence.override";
        final String defaultTangosolOverride = "tangosol-coherence-override-tibco-be.xml";

        String tangosolOverride = System.getProperty(tangosolOverrideProperty);
        tangosolOverride = (tangosolOverride != null) ? tangosolOverride.trim() : "";
        if (tangosolOverride.length() == 0) {
            System.setProperty(tangosolOverrideProperty, defaultTangosolOverride);
            tangosolOverride = defaultTangosolOverride;
        }

        URL url = classLoader.getResource(tangosolOverride);
        String tangosolOverrideFilePath = (url == null) ? "<unknown>" : url.toExternalForm();
        logger.log(Level.INFO, "[%s] is using tangosol override file [%s: %s]", getClass().getSimpleName(),
                tangosolOverride, tangosolOverrideFilePath);

        //---------------

        InputStream is = null;
        String configFile = System.getProperty("tangosol.coherence.cacheconfig");
        if ((configFile == null) || (configFile.trim().length() == 0)) {
            configFile = "coherence-cache-config-jdbc.xml";

            if (cacheLoaderClassname.endsWith("BECoherenceOracleStore")) {
                configFile = "coherence-cache-config.xml";
            }

            is = classLoader.getResourceAsStream(configFile);
            if (is == null) {
                throw new FileNotFoundException(
                        "Could not find coherence configuration file [" + configFile + "] in the resource path.");
            }

            url = classLoader.getResource(configFile);
            String configFilePath = (url == null) ? configFile : url.toExternalForm();
            logger.log(Level.INFO, "[%s] is using configuration file [%s]", getClass().getSimpleName(), configFilePath);

            System.setProperty("tangosol.coherence.cacheconfig", configFilePath);
        }
        else {
            File f = new File(configFile);
            if (!f.exists()) {
                throw new FileNotFoundException(
                        "Could not find user defined coherence configuration file [" + configFile + "].");
            }

            is = new FileInputStream(f);

            logger.log(Level.INFO, "[%s] is using configuration file [%s]", getClass().getSimpleName(), configFile);
        }

        //---------------

        XmlDocument xmlDoc = XmlHelper.loadXml(is);
        is.close();

        return xmlDoc;
    }

    @Override
    public void start() throws Exception {
        if (backingStoreEnabled && isCacheAside) {
            //TODO: Fatih - Ask Vincent if this is needed at all!?
            //mDBManager = DBManager.getInstance();
            //mDBManager.init();

            //TODO: Fatih - Ask Vincent what was this!?
            //mDBManager.createDBAdapters(dbAdapters);
            //tableName = mDBManager.getTableName(className);
        }

        logger.log(Level.INFO, "Started [" + getClass().getSimpleName() + "]");
    }

    //-----------------

    public String sanitizeName(String s) {
        s = s.replace('.', '_').replace('$', '_').replace(' ', '_');

        //Start UTF-8 hack
        s = s.replace('+', '_').replace('/', '_').replace('=', '_');
        //End UTF-8 hack

        return s;
    }

    @Override
    protected DaoSeed createDaoSeed(CacheType cacheType, String clusterName, @Optional String agentName,
                                    String cacheName, Object... props) {
        if (ManagedObjectManager.isOn()) {
        	String name;
        	if (descriptiveNames) {
        		name = cacheType.getAlias()
	                    + "-" + sanitizeName(clusterName)
	                    + "-" + (agentName == null ? "" : sanitizeName(agentName))
	                    + "-" + sanitizeName(cacheName);
        	}
        	else {
        		name = sanitizeName(clusterName)
	                    + "-" + (agentName == null ? "" : sanitizeName(agentName))
	                    + "-" + sanitizeName(cacheName);
        	}
            String entityCacheSizeStr = System.getProperty(SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(), "10000");
            entityCacheSizeStr = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(entityCacheSizeStr).toString();
            return new DaoSeed(cacheType, name, Integer.parseInt(entityCacheSizeStr), props);
        }

        return super.createDaoSeed(cacheType, clusterName, agentName, cacheName, props);
    }

    @Override
    protected <K, E extends Entity> EntityDao makeNew(Class<E> entityClass, EntityDaoConfig daoConfig,
                                                      DaoSeed daoSeed) {
        BEEntityCache<E> entityDao = new BEEntityCache<E>();

        if (ManagedObjectManager.isOn()) {
            entityDao.init(cluster, entityClass, "tx-" + daoSeed.getName(), daoConfig);
        }
        else {
            entityDao.init(cluster, entityClass, daoSeed.getName(), daoConfig);
        }

        return entityDao;
    }

    @Override
    protected <K, V> ControlDao<K, V> makeNew(ControlDaoType daoType, DaoSeed daoSeed) {
        return new CoherenceControlDao<K, V>(daoType, daoSeed, cluster);
    }

    @Override
    public GroupMemberMediator newGroupMemberMediator() {
        return new CoherenceGroupMemberMediator(this);
    }

    @Override
    public InvocationService getInvocationService() {
        return this.invocationSvc;
    }

    public CacheService getCoherenceReplicatedService() {
        return (CacheService) CacheFactory.getConfigurableCacheFactory().ensureService("ReplicatedCache");
    }

    public com.tangosol.net.InvocationService getCoherenceInvocationService() {
        return (com.tangosol.net.InvocationService) CacheFactory.getConfigurableCacheFactory()
                .ensureService("InvocationService");
    }

    @Override
    public GenericBackingStore getBackingStore() {
        if (backingStore != null) {
            return backingStore;
        }
        if (backingStoreEnabled) {
            try {
                logger.log(Level.INFO, "Creating BackingStore (cache-aside=%s class=%s)",
                        isCacheAside, cacheLoaderClassname);
                Class recClz = Class.forName(cacheLoaderClassname);
                Constructor cons = recClz.getConstructor(new Class[]{});
                backingStore = (BackingStore) cons.newInstance(new Object[]{});
            }
            catch (Exception ex) {
                logger.log(Level.WARN, ex, "Creating BackingStore failed (class=%s)", cacheLoaderClassname);
                throw new RuntimeException(ex);
            }
        }
        return backingStore;
    }

    public void recoverCluster(Cluster cluster) throws Exception {
        
    }

    @Override
    public void modelChanged() {}

}
