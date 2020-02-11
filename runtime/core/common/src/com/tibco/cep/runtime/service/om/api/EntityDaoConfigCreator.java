package com.tibco.cep.runtime.service.om.api;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.CompositeIndexesConfig;
import com.tibco.be.util.config.cdd.DomainObjectConfig;
import com.tibco.be.util.config.cdd.DomainObjectsConfig;
import com.tibco.be.util.config.cdd.FieldEncryptionConfig;
import com.tibco.be.util.config.cdd.IndexesConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.ProcessJob;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterCapability;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig.CacheMode;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.SystemProperty;

/*
 * Author: Ashwin Jayaprakash / Date: Sep 27, 2010 / Time: 2:34:01 PM
 */
public class EntityDaoConfigCreator {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(EntityDaoConfigCreator.class);

    /**
     * @param cluster
     * @param entityClass
     * @return
     * @throws RuntimeException
     */
    public static EntityDaoConfig createAndPutIfAbsent(Cluster cluster, Class<? extends Entity> entityClass, Map<Class, EntityDaoConfig> entityDaoConfigs) {
        if (entityClass != null) {
            EntityDaoConfig config = entityDaoConfigs.get(entityClass);
            if (config == null) {
                config = create(cluster, entityClass, entityDaoConfigs);
                entityDaoConfigs.put(entityClass, config);
            }
            return config;
        }
        return null;
    }

    private static EntityDaoConfig create(Cluster cluster, Class<? extends Entity> entityClass, HashMap<Class, EntityDaoConfig> entityDaoConfigs) {
        RuleServiceProvider rsp = cluster.getRuleServiceProvider();

        Ontology ontology = rsp.getProject().getOntology();

        TypeManager typeManager = rsp.getTypeManager();
        TypeDescriptor typeDescriptor = typeManager.getTypeDescriptor(entityClass);
        if (typeDescriptor == null) {
            throw new RuntimeException(
                    "Error occurred while retrieving [" + TypeDescriptor.class.getSimpleName() +
                            "] for entity class [" + entityClass.getName() + "]. No mapping found.");
        }

        EntityDaoConfig copy = null;
        if (typeDescriptor.getType() == TypeManager.TYPE_STATEMACHINE) {
            String ownerURI = ModelNameUtil.generatedClassNameToStateMachineOwnerURI(entityClass.getName());
            com.tibco.cep.designtime.model.Entity ownerModel = ontology.getEntity(ownerURI);
            if (ownerModel instanceof com.tibco.cep.designtime.model.element.Concept) {
                com.tibco.cep.designtime.model.element.Concept parentConcept =
                        ((com.tibco.cep.designtime.model.element.Concept)ownerModel);
                TypeDescriptor parentTD = typeManager.getTypeDescriptor(parentConcept.getFullPath());
                if (parentTD != null) {
                    Class parentClass = parentTD.getImplClass();
                    copy = createAndPutIfAbsent(cluster, parentClass, entityDaoConfigs);
                }
            }
        }

        String entityUri = typeDescriptor.getURI();
        if (ProcessJob.class.isAssignableFrom(typeDescriptor.getImplClass())) {
            try {
                Field field = typeDescriptor.getImplClass().getDeclaredField("designTimeType");
                entityUri = (String) field.get(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        Properties properties = rsp.getProperties();
        ClusterConfig cc = (ClusterConfig) properties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
        CacheManagerConfig cacheManagerConfig = cc.getObjectManagement().getCacheManager();

        DomainObjectsConfig domainObjectsConfig = cacheManagerConfig.getDomainObjects();

        //---------------

        //Map<String, DomainObjectConfig> domainObjectConfigMap = domainObjectsConfig.getConfigs();
        //DomainObjectConfig domainObjectConfig = domainObjectConfigMap.get(entityUri);

        DomainObjectConfig domainObjectConfig = (DomainObjectConfig) CddTools.findByUri(
                domainObjectsConfig.getDomainObject(),
                entityUri);

        DomainObjectConfig copyDOC = (copy == null) ? null : CddTools.findByUri(
                domainObjectsConfig.getDomainObject(),
                copy.getUri());

        EntityDaoConfig dc = new EntityDaoConfig();

        dc.setName(entityUri);

        dc.setUri(entityUri);

        dc.setRuleFunctionUri(domainObjectsConfig.getPreprocessor(entityUri));

        CacheMode cacheMode = CacheMode.parseString(domainObjectsConfig.getMode(entityUri));
        cacheMode = (copy == null) ? cacheMode : copy.getCacheMode();
        dc.setCacheMode(cacheMode);

        dc.setConstant((copy == null) ? domainObjectsConfig.getConstant(entityUri) : copy.isConstant());

        //TODO: Suresh: where do we get this from?
        //dc.setDeployed();

        boolean subscribed = cacheMode == CacheMode.CacheAndMemory;
        if (null != (domainObjectsConfig.getSubscribe(entityUri))) {
            subscribed = domainObjectsConfig.getSubscribe(entityUri);
        }
        dc.setSubscribed((copy == null) ? subscribed : copy.isSubscribed());

        //---------------

        /**
          if (Concept.class.isAssignableFrom(typeDescriptor.getImplClass()))
          if (entityClass instanceof com.tibco.cep.designtime.model.element.Concept)
         */
        com.tibco.cep.designtime.model.Entity entity = ontology.getEntity(entityUri);
        if (entity instanceof Concept) {
        	OverrideConfig defaultConceptTTLConfig = domainObjectsConfig.getConceptTTL();
        	String defaultConceptTTL = CddTools.getValueFromMixed(defaultConceptTTLConfig);
            
        	//Use override domain object config if available else use from Default domain objects config.
        	OverrideConfig overrideConfig = domainObjectConfig != null ? domainObjectConfig.getConceptTTL() : domainObjectsConfig.getConceptTTL();
            String conceptTTL = CddTools.getValueFromMixed(overrideConfig, defaultConceptTTL);
            if(GvCommonUtils.isGlobalVar(conceptTTL)){
            	String gv=GvCommonUtils.stripGvMarkers(conceptTTL);
            	if(rsp.getGlobalVariables().getVariable(gv) !=null){
            		conceptTTL = rsp.getGlobalVariables().getVariable(gv).getValueAsString();
            	}else{
            		LOGGER.log(Level.WARN, "%s Concept failed to set TTL as %s, %s is undefined, using -1 as TTL.", entityUri, conceptTTL,conceptTTL);
            		conceptTTL = "-1";
            	}
            }
            LOGGER.log(Level.TRACE, "%s Concept TTL is [ %s - %s ]", entityUri, conceptTTL, dc.getConceptTTL());
            try {
            	long ttlSec = Long.parseLong(conceptTTL);
            	//user value in seconds, convert to milliseconds expected by AS API downstream.. x 1000
            	dc.setConceptTTL(ttlSec > 0 ? ttlSec*1000 : ttlSec);
            } catch (Exception e) {
            	LOGGER.log(Level.WARN, "%s Concept failed to set TTL as %s, using -1 as TTL.", entityUri, conceptTTL);
            	dc.setConceptTTL(-1);
            }
            LOGGER.log(Level.INFO, "%s Concept assigned TTL is [ %s ]", entityUri, dc.getConceptTTL());
        } else {
            dc.setConceptTTL(-1);
            LOGGER.log(Level.TRACE, "%s assigned TTL is [ %s ]", entityUri, dc.getConceptTTL());
        }

        //---------------

        BackingStoreConfig backingStoreConfig = cacheManagerConfig.getBackingStore();
        String persistenceOption = CddTools.getValueFromMixed(backingStoreConfig.getPersistenceOption());
        //final boolean isBackingStoreEnabled = (null != backingStoreConfig) &&
          //      persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL);

        final boolean isBackingStoreEnabled = (null != backingStoreConfig) &&
                !persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_NONE);

        if (isBackingStoreEnabled) {
            DatabaseConfig databaseConfig = new DatabaseConfig();
            DatabaseConfig copyDB = (copy == null) ? null : copy.getDatabaseConfig();

            Long fetchSize = domainObjectsConfig.getPreLoadFetchSize(entityUri);
            databaseConfig.setLoadFetchSize((copyDB == null) ? fetchSize : copyDB.getLoadFetchSize());

            boolean recoveryEnabled = domainObjectsConfig.getPreLoadHandles(entityUri);
            databaseConfig.setRecoverOnStartup((copyDB == null) ? recoveryEnabled : copyDB.isRecoverOnStartup());

            boolean preLoadEnabled = domainObjectsConfig.getPreLoadEnabled(entityUri);
            databaseConfig.setLoadOnStartup((copyDB == null) ? preLoadEnabled : copyDB.isLoadOnStartup());

            if (copyDOC != null) {
                if (copyDOC.getBackingStore() != null) {
                    if (!Boolean.valueOf(CddTools.getValueFromMixed(
                            copyDOC.getBackingStore().getEnabled()))) {
                        databaseConfig = null;
                    }
                }
            } else if (domainObjectConfig != null) {
                if (domainObjectConfig.getBackingStore() != null) {
                    if (!Boolean.valueOf(CddTools.getValueFromMixed(
                            domainObjectConfig.getBackingStore().getEnabled()))) {
                        databaseConfig = null;
                    }
                }
            }

            dc.setDatabaseConfig(databaseConfig);
        }

        //---------------

        EnumMap<ClusterCapability, Object> clusterCapabilities = cluster.getCapabilities();

        DataCacheConfig dataCacheConfig = new DataCacheConfig();
        DataCacheConfig copyDCG = (copy == null) ? null : copy.getDataCacheConfig();

        String backupCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
        backupCountStr = rsp.getGlobalVariables().substituteVariables(backupCountStr).toString();
        dataCacheConfig.setBackupCount(Integer.parseInt(backupCountStr));

        if (isBackingStoreEnabled) {
            boolean isLimited = domainObjectsConfig.getCacheLimited(entityUri);
            isLimited = (copyDCG == null) ? isLimited : copyDCG.isLimited();
            dataCacheConfig.setLimited(isLimited);
            if (isLimited) {
                String entityCacheSizeStr = System.getProperty(SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(), "10000");
                entityCacheSizeStr = rsp.getGlobalVariables().substituteVariables(entityCacheSizeStr).toString();
                dataCacheConfig.setLimitedSize(Long.parseLong(entityCacheSizeStr));
            }
            dataCacheConfig.setCacheAside(Boolean.valueOf(
                    CddTools.getValueFromMixed(backingStoreConfig.getCacheAside())));
        }
        else {
            dataCacheConfig.setLimited(false);
        }

        dataCacheConfig.setEvictOnUpdateRequired((copyDCG == null) ? domainObjectsConfig.getEvictOnUpdate(entityUri) : copyDCG.isEvictOnUpdateRequired());

        createIndexConfig(domainObjectConfig, dataCacheConfig);

        createEncryptionConfig(domainObjectConfig, dataCacheConfig);

        dataCacheConfig.setVersionCheckRequired((copyDCG == null) ? domainObjectsConfig.getCheckForVersion(entityUri) : copyDCG.isVersionCheckRequired());

        dc.setDataCacheConfig(dataCacheConfig);

        // DB Concepts:
        // - Ignore the backing store even if it exists in the override or in the DEFAULT
        // - If override doesn't exist use mode "In Memory", even if DEFAULT mode is CACHE
        // - If override exist, use user setting
        boolean isDbConceptConfig = isDataBaseConcept(ontology, entityUri);
        if (isDbConceptConfig) {
            String mode = getModeForDBConcept(domainObjectsConfig, entityUri);
            CacheMode cMode = CacheMode.parseString(mode);
            dc.setCacheMode(cMode);
            dc.setDatabaseConfig(null);
        }

        return dc;
    }

    protected static void createIndexConfig(DomainObjectConfig domainObjectConfig, DataCacheConfig dataCacheConfig) {
        if (domainObjectConfig == null) {
            return;
        }

        LinkedList<IndexConfig> ics = new LinkedList<IndexConfig>();

        IndexesConfig indexesConfig = domainObjectConfig.getIndexes();
        if (indexesConfig == null) {
            return;
        }
        for (com.tibco.be.util.config.cdd.IndexConfig indexConfig : indexesConfig.getIndex()) {
            // The CDD does not have a facility to differentiate between indexes with multiple fields and multiple indexes with single fields.
            for (OverrideConfig o : indexConfig.getProperty()) {
                String indexFieldName = CddTools.getValueFromMixed(o);
                IndexConfig ic = new IndexConfig();
                ic.setFieldNames(new String[]{indexFieldName});
                ic.setSorted(true);
                ic.setSortAscending(true);

                ics.add(ic);
            }
        }
        
        CompositeIndexesConfig compIndexesConfig = domainObjectConfig.getCompositeIndexes();
        
		if (compIndexesConfig != null) {
			addCompositeIndexes(compIndexesConfig, ics);
		}
        
        if (ics.size() > 0) {
            dataCacheConfig.setIndexConfigs(ics.toArray(new IndexConfig[ics.size()]));
        }
    }
    
	private static void addCompositeIndexes(CompositeIndexesConfig compIndexesConfig,
			LinkedList<IndexConfig> indexConfig) {

		for (com.tibco.be.util.config.cdd.CompositeIndexConfig compIndexConfig : compIndexesConfig
				.getCompositeIndex()) {
			CompositeIndexConfig cic = new CompositeIndexConfig();
			String compIndexName = CddTools.getValueFromMixed(compIndexConfig.getName());
			cic.setIndexName(compIndexName);
			String[] fieldNames = new String[compIndexConfig.getCompositeIndexProperties().getProperty().size()];
			int i = 0;
			for (OverrideConfig o : compIndexConfig.getCompositeIndexProperties().getProperty()) {
				String indexFieldName = CddTools.getValueFromMixed(o);
				fieldNames[i++] = indexFieldName;
			}
			cic.setFieldNames(fieldNames);
			cic.setSorted(true);
			cic.setSortAscending(true);
			indexConfig.add(cic);
		}
	}

    protected static void createEncryptionConfig(DomainObjectConfig domainObjectConfig, DataCacheConfig dataCacheConfig) {
        if (domainObjectConfig == null) {
            return;
        }

        EncryptionConfig ec = new EncryptionConfig();
        List<String> ecs = new LinkedList<String>();

        FieldEncryptionConfig encryptionConfig = domainObjectConfig.getEncryption();
        if (encryptionConfig == null) {
            return;
        }
        for (OverrideConfig o : encryptionConfig.getProperty()) {
            String encryptedFieldName = CddTools.getValueFromMixed(o);
            ecs.add(encryptedFieldName);
        }

        if (ecs.size() > 0) {
            ec.setFieldNames(ecs);
            dataCacheConfig.setEncryptionConfig(ec);
        }
    }

    public static EntityDaoConfig createDefault(Cluster cluster, Class clazz) {
        return createDefault(cluster, clazz, null);
    }

    public static EntityDaoConfig createDefault(Cluster cluster, Class clazz, CacheMode cacheModeOverride) {

        RuleServiceProvider rsp = cluster.getRuleServiceProvider();

        EntityDaoConfig dc = new EntityDaoConfig();

        dc.setName(clazz.getName());

        dc.setUri(clazz.getName());

        dc.setRuleFunctionUri(null);

        Properties properties = rsp.getProperties();
        ClusterConfig cc = (ClusterConfig) properties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
        CacheManagerConfig cacheManagerConfig = cc.getObjectManagement().getCacheManager();
        DomainObjectsConfig domainObjectsConfig = cacheManagerConfig.getDomainObjects();

        CacheMode cacheMode = cacheModeOverride;
        if (cacheMode == null) {
            String cacheModeStr = domainObjectsConfig.getDefaultMode().getName();
            cacheMode = CacheMode.parseString(cacheModeStr);
        }
        dc.setCacheMode(cacheMode);

        BackingStoreConfig backingStoreConfig = cacheManagerConfig.getBackingStore();
        String persistenceOption = CddTools.getValueFromMixed(backingStoreConfig.getPersistenceOption());
        final boolean isBackingStoreEnabled = (null != backingStoreConfig) &&
                !persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_NONE);
        if (isBackingStoreEnabled && (cacheMode != CacheMode.Memory)) {
            DatabaseConfig databaseConfig = new DatabaseConfig();
            dc.setDatabaseConfig(databaseConfig);
        }

        DataCacheConfig dataCacheConfig = new DataCacheConfig();

        String backupCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
        backupCountStr = rsp.getGlobalVariables().substituteVariables(backupCountStr).toString();
        dataCacheConfig.setBackupCount(Integer.parseInt(backupCountStr));

        if (isBackingStoreEnabled) {
            String cacheLimitedStr = CddTools.getValueFromMixed(domainObjectsConfig.getCacheLimited());
            boolean isCacheLimited = Boolean.parseBoolean(cacheLimitedStr);
            dataCacheConfig.setLimited(isCacheLimited);
            if (isCacheLimited) {
                String entityCacheSizeStr = System.getProperty(SystemProperty.CLUSTER_LIMITED_SIZE.getPropertyName(), "10000");
                entityCacheSizeStr = rsp.getGlobalVariables().substituteVariables(entityCacheSizeStr).toString();
                dataCacheConfig.setLimitedSize(Long.parseLong(entityCacheSizeStr));
            }
            dataCacheConfig.setCacheAside(Boolean.valueOf(
                    CddTools.getValueFromMixed(backingStoreConfig.getCacheAside())));
        }
        else {
            dataCacheConfig.setLimited(false);
        }

        dataCacheConfig.setEvictOnUpdateRequired(Boolean.parseBoolean(CddTools.getValueFromMixed(domainObjectsConfig.getEvictOnUpdate())));
        dataCacheConfig.setVersionCheckRequired(Boolean.parseBoolean(CddTools.getValueFromMixed(domainObjectsConfig.getCheckForVersion())));

        dc.setDataCacheConfig(dataCacheConfig);

        return dc;
    }

    protected static boolean isDataBaseConcept(Ontology ontology,
                                               String entityUri) {
        com.tibco.cep.designtime.model.Entity entity = ontology
                .getEntity(entityUri);
        if (entity instanceof Concept) {
            Concept concept = (Concept) entity;
            Map extProperties = concept.getExtendedProperties();
            if (extProperties != null) {
                if (extProperties.containsKey("SCHEMA_NAME") &&
                        extProperties.containsKey("OBJECT_NAME") &&
                        extProperties.containsKey("OBJECT_TYPE")) {
                    return true;
                }
            }
        }

        return false;
    }

    protected static String getModeForDBConcept(DomainObjectsConfig domainObjectsConfig,
                                                String entityUri) {

        String mode = domainObjectsConfig.getMode(entityUri);

        DomainObjectConfig domainObjectConfig = (DomainObjectConfig) CddTools.findByUri(
                domainObjectsConfig.getDomainObject(),
                entityUri);

        if (domainObjectConfig == null) {
            mode = CacheMode.Memory.name();
        }
        else {
            mode = domainObjectConfig.getMode().getName();
            if ((null == mode) || mode.trim().isEmpty()) {
                mode = CacheMode.Memory.name();
            }
        }

        return mode;
    }
    
}
