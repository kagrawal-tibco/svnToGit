package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.TSCommand;

public class ProcessUpdateCommand extends TSCommand implements
		IGraphCommand<TSEGraph> {
	
	private static final long serialVersionUID = 4872132728431669326L;
	private TSEGraph graph;
	private Map<String, Object> updateList;
	private ModelController modelController;
	private int commandType;

	public ProcessUpdateCommand(int type, ModelController controller, TSEGraph graph,  Map<String, Object> updateList) {
		this.commandType = type;
		this.graph = graph;
		this.modelController = controller;
		this.updateList = updateList;
	}
	
	protected void doAction() throws Throwable {
		boolean update = (updateList.size() > 0);

		super.doAction();
		EObject userObject = (EObject)graph.getUserObject();
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(userObject);
		if (update)
			getModelController().updateEmfModel(elementWrapper, updateList);
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
	
		
	}
	
	@Override
	protected void redoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.redoAction();
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
	public int getCommandType() {
		return this.commandType;
	}

	@Override
	public ModelController getModelController() {
		return this.modelController;
	}

	@Override
	public TSEGraph getNodeOrGraph() {
		return this.graph;
	}
	
	protected void finalize() throws Throwable {
		modelController = null;
		updateList.clear();
		updateList = null;
		super.finalize();
	}

}
