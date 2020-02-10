/**
 * 
 */
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
public class InferenceTaskNodeUIFactory extends AbstractTaskNodeUIFactory {

	private static final long serialVersionUID = -4951988330531677312L;

	/**
	 * @param name
	 * @param layoutManager
	 */
	public InferenceTaskNodeUIFactory(String name,String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name,   referredBEResource, toolId,layoutManager,BpmnModelClass.INFERENCE_TASK);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.rule.task"));//$NON-NLS-1$
		}
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.ts.handler.model.process.activity.task.TaskNodeFactory#getTaskImage()
	 */
	@Override
	protected TSEImage getTaskImage() {
		return TaskNodeUI.RULE_IMAGE;
	}
	

}
