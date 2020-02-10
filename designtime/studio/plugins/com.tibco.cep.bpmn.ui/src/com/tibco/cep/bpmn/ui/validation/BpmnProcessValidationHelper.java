package com.tibco.cep.bpmn.ui.validation;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;


/**
 * 
 * @author majha
 *
 */
public class BpmnProcessValidationHelper {
	
	/**
	 * this check BPMN constraint rule for Sequence flow
	 * 
	 * @return proper error message is sequence flow is not valid else return
	 *         null
	 */
	static public String checkForValidSequence(EObject sequence) {
		EObjectWrapper<EClass, EObject> sequenceWrapper = EObjectWrapper.wrap(sequence);
		EObject start = sequenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
		EObject end = sequenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
		EObject startContainer = start.eContainer();
		EObject endContainer = end.eContainer();
		EObject[] args = new EObject[]{start, end, startContainer, endContainer};
		
		String id = (String)sequenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		String msg = checkForValidSequence(id, args );
		if(msg == null)
			msg = checkForMultipleSequenceFlow(id, args);
		
		return msg;
	}

	/**
	 * this check BPMN constraint rule for Sequence flow
	 * 
	 * @return proper error message is sequence flow is not valid else return
	 *         null
	 */
	static public String checkForValidSequence(String sequenceId, EObject... args) {
		String message = null;

		if(args.length < 4){
			return  Messages.getString("invalid_sequence");
		}

		EObject sourceObject = args[0];
		EObject tagetObject = args[1];
		EObject parentSourceObject = args[2];
		EObject parentTagetObject = args[3];
		// both the nodes must be flow nodes in order to create a sequence flow
		if(BpmnModelClass.FLOW_NODE.isSuperTypeOf(sourceObject.eClass()) &&
				BpmnModelClass.FLOW_NODE.isSuperTypeOf(tagetObject.eClass())){
			if(BpmnModelClass.START_EVENT.isSuperTypeOf(tagetObject.eClass())){
				message = Messages.getString("start_event_target_error",sequenceId);
			}else if (BpmnModelClass.END_EVENT.isSuperTypeOf(sourceObject.eClass())){
				message = Messages.getString("end_event_source_error", sequenceId);
			}else if(isConnectionCrossSubprocessBoundary(parentSourceObject,parentTagetObject)){
				message = Messages.getString("inter_graph_sequence_error", sequenceId);
			}else if(!(isValidSubProcessForSequence((EObject)sourceObject)&& isValidSubProcessForSequence((EObject)tagetObject)))
				message = Messages.getString("sequence_connected_event_subprocess_error", sequenceId);
			else if(isBoundaryEvent((EObject)tagetObject))
				message = Messages.getString("boundary_event_sequence_target_error", sequenceId);

		}
		return message;
	}
	
	static private String checkForMultipleSequenceFlow(String sequenceId, EObject... args){
		String message = null;

		if(args.length < 2){
			return  Messages.getString("invalid_sequence");
		}

		EObject sourceObject = args[0];
		EObject tagetObject = args[1];


		message = checkForMultipleIncomingOutgoingFLow(
				sourceObject, true);
		if (message == null)
			message = checkForMultipleIncomingOutgoingFLow(
					tagetObject, false);
		
		return message;
	}
	
	static private String checkForMultipleIncomingOutgoingFLow(EObject obj, boolean outgoing){
		if(BpmnModelClass.GATEWAY.isSuperTypeOf(obj.eClass()))
			return null;
		
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(obj);
		String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		String msg = null;
		if(outgoing && wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING)){
			EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			if(listAttribute.size() > 1)
				msg = Messages.getString("cannot_have_more_than_one_outgoing_sequence", id);
		}else if(wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING)){
//			EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
//			if(listAttribute.size() > 1)
//				msg = Messages.getString("cannot_have_more_than_one_incoming_sequence", id);
		}
		return msg;
	}

	/**
	 * this check BPMN constraint rule for Sequence flow
	 * 
	 * @return proper error message is sequence flow is not valid else return
	 *         null
	 */
	static public String checkForValidAssociation( EObject association) {
		EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.wrap(association);
		EObject start = associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
		EObject end = associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
		
		String id = (String)associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
	
		return checkForValidAssociation(start, end, id);
	}
	/**
	 * this check BPMN constraint rule for Sequence flow
	 * 
	 * @return proper error message is sequence flow is not valid else return
	 *         null
	 */
	static public String checkForValidAssociation(EObject sourceObject,
			EObject tagetObject, String associationId) {
		String message = null;

		
		if (tagetObject.eClass().equals(sourceObject.eClass())) {
			message = Messages.getString("same_type_flow_element_association_rule_error", associationId);
		} else if (!((BpmnModelClass.ARTIFACT.isSuperTypeOf(sourceObject
				.eClass()) && BpmnModelClass.BASE_ELEMENT
				.isSuperTypeOf(tagetObject.eClass())) || (BpmnModelClass.ARTIFACT
				.isSuperTypeOf(tagetObject.eClass()) && BpmnModelClass.BASE_ELEMENT
				.isSuperTypeOf(sourceObject.eClass())))) {
			message = Messages.getString("same_type_flow_element_association_rule_error", associationId);
		}

		return message;
	}
	
	/**
	 * this check BPMN constraint rule for Sequence flow
	 * 
	 * @return proper error message is sequence flow is not valid else return
	 *         null
	 */
	static public String checkForValidNodeInGraph(EObject flowNode) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObject container = flowNode.eContainer();
		EClass type = flowNode.eClass();
		EClass extType = null;
		
		if(BpmnModelClass.EVENT.isSuperTypeOf(type)){
			EList<EObject> eventDefinitions = EObjectWrapper.wrap(flowNode).getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
			if(eventDefinitions.size() == 0 )
				eventDefinitions = EObjectWrapper.wrap(flowNode).getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS);
			EObject eDef = eventDefinitions.size()>  0 ? eventDefinitions.get(0): null;
			if(eDef != null ){
				extType = eDef.eClass();
			}
		}
		
		String id = (String)flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
	
		return checkForValidNodeInGraph(container, id, type, extType, false );
	}
	
	public static String checkForValidNodeInGraph(EObject parentGraphObj,String nodeId, EClass type, EClass extType, boolean newNode) {
		String error = null;
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
				.wrap(parentGraphObj);
		if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(wrapper.getEClassType())) {
			boolean triggerByEvent = (Boolean) wrapper
					.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			if (triggerByEvent) {
				if (BpmnModelClass.START_EVENT.isSuperTypeOf(type)) {
					if (extType == null)
						error = Messages.getString("none_start_event_inside_event_subprocess", nodeId);
					else {
						Collection<EObject> flowNodes = BpmnModelUtils
								.getFlowNodes(wrapper,
										BpmnModelClass.START_EVENT);
						if (newNode && flowNodes.size() >=  1)
							error = Messages.getString("more_than_one_start_event_inside_event_subprocess", nodeId);
					}
				}
			} else {
				if (BpmnModelClass.START_EVENT.isSuperTypeOf(type) && extType != null)
					error = Messages.getString("trigger_start_event_inside_normal_subprocess", nodeId);
			}

			if (BpmnModelClass.LANE.isSuperTypeOf(type)) {
				error = Messages.getString("lane_inside_subprocess_error", nodeId);
			}
		}
		if (BpmnModelClass.LANE.isSuperTypeOf(wrapper.getEClassType())
				|| BpmnModelClass.PROCESS
						.isSuperTypeOf(wrapper.getEClassType())) {
			if (BpmnModelClass.LANE.isSuperTypeOf(type)) {
				if (extType != null
						&& BpmnModelClass.PROCESS.isSuperTypeOf(extType)) {
					error = Messages.getString("pool_inside_pool_error", nodeId);
				}
			}
		}
		
		return error;
	}
	
	public static String checkForStartEventInProcess(EObject process) {
		String error = null;
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		String id = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(processWrapper,
				BpmnModelClass.START_EVENT);
		 if (flowNodes.size() == 0)
			error = Messages
					.getString("must_have_one_start_event_inside_process", id);
		 
		 return error;
	}
	
	public static String checkForEndEventInProcess(EObject process) {
		String error = null;
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		
		Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(processWrapper,
				BpmnModelClass.END_EVENT);
		String id = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		 if (flowNodes.size() == 0)
			error = Messages
					.getString("must_have_one_end_event_inside_process", id);
		 
		 return error;
	}
	
	public static String checkForStartEventInEventSubProcess(EObject subProcess) {
		String error = null;
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper
				.wrap(subProcess);

		String id = processWrapper
				.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);

		Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(
				processWrapper, BpmnModelClass.START_EVENT);
		boolean triggerByEvent = (Boolean) processWrapper
		.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
		if (triggerByEvent && flowNodes.size() > 1)
			error = Messages.getString(
					"more_than_one_start_event_inside_event_subprocess", id);
		else if (triggerByEvent && flowNodes.size() == 0)
			error = Messages.getString(
					"must_have_one_start_event_inside_event_subprocess", id);

		return error;
	}
	

	
//	Objects in the Sub-Process cannot be connected to objects outside of the  Sub-Process
	private static boolean isConnectionCrossSubprocessBoundary(EObject parentSourceObject , EObject parentTargetObject){
		boolean interGraph= false;
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(parentSourceObject.eClass()) ||
				BpmnModelClass.SUB_PROCESS.isSuperTypeOf(parentTargetObject.eClass())){
			interGraph = !parentSourceObject.equals(parentTargetObject);
		}
		
		return interGraph;
		
	}
	
	private static boolean isValidSubProcessForSequence(EObject object){
		EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(object);
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(useInstance.getEClassType())){
			boolean triggerByEvent = (Boolean) useInstance
			.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			
			return !triggerByEvent;
		}
		
		return true;
		
	}
	
	//	The Boundary Event MUST NOT be a target for Sequence Flow; it cannot have an incoming Flow.
	private static boolean isBoundaryEvent(EObject object){
		EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(object);
		if(BpmnModelClass.BOUNDARY_EVENT.isSuperTypeOf(useInstance.getEClassType())){
			return true;
		}
		
		return false;
	}
 
	/**
	 * @param errors
	 * @param projectName
	 * @param key
	 * @param value
	 * @param id
	 * @return
	 */
	public static List<String> checkForValidRuleFunction(List<String> errors, 
			                                             String projectName, 
			                                             String key, 
			                                             String value, 
			                                             String id) {
		String error = null;
		if (value == null || value.trim().isEmpty()) {
			error = Messages.getString("empty_element_property", key, id);
		}
		if (!value.trim().isEmpty()) {
			DesignerElement re = IndexUtils.getElement(projectName, value);
			if (re == null) {
				error = Messages.getString("non_exist_empty_gateway_property", key, value, id);
			}
		}
		if (error != null) {
			errors.add(error);
		}
		return errors;
	}
	
	public static List<String> checkForValidForkRuleFunction(
			List<String> errors, String projectName, String key, String value,
			String id) {
		String error = null;
		if (value == null || value.trim().isEmpty()) {
			error = Messages.getString("empty_element_property", key, id);
		}
		if (!value.trim().isEmpty()) {
			DesignerElement re = IndexUtils.getElement(projectName, value);
			if (re == null) {
				error = Messages.getString("non_exist_empty_gateway_property",
						key, value, id);
			}else if(!isForkRuleFunction(projectName, re)){
				error = Messages.getString("invalid_fork_rulefunction",
						key);
			}
		}
		if (error != null) {
			errors.add(error);
		}
		return errors;
	}
	
	public static List<String> checkForValidJoinRuleFunction(
			List<String> errors, String projectName, String key, String value,
			String id) {
		String error = null;
		if (value == null || value.trim().isEmpty()) {
			error = Messages.getString("empty_element_property", key, id);
		}
		if (!value.trim().isEmpty()) {
			DesignerElement re = IndexUtils.getElement(projectName, value);
			if (re == null) {
				error = Messages.getString("non_exist_empty_gateway_property",
						key, value, id);
			}else if(!isJoinRuleFunction(projectName, re)){
				error = Messages.getString("invalid_join_rulefunction",
						key);
			}
		}
		if (error != null) {
			errors.add(error);
		}
		return errors;
	}
	
	private static boolean isJoinRuleFunction(String projectName, DesignerElement res){
		boolean joinRulefnc = false;
		if (res != null && res instanceof RuleElement) {
			RuleElement rf = (RuleElement) res;
			Entity entity = IndexUtils.getRule(projectName, rf.getFolder(), rf.getName(), ELEMENT_TYPES.RULE_FUNCTION);
			if(entity instanceof RuleFunction){
				RuleFunction rfn = (RuleFunction)entity;
				String returnType = rfn.getReturnType();
				if(returnType != null && returnType.equalsIgnoreCase("object")){
					Symbols symbols = rfn.getSymbols();
					EList<Symbol> symbolList = symbols.getSymbolList();
					if(symbolList.size() == 2){
						Symbol symbol = symbolList.get(0);
			            String typeName = symbol.getType();
						if(typeName.equalsIgnoreCase("object")){
							 symbol = symbolList.get(1);
				             typeName = symbol.getType();
				             if( !symbol.isArray() && typeName.equalsIgnoreCase("string")){
					            	 joinRulefnc = true;
				             }
						}
					}
				}
				
			}
		}
		return joinRulefnc;
	}
	
	private static boolean isForkRuleFunction(String projectName, DesignerElement res){
		boolean forkRulefnc = false;
		if (res != null && res instanceof RuleElement) {
			RuleElement rf = (RuleElement) res;
			Entity entity = IndexUtils.getRule(projectName, rf.getFolder(), rf.getName(), ELEMENT_TYPES.RULE_FUNCTION);
			if(entity instanceof RuleFunction){
				RuleFunction rfn = (RuleFunction)entity;
				String returnType = rfn.getReturnType();
				if(returnType != null && returnType.equalsIgnoreCase("object")){
					Symbols symbols = rfn.getSymbols();
					EList<Symbol> symbolList = symbols.getSymbolList();
					if(symbolList.size() == 2){
						Symbol symbol = symbolList.get(0);
			            String typeName = symbol.getType();
						if(!symbol.isArray() && typeName.equalsIgnoreCase("object")){
							 symbol = symbolList.get(1);
				             typeName = symbol.getType();
				             if(!symbol.isArray() && typeName.equalsIgnoreCase("string")){
				            	 forkRulefnc = true;
				             }
						}
					}
				}
				
			}
		}
		return forkRulefnc;
	}
	
	
	
}
