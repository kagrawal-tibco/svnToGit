package com.tibco.be.migration.expimp.providers.csv;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.tangosol.net.NamedCache;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.WorkerThreadPool;
import com.tibco.be.migration.expimp.providers.db.DbStore;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 24, 2008
 * Time: 5:32:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVLoader implements Runnable {
    private EntityStore entityStore;
    private NamedCache destCache;
    private RuleServiceProvider ruleServiceProvider;
    private WorkerThreadPool loaderThreadPool;
    private CSVEntityStore csvEntityStore;
    private ExpImpContext context;
    private ExpImpStats stats;
    private DbStore dbStore;
    private String inputVersion;
    private String outputVersion;
    private int passNumber;
    private boolean useMultithreads;
    protected Logger logger;


    public CSVLoader(ExpImpContext context, ExpImpStats stats, DbStore dbStore, EntityStore dbEntityStore, CSVStore csvStore, CSVEntityStore csvEntityStore, WorkerThreadPool loaderThreadPool, boolean useMultithreads, int passNumber) {
        this.context = context;
        this.stats = stats;
        this.dbStore = dbStore;
        this.entityStore = dbEntityStore;
        this.context = context;
        this.loaderThreadPool = loaderThreadPool;
        this.csvEntityStore = csvEntityStore;
        GlobalVariablesProvider gvp = (GlobalVariablesProvider) context.getRuleServiceProvider().getGlobalVariables();
        this.outputVersion = gvp.getPackagedComponentVersion("be-engine");
        this.inputVersion = csvStore.getInputVersion();
        this.useMultithreads = useMultithreads;
        this.passNumber = passNumber;
        this.logger = this.context.getRuleServiceProvider().getLogger(CSVLoader.class);
    }

    public DbStore getDbStore() {
        return dbStore;
    }

    public ExpImpContext getContext() {
        return context;
    }

    public CSVEntityStore getCsvStore() {
        return csvEntityStore;
    }

    public NamedCache getDestCache() {
        return destCache;
    }

    public WorkerThreadPool getLoaderThreadPool() {
        return loaderThreadPool;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return context.getRuleServiceProvider();
    }

    public ExpImpStats getStats() {
        return stats;
    }

    public EntityStore getEntityStore() {
        return entityStore;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void run() {

        try {
            switch (passNumber) {
                case 0: {
                    if (useMultithreads)
                        Thread.currentThread().setName(entityStore.getCacheName() + ".EntityStoreThread");

                    this.logger.log(Level.INFO, "Loading entities into db: %s from: %s",
                            this.entityStore.getStoreName(), this.csvEntityStore.getCSVFile().getName());
                    csvEntityStore.load(entityStore, inputVersion, outputVersion);
                    this.logger.log(Level.INFO, "Loaded entities into db: %s", this.entityStore.getStoreName());
                }
                break;
                case 1: {
                    if (useMultithreads)
                            Thread.currentThread().setName(entityStore.getCacheName() + ".Properties.EntityStoreThread");

                    if (Concept.class.isAssignableFrom(entityStore.getImplClass())) {
                        this.logger.log(Level.INFO, "Loading properties into db: %s from: %s-properties%s",
                                this.entityStore.getStoreName(),
                                this.csvEntityStore.getStoreName(),
                                CSVEntityStore.CSV_FILE_EXTENSION);
                        
                        csvEntityStore.loadProperties(getDbStore(), entityStore, inputVersion, outputVersion);
                        this.logger.log(Level.INFO, "Loaded properties into db: %s" + this.entityStore.getStoreName());
                    } // it's an event
                }
                break;
                default:
                    break;
            }

            if (useMultithreads)
                loaderThreadPool.afterExecute();
        } catch (Exception e) {
            final StringWriter sw = new StringWriter(8192);
            e.printStackTrace(new PrintWriter(sw));
            final String msg = sw.getBuffer().toString();
            stats.addErrorString(msg);
            this.getLogger().log(Level.ERROR, msg);
        }

    }
}
