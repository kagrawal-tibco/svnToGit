package com.tibco.be.migration.expimp.providers.db;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.migration.BEMigrationConstants;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.WorkerThreadPool;
import com.tibco.be.migration.expimp.providers.csv.CSVEntityStore;
import com.tibco.be.migration.expimp.providers.csv.CSVLoader;
import com.tibco.be.migration.expimp.providers.csv.CSVStore;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 27, 2008
 * Time: 10:04:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleImporter_v2_2 {
    protected DeployedProject project;
    protected ExpImpContext context;
    protected RuleServiceProvider provider;
    protected Ontology ontology;
    protected ExpImpStats stats;
    protected Logger m_logger;
    protected String m_connStr;
    protected DbStore dbStore;
    protected WorkerThreadPool loaderThreadPool;
    protected CSVStore csvStore;
    protected boolean useMultithreads;

    public void init(ExpImpContext context, ExpImpStats stats) throws Exception {
        this.context = context;
        this.stats = stats;
        this.m_logger = context.getLogger();
        provider = context.getRuleServiceProvider();
        BEProperties prop = (BEProperties)provider.getProperties();
        project = provider.getProject();
        ontology = this.project.getOntology();
        dbStore = new DbStore(context, stats);
        dbStore.init();
        csvStore = new CSVStore(context, stats);
        csvStore.init(dbStore);
        int numDbStore = dbStore.getAllStores().length;
        int numCsvStore = csvStore.getNumCSVEntityStore();
        int numberOfJobs = numCsvStore < numDbStore ? numCsvStore : numDbStore; // for now 1.4 statemachine is not migrated.

        this.m_logger.log(Level.INFO,  "Number of entity types to be loaded is " + numberOfJobs);
        useMultithreads = prop.getBoolean(BEMigrationConstants.PROP_USING_MULTITHREADS, true);
        if (useMultithreads) {
            int numberOfThreads = (prop.getInt(BEMigrationConstants.PROP_IMPORT_THREADS, 20));
            if (numberOfThreads >= numberOfJobs) { // too many threads
                this.m_logger.log(Level.INFO,  "Loading with multithreads. Number of threads configured is " + numberOfThreads + ", number of threads to be created is " + numberOfJobs);
                numberOfThreads = numberOfJobs;
            } else {
                this.m_logger.log(Level.INFO,  "Loading with multithreads. Number of threads configured is " + numberOfThreads + ", number of threads to be created is " + numberOfThreads);
            }
            this.loaderThreadPool = new WorkerThreadPool(numberOfThreads, numberOfJobs, provider);
        } else {
            this.m_logger.log(Level.INFO,  "Loading with single thread.");
            this.loaderThreadPool = null;
        }
    }

    public DbStore getDbStore() {
        return dbStore;
    }

    public boolean useMultithreads() {
        return useMultithreads;
    }

    public void importAll() {
        List storeList = Arrays.asList(dbStore.getAllStores());
        Iterator allStores = storeList.iterator();

        if (useMultithreads) { // Multithreads
            try {
                // Loading all entities
                while (allStores.hasNext()) {
                    EntityStore entityCacheStore = (EntityStore) allStores.next();
                    if (!(csvStore.getInputVersion().startsWith("1.4")
                            && (StateMachineConcept.class.isAssignableFrom(entityCacheStore.getImplClass()) ||
                            StateMachineConceptImpl.StateTimeoutEvent.class.isAssignableFrom(entityCacheStore.getImplClass())
                        )
                        )
                        ) {
                        CSVEntityStore entityCSVStore = csvStore.getCSVEntityStore(entityCacheStore.getImplClass().getName());
                        this.m_logger.log(Level.DEBUG, "Getting a thread for entity: %s...................",
                                entityCacheStore.getStoreName());
                        loaderThreadPool.execute(
                            new CSVLoader(context,
                                stats,
                                getDbStore(),
                                entityCacheStore,
                                csvStore,
                                entityCSVStore,
                                loaderThreadPool,
                                useMultithreads, 0)
                    );
                    }
                }// end while
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            // Wait for all entity loading threads are done
            loaderThreadPool.join();

            this.m_logger.log(Level.INFO,  "Loaded all entities into db.");

            // Loading properties of all entities
            allStores = Arrays.asList(dbStore.getAllStores()).iterator();
            try {
                while (allStores.hasNext()) {
                    EntityStore entityCacheStore = (EntityStore) allStores.next();
                    if (!(csvStore.getInputVersion().startsWith("1.4")
                            && (
                                StateMachineConcept.class.isAssignableFrom(entityCacheStore.getImplClass()) ||
                                StateMachineConceptImpl.StateTimeoutEvent.class.isAssignableFrom(entityCacheStore.getImplClass())
                            )
                        ) ) {
                        CSVEntityStore entityCSVStore = csvStore.getCSVEntityStore(entityCacheStore.getImplClass().getName());
                        this.m_logger.log(Level.DEBUG, "Getting a thread for property: %s...................",
                                entityCacheStore.getStoreName());
                        loaderThreadPool.execute(
                            new CSVLoader(context,
                                stats,
                                getDbStore(),
                                entityCacheStore,
                                csvStore,
                                entityCSVStore,
                                loaderThreadPool,
                                useMultithreads, 1)
                    );
                    }
                }// end while
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            // Wait for properties of all entity loading threads are done
            loaderThreadPool.join();
        } // if (usemultithreads)
        else { // use single thread
            try {
                // Loading all entities
                while (allStores.hasNext()) {
                    EntityStore entityCacheStore = (EntityStore) allStores.next();
                    if (!(csvStore.getInputVersion().startsWith("1.4")
                            && (StateMachineConcept.class.isAssignableFrom(entityCacheStore.getImplClass()) ||
                            StateMachineConceptImpl.StateTimeoutEvent.class.isAssignableFrom(entityCacheStore.getImplClass())
                        )
                        )
                        ) {
                        CSVEntityStore entityCSVStore = csvStore.getCSVEntityStore(entityCacheStore.getImplClass().getName());
                        new CSVLoader(context,
                                stats,
                                getDbStore(),
                                entityCacheStore,
                                csvStore,
                                entityCSVStore,
                                loaderThreadPool,
                                useMultithreads, 0).run();
                    }
                }// end while
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            this.m_logger.log(Level.INFO,  "Loaded all entities into db.");

            // Loading properties of all entities
            allStores = Arrays.asList(dbStore.getAllStores()).iterator();
            try {
                while (allStores.hasNext()) {
                    EntityStore entityCacheStore = (EntityStore) allStores.next();
                    if (!(csvStore.getInputVersion().startsWith("1.4")
                            && (
                                StateMachineConcept.class.isAssignableFrom(entityCacheStore.getImplClass()) ||
                                StateMachineConceptImpl.StateTimeoutEvent.class.isAssignableFrom(entityCacheStore.getImplClass())
                            )
                        ) ) {
                        CSVEntityStore entityCSVStore = csvStore.getCSVEntityStore(entityCacheStore.getImplClass().getName());
                        new CSVLoader(context,
                                stats,
                                getDbStore(),
                                entityCacheStore,
                                csvStore,
                                entityCSVStore,
                                loaderThreadPool,
                                useMultithreads, 1).run();
                    }
                }// end while
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } /*  end else { // use single thread */

        this.m_logger.log(Level.INFO,  "Loaded properties of all entities into db.");

        // Getting summery
        allStores = Arrays.asList(dbStore.getAllStores()).iterator();
        long numHandlesInStore = 0;
        while (allStores.hasNext()) {
            EntityStore store = (EntityStore) allStores.next();
            try {
                final long storeCount = store.getCount();
                numHandlesInStore = numHandlesInStore + storeCount;
                this.m_logger.log(Level.INFO,  "Loaded "+storeCount+" objects for "+ store.getStoreName());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        this.m_logger.log(Level.INFO,  "Total objects loaded " + numHandlesInStore );

    }

    public void destroy() {
        dbStore.close();
    }
}
