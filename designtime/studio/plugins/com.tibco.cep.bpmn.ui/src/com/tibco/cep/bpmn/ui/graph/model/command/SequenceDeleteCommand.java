package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;

public class SequenceDeleteCommand extends AbstractDeleteEdgeCommand implements
		IGraphCommand<TSEEdge> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1424350371037574069L;
	private ModelController modelController;
	private int commandType;
	private EObjectWrapper<EClass, EObject> lane;
	private EObjectWrapper<EClass, EObject> process;
	private boolean connectedToConnector;
	private EObjectWrapper<EClass, EObject> connectorObj;
	private TSEConnector connector;
	

	public SequenceDeleteCommand(int ctype,TSEEdge edge, boolean connectedToConnector,ModelController modelController) {
		super(edge);
		this.commandType = ctype;
		this.modelController = modelController;
		this.connectedToConnector = connectedToConnector;
		if(connectedToConnector)
			initializeConnector();
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
		if(connectedToConnector && (connector != null))
			getEdge().setSourceConnector(connector);
		
		EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getEdge().getSourceNode().getUserObject());
		if(connectedToConnector){
			if(connectorObj != null)
				start = connectorObj;
		}
		EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getEdge().getTargetNode().getUserObject());
		EObject userObject = (EObject) getEdge().getUserObject();
		EObjectWrapper<EClass, EObject> sequenceWrapper = EObjectWrapper
				.wrap(userObject);
		getModelController().addSequenceFlowToModel(sequenceWrapper,start, end, process, lane);
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)getEdge().getSourceNode(), getModelController().getDiagramManager());
		
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
		
		
		EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getEdge().getSourceNode().getUserObject());
		if(connectedToConnector){
			if(connectorObj != null)
				start = connectorObj;
		}
		EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getEdge().getTargetNode().getUserObject());
		getModelController().removeSequenceFlow(sequenceFlow, start, end, process, lane);
		
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)edge.getSourceNode(), getModelController().getDiagramManager());
	}

	@SuppressWarnings("rawtypes")
	private void initializeConnector(){
		if(connectedToConnector){
			List connectors = ((TSENode)getEdge().getSourceNode()).connectors();
			@SuppressWarnings("unused")
			ListIterator listIterator = connectors.listIterator();
			for (Object object : connectors) {
				if(object instanceof TSEConnector){
					TSEConnector connec = (TSEConnector)object;
					Object userObject = connec.getUserObject();
					if (userObject != null && userObject instanceof EObject) {
						EObject connecObj = (EObject) userObject;
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(connecObj);
						if(wrap.getEClassType().equals(BpmnModelClass.BOUNDARY_EVENT)){
							connectorObj = wrap;
							connector = connec;
							break;
						}
					}
				}
			}
		}
	}
	
	protected void finalize() throws Throwable {
		modelController = null;
		process = null;
		lane = null;
		super.finalize();
	}
}
