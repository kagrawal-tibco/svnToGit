package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;

public abstract class  AbstractUpdateNodeCommand extends TSCommand implements IGraphCommand<TSENode> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2694380175925030052L;
	int commandType;
	protected ModelController modelController;
	private ENamedElement extendedType;
	private EClass modelType;
	private TSENode node;
	

	public AbstractUpdateNodeCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node) {
		
		this.commandType = type;
		this.modelController = controller;
		this.modelType = modelType;
		this.extendedType = extType;
		this.node = node;
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
	
	
	public ENamedElement getExtendedType() {
		return extendedType;
	}
	
	
	
	public List<TSGraphObject> getAffectedObjects() {
		List<TSGraphObject> objects = new LinkedList<TSGraphObject>();
		objects.add(getNode());
		return objects;
	}
	
	private TSENode getNode() {
		// TODO Auto-generated method stub
		return node;
	}

	@Override
	public TSENode getNodeOrGraph() {
		return getNode();
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
		extendedType = null;
		modelType = null;
		node = null;
		super.finalize();
	}

}
