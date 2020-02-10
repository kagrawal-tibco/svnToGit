package com.tibco.cep.bpmn.ui.graph.rule;


public class TempRuleImpl extends AbstractDiagramRule implements DiagramRule {

	public TempRuleImpl(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}


	@Override
	public boolean isAllowed(Object[] args) {
//		TSENode srcTSNode = getRuleset().getSourceNode();
//		TSENode tgtTSNode = getRuleset().getTargetNode();
//		if(srcTSNode != null && tgtTSNode!=null){
//			StateEntity srcSmNode = (StateEntity) srcTSNode.getUserObject();
//			StateEntity tgtSmNode = (StateEntity) tgtTSNode.getUserObject();
//			if (srcSmNode instanceof StateStart &&
//					tgtSmNode instanceof StateStart) {
//					return false;
//				}
//				else {
//					return true;
//				}
//		}
		return false;
	}


	

}
