package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNCallActivityNodeUI;
import com.tibco.cep.diagramming.ui.CollapsedSubprocessNodeUI;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.TSObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * @author majha
 *
 */
public class CallActivityNodeUIFactory extends AbstractActivityNodeUIFactory {

	private static final long serialVersionUID = -4734758123406455201L;

	public CallActivityNodeUIFactory(String name, String referredBEResource, String toolId, BpmnLayoutManager layoutManager,boolean isExpanded) {
		super(name, referredBEResource, toolId, layoutManager,BpmnModelClass.CALL_ACTIVITY,isExpanded);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.callactivity"));//$NON-NLS-1$
		}
		
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return initGraphUI();
	}

	private TSEObjectUI initGraphUI() {
			CollapsedSubprocessNodeUI subprocessUI = new BPMNCallActivityNodeUI();
			subprocessUI.setFillColor(BpmnUIConstants.SUB_PROCESS_FILL_COLOR);
			subprocessUI.setIsCallActivity(true);
			subprocessUI.setBorderDrawn(true);
			subprocessUI.setBorderWidth(6);
			// subprocessUI.setDrawChildGraphMark(false);
			return subprocessUI;
	}

	

	@Override
	public void decorateNode(TSENode node) {
		setNodeUI((TSNodeUI) initGraphUI());
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		node.setSize(70, 56);  // used to be 80, 60 for some reason

		String label = this.getNodeName();
		if (label == null) {
			node.setName("");
		}
		else {
			node.setName(label);
		}
		// node.setName(getNodeName());

		node.setUI(getNodeUI());
	}
	
	@Override
	public void layoutNode(TSENode node) {

		
	}
	

	public TSObjectUI getNodeUI(boolean b) {
		return initGraphUI(b);
	}


}
