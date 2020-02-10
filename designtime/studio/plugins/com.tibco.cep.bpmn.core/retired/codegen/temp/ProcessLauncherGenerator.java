package com.tibco.cep.bpmn.core.codegen.temp;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.codegen.CodegenError;
import com.tibco.cep.bpmn.core.codegen.temp.offline.StartEventGenerator;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class ProcessLauncherGenerator<C extends EClass, O extends EObject> 
								extends AbstractBpmnGenerator<C,O> 
								implements WrappedObjectVisitor<C,O>,RootGenerator<C,O> {
	
	EObjectWrapper<C, O> processWrapper;
	private Symbol processSymbol;
	private Symbol jobDataSymbol;

	public ProcessLauncherGenerator(CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(null,ctx,monitor, overwrite);
	}
	
	@Override
	public EObjectWrapper<C, O> getProcessWrapper() {
		return processWrapper;
	}
	
	@Override
	public Symbol getJobSymbol() {
		return jobDataSymbol;
	}
	
	@Override
	public Symbol getProcessSymbol() {
		return processSymbol;
	}
	
	
	
	@Override
	public  boolean visit(
			EObjectWrapper<C, O> objWrapper) {
		try {
			BpmnIndex ontology = getOntology();
			
			if(objWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
				 processWrapper = objWrapper;
				final String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				String processURI = BpmnIndexUtils.getSerializableURI(getProject().getName(), processWrapper.getEInstance());
				this.processSymbol = addSymbol(processName,processURI);
				String jobDataConceptURI = ExtensionHelper.getExtensionAttributeValue(processWrapper, BpmnMetaModelExtensionConstants.E_ATTR_JOB_DATA_CONCEPT);
				this.jobDataSymbol = addSymbol("jobDataConcept", jobDataConceptURI);
				
				Collection<EObject> outgoingNodes = ontology.getFlowNodes(processWrapper.getEInstance(), false, true, null);
				if(BpmnModelUtils.containsFlowNodeType(outgoingNodes,BpmnModelClass.START_EVENT)) {
					Collection<EObject> startEvents = ontology.getFlowNodes(processWrapper.getEInstance(), false, true, BpmnModelClass.START_EVENT);
					// each start event is capable of creating the process
					for(EObject startEvent:startEvents) {
						
						final EObjectWrapper<EClass, EObject> sevWrapper = EObjectWrapper.wrap(startEvent);
//						StartEventGenerator sevGenerator = new StartEventGenerator(this,getContext(),getMonitor(),canOverwrite());
						FlowNodeGenerator<C,O> sevGenerator = new FlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
						sevWrapper.accept(sevGenerator);
						
						// create launch rulefunction for every event combination with jobDataConcept
						if(sevGenerator.isInline()) {
							Event event = sevGenerator.getTriggerEvent();
							Symbol eventSymbol = addSymbol(event.getName(),event.getFullPath());
							createLaunchRuleFunction(processName,jobDataSymbol, eventSymbol);
						}
//						Event event = sevGenerator.getEvent();
//						List<Symbol> eventSymbols = sevGenerator.findSymbol(event.getName());
//						if(eventSymbols.size() == 1) {
//							createLaunchRuleFunction(processName,jobSymbol, eventSymbols.get(0));
//						}
						
					} // for startevent
				} else if(BpmnModelUtils.containsFlowNodeType(outgoingNodes,BpmnModelClass.EVENT_BASED_GATEWAY)) {
					// check if there is a event based gateway
					
					// if the event based gateway is exclusive then one of the first matching event will create the process
					
					// if the event based gateway is inclusive then all of the matching events need to happen before the process
					// can be created
				} else if(BpmnModelUtils.containsFlowNodeType(outgoingNodes,BpmnModelClass.TASK)) {
					// if there are no event based gateways then all activities which has no incoming sequences can start
				}
				
				Collection<EObject> startEvents = ontology.getStartEvents(processWrapper.getEInstance());
				if(!startEvents.isEmpty()) {
					
					
				} else {
					
					
					
					
				}
				
			} // end if PROCESS
		} catch (Exception e) {
			CodegenError cgErr = new CodegenError(Messages
					.getString("bpmn.codegen.error"), //$NON-NLS-1$
					CodegenError.MODEL_TYPE, e);
			reportError(cgErr);
			return false;
		}
		
		return false;
	}

	

}
