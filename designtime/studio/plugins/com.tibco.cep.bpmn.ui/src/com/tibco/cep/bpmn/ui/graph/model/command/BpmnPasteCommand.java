package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tibco.cep.bpmn.ui.transfer.BPMNProcessTransfer;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.TSGroupCommand;
import com.tomsawyer.interactive.command.editing.TSEPasteCommand;
import com.tomsawyer.interactive.editing.control.TSCutCopyPasteControl;
import com.tomsawyer.util.option.TSOptionData;


/**
 * 
 * @author majha
 *
 */
public class BpmnPasteCommand extends TSEPasteCommand {
	private static final long serialVersionUID = 6510253592711757038L;
	private BpmnDiagramManager diagramManager;
	private TSGraphObject paramTSGraphObject;
	protected TSGraph rootGraph;
	protected TSEGraph currentGraph;
	protected EObjectWrapper<EClass, EObject> process;
	protected EObjectWrapper<EClass, EObject> lane;
	private Map<String, Object> copyMap;
	private List<TSENode> pastedNodes;
	private List<TSEEdge> pastedEdges;
	private TSGroupCommand deleteGrpCommand;
	private String projectname;
	
	public BpmnPasteCommand(BpmnDiagramManager diagramManager, TSGraphObject paramTSGraphObject,
			TSCutCopyPasteControl paramTSCutCopyPasteControl,
			TSPoint paramTSPoint) {
		super(paramTSGraphObject, paramTSCutCopyPasteControl, paramTSPoint);
		this.diagramManager =diagramManager;
		
		this.paramTSGraphObject = paramTSGraphObject;
		this.projectname = diagramManager.getProject().getName();
	}
	
	public BpmnPasteCommand(BpmnDiagramManager diagramManager, TSGraphObject paramTSGraphObject,
			TSCutCopyPasteControl paramTSCutCopyPasteControl,
			TSPoint paramTSPoint, TSOptionData paramTSOptionData) {
		super(paramTSGraphObject, paramTSCutCopyPasteControl, paramTSPoint, paramTSOptionData);
		this.diagramManager =diagramManager;
		copyMap = diagramManager.getCopyMap();
		this.paramTSGraphObject = paramTSGraphObject;
		this.projectname = diagramManager.getProject().getName();
	}
		
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doAction() throws Throwable {
		copyMap = BPMNProcessTransfer.getInstance().getCopyMap();
		if(paramTSGraphObject instanceof TSEGraph)
			currentGraph = (TSEGraph)paramTSGraphObject;
		else
			currentGraph = (TSEGraph)paramTSGraphObject.getOwnerGraph();
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			rootGraph = currentGraph;
			process = lane;
		}
		else{
			rootGraph = currentGraph.getGreatestAncestor();
			process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		}
		// TODO Auto-generated method stub
		List oldNodeSet = new ArrayList<TSENode>(currentGraph.nodeSet);
		List oldEdgeSet = new ArrayList<TSENode>(currentGraph.edgeSet);
		super.doAction();
		pastedNodes = new ArrayList<TSENode>(currentGraph.nodeSet);
		pastedEdges = new ArrayList<TSEEdge>(currentGraph.edgeSet);
		pastedNodes.removeAll(oldNodeSet);
		pastedEdges.removeAll(oldEdgeSet);
		pasteEmfModel(pastedNodes, pastedEdges,  process, lane);
	}
	
	@Override
	protected void undoAction() throws Throwable {
		deleteGrpCommand = new TSGroupCommand();
		for (TSENode node : pastedNodes) {
			EClass nodeType = (EClass) node
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			ENamedElement extType = (EClass) node
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) ((BpmnDiagramManager) diagramManager)
					.getModelGraphFactory()
					.getCommandFactory(nodeType, extType);
			TSCommand cmd = (TSCommand) cf.getCommand(
					IGraphCommand.COMMAND_DELETE, null, node);
			if (cmd instanceof AbstractDeleteNodeCommand) {
				((AbstractDeleteNodeCommand) cmd).setDeleteEmfModelOnly(true);
			}
			if (cmd != null)
				deleteGrpCommand.add(cmd);
		}

		for (TSEEdge edge : pastedEdges) {
			EClass edgeType = (EClass) edge
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			AbstractEdgeCommandFactory cf = (AbstractEdgeCommandFactory) ((BpmnDiagramManager) diagramManager)
					.getModelGraphFactory().getEdgeCommandFactory(edgeType);
			boolean connectedToConnector = false;
			if(edge.getSourceConnector() != null){
				TSConnector sourceConnector = edge.getSourceConnector();
				Object userObject = sourceConnector.getUserObject();
				if (userObject != null && (userObject instanceof EObject)) {
					EObject connec = (EObject) userObject;
					if(connec.eClass().equals(BpmnModelClass.BOUNDARY_EVENT))
						connectedToConnector = true;
				}
			}
			TSCommand cmd = (TSCommand) cf.getCommand(
					IGraphCommand.COMMAND_DELETE, edge, connectedToConnector);
			if (cmd instanceof AbstractDeleteEdgeCommand) {
				((AbstractDeleteEdgeCommand) cmd).setDeleteEmfModelOnly(true);
			}
			if (cmd != null)
				deleteGrpCommand.add(cmd);
		}

		deleteGrpCommand.execute();
		super.undoAction();
	}
	
	@Override
	protected void redoAction() throws Throwable {
		super.redoAction();
		pasteEmfModel(pastedNodes, pastedEdges, process, lane);
	}
	
	private void pasteEmfModel(List<TSENode> nodeList, List<TSEEdge> edgeList, EObjectWrapper<EClass, EObject> procObj, EObjectWrapper<EClass, EObject> laneObj){		
		for (TSENode node : nodeList) {
			pasteNode(node, procObj, laneObj);
		} 
		
		for (TSEEdge edge : edgeList) {
			pasteEdge(edge, procObj, laneObj);
		} 
	}
	
	@SuppressWarnings("unchecked")
	private void pasteNode(TSENode node, EObjectWrapper<EClass, EObject> procObj, EObjectWrapper<EClass, EObject> laneObj){
		EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass extType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		if(nodeType == null)
			return;
		
		String nodeName =BpmnGraphUtils.getNodeName(nodeType, extType);
		String id = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_COPY_ID);
		EObject object = (EObject)copyMap.get(id);
		if (object != null) {
			EObjectWrapper<EClass, EObject> useInstance = createCopy(procObj,object, nodeName);
			BpmnModelUtils.setInputMapperXslt(useInstance.getEInstance(), "");
			BpmnModelUtils.setOutputMapperXslt(useInstance.getEInstance(), "");
			diagramManager.getModelController().addNodeToEmfModel(
					useInstance, procObj, laneObj);
			node.setUserObject(useInstance.getEInstance());

			String name = "";
			if(useInstance.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME))
				name = (String)useInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			else if (ExtensionHelper.isValidDataExtensionAttribute(
					useInstance,
					BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
				EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(useInstance);
				if (valWrapper != null)
					name = valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
			}
			
			if(name != null && !name.trim().isEmpty()){
				if(!BpmnModelClass.TEXT_ANNOTATION.equals(useInstance.getEClassType()))
					refreshLabel(node, name);
			}
			diagramManager.getModelChangeAdapterFactory().adapt(useInstance.getEInstance(),ModelChangeListener.class);
			ExtensionHelper.resetExtensionDeinitions(useInstance);
			TSGraph childGraph = node.getChildGraph();
			if(childGraph != null){
				List<TSENode> childNodes = new ArrayList<TSENode>(childGraph.nodeSet);
				List<TSEEdge> childEdges = new ArrayList<TSEEdge>(childGraph.edgeSet);
				pasteEmfModel(childNodes, childEdges, useInstance, useInstance);
			}
		}
	}
	
	
	private void refreshLabel(TSENode node, String nodeName){
		EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass nodeExtType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		String toolId = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String resUrl = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		AbstractNodeUIFactory nodeUIFactory ;
		if (nodeExtType != null) {
			ExpandedName classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(nodeExtType);
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
					.getNodeUIFactory(nodeName, resUrl, toolId, nodeType, classSpec);
		}
		else
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(nodeName, resUrl,toolId, nodeType);
		// ((TSENodeLabel)(((TSENode)fTSENode).labels().get(0))).setDefaultOffset();
		nodeUIFactory.addNodeLabel(node, nodeName);
	}
	

	private void pasteEdge(TSEEdge edge, EObjectWrapper<EClass, EObject> procObj, EObjectWrapper<EClass, EObject> laneObj) {
		EClass edgeType = (EClass) edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		if(edgeType == null)
			return;
		
		String id = (String) edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_COPY_ID);
		EObject object = (EObject)copyMap.get(id);
		if (object != null) {
			final EObjectWrapper<EClass, EObject> start = EObjectWrapper
					.wrap((EObject) edge.getSourceNode()
							.getUserObject());
			final EObjectWrapper<EClass, EObject> end = EObjectWrapper
					.wrap((EObject) edge.getTargetNode()
							.getUserObject());
			EObjectWrapper<EClass, EObject> useInstance =createCopy(procObj,object, null);
			diagramManager.getModelController().addEdgeToEmfModel(
					useInstance, start, end, procObj, laneObj);
			edge.setUserObject(useInstance.getEInstance());
			SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)edge.getSourceNode(),diagramManager);
			ExtensionHelper.resetExtensionDeinitions(useInstance);
		}
	}
	
	private EObjectWrapper<EClass, EObject> createCopy(EObjectWrapper<EClass, EObject> process, EObject object, String name){
		EObjectWrapper<EClass, EObject> newWrapper= BpmnModelUtils.createEObjectCopy(object, process, projectname, name);

	
		if (BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(newWrapper
				.getEClassType())) {
			newWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);// as we need to iterated through each node again and insert its model
		}
		return newWrapper;
	}
	
	
}
