package com.tibco.cep.persister.bdb;

import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.*;
import com.sleepycat.bind.EntryBinding;
import com.tibco.as.space.Metaspace;
import com.tibco.be.util.FileUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.persister.ASPersistenceConstants;
import com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.SharedAllStore;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/6/12
 * Time: 3:35 PM
 */
public class BDBProvider implements SharedAllStore {

    private Environment _env;
    private static final String _CLASS_CATALOG = "java_class_catalog";
    private StoredClassCatalog _javaCatalog;
    private DatabaseConfig _dbConfig;
    private ConcurrentHashMap<String,Database> _dbNameToDatabase;
    private ConcurrentHashMap<String,SecondaryDatabase> _dbNameToSecondaryDatabase;
    private ConcurrentHashMap<String,StoredMap> _dbNameToStoredMap;
    private Logger logger = LogManagerFactory.getLogManager().getLogger(BDBProvider.class);
    private volatile boolean _initialized = false;
    protected Metaspace metaspace;
    protected String dataStorePath = null;

    public  BDBProvider(Metaspace metaspace,String dataStorePath) {
       this.metaspace = metaspace;
       if( dataStorePath!=null ) {
        this.dataStorePath = dataStorePath;
       } else {
           this.dataStorePath = ASPersistenceConstants.DefaultPersistenceDirectory;
       }
    }
    
    public String getDataStorePath() {
        return dataStorePath;
    }

    public boolean initialize()
		throws DatabaseException, FileNotFoundException, EnvironmentLockedException {
        if (isInitialized()) {
            return true;
        }

        //Check if the PersistenceDirectory exists. If not, create it
        FileUtils.createWritableDirectory(this.dataStorePath);
        File dataStoreHandle = new File(this.dataStorePath);

        //Create the environment config
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);
        //envConfig.setInitializeCache(true);
        //envConfig.setInitializeLocking(true);

        //Force the durability to be WRITE_NO_SYNC
        envConfig.setDurability( new Durability(Durability.SyncPolicy.WRITE_NO_SYNC,Durability.SyncPolicy.WRITE_NO_SYNC,Durability.ReplicaAckPolicy.NONE));

        //Create the environment
        _env = new Environment(dataStoreHandle, envConfig);
        logger.log(Level.INFO, "Creating Berkeley DB in: %s", this.dataStorePath);

        StatsConfig statsConfig = new StatsConfig();
        statsConfig.setClear(true);
        EnvironmentStats envStats = _env.getStats(statsConfig);

        Durability d = _env.getConfig().getDurability();
        logger.log(Level.INFO,"=============================== Berkeley DB Configuration ====================================");
        logger.log(Level.INFO,"Environment home="+_env.getHome().getAbsolutePath());
        logger.log(Level.INFO, "CachePercent=" + envConfig.getCachePercent() + ", CacheSize=" + _env.getConfig().getCacheSize());
        logger.log(Level.INFO, "NumBuffers=" + envStats.getNLogBuffers() + ", TotalBufferBytes=" + envStats.getBufferBytes() + ", CommitPolicy=" + ((d != null) ? d.getLocalSync().name() : null));
        logger.log(Level.INFO,"==============================================================================================");

        //Create the db config
        _dbConfig = new DatabaseConfig();
        _dbConfig.setTransactional(true);
        _dbConfig.setAllowCreate(true);
        _dbConfig.setDeferredWrite(false);

        //Create the catalog db
        Database catalogDb = _env.openDatabase(null, _CLASS_CATALOG,_dbConfig);
        _javaCatalog = new StoredClassCatalog(catalogDb);
        logger.log(Level.INFO,"Created Berkeley DB class catalog: "+_CLASS_CATALOG);

        // Will store the application database names to the com.tibco.cep.persister.bdb object references
        _dbNameToDatabase = new ConcurrentHashMap<String, Database>();
        _dbNameToSecondaryDatabase = new ConcurrentHashMap<String, SecondaryDatabase>();
        _dbNameToStoredMap = new ConcurrentHashMap<String, StoredMap>();

        _initialized = true;

        return _initialized;
    }
    
    public boolean isInitialized() {
        return _initialized;
    }

    /*
    public boolean waitForInitialized() throws InterruptedException {
        reLock.lock();
        while(!isInitialized()) {
            logger.log(Level.INFO,"Waiting for bdb provider to be initialized");
            initializedCondition.await();
        }
        reLock.unlock();
        return _initialized;
    }
    */

    public Database createDatabase(String databaseName) throws RuntimeException,InterruptedException  {
        return createDatabase(databaseName,_dbConfig);
    }

    //Allow the user to specify his own config
    public Database createDatabase(String databaseName,DatabaseConfig cfg) throws RuntimeException,InterruptedException {
        Database db = null;
        //waitForInitialized();

        try {
            if( _env == null ) {
                throw new RuntimeException("BDB environment not configured. Aborting database creation!");
            }
            db = _env.openDatabase(null, databaseName, cfg);
            logger.log(Level.INFO, "Created Berkeley DB store: %s", databaseName);
            _dbNameToDatabase.put(databaseName,db);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            return db;
        }
    }

    public SecondaryDatabase createSecondaryDatabase(String databaseName, Database primaryDb,
                                                     Class keyClass,Class valueClass,Class indexKeyClass) throws InterruptedException {

        SecondaryDatabase _secondaryDb = null;
        //waitForInitialized();

        if(logger.isEnabledFor(Level.TRACE) ) {
            logger.log(Level.TRACE,"Created secondary database ["+databaseName+"]");
        }
        //Set the environment's secondary config
        SecondaryConfig secConfig = new SecondaryConfig();
        secConfig.setTransactional(true);
        secConfig.setAllowCreate(true);
        secConfig.setKeyCreator(new BDBSecondaryKeyCreator(databaseName,_javaCatalog,keyClass,valueClass,indexKeyClass));

        // Now pass secConfig to Environment.openSecondaryDatabase
        _secondaryDb = _env.openSecondaryDatabase(null,databaseName,primaryDb,secConfig);
        _dbNameToSecondaryDatabase.put(databaseName, _secondaryDb);
        return _secondaryDb;
    }

    public StoredMap createStoredMap(Database db,Class keyClass,Class valueClass,boolean writeAllowed) throws InterruptedException {
        return createStoredMap(db,_dbConfig,keyClass,valueClass,writeAllowed);
    }

    public StoredMap createStoredMap(Database db,DatabaseConfig cfg,Class keyClass,Class valueClass,boolean writeAllowed) throws InterruptedException {

        //waitForInitialized();
        //Create the key bindings
        EntryBinding keyBinding = new SerialBinding(_javaCatalog, keyClass);
        EntryBinding valueBinding = new SerialBinding(_javaCatalog, valueClass);
        StoredMap map = null;

        try {
            map = new StoredMap(db,keyBinding,valueBinding,writeAllowed);
            logger.log(Level.INFO, "Created Berkeley DB storedmap for: %s  (Key: %s Value: %s)", db.getDatabaseName(), keyClass, valueClass);
        } catch (Exception e) {
            logger.log(Level.WARN, e, "Failed to create Berkeley DB storedmap for: %s  (Key: %s Value: %s)", db.getDatabaseName(), keyClass, valueClass);
        }
        _dbNameToStoredMap.put(db.getDatabaseName(), map);
        return map;
    }

    public StoredMap createSecondaryStoredMap(SecondaryDatabase db,
                                              Class secondaryIndexClass,Class valueClass,boolean writeAllowed) throws InterruptedException  {
        return createStoredMap(db,_dbConfig,secondaryIndexClass,valueClass,writeAllowed);
    }

    public StoredMap createSecondaryStoredMap(SecondaryDatabase db,DatabaseConfig cfg,
                                              Class secondaryIndexClass,Class valueClass,boolean writeAllowed) throws InterruptedException {

        return createStoredMap(db,cfg,secondaryIndexClass,valueClass,writeAllowed);
    }

    public StoredMap getStoredMapForDatabase(String databaseName) {
        return _dbNameToStoredMap.get(databaseName);
    }

    //Close the catalog db and the environment
    public void close() {
        try {
            logger.log(Level.INFO, "Closing Berkeley DB catalog");
            _javaCatalog.close();
            _env.close();
        } catch (Exception e) {
            logger.log(Level.WARN, "Closing Berkeley DB catalog failed", e);
        }
    }

    public final StoredClassCatalog getClassCatalog() {
        return _javaCatalog;
    }

    public final Metaspace getMetaspace() {
        return metaspace;
    }

    //Administrative functions
    public boolean updateViewAndPersister(ViewAndPersister viewAndPersister) {

        //the meta-file
        String filename = this.dataStorePath + "\\"+ ASPersistenceConstants.PersistenceMetaInfoFileName;

        //Append a line to it
        BufferedWriter bufferedWriter = null;
        try {
            //Construct the BufferedWriter object. True indicates append
            bufferedWriter = new BufferedWriter(new FileWriter(filename,true));

            //Start writing to the output stream
            bufferedWriter.write(viewAndPersister.toString());
            bufferedWriter.newLine();
        } catch (FileNotFoundException ex) {
            logger.log(Level.WARN, "Updating view failed with FileNotFoundException", ex);
        } catch (IOException ex) {
            logger.log(Level.WARN, "Updating view failed with IOException", ex);
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                logger.log(Level.WARN, "Update view to: %s closing writer failed with IOException", ex, filename);
            }
        }
        logger.log(Level.INFO,"Updated view to:", viewAndPersister.getView());
        return true;
    }

    public ViewAndPersister readViewAndPersister() {

        //the meta-file
        String filename = this.dataStorePath + "\\"+ ASPersistenceConstants.PersistenceMetaInfoFileName;
        String line = null;
        String previousLine = null;

        BufferedReader bufferedReader = null;
        try {

            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(filename));
            while ((line = bufferedReader.readLine()) != null) {
                previousLine = line;
            }

        } catch (FileNotFoundException ex) {
            logger.log(Level.WARN, "Reading view failed with FileNotFoundException", ex);
        } catch (IOException ex) {
            logger.log(Level.WARN, "Reading view failed with IOException", ex);
        } finally {
            //Close the BufferedReader
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                logger.log(Level.WARN, "Read view from: %s closing reader failed with IOException", ex, filename);
            }
        }

        if( previousLine!= null ) {
            //this is the last line
            //indicates most recent persister
            String[] result = previousLine.split(ASPersistenceConstants.ViewDelimiter);
            int viewNumber=0;
            String persisterID= null;
            for(int i=0;i<result.length;i++) {
                if(i==0) {
                    try {
                        viewNumber = Integer.parseInt(result[i]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(i==1) {
                    persisterID = result[i];
                }
            }
            return new ViewAndPersister(viewNumber,persisterID);
        } else {
            return null;
        }
    }

    public void finalize() {
       close();
    }
}