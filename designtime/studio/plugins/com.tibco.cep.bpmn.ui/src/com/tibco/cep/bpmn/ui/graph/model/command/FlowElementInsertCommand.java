package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public abstract class FlowElementInsertCommand extends AbstractInsertNodeCommand {

	private static final long serialVersionUID = 5895955611207570778L;
	protected EClass nodeType;
	protected String nodeName;
	protected String nodeLabel;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;
	protected List<EObjectWrapper<EClass, EObject>> incomingEdge;
	protected List<EObjectWrapper<EClass, EObject>> outgoingEdge;

	
	public FlowElementInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node) {
		super(type, controller, modelType, extType, graph, node);
		// TODO Auto-generated constructor stub
	}
	protected void doAction() throws Throwable {
		super.doAction();
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
		createEmfModel(false);
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
		
		EObjectWrapper<EClass, EObject> modelObject = EObjectWrapper.wrap((EObject)getNode().getUserObject());
		
		// check if there are edges ending on this node
		incomingEdge= new ArrayList<EObjectWrapper<EClass, EObject>>();
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
		createEmfModel(true);
	}
	
	protected void createEmfModel(boolean isRedo){
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String attachedRes = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		
		EObject createElement = createElement();
		String attrNameForAttachedResource = getAttrNameForAttachedResource();
		if(attrNameForAttachedResource != null &&  !attrNameForAttachedResource.isEmpty()){
			Map<String, Object> updateList = new HashMap<String, Object>();
			updateList.put(attrNameForAttachedResource, parseAttachedResource(attachedRes));
			
			getModelController().getDiagramManager().addAdditionalModel(updateList, createElement, attachedRes);
			modelController.updateEmfModel(EObjectWrapper.wrap(createElement), updateList);
		}
		getNode().setUserObject(createElement);
		
		if (!isRedo) {
			// TODO is it right place? added this as quick fix
			ExpandedName classSpec = null;
			if(getExtendedType() != null)
				classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(getExtendedType());
			AbstractNodeUIFactory nodeUIFactory = BpmnGraphUIFactory
					.getInstance(null).getNodeUIFactory(nodeName,attachedRes,toolId,
							getModelType(), classSpec);
			nodeUIFactory.decorateNode(getNode());
			String label = EObjectWrapper.wrap(createElement).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			nodeUIFactory.addNodeLabel(node, label);
			// nodeUIFactory.layoutNode(getNode());
		}
	}
	
	protected Object parseAttachedResource(String attachedRes){
		if(nodeType.equals(BpmnModelClass.INFERENCE_TASK)){
			ArrayList<String> arrayList = new ArrayList<String>();
			String[] split = attachedRes.split(",");
			Collections.addAll(arrayList, split);
			return arrayList;
		}
		return attachedRes;
	}
	

	protected String getAttrNameForAttachedResource() {
		EClass type = (EClass) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		String name = null;
		if (type.equals(BpmnModelClass.RULE_FUNCTION_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION;
		else if (type.equals(BpmnModelClass.BUSINESS_RULE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION;
		else if (type.equals(BpmnModelClass.INFERENCE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_RULES;
		else if (type.equals(BpmnModelClass.SERVICE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_WSDL;
		else if (type.equals(BpmnModelClass.RECEIVE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
		else if (type.equals(BpmnModelClass.SEND_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
		else if (BpmnModelClass.EVENT.isSuperTypeOf(type))
			name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
		else if (BpmnModelClass.CALL_ACTIVITY.isSuperTypeOf(type))
			name= BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT;
		
		return name;
	}
	
	
	
	protected abstract EObject createElement();

	protected abstract void removeElement(EObjectWrapper<EClass, EObject> modelObject);

	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
		nodeLabel = null;
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
