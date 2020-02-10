package com.tibco.cep.bpmn.ui.graph.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramHelper;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tomsawyer.drawing.TSDNode;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

/**
 * @author pdhar
 *
 */
public abstract class AbstractNodeUIFactory extends TSNodeBuilder implements IGraphUIFactory {

	private static final long serialVersionUID = 926119983209125652L;
	private BpmnLayoutManager layoutManager;
	private String nodeName;
	private String toolId;
	private String referredBEResource;
	protected EClass nodeType;
	private IDiagramModelAdapter diagramModelAdapter;
	private TSNodeUI nodeUI;
	
	public AbstractNodeUIFactory(String name, String referredBEResource, String toolId, BpmnLayoutManager layoutManager,  EClass nodeType,Object ...args) {
		this.layoutManager = layoutManager;
		this.nodeName = name;
		this.nodeType = nodeType;
		this.toolId = toolId != null? toolId:"";
		this.referredBEResource = referredBEResource;
		TSEImage.setLoaderClass(getClass());
		this.diagramModelAdapter = this.layoutManager.getDiagramManager().getDiagramModelAdapter();
		setNodeUI((TSNodeUI) initGraphUI(args));
		// this.nodeUI = (TSNodeUI) initGraphUI(args);
	}
	
	public TSNodeUI getNodeUI() {
		return this.nodeUI;
	}
	
	public void setNodeUI(TSNodeUI nodeUI) {
		this.nodeUI = nodeUI;
	}
	
	public IDiagramModelAdapter getDiagramModelAdapter() {
		return diagramModelAdapter;
	}
	
	public BpmnLayoutManager getLayoutManager() {
		return layoutManager;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
		
	public String getToolId() {
		return toolId;
	}


	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public String getAttachedBEResource() {
		return referredBEResource;
	}

	public void setAttachedBEResource(String attachedBEResource) {
		this.referredBEResource = attachedBEResource;
	}


	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public TSENode addNode(TSEGraph graph) {
		// XYZ
		TSENode node = super.addNode(graph);

//		TSNodeBuilder nodeBuilder = this.layoutManager.getDiagramManager().getGraphManager().getNodeBuilder();
//		this.layoutManager.getDiagramManager().getGraphManager().setNodeBuilder(null);
//		TSENode node = this.diagramModelAdapter.addNode();
//		node.setUI(this.nodeUI);
//		this.layoutManager.getDiagramManager().getGraphManager().setNodeBuilder(nodeBuilder);
		
		node.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, getNodeType());		
		node.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, getNodeName());	
		node.setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, getToolId());
		node.setAttribute(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL,getAttachedBEResource());
		// node.setTag(getNodeName());
		
		decorateNode(node);
		layoutNode(node);
		return node;
	}
	
	public void addNodeLabel(TSENode node, String label) {
		if (node.labels().isEmpty()) {
			// TSENodeLabel nodeLabel = ((TSENodeLabel) node.addLabel());
			TSENodeLabel nodeLabel = this.diagramModelAdapter.addNodeLabel(node);
			if(nodeLabel.getUI() != null)
				((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
			if (label == null) {
				label = "";
			}
			nodeLabel.setName(label);
			nodeLabel.setOffset(TSDNode.NODE_BOTTOM, 10.0);
			this.getLayoutManager().setNodeLabelOptions(nodeLabel);
			Object userObject = node.getUserObject();
			if(userObject != null && userObject instanceof EObject){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap((EObject)userObject);
				TSConstPoint labelCenter = nodeLabel.getCenter();
				if(layoutManager != null && layoutManager.getDiagramManager() != null && layoutManager.getDiagramManager() instanceof IBpmnDiagramManager){
					Map<String, Object> updateList = new HashMap<String, Object>();
					IBpmnDiagramManager diagramManager = (IBpmnDiagramManager)layoutManager.getDiagramManager();
					EObjectWrapper<EClass, EObject> labelPoint = diagramManager.getModelController().createPoint(labelCenter.getX(), labelCenter.getY());
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT, labelPoint.getEInstance());
					EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);
					if(addDataExtensionValueWrapper != null){
						addDataExtensionValueWrapper.getEInstance().eSetDeliver(false);
						diagramManager.getModelController().updateEmfModel(wrap, updateList);
						addDataExtensionValueWrapper.getEInstance().eSetDeliver(true);
					}
				}
				
			}
		}else{
			TSENodeLabel nodeLabel = (TSENodeLabel)node.labels().get(0);
			nodeLabel.setName(label);
		}
	}
	
	public void updateNodeToolTip(TSENode node){
		node.setTooltipText(getNodeToolTip(node));
	}

	/**
	 * @param node
	 */
	public abstract void layoutNode(TSENode node);

	/**
	 * @param node
	 */
	public abstract void decorateNode(TSENode node);
	
	/**
	 * @return
	 */
	public String getNodeToolTip(TSENode node){
		return BpmnDiagramHelper.getNodeToolTip(node);
	}
	
	/**
	 * @return
	 */
	public EClass getNodeType() {
		return nodeType;
	}
	
	
	


}
