package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;

/**
 * 
 * @author majha
 *
 */
public class SequenceInsertCommand extends AbstractInsertEdgeCommand implements
		IGraphCommand<TSEEdge> {
	private static final long serialVersionUID = -6752961437621792822L;
	private ModelController modelController;
	private int commandType;
	private EObjectWrapper<EClass, EObject> lane;
	private EObjectWrapper<EClass, EObject> process;

	public SequenceInsertCommand(int ctype, TSEEdge edge, ModelController modelController) {
		super(edge);
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
		EObjectWrapper<EClass, EObject> sequenceFlow  = EObjectWrapper.wrap((EObject)getEdge().getUserObject());		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getEdge().getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getEdge().getTargetNode().getUserObject());
		getModelController().removeSequenceFlow(sequenceFlow, start, end, process, lane);
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)getEdge().getSourceNode(), getModelController().getDiagramManager());
		getEdge().nullifyUserObject();
	}
	
	private void createEmfModel(){
		EObject laneObj = (EObject)getEdge().getOwnerGraph().getUserObject();
		lane = EObjectWrapper.wrap(laneObj);
		

		if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())) {
			process = lane;
		} else {
			EObject processObj = (EObject) ((TSEGraph)getEdge().getOwnerGraph()).getGreatestAncestor().getUserObject();
			process = EObjectWrapper.wrap(processObj);
		}
		
		
		EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getEdge().getSourceNode().getUserObject());
		EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getEdge().getTargetNode().getUserObject());
		EObjectWrapper<EClass, EObject> sequenceFlow = getModelController().createSequenceFlow(start, end, process, lane);
		getEdge().setUserObject(sequenceFlow.getEInstance());
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)getEdge().getSourceNode(), getModelController().getDiagramManager());
	}
	
	protected void finalize() throws Throwable {
		modelController = null;
		process = null;
		lane = null;
		super.finalize();
	}

}
