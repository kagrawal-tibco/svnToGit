package com.tibco.cep.runtime.session.impl;


import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedBEProject;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.ft.FTAsyncNodeManager;
import com.tibco.cep.runtime.service.ft.FTAsyncQueueManager;
import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.service.ft.FTNodeController;
import com.tibco.cep.runtime.service.ft.FTNodeManager;
import com.tibco.cep.runtime.service.ft.FTNodeManagerFactory;
import com.tibco.cep.runtime.service.ft.impl.FTAsyncQueueManagerImpl;
import com.tibco.cep.runtime.service.ft.impl.FTNodeAsyncControllerImpl;
import com.tibco.cep.runtime.service.om.ObjectStore;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 3, 2006
 * Time: 7:01:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTAsyncRuleServiceProviderImpl implements RuleServiceProvider, FTNodeController, Runnable {
    private RuleServiceProviderImpl m_rsp;
    private FTNodeManager ftNodeMgr;
    private FTNodeController ftAsyncNodeController;
    private FTAsyncQueueManager ftQueueMgr;
    private Thread m_thread;
    private final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState[] m_state = {FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STARTING_NODEMGR};
    private boolean sessionManagerStarted = false;
    private final Object guardRsp = new Object();
    private boolean isShuttingDown = false;
    private boolean secondaryStart = false;


    //
    ///////////////////////////////////////////////////////////////////////////////////////
    // internal static RuleServicePRoviderState class
    public static class FTRuleServiceProviderState {
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_STARTING_NODEMGR = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(0, "Starting Node Manager");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_NODEMGR_STARTED = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(1, "NodeManager Started");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_INITIALIZED_CHANNELS = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(2, "Initialized Channels");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_INITIALIZED_RULESESSIONS = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(3, "Initialized RuleSessions");
        //public static final FTRuleServiceProviderState STATE_INITIALIZED_RULEADMIN = new FTRuleServiceProviderState(4,"Initialized RuleAdmin");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_CHANNELS_STARTED = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(5, "Channels Started");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_CHANNELS_STOPPED = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(6, "Channels Stopped");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_INACTIVE = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(7, "Inactive");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_RULECYCLES_COMPLETED = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(8, "RuleCycles Completed");
        public static final FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState STATE_STOPPED = new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState(9, "Stopped");

        private int enumInt;
        private String stateStr;


        FTRuleServiceProviderState(int enumInt, String _stateStr) {
            this.enumInt = enumInt;
            this.stateStr = _stateStr;
        }

        public boolean equals(Object o) {
            if (o instanceof FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState) {
                return enumInt == ((FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState) o).enumInt;
            } else {
                return false;
            }
        }

        public static Collection values() {
            return Arrays.asList(new FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState []{
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STARTING_NODEMGR,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_NODEMGR_STARTED,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INITIALIZED_CHANNELS,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INITIALIZED_RULESESSIONS,
//                   STATE_INITIALIZED_RULEADMIN,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_CHANNELS_STARTED,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_CHANNELS_STOPPED,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INACTIVE,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_RULECYCLES_COMPLETED,
                    FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STOPPED
            });
        }

        public String toString() {
            return ("FTRuleServiceProviderState[ " + stateStr + " ]");
        }

        public int value() {
            return enumInt;
        }


    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // RuleServiceProvider overrides start

    /**
     * TODO
     *
     * @param instanceName
     * @param env
     * @throws Exception
     */

    public FTAsyncRuleServiceProviderImpl(String instanceName, Properties env) throws Exception {
        m_rsp = new RuleServiceProviderImpl(this,instanceName, env);
        overrideDefaultProperties();
        m_rsp.initProject();
    }


    public void configure(int configureType) throws Exception {
        ftQueueMgr = new FTAsyncQueueManagerImpl(m_rsp);
        ftQueueMgr.addNodeController(getControllerName(),this);
        ftAsyncNodeController = new FTNodeAsyncControllerImpl(ftQueueMgr);
        m_thread = new Thread(this, "FTRSProvider:" + getNodeName());
        m_thread.start();
    }

    /**
     * Gets the <code>ChannelManager</code> which manages all the {@link com.tibco.cep.runtime.channel.Channel Channels}
     * in this <code>RuleServiceProvider</code>.
     *
     * @return A <code>ChannelManager</code>.
     * @since 2.0.0
     */
    public ChannelManager getChannelManager() {
        return m_rsp.getChannelManager();
    }

    /**
     * Gets the class loader used by deserialization and hot deployment.
     *
     * @return a <code>ClassLoader</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public ClassLoader getClassLoader() {
        return m_rsp.getClassLoader();
    }

    /**
     * Gets the <code>GlobalVariables</code> that manages all global variables
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a GlobalVariables.
     * @.category public-api
     * @since 2.0.0
     */
    public GlobalVariables getGlobalVariables() {
        return m_rsp.getGlobalVariables();
    }

    /**
     * Gets the <code>IdGenerator</code> which manages unique IDs
     * in this <code>RuleServiceProvider</code>.
     *
     * @return an IdGenerator
     * @since 2.0.0
     */
    public IdGenerator getIdGenerator() {
        return m_rsp.getIdGenerator();
    }

//    /**
//     * Gets the <code>Logger</code> which manages all logging
//     * in this <code>RuleServiceProvider</code>.
//     *
//     * @return a <code>Logger</code>.
//     * @.category public-api
//     * @since 2.0.0
//     */
//    public com.tibco.cep.kernel.service.Logger getLogger() {
//        return m_rsp.getLogger();
//    }


    

    /**
     * Gets the name under which this <code>RuleServiceProvider</code> is registered
     * in its parent <code>RuleServiceProviderManager</code>.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0
     */
    public String getName() {
        return m_rsp.getName();
    }

    /**
     * Gets the <code>DeployedProject</code> which contains the part of the packaged project that has been deployed
     * to this <code>RuleServiceProvider</code>.
     *
     * @return a <code>DeployedProject</code>
     * @since 2.0.0
     */
    public DeployedProject getProject() {
        return m_rsp.getProject();
    }

    /**
     * Gets the properties used to build this <code>RuleServiceProvider</code>.
     *
     * @return a <code>Properties</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public Properties getProperties() {
        return m_rsp.getProperties();
    }

    /**
     * Gets the <code>RuleAdministrator</code> which manages remote administration
     * for this <code>RuleServiceProvider</code>.
     *
     * @return A <code>RuleAdministrator</code>.
     * @since 2.0.0
     */
    public RuleAdministrator getRuleAdministrator() {
        return m_rsp.getRuleAdministrator();
    }

    /**
     * Returns the <code>RuleRuntime</code> that manages the rule sessions in this <code>RuleServiceProvider</code>.
     *
     * @return A <code>RuleRuntime</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public RuleRuntime getRuleRuntime() {
        return m_rsp.getRuleRuntime();
    }

    /**
     * Gets the <code>TypeManager</code> which manages all Ontology type definitions
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a <code>TypeManager</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public TypeManager getTypeManager() {
        return m_rsp.getTypeManager();
    }

    protected FTNodeManager getNodeMgr() {
        return ftNodeMgr;
    }

    public boolean isPrimary() {
        return getNodeMgr().getCurrentNode().isPrimary();
    }

    public FTNode getCurrentNode() {
        return getNodeMgr().getCurrentNode();
    }

    protected void overrideDefaultProperties() {
        //m_rsp.overrideDefaultProperties();   //todo?
        BEProperties props = (BEProperties) this.getProperties();
        if (props.get("Engine.FT.UseFT") != null && props.get("be.ft.enabled") == null) {
            boolean enabled = props.getBoolean("Engine.FT.UseFT", false);
            if (enabled) {
                props.put("be.ft.enabled", "true");
                String clusterName = props.getString("Engine.FT.GroupName");
                if (clusterName != null && props.get("be.ft.cluster.name") == null) {
                    props.put("be.ft.cluster.name", clusterName);
                }
                if (props.get("Engine.FT.Weight") != null && props.get("be.ft.priority") == null) {
                    int priority = Short.MAX_VALUE - props.getInt("Engine.FT.Weight", 100);
                    props.put("be.ft.priority", Integer.toString(priority));
                }
            }
        }

    }


    public String getNodeName() {
        int nodeid = 0;
        FTNode node = null;
        String nodeName = null;
        if (ftNodeMgr != null) {
            nodeName = ftNodeMgr.getCurrentNodeName();
            if ((this.getProperties().getProperty("be.trace.cluster.name") == null)
                    || !getProperties().getProperty(SystemProperty.TRACE_NAME.getPropertyName()).equals(nodeName))
            {
                getProperties().setProperty("be.trace.cluster.name", nodeName);
            }
            return nodeName;
        } else
            return m_rsp.getName();
    }

    /**
     * Returns the async ft msg queue manager
     * @return  FTAsyncQueueManager
     */
    public FTAsyncQueueManager getAsyncQueueManager() {
        return this.ftQueueMgr;
    }

    public void initProject() throws Exception {
        m_rsp.initProject();
    }

    public boolean isMultiEngineMode() {
        return false;
    }

    // RuleServiceProvider overrides end

    /////////////////////////////////////////////////////////////////////////////////////////
    // FTRuleServiceProvider Life Cycle Thread start
    public void run() {
        try {
            ftNodeMgr = FTNodeManagerFactory.createFTNodeManager(m_rsp, ftAsyncNodeController, getNodeName());
            try {
                if (ftNodeMgr instanceof FTAsyncNodeManager) {
                    ftQueueMgr.setAsyncCallback(((FTAsyncNodeManager) ftNodeMgr).getAsyncCallback());
                    ftQueueMgr.start();
                }
                ftNodeMgr.start();
            } catch (Exception e) {

                this.m_rsp.logger.log(Level.FATAL, e, "Failed to start the FTAsyncRuleServiceProviderImpl");
                this.isShuttingDown = true;
                this.m_rsp.logger.log(Level.INFO, "Fault tolerant RuleServiceProvider is shutting down.");
                ((FTAsyncQueueManagerImpl) ftQueueMgr).stopThread();
                m_rsp.shutdown();
                synchronized(guardRsp) {
                    guardRsp.notifyAll();
                }
                return;
            }

            FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState oldState = getState();
            FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState newState = null;
            this.m_rsp.logger.log(Level.DEBUG, "FTRuleServiceProvider Thread started.");
            synchronized (m_state) {
            while (!getState().equals(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STOPPED)) {
                    try {
                        m_state.wait(1000);

                    } catch (InterruptedException e) {
                        this.m_rsp.logger.log(Level.FATAL, e, "FTRuleServiceProvider Thread crashed.");
                    }
                    if(this.isShuttingDown)
                        break;

                    newState = getState();
                    if (!newState.equals(oldState)) {
                        if (this.m_rsp.logger.isEnabledFor(Level.DEBUG)) {
                            this.m_rsp.logger.log(Level.DEBUG,  newState.toString());
                        }
                        oldState = newState;
                    }

                }
            }
           this.m_rsp.logger.log(Level.INFO, "Fault tolerant RuleServiceProvider is shutting down.");

            synchronized(guardRsp) {
                ((FTAsyncQueueManagerImpl) ftQueueMgr).stopThread();
                m_rsp.shutdown();
                ftNodeMgr.shutdown();
                guardRsp.notifyAll();
            }
            //((FTAsyncQueueManagerImpl)ftQueueMgr).join();


        }
        catch (Exception e) {
            this.m_rsp.logger.log(Level.ERROR, e, "Failed to start the FTAsyncRuleServiceProviderImpl.");
        }

    }

    protected synchronized FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState getState() {
        return m_state[0];
    }

    protected void setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState s) {
        synchronized (m_state) {
            m_state[0] = s;
            this.m_rsp.logger.log(Level.DEBUG, "State is: %s", s);
            m_state.notifyAll();
        }
    }

    protected void waitForState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState s) {
        synchronized (m_state) {
            while (!m_state[0].equals(s)) {
                try {
                    m_state.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    // FTRuleServiceProvider Life Cycle Thread end

    //////////////////////////////////////////////////////////////////////////////////////////
    // FTNodeControllerImpl

    public String getControllerName() {
        return getClass().getName();
    }

    public void nodeStarted() throws Exception {
        if (getState().equals(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STARTING_NODEMGR))
            setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_NODEMGR_STARTED);
    }

    public void initAll() throws Exception {
        if (getState().equals(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_NODEMGR_STARTED)) {
            m_rsp.getRuleAdministrator().init();  //with Properties
            initChannels();
            m_rsp.getRuleAdministrator().init();  //With GV
            initRuleSessions();
            getChannelManager().connectChannels();
            ((RuleServiceProviderImpl) m_rsp).bind();
            RuleSession[] sessions = ((RuleServiceProviderImpl) m_rsp).getRuleSessionManager().getRuleSessions();
            for (int i = 0; i < sessions.length; i++) {
                Properties props = sessions[i].getConfig().getCacheConfig();
                String cacheName = props.getProperty(Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME);
                if (this.m_rsp.logger.isEnabledFor(Level.INFO)
                        && (sessions[i].getObjectManager() instanceof ObjectStore)) {
                    this.m_rsp.logger.log(Level.INFO, "BE Session %s is using %s",
                            sessions[i].getName(), sessions[i].getObjectManager());
                }
            }

        }
    }

    public void initChannels() throws Exception {
        if (getState().equals(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_NODEMGR_STARTED)) {
            try {
                m_rsp.initChannels();
                //initRuleSessions();
                //getRuleAdministrator().init();
                setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INITIALIZED_CHANNELS);
            } catch (Exception e) {
                this.m_rsp.logger.log(Level.ERROR, e, "Failed to initialize channels.");
                throw e;
                //throw new Exception("Failed to initialize Channels",e);
            }
        }
    }


    public void initRuleSessions() throws Exception {
        if (getState().equals(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INITIALIZED_CHANNELS)) {
            try {
                m_rsp.initRuleSessions();
                setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INITIALIZED_RULESESSIONS);
            } catch (Exception e) {
                this.m_rsp.logger.log(Level.ERROR, e, "Failed to initialize RuleSessions.");
                throw e;
                //throw new Exception("Failed to initialize RuleSessions",e);
            }
        }
    }

    public void waitForActivation() throws Exception {
        System.out.println("NODE IS WAITING FOR ACTIVATION TO BECOME PRIMARY");
        this.secondaryStart = true;
    }

    public void startChannels() throws Exception {
        try {

            //getRuleSessionManager().start(true);
            //getRuleRuntime().setActiveMode(true);
            if (!sessionManagerStarted) {
                sessionManagerStarted = true;
                getChannelManager().startChannels(ChannelManager.SUSPEND_MODE);
                if(this.secondaryStart) {
                  getRuleRuntime().start(false);
                  getRuleRuntime().setActiveMode(true);
                } else {
                  getRuleRuntime().start(true);
                }
                getChannelManager().resumeChannels();
                ((DeployedBEProject) getProject()).startHotDeploy(this);  //todo - remove hotDeployer from deployedBEProject
                this.m_rsp.logger.log(Level.INFO, "Engine %s started. Cluster:%s, id:%s",
                        this.getName(),
                        this.getNodeMgr().getClusterName(),
                        this.getNodeMgr().getCurrentNode().getNodeCacheId());

                this.m_rsp.getRuleAdministrator().updateState(RuleAdministrator.STATE_RUNNING);

            } else {
                getRuleRuntime().setActiveMode(true);
                getChannelManager().setMode(ChannelManager.ACTIVE_MODE);
            }
            //getChannelManager().startChannels();

        } catch (Exception e) {
            this.m_rsp.logger.log(Level.ERROR, e, "NodeManager failed to  Start Channels.");
            throw e;
            //throw new Exception("NodeManager failed to  Start Channels",e);
        }

        setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_CHANNELS_STARTED);
    }


    public void waitForRuleCycles() throws Exception {
        if (this.m_rsp.logger.isEnabledFor(Level.DEBUG)) {
            this.m_rsp.logger.log(Level.DEBUG, "NodeManager is waiting for RuleSessions to complete %s",
                    this.getNodeMgr().getCurrentNode());
        }
        ((RuleServiceProviderImpl) m_rsp).waitForTaskControllers();
        boolean lockRTC = ((BEProperties) getProperties()).getBoolean("be.ft.rtc.lockandwait", true);
        if (lockRTC && !((RuleServiceProviderImpl) m_rsp).isRTCLocked()) {
            m_rsp.lockRTC();
        }
        else
           ((RuleServiceProviderImpl) m_rsp).deactivateSession();
        setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_RULECYCLES_COMPLETED);
    }

    public void setInactive() throws Exception {
        try {
            if (sessionManagerStarted == false) {
                sessionManagerStarted = true;

                getRuleRuntime().start(false);
                getChannelManager().setMode(ChannelManager.PASSIVE_MODE);
                ((DeployedBEProject) getProject()).startHotDeploy(this);  //todo - remove hotDeployer from deployedBEProject
                this.m_rsp.logger.log(Level.INFO, "Engine %s started. Cluster:%s, id:%s",
                        this.getName(),
                        this.getNodeMgr().getClusterName(),
                        this.getNodeMgr().getCurrentNode().getNodeCacheId());

                this.m_rsp.getRuleAdministrator().updateState(RuleAdministrator.STATE_RUNNING);
            } else {
                boolean lockRTC = ((BEProperties) getProperties()).getBoolean("be.ft.rtc.lockandwait", true);
                if (lockRTC && ((RuleServiceProviderImpl) m_rsp).isRTCLocked())
                    m_rsp.unlockRTC();
                getRuleRuntime().setActiveMode(false);
                getChannelManager().setMode(ChannelManager.PASSIVE_MODE);

            }
            setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_INACTIVE);
        }
        catch (Exception e) {
            this.m_rsp.logger.log(Level.ERROR, e, "NodeManager failed to set inactive the node");
            throw e;
            //throw new Exception ("NodeManager failed to set inactive the node",e);
        }

    }

    public void stopNode() throws Exception {
        setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STOPPED);
    }


    public void stopChannels() throws Exception {
        getChannelManager().stopChannels();
        setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_CHANNELS_STOPPED);
    }

    public void shutdown() {
        try {
            //this.ftQueueMgr.stop();
            //this.getNodeMgr().shutdown();

            synchronized(guardRsp) {
                if(this.isShuttingDown)
                    return;
                this.isShuttingDown = true;
                setState(FTAsyncRuleServiceProviderImpl.FTRuleServiceProviderState.STATE_STOPPED);
                guardRsp.wait(m_rsp.shutdownWaitTimeMillis);
            }

            //m_thread.join();
        } catch (Exception e) {
            this.m_rsp.logger.log(Level.ERROR, e, "Shutdown Error");
        } finally {
        }
    }

    public byte[] getNodeArchiveDigest() {
        return new byte[0];
    }

    public void suspendRTC() throws Exception {
        m_rsp.lockRTC();
    }

    public void activateRTC() throws Exception {
        m_rsp.unlockRTC();
    }

    public void waitBeforeStart() throws Exception {

    }


    public Locale getLocale() {
        return this.m_rsp.getLocale();
    }

    public Logger getLogger(String name) {
        return this.m_rsp.getLogger(name);
    }


    public Logger getLogger(Class clazz) {
        return this.m_rsp.getLogger(clazz);
    }

    // End NodeControl implementation


    @Override
    public Cluster getCluster() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCacheServerMode() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
