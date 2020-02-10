package com.tibco.cep.bpmn.core.codegen;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;

public interface RootGenerator extends BaseGenerator {
	
	public EObject getProcess();
	
	public Event getMsgProcEvent();
	
	public Symbol getMsgProcEventSymbol();
	
	public EObject getJobSymbol();
	
	public Concept getJobDataConcept();
	
	public Symbol getBpmnContextSymbol();

	public Map<String, RuleFunction> getFlowElementHandlerMap();

	public Scorecard getProcessConstants();

	public RuleFunction getProcessConstantsInitFunction();

}
