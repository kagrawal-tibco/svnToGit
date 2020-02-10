package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEDeleteNodeCommand;

public abstract class  AbstractDeleteNodeCommand extends TSEDeleteNodeCommand implements IGraphCommand<TSENode> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2694380175925030052L;
	int commandType;
	protected ModelController modelController;
	protected boolean deleteEmfModelOnly;

	public AbstractDeleteNodeCommand(int type,
							ModelController controller,
							TSENode node) {
		super(node);	
		this.commandType = type;
		this.modelController = controller;
	}
	
	public ModelController getModelController() {
		return modelController;
	}
	

	protected void doAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.doAction();
	}

	protected void undoAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.undoAction();
	}

	protected void redoAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.redoAction();
	}

	public void setDeleteEmfModelOnly(boolean deleteEmfModelOnly) {
		this.deleteEmfModelOnly = deleteEmfModelOnly;
	}

	public int getCommandType() {
		return commandType;
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
		if(type == TSEDeleteNodeCommand.class) {
			return this;
		}  
		return null;
	}

}
