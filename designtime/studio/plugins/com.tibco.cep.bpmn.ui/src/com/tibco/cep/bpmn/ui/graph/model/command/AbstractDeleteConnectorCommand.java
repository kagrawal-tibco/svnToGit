package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEDeleteConnectorCommand;

/**
 * 
 * @author majha
 *
 */
abstract public class  AbstractDeleteConnectorCommand extends TSEDeleteConnectorCommand implements IGraphCommand<TSEConnector> {

	private static final long serialVersionUID = -2647546892713146400L;
	protected ModelController modelController;
	protected boolean deleteEmfModelOnly;
	protected TSENode parentNode;
	private int commandType;
	protected EObjectWrapper<EClass, EObject> parentObject;
	private TSEGraph currentGraph;
	private EObjectWrapper<EClass, EObject> lane;
	private TSGraph rootGraph;
	private EObjectWrapper<EClass, EObject> process;
//	private String nodeName;

	public AbstractDeleteConnectorCommand(int commandType, ModelController controller, TSENode parentNode,
			TSEConnector connector) {
		super(connector);
		this.commandType = commandType;
		this.parentNode = parentNode;
		this.modelController = controller;
	}
	
	public ModelController getModelController() {
		return modelController;
	}
	

	protected void doAction() throws Throwable {
		TSENode parentNode = getNode();
		parentObject = EObjectWrapper.wrap((EObject)parentNode.getUserObject());
		
		currentGraph = (TSEGraph)getNode().getOwnerGraph();
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			rootGraph = currentGraph;
			process = lane;
		}
		else{
			rootGraph = ((TSEGraph)getNode().getOwnerGraph()).getGreatestAncestor();
			process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		}
		
		deleteEmfModel();
		
		if (!deleteEmfModelOnly)
			super.doAction();
		
	}

	protected void undoAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.undoAction();	
		addElement();
		
		// check if there are edges ending on this node
		@SuppressWarnings("unused")
		List<EObjectWrapper<EClass, EObject>> incoming = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNodeOrGraph().inEdges().iterator();
		while(inEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) inEdgeIterator.next();
			createSequenceFlow( edge);
		}
		
		// check if there are edges starting from this node
		@SuppressWarnings("unused")
		List<EObjectWrapper<EClass, EObject>> outgoing = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> outEdgeIterator = getNodeOrGraph().outEdges().iterator();
		while(outEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) outEdgeIterator.next();
			createSequenceFlow( edge);
		}
	}

	protected void redoAction() throws Throwable {
		deleteEmfModel();
		if (!deleteEmfModelOnly)
			super.redoAction();
	}

	public void setDeleteEmfModelOnly(boolean deleteEmfModelOnly) {
		this.deleteEmfModelOnly = deleteEmfModelOnly;
	}
	
	
	public List<TSGraphObject> getAffectedObjects() {
		List<TSGraphObject> objects = new LinkedList<TSGraphObject>();
		objects.add(getNode());
		return objects;
	}
	
	@Override
	public TSEConnector getNodeOrGraph() {
		return getConnector();
	}
		
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSEDeleteConnectorCommand.class) {
			return this;
		}  
		return null;
	}

	@Override
	public int getCommandType() {
		// TODO Auto-generated method stub
		return commandType;
	}
	
	private void deleteEmfModel(){
		EObjectWrapper<EClass, EObject> modelObject = EObjectWrapper.wrap((EObject) getNodeOrGraph().getUserObject());
		// check if there are edges ending on this node
		List<EObjectWrapper<EClass, EObject>> incoming = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNodeOrGraph().inEdges().iterator();
		while(inEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) inEdgeIterator.next();
			removeSequenceFlow( edge);
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(seqFlowWrapper.getEClassType())){
					getAffectedObjects().add(edge);
				}
			}
		}
		
		// check if there are edges starting from this node
		List<EObjectWrapper<EClass, EObject>> outgoing = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> outEdgeIterator = getNodeOrGraph().outEdges().iterator();
		while(outEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) outEdgeIterator.next();
			removeSequenceFlow( edge);
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(seqFlowWrapper.getEClassType())){
					getAffectedObjects().add(edge);
				}
			}
		}
		removeElement(parentObject, modelObject, incoming, outgoing);
	}
	


	private void removeSequenceFlow(TSEdge edge){
		EObjectWrapper<EClass, EObject> sequenceFlow  = EObjectWrapper.wrap((EObject)edge.getUserObject());		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)edge.getTargetNode().getUserObject());
		if(BpmnModelClass.ARTIFACT.isSuperTypeOf(sequenceFlow.getEClassType()))
			getModelController().removeAssociation(sequenceFlow, start, end, process, lane);
		else
			getModelController().removeSequenceFlow(sequenceFlow, start, end, process, lane);
	}
	
	private void createSequenceFlow(TSEdge edge){
		EClass type = (EClass)edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)edge.getTargetNode().getUserObject());
		EObject userObject = (EObject) edge.getUserObject();
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
				.wrap(userObject);
		if(BpmnModelClass.ARTIFACT.isSuperTypeOf(type))
			getModelController().addAssociationToModel(wrapper,start, end, process);
		else
			getModelController().addSequenceFlowToModel(wrapper,start, end, process, lane);
	}
	
	abstract protected void removeElement(EObjectWrapper<EClass, EObject> parent, EObjectWrapper<EClass, EObject> modelObject,
			List<EObjectWrapper<EClass, EObject>> incoming,
			List<EObjectWrapper<EClass, EObject>> outgoing);
	
	abstract protected void addElement();

}
