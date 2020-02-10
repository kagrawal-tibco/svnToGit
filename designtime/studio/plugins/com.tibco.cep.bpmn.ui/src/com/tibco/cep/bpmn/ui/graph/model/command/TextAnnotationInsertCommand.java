package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager.BpmnGraphManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.events.TSGraphChangeEvent;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.events.TSEEventManager;

/**
 * 
 * @author majha
 *
 */
public class TextAnnotationInsertCommand extends AbstractInsertNodeCommand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4066284871034983909L;
	protected EClass nodeType;
	protected String nodeName;
	protected String nodeLabel;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;
	protected List<EObjectWrapper<EClass, EObject>> incomingEdge;
	protected List<EObjectWrapper<EClass, EObject>> outgoingEdge;
	private BpmnDiagramManager diagramManager;
	
	public TextAnnotationInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node,BpmnDiagramManager diagramManager) {
		super(type, controller, modelType, extType, graph, node);
		this.diagramManager=diagramManager;
	}

	protected void doAction() throws Throwable {
		super.doAction();
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
				EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.ASSOCIATION.isSuperTypeOf(associationWrapper.getEClassType())){
					incomingEdge.add(associationWrapper);
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
				EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.wrap((EObject)edgeUserObj);
				if(BpmnModelClass.ASSOCIATION.isSuperTypeOf(associationWrapper.getEClassType())){
					outgoingEdge.add(associationWrapper);
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
	
	private void createEmfModel(boolean isRedo){
		currentGraph = (TSEGraph)getNode().getOwnerGraph();
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		TSEGraphManager graphManager;
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			rootGraph = currentGraph;
			process = lane;
		}
		else{
			rootGraph = ((TSEGraph)getNode().getOwnerGraph()).getGreatestAncestor();
			process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		}
		
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		String toolId = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String resUrl = (String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		
		EObject createElement = createElement();
		getNode().setUserObject(createElement);
		
		if (!isRedo) {
			// TODO is it right place? added this as quick fix
			ExpandedName classSpec = null;
			if(getExtendedType() != null)
				classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(getExtendedType());
			
			if(this.diagramManager.getGraphManager()==null)
				graphManager =new BpmnGraphManager();
			else
				graphManager=this.diagramManager.getGraphManager();
			((TSEEventManager)graphManager.getEventManager()).addGraphChangeListener(
					graphManager,
					this.diagramManager.getDiagramChangeListener(),
					TSGraphChangeEvent.ANY_NODE | TSGraphChangeEvent.ANY_EDGE | TSGraphChangeEvent.EDGE_ENDNODE_CHANGED);
			AbstractNodeUIFactory nodeUIFactory = BpmnGraphUIFactory
					.getInstance(null).getNodeUIFactory(nodeName,resUrl, toolId,
							getModelType(), classSpec);
			nodeUIFactory.decorateNode(getNode());
			nodeUIFactory.layoutNode(getNode());
		}
		
	}
	
	protected  EObject createElement() {
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createTextAnnotation(nodeName,getModelType(),process,lane);
		return modelObject.getEInstance();
	}

	protected  void removeElement(EObjectWrapper<EClass, EObject> modelObject){
		getModelController().removeTextAnnotation(modelObject,process,lane,incomingEdge,outgoingEdge);
	}

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
