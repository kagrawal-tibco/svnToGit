package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;

public class AssociationCreateCommand extends AbstractCreateEdgeCommand implements
		IGraphCommand<TSEEdge> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1424350371037574069L;
	private ModelController modelController;
	private int commandType;
	private EObjectWrapper<EClass, EObject> lane;
	private EObjectWrapper<EClass, EObject> process;

	@SuppressWarnings("rawtypes")
	public AssociationCreateCommand(int ctype, TSENode sourceNode, TSENode targetNode, List bendList, ModelController modelController) {
		super(sourceNode, targetNode, bendList);
		this.commandType = ctype;
		this.modelController = modelController;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSEAddEdgeCommand.class) {
			return this;
		}  
		return null;
	}

	@Override
	public int getCommandType() {
		return commandType;
	}

	@Override
	public ModelController getModelController() {
		return modelController;
	}

	@Override
	public TSEEdge getNodeOrGraph() {
		return getEdge();
	}
	
	@Override
	protected void doAction() throws Throwable {
		super.doAction();
		createEmfModel();
	}
	
	@Override
	public void redoAction() throws Throwable {
		super.redoAction();
		createEmfModel();
	}
	
	@Override
	protected void undoCleanup() {
		super.undoCleanup();
	}
	
	
	@Override
	protected void undoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.undoAction();
		EObjectWrapper<EClass, EObject> association  = EObjectWrapper.wrap((EObject)getEdge().getUserObject());
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getTargetNode().getUserObject());
		getModelController().removeAssociation(association, start, end, process, lane);
		getEdge().nullifyUserObject();
	}
	
	private void createEmfModel(){
		EObject laneObj = (EObject) getEdge().getOwnerGraph().getUserObject();
		lane = EObjectWrapper.wrap(laneObj);
		

		if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())) {
			process = lane;
		} else {
			EObject processObj = (EObject) ((TSEGraph) getEdge()
					.getOwnerGraph()).getGreatestAncestor().getUserObject();
			process = EObjectWrapper.wrap(processObj);
		}
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getTargetNode().getUserObject());
		EObjectWrapper<EClass, EObject> association = getModelController().createAssociation(start, end, process);
		getEdge().setUserObject(association.getEInstance());
	}

}
