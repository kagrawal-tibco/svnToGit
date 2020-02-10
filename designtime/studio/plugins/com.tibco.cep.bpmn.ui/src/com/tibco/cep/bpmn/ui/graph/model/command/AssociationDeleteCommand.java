package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.editing.TSEDeleteEdgeCommand;

public class AssociationDeleteCommand extends AbstractDeleteEdgeCommand implements
		IGraphCommand<TSEEdge> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1424350371037574069L;
	private ModelController modelController;
	private int commandType;
	private EObjectWrapper<EClass, EObject> lane;
	private EObjectWrapper<EClass, EObject> process;

	public AssociationDeleteCommand(int ctype,TSEEdge edge, ModelController modelController) {
		super(edge);
		this.commandType = ctype;
		this.modelController = modelController;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSEDeleteEdgeCommand.class) {
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
		deleteEmfModel();
		super.doAction();
	}
	
	@Override
	public void redoAction() throws Throwable {
		deleteEmfModel();
		super.redoAction();
	}
	
	@Override
	protected void undoCleanup() {
		super.undoCleanup();
	}
	
	
	@Override
	protected void undoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.undoAction();		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getEdge().getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getEdge().getTargetNode().getUserObject());
		EObject userObject = (EObject) getEdge().getUserObject();
		EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper
				.wrap(userObject);
		getModelController().addAssociationToModel(associationWrapper,start, end, process);
	}
	
	private void deleteEmfModel(){
		EObjectWrapper<EClass, EObject> sequenceFlow  = EObjectWrapper.wrap((EObject)getEdge().getUserObject());
		EObject laneObj = (EObject) getEdge().getOwnerGraph().getUserObject();
		lane = EObjectWrapper.wrap(laneObj);
		

		if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())) {
			process = lane;
		} else {
			EObject processObj = (EObject) ((TSEGraph) getEdge()
					.getOwnerGraph()).getGreatestAncestor().getUserObject();
			process = EObjectWrapper.wrap(processObj);
		}
		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getEdge().getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getEdge().getTargetNode().getUserObject());
		getModelController().removeAssociation(sequenceFlow, start, end, process, lane);
	}

}
