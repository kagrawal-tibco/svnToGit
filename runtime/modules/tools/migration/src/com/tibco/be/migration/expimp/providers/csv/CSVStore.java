package com.tibco.be.migration.expimp.providers.csv;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.db.DbStore;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.TimeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 9:29:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVStore {
    public static String STATE_TIMEOUT_EVENT_CLASS_NAME = "com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl$StateTimeoutEvent";
    public static String STATE_TIMEOUT_EVENT_FILE_NAME = "StateTimeoutEvent";

    ExpImpContext context;
    ExpImpStats stats;
    private ExportControlData exportControldata;
    private HashMap propertyIndexMap;
    private HashMap scoreCardIndexMap;
    private HashMap entityStoreMap;
    private Logger logger;

    public CSVStore(ExpImpContext context, ExpImpStats stats) {
        this.context = context;
        this.stats = stats;
        this.entityStoreMap = new HashMap();
        this.logger = context.getRuleServiceProvider().getLogger(CSVStore.class);
    }

    public void init(DbStore dbStore) {
        try {

            CSVExportControlDeserializer ecd = new CSVExportControlDeserializer(context.getInputUrl());
            this.exportControldata = ecd.deserialize();

            CSVPropertyIndexDeserializer pid = new CSVPropertyIndexDeserializer(context.getInputUrl());
            this.propertyIndexMap = pid.deserialize();

            CSVScoreCardIndexDeserializer sid = new CSVScoreCardIndexDeserializer(context.getInputUrl());
            this.scoreCardIndexMap = sid.deserialize();

            Iterator allStores = Arrays.asList(dbStore.getAllStores()).iterator();

            while (allStores.hasNext()) {
                EntityStore store = (EntityStore) allStores.next();

                if (Concept.class.isAssignableFrom(store.getImplClass())) {
                    if (StateMachineConcept.class.isAssignableFrom(store.getImplClass())) {
                        if (!getInputVersion().startsWith("1.4")) {
                            CSVStatemachineStore csvStateMachineStore = new CSVStatemachineStore(this, context, stats, store.getImplClass().getName());
                            this.entityStoreMap.put(store.getImplClass().getName(), csvStateMachineStore);
                            this.getLogger().log(Level.INFO, "Registered file store for type: %s",
                                    store.getImplClass().getName());
                        } else {
                            this.getLogger().log(Level.INFO, "Ignored file store for type: %s",
                                    store.getImplClass().getName());
                        }
                    } else {
                        CSVConceptStore csvConceptStore = new CSVConceptStore(this, context, stats, store.getImplClass().getName());
                        this.entityStoreMap.put(store.getImplClass().getName(), csvConceptStore);
                        this.getLogger().log(Level.INFO, "Registered file store for type: %s",
                                store.getImplClass().getName());
                    }
                } else if (Event.class.isAssignableFrom(store.getImplClass())) {
                    if (TimeEvent.class.isAssignableFrom(store.getImplClass())) {
                        if (StateMachineConceptImpl.StateTimeoutEvent.class.isAssignableFrom(store.getImplClass()) &&
                                getInputVersion().startsWith("1.4")) {
                            // do nothing
                            this.getLogger().log(Level.INFO,
                                    "StateTimeoutEvent classes are ignored when importing from 1.4");
                        } else {
                            String storeName = store.getImplClass().getName();
                            storeName = (storeName == STATE_TIMEOUT_EVENT_CLASS_NAME ? STATE_TIMEOUT_EVENT_FILE_NAME : storeName);
                            CSVTimeEventStore csvTimeEventStore = new CSVTimeEventStore(this, context, stats, storeName);
                            this.entityStoreMap.put(store.getImplClass().getName(), csvTimeEventStore);
                            this.getLogger().log(Level.INFO, "Registered file store for type: %s",
                                    store.getImplClass().getName());
                        }
                    } else {
                        CSVSimpleEventStore csvSimpleEventStore = new CSVSimpleEventStore(this, context, stats, store.getImplClass().getName());
                        this.entityStoreMap.put(store.getImplClass().getName(), csvSimpleEventStore);
                        this.getLogger().log(Level.INFO, "Registered file store for type: %s",
                                store.getImplClass().getName());
                    }
                }

            }

        } catch (Exception e) {
            final StringWriter sw = new StringWriter(8192);
            e.printStackTrace(new PrintWriter(sw));
            stats.addErrorString(sw.getBuffer().toString());
        }

    }

    public String getInputVersion() {
        return exportControldata.getDataVersion();
    }

    public int getPropertyIndex(String conceptUri, String propertyName) {
        PropertiesIndex ix = (PropertiesIndex) propertyIndexMap.get(conceptUri + "." + propertyName);
        if (null != ix) {
            return ix.getPropertyIndex();
        }
        return -1;
    }

    public int getScoreCardIndex(String scoreCardUri) {
        ScoreCardIndex ix = (ScoreCardIndex) scoreCardIndexMap.get(scoreCardUri);
        if (null != ix) {
            return ix.getScoreCardIndex();
        }
        return -1;
    }

    public CSVEntityStore getCSVEntityStore(String className) {
        return (CSVEntityStore) entityStoreMap.get(className);
    }

    public Logger getLogger() {
        return logger;
    }

    public int getNumCSVEntityStore() {
        return entityStoreMap.size();
    }
}
