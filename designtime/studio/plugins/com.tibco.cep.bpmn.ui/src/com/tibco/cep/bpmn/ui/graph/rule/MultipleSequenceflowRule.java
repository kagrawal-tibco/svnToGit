package com.tibco.cep.bpmn.ui.graph.rule;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

public class MultipleSequenceflowRule extends AbstractDiagramRule implements DiagramRule {

	private boolean reconnect;
	private String checkForValidSequence;
	
	public MultipleSequenceflowRule(DiagramRuleSet ruleSet, boolean reconnect) {
		super(ruleSet);
		this.reconnect = reconnect;
	}


	@Override
	public boolean isAllowed(Object[] args) {
		checkForValidSequence = null;
		if(args.length != 3 ) {
			checkForValidSequence = BpmnMessages.getString("connectorSequenceRule_notValidConn_Label");
			return false;
		}
		TSENode srcTSNode = (TSENode) args[0];
		TSENode tgtTSNode = (TSENode) args[1];
		TSEEdge edge =(TSEEdge)args[2];
		EClass edgeType = (EClass)edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);	
		if(edgeType != null ){
			// for edge other than sequence this rule is not valid, if we use same rule for message flow later, need to change the logic
			if(!edgeType.equals(BpmnModelClass.SEQUENCE_FLOW)) {
				checkForValidSequence = BpmnMessages.getString("connectorSequenceRule_notValidSeq_Label");
				return false;
			}
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
				@SuppressWarnings("unused")
				EObject tgtEObj = (EObject) targetObj;
				if(reconnect){
					if(edge.getSourceNode() != srcTSNode){
						checkForValidSequence = UiRuleHelper.checkForMultipleIncomingOutgoingFLow( srcEObj, true);
					}else{
						checkForValidSequence = null;//UiRuleHelper.checkForMultipleIncomingOutgoingFLow( tgtEObj, false);
					}
					
				}else{
					checkForValidSequence = UiRuleHelper.checkForMultipleIncomingOutgoingFLow( srcEObj, true);
//					if(checkForValidSequence == null) 
//						checkForValidSequence = null;//UiRuleHelper.checkForMultipleIncomingOutgoingFLow( tgtEObj, false);
				}
				valid = (checkForValidSequence == null);
			}
				
		}
		return valid;
	}
	
	public String getValidSequenceMessage() {
		return checkForValidSequence;
	}
}