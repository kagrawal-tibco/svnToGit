package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSETransferGroupCommand;

public class BpmnNodeTransferCommand extends TSETransferGroupCommand {

	private static final long serialVersionUID = -3923296948166945786L;
	private ModelController modelController;
	private Map<TSGraphObject, TSGraph> orginalGraphs;

	@SuppressWarnings("rawtypes")
	public BpmnNodeTransferCommand(List arg0, List arg1, List arg2, List arg3,
			List arg4, List arg5, List arg6, TSGraphObject arg7,
			TSConstPoint arg8, TSConstPoint arg9, ModelController controller) {
		super(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
		setAddToUndoHistory(true);
		modelController = controller;
		orginalGraphs = new HashMap<TSGraphObject, TSGraph>();
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void doAction() throws Throwable {
		TSGraphObject targetGraphObj = getTargetObject();
		
		List nodes = getNodes();
		@SuppressWarnings("unused")
		List graphs = getGraphs();
		Set<TSEEdge> edges = getConnectedEdge(nodes);
		buildOriginalGraphs(nodes, edges);
		
		super.doAction();

		@SuppressWarnings("unused")
		List<TSGraphObject> affectedObjects = new ArrayList<TSGraphObject>();
		if(targetGraphObj instanceof TSGraph) {
			
//			for(Object go: graphs) {
//				TSGraph g = (TSGraph) go;
//				if(g == targetGraphObj) {
//					continue;
//				}
//				EObject userObj = (EObject) g.getUserObject();
//				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.useInstance(userObj);
//				// not sure why the source graph is being listed which has LANE ????? 
//				if(!userObjWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)) {
//					continue;
//				}
//				if(targetUserObjWrapper.isInstanceOf(BpmnModelClass.FLOW_ELEMENTS_CONTAINER)){
//					targetUserObjWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS,userObj);
//					String id = BpmnModelUtils.nextIdentifier(targetUserObjWrapper, userObj.eClass());
//					userObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id);
//				}
//			}
//			
			for (Object go : nodes) {
				TSNode g = (TSNode) go;
				removeNode(orginalGraphs.get(g), g);
				addNode((TSGraph)targetGraphObj, g);
			}
			
			for (Object go : edges) {
				TSEdge g = (TSEdge) go;
				removeNode(orginalGraphs.get(g), g);
				addNode((TSGraph)targetGraphObj, g);
			}
		}
		
	}
	
	private void addNode(TSGraph graph, TSGraphObject object){
		EObjectWrapper<EClass, EObject> flowElementContainer = null;
		EObjectWrapper<EClass, EObject> lane = null;
		EObject targetUserObj = (EObject) graph.getUserObject();
		EObjectWrapper<EClass, EObject> targetUserObjWrapper = EObjectWrapper.wrap(targetUserObj);
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(targetUserObjWrapper.getEClassType())){
			lane = targetUserObjWrapper;
			EObject processObj = (EObject)((TSEGraph)graph).getGreatestAncestor().getUserObject();
			flowElementContainer = EObjectWrapper.wrap(processObj);
		}else{
			flowElementContainer = targetUserObjWrapper;//subprocess
		}
		
		if(object instanceof TSENode){
			TSNode g = (TSNode) object;
			EObject userObj = (EObject) g.getUserObject();
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObj);
			if (BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(userObjWrapper
					.getEClassType())){
				modelController.addFlowElement(userObj,
						flowElementContainer, lane);
			}
			else if (BpmnModelClass.ARTIFACT.isSuperTypeOf(userObjWrapper
					.getEClassType()))
				modelController.addArifact(userObj, flowElementContainer);
			
			regenerateIds(targetUserObjWrapper, userObjWrapper);
		}
		else if(object instanceof TSEEdge){
			TSEdge g = (TSEdge) object;
			EObject userObj = (EObject) g.getUserObject();
			EObjectWrapper<EClass, EObject> start = EObjectWrapper
					.wrap((EObject) g.getSourceNode()
							.getUserObject());
			EObjectWrapper<EClass, EObject> end = EObjectWrapper
					.wrap((EObject) g.getTargetNode()
							.getUserObject());
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObj);
			if (BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(userObjWrapper
					.getEClassType())){
				modelController.addSequenceFlowToModel(userObjWrapper,
						start, end, flowElementContainer, lane);
				IBpmnDiagramManager diagramManager = modelController.getDiagramManager();
				SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)g.getSourceNode(),diagramManager);
			}
			else if (BpmnModelClass.ASSOCIATION.isSuperTypeOf(userObjWrapper
					.getEClassType()))
				modelController.addAssociationToModel(userObjWrapper, start, end, flowElementContainer);
			
			regenerateIds(targetUserObjWrapper, userObjWrapper);
		}
	}
	
	private void removeNode(TSGraph graph, TSGraphObject object){
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap((EObject)object.getUserObject());
		EObjectWrapper<EClass, EObject> flowElementContainer = null;
		EObjectWrapper<EClass, EObject> lane = null;
		EObject graphUserObj = (EObject) graph.getUserObject();
		EObjectWrapper<EClass, EObject> graphUserObjWrapper = EObjectWrapper.wrap(graphUserObj);
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(graphUserObjWrapper.getEClassType())){
			lane = graphUserObjWrapper;
			EObject processObj = (EObject)((TSEGraph)graph).getGreatestAncestor().getUserObject();
			flowElementContainer = EObjectWrapper.wrap(processObj);
		}else{
			flowElementContainer = graphUserObjWrapper;//subprocess
		}
		
		if (BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(userObjWrapper
				.getEClassType()))
			modelController.removeFlowElement(userObjWrapper, flowElementContainer, lane);
		else if (BpmnModelClass.ARTIFACT.isSuperTypeOf(userObjWrapper
				.getEClassType()))
			modelController.removeArtifacts(userObjWrapper, flowElementContainer);
	}
	
	@SuppressWarnings("rawtypes")
	private Set<TSEEdge> getConnectedEdge(List nodes){
		Set<TSEEdge> edges = new HashSet<TSEEdge>();
		for (Object object : nodes) {
			TSENode node = (TSENode)object;
			Iterator<?> iterator = node.inEdges().iterator();
			while (iterator.hasNext()) {
				TSEEdge edge = (TSEEdge) iterator.next();
				TSNode sourceNode = edge.getSourceNode();
				if(nodes.contains(sourceNode))
					edges.add(edge);
			}
			
			iterator = node.outEdges().iterator();
			while (iterator.hasNext()) {
				TSEEdge edge = (TSEEdge) iterator.next();
				TSNode targetNode = edge.getTargetNode();
				if(nodes.contains(targetNode))
					edges.add(edge);
			}
		}
		return edges;
	}
	
	@SuppressWarnings("rawtypes")
	private void buildOriginalGraphs(List nodes,Set<TSEEdge> edges ){
		orginalGraphs.clear();
		for (TSEEdge object : edges) {
			orginalGraphs.put(object, object.getOwnerGraph());
		}
		
		for (Object object : nodes) {
			TSNode g = (TSNode) object;
			orginalGraphs.put(g, g.getOwnerGraph());
		}
	}
	
	private void regenerateIds(EObjectWrapper<EClass, EObject> container, EObjectWrapper<EClass, EObject> modelObject){
		Identifier id = BpmnModelUtils.nextIdentifier(container,
				modelObject.getEClassType(), null);
		modelObject.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,
				id.getId());
		
		if (BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(modelObject.getEClassType())){
			EList<EObject> flowElements = modelObject.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
			for (EObject object : flowElements) {
				regenerateIds(container, EObjectWrapper.wrap(object));
			}
			EList<EObject> artifacts = modelObject.getListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS);
			for (EObject object : artifacts) {
				regenerateIds(container, EObjectWrapper.wrap(object));
			}
		}
	}

	@Override
	protected void redoAction() throws Throwable {
		// TODO Auto-generated method stub
		doAction();
	}
	
	@Override
	protected void undoAction() throws Throwable {
		super.undoAction();
		TSGraphObject targetGraphObj = getTargetObject();
		Set<TSGraphObject> keySet = orginalGraphs.keySet();
		for (TSGraphObject graphObject : keySet) {
			TSGraph graph = orginalGraphs.get(graphObject);
			removeNode((TSGraph)targetGraphObj, graphObject);
			addNode(graph, graphObject);
		}
	}

}
