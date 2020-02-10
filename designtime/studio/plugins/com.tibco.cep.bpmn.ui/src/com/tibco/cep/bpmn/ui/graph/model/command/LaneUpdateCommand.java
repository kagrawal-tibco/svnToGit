package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;

public class LaneUpdateCommand extends AbstractUpdateNodeCommand implements
		IGraphCommand<TSENode> {
	
	private static final long serialVersionUID = 4872132728431669326L;
	@SuppressWarnings("unused")
	private EClass nodeType;
	@SuppressWarnings("unused")
	private String nodeName;
	@SuppressWarnings("unused")
	private String nodeLabel;
	private TSGraph rootGraph;
	private TSEGraph currentGraph;
	@SuppressWarnings("unused")
	private EObjectWrapper<EClass, EObject> process;
	@SuppressWarnings("unused")
	private EObjectWrapper<EClass, EObject> lane;
	private Map<String, Object> updateList;

	public LaneUpdateCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSENode node,Map<String, Object> updateList) {
		super(type, controller, modelType, extType, node);
		this.updateList = updateList;
	}
	
	protected void doAction() throws Throwable {
		boolean update = (updateList.size() > 0);
		TSENode laneNode = getNodeOrGraph();
		String name = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		String label = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		String toolId = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String attachedRes = (String)laneNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		
		ExpandedName classSpec = null;
		if (getExtendedType() != null)
			classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(getExtendedType());
		AbstractNodeUIFactory nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
								.getNodeUIFactory(name,attachedRes, toolId,getModelType(), classSpec);
		nodeUIFactory.decorateNode(laneNode);
		nodeUIFactory.layoutNode(laneNode);
		super.doAction();
		EObject userObject = (EObject)laneNode.getUserObject();
		EObjectWrapper<EClass, EObject> laneWrapper = EObjectWrapper.wrap(userObject);
		EClass classType = laneWrapper.getEClassType();
		if(classType.equals(getModelType())){
			rootGraph = ((TSEGraph)getNodeOrGraph().getOwnerGraph()).getGreatestAncestor();
			currentGraph = (TSEGraph)getNodeOrGraph().getOwnerGraph();
			
			nodeType = (EClass) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			nodeName = (String) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//			nodeLabel = (String) getNodeOrGraph().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
			
			process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
			lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
			getNodeOrGraph().setUserObject(laneWrapper.getEInstance());
			update = true;
		}else {
			if(!laneWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).equals(name))
				laneWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
			
		}
		if (update)
			getModelController().updateEmfModel(laneWrapper, updateList);
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
	
		
	}
	
	@Override
	protected void redoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.redoAction();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSEAddNodeCommand.class ) {
			return this;
		}  
		return null;
	}
	
	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
		nodeLabel = null;
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		updateList.clear();
		updateList = null;
		super.finalize();
	}

}
