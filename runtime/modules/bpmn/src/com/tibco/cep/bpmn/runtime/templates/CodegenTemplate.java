package com.tibco.cep.bpmn.runtime.templates;

import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;

public interface CodegenTemplate {

	void init(ProcessAgent context) throws Exception;

	String getName();

	JitCompilable generate() throws Exception;

	Class getTemplateClass() throws Exception;
}
