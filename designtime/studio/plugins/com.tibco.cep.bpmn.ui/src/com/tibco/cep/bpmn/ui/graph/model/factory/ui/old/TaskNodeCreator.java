package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.ActivityTypes;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;

/**
 * 
 * @author ggrigore
 *
 */
public class TaskNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;
	private String nodeName;
	private ActivityTypes activityType;
	@SuppressWarnings("unused")
	private TSEImage image;
	
	public TaskNodeCreator(String name, ActivityTypes activityType) {
		this.nodeName = name;
		this.activityType = activityType;
		TSEImage.setLoaderClass(this.getClass());		
	}
	
	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph);
		decorateTaskNode(node, this.nodeName, this.activityType);
		return node;
	}
	
	public static void decorateTaskNode(TSENode node, String name, ActivityTypes activityType) {
		TSEImage image = null;
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		TaskNodeUI ui = new TaskNodeUI();
		boolean useLabels = BpmnUIPlugin.getDefault().getPreferenceStore().getBoolean(BpmnPreferenceConstants.PREF_DISPLAY_FULL_NAMES);
		ui.setDrawTag(!useLabels);
		
		if (activityType != null) {
			switch(activityType) {
			case ACTIVITY_RF:
				image = TaskNodeUI.SCRIPT_IMAGE;
				break;
			case ACTIVITY_TABLE:
				image = TaskNodeUI.TABLE_IMAGE;
				break;
			case ACTIVITY_EVENT:
				image = TaskNodeUI.EVENT_IMAGE;
				break;
			case ACTIVITY_EVENT_SEND:
				image = TaskNodeUI.SEND_EVENT_IMAGE;
				break;
			case ACTIVITY_EVENT_RECEIVE:
				image = TaskNodeUI.RECEIVE_EVENT_IMAGE;
				break;
			case ACTIVITY_RULE:
				image = TaskNodeUI.RULE_IMAGE;
				break;
			case ACTIVITY_WS:
				image = TaskNodeUI.WS_IMAGE;
				break;
			case ACTIVITY_MANUAL:
				image = TaskNodeUI.MANUAL_IMAGE;
				break;
			case ACTIVITY_ONMESSAGE_EVENT:
				image = TaskNodeUI.EVENT_IMAGE;
				break;
			case ACTIVITY_TIMER_EVENT:
				image = TaskNodeUI.EVENT_IMAGE;
				break;
			default:
				DiagrammingPlugin.log("Selected unknown task type: {0}", activityType);
				image = null;
				break;
			}
		}		
		
//		ui.setBadgeImage(image);
		node.setSize(70, 56);
		node.setName(name);
		node.setUI(ui);
		node.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, activityType);
	}	
}
