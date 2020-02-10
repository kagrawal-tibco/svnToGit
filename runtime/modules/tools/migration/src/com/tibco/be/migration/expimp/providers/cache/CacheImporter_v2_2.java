/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.migration.expimp.providers.cache;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.WorkerThreadPool;
import com.tibco.be.migration.expimp.providers.csv.CSVEntityStore;
import com.tibco.be.migration.expimp.providers.csv.CSVStore;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 20, 2008
 * Time: 3:47:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheImporter_v2_2 {
    private DeployedProject project;
    private ExpImpContext context;
    private ExpImpStats stats;
    private Ontology ontology;
    protected LinkedHashMap m_beArchives = new LinkedHashMap();
    private com.tibco.cep.kernel.service.logging.Logger m_logger;
    private String m_cacheConfigXml;
    private String m_cacheConfigURI;
    private CacheStore cacheStore;
    private WorkerThreadPool loaderThreadPool;
    private CSVStore csvStore;


    /**
     * constructor
     */
    public CacheImporter_v2_2() {
    }

    /**
     * initialize
     *
     * @param context
     * @param stats
     */
    public void init(ExpImpContext context, ExpImpStats stats) throws Exception {
        this.context = context;
        this.stats = stats;
        this.m_logger = context.getLogger();
        Properties prop = context.getRuleServiceProvider().getProperties();
        project = (DeployedProject) context.getRuleServiceProvider().getProject();
        ontology = this.project.getOntology();

        cacheStore = new CacheStore(context, stats);
        cacheStore.init();

//        csvStore = new CSVStore(context,stats);
//        csvStore.init(cacheStore);

        RuleServiceProvider provider = context.getRuleServiceProvider();
        int numberOfThreads = (((BEProperties) provider.getProperties()).getInt("be.migration.import.threads", 20));
        int numberOfJobs = cacheStore.getAllStores().length;
        this.loaderThreadPool = new WorkerThreadPool(
                (numberOfThreads >= numberOfJobs) ? numberOfJobs : numberOfThreads,
                numberOfJobs, context.getRuleServiceProvider());
    }

    public CacheStore getCacheStore() {
        return cacheStore;
    }

    private CSVStore getCSVStore() {
        return csvStore;
    }
    /**
     * imports the data from csv
     */
    public void importAll() {
        RuleServiceProvider provider = context.getRuleServiceProvider();

        if (this.cacheStore.getRecoveryCache().size() > 0) {
            //alreadyLoaded=true;
        }

        Iterator allStores = Arrays.asList(cacheStore.getAllStores()).iterator();

        String cacheLoaderClass = context.getRuleServiceProvider().getProperties()
                                        .getProperty("be.migration.csvloader",
                                        "com.tibco.be.migration.expimp.providers.csv.CSVLoader");
        HashMap cacheLoaders = new HashMap();

        try {
            Class recClz = Class.forName(cacheLoaderClass);
            while (allStores.hasNext()) {
                EntityStore entityCacheStore = (EntityStore) allStores.next();
//                Constructor cons = recClz.getConstructor(new Class[]{String.class , ExpImpContext.class});
//                CSVStore csvStore = (CSVStore) cons.newInstance(new Object[]{store.getCacheName(),context });
//                CSVEntityStore csvEntityStore = new CSVEntityStore(store,context);
//                cacheLoaders.put(store, csvEntityStore);
                CSVEntityStore entityCSVStore = getCSVStore().getCSVEntityStore(entityCacheStore.getImplClass().getName());
//                loaderThreadPool.execute(
//                            new CSVLoader(context,
//                                            stats,
//                                            getCacheStore(),
//                                            entityCacheStore,
//                                            getCSVStore(),
//                                            entityCSVStore,
//                                            loaderThreadPool)
//                            );
            }// end while
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        loaderThreadPool.join();

        //checkAllCaches();

        final Logger logger = this.context.getRuleServiceProvider().getLogger(CacheImporter_v2_2.class);
        logger.log(Level.DEBUG, "CacheServer phase 1 recovery: Done, starting phase 2.");

        try {
            allStores = Arrays.asList(cacheStore.getAllStores()).iterator();
            while (allStores.hasNext()) {
                EntityStore store = (EntityStore) allStores.next();
                // Dont use the threads for loading Cache+WM
                if (store.isCustomSetting() || (store.getCacheMode() == EntityDaoConfig.CacheMode.CacheAndMemory.ordinal())) {

                    //new Recover_CacheServer_Phase2(store, this.m_session, loaderThreadPool, alreadyLoaded, (CoherenceBackingStore) cacheLoaders.get(store) ).run();
                }
            }
        } catch (Exception ex) {
            final StringWriter sw = new StringWriter(8192);
                ex.printStackTrace(new PrintWriter(sw));
                stats.addErrorString(sw.getBuffer().toString());
        }

        //checkAllCaches();


        logger.log(Level.DEBUG, "CacheServer phase 2 recovery: Cache+WM caches recovered, starting CacheOnly.");

        allStores = Arrays.asList(cacheStore.getAllStores()).iterator();

        int syncCaches = 0;
        while (allStores.hasNext()) {
            EntityStore store = (EntityStore) allStores.next();
            // Dont use the threads for loading Cache+WM
            if (store.isCustomSetting() && (store.getCacheMode() == EntityDaoConfig.CacheMode.Cache.ordinal())) {
                String loadFlag = context.getRuleServiceProvider().getProperties().getProperty(store.getImplClass().getName() + ".coherence.recover.load", "no");
                if (loadFlag.equalsIgnoreCase("async")) {
                    //loaderThreadPool.execute(new Recover_CacheServer_Phase2(store, this.m_session, loaderThreadPool, alreadyLoaded,(CoherenceBackingStore) cacheLoaders.get(store)));
                } else if (loadFlag.equalsIgnoreCase("sync")) {
                    //loaderThreadPool.execute(new Recover_CacheServer_Phase2(store, this.m_session, loaderThreadPool, alreadyLoaded,(CoherenceBackingStore) cacheLoaders.get(store)));
                    syncCaches++;
                }
            }
        }

        if (syncCaches > 0) {
            logger.log(Level.DEBUG, "CacheServer phase 3 recovery: Waiting for %s to recover", syncCaches);
            //loaderThreadPool.join(syncCaches);
            //checkAllCaches();
        }

        cacheLoaders.clear();

        allStores = Arrays.asList(cacheStore.getAllStores()).iterator();
        long numHandlesInStore = 0;
        while (allStores.hasNext()) {
            EntityStore store = (EntityStore) allStores.next();
            numHandlesInStore = numHandlesInStore + store.getCoherenceCache().size();
        }

        logger.log(Level.INFO, "Number of Handles from CSV Store = %d", numHandlesInStore);


    }

    /**
     * clean up after import
     */
    public void destroy() {
        cacheStore.close();
    }



}
