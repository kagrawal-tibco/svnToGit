package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

public class BusinessRuleTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = 3596568404297039260L;

	public BusinessRuleTaskNodeUIFactory(String name,String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource,  toolId, layoutManager,BpmnModelClass.BUSINESS_RULE_TASK);
		if(getNodeName() == null){
			setNodeName(Messages.getString("title.businessrule.task"));//$NON-NLS-1$
		}
	}
	

	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.TABLE_IMAGE;
	}
	
}
