/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.session.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.JarInputStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaAnnotationLookup;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.BEPropertiesFactory;
import com.tibco.be.util.OversizeStringConstants;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.hotdeploy.HotDeployValidator;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.ChannelManagerImpl;
import com.tibco.cep.runtime.managed.EximHelper;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.management.LocalDomainRegistry;
import com.tibco.cep.runtime.management.ManagementCentral;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.model.EntityFactory;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.LifecycleListener;
import com.tibco.cep.runtime.perf.stats.PerformanceStatsCollectorFactory;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.DependencyWatcher;
import com.tibco.cep.runtime.service.basic.Dependent;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.events.notification.CacheChangeListenerService;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.loader.ClassManager.ClassLoadingResult;
import com.tibco.cep.runtime.service.loader.JarInputStreamReader;
import com.tibco.cep.runtime.service.logging.impl.LogManagerImpl;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansManager;
import com.tibco.cep.runtime.service.management.agent.impl.AgentHotDeployMBeanImpl;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.service.management.process.EngineMBeansManager;
import com.tibco.cep.runtime.service.om.IdGeneratorFactory;
import com.tibco.cep.runtime.service.om.api.DaoProviderFactory;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleAdministratorFactory;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.ExecutableResource;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.util.ResourceManager;


/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jun 20, 2006
 * Time: 10:46:42 AM
 * To change this template use File | Settings | File Templates.
 */

public class RuleServiceProviderImpl implements RuleServiceProvider, ChangeListener {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }

    public static final byte STATE_UNINITIALIZED = -1;
    public static final byte STATE_PROJECT_INITIALIZED = 1;
    public static final byte STATE_RUNNING = 15;

    private byte status = STATE_UNINITIALIZED;

    public static final byte CACHE_SERVER_MODE_OFF = 0;
    public static final byte CACHE_SERVER_MODE_CHANNEL_OFF = 1;
    public static final byte CACHE_SERVER_MODE_CHANNEL_ON = 2;

    protected String m_instanceName;
    private ChannelManager m_channelManager;
    protected RuleSessionManager m_ruleSessionManager;
    protected DeployedProject m_deployedProject;
    protected TypeManager m_typeManager;
    private RuleAdministrator m_ruleAdministrator;
    protected GlobalVariables m_globalVariables;

    protected IdGenerator m_idGenerator;

    protected com.tibco.cep.kernel.service.logging.Logger logger;
    protected BEProperties beProperties;
    private RuleServiceProvider m_containerRsp;
    private boolean isContained = false;

    protected boolean m_shutdown = false;
    protected boolean isRspClient = false; //Usually BW trying to masquerade and use RSP


    protected Map m_variables = new ConcurrentHashMap();

    public byte cacheServerMode = CACHE_SERVER_MODE_OFF;
    protected com.tibco.cep.modules.ModuleManager moduleManager;
    private volatile boolean isShuttingDown;
    private int runMode;
    private boolean exceptionShutdown = false;

    // introduced this to tell if an existing logger is being used.(context of for BE/BW integration)
    private boolean usingContainerLog = false;
    private boolean bMultiEngineMode = false;
    // introduced so that BW can be cleanly shutdown when BE shuts down
    private List shutdownListeners = new ArrayList();
    protected long shutdownWaitTimeMillis = 90000;

    protected final ReentrantLock shutdownLock = new ReentrantLock();

    protected final ReentrantLock RSPLOCK = new ReentrantLock();
    protected final Condition RSPLOCKCONDITION = RSPLOCK.newCondition();
    protected volatile boolean isSuspended = false;
    protected ThreadGroup RSPThreadGroup;
    protected HashSet<ExecutableResource> executableResources = new HashSet<ExecutableResource>();

    protected CacheChangeListenerService changeListenerService;
    public static final int SUSPEND_MODE_NONE = -1;
    public static final int SUSPEND_MODE_GENERIC = 0;
    public static final int SUSPEND_MODE_ADMIN = 1;
    public static final int SUSPEND_MODE_STORAGE_EXCEPTION = 2;
    public static final int SUSPEND_MODE_CLUSTER = 3;
    protected int suspendMode = SUSPEND_MODE_NONE;

    public static final String CLUSTER_NAME_STAND_ALONE = "standalone-cluster";

    private Cluster cluster;
    LogManagerImpl logManager =null;

    // Listeners for getting hot deployment notifications
    protected List<ChangeListener> hdListeners = new ArrayList<ChangeListener>();

    // Listener for Event life cycle events (send event, schedule event, etc)
    private LifecycleListener m_lifecycleListener;

    protected RuleServiceProviderImpl() {

    }

    /**
     * TODO
     *
     * @param instanceName
     * @param env
     * @throws Exception
     */
    public RuleServiceProviderImpl(String instanceName, Properties env) throws Exception {
        m_instanceName = instanceName;
        this.m_containerRsp = null;
        this.isContained = false;
        // RSPThreadGroup = new ThreadGroup(m_instanceName);
        this.initialize(env);
        this.bMultiEngineMode = ((BEProperties) getProperties()).getBoolean(
                SystemProperty.CLUSTER_MULTI_ENGINE_ON.getPropertyName(), true);

        changeListenerService = new CacheChangeListenerService();
    }

    public RuleServiceProviderImpl(RuleServiceProvider containerRsp, String instanceName, Properties env) throws Exception {
        this.m_containerRsp = containerRsp;
        this.isContained = true;
        m_instanceName = instanceName;
        this.initialize(env);
        this.bMultiEngineMode = ((BEProperties) getProperties()).getBoolean(
                SystemProperty.CLUSTER_MULTI_ENGINE_ON.getPropertyName(), true);
        // bMultiEngineMode= true;

        changeListenerService = new CacheChangeListenerService();
    }

    public boolean isMultiEngineMode() {
        return bMultiEngineMode;
    }

    public ThreadGroup getRSPThreadGroup() {
        return RSPThreadGroup;
    }

    public boolean isContained() {
        return isContained;
    }

    public RuleServiceProvider getContainerRsp() {
        return this.m_containerRsp;
    }

    public boolean isCacheServerMode() {
        return (cacheServerMode != CACHE_SERVER_MODE_OFF);
    }

    public void configure(int configureType) throws Exception {
        this.runMode = configureType;
        
        try {
            switch (configureType) {
                case MODE_LOAD_ONLY:
                    this.logger.log(Level.INFO, "Starting BE engine in LOAD_ONLY mode");
                    initProject();
                    break;
                case MODE_INIT:
                    this.logger.log(Level.INFO, "Starting BE engine in INIT mode");
                    initAll();
                    break;
                case MODE_PRIMARY:
                case MODE_START_ALL:
                    this.logger.log(Level.INFO, "Starting BE engine in PRIMARY mode");
                    initAll();
                    startAll(true);
                    m_deployedProject.startHotDeploy(this);
                    break;
                case MODE_API:
                    this.logger.log(Level.INFO, "Starting BE engine in API mode");
                    initRuleSessions();
                    initModules();
                    getRuleRuntime().start(true);
                    moduleManager.startModules(0);
                    m_deployedProject.startHotDeploy(this);
                    break;
                // case MODE_CACHESERVER:
                // cacheServerMode = CACHE_SERVER_MODE_CHANNEL_OFF;
                // this.logger.log(Level.INFO, "Starting BE engine in CACHESERVER mode");
                // m_ruleAdministrator.init(); //with Properties
                // initProject();
                // m_ruleAdministrator.init(); //With GV
                // initRuleSessions();
                // initModules();
                // getRuleRuntime().start(true);
                // moduleManager.startModules(0);
                // m_deployedProject.startHotDeploy(this);
                // break;
                // case MODE_CACHESERVER_W_CHANNEL:
                // cacheServerMode = CACHE_SERVER_MODE_CHANNEL_ON;
                // this.logger.log(Level.INFO, "Starting BE engine in CACHESERVER_W_CHANNEL mode");
                // initAll();
                // getRuleRuntime().start(true);
                // m_deployedProject.startHotDeploy(this);
                // break;

                case MODE_CLUSTER:
                    this.logger.log(Level.INFO, "Starting BE engine in CLUSTER mode");
                    initCluster();
                    startCluster();
                    // startAll(true);
                    m_deployedProject.startHotDeploy(this);
                    break;
                case MODE_CLUSTER_CACHESERVER:
                    cacheServerMode = CACHE_SERVER_MODE_CHANNEL_ON;
                    this.logger.log(Level.INFO, "Starting BE engine in CACHESERVER mode");
                    initCluster();
                    startCluster();
                    // startAll(true);
                    m_deployedProject.startHotDeploy(this);
                    break;
            }

            new EngineMBeansManager(this).registerEngineMBeans();
            
            this.logger.log(Level.OFF, "BE Engine %s started", this.getName());

            this.status = STATE_RUNNING;

            this.m_ruleAdministrator.updateState(RuleAdministrator.STATE_RUNNING);
        } catch (Exception e) {
            exceptionShutdown = true;
            throw e; // need to get out before shutdown guard is hit
        }
        if (!isContained && configureType != MODE_API && configureType != MODE_INIT
                && configureType != MODE_LOAD_ONLY && configureType != MODE_START_ALL) {
            // synchronized (shutdownGuard) {
            // while(shutdownWasCalled == false){
            // shutdownGuard.wait();
            // }
            //
            // shutdown(true);
            // System.out.println("Shutdown completed ...");
            // }
            // synchronized(ctrlcGuard) {
            // ctrlcGuard.notifyAll();
            // }
        }
    }

    public void registerExecutableResource(ExecutableResource resource) {
        synchronized (executableResources) {
            executableResources.add(resource);
        }
    }

    public synchronized void unregisterExecutableResource(ExecutableResource resource) {
        synchronized (executableResources) {
            executableResources.remove(resource);
        }
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void ensureRSP() {
        if (!isSuspended) {
            return;
        }

        try {
            RSPLOCK.lockInterruptibly();
        }
        catch (InterruptedException e) {
            return;
        }

        try {
            if (this.isSuspended) {
                this.logger.log(Level.INFO, "Suspending thread...");
            }

            while (isSuspended) {
                try {
                    this.RSPLOCKCONDITION.await();
                }
                catch (InterruptedException e) {
                }
            }
            this.suspendMode = SUSPEND_MODE_NONE;

            if (!this.isSuspended) {
                this.logger.log(Level.INFO, "Resuming thread..");
            }
        }
        finally {
            RSPLOCK.unlock();
        }
    }

    public void suspendRSP(int suspendMode) {
        if (isSuspended) {
            return;
        }

        try {
            this.logger.log(Level.INFO, "Suspending all nodes...");

            RSPLOCK.lockInterruptibly();
            isSuspended = true;
            this.suspendMode = suspendMode;

            this.logger.log(Level.INFO, "Notifying all threads...");
            for (ExecutableResource executableResource : executableResources) {
                executableResource.suspendResource();
            }

            this.logger.log(Level.INFO, "Waiting on all threads to finish...");
            waitOnThreads();

            this.logger.log(Level.INFO, "All threads finished...");

            if ((runMode == MODE_CLUSTER) || (runMode == MODE_CLUSTER_CACHESERVER)) {
                Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
                try {
                    cluster.flushAll();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {

        } finally {
            RSPLOCK.unlock();
        }
    }

    public void suspendRSP() {
        suspendRSP(SUSPEND_MODE_GENERIC);
    }

    public int getSuspendMode() {
        return suspendMode;
    }

    private void waitOnThreads() {
        Thread currentThread = Thread.currentThread();

        while (true) {
            boolean isRunning = false;

            for (ExecutableResource executableResource : executableResources) {
                boolean isCallingThread = currentThread.equals(executableResource);

                if (executableResource.isStarted() && !executableResource.isSuspended()
                        && !isCallingThread) {
                    isRunning = true;
                    break;
                }
            }

            if (!isRunning) {
                return;
            }

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
    }

    public void resumeRSP() {
        if (!isSuspended) {
            return;
        }

        try {
            this.logger.log(Level.INFO, "Resuming all threads...");

            RSPLOCK.lockInterruptibly();
            this.logger.log(Level.INFO, "Acquired Lock, signal all threads");

            isSuspended = false;

            for (ExecutableResource executableResource : executableResources) {
                executableResource.resumeResource();
            }

            this.RSPLOCKCONDITION.signalAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            RSPLOCK.unlock();
        }
    }


    protected void createProperties(Properties env) throws Exception {
        if (env instanceof BEProperties) {
            this.beProperties = (BEProperties) env;
        } else {
            this.beProperties = BEPropertiesFactory.getInstance().makeBEProperties(env, this.m_instanceName);
        }
    }


    public RuleSessionManager createRuleSessionManager(BEProperties env) throws Exception {

        String className = env.getProperty("com.tibco.cep.rulesession.factory", RuleSessionManagerImpl.class.getName());
        Class clazz = Class.forName(className);

        Constructor constructor = clazz.getConstructor(new Class[] { RuleServiceProvider.class, BEProperties.class });
        RuleSessionManager manager = (RuleSessionManager) constructor.newInstance(new Object[] { this, env });
        return manager;
    }

    public Locale getLocale() {
        String lang = beProperties.getString("be.locale.language");
        if (lang == null || lang.length() == 0) {
            return Locale.getDefault();
        } else {
            return new Locale(lang, beProperties.getString("be.locale.country"),
                    beProperties.getString("be.locale.variant", ""));
        }
    }

    protected void initialize(Properties env) throws Exception {
        createProperties(env);

        this.m_instanceName = this.beProperties.getString(SystemProperty.ENGINE_NAME.getPropertyName());

        //Initialize the DaoProvider before anything
        DaoProviderFactory.initialize();
        // /TODO : SS/NL
        Locale l = getLocale();
        ResourceManager manager = ResourceManager.getInstance();
        manager.setDefaultLocale(l);
        manager.addResourceBundle("com.tibco.cep.runtime.messages", l);
        // manager.addResourceBundle("com.tibco.cep.driver.messages", l);
        // manager.addResourceBundle("com.tibco.cep.container.messages", l);


        logManager = new LogManagerImpl(this.beProperties);
        LogManagerFactory.setLogManager(logManager);
        this.logger = logManager.getLogger(RuleServiceProviderImpl.class);



        final ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        registry.registerService(LogManagerImpl.class, logManager);

        if (RuleServiceProviderManager.getInstance().getDefaultProvider() == null) {
            // if BW has instantiated a new RSP or BE has started for the first time
            //m_logger = LoggerImpl.createLogger(beProperties);
        } else {
            // if BE is the container, use container's logger
            //m_logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger();
            usingContainerLog = true;
        }

        //if (m_logger != null && m_logger instanceof LoggerImpl) {
        //    registry.registerService(LoggerImpl.class, (LoggerImpl) m_logger);
        //}

        if (Boolean.valueOf(env.getProperty("com.tibco.pe.PEMain", "false")).booleanValue()) {
            usingContainerLog = true; // BW is the container
        }
        m_ruleAdministrator = RuleAdministratorFactory.createRuleAdministrator(this, this.beProperties);
        m_ruleAdministrator.init();

        final Class deployedProjectClass = Class.forName("com.tibco.cep.studio.core.repo.emf.DeployedEMFProject");
        final Constructor deployedProjectConstructor = deployedProjectClass.getConstructor(
                Properties.class, ChangeListener.class);

        String repoUrl = beProperties.getProperty("tibco.repourl");

        m_deployedProject = (DeployedProject) deployedProjectConstructor.newInstance(
                beProperties, this);  // Can be very slow!

        m_globalVariables = m_deployedProject.getGlobalVariables();
        m_typeManager = EntityFactory.createTypeManager(this, beProperties);
        m_channelManager = new ChannelManagerImpl(this);
        m_ruleSessionManager = this.createRuleSessionManager(beProperties);
        m_idGenerator = IdGeneratorFactory.createIdGenerator(getName(), this);
        isRspClient = Boolean.valueOf(env.getProperty("com.tibco.cep.rsp.isClient", "false")).booleanValue();
        moduleManager = new com.tibco.cep.modules.impl.ModuleManagerImpl(this);
        shutdownWaitTimeMillis = beProperties.getLong("be.shutdown.waittimemillis", 90000);
    }


    private void registerClasses() throws Exception {
        final Map<String, Object> nameToByteCode = new HashMap<String, Object>();
        final JavaArchiveResourceProvider jarp = this.m_deployedProject.getJavaArchiveResourceProvider();
        final Collection uris = jarp.getAllResourceURI();
        for (Object uri : uris) {
            final byte[] raw = jarp.getResourceAsByteArray(uri.toString());
            if ((raw != null) && (raw.length != 0)) {
                ByteArrayInputStream bis = new ByteArrayInputStream(raw);
                nameToByteCode.putAll(JarInputStreamReader.read(new JarInputStream(bis)));
            }
        }

        BEClassLoader beClassLoader = (BEClassLoader) m_typeManager;
        // remove this so that analyzeByteCodes doesn't choke on it
        Properties oversizeStringConstants = (Properties) nameToByteCode.remove(OversizeStringConstants.PROPERTY_FILE_NAME);
        Map resultMap = beClassLoader.analyzeByteCodes(nameToByteCode);
        beClassLoader.lockAndUpdate(newArchives(m_deployedProject), resultMap, oversizeStringConstants);

        // free up the shared resource
        if (jarp.isOpen()){
            //jarp.close(); // do not close it for bpmn process as it needs the be.jar classes to do dynamic compilation
        }

    }

    protected void initAll() throws Exception {
        try {
            if (beProperties.getProperty(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName()) == null) {
                beProperties.setProperty(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(), Boolean.TRUE.toString());
            }

            // -------------

            m_ruleAdministrator.init(); // with Properties
            initProject();
            m_ruleAdministrator.init(); // With GV
            initChannels();
            initRuleSessions();
            boolean isClient = beProperties.getBoolean("com.tibco.cep.rsp.isClient", false);
            if (!isClient) {
                initModules();
            }

            // -------------

            RuleSession[] ruleSessions = m_ruleSessionManager.getRuleSessions();

            LinkedList<DomainInfo> domainInfos = new LinkedList<DomainInfo>();

            for (int i = 0; i < ruleSessions.length; i++) {
                RuleSession rs = ruleSessions[i];

                new AgentMBeansManager(i, this, rs.getName()).registerAgentMBeans();

                String agentType = isCacheServerMode() ? CacheAgent.Type.CACHESERVER.name() : CacheAgent.Type.INFERENCE.name();

                DomainInfo domainInfo = new DomainInfo(rs.getName(), i, agentType);
                domainInfos.add(domainInfo);
            }

            registerDomains(CacheClusterProvider.CLUSTER_NAME_DEFAULT_VALUE, true,domainInfos);
            
            // Register mbeans for HD
            if (!isCacheServerMode()) {
            	registerDTDeployerMBeans();
            	registerRTDeployerMBean();
            }
            
            // load DT's at startup
            AgentHotDeployMBeanImpl.loadAllDTsFromDirectory(this, logger);
            
            if (cluster == null) {
                //Do we need to enable stop watches for memory mode ?
                String s =
                        beProperties.getProperty(SystemProperty.METRIC_PUBLISH_ENABLE.getPropertyName(),
                                Boolean.FALSE.toString());
                boolean enableMetrics = Boolean.parseBoolean(s);

                if (enableMetrics) {
                    //create the performance stats collector factory service
                    PerformanceStatsCollectorFactory factory = new PerformanceStatsCollectorFactory();
                    factory.init(ServiceRegistry.getSingletonServiceRegistry().getConfiguration());
                    factory.start();
                    ServiceRegistry.getSingletonServiceRegistry().registerService(PerformanceStatsCollectorFactory.class, factory);
                    //we are in memory mode
                    for (int i = 0; i < ruleSessions.length; i++) {
                        RuleSession rs = ruleSessions[i];
                        String agentName = rs.getName();
                        int agentId = i;

                        //create the rule session stop watch keeper
                        RuleSessionStopWatchKeeper keeper = new RuleSessionStopWatchKeeper(CacheClusterProvider.CLUSTER_NAME_DEFAULT_VALUE,
                                ProcessInfo.getProcessIdentifier(), agentName, agentId, false);
                        //init the rete listener
                        ReteListener reteListener = keeper.init();
                        //add the rete listener to the rule
                        ((ReteWM) ((RuleSessionImpl) rs).getWorkingMemory()).addReteListener(reteListener);
                        //create the performance gathering service
                        factory.createCollector(rs, agentName, agentId, ManagementFactory.getPlatformMBeanServer());
                    }
                }
            }
        } catch (ClassNotFoundException cfe) {

        } catch (Exception e) {
            if (this.logger != null) {
                this.logger.log(Level.DEBUG, e, "Error performing initAll()");
                throw e;
            }
        }
    }

    private void registerDTDeployerMBeans() {
    	final String hdInterfaceClass = "com.tibco.cep.runtime.service.management.agent.AgentHotDeployMBean";
    	final String hdImplClass = "com.tibco.cep.runtime.service.management.agent.impl.AgentHotDeployMBeanImpl";
    	final String hdObjectName = "com.tibco.be:dir=Hot Deploy,name=Decision Table Deployer";
    	
    	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    	ObjectName name = null;
    	try {
    		name = new ObjectName(hdObjectName);
    		//create hotdeployer mbean instance
    		Class<?> mHDClassName = Class.forName(hdImplClass);
    		Object mHDBeanObj  = mHDClassName.newInstance();
    		//Sets the RSP and Logger in each of the MBean classes
    		mHDClassName.getMethod("setRuleServiceProvider", RuleServiceProvider.class).invoke(mHDBeanObj, this);
    		mHDClassName.getMethod("setLogger",Logger.class).invoke(mHDBeanObj, logger);

    		StandardMBean mBean = new StandardMBean(mHDBeanObj, (Class<Object>) Class.forName(hdInterfaceClass));
    		
    		if (!mbs.isRegistered(name)) mbs.registerMBean(mBean, name);
    	} catch(Exception exception) {
    		logger.log(Level.DEBUG,"Error registering " + hdInterfaceClass + " MBean with ObjectName: " + hdObjectName);
    		if (name != null && mbs.isRegistered(name)) {
    			try {
    				mbs.unregisterMBean(name);
    			} catch(Exception e) {
    				logger.log(Level.DEBUG,"Error unregistering " + hdInterfaceClass + " MBean with ObjectName: " + hdObjectName);
    			}
    		}
    		exception.printStackTrace();
    	}
    }
    
    private void registerRTDeployerMBean() {
    	final String rtiMBeanInterface = "com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean";
        try {
            Class rtiDeployerClass = Class.forName("com.tibco.cep.runtime.service.deploy.RuleTemplateIMDeployer");
            Object rtiDeployer = rtiDeployerClass.getConstructor(RuleServiceProvider.class).newInstance(this);
            Method mbeanMethod = rtiDeployerClass.getMethod("registerMBean");
            mbeanMethod.invoke(rtiDeployer);
        } catch (Exception e) {
        	logger.log(Level.DEBUG,"Error registering " + rtiMBeanInterface + " MBean");
        }
    }

    private Thread loadCatalogFunctions()
    {
        final Thread t = new Thread("Catalog-Functions-Loader")
        {
            @Override
            public void run()
            {
                try {
                	
                    final FunctionsCatalog catalog = FunctionsCatalog.getINSTANCE();
                    RuleServiceProviderImpl.this.logger.log(Level.INFO, "Loaded catalog functions.");
                } catch (Exception e) {
                    if (RuleServiceProviderImpl.this.logger != null) {
                        RuleServiceProviderImpl.this.logger.log(Level.ERROR, e,
                                "Failed to load catalog functions");
                    }
                }
            }
        };
        t.setDaemon(true);

        RuleServiceProviderImpl.this.logger.log(Level.INFO, "Loading catalog functions.");
        t.start();
        return t;
    }
    
    /**
     * Loads the BE Jar custom catalog functions after initProject is completed
     */
    private void loadBEJarFunctions() {
    	try {
    		JavaAnnotationLookup.loadBEJarFunctions(this);
    	} catch(Exception e) {
    		if (RuleServiceProviderImpl.this.logger != null) {
                RuleServiceProviderImpl.this.logger.log(Level.ERROR, e,
                        "Failed to load BE Jar functions");
            }
    	}
    }


    /**
     * Set message encoding for RV message.
     *
     * @see SystemProperty#MESSAGE_ENCODING
     */
    private void setRVMessageEncoding() {
        Class<?> clazz = null;
        String rvMessageEncoding = this.getGlobalVariables().getVariableAsString(SystemProperty.MESSAGE_ENCODING.getPropertyName(),
                beProperties.getString(SystemProperty.MESSAGE_ENCODING.getPropertyName(),
                        "ISO8859-1"));

        if (rvMessageEncoding != null) {
            try {
                try {
                    clazz = Class.forName("com.tibco.tibrv.TibrvMsg");
                } catch (ClassNotFoundException e) {
                }
                if (clazz != null) {
                    Method setStringEncodingMethod = clazz.getMethod("setStringEncoding", new Class[]{String.class});
                    setStringEncodingMethod.invoke(null, rvMessageEncoding);
                    if (this.logger != null) {
                        this.logger.log(Level.INFO, "Tibrv string encoding: %s", rvMessageEncoding);
                    }
                }
            } catch (Error e) {
                if (this.logger != null) {
                    this.logger.log(Level.DEBUG, e, "Error setting RV encoding");
                }
            } catch (Exception e) {
                if (this.logger != null) {
                    this.logger.log(Level.DEBUG, e, "Error setting RV encoding");
                }
            }
        }
    }

    protected void initCluster() throws Exception {
        try {
            m_ruleAdministrator.init(); // with Properties
            initProject();
            m_ruleAdministrator.init(); // With GV

            ((RuleSessionManagerImpl) this.m_ruleSessionManager).logDesignTimeComponents(this.m_deployedProject);

            cluster = CacheClusterProvider.getInstance().createCluster(this);
            cluster.init();

            boolean isQueryAgent = RuleServiceProviderManager.isConfigAsQueryAgent(this);
            boolean isCacheServer = RuleServiceProviderManager.isConfigAsCacheServer(beProperties);
            
            boolean disableChannels = (isCacheServer && RuleServiceProviderManager.getCacheServerMode(beProperties) == RuleServiceProvider.MODE_CACHESERVER) ||
            							(isQueryAgent && RuleServiceProviderManager.getQueryAgentMode(beProperties) == RuleServiceProvider.MODE_QUERYAGENT);
            
            if (disableChannels == false) {
                initChannels();
                m_channelManager.connectChannels();
            }

            //m_ruleSessionManager.init();

            initModules();

            // ------------- Register Engine (Process) MBeans


            // -------------

//            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
//            Configuration configuration = registry.getConfiguration();
//
//            HashMap<RuleSession, MetadataCache> rsAndMetadata =
//                    new HashMap<RuleSession, MetadataCache>();
//            for (RuleSession ruleSession : rsAndAgent.keySet()) {
//                InferenceAgent agent = rsAndAgent.get(ruleSession);
//
//                rsAndMetadata.put(ruleSession, agent.getCluster().getMetadataCache());
//            }
//
//            changeListenerService.init(configuration, rsAndMetadata);
//            registry.registerService(CacheChangeListenerService.class, changeListenerService);

            // -------------

            CacheAgent[] allAgents = cluster.getAgentManager().getLocalAgents();
            LinkedList<DomainInfo> domainInfos = new LinkedList<DomainInfo>();
            for (CacheAgent agent : allAgents) {
                String agentType = agent.getAgentType().name();
                domainInfos.add(new DomainInfo(agent.getAgentName(), agent.getAgentId(), agentType));
            }

            String clusterName = (cluster == null) ? CLUSTER_NAME_STAND_ALONE :
                    cluster.getClusterName();

            boolean standaloneMode = beProperties.getBoolean(
                    SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(), Boolean.FALSE);

            registerDomains(clusterName, standaloneMode, domainInfos);

            // -------------

//            if (isCacheServer) {
//                if (beProperties.getBoolean(SystemProperty.VM_SPECIAL_CACHE_OM.getPropertyName(), false)) {
//                    logger.log(Level.DEBUG, "Special OM requested. Initiating...");
//
//                    Class cacheServerAgentService = Class.forName(
//                            "com.tibco.cep.query.stream.impl.rete.integ.CacheServerAgentService");
//
//                    Method csasInitMethod =
//                            cacheServerAgentService
//                                    .getMethod("init", String.class, Cluster.class,
//                                            Integer.TYPE);
//
//                    int maxThreads = beProperties.getInt(SystemProperty.VM_SPECIAL_CACHE_OM_MAX_THREADS.getPropertyName(), 16);
//
//                    csasInitMethod.invoke(null /*static*/, "RemoteQueryFilterSpecialOM",
//                            CacheClusterProvider.getInstance().getCacheCluster(), maxThreads);
//
//                    logger.log(Level.INFO, "Special OM initialized with [" + maxThreads + "] threads");
//                } else {
//                    logger.log(Level.DEBUG, "Special OM not requested");
//                }
//            }
            
            String s = beProperties.getProperty(SystemProperty.METRIC_PUBLISH_ENABLE.getPropertyName(), Boolean.FALSE.toString());
            boolean enableMetrics = Boolean.parseBoolean(s);

            if (enableMetrics) {
	            PerformanceStatsCollectorFactory factory = new PerformanceStatsCollectorFactory();
	            factory.init(ServiceRegistry.getSingletonServiceRegistry().getConfiguration());
	            factory.start();
	            ServiceRegistry.getSingletonServiceRegistry().registerService(PerformanceStatsCollectorFactory.class, factory);
	            for (CacheAgent agent : cluster.getAgentManager().getLocalAgents()) {
	            	switch(agent.getAgentType()) {
	            	case INFERENCE:
						factory.createCollector(((InferenceAgent) agent).getRuleSession(), agent.getAgentName(),
								agent.getAgentId(), ManagementFactory.getPlatformMBeanServer());
	            	};
	            }
            }
        } catch (Exception e) {
            if (this.logger != null) {
                this.logger.log(Level.DEBUG, e, "Error performing initCluster()");
                throw e;
            }
        }
    }

    public void startCluster() throws LifecycleException
    {
        try {
        	//bind();
        	//m_channelManager.startChannels(ChannelManager.SUSPEND_MODE);

            cluster.start();

            changeListenerService.start();

            //m_channelManager.resumeChannels();
            moduleManager.startModules(0);
        }
        catch (Exception e) {
            throw new LifecycleException(e);
        }
    }

    //TODO will come later
//    private void registerMBean() {
//        try {
//            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//            ObjectName name =
//                    new ObjectName("com.tibco.be:service=ChangeListener,name=ChangeNotifier");
//            ReteChangeNotifierMBean mBean = new ReteChangeNotifier();
//            mbs.registerMBean(mBean, name);
//            startRMIServer(mbs);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    /**
     * Start an RMI server for listening to remote requests from MBean clients
     * @param mbs
     * @throws Exception
     */
    //TODO To come later
//    protected void startRMIServer(MBeanServer mbs) throws Exception {
//        BEProperties props = beProperties;
//        boolean isRMIServerEnabled =
//                props.getBoolean("be.engine.cluster.rmi.enabled", false);
//        if (isRMIServerEnabled) {
//
//            String host = props.getProperty("be.engine.cluster.rmi.host", "localhost");
//            int port = props.getInt("be.engine.cluster.rmi.port", 9999);
//
//            logger.log(Level.INFO,"Starting RMI server on host " + host + ":" + port);
//
//            //Start RMI registry here
//            LocateRegistry.createRegistry(port);
//
//            JMXServiceURL url =
//                    new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/server");
//            JMXConnectorServer cs =
//                    JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
//            cs.start();
//        }
//    }

    protected void registerDomains(String clusterName, boolean standaloneMode,
                                   Collection<DomainInfo> domainInfos)
            throws Exception {
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();

        Domain processDomain = new Domain(DomainKey.class);

        String pid = ProcessInfo.getProcessIdentifier();

        // To handle the case when we have multiple NIC in a host.
        String hostAddress = (this.beProperties.getProperty(SystemProperty.ENGINE_HOST_ADDRESS.getPropertyName()) != null
                && !this.beProperties.getProperty(SystemProperty.ENGINE_HOST_ADDRESS.getPropertyName()).trim().equalsIgnoreCase("localhost")) ?
                this.beProperties.getProperty(SystemProperty.ENGINE_HOST_ADDRESS.getPropertyName()) :
                ProcessInfo.getHostAddress();

        String jmxString = convertJMXPropsToCSV(hostAddress);
        String hawkString = convertHawkPropsToCSV();

        LocalDomainRegistry domainRegistry = new LocalDomainRegistry();
        domainRegistry.init(registry.getConfiguration(), this, clusterName, pid);
        registry.registerService(LocalDomainRegistry.class, domainRegistry);
        domainRegistry.start();

        FQName processDomainName = new FQName(clusterName, pid);
        processDomain.safePut(DomainKey.NAME, processDomainName);

        processDomain.safePut(DomainKey.HOST_PROCESS_ID, pid);
        processDomain.safePut(DomainKey.HOST_IP_ADDRESS, hostAddress);
        if (jmxString != null) {
            processDomain.safePut(DomainKey.JMX_PROPS_CSV, jmxString);
        }
        if (hawkString != null) {
            processDomain.safePut(DomainKey.HAWK_PROPS_CSV, hawkString);
        }

        //Added to resolve the simple vs fully qualified host name for BEMM (JIRA BE-12699)
        String fqPid = ProcessInfo.getFQProcessIdentifier(pid,hostAddress);
        FQName fqProcessDomainName = new FQName(clusterName, fqPid);
        processDomain.safePut(DomainKey.FQ_NAME, fqProcessDomainName);

        domainRegistry.registerDomain(processDomain);

        // -------------

        DependencyWatcher dependencyWatcher = registry.getDependencyWatcher();
        dependencyWatcher
                .notifyDependents(Dependent.DEP_KEY_CLUSTER_INIT, standaloneMode, clusterName, cluster);

        ManagementCentral managementCentral = registry.getService(ManagementCentral.class);
        ManagementTable managementTable = managementCentral.getManagementTable();
        managementTable.setMemberInfo(pid); //for ASManagementTable only

        for (DomainInfo domainInfo : domainInfos) {
            Domain agentSubDomain = new Domain(DomainKey.class);
            FQName agentSubDomainName = new FQName(processDomainName, domainInfo.getName(),
                    domainInfo.getId() + "");
            agentSubDomain.safePut(DomainKey.NAME, agentSubDomainName);
            agentSubDomain.safePut(DomainKey.HOST_PROCESS_ID, pid);
            agentSubDomain.safePut(DomainKey.HOST_IP_ADDRESS, hostAddress);
            if (jmxString != null) {
                agentSubDomain.safePut(DomainKey.JMX_PROPS_CSV, jmxString);
            }
            if (hawkString != null) {
                agentSubDomain.safePut(DomainKey.HAWK_PROPS_CSV, hawkString);
            }
            String desc = "nodeType=" + domainInfo.getType();
            agentSubDomain.safePut(DomainKey.DESCRIPTION_CSV, desc);

            //Added to resolve the simple vs fully qualified host name for BEMM (JIRA BE-12699)
            FQName fqAgentSubDomainName = new FQName(fqProcessDomainName, domainInfo.getName(),
                    domainInfo.getId() + "");
            agentSubDomain.safePut(DomainKey.FQ_NAME, fqAgentSubDomainName);

            domainRegistry.registerDomain(agentSubDomain);

            managementTable.putOrRenewDomain(agentSubDomain, Long.MAX_VALUE);
        }

        managementTable.putOrRenewDomain(processDomain, Long.MAX_VALUE);
    }

    protected String convertJMXPropsToCSV(String hostAddress) {
        // If the user specifies the property JMX_CONNECTOR_PORT, a secure JMX connector server is started.
        // If the property is omitted, no JMX connector server is started
        final boolean useSecureJMXConn = beProperties.getProperty(JMXConnUtil.BE_PROPERTIES.JMX_CONNECTOR_PORT) != null;
        String sslEnabled;
        int port=-1;

        // Check if secure JMX connector is to be used. In case it is, it uses it instead of the Standard JMX port
        if (useSecureJMXConn) {
            port = JMXConnUtil.getConnPort(this);
            sslEnabled = JMXConnUtil.getSSL();
        } else { // No secure JMX connector was specified. Check if standard JMX port was specified.
            // Needs to use System.getProperty because it deals with JVM properties
            final String jmxEnabled = System.getProperty(SystemProperty.JMX_ENABLED.getPropertyName());

            // Recall that the default value for property JMX_ENABLED is true
            if (jmxEnabled != null && jmxEnabled.trim().equalsIgnoreCase("false")) {
                return null;
            }

            try {
                port = Integer.parseInt(System.getProperty(SystemProperty.JMX_PORT.getPropertyName()));
            } catch (NumberFormatException e) {
                port = -1;  //either port not specified, or has illegal value
            }

            sslEnabled = System.getProperty(SystemProperty.JMX_SSL_ENABLED.getPropertyName(), "true");
        }

        //TODO: are these properties enough?
        if (port != -1 ) {
            return "address=" + hostAddress + ",port=" + port + ",sslEnabled=" + sslEnabled;
        }
        return null; //invalid port
    }

    protected String convertHawkPropsToCSV() {
        //Anand - 5/12/2009 - We dont check for hawk enabled since that property is used for the AMI agent
        // boolean hawkEnabled = m_globalVariables
        //                .getVariableAsBoolean(SystemProperty.HAWK_ENABLED.getPropertyName(), false);
        // if (hawkEnabled == false) {
        // return null;
        // }

        String domain = m_globalVariables
                .getVariableAsString(SystemProperty.RV_DOMAIN.getPropertyName(), null);
        String service = m_globalVariables
                .getVariableAsString(SystemProperty.HAWK_SERVICE.getPropertyName(), null);
        String daemon = m_globalVariables
                .getVariableAsString(SystemProperty.HAWK_DAEMON.getPropertyName(), null);
        String network = m_globalVariables
                .getVariableAsString(SystemProperty.HAWK_NETWORK.getPropertyName(), null);

        return "domain=" + domain + ",service=" + service + ",daemon=" + daemon + ",network=" + network;
    }

    public void initProject() throws Exception {

        if (status >= STATE_PROJECT_INITIALIZED) {
            return;
        }

        //Security.addProvider(new Provider());
        // Let the catalog function and project load go in parallel threads
        // main thread loads the project
        Thread t = this.loadCatalogFunctions();

        // Load the project
        this.logger.log(Level.INFO, "Loading the project from: %s", beProperties.getString("tibco.repourl"));
        m_deployedProject.load();
        this.logger.log(Level.INFO, "Loaded the project successfully");
        
        GlobalVariables gvs=m_deployedProject.getGlobalVariables();
		for(BEArchiveResource archive :m_deployedProject.getBEArchiveResourceProvider().getBEArchives()){
			if(GvCommonUtils.isGlobalVar(archive.getName())){
	        	GlobalVariableDescriptor gv =gvs.getVariable(GvCommonUtils.stripGvMarkers(archive.getName()));
	        	archive.setName(gv.getValueAsString());
	        }	
		}

		final ProcessingUnitConfig puc = m_deployedProject.getProcessingUnitConfig();
		Properties props=m_deployedProject.getProperties();
		if (puc != null) {
			for (AgentConfig ac: puc.getAgents().getAgent()) {
				String propKey="Agent." + ac.getRef().getId() + ".key";
				String val=props.getProperty(propKey);
				if(GvCommonUtils.isGlobalVar(val)){
		        	GlobalVariableDescriptor gv =gvs.getVariable(GvCommonUtils.stripGvMarkers(val));
		        	props.put(propKey, gv.getValueAsString());
		        }
			}
		}
		String levelString = beProperties.getProperty(SystemProperty.TRACE_ROLES.getPropertyName());
		String maxNumString = beProperties.getProperty(SystemProperty.TRACE_FILE_MAX_NUM.getPropertyName());
		String maxSizeString = beProperties.getProperty(SystemProperty.TRACE_FILE_MAX_SIZE.getPropertyName());

		if (LogManagerImpl.isGlobalVar(levelString) || LogManagerImpl.isGlobalVar(maxNumString)
				|| LogManagerImpl.isGlobalVar(maxSizeString)) {
			for (GlobalVariableDescriptor gvd : gvs.getVariables()) {
				if (gvd.getName().equalsIgnoreCase(LogManagerImpl.stripGvMarkers(levelString))) {
					levelString = gvd.getValueAsString();
					logManager.configureLevelsWithGv(beProperties, levelString);
					LogManagerFactory.setLogManager(logManager);
					continue;
				} else if (gvd.getName().equalsIgnoreCase(LogManagerImpl.stripGvMarkers(maxNumString))) {
					maxNumString = gvd.getValueAsString();
					logManager.configureMaxNumWithGv(beProperties, maxNumString);
					LogManagerFactory.setLogManager(logManager);
					continue;
				} else if (gvd.getName().equalsIgnoreCase(LogManagerImpl.stripGvMarkers(maxSizeString))) {
					maxSizeString = gvd.getValueAsString();
					logManager.configureMaxSizeWithGv(beProperties, maxSizeString);
					LogManagerFactory.setLogManager(logManager);
					continue;
				}
			}
		}
        // wait for the catalog functions to be loaded
        // also ensure project is loaded in the main thread
        t.join();
        
        this.setRVMessageEncoding();
        
        if(TraxSupport.isXPathVersion(XPATH_VERSION.XPATH_20, this)) {
        	this.logger.log(Level.INFO, String.format("Project XPATH version:%s", XPATH_VERSION.XPATH_20.getLiteral()));
        }

        if (!isRspClient) {

            this.logger.log(Level.INFO, "Registering Ontology Classes...");
            registerClasses();
            this.logger.log(Level.INFO, "Registered Ontology Classes");

        }
        this.loadBEJarFunctions();
        this.logger.log(Level.INFO, "Loaded BE Jar catalog functions");
        status = STATE_PROJECT_INITIALIZED;
    }

    protected void initRuleSessions() throws Exception {
        initProject();
        m_ruleSessionManager.init();
    }

    protected void initModules() throws Exception {
        initProject();
        moduleManager.init();
    }

    protected void initChannels() throws Exception {
        initProject();
        ((ChannelManagerImpl) m_channelManager).init();
    }

    protected void startAll(boolean activeMode) throws Exception {
        m_channelManager.connectChannels();
        bind();
        
        m_channelManager.startChannels(ChannelManager.SUSPEND_MODE);
        m_ruleSessionManager.start(activeMode);

        changeListenerService.start();

        // TODO: Why don't we call start here, instead of resume?
		// FILDIZ: Afraid to make that change, without knowing how 
		//		   other channels might be affected.
		//m_channelManager.startChannels(ChannelManager.ACTIVE_MODE);
        m_channelManager.resumeChannels();
        moduleManager.startModules(0);
    }

    /**
     * @throws Exception
     */
    protected void bind() throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            RuleSession ruleSession = this.m_ruleSessionManager.getRuleSession(archive.getName());
            for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                if (dest != null) {
                    logger.log(Level.DEBUG, ruleSession.getName() + " binding input channel " + config.getDestinationURI() + " to destination " + dest.getChannel().getURI());
                    dest.bind(ruleSession);
                } else {
                    logger.log(Level.WARN, ruleSession.getName() + " skip binding input channel " + config.getDestinationURI() + " to UNDEFINED destination ");
                }
            }
        }
    }
    
    public void bindForReconnect(Channel.Destination dest) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            RuleSession ruleSession = this.m_ruleSessionManager.getRuleSession(archive.getName());
            for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                if (dest.getURI().equals(config.getDestinationURI())) {
                    logger.log(Level.DEBUG, ruleSession.getName() + " (reconnect) binding input channel " + config.getDestinationURI() + " to destination " + dest.getChannel().getURI());
                    dest.bind(ruleSession);
                }
            }
        }
    }
    
    public void bind(RuleSession ruleSession) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    if (dest != null) {
                        logger.log(Level.DEBUG, ruleSession.getName() + " binding input channel " + config.getDestinationURI() + " to destination " + dest.getChannel().getURI());
                        dest.bind(ruleSession);
                    } else {
                        logger.log(Level.WARN, ruleSession.getName() + " skip binding input channel " + config.getDestinationURI() + " to UNDEFINED destination ");
                    }
                }
                return;
            }
        }
    }

    public void connectChannels(RuleSession ruleSession) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    if (dest != null) {
                        dest.getChannel().connect();
                        logger.log(Level.DEBUG, ruleSession.getName()
                                + " connected input channel " + dest.getChannel().getURI()
                                + " to destination " + config.getDestinationURI());
                    } /*else {
                        logger.log(Level.WARN, ruleSession.getName() + " skip connecting input channel " + config.getDestinationURI() + " to UNDEFINED destination ");
                    }*/
                }
                return;
            }
        }
    }

    public void startChannels(RuleSession ruleSession, int initialMode) throws Exception {
        ArrayList startedChannels = new ArrayList();
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    if (dest != null) {
                        Channel channel = dest.getChannel();
                        // Do not re-start 'started' channels to avoid confusion from printouts.
                        if (startedChannels.indexOf(channel) == -1) {
                            logger.log(Level.DEBUG, ruleSession.getName() + " starting channel " + config.getDestinationURI() + " with destination " + dest.getChannel().getURI());
                            channel.start(initialMode);
                            startedChannels.add(channel);
                        }
                    } else {
                        logger.log(Level.WARN, ruleSession.getName() + " skip channel " + config.getDestinationURI() + " with UNDEFINED destination ");
                    }
                }
                return;
            }
        }
    }

    public void resumeChannels(RuleSession ruleSession) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    dest.getChannel().resume();
                }
                return;
            }
        }
    }

    public void suspendChannels(RuleSession ruleSession) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config : archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    dest.getChannel().suspend();
                }
                return;
            }
        }
    }

    public void unbindRuleSession(RuleSession ruleSession) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config: archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    if (dest != null) {
                        ((AbstractDestination) dest).unbind(ruleSession);
                    }
                }
                return;
            }
        }
    }

    /**
     * Performs any activities that needs to be done after unbinding the RuleSession (after all the events has acknowledged).
     * @param ruleSession
     * @throws Exception
     */
    public void postUnbindRuleSession(RuleSession ruleSession) throws Exception {
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();
        while (j.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) j.next();
            if (archive.getName().equals(ruleSession.getName())) {
                for (final ArchiveInputDestinationConfig config: archive.getInputDestinations()) {
                    Channel.Destination dest = this.m_channelManager.getDestination(config.getDestinationURI());
                    if (dest != null) {
                        ((AbstractDestination) dest).postUnbind(ruleSession);
                    }
                }
                return;
            }
        }
    }

    public boolean isShuttingDown() {
        return isShuttingDown;
    }

    public void shutdown() {
        if (isShuttingDown) {
            return;
        }

        // Lock and enter.
        shutdownLock.lock();
        try {
            // Some one has already done it.
            if (isShuttingDown) {
                return;
            }

            isShuttingDown = true;
        } finally {
            shutdownLock.unlock();
        }

        // --------------

        printCallerToSysOut();

        if (this.isContained) {
            shutdown(false);
        }
        else {
            try {
                // do not wait if there is an exception or run mode is API
            	if (runMode == MODE_API) {
            		shutdown(false);
            	}else if (runMode != MODE_INIT && runMode != MODE_LOAD_ONLY && runMode != MODE_API && runMode != MODE_START_ALL && !exceptionShutdown) {
                    shutdown(true);
                } else {
                    /* if usingContainerLog, dont stop the logger as the container will manage it.
                     */
                    if (usingContainerLog) {
                        shutdown(false);
                    } else {
                        shutdown(true);
                    }
                }
            } catch (Exception e) {
                if (this.logger != null) {
                    this.logger.log(Level.ERROR, e, "Shutdown Exception");
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    private void printCallerToSysOut() {
        Thread t = Thread.currentThread();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);

        String s = getClass().getName() + "@" + hashCode() + " is being shutdown by thread [" +
                t.getName() + "]. Trace follows.";
        ps.println(s);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //Skip the first 3 items in the trace. Do not show this method in the trace.
        for (int i = 3; i < stackTraceElements.length; i++) {
            ps.println("    " + stackTraceElements[i]);
        }

        ps.close();
        byte[] bytes = bos.toByteArray();
        s = new String(bytes);

        System.out.println(s);
    }

    private void shutdown(boolean shutlogger) {
        boolean shutdownCluster = false;
        DeployedProject project = (DeployedProject) this.getProject();
        Collection archives = project.getDeployedBEArchives();
        Iterator j = archives.iterator();

        try {
            changeListenerService.stop();

            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
            registry.unregisterService(CacheChangeListenerService.class);
        }
        catch (Exception e) {
            this.logger.log(Level.ERROR, e,
                    "Error occurred while shutting down: %s", CacheChangeListenerService.class.getSimpleName());
        }

        while (j.hasNext()) {
            BEArchiveResource bar = (BEArchiveResource) j.next();
            if (bar.isCacheEnabled(project.getGlobalVariables())) {
                if (runMode == MODE_CLUSTER || runMode == MODE_CLUSTER_CACHESERVER) {
                    shutdownCluster = true;
                }
            }
        }
        if (!m_shutdown) {
            try {
                this.logger.log(Level.INFO, "Deactivating channels ...");
                this.getChannelManager().setMode(ChannelManager.PASSIVE_MODE);
            } catch (Exception e) {
                this.logger.log(Level.ERROR, e, "Error while deactivating channels.");
            }

            this.logger.log(Level.INFO, "RuleServiceProvider %s is shutting down.", this.m_instanceName);
            if (null != this.m_deployedProject) {
                this.m_deployedProject.stopHotDeploy(this);
                // this.m_deployedProject = null;
            }

            for (final Object listenerObject : this.shutdownListeners) {
                final RSPShutdownListener listener = (RSPShutdownListener) listenerObject;
                try {
                    listener.onShutdown();
                } catch (Exception e) {
                    this.logger.log(Level.ERROR, "Failed to stop shutdown hook %s", listener.getName());
                }
            }

            if (shutdownCluster) {
                for (RuleSession ruleSession : m_ruleSessionManager.getRuleSessions()) {
                    this.logger.log(Level.INFO, "Stopping rulesession [" + ruleSession.getName() + "]");
                    ruleSession.stopAndShutdown();
                    this.logger.log(Level.INFO, "Stopped rulesession [" + ruleSession.getName() + "]");
                }
            }
            else if (!this.isRspClient) {
                this.getRuleRuntime().shutdown();
                ((RuleSessionManagerImpl) this.m_ruleSessionManager).setCurrentRuleSession(null);
                this.m_ruleSessionManager = null;
            }

            try {
                this.logger.log(Level.INFO, "Shutting down ChannelManager ...");
                this.getChannelManager().shutdown();
            } catch (Exception e) {
                this.logger.log(Level.ERROR, e, "Error while shutting down ChannelManager.");
            }
            this.m_channelManager = null;

            try {
                this.logger.log(Level.INFO, "Shutting down RuleAdministrator ...");
                this.getRuleAdministrator().shutdown();
            } catch (Exception e) {
                this.logger.log(Level.ERROR, e, "Error while shutting down RuleAdministrator.");
            }
            this.m_ruleAdministrator = null;

            try {
                this.logger.log(Level.INFO, "Stopping modules ...");
                this.moduleManager.stopModules();
            } catch (Exception e) {
                this.logger.log(Level.ERROR, e, "Error while stopping modules.");
            }
            this.moduleManager = null;

            try {
                this.logger.log(Level.INFO, "Stopping services ...");
                ServiceRegistry.getSingletonServiceRegistry().discard();
                this.logger.log(Level.INFO, "Stopped services.");
            } catch (Exception e) {
                this.logger.log(Level.ERROR, e, "Error while stopping services.");
            }

            if (shutdownCluster) {
                try {
                    Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
                    if (cluster != null) {
                        this.logger.log(Level.INFO, "Shutting down cluster ...");
                        cluster.stop();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if (shutlogger) {
                this.logger.log(Level.INFO, "Closing Logger...");
                LogManagerFactory.getLogManager().close();
            }

            TraxSupport.destroy();

            m_shutdown = true;
        }

    }// shutdown

    public RuleRuntime getRuleRuntime() {
        return m_ruleSessionManager;
    }

    public RuleAdministrator getRuleAdministrator() {
        return m_ruleAdministrator;
    }

    public TypeManager getTypeManager() {
        return m_typeManager;
    }

    public RuleSessionManager getRuleSessionManager() {
        return m_ruleSessionManager;
    }

    public IdGenerator getIdGenerator() {
        return m_idGenerator;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        m_idGenerator = idGenerator;
    }

    public ClassLoader getClassLoader() {
        if (m_typeManager instanceof ClassLoader) {
            return (ClassLoader) m_typeManager;
        }
        return m_typeManager.getClass().getClassLoader();
    }

    public GlobalVariables getGlobalVariables() {
        return m_globalVariables;
    }

    public DeployedProject getProject() {
        return m_deployedProject;
    }

//    /**
//     * @return Logger
//     * @deprecated
//     */
//    public Logger getLogger() {
//        return m_logger;
//    }

    public com.tibco.cep.kernel.service.logging.Logger getLogger(String name) {
        return LogManagerFactory.getLogManager().getLogger(name);
    }

    public com.tibco.cep.kernel.service.logging.Logger getLogger(Class clazz) {
        return LogManagerFactory.getLogManager().getLogger(clazz);
    }

    public ChannelManager getChannelManager() {
        return m_channelManager;
    }

    public RuleSessionManager getRuleSessionService() {
        return m_ruleSessionManager;
    }

    public String getName() {
        return m_instanceName;
    }

    public Properties getProperties() {
        return this.beProperties;
    }

    public ResourceManager getResourceManager() {
        return null;
    }

    public BEProperties getSystemProperties() {
        return beProperties;
    }

    public Object getVariable(Object key) {
        return m_variables.get(key);
    }

    public void setVariable(Object key, Object value) {
        m_variables.put(key, value);
    }

    public void removeVariable(Object key) {
        m_variables.remove(key);
    }

    public void notify(ChangeEvent e) {
    	DeployedProject project = null;
        suspendRSP();
        try {
            Map<String, BEArchiveResource> addedSessions = null;
            project = m_deployedProject;  //get a Reference to it.
            GlobalVariables orgVar = m_globalVariables;
            try {
                String repoPath = e.getChangedProjects()[0];

                final Class deployedProjectClass = Class.forName("com.tibco.cep.studio.core.repo.emf.DeployedEMFProject");
                final Constructor deployedProjectConstructor =
                        deployedProjectClass.getConstructor(Properties.class, ChangeListener.class);
                this.m_deployedProject = (DeployedProject) deployedProjectConstructor.newInstance(
                        this.beProperties, this);

                this.m_deployedProject.load();

                this.logger.log(Level.INFO, "Project reloaded from location: %s", e.getChangedProjects()[0]);
                this.logger.log(Level.INFO, "Analyzing project...");
                HotDeployValidator.checkDeployedBEArchives(project, this.m_deployedProject);

                HotDeployValidator.checkChannelAndDestinationConfig(project, this.m_deployedProject);

                addedSessions = HotDeployValidator.getNewBEArchives(project, this.m_deployedProject);
                if (addedSessions.size() > 0) {
                    throw new Exception("Adding new session is not allowed, added " + addedSessions.keySet());
                }

                final Map<String, BEArchiveResource> deletedSessions = HotDeployValidator.getDeletedBEArchives(
                        project, this.m_deployedProject);
                if (deletedSessions.size() > 0) {
                    throw new Exception("Removing session is not allowed, removed " + deletedSessions.keySet());
                }

                //overwrite any changed values, add the deleted ones, and keep the newly created
                m_deployedProject.getGlobalVariables().merge(m_globalVariables);
                m_globalVariables = m_deployedProject.getGlobalVariables();

                final boolean isRulesChanged = HotDeployValidator.isDeployedRulesChanged(
                        project, this.m_deployedProject);

                Properties oversizeStringConstants = new Properties();
                Map analyzedResults = newNameToByteCode(m_deployedProject, oversizeStringConstants);

                if (!isRulesChanged && (analyzedResults == null)) {
                    this.logger.log(Level.INFO, "No changes.  Load new global variables (if any).");
                    OversizeStringConstants.init(oversizeStringConstants, (BEClassLoader) m_typeManager);
                    return;
                }

                this.logger.log(Level.INFO, "Hotdeploy applying...");
                // todo - stop channel

                ((BEClassLoader) m_typeManager).lockAndUpdate(newArchives(m_deployedProject), analyzedResults, oversizeStringConstants);

                final Cluster cluster = this.getCluster();
                if (null != cluster) {
                    boolean modelChanged = false;
                    boolean entitiesAdded = false;
                    List changedClasses = new ArrayList();
                    for(ClassLoadingResult clr : (Collection<ClassLoadingResult>)analyzedResults.values()) {
                        if(clr != null && (clr.status == ClassLoadingResult.NEW || clr.status == ClassLoadingResult.CHANGED)) {
                            Class cls = ((BEClassLoader) m_typeManager).loadClass(clr.className);
                            if(cls != null && Concept.class.isAssignableFrom(cls)) {
                                modelChanged = true;
                                if(clr.status == ClassLoadingResult.NEW) entitiesAdded = true;
                                else changedClasses.add(cls);
                            }
                        }
                    }

                    if(modelChanged) {
                        //this call is required even if no existing concepts were changed
                        //because reloading the metadata cache will also load the new concepts
                        cluster.getDaoProvider().modelChanged();
                        if(changedClasses.size() > 0) {
                            for(CacheAgent agent : cluster.getAgentManager().getLocalAgents()) {
                                agent.entitiesChanged(changedClasses);
                            }
                        }
                        if(entitiesAdded) {
                            for(CacheAgent agent : cluster.getAgentManager().getLocalAgents()) {
                                agent.entitiesAdded();
                            }
                            EximHelper.entitiesAdded();
                        }
                    }
                }

                // todo - start channel
                this.logger.log(Level.INFO, "Hotdeploy applied.");
            }
            catch (Exception ex) {
            	if(m_deployedProject!=null){
            		if(m_deployedProject != project){
            			m_deployedProject.close();
            		}
            	}
                m_deployedProject = project;
                m_globalVariables = orgVar;
                this.logger.log(Level.ERROR, ex, "Hot deployment of %s failed!!!", e.getChangedProjects()[0]);
                if (addedSessions != null && addedSessions.size() > 0) {
                    Iterator ite = addedSessions.keySet().iterator();
                    while (ite.hasNext()) {
                        String archiveName = (String) ite.next();
                        RuleSession session = m_ruleSessionManager.getRuleSession(archiveName);
                        if (session != null) {
                        	try {
                        		session.stopAndShutdown();
                        	}
                        	catch (Exception expt) {
                        	}
                        	m_ruleSessionManager.removeRuleSession(archiveName);
                        }
                    }
                }
                this.logger.log(Level.INFO, "Revert hot deployment changes");
                // create and assert advisory event
                ((RuleSessionManagerImpl) m_ruleSessionManager).notifyHotDeployment(false);
                // todo - start channel
            }
        } finally {
        	if(m_deployedProject != project){
        		project.close();
        	}
            resumeRSP();
            // notify all registered listeners
            for(ChangeListener listener: hdListeners) {
                listener.notify(e);
            }
        }
    }


    private Map newArchives(
            DeployedProject beproject) {
        Map ret = new HashMap();
        Collection c = beproject.getDeployedBEArchives();
        Iterator ite = c.iterator();
        while (ite.hasNext()) {
            BEArchiveResource archive = (BEArchiveResource) ite.next();
            ret.put(archive.getReferenceClassName(), archive);
        }
        return ret;
    }

    private Map newNameToByteCode(
            DeployedProject project,
            Properties oversizeStringConstants)
            throws Exception {
        HashMap nameToByteCode = new HashMap();
        ArchiveResourceProvider jarp = project.getJavaArchiveResourceProvider();
        Iterator i = jarp.getAllResourceURI().iterator();

        try {
            while (i.hasNext()) {
                byte[] raw = jarp.getResourceAsByteArray(i.next().toString());
                if (raw != null && raw.length != 0) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(raw);
                    nameToByteCode.putAll(JarInputStreamReader.read(new JarInputStream(bis)));
                }
            }
            if (nameToByteCode != null && nameToByteCode.size() > 0) {

                BEClassLoader beClassLoader = (BEClassLoader) m_typeManager;

                // remove this so that analyzeByteCodes doesn't choke on it
                oversizeStringConstants.putAll((Properties) nameToByteCode.remove(OversizeStringConstants.PROPERTY_FILE_NAME));

                // get differences
                Map resultMap = beClassLoader.analyzeByteCodes(nameToByteCode);

                boolean hasClassChanges = beClassLoader.validate(resultMap);
                if (hasClassChanges)
                    return resultMap;
                else {
                    return null;
                }
            }
            else {
                throw new Exception("Unable to read any class");
            }
        } finally {
            i = new ArrayList(jarp.getAllResourceURI()).iterator();
            while (i.hasNext()) {
                jarp.removeResource(i.next().toString());
            }
        }
    }

    /**
     * Safely deactivates all rule sessions.
     * First the working memory is locked to make sure that all current
     * run-to-completion cycles are finished and that no other cycle can be started.
     * Then the working memory is deactivated.
     *
     * @since 2.0
     */
    public void deactivateSession() {
        RuleSession[] archs = getRuleRuntime().getRuleSessions();
        deactivateRuleSessions(archs, 0);
    }


    private void deactivateRuleSessions(RuleSession[] archives, int sync) {
        if (sync == archives.length) {
            // locked
            // todo - stop the timers, stops other stuff
        } else {
            synchronized (((RuleSessionImpl) archives[sync]).getWorkingMemory()) {
                archives[sync].setActiveMode(false);
                deactivateRuleSessions(archives, sync + 1);
                return;
            }
        }
    }

    public void waitForTaskControllers() {
        RuleSession[] archs = getRuleRuntime().getRuleSessions();
        for (int i = 0; i < archs.length; i++) {
            ((RuleSessionImpl) archs[i]).waitForTaskController();
        }
    }

    public void lockRTC() throws Exception {
        getRuleRuntime().suspendWMs(true);
        this.logger.log(Level.DEBUG, "RTC Locking enabled...");
    }

    public void unlockRTC() throws Exception {
        getRuleRuntime().releaseWMs();
        this.logger.log(Level.DEBUG, "RTC Locking disabled...");
    }

    public boolean isRTCLocked() {
        return getRuleRuntime().isLocked();
    }

    public byte getStatus() {
        return this.status;
    }

    /**
     *
     */
    public void hawkShutdown() {
        if (this.isContained && this.m_containerRsp != null) {
            m_containerRsp.shutdown();
        } else {
            shutdown();
        }

    }

    public void addShutdownListener(RSPShutdownListener listener) throws Exception {
        if (listener == null || listener != null &&
                (listener.getName() == null || listener.getName() != null && listener.getName().trim().equals(""))) {
            throw new Exception("Cannot add null shutdownlistener or listener with null or empty name");
        }
        this.shutdownListeners.add(listener);
    }

    public void onOutOfMemory(Thread t, Throwable e) {
        this.logger.log(Level.FATAL, e, "Out of Memory Exception on thread (%s): %s", t.getId(), t.getName());
        System.exit(-1);
    }

    // -----------------

    public interface RSPShutdownListener {
        public String getName();

        public void onShutdown();
    }

    protected static class DomainInfo {
        protected String name;
        protected int id;
        protected String type;

        public DomainInfo(String name, int id, String type) {
            this.name = name;
            this.id = id;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }
    }

    @Override
    public Cluster getCluster() {
        return cluster;
    }

    // ntamhank: adding method for registering hot deployment listeners
    public void registerHDListener(ChangeListener listener) {
        this.hdListeners.add(listener);
    }
    
	public void setLifecycleListener(LifecycleListener lifecycleListener) {
		// for now we only need one
		this.m_lifecycleListener = lifecycleListener;
	}
	
	public LifecycleListener getLifecycleListener() {
		return m_lifecycleListener;
	}

}
