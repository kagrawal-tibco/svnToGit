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
public class TaskInsertCommand extends FlowElementInsertCommand {

	private static final long serialVersionUID = -3300174035300953009L;

	public TaskInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node) {
		super(type, controller, modelType, extType, graph, node);
		// TODO Auto-generated constructor stub
	}

	protected EObject createElement() {
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = getModelController()
			.createActivity(nodeName,toolId,getModelType(),process,lane);
		return modelObject.getEInstance();
	}
	
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeTask(modelObject,process,lane,incomingEdge,outgoingEdge);
	}

}
