package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TaskResult.Status;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.TriggerIdentifier;
import com.tibco.cep.bpmn.runtime.activity.TriggerType;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessException;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.agent.StarterJob;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.utils.ProcessHelperUtils;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.xml.datamodel.XiNode;

public class DefaultTriggerableLoopTask<T extends AbstractTask> extends DefaultLoopTask<T> implements TriggerableLoopTask {

	protected String resourceUri;
	private EObjectWrapper<EClass, EObject> eventWrapper;
	protected com.tibco.cep.designtime.model.event.Event event;
	private Class<Event> runtimeEventClass;
	protected String conditionExpression;
	protected TriggerType triggerType;
	protected int priority = 5;
	protected String keyFilter;
	public static final ThreadLocal<String> triggerExtId = new ThreadLocal<String>();
	public static final ThreadLocal<Long> triggerEventId = new ThreadLocal<Long>();
	protected JobContext checkPointedJobContext;


	public DefaultTriggerableLoopTask(T task) {
		super(task);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(InitContext context, Object... args) throws Exception {
		super.init(context, args);

		eventWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(eventWrapper);
		resourceUri = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
		if (resourceUri != null && !resourceUri.isEmpty()) { // null in case of
																// start event
																// none
			Ontology ontology = context.getProcessModel().getOntology();
			this.event = ontology.getEvent(resourceUri);
			this.runtimeEventClass = getEventClass(resourceUri);
		}

		// get Event Definition

		if (eventWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK)) {
			// key expression is only available for a receive event task which
			// waits for a
			// Job key @extid
			String xpathExpr = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
			if (xpathExpr != null && !xpathExpr.trim().isEmpty()) {
				XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpr);
				this.keyFilter = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
			} else {
				this.keyFilter = "";
			}
		}
		
		this.triggerType = TriggerType.NONE;

	}
	public static String getTriggerExtId() {
		return triggerExtId.get();
	}
	
	public static void setTriggerExtId(String extId) {
		triggerExtId.set(extId);
	}
	public static long getTriggerEventId() {
        return triggerEventId.get();
    }
	
	public static void setTriggerEventId(Long id) {
		triggerEventId.set(id);
	}
	
	protected String makeLoopKey(Job job, Task ltask) throws ProcessException {
		//long id = job.getJobContext().getId();
		
		String extId = getTriggerExtId() != null ? getTriggerExtId() :  job.getJobContext().getExtId();
		if(extId == null) {
			throw new ProcessException("Job Context extId is not available to handle Receive events");
		}
		String taskId = ltask.getName();
		return String.format("[%s]:%s", extId,taskId);
	}
	
	
//	private String makeLockContext(JobContext context, Task ltask) {
//		long id = context.getId();
//		String taskId = ltask.getName();
//		return String.format("[%d]:%s", id,taskId);
//	}
	
	public TaskResult execute(Job job, Variables vars, Task loopTask) {
		TaskResult result = null;
		try {
			//setLockContext(makeLockContext(job.getJobContext(), this));
			if(job instanceof StarterJob) {
				result =  onEventArrivingAtTask( job,  vars,  loopTask);
			} else {
				result = onJobTransitionAtTask( job,  vars,  loopTask);
			}
		} catch(Exception e) {
			result = new ExceptionResult(e);
		} finally {
			//setLockContext(null);
		}


		return result;
	}
	
	
	
	private TaskResult onEventArrivingAtTask(Job job, Variables vars, Task loopTask) throws Exception {
		TaskResult result = null;
		ProcessAgent pac = getInitContext().getProcessAgent();
		ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
		LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();
		Event event = (Event) vars.getVariable(getInputEvent().getName());
		String extId = getJobKeyFromEvent(event);
		setTriggerExtId(extId);
		setTriggerEventId(event.getId());
		boolean locked = false;
		String lContext = null;
		try {
			// Event arriving at task
			if(extId != null) {
				lContext = makeLockKey(extId);
				locked = lockManager.lock(lContext, -1, LockManager.LockLevel.LEVEL2);
			}
			// For Receive Task loop the event arriving at the task should be asserted into the memory
			if (this.loopTask instanceof ReceiveTask) {
				
				JobContext cacheJob = (JobContext) processRuleSession.getObjectManager().getElement(extId);
				setCheckPointedJobContext(cacheJob);
				
				if (logger.isEnabledFor(LEVEL)) {
					logger.log(LEVEL, "Waiting for process "
							+ getClass().getSimpleName() + " : " + vars + " : "
							+ event + " : " + extId);
				}
				Transition[] incoming = this.getIncomingTransitions();
				boolean loopBack = false;
				for (Transition t : incoming) {
					LinkedList<LinkedList<Task>> paths = new LinkedList<LinkedList<Task>>();
					ProcessHelperUtils.dfs(this, t.fromTask(),
							new LinkedList<Task>(), paths);
					loopBack |= (paths.size() > 0);
				}
				if (runtimeEventClass == null)
					throw new Exception(
							"No matching event specified as wait expression");
				if (!(loopBack || job instanceof StarterJob)) {
					((JobImpl) job).assertObject(event);
				}
				DaoProvider daoProvider = job.getProcessAgent().getCluster().getDaoProvider();
				EntityDao<Object, Event> dao = daoProvider.getEntityDao(runtimeEventClass);
				dao.put(event);
				//This is to check if the event has arrived before the process gets started 
				//If so there will be no checkpointed job and thus evaluation of the loop task will throw exception
				if ( getCheckPointedJobContext() == null ) {
					return new DefaultResult(Status.WAITFORJOB, event);
				} else {
					result = super.execute(job, vars, loopTask);
					
				}
			} 
			else {
				result = super.execute(job, vars, loopTask);
			}
		} finally {
			if (locked) {
				lockManager.unlock(lContext);
			}
			setTriggerExtId(null);
			setTriggerEventId(null);
		}
		return result;
	}

	private TaskResult onJobTransitionAtTask(Job job, Variables vars, Task loopTask) throws Exception {
		TaskResult result = null;
		ProcessAgent pac = getInitContext().getProcessAgent();
		ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
		LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();

		JobContext pv = job.getJobContext();
		String extId = pv.getExtId();
		boolean locked = false;
		String lContext = null;
		try {
			// Event arriving at task
			if(extId != null) {
				setTriggerExtId(extId);
				setTriggerEventId(-1L);
				lContext = makeLockKey(extId);
				locked = lockManager.lock(lContext, -1, LockManager.LockLevel.LEVEL2);
			}
			Element element =  ((DistributedCacheBasedStore) processRuleSession.getObjectManager()).getElement(extId);
			// Check if there Triggerable Loop Job exists in incomplete state
			if(element == null ){
				// if it does not exists it means the EndEvent has been
				// reached and removed from cache and bs
				
				result = new DefaultResult(TaskResult.Status.COMPLETE, job);
			} else {
				if (this.loopTask instanceof ReceiveTask) {
					JobContext cacheJob = (JobContext) element;
					setCheckPointedJobContext(cacheJob);
				}
				result = super.execute(job, vars, loopTask);
			}
				
		} finally {
			if (locked) {
				lockManager.unlock(lContext);
			}
			setTriggerExtId(null);
			setTriggerEventId(null);
		}

		
		return result;
	}
	
	

	@Override
	public boolean isAsyncExec() {
		return true;
	}

	protected Class<Event> getRuntimeEventClass() {
        return runtimeEventClass;
    }

	private Class<Event> getEventClass(String resourceUri) {

		RuleServiceProvider rsp = getInitContext().getProcessAgent().getRuleServiceProvider();
		TypeManager tm = rsp.getTypeManager();
		if (resourceUri != null) {
			// Timer Start Events may not have an associated BE Time Event
			// because
			// BPMN timers are different from BE timers. This is not decided
			// yet: TODO

			TypeDescriptor td = tm.getTypeDescriptor(resourceUri);
			return td.getImplClass();

		}
		return null;
	}

	@Override
	public com.tibco.cep.designtime.model.event.Event getInputEvent() {
		return event;
	}

	@Override
	public com.tibco.cep.designtime.model.event.Event getOutputEvent() {
		return null;
	}
	
	public String makeLockKey(String extId) {
		return extId;// +":loop" ;
	}
	
   public String getJobKeyFromEvent(Event event) {
    	ProcessAgent pac = getInitContext().getProcessAgent();
        ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
        LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();


        //Event event = (Event) vars.getVariable(getInputEvent().getName());

        String extId = JXPathHelper.evalXPathAsString(getJobKeyExpression(),
                new String[]{"globalVariables", getInputEvent().getName()},
                new Object[]{processRuleSession.getRuleServiceProvider().getGlobalVariables(), event});

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Looking up process job " + getClass().getSimpleName()
                   + " for Event: " + event + " using key: " + extId);
        }
        
        return extId;
    }


	@Override
	public TriggerType getTriggerType() {
		return this.triggerType;
	}

	@Override
	public int getPriority() {
		return 5;
	}

	@Override
	public TriggerIdentifier[] getIdentifiers() {
		final Class<Event> eventClass = getEventClass(resourceUri);
		final String name = getInputEvent().getName();
    	if(getInputEvent() != null) {
    		return new TriggerIdentifier[] { new TriggerIdentifier() {
				
				@Override
				public Class<? extends Entity> getType() {
					return eventClass;
				}
				
				@Override
				public String getName() {
					return name;
				}
			}};
    	}
    	// for Event Def type NONE
    	return new TriggerIdentifier[0];
	}

	@Override
	public boolean instantiatesProcess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIntermediate() {
		return false;
	}

	@Override
	public boolean isInterrupting() {
		return false;
	}

	@Override
	public String getJobKeyExpression() {
		return keyFilter;
	}

	@Override
	public boolean eval(Variables variables) {
		return true;
	}

//	@Override
//	public void setComplete(Job job) throws ProcessException {
//		boolean result = false;
//		LockManager lockManager = getLockManager();
//		
//		JobContext pv = job.getJobContext();
//		String extId = pv.getExtId();
//		boolean locked = false;
//		String lContext = null;
//		try {
//			// Event arriving at task
//			if(extId != null) {
//				setTriggerExtId(extId);
//				lContext = makeLockKey(extId);
//				locked = lockManager.lock(lContext, -1, LockManager.LockLevel.LEVEL2);
//			}
//			super.setComplete(job);
//		} finally {
//			if (locked) {
//				lockManager.unlock(lContext);
//			}
//			setTriggerExtId(null);
//		}
//	}
//	@Override
//	public boolean isComplete(Job job) throws ProcessException {
//		boolean result = false;
//		LockManager lockManager = getLockManager();
//
//		JobContext pv = job.getJobContext();
//		String extId = pv.getExtId();
//		boolean locked = false;
//		String lContext = null;
//		try {
//			// Event arriving at task
//			if(extId != null) {
//				setTriggerExtId(extId);
//				lContext = makeLockKey(extId);
//				locked = lockManager.lock(lContext, -1, LockManager.LockLevel.LEVEL2);
//			}
//			result = super.isComplete(job);
//		} finally {
//			if (locked) {
//				lockManager.unlock(lContext);
//			}
//			setTriggerExtId(null);
//		}
//		return result;
//	}

	@Override
	public boolean lock(Job job) {
		LockManager lockManager = getLockManager();
		JobContext pv = job.getJobContext();
		String extId = pv.getExtId();
		setTriggerExtId(extId);
		String lContext = makeLockKey(extId);
		return  lockManager.lock(lContext, -1, LockManager.LockLevel.LEVEL2);
		
	}

	@Override
	public void unlock(Job job) {
		LockManager lockManager = getLockManager();
		JobContext pv = job.getJobContext();
		String extId = pv.getExtId();
		setTriggerExtId(null);
		String lContext = makeLockKey(extId);
		lockManager.unlock(lContext);
	}

	@Override
	public void setCheckPointedJobContext(JobContext checkPointedJob) {
		this.checkPointedJobContext = checkPointedJob ;
		
	}

	@Override
	public JobContext getCheckPointedJobContext() {
		return this.checkPointedJobContext ;
	}

//	@Override
//	public void incrementAndSaveCounter(Job job, Variables vars) throws ProcessException {
//		LoopTuple lt = super.getCreateLoopTuple(job, vars);
//		final long eventId = getTriggerEventId();
//		if(!lt.containsEvent(eventId)){
//			lt.recordEvent(eventId);
//			super.updateTuple(job, vars, lt);
//			super.incrementAndSaveCounter(job, vars);
//		}
//	}
	
	
	
	

}
