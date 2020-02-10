package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;

import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.gateways.ParallelGateway;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplateRegistry;
import com.tibco.cep.bpmn.runtime.utils.Constants;
import com.tibco.cep.bpmn.runtime.utils.ProcessHelperUtils;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.exception.impl.BEExceptionImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Until Intermediate Event and Event Sub processes are implement, use the
 * ExceptionResultHandler to handle gracefully any exceptions and errors 1. Give
 * the user a chance to handle the error via rule function 2. if the Job Context
 * is a normal context i.e. not a subproces,call activity, parallel gw split job
 * then it needs to be cleaned from cache/backingstore to allow the user to
 * re-send starting event and avoid duplicate exceptions 3. if the Job Context
 * is one of subproces,call activity, parallel gw split job we need to ensure
 * that the merge paths/tuples are completed , parent jobs are completed. 4. if
 * the failing task is a loop task then loop tuples/tables need to be cleaned
 * out. 5. The ExceptionHandler function signature is void handlerFn(String
 * taskName,Concept jobContext,Exception e)
 */
public class ExceptionResultHandler {

	private static final String TASK_NAME = "taskName";
	private static final String JOB_CONTEXT = "jobData";
	private static final String TASK_EXCEPTION = "taskException";
	private static final ExpandedName PROCESS_EXCEPTION_NM = ExpandedName.makeName(RDFTnsFlavor.BE_NAMESPACE, TASK_EXCEPTION);
	protected static Logger logger = LogManagerFactory.getLogManager().getLogger(ExceptionResultHandler.class);
	protected static Level LEVEL = Constants.DEBUG ? Level.WARN : Level.DEBUG;

	public static com.tibco.cep.designtime.model.rule.RuleFunction getExceptionHandlerFunction(RuleServiceProvider rsp, ProcessTemplate template) {
		String fnURI = template.getExceptionHandler().trim();
		return rsp.getProject().getOntology().getRuleFunction(fnURI);
	}

	public static ProcessModel getExceptionHandlerProcess(ProcessAgent pa, ProcessTemplate exceptionProcesstemplate) {

		final Ontology o = pa.getRuleServiceProvider().getProject().getOntology();
		String procURI = exceptionProcesstemplate.getExceptionHandler().trim();
		return ProcessRuleSession.getCalledProcessByUri(o, procURI);

	}

	public static Entity getHandlerProcessOrFunction(ProcessAgent pa, ProcessTemplate exceptionProcesstemplate) {
		final Ontology ontology = pa.getRuleServiceProvider().getProject().getOntology();
		String procOrFnURI = exceptionProcesstemplate.getExceptionHandler().trim();

		if (procOrFnURI.endsWith(CommonIndexUtils.PROCESS_EXTENSION)) {
			int index = procOrFnURI.indexOf(CommonIndexUtils.PROCESS_EXTENSION);
			procOrFnURI = procOrFnURI.substring(0, index - 1);
		} else if (procOrFnURI.endsWith(CommonIndexUtils.RULEFUNCTION_EXTENSION)) {
			int index = procOrFnURI.indexOf(CommonIndexUtils.RULEFUNCTION_EXTENSION);
			procOrFnURI = procOrFnURI.substring(0, index - 1);
		}
		return ontology.getEntity(procOrFnURI);
	}

	public static void handleExceptionResult(Job job, Task task, ExceptionResult taskResult, List<Job> jobs) {
		ProcessAgent pa = job.getProcessAgent();
		final JobContext jobContext = job.getJobContext();
		final long processId = jobContext.getId();
		final ProcessTemplate exceptionProcesstemplate = jobContext.getProcessTemplate();
		try {

			Entity e = getHandlerProcessOrFunction(pa, exceptionProcesstemplate);
			if (e instanceof com.tibco.cep.designtime.model.rule.RuleFunction) {
				handleExceptionResultFunction(job, task, taskResult);
			} else if (e instanceof ProcessModel) {
				handleExceptionResultProcess(job, task, taskResult,exceptionProcesstemplate.isAsyncExceptionHandler());
			}

			// clean up Merge if the job ends up in a parallel merge
			ParallelGateway pg = null;
			Map<Task,LinkedList<Task>> gwPathsMap = new HashMap<Task,LinkedList<Task>>();
			List<Task> directPath = null;
			Comparator<LinkedList<Task>> scomp = new Comparator<LinkedList<Task>>() {
				@Override
				public int compare(LinkedList<Task> o1, LinkedList<Task> o2) {
					return o1.size()<o2.size() ? -1 : (o1.size() == o2.size() ? 0 : 1);
				}
				
			};
			
			for (Task t : exceptionProcesstemplate.allTasks()) {
				if (t instanceof ParallelGateway) {
					LinkedList<LinkedList<Task>> gwpaths =  getParallelGatewayInPath(task, t);
					if(!gwpaths.isEmpty()){
						Collections.sort(gwpaths,scomp);
						gwPathsMap.put(t, gwpaths.get(0));
					}
				}
			}
			Comparator<Map.Entry<Task,LinkedList<Task>>> mcomp = new Comparator<Map.Entry<Task,LinkedList<Task>>>() {

				@Override
				public int compare(Entry<Task, LinkedList<Task>> o1, Entry<Task, LinkedList<Task>> o2) {
					LinkedList<Task> l1 = o1.getValue();
					LinkedList<Task> l2 = o2.getValue();
					int l1size = l1 == null ? 0 : l1.size();
					int l2size = l2 == null ? 0 : l2.size();
					return l1size<l2size ? -1 : (l1size == l2size ? 0 : 1);
				}
				
			};
			List<Map.Entry<Task, LinkedList<Task>>> gps = new ArrayList<Map.Entry<Task, LinkedList<Task>>>(gwPathsMap.entrySet());
			Collections.sort(gps,mcomp);
			if(gps.size() > 0){
				pg = (ParallelGateway) gps.get(0).getKey();
			}
			try {
				if (pg != null) {
					TaskResult res = pg.join(job, new Variables(),true);
					if(res.getResult() instanceof JobImpl) {
						JobImpl resJob = (JobImpl) res.getResult();
						String transitionName = null;
						if(pg.getOutgoingTransitions().length > 0) {
							transitionName = pg.getOutgoingTransitions()[0].getName();
						} else if(pg.getIncomingTransitions().length > 0) {
							transitionName = pg.getIncomingTransitions()[0].getName();
						}
						resJob.setTransitionName(transitionName);
						// let the job continue 
						pa.getProcessExecutor().submitJob(resJob);
					}
				}
			} finally {

			}

			// clean up loop tuples if the task is a loop
			if (task instanceof DefaultLoopTask) {
				((DefaultLoopTask) task).setComplete(job, true);
			}

		} catch (Exception e) {
			logger.log(Level.ERROR, "Failed to handle ExceptionResult event for process:%s and task: %s", e, processId, task.getName());
		}
	}

	public static void handleExceptionResultProcess(Job job, Task task, ExceptionResult taskResult, boolean isAsync) throws Exception {
		ProcessAgent pa = job.getProcessAgent();
		RuleServiceProvider rsp = pa.getRuleServiceProvider();

		final JobContext jobContext = job.getJobContext();
		final long processId = jobContext.getId();
		// final String processExtId = jobContext.getExtId();
		// final ExpandedName processExpandedName =
		// jobContext.getExpandedName();

		final ProcessTemplate exceptionProcesstemplate = jobContext.getProcessTemplate();
		// final ProcessName procName =
		// exceptionProcesstemplate.getProcessName();

		ProcessModel handlerProcessModel = getExceptionHandlerProcess(pa, exceptionProcesstemplate);
		ProcessTemplate handlerProcesstemplate = ProcessTemplateRegistry.getInstance().getProcessTemplate(handlerProcessModel);
		JobContext handlerJobData = handlerProcesstemplate.newProcessData();

		Task startTask = ProcessRuleSession.getStartTask(handlerProcessModel);

		if (startTask != null) {
			Variables nvars = new Variables();
			JobImpl handlerProcessJob = new JobImpl(handlerJobData, pa);

			Property paName = handlerJobData.getProperty(TASK_NAME);
			if (paName != null && paName instanceof PropertyAtomString) {
				String task_name = task.getName();
				((PropertyAtomString) paName).setString(task_name);
			}
			Property paJob = handlerJobData.getProperty(JOB_CONTEXT);
			if (paJob != null && paJob instanceof PropertyAtomString) {
				String exceptionJobData = ObjectHelper.serializeUsingDefaults(jobContext);
				((PropertyAtomString) paJob).setString(exceptionJobData);
			}
			Property paException = handlerJobData.getProperty(TASK_EXCEPTION);
			if (paException != null && paException instanceof PropertyAtomString && taskResult.getThrowable() != null) {
				XiNode node = XiSupport.getXiFactory().createElement(PROCESS_EXCEPTION_NM);
				BEExceptionImpl.wrapThrowable(taskResult.getThrowable()).toXiNode(node);
				String exceptionXML = XiSerializer.serialize(node);
				String exceptionXMLUnescapeStr= StringEscapeUtils.unescapeXml(exceptionXML);
				((PropertyAtomString) paException).setString(exceptionXMLUnescapeStr);
			}

			startTask.getMapper().outputTransform(job, nvars);
			handlerProcessJob.setCurrentTask(startTask);
			handlerProcessJob.setLastTask(startTask);
			ProcessRuleSession prs = (ProcessRuleSession) RuleSessionManager.getCurrentRuleSession();
			prs.invokeProcess(handlerProcessJob, isAsync);
			//handlerProcessJob.run(); // synchronous
			// pa.getProcessExecutor().submitJob(handlerProcessJob);
		}

	}

	public static void handleExceptionResultFunction(Job job, Task task, ExceptionResult taskResult) {
		ProcessAgent pa = job.getProcessAgent();
		RuleServiceProvider rsp = pa.getRuleServiceProvider();

		final JobContext jobContext = job.getJobContext();
		final long processId = jobContext.getId();
		// final String processExtId = jobContext.getExtId();
		// final ExpandedName processExpandedName =
		// jobContext.getExpandedName();

		final ProcessTemplate template = jobContext.getProcessTemplate();
		// final ProcessName procName = template.getProcessName();

		com.tibco.cep.designtime.model.rule.RuleFunction ruleFn = getExceptionHandlerFunction(rsp, template);

		if (ruleFn != null) {

			RuleSession rs = pa.getRuleSession();

			Object[] args = new Object[] { task.getName(), // String taskName
					jobContext, // Concept jobContext
					BEExceptionImpl.wrapThrowable(taskResult.getThrowable()) // Exception
			};

			// com.tibco.cep.kernel.model.rule.RuleFunction rfInstance =
			// ((BEClassLoader)rs.getRuleServiceProvider().getTypeManager()).getRuleFunctionInstance(ruleFn.getFullPath());

			Object obj = rs.invokeFunction(ruleFn.getFullPath(), args, true);
		}

	}

	private static LinkedList<LinkedList<Task>> getParallelGatewayInPath(Task exceptionTask, Task gwTask) {

		LinkedList<LinkedList<Task>> paths = new LinkedList<LinkedList<Task>>();
		ProcessHelperUtils.dfs(exceptionTask, gwTask, new LinkedList<Task>(), paths);
		return paths;

	}

}
