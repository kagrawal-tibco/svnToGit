package com.tibco.cep.bpmn.runtime.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.bpmn.runtime.activity.Activity;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TriggerIdentifier;
import com.tibco.cep.bpmn.runtime.activity.TriggerType;
import com.tibco.cep.bpmn.runtime.activity.Triggerable;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplateRegistry;
import com.tibco.cep.bpmn.runtime.utils.FQName;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.cep.runtime.scheduler.TaskControllerFactory;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionConfigImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionMetricsImpl;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/*
* Author: Suresh Subramani / Date: 11/26/11 / Time: 10:36 AM
* A Rulession that is available for all ProcessInstances.
* RuleSessionManagerImpl - dynamically constructs an instanceof this class.
*/
public class ProcessRuleSession extends RuleSessionImpl  {


    enum AssertOrRetractType {

        ASSERT_FROM_STARTUP, //Advisory events

        ASSERT_FROM_CHANNEL, //Messages, and Timer

        ASSERT_FROM_GENERICTASKCONTEXT, //From Script, Decisiontable task, assert of a concept or modify of a concept

        ASSERT_FROM_RETETASKCONTEXT, //Assert from a Rete task - InstanceHelper, EventHelper createInstance, assertEvent will call this.

        RETRACT_FROM_STARTUP,

        RETRACT_FROM_GENERICTASKCONTEXT,

        RETRACT_FROM_RETETASKCONTEXT,

        NONE;

    }


    static Logger logger = LogManagerFactory.getLogManager().getLogger(ProcessRuleSession.class);
    ProcessAgent processAgent;
    Map<Class<? extends Entity>, List<Triggerable>> starterOrWaiterRules;
    private RetePool retePool;
    //coherence store uses RuleSession.getWorkingMemory() to get the class node for a handle
    //need thread local sessions so that inference tasks can have thread local class nodes
    protected ThreadLocal<WorkingMemory> threadLocalWMs = new ThreadLocal();
	private boolean registeredTypes = false;
    private static AtomicInteger ruleSessionId = new AtomicInteger(0);

    /**
     * This constructor is called from RuleSessionManagerImpl.
     * @param barResource
     * @param beProperties
     * @param manager
     * @param objectManager
     * @throws Exception
     */

    public ProcessRuleSession(BEArchiveResource barResource, BEProperties beProperties, RuleSessionManager manager, ObjectManager objectManager) throws Exception {
        this(barResource, beProperties, manager, ruleSessionId.getAndIncrement(), objectManager);
    }

    public ProcessRuleSession(BEArchiveResource barResource, BEProperties beProperties, RuleSessionManager manager, int instanceId, ObjectManager objectManager) throws Exception {

        super();

        //next three lines cut and pasted from 
        //protected RuleSessionImpl(LockManager lockMgr)

        this.config         = new RuleSessionConfigImpl(barResource, manager.getRuleServiceProvider());

        this.instanceId     = instanceId;

        this.name = String.format("%s%s", config.getName(), instanceId <= 0 ? "" : String.format(".%d", instanceId));

        this.configuration = new Configuration(name, new Properties());

        this.ruleSessionMetrics = new RuleSessionMetricsImpl(this);

        this.sessManager = (RuleSessionManagerImpl) manager;

        this.objectManager  = (BaseObjectManager) objectManager;


        retePool = RetePool.createPool(16, name, objectManager);


        this.workingMemory = (WorkingMemoryImpl) retePool.getFreeRete();

        this.controller = TaskControllerFactory.createTaskController(this, beProperties);

        this.starterOrWaiterRules = new HashMap<Class<? extends Entity>, List<Triggerable>>();

    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public RuleSessionConfig getConfig() {
        return config;
    }



    @Override
    public void executeRules() {
        return;
    }

    public RetePool getRetePool() {
        return this.retePool;
    }


    public void init(ProcessAgent processAgent) throws Exception {

        this.processAgent = processAgent;
        registerTypes();

        this.workingMemory.init(this.startup, new ActivateAction(), new HashSet<Rule>());
        workingMemory.initEntitySharingLevels();

    }

    /**
     * Register the ProcessVarible type, and also all the template classes.
     */

    public void registerTypes() {

        super.registerTypes(this.workingMemory);
        Iterator<WorkingMemory> iterator = retePool.queue.iterator();
        while (iterator.hasNext()) {
            WorkingMemory wm = iterator.next();
            super.registerTypes(wm);
        }

        if(!registeredTypes) {
	        registerType(JobContext.class);
	        registerType(LoopTuple.class);
	        registerType(MergeTuple.class);
	        //((BEClassLoader)getRuleServiceProvider().getClassLoader()).registerClass(LoopTuple.class);
	        //((BEClassLoader)getRuleServiceProvider().getClassLoader()).registerClass(MergeTuple.class);
	        //getRuleServiceProvider().getCluster().getMetadataCache().registerType(LoopTuple.class);
	        //getRuleServiceProvider().getCluster().getMetadataCache().registerType(MergeTuple.class);
	        registeredTypes = true;
        }
    }

    public void registerType(Class cls) {
        super.registerType(this.workingMemory, cls);
        Iterator<WorkingMemory> iterator = retePool.queue.iterator();
        while (iterator.hasNext()) {
            WorkingMemory wm = iterator.next();
            super.registerType(wm, cls);
        }
    }

    @Override
    public void start(boolean active) throws Exception {

        loadStarterOrWaiterTasks();
        super.start(active);
        if (active)
            invokeStartupProcesses();


    }
    
	public void invokeProcess(Job job, boolean isAsync) {
		// execute startup processes
		if (isAsync) {
			sessManager.setCurrentRuleSession(this);
			try {

				this.getProcessAgent().getProcessExecutor().submitJob(job);

			} finally {
				sessManager.setCurrentRuleSession(null);

			}
		} else {
			if(sessManager.getCurrentRuleSession() != null) {
				job.run();
			}
			
		}
	}

    private void invokeStartupProcesses() throws Exception{
        // execute startup processes
        sessManager.setCurrentRuleSession(this);
        try {
            for(String s: processAgent.getProcessAgentConfiguration().getStartupProcesses()){

                final Ontology o = getRuleServiceProvider().getProject().getOntology();
                final ProcessModel processModel = getCalledProcessByUri(o, s);
                final ProcessAgent pac = processAgent;

                ProcessTemplate template = ProcessTemplateRegistry.getInstance().getProcessTemplate(processModel);
                JobContext calleeVars = template.newProcessData();

                Task task = getStartTask(processModel);

                if (task != null) {
                    Variables nvars = new Variables();
                    JobImpl job = new JobImpl(calleeVars, pac);
                    task.getMapper().outputTransform(job, nvars);
                    job.setCurrentTask(task);
                    job.setLastTask(task);
                    job.run();
                }

            }
        }
        finally {
            sessManager.setCurrentRuleSession(null);
        }

    }


    private void invokeshutdownProcesses() throws Exception {

        sessManager.setCurrentRuleSession(this);
        try {
            for(String s: processAgent.getProcessAgentConfiguration().getShutdownProcesses()){

                final Ontology o = getRuleServiceProvider().getProject().getOntology();
                final ProcessModel processModel = getCalledProcessByUri(o, s);
                final ProcessAgent pac = processAgent;

                ProcessTemplate template = ProcessTemplateRegistry.getInstance().getProcessTemplate(processModel);
                JobContext calleeVars = template.newProcessData();

                Task task = getStartTask(processModel);

                if (task != null) {
                    Variables nvars = new Variables();
                    JobImpl job = new JobImpl(calleeVars, pac);
                    task.getMapper().outputTransform(job, nvars);
                    job.setCurrentTask(task);
                    job.setLastTask(task);
                    job.run();
                }

            }
        }
        finally {
            sessManager.setCurrentRuleSession(null);
        }


    }

    @Override
    public void stopAndShutdown() {
        try {
            invokeshutdownProcesses();
            super.stopAndShutdown();
        }
        catch (Throwable throwable) {
            logger.log(Level.ERROR,  throwable, "Exception while invoking shutdown process");
        }
    }

    public static ProcessModel getCalledProcessByUri(Ontology ontology, String uri) {
        if (uri.endsWith(CommonIndexUtils.PROCESS_EXTENSION)) {
            int index = uri.indexOf(CommonIndexUtils.PROCESS_EXTENSION);
            uri = uri.substring(0, index - 1);
        }
        ProcessModel procModel = (ProcessModel) ontology.getEntity(uri);
        return procModel;
    }

    public static ProcessTemplate getProcessTemplateFromModel(ProcessModel pmodel) {
        final FQName fqName = FQName.makeName(pmodel.getFullPath(), pmodel.getRevision());
        final String processName = ModelNameUtil.modelPathToGeneratedClassName(fqName.getName());
        final ProcessTemplate processTemplate = ProcessTemplateRegistry.getInstance().getProcessTemplate(processName);
        return processTemplate;
    }

    public static Task getStartTask(ProcessModel pmodel) {
        final ProcessTemplate processTemplate = getProcessTemplateFromModel(pmodel);
        List<Task> starterTasks = processTemplate.getStarterTask();
        if (!starterTasks.isEmpty()) {
            return starterTasks.get(0);
        }
        return null;
    }

    private void loadStarterOrWaiterTasks() throws Exception {

        List<ProcessTemplate> processTemplates = processAgent.getDeployedProcessTemplates();

        for(ProcessTemplate template : processTemplates) {

            Collection<Task> tasks = template.allTasks();

            for(Task task : tasks) {

                if (task instanceof Triggerable) {
                    Triggerable triggerable = (Triggerable) task;
                    //     Rule rule = null;
                    TriggerType triggerType = triggerable.getTriggerType();
                    TriggerIdentifier[] identifiers = triggerable.getIdentifiers();
                    for (TriggerIdentifier ti : identifiers) {
                        Class<? extends Entity> eventClass = ti.getType();
                        List<Triggerable> triggerables = starterOrWaiterRules.get(eventClass);
                        if (triggerables == null) {
                            triggerables = new ArrayList<Triggerable>();
                            starterOrWaiterRules.put(ti.getType(), triggerables);
                            //TODO - put in the defaultTriggerable
                        }
                        triggerables.add(triggerable);

                        if (TimeEvent.class.isAssignableFrom(eventClass)) {
                            TimeManager timeManager = this.getTimeManager();
                            timeManager.registerEvent(eventClass);
                        }
                    }

                }
            }

        }
        return;

    }


    @Override
    public void assertObject(Object object, boolean executeRules) throws DuplicateExtIdException {

        if (logger.isEnabledFor(Level.DEBUG))
            logger.log(Level.DEBUG, String.format("Asserting called : %s", object.toString()));

        Job job = JobImpl.getCurrentJob();


        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();

        AssertOrRetractType assertOrRetractType = AssertOrRetractType.NONE;


        assertOrRetractType = ((threadSession == null) && (job == null)) ? AssertOrRetractType.ASSERT_FROM_CHANNEL : assertOrRetractType;
        assertOrRetractType = ((threadSession != null) && (job == null)) ? AssertOrRetractType.ASSERT_FROM_STARTUP : assertOrRetractType;
        assertOrRetractType = ((threadSession != null) && (job != null)) ? AssertOrRetractType.ASSERT_FROM_GENERICTASKCONTEXT : assertOrRetractType;
        assertOrRetractType = this.getWorkingMemory() == workingMemory ? assertOrRetractType : AssertOrRetractType.ASSERT_FROM_RETETASKCONTEXT;
        switch (assertOrRetractType) {
            case ASSERT_FROM_CHANNEL:
                assertFromChannel(object, executeRules); //From Messaging system or Timer
                break;
            case ASSERT_FROM_GENERICTASKCONTEXT:
                assertFromGenericTaskContext(object, executeRules);
                break;
            case ASSERT_FROM_RETETASKCONTEXT:
                assertFromReteTask(object, executeRules);
                break;
            case ASSERT_FROM_STARTUP:
            	//also for rule based time events
                assertFromStartup(object, executeRules);
                break;
            default:
                String threadSessionName = threadSession == null ? "(null)" : threadSession.getName();
                throw new RuntimeException("Session " + threadSessionName + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");

        }


    }


    private void assertFromStartup(Object obj, boolean executeRules) throws DuplicateExtIdException
    {
        if (obj instanceof AdvisoryEvent) executeRules = false;

        if (activeMode) {
            assertFromChannel(obj, executeRules);
        }

    }



    private void assertFromReteTask(Object object, boolean executeRules) throws DuplicateExtIdException {

        assertFromGenericTaskContext(object, executeRules);
        getWorkingMemory().assertObject(object, executeRules);

    }


    private void assertFromChannel(Object object, boolean executeRules) throws DuplicateExtIdException {
        try {
            sessManager.setCurrentRuleSession(this);

            List<Triggerable> triggerables = starterOrWaiterRules.get(object.getClass());      //No inheritance on Event provided as of yet.

            if (triggerables == null) {
                logger.log(Level.WARN, String.format("No starter process configured with this class of object : %s", object.getClass()));
                return;
            }

            for (Triggerable t : triggerables) {
                StarterJob starterJob = new StarterJobImpl(this.processAgent, t.getInitContext().getProcessTemplate());
                starterJob.assertEvent(t, (Event) object);

            }

        }
        finally {
            sessManager.setCurrentRuleSession(null);
        }
    }

    private void assertFromGenericTaskContext(Object object, boolean executeRules) throws DuplicateExtIdException {

        JobImpl job = (JobImpl) JobImpl.getCurrentJob();
        if (job == null) return;

        Activity currentTask = job.getCurrentTask();

        if (object instanceof JobContext) {
            logger.log(Level.ERROR, String.format("Attempting to asserting JobContext (ProcessVariables) from Task :%s", currentTask.getName()));
            return;
        }

        job.assertObject(object);

        return;


    }

    @Override
    public void retractObject(Object object, boolean executeRules) {

        if (logger.isEnabledFor(Level.DEBUG))
            logger.log(Level.DEBUG, String.format("Retracting called : %s", object.toString()));

        Job job = JobImpl.getCurrentJob();


        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();

        AssertOrRetractType assertOrRetractType = AssertOrRetractType.NONE;


        assertOrRetractType = ((threadSession != null) && (job == null)) ? AssertOrRetractType.RETRACT_FROM_STARTUP : assertOrRetractType;
        assertOrRetractType = ((threadSession != null) && (job != null)) ? AssertOrRetractType.RETRACT_FROM_GENERICTASKCONTEXT : assertOrRetractType;
        assertOrRetractType = this.getWorkingMemory() == workingMemory ? assertOrRetractType : AssertOrRetractType.RETRACT_FROM_RETETASKCONTEXT;

        switch (assertOrRetractType) {
            case RETRACT_FROM_GENERICTASKCONTEXT:
                retractFromGenericTaskContext(object, executeRules);
                break;
            case RETRACT_FROM_RETETASKCONTEXT:
                retractFromReteTask(object, executeRules);
                break;
            case RETRACT_FROM_STARTUP:_FROM_STARTUP:
            retractFromStartupOrShutdown(object, executeRules);
                break;
            default:
                String threadSessionName = threadSession == null ? "(null)" : threadSession.getName();
                throw new RuntimeException("Session " + threadSessionName + " can't directly access session " + this.getName() + ".  Use Local Channel to communicate between sessions");

        }

    }

    private void retractFromReteTask(Object object, boolean executeRules) {

        retractFromGenericTaskContext(object, executeRules);
        getWorkingMemory().retractObject(object, executeRules);
    }

    private void retractFromStartupOrShutdown(Object object, boolean executeRules) {

    }

    private void retractFromGenericTaskContext(Object object, boolean executeRules) {

        JobImpl job = (JobImpl) JobImpl.getCurrentJob();
        if (job == null) return;

        Activity currentTask = job.getCurrentTask();

        if (object instanceof JobContext) {
        	logger.log(Level.ERROR, String.format("Attempting to retract current JobContext (ProcessVariables) from Task :%s", currentTask.getName()));
            return;

        }

        job.retractObject(object);



        return;
    }




    @Override
    public ObjectManager getObjectManager() {
        return this.objectManager;
    }


    @Override
    public Object invokeFunction(String functionURI, Object[] args, boolean synchronize) {
        RuleFunction ruleFn = getRuleFunction(functionURI);
        Object ret = ruleFn.invoke(args);
        return ret;
    }

    public RuleFunction getRuleFunction(String URI) {
        RuleFunction ruleFunc = ((BEClassLoader) getRuleServiceProvider().getTypeManager()).getRuleFunctionInstance(URI);
        if (ruleFunc == null)
            throw new RuntimeException("RuleFunction " + URI + " does not exist");
        return ruleFunc;
    }

    public Rule getRule(String ruleUri) {
        return workingMemory.getRuleLoader().getRule(ruleUri);
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        WorkingMemory local = threadLocalWMs.get();
        if(local != null) return local;
        return workingMemory;
    }

    public void setThreadLocalWorkingMemory(WorkingMemory wm) {
        threadLocalWMs.set(wm);
    }

    @Override
    public void fireRepeatEvent(TimeEvent timeEvent, int numPerInterval) {

        try {
            for (int i=0; i <numPerInterval; i++) {
                //logger.log(Level.INFO, "Firing timer");
                this.assertObject(timeEvent, true);
            }
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, "Timer Exception - asserting timer event");
        }
    }

    @Override
    public String getLogComponent() {
        Job job = JobImpl.getCurrentJob();
        if (job == null) return super.getLogComponent();

        long id = job.getJobContext().getId();
        Activity activity = job.getCurrentTask();

        String name = activity == null ? "TaskNone" : activity.getName();


        return String.format("Job@%d-%s", job.getJobContext().getId(), name);
    }
    
    public void releaseAllLocks() {
        LockManager lockManager = ((ObjectBasedStore)getObjectManager()).getLockManager();
        lockManager.takeLockDataStuckToThread();
    }

	public ProcessAgent getProcessAgent() {
		return processAgent;
	}
}
