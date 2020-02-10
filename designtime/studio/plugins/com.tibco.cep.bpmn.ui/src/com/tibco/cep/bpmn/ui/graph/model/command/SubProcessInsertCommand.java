package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public class SubProcessInsertCommand extends FlowElementInsertCommand {

	private static final long serialVersionUID = 6598540339585421205L;
	private BpmnDiagramManager diagramManager;
	private boolean isRedoActive;
	private EObjectWrapper<EClass, EObject> deletedModel;
	
	
	public SubProcessInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node, BpmnDiagramManager manager) {
		super(type, controller, modelType, extType, graph, node);
		this.diagramManager = manager;
	}

	

	@Override
	protected EObject createElement() {
		if(isRedoActive && deletedModel != null){
			getModelController().addFlowElement( deletedModel.getEInstance(), process,lane);
			return deletedModel.getEInstance();
		}
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String name = nodeName.replaceAll("-", "_");
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createActivity(name,toolId,getModelType(),process,lane);
		TSEGraph childGraph = (TSEGraph) getNode().getChildGraph();
		if(childGraph != null) {
			childGraph.setUserObject(modelObject.getEInstance());
		}
		
		return modelObject.getEInstance();
	}
	
	
	@Override
	protected void createEmfModel(boolean isRedo) {
		isRedoActive = true;
		super.createEmfModel(isRedo);
		isRedoActive = false;
		
		if(!isRedo)
			this.diagramManager.createSubprocessInitialChild(getNode());
	}
	
	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeTask(modelObject,process,lane,incomingEdge,outgoingEdge);
		deletedModel = modelObject;
		
	}


}
