package com.tibco.cep.bpmn.ui.graph.rule;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author majha
 *
 */
public class DeleteNodeRule extends AbstractDiagramRule implements DiagramRule {

	private String message;

	public DeleteNodeRule(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}


	@Override
	public boolean isAllowed(Object[] args) {
		if(args.length != 1 ) 
			return false;
		if(args[0] != null && args[0] instanceof TSENode){
			TSENode node = (TSENode) args[0];
			message = null;

			if(isRootLane(node)){
				message =com.tibco.cep.bpmn.ui.Messages
				.getString("delete_lane_warning");
				return false;
			}else if(eventCanNotBedeleted(node)){
				EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				String event="";
				String process="Process";
				if(nodeType.equals(BpmnModelClass.START_EVENT))
					event = "start";
				else if(nodeType.equals(BpmnModelClass.END_EVENT))
					event = "end";
				
				TSGraph ownerGraph = node.getOwnerGraph();
				EObjectWrapper<EClass, EObject> ownerGraphUserObject = EObjectWrapper.wrap((EObject)ownerGraph.getUserObject());

				if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(ownerGraphUserObject.getEClassType())){

					if(isEventSubprocess(ownerGraphUserObject))
						process="Event sub-process";
				}
				
				message =com.tibco.cep.bpmn.ui.Messages
				.getString("delete_event_warning",process, event );
				return false;
			}
		}


		return true;
	}
	
	

	private boolean isRootLane(TSENode node){
		EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EObject userObj = (EObject) node.getOwnerGraph().getUserObject();
		boolean isProcess = BpmnModelClass.PROCESS.isSuperTypeOf(userObj.eClass());
		boolean rootlane = false;
		if (nodeType != null && BpmnModelClass.LANE.isSuperTypeOf(nodeType)) {
			if(isProcess) {
				rootlane = true;
			}
		}
		
		return rootlane;
	}
	
	//
	private boolean eventCanNotBedeleted(TSENode node){
		// Manish TODO : let user delete the event in design time, and add validation for at least one start/end event
		
//		TSGraph ownerGraph = node.getOwnerGraph();
//		EObjectWrapper<EClass, EObject> ownerGraphUserObject = EObjectWrapper.wrap((EObject)ownerGraph.getUserObject());
//		
//		TSGraph rootGraph = null;
//		EObjectWrapper<EClass, EObject> process = null;
//		boolean shouldHaveAtleastOneStartEndEvent = true;
//		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(ownerGraphUserObject.getEClassType())){
//			rootGraph = ownerGraph;
//			process = ownerGraphUserObject;
//			if(!isEventSubprocess(process))
//				shouldHaveAtleastOneStartEndEvent = false;
//		}
//		else{
//			rootGraph = ((TSEGraph)ownerGraph).getGreatestAncestor();
//			process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
//		}
//		
//		EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//		if(nodeType.equals(BpmnModelClass.START_EVENT)|| nodeType.equals(BpmnModelClass.END_EVENT)){
//			Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(process, nodeType);
//			if(shouldHaveAtleastOneStartEndEvent  && flowNodes.size() == 1){
//				return true;
//			}
//		}
		
		return false;
	}
	
	private boolean isEventSubprocess(EObjectWrapper<EClass, EObject> object){
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(object.getEClassType())){
			boolean triggerByEvent = (Boolean) object
			.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			
			return triggerByEvent;
		}
		
		return true;
	}
	
	
	
	@Override
	public String getMessage() {
		return message;
	}
	

}
