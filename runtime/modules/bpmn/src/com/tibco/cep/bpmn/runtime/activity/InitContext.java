package com.tibco.cep.bpmn.runtime.activity;


import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.designtime.model.process.ProcessModel;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 10:13 PM
*/
public interface InitContext {
	
	ProcessModel getProcessModel();

    ProcessAgent getProcessAgent();

    ProcessTemplate getProcessTemplate();
}
