package com.tibco.cep.runtime.session.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.BeTransaction;
import com.tibco.cep.kernel.core.rete.CompositeAction;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.RuleLoader;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.InvalidRuleException;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.kernel.service.profiler.CSVWriter;
import com.tibco.cep.kernel.service.profiler.SimpleProfiler;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineRule;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.scheduler.TaskControllerFactory;
import com.tibco.cep.runtime.service.exception.BEExceptionHandler;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.service.om.ObjectStore;
import com.tibco.cep.runtime.service.om.impl.mem.ConcurrentInMemoryObjectManager;
import com.tibco.cep.runtime.service.om.impl.mem.InMemoryObjectManager;
import com.tibco.cep.runtime.service.time.BETimeManager;
import com.tibco.cep.runtime.session.ProcessingContextProvider;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionEx;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.RuleSessionMetrics;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.SimpleConcurrentLocalLockManager;
import com.tibco.cep.runtime.session.sequences.InMemorySequenceManager;
import com.tibco.cep.runtime.session.sequences.SequenceManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.FileBasedRecorder;
import com.tibco.cep.util.InmemoryRecorder;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 29, 2006
 * Time: 4:36:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleSessionImpl implements RuleSessionEx {
    protected String name;
    protected RuleSessionManagerImpl sessManager;
    protected WorkingMemoryImpl workingMemory;
    protected BaseObjectManager objectManager;

    protected TaskController controller;

    protected CompositeAction startup;
    protected CompositeAction shutdown;

    protected boolean objectStoreEnabled;
    protected boolean activeMode;
    public boolean cacheServer;
    protected RuleSessionConfigImpl config;

    protected LockManager lockMgr;
    protected int instanceId;

    protected Configuration configuration = null;
    protected InMemorySequenceManager localSequenceManager = new InMemorySequenceManager();

    protected ReadWriteLock smTimeoutCallBacksLock = new ReentrantReadWriteLock();
    protected Map<Class, RuleFunction> smTimeoutCallbacks = null;
    protected RuleSessionMetrics ruleSessionMetrics = null;

    protected Object ruleTemplateLoader = null;

    private static final Class RULE_TEMPLATE_CLASS;
    private static final Class RULE_TEMPLATE_LOADER_CLASS;
    private static final Class TEMPLATE_CONFIG_FILE_LOADER_CLASS;
    protected static Logger logger = LogManagerFactory.getLogManager().getLogger(RuleSession.class);
    private final ProcessingContextProvider processingContextProvider = new ProcessingContextProvider();


    static {
        Class rtClass, rtlClass, tcfl;
        try {
            rtClass = Class.forName("com.tibco.cep.interpreter.template.TemplatedRule");
            rtlClass = Class.forName("com.tibco.cep.interpreter.template.TemplatedRuleLoader");
            tcfl = Class.forName("com.tibco.cep.interpreter.template.TemplateConfigFileLoader");
        }
        catch (Throwable t) {
            rtClass = rtlClass = tcfl = null;
        }
        RULE_TEMPLATE_CLASS = rtClass;
        RULE_TEMPLATE_LOADER_CLASS = rtlClass;
        TEMPLATE_CONFIG_FILE_LOADER_CLASS = tcfl;
    }

    protected RuleSessionImpl() {

    }

    protected RuleSessionImpl(LockManager lockMgr) {
        this();
        try {
            if (this.lockMgr == null) {
                if (lockMgr != null) {
                    this.lockMgr = lockMgr;
                } else {
                    this.lockMgr = createDummyLockMgr();
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        String name = getClass().getSimpleName() + ":" + System.identityHashCode(this);
        this.configuration = new Configuration(name, new Properties());
        this.ruleSessionMetrics = new RuleSessionMetricsImpl(this);
    }

//    protected RuleSessionImpl(RuleSessionManagerImpl manager) throws Exception {
//        this.sessManager = manager;
//        this.objectManager = ObjectManagerProvider.createOM(null, this);
//        init(false);
//        loadNamedInstances();
//
//        String name = getClass().getSimpleName() + ":" + System.identityHashCode(this);
//        this.configuration = new Configuration(name, new Properties());
//        this.ruleSessionMetrics = new RuleSessionMetricsImpl(this);
//    }

    protected RuleSessionImpl(BEArchiveResource barResource, final RuleSessionManagerImpl manager) throws Exception {
        this(barResource, null, manager,-1, null);
    }

    protected RuleSessionImpl(BEArchiveResource barResource, BEProperties beProperties, final RuleSessionManagerImpl manager) throws Exception {
        this(barResource,beProperties, manager,-1, null);
    }

    protected RuleSessionImpl(BEArchiveResource barResource, BEProperties beProperties, RuleSessionManagerImpl manager, ObjectManager objectManager) throws Exception {
        this(barResource, beProperties, manager, -1, objectManager);
    }

    /**
     * @param barResource
     * @param beProperties Can be <code>null</code>.
     * @param manager
     * @param instance
     * @throws Exception
     */
    protected RuleSessionImpl(BEArchiveResource barResource, BEProperties beProperties, final RuleSessionManagerImpl manager, int instance, ObjectManager objectMgr) throws Exception {
        sessManager = manager;
        sessManager.setCurrentRuleSession(this);
        this.objectManager = (BaseObjectManager) objectMgr;

        try {
            config = new RuleSessionConfigImpl(barResource, sessManager.getRuleServiceProvider());
            configuration = config.getRuleSessionConfig();

            if (instance == -1) {
                name = config.getName();
                this.instanceId = -1;
            } else {
                name = config.getName()+"."+instance;
                this.instanceId = instance;
            }

            // ConcurrentRTC flag is set at the Agent class level (not at the PU level). Therefore use AgentName not AgentKey
            String agentName = config.getCacheConfig().getProperty(Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME);
            boolean isConcurrent = Boolean.parseBoolean(manager.getEnv().getProperty("Agent." + agentName + ".concurrentwm", "false"));
            boolean enableNewConcurrentImpl = Boolean.parseBoolean(manager.getEnv().getProperty("be.engine.concurrentwm.enable.newImpl", String.valueOf(isConcurrent)));
            if (this.objectManager == null) {
                this.objectManager =  isConcurrent && enableNewConcurrentImpl ? new ConcurrentInMemoryObjectManager(name) : new InMemoryObjectManager(this.getName());
            }
            logger.log(Level.INFO, String.format("Using ObjectManager type :%s Concurrent : %s " , this.objectManager, isConcurrent));

            if (!barResource.getName().equals(RuleSessionManager.DEFAULT_CLUSTER_RULESESSION_NAME) ) {
                controller = TaskControllerFactory.createTaskController(this, sessManager.getRuleServiceProvider().getProperties());
            }

            objectStoreEnabled = (this.objectManager instanceof ObjectStore);
            BaseTimeManager timeManager = (this.objectManager.getTimeManager() == null) ?
                    new BETimeManager(this, new SetSessionContext(this)) : this.objectManager.getTimeManager();

            BEExceptionHandler exphandler = new BEExceptionHandler(sessManager.getRuleServiceProvider());
            boolean isMultiEngineMode= ((RuleServiceProviderImpl) sessManager.getRuleServiceProvider()).isMultiEngineMode();
            final LogManager logManager = LogManagerFactory.getLogManager();

            workingMemory = new ReteWM(name, logManager, exphandler, this.objectManager, timeManager, null, isMultiEngineMode, isConcurrent);

            exphandler.setWorkingMemory((ReteWM)workingMemory);

            //this.workingMemory.getRuleLoader().deployRulesToWM(this.config.getDeployedRuleUris());

            this.initStartupShutdown();

            activeMode = false;
            this.ruleSessionMetrics = new RuleSessionMetricsImpl(this);
        }
        finally {
            sessManager.setCurrentRuleSession(null);
        }
    }

    protected void initStartupShutdown() {
        List<String> functionUris = this.config.getStartupFunctionUris();
        if ((null != functionUris) && (functionUris.size() > 0)) {
            final List<RuleFunction> ruleFunctions = new ArrayList<RuleFunction>();
            for (String uri : functionUris) {
                ruleFunctions.addAll(this.getStartupRuleFunctions(uri));
            }
            this.startup = new RuleFunctionsExecAction(ruleFunctions);
        }
        functionUris = this.config.getShutdownFunctionUris();
        if ((null != functionUris) && (functionUris.size() > 0)) {
            final List<RuleFunction> ruleFunctions = new ArrayList<RuleFunction>();
            for (String uri : functionUris) {
                ruleFunctions.addAll(this.getStartupRuleFunctions(uri));
            }
            this.shutdown = new RuleFunctionsExecAction(ruleFunctions);
        }
    }

    private static LockManager createDummyLockMgr() throws Exception {
        return new SimpleConcurrentLocalLockManager();
    }

    private static LockManager extractOrCreateLockMgr(ObjectManager objectManager)
            throws Exception {
        if (objectManager instanceof ObjectBasedStore) {
            return ((ObjectBasedStore) objectManager).getLockManager();
        }

        return createDummyLockMgr();
    }

    public int getInstanceId() {
        return instanceId;
    }

    public String getName() {
        return name;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public RuleSessionConfig getConfig() {
        return config;
    }

    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public TaskController getTaskController() {
        return controller;
    }

    public void waitForTaskController() {
        if (controller != null) {
            controller.shutdown();
        }

        //        try {
        //            if (controller != null) {
        //                controller.shutdown();
        //            }
        //            controller.shutdown();
        //            while (controller != null && controller.isRunning())
        //                Thread.sleep(10);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
    }

    public boolean getActiveMode() {
        return activeMode;
    }

    @Override
    public RuleSessionMetrics getRuleSessionMetrics() {
        return ruleSessionMetrics;
    }

    public void setActiveMode(boolean active) {
        activeMode = active;
        sessManager.setCurrentRuleSession(this);
        try {
            this.objectManager.setActiveMode(active);
//            if(active)
//                this.loadNamedInstances();
            workingMemory.setActiveMode(active);
        }
        finally {
            sessManager.setCurrentRuleSession(null);
        }

    }

    public void start(boolean active) throws Exception {
        if (lockMgr == null) {
            lockMgr = extractOrCreateLockMgr(objectManager);
        }

        activeMode = active;
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if(threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                String warning = workingMemory.getWarningMessages();
                if(warning != null && warning.length() > 0) {
                    logger.log(Level.WARN, warning);
                    workingMemory.clearWarningMessages();
                }
                objectManager.setActiveMode(active);

//                new BeTransaction("rulesession-start") {
//                    @Override
//                    protected void doTxnWork() {
//                        loadNamedInstances();
//                    }
//                }.execute();

                if (controller != null) {
                    controller.start();
                }
                workingMemory.start(active);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't start another session " + this.getName());
        }
    }

    public void stop() {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.stop();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't stop another session " + this.getName());
        }
    }
    
    public void suspend() {
    	waitForTaskController();
        workingMemory.suspend();
    }
    
    public void resume() throws Exception {
    	if(controller != null) {
    		controller.start();
    	}
        workingMemory.resume();
    }

    public void stopAndShutdown() {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            try {
                if (controller != null) {
                    controller.shutdown();
                }
                workingMemory.stopAndShutdown((CompositeAction)shutdown);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                if (controller != null) {
                    controller.shutdown();
                }
                workingMemory.stopAndShutdown((CompositeAction)shutdown);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't shutdown another session " + this.getName());
        }
    }

    public void init(boolean cacheServerOnly) throws Exception {
        init(cacheServerOnly, false);
    }

    public void init(boolean cacheServerOnly, boolean isConcurrent) throws Exception {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                cacheServer = cacheServerOnly;
                // Remove this for multiple threads recovery Nick needs to review this
                // to see if it is safe
                // synchronized(workingMemory) {
                if (cacheServer) {
                    this.workingMemory.init((CompositeAction)null, null, new HashSet<Rule>());  //no rule and no startup function
                }
                else {
                    this.workingMemory.init((CompositeAction)this.startup, new ActivateAction(), this.loadRules());
                }
                registerTypes();
                workingMemory.initEntitySharingLevels();

                if (objectStoreEnabled)
                    ((ObjectStore) objectManager).recover();

                // Initialize Profiler except for non-default cluster rulesession "$cluster"
                if (!this.name.equals(sessManager.DEFAULT_CLUSTER_RULESESSION_NAME)) {// not to do profiling for the default cluster server
                    initializeProfiler();
                    initializeRecorder();
                }
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't init another session " + this.getName());
        }
    }

    protected Set<Rule> loadRules()
            throws SetupException, InvalidRuleException {
        final Set<Rule> rules = new HashSet<Rule>();

        final Set<String> deployedRuleUris = this.config.getDeployedRuleUris();
        final RuleLoader ruleLoader = this.workingMemory.getRuleLoader();
        final TypeManager typeManager = this.sessManager.getRuleServiceProvider().getTypeManager();

        final String separator = String.valueOf(Folder.FOLDER_SEPARATOR_CHAR);

        for (Object o : typeManager.getTypeDescriptors(TypeManager.TYPE_RULE)) {
            final TypeManager.TypeDescriptor descriptor = (TypeManager.TypeDescriptor) o;
            if (StateMachineRule.class.isAssignableFrom(descriptor.getImplClass())) {
                rules.add(ruleLoader.loadRule(descriptor.getImplClass()));
            } else if (!isRuleTemplate(descriptor)) {
                // Checks for both rules and rule folders
                final String uri = descriptor.getURI();
                for (String dru : deployedRuleUris) {
                    if (uri.equals(dru)
                            || (dru.endsWith(separator) && uri.startsWith(dru))
                            || ((!dru.endsWith(separator)) && uri.startsWith(dru + separator))) {
                        rules.add(ruleLoader.loadRule(descriptor.getImplClass()));
                    }
                }
            }
        }

        rules.addAll(this.loadRuleTemplates());

        return rules;
    }


    public boolean isRuleTemplate(TypeManager.TypeDescriptor descriptor) {
        return (null != RULE_TEMPLATE_CLASS)
                && RULE_TEMPLATE_CLASS.isAssignableFrom(descriptor.getImplClass());
    }


    private Set<Rule> loadRuleTemplates()
            throws InvalidRuleException, SetupException {

        if ((null == this.ruleTemplateLoader) && (null != RULE_TEMPLATE_LOADER_CLASS)) {
            try {
            	RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
            	String rtiDeploymentPath = RSP.getProperties().getProperty(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
                if (null != rtiDeploymentPath) {
                	rtiDeploymentPath = RSP.getGlobalVariables().substituteVariables(rtiDeploymentPath).toString();
                    final File rtiDeploymentFile = new File(rtiDeploymentPath);
                    if (rtiDeploymentFile.canRead()) {
                        TEMPLATE_CONFIG_FILE_LOADER_CLASS.getMethod("loadAll", File.class, boolean.class).invoke(
                                TEMPLATE_CONFIG_FILE_LOADER_CLASS.newInstance(), rtiDeploymentFile, true);
                    }
                }

                this.ruleTemplateLoader = RULE_TEMPLATE_LOADER_CLASS
                        .getConstructor(new Class[]{RuleLoader.class, RuleServiceProvider.class})
                        .newInstance(this.workingMemory.getRuleLoader(), this.sessManager.getRuleServiceProvider());
            }
            catch (Throwable t) {
                //Need to log such errors. No point in just wrapping the exception message.
                logger.log(Level.ERROR, "Rule Template loader init failed", t);
                throw new SetupException(t.getMessage());
            }
        }

        if (null == this.ruleTemplateLoader) {
            this.logger.log(Level.DEBUG, "Rule templates not available.");
            return new HashSet<Rule>();
        }

        try {
            //noinspection unchecked
            return (Set<Rule>) RULE_TEMPLATE_LOADER_CLASS.getMethod("loadRuleTemplates")
                    .invoke(this.ruleTemplateLoader);
        }
        catch (Exception e) {
            this.logger.log(Level.ERROR, e, "Failed to load rule templates: %s", e.getMessage());
            if (e instanceof InvalidRuleException) {
                throw (InvalidRuleException) e;
            }
            else if (e instanceof SetupException) {
                throw (SetupException) e;
            }
            else {
                throw new SetupException(e.getMessage());
            }
        }
    }

    public void registerTypes() {
        registerTypes(workingMemory);
    }


    public void registerTypes(WorkingMemory workingMemory) {
        //register the advisory event type
        workingMemory.registerType(AdvisoryEventImpl.class);

        try {
            Class c = Class.forName("com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl$StateTimeoutEvent");
            workingMemory.registerType(c);
            c = Class.forName("com.tibco.cep.runtime.model.element.impl.LocalStateTimeoutEvent");
            workingMemory.registerType(c);
        } catch (ClassNotFoundException e) {
            this.logger.log(Level.DEBUG, "StateTimeoutEvent is not available.");
        }

        /**
         try {
         workingMemory.getRuleLoader().addRule(null, new StateMachineConceptImpl.StateTimeoutRule("$StateTimeoutRule"));
         workingMemory.getRuleLoader().deployRuleSet(null);
         } catch (SetupException e) {
         e.printStackTrace();   //impossible
         } catch (InvalidRuleException e) {
         e.printStackTrace();   //impossible
         } catch (Exception e) {
         e.printStackTrace();
         }
         **/
        TypeManager typeManager = sessManager.getRuleServiceProvider().getTypeManager();
        Collection types = typeManager.getTypeDescriptors(TypeManager.TYPE_CONCEPT | TypeManager.TYPE_NAMEDINSTANCE | TypeManager.TYPE_SIMPLEEVENT | TypeManager.TYPE_TIMEEVENT | TypeManager.TYPE_STATEMACHINE);
        Iterator ite = types.iterator();
        while (ite.hasNext()) {
            TypeManager.TypeDescriptor typeDescriptor = (TypeManager.TypeDescriptor) ite.next();
            workingMemory.registerType(typeDescriptor.getImplClass());
            if (this.objectManager instanceof ObjectBasedStore) {
                ((ObjectBasedStore) objectManager).registerType(typeDescriptor);
            }
        }
        if (this.objectManager instanceof ObjectBasedStore) {
            ((ObjectBasedStore) objectManager).registerType(StateMachineConceptImpl.StateTimeoutEvent.class);
        }
    }

//    public void loadNamedInstances() {
//        if (cacheServer) return;
//        if (objectStoreEnabled && !activeMode) return;
//        TypeManager typeManager = sessManager.getRuleServiceProvider().getTypeManager();
//        Collection namedInstanceDescriptors = typeManager.getTypeDescriptors(TypeManager.TYPE_NAMEDINSTANCE);
//        Iterator ite = namedInstanceDescriptors.iterator();
//        while (ite.hasNext()) {
//            Element namedInst = null;
//            TypeManager.TypeDescriptor typeDescriptor = (TypeManager.TypeDescriptor) ite.next();
//            Class namedInstanceClass = typeDescriptor.getImplClass();
//            namedInst = objectManager.getNamedInstance(typeDescriptor.getURI(), namedInstanceClass);
//            this.logger.log(Level.DEBUG, "Loaded NamedInstance [%s]", namedInst);
//            //            if (objectStoreEnabled) {
//            //                namedInst=((ObjectStore) objectManager).getNamedInstance(typeDescriptor.getURI(), namedInstanceClass);
//            //            } else {
//            //                namedInst = objectManager.getElement(typeDescriptor.getURI());
//            //                if(namedInst == null) {
//            //
//            //                    long id = sessManager.getRuleServiceProvider().getIdGenerator().nextEntityId();
//            //                    try {
//            //                        Constructor cons = namedInstanceClass.getConstructor(new Class[] {long.class});
//            //                        namedInst = (Element) cons.newInstance(new Object[] {new Long(id)});
//            //                        loadObject(namedInst);
//            //                    }
//            //                    catch(Exception ex) {
//            //                        ex.printStackTrace();
//            //                        this.getRuleServiceProvider().getLogger().logError("Can't created NamedInstance  [" + namedInst + "]");
//            //                    }
//            //                }
//            ////                if(objectStoreEnabled)
//            ////                    ((ObjectStore)objectManager).addInstance((Concept)namedInst);
//            //                getRuleServiceProvider().getLogger().logDebug("Created NamedInstance [" + namedInst + "]");
//            //            }
//            //            getRuleServiceProvider().getLogger().logDebug("Loaded NamedInstance [" + namedInst + "]");
//        }
//    }

    public RuleFunction getRuleFunction(String URI) {
        RuleFunction ruleFunc = ((BEClassLoader) getRuleServiceProvider().getTypeManager()).getRuleFunctionInstance(URI);
        if (ruleFunc == null)
            throw new RuntimeException("RuleFunction " + URI + " does not exist");
        return ruleFunc;
    }

    public List<RuleFunction> getStartupRuleFunctions(String uri) {
        final List<RuleFunction> functions = new ArrayList<RuleFunction>();

        final BEClassLoader typeManager = (BEClassLoader) this.sessManager.getRuleServiceProvider().getTypeManager();
        final Ontology ontology = this.sessManager.getRuleServiceProvider().getProject().getOntology();

        final String separator = String.valueOf(Folder.FOLDER_SEPARATOR_CHAR);

        for (TypeManager.TypeDescriptor descriptor :
                new TreeSet<TypeManager.TypeDescriptor>((Collection<TypeManager.TypeDescriptor>)
                        typeManager.getTypeDescriptors(TypeManager.TYPE_RULEFUNCTION))) {
            final String rfUri = descriptor.getURI();
            if (rfUri.equals(uri)
                    || (uri.endsWith(separator) && rfUri.startsWith(uri))
                    || ((!uri.endsWith(separator)) && rfUri.startsWith(uri + separator))) {
                final com.tibco.cep.designtime.model.rule.RuleFunction drf = ontology.getRuleFunction(rfUri);
                if (drf.getArguments().isEmpty()
                        && (drf.getValidity() == com.tibco.cep.designtime.model.rule.RuleFunction.Validity.ACTION)) {
                    final RuleFunction rf = typeManager.getRuleFunctionInstance(rfUri);
                    if (null == rf) {
                        throw new RuntimeException("RuleFunction " + rfUri + " was not found.");
                    }
                    functions.add(rf);
                }
            }
        }
        return functions;
    }

    public Object[][] findSessionMatches(String ruleURI, Object[] arguments, boolean executeRuleAction, boolean dirtyRead) {
        sessManager.setCurrentRuleSession(this);
        try {
            Class ruleClass = this.getRuleServiceProvider().getTypeManager().getTypeDescriptor(ruleURI).getImplClass();
            Rule rule = this.getWorkingMemory().getRuleLoader().getRule(ruleClass);
            if (rule == null) {
                throw new RuntimeException("Rule <" + ruleURI + "> is not deployed");
            }
            return workingMemory.findMatches(rule, arguments, executeRuleAction, dirtyRead);
        } finally {
            sessManager.setCurrentRuleSession(null);
        }
    }

    public Object[][] findMatches(String ruleURI, Object[] arguments, boolean executeRuleAction, boolean dirtyRead) {
        Class ruleClass = this.getRuleServiceProvider().getTypeManager().getTypeDescriptor(ruleURI).getImplClass();
        Rule rule = this.getWorkingMemory().getRuleLoader().getRule(ruleClass);
        if (rule == null) {
            throw new RuntimeException("Rule <" + ruleURI + "> is not deployed");
        }
        return workingMemory.findMatches(rule, arguments, executeRuleAction, dirtyRead);
    }

    public Object invokeFunction(String functionURI, Object[] args, boolean synchronize) {
        return invokeFunction(functionURI, args, synchronize, false);
    }
    public Object invokeFunction(String functionURI, Object[] args, boolean synchronize, boolean force) {
        RuleFunction ruleFn = getRuleFunction(functionURI);
        return invokeFunction(ruleFn, args, synchronize, force);
    }

    public Object invokeFunction(final RuleFunction ruleFn, final Object[] args, final boolean synchronize) {
        return invokeFunction(ruleFn, args, synchronize, false);
    }
    public Object invokeFunction(final RuleFunction ruleFn, final Object[] args, final boolean synchronize, final boolean force) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (!force && threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (force || threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                final Object[] array = new Object[1];

                new BeTransaction("invoke-function"){
                    @Override
                    protected void doTxnWork() {
                        if (synchronize){
                            array[0] = workingMemory.invoke(ruleFn, args);
                        }
                        else {
                            PreprocessContext preContext = PreprocessContext.newContext(RuleSessionImpl.this);
                            FunctionExecutionContext fc = new FunctionExecutionContext(ruleFn, args);
                            ((ReteWM) workingMemory).setCurrentContext(fc);
                            array[0] = ruleFn.invoke(args);
                            if (preContext.hasWork()) {
                                List asserted = preContext.getAsserted();
                                LinkedHashSet deleted = preContext.getDeleted();
                                List reloaded = preContext.getReloaded();
                                List reevaluate = preContext.getReevaluate();
                                PreprocessContext.cleanContext();
                                ((ReteWM) workingMemory).executeRules("Invoke: " + ruleFn.getSignature(), reloaded, asserted, deleted, reevaluate);
                            }
                        }
                    }
                }.execute();

                return array[0];
            }
            finally {
                try {
                    releaseLocks();
                }
                finally {
                    sessManager.setCurrentRuleSession(null);
                    PreprocessContext.cleanContext();
                }
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.session.RuleSession#invokeCatalog(java.lang.String, java.lang.Object[], boolean)
     * The invokeCatalog does not check RuleSession because the onus of checking the session is on the called catalog function.
     */
    @Override
    public Object invokeCatalog(final String functionFQName,final Object... args) {
    	 
    	 JavaStaticFunction statFunc = null;
    	 final Object[] array = new Object[1];
    	 try {
    		 Object catFn = FunctionsCatalog.getINSTANCE().lookup(functionFQName, true);
    		 if(catFn != null && catFn instanceof JavaStaticFunction ) {
    			 statFunc = (JavaStaticFunction) catFn;
    			 array[0] = statFunc.getMethod().invoke(null, args);
    		 } 
    	 
    	 }catch (Exception e) {
    		 throw new RuntimeException("Failed to execute static function:"+functionFQName,e);
    	 }
    	 
    	 return array[0];
    }

    /*
   public void preprocessPassthru(RuleFunction func, SimpleEvent[] events) {
       RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
       if(threadSession == this) { //already inside
           throw new RuntimeException("Can't invokeFunction inside the session itself");
       }
       else if(threadSession == null) {
           sessManager.setCurrentRuleSession(this);
           try {
               PreprocessContext preContext = PreprocessContext.newContext(this);
               for(int i = 0; i < events.length; i++) {
                   try {
                       preContext.add(events[i]);
                       func.invoke(new Object[] { events[i] });
                   }
                   catch(Exception ex) {
                       String msg = getName() + " Got exception [" + ex + "] while executing preprocessor [" + func.getSignature() + "] for event [" + events[i] + "]";
                       this.getRuleServiceProvider().getLogger().logError(msg, ex);
                   }
               }
               if(preContext.hasWork()) {
                   List asserted = preContext.getAsserted();
                   LinkedHashSet deleted = preContext.getDeleted();
                   List reloaded = preContext.getReloaded();
                   PreprocessContext.cleanContext();
                   ((ReteWM)workingMemory).executeRules(func, reloaded, asserted,deleted);
               }
           }
           finally {
               releaseLocks();
               sessManager.setCurrentRuleSession(null);
               PreprocessContext.cleanContext();
           }
       }
       else {
           throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
       }
    }
    */

    public void releaseLocks() {
        if(lockMgr != null){
            lockMgr.unlockAllLocksHeldByThread();
        }

        if (this.getObjectManager() instanceof ObjectBasedStore) {
            ((ObjectBasedStore) this.getObjectManager()).getLockManager().unlockAllLocksHeldByThread();
        }
    }

    public PreprocessContext assertSMTimeout(final com.tibco.cep.kernel.model.entity.Event event,
                                             final Concept parent) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            final PreprocessContext[] array = new PreprocessContext[1];

            new BeTransaction("assert-sm-timeout"){
                @Override
                protected void doTxnWork() {
                    sessManager.setCurrentRuleSession(RuleSessionImpl.this);
                    try {
                        PreprocessContext preContext = PreprocessContext.newContext(RuleSessionImpl.this);
                        preContext.reload(parent);
                        preContext.setTrigger(event);

                        String ppSig = "";
                        RuleFunction func = getSMTimeoutCallback(parent.getClass());
                        if(func != null) {
                            try {
                                ppSig = "Preprocessor: " + func.getSignature();
                                Object[] args = new Object[]{parent};
                                func.invoke(args);
                            }
                            catch (Exception ex) {
                                logger.log(Level.ERROR, ex,
                                        "%s got exception [%s] while executing preprocessor [%s] for event [%s]",
                                        getName(), ex, func.getSignature(), event);
                            }
                        }
                        
                        //reload the parent concept after the PP has executed and before adding it to the precontext
                   	 	//since it must be loaded and passed to the PP function before any lock is taken in the PP
                   	 	//it may otherwise be stale or even deleted
                        Object reloadedParent = objectManager.getElement(parent.getId(), parent.getClass());
                        preContext.forceRefresh(parent.getId(), parent.getExtId(), parent, reloadedParent);
                        //if the parent concept is null then act like the event was consumed by the preprocessor
                        if(reloadedParent == null) {
                        	preContext.delete(event);
                        }

                        if (preContext.hasWork()) {
                            List asserted = preContext.getAsserted();
                            LinkedHashSet deleted = preContext.getDeleted();
                            List reloaded = preContext.getReloaded();
                            List reevaluate = preContext.getReevaluate();
                            PreprocessContext.cleanContext();
                            ((ReteWM) workingMemory).executeRules(ppSig, reloaded, asserted, deleted, reevaluate);
                        }
                        array[0] = preContext;
                    }
                    finally {
                        releaseLocks();
                        sessManager.setCurrentRuleSession(null);
                        PreprocessContext.cleanContext();
                    }
                }
            }.execute();

            return array[0];
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    public PreprocessContext preprocessPassthru(final RuleFunction func, final com.tibco.cep.kernel.model.entity.Event event, final Object[] args) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            final PreprocessContext[] array = new PreprocessContext[1];

            new BeTransaction("preprocess-pass-thru"){
                @Override
                protected void doTxnWork() {
                    sessManager.setCurrentRuleSession(RuleSessionImpl.this);
                    try {
                        PreprocessContext preContext = PreprocessContext.newContext(RuleSessionImpl.this);
                        try {
                            preContext.setTrigger(event);
                            //preContext.add(event);
                            func.invoke(args);
                        }
                        catch (Exception ex) {
                            logger.log(Level.ERROR, ex,
                                    "%s got exception [%s] while executing preprocessor [%s] for event [%s]",
                                    getName(), ex, func.getSignature(), event);
                        }
                        if (preContext.hasWork()) {
                            List asserted = preContext.getAsserted();
                            LinkedHashSet deleted = preContext.getDeleted();
                            List reloaded = preContext.getReloaded();
                            List reevaluate = preContext.getReevaluate();
                            PreprocessContext.cleanContext();
                            ((ReteWM) workingMemory).executeRules("Preprocessor: " + func.getSignature(), reloaded, asserted, deleted, reevaluate);
                        }
                        array[0] = preContext;
                    }
                    finally {
                        releaseLocks();
                        sessManager.setCurrentRuleSession(null);
                        PreprocessContext.cleanContext();
                    }
                }
            }.execute();

            return array[0];
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    public PreprocessContext applyElementChanges(RuleFunction func, long id, String extId, int version,
                                                 int typeId, Class entityClz, int[] dirtyBits) {

        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                PreprocessContext preContext = null;
                if (func != null) {
                    preContext = PreprocessContext.newContext(this);
                    try {
                        Object[] args = new Object[5];

                        args[0] = id;
                        args[1] = extId;
                        args[2] = typeId;
                        args[3] = version;
                        args[4] = false;

                        Object ret = func.invoke(args);
                        if (ret instanceof Boolean) {
                            if (!((Boolean) ret).booleanValue()) {
                                return preContext;
                            }
                        }
                    }
                    catch (Exception ex) {
                        this.logger.log(Level.ERROR, ex,
                                "%s got exception [%s] while executing preprocessor [%s] for id [%s,class=%s]",
                                getName(), ex, func.getSignature(), id, entityClz);
                    }
                }
                if ((preContext != null) && preContext.hasWork()) {
                    List asserted = preContext.getAsserted();
                    LinkedHashSet deleted = preContext.getDeleted();
                    List reloaded = preContext.getReloaded();
                    List reevaluate = preContext.getReevaluate();
                    boolean reloadOnly = preContext.getClusterSubscriptionReloadOnly();
                    PreprocessContext.cleanContext();
                    //((ReteWM)workingMemory).executeRules("Preprocessor: "+ func.getSignature() , reloaded, asserted,deleted);
                    ((ReteWM) workingMemory).applyElementChanges("Preprocessor: " + func.getSignature(), id, entityClz, dirtyBits, reloaded, asserted, deleted, reevaluate, reloadOnly);
                } else {
                    ((ReteWM) workingMemory).applyElementChanges("No-Preprocessor: ", id, entityClz, dirtyBits, null, null, null, null, false);
                }
                return preContext;
            }
            finally {
                releaseLocks();
                sessManager.setCurrentRuleSession(null);
                PreprocessContext.cleanContext();
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    public PreprocessContext applyStateChanges(RuleFunction func, long id, String parentPropertyName, long parentId, String parentExtId, int parentVersion,
                                               int parentTypeId, Class entityClz, int[] dirtyBits) {

        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                PreprocessContext preContext = null;
                if (func != null) {
                    preContext = PreprocessContext.newContext(this);
                    try {
                        Object[] args = new Object[5];

                        args[0] = parentId;
                        args[1] = parentExtId;
                        args[2] = parentTypeId;
                        args[3] = parentVersion;
                        args[4] = false;

                        Object ret = func.invoke(args);
                        if (ret instanceof Boolean) {
                            if (!((Boolean) ret).booleanValue()) {
                                return preContext;
                            }
                        }
                    }
                    catch (Exception ex) {
                        this.logger.log(Level.ERROR, ex,
                                "%s got exception [%s] while executing preprocessor [%s] for id [%s,class=%s]",
                                getName(), ex, func.getSignature(), id, entityClz);
                    }
                }
                if ((preContext != null) && preContext.hasWork()) {
                    List asserted = preContext.getAsserted();
                    LinkedHashSet deleted = preContext.getDeleted();
                    List reloaded = preContext.getReloaded();
                    List reevaluate = preContext.getReevaluate();
                    PreprocessContext.cleanContext();
                    boolean reloadOnly = preContext.getClusterSubscriptionReloadOnly();
                    //((ReteWM)workingMemory).executeRules("Preprocessor: "+ func.getSignature() , reloaded, asserted,deleted);
                    ((ReteWM) workingMemory).applyElementChanges("Preprocessor: " + func.getSignature(), id, entityClz, dirtyBits, reloaded, asserted, deleted, reevaluate, reloadOnly);
                } else {
                    ((ReteWM) workingMemory).applyElementChanges("No-Preprocessor: ", id, entityClz, dirtyBits, null, null, null, null, false);
                }
                return preContext;
            }
            finally {
                releaseLocks();
                sessManager.setCurrentRuleSession(null);
                PreprocessContext.cleanContext();
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    public PreprocessContext applyDelete(RuleFunction func, long id, String extId, int typeId, Class entityClz, int version) {

        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                PreprocessContext preContext = null;
                if (func != null) {
                    preContext = PreprocessContext.newContext(this);
                    try {
                        Object[] args = new Object[5];

                        args[0] = id;
                        args[1] = extId;
                        args[2] = typeId;
                        args[3] = version;
                        args[4] = true;
                        func.invoke(args);
                    }
                    catch (Exception ex) {
                        this.logger.log(Level.ERROR, ex,
                                "%s got exception [%s] while executing preprocessor [%s] for id [%s,class=%s]",
                                getName(), ex, func.getSignature(), id, entityClz);
                    }
                }
                if ((preContext != null) && preContext.hasWork()) {
                    List asserted = preContext.getAsserted();
                    LinkedHashSet deleted = preContext.getDeleted();
                    List reloaded = preContext.getReloaded();
                    List reevaluate = preContext.getReevaluate();
                    PreprocessContext.cleanContext();
                    //((ReteWM)workingMemory).executeRules("Preprocessor: "+ func.getSignature() , reloaded, asserted,deleted);
                    ((ReteWM) workingMemory).applyDelete("Preprocessor: " + func.getSignature(), id, entityClz, reloaded, asserted, deleted, reevaluate);
                } else {
                    ((ReteWM) workingMemory).applyDelete("No-Preprocessor: ", id, entityClz, null, null, null, null);
                }
                return preContext;
            }
            finally {
                releaseLocks();
                sessManager.setCurrentRuleSession(null);
                PreprocessContext.cleanContext();
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    public PreprocessContext preprocessPassthru(final RuleFunction func, final SimpleEvent event) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            final PreprocessContext[] array = new PreprocessContext[1];

            new BeTransaction("preprocess-pass-thru"){
                @Override
                protected void doTxnWork() {
                    sessManager.setCurrentRuleSession(RuleSessionImpl.this);
                    try {
                        PreprocessContext preContext = PreprocessContext.newContext(RuleSessionImpl.this);
                        try {
                            preContext.setTrigger(event);
                            func.invoke(new Object[]{event});
                        }
                        catch (Exception ex) {
                            logger.log(Level.ERROR, ex,
                                    "%s got exception [%s] while executing preprocessor [%s] for event [%s]",
                                    getName(), ex, func.getSignature(), event);
                        }
                        if (preContext.hasWork()) {
                            List asserted = preContext.getAsserted();
                            LinkedHashSet deleted = preContext.getDeleted();
                            List reloaded = preContext.getReloaded();
                            List reevaluate = preContext.getReevaluate();
                            PreprocessContext.cleanContext();
                            workingMemory.getRtcOpList().setTrigger(event);
                            ((ReteWM) workingMemory).executeRules("Preprocessor: " + func.getSignature(), reloaded, asserted, deleted, reevaluate);
                        }
                        array[0] = preContext;
                    }
                    finally {
                        releaseLocks();
                        sessManager.setCurrentRuleSession(null);
                        PreprocessContext.cleanContext();
                    }
                }
            }.execute();

            return array[0];
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    /*
     public void preprocessPassthru(String functionURI, Event event) throws DuplicateExtIdException {
         RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
         if(threadSession == this) { //already inside
             throw new RuntimeException("Can't invokeFunction inside the session itself");
         }
         else if(threadSession == null) {
             sessManager.setCurrentRuleSession(this);
             try {
                 PreprocessContext preContext = PreprocessContext.newContext(this);
                 preContext.add(event);
                 getRuleFunction(functionURI).invoke(new Object[] { event });
                 if(preContext.hasWork())
                     ((ReteWM)workingMemory).executeRules(preContext.getAsserted(), preContext.getDeleted());
             }
             finally {
                 sessManager.setCurrentRuleSession(null);
                 PreprocessContext.cleanContext();
             }
         }
         else
             throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
     }
    */

    //PreprocessContext has to be set before, and clean after
    /*
    public void preprocessPreSchedule(String functionURI, Event event) throws DuplicateExtIdException {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if(threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        }
        else if(threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                PreprocessContext preContext = PreprocessContext.getContext();
                preContext.add(event);
                getRuleFunction(functionURI).invoke(new Object[] { event });
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        }
        else
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
    }*/

    public void preprocessPreSchedule(RuleFunction func, SimpleEvent event) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                PreprocessContext preContext = PreprocessContext.getContext();
                preContext.setTrigger(event);
                func.invoke(new Object[]{event});
            }
            catch (Exception ex) {
                this.logger.log(Level.ERROR, ex,
                        "%s got exception [%s] while executing preprocessor [%s] for event [%s]",
                        getName(), ex, func.getSignature(), event);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    public void preprocessPostSchedule(PreprocessContext preprocessContext) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                if (preprocessContext.hasWork())
                    ((ReteWM) workingMemory).executeRules("preprocessPostSchedule", preprocessContext.getReloaded(), preprocessContext.getAsserted(), preprocessContext.getDeleted(), preprocessContext.getReevaluate());  //todo - use another context
            }
            catch (Exception ex) {
                this.logger.log(Level.ERROR, ex,
                        "%s got exception [%s] while executing preprocessContext [%s]",
                        getName(), ex, preprocessContext);
            }
            finally {
                releaseLocks();
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't invokeFunction in session " + this.getName());
        }
    }

    /**
     * @param key
     * @param timeout
     * @param localOnly
     * @return
     */
    public boolean lock(String key, long timeout, boolean localOnly) {
        LockManager.LockLevel lockLevel =
                (localOnly) ? LockManager.LockLevel.LEVEL1 : LockManager.LockLevel.LEVEL2;

        return lockMgr.lock(key, timeout, lockLevel);
    }

    /**
     * To be called by BE user function Cluster.DataGrid.UnLock 
     * If lock is called twice on the same key, this must also be called twice to release the lock
     */
    public void unlock(String key, boolean localOnly) {
        lockMgr.explicitUnlock(key, localOnly);
    }

    /*
    protected void executeRules(LinkedHashMap created, LinkedHashSet deleted) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if(threadSession == this) {
            ((ReteWM)workingMemory).executeRules(created, deleted);
        }
        else if(threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                ((ReteWM)workingMemory).executeRules(created, deleted);
            } finally {
                sessManager.setCurrentRuleSession(null);
            }
        }
        else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }
    */

    protected void executeRules(List assertObjects, List deleteObjects) throws DuplicateExtIdException {
        //todo - no longer supported
        throw new RuntimeException("Not supported");
    }

    public void executeRules() {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            workingMemory.executeRules();
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.executeRules();
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }

    public List getObjects() {
        return workingMemory.getObjectManager().getObjects();
    }

    public List getObjects(Filter filter) {
        return workingMemory.getObjectManager().getObjects(filter);
    }


    //    public void retrieveObject(Object object) {
    //        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
    //        if(threadSession == this) {
    //            throw new RuntimeException("Session " + threadSession.getName() + " can't retrieve object " + object + " to itself");
    //        }
    //        else if(threadSession == null) {
    //            sessManager.setCurrentRuleSession(this);
    //            try {
    //                workingMemory.retrieveObject(object);
    //            }
    //            finally {
    //                sessManager.setCurrentRuleSession(null);
    //            }
    //        }
    //        else {
    //            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
    //        }
    //    }

    public void reevaluateElements(long[] ids) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            String idsStr = "";
            for (int i = 0; i < ids.length; i++) {
                if (i == 0) idsStr += ids[i];
                else idsStr = idsStr + ", " + ids[i];
            }
            throw new RuntimeException("Session " + threadSession.getName() + " can't reevaluateElements " + idsStr + " to itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.reevaluateElements(ids);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }

    public void reevaluateElements(Collection IDs) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            throw new RuntimeException("Session " + threadSession.getName() + " can't reevaluateElements " + IDs + " to itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.reevaluateElements(IDs);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }

    public void reevaluateEvents(Collection IDs) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            throw new RuntimeException("Session " + threadSession.getName() + " can't reevaluateEvents " + IDs + " to itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.reevaluateEvents(IDs);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }

    public void reloadFromCache(Object obj) {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext != null) {
            //add retrieve from cache
            preContext.reload(obj);
            return;
        }
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            ((ReteWM) workingMemory).reloadFromCache(obj);
        } else if (threadSession == null) {
            throw new RuntimeException("RuleSessionImpl.reloadFromCache(Object) has to be called inside RuleSession or Preprocessor");
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }

    public void assertObject(Object object, boolean executeRules) throws DuplicateExtIdException {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext != null) {
            preContext.add(object);
            recordPreprocOp(object, ReteListener.RTC_OBJECT_ASSERTED);
            return;
        }

        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            workingMemory.assertObject(object, executeRules);
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
            	getRtcOperationList().setTrigger(object);
                workingMemory.assertObject(object, executeRules);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }


    /*
     public void modifyObject(Object modifiedObject, boolean executeRules) {
         RuleSession threadSession = sessManager.getCurrentRuleSession();
         if(threadSession == this) {
             if (executeRules)
                 workingMemory.modifyObject(modifiedObject);
             else
                 workingMemory.updateObject(modifiedObject);  //todo - fix this
         }
         else if(threadSession == null) {
             sessManager.setCurrentRuleSession(this);
             try {
                 if (executeRules)
                     workingMemory.modifyObject(modifiedObject);
                 else
                     workingMemory.updateObject(modifiedObject);  //todo - fix this
             } finally {
                 sessManager.setCurrentRuleSession(null);
             }
         }
         else {
             throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local MutableChannel to communicate between sessions");
         }
     }
     */

    public void cleanupEventHandle(long id) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        Handle handle = objectManager.getEventHandle(id);
        if (handle == null) return;

        if (threadSession == this) {
            ((ReteWM) workingMemory).cleanupHandle(handle);
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                ((ReteWM) workingMemory).cleanupHandle(handle);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("this method is not callable from here");
        }
    }

    public void cleanupElementHandle(long id) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        Handle handle = objectManager.getElementHandle(id);
        if (handle == null) return;

        if (threadSession == this) {
            ((ReteWM) workingMemory).cleanupHandle(handle);
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                ((ReteWM) workingMemory).cleanupHandle(handle);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("this method is not callable from here");
        }
    }

    public void retractObject(Object object, boolean executeRules) {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext != null) {
            preContext.delete(object);
            recordPreprocOp(object, ReteListener.RTC_OBJECT_DELETED);
            return;
        }

        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            workingMemory.retractObject(object, executeRules);
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.retractObject(object, executeRules);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
        }
    }

    private void recordPreprocOp(Object object, int op) {
    	Collection<ChangeListener> changeListeners = ((WorkingMemoryImpl)workingMemory).getChangeListeners();
    	if (changeListeners != null && changeListeners.size() > 0) {
    		for (ChangeListener changeListener : changeListeners) {
    			switch (op) {
				case ReteListener.RTC_OBJECT_DELETED:
					changeListener.retracted(object, null);
					break;
					
				case ReteListener.RTC_OBJECT_MODIFIED:
					changeListener.modified(object, null);
					break;
					
				case ReteListener.RTC_OBJECT_ASSERTED:
					changeListener.asserted(object, null);
					break;

				default:
					break;
				}
    		}
    	}
    }

//    public void assertFromAgent(List modifyElements, List dirtyBits, List reloaded, List deleted) throws DuplicateExtIdException {
//        PreprocessContext preContext = PreprocessContext.getContext();
//        if (preContext != null) {
//            throw new RuntimeException("Can't call RuleSessionImpl.modifyElement(Element, int[]) from preprocessor");
//        }
//
//        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
//        if (threadSession == this) {
//            throw new RuntimeException("Can't call RuleSessionImpl.modifyElement(Element, int[]) inside the session itself");
//        } else if (threadSession == null) {
//            sessManager.setCurrentRuleSession(this);
//            try {
//                //                int end= reloaded.size();
//                //                for (int i=0; i < end; i++) {
//                //                    ConceptImpl obj= (ConceptImpl) reloaded.get(i);
//                //                    if (((ConceptImpl) obj).getParent() != null) {
//                //                        if (((ConceptImpl) obj).getParent() != null) {
//                //                            reloaded.add(((ConceptImpl) obj).getParent());
//                //                        }
//                //                    }
//                //                }
//                //
//                //                for (int i=0; i < modifyElements.size(); i++) {
//                //                    ConceptImpl obj= (ConceptImpl) modifyElements.get(i);
//                //                    if (((ConceptImpl) obj).getParent() != null) {
//                //                        reloaded.add(((ConceptImpl) obj).getParent());
//                //                    }
//                //                }
//
//                workingMemory.executeRules("Remote-Agent", modifyElements, dirtyBits, deleted, reloaded, null);
//            }
//            finally {
//                sessManager.setCurrentRuleSession(null);
//            }
//
//        } else {
//            throw new RuntimeException("Session " + threadSession.getName() + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");
//        }
//    }

    public void modifyElement(Element element, int[] dirtyBitArray, boolean recordThisModication) throws DuplicateExtIdException {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext != null) {
            throw new RuntimeException("Can't call RuleSessionImpl.modifyElement(Element, int[]) from preprocessor");
        }

        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.modifyElement(element, dirtyBitArray, recordThisModication);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }

            //            throw new RuntimeException("Can't call RuleSessionImpl.modifyElement(Element, int[]) inside the session itself");
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.modifyElement(element, dirtyBitArray, recordThisModication);
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Can't call RuleSessionImpl.modifyElement(Element, int[]) from another Session");
        }
    }

    public void reset() {
        sessManager.setCurrentRuleSession(this);
        workingMemory.reset();
    }

    public RuleRuntime getRuleRuntime() {
        return sessManager;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return sessManager.getRuleServiceProvider();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

//    public Handle loadEvictedHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException {
//        if (Event.class.isAssignableFrom(type))
//            throw new RuntimeException("Haven't implemented Event for loadEvictedHandle");
//        //event need schedule and expiry...
//        return workingMemory.loadEvictedHandle(id, extId, type);
//    }

    public Handle loadObject(Object obj) throws DuplicateExtIdException, DuplicateException {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == this) {
            if (obj instanceof TimeEvent) {
                TimeEvent te = (TimeEvent) obj;
                long delay = te.getScheduledTimeMillis() - System.currentTimeMillis();
                if (delay <= 0) {
                    if (te.isFired()) {
                        return workingMemory.loadObject(te);
                    } else {
                        return workingMemory.loadScheduleEvent(te, 0);  //was loadObject, fire the timer event again
                    }
                } else {
                    return workingMemory.loadScheduleEvent(te, delay);
                }
            } else {
                return workingMemory.loadObject(obj);
            }
        } else if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                if (obj instanceof TimeEvent) {
                    TimeEvent te = (TimeEvent) obj;
                    long delay = te.getScheduledTimeMillis() - System.currentTimeMillis();
                    if (delay <= 0) {
                        if (te.isFired()) {
                            return workingMemory.loadObject(te);
                        } else {
                            return workingMemory.loadScheduleEvent(te, 0);  //was loadObject, fire the timer event again
                        }
                    } else {
                        return workingMemory.loadScheduleEvent(te, delay);
                    }
                } else {
                    return workingMemory.loadObject(obj);
                }
            } finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't load object in another session " + this.getName());
        }
    }

    public Handle reloadObject(Object obj) throws DuplicateExtIdException, DuplicateException {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                return workingMemory.reloadObject(obj);
            } finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't reload object in another session " + this.getName());
        }
    }

//    public boolean unloadObject(Object obj) {
//        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
//        if (threadSession == null) {
//            sessManager.setCurrentRuleSession(this);
//            try {
//                return workingMemory.unloadObject(obj);
//            } finally {
//                sessManager.setCurrentRuleSession(null);
//            }
//        } else {
//            throw new RuntimeException("Session " + threadSession.getName() + " can't unload object in another session " + this.getName());
//        }
//    }

//    public boolean unloadInstance(long id) {
//        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
//        if (threadSession == null) {
//            sessManager.setCurrentRuleSession(this);
//            try {
//                return workingMemory.unloadElement(id);
//            } finally {
//                sessManager.setCurrentRuleSession(null);
//            }
//        } else {
//            throw new RuntimeException("Session " + threadSession.getName() + " can't unload object in another session " + this.getName());
//        }
//    }

//    public boolean unloadEvent(long id) {
//        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
//        if (threadSession == null) {
//            sessManager.setCurrentRuleSession(this);
//            try {
//                return workingMemory.unloadEvent(id);
//            } finally {
//                sessManager.setCurrentRuleSession(null);
//            }
//        } else {
//            throw new RuntimeException("Session " + threadSession.getName() + " can't unload object in another session " + this.getName());
//        }
//    }

    public void loadObjectToAddedRule() {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.loadObjectToAddedRule();
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't call loadObjectToAddedRule in another session " + this.getName());
        }
    }

    public void setSession() {
        sessManager.setCurrentRuleSession(this);
    }

    public void fireRepeatEvent(final TimeEvent timeEvent, final int numPerInterval) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();

        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                new BeTransaction("fire-repeat-event-" + timeEvent.getId() + "-" + numPerInterval) {
                    @Override
                    protected void doTxnWork() {
                        try {
                            workingMemory.fireRepeatEvent(timeEvent, numPerInterval);
                        }
                        catch (DuplicateExtIdException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            } finally {
                sessManager.setCurrentRuleSession(null);
            }
        }
    }

    public void expireEvent(AbstractEventHandle handle) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();

        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                if (!handle.isTimerCancelled() && !handle.isRetracted()) {
                    ((WorkingMemoryImpl) workingMemory).expireEvent(handle);
                    handle.cancelTimer();
                }
            } finally {
                sessManager.setCurrentRuleSession(null);
            }
        }
    }

    public void resetSession() {
        sessManager.setCurrentRuleSession(null);
    }

    public void applyObjectToAddedRules() {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                workingMemory.applyObjectToAddedRules();
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't call applyObjectToAddedRules in another session " + this.getName());
        }
    }

    public TimeManager getTimeManager() {
        return workingMemory.getTimeManager();
    }

    /**
     * @return a collection of Classes with all the scope that this rulesession has.
     */
    public Collection<Class> getAllScopeTypes() {
        HashSet<Class> classSet = new HashSet<Class>();
        Set ruleSets = workingMemory.getRuleLoader().getDeployedRules();
        Iterator itr = ruleSets.iterator();
        while (itr.hasNext()) {
            final Rule rule = (Rule) itr.next();
            this.getScopeTypesForRule(classSet, rule);
        }
        return classSet;
    }

//     /**
//      * Get the list of all scope and their type for this RuleSet.
//      * @param classSet
//      * @param ruleSet
//      */
//     public void getScopeTypesForRuleSet(HashSet<Class> classSet, RuleSet ruleSet) {
//         Iterator ite = ruleSet.rules().iterator();
//         while (ite.hasNext()) {
//             Rule rule = (Rule) ite.next();
//             getScopeTypesForRule(classSet, rule);
//         }
//     }

    public SequenceManager getSequenceManager(boolean localOnly) {
        if (localOnly) {
            return localSequenceManager;
        } else {
            if (objectManager instanceof ObjectBasedStore) {
                return ((ObjectBasedStore) objectManager).getSequenceManager();
            }
            throw new RuntimeException("No SequenceManager found for localOnly=false. The flag is true only for cache based store");
        }
    }

    /**
     * Gets the list of the Scope Types that the Rule has defined.
     * Pass a initialized HashSet<Class> and the method will fill in.
     *
     * @param classSet -A initialized HashSet<Class>
     * @param rule     - The Rule {@link com.tibco.cep.kernel.model.rule.Rule} for which you want the scope type
     */
    public void getScopeTypesForRule(HashSet<Class> classSet, Rule rule) {
        Identifier[] ids = rule.getIdentifiers();
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                Identifier id = ids[i];
                Class clazz = id.getType();
                if (Entity.class.isAssignableFrom(clazz)) {
                    classSet.add(clazz);
                    addDerivedClasses(classSet, clazz);
                }
            }
        }
    }

    //Highly inefficient - NxN times. Wish we build the Class Hierarchy.

    private void addDerivedClasses(HashSet<Class> classSet, Class parentClass) {
        BEClassLoader loader = (BEClassLoader) getRuleServiceProvider().getTypeManager();
        Collection classes = loader.getClasses();
        Iterator itr = classes.iterator();
        while (itr.hasNext()) {
            Class clazz = (Class) itr.next();
            if (parentClass.isAssignableFrom(clazz)) {
                classSet.add(clazz);
            }
        }
    }

    private void initializeRecorder() {
//        if (((WorkingMemoryImpl) (this).getWorkingMemory()).getChangeListener() != null)
//            return;
        BEProperties beProps = ((BEProperties) sessManager.getRuleServiceProvider().getProperties());
        String recorderDir = beProps.getString("be.engine.recorder.file.dir", null);
        if (recorderDir != null && recorderDir.trim().length() > 0) {
            String mode = beProps.getString("be.engine.recorder.file.mode", "cudsraxfe");
            FileBasedRecorder.start(this, recorderDir, mode);

            String filter = beProps.getString("be.engine.recorder.file.filter", null);
            if (filter != null && filter.trim().length() > 0) {
                FileBasedRecorder fr = null;
                Collection<ChangeListener> changeListeners = ((WorkingMemoryImpl) this.getWorkingMemory()).getChangeListeners();
                for (ChangeListener changeListener : changeListeners) {
                    if (changeListener instanceof FileBasedRecorder) {
                        fr = (FileBasedRecorder)changeListener;
                        break;
                    }
                }
                fr.resetFilter();
                FileBasedRecorder.Filter f = new FileBasedRecorder.Filter(filter);
                fr.addFilter(f);
                logger.log(Level.INFO, "File Recorder is enabled for rule session %s, dir = %s, mode = %s, filter = %s", name, recorderDir,  mode, filter);
            } else {
                logger.log(Level.INFO, "File Recorder is enabled for rule session %s, dir = %s, mode = %s", name, recorderDir,  mode);
            }
        }
        
        String inmemRecorder = beProps.getString("be.engine.inmemory.statistic.enable", "false");
        if (Boolean.parseBoolean(inmemRecorder)) {
        	String mode = beProps.getString("be.engine.inmemory.statistic.mode", "c");//c:Asserted;
        	InmemoryRecorder.startRecorder(this, mode);
        	logger.log(Level.INFO, "InMemory Recorder is enabled for rule session %s, mode = %s", name, mode);
        }
    }

    //Initialize Profiler
    private void initializeProfiler() {
        boolean profilingEnabled = false;
        BEProperties beProps = ((BEProperties) sessManager.getRuleServiceProvider().getProperties());
        String propSessionBranch = "be.engine.profile." + getName();
        if (((beProps.getProperty("be.engine.profile.*.enable") != null) && (beProps.getProperty("be.engine.profile.*.enable").equalsIgnoreCase("true")))
                || ((beProps.getProperty(propSessionBranch + ".enable") != null) && (beProps.getProperty(propSessionBranch + ".enable").equalsIgnoreCase("true")))) {
            profilingEnabled = true;
        }

        if (profilingEnabled) {
            String profilingName = null;
            if (((profilingName = beProps.getString(propSessionBranch + ".file")) == null)
                    || (profilingName.trim().length() == 0)) {
                String sessionName = this.name.trim().replace(' ', '_');
                if (((profilingName = beProps.getString("be.engine.profile.*.file")) != null)
                        && (profilingName.trim().length() != 0)) {
                    int index = profilingName.lastIndexOf('.');
                    if (index != -1) {
                        profilingName = new StringBuilder(profilingName.substring(0, index)).append('_').append(sessionName).append(profilingName.substring(index)).toString();
                    } else {
                        profilingName = new StringBuilder(profilingName).append('_').append(sessionName).toString();
                    }
                } else {
                    profilingName = new StringBuilder(CSVWriter.DEFAULT_FILE_PREFIX).append('_').append(sessionName).append(".csv").toString();
                }
            }

            int level = -1;
            if ((beProps.getString(propSessionBranch + ".level") != null)
                    && (beProps.getString(propSessionBranch + ".level").trim().length() != 0)) {
                level = beProps.getInt(propSessionBranch + ".level", -1);
            } else if ((beProps.getString("be.engine.profile.*.level") != null)
                    && (beProps.getString("be.engine.profile.*.level").trim().length() != 0)) {
                level = beProps.getInt("be.engine.profile.*.level", -1);
            }

            long duration = -1L;
            if ((beProps.getString(propSessionBranch + ".duration") != null)
                    && (beProps.getString(propSessionBranch + ".duration").trim().length() != 0)) {
                duration = beProps.getLong(propSessionBranch + ".duration", -1L);
            } else if ((beProps.getString("be.engine.profile.*.duration") != null)
                    && (beProps.getString("be.engine.profile.*.duration").trim().length() != 0)) {
                duration = beProps.getLong("be.engine.profile.*.duration", -1L);
            }

            SimpleProfiler profiler = (SimpleProfiler) ((ReteWM) workingMemory).getReteListener(SimpleProfiler.class);
            if (profiler == null) {
                String delim = beProps.getProperty("be.engine.profile.delimiter");
                profiler = new SimpleProfiler(profilingName, level, duration, 0, delim, logger);
                ((ReteWM) workingMemory).addReteListener(profiler);
            }
            profiler.on();
            logger.log(Level.INFO, "Profiling is enabled for rule session " + name);
        }
    }

    public void registerSMTimeoutCallback(String conceptURI, String ruleFunctionURI) {
        TypeManager.TypeDescriptor td = getRuleServiceProvider().getTypeManager().getTypeDescriptor(conceptURI);
        if (td != null) {
            Class entityClz = td.getImplClass();
            if (ConceptImpl.class.isAssignableFrom(entityClz)) {
                RuleFunction rf = getRuleFunction(ruleFunctionURI);
                if (rf != null) {
                	try {
                		smTimeoutCallBacksLock.writeLock().lock();
                		if(smTimeoutCallbacks == null) smTimeoutCallbacks = new HashMap();
                		smTimeoutCallbacks.put(entityClz, rf);
                	} finally {
                		smTimeoutCallBacksLock.writeLock().unlock();
                	}
                } else {
                    throw new RuntimeException("registerSMTimeoutCallback(): RuleFunction " + ruleFunctionURI + " not found");
                }
            } else {
                throw new RuntimeException("registerSMTimeoutCallback(): URI " + conceptURI + " should be a concept");
            }
        } else {
            throw new RuntimeException("registerSMTimeoutCallback(): URI " + conceptURI + " not found in the project");
        }
    }

    public RuleFunction getSMTimeoutCallback(Class cls) {
    	if(smTimeoutCallbacks == null) return null;
    	try {
    		smTimeoutCallBacksLock.readLock().lock();
    		return smTimeoutCallbacks.get(cls);
    	} finally {
    		smTimeoutCallBacksLock.readLock().unlock();
    	}
    }

    protected static class SetSessionContext implements Action {
        RuleSession m_session;

        public SetSessionContext(RuleSession session) {  //todo - chg to protected
            m_session = session;
        }

        public Identifier[] getIdentifiers() {
            return null;
        }

        public void execute(Object[] objects) {
            ((RuleSessionImpl) m_session).sessManager.setCurrentRuleSession(m_session);
        }

        public Rule getRule() {
            return null;
        }
    }

    public static class ActivateAction implements Action {

        public Identifier[] getIdentifiers() {
            return null;
        }

        public void execute(Object[] objects) {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class);
            String msg = "Engine " + session.getRuleServiceProvider().getName() + " activated";
            AdvisoryEvent advEvent = new AdvisoryEventImpl(id, null, AdvisoryEvent.CATEGORY_ENGINE, AdvisoryEventDictionary.ENGINE_PrimaryActivated, msg);
            try {
                session.assertObject(advEvent, true);
            } catch (DuplicateExtIdException e) {
            }//impossible
        }

        public Rule getRule() {
            return null;
        }
    }

    public static class InputDestinationConfigImpl
            implements RuleSessionConfig.InputDestinationConfig
    {
        String destURI;
        Event filterEvent;
        RuleFunction preProcessor;
        RuleFunction threadAffinityRuleFunction;
        int numWorker;
        int queueSize;
        int weight;

        RuleSessionConfig.ThreadingModel threadingModel;

        public InputDestinationConfigImpl(String destURI,
                                          Event filterEvent,
                                          RuleFunction preProcessor,
                                          RuleFunction threadAffinityRuleFunction,
                                          boolean enable,
                                          RuleSessionConfig.ThreadingModel threadingModel,
                                          int numWorker,
                                          int queueSize,
                                          int weight) {
            this.destURI = destURI;
            this.filterEvent = filterEvent;
            this.preProcessor = preProcessor;
            this.threadingModel = threadingModel;
            this.threadAffinityRuleFunction = threadAffinityRuleFunction;
            this.numWorker = numWorker;
            this.queueSize = queueSize;
            this.weight = weight;
            if (null == this.threadingModel) {
                this.threadingModel = RuleSessionConfig.ThreadingModel.SHARED_QUEUE;
            }
        }
        
		public InputDestinationConfigImpl(String destURI, Event filterEvent,
				RuleFunction preProcessor, boolean enable,
				RuleSessionConfig.ThreadingModel threadingModel, int numWorker,
				int queueSize, int weight) {
			this(destURI, filterEvent, preProcessor, null, enable, threadingModel, numWorker, queueSize, weight);
		}
        
        

        public String getURI() {
            return destURI;
        }

        public Event getFilter() {
            return filterEvent;
        }

        public RuleFunction getPreprocessor() {
            return preProcessor;
        }

        public int getNumWorker() {
            switch (this.threadingModel) {
//                case CALLER:
//                    return -1;
                case SHARED_QUEUE:
                    return 0;
                default:
                    return this.numWorker;
            }
        }

        public int getQueueSize() {
            return queueSize;
        }

        public int getWeight() {
            return weight;
        }

        /**
         * @return
         */
        public RuleSessionConfig.ThreadingModel getThreadingModel() {
            return threadingModel;
        }

		public RuleFunction getThreadAffinityRuleFunction() {
			return threadAffinityRuleFunction;
		}
    }

    @Override
    public void registerType(Class type) {
        registerType(this.workingMemory, type);
    }

    public void registerType(WorkingMemory workingMemory, Class type) {
        TypeInfo typeInfo = workingMemory.getTypeInfo(type);
         if (typeInfo == null) {
            ((WorkingMemory)(workingMemory)).registerType(type);
         }
    }

    @Override
    public RtcOperationList getRtcOperationList() {
        return workingMemory.getRtcOpList();
    }

    @Override
    public String getLogComponent() {
        return getName();
    }


    @Override
    public ProcessingContextProvider getProcessingContextProvider() {
        return this.processingContextProvider;
    }

}
