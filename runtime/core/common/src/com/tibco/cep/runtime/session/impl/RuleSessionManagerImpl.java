package com.tibco.cep.runtime.session.impl;


import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.util.BEJarVersionsInspector;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.impl.BEArchiveResourceImpl;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 29, 2006
 * Time: 4:34:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleSessionManagerImpl extends RuleSessionManager {

    private static final String BRK = System.getProperty("line.separator", "\n");

    public static byte STATE_ERROR = -1;
    public static byte STATE_UNINITIALIZED = 0;
    public static byte STATE_INITIALIZED = 1;
    public static byte STATE_STARTED = 2;
    public static byte STATE_STOP = 3;

    protected LinkedHashMap<String, RuleSession> sessionMap = new LinkedHashMap<String, RuleSession>();
    protected LinkedHashMap<RuleSession, Integer> sessionIndices = new LinkedHashMap<RuleSession, Integer>();
    protected RuleServiceProvider serviceProvider;
    protected BEProperties env;
    protected byte status = STATE_UNINITIALIZED;
    private final Object rtcGuard = new Object();
    protected Logger logger;

    private int lastIndex = 0;



    public RuleSessionManagerImpl(RuleServiceProvider serviceProvider, BEProperties env) {
        super();
        this.serviceProvider = serviceProvider;
        this.env = env;
        this.logger = ((RuleServiceProviderImpl)this.serviceProvider).logger;
    }

//    public Logger getLogger() {
//        return logger;
//    }

    public BEProperties getEnv() {
        return env;
    }

    public RuleSession createClusterRuleSession() throws Exception{
        RuleSessionImpl session = new RuleSessionImpl(BEArchiveResourceImpl.newBEArchive(DEFAULT_CLUSTER_RULESESSION_NAME),this);
        //RuleSessionImpl session = new RuleSessionImpl(this);
        session.init(false);
        session.start(true);
        return session;
//        session.invokeFunction(rulefn, new Object[0], true);
//        session.stop();
    }


    public void init() throws Exception  {
        //SS Fixed: SRID:1-7V7RD4 ++
        boolean isClient = Boolean.valueOf(env.getProperty("com.tibco.cep.rsp.isClient", "false")).booleanValue();
        if (isClient) return;
        //SS Fixed: SRID:1-7V7RD4 --
        if (status >= STATE_INITIALIZED) {
            return;
        }
        final DeployedProject project = serviceProvider.getProject();
        this.logDesignTimeComponents(project);
        BEProperties props = (BEProperties) getRuleServiceProvider().getProperties();

        //------------

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        Configuration rootConfig =  registry.getConfiguration();

        String projectName = project.getName();
        Configuration projectConfig = new Configuration(projectName, props);
        rootConfig.addChild(projectConfig);

        //------------

        int index = 0;
        for (Iterator i = project.getDeployedBEArchives().iterator(); i.hasNext(); ) {

            final BEArchiveResource bear = (BEArchiveResource) i.next();
            RuleSessionImpl session;
            final boolean isCacheEnabled = this.serviceProvider.getProject().isCacheEnabled();

            if (bear.getType() == Constants.ArchiveType.QUERY) {
                session = this.createQuerySession(bear);
                session.init(((RuleServiceProviderImpl) serviceProvider).isCacheServerMode());
                this.sessionMap.put(bear.getName(), session);
                this.sessionIndices.put(session, index);
            }
            else if (bear.getType() == Constants.ArchiveType.DASHBOARD) {
                session = this.createDashboardSession(bear, props, null);
                session.init(((RuleServiceProviderImpl) serviceProvider).isCacheServerMode());
                this.sessionMap.put(bear.getName(), session);
                this.sessionIndices.put(session, index);
            }
            else {
                if(!isCacheEnabled) {
                    session = new RuleSessionImpl(bear, this);
                    session.init(((RuleServiceProviderImpl) serviceProvider).isCacheServerMode());
                    this.sessionMap.put(bear.getName(), session);
                    this.sessionIndices.put(session, index);

                }  else {

                    if (((RuleServiceProviderImpl) serviceProvider).isCacheServerMode()) {
                        session = new RuleSessionImpl(bear, this);
                        session.init(((RuleServiceProviderImpl) serviceProvider).isCacheServerMode());
                        String sessionName = bear.getName();
                        this.sessionMap.put(sessionName, session);
                        this.sessionIndices.put(session, index);
                        if (logger.isEnabledFor(Level.INFO)) {
                            logger.log(Level.INFO,"Created Rulesession: " + sessionName);
                        }
                    } else {

                        String agentGroupName = getAgentGroupName(bear);
                        int numInstances = props.getInt("Agent." + agentGroupName + ".numlocal", 1);
                        for (int instanceId = 0; instanceId < numInstances; instanceId++) {
                            // dont add instanceid to the Rulesession name because it is bound to channel destinations
                            // and if the destination session name does not match no msgs go through
                            session = new RuleSessionImpl(bear, props, this);
                            session.init(((RuleServiceProviderImpl) serviceProvider).isCacheServerMode());
                            String sessionName = null;
                            if (numInstances == 1) {
                                sessionName = session.getName();
                                this.sessionMap.put(sessionName, session);
                                this.sessionIndices.put(session, index);
                                ++index;
                            } else {
                                sessionName = session.getName() + "." + instanceId;
                                this.sessionMap.put(sessionName, session);
                                this.sessionIndices.put(session, index);
                                ++index;
                            }
                            if (logger.isEnabledFor(Level.INFO)) {
                                logger.log(Level.INFO,"Created Rulesession: " + sessionName);
                            }
                        }
                    }
                }
            }
         ++index;
        }
        checkDuplicateCacheName();
        this.lastIndex = index;
        this.status = STATE_INITIALIZED;
    }

    private String getAgentGroupName(BEArchiveResource bar) throws Exception {
        RuleSessionConfigImpl config = new RuleSessionConfigImpl(bar, getRuleServiceProvider());
        String agentGroupName = config.getCacheConfig().getProperty(Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME);
        return agentGroupName;
    }


    private RuleSessionImpl createQuerySession(BEArchiveResource bear) throws Exception {
        final Class qsClass = Class.forName("com.tibco.cep.query.service.impl.QueryRuleSessionImpl");
        final Object qsObject = qsClass
                .getConstructor(BEArchiveResource.class, RuleSessionManagerImpl.class)
                .newInstance(bear, this);

        return (RuleSessionImpl) qsObject;
    }
    
    private RuleSessionImpl createDashboardSession(BEArchiveResource bear, BEProperties props, ObjectManager objectManager) throws Exception {
        final Class<? extends RuleSessionImpl> sessionClass = Class.forName("com.tibco.cep.dashboard.integration.be.DashboardSessionImpl").asSubclass(RuleSessionImpl.class);
        Constructor<? extends RuleSessionImpl> sessionConstructor = sessionClass.getConstructor(BEArchiveResource.class, BEProperties.class, RuleSessionManagerImpl.class, ObjectManager.class);
        return sessionConstructor.newInstance(bear, props, this, objectManager);
    }    


    public void logDesignTimeComponents(DeployedProject project) {
        final Map<String, BEJarVersionsInspector.Version> versions =
                project.getBEArchiveResourceProvider().getDesignTimeVersions();

        if (versions.size() > 0) {
            logger.log(Level.INFO,"EAR generated with:");
            int maxLength = 0;
            for (Iterator it = versions.keySet().iterator(); it.hasNext();) {
                final String jarName = (String) it.next();
                maxLength = Math.max(maxLength, jarName.length());
            }
            final char[] charSpaces = new char[maxLength];
            Arrays.fill(charSpaces, ' ');
            final String spaces = new String(charSpaces);
            String jarNameList = "";
            for (BEJarVersionsInspector.Version version : versions.values()) {
            		final String versionString = version.getVersion();
            		final String component = version.getComponent();
            		if(!jarNameList.contains(version.getName())){
            			logger.log(Level.INFO," "
            					+ (version.getName() + spaces).substring(0, maxLength)
            					+ " : "
            					+ ((null == versionString) ? "" : versionString)
            					+ " - "
            					+ ((null == component) ? "" : component));
            			jarNameList = jarNameList + version.getName() +  File.pathSeparator;
            		}
            				
             }
        }
    }


    public void start(boolean activeMode) throws Exception {
        if (status < STATE_INITIALIZED) {
            //TODO - get it from ResourceBundle
            String errString = "Cannot start - RulesessionManager is in invalid state (error/uninitialized). Try initializing again";
            this.logger.log(Level.ERROR, errString);
            throw new Exception (errString);
        }
        Iterator<RuleSession> i = sessionMap.values().iterator();
        while (i.hasNext()) {
            i.next().start(activeMode);
        }
        status = STATE_STARTED;
    }

    public void setActiveMode(boolean activeMode) {
        Iterator<RuleSession> i = sessionMap.values().iterator();
        while (i.hasNext()) {
            i.next().setActiveMode(activeMode);
        }
    }


    public void stop() throws Exception {
        if (status == STATE_STOP ) {
            return;
        }
        if (status < STATE_INITIALIZED) {
            //TODO - get it from ResourceBundle
            String errString = "Cannot stop - RulesessionManager is in invalid state (error/uninitialized). Try initializing again";
            this.logger.log(Level.ERROR, errString);
            throw new Exception (errString);
        }
        if (status == STATE_STARTED) {
            Iterator<RuleSession> i = sessionMap.values().iterator();
            while (i.hasNext()) {
                i.next().stop();
            }
        }
        status = STATE_STOP;
    }

    public void shutdown()  {
        rtcLocker.setAction(new LockAction() {
            public void onLockAll() throws Throwable {
                logger.log(Level.INFO,"Rulesessions shutdown successfully");
            }

            public void onLock(RuleSession rs) {
//                rs.stop();
//                rs.shutdown();
                rs.stopAndShutdown();
            }
        });
        rtcLocker.setHoldLock(false);
        rtcLocker.lockWMs();
    }

    public RuleSession[] getRuleSessions() {
        RuleSession[] ret = new RuleSession[sessionMap.size()];
        sessionMap.values().toArray(ret);
        return ret;
    }

    public String[] getRuleSessionNames() {
        return sessionMap.keySet().toArray(new String[0]);
    }

    public RuleSession getRuleSession(String name) {
        return sessionMap.get(name);
    }

    public RuleSession removeRuleSession(String name) {
        RuleSession session = sessionMap.remove(name);
//        session.stop();
//        session.shutdown();
        session.stopAndShutdown();
        return session;
    }
    
    public List<RuleSession> getRelatedRuleSessions(String prefix) {
        ArrayList<RuleSession> sessions = new ArrayList<RuleSession>();
        if(prefix != null) {
            for(Map.Entry<String, RuleSession> entry : (Set<Map.Entry<String, RuleSession>>)sessionMap.entrySet()) {
                if(entry.getKey() != null && entry.getKey().startsWith(prefix)) {
                    if(entry.getValue() != null) {
                        sessions.add(entry.getValue());
                    }
                }
            }
        }
        return sessions;
    }

    public synchronized RuleSession createRuleSession(String name) throws Exception {
        return this.createRuleSession(name, null);
    }
    public synchronized RuleSession createRuleSession(String name, ObjectManager objectManager) throws Exception {
        RuleSession session = sessionMap.get(name);

        if (session != null)
            throw new Exception ("Rulesession by this name already exist: " + name);


        DeployedProject project = serviceProvider.getProject();
        Collection archives = project.getDeployedBEArchives();

        Iterator i = archives.iterator();
        while (i.hasNext()) {
            BEArchiveResource bear = (BEArchiveResource) i.next();
            String refClassName = bear.getReferenceClassName();
            if (name.equals(refClassName)) {
                Constants.ArchiveType archiveType = bear.getType();
                //Suresh TODO - should be done by class.forName.
                switch(archiveType) {
                    case QUERY:
                        session = this.createQuerySession(bear);
                        break;
                    case DASHBOARD:
                        session = this.createDashboardSession(bear, (BEProperties)serviceProvider.getProperties(), objectManager);
                        break;
                    case INFERENCE:
                    case LIVEVIEW:	
                    case DATAGRID: //CacheServer
                        session = new RuleSessionImpl(bear, (BEProperties)serviceProvider.getProperties(), this, objectManager);
                        break;
                    case PROCESSGRAPH:
                        session = this.createProcessSession(bear, (BEProperties)serviceProvider.getProperties(), this, objectManager );
                        break;
                    default: //CacheServer
                        session = new RuleSessionImpl(bear, this);
                        break;
                }

                //((RuleSessionImpl)session).init(((RuleServiceProviderImpl)serviceProvider).isCacheServerMode()); Init in the inference engine.
                sessionMap.put(name, session);
                sessionIndices.put(session, ++this.lastIndex);
                return session;
            }

        }

        throw new Exception("No Archive configured by this name in the deployed Project");

    }


    private RuleSession createProcessSession(BEArchiveResource bear, BEProperties properties, RuleSessionManagerImpl ruleSessionManager, ObjectManager objectManager) throws Exception {

        Class<RuleSession> ruleSessionClass = (Class<RuleSession>) Class.forName("com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession");
        Constructor<RuleSession> ctor = ruleSessionClass.getConstructor(BEArchiveResource.class, BEProperties.class, RuleSessionManager.class, ObjectManager.class);

        RuleSession ruleSession =  ctor.newInstance(bear, properties, ruleSessionManager, objectManager);

        return ruleSession;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return serviceProvider;
    }

    public void setCurrentRuleSession(RuleSession sess) {
        RuleSessionManager.currentRuleSessions.set(sess);
    }

    /**
     * Unbinds the RuleSession that is tied to the calling Thread.
     * @see #setCurrentRuleSession(com.tibco.cep.runtime.session.RuleSession) . 
     */
    public void unsetCurrentRuleSession() {
        RuleSessionManager.currentRuleSessions.remove();
    }

    void checkDuplicateCacheName() throws OMException {
//        RuleSessionImpl[] sessions = new RuleSessionImpl[m_beArchives.values().size()];
//        m_beArchives.values().toArray(sessions);
//        for(int i = 0; i < sessions.length-1; i++) {
//            ObjectManager om = sessions[i].getObjectManager();
//            if(om instanceof DefaultDistributedCacheBasedStore) {
//                for(int j = i+1; j < sessions.length; j++) {
//                    ObjectManager om2 = sessions[j].getObjectManager();
//                    if(om2 instanceof DefaultDistributedCacheBasedStore) {
//                        String cache1 = ((DefaultDistributedCacheBasedStore)om).getMasterCacheName();
//                        String cache2 = ((DefaultDistributedCacheBasedStore)om2).getMasterCacheName();
//                        if(cache1.equals(cache2)) {
//                            throw new OMException("Duplicate Cache Name <" + cache1 + "> in RuleSession <"
//                                    + sessions[i].getName() + "> and <" + sessions[j].getName() +">");
//                        }
//                    }
//                }
//            }
//        }
    }

    public void notifyHotDeployment(boolean success) {
        if(((RuleServiceProviderImpl)serviceProvider).isCacheServerMode()) {
            // Do not throw hotdeploy advisory event when deploying on a CacheServer
            return;
        }
        String project = ((DeployedProject)serviceProvider.getProject()).getRepoPath();
        RuleSessionImpl[] sessions = new RuleSessionImpl[sessionMap.values().size()];
        sessionMap.values().toArray(sessions);
        AdvisoryEvent advEvent;
        long id = this.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class);
        if(success) {
            String msg = "Hot deployed project " + project;
            advEvent = new AdvisoryEventImpl(id, null, AdvisoryEvent.CATEGORY_DEPLOYMENT,
                    AdvisoryEventDictionary.DEPLOYMENT_HotDeploySuccess, msg);
        }
        else {
            String msg = "Failed to hot deploy project " + project;
            advEvent = new AdvisoryEventImpl(id, null, AdvisoryEvent.CATEGORY_DEPLOYMENT,
                    AdvisoryEventDictionary.DEPLOYMENT_HotDeployFail, msg);
        }
        for(int i = 0; i < sessions.length; i++) {
            RuleSessionImpl session = sessions[i];
            try {
                session.assertObject(advEvent, true);
            } catch (DuplicateExtIdException e) {
                e.printStackTrace();//impossible
            }
        }
    }

    RTCLocker rtcLocker = new RTCLocker();

    public void suspendWMs() {
        logger.log(Level.DEBUG,"Locking RTC ...");
        rtcLocker.lock();
    }

    public void suspendWMs(boolean holdLock) {
        logger.log(Level.DEBUG,"Locking RTC ...");
        rtcLocker.setHoldLock(holdLock);
        rtcLocker.lock();
    }

    public void releaseWMs() {
        logger.log(Level.DEBUG,"Unlocking RTC ...");
        rtcLocker.unLock();
        rtcLocker = new RTCLocker();
    }

    public boolean isLocked() {
        return rtcLocker.isLocked();
    }

    public int getSessionIndex(RuleSession session) {

        return sessionIndices.get(session);
    }


    private interface LockAction {
        public void onLockAll() throws Throwable;
        public void onLock(RuleSession rs);
    }

    private class  RTCLocker extends Thread  {
        public static final int WM_LOCK = 0;
        public static final int WM_UNLOCK = 1;
        public static final int WM_IDLE = -1;

        private final  int [] m_lockStatus = new int[]{WM_IDLE};
        private boolean bholdLock = true;
        private LockAction action = null;

        public RTCLocker() {
            super("RTCLocker");
            this.bholdLock = true;
            this.action = null;
        }
        public RTCLocker(LockAction action) {
            super("RTCLocker");
            this.bholdLock = true;
            this.action = action;
        }
        public RTCLocker(boolean bholdLock) {
            super("RTCLocker");
            this.bholdLock = bholdLock;
            this.action = null;
        }

        public void setHoldLock(boolean bholdLock) {
            this.bholdLock = bholdLock;
        }

        public void setAction(LockAction action) {
            this.action = action;
        }


        public void run() {
            // get a lock on all WM's by this thread
            lockWMs();
        }

        public int lock() {
            this.start();
            synchronized(rtcGuard) {
                try {
                    rtcGuard.wait();
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR,"RTCLocker interrupted...", e);
                    unLock();
                }
            }
            synchronized(m_lockStatus) {
                return m_lockStatus[0];
            }
        }

        public void unLock() {
            synchronized(m_lockStatus) {
                m_lockStatus[0] = WM_UNLOCK;
                m_lockStatus.notify();
            }
        }

        public boolean isLocked() {
            synchronized(m_lockStatus) {
                return (m_lockStatus[0] == WM_LOCK);
            }
        }
        /**
         * Safely locks all rule sessions.
         * First the working memory is locked to make sure that all current
         * run-to-completion cycles are finished and that no other cycle can be started.
         *
         * @since 2.0
         */
        private void lockWMs() {
            RuleSession[] archs = getRuleSessions();
            lockWM(archs, 0);
        }

        private void lockWM(RuleSession[] archives, int sync) {
            if (sync == archives.length) {
                onLocksAcquired();
                if (this.bholdLock) {
                    holdLock();
                }
            } else {
                synchronized (((RuleSessionImpl) archives[sync]).getWorkingMemory()) {
                    onSessionLock(archives, sync);
                    lockWM(archives, sync + 1);
                }
            }
        }

        private void onSessionLock(RuleSession[] archives, int sync) {
            if (this.action != null) {
                try {
                    this.action.onLock(archives[sync]);
                } catch (Throwable e) {
                    logger.log(Level.DEBUG,"Error in LockAndWaitAction", e);
                }
            }
        }

        private void holdLock() {
            synchronized (m_lockStatus) {
                m_lockStatus[0] = WM_LOCK;
                while (m_lockStatus[0] == WM_LOCK) {
                    try {
                        m_lockStatus.wait();
                    } catch (InterruptedException e) {
                        logger.log(Level.ERROR,"RTCLockable has been interrupted before cache activateRTC notification ");
                    }
                }
            }
            logger.log(Level.DEBUG,"RTC is UnLocked.");
        }

        private void onLocksAcquired() {
            synchronized (rtcGuard) {
                rtcGuard.notifyAll();
                if(this.action != null) {
                    try{
                        this.action.onLockAll();
                    }catch(Throwable e) {
                        logger.log(Level.DEBUG,"Error in LockAndWaitAction",e);
                    }
                }

            }
        }

    }
}
