package com.tibco.cep.bpmn.ui.graph.model.command;

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
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public class LaneInsertCommand extends AbstractInsertNodeCommand {

	private static final long serialVersionUID = 3397206420409186934L;
	@SuppressWarnings("unused")
	private EClass nodeType;
	private String nodeName;
	@SuppressWarnings("unused")
	private String nodeLabel;
	private TSGraph rootGraph;
	private TSEGraph currentGraph;
	@SuppressWarnings("unused")
	private EObjectWrapper<EClass, EObject> process;
	private EObjectWrapper<EClass, EObject> lane;

	
	
	public LaneInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node) {
		super(type, controller, modelType, extType, graph, node);
		// TODO Auto-generated constructor stub
	}

	protected void doAction() throws Throwable {
		super.doAction();
		createEmfModel(false);
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
		TSENode laneNode = getNode();
		
		rootGraph = ((TSEGraph)laneNode.getOwnerGraph()).getGreatestAncestor();
		currentGraph = (TSEGraph)laneNode.getOwnerGraph();
		
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
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
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap((EObject)laneNode.getUserObject());
		getModelController().removeLane(wrapper,parentLaneSet);
		getNode().nullifyUserObject();
	}	
	
	protected void redoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.redoAction();
		createEmfModel(true);
	}
	
	
	private void createEmfModel(boolean isRedo){
		TSENode laneNode = getNode();
		String name = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		String label = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		String toolId = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String attachedRes = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		
		ExpandedName classSpec = null;
		if(getExtendedType() != null)
			classSpec = BpmnMetaModel.INSTANCE
				.getExpandedName(getExtendedType());
		AbstractNodeUIFactory nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
			.getNodeUIFactory(name,attachedRes, toolId, getModelType(), classSpec);
		nodeUIFactory.decorateNode(laneNode);
		nodeUIFactory.layoutNode(laneNode);
		laneNode.setName(name);
		
		rootGraph = ((TSEGraph)laneNode.getOwnerGraph()).getGreatestAncestor();
		currentGraph = (TSEGraph)laneNode.getOwnerGraph();
		
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
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
		
		if (!isRedo) {
			EObjectWrapper<EClass, EObject> laneWrapper = getModelController().createLane(name,parentLaneSet);
			laneNode.setUserObject(laneWrapper.getEInstance());
			TSEGraph childGraph = (TSEGraph) laneNode.getChildGraph();
			if (childGraph != null) {
				childGraph.setUserObject(laneWrapper.getEInstance());
			}
		}
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
