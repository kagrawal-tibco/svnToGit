package com.tibco.cep.bpmn.ui.graph.rule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

public class FlowNodeRule extends AbstractDiagramRule implements DiagramRule {

	public FlowNodeRule(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}


	@Override
	public boolean isAllowed(Object[] args) {
		if(args.length != 3 ) 
			return false;
		TSENode srcTSNode = (TSENode) args[0];
		TSENode tgtTSNode = (TSENode) args[1];
		TSEEdge edge =(TSEEdge)args[2];
		EClass edgeType = (EClass)edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);	
		if(edgeType != null ){
			// for edge other than sequence this rule is not valid, if we use same rule for message flow later, need to change the logic
			if(!edgeType.equals(BpmnModelClass.SEQUENCE_FLOW))
				return false;
		}
		
		boolean valid = false;
		if(srcTSNode != null && tgtTSNode!=null){
			Object sourceObj = srcTSNode.getUserObject();
			Object targetObj =  tgtTSNode.getUserObject();
			if (	sourceObj != null &&
					targetObj != null &&
					sourceObj instanceof EObject &&
					targetObj instanceof EObject) {
				EObject srcEObj = (EObject) sourceObj;
				EObject tgtEObj = (EObject) targetObj;
				if(srcEObj == tgtEObj){
					valid = false;
				}else{
					TSGraph srcOwnerGraph = srcTSNode.getOwnerGraph();
					TSGraph targetOwnerGraph = tgtTSNode.getOwnerGraph();
					EObject srcParentObject = (EObject)srcOwnerGraph.getUserObject();
					EObject targetParentObject = (EObject)targetOwnerGraph.getUserObject();
					String checkForValidSequence = UiRuleHelper.checkForValidSequence("", srcEObj, tgtEObj, srcParentObject, targetParentObject);
					
					
					valid = (checkForValidSequence == null);
					
					if(valid)
						valid = !checkForExistingSequence(srcEObj, tgtEObj);
				}
				
			}
				
		}
		return valid;
	}
	
	public boolean checkForExistingSequence(EObject startNode, EObject endNode){
		int counter = 0;
		EObjectWrapper<EClass, EObject> startNodeWrap = EObjectWrapper.wrap(startNode);
		EObjectWrapper<EClass, EObject> endNodeWrap = EObjectWrapper.wrap(endNode);
		EList<EObject> list1 = startNodeWrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);list1.size();
		EList<EObject> list2 = endNodeWrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);list2.size();
		for (EObject eObject : list2) {
			if(list1.contains(eObject)){
				if (++counter > 1) return true;
			}
		}
		
		return false;
		
	}
	

}
