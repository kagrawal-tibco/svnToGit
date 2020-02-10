package com.tibco.cep.bpmn.runtime.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bpmn.runtime.activity.Activity;
import com.tibco.cep.bpmn.runtime.activity.Gateway;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TaskResult.Status;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.gateways.ParallelGateway;
import com.tibco.cep.bpmn.runtime.activity.mapper.Mapper;
import com.tibco.cep.bpmn.runtime.activity.results.CallActivityResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.results.ForkResult;
import com.tibco.cep.bpmn.runtime.activity.results.MergeCompleteResult;
import com.tibco.cep.bpmn.runtime.activity.results.StartEventResult;
import com.tibco.cep.bpmn.runtime.activity.tasks.DefaultLoopTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.ExceptionResultHandler;
import com.tibco.cep.bpmn.runtime.activity.tasks.LoopTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.TriggerableLoopTask;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.runtime.utils.PendingEventHelper;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.rete.BeTransaction;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.process.ObjectBean;
import com.tibco.cep.runtime.model.process.ObjectBean.BeanOp;
import com.tibco.cep.runtime.model.process.ObjectBeanHandle;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentAction;
import com.tibco.cep.runtime.service.cluster.agent.tasks.IAgentActionManager;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

/*
 * Author: Suresh Subramani / Date: 1/19/12 / Time: 7:54 PM
 */
public class JobImpl implements Job, IAgentActionManager {

	static Logger logger = LogManagerFactory.getLogManager().getLogger(Job.class);

	public static final ThreadLocal<Job> currentJobContexts = new ThreadLocal<Job>();
	// public static final ThreadLocal<Task> currentTask = new
	// ThreadLocal<Task>();

	JobContext jobContext;
	ProcessAgent processAgent;
	// PreprocessContext preprocessContext = null;
	Map<String, Set<PendingEvent>> pendingEvents;
	Map<String, Set<JobContext>> completedJobs;
	Map<Long,Element> retractedElements;
	String transitionName;
	Collection<AgentAction> actions = null;
	private transient Activity currentTask = null; // This is the current task
													// being executed.
	TaskResult childTaskResult = null;


	public JobImpl(JobContext process, ProcessAgent processAgent) {

		this.jobContext = process;
		this.processAgent = processAgent;
		this.pendingEvents = new ConcurrentHashMap<String, Set<PendingEvent>>();
		this.completedJobs = new ConcurrentHashMap<String,Set<JobContext>>();
		this.retractedElements = new ConcurrentHashMap<Long,Element>();
		// traverse the processData, and add it to the preprocessContext for
		// loading purpose.
		// TODO Suresh

	}

	@Override
	public JobContext getJobContext() {
		return jobContext;
	}

	@Override
	public ProcessAgent getProcessAgent() {
		return processAgent;
	}

	public void setLastTask(Task executedTask) {
		this.jobContext.setLastTaskExecuted(executedTask);
	}

	@Override
	public Task getLastTask() {
		return this.jobContext.getLastTaskExecuted();
	}

	public void setNextTask(Task next) {
		this.jobContext.setNextTask(next);
	}

	@Override
	public Task getNextTask() {
		return this.jobContext.getNextTask();
	}
	
	public Map<String, Set<JobContext>> getCompletedJobs() {
		return completedJobs;
	}
	

	

	@Override
	public Map<String, Set<PendingEvent>> getPendingEvents() {
		return pendingEvents;
	}
	
	public void setPendingEvents(Map<String, Set<PendingEvent>> pevents) {
		this.pendingEvents = deepCopy(pevents);
	}

	public static Map<String, Set<PendingEvent>> deepCopy(Map<String, Set<PendingEvent>> pevents) {
		Map<String, Set<PendingEvent>> zEvents = new HashMap<String, Set<PendingEvent>>();
		for(Entry<String, Set<PendingEvent>> entry:pevents.entrySet()){
			String key = entry.getKey();
			Set<PendingEvent> value = entry.getValue();
			zEvents.put(key, new HashSet<PendingEvent>(value));
		}
		return zEvents;
	}

	@Override
	public Set<PendingEvent> getPendingEvents(String uri) {
		return pendingEvents.get(uri);
	}

	public void recordEvent(Activity task, Event event) {
		if (event instanceof TimeEvent)
			return;
		Set<PendingEvent> set = pendingEvents.get(task.getName());
		if (set == null) {
			set = new HashSet<PendingEvent>();
			pendingEvents.put(task.getName(), set);
		}
		set.add(new PendingEventImpl(event));

	}
	
	@Override
	public void recordCompletedJob(Activity task, JobContext jc) {
		if (task instanceof ParallelGateway) {
			
			Set<JobContext> set = this.completedJobs.get(task.getName());
			if (set == null) {
				set = new HashSet<JobContext>();
				this.completedJobs.put(task.getName(), set);
			}
			set.add(jc);
		}
	}
	
	public void recordRetraction(Element element) {
		this.retractedElements.put(element.getId(),element);
	}
	
	public Collection<Element> getAllRetractedElements() {
		return this.retractedElements.values();
	}

	@Override
	public void run() {
		RuleSessionManagerImpl sessManager = (RuleSessionManagerImpl) processAgent.getRuleServiceProvider().getRuleRuntime();
		try {
			sessManager.setCurrentRuleSession(processAgent.getRuleSession());
			new BeTransaction("bpmn-job-execution") {
				@Override
				protected void doTxnWork() {
					boolean bProcessing = true;

					while (bProcessing) {
						JobImpl.setCurrentJob(JobImpl.this);
						
						Activity activity = getActivityToExecute();
						if (activity == null) {
							break;
						}
						
						setCurrentTask(activity);
						try {
							TaskResult result = getChildTaskResult();
							// child task result is set when call activity/sub process have an error
							// and when the parent job is woken up after child is cleaned up 
							// the error needs to propagate back to the call stack
							if (result == null) {
								result = executeActivity(activity);

							} else {
								activity = getLastTask();
							}

							List<Job> jobs = new ArrayList<Job>();

							ProcessResultMode resultMode = processTaskResult((Task) activity, result, jobs);
							
							bProcessing = interpretProcessResultMode(resultMode, jobs);
							
						} finally {
							setChildTaskResult(null);
						}

						
						if(!bProcessing) {
							// when it is known that the job on this thread is ending
							// all locks will be released instead of unlocking
							// at checkpoint()->applyChanges() where any calls 
							// to applychanges() releases looping locks and user locks just
							// after executing a task and checkpointing in the middle of a loop. 
							// This is different from inferent agent endofrtc
							// applychanges where all locks were released.
							processAgent.releaseAllLocks();
						}
					}
				}
			}.execute();
		} finally {
			sessManager.setCurrentRuleSession(null);
			JobImpl.setCurrentJob(null);
		}
	}

	private Activity getActivityToExecute() {
		final Task lastTask = getLastTask();
		if (lastTask != null) {
			if (lastTask instanceof LoopTask) {
				final LoopTask loopTask = (LoopTask) lastTask;
				try {
					if (loopTask instanceof TriggerableLoopTask) {
						try {
							((TriggerableLoopTask) loopTask).lock(getCurrentJob());
							if (!loopTask.isComplete(getCurrentJob())) {
								// continue until loop is complete
								logger.log(Level.DEBUG, String.format("Loop task:%s", loopTask.getName()));
								return (Activity) loopTask;
							} else {
								loopTask.setComplete(getCurrentJob());
								logger.log(Level.DEBUG, String.format("Loop Completed after task:%s", loopTask.getName()));
							}
						} finally {
							((TriggerableLoopTask) loopTask).unlock(getCurrentJob());
						}
						
					} else {
						
						if (!loopTask.isComplete(getCurrentJob())) {
							// continue until loop is complete
							return (Activity) loopTask;
						} else {
							loopTask.setComplete(getCurrentJob());
							logger.log(Level.DEBUG, String.format("Loop Completed after task:%s", loopTask.getName()));
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.ERROR,e.getMessage(), e);
				}
			}
			Transition[] outgoing = lastTask.getOutgoingTransitions();

			if ((outgoing == null) || outgoing.length == 0) {
				return null; // We ended the processing.
			}

			if (outgoing.length > 1) {
				logger.log(Level.WARN, "Multiple outgoing from a task is not supported v1.0 of BPMN process execution. Only from a Gateway");
			}

			Transition transition = outgoing[0];
			this.transitionName = transition.getName();
			logger.log(Level.DEBUG, String.format("ActivityToExecute fromTask:%s toTask:%s", lastTask.getName(), transition.toTask()));
			return transition.toTask();
		} else {
			return getNextTask();
		}

	}
	
	public TaskResult getChildTaskResult() {
		return childTaskResult;
	}
	
	public void setChildTaskResult(TaskResult result) {
		childTaskResult = result;
	}

	private TaskResult executeActivity(Activity activity) {
		TaskResult result;
		if (activity instanceof Gateway) {
			result = executeGateway((Gateway) activity);
		} else {
			result = executeTask((Task) activity);
		}
		return result;
	}

	private TaskResult executeGateway(Gateway gateway) {
		Variables variables = new Variables();

		TaskResult result = gateway.exec(this, variables, null);

		// if (result.getStatus() == TaskResult.Status.EXCLUSIVE) {
		// result = executeActivity((Activity) result.getResult());
		// }

		return result;
	}

	private TaskResult executeTask(Task task) {

		String stage = "input";
		try {

			logger.log(Level.DEBUG, String.format("Executing task %s", task.getName()));
			Variables variables = new Variables();
			Mapper mapper = task.getMapper();

			if (mapper != null)
				mapper.inputTransform(this, variables);

			stage = "execute";

			TaskResult taskResult = task.exec(this, variables, null);

			if (taskResult.getStatus() == TaskResult.Status.OK) {
				variables.setVariable(MapperConstants.RETURN, taskResult.getResult());

				stage = "output";
				if (mapper != null)
					mapper.outputTransform(this, variables);
			}

			return taskResult;
		} catch (Exception e) {
			logger.log(Level.ERROR, String.format("Failed to execute the %s mapping for task %s", stage, task.getName()), e);
			return new ExceptionResult(e);
		}

	}

	public void setCurrentTask(Activity task) {
		currentTask = task;
	}

	public Activity getCurrentTask() {
		return currentTask;
	}

	public ProcessResultMode processTaskResult(Task task, TaskResult taskResult, List<Job> jobs) {
		ProcessResultMode resultMode;
		ProcessStatus status = ProcessStatus.RUNNING;

		boolean isCheckpointEnabled = task.isCheckpointEnabled();
		boolean unlock = false;
		boolean removeHandles = false;

		switch (taskResult.getStatus()) {
		case OK: {
			jobs.add(this);
			resultMode = ProcessResultMode.CONTINUE_SYNC;
			break;
		}

		case EXCLUSIVE: {
			jobs.add(this);
			resultMode = ProcessResultMode.CONTINUE_SYNC;
			setNextTask((Task) taskResult.getResult());
			setLastTask(null);
			return resultMode;

		}

		case LOOP_WAIT: {
			status = ProcessStatus.WAIT;
			resultMode = ProcessResultMode.STOP_CURRENT;
			isCheckpointEnabled = true;
			removeHandles = true;
			unlock = true;
			break;
		}
		case LOOP_CONTINUE: {
			Object tresult = taskResult.getResult();
			if (tresult != null && tresult instanceof Job && tresult != this) {
				jobs.add((Job) tresult);
				resultMode = ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT;
			} else {
				resultMode = ProcessResultMode.CONTINUE_SYNC;
			}
			break;
		}

		case STARTEVENT: {
			JobImpl createdJob = (JobImpl) ((StartEventResult) taskResult).getCreatedJob();
			jobs.add(createdJob);
			resultMode = ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT;
			Event event = (Event) ((StartEventResult) taskResult).getCausal();
			/*
			 * The StartEvent task does JobImpl.assertObject() which does
			 *  JobImpl.recordEvent() with the current task(StartEvent)
			 *  No need to do it twice, also fixing the HashCode+Equals 
			 *  of the PendingEvents
			 */
//			if (event.getTTL() != 0)
//				createdJob.recordEvent(task, ((StartEventResult) taskResult).getCausal());

			createdJob.setCurrentTask(task);
			isCheckpointEnabled = false;
			return resultMode;
		}

		case FORK: {
			ForkResult forkResult = (ForkResult) taskResult;
			jobs.addAll(forkResult.getForkedProcess());
			resultMode = ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT;
			isCheckpointEnabled = false; // Already checkpointed

			break;
		}

		case MERGECOMPLETE: {
			Job mergedJob = ((MergeCompleteResult) taskResult).getMergedJob();

			if (mergedJob == this) { // If the Parallel Gateway acts like a pure
										// split with one inbound transition.
				resultMode = ProcessResultMode.CONTINUE_SYNC;
				break;
			}

			jobs.add(mergedJob);
			resultMode = ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT;
			isCheckpointEnabled = true; // The child which merged is complete
										// now.
			unlock = true;
			status = ProcessStatus.COMPLETE; // the child job is complete. We
												// should also unlock the
												// children. Only the last one
												// is complete. This should go
												// to Parallel Gateway

			break;

		}
		case ERROREXCEPTION:
		case ESCALTIONEXCEPTION: {
			// Find the compensation or error task and probably if it
			// interrupting or not.
			// ((ExceptionResult)taskResult).getThrowable().printStackTrace();
			
			ExceptionResultHandler.handleExceptionResult(this, task, ((ExceptionResult)taskResult),jobs);
			logger.log(Level.ERROR, ((ExceptionResult) taskResult).getThrowable(), String.format("Exception processing the task: %s", task.getName()));
			joinwithParent(jobs,taskResult);
			resultMode = this.jobContext.getParent() != null? ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT: ProcessResultMode.STOP_CURRENT;
			status = ProcessStatus.ERROR; // considering the job is completed for cleanup
			isCheckpointEnabled = true;
			removeHandles = true;
			unlock = true;

			break;
		}

		case CALLACTIVITY: {
			CallActivityResult result = (CallActivityResult) taskResult;
			jobs.add(result.getCalledActivityContext());
			resultMode = ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT;
			status = ProcessStatus.WAITCALLACTIVITY;
			isCheckpointEnabled = true;
			unlock = false;
			break;
		}

		case WAITFORJOB: {
			resultMode = ProcessResultMode.STOP_CURRENT;
			isCheckpointEnabled = true;
			status = ProcessStatus.WAIT;
			removeHandles = true;
			break;
		}

		case WAITFOREVENT:
		case WAITFORJOIN: {
			status = ProcessStatus.WAIT;
			resultMode = ProcessResultMode.STOP_CURRENT;
			isCheckpointEnabled = true;
			removeHandles = true;
			unlock = true;
			break;

		}
		case CANCELJOB:
		case CANCELTASK: {
			status = ProcessStatus.CANCEL;
			resultMode = ProcessResultMode.STOP_CURRENT;
			isCheckpointEnabled = true;
			break;
		}

		case TIMEOUT: {
			resultMode = ProcessResultMode.STOP_CURRENT;
			isCheckpointEnabled = true;
			status = ProcessStatus.TIMEOUT;
			break;
		}

		case COMPLETE:

			isCheckpointEnabled = joinwithParent(jobs,taskResult);
			status = ProcessStatus.COMPLETE;
			resultMode = ProcessResultMode.CONTINUE_ASYNC_ANDSTOP_CURRENT;
			removeHandles = true;
			unlock = true;
			break;

		default:
			resultMode = ProcessResultMode.STOP_CURRENT;
			break;
		}

		try {
			setLastTask(task);
			jobContext.setProcessStatus(status);
			isCheckpointEnabled = isCheckpointEnabled && (this instanceof JobImpl);
			if (isCheckpointEnabled)
				checkpoint(removeHandles);
		} catch (Exception ex) {

			logger.log(Level.ERROR, ex, String.format("Error while checkpointing the Job[%s:%s] at task:%s", jobContext.getProcessTemplate().getProcessName()
					.getSimpleName(), jobContext.getExtId(), task.getName()));
			resultMode = ProcessResultMode.STOP_CURRENT;
			unlock = true;

		}

		if (unlock) {
			if (jobContext instanceof StarterJobContext) {
				// The unlocking is done at StartEvent exception handler where it has the right jobContext
			} else {
				EntityDao dao = processAgent.getCluster().getDaoProvider().getEntityDao(jobContext.getClass());
				dao.unlock(jobContext.getId());
			}
		}

		return resultMode;
	}

	public boolean interpretProcessResultMode(ProcessResultMode resultMode, List<Job> jobs) {
		boolean bProcessing = false;

		switch (resultMode) {
		case CONTINUE_SYNC:
			bProcessing = true;
			break;
		case STOP_CURRENT:
			bProcessing = false;
			break;
		case CONTINUE_ASYNC:
			bProcessing = false;
			processAgent.getProcessExecutor().submitJob(this);
			break;

		case CONTINUE_ASYNC_ANDSTOP_CURRENT:
			for (Job job : jobs) {
				processAgent.getProcessExecutor().submitJob(job);
			}
			bProcessing = false;
			break;

		}
		return bProcessing;
	}

	private boolean joinwithParent(List<Job> jobs, TaskResult taskResult) {
		boolean isCheckpointEnabled = true;
		JobContext parentData = (JobContext) this.jobContext.getParent();

		if (parentData == null) {
			// this is the parent therefore no other job is waiting for this 
			// job to finish, it can be checkpointed
			return isCheckpointEnabled;
		}

		if (parentData.getProcessStatus() == ProcessStatus.WAITCALLACTIVITY) { // Could be waiting for Merge too.
			
																				
			// Current child job needs cleanup as execution control gets transferred to parent job
			JobImpl parentJob = new JobImpl(parentData, this.getProcessAgent());
			// copy pending events when changing jobs
			parentJob.setPendingEvents(getPendingEvents());
			if(taskResult.getStatus() == Status.COMPLETE) {
				// map output of call activity only when it complete
				joinOnCallActivity(parentJob);
			} else { 
				// else it is an error condition joinwithparent
				parentJob.setChildTaskResult(taskResult);
			}

			EntityDao dao = this.processAgent.getCluster().getDaoProvider().getEntityDao(parentData.getClass());
			dao.lock(parentData.getId(), -1);

			jobs.add(parentJob);
		} else {
			isCheckpointEnabled = false;
		}

		return isCheckpointEnabled;

	}

	private void joinOnCallActivity(JobImpl parentJob) {

		try {

			final Variables variables = new Variables();
			final Task task = parentJob.getLastTask();
			if(task instanceof DefaultLoopTask)
				((DefaultLoopTask)task).incrementAndSaveCounter(parentJob, variables);
			Mapper mapper = task.getMapper();
			variables.setVariable(MapperConstants.RETURN, this.getJobContext());
			variables.setVariable(MapperConstants.JOB, parentJob.getJobContext());
			if (mapper != null)
				mapper.outputTransform(parentJob, variables);

		} catch (Exception e) {
			logger.log(Level.ERROR, "Exception while processing the outputTransform", e);
		}
		return;
	}

	@Override
	public String getTransitionName() {
		return transitionName;
	}
	
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	
	
	
	@Override
	public void checkpoint(boolean removeHandles) throws Exception {
		ProcessChangeListManager processChangeListManager = new ProcessChangeListManager();
		DistributedCacheBasedStore objManager = (DistributedCacheBasedStore) getProcessAgent().getRuleSession().getObjectManager();
		RtcOperationList opList = new RtcOperationList(true);
		boolean isComplete = this.jobContext.getProcessStatus() == ProcessStatus.COMPLETE ||
				 this.jobContext.getProcessStatus() == ProcessStatus.ERROR ;
		boolean checkDuplicates = processAgent.getProcessAgentConfiguration().isDuplicateCheckOn();

		processChangeListManager.traverseConceptGraphForChanges(this.jobContext, objManager, opList, isComplete,checkDuplicates,this.retractedElements.values());
		ArrayList<PendingEvent> deletedEvents = new ArrayList<PendingEvent>();
		ArrayList<JobContext> deletedCompletedJobs = new ArrayList<JobContext>();
		
		Map<Long,String> peMap = new HashMap<Long,String>();
		for (Entry<String, Set<PendingEvent>> entry : pendingEvents.entrySet()) {
			for (PendingEvent pendingEvent : entry.getValue()) {

				if (pendingEvent.getState() == PendingEventState.ASSERT) {
					SimpleEvent se = (SimpleEvent) pendingEvent.getEvent();
					BaseHandle handle = (BaseHandle) objManager.getAddEventHandle(se, false);
					opList.setRtcAsserted(handle);
					pendingEvent.acknowledge();
				} 
				if(isComplete ||removeHandles ) {
					pendingEvent.consume();
				}
				if (pendingEvent.getState() == PendingEventState.CONSUMED && !( this instanceof StarterJob)) {
					SimpleEvent se = (SimpleEvent) pendingEvent.getEvent();
					BaseHandle handle = (BaseHandle) objManager.getAddEventHandle(se, false);
					opList.setRtcDeleted(handle);
					deletedEvents.add(pendingEvent);
				} 
				peMap.clear();
				PropertyArrayString peMapArr= this.jobContext.getPendingEventMap();
				PendingEventHelper.convertPropertyArrayToMap(peMapArr,peMap);
				if(pendingEvent.getState() == PendingEventState.CONSUMED){
					// remove from the map
					peMap.remove(pendingEvent.getId());
				} else {
					// add to the map
					long eventId = pendingEvent.getId();
					if(!peMap.containsKey(eventId) && !( this instanceof StarterJob)) {
						peMap.put(eventId,String.format(PendingEventHelper.PENDING_EVENT_VALUE_FORMAT,pendingEvent.getState().name(),entry.getKey()));
					}
				}
				PendingEventHelper.convertMapToPropertyArray(peMap,peMapArr);
				
			}
		}
		
		for(Set<JobContext> cjobs:this.completedJobs.values()) {
			for(JobContext jc:cjobs) {
				processChangeListManager.traverseConceptGraphForChanges(jc, objManager, opList, true,checkDuplicates,Collections.EMPTY_LIST);
				deletedCompletedJobs.add(jc);
				/**
				 * The handle for the completed job from parallel gw paths needs to be cleared too, because if the same start 
				 * event repeats the parallel gw with the same keys, it results in DuplicateIdException
				 */
				Iterator itr = opList.nonClearingIterator();
				while (itr.hasNext()) {
					BaseHandle handle = (BaseHandle) itr.next();

					if (handle.isRtcDeleted() && handle.getObject().equals(jc))
						objManager.removeHandleForCacheOnly(handle, handle.getObject());
				}
			}
		}
		
		if (removeHandles) {
			Iterator itr = opList.nonClearingIterator();
			while (itr.hasNext()) {
				BaseHandle handle = (BaseHandle) itr.next();

				if (handle.isRtcDeleted()) {
					objManager.removeHandleForCacheOnly(handle, handle.getObject());
				}
			}
		}
		
		((ProcessAgentImpl) processAgent).applyChanges(opList, removeHandles, false);

		for (PendingEvent pe : deletedEvents) {
			this.removePendingEvent(pe);
		}
		for(JobContext jc:deletedCompletedJobs) {
			this.removeCompletedJob(jc);
		}
		this.retractedElements.clear();

	}

	public static void setCurrentJob(JobImpl pci) {
		currentJobContexts.set(pci);
	}

	public static Job getCurrentJob() {
		return currentJobContexts.get();
	}

	@Override
	public void addAction(AgentAction action) {
		if (actions == null) {
			// linked list is type used in AgentActionManager
			actions = new LinkedList();
		}
		actions.add(action);
	}

	@Override
	public Collection<AgentAction> removeAll() {
		Collection<AgentAction> ret = actions;
		actions = null;
		return ret;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("{transitionName='").append(transitionName).append('\'');
		sb.append(", processAgent=").append(processAgent);
		sb.append(", processData=").append(jobContext);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void removePendingEvents(String taskName) {
		Set<PendingEvent> pendingSet = pendingEvents.get(taskName);
		for (PendingEvent pe : pendingSet) {
			pe.consume();
		}
	}

	/**
	 * Refactored it later to JobStore or JobPreprocessContext
	 * 
	 * @param object
	 */
	public void retractObject(Object object) {
		if (object instanceof Event) {
			PendingEvent pendingEvent = getPendingEvent((Event) object);
			
			if (pendingEvent != null) {
				if (pendingEvent.getState() == PendingEventState.ASSERT) {
					pendingEvent.acknowledge();
				}
				pendingEvent.consume();
			}
		} else if(object instanceof Element){
			DistributedCacheBasedStore objManager = (DistributedCacheBasedStore) getProcessAgent().getRuleSession().getObjectManager();
			BaseHandle baseHandle = null; 
            if(object instanceof Element && !(object instanceof JobContext)){
            	recordRetraction((Element) object);
            }
		}
	}
	public void retractIfAbsent(Object object) {
		if (object instanceof Event) {
			PendingEvent pendingEvent = getPendingEvent((Event) object);
			if(pendingEvent == null) {
				recordEvent(this.currentTask, (Event) object);
				pendingEvent = getPendingEvent((Event) object);
			}
			if (pendingEvent != null) {
				if (pendingEvent.getState() == PendingEventState.ASSERT) {
					pendingEvent.acknowledge();
				}
				pendingEvent.consume();
			}
		}
	}

	public void assertObject(Object object) throws DuplicateExtIdException {
		if (object instanceof Event) {
			recordEvent(this.currentTask, (Event) object);
		}
	}

	private PendingEvent getPendingEvent(Event event) {

		for (Set<PendingEvent> events : pendingEvents.values()) {
			for (PendingEvent pe : events) {
				if (pe.getEvent().getId() == event.getId())
					return pe;
			}
		}
		return null;
	}
	

	
	private void removeCompletedJob(JobContext jc) {
		for (Set<JobContext> cjobs : completedJobs.values()) {
			if (cjobs.remove(jc))
				break;
		}
	}

	private void removePendingEvent(PendingEvent event) {

		for (Set<PendingEvent> events : pendingEvents.values()) {
			if (events.remove(event))
				break;
		}
	}

	public Collection getAllPendingEvents() {
		HashSet<Event> events = new HashSet<Event>();
		for (Set<Job.PendingEvent> pendingEventSet : pendingEvents.values()) {
			
			for (Job.PendingEvent pe : pendingEventSet) {
				
				if (pe.getState() != Job.PendingEventState.CONSUMED)
					events.add(pe.getEvent());
			}
		}
		return events;
	}


	/**
	 * traverse the Job graph, and based on the version of the concept fill in
	 * the list. concept.getVersion() == 0 is assertedList concept.getVersion()
	 * > 0 is modified. We will have to mark the concept as deleted if it is
	 * being retracted from Script task.
	 * 
	 * @param assertedList
	 * @param loadedList
	 * @param reevaluationList
	 */
	public void entitiesForRuleEvaluation(Collection<Entity> assertedList, Collection<Entity> loadedList, Collection<Entity> reevaluationList) throws Exception {

		ProcessChangeListManager pclm = new ProcessChangeListManager();
		pclm.traverseConceptGraphForChanges(this.getJobContext(), assertedList, loadedList);

	}

	/**
	 * Checkpoint control dao tuples
	 * @param removeHandles
	 * @param tuple 
	 * @throws Exception
	 */
	public void recordControlDaoTuple(ObjectBean cdt) throws Exception {
		DistributedCacheBasedStore objManager = (DistributedCacheBasedStore) getProcessAgent().getRuleSession().getObjectManager();
		RtcOperationList opList = new RtcOperationList(true);
		
		TypeInfo tinfo = ((RuleSessionImpl)getProcessAgent().getRuleSession()).getWorkingMemory().getTypeInfo(cdt.getType());
		if(cdt.getBeanOp()  == BeanOp.BEAN_CREATED) {
			BaseHandle handle = new ObjectBeanHandle(tinfo,cdt);
			opList.setRtcAsserted(handle);
		} else if(cdt.getBeanOp() == BeanOp.BEAN_UPDATED) {
			BaseHandle handle = new ObjectBeanHandle(tinfo,cdt);
			opList.setRtcModified(handle);
		} else if(cdt.getBeanOp() == BeanOp.BEAN_DELETED) {
			BaseHandle handle = new ObjectBeanHandle(tinfo,cdt);
			opList.setRtcDeleted(handle);
		}

		((ProcessAgentImpl) processAgent).applyChanges(opList, false, false);
	}
	

}
