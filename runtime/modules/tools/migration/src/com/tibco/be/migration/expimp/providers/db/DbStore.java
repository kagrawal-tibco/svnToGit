package com.tibco.be.migration.expimp.providers.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.migration.BEMigrationConstants;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 27, 2008
 * Time: 10:02:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class DbStore {
    private Map entityDbStores = new HashMap();
    private ExpImpStats stats;
    private ExpImpContext context;
    protected RuleServiceProvider mrsp;
    protected Logger logger;
    protected String barName;
    protected String connStr;
    boolean initDone = false;
    private String masterCacheName;
    private Properties omConfig;
    private int partitionId;

    public DbStore(ExpImpContext context, ExpImpStats stats) {
        this.context = context;
        this.stats = stats;
        this.mrsp = context.getRuleServiceProvider();
        this.logger = this.mrsp.getLogger(DbStore.class);
        this.barName = context.getEnvironmentProperties().getProperty(BEMigrationConstants.PROP_BAR_NAME);
        this.omConfig = getOmConfig();
        this.connStr =  context.getEnvironmentProperties().getProperty(BEMigrationConstants.PROP_DB_CONN_STR);
        this.partitionId = Integer.valueOf(context.getEnvironmentProperties().getProperty(BEMigrationConstants.PROP_PARTITION_ID)).intValue();
    }

    public void init() throws Exception {
        loadOntology();
        registerTypes();

        this.getLogger().log(Level.DEBUG, "Register Cache Ids....");

        Iterator allCaches = entityDbStores.values().iterator();
        while (allCaches.hasNext()) {
            EntityStore store = (EntityStore) allCaches.next();
            store.init();
            store.connect();
        }

        initDone = true;
    }

    public String getBarName() {
        return barName;
    }

    public String getConnectionString() {
        return this.connStr;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return mrsp;
    }

    public ExpImpContext getContext() {
        return context;
    }

    public ExpImpStats getStats() {
        return stats;
    }

    BEArchiveResource getBEArchiveResource(String barName) {
        BEArchiveResource beArchive;
        Collection archives = getRuleServiceProvider().getProject().getDeployedBEArchives();
        Iterator i = archives.iterator();
        while (i.hasNext()) {
            beArchive = (BEArchiveResource) i.next();
            if (beArchive.getName().equals(barName)) {
                return beArchive;
            }
        }
        return null;
    }

     public Properties getOmConfig() {
        if (null == this.omConfig) {
            BEArchiveResource bar = getBEArchiveResource(barName);
            if (bar == null) {
                this.logger.log(Level.FATAL, "Can not find BE archive with the BAR name provided: %s", this.barName);
                System.exit(-1);
            }
            omConfig = new Properties();

            final GlobalVariables gvs = getRuleServiceProvider().getProject().getGlobalVariables();
            for (Iterator it = bar.getCacheConfig().entrySet().iterator(); it.hasNext();) {
                final Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() instanceof String) {
                    omConfig.put(entry.getKey(), gvs.substituteVariables(entry.getValue().toString()).toString());
                } else {
                    omConfig.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return this.omConfig;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getMasterCacheName() {
        if(null == this.masterCacheName) {
            this.masterCacheName = getOmConfig().getProperty(Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME, getBarName());
        }
        return this.masterCacheName;
    }

    public String getEntityStoreName(String entityClassName) {
        return getMasterCacheName() + "." + entityClassName;
    }


    public EntityStore getEntityStore(String cacheName) {
        return getEntityStoreForClass(cacheName.substring(0, getMasterCacheName().length() + 1));
    }

    public EntityStore getEntityStoreForClass(String entityClassName) {
        return (EntityStore) entityDbStores.get(entityClassName);
    }

    public EntityStore getEntityStoreFromUri(String uri) {
        return getEntityStoreForClass(getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri).getImplClass().getName());
    }


    public EntityStore[] getAllStores() {
        return (EntityStore[]) entityDbStores.values().toArray(new EntityStore[0]);
    }

    public void registerTypes() throws Exception {
        TypeManager typeManager = getRuleServiceProvider().getTypeManager();
        Collection types = typeManager.getTypeDescriptors(
                                        TypeManager.TYPE_CONCEPT |
                                        TypeManager.TYPE_NAMEDINSTANCE |
                                        TypeManager.TYPE_SIMPLEEVENT |
                                        TypeManager.TYPE_TIMEEVENT |
                                        TypeManager.TYPE_STATEMACHINE );
        Iterator ite = types.iterator();
        while(ite.hasNext()) {
            TypeManager.TypeDescriptor typeDescriptor = (TypeManager.TypeDescriptor) ite.next();
            registerType(typeDescriptor);

        }

        EntityStore entityStore = getTypeCache(StateMachineConceptImpl.StateTimeoutEvent.class);
//        m_objectCaches.put(entityStore.getImplClass().getName(), entityStore);
//        this.getLogger().log(Level.INFO, "Registered Entity Store for type :"+entityStore.getImplClass().getName());
    }

    private EntityStore getTypeCache(Class implClass) throws Exception {
        EntityStore entityStore = null;
        if(entityDbStores.containsKey(implClass.getName())) {
            return (EntityStore) entityDbStores.get(implClass.getName());
        }
        if (Concept.class.isAssignableFrom(implClass)) {
            entityStore = new EntityStore(this,implClass,getPartitionId(),getConnectionString());
            //entityStore = new ConceptStore(getRuleServiceProvider(), getMasterCacheName(), implClass, getCacheConfig());
            entityDbStores.put(implClass.getName(), entityStore);
            this.getLogger().log(Level.INFO, "Registered entity type: %s", implClass.getName());
        } else if (Event.class.isAssignableFrom(implClass)) {
            if (TimeEvent.class.isAssignableFrom(implClass)) {
                //entityStore = new TimeEventStore(getRuleServiceProvider(), getMasterCacheName(), implClass, getCacheConfig());
                entityStore = new EntityStore(this,implClass,getPartitionId(),getConnectionString());
                entityDbStores.put(implClass.getName(), entityStore);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", implClass.getName());
            } else {
                //entityStore = new EventStore(getRuleServiceProvider(), getMasterCacheName(), implClass, getCacheConfig());
                entityStore = new EntityStore(this,implClass,getPartitionId(),getConnectionString());
                entityDbStores.put(implClass.getName(), entityStore);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", implClass.getName());
            }
        }
        return entityStore;
    }

    public void registerType(TypeManager.TypeDescriptor td) {
        TypeManager tm = getRuleServiceProvider().getTypeManager();
        Class implClass = td.getImplClass();
        Entity entity = getRuleServiceProvider().getProject().getOntology().getEntity(td.getURI());
        try {
            if (entityDbStores.containsKey(td.getImplClass().getName())) {
                return;
            }
            String modelPath = td.getURI();
            if (td.getType() == TypeManager.TYPE_STATEMACHINE) {
                String conceptPath = modelPath.substring(0, modelPath.lastIndexOf('/'));
                String smName = modelPath.substring(modelPath.lastIndexOf('/') + 1);
                com.tibco.cep.designtime.model.element.Concept cept
                        = getRuleServiceProvider()
                        .getProject()
                        .getOntology()
                        .getConcept(conceptPath);

                loadEntity(cept, getOmConfig());
            } else {
                if (entity != null) {
                    loadEntity(entity, getOmConfig());
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    void loadOntology() throws Exception {
        Ontology ontology= getRuleServiceProvider().getProject().getOntology();        // Add all the concepts
        Iterator allConcepts= ontology.getConcepts().iterator();
        while (allConcepts.hasNext()) {
            com.tibco.cep.designtime.model.element.Concept cept = (com.tibco.cep.designtime.model.element.Concept) allConcepts.next();
            loadEntity(cept,getOmConfig());
        }
        // Add all the events
        Iterator allEvents= ontology.getEvents().iterator();
        while (allEvents.hasNext()) {
            com.tibco.cep.designtime.model.event.Event evt = (com.tibco.cep.designtime.model.event.Event) allEvents.next();
            loadEntity(evt, getOmConfig());
        }
    }

    private void loadEntity(com.tibco.cep.designtime.model.Entity entity, Properties cacheConfig) throws Exception{
        if (entity instanceof com.tibco.cep.designtime.model.element.Concept) {
            com.tibco.cep.designtime.model.element.Concept cept = (com.tibco.cep.designtime.model.element.Concept) entity;
            //ConceptStore conceptCache = new ConceptStore(getRuleServiceProvider(), getMasterCacheName(), entity, getCacheConfig());
            EntityStore entityStore = new EntityStore(this,entity,getPartitionId(),getConnectionString());
            entityDbStores.put(entityStore.getImplClass().getName(), entityStore);
            this.getLogger().log(Level.INFO, "Registered entity type: %s", entityStore.getImplClass().getName());
            List allMachines = cept.getStateMachines();
            for (int i = 0; null != allMachines && i < allMachines.size(); i++) {
                StateMachine sm = (StateMachine) allMachines.get(i);
                entityStore = new EntityStore(this,sm,getPartitionId(),getConnectionString());
                entityDbStores.put(entityStore.getImplClass().getName(), entityStore);
                //StateMachineStore smCache = new StateMachineStore(getRuleServiceProvider(), sm, getMasterCacheName(), getCacheConfig());

                this.getLogger().log(Level.INFO, "Registered entity type: %s", entityStore.getImplClass().getName());
            }

        } else if (entity instanceof com.tibco.cep.designtime.model.event.Event) {
            com.tibco.cep.designtime.model.event.Event evt = (com.tibco.cep.designtime.model.event.Event) entity;
            if (((com.tibco.cep.designtime.model.event.Event) entity).getType() == com.tibco.cep.designtime.model.event.Event.TIME_EVENT)
            {
                EntityStore entityStore = new EntityStore(this,entity,getPartitionId(),getConnectionString());
                entityDbStores.put(entityStore.getImplClass().getName(), entityStore);
//                TimeEventStore eventCache = new TimeEventStore(getRuleServiceProvider(),getMasterCacheName(),evt,getCacheConfig());
//                entityDbStores.put(eventCache.getImplClass().getName(), eventCache);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", entityStore.getImplClass().getName());
            } else {
                EntityStore entityStore = new EntityStore(this,entity,getPartitionId(),getConnectionString());
                entityDbStores.put(entityStore.getImplClass().getName(), entityStore);
//                EventStore eventCache = new EventStore(getRuleServiceProvider(),getMasterCacheName(),evt,getCacheConfig());
//                entityDbStores.put(eventCache.getImplClass().getName(), eventCache);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", entityStore.getImplClass().getName());
            }

        }
    }

    public void close() {
        Iterator allStores = entityDbStores.values().iterator();
        while (allStores.hasNext()) {
            allStores.next();
            //TODO: clean up
        }
        initDone = false;

    }

}
