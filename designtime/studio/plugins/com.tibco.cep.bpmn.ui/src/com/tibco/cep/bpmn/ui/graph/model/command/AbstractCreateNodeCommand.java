package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;

public abstract class  AbstractCreateNodeCommand extends TSEAddNodeCommand implements IGraphCommand<TSENode> {

	private static final long serialVersionUID = -2694380175925030052L;
	int commandType;
	protected ModelController modelController;
	private ENamedElement extendedType;
	private EClass modelType;
	protected boolean createEmfModelOnly;
	
	public AbstractCreateNodeCommand(int type,ModelController controller,EClass modelType, ENamedElement extType, TSEGraph graph,double x,double y) {
		super(graph,x,y);
		
		this.commandType = type;
		this.modelController = controller;
		this.modelType = modelType;
		this.extendedType = extType;
	}
	
	
	protected void doAction() throws Throwable {
		if (!createEmfModelOnly)
			super.doAction();
	}

	protected void undoAction() throws Throwable {
		if (!createEmfModelOnly)
			super.undoAction();
	}

	protected void redoAction() throws Throwable {
		if (!createEmfModelOnly)
			super.redoAction();
	}
	
	public void setCreateEmfModelOnly(boolean deleteEmfModelOnly) {
		this.createEmfModelOnly = deleteEmfModelOnly;
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
	
	@Override
	public TSENode getNodeOrGraph() {
		return getNode();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSEAddNodeCommand.class) {
			return this;
		}  
		return null;
	}

}
