package com.tibco.cep.bpmn.ui.editor;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

public interface IBpmnDiagramManager {

	public ModelController getModelController();
	
	public boolean isPrivateProcess();
	
    public void refreshNode(TSENode node);
	
	public void refreshEdge(TSEEdge edge);
	
	public void onNodeMoved(TSENode tsNode);
	
	public void addAdditionalModel(Map<String, Object> updateMap, EObject flowNode, String attachedResource);
	
	
}
