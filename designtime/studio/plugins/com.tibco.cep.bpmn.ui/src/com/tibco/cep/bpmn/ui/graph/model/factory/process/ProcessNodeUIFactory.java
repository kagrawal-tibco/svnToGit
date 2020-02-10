package com.tibco.cep.bpmn.ui.graph.model.factory.process;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ProcessNodeUIFactory extends AbstractNodeUIFactory {

	private static final long serialVersionUID = -5239273756828856889L;

	public ProcessNodeUIFactory(String name, String referredBEResource, String toolId,	BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.PROCESS);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.process"));//$NON-NLS-1$
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void decorateNode(TSENode node) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void layoutNode(TSENode node) {
		// TODO Auto-generated method stub
		
	}
}
