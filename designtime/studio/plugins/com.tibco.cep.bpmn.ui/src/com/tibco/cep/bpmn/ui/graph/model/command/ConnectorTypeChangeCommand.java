package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.PropertyNodeType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * @author majha
 *
 */
public class ConnectorTypeChangeCommand extends  TSCommand implements IGraphCommand<TSEConnector> {

	private static final long serialVersionUID = -7752065218033507526L;
	protected String nodeName;
	protected Map<String, Object> updateList;
	private ArrayList<EObjectWrapper<EClass, EObject>> incomingEdge;
	private ArrayList<EObjectWrapper<EClass, EObject>> outgoingEdge;
	
	private PropertyNodeType toType;
	@SuppressWarnings("unused")
	private PropertyNodeType fromType;
	private EObject oldModel;
	private EObject currentModel;
	int commandType;
	protected ModelController modelController;
	private EClass modelType;
	private TSEConnector connector;
	
	public ConnectorTypeChangeCommand(int type,ModelController controller,TSEConnector node,
			PropertyNodeType fromType, PropertyNodeType toType, Map<String, Object> updateList) {
		this.commandType = type;
		this.modelController = controller;
		this.modelType = BpmnModelClass.BOUNDARY_EVENT;
		this.updateList = updateList;
		this.toType = toType;
		this.fromType = fromType;
		connector = node;
	}
	
	@Override
	protected void init() {
		super.init();
		
		nodeName = (String) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		
		// check if there are edges ending on this node
		incomingEdge = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> inEdgeIterator = getNodeOrGraph().inEdges().iterator();
		while(inEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) inEdgeIterator.next();
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(seqFlowWrapper.getEClassType())||
						BpmnModelClass.ASSOCIATION.isSuperTypeOf(seqFlowWrapper.getEClassType())){
					incomingEdge.add(seqFlowWrapper);
				}
			}
		}
		
		// check if there are edges starting from this node
		outgoingEdge = new ArrayList<EObjectWrapper<EClass, EObject>>();
		Iterator<?> outEdgeIterator = getNodeOrGraph().outEdges().iterator();
		while(outEdgeIterator.hasNext()) {
			TSEdge edge = (TSEdge) outEdgeIterator.next();
			Object edgeUserObj = edge.getUserObject();
			if(edgeUserObj != null && edgeUserObj instanceof EObject) {
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(seqFlowWrapper.getEClassType())||
						BpmnModelClass.ASSOCIATION.isSuperTypeOf(seqFlowWrapper.getEClassType())){
					outgoingEdge.add(seqFlowWrapper);
				}
			}
		}
		
		TSEConnector node = getNodeOrGraph();
		oldModel = (EObject)node.getUserObject();
	}

	protected void doAction() throws Throwable {
		super.doAction();
		oldModel = (EObject)connector.getUserObject();
		currentModel = createElement(toType);
		if (currentModel != null) {
			associateModelWithEdgeAndNode(currentModel);
			removeElement(EObjectWrapper.wrap(oldModel));
			getNodeOrGraph().setUserObject(currentModel);
		}
//		oldProperties = getModelController().updateEmfModel(EObjectWrapper.useInstance(currentModel), updateList);
	
	}

	protected void undoAction() throws Throwable {
		super.undoAction();

		if (currentModel != null) {
//			oldModel = createElement(fromType);
			associateModelWithEdgeAndNode(oldModel);
			removeElement(EObjectWrapper.wrap(currentModel));
			getNodeOrGraph().setUserObject(oldModel);
		}
//		getModelController().updateEmfModel(EObjectWrapper.useInstance(oldModel), oldProperties);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		doAction();
	}
	

	protected EObject createElement(PropertyNodeType tType) {
		EObjectWrapper<EClass, EObject> modelWrapper = null;
		EClass nodeType = tType.getNodeType();
		EObject userObject = (EObject)connector.getOwner().getUserObject();
		String toolId = (String)getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		if (BpmnModelClass.BOUNDARY_EVENT.isSuperTypeOf(nodeType))
			modelWrapper = getModelController().addBoundaryEvent(EObjectWrapper.wrap(userObject), tType.getNodeExtType(), toolId);
		return modelWrapper.getEInstance();
	}

	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		EObject userObject = (EObject)connector.getOwner().getUserObject();
		getModelController().removeBoundaryEvent(EObjectWrapper.wrap(userObject), modelObject, incomingEdge, outgoingEdge);
	}
	
	
	private void associateModelWithEdgeAndNode(EObject model){
		for (EObjectWrapper<EClass, EObject> sequenceFlow : outgoingEdge) {
			sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, model);
			
		}
		for (EObjectWrapper<EClass, EObject> sequenceFlow : incomingEdge) {
			sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, model);
		}
		
		getNodeOrGraph().setUserObject(model);
	}
	
	@SuppressWarnings("unused")
	private void removeModelWithEdgeAndNode(EObject model){
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(model);
		for(EObjectWrapper<EClass, EObject> inSeq:incomingEdge) {
			if(inSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( elementWrapper.getEInstance()))
				inSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
		}
		for(EObjectWrapper<EClass, EObject> outSeq:outgoingEdge) {
			if(outSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( elementWrapper.getEInstance()))
				outSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
		}
	}	
	
	protected void finalize() throws Throwable {
		nodeName = null;
//		nodeLabel = null;
		updateList.clear();
		updateList = null;
		incomingEdge.clear();
		incomingEdge = null;
		outgoingEdge.clear();
		outgoingEdge = null;
		super.finalize();
	}

	public ModelController getModelController() {
		return modelController;
	}


	public int getCommandType() {
		return commandType;
	}
	
	public EClass getModelType() {
		return modelType;
	}	
	
	
	public List<TSGraphObject> getAffectedObjects() {
		List<TSGraphObject> objects = new LinkedList<TSGraphObject>();
		objects.add(getNode());
		return objects;
	}
	
	private TSEConnector getNode() {
		// TODO Auto-generated method stub
		return connector;
	}

	@Override
	public TSEConnector getNodeOrGraph() {
		return getNode();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSCommand.class) {
			return this;
		}  
		return null;
	}
}
