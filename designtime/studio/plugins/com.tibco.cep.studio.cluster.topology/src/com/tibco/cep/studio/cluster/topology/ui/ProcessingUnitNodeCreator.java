package com.tibco.cep.studio.cluster.topology.ui;

import com.tibco.cep.diagramming.ui.ImageNodeUI;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ProcessingUnitNodeCreator extends TSNodeBuilder{

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.model.SimpleStateNodeCreator#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public TSENode addNode(TSEGraph graph) {
		ImageNodeUI ui =new ImageNodeUI(new TSEImage(this.getClass(),"/icons/agents_48x48.png"));
		TSENode node = super.addNode(graph);
		try {
			String tag = ClusterTopologyUtils.getNodeName(graph,ClusterTopologyUtils.UNIQUE_PROCESSING_UNIT);
			node.setName(tag);
			node.setAttribute(ClusterTopologyConstants.NODE_TYPE, Integer.valueOf(ClusterTopologyConstants.PU_NODE));
			node.setSize(48, 48);
			node.addLabel().setName(tag);
			node.setUI((TSEObjectUI) ui);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return node;
	}
}