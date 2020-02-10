package com.tibco.cep.bpmn.ui.graph.rule;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;


public class GenericNodeRule extends AbstractDiagramRule implements DiagramRule {

	public GenericNodeRule(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}


	@Override
	public boolean isAllowed(Object[] args) { // CreateNodeTool is calling this check args
		if(args.length != 1) 
			return false;
		TSENode node = (TSENode) args[0];
		
		boolean allowed = true;
		TSEGraph graph = (TSEGraph)node.getOwnerGraph();
		EObject userObject = (EObject)graph.getUserObject();
		EClass type = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass extType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		if (userObject == null) {
			allowed = false;
			if(BpmnModelClass.LANE.isSuperTypeOf(type)){
				if(extType != null && BpmnModelClass.PROCESS.isSuperTypeOf(extType)){
					allowed = true;
				}
			}
		} else {
			String error = UiRuleHelper.checkForValidNodeInGraph(userObject, "", type, extType, true);
			allowed = (error == null);
		}

		return allowed;
	}

}
