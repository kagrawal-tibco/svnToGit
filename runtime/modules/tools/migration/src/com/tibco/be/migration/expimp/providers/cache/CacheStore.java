package com.tibco.be.migration.expimp.providers.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tibco.be.migration.BEMigrationConstants;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.TimeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 21, 2008
 * Time: 10:10:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheStore extends AbstractStore {

    private Map m_objectCaches = new HashMap();
    private String masterCacheName;
    private NamedCache m_cache;
    private ExpImpStats stats;
    private ExpImpContext context;


    public CacheStore(ExpImpContext context, ExpImpStats stats) {
        super(context.getRuleServiceProvider()
              ,context.getBarName()
              ,context.getRuleServiceProvider()
                    .getProperties()
                    .getProperty(BEMigrationConstants.PROP_OUTPUT_URL));
        this.context = context;
        this.stats = stats;
    }


    public void init() throws Exception {

//            this.m_entityData = getAdvancedSettings(cacheConfig);
            initConfig(getConfigXml(getConfigURI()));
            final Properties cacheConfig = getCacheConfig();
            loadOntology();
            registerTypes();

            this.getLogger().log(Level.INFO, "Register cache ids....");

            this.m_cache = CacheFactory.getCache(getMasterCacheName(),(ClassLoader) mrsp.getTypeManager());
            Iterator allCaches= m_objectCaches.values().iterator();
            while (allCaches.hasNext()) {
                EntityStore store = (EntityStore) allCaches.next();
                store.connect();
            }
            m_recoveryCache=CacheFactory.getCache(getRecoveryCacheName(),(ClassLoader) mrsp.getTypeManager());
            initDone = true;
    }

    public String getMasterCacheName() {
        if(null == this.masterCacheName) {
            this.masterCacheName = getCacheConfig().getProperty(Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME, getBarName());
        }
        return this.masterCacheName;
    }


    public String getMetadataCacheName() {
        return masterCacheName + ".$cacheRegistry";
    }


    public String getRecoveryCacheName() {
        return "$Recovery." + masterCacheName + ".$recoveryCache";
    }

    public String getEntityStoreName(String entityClassName) {
        return getMasterCacheName() + "." + entityClassName;
    }


    public EntityStore getEntityStore(String cacheName) {
        return getEntityStoreForClass(cacheName.substring(0, getMasterCacheName().length() + 1));
    }

    public EntityStore getEntityStoreForClass(String entityClassName) {
        return (EntityStore) m_objectCaches.get(entityClassName);
    }

    public EntityStore getEntityStoreFromUri(String uri) {
        return getEntityStoreForClass(getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri).getImplClass().getName());
    }


    public EntityStore[] getAllStores() {
        return (EntityStore[]) m_objectCaches.values().toArray(new EntityStore[0]);
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
//        getLogger().logInfo("Registered Entity Store for type :"+entityStore.getImplClass().getName());
    }

    private EntityStore getTypeCache(Class implClass) throws Exception {
        EntityStore entityStore = null;
        if(m_objectCaches.containsKey(implClass.getName())) {
            return (EntityStore) m_objectCaches.get(implClass.getName());
        }
        if (Concept.class.isAssignableFrom(implClass)) {
            entityStore = new ConceptStore(getRuleServiceProvider(), getMasterCacheName(), implClass, getCacheConfig());
            m_objectCaches.put(implClass.getName(), entityStore);
            this.getLogger().log(Level.INFO, "Registered entity type: %s", implClass.getName());
        } else if (Event.class.isAssignableFrom(implClass)) {
            if (TimeEvent.class.isAssignableFrom(implClass)) {
                entityStore = new TimeEventStore(getRuleServiceProvider(), getMasterCacheName(), implClass, getCacheConfig());
                m_objectCaches.put(implClass.getName(), entityStore);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", implClass.getName());
            } else {
                entityStore = new EventStore(getRuleServiceProvider(), getMasterCacheName(), implClass, getCacheConfig());
                m_objectCaches.put(implClass.getName(), entityStore);
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
            if (m_objectCaches.containsKey(td.getImplClass().getName())) {
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

                loadEntity(cept, getCacheConfig());
            } else {
                if (entity != null) {
                    loadEntity(entity, getCacheConfig());
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
            loadEntity(cept,getCacheConfig());
        }
        // Add all the events
        Iterator allEvents= ontology.getEvents().iterator();
        while (allEvents.hasNext()) {
            com.tibco.cep.designtime.model.event.Event evt = (com.tibco.cep.designtime.model.event.Event) allEvents.next();
            loadEntity(evt, getCacheConfig());
        }
    }

    private void loadEntity(com.tibco.cep.designtime.model.Entity entity, Properties cacheConfig) throws Exception{
        if (entity instanceof com.tibco.cep.designtime.model.element.Concept) {
            com.tibco.cep.designtime.model.element.Concept cept = (com.tibco.cep.designtime.model.element.Concept) entity;
            ConceptStore conceptCache = new ConceptStore(getRuleServiceProvider(), getMasterCacheName(), entity, getCacheConfig());
            m_objectCaches.put(conceptCache.getImplClass().getName(), conceptCache);
            this.getLogger().log(Level.INFO, "Registered entity type: %s", conceptCache.getImplClass().getName());
            List allMachines = cept.getStateMachines();
            for (int i = 0; null != allMachines && i < allMachines.size(); i++) {
                StateMachine sm = (StateMachine) allMachines.get(i);
                StateMachineStore smCache = new StateMachineStore(getRuleServiceProvider(), sm, getMasterCacheName(), getCacheConfig());
                m_objectCaches.put(smCache.getImplClass().getName(), smCache);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", smCache.getImplClass().getName());
            }

        } else if (entity instanceof com.tibco.cep.designtime.model.event.Event) {
            com.tibco.cep.designtime.model.event.Event evt = (com.tibco.cep.designtime.model.event.Event) entity;
            if (((com.tibco.cep.designtime.model.event.Event) entity).getType() == com.tibco.cep.designtime.model.event.Event.TIME_EVENT)
            {
                TimeEventStore eventCache = new TimeEventStore(getRuleServiceProvider(),getMasterCacheName(),evt,getCacheConfig());
                m_objectCaches.put(eventCache.getImplClass().getName(), eventCache);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", eventCache.getImplClass().getName());
            } else {
                EventStore eventCache = new EventStore(getRuleServiceProvider(),getMasterCacheName(),evt,getCacheConfig());
                m_objectCaches.put(eventCache.getImplClass().getName(), eventCache);
                this.getLogger().log(Level.INFO, "Registered entity type: %s", eventCache.getImplClass().getName());
            }

        }
    }

    public void close() {
        Iterator allStores = m_objectCaches.values().iterator();
        while (allStores.hasNext()) {
            EntityStore store = (EntityStore) allStores.next();
            store.release();
        }
        m_recoveryCache.release();
        m_cache.release();
        initDone = false;

    }
}
