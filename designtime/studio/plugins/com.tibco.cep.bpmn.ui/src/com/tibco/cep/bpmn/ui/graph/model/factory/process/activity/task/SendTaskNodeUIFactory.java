package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

/**
 * @author pdhar
 *
 */
public class SendTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = -1883049145243074653L;

	public SendTaskNodeUIFactory(String name, String referredBEResource, String toolId, BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager,BpmnModelClass.SEND_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.send.task"));//$NON-NLS-1$
		}
	}

	
	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.SEND_EVENT_IMAGE;
	}
	
}
