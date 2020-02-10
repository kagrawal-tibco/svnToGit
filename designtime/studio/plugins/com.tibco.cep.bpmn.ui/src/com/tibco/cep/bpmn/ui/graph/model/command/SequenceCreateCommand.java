package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;

public class SequenceCreateCommand extends AbstractCreateEdgeCommand implements
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

	@SuppressWarnings("rawtypes")
	public SequenceCreateCommand(int ctype, TSENode sourceNode, TSENode targetNode, List bendList, boolean connectedToConnector, ModelController modelController) {
		super(sourceNode, targetNode, bendList);
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
		super.doAction();
		if(connectedToConnector && (connector != null))
			getEdge().setSourceConnector(connector);
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
		
		EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getSourceNode().getUserObject());
		if(connectedToConnector){
			if(connectorObj != null)
				start = connectorObj;
		}
		EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getTargetNode().getUserObject());
		getModelController().removeSequenceFlow(sequenceFlow, start, end, process, lane);
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)getSourceNode(), getModelController().getDiagramManager());
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
		
		
		EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)getSourceNode().getUserObject());
		if(connectedToConnector){
			if(connectorObj != null)
				start = connectorObj;
		}
		EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)getTargetNode().getUserObject());
		EObjectWrapper<EClass, EObject> sequenceFlow = getModelController().createSequenceFlow(start, end, process, lane);
		getEdge().setUserObject(sequenceFlow.getEInstance());
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway(getSourceNode(), getModelController().getDiagramManager());
		
		EClass edgeType = (EClass) getEdge().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		String lbl =sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		AbstractEdgeUIFactory edgeUIFactory = BpmnGraphUIFactory.getInstance(null)
		.getEdgeUIFactory(lbl,  edgeType);
		edgeUIFactory.addEdgeLabel(getEdge(), lbl);
	}
	
	@SuppressWarnings("rawtypes")
	private void initializeConnector(){
		if(connectedToConnector){
			List connectors = getSourceNode().connectors();
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
