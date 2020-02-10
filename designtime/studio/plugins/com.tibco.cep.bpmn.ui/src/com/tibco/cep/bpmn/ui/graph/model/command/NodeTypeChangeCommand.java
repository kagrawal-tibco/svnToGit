package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.PropertyNodeType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;

/**
 * @author majha
 *
 */
public class NodeTypeChangeCommand extends AbstractUpdateNodeCommand {

	private static final long serialVersionUID = -7752065218033507526L;
	protected String nodeName;
//	protected String nodeLabel;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;
	protected Map<String, Object> updateList;
	private ArrayList<EObjectWrapper<EClass, EObject>> incomingEdge;
	private ArrayList<EObjectWrapper<EClass, EObject>> outgoingEdge;
	
	private PropertyNodeType toType;
	@SuppressWarnings("unused")
	private PropertyNodeType fromType;
	private EObject oldModel;
	private EObject currentModel;
	@SuppressWarnings("unused")
	private Map<String, Object> oldProperties;
	
	public NodeTypeChangeCommand(int type,ModelController controller,TSENode node,
			PropertyNodeType fromType, PropertyNodeType toType, Map<String, Object> updateList) {
		super(type,controller,fromType.getNodeType(),fromType.getNodeExtType(),node);
		this.updateList = updateList;
		this.toType = toType;
		this.fromType = fromType;
	}
	
	@Override
	protected void init() {
		super.init();
		rootGraph = ((TSEGraph)getNodeOrGraph().getOwnerGraph()).getGreatestAncestor();
		currentGraph = (TSEGraph)getNodeOrGraph().getOwnerGraph();
		
		nodeName = (String) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			rootGraph = currentGraph;
			process = lane;
		}
		
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
		
		TSENode node = getNodeOrGraph();
		oldModel = (EObject)node.getUserObject();
	}

	protected void doAction() throws Throwable {
		super.doAction();
	
		if(currentModel == null)
			currentModel = createElement(toType);
		else
			addElement(currentModel);
		
		if (currentModel != null) {
			associateModelWithEdgeAndNode(currentModel);
			removeElement(EObjectWrapper.wrap(oldModel));
			getNodeOrGraph().setUserObject(currentModel);
			updateNodePosition(getNodeOrGraph());
		}
//		oldProperties = getModelController().updateEmfModel(EObjectWrapper.useInstance(currentModel), updateList);
	
	}
	
	@SuppressWarnings("rawtypes")
	private void updateNodePosition(TSENode node) {
		EObject userObject = (EObject) node.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
			if (ExtensionHelper.isValidDataExtensionAttribute(wrap, BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT)) {
				Map<String, Object> updateList = new HashMap<String, Object>();
				TSConstPoint center = node.getCenter();
				EObjectWrapper<EClass, EObject> createPoint = getModelController().createPoint(center.getX(), center.getY());
				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT, createPoint.getEInstance());
				List labels = node.getLabels();
				if (labels.size() > 0) {
					TSENodeLabel label = (TSENodeLabel) labels.get(0);
					TSConstPoint labelCenter = label.getCenter();
					EObjectWrapper<EClass, EObject> labelPoint = getModelController().createPoint(labelCenter.getX(), labelCenter.getY());
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT, labelPoint.getEInstance());

				}
				modelController.updateEmfModel(wrap, updateList);
			}
		}
	}

	protected void undoAction() throws Throwable {
		super.undoAction();

		if (currentModel != null) {
			addElement(oldModel);
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
		String toolId = (String)getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EClass nodeType = tType.getNodeType();
		EClass extType = tType.getNodeExtType();
		if (BpmnModelClass.EVENT.isSuperTypeOf(nodeType))
			modelWrapper = getModelController().createEvent(nodeName,toolId, nodeType,
					extType, process, lane);
		else if (BpmnModelClass.GATEWAY.isSuperTypeOf(nodeType))
			modelWrapper = getModelController().createGateway(nodeName,toolId,
					nodeType, null, process, lane);
		else if (BpmnModelClass.ACTIVITY.isSuperTypeOf(nodeType))
			modelWrapper = getModelController().createActivity(nodeName,toolId,nodeType,
					process, lane);
		return modelWrapper.getEInstance();
	}

	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		if (BpmnModelClass.EVENT.isSuperTypeOf(modelObject.getEClassType()))
			getModelController().removeEvent(modelObject,process,lane,incomingEdge,outgoingEdge);
		else if (BpmnModelClass.GATEWAY.isSuperTypeOf(modelObject.getEClassType()))
			getModelController().removeGateway(modelObject,process,lane,incomingEdge,outgoingEdge);
		else if (BpmnModelClass.ACTIVITY.isSuperTypeOf(modelObject.getEClassType()))
			getModelController().removeTask(modelObject,process,lane,incomingEdge,outgoingEdge);
	}
	
	protected void addElement(EObject modelObject) {
		getModelController().addFlowElement(modelObject, process, lane);
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
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		updateList.clear();
		updateList = null;
		incomingEdge.clear();
		incomingEdge = null;
		outgoingEdge.clear();
		outgoingEdge = null;
		super.finalize();
	}
}
