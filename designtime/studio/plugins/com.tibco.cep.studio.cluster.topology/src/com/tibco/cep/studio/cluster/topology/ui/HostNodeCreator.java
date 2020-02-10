package com.tibco.cep.studio.cluster.topology.ui;

import com.tibco.cep.diagramming.ui.NoTagImageNodeUI;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.shared.TSUIConstants;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.interactive.command.editing.TSESetAttributeCommand;

/**
 * @author hitesh
 *
 */
@SuppressWarnings("serial")
public class HostNodeCreator extends TSNodeBuilder {
	
	private TSEImage image;
	
	public HostNodeCreator() {
		TSEImage.setLoaderClass(this.getClass());		
		this.image = ClusterTopologyDiagramManager.HOST_IMAGE;
	}
	
	public HostNodeCreator(String imageName) {
		TSEImage.setLoaderClass(this.getClass());		
		this.image = new TSEImage(this.getClass(), "/icons/" + imageName);
	}
	
	public TSENode addNode(TSEGraph graph) {	
		NoTagImageNodeUI ui = new NoTagImageNodeUI(this.image);
		//ui.setImage(this.image);
		TSENode node = super.addNode(graph);
		//node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		//String tag = ClusterTopologyUtils.UNIQUE_HOST_ID + ClusterTopologyUtils.getNodeNameNumber(ClusterTopologyConstants.HOST_NODE);
		//node.setName(tag);
		TSESetAttributeCommand attributeCommand = new TSESetAttributeCommand(node, "Text_Orientation", new Integer(TSUIConstants.CENTER_JUSTIFY));
		//node.setAttribute("Text_Orientation", new Integer(TSUIConstants.CENTER_JUSTIFY));
		attributeCommand.execute();
		TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
		((TSEAnnotatedUI)nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		//nodeLabel.setName(tag);
		nodeLabel.setDefaultOffset();
		node.setAttribute(ClusterTopologyConstants.NODE_TYPE,
			Integer.valueOf(ClusterTopologyConstants.HOST_NODE));
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		node.setUI(ui);	
		return node;
	}
}