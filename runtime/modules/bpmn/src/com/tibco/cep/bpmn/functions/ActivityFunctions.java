package com.tibco.cep.bpmn.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.PROCESS;
import com.tibco.be.model.functions.Enabled;

import java.util.LinkedList;
import java.util.List;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskFactory;
import com.tibco.cep.bpmn.runtime.activity.gateways.ParallelGateway;
import com.tibco.cep.bpmn.runtime.activity.tasks.MoveToTask;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.utils.ProcessHelperUtils;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.locks.LockManager;

/**
 * @author pdhar
 *
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Process Orchestration",
        category = "Process.Activity",
        synopsis = "Process Activity Functions")

public class ActivityFunctions {
	
	
	
	private final static int MOVE_SUCCESS = 0;
	private final static int MOVE_NOPROCESS = 1;
	private final static int MOVE_TIMEOUT = 2;
	private final static int MOVE_INVALID = 3;
	@BEFunction(
		name = "moveTo",
        synopsis = "Move the job to the specified activityName",
        signature = "int moveTo(Object object, String activityName,[int timeOutInMillis])", 
        params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "object", type = "Object", desc = "Job Context"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "activityName", type = "String", desc = "Specifies the activity name"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeOutInMillis", type = "int", desc = "&lt;optional&gt; time out in milliseconds to get the job context lock")
		},
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "error/success codes. 0-Success. 1-Process Not Found. 2-Timeout. 3-Invalid Move."),
        version = "1.0", 
        see = "", 
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Jumps to specified activity",
        cautions = "none",
        fndomain = {ACTION, CONDITION, PROCESS},
        example = "")
	public static int moveTo(Object object, String activityName,Object ... options) {

		RuleSession currentRuleSession = (RuleSession) RuleSessionManager
				.getCurrentRuleSession();
		ProcessRuleSession processRuleSession = null;
		if (currentRuleSession instanceof ProcessRuleSession) {
			processRuleSession = (ProcessRuleSession) currentRuleSession;
		} else {
			throw new RuntimeException(
					"moveTo funtion not supported for the current RuleSession");
		}

		JobContext jobContext = null;
		if (object instanceof Job) {
			jobContext = ((Job) object).getJobContext();
		} else if (object instanceof JobContext) {
			jobContext = (JobContext) object;
		} else if (object instanceof String) {
			String extId = (String) object;
			jobContext = (JobContext) processRuleSession.getObjectManager()
					.getElement(extId);
		}

		Task lastTaskExecuted = jobContext.getLastTaskExecuted();

		Task nextTask = jobContext.getProcessTemplate().getTask(activityName);
		
		int timeOutInMillis = -1; // default is infinite
		if(options != null && options.length > 0) {
			if(int.class.isAssignableFrom(options[0].getClass()) || Integer.class.isAssignableFrom(options[0].getClass()) ) {
				timeOutInMillis = (Integer) options[0];
			} else {
				throw new IllegalArgumentException("Incorrect timeout parameter");
			}
		}	

		if (isValidMove(jobContext, lastTaskExecuted, nextTask)) {
			LockManager lockManager = ((ObjectBasedStore) processRuleSession
					.getObjectManager()).getLockManager();
			boolean locked = false;
			try {
				locked = lockManager.lock(jobContext.getExtId(), timeOutInMillis,
						LockManager.LockLevel.LEVEL2);

				JobContext pv = (JobContext) processRuleSession
						.getObjectManager().getElement(jobContext.getExtId());

				if (pv != null) {

					try {
						final MoveToTask movetoTask = (MoveToTask) TaskFactory
								.getInstance()
								.newTask(
										BpmnMetaModelConstants.MOVE_TO_TASK
												.toString(),(jobContext.getProcessTemplate().getName()+".DummyTask"), false);

						movetoTask.init(lastTaskExecuted, nextTask);
						JobImpl job = new JobImpl(pv,
								processRuleSession.getProcessAgent()) {
							boolean moved = false;

							public Task getLastTask() {
								if (!moved) {
									moved = true;
									return movetoTask;
								} else
									return super.getLastTask();
							}
						};
						job.setCurrentTask(nextTask);
						job.setLastTask(movetoTask);
						processRuleSession.getProcessAgent()
								.getProcessExecutor().submitJob(job);
					} catch (Exception e) {
						e.printStackTrace();
					}

					return MOVE_SUCCESS;
				} else {
					return MOVE_NOPROCESS;
				}

			} finally {
				if (locked) {
					lockManager.unlock(jobContext.getExtId());
				} else {
					return MOVE_TIMEOUT;
				}
			}
		} else {

			return MOVE_INVALID;
		}

	}

	private static boolean isValidMove(JobContext jobContext, Task startTask,
			Task endTask) {

		if (jobContext == null || startTask == null || endTask == null) {
			return false;
		}

		if (JobImpl.getCurrentJob().getJobContext().equals(jobContext))
			return false;

		if (endTask instanceof ParallelGateway
				&& (((ParallelGateway) endTask).isConverging() || ((ParallelGateway) endTask)
						.isMixed())) {
			return false;
		}
		LinkedList<LinkedList<Task>> paths = new LinkedList<LinkedList<Task>>();
		ProcessHelperUtils.dfs(startTask, endTask, new LinkedList<Task>(), paths);

		boolean pathFound = false;

		for (List<Task> taskPath : paths) {

			int gatewayCount = 0;
			for (Task task : taskPath) {
				if (task.getTaskModel().isInstanceOf(
						BpmnModelClass.PARALLEL_GATEWAY)) {
					if (((ParallelGateway) task).isConverging()) {
						gatewayCount--;
					}
					if (((ParallelGateway) task).isDiverging()) {
						gatewayCount++;
					}
				}
			}
			if (gatewayCount == 0 || gatewayCount == 1) {
				pathFound = true;
				break;
			}

		}

		return pathFound;
	}

	


	@BEFunction(
			name = "getCurrentActivity",
			synopsis = "Returns the currently executing activity",
			signature = "Object getCurrentActivity()",
			params = { },
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The currently executing activity"),
			version = "1.0",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Returns the currently executing activity name",
			cautions = "none",
			fndomain = {ACTION, CONDITION, PROCESS},
			example = ""
			)
	public static Object getCurrentActivity() {

        Job job = JobImpl.getCurrentJob();

        return job != null ? job.getCurrentTask() : null;

	}
	

	@BEFunction(
			name = "getActivityByName",
			synopsis = "Returns the activity for the given name",
			enabled = @Enabled(value=false),
			signature = "Object getActivityByName(String activityName)",
			params = { 
					@com.tibco.be.model.functions.FunctionParamDescriptor(name = "activityName", type = "String", desc = "Specifies the activity name")

			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The specified activity"),
			version = "1.0",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Returns the activity for the given name",
			cautions = "none",
			fndomain = {ACTION, CONDITION, PROCESS},
			example = ""
			)
	public static Object getActivityByName(String taskName) {

        Job job = JobImpl.getCurrentJob();
        final ProcessTemplate processTemplate = job.getJobContext().getProcessTemplate();
       	return processTemplate.getTask(taskName);

	}
	
	@BEFunction(
			name = "getActivityName",
			synopsis = "Returns the activity name for the given task",
			enabled = @Enabled(value=false),
			signature = "String getActivityName(Object task)",
			params = { 
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "task", type = "Object", desc = "Specifies the given activity")

			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The specified activity"),
			version = "1.0",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Returns the activity name",
			cautions = "none",
			fndomain = {ACTION, CONDITION, PROCESS},
			example = ""
			)
	public static String getActivityName(Task task) {
		RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();
		if(rsex instanceof ProcessRuleSession) {
			return task.getName();
		}
		throw new RuntimeException(String.format("The current Rule Session % is invalid for %() function call ", rsex.getName(),"getActivityName"));
	}
	
	
	

}
