package com.tibco.cep.bpmn.ui.graph.rule;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.Messages;


/**
 * 
 * @author majha
 *
 */
public class UiRuleHelper {
	

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
	
	static public String checkForValidConnectorSequence(String sequenceId, EObject... args) {
		String message = null;

		if(args.length < 4){
			return  Messages.getString("invalid_sequence");
		}

		EObject sourceObject = args[0];
		EObject tagetObject = args[1];
		EObject parentSourceObject = args[2];
		EObject parentTagetObject = args[3];
		// both the nodes must be flow nodes in order to create a sequence flow
		if(BpmnModelClass.BOUNDARY_EVENT.equals(sourceObject.eClass()) &&
				BpmnModelClass.FLOW_NODE.isSuperTypeOf(tagetObject.eClass())){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(sourceObject);
			String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			if(listAttribute.size() >0)
				message = Messages.getString("cannot_have_more_than_one_outgoing_sequence", id);
			else if (!BpmnModelClass.ACTIVITY.isSuperTypeOf(tagetObject.eClass())){
				message = Messages.getString("end_event_source_error", sequenceId);
			}else if(isConnectionCrossSubprocessBoundary(parentSourceObject,parentTagetObject)){
				message = Messages.getString("inter_graph_sequence_error", sequenceId);
			}else if(!(isValidSubProcessForSequence((EObject)tagetObject)))
				message = Messages.getString("sequence_connected_event_subprocess_error", sequenceId);
			
		}
		return message;
	}
	
	
	static public String checkForMultipleIncomingOutgoingFLow(EObject obj, boolean outgoing){
		if(BpmnModelClass.GATEWAY.isSuperTypeOf(obj.eClass()))
			return null;
		
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(obj);
		String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		String msg = null;
		if(outgoing && wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING)){
			EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			if(!listAttribute.isEmpty())
				msg = Messages.getString("cannot_have_more_than_one_outgoing_sequence", id);
		}else if(wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING)){
			EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
			if( !listAttribute.isEmpty())
				msg = Messages.getString("cannot_have_more_than_one_incoming_sequence", id);
		}
		return msg;
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
		
		if(BpmnModelClass.SUB_PROCESS.equals(wrapper.getEClassType())){
			if (BpmnModelClass.SUB_PROCESS.equals(type)) {
				error = "subprocess inside subprocess is not allowed";
			}
		}
		
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
 
	
}
