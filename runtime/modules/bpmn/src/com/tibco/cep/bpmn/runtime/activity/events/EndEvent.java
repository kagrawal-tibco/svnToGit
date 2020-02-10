package com.tibco.cep.bpmn.runtime.activity.events;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.TriggerType;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.tasks.SendTask;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

/**
 * @author pdhar
 *
 */
public class EndEvent extends SendTask {
	
	private TriggerType triggerType;
	private String conditionExpression;

	public EndEvent() {

	}


	@Override
	public void init(InitContext context, Object... args) throws Exception {
		super.init(context, args);
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		List<ROEObjectWrapper<EClass, EObject>> eventDefWrappers =  flowNodeWrapper.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
		if(eventDefWrappers.size() == 0) {
            this.triggerType = TriggerType.NONE;
        } else if(eventDefWrappers.size() == 1) {
            ROEObjectWrapper<EClass, EObject> eventDefWrapper = eventDefWrappers.get(0);
            if(eventDefWrapper.isInstanceOf(BpmnModelClass.MESSAGE_EVENT_DEFINITION)){
                this.triggerType =  TriggerType.MESSAGE;
            } else if(eventDefWrapper.isInstanceOf(BpmnModelClass.TIMER_EVENT_DEFINITION)){
                this.triggerType =  TriggerType.TIMER;
            } else if(eventDefWrapper.isInstanceOf(BpmnModelClass.SIGNAL_EVENT_DEFINITION)){
                this.triggerType =  TriggerType.SIGNAL;
            } else if(eventDefWrapper.isInstanceOf(BpmnModelClass.CONDITIONAL_EVENT_DEFINITION)){
                this.triggerType =  TriggerType.CONDITIONAL;
                this.conditionExpression = eventDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION);
            }

        } else {
            // event can have multiple event definitions
            this.triggerType =  TriggerType.MULTIPLE;
        }
	}


	
	@Override
	protected void initOutputTransforms() {
	}
	


	protected void initInputTransforms() {
		List dataInputAssocs = getTaskModel()
				.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION);
		if (!dataInputAssocs.isEmpty()) {
			ROEObjectWrapper doAssocWrap = ROEObjectWrapper.wrap((EObject) dataInputAssocs.get(0));

			EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
			ROEObjectWrapper transformWrap = ROEObjectWrapper.wrap(transform);
			if (transformWrap != null) {
				inputXslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
	 */
	@Override
	public TaskResult execute(Job job, Variables vars, Task loopTask) {

		try {
			consumeEvents(job, false);
            
		    if(this.triggerType == TriggerType.MESSAGE || triggerType == TriggerType.SIGNAL) {
                super.execute(job, vars, loopTask); //Send and Consume
		    }

		    return new DefaultResult(TaskResult.Status.COMPLETE, null );

		} catch (Throwable throwable) {
			return new ExceptionResult(throwable);
		}

	}



}
