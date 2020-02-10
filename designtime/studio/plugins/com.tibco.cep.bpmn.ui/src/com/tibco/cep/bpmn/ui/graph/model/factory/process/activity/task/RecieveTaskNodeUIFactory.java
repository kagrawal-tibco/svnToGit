package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

public class RecieveTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = -8980085779647645697L;

	public RecieveTaskNodeUIFactory(String name,String referredBEResource, String toolId, BpmnLayoutManager layoutManager) {
		super(name,  referredBEResource, toolId, layoutManager,BpmnModelClass.RECEIVE_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.receive.task"));//$NON-NLS-1$
		}
	}


	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.RECEIVE_EVENT_IMAGE;
	}
	
}
