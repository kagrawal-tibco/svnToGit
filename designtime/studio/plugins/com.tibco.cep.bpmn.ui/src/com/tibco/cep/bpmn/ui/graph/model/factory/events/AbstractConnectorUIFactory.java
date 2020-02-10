package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramHelper;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.IGraphUIFactory;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSConnectorBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSConnectorUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * @author majha
 *
 */
public abstract class AbstractConnectorUIFactory  extends TSConnectorBuilder implements IGraphUIFactory{
	private static final long serialVersionUID = -2326087414762107093L;
	private BpmnLayoutManager layoutManager;
	private String connectorName;
	private String toolId;
	private String referredBEResource;
	protected EClass connectorType;

	protected EClass connectorDefType;
	protected TSENode parentNode;
	private IDiagramModelAdapter diagramModelAdapter;
	
	
	public AbstractConnectorUIFactory(TSENode parentNode,String name, String referredBEResource, String toolId, BpmnLayoutManager layoutManager,  EClass nodeType, EClass eventDefType) {
		this.layoutManager = layoutManager;
		this.connectorName = name;
		this.referredBEResource = referredBEResource;
		this.connectorType = nodeType;
		this.connectorDefType = eventDefType;
		this.toolId = toolId != null? toolId:"";
		this.parentNode = parentNode;
		TSEImage.setLoaderClass(getClass());
		setConnectorUI((TSConnectorUI) initGraphUI(eventDefType));
		this.diagramModelAdapter = this.layoutManager.getDiagramManager().getDiagramModelAdapter();	
	}
	
	abstract public TSEObjectUI initGraphUI(Object ...args);

	public BpmnLayoutManager getLayoutManager() {
		return layoutManager;
	}
	
	public IDiagramModelAdapter getDiagramModelAdapter() {
		return this.diagramModelAdapter;
	}
	
	public String getConnectorName() {
		return connectorName;
	}
	
	public void setConnectorName(String name) {
		this.connectorName = name;
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


	@Override
	// XYZ?
	public TSEConnector addConnector(TSENode node) {
		TSEConnector connector = super.addConnector(node);
		connector.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, getConnectorType());		
		connector.setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, getConnectorDefType());	
		connector.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, getConnectorName());	
		connector.setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, getToolId());

		node.setAttribute(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL,getAttachedBEResource());
		connector.setSize(20.0, 20.0);
		decorateNode(connector);
		layoutNode(connector);
		return connector;
	}
	
	public void removeExistingConnectors(TSENode node) {
		List<?> connectors = node.connectors();
		for (Object object : connectors) {
			 node.remove((TSConnector)object);
		}
	}


	public void updateNodeToolTip(TSEConnector connector){
		connector.setTooltipText(getNodeToolTip(connector));
	}

	/**
	 * @param node
	 */
	abstract public void layoutNode(TSEConnector connector);

	/**
	 * @param node
	 */
	abstract public void decorateNode(TSEConnector connector);
	
	/**
	 * @return
	 */
	public String getNodeToolTip(TSEConnector connector){
		return BpmnDiagramHelper.getNodeToolTip(connector);
	}
	
	/**
	 * @return
	 */
	public EClass getConnectorType() {
		return connectorType;
	}
	
	/**
	 * @return
	 */
	public EClass getConnectorDefType() {
		return connectorDefType;
	}
	
	
	


}
