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
public class GatewayCreateCommand extends FlowElementCreateCommand {
	
	private static final long serialVersionUID = -7335404990714014618L;

	public GatewayCreateCommand(int commandAdd, ModelController modelController,
			EClass modelType, ENamedElement extType, TSEGraph graph, double x, double y) {
		super(commandAdd,modelController,modelType,extType,graph,x,y);
	}

	@Override
	protected EObject createElement() {
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createGateway(nodeName, toolId,getModelType(),null,process,lane);
		return modelObject.getEInstance();
	}
	
	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeGateway(modelObject,process,lane,incomingEdge,outgoingEdge);
	}
		
}
