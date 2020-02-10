/**
 * 
 */
package com.tibco.cep.studio.cluster.topology.ui;

import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyLayoutManager;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;

/**
 * @author hitesh
 *
 */
public class DeploymentUnitNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TSEGraph deploymentUnitNodeGraph;
	private TSEGraphManager graphManager;
	private LayoutManager layoutManager;
	//private String siteTopologyFileName;
	
	/**
	 * @param layoutManager
	 */
	public DeploymentUnitNodeCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
		//this.siteTopologyFileName=siteTopologyFileName;
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public TSENode addNode(TSEGraph graph) {
		
		DeploymentUnitNodeUI deploymentUnitNodeUI = new DeploymentUnitNodeUI();
		TSENode node = super.addNode(graph);
		//String tag = ClusterTopologyUtils.UNIQUE_DEPLOYMENT_UNIT + ClusterTopologyUtils.getNodeNameNumber(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE);
		//node.setName(tag);
		node.setSize(70, 70);
		node.setAttribute(ClusterTopologyConstants.NODE_TYPE, Integer.valueOf(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE));
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		deploymentUnitNodeGraph = (TSEGraph) graphManager.addGraph();
		node.setChildGraph(deploymentUnitNodeGraph);
		node.setUI(deploymentUnitNodeUI);
		((ClusterTopologyLayoutManager) this.layoutManager).configureChildGraphLayoutOptions(deploymentUnitNodeGraph);
		TSENestingManager.expand(node);
		
		return node;
	}
}
