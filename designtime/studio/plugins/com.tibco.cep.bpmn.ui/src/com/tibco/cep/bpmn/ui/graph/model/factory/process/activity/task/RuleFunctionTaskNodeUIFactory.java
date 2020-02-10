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
public class RuleFunctionTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = -8615597163568175458L;

	public RuleFunctionTaskNodeUIFactory(String name,String referredBEResource, String toolId, BpmnLayoutManager layoutManager) {
		super(name,referredBEResource, toolId, layoutManager,BpmnModelClass.RULE_FUNCTION_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.rulefunction"));
		}
	}

	
	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.SCRIPT_IMAGE;
	}
	

}
