package com.tibco.cep.bpmn.ui.graph.model;

import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public interface IGraphUIFactory {
	
	TSEObjectUI initGraphUI(Object ...args);
	
	public IDiagramModelAdapter getDiagramModelAdapter();

}
