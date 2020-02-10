package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class TextAnnotationDeleteCommand extends AbstractDeleteNodeCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6754074383243751208L;
	protected EClass nodeType;
	protected ENamedElement extType;
	protected String nodeName;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;
	
	public TextAnnotationDeleteCommand(int commandAdd, ModelController modelController,EClass modelType, ENamedElement extType,TSENode node) {
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
		
		EObject userObject = (EObject)getNode().getUserObject();
		getModelController().addArifact(userObject, process);
		
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
		EObjectWrapper<EClass, EObject> modelObject = EObjectWrapper.wrap((EObject) getNode().getUserObject());
		// check if there are edges ending on this node
		List<EObjectWrapper<EClass, EObject>> incoming = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNode().inEdges().iterator();
		while(inEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) inEdgeIterator.next();
			removeSequenceFlow( edge);
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.ASSOCIATION.isSuperTypeOf(associationWrapper.getEClassType())){
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
		final EObject laneObj = (EObject) edge.getOwnerGraph().getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		
		final EObject processObj = (EObject) ((TSEGraph)edge.getOwnerGraph()).getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);
		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)edge.getTargetNode().getUserObject());
		getModelController().removeAssociation(sequenceFlow, start, end, process, lane);
	}
	
	private void createSequenceFlow(TSEdge edge) {
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper
				.wrap((EObject) edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper
				.wrap((EObject) edge.getTargetNode().getUserObject());
		EObject userObject = (EObject) edge.getUserObject();
		EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper
				.wrap(userObject);
		getModelController().addAssociationToModel(associationWrapper, start,
				end, process);
	}
	
	protected ENamedElement getExtendedType() {
		// TODO Auto-generated method stub
		return extType;
	}

	protected EClass getModelType() {
		// TODO Auto-generated method stub
		return nodeType;
	}
	protected  EObject createElement() {
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createTextAnnotation(nodeName,getModelType(),process,lane);
		return modelObject.getEInstance();
	}

	protected  void removeElement(EObjectWrapper<EClass, EObject> modelObject,List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing){
			getModelController().removeTextAnnotation(modelObject,process,lane,incoming,outgoing);
	}
	
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
