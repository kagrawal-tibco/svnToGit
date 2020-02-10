package com.tibco.cep.bpmn.core.codegen;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

/**
 * @author pdhar
 *
 */
public class GeneratorFactory {
	
	public static <T extends BaseGenerator> T createGenerator(EObject eObj,BaseGenerator parent,CodeGenContext context,IProgressMonitor monitor,boolean canOverwrite) {
		BaseGenerator bg = null;
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(eObj);
		if(wrapper.isInstanceOf(BpmnModelClass.ACTIVITY)) {
			if(wrapper.isInstanceOf(BpmnModelClass.TASK)){
				if(wrapper.isInstanceOf(BpmnModelClass.RULE_FUNCTION_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.SEND_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.DECISION_TABLE_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.SERVICE_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.BUSINESS_RULE_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.MANUAL_TASK)) {
					bg = new RuleFunctionTaskGenerator(eObj,parent,context,monitor,canOverwrite);
				} 
			} else {
				
			}
		} else if (wrapper.isInstanceOf(BpmnModelClass.EVENT)) {
			if(wrapper.isInstanceOf(BpmnModelClass.CATCH_EVENT)) {
				if(wrapper.isInstanceOf(BpmnModelClass.START_EVENT)) {
					bg = new StartEventGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.INTERMEDIATE_CATCH_EVENT)) {
					bg = new IntermediateCatchEventGenerator(eObj,parent,context,monitor,canOverwrite);
				} 
			} else if(wrapper.isInstanceOf(BpmnModelClass.THROW_EVENT)){
				if(wrapper.isInstanceOf(BpmnModelClass.END_EVENT)) {
					bg = new EndEventGenerator(eObj,parent,context,monitor,canOverwrite);
				} else if(wrapper.isInstanceOf(BpmnModelClass.INTERMEDIATE_THROW_EVENT)){
					bg = new IntermediateThrowEventGenerator(eObj,parent,context,monitor,canOverwrite);
				}
			}
			
		} else if (wrapper.isInstanceOf(BpmnModelClass.GATEWAY)) {
			if(wrapper.isInstanceOf(BpmnModelClass.INCLUSIVE_GATEWAY)) {
				bg = new GatewayGenerator(eObj,parent,context,monitor,canOverwrite);
			} else if(wrapper.isInstanceOf(BpmnModelClass.EXCLUSIVE_GATEWAY)) {
				bg = new GatewayGenerator(eObj,parent,context,monitor,canOverwrite);
			} else if(wrapper.isInstanceOf(BpmnModelClass.COMPLEX_GATEWAY)) {
				bg = new GatewayGenerator(eObj,parent,context,monitor,canOverwrite);
			} else if(wrapper.isInstanceOf(BpmnModelClass.PARALLEL_GATEWAY)) {
				bg = new GatewayGenerator(eObj,parent,context,monitor,canOverwrite);
			} else if(wrapper.isInstanceOf(BpmnModelClass.EVENT_BASED_GATEWAY)) {
				bg = new GatewayGenerator(eObj,parent,context,monitor,canOverwrite);
			} 
			
		} else if (wrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
			bg = new ProcessGenerator(eObj,parent,context,monitor,canOverwrite);
		} else if (wrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
			bg = new SequenceFlowGenerator(eObj,parent,context,monitor,canOverwrite);
		}
		return (T) bg;
	}

}
