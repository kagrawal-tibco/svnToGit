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
public class JavaTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = -8615597163568175458L;

	public JavaTaskNodeUIFactory(String name,String referredBEResource, String toolId, BpmnLayoutManager layoutManager) {
		super(name,referredBEResource, toolId, layoutManager,BpmnModelClass.JAVA_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.javatask"));
		}
	}

	
	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.SCRIPT_IMAGE;
	}
	

}
