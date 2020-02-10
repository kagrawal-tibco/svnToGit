package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public class LaneDeleteCommand extends AbstractDeleteNodeCommand {

	private static final long serialVersionUID = -7674437117031399410L;
	private ENamedElement extType;


	
	public LaneDeleteCommand(int commandAdd, ModelController modelController,EClass modelType, ENamedElement extType,TSENode node) {
		super(commandAdd,modelController, node);

		this.extType = extType;
	}	
	protected void doAction() throws Throwable {
		deleteNode(getNode());
		super.doAction();
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
		createNode(getNode());
	}	
	
	protected void redoAction() throws Throwable {
		deleteNode(getNode());
		super.redoAction();
	}
	
	
	@SuppressWarnings("rawtypes")
	private void deleteNode(TSENode node) {
		if(node.getUserObject() == null)
			return;
		TSGraph childGraph = node.getChildGraph();
		if (childGraph != null) {
			List nodeList = childGraph.nodeSet;
			List edgeList = childGraph.edgeSet;
			for (Object edge : edgeList) {
				deleteEdge((TSEEdge) edge);
			}
			for (Object childNode : nodeList) {
				deleteNode((TSENode) childNode);
			}
		}
		TSGraph rootGraph = ((TSEGraph) node.getOwnerGraph())
				.getGreatestAncestor();
		TSEGraph currentGraph = (TSEGraph) node.getOwnerGraph();
		EClass nodeType = (EClass) node
				.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		String nodeName = (String) node
				.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);

		EObjectWrapper<EClass, EObject> process = EObjectWrapper
				.wrap((EObject) rootGraph.getUserObject());
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper
				.wrap((EObject) currentGraph.getUserObject());
		EObjectWrapper<EClass, EObject> modelObject = EObjectWrapper
				.wrap((EObject) node.getUserObject());

		if (nodeType.equals(BpmnModelClass.LANE)) {
			EObjectWrapper<EClass, EObject> parentLaneSet = null;
			if (lane.isInstanceOf(BpmnModelClass.PROCESS)) {
				@SuppressWarnings("unchecked")
				EList<EObject> laneSets = (EList<EObject>) lane
						.getAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
				parentLaneSet = getModelController().createLaneSet(nodeName,null);
				laneSets.add(parentLaneSet.getEInstance());
			} else if (lane.isInstanceOf(BpmnModelClass.LANE)) {
				EObject childLaneSet = (EObject) lane
						.getAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
				parentLaneSet = EObjectWrapper.wrap(childLaneSet);
			}
			getModelController().removeLane(modelObject, parentLaneSet);
		} else {
			List<EObjectWrapper<EClass, EObject>> incoming = new ArrayList<EObjectWrapper<EClass, EObject>>();

			List<EObjectWrapper<EClass, EObject>> outgoing = new ArrayList<EObjectWrapper<EClass, EObject>>();
			if (BpmnModelClass.EVENT.isSuperTypeOf(nodeType))
				getModelController().removeEvent(modelObject, process, lane,
						incoming, outgoing);
			else if (BpmnModelClass.GATEWAY.isSuperTypeOf(nodeType))
				getModelController().removeGateway(modelObject, process, lane,
						incoming, outgoing);
			else if (BpmnModelClass.TASK.isSuperTypeOf(nodeType))
				getModelController().removeTask(modelObject, process, lane,
						incoming, outgoing);
			else if (BpmnModelClass.TEXT_ANNOTATION.isSuperTypeOf(nodeType))
				getModelController().removeTextAnnotation(modelObject, process, lane,
						incoming, outgoing);
		}
	}
	
	private void deleteEdge(TSEEdge edge){
		EObjectWrapper<EClass, EObject> sequenceFlow  = EObjectWrapper.wrap((EObject)edge.getUserObject());
		final EObject laneObj = (EObject) edge.getOwnerGraph().getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		
		final EObject processObj = (EObject) ((TSEGraph)edge.getOwnerGraph()).getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);
		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)edge.getTargetNode().getUserObject());
		EClass type = (EClass)edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
	
		if(BpmnModelClass.ARTIFACT.isSuperTypeOf(type))
			getModelController().removeAssociation(sequenceFlow, start, end, process, lane);
		else
			getModelController().removeSequenceFlow(sequenceFlow, start, end, process, lane);
	}
	
	@SuppressWarnings("rawtypes")
	private void createNode(TSENode node){
		TSGraph rootGraph = ((TSEGraph)node.getOwnerGraph()).getGreatestAncestor();
		TSEGraph currentGraph = (TSEGraph)node.getOwnerGraph();
		
		EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		String toolId = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String attachedRes = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		
		EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
		@SuppressWarnings("unused")
		EObjectWrapper<EClass, EObject> modelObject = null;
		if(BpmnModelClass.LANE.isSuperTypeOf(nodeType)){
			String nodeName = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//			String label = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);

			ExpandedName classSpec = null;
			if(extType != null)
				classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(extType);
			AbstractNodeUIFactory nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(nodeName, attachedRes, toolId, nodeType, classSpec);
			nodeUIFactory.decorateNode(node);
			nodeUIFactory.layoutNode(node);
			
			EObjectWrapper<EClass, EObject> parentLaneSet = null;
			if(lane.isInstanceOf(BpmnModelClass.PROCESS)) {
				@SuppressWarnings("unchecked")
				EList<EObject>laneSets = (EList<EObject>) lane.getAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
				parentLaneSet = getModelController().createLaneSet(nodeName,null);
				laneSets.add(parentLaneSet.getEInstance());
			} else if(lane.isInstanceOf(BpmnModelClass.LANE)) {
				    EObject childLaneSet = (EObject) lane.getAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
				    	parentLaneSet = EObjectWrapper.wrap(childLaneSet);
			}
			EObject userObject = (EObject)node.getUserObject();
			EObjectWrapper<EClass, EObject>wrapper = EObjectWrapper.wrap(userObject);
			modelObject = getModelController().addLaneToModel(wrapper,nodeName,parentLaneSet);

		}else if(BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(nodeType)){
			Object userObject = node.getUserObject();
			getModelController().addFlowElement((EObject)userObject, process, lane);
		}else if(BpmnModelClass.ARTIFACT.isSuperTypeOf(nodeType)){
			Object userObject = node.getUserObject();
			getModelController().addArifact((EObject)userObject, process);
		}
		
		TSEGraph childGraph = (TSEGraph) node.getChildGraph();
		if(childGraph != null) {
			List nodeList = childGraph.nodeSet;
			for (Object childNode : nodeList) {
				createNode((TSENode) childNode);
			}
			List edgeList = childGraph.edgeSet;
			for (Object edge : edgeList) {
				createEdge((TSEEdge) edge);
			}			
		}
	}
	
	private void createEdge(TSEEdge edge){
		final EObject laneObj = (EObject) edge.getOwnerGraph().getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		
		final EObject processObj = (EObject) ((TSEGraph)edge.getOwnerGraph()).getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);
		
		
		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject)edge.getSourceNode().getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject)edge.getTargetNode().getUserObject());
		EObject userObject = (EObject) edge.getUserObject();
		EClass type = (EClass)edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
				.wrap(userObject);
		if(BpmnModelClass.ARTIFACT.isSuperTypeOf(type))
			getModelController().addAssociationToModel(wrapper,start, end, process);
		else
			getModelController().addSequenceFlowToModel(wrapper,start, end, process, lane);
	}
	
	
}
