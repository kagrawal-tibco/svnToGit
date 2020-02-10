package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEEdge;

public class SequenceUpdateCommand extends AbstractUpdateEdgeCommand implements
		IGraphCommand<TSEEdge> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8321521033450832490L;

	public SequenceUpdateCommand(int type, 
								ModelController controller,
								EClass modelType, 
								TSEEdge edge,
								Map<String, Object> updateList) {
		super(type, controller, modelType,edge);
		// TODO Auto-generated constructor stub
	}

	

}
