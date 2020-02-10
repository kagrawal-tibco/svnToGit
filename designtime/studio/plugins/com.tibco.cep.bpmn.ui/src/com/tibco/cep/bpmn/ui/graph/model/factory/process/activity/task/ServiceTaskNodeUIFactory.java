package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

public class ServiceTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = -3870355954967015124L;

	public ServiceTaskNodeUIFactory(String name,String referredBEResource,  String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager,BpmnModelClass.SERVICE_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.webservice.task"));
		}
	}
	

	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.WS_IMAGE;
	}
	

}
