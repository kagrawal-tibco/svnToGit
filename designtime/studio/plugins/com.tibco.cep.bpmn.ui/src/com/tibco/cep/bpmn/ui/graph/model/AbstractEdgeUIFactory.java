package com.tibco.cep.bpmn.ui.graph.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public abstract class AbstractEdgeUIFactory extends TSEdgeBuilder implements IGraphUIFactory {

	private static final long serialVersionUID = 7550099303126165368L;
	
	private BpmnLayoutManager layoutManager;
	private String edgeName;
//	private String edgeLabel;
	private EClass edgeType;
	private IDiagramModelAdapter diagramModelAdapter;

	public AbstractEdgeUIFactory(String name, EClass type, BpmnLayoutManager layoutManager) {
		super();
		this.layoutManager = layoutManager;
		this.edgeName = name;
//		this.edgeLabel = label;
		this.edgeType = type;
		setEdgeUI((TSEdgeUI) initGraphUI());
		TSEImage.setLoaderClass(getClass());
		this.diagramModelAdapter = this.layoutManager.getDiagramManager().getDiagramModelAdapter();		
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		TSECurvedEdgeUI edgeUI = new TSECurvedEdgeUI();
		edgeUI.setAntiAliasingEnabled(true);
		edgeUI.setCurvature(100);
		return edgeUI;
	}
	
	
	public IDiagramModelAdapter getDiagramModelAdapter() {
		return this.diagramModelAdapter;
	}	
	

	// XYZ
	public TSEEdge addEdge(TSEGraphManager graphManager, TSENode startNode, TSENode endNode) {
		TSEEdge edge = super.addEdge(graphManager, startNode, endNode);
		edge.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, edgeType);		
		edge.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, edgeName);
		decorateEdge(edge);
		layoutEdge(edge);
		return edge;
	}
	
	// XYZ
	public TSEEdge addEdge(TSEGraph graph,TSENode startNode,TSENode endNode) {
		// TSEEdge edge = (TSEEdge) graph.addEdge(startNode, endNode);
		TSEEdge edge = this.diagramModelAdapter.addEdge(graph, startNode, endNode);
		
		edge.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, edgeType);		
		edge.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, edgeName);
		
		edge.setUI(getEdgeUI());
		decorateEdge(edge);
		layoutEdge(edge);
		if (((TSENode)edge.getTargetNode()).getUI() instanceof TaskNodeUI) {
			// Temp hack to show red edges of a target node is glowing (due to debugging)
			if (((TaskNodeUI)((TSENode)edge.getTargetNode()).getUI()).isGlowDrawn()) {
				if (edge.getUI() instanceof TSECurvedEdgeUI) {
					((TSECurvedEdgeUI)edge.getUI()).setLineColor(TSEColor.red);
					((TSECurvedEdgeUI)edge.getUI()).setLineWidth(2);
				}
			}
		}
//		edgeName = startNode.getTagString()+"_"+endNode.getTagString();
		return edge;
	}
	
	public TSEEdge addEdge(TSEGraph graph,TSENode startNode,TSENode endNode, TSEConnector sourceConnector) {
		// TSEEdge edge = (TSEEdge) graph.addEdge(startNode, endNode);
		TSEEdge edge = addEdge(graph, startNode, endNode);
		if(sourceConnector != null)
			edge.setSourceConnector(sourceConnector);

		return edge;
	}
	
	// XYZ?
	public void addEdgeLabel(TSEEdge edge, String label) {
		boolean showLabel = true;
		if (!layoutManager.getDiagramManager().isWeb()) {
			IPreferenceStore prefstore=(BpmnUIPlugin.getDefault().getPreferenceStore());
			showLabel = prefstore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_SEQUENCE_LABEL);
		}
		if(showLabel){
			if (edge.labels().isEmpty()) {
				// TSEEdgeLabel edgeLabel = ((TSEEdgeLabel) edge.addLabel());
				TSEEdgeLabel edgeLabel = this.diagramModelAdapter.addEdgeLabel(edge);
				((TSEAnnotatedUI) edgeLabel.getUI()).setTextAntiAliasingEnabled(true);
				edgeLabel.setName(label);
				edgeLabel.setDistanceFromSource(0.5);
				edgeLabel.setOffsetY(-10.0);
				this.getLayoutManager().setEdgeLabelOptions(edgeLabel);
			}else{
				TSEEdgeLabel edgeLabel = (TSEEdgeLabel)edge.labels().get(0);
				edgeLabel.setName(label);
			}
		}

	}
	

	protected void layoutEdge(TSEEdge edge) {
		// TODO Auto-generated method stub
		
	}

	protected void decorateEdge(TSEEdge edge) {
//		TSEEdgeLabel edgeLabel = ((TSEEdgeLabel) edge.addLabel());
//		edgeLabel.setDistanceFromSource(0.5);
//		((TSEAnnotatedUI) edgeLabel.getUI()).setTextAntiAliasingEnabled(true);
//		edgeLabel.setName("");
	/*	if (this.getEdgeName() == null) {
			edgeLabel.setName("");
		}
		else {
			edgeLabel.setName(getEdgeName());
		}*/
	}

	public BpmnLayoutManager getLayoutManager() {
		return layoutManager;
	}
	
	public String getEdgeName() {
		return edgeName;
	}
	
//	public String getEdgeLabel() {
//		return edgeLabel;
//	}	

}
