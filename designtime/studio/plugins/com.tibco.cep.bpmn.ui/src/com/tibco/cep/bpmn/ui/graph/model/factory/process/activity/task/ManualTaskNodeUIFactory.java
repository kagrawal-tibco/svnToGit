package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

public class ManualTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = 6872429273670018118L;

	public ManualTaskNodeUIFactory(String name,  String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager,BpmnModelClass.MANUAL_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.manual.task"));//$NON-NLS-1$
		}
	}
	

	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.MANUAL_IMAGE;
	}
	
}
