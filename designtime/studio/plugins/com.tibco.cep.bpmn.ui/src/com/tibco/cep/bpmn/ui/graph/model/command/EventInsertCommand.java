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
public class EventInsertCommand extends FlowElementInsertCommand {
	private static final long serialVersionUID = -6297361078227421794L;

	public EventInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node) {
		super(type, controller, modelType, extType, graph, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected EObject createElement() {
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createEvent(nodeName,toolId,getModelType(),(EClass) getExtendedType(),process,lane);
		return modelObject.getEInstance();
	}
	
	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeEvent(modelObject,process,lane,incomingEdge,outgoingEdge);
		
	}
}
