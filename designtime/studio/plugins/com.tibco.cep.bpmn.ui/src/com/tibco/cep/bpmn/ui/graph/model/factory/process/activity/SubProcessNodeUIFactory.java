package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.command.EmfModelPropertiesUpdateCommand;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNCollapsedSubprocessNodeUI;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNExpandedSubprocessNodeUI;
import com.tibco.cep.diagramming.ui.CollapsedSubprocessNodeUI;
import com.tibco.cep.diagramming.ui.ExpandedSubprocessNodeUI;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.TSObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * @author pdhar
 *
 */
public class SubProcessNodeUIFactory extends AbstractActivityNodeUIFactory {

	private static final long serialVersionUID = -4734758123406455201L;
	boolean isExpanded = true;
	boolean isTriggeredByEvent = false;

	public SubProcessNodeUIFactory(String name,String referredBEResource, String toolId, BpmnLayoutManager layoutManager,boolean isExpanded, boolean isTrigByEvent) {
		super(name,referredBEResource, toolId, layoutManager,BpmnModelClass.SUB_PROCESS,isExpanded, isTrigByEvent);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.subprocess"));//$NON-NLS-1$
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		if(args.length > 0) {
			if(args[0] instanceof Boolean) {
				isExpanded = ((Boolean)args[0]);
			}
		}
		if(args.length > 1){
			if(args[1] instanceof Boolean) {
				isTriggeredByEvent = ((Boolean)args[1]);
			}
		}
		return initGraphUI();
	}

	private TSEObjectUI initGraphUI() {
		if(isExpanded) {
			// BPMNExpandedSubProcessNodeUI subprocessUI = new BPMNExpandedSubProcessNodeUI(false);
			BPMNExpandedSubprocessNodeUI subprocessUI = new BPMNExpandedSubprocessNodeUI();
			// subprocessUI.setBorderColor(new TSEColor(46,0,136));
			// subprocessUI.setFillColor(new TSEColor(255,220,81));
			subprocessUI.setFillColor(subprocessUI.getColor());
			subprocessUI.setBorderColor(RoundRectNodeUI.BORDER_COLOR);
			subprocessUI.setBorderDrawn(true);
			subprocessUI.setDrawChildGraphMark(false);
			subprocessUI.setTextAntiAliasingEnabled(true);
			 subprocessUI.setDashedBorder(isTriggeredByEvent);
			return subprocessUI;
		} else {
			BPMNCollapsedSubprocessNodeUI subprocessUI = new BPMNCollapsedSubprocessNodeUI();
			subprocessUI.setFillColor(BpmnUIConstants.SUB_PROCESS_FILL_COLOR);
			subprocessUI.setBorderDrawn(true);
			subprocessUI.setDashedBorder(isTriggeredByEvent);
			// subprocessUI.setDrawChildGraphMark(false);
			return subprocessUI;
		}
	}

	

	@Override
	public void decorateNode(TSENode node) {
		setNodeUI((TSNodeUI) initGraphUI());
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		if (!isExpanded) {
			node.setSize(80, 60);
		}

		if (node.getChildGraph() == null) {
			TSGraph graph = node.getOwnerGraph();
			// XYZ
			TSEGraph childGraph = (TSEGraph) graph.getOwnerGraphManager().addGraph();
			node.setChildGraph(childGraph);		
			node.setExpandedResizability(TSESolidObject.RESIZABILITY_NO_FIT);
		}
		String label = this.getNodeName();
		if (label == null) {
			label = "";
		}
//		node.setName(label);

//		// TODO: Manish: temporary until we allow uses to have labels for subprocesses and processes
//		node.setName(getNodeName());
//		
//		// TODO: Manish: also temporary until we get rid of all node/edge TS labels created automatically
//		// even if no label is created
//		
//		// subprocesses already have their name in expanded form.
//		TSENodeLabel nodeLabel = ((TSENodeLabel) node.addLabel());
//		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
//		nodeLabel.setName(label);
//		nodeLabel.setDefaultOffset();
//		this.getLayoutManager().setNodeLabelOptions(nodeLabel);
//		// End TODO
		
		node.setUI(getNodeUI());
	}
	
	@Override
	public void layoutNode(TSENode node) {
		if (isExpanded) {
			TSENestingManager.expand(node);
			EObject userObject = (EObject)node.getUserObject();
			boolean set = false;
			if(userObject != null && userObject.eClass().equals(BpmnModelClass.SUB_PROCESS)){
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
				if(valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH)&&
						valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH)){
					Double length = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH);
					Double breadth = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH);
					if(length != null && breadth != null){
						node.setSize(length, breadth);
						set = true;
					}
				}
				if(!set)
					node.setSize(200, 100);
			}
		}
		
		getLayoutManager().setFitNestedGraph(node, false);
		TSEGraph childGraph = (TSEGraph) node.getChildGraph();
		if(childGraph != null) {
			getLayoutManager().setLayoutOptionsForSubProcess(childGraph);
		}
	}
	

	public TSObjectUI getNodeUI(boolean b) {
		return initGraphUI(b);
	}

	public void setExpanded(boolean b, TSENode node) {
		isExpanded = b;	
		setNodeUI(getNodeUI());
		layoutNode(node);
//		DiagramManager diagramManager = getLayoutManager().getDiagramManager();
	}
	
	public void onNodeExpanded(TSENode tsNode, boolean expanded) {
//		DiagramManager diagramManager = getLayoutManager().getDiagramManager();
		if(getLayoutManager().getDiagramManager() instanceof IBpmnDiagramManager){
			IBpmnDiagramManager bDiaMgr = (IBpmnDiagramManager)getLayoutManager().getDiagramManager();
			bDiaMgr.onNodeMoved(tsNode);
			ModelController modelController = bDiaMgr.getModelController();
			Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
			EObject userObject = (EObject)tsNode.getUserObject();
			if(userObject != null){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
				if(ExtensionHelper.isValidDataExtensionAttribute(wrap, BpmnMetaModelExtensionConstants.E_ATTR_EXPANDED)){
					Map<String, Object> updateList = new HashMap<String, Object>();
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_EXPANDED, expanded);
					updateMap.put(wrap, updateList);
				}
				
			}
			
			if(updateMap.size() > 0){
				TSCommand cmd = new EmfModelPropertiesUpdateCommand(modelController, updateMap);
				cmd.execute();
			}
			
		}
	}
	
	public void setTrigByevent(boolean b, TSENode node){
		isTriggeredByEvent = b;	
		isExpanded = node.getNodeUI() instanceof ExpandedSubprocessNodeUI ? true: false;
		TSNodeUI ui = node.getNodeUI();
		if(ui instanceof ExpandedSubprocessNodeUI ){
			ExpandedSubprocessNodeUI expanUi = (ExpandedSubprocessNodeUI)ui;
			expanUi.setDashedBorder(isTriggeredByEvent);
		}else if(ui instanceof CollapsedSubprocessNodeUI ){
			CollapsedSubprocessNodeUI collUi = (CollapsedSubprocessNodeUI)ui;
			collUi.setDashedBorder(isTriggeredByEvent);
		}
	}

}
