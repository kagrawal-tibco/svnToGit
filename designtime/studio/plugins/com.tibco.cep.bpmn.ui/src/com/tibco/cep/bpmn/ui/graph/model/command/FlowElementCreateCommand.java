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
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * @author majha
 *
 */
public abstract class FlowElementCreateCommand extends AbstractCreateNodeCommand {

	private static final long serialVersionUID = -4032424528478045518L;
	
	protected EClass nodeType;
	protected String nodeName;
//	protected String nodeLabel;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;
	protected List<EObjectWrapper<EClass, EObject>> incomingEdge;
	protected List<EObjectWrapper<EClass, EObject>> outgoingEdge;

	
	public FlowElementCreateCommand(int commandAdd, ModelController modelController,
			EClass modelType, ENamedElement extType, TSEGraph graph, double x, double y) {
		super(commandAdd,modelController,modelType,extType,graph,x,y);
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
		super.doAction();
		createEmfModel();
	}

	protected void undoAction() throws Throwable {
		super.undoAction();

		
		EObjectWrapper<EClass, EObject> modelObject = EObjectWrapper.wrap((EObject)getNode().getUserObject());
		
		
		// check if there are edges ending on this node
		incomingEdge = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNode().inEdges().iterator();
		while(inEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) inEdgeIterator.next();
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(seqFlowWrapper.getEClassType())){
					incomingEdge.add(seqFlowWrapper);
					getAffectedObjects().add(edge);
				}
			}
		}
		
		// check if there are edges starting from this node
		outgoingEdge = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> outEdgeIterator = getNode().outEdges().iterator();
		while(outEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) outEdgeIterator.next();
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(seqFlowWrapper.getEClassType())){
					outgoingEdge.add(seqFlowWrapper);
					getAffectedObjects().add(edge);
				}
			}
		}
		removeElement(modelObject);
		getNode().nullifyUserObject();
		
	}
	
	@Override
	protected void redoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.redoAction();
		createEmfModel();
	}
	
	private void createEmfModel(){
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		

		
		EObject element = createElement();
		getNode().setUserObject(element);
	}
	
	protected abstract EObject createElement();

	protected abstract void removeElement(EObjectWrapper<EClass, EObject> modelObject);
	
	@Override
	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		incomingEdge.clear();
		incomingEdge = null;
		outgoingEdge.clear();
		outgoingEdge = null;
		super.finalize();
	}
}
