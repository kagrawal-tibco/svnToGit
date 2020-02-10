package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TaskResult.Status;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.Triggerable;
import com.tibco.cep.bpmn.runtime.activity.mapper.Mapper;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.results.LoopTaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.StartEventResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.Job.PendingEvent;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessException;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.jxpath.objects.XObjectWrapper;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author Pranab Dhar
 * 
 *         This is a Loop Task Wrap
 * 
 */
public class DefaultLoopTask<T extends AbstractTask> extends AbstractTask implements LoopTask<T> {

	T loopTask;
	
	private LoopCharacteristics loopCharacteristics;
//	boolean hasCheckpoint = false;
	private Map<String, Set<PendingEvent>> pendingEvents;

	public DefaultLoopTask(T task) {
		this.loopTask = task;
	}

	public T getLoopTask() {
		return loopTask;
	}

	@Override
	public void init(InitContext context, Object... args) throws Exception {
		// initialize the internal task
		loopTask.init(context, args);
		setTaskModel(loopTask.getTaskModel());
		setContext(context, args);
		initTask();

		EObject lcObj = (EObject) getTaskModel().getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
		if (lcObj != null) {
			ROEObjectWrapper<EClass, EObject> lcw = EObjectWrapper.wrap(lcObj);
			if (lcw.isInstanceOf(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS)) {
				final boolean testBefore = (Boolean) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
				final EObject loopConditionExprObj = (EObject) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION);
				ROEObjectWrapper<EClass, EObject> condExpr = EObjectWrapper.wrap(loopConditionExprObj);
				final String loopConditionExpr = condExpr.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				final String loopConditionLang = condExpr.getAttribute(BpmnMetaModelConstants.E_ATTR_LANGUAGE);
				final String loopCountExpr = (String) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM);
				this.loopCharacteristics = new LoopCharacteristics() {

					@Override
					public boolean testBefore() {
						return testBefore;
					}

					@Override
					public String loopCountVarExpr() {
						return loopCountExpr;
					}

					@Override
					public String loopConditionExpr() {
						return loopConditionExpr;
					}
				};
			} else if (lcw.isInstanceOf(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS)) {
				final boolean testBefore = (Boolean) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
				// final boolean testBefore = true;
				final EObject expr = lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION);
				final ROEObjectWrapper<EClass, EObject> body = ROEObjectWrapper.wrap(expr);
				final String loopConditionExpr = (String) body.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				final String loopIteratorExpr = (String) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT);
				final boolean sequential = (Boolean) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_SEQUENTIAL);
				final String cardinality = (String) lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CARDINALITY);
				EEnumLiteral bv = lcw.getEnumAttribute(BpmnMetaModelConstants.E_ATTR_BEHAVIOR);
				final MultiInstanceLoopCharacteristics.Behavior b = MultiInstanceLoopCharacteristics.Behavior.fromString(bv.getName());
				LoopVarType loopDataType = null;
				EObject eobj = lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_DATA_TYPE);
				if (eobj != null) {
					EObjectWrapper<EClass, EObject> eobjWrap = EObjectWrapper.wrap(eobj);
					boolean isConcept = false;
					String type = (String) eobjWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_VARIABLE_TYPE);
					String path = eobjWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_VARIABLE_PATH);
					if (type != null && type.equalsIgnoreCase("Object")) {
						type = path;
						isConcept = true;
					}

					final String loopVarTypStr = type;
					final boolean isLoopVarTypeConcept = isConcept;

					loopDataType = new LoopVarType() {

						@Override
						public String getType() {
							// TODO Auto-generated method stub
							return loopVarTypStr;
						}

						@Override
						public boolean isConcept() {
							// TODO Auto-generated method stub
							return isLoopVarTypeConcept;
						}
					};

				}
				final LoopVarType loopVarType = loopDataType;

				this.loopCharacteristics = new MultiInstanceLoopCharacteristics() {

					@Override
					public boolean testBefore() {
						return testBefore;
					}

					@Override
					public String loopCountVarExpr() {
						return loopIteratorExpr;
					}

					@Override
					public String loopConditionExpr() {
						return loopConditionExpr;
					}

					@Override
					public String loopCardinality() {
						return cardinality;
					}

					@Override
					public boolean isSequential() {
						return sequential;
					}

					@Override
					public Behavior getBehavior() {
						return b;
					}

					@Override
					public LoopVarType getLoopVarType() {
						return loopVarType;
					}

				};

			}
//			hasCheckpoint = getCheckpoint(loopTask);
		}
	}

	@Override
	public void initTransitions() throws Exception {
		loopTask.initTransitions();
	}

//	private boolean getCheckpoint(T loopTask) {
//		return loopTask.isCheckpointEnabled();
//	}

//	@Override
//	public boolean hasCheckpoint() {
//		return hasCheckpoint;
//	}
	
	

	@Override
	public boolean isAsyncExec() {
		return loopTask.isAsyncExec();
	}

	@Override
	public boolean isCheckpointEnabled() {
		return hasWait() || loopTask.isCheckpointEnabled();
	}

	@Override
	public Mapper getMapper() {
		// Loop task does not have its own mapper it calls the mapper of the
		// delegate task during the loop
		return null;
	}

	@Override
	public boolean hasWait() {
		return (loopTask instanceof CallActivityTask || 
				loopTask instanceof SubProcessTask || 
				loopTask instanceof ReceiveTask || isAsyncExec());
	}

	@Override
	public LoopCharacteristics getLoopCharacteristics() {
		return this.loopCharacteristics;
	}

	@Override
	public String getInputMapperString() {
		if (loopTask != null)
			return loopTask.getInputMapperString();
		else
			return null;
	}

	@Override
	public String getOutputMapperString() {
		if (loopTask != null) {
			return loopTask.getOutputMapperString();
		} else
			return null;
	}

	protected LoopTuple getCreateLoopTuple(Job job, Variables vars) throws Exception {
		LoopCharacteristics lc = getLoopCharacteristics();
		ProcessAgent pac = this.getInitContext().getProcessAgent();
		ControlDao<String, LoopTuple> loopTable = pac.getLoopCounterTuple();
		String loopKey = makeLoopKey(job, loopTask);
		String jobKey = makeJobKey(job);
		LoopTuple loopTuple = loopTable.get(loopKey);
		boolean changed = false;
		if (loopTuple == null) {
			if (vars != null) {
				loopTuple = new DefaultLoopTuple(jobKey, loopKey, loopTask.getName(), evalCounterMax(job, vars, lc));
				changed = true;
			} else {
				loopTuple = new DefaultLoopTuple(jobKey, loopKey, loopTask.getName());
				changed = true;
			}

		} else {
			if (!loopTuple.isInitialized() && vars != null) {
				loopTuple.initialize(evalCounterMax(job, vars, lc));
				changed = true;
//				loopTable.put(loopKey, loopTuple);
			}
		}
		if(changed) {
			loopTable.put(loopKey, loopTuple);
			if(isCheckpointEnabled()){
				((JobImpl)job).recordControlDaoTuple(loopTuple);
			}
		}
		return loopTuple;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask#execute(com.tibco
	 * .cep.bpmn.runtime.agent.Job, com.tibco.cep.bpmn.runtime.utils.Variables)
	 */
	@Override
	public TaskResult execute(Job job, Variables vars, Task ltask) {
		LoopCharacteristics lc = getLoopCharacteristics();
		ProcessAgent pac = this.getInitContext().getProcessAgent();
		String loopKey = null;
		try {
			loopKey = makeLoopKey(job, loopTask);
			if (logger.isEnabledFor(LEVEL)) {
				logger.log(LEVEL, "Executing " + this + " : " + vars + (job == null ? "" : " : " + job.getTransitionName()) + " join with loop key : "
						+ loopKey);
			}
			
			LoopTuple loopTuple = getCreateLoopTuple(job, vars);

			vars.setVariable(MapperConstants.LOOP_TUPLE, loopTuple);

			if (loopTuple.getCounterMax() > 0) {
				if (lc.testBefore() ||  hasWait()) {
					return testBefore(job, vars, lc);
				} else {
					return testAfter(job, vars, lc);
				}
			}

			return new DefaultResult(TaskResult.Status.OK, job);

		} catch (Exception e) {
			return new ExceptionResult(e);
		} finally {

		}
	}

	/**
	 * This function gets the nth index position tuple from a collection
	 * returned by the MI expression.
	 * 
	 * @param job
	 * @param vars
	 * @param mlc
	 * @param loopTuple
	 * @return
	 */
	private Object getLoopVarAtIndex(Job job, Variables vars, MultiInstanceLoopCharacteristics mlc, LoopTuple loopTuple) {

		String xpathExpr = mlc.loopCountVarExpr();
		if (xpathExpr == null)
			return 0;

		GlobalVariables gvars = getInitContext().getProcessAgent().getRuleServiceProvider().getGlobalVariables();
		JobContext ctx ;
		
		// For Receive Task MultiInstance Loop the Original Job COntext needs to be accessed For evaluating the loopVar 
		// This is not available in Starter Job impl
		JobContext checkPointedContext = getCheckPointedContext();
		if (checkPointedContext != null) {
			ctx = checkPointedContext;
		} else {
			ctx = job.getJobContext();
		}
		try {
			XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpr);
			String value = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
			List varNames = XSTemplateSerializer.getVariablesinXPath(xpathNode);

			List<Object> varlist = JXPathHelper.evalXPathAsList(value, new String[] { MapperConstants.JOB, MapperConstants.GLOBALVARIABLES }, new Object[] {
					ctx, gvars });
			Object returnValue = null;
			if (varlist.size() > loopTuple.getCounter()) {
				returnValue = varlist.get(loopTuple.getCounter());
			}
			if (returnValue instanceof PropertyAtom) {
				returnValue = ((PropertyAtom) returnValue).getValue();
			}
			if (returnValue instanceof XObjectWrapper) {
				returnValue = ((XObjectWrapper) returnValue).object();
			}
			return returnValue;
		} catch (Exception e) {
			throw new RuntimeException(String.format("Expression: %S \n cannot be evaluated at %s", xpathExpr, getName()), e);
		}
	}

	private int evalCounterMax(Job ctx, Variables vars, LoopCharacteristics lc) {
		String xpathExpr = null;
		xpathExpr = lc.loopCountVarExpr();

		if (xpathExpr == null)
			return 0;

		GlobalVariables gvars = getInitContext().getProcessAgent().getRuleServiceProvider().getGlobalVariables();
		JobContext job = ctx.getJobContext();
		try {
			XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpr);
			String value = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
			List varNames = XSTemplateSerializer.getVariablesinXPath(xpathNode);
			if (lc instanceof MultiInstanceLoopCharacteristics) {
				
				// For Receive Task MultiInstance Loop the Original Job Context needs to be accessed For evaluating the loopVarlist
				// This is not available in Starter Job impl
				JobContext checkPointedContext = getCheckPointedContext();
				if (checkPointedContext != null) job = checkPointedContext;
				
				// get the collection and return the size
				List<Object> varlist = JXPathHelper.evalXPathAsList(value, new String[] { MapperConstants.JOB, MapperConstants.GLOBALVARIABLES }, new Object[] {
						job, gvars });
				return varlist.size();
			} else {
				// return a simple integer count from the expression
				return JXPathHelper.evalXPathAsInt(value, new String[] { MapperConstants.JOB, MapperConstants.GLOBALVARIABLES }, new Object[] { job, gvars });
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("Expression: %S \n cannot be evaluated at %s", xpathExpr, getName()), e);
		}

	}

	protected LockManager getLockManager() {
		ProcessAgent pac = getInitContext().getProcessAgent();
		ProcessRuleSession processRuleSession = (ProcessRuleSession) pac.getRuleSession();
		LockManager lockManager = ((ObjectBasedStore) processRuleSession.getObjectManager()).getLockManager();
		return lockManager;
	}

	protected String makeLoopKey(Job job, Task ltask) throws ProcessException {
		long id = job.getJobContext().getId();
		String taskId = ltask.getName();
		return String.format("[%d]:%s", id, taskId);
	}

	protected String makeJobKey(Job job) {
		if (job.getJobContext().getExtId() != null) {
			return job.getJobContext().getExtId();
		} else {
			return String.format("%d", job.getJobContext().getId());
		}
	}

	/**
	 * @param job
	 * @param vars
	 * @param lc
	 * @return
	 * @throws ProcessException
	 */
	private TaskResult testAfter(Job job, Variables vars, LoopCharacteristics lc) throws Exception {
		TaskResult result = null;
		// String loopKey = makeLoopKey(job, this.loopTask);
		LoopTuple lt = getCreateLoopTuple(job, vars);
		do {
			lt = getCreateLoopTuple(job, vars);
			vars.setVariable(MapperConstants.LOOP_TUPLE, lt);
			vars.setVariable(MapperConstants.LOOP_COUNTER, lt.getCounter());
			vars.setVariable(MapperConstants.LOOP_MAX, lt.getCounterMax());
			if (lc instanceof MultiInstanceLoopCharacteristics) {
				vars.setVariable(MapperConstants.LOOP_VAR, getLoopVarAtIndex(job, vars, (MultiInstanceLoopCharacteristics) lc, lt));
			}
			try {
				result = performExecute(job, vars);
				if (result.getStatus() == TaskResult.Status.ERROREXCEPTION ){
					return result;
				}
				/**
				 * if the task or its children tasks have wait tasks or
				 * checkpoints
				 */
				if (hasWait()) {
					if (result.getStatus() == Status.CALLACTIVITY) {
						// do nothing return the existing result

					} else if (result.getStatus() == TaskResult.Status.WAITFOREVENT) {
						// This case handles any wait states where an event is
						// expected to complete the loop e.g.
						// There were no events in
						// ReceiveTask.onJobTransitionAtTask
						// therefore there is no other option other than to wait

						result = new LoopTaskResult(TaskResult.Status.LOOP_WAIT, job);
					} else if (result.getStatus() == TaskResult.Status.OK) {
						// This case handles the situation where some or all of
						// the events needed
						// to complete the loop has arrived and it needs to
						// continue the existing
						// job until it exhausts the events recorded in the
						// event cache and then go
						// to wait if more events are expected or finish the
						// loop e.g.
						// There was at least one event in
						// ReceiveTask.onJobTransitionAtTask
						// therefore we need to loop again until the loopTuple
						// is
						// complete
						// It is possible in the next iteration that there is no
						// event as in
						// the case above where the job must wait for the event
						// to
						// be woken up.
						result = new LoopTaskResult(TaskResult.Status.LOOP_CONTINUE, job);
					} else if (result instanceof StartEventResult) {
						// This case handles the situation where the start event
						// was handled properly
						// and the Starter Job context was used to create a new
						// JobImpl and
						// relinquish the starter thread.
						JobImpl createdJob = (JobImpl) ((StartEventResult) result).getCreatedJob();
						createdJob.setLastTask(this); // replace the internal
														// looptask to the outer
														// one
						SimpleEvent event = (SimpleEvent) ((StartEventResult) result).getCausal();
						// if (event.getTTL() != 0)
						// createdJob.recordEvent(this, ((StartEventResult)
						// result).getCausal());
						result = new LoopTaskResult(TaskResult.Status.LOOP_CONTINUE, createdJob);
					}
				}

			} catch (Exception e) {
				return new ExceptionResult(e);
			}

		} while (testCondition(job, vars, lc));
		result = new DefaultResult(TaskResult.Status.OK, job);
		return result;
	}

	/**
	 * @param job
	 * @param vars
	 * @param lc
	 * @return
	 * @throws ProcessException
	 */
	private TaskResult testBefore(Job job, Variables vars, LoopCharacteristics lc) throws Exception {
		TaskResult result = null;
		// String loopKey = makeLoopKey(job, this.loopTask);
		LoopTuple lt = getCreateLoopTuple(job, vars);
		JobContext context = job.getJobContext();

		while (testCondition(job, vars, lc)) {
			lt = getCreateLoopTuple(job, vars);
			vars.setVariable(MapperConstants.LOOP_TUPLE, lt);
			vars.setVariable(MapperConstants.LOOP_COUNTER, lt.getCounter());
			vars.setVariable(MapperConstants.LOOP_MAX, lt.getCounterMax());
			if (lc instanceof MultiInstanceLoopCharacteristics) {
				vars.setVariable(MapperConstants.LOOP_VAR, getLoopVarAtIndex(job, vars, (MultiInstanceLoopCharacteristics) lc, lt));
			}
			try {
				result = performExecute(job, vars);
				if (result.getStatus() == TaskResult.Status.ERROREXCEPTION ){
					return result;
				}
				lt = getCreateLoopTuple(job, vars);
				/**
				 * if the task or its children tastestks have wait tasks or
				 * checkpoints
				 */
				if (hasWait()) {
					if (result.getStatus() == Status.CALLACTIVITY) {
						// do nothing return the existing result

					} else if (result.getStatus() == TaskResult.Status.WAITFOREVENT) {
						// This case handles any wait states where an event is
						// expected to complete the loop e.g.
						// There were no events in
						// ReceiveTask.onJobTransitionAtTask
						// therefore there is no other option other than to wait

						result = new LoopTaskResult(TaskResult.Status.LOOP_WAIT, job);
					} else if (result.getStatus() == TaskResult.Status.OK) {
						// This case handles the situation where some or all of
						// the events needed
						// to complete the loop has arrived and it needs to
						// continue the existing
						// job until it exhausts the events recorded in the
						// event cache and then go
						// to wait if more events are expected or finish the
						// loop e.g.
						// There was at least one event in
						// ReceiveTask.onJobTransitionAtTask
						// therefore we need to loop again until the loopTuple
						// is
						// complete
						// It is possible in the next iteration that there is no
						// event as in
						// the case above where the job must wait for the event
						// to
						// be woken up.
						result = new LoopTaskResult(TaskResult.Status.LOOP_CONTINUE, job);
					} else if (result instanceof StartEventResult) {
						// This case handles the situation where the start event
						// was handled properly
						// and the Starter Job context was used to create a new
						// JobImpl and
						// relinquish the starter thread.
						JobImpl createdJob = (JobImpl) ((StartEventResult) result).getCreatedJob();
						createdJob.setLastTask(this); // replace the internal
														// looptask to the outer
														// one
						SimpleEvent event = (SimpleEvent) ((StartEventResult) result).getCausal();
						// if (event.getTTL() != 0)
						// createdJob.recordEvent(this, ((StartEventResult)
						// result).getCausal());
						result = new LoopTaskResult(TaskResult.Status.LOOP_CONTINUE, createdJob);
					}

					return result;

				}
			} catch (Exception e) {
				return new ExceptionResult(e);
			}

		}
		result = new DefaultResult(TaskResult.Status.OK, job);
		return result;
	}

	private TaskResult performExecute(Job context, Variables vars) throws Exception {
		TaskResult result = null;
		Mapper mapper = loopTask.getMapper();
		if (mapper != null) {
			mapper.inputTransform(context, vars);

		}

		result = loopTask.exec(context, vars, this);

		if (result.getStatus() == TaskResult.Status.OK) {
			vars.setVariable(MapperConstants.RETURN, result.getResult());

			if (mapper != null) {
				mapper.outputTransform(context, vars);
			}
			incrementAndSaveCounter(context, vars);
		}

		if (result instanceof StartEventResult && ((StartEventResult) result).getCreatedJob() != null) {
			Job newJob = ((StartEventResult) result).getCreatedJob();
			newJob.checkpoint(false);
		}
		if (result.getStatus() == TaskResult.Status.ERROREXCEPTION ){
			return result;
		}
		if (isCheckpointEnabled() || hasWait()) {
			context.checkpoint(hasWait());
		}
		if (loopTask instanceof Triggerable && (result.getStatus() == TaskResult.Status.WAITFORJOB || result.getStatus() == TaskResult.Status.WAITFOREVENT)) {
			logger.log(Level.DEBUG, "LoopTask [" + loopTask.getName() + "]is going to wait for " + result.getStatus());
		}
		return result;
	}

	private boolean testCondition(Job job, Variables vars, LoopCharacteristics lc) throws Exception {
		boolean result = true;
		ProcessAgent pac = this.getInitContext().getProcessAgent();
		// ControlDao<String, LoopTuple> loopTable = pac.getLoopCounterTuple();
		GlobalVariables gvars = pac.getRuleServiceProvider().getGlobalVariables();
		// String loopKey = makeLoopKey(job, loopTask);
		LoopTuple lt = getCreateLoopTuple(job, vars);
		JobContext context = job.getJobContext();
		vars.setVariable(MapperConstants.LOOP_TUPLE, lt);
		if (lc.loopCountVarExpr() != null) {
			result = (lt.getCounter() < lt.getCounterMax());
			if (result) {
				if (lc.loopConditionExpr() != null) {
					// we have condition expression - so evaluate it
					try {
						XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(lc.loopConditionExpr());
						String value = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
						List varNames = XSTemplateSerializer.getVariablesinXPath(xpathNode);

						String[] strArr = new String[] { MapperConstants.JOB, MapperConstants.GLOBALVARIABLES, MapperConstants.LOOP_COUNTER,
								MapperConstants.LOOP_MAX };
						Object[] objArr = new Object[] { context, gvars, lt.getCounter(), lt.getCounterMax() };

						if (lc instanceof MultiInstanceLoopCharacteristics) {
							strArr = new String[] { MapperConstants.JOB, MapperConstants.GLOBALVARIABLES, MapperConstants.LOOP_COUNTER,
									MapperConstants.LOOP_MAX, MapperConstants.LOOP_VAR };
							objArr = new Object[] { context, gvars, lt.getCounter(), lt.getCounterMax(),
									getLoopVarAtIndex(job, vars, (MultiInstanceLoopCharacteristics) lc, lt) };
						}

						boolean conditionResult = JXPathHelper.evalXPathAsBoolean(value, strArr, objArr);

						if (lc instanceof MultiInstanceLoopCharacteristics)
							result = result && !conditionResult;
						else
							result = result && conditionResult;

					} catch (Exception e) {
						throw new RuntimeException(String.format("Expression: %S \n cannot be evaluated at %s", lc.loopConditionExpr(), getName()), e);
					}
				}

			}
			 if (result != true) {
			 lt.setComplete();
			 updateTuple(job, vars, lt);
//			 loopTable.put(lt.getLoopKey(), lt);
			 }

			return result;

		} else {
			 lt.setComplete();
			 updateTuple(job, vars, lt);
//			 loopTable.put(lt.getLoopKey(), lt);
			return false;
		}
	}

	public void incrementAndSaveCounter(Job job, Variables vars) throws Exception {

		ProcessAgent pac = this.getInitContext().getProcessAgent();
		ControlDao<String, LoopTuple> loopTable = pac.getLoopCounterTuple();
		String loopKey = makeLoopKey(job, loopTask);
		// LoopTuple lt = (LoopTuple)
		// vars.getVariable(MapperConstants.LOOP_TUPLE);
		try {
			LoopTuple lt = loopTable.get(loopKey);
			lt.incrementCounter();
			loopTable.put(loopKey, lt);
			if(isCheckpointEnabled()){
				((JobImpl)job).recordControlDaoTuple(lt);
			}
			vars.setVariable(MapperConstants.LOOP_TUPLE, lt);
			if (logger.isEnabledFor(LEVEL)) {
				logger.log(LEVEL, "Incrementing Loop:" + lt.toString());
			}
		} finally {

		}

	}

	public void updateTuple(Job job, Variables vars, LoopTuple lt) throws ProcessException {

		ProcessAgent pac = this.getInitContext().getProcessAgent();
		ControlDao<String, LoopTuple> loopTable = pac.getLoopCounterTuple();
		String loopKey = makeLoopKey(job, loopTask);
		// LoopTuple lt = (LoopTuple)
		// vars.getVariable(MapperConstants.LOOP_TUPLE);
		try {
			loopTable.put(loopKey, lt);
			vars.setVariable(MapperConstants.LOOP_TUPLE, lt);
			if (logger.isEnabledFor(LEVEL)) {
				logger.log(LEVEL, "Incrementing Loop:" + lt.toString());
			}
		} finally {

		}

	}

	@Override
	public Transition[] getOutgoingTransitions() {
		return loopTask.getOutgoingTransitions();
	}

	@Override
	public Transition[] getIncomingTransitions() {
		return loopTask.getIncomingTransitions();
	}

	public boolean isComplete(Job job) throws Exception {
		boolean complete = false;
		LoopTuple loopTuple = null;
		loopTuple = getCreateLoopTuple(job, null);
		complete = loopTuple.isComplete();
		if (logger.isEnabledFor(LEVEL))
			logger.log(LEVEL, String.format("LoopTuple.isComplete() :%s", loopTuple));

		return complete;
	}
	
	@Override
	public void setComplete(Job job) throws Exception {
		setComplete(job,false);
	}

	public void setComplete(Job job,boolean override) throws Exception {
		LoopCharacteristics lc = getLoopCharacteristics();
		ProcessAgent pac = this.getInitContext().getProcessAgent();
		ControlDao<String, LoopTuple> loopTable = pac.getLoopCounterTuple();
		String loopKey = makeLoopKey(job, loopTask);

		LoopTuple loopTuple = null;

		try {
			loopTuple = getCreateLoopTuple(job, null);
			if (logger.isEnabledFor(LEVEL)) {
				logger.log(LEVEL, String.format("Setting LoopTuple.setComplete() :%s", loopKey));
			}
			if (loopTuple.isComplete() || override) {
				if(pendingEvents != null) {
					// if the call activity/sub process tasks in a looped task has set pendingevents in the loop
					// then pass it back to the job when the loop ends.
					((JobImpl)job).setPendingEvents(pendingEvents);
				}
				loopTuple.setComplete();
				if(isCheckpointEnabled()){
					((JobImpl)job).recordControlDaoTuple(loopTuple);
				}
				loopTable.remove(loopKey);
			}
		} finally {
		}
	}

	@Override
	public String toString() {
		if(loopTask != null) {
			return "Loop ["+loopTask.getName()+"]";
		}
		return super.toString();
	}

	public void setPendingEvents(Map<String, Set<PendingEvent>> pevents) {
		this.pendingEvents = JobImpl.deepCopy(pevents);
	}
	
	public Map<String, Set<PendingEvent>> getPendingEvents() {
		return pendingEvents;
	}

	/**
	 * 
	 * @return
	 */
	private JobContext getCheckPointedContext() {
		JobContext checkPointedContext = null;
		if (this instanceof TriggerableLoopTask) {
			 checkPointedContext = ((TriggerableLoopTask)this).getCheckPointedJobContext();
		}
		if (this.loopTask instanceof ReceiveTask && checkPointedContext != null) {
			return checkPointedContext;
		}
		return null;
	}
}
