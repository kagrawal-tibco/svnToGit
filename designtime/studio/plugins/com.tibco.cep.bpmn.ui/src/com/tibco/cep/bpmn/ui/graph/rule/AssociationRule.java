package com.tibco.cep.bpmn.ui.graph.rule;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

public class AssociationRule extends AbstractDiagramRule implements DiagramRule {

	public AssociationRule(DiagramRuleSet ruleSet) {
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
			if(!edgeType.equals(BpmnModelClass.ASSOCIATION))
				return false;// for edge other than association this rule is not valid
		}

		boolean isAllowed = false;
		if(srcTSNode != null && tgtTSNode!=null){
			Object sourceObj = srcTSNode.getUserObject();
			Object targetObj =  tgtTSNode.getUserObject();
			if (	sourceObj != null &&
					targetObj != null &&
					sourceObj instanceof EObject &&
					targetObj instanceof EObject) {
				EObject srcEObj = (EObject) sourceObj;
				EObject tgtEObj = (EObject) targetObj;
				String error = UiRuleHelper.checkForValidAssociation(srcEObj, tgtEObj, "");
				isAllowed = (error == null);
			}
				
		}
		return isAllowed;
	}
		

}
