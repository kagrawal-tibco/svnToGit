package com.tibco.cep.bpmn.ui.graph.handler;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SUB_PROCESS;
import static com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils.setWorkbenchSelection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeEvent;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelGraphFactory;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.drawing.TSLabel;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class GraphChangeListener extends DiagramChangeListener<BpmnDiagramManager> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5524826866235590004L;

	protected ModelGraphFactory tseEntityCreator;
	
	protected ModelController controller;
	
	// part of hack below
	double OFFSET = 100;

	public GraphChangeListener(
			BpmnDiagramManager diagramManager,
			ModelGraphFactory tseEntityCreator) {
		super(diagramManager);
		this.controller = diagramManager.getController();
		this.tseEntityCreator = tseEntityCreator;
		
	}
	
	@SuppressWarnings("unused")
	private void addDummyNode(TSEGraph childGraph, double x, double y) {
		TSENode node = (TSENode) childGraph.addNode();
		node.setVisible(true);
		node.setSize(1.0, 1.0);
		node.setCenterX(x);
		node.setCenterY(y);
	}


	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeAdded(com.tomsawyer.graphicaldrawing.TSENode)
	 */
	protected void onNodeAdded(TSENode tsNode) {
		// System.out.println("onNodeAdded");
		super.onNodeAdded(tsNode);
		final EClass nodeType = (EClass) tsNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		@SuppressWarnings("unused")
		final String nodeName = (String) tsNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
		
		// Hack: if subprocess, add dummy nodes to make child graph big even
		// though it has no nodes.
		if (nodeType != null && 
				(
						BpmnModelClass.SUB_PROCESS.isSuperTypeOf(nodeType) ||
						BpmnModelClass.LANE.isSuperTypeOf(nodeType)		
				)) {
			if (tsNode.getChildGraph() != null) {
				manager.refreshNode(tsNode);
			}
		}	
		
			
    	// set left to right orientation for subgraphs
    	if ( nodeType != null && SUB_PROCESS.isSuperTypeOf(nodeType)) {
    		if (tsNode.getChildGraph() != null) {
    			manager.getLayoutManager().setLeftToRightOrthHierarchicalOptions(
    				(TSEGraph) tsNode.getChildGraph());
    		}
    	}
    	if(!manager.isSelectedMultipleGraph()) { 
    	    setWorkbenchSelection(tsNode, this.manager.getEditor(), false);
    	}
//   		this.manager.layoutDiagramOnChange();    
	    
    	// reset to Select tool after adding a node if such a preference is set
    	//This check has been needed everytime a new Process node is added
   		this.resetToSelectToolAfterChange();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeDeleted(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeDeleted(TSENode tsNode) {
    	super.onNodeDeleted(tsNode);
//   		this.manager.layoutDiagramOnChange();    	
   		//this.resetToSelectToolAfterChange();
    }
    
    @SuppressWarnings("unused")
	private void fire(Object source) {
    	final ModelChangeEvent mce = new ModelChangeEvent(source);
    	
    	//Call from a swing thread
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				manager.modelChanged(mce);
			}
		}, false); 	
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeAdded(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeAdded(TSEEdge tsEdge) {
    	super.onEdgeAdded(tsEdge);
    	//Find from, and To Nodes
    	
//    	TSNode fromNode = tsEdge.getSourceNode();
//    	TSNode toNode = tsEdge.getTargetNode();
//    	//Create Transition
//    	GraphTransition graphTransition = controller.createNewTransition();
//    	Object fromObject = fromNode.getUserObject();
//    	Object toObject = toNode.getUserObject();
//    	
//    	String fromId = BpmnGraphUtils.getId(fromObject);
//    	String toId = BpmnGraphUtils.getId(toObject);
//    	
//    	graphTransition.setFrom(fromId);
//    	graphTransition.setTo(toId);
//    	tsEdge.setUserObject(graphTransition);
//    	fire(graphTransition);
    	
//    	EObjectWrapper<EClass, EObject> sequenceFlow = controller.onEdgeAdded(tsEdge);
	    setWorkbenchSelection(tsEdge, this.manager.getEditor(), false);

//   		this.manager.layoutDiagramOnChange();   
   		this.resetToSelectToolAfterChange();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeDeleted(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeDeleted(TSEEdge tsEdge) {
    	super.onEdgeDeleted(tsEdge);
    	// TODO: update model
//   		this.manager.layoutDiagramOnChange();  
   		//this.resetToSelectToolAfterChange();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeMoved(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeMoved(TSENode tsNode) {
    	super.onNodeMoved(tsNode);
    	// TODO: update model
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeResized(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeResized(TSENode tsNode) {
    	super.onNodeResized(tsNode);
    	
    	manager.onNodeMoved(tsNode);
    	// TODO: update model
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeMoved(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeMoved(TSEEdge tsEdge) {
    	super.onEdgeMoved(tsEdge);
    	// TODO: update model
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeReconnected(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeReconnected(TSEEdge tsEdge) {
    	super.onEdgeReconnected(tsEdge);
    	// TODO: update model
   		this.manager.layoutDiagramOnChange();
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#resetToSelectToolAfterChange()
	 */
	@Override
	public void resetToSelectToolAfterChange() {
		if (manager.isResetToolOnChange()) {
			super.resetToSelectToolAfterChange();
			StudioUIUtils.resetPaletteSelection();
		}
	}
	
	@Override
	protected void onLabelChanged(TSLabel label) {
		super.onLabelChanged(label);
		String name = (String)label.getName();
		TSGraphObject owner = label.getOwner();
		if(owner instanceof TSENode){
			TSENode node = (TSENode)owner;
			EObject userObject = (EObject)node.getUserObject();
			if(userObject != null){
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				String lbl = null;
				if (userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
					lbl =userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					if(lbl == null || !lbl.equals(name))
						userObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
				}else if (ExtensionHelper.isValidDataExtensionAttribute(
						userObjWrapper,
						BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(userObjWrapper);
					if (valWrapper != null){
						lbl = valWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
						if(lbl == null || !lbl.equals(name))
							userObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL, name);
					}
				}
			}
		}
	}

}
