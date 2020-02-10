package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Suresh Subramani / Date: 12/10/11 / Time: 10:22 AM
*/
public class TaskInitContext implements InitContext {

    private ProcessTemplate processTemplate;
    private ProcessAgent processAgent;
    private ROEObjectWrapper taskModel;


    public TaskInitContext(ProcessTemplate processTemplate, ProcessAgent processAgent, ROEObjectWrapper taskModel) {
        this.processTemplate = processTemplate;
        this.processAgent = processAgent;
        this.taskModel = taskModel;

    }

    @Override
    public ProcessModel getProcessModel() {
        return processTemplate.getProcessModel();
    }

    public RuleServiceProvider getRuleServiceProvider()
    {
        return this.processAgent.getRuleServiceProvider();
    }

    @Override
    public ProcessAgent getProcessAgent() {
        return processAgent;
    }

    @Override
    public ProcessTemplate getProcessTemplate() {
        return processTemplate;
    }
}
