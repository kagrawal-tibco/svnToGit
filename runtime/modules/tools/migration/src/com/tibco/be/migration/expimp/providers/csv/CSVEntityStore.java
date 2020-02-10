package com.tibco.be.migration.expimp.providers.csv;

import java.io.File;

import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.db.DbStore;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 24, 2008
 * Time: 5:39:45 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CSVEntityStore  {

    public static final String CSV_FILE_EXTENSION = ".bexm";
    protected static final String FILE_SEPARATOR = System.getProperty("file.separator");
    protected static final String PATH_SEPARATOR = System.getProperty("path.separator");
    private String cacheName;
    private ExpImpContext context;
    private ExpImpStats stats;
    File    csvFile;
    private CSVStore csvStore;
    private String storeName;
    public static final int BATCH_SIZE = 1000;
    protected Logger logger;


    public CSVEntityStore(ExpImpContext context, ExpImpStats stats, CSVStore csvStore, String storeName) {
        this.csvStore = csvStore;
        this.context = context;
        this.stats = stats;
        this.storeName = storeName;
        csvFile = new File(context.getInputUrl()+FILE_SEPARATOR+getStoreName()+CSV_FILE_EXTENSION);
        this.logger = this.context.getRuleServiceProvider().getLogger(CSVEntityStore.class);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDir() {
        return context.getInputUrl();
    }

    public File getCSVFile() {
        return csvFile;
    }

    public CSVStore getCSVStore() {
        return csvStore;
    }

    public ExpImpStats getStats() {
        return stats;
    }

    public abstract void load(EntityStore entityStore, String inputVersion, String outputVersion) throws Exception;

    public abstract void loadProperties(DbStore cacheStore, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception ;

}
