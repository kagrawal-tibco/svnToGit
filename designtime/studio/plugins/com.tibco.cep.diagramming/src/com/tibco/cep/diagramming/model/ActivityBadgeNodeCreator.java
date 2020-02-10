package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.ActivityBadgeNodeUI;
import com.tibco.cep.diagramming.utils.ActivityTypes;
import com.tibco.cep.diagramming.utils.TSConstants;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * 
 * @author hitesh
 *
 */

public class ActivityBadgeNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;

	private String nodeName;
	private ActivityTypes activityType;
	private int type = -1;
	private String node_type = "";
	
	public ActivityBadgeNodeCreator(String name, ActivityTypes activityType) {
		this.nodeName = name;
		this.activityType = activityType;
	}

	public ActivityBadgeNodeCreator(String name, int type, String node_type) {
		this.nodeName = name;
		this.type = type;
		this.node_type = node_type;
	}
	
	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph);
		ActivityBadgeNodeUI ui = null;
		if (type == -1) {
			ui = new ActivityBadgeNodeUI();
		} else {
			ui = new ActivityBadgeNodeUI(type);
		}
		node.setSize(80, 80);
		node.setName(nodeName);
		node.setAttribute(TSConstants.TYPE_ATTR, activityType);
		node.setAttribute(TSConstants.NODE_TYPE_ATTR, node_type);
		node.setUI((TSEObjectUI) ui);
		return node;
	}
}
