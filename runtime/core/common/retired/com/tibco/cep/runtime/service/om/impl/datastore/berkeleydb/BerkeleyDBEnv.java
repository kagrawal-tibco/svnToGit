package com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb;

import com.sleepycat.je.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.DBTransaction;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.DataStoreEnv;
import com.tibco.cep.util.ResourceManager;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 19, 2004
 * Time: 4:14:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BerkeleyDBEnv implements DataStoreEnv {

    private Environment env;
    private static HashMap envs;
    private Logger m_logger;
    static boolean noRecovery = false;

    private BerkeleyDBEnv(Properties beprops, Logger _logger) throws OMException {

        String dbDir = getDbDir(beprops);
        m_logger = _logger;

        boolean debugTruncate = false;

        noRecovery = Boolean.TRUE.toString().toLowerCase().equals(beprops.getProperty("omNoRecovery").toLowerCase());
        if(noRecovery)
            debugTruncate = true;
        else {
            debugTruncate = Boolean.TRUE.toString().toLowerCase().equals(beprops.getProperty("be.engine.om.berkeleydb.truncate").toLowerCase());
        }
        File dir = new File(dbDir);
        if(debugTruncate && dir.exists()) {
            if(m_logger.isEnabledFor(Level.WARN))
                m_logger.log(Level.WARN,ResourceManager.getInstance().getMessage("be.om.berkeleydb.truncate"));
            deleteDir(dir);
        }
        if(!dir.exists()) {
            boolean noCreateDir = Boolean.TRUE.toString().toLowerCase().equals(beprops.getProperty("be.engine.om.berkeleydb.nocreatedbenv").toLowerCase());
            if(noCreateDir) {
                   m_logger.log(Level.FATAL,"be.om.berkeleydb.dirnotexist " + dbDir);
                //System.exit(1);
            }
            dir.mkdirs();
        }
        dir = null;


        EnvironmentConfig envConfig = new EnvironmentConfig();
        if(noRecovery) {
            envConfig.setTransactional(false);
            envConfig.setTxnNoSync(true);
            envConfig.setTxnWriteNoSync(true);
        } else
            envConfig.setTransactional(true);

        envConfig.setAllowCreate(true);

        String cachePercent = beprops.getProperty("cachepercent");
        int cachepct = 20;
        try {
            cachepct = Integer.parseInt(cachePercent);
        } catch (NumberFormatException nfe) {
            assert(false);
        }
        
        
        envConfig.setCachePercent(cachepct);

        if(m_logger.isEnabledFor(Level.INFO)) {
            m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.jvm.maxMemory",new Long(Runtime.getRuntime().maxMemory())));
            m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.berkeleydb.internalcache", new Integer(cachepct)));
            m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.berkeleydb.initializing", dbDir));
        }

        try {
            env = new Environment(new File(dbDir), envConfig);
        } catch (DatabaseException e) {
            throw new OMException(e);
        } catch(OutOfMemoryError oome) {
            m_logger.log(Level.ERROR,"", oome);
           m_logger.log(Level.FATAL,ResourceManager.getInstance().getMessage("be.om.berkeleydb.oome"));
            //System.exit(1);
        }
        if(m_logger.isEnabledFor(Level.INFO))
            m_logger.log(Level.INFO,ResourceManager.getInstance().getMessage("be.om.berkeleydb.recovered"));
    }

    private BerkeleyDBEnv(String dbDir) throws OMException {
        File dir = new File(dbDir);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);
        try {
            env = new Environment(new File(dbDir), envConfig);
        } catch (DatabaseException e) {
            throw new OMException(e);
        } catch(OutOfMemoryError oome) {
            m_logger.log(Level.ERROR,"", oome);
           m_logger.log(Level.FATAL,ResourceManager.getInstance().getMessage("be.om.berkeleydb.oome"));
            //System.exit(1);
        }
    }

    public static synchronized BerkeleyDBEnv create(Properties beprops, Logger _logger) throws OMException {

        String dbDir = getDbDir(beprops);

        if(envs == null)
            envs = new HashMap();

        BerkeleyDBEnv env = (BerkeleyDBEnv) envs.get(dbDir);
        if(env != null)
            return env;
        else {
            env = new BerkeleyDBEnv(beprops, _logger);
            envs.put(dbDir, env);
            return env;
        }
    }

    private static String getDbDir(Properties beprops) {
        String dbRoot = beprops.getProperty("be.engine.om.berkeleydb.dbenv", "./db");
        String dbDir = beprops.getProperty("omDbEnvDir");
        if(dbDir == null || dbDir.length() == 0)
            dbDir = dbRoot + "/" + beprops.getProperty("sessionName");
        return dbDir;
    }

    public static synchronized BerkeleyDBEnv create(String dbDir) throws OMException {

        if(envs == null)
            envs = new HashMap();

        BerkeleyDBEnv env = (BerkeleyDBEnv) envs.get(dbDir);
        if(env != null)
            return env;
        else {
            env = new BerkeleyDBEnv(dbDir);
            envs.put(dbDir, env);
            return env;
        }

    }

    public static synchronized BerkeleyDBEnv getInstance(Properties beprops) {
        if(envs == null)
            return null;

        String dbDir = getDbDir(beprops);

        return (BerkeleyDBEnv) envs.get(dbDir);
    }

    public static synchronized BerkeleyDBEnv getInstance(String dbDir) {
        if(envs == null)
            return null;

        return (BerkeleyDBEnv) envs.get(dbDir);
    }

    public Environment getEnvironment() {
        return env;
    }

    public DBTransaction starttxn() throws OMException {
        try {
            return new BerkeleyDBTransaction(env.beginTransaction(null, null));
        } catch (DatabaseException e) {
            throw new OMException(e);
        }
    }

    public void committxn(DBTransaction txn) throws OMException {
        txn.commit();
    }

    // Checks for existence of a table in the database.
    public boolean tableExists(String tableName) throws OMException {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(false);
        Database db = null;
        try {
            db = env.openDatabase(null, tableName, dbConfig);
        } catch (DatabaseNotFoundException e) {
            return false; // Db not found.
        } catch (DatabaseException e) { // Some other exception.
            e.printStackTrace();
            throw new OMException(e.getMessage());
        }

        if(db != null) {
            try {
                db.close();
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new OMException(e.getMessage());
            }
            return true;
        } else
            return false;
    }

    public void close() throws OMException {
        try {
            env.close();
        } catch (DatabaseException e) {
            throw new OMException(e);
        }
    }

    private void deleteDir(File dir) {
        File[] files = dir.listFiles();
        for(int ii=0; ii < files.length; ii++) {
            if(files[ii].isDirectory())
                deleteDir(files[ii]);
            else // is a file.
                files[ii].delete();
        }
    }


}
