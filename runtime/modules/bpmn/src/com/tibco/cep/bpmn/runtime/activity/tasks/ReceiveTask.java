package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TaskResult.Status;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.events.AbstractTriggerEvent;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.results.StartEventResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.Job.PendingEvent;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.agent.ProcessStatus;
import com.tibco.cep.bpmn.runtime.agent.StarterJob;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.utils.PendingEventHelper;
import com.tibco.cep.bpmn.runtime.utils.ProcessHelperUtils;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author pdhar
 */
public class ReceiveTask extends AbstractTriggerEvent {
	public ReceiveTask() {
	}

	@Override
	public void init(InitContext context, Object... args) throws Exception {
		super.init(context, args);
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		String eventURI = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
		Ontology ontology = getInitContext().getProcessModel().getOntology();
		this.event = ontology.getEvent(eventURI);
		String xpathExpr = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
		if (xpathExpr != null && !xpathExpr.trim().isEmpty()) {
			XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpr);
			this.keyFilter = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
		} else {
			this.keyFilter = "";
		}
	}

	com.tibco.cep.designtime.model.event.Event getEvent() {
		return this.event;
	}

	@Override
	public TaskResult execute(Job job, Variables vars, Task loopTask) {

		TaskResult result = null;

		try {
			// Asserted from channel.
			if (job instanceof StarterJob) {
				result = onEventArrivingAtTask(job, vars, loopTask);
			} else {
				result = onJobTransitionAtTask(job, vars, loopTask);
			}
		} catch (Exception ex) {
			result = new ExceptionResult(ex);
		}
		return result; // needed for debugger exit task notification
	}

	private TaskResult onJobTransitionAtTask(Job job, Variables vars, Task loopTask) throws Exception {

		ProcessAgent pac = getInitContext().getProcessAgent();
		ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
		LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();

		JobContext pv = job.getJobContext();
		String extId = pv.getExtId();
		boolean locked = false;

		try {
			if (loopTask == null) {
				// avoid double locking when in loop
				locked = lockManager.lock(extId, -1, LockManager.LockLevel.LEVEL2);
			}

			// Check if the job's current task to be executed is same
			// as this recieve task to
			// use the event otherwise put the event to cache and wait for the
			// Job to reach this task to use the event
			Task lastTask = pv.getLastTaskExecuted();
			String currTaskName = getName();
			boolean isCurrentTask = (lastTask != null) && !lastTask.getName().equals(currTaskName) && isCurrentTaskFromJobContext(lastTask, currTaskName);
			// is current receive task the next task of persisted last task
			
			if (isCurrentTask || (loopTask != null)) {
				// Search for matching trigger event.
				Class<Event> runtimeClass = getRuntimeEventClass();
				if (runtimeClass == null)
					throw new Exception("No matching event specified as wait expression");

				if (logger.isEnabledFor(LEVEL)) {
					logger.log(LEVEL, "Looking up event " + getClass().getSimpleName() + " : " + vars + " : " + extId);
				}

				EventFinder filter = new EventFinder(getInputEvent().getName(), getJobKeyExpression(), extId);

				DaoProvider daoProvider = job.getProcessAgent().getCluster().getDaoProvider();
				EntityDao<Object, Event> dao = daoProvider.getEntityDao(runtimeClass);
				if (logger.isEnabledFor(LEVEL)) {
					logger.log(LEVEL, "Available first 100 event keys:" + dao.keySet(filter, 100));
				}
				Set<Entry> values = dao.entrySet(filter, 1);
				if (!values.isEmpty()) {
//					Event runtimeEvent = findRetractableEvent(job,values);
//					
//					if(runtimeEvent == null) {
//						return new DefaultResult(TaskResult.Status.WAITFOREVENT, job);
//					}

					Entry entry = values.iterator().next();
					Event runtimeEvent = (Event) entry.getValue();
					vars.setVariable(getInputEvent().getName(), runtimeEvent);
					// this.getMapper().outputTransform(job, vars); // this
					// is already getting called by JobImpl after returning from the
					// exec method,  do not do it here
					((JobImpl)job).retractIfAbsent(runtimeEvent);

					if (loopTask != null && loopTask instanceof DefaultTriggerableLoopTask) {
						DefaultTriggerableLoopTask.setTriggerEventId(runtimeEvent.getId());
					}

					if (logger.isEnabledFor(LEVEL)) {
						logger.log(LEVEL, "Completed onJobTransitionAtTask " + getClass().getSimpleName() + " : " + vars + " : " + job.getTransitionName()
								+ " : " + runtimeEvent + " : " + extId);
					}
					((JobImpl)job).setLastTask(this);
					((JobImpl)job).setCurrentTask(this);
					if(loopTask == null){
						job.checkpoint(false);
					}
					return new DefaultResult(Status.OK, job);

				}
			} 

			if (logger.isEnabledFor(LEVEL)) {
				logger.log(LEVEL, "Waiting for event " + getClass().getSimpleName() + " : " + vars + " : " + job.getTransitionName() + " : " + extId);
			}

			return new DefaultResult(TaskResult.Status.WAITFOREVENT, job);
		} finally {
			if (locked) {
				lockManager.unlock(extId);
			}
		}

	}

	private Event findRetractableEvent(Job job, Set<Entry> values) {
		Event firstFilterEvent = null;
		Event matchedEvent = null;
		Set<PendingEvent> pevs=job.getPendingEvents(getName());
		if(pevs == null)
			return null;
		for( PendingEvent pev:pevs){

			for(Entry entry:values) {
				Event filterEvent = (Event) entry.getValue();
				if(firstFilterEvent == null) {
					firstFilterEvent = filterEvent;
				}
				if(pev.getEvent().equals(filterEvent)){
					matchedEvent = filterEvent;
					break;
				}
			}

		}
		
		return matchedEvent != null ? matchedEvent : firstFilterEvent ;
	}

	protected TaskResult onEventArrivingAtTask(Job job, Variables vars, Task loopTask) throws Exception {

		ProcessAgent pac = getInitContext().getProcessAgent();
		ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
		LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();
		final DistributedCacheBasedStore objectStore = (DistributedCacheBasedStore) processRuleSession.getObjectManager();

		Event event = (Event) vars.getVariable(getInputEvent().getName());

		String extId = JXPathHelper.evalXPathAsString(getJobKeyExpression(), new String[] { "globalVariables", getInputEvent().getName() }, new Object[] {
				processRuleSession.getRuleServiceProvider().getGlobalVariables(), event });

		if (logger.isEnabledFor(LEVEL)) {
			logger.log(LEVEL, "Looking up process " + getClass().getSimpleName() + " : " + vars + " : " + event + " : " + extId);
		}
		Transition[] incoming = this.getIncomingTransitions();
		boolean loopBack = false;
		for(Transition t:incoming) {
			LinkedList<LinkedList<Task>> paths = new LinkedList<LinkedList<Task>>();
			ProcessHelperUtils.dfs(this, t.fromTask(), new LinkedList<Task>(), paths);
			loopBack |= (paths.size() >  0);
		}
		boolean locked = false;
		try {
			if (loopTask == null) {
				// avoid double locking when in loop
				locked = lockManager.lock(extId, -1, LockManager.LockLevel.LEVEL2);
			}

			JobContext pv = (JobContext) processRuleSession.getObjectManager().getElement(extId);

			if (pv == null) {
				if (logger.isEnabledFor(LEVEL)) {
					logger.log(LEVEL, "Waiting for process " + getClass().getSimpleName() + " : " + vars + " : " + event + " : " + extId);
				}

				/*
				 * The event needs to be saved even if the Job is not yet ready
				 * e.g. (SE) - Starts the job followed by 4 (RE)s and this is
				 * the RE invocation which found that the SE job creation is
				 * still on its way.
				 */
				// Search for matching trigger event.
				Class<Event> runtimeClass = getRuntimeEventClass();
				if (runtimeClass == null)
					throw new Exception("No matching event specified as wait expression");
				if(!loopBack) {
					((JobImpl) job).assertObject(event);
				}

				// Get the Event entity Dao
				DaoProvider daoProvider = job.getProcessAgent().getCluster().getDaoProvider();
				EntityDao<Object, Event> dao = daoProvider.getEntityDao(runtimeClass);
				// save the event while the process job is yet to arrive
				dao.put(event);

				return new DefaultResult(Status.WAITFORJOB, event);
			}

			// Check if the retrieved job's current task to be executed is same
			// as this recieve task to
			// use the event otherwise put the event to cache and wait for the
			// Job to reach this task to use the event
			Task lastTask = pv.getLastTaskExecuted();
			String currTaskName = getName();
			boolean isCurrentTask;
			if(job instanceof StarterJob) {
				isCurrentTask = (lastTask != null) && lastTask.getName().equals(currTaskName) ;
			} else {
				isCurrentTask = (lastTask != null) && !lastTask.getName().equals(currTaskName) && isCurrentTaskFromJobContext(lastTask, currTaskName);
			}
			// is current receive task the next task of persisted last task
			Class<Event> runtimeClass = getRuntimeEventClass();
			if (isCurrentTask || (loopTask != null)) {
				if (logger.isEnabledFor(LEVEL)) {
					logger.log(LEVEL, "Found Job:" + pv + "waiting at " + lastTask.getName() + " for Event  :" + extId);
				}
			} else {
				// Search for matching trigger event.
				if (runtimeClass == null)
					throw new Exception("No matching event specified as wait expression");

				((JobImpl) job).assertObject(event);
				// Get the Event entity Dao
				DaoProvider daoProvider = job.getProcessAgent().getCluster().getDaoProvider();
				EntityDao<Object, Event> dao = daoProvider.getEntityDao(runtimeClass);

				dao.put(event);

				if (logger.isEnabledFor(LEVEL)) {
					logger.log(LEVEL, "Found Job waiting at " + lastTask.getName() + " for Event  :" + extId);
				}

				return new DefaultResult(Status.WAITFORJOB, event);
			}

			if (logger.isEnabledFor(LEVEL)) {
				logger.log(LEVEL, "Completed onEventArrivingAtTask " + getClass().getSimpleName() + " : " + vars + " : " + event + " : " + extId);
			}
			
			pv.setProcessStatus(ProcessStatus.RUNNING);
			JobImpl newJob = new JobImpl(pv, pac);
			newJob.setCurrentTask(this);
			newJob.setLastTask(this);
			
			Map<Long,String> peMap = new HashMap<Long,String>();
	        
			// reinitialize the job with pending events from the jobcontext
			// this is needed to carry the pending event information to the end task where it gets 
			// cleaned up from the backingstore
			PropertyArrayString peMapArr= pv.getPendingEventMap();
			PendingEventHelper.convertPropertyArrayToMap(peMapArr,peMap);
			Map<String, Set<PendingEvent>> pendingEvents = PendingEventHelper.convertMapToPendingEvents(peMap,objectStore);
			newJob.setPendingEvents(pendingEvents);
			
			EntityDao dao = pac.getCluster().getDaoProvider().getEntityDao(pv.getClass());
			try {
				dao.lock(pv.getId(), -1);
				this.getMapper().outputTransform(newJob, vars);
				if(!loopBack) {
					newJob.assertObject(event);
				}
			} finally {
				dao.unlock(pv.getId());
			}
			newJob.checkpoint(false); // checkpoint to record the running state of the job
			return new StartEventResult(newJob, (Event) event);
		} finally {
			if (locked) {
				lockManager.unlock(extId);
			}
		}

	}

	/**
	 * @param lastTask
	 * @param currTaskName
	 * @return
	 */
	private boolean isCurrentTaskFromJobContext(Task lastTask, String currTaskName) {
		Transition[] transitions = lastTask.getOutgoingTransitions();
		if (transitions != null && transitions.length > 0) {
			for (Transition t : transitions) {
				if (t.toTask().getName().equals(currTaskName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean instantiatesProcess() {
		return false;
	}

	@Override
	public boolean isIntermediate() {
		return false;
	}

	@Override
	public boolean isInterrupting() {
		return true;
	}

	// --------------

	public static class EventFinder implements Filter {
		String eventName;

		String xpath;

		String extIdToMatch;

		transient GlobalVariables globalVariables;

		transient String[] argNames;

		transient Object[] args;

		public EventFinder() {
			init();
		}

		public EventFinder(String eventName, String xpath, String extIdToMatch) {
			this.eventName = eventName;
			this.xpath = xpath;
			this.extIdToMatch = extIdToMatch;

			init();
		}

		private void init() {
			this.globalVariables = RuleServiceProviderManager.getInstance().getDefaultProvider().getGlobalVariables();
			this.argNames = new String[] { "globalVariables", eventName };
			this.args = new Object[] { globalVariables, null };
		}

		@Override
		public boolean evaluate(Object o, FilterContext context) {
			if (args == null) {
				init();
			}

			Event event = (Event) o;
			args[1] = event;

			String extId = JXPathHelper.evalXPathAsString(xpath, argNames, args);

			return extIdToMatch.equals(extId);
		}
	}

	public static class EventFinderById implements Filter {

		private long eventId;

		public EventFinderById(long id) {
			this.eventId = id;

		}

		@Override
		public boolean evaluate(Object o, FilterContext context) {
			Event event = (Event) o;
			return event.getId() == eventId;
		}
	}
}
