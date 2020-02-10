package com.tibco.be.functions.engine;



/*@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Engine.OM",
        synopsis = "Functions to operate on the Object Manager.")*/

public class OMFunctions {

    /* TODO: NB no more OM?

    / ** @.name checkpointInterval
     * @.synopsis Returns the interval in seconds between two checkpoints in the Object Manager.
     * @.signature long checkpointInterval ()
     * @return long interval in seconds between checkpoints.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the interval in seconds between two checkpoints in the Object Manager.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static long checkpointInterval() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.checkpointInterval;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name dbInternalCacheSizePct
     * @.synopsis Returns the size of the DB internal memory cache in percentage of the JVM maximum memory.
     * @.signature int dbInternalCacheSizePct()
     * @return int The percentage of the JVM max memory used by the DB cache.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the size of the DB internal memory cache in percentage of the JVM max memory.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int dbInternalCacheSizePct() {
        final ObjectManagerStats omStats = getOMStats();
        return omStats.dbinternalcachepercent;
    }//nbConcepts



    private static ObjectManagerStats getOMStats() {
        final ObjectManager om = WorkingMemory.getWorkingMemory().getObjectManager();
        if (null != om) {
            final ObjectManagerStats omStats = om.hawkGetOMStats();
            return omStats;
        }//if
        return null;
    }//getOMStats


    / ** @.name lastCheckpointFinished
     * @.synopsis Returns the UNIX time of the last completed checkpoint.
     * @.signature long checkpointInterval()
     * @return long The time of the last completed checkpoint.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the time (in milliseconds since January 1, 1970) of the last completed checkpoint.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static long lastCheckpointFinished() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.lastCheckpointFinished;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name nbConcepts
     * @.synopsis Returns the total number of concepts known to the Object Manager.
     * @.signature int nbConcepts ()
     * @return int The number of Concepts known to the Object Manager.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the total number of concepts known to the Object Manager.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int nbConcepts() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.numConcepts;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name nbDirtyProperties
     * @.synopsis Returns the total number of dirty properties known to the Object Manager.
     * @.signature int nbDirtyProperties ()
     * @return int The number of dirty properties known to the Object Manager.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the total number of dirty properties known to the Object Manager.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int nbDirtyProperties() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.numDirtyProps;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name nbEvents
     * @.synopsis Returns the total number of events known to the Object Manager.
     * @.signature int nbEvents ()
     * @return int The number of events known to the Object Manager.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the total number of events known to the Object Manager.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int nbEvents() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.numEvents;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name nbNewConcepts
     * @.synopsis Returns the total number of concepts known to the Object Manager, that are not persisted yet.
     * @.signature int nbNewConcepts ()
     * @return int The number of new concepts.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the total number of concepts known to the Object Manager, that are not persisted yet.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int nbNewConcepts() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.numNewConcepts;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name nbNewEvents
     * @.synopsis Returns the total number of events known to the Object Manager, that are not persisted yet.
     * @.signature int  nbNewEvents ()
     * @return int The number of new events.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the total number of events known to the Object Manager, that are not persisted yet.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int nbNewEvents() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.numNewEvents;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name propCacheCurrentSize
     * @.synopsis Returns the current size of the Object Manager's property cache.
     * @.signature int propCacheCurrentSize ()
     * @return int The current number of properties in the cache.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the current size of the Object Manager's property cache.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int propCacheCurrentSize() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.propCacheCurrSize;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    / ** @.name propCacheMaxSize
     * @.synopsis Returns the maximum size of the Object Manager's property cache.
     * @.signature int propCacheMaxSize ()
     * @return int The maximum number of properties in the cache.
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Returns the maximum size of the Object Manager's property cache.
     * @.cautions none
     * @.domain action, condition
     * @.example
     * @.exposed false
     * /
    public static int propCacheMaxSize() {
        try {
            final ObjectManagerStats omStats = getOMStats();
            return omStats.propCacheMaxSize;
        } catch (Exception e) {
            return 0;
        }//catch
    }//nbConcepts


    // Functions for supporting creation of event logs directly without OM.

    / ** @.name getDBEnv
     * @.synopsis Creates a new BerkeleyDB Environment in the specified direcoty
     * @.signature String directiory
     * @.version 1.0
     * @.see
     * @.mapper false
     * @.description
     * Creates a new or returns already created BerkeleyDB Environment
     * @.cautions none
     * @.domain action
     * @.example
     * @.exposed true
     * /
    public static Object getDBEnv(String dbDir) {
        try {
            return (BerkeleyDBEnv) BerkeleyDBEnv.create(dbDir);
        } catch (OMException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getEventLog(Object env) {
        BerkeleyDBEnv bdbenv = (BerkeleyDBEnv) env;
        try {
            return (BerkeleyDB) BerkeleyDBFactory.createEventDataStore(bdbenv);
        } catch (OMException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object startTxn(Object env) {
        BerkeleyDBEnv bdbenv = (BerkeleyDBEnv) env;
        try {
            return (BerkeleyDBTransaction) bdbenv.starttxn();
        } catch (OMException e) {
            throw new RuntimeException(e);
        }
    }

    public static void commitTxn(Object txn) {
        BerkeleyDBTransaction bdbtxn = (BerkeleyDBTransaction) txn;
        try {
            bdbtxn.commit();
        } catch (OMException e) {
            throw new RuntimeException(e);
        }
    }

    public static void add(Object eventLog, Object txn, Event evt) {
        AbstractEventImpl event = (AbstractEventImpl) evt;
        BerkeleyDB ds = (BerkeleyDB) eventLog;
        BerkeleyDBTransaction bdbtxn = (BerkeleyDBTransaction) txn;
        try {
            ds.add(bdbtxn, event.primaryKey(), event);
        } catch (OMAddException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modify(Object eventLog, Object txn, Event evt) {
        AbstractEventImpl event = (AbstractEventImpl) evt;
        BerkeleyDB ds = (BerkeleyDB) eventLog;
        BerkeleyDBTransaction bdbtxn = (BerkeleyDBTransaction) txn;
        try {
            ds.modify(bdbtxn, event.primaryKey(), event);
        } catch (OMModifyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(Object eventLog, Object txn, Event evt) {
        AbstractEventImpl event = (AbstractEventImpl) evt;
        BerkeleyDB ds = (BerkeleyDB) eventLog;
        BerkeleyDBTransaction bdbtxn = (BerkeleyDBTransaction) txn;
        try {
            ds.delete(bdbtxn, event.primaryKey());
        } catch (OMDeleteException e) {
            throw new RuntimeException(e);
        }
    }

    public static Event fetch(Object eventLog, Object txn, long id) {
        BerkeleyDB ds = (BerkeleyDB) eventLog;
        BerkeleyDBTransaction bdbtxn = (BerkeleyDBTransaction) txn;
        try {
            return (Event) ds.fetch(bdbtxn, new URIImplPrimaryKey(id), null);
        } catch (OMFetchException e) {
            throw new RuntimeException(e);
        }
    }
    */
}//class
