package com.tibco.cep.bpmn.runtime.activity.events;

import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TriggerType;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.results.StartEventResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/*
 * Author: Suresh Subramani / Date: 2/12/12 / Time: 5:55 PM
 */
public class StartEvent extends AbstractTriggerEvent {

	@Override
	public boolean instantiatesProcess() {
		return true;
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
	public boolean eval(Variables variables) {

		if (getTriggerType() != TriggerType.CONDITIONAL)
			return true;

		// Only valid for Conditional Trigger Type.
		RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();

		String expr = this.getJobKeyExpression();

		if ((expr == null) || (expr.length() == 0))
			return true;

		String inputName = this.getInputEvent().getName();
		Object obj = variables.getVariable(inputName);

		GlobalVariables gvars = ruleSession.getRuleServiceProvider().getGlobalVariables();
		boolean ret = JXPathHelper.evalXPathAsBoolean(expr, new String[] { "globalvariables", inputName }, new Object[] { gvars, obj });

		return ret;
	}

	@Override
	public TaskResult execute(Job job, Variables vars, Task loopTask) {

		TaskResult result = null;
		JobImpl newjob = null;
		boolean locked = false;
		EntityDao dao = null;
		long lockId=-1;
		try {
			switch (this.triggerType) {

			case NONE:
				result = new DefaultResult(TaskResult.Status.STARTEVENT, job);
				break;

			case TIMER:
			case MESSAGE:
			case CONDITIONAL:
			case SIGNAL: {
				ProcessTemplate processTemplate = this.getInitContext().getProcessTemplate();
				ProcessAgent pac = this.getInitContext().getProcessAgent();

				JobContext context = processTemplate.newProcessData();
				newjob = new JobImpl(context, pac);
				newjob.setCurrentTask(job.getCurrentTask());
				dao = pac.getCluster().getDaoProvider().getEntityDao(context.getClass());
				lockId = context.getId();
				locked = dao.lock(lockId, -1);

				String inputName = this.getInputEvent().getName();
				Object obj = vars.getVariable(inputName);

				this.getMapper().outputTransform(newjob, vars);
				newjob.assertObject(obj);
				newjob.checkpoint(false);
				result = new StartEventResult(newjob, (Event) obj);
			}
				break;
			default:
				result = new ExceptionResult("invalid StartEvent type");
			}

			if (newjob != null)
				((JobImpl) newjob).setLastTask(this);
			return result;
		}

		catch (Throwable throwable) {
			if(locked && dao!= null && lockId != -1){
				dao.unlock(lockId);
				logger.log(Level.DEBUG, String.format("Unlocking Start Event JobContext lock :%d", lockId));
			}
			return new ExceptionResult(throwable);
		}
	}
}
