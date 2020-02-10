package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public class GatewayInsertCommand extends FlowElementInsertCommand {

	private static final long serialVersionUID = -5392842773213751080L;
	
	public GatewayInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node) {
		super(type, controller, modelType, extType, graph, node);
	}

	

	@Override
	protected EObject createElement() {
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createGateway(nodeName,toolId,getModelType(),null,process,lane);
		return modelObject.getEInstance();
	}
	
	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeGateway(modelObject,process,lane,incomingEdge,outgoingEdge);
	}

}
