package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public abstract class FlowElementDeleteCommand extends AbstractDeleteNodeCommand {

	private static final long serialVersionUID = 3092117840988218253L;
	protected EClass nodeType;
	protected ENamedElement extType;
	protected String nodeName;
	protected String nodeLabel;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;

	
	public FlowElementDeleteCommand(int commandAdd, ModelController modelController,EClass modelType, ENamedElement extType,TSENode node) {
		super(commandAdd,modelController, node);
		nodeType = modelType;
		this.extType = extType;
	}

	protected void doAction() throws Throwable {
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
		super.doAction();
	}

	protected void undoAction() throws Throwable {
		super.undoAction();

		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		
		EObject userObject = (EObject)getNode().getUserObject();
		getModelController().addFlowElement(userObject, process, lane);
		
		// check if there are edges ending on this node
		@SuppressWarnings("unused")
		List<EObjectWrapper<EClass, EObject>> incoming = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNode().inEdges().iterator();
		while(inEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) inEdgeIterator.next();
			createSequenceFlow( edge);
		}
		
		// check if there are edges starting from this node
		@SuppressWarnings("unused")
		List<EObjectWrapper<EClass, EObject>> outgoing = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> outEdgeIterator = getNode().outEdges().iterator();
		while(outEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) outEdgeIterator.next();
			createSequenceFlow( edge);
		}
	}
	
	@Override
	protected void redoAction() throws Throwable {
		deleteEmfModel();
		super.redoAction();
	}
	
	private void deleteEmfModel(){
		Object userObject = getNode().getUserObject();
		if(userObject == null){
			return;
		}
		EObjectWrapper<EClass, EObject> modelObject = EObjectWrapper.wrap((EObject) userObject);
		// check if there are edges ending on this node
		List<EObjectWrapper<EClass, EObject>> incoming = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNode().inEdges().iterator();
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
		Iterator<?> outEdgeIterator = getNode().outEdges().iterator();
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
		removeElement(modelObject, incoming, outgoing);
	}
	
	private void removeSequenceFlow(TSEdge edge){
		EObjectWrapper<EClass, EObject> sequenceFlow  = EObjectWrapper.wrap((EObject)edge.getUserObject());		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)edge.getTargetNode().getUserObject());
		if(BpmnModelClass.ARTIFACT.isSuperTypeOf(sequenceFlow.getEClassType()))
			getModelController().removeAssociation(sequenceFlow, start, end, process, lane);
		else
			getModelController().removeSequenceFlow(sequenceFlow, start, end, process, lane);
		
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)edge.getSourceNode(), getModelController().getDiagramManager());
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
		
		SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)edge.getSourceNode(), getModelController().getDiagramManager());
	}
	
	protected ENamedElement getExtendedType() {
		// TODO Auto-generated method stub
		return extType;
	}

	protected EClass getModelType() {
		// TODO Auto-generated method stub
		return nodeType;
	}
	protected abstract EObject createElement();

	protected abstract void removeElement(EObjectWrapper<EClass, EObject> modelObject,List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing);
	
	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		super.finalize();
	}
}
