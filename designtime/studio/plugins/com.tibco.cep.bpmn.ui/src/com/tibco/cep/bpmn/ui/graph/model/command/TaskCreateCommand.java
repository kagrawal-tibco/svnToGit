package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * @author pdhar
 *
 */
public class TaskCreateCommand extends FlowElementCreateCommand {

	private static final long serialVersionUID = -3659762336332584818L;

	public TaskCreateCommand(int commandAdd, ModelController modelController,
			EClass modelType, ENamedElement extType, TSEGraph graph, double x, double y) {
		super(commandAdd,modelController,modelType,extType,graph,x,y);
	}	

	protected EObject createElement() {
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = 
				getModelController().createActivity(nodeName,toolId,getModelType(),process,lane);
		return modelObject.getEInstance();
	}
	
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeTask(modelObject,process,lane,incomingEdge,outgoingEdge);
	}

}
