package com.tibco.cep.runtime.service.om.impl.datastore;


import com.tibco.be.util.BEProperties;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.kernel.concurrent.Guard;
import com.tibco.cep.kernel.core.base.*;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.service.om.exception.OMInitException;
import com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb.BerkeleyDBFactory;
import com.tibco.cep.runtime.service.om.impl.mem.InMemoryObjectManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionConfigImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.ResourceManager;
import com.tibco.cep.runtime.service.om.impl.ElementHandle;
import com.tibco.cep.runtime.service.om.impl.ElementExtHandle;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 25, 2006
 * Time: 9:36:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersistentStore extends InMemoryObjectManager implements PropertyBasedStore {

    public Properties omConfig;
    boolean omEnabled = true;
    long checkpointTime;
    boolean immediateDeletePolicy = true;
    boolean noRecovery = false;
    //HouseKeeper maid;
    //static final boolean testMode = true;

    private boolean syncCheckpoint = false;
    private boolean doSomething = false;
    private long checkpointOutstandingOpsLimit;
    private long numOutstandingOps = 0L;
    private long lastCheckpointDuration = 0L;
    private DataStoreFactory dbfactory;
    private ConceptManager cm;
    private PropertyManager propman;
    private EventLogger eventlog;
    private CheckPointer checkpointer;
    private DataStoreEnv env;
    private DataStore migratedActiveStates = null;
    private BEProperties beProperties;
    Logger m_logger;
    RuleSessionImpl session;
    DefaultSerializer defaultSerializer;

    public PersistentStore(Properties _omConfig, RuleSession _session) {
        super(_session.getName());
        omConfig = _omConfig;
        session = (RuleSessionImpl) _session;
        m_logger = session.getRuleServiceProvider().getLogger(PersistentStore.class);
        RuleServiceProvider RSP = session.getRuleServiceProvider();
        defaultSerializer = new DefaultSerializer(RSP);
        beProperties = (BEProperties)RSP.getProperties();
        //defaultSerializer.setClassLoader(session.getRuleServiceProvider().getClassLoader());

        this.immediateDeletePolicy = Boolean.valueOf(
                this.beProperties.getProperty(Constants.PROPERTY_NAME_OM_BDB_DELETE_POLICY));
        this.noRecovery = Boolean.valueOf(
                this.beProperties.getProperty(Constants.PROPERTY_NAME_OM_BDB_NO_RECOVERY_REQD));
        
        putCachePercent();

        cm = new ConceptManager(this);
        propman = new PropertyManager(this);
        eventlog = new EventLogger(this);
        checkpointer = new CheckPointer(this);
/* TODO
        maid = new HouseKeeper();
*/
    }

    public void init(WorkingMemory wm) throws Exception {
        super.init(wm);
        propman.init();
        cm.init();
        eventlog.init();
        checkpointer.init();
/* TODO
        maid.init();
*/
        checkpointOutstandingOpsLimit = Integer.parseInt(omConfig.getProperty("omCheckPtOpsLimit", "0"));
        //syncCheckpoint = beprops.getBoolean("be.engine.om.synccheckpoint", false);
        //WorkingMemory.resetWorkingMemory();

    }

    BaseHandle addObjectHandle(Object obj) throws DuplicateExtIdException, DuplicateException {
        return this.addHandle(obj);
    }

    Iterator getObjectIterator() {
        return this.objectIterator();
    }

    public void start() throws Exception {
        propman.start();
        cm.start();
        eventlog.start();
        checkpointer.start();

//TODO        maid.start();
    }

    public void shutdown() throws Exception {
        forceCheckpoint();
        stop();
        checkpointer.shutdown();
        propman.shutdown();
        cm.shutdown();
        eventlog.shutdown();
        env.close();
    }

    public void stop() throws Exception {
        checkpointer.stop();
        propman.stop();
        cm.stop();
        eventlog.stop();

        //checkpointer.timeToStop = true;
        //checkpointer.interrupt();
/* TODO
        if(maid != null) {
            maid.timeToStop = true;
            maid.interrupt();

            try {
                //checkpointer.join();
                maid.join();
            } catch (InterruptedException e) {
            }
        }
*/


    }

     /**
     * Create a new database factory object if required and return it.
     * @return
     * @throws com.tibco.cep.runtime.service.om.exception.OMException
     */
    DataStoreFactory dbFactory() throws OMException {
        if (dbfactory != null)
            return dbfactory;
        else {
            //BEProperties beprops = getContainer().getProperties();
            String dbType = omConfig.getProperty("be.engine.om.datastore.factory", BerkeleyDBFactory.class.getName());

            try {
                Class dbFactoryClass = Class.forName(dbType);
                dbfactory = (DataStoreFactory) dbFactoryClass.newInstance();
                dbfactory.setLogger(m_logger);
            } catch (Exception e) {
                m_logger.log(Level.ERROR,"", e);
                throw new OMInitException(e);
            }

            env = dbfactory.createDataStoreEnv(omConfig);
            checkDbVersion();
            return dbfactory;
        }
    }

    private void checkDbVersion() throws OMException {
        if( dbfactory.checkBEVersionDSExists(omConfig) ) {
            // dbversion >= 1.1 already.
            checkVersionRecord();
        } else {
            if(dbfactory.checkConceptDSExists(omConfig)) {
                // No BEVersion table but Concept table already exists. dbversion == 1.0
                throw new OMException("Engine and Database Version mismatch!. Engine Version = " + cep_commonVersion.version + " Database Version = 1.0.0" );
            } else {
                // looks like the db is being created anew. No problem.
                createVersionRecord();
            }
        }

    }

    public static class VersionRecord {
        private static final String BE_VERSION_RECORD="BEVersionRecord";
        public String db_be_version;
    }

    private void createVersionRecord() throws OMException {
        DataStore versionds = dbfactory.createBEVersionDataStore(omConfig, defaultSerializer);
        versionds.init();
        versionds.add(null, VersionRecord.BE_VERSION_RECORD, cep_commonVersion.version);
        versionds.close();
    }

    private void checkVersionRecord() throws OMException {
        DataStore versionds = dbfactory.createBEVersionDataStore(omConfig, defaultSerializer);
        versionds.init();
        VersionRecord versionrec = new VersionRecord();
        versionds.fetch(null, VersionRecord.BE_VERSION_RECORD, versionrec);
        versionds.close();

        //Perform this check only if the property below is true
        boolean needsDBVersionCheck =
                beProperties.getBoolean("be.engine.om.datastore.versionCheck", true);
        if(needsDBVersionCheck && ! compareVersions(cep_commonVersion.version, versionrec.db_be_version)) {
            //if( ! versionrec.db_be_version.equals(be_engineVersion.version)) {
            // Version mismatch!
            throw new OMException("Engine and Database Version mismatch and versions are incompatible!. Engine Version = " + cep_commonVersion.version + " Database Version = " + versionrec.db_be_version);
        }
    }

    private boolean compareVersions(String engine_version, String db_version) {
        String[] engineVersions = {
                "1.0.0",
                "1.1.0",
                "1.1.1",
                "1.2.0",
                "1.2.1",
                "1.3.0",
                "1.3.1",
                "1.4.0",
                "2.0.0",
                "2.1.0",
                "2.1.1",
                "2.2.0",
                "3.0.0",
                "3.0.1",
				"3.0.2",
                "4.0.0",
                "4.0.1",
                "5.0.0"

        };
        String[][] compatibleDbVersions = {
                {}, // 1.0.0 only compatible with itself.
                {}, // 1.1.0 only compatible with itself.
                {"1.1.0"}, // 1.1.1
                {"1.1.0", "1.1.1"}, // 1.2.0
                {"1.1.0", "1.1.1", "1.2.0" }, // 1.2.1
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1"}, // 1.3.0
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1", "1.3.0"}, // 1.3.1
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1", "1.3.0", "1.3.1"}, // 1.4.0
                {}, //2.0 is incompatible w/ previous versions
                {"2.0.0"}, // 2.1.0
                {"2.0.0", "2.1.0"}, // 2.1.1
                {"2.0.0", "2.1.0", "2.1.1"}, // 2.2.0
                {"2.0.0", "2.1.0", "2.1.1", "2.2.0"}, // 3.0.0
                {"2.0.0", "2.1.0", "2.1.1", "2.2.0", "3.0.0"}, // 3.0.1
				{"2.0.0", "2.1.0", "2.1.1", "2.2.0", "3.0.0", "3.0.1"}, // 3.0.2
                {"2.0.0", "2.1.0", "2.1.1", "2.2.0", "3.0.0", "3.0.1", "3.0.2"}, // 4.0.0
				{"2.0.0", "2.1.0", "2.1.1", "2.2.0", "3.0.0", "3.0.1", "3.0.2", "4.0.0"}, // 4.0.1
				{"2.0.0", "2.1.0", "2.1.1", "2.2.0", "3.0.0", "3.0.1", "3.0.2", "4.0.0", "4.0.1"} // 5.0.0

        };
        assert (engineVersions.length == compatibleDbVersions.length) : ("engineVersions.length=" + engineVersions.length + " compatibleDbVersions.length=" + compatibleDbVersions.length) ;

        // Simple case first.
        if(db_version.equals(engine_version))
            return true;

        int i,j;
        for (i = 0; i < engineVersions.length; i++) {
            if(engineVersions[i].equals(engine_version))
                break;
        }

        if(i == engineVersions.length)
            return false;

        for(j = 0; j < compatibleDbVersions[i].length; j++) {
            if(compatibleDbVersions[i][j].equals(db_version))
                return true;
        }

        return false;
    }

    public Property getProperty(Concept subject, String className) {
        return propman.getProperty(subject, className);
    }

    public Property getProperty(long subjectId, String className) {
        return propman.getProperty(subjectId, className);
    }

    public Element getNamedInstance(String uri, Class entityClz) {
        Element ni=super.getElement(uri);
        if (ni == null) {
            long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(entityClz);
            try {
                Constructor cons = entityClz.getConstructor(new Class[] {long.class, String.class});
                ni = (Element) cons.newInstance(new Object[] {new Long(id), uri});
                ((RuleSessionImpl) session).loadObject(ni);
                addInstance((Concept) ni);
                return ni;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } else {
            return ni;
        }
    }

    public void addInstance(Concept c) {
        cm.addConcept(c);
        Property[] props = c.getProperties();
        for(int i = 0; i < props.length; i++) {
            if(props[i] instanceof PropertyAtom) {
                if(((PropertyAtom)props[i]).isSet())
                    addProperty(props[i]);
            }
            else if (props[i] instanceof PropertyArray) {
                if(((PropertyArray)props[i]).length() > 0)
                    addProperty(props[i]);
            }
            else {
                throw new RuntimeException("Unknown PropertyType <" + props[i] + ">");
            }
        }
        //add state machine property
        if(((ConceptImpl)c).sm != null) {
            addProperty(((ConceptImpl)c).getMachineClosed());
            addProperty(((ConceptImpl)c).getTransitionStatuses());
        }
    }

    public void removeInstance(Concept c) {
        ((ConceptImpl)c).removeProperties();
        cm.removeConcept(c);
    }


    Event getEventFromCache(long id) {
        return eventlog.getEvent(id);
    }

    public void addEvent(Event e) {
        throw new RuntimeException("PROGRAM ERROR: This method shouldn't be called");
//        eventlog.addEvent(e);
    }

    public void removeEvent(Event e) {
        throw new RuntimeException("PROGRAM ERROR: This method shouldn't be called");
//        eventlog.removeEvent(e);
    }

    public void addProperty(Property p) {
        propman.add(p);
    }

    public void removeProperty(Property p) {
        propman.remove(p);
    }

    public void removeProperty(Concept subject, String name) {
        propman.remove(subject, name);
    }

    public void modifyProperty(Property p) {
        propman.modify(p);
    }

    public void applyChanges(RtcOperationList rtcList) {
        if (rtcList.isEmpty()) {
            return;
        }

        Iterator ite = rtcList.iterator();
        while(ite.hasNext()) {
            BaseHandle handle = (BaseHandle) ite.next();
            Object obj = handle.getObject();

            if(obj instanceof Event) {
                if (handle.isRtcAsserted_AND_Deleted()) { //assert and delete in same rtc
                    if(obj instanceof TimeEvent) {
                        eventlog.removeEvent((Event)obj, handle);
                        if(!((TimeEvent)obj).isRepeating()) {
                            if(((RuleSessionConfigImpl)session.getConfig()).removeRefOnDeleteNonRepeatingTimeEvent()) {
                                //null out the reference in the handle to avoid holding on to this object if the handle is still in a timer queue
                                ((AbstractEventHandle)handle).removeRef();
                            }
                        }
                    }
                    else if(obj instanceof SimpleEvent)
                        eventlog.ackEvent((SimpleEvent) obj);
                }
                else if (handle.isRtcDeleted()) {
                    if(!(obj instanceof TimeEvent) || !((TimeEvent)obj).isRepeating()) {
                        eventlog.removeEvent((Event)obj, handle);
                        if(((RuleSessionConfigImpl)session.getConfig()).removeRefOnDeleteNonRepeatingTimeEvent()) {
                            //null out the reference in the handle to avoid holding on to this object if the handle is still in a timer queue
                            ((AbstractEventHandle)handle).removeRef();
                        }
                    }
                }
                else if (handle.isRtcAsserted()) {
                    if (obj instanceof SimpleEvent) {
                        eventlog.addEvent((Event)obj, handle);
                        eventlog.ackEvent((SimpleEvent) obj);
                    } //ignore TimeEvent
                }
                else if(handle.isRtcScheduled()) {
                    eventlog.addEvent((Event)obj, handle);
                }
            }
            else {  //Concept instance
                if(handle.isRtcAsserted_AND_Deleted()) //assert and delete in same rtc
                    removeProperties((Concept)obj);
                else if (handle.isRtcDeleted())
                    removeInstance((Concept)obj);
                else if (handle.isRtcAsserted()) {
                    addInstance((Concept)obj);
                    evictProperties((Concept)obj);
                }
                //ignore modify - will be saved in property manager
            }
            handle.rtcClearAll();
        }
    }

    private void removeProperties(Concept concept) {

        Property[] properties = concept.getProperties();
        for(Property prop : properties) {
            this.removeProperty(prop);
        }
    }

    private void evictProperties(Concept concept) {
         Property[] properties = concept.getProperties();
        for(Property prop : properties) {
            prop = null;
        }
    }

    public void recover() throws OMException {

        cm.recover();
        eventlog.recover();
        if(dbFactory().checkMigratedActiveStatesDSExists(omConfig)) {
            createStateTimeouts();
        }
    }
    
    private void createStateTimeouts() throws OMException {
        migratedActiveStates = dbFactory().createMigratedActiveStatesDataStore(omConfig, defaultSerializer);
        migratedActiveStates.init();
        
        final int[] num = new int[2];
        num[0] = 0;
        num[1] = 0;
       m_logger.log(Level.INFO,ResourceManager.getInstance().getMessage("be.om.migratedActiveStates.migrating"));
        migratedActiveStates.readAll(new DataStore.ReadCallback() {
            public void readObj(Object obj) throws Exception {
                assert(obj instanceof StateMachineConceptImpl.MigratedStateTimeoutEvent);
                if(obj instanceof StateMachineConceptImpl.MigratedStateTimeoutEvent) {
                   if(StateMachineConceptImpl.MigratedStateTimeoutEvent.migrateStateTimeout((StateMachineConceptImpl.MigratedStateTimeoutEvent)obj)) num[1]++;
                }
                num[0]++;
                if(num[0]%1000 == 0) {
                   m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.migratedActiveStates.loaded",new Long(num[0])));
                }
            }
        });
       m_logger.log(Level.INFO,ResourceManager.getInstance().formatMessage("be.om.migratedActiveStates.migrated", new Long(num[0])));
        m_logger.log(Level.DEBUG,ResourceManager.getInstance().formatMessage("be.om.migratedActiveStates.total_scheduled",new Long(num[1])));
    }

    public long lastCheckpointDuration() {
        return lastCheckpointDuration;
    }


//    synchronized public void checkpoint() throws OMException {  //todo - revert later when async chpt is implemented
    synchronized public void checkpoint() throws OMException {
        Guard guard = session.getWorkingMemory().getGuard();
        long ops;
        try {
            guard.lock();
            ops = numOutstandingOps;
            prepareCheckpoint();
//            if (syncCheckpoint)   //todo - revert later when async chpt is implemented
                doCheckpoint(ops);
        } finally {
            guard.unlock();
        }
//        if (!syncCheckpoint)     //todo - revert later when async chpt is implemented
//            doCheckpoint(ops);   //todo - revert later when async chpt is implemented
        this.ackEvents();
    }


//    synchronized public void forceCheckpoint() throws OMException {
    synchronized public void forceCheckpoint() throws OMException {     //todo - revert later when async chpt is implemented
        Guard guard = session.getWorkingMemory().getGuard();
        try {
            guard.lock();
            long ops = numOutstandingOps;
            this.prepareCheckpoint();
            this.doCheckpoint(ops);
            this.ackEvents();
        } finally {
            guard.unlock();
        }
    }


    void incrementOutstandingOps() {
        ++numOutstandingOps;
        if(checkpointOutstandingOpsLimit != 0L) {
            if(numOutstandingOps > checkpointOutstandingOpsLimit)
                checkpointer.forceCheckpoint();
        }
    }

    private void prepareCheckpoint() {
        if(m_logger.isEnabledFor(Level.INFO) && (numOutstandingOps > 0 || eventlog.numOfEventToAck() > 0))
           m_logger.log(Level.INFO,"Preparing Checkpoint... ");

        cm.prepareCheckpoint();
        eventlog.prepareCheckpoint();
        propman.prepareCheckpoint();
        numOutstandingOps = 0L;
    }

    private void doCheckpoint(long ops) throws OMException {
        long t1=System.currentTimeMillis();
//        if(ops > 0L && m_logManager.isDebug()) {
//            m_logManager.logDebug("Started checkpoint at " + new Date(t1));
//        }
        DBTransaction txn = null;
        if(!noRecovery) {
            txn = env.starttxn();
        }
        cm.doCheckpoint(txn);
        eventlog.doCheckpoint(txn);
        propman.doCheckpoint(txn);
        if(migratedActiveStates != null) {
            //it is safe to do this here because 
            //the entire migratedActiveStates db will be read before it is deleted
            //because  recovery will complete before the checkpointer starts 
            //(RuleSessionImpl.init is called before RuleSessionImpl.start)
            migratedActiveStates.deleteDatabase(txn);
            migratedActiveStates = null;
        }
        if(txn != null) {
            txn.commit();
        }
        propman.evictOldProperties();
        lastCheckpointDuration = System.currentTimeMillis() - t1;
        if(ops > 0L && m_logger.isEnabledFor(Level.INFO))
           m_logger.log(Level.INFO,"Completed " + ops + " DB operations in " + lastCheckpointDuration + " ms.");


        if ( m_logger.isEnabledFor(Level.WARN) && checkpointer.getCheckpointInterval() > 0 &&  lastCheckpointDuration > checkpointer.getCheckpointInterval()) {
            m_logger.log(Level.WARN,ResourceManager.getInstance().formatMessage("be.om.checkpointer.timeexceed", new Long(lastCheckpointDuration/1000L), new Long(checkpointer.getCheckpointInterval()/1000L)));
        }
        //TODO If checkpoint duration is aproaching checkpoint interval display warnings..
        /*else if (checkpointer.getCheckpointInterval() > 0) {
            double rate = ((double)checkpointDuration) / ((double)checkpointer.getCheckpointInterval());

            if((lastCheckpointDuration != 0L)
                    && ((checkpointDuration - lastCheckpointDuration) > 0)
                    && (rate > 0.75)) {
                mTrace.traceWarn(TraceMessage.build("be.om.checkpointer.timeincrease", new Long(checkpointDuration/1000L), new Long( checkpointer.getCheckpointInterval()/1000L)));
            }
        }*/
    }


    private void ackEvents() {
        eventlog.ackEvents();
    }

    private void putCachePercent() {
        Properties beprops = session.getRuleServiceProvider().getProperties();
        //Total percentage of JVM memory to use for BDB caches. Gets divided among the BDB sessions.
        int totalCachePercent = parseInt(beprops.getProperty("be.engine.om.berkeleydb.internalcachepercent"), 20);
        //number of BDB sessions in this project (assumes that there is only one project per JVM)
        int numBDB = parseInt(omConfig.getProperty(Constants.PROPERTY_NAME_OM_BDB_NUM_BDB), 1);

        //it is optional to specify the cache weights in the TRA, this may return -1
        int cachePercent = getWeightedCachePercent(numBDB, totalCachePercent);
        if(cachePercent <= 0) {
            //this property is set by putWeightedCachePercents)
            cachePercent = parseInt(beprops.getProperty("be.engine.om.berkeleydb.defaultcachepercent"), totalCachePercent / numBDB);
        }
        String name = session.getName();
        omConfig.setProperty("cachepercent", String.valueOf(cachePercent));
    }

    //gives the cache percent for this session as calculated based on the weights for each session specified in the TRA
    private int getWeightedCachePercent(int numBDB, int totalCachePercent) {
        Properties beprops = session.getRuleServiceProvider().getProperties();
        //this would have been written into beprops by putWeightedCachePercents
        int weightedCachePercent = parseInt(beprops.getProperty("be.engine.om.berkeleydb.cachepercent." + session.getName()), -1);
        if(weightedCachePercent <= 0) {
            //if weightedCachePercent wasn't found, then try initializing the weighted cache values for each session
            putWeightedCachePercents(numBDB, totalCachePercent);
            //then retry fetching the property
            weightedCachePercent = parseInt(beprops.getProperty("be.engine.om.berkeleydb.cachepercent." + session.getName()), -1);
        }
        return weightedCachePercent;
    }

    private void putWeightedCachePercents(int numBDB, int totalCachePercent) {
        BEProperties beprops = (BEProperties)session.getRuleServiceProvider().getProperties();
        //this property gets set at the end, so if it exists, then there is no need to repeat the work
        if(beprops.getProperty("be.engine.om.berkeleydb.defaultcachepercent") != null) return;

        BEProperties weightProps  = beprops.getStartsWith("be.engine.om.berkeleydb.cacheweight.");
        String[] sessionNames = new String[weightProps.size()];
        int[] weights = new int[sessionNames.length];
        int weightsTotal = 0;
        int ii = 0;
        for(Iterator it = weightProps.entrySet().iterator(); it.hasNext();) {
            Map.Entry ent = (Map.Entry)it.next();
            String sessionName = (String)ent.getKey();
            int lastDot = (sessionName.lastIndexOf('.'));
            if(lastDot >= 0) {
                sessionName = sessionName.substring(lastDot+1);
            }
            sessionNames[ii] = sessionName;
            String weightString = (String)ent.getValue();
            int weight = parseInt(weightString, 1);
            weights[ii] = weight;
            weightsTotal += weight;
            ii++;
        }
        //add a default weight of 1 for each additional BDB session that didn't have an entry in cacheWeightsString
        //this doesn't really do the right thing if the weighting strings contains session names that
        //don't correspond to a BDB session in the project, but it at least lets you only mention sessions
        //whose weights need to be increased
        int missingSessions = numBDB - weights.length;
        if(missingSessions > 0) weightsTotal += missingSessions;
        //avoid divide by zero error
        if(weightsTotal == 0) weightsTotal = 1;
        
        for(ii = 0; ii < weights.length; ii++) {
            int cachePercent = (totalCachePercent * weights[ii]) / weightsTotal;
            beprops.setProperty("be.engine.om.berkeleydb.cachepercent." + sessionNames[ii], String.valueOf(cachePercent));
        }
        //set the default cache percent for sessions not specifically mentioned (assume their weight is 1)
        beprops.setProperty("be.engine.om.berkeleydb.defaultcachepercent", String.valueOf(totalCachePercent / weightsTotal));
    }

    private static int parseInt(String str, int _default) {
        if(str == null) return _default;

        int num = _default;
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {}
        return num;
    }

    protected AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return new PersistentConceptHandle(obj, _next, _typeInfo);
    }

    protected AbstractElementHandle getNewElementExtHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return new PersistentConceptExtHandle(obj, _next, _typeInfo);
    }

    protected AbstractEventHandle getNewEventHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return new PersistentEventHandle(obj, _next, _typeInfo);
    }

    protected AbstractEventHandle getNewEventExtHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return new PersistentEventExtHandle(obj, _next, _typeInfo);
    }

    static class PersistentConceptHandle extends ElementHandle implements PropertyBasedStoreHandle, PersistentHandle {
        public PersistentConceptHandle(Element _element, AbstractElementHandle _next, TypeInfo _typeInfo) {
            super(_element, _next, _typeInfo);
        }

        public void removeRef() {

        }

        public Object getRef() {
            return element;
        }

        public Object setRef(Object entity) {
            return element;
        }
    }

    static class PersistentConceptExtHandle extends ElementExtHandle implements PropertyBasedStoreHandle, PersistentHandle {
        public PersistentConceptExtHandle(Element _element, AbstractElementHandle _next, TypeInfo _typeInfo) {
            super(_element, _next, _typeInfo);
        }

        public void removeRef() {

        }

        public Object getRef() {
            return element;
        }

        public Object setRef(Object entity) {
            return element;
        }
    }

    public boolean isObjectStore() {
        return true;
    }
}
