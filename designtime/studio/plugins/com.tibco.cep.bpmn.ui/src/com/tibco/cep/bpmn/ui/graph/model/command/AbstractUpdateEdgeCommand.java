package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.command.TSCommand;

public abstract class  AbstractUpdateEdgeCommand extends TSCommand implements IGraphCommand<TSEEdge> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6221074767146867145L;
	int commandType;
	protected ModelController modelController;
	private EClass modelType;
	private TSEEdge edge;
	
	
	
	
	public AbstractUpdateEdgeCommand(int type,ModelController controller,EClass modelType,TSEEdge edge) {
		
		this.commandType = type;
		this.modelController = controller;
		this.modelType = modelType;
		this.edge = edge;
	}
	
	public ModelController getModelController() {
		return modelController;
	}


	public int getCommandType() {
		return commandType;
	}
	
	public EClass getModelType() {
		return modelType;
	}
	
	
	
	
	
	public List<TSGraphObject> getAffectedObjects() {
		List<TSGraphObject> objects = new LinkedList<TSGraphObject>();
		objects.add(getEdge());
		return objects;
	}
	
	private TSEEdge getEdge() {
		// TODO Auto-generated method stub
		return edge;
	}

	@Override
	public TSEEdge getNodeOrGraph() {
		return getEdge();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSCommand.class) {
			return this;
		}  
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		modelController = null;
		edge = null;
		modelType = null;
		super.finalize();
	}
}
