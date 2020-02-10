package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author mjha
 *
 */
public class SubProcessDeleteCommand extends FlowElementDeleteCommand {
	private static final long serialVersionUID = 8545752166964454365L;

	public SubProcessDeleteCommand(int commandAdd, ModelController modelController,EClass modelType, ENamedElement extType,TSENode node) {
		super(commandAdd,modelController,modelType, extType, node);
	}

	@Override
	protected EObject createElement() {
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = 
			getModelController().createActivity(nodeName,toolId,getModelType(),process,lane);
		return modelObject.getEInstance();
	}

	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject,
			List<EObjectWrapper<EClass, EObject>> incoming,
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		getModelController().removeTask(modelObject,process,lane,incoming,outgoing);
		
	}
	
}