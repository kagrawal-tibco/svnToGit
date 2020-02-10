package com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.*;

import java.util.HashMap;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 13, 2004
 * Time: 4:11:11 PM
 * To change this template use File | Settings | File Templates.
 */
public final class BerkeleyDBFactory implements DataStoreFactory {

    private Logger m_logger;

    public BerkeleyDBFactory() {
    }

    public DataStore createDataStore() {

        BerkeleyDBConfig config = new BerkeleyDBConfig();
        return new BerkeleyDB(config, m_logger);
    }

    public DataStore createDataStore(String dbname, String secondaryDbName, Serializer serializer) {
        BerkeleyDBConfig config = new BerkeleyDBConfig();
        config.setDbName(dbname);
        config.setSecondayDbName(secondaryDbName);
        if(serializer == null) serializer = DefaultSerializer.getInstance();
        config.setSerializer(serializer);
        return new BerkeleyDB(config, m_logger);
    }

    public DataStoreEnv createDataStoreEnv(Properties beprops) throws OMException {
        return BerkeleyDBEnv.create(beprops, m_logger);
    }

    public DataStoreEnv getDataStoreEnv(Properties beprops) {
        return BerkeleyDBEnv.getInstance(beprops);
    }

    public DataStore createConceptDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String conceptdbname = beprops.getProperty("be.engine.om.berkeleydb.conceptable", "BEConcepts");
        //String urimap = beprops.getProperty("be.engine.om.berkeleydb.concepturimap", "BEConceptURIMap");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(conceptdbname, /*urimap*/ null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }

    public DataStore createPropertiesDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        BerkeleyDB ds = (BerkeleyDB) createDataStore(beprops.getProperty("be.engine.om.berkeleydb.propertiestable", "BEProperties"), null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }

    public DataStore createEventDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String eventslog = beprops.getProperty("be.engine.om.berkeleydb.eventslog", "BEEventLog");
        //String urimap = beprops.getProperty("be.engine.om.berkeleydb.eventurimap", "BEEventsURIMap");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(eventslog, /*urimap*/ null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }

    public DataStore createMigratedActiveStatesDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String dbName= beprops.getProperty("be.engine.om.berkeleydb.migratedActiveStates", "BEMigratedActiveStates");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(dbName, /*urimap*/ null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }

    // Method to support creating own event data store in BerkeleyDB - not related to OM.
    static HashMap eventlogs;
    public static DataStore createEventDataStore(BerkeleyDBEnv env) throws OMException {
        if(eventlogs == null)
            eventlogs = new HashMap();
        DataStore ds = (DataStore) eventlogs.get(env);
        if(ds != null)
            return ds;
        else {
            BerkeleyDBConfig config = new BerkeleyDBConfig();
            config.setDbName("BEEventLog");
            config.setSerializer(DefaultSerializer.getInstance());
            ds = new BerkeleyDB(env, config, null);
            eventlogs.put(env, ds);
            return ds;
        }
    }

    /*public DataStore createPayloadDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String payloaddbname = beprops.getProperty("be.engine.om.berkeleydb.payloadstable", "BEEventPayloads");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(payloaddbname, null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }*/

    /*public DataStore createNamedInstanceMapDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String namedinstancedbname = beprops.getProperty("be.engine.om.berkeleydb.namedinstancemap", "BENamedInstanceIds");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(namedinstancedbname, null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }*/

    public DataStore createPropertyIndexDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String propindexdbname = beprops.getProperty("be.engine.om.berkeleydb.propertyindextable", "BEPropertiesIndexMap");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(propindexdbname, null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }

    public DataStore createBEVersionDataStore(Properties beprops, Serializer serializer) throws OMException {
        BerkeleyDBEnv env = (BerkeleyDBEnv) createDataStoreEnv(beprops);
        String beVersionds = beprops.getProperty("be.engine.om.berkeleydb.beversiontable", "BEVersionTable");
        BerkeleyDB ds = (BerkeleyDB) createDataStore(beVersionds, null, serializer);
        ds.setEnv(env.getEnvironment());
        return ds;
    }

    public void setLogger(Logger logger) {
        m_logger = logger;
    }

    public boolean checkDSExists(Properties beprops, String dataStoreName) throws OMException {
        DataStoreEnv env = createDataStoreEnv(beprops);
        return env.tableExists(dataStoreName);
    }

    public boolean checkConceptDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.conceptable", "BEConcepts"));
    }

    public boolean checkPropertiesDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.propertiestable", "BEProperties"));
    }

    public boolean checkEventDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.eventslog", "BEEventLog"));
    }

    /*public boolean checkPaylodDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.payloadstable", "BEEventPayloads"));
    }

    public boolean checkNamedInstanceMapDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.namedinstancemap", "BENamedInstanceIds"));

    }*/

    public boolean checkPropertyIndexDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.propertyindextable", "BEPropertiesIndexMap"));
    }

    public boolean checkBEVersionDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.beversiontable", "BEVersionTable"));
    }

    public boolean checkMigratedActiveStatesDSExists(Properties beprops) throws OMException {
        return checkDSExists(beprops, beprops.getProperty("be.engine.om.berkeleydb.migratedActiveStates", "BEMigratedActiveStates"));
    }
}
